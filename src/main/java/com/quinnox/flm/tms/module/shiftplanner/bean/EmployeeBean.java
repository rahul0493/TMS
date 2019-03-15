package com.quinnox.flm.tms.module.shiftplanner.bean;

import java.util.List;

public class EmployeeBean {

	public Integer empid;
	public String empnm;
	public String emp_init;
	public Integer emp_trackid;
	public List<TrackBean> other_track;
	
	public Integer getEmp_trackid() {
		return emp_trackid;
	}
	public void setEmp_trackid(Integer emp_trackid) {
		this.emp_trackid = emp_trackid;
	}
	public List<TrackBean> getOther_track() {
		return other_track;
	}
	public void setOther_track(List<TrackBean> other_track) {
		this.other_track = other_track;
	}
	/**
	 * @return the empid
	 */
	public Integer getEmpid() {
		return empid;
	}
	/**
	 * @param empid the empid to set
	 */
	public void setEmpid(Integer empid) {
		this.empid = empid;
	}
	/**
	 * @return the empnm
	 */
	public String getEmpnm() {
		return empnm;
	}
	/**
	 * @param empnm the empnm to set
	 */
	public void setEmpnm(String empnm) {
		this.empnm = empnm;
	}
	/**
	 * @return the emp_init
	 */
	public String getEmp_init() {
		return emp_init;
	}
	/**
	 * @param emp_init the emp_init to set
	 */
	public void setEmp_init(String emp_init) {
		this.emp_init = emp_init;
	}
	/**
	 * @return the emp_trackid
	 */
	
	
	
}
