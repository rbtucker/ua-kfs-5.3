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

package org.kuali.kfs.vnd.businessobject;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krad.datadictionary.AttributeSecurity;
import org.kuali.rice.krad.service.DataDictionaryService;
import org.kuali.rice.krad.service.KualiModuleService;
import org.kuali.rice.krad.service.ModuleService;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.location.api.LocationConstants;
import org.kuali.rice.location.framework.country.CountryEbo;

/**
 * Contains information specific to a parent Vendor, which may be shared by its division Vendors if it has any. Contained by a
 * <code>VendorDetail</code>.
 *
 * @see org.kuali.kfs.vnd.businessobject.VendorDetail
 */
public class VendorHeader extends PersistableBusinessObjectBase {
    private static Logger LOG = Logger.getLogger(VendorHeader.class);

    private Integer vendorHeaderGeneratedIdentifier;
    private String vendorTypeCode;
    private String vendorTaxNumber;
    private String vendorTaxTypeCode;
    private String vendorOwnershipCode;
    private String vendorOwnershipCategoryCode;
    private Date vendorFederalWithholdingTaxBeginningDate;
    private Date vendorFederalWithholdingTaxEndDate;
    private Boolean vendorW9ReceivedIndicator;
    private Boolean vendorW8BenReceivedIndicator;
    private Boolean vendorDebarredIndicator;
    private Boolean vendorForeignIndicator;

    private String vendorW8TypeCode;
    private Date vendorW8SignedDate;
    private Date vendorW9SignedDate;
    private String vendorCorpCitizenCode;
    private String vendorForeignTaxId;
    private String vendorGIIN;
    private Date vendorDOB;
    private String vendorChapter3StatusCode;
    private String vendorChapter4StatusCode;

    private VendorType vendorType;
    private OwnershipType vendorOwnership;
    private OwnershipCategory vendorOwnershipCategory;
    private W8Type w8Type;
    private Chapter3Status chapter3Status;
    private Chapter4Status chapter4Status;
    private List<VendorSupplierDiversity> vendorSupplierDiversities;
    private List<VendorTaxChange> vendorTaxChanges;
    protected CountryEbo vendorCountry;

    /**
     * Default constructor.
     */
    public VendorHeader() {
        vendorSupplierDiversities = new ArrayList<VendorSupplierDiversity>();
    }

    public Integer getVendorHeaderGeneratedIdentifier() {

        return vendorHeaderGeneratedIdentifier;
    }

    public void setVendorHeaderGeneratedIdentifier(Integer vendorHeaderGeneratedIdentifier) {
        this.vendorHeaderGeneratedIdentifier = vendorHeaderGeneratedIdentifier;
    }

    public String getVendorTypeCode() {

        return vendorTypeCode;
    }

    public void setVendorTypeCode(String vendorTypeCode) {
        this.vendorTypeCode = vendorTypeCode;
    }

    public String getVendorTaxNumber() {

        return vendorTaxNumber;
    }

    public void setVendorTaxNumber(String vendorTaxNumber) {
        this.vendorTaxNumber = vendorTaxNumber;
    }

    public String getVendorTaxTypeCode() {

        return vendorTaxTypeCode;
    }

    public void setVendorTaxTypeCode(String vendorTaxTypeCode) {
        this.vendorTaxTypeCode = vendorTaxTypeCode;
    }

    public String getVendorOwnershipCode() {

        return vendorOwnershipCode;
    }

    public void setVendorOwnershipCode(String vendorOwnershipCode) {
        this.vendorOwnershipCode = vendorOwnershipCode;
    }

    public String getVendorOwnershipCategoryCode() {

        return vendorOwnershipCategoryCode;
    }

    public void setVendorOwnershipCategoryCode(String vendorOwnershipCategoryCode) {
        this.vendorOwnershipCategoryCode = vendorOwnershipCategoryCode;
    }

    public Date getVendorFederalWithholdingTaxBeginningDate() {

        return vendorFederalWithholdingTaxBeginningDate;
    }

    public void setVendorFederalWithholdingTaxBeginningDate(Date vendorFederalWithholdingTaxBeginningDate) {
        this.vendorFederalWithholdingTaxBeginningDate = vendorFederalWithholdingTaxBeginningDate;
    }

    public Date getVendorFederalWithholdingTaxEndDate() {

        return vendorFederalWithholdingTaxEndDate;
    }

    public void setVendorFederalWithholdingTaxEndDate(Date vendorFederalWithholdingTaxEndDate) {
        this.vendorFederalWithholdingTaxEndDate = vendorFederalWithholdingTaxEndDate;
    }

    public Boolean getVendorW9ReceivedIndicator() {

        return vendorW9ReceivedIndicator;
    }

    public void setVendorW9ReceivedIndicator(Boolean vendorW9ReceivedIndicator) {
        this.vendorW9ReceivedIndicator = vendorW9ReceivedIndicator;
    }

    public Boolean getVendorW8BenReceivedIndicator() {

        return vendorW8BenReceivedIndicator;
    }

    public void setVendorW8BenReceivedIndicator(Boolean vendorW8BenReceivedIndicator) {
        this.vendorW8BenReceivedIndicator = vendorW8BenReceivedIndicator;
    }

    public VendorType getVendorType() {
        // refresh because proxy doesn't work properly and vendor type sometimes is null
        if (vendorType == null) {
            this.refreshReferenceObject("vendorType");
        }
        return vendorType;
    }

    /**
     * Sets the vendorType attribute.
     *
     * @param vendorType The vendorType to set.
     * @deprecated
     */
    @Deprecated
    public void setVendorType(VendorType vendorType) {
        this.vendorType = vendorType;
    }

    public OwnershipType getVendorOwnership() {

        return vendorOwnership;
    }

    /**
     * Sets the vendorOwnership attribute.
     *
     * @param vendorOwnership The vendorOwnership to set.
     * @deprecated
     */
    @Deprecated
    public void setVendorOwnership(OwnershipType vendorOwnership) {
        this.vendorOwnership = vendorOwnership;
    }

    public OwnershipCategory getVendorOwnershipCategory() {

        return vendorOwnershipCategory;
    }

    /**
     * Sets the vendorOwnershipCategory attribute.
     *
     * @param vendorOwnershipCategory The vendorOwnershipCategory to set.
     * @deprecated
     */
    @Deprecated
    public void setVendorOwnershipCategory(OwnershipCategory vendorOwnershipCategory) {
        this.vendorOwnershipCategory = vendorOwnershipCategory;
    }

    public Boolean getVendorDebarredIndicator() {

        return vendorDebarredIndicator;
    }

    /**
     * Sets the vendorDebarredIndicator attribute value.
     *
     * @param vendorDebarredIndicator The vendorDebarredIndicator to set.
     */
    public void setVendorDebarredIndicator(Boolean vendorDebarredIndicator) {
        this.vendorDebarredIndicator = vendorDebarredIndicator;
    }

    public Boolean getVendorForeignIndicator() {

        return vendorForeignIndicator;
    }

    /**
     * Sets the vendorForeignIndicator attribute value.
     *
     * @param vendorForeignIndicator The vendorForeignIndicator to set.
     */
    public void setVendorForeignIndicator(Boolean vendorForeignIndicator) {
        this.vendorForeignIndicator = vendorForeignIndicator;
    }

    public List<VendorSupplierDiversity> getVendorSupplierDiversities() {

        return vendorSupplierDiversities;
    }

    public void setVendorSupplierDiversities(List<VendorSupplierDiversity> vendorSupplierDiversities) {
        this.vendorSupplierDiversities = vendorSupplierDiversities;
    }

    /**
     * Gets the vendorW8SignedDate attribute.
     *
     * @return Returns the vendorW8SignedDate
     */

    public Date getVendorW8SignedDate() {
        return vendorW8SignedDate;
    }

    /**
     * Sets the vendorW8SignedDate attribute.
     *
     * @param vendorW8SignedDate The vendorW8SignedDate to set.
     */
    public void setVendorW8SignedDate(Date vendorW8SignedDate) {
        this.vendorW8SignedDate = vendorW8SignedDate;
    }

    /**
     * Gets the vendorW9SignedDate attribute.
     *
     * @return Returns the vendorW9SignedDate
     */

    public Date getVendorW9SignedDate() {
        return vendorW9SignedDate;
    }

    /**
     * Sets the vendorW9SignedDate attribute.
     *
     * @param vendorW9SignedDate The vendorW9SignedDate to set.
     */
    public void setVendorW9SignedDate(Date vendorW9SignedDate) {
        this.vendorW9SignedDate = vendorW9SignedDate;
    }

    /**
     * Gets the vendorCorpCitizenCode attribute.
     *
     * @return Returns the vendorCorpCitizenCode
     */


    public String getVendorCorpCitizenCode() {
        return vendorCorpCitizenCode;
    }

    /**
     * Gets the vendorW8TypeCode attribute.
     *
     * @return Returns the vendorW8TypeCode
     */

    public String getVendorW8TypeCode() {
        return vendorW8TypeCode;
    }

    /**
     * Sets the vendorW8TypeCode attribute.
     *
     * @param vendorW8TypeCode The vendorW8TypeCode to set.
     */
    public void setVendorW8TypeCode(String vendorW8TypeCode) {
        this.vendorW8TypeCode = vendorW8TypeCode;
    }

    /**
     * Sets the vendorCorpCitizenCode attribute.
     *
     * @param vendorCorpCitizenCode The vendorCorpCitizenCode to set.
     */
    public void setVendorCorpCitizenCode(String vendorCorpCitizenCode) {
        this.vendorCorpCitizenCode = vendorCorpCitizenCode;
    }

    /**
     * Gets the vendorForeignTaxId attribute.
     *
     * @return Returns the vendorForeignTaxId
     */

    public String getVendorForeignTaxId() {
        return vendorForeignTaxId;
    }

    /**
     * Sets the vendorForeignTaxId attribute.
     *
     * @param vendorForeignTaxId The vendorForeignTaxId to set.
     */
    public void setVendorForeignTaxId(String vendorForeignTaxId) {
        this.vendorForeignTaxId = vendorForeignTaxId;
    }

    /**
     * Gets the vendorGIIN attribute.
     *
     * @return Returns the vendorGIIN
     */

    public String getVendorGIIN() {
        return vendorGIIN;
    }

    /**
     * Sets the vendorGIIN attribute.
     *
     * @param vendorGIIN The vendorGIIN to set.
     */
    public void setVendorGIIN(String vendorGIIN) {
        this.vendorGIIN = vendorGIIN;
    }

    /**
     * Gets the vendorDOB attribute.
     *
     * @return Returns the vendorDOB
     */

    public Date getVendorDOB() {
        return vendorDOB;
    }

    /**
     * Sets the vendorDOB attribute.
     *
     * @param vendorDOB The vendorDOB to set.
     */
    public void setVendorDOB(Date vendorDOB) {
        this.vendorDOB = vendorDOB;
    }

    /**
     * Gets the vendorChapter3StatusCode attribute.
     *
     * @return Returns the vendorChapter3StatusCode
     */

    public String getVendorChapter3StatusCode() {
        return vendorChapter3StatusCode;
    }

    /**
     * Sets the vendorChapter3StatusCode attribute.
     *
     * @param vendorChapter3StatusCode The vendorChapter3StatusCode to set.
     */
    public void setVendorChapter3StatusCode(String vendorChapter3StatusCode) {
        this.vendorChapter3StatusCode = vendorChapter3StatusCode;
    }

    /**
     * Gets the vendorChapter4StatusCode attribute.
     *
     * @return Returns the vendorChapter4StatusCode
     */

    public String getVendorChapter4StatusCode() {
        return vendorChapter4StatusCode;
    }

    /**
     * Sets the vendorChapter4StatusCode attribute.
     *
     * @param vendorChapter4StatusCode The vendorChapter4StatusCode to set.
     */
    public void setVendorChapter4StatusCode(String vendorChapter4StatusCode) {
        this.vendorChapter4StatusCode = vendorChapter4StatusCode;
    }

    /**
     * Gets the w8Type attribute.
     *
     * @return Returns the w8Type
     */

    public W8Type getW8Type() {
        return w8Type;
    }

    /**
     * Sets the w8Type attribute.
     *
     * @param w8Type The w8Type to set.
     */
    public void setW8Type(W8Type w8Type) {
        this.w8Type = w8Type;
    }

    /**
     * Gets the chapter3Status attribute.
     *
     * @return Returns the chapter3Status
     */

    public Chapter3Status getChapter3Status() {
        return chapter3Status;
    }

    /**
     * Sets the chapter3Status attribute.
     *
     * @param chapter3Status The chapter3Status to set.
     */
    public void setChapter3Status(Chapter3Status chapter3Status) {
        this.chapter3Status = chapter3Status;
    }

    /**
     * Gets the chapter4Status attribute.
     *
     * @return Returns the chapter4Status
     */

    public Chapter4Status getChapter4Status() {
        return chapter4Status;
    }

    /**
     * Sets the chapter4Status attribute.
     *
     * @param chapter4Status The chapter4Status to set.
     */
    public void setChapter4Status(Chapter4Status chapter4Status) {
        this.chapter4Status = chapter4Status;
    }

    public CountryEbo getVendorCountry() {
        if ( StringUtils.isBlank(vendorCorpCitizenCode) ) {
            vendorCountry = null;
        } else {
            if ( vendorCountry == null || !StringUtils.equals( vendorCountry.getCode(),vendorCorpCitizenCode) ) {
                ModuleService moduleService = SpringContext.getBean(KualiModuleService.class).getResponsibleModuleService(CountryEbo.class);
                if ( moduleService != null ) {
                    Map<String,Object> keys = new HashMap<String, Object>(1);
                    keys.put(LocationConstants.PrimaryKeyConstants.CODE, vendorCorpCitizenCode);
                    vendorCountry = moduleService.getExternalizableBusinessObject(CountryEbo.class, keys);
                } else {
                    throw new RuntimeException( "CONFIGURATION ERROR: No responsible module found for EBO class.  Unable to proceed." );
                }
            }
        }
        return vendorCountry;
    }

    /**
     * Sets the vendorCountry attribute.
     *
     * @param vendorCountry The vendorCountry to set.
     * @deprecated
     */
    @Deprecated
    public void setVendorCountry(CountryEbo vendorCountry) {
        this.vendorCountry = vendorCountry;
    }

    /**
     * Used by the Spring Framework to correctly retrieve the vendor supplier diversities as a single
     * attribute. The vendorSupplierDiversities is a collection of diversities, and without this method,
     * there was no way to get a single attribute for it.
     *
     * @return the vendor supplier diversities as a single attribute
     */
    public String getVendorSupplierDiversitiesAsString() {
        StringBuilder sb = new StringBuilder("vendorSupplierDiversities=[");

        boolean first = true;
        for (VendorSupplierDiversity vsd : vendorSupplierDiversities) {
            if(vsd.isActive()){
                if (!first) {
                    sb.append(", ");
                } else {
                    first = false;
                }
                sb.append(vsd.getVendorSupplierDiversity().getVendorSupplierDiversityDescription().toString());
            }
        }
        sb.append(']');

        return sb.toString();
    }

    public List<VendorTaxChange> getVendorTaxChanges() {

        return vendorTaxChanges;
    }

    public void setVendorTaxChanges(List<VendorTaxChange> vendorTaxChanges) {
        this.vendorTaxChanges = vendorTaxChanges;
    }

    /**
     * This method is a predicate to test equality of all the persisted attributes of an instance of this class, including member
     * collections. This is used to determine whether to route
     *
     * @param vh Another VendorHeader object
     * @return True if all non-derived attributes of the given object are equal to this one's
     */
    public boolean isEqualForRouting(VendorHeader vh) {
        LOG.debug("Entering isEqualForRouting.");
        return new EqualsBuilder()
                .append(getVendorTypeCode(), vh.getVendorTypeCode())
                .append(getVendorTaxNumber(), vh.getVendorTaxNumber())
                .append(getVendorOwnershipCode(), vh.getVendorOwnershipCode())
                .append(getVendorOwnershipCategoryCode(), vh.getVendorOwnershipCategoryCode())
                .append(getVendorFederalWithholdingTaxBeginningDate(), vh.getVendorFederalWithholdingTaxBeginningDate())
                .append(getVendorFederalWithholdingTaxEndDate(), vh.getVendorFederalWithholdingTaxEndDate())
                .append(getVendorW9ReceivedIndicator(), vh.getVendorW9ReceivedIndicator())
                .append(getVendorW8BenReceivedIndicator(), vh.getVendorW8BenReceivedIndicator())
                .append(getVendorDebarredIndicator(), vh.getVendorDebarredIndicator())
                .append(getVendorForeignIndicator(), vh.getVendorForeignIndicator())
                .isEquals();
    }

    @Override
    public String toString() {
        class VendorHeaderToStringBuilder extends ReflectionToStringBuilder {
            private VendorHeaderToStringBuilder(Object object) {
                super(object);
            }

            @Override
            public boolean accept(Field field) {
                if (BusinessObject.class.isAssignableFrom(field.getType())) {
                    return false;
                }

                DataDictionaryService dataDictionaryService = SpringContext.getBean(DataDictionaryService.class);
                AttributeSecurity attributeSecurity = dataDictionaryService.getAttributeSecurity(VendorHeader.class.getName(), field.getName());
                if (ObjectUtils.isNotNull(attributeSecurity)
                                && (attributeSecurity.isHide() || attributeSecurity.isMask() || attributeSecurity.isPartialMask())) {
                    return false;
                }

                return super.accept(field);
            }
        };
        ReflectionToStringBuilder toStringBuilder = new VendorHeaderToStringBuilder(this);
        return toStringBuilder.toString();
    }

}
