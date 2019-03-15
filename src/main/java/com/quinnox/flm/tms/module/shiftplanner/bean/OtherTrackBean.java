package com.quinnox.flm.tms.module.shiftplanner.bean;

public class OtherTrackBean {

	private Integer trck_id;
	private String trck_nm;
	private String trackAssignedTo;
	
	public String getTrackAssignedTo() {
		return trackAssignedTo;
	}
	public void setTrackAssignedTo(String trackAssignedTo) {
		this.trackAssignedTo = trackAssignedTo;
	}
	public Integer getTrck_id() {
		return trck_id;
	}
	public void setTrck_id(Integer trck_id) {
		this.trck_id = trck_id;
	}
	public String getTrck_nm() {
		return trck_nm;
	}
	public void setTrck_nm(String trck_nm) {
		this.trck_nm = trck_nm;
	}
	@Override
	public String toString() {
		return "OtherTrackBean [trck_id=" + trck_id + ", trck_nm=" + trck_nm
				+ ", trackAssignedTo=" + trackAssignedTo + "]";
	}
	
	
}
