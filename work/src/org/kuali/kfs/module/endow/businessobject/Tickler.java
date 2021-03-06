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
package org.kuali.kfs.module.endow.businessobject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.mo.common.active.MutableInactivatable;
import org.kuali.rice.kim.framework.group.GroupEbo;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class Tickler extends PersistableBusinessObjectBase implements MutableInactivatable {

    protected String number;
    protected String typeCode;
    protected String frequencyCode;
    protected Date nextDueDate;
    protected String detail;
    protected Date entryDate;
    protected Date terminationDate;
    protected boolean active;

    protected String groupId;
    protected String assignedToGroupNamespaceForLookup;
    protected String assignedToGroupNameForLookup;
    protected GroupEbo groupLookup;

    protected TicklerTypeCode type;
    protected FrequencyCode frequency;

    // Collections
    protected List<TicklerKEMID> kemIds;
    protected List<TicklerSecurity> securities;
    protected List<TicklerRecipientPrincipal> recipientPrincipals;
    protected List<TicklerRecipientGroup> recipientGroups;

    public Tickler()
    {
        super();
        kemIds = new ArrayList<TicklerKEMID>();
        securities = new ArrayList<TicklerSecurity>();
        recipientPrincipals = new ArrayList<TicklerRecipientPrincipal>();
        recipientGroups = new ArrayList<TicklerRecipientGroup>();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(Date nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    public List<TicklerKEMID> getKemIds() {
        return kemIds;
    }

    public void setKemIds(List<TicklerKEMID> kemIds) {
        this.kemIds = kemIds;
    }

    public List<TicklerSecurity> getSecurities() {
        return securities;
    }

    public void setSecurities(List<TicklerSecurity> securities) {
        this.securities = securities;
    }

    public List<TicklerRecipientPrincipal> getRecipientPrincipals() {
        return recipientPrincipals;
    }

    public void setRecipientPrincipals(List<TicklerRecipientPrincipal> recipientPrincipals) {
        this.recipientPrincipals = recipientPrincipals;
    }

    public List<TicklerRecipientGroup> getRecipientGroups() {
        return recipientGroups;
    }

    public void setRecipientGroups(List<TicklerRecipientGroup> recipientGroups) {
        this.recipientGroups = recipientGroups;
    }

    public String getFrequencyCode() {
        return frequencyCode;
    }

    public void setFrequencyCode(String frequencyCode) {
        this.frequencyCode = frequencyCode;
    }

    public FrequencyCode getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyCode frequency) {
        this.frequency = frequency;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public TicklerTypeCode getType() {
        return type;
    }

    public void setType(TicklerTypeCode type) {
        this.type = type;
    }

    public String getAssignedToGroupNamespaceForLookup() {
        return assignedToGroupNamespaceForLookup;
    }

    public void setAssignedToGroupNamespaceForLookup(String assignedToGroupNamespaceForLookup) {
        this.assignedToGroupNamespaceForLookup = assignedToGroupNamespaceForLookup;
    }

    public String getAssignedToGroupNameForLookup() {
        return assignedToGroupNameForLookup;
    }

    public void setAssignedToGroupNameForLookup(String assignedToGroupNameForLookup) {
        this.assignedToGroupNameForLookup = assignedToGroupNameForLookup;
    }

    public GroupEbo getGroupLookup() {
        return groupLookup;
    }

    public void setGroupLookup(GroupEbo groupLookup) {
        this.groupLookup = groupLookup;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


}
