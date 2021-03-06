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
package org.kuali.kfs.sys.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.kfs.sys.batch.service.SchedulerService;
import org.quartz.JobDetail;
import org.springframework.beans.factory.BeanNameAware;

public class JobDescriptor implements BeanNameAware {
    private String name;
    private String namespaceCode;
    private String group;
    private Map<String, String> dependencies;
    private List<Step> steps;
    private SchedulerService schedulerService;
    private boolean durable = true;

    public JobDescriptor() {
        dependencies = new HashMap();
        steps = new ArrayList();
    }

    public JobDescriptor(String name, String group, Step step, boolean durable) {
        this();
        this.name = name;
        this.group = group;
        this.durable = durable;
        steps.add(step);
    }

    /**
     * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
     */
    public void setBeanName(String name) {
        this.name = name;
    }

    /**
     * Constructs a non-volatile, durable, non-recoverable JobDetail w/ org.kuali.kfs.sys.batch.Job as the job class and the specified
     * name and group from this instance. Also adds status=Pending to the JobDataMap, if this is a scheduled job.
     * 
     * @return the org.quartz.JobDetail corresponding to this instance
     */
    public JobDetail getJobDetail() {
        return new JobDetail(name, group, Job.class, false, durable, false);
    }

    /**
     * Sets the group attribute value.
     * 
     * @param group The group to set.
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Sets the dependencies attribute value.
     * 
     * @param dependencies The dependencies to set.
     */
    public void setDependencies(Map<String, String> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * Gets the dependencies attribute.
     * 
     * @return Returns the dependencies.
     */
    public Map<String, String> getDependencies() {
        return dependencies;
    }

    /**
     * Sets the steps attribute value.
     * 
     * @param steps The steps to set.
     */
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    /**
     * Gets the steps attribute.
     * 
     * @return Returns the steps.
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * Sets the schedulerService attribute value.
     * 
     * @param schedulerService The schedulerService to set.
     */
    public void setSchedulerService(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public SchedulerService getSchedulerService() {
        return schedulerService;
    }

    public boolean isDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    /**
     * Gets the namespaceCode attribute. 
     * @return Returns the namespaceCode.
     */
    public String getNamespaceCode() {
        return namespaceCode;
    }

    /**
     * Sets the namespaceCode attribute value.
     * @param namespaceCode The namespaceCode to set.
     */
    public void setNamespaceCode(String namespaceCode) {
        this.namespaceCode = namespaceCode;
    }

}
