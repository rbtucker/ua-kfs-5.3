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
package org.kuali.kfs.sys.document.web;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.kuali.kfs.sys.KFSConstants;
import org.kuali.kfs.sys.KFSKeyConstants;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.kfs.sys.document.web.renderers.ActionsRenderer;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.kns.web.ui.Field;

/**
 * A field that can join tables and also be rendered, this represents a table cell
 * that displays the actions available on an accounting line
 */
public class AccountingLineViewActionsField extends FieldTableJoiningWithHeader {
    private String name = KFSConstants.AccountingLineViewStandardBlockNames.ACTION_BLOCK;

    /**
     * Returns the name of this actions field
     * @see org.kuali.kfs.sys.document.web.TableJoining#getName()
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of this actions field
     * @param name the name of this block
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * We are an action block.  For real, even
     * @see org.kuali.kfs.sys.document.web.FieldTableJoining#isActionBlock()
     */
    @Override
    public boolean isActionBlock() {
        return true;
    }

    /**
     * @see org.kuali.kfs.sys.document.web.FieldTableJoiningWithHeader#joinTable(java.util.List)
     */
    @Override
    public void joinTable(List<AccountingLineTableRow> rows) {
        // 1. add header cell
        AccountingLineTableCell headerCell = createHeaderLabelTableCell();
        rows.get(0).addCell(headerCell);

        // 2. add blank cell to make sure this cell shows up on the bottom line
        final int blankCellRowSpan = rows.size() - 2;
        if (blankCellRowSpan > 0) {
            AccountingLineTableCell blankCell = createBlankTableCell(blankCellRowSpan);
            rows.get(1).addCell(blankCell);
        }
        // 3. add field cell
        AccountingLineTableCell cell = createTableCell();
        rows.get((rows.size()-1)).addCell(cell);
    }
    
    /**
     * Builds a blank cell for the action so the actions always appear below that
     * @param rowSpan the row span of the blank cell
     * @return the blank row-spanning table cell
     */
    protected AccountingLineTableCell createBlankTableCell(int rowSpan) {
        AccountingLineTableCell blankCell = new AccountingLineTableCell();
        blankCell.setNeverEmpty(true);
        blankCell.setExtraStyle("border-bottom-style: none;");
        if (rowSpan > 1) {
            blankCell.setRowSpan(rowSpan);
        }
        return blankCell;
    }

    /**
     * @see org.kuali.kfs.sys.document.web.RenderableElement#renderElement(javax.servlet.jsp.PageContext, javax.servlet.jsp.tagext.Tag)
     */
    public void renderElement(PageContext pageContext, Tag parentTag, AccountingLineRenderingContext renderingContext) throws JspException {
        ActionsRenderer renderer = new ActionsRenderer();
        renderer.setActions(renderingContext.getActionsForLine());
        renderer.render(pageContext, parentTag);
        renderer.clear();
    }

    /**
     * @see org.kuali.kfs.sys.document.web.TableJoiningWithHeader#createHeaderLabel()
     */
    public HeaderLabel createHeaderLabel() {
        return new LiteralHeaderLabel(SpringContext.getBean(ConfigurationService.class).getPropertyValueAsString(KFSKeyConstants.AccountingLineViewRendering.ACCOUNTING_LINE_ACTIONS_LABEL));
    }

    /**
     * This doesn't hold a field, so this implementation does nothing
     * @see org.kuali.kfs.sys.document.web.RenderableElement#appendFieldNames(java.util.List)
     * 
     * KRAD Conversion: Customization of the fields - No use of data dictionary
     */
    public void appendFields(List<Field> fields) { }

    /**
     * Doesn't do anything
     * @see org.kuali.kfs.sys.document.web.RenderableElement#populateWithTabIndexIfRequested(int[], int)
     */
    public void populateWithTabIndexIfRequested(int reallyHighIndex) {}
    
}
