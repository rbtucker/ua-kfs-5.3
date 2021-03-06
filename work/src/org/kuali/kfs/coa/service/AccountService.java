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
package org.kuali.kfs.coa.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.kuali.kfs.coa.businessobject.Account;
import org.kuali.kfs.coa.businessobject.AccountDelegate;
import org.kuali.kfs.sys.businessobject.AccountingLine;
import org.kuali.rice.kim.api.identity.Person;


/**
 * This interface defines methods that an Account Service must provide
 */
public interface AccountService {
    /**
     * Retrieves an Account object based on primary key.
     *
     * @param chartOfAccountsCode - Chart of Accounts Code
     * @param accountNumber - Account Number
     * @return Account
     */
    public Account getByPrimaryId(String chartOfAccountsCode, String accountNumber);

    /**
     * Method is used by KualiAccountAttribute to enable caching of orgs for routing.
     *
     * @see org.kuali.kfs.coa.service.AccountService#getByPrimaryId(java.lang.String, java.lang.String)
     */
    public Account getByPrimaryIdWithCaching(String chartOfAccountsCode, String accountNumber);

    /**
     * This method retrieves the fiscal officers primary delegate based on the chart, account, and document type specified on the
     * example, along with the total dollar amount
     *
     * @param delegateExample The object that contains the chart, account, and document type that should be used to query the
     *        account delegate table
     * @param totalDollarAmount The amount that should be compared to the from and to amount on the account delegate table
     * @return The primary delegate for this account, document type, and amount
     */
    public AccountDelegate getPrimaryDelegationByExample(AccountDelegate delegateExample, String totalDollarAmount);

    /**
     * This method retrieves the fiscal officers secondary delegates based on the chart, account, and document type specified on the
     * example, along with the total dollar amount
     *
     * @param delegateExample The object that contains the chart, account, and document type that should be used to query the
     *        account delegate table
     * @param totalDollarAmount The amount that should be compared to the from and to amount on the account delegate table
     * @return The primary delegate for this account, document type, and amount
     */
    public List getSecondaryDelegationsByExample(AccountDelegate delegateExample, String totalDollarAmount);

    /**
     * Fetches the accounts that the user is either the fiscal officer or fiscal officer delegate for.
     *
     * @param kualiUser
     * @return a list of Accounts that the user has responsibility for
     */
    public List getAccountsThatUserIsResponsibleFor(Person kualiUser);

    /**
     * Does the given user have responsibilities on the given account?
     *
     * @param kualiUser the universal user to check responsibilities for
     * @param account the account to check responsibilities on
     * @return true if user does have responsibilities, false if otherwise
     */
    public boolean hasResponsibilityOnAccount(Person kualiUser, Account account);

    /**
     * get all accounts in the system. This is needed by a sufficient funds rebuilder job
     *
     * @return iterator of all accounts
     */
    public Iterator getAllAccounts();

    /**
     * Retrieves all active accounts from the database where the given principal is the fiscal officer
     *
     * @param principalId the principal id of the fiscal officer
     * @return an Iterator of active Accounts
     */
    public abstract Iterator<Account> getActiveAccountsForFiscalOfficer(String principalId);

    /**
     * Retrieves all expired accounts from the database where the given principal is the fiscal officer
     *
     * @param principalId the principal id of the fiscal officer
     * @return an Iterator of expired Accounts
     */
    public abstract Iterator<Account> getExpiredAccountsForFiscalOfficer(String principalId);

    /**
     * Retrieves all active accounts from the database where the given principal is the account supervisor
     *
     * @param principalId the principal id of the account supervisor
     * @return an Iterator of active Accounts
     */
    public abstract Iterator<Account> getActiveAccountsForAccountSupervisor(String principalId);

    /**
     * Retrieves all active accounts from the database where the given principal is the account supervisor
     *
     * @param principalId the principal id of the account supervisor
     * @return an Iterator of expired Accounts
     */
    public abstract Iterator<Account> getExpiredAccountsForAccountSupervisor(String principalId);

    /**
     * Determines if the given principal is the fiscal officer of any non-closed account
     *
     * @param principalId the principal to check for the fiscal officer role
     * @return true if the principal is a fiscal officer for any non-closed account, false otherwise
     */
    public abstract boolean isPrincipalInAnyWayShapeOrFormFiscalOfficer(String principalId);

    /**
     * Determines if the given principal is the account supervisor of any non-closed account
     *
     * @param principalId the principal to check for the account supervisor role
     * @return true if the principal is a account supervisor for any non-closed account, false otherwise
     */
    public abstract boolean isPrincipalInAnyWayShapeOrFormAccountSupervisor(String principalId);

    /**
     * Determines if the given principal is the account manager of any non-closed account
     *
     * @param principalId the principal to check for the account manager role
     * @return true if the principal is a account manager for any non-closed account, false otherwise
     */
    public abstract boolean isPrincipalInAnyWayShapeOrFormAccountManager(String principalId);

    /**
     * Returns the accounts associated with a given account number
     *
     * @param accountNumber the account number
     * @return a list of accounts associated with that account number
     */
    public abstract Collection<Account> getAccountsForAccountNumber(String accountNumber);

    /**
     * Retrieves the default labor benefit rate category code based on account type code.
     *
     * @param accountTypeCode - The Account Type Code to find the default value for
     * @return String - the default labor benefit rate category code for given account type code
     */
    public String getDefaultLaborBenefitRateCategoryCodeForAccountType(String accountTypeCode);

    /**
     * Retrieve the from the parameter if ENABLE_FRINGE_BENEFIT_CALC_BY_BENEFIT_RATE_CATEGORY_IND
     * is enabled
     *
     * Default to Boolean.FALSE if it cannot be evaluated or parameter is not defined
     *
     * @return
     */
    public Boolean isFridgeBenefitCalculationEnable();

    /**
     * Returns the unique account associated with a given account number. This method only applies when parameter
     * ACCOUNTS_CAN_CROSS_CHARTS_IND is set to "N".
     *
     * @param accountNumber the account number
     * @return the unique account associated with that account number
     */
    public Account getUniqueAccountForAccountNumber(String accountNumber);

    /**
     * Returns true if parameter ACCOUNTS_CAN_CROSS_CHARTS_IND is set to "Y"; otherwise false.
     */
    public boolean accountsCanCrossCharts();

    /*
     * Populate chart code if it's null, according to the account number in the specified accounting line. This only happens when
     * parameter ACCOUNTS_CAN_CROSS_CHARTS_IND is set to "N", and Javascript is turned off.
     * @param the new source accounting line
     */
    public void populateAccountingLineChartIfNeeded(AccountingLine line);

    /**
     * @param account an expired account. If an expired account is passed, it will not be returned, only
     *    its first continuation that is not expired will be returned or, of course, null.
     * @return an unexpired continuation account for the given account, or, if one cannot be found, null
     */
    public Account getUnexpiredContinuationAccountOrNull(Account account) ;
}
