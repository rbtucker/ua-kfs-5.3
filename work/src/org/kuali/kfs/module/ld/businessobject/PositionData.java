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
package org.kuali.kfs.module.ld.businessobject;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.kfs.gl.Constant;
import org.kuali.kfs.gl.businessobject.TransientBalanceInquiryAttributes;
import org.kuali.kfs.integration.ld.LaborLedgerPositionData;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * Labor business object for PositionData
 */
public class PositionData extends PersistableBusinessObjectBase implements LaborLedgerPositionData {
    private String positionNumber;
    private String jobCode;
    private Date effectiveDate;
    private String positionEffectiveStatus;
    private String description;
    private String shortDescription;
    private String businessUnit;
    private String departmentId;
    private String positionStatus;
    private Date statusDate;
    private String budgetedPosition;
    private BigDecimal standardHoursDefault;
    private String standardHoursFrequency;
    private String positionRegularTemporary;
    private BigDecimal positionFullTimeEquivalency;
    private String positionSalaryPlanDefault;
    private String positionGradeDefault;
    private TransientBalanceInquiryAttributes dummyBusinessObject;

    /**
     * Default constructor.
     */
    public PositionData() {
        super();
        this.dummyBusinessObject = new TransientBalanceInquiryAttributes();
        this.dummyBusinessObject.setLinkButtonOption(Constant.LOOKUP_BUTTON_VALUE);
    }

    /**
     * Gets the positionNumber
     * 
     * @return Returns the positionNumber
     */
    @Override
    public String getPositionNumber() {
        return positionNumber;
    }

    /**
     * Sets the positionNumber
     * 
     * @param positionNumber The positionNumber to set.
     */
    public void setPositionNumber(String positionNumber) {
        this.positionNumber = positionNumber;
    }

    /**
     * Gets the jobCode
     * 
     * @return Returns the jobCode
     */
    @Override
    public String getJobCode() {
        return jobCode;
    }

    /**
     * Sets the jobCode
     * 
     * @param jobCode The jobCode to set.
     */
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    /**
     * Gets the effectiveDate
     * 
     * @return Returns the effectiveDate
     */
    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the effectiveDate
     * 
     * @param effectiveDate The effectiveDate to set.
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Gets the positionEffectiveStatus
     * 
     * @return Returns the positionEffectiveStatus
     */
    @Override
    public String getPositionEffectiveStatus() {
        return positionEffectiveStatus;
    }

    /**
     * Sets the positionEffectiveStatus
     * 
     * @param positionEffectiveStatus The positionEffectiveStatus to set.
     */
    public void setPositionEffectiveStatus(String positionEffectiveStatus) {
        this.positionEffectiveStatus = positionEffectiveStatus;
    }

    /**
     * Gets the description
     * 
     * @return Returns the description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description
     * 
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the shortDescription
     * 
     * @return Returns the shortDescription
     */
    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the shortDescription
     * 
     * @param shortDescription The shortDescription to set.
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * Gets the businessUnit
     * 
     * @return Returns the businessUnit
     */
    @Override
    public String getBusinessUnit() {
        return businessUnit;
    }

    /**
     * Sets the businessUnit
     * 
     * @param businessUnit The businessUnit to set.
     */
    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    /**
     * Gets the departmentId
     * 
     * @return Returns the departmentId
     */
    @Override
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the departmentId
     * 
     * @param departmentId The departmentId to set.
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets the positionStatus
     * 
     * @return Returns the positionStatus
     */
    @Override
    public String getPositionStatus() {
        return positionStatus;
    }

    /**
     * Sets the positionStatus
     * 
     * @param positionStatus The positionStatus to set.
     */
    public void setPositionStatus(String positionStatus) {
        this.positionStatus = positionStatus;
    }

    /**
     * Gets the statusDate
     * 
     * @return Returns the statusDate
     */
    @Override
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * Sets the statusDate
     * 
     * @param statusDate The statusDate to set.
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * Gets the budgetedPosition
     * 
     * @return Returns the budgetedPosition
     */
    @Override
    public String getBudgetedPosition() {
        return budgetedPosition;
    }

    /**
     * Sets the budgetedPosition
     * 
     * @param budgetedPosition The budgetedPosition to set.
     */
    public void setBudgetedPosition(String budgetedPosition) {
        this.budgetedPosition = budgetedPosition;
    }

    /**
     * Gets the standardHoursDefault
     * 
     * @return Returns the standardHoursDefault
     */
    public BigDecimal getStandardHoursDefault() {
        return standardHoursDefault;
    }

    /**
     * Sets the standardHoursDefault
     * 
     * @param standardHoursDefault The standardHoursDefault to set.
     */
    public void setStandardHoursDefault(BigDecimal standardHoursDefault) {
        this.standardHoursDefault = standardHoursDefault;
    }

    /**
     * Gets the standardHoursFrequency
     * 
     * @return Returns the standardHoursFrequency
     */
    @Override
    public String getStandardHoursFrequency() {
        return standardHoursFrequency;
    }

    /**
     * Sets the standardHoursFrequency
     * 
     * @param standardHoursFrequency The standardHoursFrequency to set.
     */
    public void setStandardHoursFrequency(String standardHoursFrequency) {
        this.standardHoursFrequency = standardHoursFrequency;
    }

    /**
     * Gets the positionRegularTemporary
     * 
     * @return Returns the positionRegularTemporary
     */
    @Override
    public String getPositionRegularTemporary() {
        return positionRegularTemporary;
    }

    /**
     * Sets the positionRegularTemporary
     * 
     * @param positionRegularTemporary The positionRegularTemporary to set.
     */
    public void setPositionRegularTemporary(String positionRegularTemporary) {
        this.positionRegularTemporary = positionRegularTemporary;
    }

    /**
     * Gets the positionFullTimeEquivalency
     * 
     * @return Returns the positionFullTimeEquivalency
     */
    @Override
    public BigDecimal getPositionFullTimeEquivalency() {
        return positionFullTimeEquivalency;
    }

    /**
     * Sets the positionFullTimeEquivalency
     * 
     * @param positionFullTimeEquivalency The positionFullTimeEquivalency to set.
     */
    public void setPositionFullTimeEquivalency(BigDecimal positionFullTimeEquivalency) {
        this.positionFullTimeEquivalency = positionFullTimeEquivalency;
    }

    /**
     * Gets the positionSalaryPlanDefault
     * 
     * @return Returns the positionSalaryPlanDefault
     */
    @Override
    public String getPositionSalaryPlanDefault() {
        return positionSalaryPlanDefault;
    }

    /**
     * Sets the positionSalaryPlanDefault
     * 
     * @param positionSalaryPlanDefault The positionSalaryPlanDefault to set.
     */
    public void setPositionSalaryPlanDefault(String positionSalaryPlanDefault) {
        this.positionSalaryPlanDefault = positionSalaryPlanDefault;
    }

    /**
     * Gets the positionGradeDefault
     * 
     * @return Returns the positionGradeDefault
     */
    @Override
    public String getPositionGradeDefault() {
        return positionGradeDefault;
    }

    /**
     * Sets the positionGradeDefault
     * 
     * @param positionGradeDefault The positionGradeDefault to set.
     */
    public void setPositionGradeDefault(String positionGradeDefault) {
        this.positionGradeDefault = positionGradeDefault;
    }

    /**
     * construct the key list of the business object.
     * 
     * @see org.kuali.rice.krad.bo.BusinessObjectBase#toStringMapper()
     */
    protected LinkedHashMap toStringMapper_RICE20_REFACTORME() {
        LinkedHashMap m = new LinkedHashMap();
        m.put("positionNumber", this.positionNumber);
        if (this.effectiveDate != null) {
            m.put("effectiveDate", this.effectiveDate.toString());
        }

        return m;
    }

    /**
     * Gets the dummyBusinessObject
     * 
     * @return Returns the dummyBusinessObject.
     */
    @Override
    public TransientBalanceInquiryAttributes getDummyBusinessObject() {
        return dummyBusinessObject;
    }

    /**
     * Sets the dummyBusinessObject
     * 
     * @param dummyBusinessObject The dummyBusinessObject to set.
     */
    public void setDummyBusinessObject(TransientBalanceInquiryAttributes dummyBusinessObject) {
        this.dummyBusinessObject = dummyBusinessObject;
    }
}
