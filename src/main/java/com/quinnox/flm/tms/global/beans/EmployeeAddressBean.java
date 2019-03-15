package com.quinnox.flm.tms.global.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EmployeeAddressBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2484740025366668218L;
	private Integer empAddressBeanId;
	private String location;
	private Long pincode;
	private String landmark;
	private String city;
	private String state;
	private String alias;
	private String address;
	private Integer employeeId;
	private String empCode;
	private Boolean status;
	private Boolean defaultAddress;
	private String statusValue;
	private Integer projectId;
	private String projectName;
	private Date effectiveDate;
	private String ManagerEmail;
	private Boolean isCabRequired;
	private String comment;
	private Integer locationId;
	
	public Integer getEmpAddressBeanId() {
		return empAddressBeanId;
	}
	public void setEmpAddressBeanId(Integer empAddressBeanId) {
		this.empAddressBeanId = empAddressBeanId;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Boolean getDefaultAddress() {
		return defaultAddress;
	}
	public void setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getPincode() {
		return pincode;
	}
	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getManagerEmail() {
		return ManagerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		ManagerEmail = managerEmail;
	}
	public Boolean getIsCabRequired() {
		return isCabRequired;
	}
	public void setIsCabRequired(Boolean isCabRequired) {
		this.isCabRequired = isCabRequired;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
//	public String getStatusMsg() {
//		return statusMsg;
//	}
//	public void setStatusMsg(String statusMsg) {
//		this.statusMsg = statusMsg;
//	}
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
}
