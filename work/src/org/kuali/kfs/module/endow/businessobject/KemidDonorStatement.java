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

import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.kfs.module.endow.EndowPropertyConstants;
import org.kuali.rice.core.api.util.type.KualiInteger;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * This KemidDonorStatement class provides the Donor to receive annual statements on the KEMID.
 */
public class KemidDonorStatement extends PersistableBusinessObjectBase {

    private String kemid;
    private String donorId;
    private KualiInteger donorSeq;
    private String donorStatementCode;
    private String combineWithDonorId;
    private Date effectiveDate;
    private Date terminationDate;
    private String terminationReason;
    private String donorLabel;

    private KEMID kemidObjRef;
    private Donor donor;
    private Donor combineWithDonor;
    private DonorStatementCode donorStatement;
    private DonorLabel donorLabelObjRef;


    /**
     * @see org.kuali.rice.krad.bo.BusinessObjectBase#toStringMapper()
     */
    
    protected LinkedHashMap toStringMapper_RICE20_REFACTORME() {
        LinkedHashMap<String, String> m = new LinkedHashMap<String, String>();
        m.put(EndowPropertyConstants.KEMID, this.kemid);
        m.put(EndowPropertyConstants.KEMID_DONOR_STATEMENT_SEQ, String.valueOf(donorSeq));
        m.put(EndowPropertyConstants.KEMID_DONOR_STATEMENT_ID, donorId);
        return m;
    }

    /**
     * Gets the combineWithDonorId.
     * 
     * @return combineWithDonorId
     */
    public String getCombineWithDonorId() {
        return combineWithDonorId;
    }

    /**
     * Sets the combineWithDonorId.
     * 
     * @param combineWithDonorId
     */
    public void setCombineWithDonorId(String combineWithDonorId) {
        this.combineWithDonorId = combineWithDonorId;
    }

    /**
     * Gets the donorId.
     * 
     * @return donorId
     */
    public String getDonorId() {
        return donorId;
    }

    /**
     * Sets the donorId.
     * 
     * @param donorId
     */
    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    /**
     * Gets the donorSeq.
     * 
     * @return donorSeq
     */
    public KualiInteger getDonorSeq() {
        return donorSeq;
    }

    /**
     * Sets the donorSeq.
     * 
     * @param donorSeq
     */
    public void setDonorSeq(KualiInteger donorSeq) {
        this.donorSeq = donorSeq;
    }

    /**
     * Gets the donorStatementCode.
     * 
     * @return donorStatementCode
     */
    public String getDonorStatementCode() {
        return donorStatementCode;
    }

    /**
     * Sets the donorStatementCode.
     * 
     * @param donorStatementCode
     */
    public void setDonorStatementCode(String donorStatementCode) {
        this.donorStatementCode = donorStatementCode;
    }

    /**
     * Gets the effectiveDate.
     * 
     * @return effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the effectiveDate.
     * 
     * @param effectiveDate
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Gets the kemid.
     * 
     * @return kemid
     */
    public String getKemid() {
        return kemid;
    }

    /**
     * Sets the kemid.
     * 
     * @param kemid
     */
    public void setKemid(String kemid) {
        this.kemid = kemid;
    }

    /**
     * Gets the terminationDate.
     * 
     * @return terminationDate
     */
    public Date getTerminationDate() {
        return terminationDate;
    }

    /**
     * Sets the terminationDate.
     * 
     * @param terminationDate
     */
    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    /**
     * Gets the terminationReason.
     * 
     * @return terminationReason
     */
    public String getTerminationReason() {
        return terminationReason;
    }

    /**
     * Sets the terminationReason.
     * 
     * @param terminationReason
     */
    public void setTerminationReason(String terminationReason) {
        this.terminationReason = terminationReason;
    }

    /**
     * Gets the donor.
     * 
     * @return donor
     */
    public Donor getDonor() {
        return donor;
    }

    /**
     * Sets the donor.
     * 
     * @param donor
     */
    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    /**
     * Gets the donorStatement.
     * 
     * @return donorStatement
     */
    public DonorStatementCode getDonorStatement() {
        return donorStatement;
    }

    /**
     * Sets the donorStatement.
     * 
     * @param donorStatement
     */
    public void setDonorStatement(DonorStatementCode donorStatement) {
        this.donorStatement = donorStatement;
    }

    /**
     * Gets the kemidObjRef.
     * 
     * @return kemidObjRef
     */
    public KEMID getKemidObjRef() {
        return kemidObjRef;
    }

    /**
     * Sets the kemidObjRef.
     * 
     * @param kemidObjRef
     */
    public void setKemidObjRef(KEMID kemidObjRef) {
        this.kemidObjRef = kemidObjRef;
    }

    /**
     * Gets the combineWithDonor.
     * 
     * @return combineWithDonor
     */
    public Donor getCombineWithDonor() {
        return combineWithDonor;
    }

    /**
     * Sets the combineWithDonor.
     * 
     * @param combineWithDonor
     */
    public void setCombineWithDonor(Donor combineWithDonor) {
        this.combineWithDonor = combineWithDonor;
    }

    /**
     * Gets the donorLabel.
     * 
     * @return donorLabel
     */
    public String getDonorLabel() {
        return donorLabel;
    }

    /**
     * Sets the donorLabel.
     * 
     * @param donorLabel
     */
    public void setDonorLabel(String donorLabel) {
        this.donorLabel = donorLabel;
    }

    /**
     * Gets the donorLabelObjRef.
     * 
     * @return donorLabelObjRef
     */
    public DonorLabel getDonorLabelObjRef() {
        return donorLabelObjRef;
    }

    /**
     * Sets the donorLabelObjRef.
     * 
     * @param donorLabelObjRef
     */
    public void setDonorLabelObjRef(DonorLabel donorLabelObjRef) {
        this.donorLabelObjRef = donorLabelObjRef;
    }
}
