package com.quinnox.flm.tms.global.beans;

import java.io.Serializable;

public class UserTrackBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3265903357191698272L;
	private Integer empId;
	private String empName;
	private String empCode;
	private Boolean isSpoc=false;  
    private String trackName; 
    private Integer trackId;
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Boolean getIsSpoc() {
		return isSpoc;
	}
	public void setIsSpoc(Boolean isSpoc) {
		this.isSpoc = isSpoc;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public Integer getTrackId() {
		return trackId;
	}
	public void setTrackId(Integer trackId) {
		this.trackId = trackId;
	}
	@Override
	public String toString() {
		return "UserTrackBean [empId=" + empId + ", empName=" + empName + ", empCode=" + empCode + ", isSpoc=" + isSpoc
				+ ", trackName=" + trackName + ", trackId=" + trackId + "]";
	}
    
}
