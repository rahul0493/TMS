package com.quinnox.flm.tms.module.beans;

import java.io.Serializable;
import java.util.Date;

public class CabReportBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9062739576768973397L;
	
	private Integer accountId;
	private String projectName;
	private Integer projectId;
	private String requestNature;
	private Date fromDate;
	private Date toDate;
	private String requestType;
	private String empCode;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getRequestNature() {
		return requestNature;
	}
	public void setRequestNature(String requestNature) {
		this.requestNature = requestNature;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	
}
