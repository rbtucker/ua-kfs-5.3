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

package org.kuali.kfs.module.bc.businessobject;

import java.util.LinkedHashMap;

import org.kuali.kfs.coa.businessobject.Chart;
import org.kuali.kfs.coa.businessobject.Organization;
import org.kuali.kfs.coa.businessobject.ResponsibilityCenter;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * 
 */
public class BudgetConstructionOrganizationReports extends PersistableBusinessObjectBase {

    private String chartOfAccountsCode;
    private String organizationCode;
    private String reportsToChartOfAccountsCode;
    private String reportsToOrganizationCode;
    private String responsibilityCenterCode;

    private String selectionKeyCode;

    private Chart chartOfAccounts;
    private Organization organization;
    private Chart reportsToChartOfAccounts;
    private Organization reportsToOrganization;
    private ResponsibilityCenter responsibilityCenter;

    /**
     * Default constructor.
     */
    public BudgetConstructionOrganizationReports() {

    }

    /**
     * Gets the chartOfAccountsCode attribute.
     * 
     * @return Returns the chartOfAccountsCode
     */
    public String getChartOfAccountsCode() {
        return chartOfAccountsCode;
    }

    /**
     * Sets the chartOfAccountsCode attribute.
     * 
     * @param chartOfAccountsCode The chartOfAccountsCode to set.
     */
    public void setChartOfAccountsCode(String chartOfAccountsCode) {
        this.chartOfAccountsCode = chartOfAccountsCode;
    }


    /**
     * Gets the organizationCode attribute.
     * 
     * @return Returns the organizationCode
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * Sets the organizationCode attribute.
     * 
     * @param organizationCode The organizationCode to set.
     */
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }


    /**
     * Gets the reportsToChartOfAccountsCode attribute.
     * 
     * @return Returns the reportsToChartOfAccountsCode
     */
    public String getReportsToChartOfAccountsCode() {
        return reportsToChartOfAccountsCode;
    }

    /**
     * Sets the reportsToChartOfAccountsCode attribute.
     * 
     * @param reportsToChartOfAccountsCode The reportsToChartOfAccountsCode to set.
     */
    public void setReportsToChartOfAccountsCode(String reportsToChartOfAccountsCode) {
        this.reportsToChartOfAccountsCode = reportsToChartOfAccountsCode;
    }


    /**
     * Gets the reportsToOrganizationCode attribute.
     * 
     * @return Returns the reportsToOrganizationCode
     */
    public String getReportsToOrganizationCode() {
        return reportsToOrganizationCode;
    }

    /**
     * Sets the reportsToOrganizationCode attribute.
     * 
     * @param reportsToOrganizationCode The reportsToOrganizationCode to set.
     */
    public void setReportsToOrganizationCode(String reportsToOrganizationCode) {
        this.reportsToOrganizationCode = reportsToOrganizationCode;
    }


    /**
     * Gets the responsibilityCenterCode attribute.
     * 
     * @return Returns the responsibilityCenterCode
     */
    public String getResponsibilityCenterCode() {
        return responsibilityCenterCode;
    }

    /**
     * Sets the responsibilityCenterCode attribute.
     * 
     * @param responsibilityCenterCode The responsibilityCenterCode to set.
     */
    public void setResponsibilityCenterCode(String responsibilityCenterCode) {
        this.responsibilityCenterCode = responsibilityCenterCode;
    }


    /**
     * Gets the chartOfAccounts attribute.
     * 
     * @return Returns the chartOfAccounts
     */
    public Chart getChartOfAccounts() {
        return chartOfAccounts;
    }

    /**
     * Sets the chartOfAccounts attribute.
     * 
     * @param chartOfAccounts The chartOfAccounts to set.
     * @deprecated
     */
    public void setChartOfAccounts(Chart chartOfAccounts) {
        this.chartOfAccounts = chartOfAccounts;
    }

    /**
     * Gets the organization attribute.
     * 
     * @return Returns the organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * Sets the organization attribute.
     * 
     * @param organization The organization to set.
     * @deprecated
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * Gets the reportsToChartOfAccounts attribute.
     * 
     * @return Returns the reportsToChartOfAccounts
     */
    public Chart getReportsToChartOfAccounts() {
        return reportsToChartOfAccounts;
    }

    /**
     * Sets the reportsToChartOfAccounts attribute.
     * 
     * @param reportsToChartOfAccounts The reportsToChartOfAccounts to set.
     * @deprecated
     */
    public void setReportsToChartOfAccounts(Chart reportsToChartOfAccounts) {
        this.reportsToChartOfAccounts = reportsToChartOfAccounts;
    }

    /**
     * Gets the reportsToOrganization attribute.
     * 
     * @return Returns the reportsToOrganization
     */
    public Organization getReportsToOrganization() {
        return reportsToOrganization;
    }

    /**
     * Sets the reportsToOrganization attribute.
     * 
     * @param reportsToOrganization The reportsToOrganization to set.
     * @deprecated
     */
    public void setReportsToOrganization(Organization reportsToOrganization) {
        this.reportsToOrganization = reportsToOrganization;
    }

    /**
     * Gets the responsibilityCenter attribute.
     * 
     * @return Returns the responsibilityCenter.
     */
    public ResponsibilityCenter getResponsibilityCenter() {
        return responsibilityCenter;
    }

    /**
     * Sets the responsibilityCenter attribute value.
     * 
     * @param responsibilityCenter The responsibilityCenter to set.
     * @deprecated
     */
    public void setResponsibilityCenter(ResponsibilityCenter responsibilityCenter) {
        this.responsibilityCenter = responsibilityCenter;
    }

    /**
     * Gets the selectionKeyCode attribute.
     * 
     * @return Returns the selectionKeyCode.
     */
    public String getSelectionKeyCode() {
        return selectionKeyCode;
    }

    /**
     * Sets the selectionKeyCode attribute value.
     * 
     * @param selectionKeyCode The selectionKeyCode to set.
     */
    public void setSelectionKeyCode(String selectionKeyCode) {
        this.selectionKeyCode = selectionKeyCode;
    }

    /**
     * @see org.kuali.rice.krad.bo.BusinessObjectBase#toStringMapper()
     */
    protected LinkedHashMap toStringMapper_RICE20_REFACTORME() {
        LinkedHashMap m = new LinkedHashMap();
        m.put("chartOfAccountsCode", this.chartOfAccountsCode);
        m.put("organizationCode", this.organizationCode);
        return m;
    }
}
