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
package org.kuali.kfs.module.ld.document;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kfs.module.ld.businessobject.LaborLedgerPendingEntry;
import org.kuali.kfs.module.ld.service.LaborLedgerPendingEntryService;
import org.kuali.kfs.sys.KFSConstants;
import org.kuali.kfs.sys.businessobject.GeneralLedgerPendingEntrySequenceHelper;
import org.kuali.kfs.sys.businessobject.GeneralLedgerPendingEntrySourceDetail;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.kfs.sys.document.AccountingDocumentBase;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.krad.exception.ValidationException;
import org.kuali.rice.krad.rules.rule.event.KualiDocumentEvent;

/**
 * Labor Document base class implementation for all labor eDocs that are transactional, meaning implementing
 * TransactionalDocumentBase. Additional functionality for labor is provided by this class, suchc as retrieving labor ledger pending
 * entries.
 */
public abstract class LaborLedgerPostingDocumentBase extends AccountingDocumentBase implements LaborLedgerPostingDocument {
    protected List<LaborLedgerPendingEntry> laborLedgerPendingEntries;
    
    protected final static String LABOR_LEDGER_GENERAL_LEDGER_POSTING_HELPER_BEAN_ID = "kfsDoNothingGeneralLedgerPostingHelper";

    /**
     * Initializes the pending entries.
     */
    public LaborLedgerPostingDocumentBase() {
        super();
        setLaborLedgerPendingEntries(new ArrayList<LaborLedgerPendingEntry>());
    }

    /**
     * @see org.kuali.kfs.module.ld.document.LaborLedgerPostingDocument#getLaborLedgerPendingEntries()
     */
    public List<LaborLedgerPendingEntry> getLaborLedgerPendingEntries() {
        return this.laborLedgerPendingEntries;
    }

    /**
     * @see org.kuali.kfs.module.ld.document.LaborLedgerPostingDocument#setLaborLedgerPendingEntries(java.util.List)
     */
    public void setLaborLedgerPendingEntries(List<LaborLedgerPendingEntry> laborLedgerPendingEntries) {
        this.laborLedgerPendingEntries = laborLedgerPendingEntries;
    }

    /**
     * Override to call super and then iterate over all GLPEs and update the approved code appropriately.
     * 
     * @see Document#doRouteStatusChange()
     */
    @Override
    public void doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) {
        super.doRouteStatusChange(statusChangeEvent);
        if (getDocumentHeader().getWorkflowDocument().isProcessed()) {
            changeLedgerPendingEntriesApprovedStatusCode();
        }
        else if (getDocumentHeader().getWorkflowDocument().isCanceled() || getDocumentHeader().getWorkflowDocument().isDisapproved()) {
            removeLedgerPendingEntries();
        }
    }

    /**
     * This method iterates over all of the pending entries for a document and sets their approved status code to APPROVED "A".
     */
    protected void changeLedgerPendingEntriesApprovedStatusCode() {
        for (LaborLedgerPendingEntry pendingEntry : laborLedgerPendingEntries) {
            pendingEntry.setFinancialDocumentApprovedCode(KFSConstants.DocumentStatusCodes.APPROVED);
        }
    }

    /**
     * This method calls the service to remove all of the pending entries associated with this document
     */
    protected void removeLedgerPendingEntries() {
        LaborLedgerPendingEntryService laborLedgerPendingEntryService = SpringContext.getBean(LaborLedgerPendingEntryService.class);
        laborLedgerPendingEntryService.delete(getDocumentHeader().getDocumentNumber());
    }

    /**
     * This implementation is coupled tightly with some underlying issues that the Struts PojoProcessor plugin has with how objects
     * get instantiated within lists. The first three lines are required otherwise when the PojoProcessor tries to automatically
     * inject values into the list, it will get an index out of bounds error if the instance at an index is being called and prior
     * instances at indices before that one are not being instantiated. So changing the code below will cause things to break.
     * 
     * @param index of Labor Ledger Pending Entry to retrieve
     * @return LaborLedgerPendingEntry
     */
    public LaborLedgerPendingEntry getLaborLedgerPendingEntry(int index) {
        while (laborLedgerPendingEntries.size() <= index) {
            laborLedgerPendingEntries.add(new LaborLedgerPendingEntry());
        }
        return laborLedgerPendingEntries.get(index);
    }

    /**
     * @see org.kuali.kfs.sys.document.AccountingDocumentBase#prepareForSave(org.kuali.rice.krad.rule.event.KualiDocumentEvent)
     */
    @Override
    public void prepareForSave(KualiDocumentEvent event) {
        if (!SpringContext.getBean(LaborLedgerPendingEntryService.class).generateLaborLedgerPendingEntries(this)) {
            logErrors();
            throw new ValidationException("labor ledger LLPE generation failed");
        }
    }
    
    /**
     * This is a "do nothing" version of the method - it just won't create GLPEs
     * @see org.kuali.kfs.sys.document.AccountingDocumentBase#generateGeneralLedgerPendingEntries(org.kuali.kfs.sys.businessobject.GeneralLedgerPendingEntrySourceDetail, org.kuali.kfs.sys.businessobject.GeneralLedgerPendingEntrySequenceHelper)
     */
    @Override
    public boolean generateGeneralLedgerPendingEntries(GeneralLedgerPendingEntrySourceDetail glpeSourceDetail, GeneralLedgerPendingEntrySequenceHelper sequenceHelper) {
        return true;
    }

    /**
     * Labor docs never generate general ledger pending entries, and therefore, this method is never called, but we always return true, since
     * we're required to have a concrete representation
     * @see org.kuali.kfs.sys.document.AccountingDocumentBase#isDebit(org.kuali.kfs.sys.businessobject.GeneralLedgerPendingEntrySourceDetail)
     */
    @Override
    public boolean isDebit(GeneralLedgerPendingEntrySourceDetail postable) {
        return true;
    }
    
}
