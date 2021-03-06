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
package org.kuali.kfs.coa.businessobject.options;

import java.util.Comparator;

import org.kuali.kfs.coa.businessobject.BudgetAggregationCode;

/**
 * This class allows us to compare two {@link BudgetAggregationCode} objects using their codes for comparison
 */
public class BudgetAggregationCodeComparator implements Comparator {

    public BudgetAggregationCodeComparator() {
    }

    /**
     * compares two {@link BudgetAggregationCode}
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object o1, Object o2) {

        BudgetAggregationCode obj1 = (BudgetAggregationCode) o1;
        BudgetAggregationCode obj2 = (BudgetAggregationCode) o2;

        return obj1.getCode().compareTo(obj2.getCode());
    }

}
