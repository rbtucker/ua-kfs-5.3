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
package org.kuali.kfs.module.endow.businessobject;

import java.util.LinkedHashMap;

import org.kuali.rice.krad.bo.TransientBusinessObjectBase;

public class GLInterfaceBatchStatisticsReportHeader extends TransientBusinessObjectBase {
    private String columnHeading1;
    private String columnHeading2;
    private String columnHeading3;

    public GLInterfaceBatchStatisticsReportHeader() {
        columnHeading1 = "";
        columnHeading2 = "";
        columnHeading3 = "";
    }
    
    /**
     * Gets the columnHeading1 attribute. 
     * @return Returns the columnHeading1.
     */   
    public String getColumnHeading1() {
        return columnHeading1;
    }

    /**
     * Sets the columnHeading1 attribute. 
     * @return Returns the columnHeading1.
     */
    public void setColumnHeading1(String columnHeading1) {
        this.columnHeading1 = columnHeading1;
    }

    /**
     * Gets the columnHeading2 attribute. 
     * @return Returns the columnHeading2.
     */    
    public String getColumnHeading2() {
        return columnHeading2;
    }

    /**
     * Sets the columnHeading2 attribute. 
     * @return Returns the columnHeading2.
     */    
    public void setColumnHeading2(String columnHeading2) {
        this.columnHeading2 = columnHeading2;
    }

    /**
     * Gets the columnHeading3 attribute. 
     * @return Returns the columnHeading3.
     */    
    public String getColumnHeading3() {
        return columnHeading3;
    }

    /**
     * Sets the columnHeading3 attribute. 
     * @return Returns the columnHeading3.
     */    
    public void setColumnHeading3(String columnHeading3) {
        this.columnHeading3 = columnHeading3;
    }

    /**
     * A map of the "keys" of this transient business object
     * @see org.kuali.rice.krad.bo.BusinessObjectBase#toStringMapper()
     */
    
    protected LinkedHashMap toStringMapper_RICE20_REFACTORME() {
        LinkedHashMap pks = new LinkedHashMap<String, Object>();
        pks.put("columnHeading1",this.getColumnHeading1());
        pks.put("columnHeading2",this.getColumnHeading2());
        pks.put("columnHeading3",this.getColumnHeading3());
        
        return pks;
    }
}
