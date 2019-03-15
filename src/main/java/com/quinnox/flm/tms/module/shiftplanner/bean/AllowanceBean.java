/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.bean;

/**
 * @author RahulY
 *
 */
public class AllowanceBean {

	private String empId;
    private String empName;
    private String earningComponentAffected;
    private Long allowanceAmount;
	private String remark;
	private String ProjectName;
	private String spocName;
	private String month;
	private Integer noOfDays;
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEarningComponentAffected() {
		return earningComponentAffected;
	}
	public void setEarningComponentAffected(String earningComponentAffected) {
		this.earningComponentAffected = earningComponentAffected;
	}
	public Long getAllowanceAmount() {
		return allowanceAmount;
	}
	public void setAllowanceAmount(Long allowanceAmount) {
		this.allowanceAmount = allowanceAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProjectName() {
		return ProjectName;
	}
	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}
	public String getSpocName() {
		return spocName;
	}
	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}
	
}
