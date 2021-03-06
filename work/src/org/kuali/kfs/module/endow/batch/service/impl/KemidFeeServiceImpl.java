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
package org.kuali.kfs.module.endow.batch.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kuali.kfs.module.endow.EndowParameterKeyConstants;
import org.kuali.kfs.module.endow.EndowPropertyConstants;
import org.kuali.kfs.module.endow.batch.service.KemidCorpusValueService;
import org.kuali.kfs.module.endow.batch.service.KemidFeeService;
import org.kuali.kfs.module.endow.businessobject.FeeMethod;
import org.kuali.kfs.module.endow.businessobject.KemidFee;
import org.kuali.kfs.module.endow.document.service.KEMService;
import org.kuali.kfs.sys.service.impl.KfsParameterConstants;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.ObjectUtils;

/**
 * This class is the service implementation for the KemidFeeService. This is the default, Kuali provided implementation.
 */
public class KemidFeeServiceImpl implements KemidFeeService {
    protected static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KemidFeeServiceImpl.class);

    private BusinessObjectService businessObjectService;
    protected KEMService kemService;
    protected ParameterService parameterService;
    protected KemidCorpusValueService kemidCorpusValueService;

    /**
     * @see org.kuali.kfs.module.endow.batch.service.KemidFeeService#getAllKemIdFee()
     */
    public Collection <KemidFee> getAllKemIdFee() {
        Collection <KemidFee> kemIdFee = new ArrayList();
        
        kemIdFee = businessObjectService.findAll(KemidFee.class);
        
        return kemIdFee;
    }
    
    /**
     * 
     */
    public boolean saveKemidFee(KemidFee kemidFee) {
        boolean saved = true;
        
        try {
            businessObjectService.save(kemidFee);
            return saved;
        } 
        catch (IllegalArgumentException iae) {
            return false;
        }
    }
    
    /**
     * @see org.kuali.kfs.module.endow.batch.KemidFeeService#KemidFeeService(String)
     */
    public Collection<KemidFee> getAllKemidForFeeMethodCode(String feeMethodCode) {
       Collection<KemidFee> kemId = new ArrayList(); 
       
       Map fieldValues = new HashMap();
       
       fieldValues.put(EndowPropertyConstants.KEMID_FEE_MTHD_CD, feeMethodCode);
       
       kemId = businessObjectService.findMatching(KemidFee.class, fieldValues);
       
       return kemId;
    }
    
    /**
     * @see org.kuali.kfs.module.endow.batch.KemidFeeService#ChargeFeeToKemid(KemidFee, boolean)
     */
    public boolean chargeFeeToKemid(FeeMethod feeMethod, KemidFee kemidFee) {
        boolean chargeFee = true;
        boolean corpusPercentToleranceGreaterThanZero = (feeMethod.getCorpusPctTolerance().isGreaterThan(new KualiDecimal("0")) ? true : false );
        
        if (kemidFee.getFeeStartDate().compareTo(kemService.getCurrentDate()) <= 0) {
            if (ObjectUtils.isNull(kemidFee.getFeeEndDate()) || 
               (kemidFee.getFeeEndDate().compareTo(kemService.getCurrentDate()) > 0)) {
                chargeFee = kemidCorpusValueService.canFeeBeChargedToKemid(kemidFee.getKemid(), feeMethod.getCorpusPctTolerance());
                return chargeFee;
            }
        }
        
        return chargeFee;
    }
    
    /**
     * Updates the END_KEMID_FEE_T table records by setting the amounts in WAIVE_FEE_YTD to zero
     * if current date is the first day of the institution's fiscal year
     * @see org.kuali.kfs.module.endow.document.service.KemidFeeService#updateWaiverFeeYearToDateTotals()
     */
    public boolean updateWaiverFeeYearToDateTotals() {
       LOG.debug("updateWaiverFeeYearToDateTotals() process started");
       
       boolean updated = true;
       
       if (!systemParametersForUpdateWaiverFeeAmounts()) {
           return false;
       }
       
       try {
           java.sql.Date firstDayAfterFiscalYear = kemService.getFirstDayAfterFiscalYearEndDayAndMonth();
           
           if (firstDayAfterFiscalYear.equals(kemService.getCurrentDate())) {
               //update END_KEMID_FEE_T:
               Collection <KemidFee> kemidFeeRecords = getAllKemIdFee();
                  
               for (KemidFee kemidFee : kemidFeeRecords) {
                    kemidFee.setTotalWaivedFees(KualiDecimal.ZERO);
                    businessObjectService.save(kemidFee);
               }
           }
           
           return updated;
       }
       catch (Exception exception) {
           return false;
       }
    }
    
    /**
     * This method checks if the System parameters have been set up for this batch job.
     * @result return true if the system parameters exist, else false
     */
    protected boolean systemParametersForUpdateWaiverFeeAmounts() {
        LOG.debug("systemParametersForUpdateWaiverFeeAmounts() started.");
        
        boolean systemParameterExists = true;
        
        // check to make sure the system parameter has been setup...
        if (!getParameterService().parameterExists(KfsParameterConstants.ENDOWMENT_BATCH.class, EndowParameterKeyConstants.FISCAL_YEAR_END_MONTH_AND_DAY)) {
          LOG.warn("FISCAL_YEAR_END_MONTH_AND_DAY System parameter does not exist in the parameters list.  The job can not continue without this parameter");
          return false;
        }
        
        return systemParameterExists;
    }
    
    /**
     * This method gets the businessObjectService.
     * 
     * @return businessObjectService
     */
    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    /**
     * This method sets the businessObjectService
     * 
     * @param businessObjectService
     */
    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    /**
     * Gets the kemService.
     * @return kemService
     */
    protected KEMService getKemService() {
        return kemService;
    }

    /**
     * Sets the kemService.
     * @param kemService
     */
    public void setKemService(KEMService kemService) {
        this.kemService = kemService;
    }

    /**
     * Gets the parameterService attribute.
     * @return Returns the parameterService.
     */    
    protected ParameterService getParameterService() {
        return parameterService;
    }

    /**
     * Sets the parameterService attribute value.
     * @param parameterService The parameterService to set.
     */    
    public void setParameterService(ParameterService parameterService) {
        this.parameterService = parameterService;
    }
    
    /**
     * Gets the kemidCorpusValueService attribute. 
     * @return Returns the kemidCorpusValueService.
     */
    public KemidCorpusValueService getKemidCorpusValueService() {
        return kemidCorpusValueService;
    }

    /**
     * Sets the kemidCorpusValueService attribute value.
     * @param kemidCorpusValueService The kemidCorpusValueService to set.
     */
    public void setKemidCorpusValueService(KemidCorpusValueService kemidCorpusValueService) {
        this.kemidCorpusValueService = kemidCorpusValueService;
    }
}
