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
package org.kuali.kfs.module.ld.businessobject.inquiry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.kfs.gl.Constant;
import org.kuali.kfs.module.ld.businessobject.LedgerEntry;
import org.kuali.kfs.sys.KFSConstants;
import org.kuali.kfs.sys.KFSPropertyConstants;

/**
 * Service implementation of LedgerBalanceInquirable. This class is used to generate the URL for the user-defined attributes for the
 * Ledger Balance screen. It is entended the KualiInquirableImpl class, so it covers both the default implementation and customized
 * implemetnation.
 */
public class LedgerBalanceInquirableImpl extends AbstractLaborInquirableImpl {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LedgerBalanceInquirableImpl.class);

    /**
     * @see org.kuali.kfs.gl.businessobject.inquiry.AbstractGeneralLedgerInquirableImpl#buildUserDefinedAttributeKeyList()
     */
    protected List buildUserDefinedAttributeKeyList() {
        List keys = new ArrayList();

        keys.add(KFSPropertyConstants.UNIVERSITY_FISCAL_YEAR);
        keys.add(KFSPropertyConstants.ACCOUNT_NUMBER);
        keys.add(KFSPropertyConstants.CHART_OF_ACCOUNTS_CODE);
        keys.add(KFSPropertyConstants.FINANCIAL_BALANCE_TYPE_CODE);
        keys.add(KFSPropertyConstants.SUB_ACCOUNT_NUMBER);
        keys.add(KFSPropertyConstants.FINANCIAL_OBJECT_CODE);
        keys.add(KFSPropertyConstants.FINANCIAL_SUB_OBJECT_CODE);
        keys.add(KFSPropertyConstants.FINANCIAL_OBJECT_TYPE_CODE);
        keys.add(KFSPropertyConstants.EMPLID);
        keys.add(KFSPropertyConstants.POSITION_NUMBER);
        keys.add(Constant.PENDING_ENTRY_OPTION);

        return keys;
    }

    /**
     * @see org.kuali.kfs.gl.businessobject.inquiry.AbstractGeneralLedgerInquirableImpl#getUserDefinedAttributeMap()
     */
    protected Map getUserDefinedAttributeMap() {
        Map userDefinedAttributeMap = new HashMap();

        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH1_AMOUNT, KFSConstants.MONTH1);
        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH2_AMOUNT, KFSConstants.MONTH2);
        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH3_AMOUNT, KFSConstants.MONTH3);

        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH4_AMOUNT, KFSConstants.MONTH4);
        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH5_AMOUNT, KFSConstants.MONTH5);
        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH6_AMOUNT, KFSConstants.MONTH6);

        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH7_AMOUNT, KFSConstants.MONTH7);
        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH8_AMOUNT, KFSConstants.MONTH8);
        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH9_AMOUNT, KFSConstants.MONTH9);

        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH10_AMOUNT, KFSConstants.MONTH10);
        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH11_AMOUNT, KFSConstants.MONTH11);
        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH12_AMOUNT, KFSConstants.MONTH12);
        userDefinedAttributeMap.put(KFSPropertyConstants.MONTH13_AMOUNT, KFSConstants.MONTH13);

        userDefinedAttributeMap.put(KFSPropertyConstants.FINANCIAL_BEGINNING_BALANCE_LINE_AMOUNT, KFSConstants.PERIOD_CODE_BEGINNING_BALANCE);
        userDefinedAttributeMap.put(KFSPropertyConstants.CONTRACTS_GRANTS_BEGINNING_BALANCE_AMOUNT, KFSConstants.PERIOD_CODE_CG_BEGINNING_BALANCE);

        return userDefinedAttributeMap;
    }

    /**
     * @see org.kuali.kfs.gl.businessobject.inquiry.AbstractGeneralLedgerInquirableImpl#getAttributeName(java.lang.String)
     */
    protected String getAttributeName(String attributeName) {
        return attributeName;
    }

    /**
     * @see org.kuali.kfs.gl.businessobject.inquiry.AbstractGeneralLedgerInquirableImpl#getKeyValue(java.lang.String, java.lang.Object)
     */
    protected Object getKeyValue(String keyName, Object keyValue) {
        if (isExclusiveField(keyName, keyValue)) {
            keyValue = Constant.EMPTY_STRING;
        }
        return keyValue;
    }

    /**
     * @see org.kuali.kfs.gl.businessobject.inquiry.AbstractGeneralLedgerInquirableImpl#getKeyName(java.lang.String)
     */
    protected String getKeyName(String keyName) {
        return keyName;
    }

    /**
     * @see org.kuali.kfs.gl.businessobject.inquiry.AbstractGeneralLedgerInquirableImpl#getLookupableImplAttributeName()
     */
    protected String getLookupableImplAttributeName() {
        return null;
    }

    /**
     * @see org.kuali.kfs.gl.businessobject.inquiry.AbstractGeneralLedgerInquirableImpl#getBaseUrl()
     */
    protected String getBaseUrl() {
        return KFSConstants.GL_MODIFIED_INQUIRY_ACTION;
    }

    /**
     * @see org.kuali.kfs.gl.businessobject.inquiry.AbstractGeneralLedgerInquirableImpl#getInquiryBusinessObjectClass(String)
     */
    protected Class getInquiryBusinessObjectClass(String attributeName) {
        return LedgerEntry.class;
    }

    /**
     * @see org.kuali.kfs.gl.businessobject.inquiry.AbstractGeneralLedgerInquirableImpl#addMoreParameters(java.util.Properties, java.lang.String)
     */
    protected void addMoreParameters(Properties parameter, String attributeName) {
        String periodCode = (String) getUserDefinedAttributeMap().get(attributeName);
        parameter.put(KFSConstants.UNIVERSITY_FISCAL_PERIOD_CODE_PROPERTY_NAME, periodCode);
    }
}
