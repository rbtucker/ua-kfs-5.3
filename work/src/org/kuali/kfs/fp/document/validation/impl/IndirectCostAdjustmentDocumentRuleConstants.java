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
package org.kuali.kfs.fp.document.validation.impl;

/**
 * Holds constants for <code>{@link org.kuali.kfs.fp.document.IndirectCostAdjustmentDocument}</code> business rules.
 */
public interface IndirectCostAdjustmentDocumentRuleConstants {
    public static final String INDIRECT_COST_ADJUSTMENT_DOCUMENT_SECURITY_GROUPING = "Kuali.FinancialTransactionProcessing.IndirectCostAdjustmentDocument";

    public static final String RESTRICTED_SUB_TYPE_GROUP_CODES = "OBJECT_SUB_TYPES";
    public static final String RESTRICTED_OBJECT_TYPE_CODES = "OBJECT_TYPES";
    public static final String GRANT_OBJECT_CODE = "GRANT_OBJECT_CODE";
    public static final String RECEIPT_OBJECT_CODE = "RECEIPT_OBJECT_CODE";
}
