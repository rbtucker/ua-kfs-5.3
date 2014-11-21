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
package org.kuali.kfs.module.ar.web.struts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.kfs.sys.KFSConstants;
import org.kuali.kfs.sys.businessobject.GeneralLedgerPendingEntry;
import org.kuali.kfs.sys.businessobject.lookup.LookupableSpringContext;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.rice.kns.lookup.Lookupable;
import org.kuali.rice.kns.service.BusinessObjectDictionaryService;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.kns.web.ui.Field;
import org.kuali.rice.kns.web.ui.Row;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.lookup.LookupUtils;


/**
 * This class is the action form for Customer Aging Reports.
 */
public class CustomerAgingReportForm extends LookupForm {
    private static final long serialVersionUID = 1L;

    private static final org.apache.commons.logging.Log LOG = org.apache.commons.logging.LogFactory.getLog(CustomerAgingReportForm.class);

    private String formKey;
    private String backLocation;
    private Map fields;
    private String lookupableImplServiceName;
    private String conversionFields;
    private Map fieldConversions;
    private String businessObjectClassName;
    private Lookupable lookupable;
    private Lookupable pendingEntryLookupable;
    private boolean hideReturnLink = false;

    private String total0to30;
    private String total31to60;
    private String total61to90;
    private String total91toSYSPR;
    private String totalSYSPRplus1orMore;


    /**
     * Picks out business object name from the request to get retrieve a lookupable and set properties.
     *
     * @see org.kuali.rice.kns.web.struts.form.LookupForm#populate(javax.servlet.http.HttpServletRequest)
     *
     * KRAD Conversion: Lookupable performs customization of the fields and check for additional fields.
     *
     * Data dictionary is used to retrieve the field properties.
     */
    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);

        try {
            if (StringUtils.isBlank(request.getParameter(KFSConstants.LOOKUPABLE_IMPL_ATTRIBUTE_NAME)) && StringUtils.isBlank(getLookupableImplServiceName())) {

                // get the business object class for the lookup
                String localBusinessObjectClassName = request.getParameter(KFSConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE);
                setBusinessObjectClassName(localBusinessObjectClassName);

                if (StringUtils.isBlank(localBusinessObjectClassName)) {
                    LOG.error("Business object class not passed to lookup.");
                    throw new RuntimeException("Business object class not passed to lookup.");
                }

                // call data dictionary service to get lookup impl for bo class
                String lookupImplID = SpringContext.getBean(BusinessObjectDictionaryService.class).getLookupableID(Class.forName(localBusinessObjectClassName));
                if (lookupImplID == null) {
                    lookupImplID = "lookupable";
                }

                setLookupableImplServiceName(lookupImplID);
            }
            setLookupable(LookupableSpringContext.getLookupable(getLookupableImplServiceName()));

            if (getLookupable() == null) {
                LOG.error("Lookup impl not found for lookup impl name " + getLookupableImplServiceName());
                throw new RuntimeException("Lookup impl not found for lookup impl name " + getLookupableImplServiceName());
            }

            if (request.getParameter(KFSConstants.LOOKUPABLE_IMPL_ATTRIBUTE_NAME) != null) {
                setLookupableImplServiceName(request.getParameter(KFSConstants.LOOKUPABLE_IMPL_ATTRIBUTE_NAME));
            }

            // check the doc form key is empty before setting so we don't override a restored lookup form
            if (request.getAttribute(KFSConstants.DOC_FORM_KEY) != null && StringUtils.isBlank(this.getFormKey())) {
                setFormKey((String) request.getAttribute(KFSConstants.DOC_FORM_KEY));
            }
            else if (request.getParameter(KFSConstants.DOC_FORM_KEY) != null && StringUtils.isBlank(this.getFormKey())) {
                setFormKey(request.getParameter(KFSConstants.DOC_FORM_KEY));
            }

            if (request.getParameter(KFSConstants.RETURN_LOCATION_PARAMETER) != null) {
                setBackLocation(request.getParameter(KFSConstants.RETURN_LOCATION_PARAMETER));
            }
            if (request.getParameter(KFSConstants.CONVERSION_FIELDS_PARAMETER) != null) {
                setConversionFields(request.getParameter(KFSConstants.CONVERSION_FIELDS_PARAMETER));
            }


            // init lookupable with bo class
            getLookupable().setBusinessObjectClass((Class<? extends BusinessObject>) Class.forName(getBusinessObjectClassName()));
            if (null != getPendingEntryLookupable()) {
                getPendingEntryLookupable().setBusinessObjectClass(GeneralLedgerPendingEntry.class);
            }

            Map fieldValues = new HashMap();
            Map formFields = getFields();
            Class boClass = Class.forName(getBusinessObjectClassName());
//            LOG.info("\n\n\n\nBusiness Object class " + getBusinessObjectClassName() + " is found\n\n\n\n");
            for (Iterator iter = getLookupable().getRows().iterator(); iter.hasNext();) {
                Row row = (Row) iter.next();

                for (Iterator iterator = row.getFields().iterator(); iterator.hasNext();) {
                    Field field = (Field) iterator.next();

                    // check whether form already has value for field
                    if (formFields != null && formFields.containsKey(field.getPropertyName())) {
                        field.setPropertyValue(formFields.get(field.getPropertyName()));
                    }

                    // override values with request
                    if (request.getParameter(field.getPropertyName()) != null) {
                        field.setPropertyValue(request.getParameter(field.getPropertyName()));
                    }

                    // force uppercase if necessary
                    field.setPropertyValue(LookupUtils.forceUppercase(boClass, field.getPropertyName(), field.getPropertyValue()));

                    fieldValues.put(field.getPropertyName(), field.getPropertyValue());
                }
            }
            if (getLookupable().checkForAdditionalFields(fieldValues)) {
                for (Iterator iter = getLookupable().getRows().iterator(); iter.hasNext();) {
                    Row row = (Row) iter.next();

                    for (Iterator iterator = row.getFields().iterator(); iterator.hasNext();) {
                        Field field = (Field) iterator.next();

                        // check whether form already has value for field
                        if (formFields != null && formFields.containsKey(field.getPropertyName())) {
                            field.setPropertyValue(formFields.get(field.getPropertyName()));
                            LOG.info("\n\n\n\n");
                            LOG.info("field " + field.toString() + " = " + field.getPropertyValue() + " ***\n\n");
                        }

                        // override values with request
                        if (request.getParameter(field.getPropertyName()) != null) {
                            field.setPropertyValue(request.getParameter(field.getPropertyName()));
                        }
                        fieldValues.put(field.getPropertyName(), field.getPropertyValue());
                    }
                }
            }
            fieldValues.put(KFSConstants.DOC_FORM_KEY, this.getFormKey());
            fieldValues.put(KFSConstants.BACK_LOCATION, this.getBackLocation());

            this.setFields(fieldValues);

            Map fieldConversionMap = new HashMap();
            if (StringUtils.isNotEmpty(this.getConversionFields())) {
                if (this.getConversionFields().indexOf(",") > 0) {
                    StringTokenizer token = new StringTokenizer(this.getConversionFields(), ",");
                    while (token.hasMoreTokens()) {
                        String element = token.nextToken();
                        fieldConversionMap.put(element.substring(0, element.indexOf(":")), element.substring(element.indexOf(":") + 1));
                    }
                }
                else {
                    fieldConversionMap.put(this.getConversionFields().substring(0, this.getConversionFields().indexOf(":")), this.getConversionFields().substring(this.getConversionFields().indexOf(":") + 1));
                }
            }
            setFieldConversions(fieldConversionMap);
            getLookupable().setFieldConversions(fieldConversionMap);
            if (null != getPendingEntryLookupable()) {
                getPendingEntryLookupable().setFieldConversions(fieldConversionMap);
            }
        }
        catch (ClassNotFoundException e) {
            LOG.error("Business Object class " + getBusinessObjectClassName() + " not found");
            throw new RuntimeException("Business Object class " + getBusinessObjectClassName() + " not found", e);
        }
    }

    /**
     * @return Returns the lookupableImplServiceName.
     */
    @Override
    public String getLookupableImplServiceName() {
        return lookupableImplServiceName;
    }

    /**
     * @param lookupableImplServiceName The lookupableImplServiceName to set.
     */
    @Override
    public void setLookupableImplServiceName(String lookupableImplServiceName) {
        this.lookupableImplServiceName = lookupableImplServiceName;
    }


    /**
     * @return Returns the backLocation.
     */
    @Override
    public String getBackLocation() {
        return backLocation;
    }

    /**
     * @param backLocation The backLocation to set.
     */
    @Override
    public void setBackLocation(String backLocation) {
        this.backLocation = backLocation;
    }

    /**
     * @return Returns the formKey.
     */
    @Override
    public String getFormKey() {
        return formKey;
    }

    /**
     * @param formKey The formKey to set.
     */
    @Override
    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    /**
     * @return Returns the fields.
     */
    @Override
    public Map getFields() {
        return fields;
    }

    /**
     * @param fields The fields to set.
     */
    @Override
    public void setFields(Map fields) {
        this.fields = fields;
    }


    /**
     * @return Returns the conversionFields.
     */
    @Override
    public String getConversionFields() {
        return conversionFields;
    }

    /**
     * @param conversionFields The conversionFields to set.
     */
    @Override
    public void setConversionFields(String conversionFields) {
        this.conversionFields = conversionFields;
    }

    /**
     * @return Returns the fieldConversions.
     */
    @Override
    public Map getFieldConversions() {
        return fieldConversions;
    }

    /**
     * @param fieldConversions The fieldConversions to set.
     */
    @Override
    public void setFieldConversions(Map fieldConversions) {
        this.fieldConversions = fieldConversions;
    }

    /**
     * @return Returns the businessObjectClassName.
     */
    @Override
    public String getBusinessObjectClassName() {
        return businessObjectClassName;
    }

    /**
     * @param businessObjectClassName The businessObjectClassName to set.
     */
    @Override
    public void setBusinessObjectClassName(String businessObjectClassName) {
        this.businessObjectClassName = businessObjectClassName;
    }


    /**
     * @return Returns the lookupable.
     */
    @Override
    public Lookupable getLookupable() {
        return lookupable;
    }


    /**
     * @param lookupable The lookupable to set.
     */
    @Override
    public void setLookupable(Lookupable lookupable) {
        this.lookupable = lookupable;
    }


    /**
     * @return Returns the hideReturnLink.
     */
    @Override
    public boolean isHideReturnLink() {
        return hideReturnLink;
    }


    /**
     * @param hideReturnLink The hideReturnLink to set.
     */
    @Override
    public void setHideReturnLink(boolean hideReturnLink) {
        this.hideReturnLink = hideReturnLink;
    }


    /**
     * @param pendingEntryLookupable
     */
    public void setPendingEntryLookupable(Lookupable pendingEntryLookupable) {
        this.pendingEntryLookupable = pendingEntryLookupable;
    }


    /**
     * @return Returns the pendingEntryLookupable.
     */
    public Lookupable getPendingEntryLookupable() {
        return this.pendingEntryLookupable;
    }

    /**
     * Gets the total0to30 attribute.
     *
     * @return Returns the total0to30.
     */
    public String getTotal0to30() {
        return total0to30;
    }

    /**
     * Sets the total0to30 attribute value.
     *
     * @param total0to30 The total0to30 to set.
     */
    public void setTotal0to30(String total0to30) {
        this.total0to30 = total0to30;
    }

    /**
     * Gets the total31to60 attribute.
     *
     * @return Returns the total31to60.
     */
    public String getTotal31to60() {
        return total31to60;
    }

    /**
     * Sets the total31to60 attribute value.
     *
     * @param total31to60 The total31to60 to set.
     */
    public void setTotal31to60(String total31to60) {
        this.total31to60 = total31to60;
    }

    /**
     * Gets the total61to90 attribute.
     *
     * @return Returns the total61to90.
     */
    public String getTotal61to90() {
        return total61to90;
    }

    /**
     * Sets the total61to90 attribute value.
     *
     * @param total61to90 The total61to90 to set.
     */
    public void setTotal61to90(String total61to90) {
        this.total61to90 = total61to90;
    }

    /**
     * Gets the total91toSYSPR attribute.
     *
     * @return Returns the total91toSYSPR.
     */
    public String getTotal91toSYSPR() {
        return total91toSYSPR;
    }

    /**
     * Sets the total91toSYSPR attribute value.
     *
     * @param total91toSYSPR The total91toSYSPR to set.
     */
    public void setTotal91toSYSPR(String total91toSYSPR) {
        this.total91toSYSPR = total91toSYSPR;
    }

    /**
     * Gets the totalSYSPRplus1orMore attribute.
     *
     * @return Returns the totalSYSPRplus1orMore.
     */
    public String getTotalSYSPRplus1orMore() {
        return totalSYSPRplus1orMore;
    }

    /**
     * Sets the totalSYSPRplus1orMore attribute value.
     *
     * @param totalSYSPRplus1orMore The totalSYSPRplus1orMore to set.
     */
    public void setTotalSYSPRplus1orMore(String totalSYSPRplus1orMore) {
        this.totalSYSPRplus1orMore = totalSYSPRplus1orMore;
    }
}