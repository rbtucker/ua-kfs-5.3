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

package org.kuali.kfs.sys.businessobject;

import java.util.LinkedHashMap;

import org.kuali.rice.core.api.mo.common.active.MutableInactivatable;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;


/**
 * 
 */
public class OriginationCode extends PersistableBusinessObjectBase implements MutableInactivatable {

    private String financialSystemOriginationCode;
    private String financialSystemServerName;
    private String financialSystemDatabaseName;
    private String financialSystemDatabaseDesc;
    private Long nextCapitalAssetNumber;
    private Long nextNonCapitalAssetNumber;
    private Long nextCgProposalNumber;
    private String nextContractsAndGrantsAgencyNumber;
    private String nextContractsAndGrantsSubcontractorNumber;
    private Long nextDisbVchrPayeeIdNbr;
    private Long nextFdocAttachmentIdentifier;
    private Long acctRcvbNextCustomerNumber;
    private Long financialDocumentNextItemIdentifier;
    private boolean active;

    /**
     * Default no-arg constructor.
     */
    public OriginationCode() {

    }

    /**
     * Gets the financialSystemOriginationCode attribute.
     * 
     * @return Returns the financialSystemOriginationCode
     */
    public String getFinancialSystemOriginationCode() {
        return financialSystemOriginationCode;
    }


    /**
     * Sets the financialSystemOriginationCode attribute.
     * 
     * @param financialSystemOriginationCode The financialSystemOriginationCode to set.
     */
    public void setFinancialSystemOriginationCode(String financialSystemOriginationCode) {
        this.financialSystemOriginationCode = financialSystemOriginationCode;
    }

     /**
     * Gets the financialSystemServerName attribute.
     * 
     * @return Returns the financialSystemServerName
     */
    public String getFinancialSystemServerName() {
        return financialSystemServerName;
    }


    /**
     * Sets the financialSystemServerName attribute.
     * 
     * @param financialSystemServerName The financialSystemServerName to set.
     */
    public void setFinancialSystemServerName(String financialSystemServerName) {
        this.financialSystemServerName = financialSystemServerName;
    }

    /**
     * Gets the financialSystemDatabaseName attribute.
     * 
     * @return Returns the financialSystemDatabaseName
     */
    public String getFinancialSystemDatabaseName() {
        return financialSystemDatabaseName;
    }


    /**
     * Sets the financialSystemDatabaseName attribute.
     * 
     * @param financialSystemDatabaseName The financialSystemDatabaseName to set.
     */
    public void setFinancialSystemDatabaseName(String financialSystemDatabaseName) {
        this.financialSystemDatabaseName = financialSystemDatabaseName;
    }

    /**
     * Gets the financialSystemDatabaseDesc attribute.
     * 
     * @return Returns the financialSystemDatabaseDesc
     */
    public String getFinancialSystemDatabaseDesc() {
        return financialSystemDatabaseDesc;
    }


    /**
     * Sets the financialSystemDatabaseDesc attribute.
     * 
     * @param financialSystemDatabaseDesc The financialSystemDatabaseDesc to set.
     */
    public void setFinancialSystemDatabaseDesc(String financialSystemDatabaseDesc) {
        this.financialSystemDatabaseDesc = financialSystemDatabaseDesc;
    }

    /**
     * Gets the nextCapitalAssetNumber attribute.
     * 
     * @return Returns the nextCapitalAssetNumber
     */
    public Long getNextCapitalAssetNumber() {
        return nextCapitalAssetNumber;
    }


    /**
     * Sets the nextCapitalAssetNumber attribute.
     * 
     * @param nextCapitalAssetNumber The nextCapitalAssetNumber to set.
     */
    public void setNextCapitalAssetNumber(Long nextCapitalAssetNumber) {
        this.nextCapitalAssetNumber = nextCapitalAssetNumber;
    }

    /**
     * Gets the nextNonCapitalAssetNumber attribute.
     * 
     * @return Returns the nextNonCapitalAssetNumber
     */
    public Long getNextNonCapitalAssetNumber() {
        return nextNonCapitalAssetNumber;
    }


    /**
     * Sets the nextNonCapitalAssetNumber attribute.
     * 
     * @param nextNonCapitalAssetNumber The nextNonCapitalAssetNumber to set.
     */
    public void setNextNonCapitalAssetNumber(Long nextNonCapitalAssetNumber) {
        this.nextNonCapitalAssetNumber = nextNonCapitalAssetNumber;
    }

    /**
     * Gets the nextCgProposalNumber attribute.
     * 
     * @return Returns the nextCgProposalNumber
     */
    public Long getNextCgProposalNumber() {
        return nextCgProposalNumber;
    }


    /**
     * Sets the nextCgProposalNumber attribute.
     * 
     * @param nextCgProposalNumber The nextCgProposalNumber to set.
     */
    public void setNextCgProposalNumber(Long nextCgProposalNumber) {
        this.nextCgProposalNumber = nextCgProposalNumber;
    }

    /**
     * Gets the nextContractsAndGrantsAgencyNumber attribute.
     * 
     * @return Returns the nextContractsAndGrantsAgencyNumber
     */
    public String getNextContractsAndGrantsAgencyNumber() {
        return nextContractsAndGrantsAgencyNumber;
    }


    /**
     * Sets the nextContractsAndGrantsAgencyNumber attribute.
     * 
     * @param nextContractsAndGrantsAgencyNumber The nextContractsAndGrantsAgencyNumber to set.
     */
    public void setNextContractsAndGrantsAgencyNumber(String nextContractsAndGrantsAgencyNumber) {
        this.nextContractsAndGrantsAgencyNumber = nextContractsAndGrantsAgencyNumber;
    }

    /**
     * Gets the nextContractsAndGrantsSubcontractorNumber attribute.
     * 
     * @return Returns the nextContractsAndGrantsSubcontractorNumber
     */
    public String getNextContractsAndGrantsSubcontractorNumber() {
        return nextContractsAndGrantsSubcontractorNumber;
    }


    /**
     * Sets the nextContractsAndGrantsSubcontractorNumber attribute.
     * 
     * @param nextContractsAndGrantsSubcontractorNumber The nextContractsAndGrantsSubcontractorNumber to set.
     */
    public void setNextContractsAndGrantsSubcontractorNumber(String nextContractsAndGrantsSubcontractorNumber) {
        this.nextContractsAndGrantsSubcontractorNumber = nextContractsAndGrantsSubcontractorNumber;
    }

    /**
     * Gets the nextDisbVchrPayeeIdNbr attribute.
     * 
     * @return Returns the nextDisbVchrPayeeIdNbr
     */
    public Long getNextDisbVchrPayeeIdNbr() {
        return nextDisbVchrPayeeIdNbr;
    }


    /**
     * Sets the nextDisbVchrPayeeIdNbr attribute.
     * 
     * @param nextDisbVchrPayeeIdNbr The nextDisbVchrPayeeIdNbr to set.
     */
    public void setNextDisbVchrPayeeIdNbr(Long nextDisbVchrPayeeIdNbr) {
        this.nextDisbVchrPayeeIdNbr = nextDisbVchrPayeeIdNbr;
    }

    /**
     * Gets the nextFdocAttachmentIdentifier attribute.
     * 
     * @return Returns the nextFdocAttachmentIdentifier
     */
    public Long getNextFdocAttachmentIdentifier() {
        return nextFdocAttachmentIdentifier;
    }


    /**
     * Sets the nextFdocAttachmentIdentifier attribute.
     * 
     * @param nextFdocAttachmentIdentifier The nextFdocAttachmentIdentifier to set.
     */
    public void setNextFdocAttachmentIdentifier(Long nextFdocAttachmentIdentifier) {
        this.nextFdocAttachmentIdentifier = nextFdocAttachmentIdentifier;
    }

    /**
     * Gets the acctRcvbNextCustomerNumber attribute.
     * 
     * @return Returns the acctRcvbNextCustomerNumber
     */
    public Long getAcctRcvbNextCustomerNumber() {
        return acctRcvbNextCustomerNumber;
    }


    /**
     * Sets the acctRcvbNextCustomerNumber attribute.
     * 
     * @param acctRcvbNextCustomerNumber The acctRcvbNextCustomerNumber to set.
     */
    public void setAcctRcvbNextCustomerNumber(Long acctRcvbNextCustomerNumber) {
        this.acctRcvbNextCustomerNumber = acctRcvbNextCustomerNumber;
    }

    /**
     * Gets the financialDocumentNextItemIdentifier attribute.
     * 
     * @return Returns the financialDocumentNextItemIdentifier
     */
    public Long getFinancialDocumentNextItemIdentifier() {
        return financialDocumentNextItemIdentifier;
    }


    /**
     * Sets the financialDocumentNextItemIdentifier attribute.
     * 
     * @param financialDocumentNextItemIdentifier The financialDocumentNextItemIdentifier to set.
     */
    public void setFinancialDocumentNextItemIdentifier(Long financialDocumentNextItemIdentifier) {
        this.financialDocumentNextItemIdentifier = financialDocumentNextItemIdentifier;
    }

    /**
     * @see org.kuali.rice.krad.bo.BusinessObjectBase#toStringMapper()
     */
    protected LinkedHashMap toStringMapper_RICE20_REFACTORME() {
        LinkedHashMap m = new LinkedHashMap();
        m.put("financialSystemOriginationCode", this.financialSystemOriginationCode);
        return m;
    }

    /**
     * Gets the active attribute. 
     * @return Returns the active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active attribute value.
     * @param active The active to set.
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
