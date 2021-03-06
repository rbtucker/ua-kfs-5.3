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
package org.kuali.kfs.module.ar.businessobject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.kfs.coa.businessobject.Account;
import org.kuali.kfs.coa.businessobject.Chart;
import org.kuali.kfs.coa.businessobject.ObjectCode;
import org.kuali.kfs.coa.businessobject.ProjectCode;
import org.kuali.kfs.coa.businessobject.SubAccount;
import org.kuali.kfs.coa.businessobject.SubObjectCode;
import org.kuali.kfs.coa.service.AccountService;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krad.util.ObjectUtils;

/**
 * @author Kuali Nervous System Team (kualidev@oncourse.iu.edu)
 */
public class NonInvoiced extends PersistableBusinessObjectBase {

	private String documentNumber;
	private Integer financialDocumentLineNumber;
	private Integer financialDocumentPostingYear;
	private String chartOfAccountsCode;
	private String accountNumber;
	private String subAccountNumber;
	private String financialObjectCode;
	private String financialSubObjectCode;
	private String projectCode;
	private KualiDecimal financialDocumentLineAmount;
	private String financialDocumentOverrideCode;
    private KualiDecimal nonInvoicedDistributionAmount = KualiDecimal.ZERO;

    private AccountsReceivableDocumentHeader accountsReceivableDocumentHeader;
    private ObjectCode financialObject;
	private SubAccount subAccount;
	private Chart chartOfAccounts;
	private SubObjectCode financialSubObject;
	private Account account;
	private ProjectCode project;


    List<NonInvoicedDistribution> nonInvoicedDistributions;

	/**
	 * Default constructor.
	 */
	public NonInvoiced() {
        nonInvoicedDistributions = new ArrayList<NonInvoicedDistribution>();

	}

	/**
	 * Gets the documentNumber attribute.
	 *
	 * @return Returns the documentNumber
	 *
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * Sets the documentNumber attribute.
	 *
	 * @param documentNumber The documentNumber to set.
	 *
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}


	/**
	 * Gets the financialDocumentLineNumber attribute.
	 *
	 * @return Returns the financialDocumentLineNumber
	 *
	 */
	public Integer getFinancialDocumentLineNumber() {
		return financialDocumentLineNumber;
	}

	/**
	 * Sets the financialDocumentLineNumber attribute.
	 *
	 * @param financialDocumentLineNumber The financialDocumentLineNumber to set.
	 *
	 */
	public void setFinancialDocumentLineNumber(Integer financialDocumentLineNumber) {
		this.financialDocumentLineNumber = financialDocumentLineNumber;
	}


	/**
	 * Gets the financialDocumentPostingYear attribute.
	 *
	 * @return Returns the financialDocumentPostingYear
	 *
	 */
	public Integer getFinancialDocumentPostingYear() {
		return financialDocumentPostingYear;
	}

	/**
	 * Sets the financialDocumentPostingYear attribute.
	 *
	 * @param financialDocumentPostingYear The financialDocumentPostingYear to set.
	 *
	 */
	public void setFinancialDocumentPostingYear(Integer financialDocumentPostingYear) {
		this.financialDocumentPostingYear = financialDocumentPostingYear;
	}


	/**
	 * Gets the chartOfAccountsCode attribute.
	 *
	 * @return Returns the chartOfAccountsCode
	 *
	 */
	public String getChartOfAccountsCode() {
		return chartOfAccountsCode;
	}

	/**
	 * Sets the chartOfAccountsCode attribute.
	 *
	 * @param chartOfAccountsCode The chartOfAccountsCode to set.
	 *
	 */
	public void setChartOfAccountsCode(String chartOfAccountsCode) {
		this.chartOfAccountsCode = chartOfAccountsCode;
	}


	/**
	 * Gets the accountNumber attribute.
	 *
	 * @return Returns the accountNumber
	 *
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Sets the accountNumber attribute.
	 *
	 * @param accountNumber The accountNumber to set.
	 *
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;

        // if accounts can't cross charts, set chart code whenever account number is set
        AccountService accountService = SpringContext.getBean(AccountService.class);
        if (!accountService.accountsCanCrossCharts()) {
            Account account = accountService.getUniqueAccountForAccountNumber(accountNumber);
            if (ObjectUtils.isNotNull(account)) {
                setChartOfAccountsCode(account.getChartOfAccountsCode());
            }
        }
	}

	/**
	 * Gets the subAccountNumber attribute.
	 *
	 * @return Returns the subAccountNumber
	 *
	 */
	public String getSubAccountNumber() {
		return subAccountNumber;
	}

	/**
	 * Sets the subAccountNumber attribute.
	 *
	 * @param subAccountNumber The subAccountNumber to set.
	 *
	 */
	public void setSubAccountNumber(String subAccountNumber) {
		this.subAccountNumber = subAccountNumber;
	}


	/**
	 * Gets the financialObjectCode attribute.
	 *
	 * @return Returns the financialObjectCode
	 *
	 */
	public String getFinancialObjectCode() {
		return financialObjectCode;
	}

	/**
	 * Sets the financialObjectCode attribute.
	 *
	 * @param financialObjectCode The financialObjectCode to set.
	 *
	 */
	public void setFinancialObjectCode(String financialObjectCode) {
		this.financialObjectCode = financialObjectCode;
	}


	/**
	 * Gets the financialSubObjectCode attribute.
	 *
	 * @return Returns the financialSubObjectCode
	 *
	 */
	public String getFinancialSubObjectCode() {
		return financialSubObjectCode;
	}

	/**
	 * Sets the financialSubObjectCode attribute.
	 *
	 * @param financialSubObjectCode The financialSubObjectCode to set.
	 *
	 */
	public void setFinancialSubObjectCode(String financialSubObjectCode) {
		this.financialSubObjectCode = financialSubObjectCode;
	}


	/**
	 * Gets the projectCode attribute.
	 *
	 * @return Returns the projectCode
	 *
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * Sets the projectCode attribute.
	 *
	 * @param projectCode The projectCode to set.
	 *
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}


	/**
	 * Gets the financialDocumentLineAmount attribute.
	 *
	 * @return Returns the financialDocumentLineAmount
	 *
	 */
	public KualiDecimal getFinancialDocumentLineAmount() {
		return financialDocumentLineAmount;
	}

	/**
	 * Sets the financialDocumentLineAmount attribute.
	 *
	 * @param financialDocumentLineAmount The financialDocumentLineAmount to set.
	 *
	 */
	public void setFinancialDocumentLineAmount(KualiDecimal financialDocumentLineAmount) {
		this.financialDocumentLineAmount = financialDocumentLineAmount;
	}


	/**
	 * Gets the financialDocumentOverrideCode attribute.
	 *
	 * @return Returns the financialDocumentOverrideCode
	 *
	 */
	public String getFinancialDocumentOverrideCode() {
		return financialDocumentOverrideCode;
	}

	/**
	 * Sets the financialDocumentOverrideCode attribute.
	 *
	 * @param financialDocumentOverrideCode The financialDocumentOverrideCode to set.
	 *
	 */
	public void setFinancialDocumentOverrideCode(String financialDocumentOverrideCode) {
		this.financialDocumentOverrideCode = financialDocumentOverrideCode;
	}

    /**
     * Gets the accountsReceivableDocumentHeader attribute.
     * @return Returns the accountsReceivableDocumentHeader.
     */
    public AccountsReceivableDocumentHeader getAccountsReceivableDocumentHeader() {
        return accountsReceivableDocumentHeader;
    }

    /**
     * Sets the accountsReceivableDocumentHeader attribute value.
     * @param accountsReceivableDocumentHeader The accountsReceivableDocumentHeader to set.
     * @deprecated
     */
    @Deprecated
    public void setAccountsReceivableDocumentHeader(AccountsReceivableDocumentHeader accountsReceivableDocumentHeader) {
        this.accountsReceivableDocumentHeader = accountsReceivableDocumentHeader;
    }

	/**
	 * Gets the financialObject attribute.
	 *
	 * @return Returns the financialObject
	 *
	 */
	public ObjectCode getFinancialObject() {
		return financialObject;
	}

	/**
	 * Sets the financialObject attribute.
	 *
	 * @param financialObject The financialObject to set.
	 * @deprecated
	 */
	@Deprecated
    public void setFinancialObject(ObjectCode financialObject) {
		this.financialObject = financialObject;
	}

	/**
	 * Gets the subAccount attribute.
	 *
	 * @return Returns the subAccount
	 *
	 */
	public SubAccount getSubAccount() {
		return subAccount;
	}

	/**
	 * Sets the subAccount attribute.
	 *
	 * @param subAccount The subAccount to set.
	 * @deprecated
	 */
	@Deprecated
    public void setSubAccount(SubAccount subAccount) {
		this.subAccount = subAccount;
	}

	/**
	 * Gets the chartOfAccounts attribute.
	 *
	 * @return Returns the chartOfAccounts
	 *
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
	@Deprecated
    public void setChartOfAccounts(Chart chartOfAccounts) {
		this.chartOfAccounts = chartOfAccounts;
	}

	/**
	 * Gets the financialSubObject attribute.
	 *
	 * @return Returns the financialSubObject
	 *
	 */
	public SubObjectCode getFinancialSubObject() {
		return financialSubObject;
	}

	/**
	 * Sets the financialSubObject attribute.
	 *
	 * @param financialSubObject The financialSubObject to set.
	 * @deprecated
	 */
	@Deprecated
    public void setFinancialSubObject(SubObjectCode financialSubObject) {
		this.financialSubObject = financialSubObject;
	}

	/**
	 * Gets the account attribute.
	 *
	 * @return Returns the account
	 *
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * Sets the account attribute.
	 *
	 * @param account The account to set.
	 * @deprecated
	 */
	@Deprecated
    public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * Gets the project attribute.
	 *
	 * @return Returns the project
	 *
	 */
	public ProjectCode getProject() {
		return project;
	}

	/**
	 * Sets the project attribute.
	 *
	 * @param project The project to set.
	 * @deprecated
	 */
	@Deprecated
    public void setProject(ProjectCode project) {
		this.project = project;
	}

	/**
     * Gets the nonInvoicedDistributions attribute.
     * @return Returns the nonInvoicedDistributions.
     */
    public List<NonInvoicedDistribution> getNonInvoicedDistributions() {
        return nonInvoicedDistributions;
    }

    /**
     * Sets the nonInvoicedDistributions attribute value.
     * @param nonInvoicedDistributions The nonInvoicedDistributions to set.
     */
    public void setNonInvoicedDistributions(List<NonInvoicedDistribution> nonInvoicedDistributions) {
        this.nonInvoicedDistributions = nonInvoicedDistributions;
    }

    /**
	 * @see org.kuali.rice.krad.bo.BusinessObjectBase#toStringMapper()
	 */
	protected LinkedHashMap toStringMapper_RICE20_REFACTORME() {
	    LinkedHashMap m = new LinkedHashMap();
        m.put("documentNumber", this.documentNumber);
        if (this.financialDocumentLineNumber != null) {
            m.put("financialDocumentLineNumber", this.financialDocumentLineNumber.toString());
        }
	    return m;
    }

    /**
     * Gets the nonInvoicedDistributionAmount attribute.
     *
     * @return Returns the nonInvoicedDistributionAmount
     *
     */
    public KualiDecimal getNonInvoicedDistributionAmount() {
        return nonInvoicedDistributionAmount;
    }

    /**
     * Sets the remainingAmountForDistribution attribute.
     *
     * @param remainingAmountForDistribution The remainingAmountForDistribution to set.
     *
     */
    public void setNonInvoicedDistributionAmount(KualiDecimal nonInvoicedDistributionAmount) {
        this.nonInvoicedDistributionAmount = nonInvoicedDistributionAmount;
    }

}
