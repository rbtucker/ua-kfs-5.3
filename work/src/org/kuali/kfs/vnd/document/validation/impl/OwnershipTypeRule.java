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
package org.kuali.kfs.vnd.document.validation.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.kuali.kfs.sys.KFSKeyConstants;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.kfs.vnd.VendorParameterConstants;
import org.kuali.kfs.vnd.businessobject.OwnershipType;
import org.kuali.kfs.vnd.businessobject.VendorDetail;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.DataDictionaryService;
import org.kuali.rice.krad.datadictionary.BusinessObjectEntry;

/*
 *
*/
public class OwnershipTypeRule extends MaintenanceDocumentRuleBase {
    protected static final String DEFAULT_OWNERSHIP_TYPE_LABEL = "Ownership Type"; // we shouldn't need this, but just in case...

    @Override
    protected boolean processCustomApproveDocumentBusinessRules(MaintenanceDocument document) {
        LOG.info("processCustomApproveDocumentBusinessRules called");
        this.setupConvenienceObjects();
        boolean success = this.checkForSystemParametersExistence();
        return success && super.processCustomApproveDocumentBusinessRules(document);
    }

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        LOG.info("processCustomRouteDocumentBusinessRules called");
        this.setupConvenienceObjects();
        boolean success = this.checkForSystemParametersExistence();
        return success && super.processCustomRouteDocumentBusinessRules(document);
    }

    @Override
    protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
        LOG.info("processCustomSaveDocumentBusinessRules called");
        this.setupConvenienceObjects();
        boolean success = this.checkForSystemParametersExistence();
        return success && super.processCustomSaveDocumentBusinessRules(document);
    }

    protected boolean checkForSystemParametersExistence() {
        LOG.info("checkForSystemParametersExistence called");
        boolean success = true;
        Collection<String> feinParameterValues = new ArrayList<String>( SpringContext.getBean(ParameterService.class).getParameterValuesAsString(VendorDetail.class, VendorParameterConstants.FEIN_ALLOWED_OWNERSHIP_TYPES) );
        Collection<String> ssnParameterValues = new ArrayList<String>( SpringContext.getBean(ParameterService.class).getParameterValuesAsString(VendorDetail.class, VendorParameterConstants.SSN_ALLOWED_OWNERSHIP_TYPES) );
        OwnershipType newBo = (OwnershipType)getNewBo();
        OwnershipType oldBo = (OwnershipType) getOldBo();

        if ((feinParameterValues.contains(newBo.getVendorOwnershipCode()) || ssnParameterValues.contains(newBo.getVendorOwnershipCode())) && ! newBo.isActive() && oldBo.isActive()) {
            success = false;
            final BusinessObjectEntry ownershipTypeEntry = SpringContext.getBean(DataDictionaryService.class).getDataDictionary().getBusinessObjectEntry(OwnershipType.class.getName());
            final String documentLabel = ownershipTypeEntry == null ? DEFAULT_OWNERSHIP_TYPE_LABEL : ownershipTypeEntry.getObjectLabel();
            putGlobalError(KFSKeyConstants.ERROR_CANNOT_INACTIVATE_USED_IN_SYSTEM_PARAMETERS, documentLabel);
        }
        return success;
    }
}
