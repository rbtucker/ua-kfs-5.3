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
package org.kuali.kfs.module.bc.document.web.struts;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kfs.module.bc.BCConstants;
import org.kuali.kfs.module.bc.BudgetConstructionReportMode;
import org.kuali.kfs.module.bc.document.service.ReportExportService;
import org.kuali.kfs.sys.KFSConstants.ReportGeneration;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * Struts action class for report dumps.
 */
public class ReportExportAction extends BudgetConstructionImportExportAction {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ReportExportAction.class);

    /**
     * Sets up params for export screen based on the dump mode.
     * 
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReportExportForm reportDumpForm = (ReportExportForm) form;

        // retrieve report mode to titles for export screen
        BudgetConstructionReportMode reportMode = BudgetConstructionReportMode.getBudgetConstructionReportModeByName(reportDumpForm.getReportMode());
        if (reportMode == null) {
            LOG.error("Invalid report mode passed to report export action: " + reportDumpForm.getReportMode());
            throw new RuntimeException("Invalid report mode passed to report export action: " + reportDumpForm.getReportMode());
        }

        switch (reportMode) {
            case ACCOUNT_EXPORT:
                reportDumpForm.setTitle("Budget Revenue/Expenditure Export");
                break;
            case MONTHLY_EXPORT:
                reportDumpForm.setTitle("Monthly Budget Export");
                break;
            case FUNDING_EXPORT:
                reportDumpForm.setTitle("Budgeted Salary Lines Export");
                break;
        }

        return mapping.findForward(BCConstants.MAPPING_IMPORT_EXPORT);
    }

    /**
     * Validates export settings, calls service to build the dump data and dump file.
     * 
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward submit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReportExportForm reportExportForm = (ReportExportForm) form;

        boolean isValid = validateFormData(reportExportForm);
        
        if (!isValid) {
            return mapping.findForward(BCConstants.MAPPING_IMPORT_EXPORT);
        }
        
        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        BudgetConstructionReportMode reportMode = BudgetConstructionReportMode.getBudgetConstructionReportModeByName(reportExportForm.getReportMode());

        // retrieve dump records and build export file
        StringBuilder fileString = null;
        String fileName = "";
        switch (reportMode) {
            case ACCOUNT_EXPORT:
                if (reportExportForm.isOrgReport()) {
                    fileString = SpringContext.getBean(ReportExportService.class).buildOrganizationAccountDumpFile(principalId, getFieldSeparator(reportExportForm), getTextFieldDelimiter(reportExportForm));
                } else {
                    fileString = SpringContext.getBean(ReportExportService.class).buildAccountDumpFile(principalId, getFieldSeparator(reportExportForm), getTextFieldDelimiter(reportExportForm), reportExportForm.getUniversityFiscalYear(), reportExportForm.getChartOfAccountsCode(), reportExportForm.getAccountNumber(), reportExportForm.getSubAccountNumber());
                }
                
                fileName = ReportGeneration.ACCOUNT_EXPORT_FILE_NAME;
                break;
            case MONTHLY_EXPORT:
                if (reportExportForm.isOrgReport()) {
                    fileString = SpringContext.getBean(ReportExportService.class).buildOrganizationMonthlyDumpFile(principalId, getFieldSeparator(reportExportForm), getTextFieldDelimiter(reportExportForm));
                } else {
                    fileString = SpringContext.getBean(ReportExportService.class).buildAccountMonthlyDumpFile(principalId, getFieldSeparator(reportExportForm), getTextFieldDelimiter(reportExportForm), reportExportForm.getUniversityFiscalYear(), reportExportForm.getChartOfAccountsCode(), reportExportForm.getAccountNumber(), reportExportForm.getSubAccountNumber());
                }
                
                fileName = ReportGeneration.MONTHLY_EXPORT_FILE_NAME;
                break;
            case FUNDING_EXPORT:
                if (reportExportForm.isOrgReport()) {
                    fileString = SpringContext.getBean(ReportExportService.class).buildOrganizationFundingDumpFile(principalId, getFieldSeparator(reportExportForm), getTextFieldDelimiter(reportExportForm));
                } else {
                    fileString = SpringContext.getBean(ReportExportService.class).buildAccountFundingDumpFile(principalId, getFieldSeparator(reportExportForm), getTextFieldDelimiter(reportExportForm), reportExportForm.getUniversityFiscalYear(), reportExportForm.getChartOfAccountsCode(), reportExportForm.getAccountNumber(), reportExportForm.getSubAccountNumber());
                }
                
                fileName = ReportGeneration.FUNDING_EXPORT_FILE_NAME;
                break;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (fileString.length() == 0){
            String noDataMessage = BCConstants.Report.MSG_REPORT_NO_DATA;
            baos.write(noDataMessage.getBytes());
            WebUtils.saveMimeOutputStreamAsFile(response, ReportGeneration.TEXT_MIME_TYPE, baos, fileName);
        }
        else {
            // stream text file back
            baos.write(fileString.toString().getBytes());
            WebUtils.saveMimeOutputStreamAsFile(response, ReportGeneration.TEXT_MIME_TYPE, baos, fileName);
        }

        return null;
    }
}

