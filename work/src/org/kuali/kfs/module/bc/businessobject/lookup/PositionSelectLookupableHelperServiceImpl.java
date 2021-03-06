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
package org.kuali.kfs.module.bc.businessobject.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.kuali.kfs.module.bc.BCConstants;
import org.kuali.kfs.module.bc.BCPropertyConstants;
import org.kuali.kfs.module.bc.businessobject.BudgetConstructionPositionSelect;
import org.kuali.kfs.sys.KFSConstants;
import org.kuali.kfs.sys.KFSPropertyConstants;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.UrlFactory;

/**
 * Lookupable helper service implementation for the position select screen.
 */
public class PositionSelectLookupableHelperServiceImpl extends SelectLookupableHelperServiceImpl {

    /**
     * @see org.kuali.rice.kns.lookup.LookupableHelperService#getCustomActionUrls(org.kuali.rice.krad.bo.BusinessObject, java.util.List, java.util.List pkNames)
     */
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
        BudgetConstructionPositionSelect positionSelect = (BudgetConstructionPositionSelect) businessObject;

        Properties parameters = new Properties();
        parameters.put(KFSConstants.DISPATCH_REQUEST_PARAMETER, BCConstants.POSITION_SALARY_SETTING_METHOD);

        parameters.put(KFSPropertyConstants.UNIVERSITY_FISCAL_YEAR, positionSelect.getUniversityFiscalYear().toString());
        parameters.put(BCPropertyConstants.POSITION_NUMBER, positionSelect.getPositionNumber());
        parameters.put(BCPropertyConstants.SINGLE_ACCOUNT_MODE, "false");
        parameters.put(BCPropertyConstants.ADD_LINE, "false");

        String href = UrlFactory.parameterizeUrl(BCConstants.POSITION_SALARY_SETTING_ACTION, parameters);
        List<HtmlData> anchorHtmlDataList = new ArrayList<HtmlData>();
        AnchorHtmlData anchorHtmlData = new AnchorHtmlData(href, BCConstants.POSITION_SALARY_SETTING_METHOD, "Posn Salset");
        anchorHtmlData.setTarget(BCConstants.SECOND_WINDOW_TARGET_NAME);
        anchorHtmlDataList.add(anchorHtmlData);

        return anchorHtmlDataList;
    }
}
