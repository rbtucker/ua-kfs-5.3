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
package org.kuali.kfs.gl.dataaccess.impl;

import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kfs.gl.businessobject.CorrectionChange;
import org.kuali.kfs.gl.dataaccess.CorrectionChangeDao;
import org.kuali.kfs.sys.KFSPropertyConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

/**
 * The OJB implementation of the CorrectionChangeDao
 */
public class CorrectionChangeDaoOjb extends PlatformAwareDaoBaseOjb implements CorrectionChangeDao {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CorrectionChangeDaoOjb.class);

    /**
     * Delete a CorrectionChange from the database
     * 
     * @param spec the CorrectionChange to delete
     * @see org.kuali.kfs.gl.dataaccess.CorrectionChangeDao#delete(org.kuali.kfs.gl.businessobject.CorrectionChange)
     */
    public void delete(CorrectionChange spec) {
        LOG.debug("delete() started");

        getPersistenceBrokerTemplate().delete(spec);
    }

    /**
     * Query the database to find qualifying CorrectionChange records
     * 
     * @param documentHeaderId the document number of a GLCP document
     * @param correctionGroupLineNumber the line number of the group within the GLCP document to find correction chagnes for
     * @return a List of correction changes
     * @see org.kuali.kfs.gl.dataaccess.CorrectionChangeDao#findByDocumentHeaderIdAndCorrectionGroupNumber(java.lang.String,
     *      java.lang.Integer)
     */
    public List findByDocumentHeaderIdAndCorrectionGroupNumber(String documentNumber, Integer correctionGroupLineNumber) {
        LOG.debug("findByDocumentHeaderIdAndCorrectionGroupNumber() started");

        Criteria criteria = new Criteria();
        criteria.addEqualTo(KFSPropertyConstants.DOCUMENT_NUMBER, documentNumber);
        criteria.addEqualTo("correctionChangeGroupLineNumber", correctionGroupLineNumber);

        QueryByCriteria query = QueryFactory.newQuery(CorrectionChange.class, criteria);

        return (List) getPersistenceBrokerTemplate().getCollectionByQuery(query);
    }

}
