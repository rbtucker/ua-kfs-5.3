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
package org.kuali.kfs.module.ar.document.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.kfs.module.ar.businessobject.AccountsReceivableDocumentHeader;
import org.kuali.kfs.module.ar.businessobject.AppliedPayment;
import org.kuali.kfs.module.ar.businessobject.CashControlDetail;
import org.kuali.kfs.module.ar.businessobject.CustomerInvoiceDetail;
import org.kuali.kfs.module.ar.businessobject.InvoicePaidApplied;
import org.kuali.kfs.module.ar.businessobject.NonAppliedHolding;
import org.kuali.kfs.module.ar.document.CashControlDocument;
import org.kuali.kfs.module.ar.document.CustomerInvoiceDocument;
import org.kuali.kfs.module.ar.document.PaymentApplicationDocument;
import org.kuali.kfs.module.ar.document.dataaccess.CashControlDetailDao;
import org.kuali.kfs.module.ar.document.service.InvoicePaidAppliedService;
import org.kuali.kfs.module.ar.document.service.NonAppliedHoldingService;
import org.kuali.kfs.module.ar.document.service.PaymentApplicationDocumentService;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.kfs.sys.service.UniversityDateService;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DocumentService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PaymentApplicationDocumentServiceImpl implements PaymentApplicationDocumentService {
    private static Logger LOG = org.apache.log4j.Logger.getLogger(PaymentApplicationDocumentServiceImpl.class);;

    private DocumentService documentService;
    private BusinessObjectService businessObjectService;
    private NonAppliedHoldingService nonAppliedHoldingService;
    private InvoicePaidAppliedService<AppliedPayment> invoicePaidAppliedService;
    private UniversityDateService universityDateService;
    private CashControlDetailDao cashControlDetailDao;

    /**
     *
     * @param customerInvoiceDocument
     * @return
     * @throws WorkflowException
     */
    @Override
    public PaymentApplicationDocument createPaymentApplicationToMatchInvoice(CustomerInvoiceDocument customerInvoiceDocument) throws WorkflowException {

        PaymentApplicationDocument applicationDocument =
            (PaymentApplicationDocument) documentService.getNewDocument(PaymentApplicationDocument.class);

        //  get the processing chart & org off the invoice, we'll create the payapp with the same processing org
        String processingChartCode = customerInvoiceDocument.getAccountsReceivableDocumentHeader().getProcessingChartOfAccountCode();
        String processingOrgCode = customerInvoiceDocument.getAccountsReceivableDocumentHeader().getProcessingOrganizationCode();

        AccountsReceivableDocumentHeader arDocHeader = new AccountsReceivableDocumentHeader();
        arDocHeader.setProcessingChartOfAccountCode(processingChartCode);
        arDocHeader.setProcessingOrganizationCode(processingOrgCode);
        arDocHeader.setDocumentNumber(applicationDocument.getDocumentNumber());
        applicationDocument.setAccountsReceivableDocumentHeader(arDocHeader);

        // This code is basically copied from PaymentApplicationDocumentAction.quickApply
        int paidAppliedItemNumber = 1;
        for(CustomerInvoiceDetail customerInvoiceDetail : customerInvoiceDocument.getCustomerInvoiceDetailsWithoutDiscounts()) {
            InvoicePaidApplied invoicePaidApplied =
                createInvoicePaidAppliedForInvoiceDetail(
                    customerInvoiceDetail, applicationDocument, paidAppliedItemNumber);
            // if there was not another invoice paid applied already created for the current detail then invoicePaidApplied will not be null
            if (invoicePaidApplied != null) {
                // add it to the payment application document list of applied payments
                applicationDocument.getInvoicePaidApplieds().add(invoicePaidApplied);
                paidAppliedItemNumber++;
            }
        }

        return applicationDocument;
    }

    /**
     *
     * @param customerInvoiceDocument
     * @return
     * @throws WorkflowException
     */
    @Override
    public PaymentApplicationDocument createAndSavePaymentApplicationToMatchInvoice(CustomerInvoiceDocument customerInvoiceDocument) throws WorkflowException {
        PaymentApplicationDocument applicationDocument = createPaymentApplicationToMatchInvoice(customerInvoiceDocument);
        documentService.saveDocument(applicationDocument);
        return applicationDocument;
    }

    /**
     *
     * @param customerInvoiceDocument
     * @param approvalAnnotation
     * @param workflowNotificationRecipients
     * @return
     * @throws WorkflowException
     */
    @Override
    public PaymentApplicationDocument createSaveAndApprovePaymentApplicationToMatchInvoice(CustomerInvoiceDocument customerInvoiceDocument, String approvalAnnotation, List workflowNotificationRecipients) throws WorkflowException {
        DocumentService documentService = SpringContext.getBean(DocumentService.class);
        PaymentApplicationDocument applicationDocument = createAndSavePaymentApplicationToMatchInvoice(customerInvoiceDocument);
        documentService.approveDocument(applicationDocument, approvalAnnotation, workflowNotificationRecipients);
        return applicationDocument;
    }

    /**
     *
     * @param document
     * @return
     */
    public KualiDecimal getTotalAppliedAmountForPaymentApplicationDocument(PaymentApplicationDocument document) {
        KualiDecimal total = KualiDecimal.ZERO;
        Collection<InvoicePaidApplied> invoicePaidApplieds = document.getInvoicePaidApplieds();

        for (InvoicePaidApplied invoicePaidApplied : invoicePaidApplieds) {
            total = total.add(invoicePaidApplied.getInvoiceItemAppliedAmount());
        }

        // Include non-ar funds as well
        total = total.add(document.getSumOfNonInvoiceds());

        return total;
    }

    /**
     *
     * @param document
     * @return
     */
    public KualiDecimal getTotalUnappliedFundsForPaymentApplicationDocument(PaymentApplicationDocument document) {
        KualiDecimal total = KualiDecimal.ZERO;

        String customerNumber = document.getAccountsReceivableDocumentHeader().getCustomerNumber();
        Collection<NonAppliedHolding> nonAppliedHoldings = nonAppliedHoldingService.getNonAppliedHoldingsForCustomer(customerNumber);

        for (NonAppliedHolding nonAppliedHolding : nonAppliedHoldings) {
            total = total.add(nonAppliedHolding.getFinancialDocumentLineAmount());
        }

        // Add the amount for this document, if it's set
        NonAppliedHolding nonAppliedHolding = document.getNonAppliedHolding();
        if(null != nonAppliedHolding) {
            KualiDecimal amount = nonAppliedHolding.getFinancialDocumentLineAmount();
            if(null != amount) {
                total = total.add(amount);
            }
        }

        return total;
    }

    /**
     *
     * @param document
     * @return
     */
    public KualiDecimal getTotalUnappliedFundsToBeAppliedForPaymentApplicationDocument(PaymentApplicationDocument document) {
        KualiDecimal totalUnapplied = getTotalUnappliedFundsForPaymentApplicationDocument(document);
        KualiDecimal totalApplied = getTotalAppliedAmountForPaymentApplicationDocument(document);
        return totalUnapplied.subtract(totalApplied);
    }

    /**
     *
     * @see org.kuali.kfs.module.ar.document.service.PaymentApplicationDocumentService#getCashControlDocumentForPaymentApplicationDocument(org.kuali.kfs.module.ar.document.PaymentApplicationDocument)
     */
    @Override
    public CashControlDocument getCashControlDocumentForPaymentApplicationDocument(PaymentApplicationDocument paymentApplicationDocument) {
        if (paymentApplicationDocument == null) {
            throw new IllegalArgumentException("A null paymentApplicationDocument parameter was passed in.");
        }
        String payAppDocNumber = paymentApplicationDocument.getDocumentHeader().getDocumentNumber();
        return getCashControlDocumentForPayAppDocNumber(payAppDocNumber);
    }

    /**
     *
     * @see org.kuali.kfs.module.ar.document.service.PaymentApplicationDocumentService#getCashControlDocumentForPayAppDocNumber(java.lang.String)
     */
    @Override
    public CashControlDocument getCashControlDocumentForPayAppDocNumber(String paymentApplicationDocumentNumber) {
        if (StringUtils.isBlank(paymentApplicationDocumentNumber)) {
            throw new IllegalArgumentException("A null or blank paymentApplicationDocumentNumber paraemter was passed in.");
        }
        CashControlDetail cashControlDetail = getCashControlDetailForPayAppDocNumber(paymentApplicationDocumentNumber);
        if (cashControlDetail == null) {
            return null;
        }
        CashControlDocument cashControlDocument = null;
        try {
            cashControlDocument = (CashControlDocument) documentService.getByDocumentHeaderId(cashControlDetail.getDocumentNumber());
        }
        catch (WorkflowException e) {
            //TODO we may need to swallow this ...
            throw new RuntimeException("A workflow exception was thrown when trying to retrieve document [" + cashControlDetail.getDocumentNumber() + "].", e);
        }
        return cashControlDocument;
    }

    /**
     * @see org.kuali.kfs.module.ar.document.service.PaymentApplicationDocumentService#getCashControlDetailForPaymentApplicationDocument(org.kuali.kfs.module.ar.document.PaymentApplicationDocument)
     */
    @Override
    public CashControlDetail getCashControlDetailForPaymentApplicationDocument(PaymentApplicationDocument document) {
        if (document == null) {
            throw new IllegalArgumentException("A null paymentApplicationDocument parameter was passed in.");
        }
        String payAppDocumentNumber = document.getDocumentNumber();
        CashControlDetail cashControlDetail = getCashControlDetailForPayAppDocNumber(payAppDocumentNumber);
        return cashControlDetail;
    }

    /**
     *
     * @see org.kuali.kfs.module.ar.document.service.PaymentApplicationDocumentService#getCashControlDetailForPayAppDocNumber(java.lang.String)
     */
    @Override
    public CashControlDetail getCashControlDetailForPayAppDocNumber(String payAppDocNumber) {
        if (StringUtils.isBlank(payAppDocNumber)) {
            throw new IllegalArgumentException("A null or blank payAppDocNumber parameter was passed in.");
        }
        CashControlDetail cashControlDetail = cashControlDetailDao.getCashControlDetailByRefDocNumber(payAppDocNumber);
        return cashControlDetail;
    }

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    @Override
    public PaymentApplicationDocument createInvoicePaidAppliedsForEntireInvoiceDocument(CustomerInvoiceDocument customerInvoiceDocument, PaymentApplicationDocument paymentApplicationDocument) {

        //  clear any existing paidapplieds
        paymentApplicationDocument.getInvoicePaidApplieds().clear();

        int paidAppliedItemNumber = 1;
        for(CustomerInvoiceDetail detail : customerInvoiceDocument.getCustomerInvoiceDetailsWithoutDiscounts()) {

            //  create the new paidapplied
            InvoicePaidApplied invoicePaidApplied = createInvoicePaidAppliedForInvoiceDetail(
                    detail, paymentApplicationDocument, paidAppliedItemNumber);

            // add it to the payment application document list of applied payments
            paymentApplicationDocument.getInvoicePaidApplieds().add(invoicePaidApplied);
            paidAppliedItemNumber++;
        }

        return paymentApplicationDocument;
    }

    /**
     * @see org.kuali.kfs.module.ar.document.service.PaymentApplicationDocumentService#createInvoicePaidAppliedForInvoiceDetail(org.kuali.kfs.module.ar.businessobject.CustomerInvoiceDetail, org.kuali.rice.core.api.util.type.KualiDecimal)
     */
    @Override
    public InvoicePaidApplied createInvoicePaidAppliedForInvoiceDetail(CustomerInvoiceDetail customerInvoiceDetail, PaymentApplicationDocument paymentApplicationDocument, Integer paidAppliedItemNumber) {

        Integer universityFiscalYear = universityDateService.getCurrentFiscalYear();
        String universityFiscalPeriodCode = universityDateService.getCurrentUniversityDate().getAccountingPeriod().getUniversityFiscalPeriodCode();

        InvoicePaidApplied invoicePaidApplied = new InvoicePaidApplied();

        // set the document number for the invoice paid applied to the payment application document number.
        invoicePaidApplied.setDocumentNumber(paymentApplicationDocument.getDocumentNumber());

        // Set the invoice paid applied ref doc number to the document number for the customer invoice document
        invoicePaidApplied.setFinancialDocumentReferenceInvoiceNumber(customerInvoiceDetail.getDocumentNumber());

        invoicePaidApplied.setInvoiceItemNumber(customerInvoiceDetail.getSequenceNumber());
        invoicePaidApplied.setInvoiceItemAppliedAmount(customerInvoiceDetail.getAmountOpen());
        invoicePaidApplied.setUniversityFiscalYear(universityFiscalYear);
        invoicePaidApplied.setUniversityFiscalPeriodCode(universityFiscalPeriodCode);
        invoicePaidApplied.setPaidAppliedItemNumber(paidAppliedItemNumber);

        return invoicePaidApplied;
    }

    /**
     * @see org.kuali.kfs.module.ar.document.service.PaymentApplicationDocumentService#customerInvoiceDetailPairsWithInvoicePaidApplied(org.kuali.kfs.module.ar.businessobject.CustomerInvoiceDetail, org.kuali.kfs.module.ar.businessobject.InvoicePaidApplied)
     */
    @Override
    public boolean customerInvoiceDetailPairsWithInvoicePaidApplied(CustomerInvoiceDetail customerInvoiceDetail, InvoicePaidApplied invoicePaidApplied) {
        boolean pairs = true;
        pairs &= customerInvoiceDetail.getSequenceNumber().equals(invoicePaidApplied.getInvoiceItemNumber());
        pairs &= customerInvoiceDetail.getDocumentNumber().equals(invoicePaidApplied.getFinancialDocumentReferenceInvoiceNumber());
        return pairs;
    }

    public Collection<PaymentApplicationDocument> getPaymentApplicationDocumentByInvoiceDocument(String invoiceNumber) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("financialDocumentReferenceInvoiceNumber", invoiceNumber);
        BusinessObjectService service = SpringContext.getBean(BusinessObjectService.class);

        Collection<PaymentApplicationDocument> payments = service.findMatching(PaymentApplicationDocument.class, fieldValues);

        return payments;
    }

    /*
    public Collection<PaymentApplicationDocument> getPaymentApplicationDocumentsByCustomerNumber(String customerNumber) {

        Collection<PaymentApplicationDocument> payments = new ArrayList<PaymentApplicationDocument>();

        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("customerNumber", customerNumber);

        Collection<AccountsReceivableDocumentHeader> documentHeaders = businessObjectService.findMatching(AccountsReceivableDocumentHeader.class, fieldValues);

        List<String> documentHeaderIds = new ArrayList<String>();
        for (AccountsReceivableDocumentHeader header : documentHeaders) {
            String documentNumber = null;
            try {
                Long.parseLong(header.getDocumentHeader().getDocumentNumber());
                documentNumber = header.getDocumentHeader().getDocumentNumber();
                documentHeaderIds.add(documentNumber);
            }
            catch (NumberFormatException nfe) {
            }
        }

        if (0 < documentHeaderIds.size()) {
            try {
                payments = documentService.getDocumentsByListOfDocumentHeaderIds(PaymentApplicationDocument.class, documentHeaderIds);
            }
            catch (WorkflowException e) {
                //LOG.error(e.getMessage(), e);
            }
        }
        return payments;
    }

    public Collection<PaymentApplicationDocument> getPaymentApplicationDocumentsByAccountNumber(String accountNumber) {

        Collection<CustomerInvoiceDocument> invoiceList = SpringContext.getBean(CustomerInvoiceDocumentService.class).getCustomerInvoiceDocumentsByAccountNumber(accountNumber);

        Set<String> customerNumberSet = new HashSet<String>();
        for (CustomerInvoiceDocument invoice : invoiceList) {
            Map<String, String> fieldValues = new HashMap<String, String>();
            fieldValues.put("documentNumber", invoice.getDocumentNumber());

            AccountsReceivableDocumentHeader arDocHeader = (AccountsReceivableDocumentHeader)businessObjectService.findByPrimaryKey(AccountsReceivableDocumentHeader.class, fieldValues);
            customerNumberSet.add(arDocHeader.getCustomerNumber());
        }

        Collection<PaymentApplicationDocument> paymentApplicationDocumentList = new ArrayList<PaymentApplicationDocument>();
        for (String customerNumber : customerNumberSet) {
            paymentApplicationDocumentList.addAll(getPaymentApplicationDocumentsByCustomerNumber(customerNumber));
        }

        return paymentApplicationDocumentList;
    }
    */

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void setInvoicePaidAppliedService(InvoicePaidAppliedService invoicePaidAppliedService) {
        this.invoicePaidAppliedService = invoicePaidAppliedService;
    }

    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    public NonAppliedHoldingService getNonAppliedHoldingService() {
        return nonAppliedHoldingService;
    }

    public void setNonAppliedHoldingService(NonAppliedHoldingService nonAppliedHoldingService) {
        this.nonAppliedHoldingService = nonAppliedHoldingService;
    }

    public void setUniversityDateService(UniversityDateService universityDateService) {
        this.universityDateService = universityDateService;
    }

    public void setCashControlDetailDao(CashControlDetailDao cashControlDetailDao) {
        this.cashControlDetailDao = cashControlDetailDao;
    }

}
