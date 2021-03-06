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
package org.kuali.kfs.module.purap.fixture;

import java.math.BigDecimal;

import org.kuali.kfs.module.purap.businessobject.ElectronicInvoiceRejectItem;
import org.kuali.kfs.module.purap.document.ElectronicInvoiceRejectDocument;

public enum ElectronicInvoiceRejectItemFixture {

    EIRI_BASIC(
            1, // invoiceItemLineNumber;
            new BigDecimal(1), // invoiceItemQuantity;
            "BG", // invoiceItemUnitOfMeasureCode;
            "1212", // invoiceItemCatalogNumber;
            
            "USD", // invoiceItemUnitPriceCurrencyCode;
            "USD", // invoiceItemSubTotalCurrencyCode;
            "USD", // invoiceItemSpecialHandlingCurrencyCode;
            "USD", // invoiceItemShippingCurrencyCode;
            "Freight", // invoiceItemShippingDescription;
            "USD", // invoiceItemTaxCurrencyCode;
            "AZ State Tax", // invoiceItemTaxDescription;
            "USD", // invoiceItemGrossCurrencyCode;
            "USD", // invoiceItemDiscountCurrencyCode;
            "USD", // invoiceItemNetCurrencyCode;
            
            new BigDecimal(1.00), // invoiceItemUnitPrice;
            new BigDecimal(1.00), // invoiceItemSubTotalAmount;
            new BigDecimal(1.00), // invoiceItemSpecialHandlingAmount;
            new BigDecimal(1.00), // invoiceItemShippingAmount;
            new BigDecimal(1.00), // invoiceItemTaxAmount;
            new BigDecimal(1.00), // invoiceItemGrossAmount;
            new BigDecimal(1.00), // invoiceItemDiscountAmount;
            new BigDecimal(1.00), // invoiceItemNetAmount;
            
            1, // invoiceReferenceItemLineNumber;
            null, // invoiceReferenceItemSerialNumber;
            null, // invoiceReferenceItemSupplierPartIdentifier;
            null, // invoiceReferenceItemSupplierPartAuxiliaryIdentifier;
            "Test Description", // invoiceReferenceItemDescription;
            null, // invoiceReferenceItemManufacturerPartIdentifier;
            null, // invoiceReferenceItemManufacturerName;
            null, // invoiceReferenceItemCountryCode;
            null // invoiceReferenceItemCountryName;
     ), ;

    private Integer invoiceItemLineNumber;
    private BigDecimal invoiceItemQuantity;
    private String invoiceItemUnitOfMeasureCode;
    private String invoiceItemCatalogNumber;
    
    private String invoiceItemUnitPriceCurrencyCode;
    private String invoiceItemSubTotalCurrencyCode;
    private String invoiceItemSpecialHandlingCurrencyCode;
    private String invoiceItemShippingCurrencyCode;
    private String invoiceItemShippingDescription;
    private String invoiceItemTaxCurrencyCode;
    private String invoiceItemTaxDescription;
    private String invoiceItemGrossCurrencyCode;
    private String invoiceItemDiscountCurrencyCode;
    private String invoiceItemNetCurrencyCode;
    
    private BigDecimal invoiceItemUnitPrice;
    private BigDecimal invoiceItemSubTotalAmount;
    private BigDecimal invoiceItemSpecialHandlingAmount;
    private BigDecimal invoiceItemShippingAmount;
    private BigDecimal invoiceItemTaxAmount;
    private BigDecimal invoiceItemGrossAmount;
    private BigDecimal invoiceItemDiscountAmount;
    private BigDecimal invoiceItemNetAmount;
    
    private Integer invoiceReferenceItemLineNumber;
    private String invoiceReferenceItemSerialNumber;
    private String invoiceReferenceItemSupplierPartIdentifier;
    private String invoiceReferenceItemSupplierPartAuxiliaryIdentifier;
    private String invoiceReferenceItemDescription;
    private String invoiceReferenceItemManufacturerPartIdentifier;
    private String invoiceReferenceItemManufacturerName;
    private String invoiceReferenceItemCountryCode;
    private String invoiceReferenceItemCountryName;

    private ElectronicInvoiceRejectItemFixture(Integer invoiceItemLineNumber,
            BigDecimal invoiceItemQuantity, String invoiceItemUnitOfMeasureCode, String invoiceItemCatalogNumber, String invoiceItemUnitPriceCurrencyCode,
            String invoiceItemSubTotalCurrencyCode, String invoiceItemSpecialHandlingCurrencyCode, String invoiceItemShippingCurrencyCode,
            String invoiceItemShippingDescription, String invoiceItemTaxCurrencyCode, String invoiceItemTaxDescription, String invoiceItemGrossCurrencyCode,
            String invoiceItemDiscountCurrencyCode, String invoiceItemNetCurrencyCode, BigDecimal invoiceItemUnitPrice, BigDecimal invoiceItemSubTotalAmount, BigDecimal invoiceItemSpecialHandlingAmount, BigDecimal invoiceItemShippingAmount,
            BigDecimal invoiceItemTaxAmount, BigDecimal invoiceItemGrossAmount, BigDecimal invoiceItemDiscountAmount, BigDecimal invoiceItemNetAmount,
            Integer invoiceReferenceItemLineNumber, String invoiceReferenceItemSerialNumber, String invoiceReferenceItemSupplierPartIdentifier,
            String invoiceReferenceItemSupplierPartAuxiliaryIdentifier, String invoiceReferenceItemDescription, String invoiceReferenceItemManufacturerPartIdentifier,
            String invoiceReferenceItemManufacturerName, String invoiceReferenceItemCountryCode, String invoiceReferenceItemCountryName) {

        this.invoiceItemLineNumber = invoiceItemLineNumber;
        this.invoiceItemQuantity = invoiceItemQuantity;
        this.invoiceItemUnitOfMeasureCode = invoiceItemUnitOfMeasureCode;
        this.invoiceItemCatalogNumber = invoiceItemCatalogNumber;
        
        this.invoiceItemUnitPriceCurrencyCode = invoiceItemUnitPriceCurrencyCode;
        this.invoiceItemSubTotalCurrencyCode = invoiceItemSubTotalCurrencyCode;
        this.invoiceItemSpecialHandlingCurrencyCode = invoiceItemSpecialHandlingCurrencyCode;
        this.invoiceItemShippingCurrencyCode = invoiceItemShippingCurrencyCode;
        this.invoiceItemShippingDescription = invoiceItemShippingDescription;
        this.invoiceItemTaxCurrencyCode = invoiceItemTaxCurrencyCode;
        this.invoiceItemTaxDescription = invoiceItemTaxDescription;
        this.invoiceItemGrossCurrencyCode = invoiceItemGrossCurrencyCode;
        this.invoiceItemDiscountCurrencyCode = invoiceItemDiscountCurrencyCode;
        this.invoiceItemNetCurrencyCode = invoiceItemNetCurrencyCode;
        
        this.invoiceItemUnitPrice = invoiceItemUnitPrice;
        this.invoiceItemSubTotalAmount = invoiceItemSubTotalAmount;
        this.invoiceItemSpecialHandlingAmount = invoiceItemSpecialHandlingAmount;
        this.invoiceItemShippingAmount = invoiceItemShippingAmount;
        this.invoiceItemTaxAmount = invoiceItemTaxAmount;
        this.invoiceItemGrossAmount = invoiceItemGrossAmount;
        this.invoiceItemDiscountAmount = invoiceItemDiscountAmount;
        this.invoiceItemNetAmount = invoiceItemNetAmount;
        
        this.invoiceReferenceItemLineNumber = invoiceReferenceItemLineNumber;
        this.invoiceReferenceItemSerialNumber = invoiceReferenceItemSerialNumber;
        this.invoiceReferenceItemSupplierPartIdentifier = invoiceReferenceItemSupplierPartIdentifier;
        this.invoiceReferenceItemSupplierPartAuxiliaryIdentifier = invoiceReferenceItemSupplierPartAuxiliaryIdentifier;
        this.invoiceReferenceItemDescription = invoiceReferenceItemDescription;
        this.invoiceReferenceItemManufacturerPartIdentifier = invoiceReferenceItemManufacturerPartIdentifier;
        this.invoiceReferenceItemManufacturerName = invoiceReferenceItemManufacturerName;
        this.invoiceReferenceItemCountryCode = invoiceReferenceItemCountryCode;
        this.invoiceReferenceItemCountryName = invoiceReferenceItemCountryName;
    }

    /**
     * Creates a Reject Reason from this fixture and adds the item to the specified Document.
     * 
     * @param receivingLineDocument the specified Receiving Line Document.
     */
    public void addTo(ElectronicInvoiceRejectDocument rejectDocument) {
        ElectronicInvoiceRejectItem rejectItem = null;
        rejectItem = this.createElectronicInvoiceRejectItem();
        rejectItem.setElectronicInvoiceRejectDocument(rejectDocument);
        rejectItem.setPurapDocumentIdentifier(rejectDocument.getPurapDocumentIdentifier());
        rejectDocument.addRejectItem(rejectItem);
    }

    public ElectronicInvoiceRejectItem createElectronicInvoiceRejectItem() {
        ElectronicInvoiceRejectItem rejectItem = new ElectronicInvoiceRejectItem();

        rejectItem.setInvoiceItemLineNumber(invoiceItemLineNumber);
        rejectItem.setInvoiceItemQuantity(invoiceItemQuantity);
        rejectItem.setInvoiceItemUnitOfMeasureCode(invoiceItemUnitOfMeasureCode);
        rejectItem.setInvoiceItemCatalogNumber(invoiceItemCatalogNumber);
        
        rejectItem.setInvoiceItemUnitPriceCurrencyCode(invoiceItemUnitPriceCurrencyCode);
        rejectItem.setInvoiceItemSubTotalCurrencyCode(invoiceItemSubTotalCurrencyCode);
        rejectItem.setInvoiceItemSpecialHandlingCurrencyCode(invoiceItemSpecialHandlingCurrencyCode);
        rejectItem.setInvoiceItemShippingCurrencyCode(invoiceItemShippingCurrencyCode);
        rejectItem.setInvoiceItemShippingDescription(invoiceItemShippingDescription);
        rejectItem.setInvoiceItemTaxCurrencyCode(invoiceItemTaxCurrencyCode);
        rejectItem.setInvoiceItemTaxDescription(invoiceItemTaxDescription);
        rejectItem.setInvoiceItemGrossCurrencyCode(invoiceItemGrossCurrencyCode);
        rejectItem.setInvoiceItemDiscountCurrencyCode(invoiceItemDiscountCurrencyCode);
        rejectItem.setInvoiceItemNetCurrencyCode(invoiceItemNetCurrencyCode);
        
        rejectItem.setInvoiceItemUnitPrice(invoiceItemUnitPrice);
        rejectItem.setInvoiceItemSubTotalAmount(invoiceItemSubTotalAmount);
        rejectItem.setInvoiceItemSpecialHandlingAmount(invoiceItemSpecialHandlingAmount);
        rejectItem.setInvoiceItemShippingAmount(invoiceItemShippingAmount);
        rejectItem.setInvoiceItemTaxAmount(invoiceItemTaxAmount);
        rejectItem.setInvoiceItemGrossAmount(invoiceItemGrossAmount);
        rejectItem.setInvoiceItemDiscountAmount(invoiceItemDiscountAmount);
        rejectItem.setInvoiceItemNetAmount(invoiceItemNetAmount);
        
        rejectItem.setInvoiceReferenceItemLineNumber(invoiceReferenceItemLineNumber);
        rejectItem.setInvoiceReferenceItemSerialNumber(invoiceReferenceItemSerialNumber);
        rejectItem.setInvoiceReferenceItemSupplierPartIdentifier(invoiceReferenceItemSupplierPartIdentifier);
        rejectItem.setInvoiceReferenceItemSupplierPartAuxiliaryIdentifier(invoiceReferenceItemSupplierPartAuxiliaryIdentifier);
        rejectItem.setInvoiceReferenceItemDescription(invoiceReferenceItemDescription);
        rejectItem.setInvoiceReferenceItemManufacturerPartIdentifier(invoiceReferenceItemManufacturerPartIdentifier);
        rejectItem.setInvoiceReferenceItemManufacturerName(invoiceReferenceItemManufacturerName);
        rejectItem.setInvoiceReferenceItemCountryCode(invoiceReferenceItemCountryCode);
        rejectItem.setInvoiceReferenceItemCountryName(invoiceReferenceItemCountryName);

        return rejectItem;
    }

}
