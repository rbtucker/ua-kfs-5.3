/*
 * The Kuali Financial System, a comprehensive financial management system for higher education.
 * 
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kuali.kfs.module.purap.dataaccess.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.metadata.ClassDescriptor;
import org.apache.ojb.broker.metadata.CollectionDescriptor;
import org.apache.ojb.broker.metadata.FieldDescriptor;
import org.apache.ojb.broker.platforms.PlatformMySQLImpl;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;

/**
 * This class improves the default order by in OJB by enforcing consistency between Oracle and MySQLs handling of Null values in a
 * column. Oracle by default sorts nulls last while MySQL does nulls first (i.e. 1,2,3,null MySQL:null,1,2,3; Oracle:1,2,3,null To
 * get Mysql to sort correctly we need to negate the field that is being Sorted on (i.e. ORDER BY -column DESC = 1,2,3,null while
 * ORDER BY column DESC = 3,2,1,null) the oracle default for ORDER BY is "NULLS LAST" which the above MySQL tweak should make it
 * like. This could be improved to pass in nullsFirst to decide which way to display but that would be beyond what ojb currently
 * does
 */
public class PurapItemQueryCustomizer extends KualiQueryCustomizerDefaultImpl {
    protected static final String MYSQL_NEGATION = "-";
    public final static String ORDER_BY_FIELD = "orderByField.";
    public final static String ASCENDING = "ASC";
    public final static String DESCENDING = "DESC";

    /**
     * In addition to what the referenced method does, this also fixes a mysql order by issue (see class comments)
     * @see org.apache.ojb.broker.accesslayer.QueryCustomizerDefaultImpl#customizeQuery(java.lang.Object,
     *      org.apache.ojb.broker.PersistenceBroker, org.apache.ojb.broker.metadata.CollectionDescriptor,
     *      org.apache.ojb.broker.query.QueryByCriteria)
     */
    @Override
    public Query customizeQuery(Object anObject, PersistenceBroker broker, CollectionDescriptor cod, QueryByCriteria query) {
        boolean platformMySQL = broker.serviceSqlGenerator().getPlatform() instanceof PlatformMySQLImpl;

        Map<String, String> attributes = getAttributes();
        for (String attributeName : attributes.keySet()) {
            if (!attributeName.startsWith(ORDER_BY_FIELD)) {
                continue;
            }

            String fieldName = attributeName.substring(ORDER_BY_FIELD.length());
            ClassDescriptor itemClassDescriptor = broker.getClassDescriptor(cod.getItemClass());
            FieldDescriptor orderByFieldDescriptior = itemClassDescriptor.getFieldDescriptorByName(fieldName);

            // the column to sort on derived from the property name
            String orderByColumnName = orderByFieldDescriptior.getColumnName();

            // ascending or descending
            String fieldValue = attributes.get(attributeName);
            boolean ascending = (StringUtils.equals(fieldValue, ASCENDING));
            // throw an error if not ascending or descending
            if (!ascending && StringUtils.equals(fieldValue, DESCENDING)) {
                throw new RuntimeException("neither ASC nor DESC was specified in ojb file for " + fieldName);
            }

            if (platformMySQL) {
                // by negating the column name in MySQL we can get nulls last (ascending or descending)
                String mysqlPrefix = (ascending) ? MYSQL_NEGATION : "";
                query.addOrderBy(mysqlPrefix + orderByColumnName, false);
            }
            else {
                query.addOrderBy(orderByColumnName, ascending);
            }
        }
        return query;
    }
}
