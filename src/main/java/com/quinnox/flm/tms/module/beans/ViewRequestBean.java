package com.quinnox.flm.tms.module.beans;

import java.io.Serializable;
import java.util.Set;

public class ViewRequestBean  implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3122018542316915323L;
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getTravelType() {
		return travelType;
	}
	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}
//	public String getStartDate() {
//		return startDate;
//	}
//	public void setStartDate(String startDate) {
//		this.startDate = startDate;
//	}
//	public String getEndDate() {
//		return endDate;
//	}
//	public void setEndDate(String endDate) {
//		this.endDate = endDate;
//	}
	public String getRequestRaisedDate() {
		return requestRaisedDate;
	}
	public void setRequestRaisedDate(String requestRaisedDate) {
		this.requestRaisedDate = requestRaisedDate;
	}
	public String getEmployeeReason() {
		return employeeReason;
	}
	public void setEmployeeReason(String employeeReason) {
		this.employeeReason = employeeReason;
	}
	public String getManagerComment() {
		return managerComment;
	}
	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public int getCabRequestId() {
		return cabRequestId;
	}
	public void setCabRequestId(int cabRequestId) {
		this.cabRequestId = cabRequestId;
	}

	private int cabRequestId;
	private String requestType;
	private String travelType;
	private String startDate;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	private String endDate;
	private String requestRaisedDate;
	private String employeeReason;
	private String managerComment;
	private String status;
	private String adhocOrMonthly;
	
	public String getAdhocOrMonthly() {
		return adhocOrMonthly;
	}
	public void setAdhocOrMonthly(String adhocOrMonthly) {
		this.adhocOrMonthly = adhocOrMonthly;
	}

	private Set<SubViewRequestBean> subViewRequestBean;
	
	public Set<SubViewRequestBean> getSubViewRequestBean() {
		return subViewRequestBean;
	}
	public void setSubViewRequestBean(Set<SubViewRequestBean> subViewRequestBean) {
		this.subViewRequestBean = subViewRequestBean;
	}
	
}
