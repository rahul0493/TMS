package com.quinnox.flm.tms.module.beans;

import java.io.Serializable;

public class UserProfileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 921110521127584061L;
	private String employeeName;
	private Integer employeeId;
	private String empCode;
	private String email;
	private Long mobileNumber;
	private String manager;
	private String currentAddress;
	private Long pincode;
	private String landmark;
	private String address;
	private String location;
	private String pinCode;
	private String account;
	private Integer accountId;
	private Integer projectId;
	private String projectName;
	private String managerCode;
	private String managerName;
	private String managerEmail;
	private String requestFor;
   	private Integer requestedEmpId;
   	private String guestUserName;
   	private String reqEmpEmail;
   	private String reqEmpMgrCode;
   	private String reqEmpMgrEmail;
   	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getMobileNumber() {
		return mobileNumber;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public Long getPincode() {
		return pincode;
	}
	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
/*	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}*/
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	public String getRequestFor() {
		return requestFor;
	}
	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
	}
	public Integer getRequestedEmpId() {
		return requestedEmpId;
	}
	public void setRequestedEmpId(Integer requestedEmpId) {
		this.requestedEmpId = requestedEmpId;
	}
	public String getGuestUserName() {
		return guestUserName;
	}
	public void setGuestUserName(String guestUserName) {
		this.guestUserName = guestUserName;
	}
	public String getReqEmpEmail() {
		return reqEmpEmail;
	}
	public void setReqEmpEmail(String reqEmpEmail) {
		this.reqEmpEmail = reqEmpEmail;
	}
	public String getReqEmpMgrCode() {
		return reqEmpMgrCode;
	}
	public void setReqEmpMgrCode(String reqEmpMgrCode) {
		this.reqEmpMgrCode = reqEmpMgrCode;
	}
	public String getReqEmpMgrEmail() {
		return reqEmpMgrEmail;
	}
	public void setReqEmpMgrEmail(String reqEmpMgrEmail) {
		this.reqEmpMgrEmail = reqEmpMgrEmail;
	}
	
}
