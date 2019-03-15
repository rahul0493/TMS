package com.quinnox.flm.tms.global.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class TrackMappingBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6844538998517321516L;
	private Integer empId;
	private String empName;
	private String empCode;
	private Boolean isSpoc=false;
	private Map<String,Boolean> isEmpMapToTrack;
    private List<TrackBean> trackDetails;
    
   
    private String trackName; 
    private Integer trackId;
    
    
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

		public List<TrackBean> getTrackDetails() {
		return trackDetails;
	}

	public void setTrackDetails(List<TrackBean> trackDetails) {
		this.trackDetails = trackDetails;
	}



	
		public Map<String, Boolean> getIsEmpMapToTrack() {
		return isEmpMapToTrack;
	}

	public void setIsEmpMapToTrack(Map<String, Boolean> isEmpMapToTrack) {
		this.isEmpMapToTrack = isEmpMapToTrack;
	}

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

	@Override
	public String toString() {
		return "TrackMappingBean [empId=" + empId + ", empName=" + empName
				+ ", empCode=" + empCode + ", isSpoc=" + isSpoc
				 + "]";
	}
    
    
}
