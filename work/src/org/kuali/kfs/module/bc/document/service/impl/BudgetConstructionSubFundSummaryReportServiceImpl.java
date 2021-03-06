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
package org.kuali.kfs.module.bc.document.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.kfs.module.bc.BCConstants;
import org.kuali.kfs.module.bc.BCKeyConstants;
import org.kuali.kfs.module.bc.businessobject.BudgetConstructionAccountSummary;
import org.kuali.kfs.module.bc.businessobject.BudgetConstructionOrgSubFundSummaryReport;
import org.kuali.kfs.module.bc.businessobject.BudgetConstructionOrgSubFundSummaryReportTotal;
import org.kuali.kfs.module.bc.document.dataaccess.BudgetConstructionAccountSummaryReportDao;
import org.kuali.kfs.module.bc.document.service.BudgetConstructionReportsServiceHelper;
import org.kuali.kfs.module.bc.document.service.BudgetConstructionSubFundSummaryReportService;
import org.kuali.kfs.module.bc.report.BudgetConstructionReportHelper;
import org.kuali.kfs.module.bc.util.BudgetConstructionUtils;
import org.kuali.kfs.sys.KFSPropertyConstants;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.util.type.KualiInteger;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation of BudgetConstructionAccountSummaryReportService.
 */
@Transactional
public class BudgetConstructionSubFundSummaryReportServiceImpl implements BudgetConstructionSubFundSummaryReportService {

    protected BudgetConstructionAccountSummaryReportDao budgetConstructionAccountSummaryReportDao;
    protected ConfigurationService kualiConfigurationService;
    protected BudgetConstructionReportsServiceHelper budgetConstructionReportsServiceHelper;
    protected boolean trExist = false;

    /**
     * @see org.kuali.kfs.module.bc.document.service.BudgetReportsControlListService#updateSubFundSummaryReport(java.lang.String)
     */
    public void updateSubFundSummaryReport(String principalName) {
        String expenditureINList = BudgetConstructionUtils.getExpenditureINList();
        String revenueINList = BudgetConstructionUtils.getRevenueINList();
        budgetConstructionAccountSummaryReportDao.cleanReportsAccountSummaryTable(principalName);
        budgetConstructionAccountSummaryReportDao.updateSubFundSummaryReport(principalName, revenueINList, expenditureINList);
    }

    /**
     * @see org.kuali.kfs.module.bc.document.service.BudgetConstructionSubFundSummaryReportService#buildReports(java.lang.Integer,
     *      java.util.Collection)
     */
    public Collection<BudgetConstructionOrgSubFundSummaryReport> buildReports(Integer universityFiscalYear, String principalName) {
        Collection<BudgetConstructionOrgSubFundSummaryReport> reportSet = new ArrayList();

        // build order list
        Collection<BudgetConstructionAccountSummary> subFundSummaryList = budgetConstructionReportsServiceHelper.getDataForBuildingReports(BudgetConstructionAccountSummary.class, principalName, buildOrderByList());

        // Making a list with same organizationChartOfAccountsCode, organizationCode, chartOfAccountsCode, subFundGroupCode
        List subTotalList = BudgetConstructionReportHelper.deleteDuplicated((List) subFundSummaryList, fieldsForSubTotal());
        List totalList = BudgetConstructionReportHelper.deleteDuplicated((List) subFundSummaryList, fieldsForTotal());

        // Calculate Total Section
        List<BudgetConstructionOrgSubFundSummaryReportTotal> orgSubFundSummaryReportSubTotalList = calculateSubTotal((List) subFundSummaryList, subTotalList);
        List<BudgetConstructionOrgSubFundSummaryReportTotal> orgSubFundSummaryReportTotalList = calculateTotal((List) subFundSummaryList, totalList);

        for (BudgetConstructionAccountSummary subFundSummaryEntry : subFundSummaryList) {
            BudgetConstructionOrgSubFundSummaryReport orgSubFundSummaryReportEntry = new BudgetConstructionOrgSubFundSummaryReport();
            buildReportsHeader(universityFiscalYear, orgSubFundSummaryReportEntry, subFundSummaryEntry);
            buildReportsBody(orgSubFundSummaryReportEntry, subFundSummaryEntry);
            buildReportsTotal(orgSubFundSummaryReportEntry, subFundSummaryEntry, orgSubFundSummaryReportSubTotalList, orgSubFundSummaryReportTotalList);
            reportSet.add(orgSubFundSummaryReportEntry);
        }

        return reportSet;
    }

    /**
     * builds report Header
     *
     * @param universityFiscalYear
     * @param orgSubFundSummaryReportEntry
     * @param subFundSummaryList
     */
    public void buildReportsHeader(Integer universityFiscalYear, BudgetConstructionOrgSubFundSummaryReport orgSubFundSummaryReportEntry, BudgetConstructionAccountSummary subFundSummaryList) {
        String orgChartDesc = subFundSummaryList.getOrganizationChartOfAccounts().getFinChartOfAccountDescription();
        String chartDesc = subFundSummaryList.getChartOfAccounts().getFinChartOfAccountDescription();
        String orgName = subFundSummaryList.getOrganization().getOrganizationName();
        String reportChartDesc = subFundSummaryList.getChartOfAccounts().getReportsToChartOfAccounts().getFinChartOfAccountDescription();
        String subFundGroupName = subFundSummaryList.getFundGroup().getName();
        String subFundGroupDes = subFundSummaryList.getSubFundGroup().getSubFundGroupDescription();
        Integer prevFiscalyear = universityFiscalYear - 1;
        orgSubFundSummaryReportEntry.setFiscalYear(prevFiscalyear.toString() + "-" + universityFiscalYear.toString().substring(2, 4));
        orgSubFundSummaryReportEntry.setOrgChartOfAccountsCode(subFundSummaryList.getOrganizationChartOfAccountsCode());
        if (orgChartDesc == null) {
            orgSubFundSummaryReportEntry.setOrgChartOfAccountDescription(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.ERROR_REPORT_GETTING_CHART_DESCRIPTION));
        }
        else {
            orgSubFundSummaryReportEntry.setOrgChartOfAccountDescription(orgChartDesc);
        }
        orgSubFundSummaryReportEntry.setOrganizationCode(subFundSummaryList.getOrganizationCode());
        if (orgName == null) {
            orgSubFundSummaryReportEntry.setOrganizationName(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.ERROR_REPORT_GETTING_ORGANIZATION_NAME));
        }
        else {
            orgSubFundSummaryReportEntry.setOrganizationName(orgName);
        }
        orgSubFundSummaryReportEntry.setChartOfAccountsCode(subFundSummaryList.getChartOfAccountsCode());
        if (chartDesc == null) {
            orgSubFundSummaryReportEntry.setChartOfAccountDescription(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.ERROR_REPORT_GETTING_CHART_DESCRIPTION));
        }
        else {
            orgSubFundSummaryReportEntry.setChartOfAccountDescription(chartDesc);
        }
        orgSubFundSummaryReportEntry.setFundGroupCode(subFundSummaryList.getFundGroupCode());
        if (subFundGroupName == null) {
            orgSubFundSummaryReportEntry.setFundGroupName(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.ERROR_REPORT_GETTING_FUNDGROUP_NAME));
        }
        else {
            orgSubFundSummaryReportEntry.setFundGroupName(subFundGroupName);
        }
        orgSubFundSummaryReportEntry.setSubFundGroupCode(subFundSummaryList.getSubFundGroupCode());
        if (subFundGroupName == null) {
            orgSubFundSummaryReportEntry.setSubFundGroupDescription(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.ERROR_REPORT_GETTING_SUBFUNDGROUP_DESCRIPTION));
        }
        else {
            orgSubFundSummaryReportEntry.setSubFundGroupDescription(subFundGroupDes);
        }
        Integer prevPrevFiscalyear = prevFiscalyear - 1;
        orgSubFundSummaryReportEntry.setBaseFy(prevPrevFiscalyear.toString() + "-" + prevFiscalyear.toString().substring(2, 4));
        orgSubFundSummaryReportEntry.setReqFy(prevFiscalyear.toString() + "-" + universityFiscalYear.toString().substring(2, 4));
        orgSubFundSummaryReportEntry.setHeader1(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.MSG_REPORT_HEADER_SUBFUND));
        orgSubFundSummaryReportEntry.setHeader2(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.MSG_REPORT_HEADER_SUBFUND_DESCRIPTION));
        orgSubFundSummaryReportEntry.setHeader3(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.MSG_REPORT_HEADER_BASE_AMOUNT));
        orgSubFundSummaryReportEntry.setHeader4(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.MSG_REPORT_HEADER_REQ_AMOUNT));
        orgSubFundSummaryReportEntry.setHeader5(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.MSG_REPORT_HEADER_CHANGE));
        orgSubFundSummaryReportEntry.setHeader6(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.MSG_REPORT_HEADER_CHANGE));
        orgSubFundSummaryReportEntry.setConsHdr("");
    }

    /**
     * builds report body
     *
     * @param orgSubFundSummaryReportEntry
     * @param subFundSummary
     */
    public void buildReportsBody(BudgetConstructionOrgSubFundSummaryReport orgSubFundSummaryReportEntry, BudgetConstructionAccountSummary subFundSummary) {

        // build income expense description
        if (subFundSummary.getIncomeExpenseCode().equals(BCConstants.Report.INCOME_EXP_TYPE_A)) {
            orgSubFundSummaryReportEntry.setIncExpDesc(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.MSG_REPORT_INCOME_EXP_DESC_REVENUE));
        }
        else if (subFundSummary.getIncomeExpenseCode().equals(BCConstants.Report.INCOME_EXP_TYPE_E)) {
            orgSubFundSummaryReportEntry.setIncExpDesc(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.MSG_REPORT_INCOME_EXP_DESC_EXP_GROSS));
        }
        else if (subFundSummary.getIncomeExpenseCode().equals(BCConstants.Report.INCOME_EXP_TYPE_T)) {
            trExist = true;
            orgSubFundSummaryReportEntry.setIncExpDesc(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.MSG_REPORT_INCOME_EXP_DESC_TRNFR_IN));
        }
        else {
            if (trExist) {
                trExist = false;
                orgSubFundSummaryReportEntry.setIncExpDesc(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.MSG_REPORT_INCOME_EXP_DESC_EXP_NET_TRNFR));
            }
            else {
                orgSubFundSummaryReportEntry.setIncExpDesc(kualiConfigurationService.getPropertyValueAsString(BCKeyConstants.MSG_REPORT_INCOME_EXP_DESC_EXPENDITURE));
            }
        }
        orgSubFundSummaryReportEntry.setBaseAmount(subFundSummary.getFinancialBeginningBalanceLineAmount());
        orgSubFundSummaryReportEntry.setReqAmount(subFundSummary.getAccountLineAnnualBalanceAmount());
        orgSubFundSummaryReportEntry.setAmountChange(orgSubFundSummaryReportEntry.getReqAmount().subtract(orgSubFundSummaryReportEntry.getBaseAmount()));
        orgSubFundSummaryReportEntry.setPercentChange(BudgetConstructionReportHelper.calculatePercent(orgSubFundSummaryReportEntry.getAmountChange(), orgSubFundSummaryReportEntry.getBaseAmount()));
    }

    /**
     * builds report total
     *
     * @param orgSubFundSummaryReportEntry
     * @param subFundSummaryList
     * @param reportTotalList
     */
    public void buildReportsTotal(BudgetConstructionOrgSubFundSummaryReport orgSubFundSummaryReportEntry, BudgetConstructionAccountSummary subFundSummaryList, List<BudgetConstructionOrgSubFundSummaryReportTotal> reportSubTotalList, List<BudgetConstructionOrgSubFundSummaryReportTotal> reportTotalList) {
        for (BudgetConstructionOrgSubFundSummaryReportTotal subTotalEntry : reportSubTotalList) {
            if (BudgetConstructionReportHelper.isSameEntry(subFundSummaryList, subTotalEntry.getBcas(), fieldsForSubTotal())) {
                orgSubFundSummaryReportEntry.setSubFundTotalRevenueBaseAmount(subTotalEntry.getSubFundTotalRevenueBaseAmount());
                orgSubFundSummaryReportEntry.setSubFundTotalRevenueReqAmount(subTotalEntry.getSubFundTotalRevenueReqAmount());
                orgSubFundSummaryReportEntry.setSubFundTotalRevenueAmountChange(subTotalEntry.getSubFundTotalRevenueReqAmount().subtract(subTotalEntry.getSubFundTotalRevenueBaseAmount()));
                BigDecimal percentChange = BigDecimal.ZERO;
                percentChange = BudgetConstructionReportHelper.calculatePercent(orgSubFundSummaryReportEntry.getSubFundTotalRevenueAmountChange(), orgSubFundSummaryReportEntry.getSubFundTotalRevenueBaseAmount());
                orgSubFundSummaryReportEntry.setSubFundTotalRevenuePercentChange(percentChange);
            }


            for (BudgetConstructionOrgSubFundSummaryReportTotal totalEntry : reportTotalList) {
                if (BudgetConstructionReportHelper.isSameEntry(subFundSummaryList, totalEntry.getBcas(), fieldsForTotal())) {
                    BigDecimal percentChange = BigDecimal.ZERO;
                    orgSubFundSummaryReportEntry.setTotalRevenueBaseAmount(totalEntry.getTotalRevenueBaseAmount());
                    orgSubFundSummaryReportEntry.setTotalGrossBaseAmount(totalEntry.getTotalGrossBaseAmount());
                    orgSubFundSummaryReportEntry.setTotalTransferInBaseAmount(totalEntry.getTotalTransferInBaseAmount());
                    orgSubFundSummaryReportEntry.setTotalNetTransferBaseAmount(totalEntry.getTotalNetTransferBaseAmount());

                    orgSubFundSummaryReportEntry.setTotalRevenueReqAmount(totalEntry.getTotalRevenueReqAmount());
                    orgSubFundSummaryReportEntry.setTotalGrossReqAmount(totalEntry.getTotalGrossReqAmount());
                    orgSubFundSummaryReportEntry.setTotalTransferInReqAmount(totalEntry.getTotalTransferInReqAmount());
                    orgSubFundSummaryReportEntry.setTotalNetTransferReqAmount(totalEntry.getTotalNetTransferReqAmount());
                    orgSubFundSummaryReportEntry.setTotalRevenueAmountChange(orgSubFundSummaryReportEntry.getTotalRevenueReqAmount().subtract(orgSubFundSummaryReportEntry.getTotalRevenueBaseAmount()));
                    percentChange = BudgetConstructionReportHelper.calculatePercent(orgSubFundSummaryReportEntry.getTotalRevenueAmountChange(), orgSubFundSummaryReportEntry.getTotalRevenueBaseAmount());
                    orgSubFundSummaryReportEntry.setTotalRevenuePercentChange(percentChange);

                    orgSubFundSummaryReportEntry.setTotalGrossAmountChange(orgSubFundSummaryReportEntry.getTotalGrossReqAmount().subtract(orgSubFundSummaryReportEntry.getTotalGrossBaseAmount()));
                    percentChange = BudgetConstructionReportHelper.calculatePercent(orgSubFundSummaryReportEntry.getTotalGrossAmountChange(), orgSubFundSummaryReportEntry.getTotalGrossBaseAmount());
                    orgSubFundSummaryReportEntry.setTotalGrossPercentChange(percentChange);

                    orgSubFundSummaryReportEntry.setTotalTransferAmountChange(orgSubFundSummaryReportEntry.getTotalTransferInReqAmount().subtract(orgSubFundSummaryReportEntry.getTotalTransferInBaseAmount()));
                    percentChange = BudgetConstructionReportHelper.calculatePercent(orgSubFundSummaryReportEntry.getTotalTransferAmountChange(), orgSubFundSummaryReportEntry.getTotalTransferInBaseAmount());
                    orgSubFundSummaryReportEntry.setTotalTransferInPercentChange(percentChange);


                    orgSubFundSummaryReportEntry.setTotalNetTransferAmountChange(orgSubFundSummaryReportEntry.getTotalNetTransferReqAmount().subtract(orgSubFundSummaryReportEntry.getTotalNetTransferBaseAmount()));
                    percentChange = BudgetConstructionReportHelper.calculatePercent(orgSubFundSummaryReportEntry.getTotalNetTransferAmountChange(), orgSubFundSummaryReportEntry.getTotalNetTransferBaseAmount());
                    orgSubFundSummaryReportEntry.setTotalNetTransferPercentChange(percentChange);

                    orgSubFundSummaryReportEntry.setRevExpDifferenceBaseAmount(orgSubFundSummaryReportEntry.getTotalRevenueBaseAmount().subtract(orgSubFundSummaryReportEntry.getTotalNetTransferBaseAmount()));
                    orgSubFundSummaryReportEntry.setRevExpDifferenceReqAmount(orgSubFundSummaryReportEntry.getTotalRevenueReqAmount().subtract(orgSubFundSummaryReportEntry.getTotalNetTransferReqAmount()));
                    orgSubFundSummaryReportEntry.setRevExpDifferenceAmountChange(orgSubFundSummaryReportEntry.getRevExpDifferenceReqAmount().subtract(orgSubFundSummaryReportEntry.getRevExpDifferenceBaseAmount()));
                    percentChange = BudgetConstructionReportHelper.calculatePercent(orgSubFundSummaryReportEntry.getRevExpDifferenceAmountChange(), orgSubFundSummaryReportEntry.getRevExpDifferenceBaseAmount());
                    orgSubFundSummaryReportEntry.setRevExpDifferencePercentChange(percentChange);
                }
            }
        }
    }


    /**
     * Calculates total part of report
     *
     * @param List bcasList
     * @param List simpleList
     */
    public List calculateSubTotal(List<BudgetConstructionAccountSummary> bcasList, List<BudgetConstructionAccountSummary> subTotalList) {
        KualiInteger subFundTotalRevenueBaseAmount = KualiInteger.ZERO;
        KualiInteger subFundTotalRevenueReqAmount = KualiInteger.ZERO;

        List returnList = new ArrayList();
        for (BudgetConstructionAccountSummary simpleBcasEntry : subTotalList) {
            BudgetConstructionOrgSubFundSummaryReportTotal bcSubFundTotal = new BudgetConstructionOrgSubFundSummaryReportTotal();
            for (BudgetConstructionAccountSummary bcasListEntry : bcasList) {
                if (BudgetConstructionReportHelper.isSameEntry(simpleBcasEntry, bcasListEntry, fieldsForSubTotal())) {
                    if (bcasListEntry.getIncomeExpenseCode().equals(BCConstants.Report.INCOME_EXP_TYPE_A)) {
                        subFundTotalRevenueBaseAmount = subFundTotalRevenueBaseAmount.subtract(bcasListEntry.getFinancialBeginningBalanceLineAmount());
                        subFundTotalRevenueReqAmount = subFundTotalRevenueReqAmount.subtract(bcasListEntry.getAccountLineAnnualBalanceAmount());
                    }
                    else if (bcasListEntry.getIncomeExpenseCode().equals(BCConstants.Report.INCOME_EXP_TYPE_X)) {
                        subFundTotalRevenueBaseAmount = subFundTotalRevenueBaseAmount.add(bcasListEntry.getFinancialBeginningBalanceLineAmount());
                        subFundTotalRevenueReqAmount = subFundTotalRevenueReqAmount.add(bcasListEntry.getAccountLineAnnualBalanceAmount());
                    }
                }
            }
            bcSubFundTotal.setBcas(simpleBcasEntry);
            bcSubFundTotal.setSubFundTotalRevenueBaseAmount(subFundTotalRevenueBaseAmount);
            bcSubFundTotal.setSubFundTotalRevenueReqAmount(subFundTotalRevenueReqAmount);
            subFundTotalRevenueBaseAmount = KualiInteger.ZERO;
            subFundTotalRevenueReqAmount = KualiInteger.ZERO;
            returnList.add(bcSubFundTotal);

        }
        return returnList;
    }


    /**
     * Calculates total part of report
     *
     * @param List bcasList
     * @param List simpleList
     */
    public List calculateTotal(List<BudgetConstructionAccountSummary> bcasList, List<BudgetConstructionAccountSummary> totalList) {
        KualiInteger totalRevenueBaseAmount = KualiInteger.ZERO;
        KualiInteger totalGrossBaseAmount = KualiInteger.ZERO;
        KualiInteger totalTransferInBaseAmount = KualiInteger.ZERO;
        KualiInteger totalNetTransferBaseAmount = KualiInteger.ZERO;
        KualiInteger totalRevenueReqAmount = KualiInteger.ZERO;
        KualiInteger totalGrossReqAmount = KualiInteger.ZERO;
        KualiInteger totalTransferInReqAmount = KualiInteger.ZERO;
        KualiInteger totalNetTransferReqAmount = KualiInteger.ZERO;

        List returnList = new ArrayList();
        for (BudgetConstructionAccountSummary simpleBcasEntry : totalList) {
            BudgetConstructionOrgSubFundSummaryReportTotal bcSubFundTotal = new BudgetConstructionOrgSubFundSummaryReportTotal();
            for (BudgetConstructionAccountSummary bcasListEntry : bcasList) {
                if (BudgetConstructionReportHelper.isSameEntry(simpleBcasEntry, bcasListEntry, fieldsForTotal())) {
                    if (bcasListEntry.getIncomeExpenseCode().equals(BCConstants.Report.INCOME_EXP_TYPE_A)) {
                        totalRevenueBaseAmount = totalRevenueBaseAmount.add(bcasListEntry.getFinancialBeginningBalanceLineAmount());
                        totalRevenueReqAmount = totalRevenueReqAmount.add(bcasListEntry.getAccountLineAnnualBalanceAmount());
                    }
                    else if (bcasListEntry.getIncomeExpenseCode().equals(BCConstants.Report.INCOME_EXP_TYPE_E)) {
                        totalGrossBaseAmount = totalGrossBaseAmount.add(bcasListEntry.getFinancialBeginningBalanceLineAmount());
                        totalGrossReqAmount = totalGrossReqAmount.add(bcasListEntry.getAccountLineAnnualBalanceAmount());

                    }
                    else if (bcasListEntry.getIncomeExpenseCode().equals(BCConstants.Report.INCOME_EXP_TYPE_T)) {
                        totalTransferInBaseAmount = totalTransferInBaseAmount.add(bcasListEntry.getFinancialBeginningBalanceLineAmount());
                        totalTransferInReqAmount = totalTransferInReqAmount.add(bcasListEntry.getAccountLineAnnualBalanceAmount());
                    }
                    else if (bcasListEntry.getIncomeExpenseCode().equals(BCConstants.Report.INCOME_EXP_TYPE_X)) {
                        totalNetTransferBaseAmount = totalNetTransferBaseAmount.add(bcasListEntry.getFinancialBeginningBalanceLineAmount());
                        totalNetTransferReqAmount = totalNetTransferReqAmount.add(bcasListEntry.getAccountLineAnnualBalanceAmount());
                    }
                }
            }
            bcSubFundTotal.setBcas(simpleBcasEntry);
            bcSubFundTotal.setTotalGrossBaseAmount(totalGrossBaseAmount);
            bcSubFundTotal.setTotalGrossReqAmount(totalGrossReqAmount);
            bcSubFundTotal.setTotalNetTransferBaseAmount(totalNetTransferBaseAmount);
            bcSubFundTotal.setTotalNetTransferReqAmount(totalNetTransferReqAmount);
            bcSubFundTotal.setTotalRevenueBaseAmount(totalRevenueBaseAmount);
            bcSubFundTotal.setTotalRevenueReqAmount(totalRevenueReqAmount);
            bcSubFundTotal.setTotalTransferInBaseAmount(totalTransferInBaseAmount);
            bcSubFundTotal.setTotalTransferInReqAmount(totalTransferInReqAmount);
            returnList.add(bcSubFundTotal);
            totalGrossBaseAmount = KualiInteger.ZERO;
            totalGrossReqAmount = KualiInteger.ZERO;
            totalNetTransferBaseAmount = KualiInteger.ZERO;
            totalNetTransferReqAmount = KualiInteger.ZERO;
            totalRevenueBaseAmount = KualiInteger.ZERO;
            totalRevenueReqAmount = KualiInteger.ZERO;
            totalTransferInBaseAmount = KualiInteger.ZERO;
            totalTransferInReqAmount = KualiInteger.ZERO;
        }
        return returnList;
    }

    protected List<String> fieldsForSubTotal() {
        List<String> fieldList = fieldsForTotal();
        fieldList.add(KFSPropertyConstants.SUB_FUND_GROUP_CODE);
        return fieldList;
    }


    protected List<String> fieldsForTotal() {
        List<String> fieldList = new ArrayList();
        fieldList.add(KFSPropertyConstants.ORGANIZATION_CHART_OF_ACCOUNTS_CODE);
        fieldList.add(KFSPropertyConstants.ORGANIZATION_CODE);
        fieldList.add(KFSPropertyConstants.CHART_OF_ACCOUNTS_CODE);
        fieldList.add(KFSPropertyConstants.FUND_GROUP_CODE);
        return fieldList;
    }


    /**
     * builds orderByList for sort order.
     *
     * @return returnList
     */
    public List<String> buildOrderByList() {
        List<String> returnList = new ArrayList();
        returnList.add(KFSPropertyConstants.ORGANIZATION_CHART_OF_ACCOUNTS_CODE);
        returnList.add(KFSPropertyConstants.ORGANIZATION_CODE);
        returnList.add(KFSPropertyConstants.CHART_OF_ACCOUNTS_CODE);
        returnList.add(KFSPropertyConstants.SUB_FUND_SORT_CODE);
        returnList.add(KFSPropertyConstants.FUND_GROUP_CODE);
        returnList.add(KFSPropertyConstants.SUB_FUND_GROUP_CODE);
        returnList.add(KFSPropertyConstants.ACCOUNT_NUMBER);
        returnList.add(KFSPropertyConstants.SUB_ACCOUNT_NUMBER);
        returnList.add(KFSPropertyConstants.INCOME_EXPENSE_CODE);
        return returnList;
    }


    /**
     * sets budgetConstructionAccountSummaryReportDao
     *
     * @param budgetConstructionAccountSummaryReportDao
     */
    public void setBudgetConstructionAccountSummaryReportDao(BudgetConstructionAccountSummaryReportDao budgetConstructionAccountSummaryReportDao) {
        this.budgetConstructionAccountSummaryReportDao = budgetConstructionAccountSummaryReportDao;
    }

    public void setConfigurationService(ConfigurationService kualiConfigurationService) {
        this.kualiConfigurationService = kualiConfigurationService;
    }

    public void setBudgetConstructionReportsServiceHelper(BudgetConstructionReportsServiceHelper budgetConstructionReportsServiceHelper) {
        this.budgetConstructionReportsServiceHelper = budgetConstructionReportsServiceHelper;
    }
}
