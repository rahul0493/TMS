package com.quinnox.flm.tms.module.beans;

import java.util.List;

import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;

public class AddressProjectBean {
	List<EmployeeAddressBean> employeeAddress;
	List<ProjectDetailsBean> projectList;
	public List<EmployeeAddressBean> getEmployeeAddress() {
		return employeeAddress;
	}
	public void setEmployeeAddress(List<EmployeeAddressBean> employeeAddress) {
		this.employeeAddress = employeeAddress;
	}
	public List<ProjectDetailsBean> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<ProjectDetailsBean> projectList) {
		this.projectList = projectList;
	}
}
