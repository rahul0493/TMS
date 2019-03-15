package com.quinnox.flm.tms.module.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class TrackDetailsBean implements Serializable , Comparable<TrackDetailsBean>{
	
	private static final long serialVersionUID = 3640226034895344731L;
	
	private Integer trackDetailsId;
	private String trackName;
	private Set<ShiftDetailsBean> shiftDetails;
	private List<ShiftDetailsBean> shiftDetailsList;
	private boolean shiftDetailsExits;
	
	public Integer getTrackDetailsId() {
		return trackDetailsId;
	}
	public void setTrackDetailsId(Integer trackDetailsId) {
		this.trackDetailsId = trackDetailsId;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public Set<ShiftDetailsBean> getShiftDetails() {
		return shiftDetails;
	}
	public void setShiftDetails(Set<ShiftDetailsBean> shiftDetails) {
		this.shiftDetails = shiftDetails;
	}
	
	public List<ShiftDetailsBean> getShiftDetailsList() {
		return shiftDetailsList;
	}
	public void setShiftDetailsList(List<ShiftDetailsBean> shiftDetailsList) {
		this.shiftDetailsList = shiftDetailsList;
	}
	@Override
	public int compareTo(TrackDetailsBean trackBean) {
		return this.trackName.compareToIgnoreCase(trackBean.getTrackName());
		//return this.trackDetailsId.compareTo(trackBean.getTrackDetailsId());
	}
//	public Set<ShiftDetails> getShiftDetailModels() {
//		return shiftDetailModels;
//	}
//	public void setShiftDetailModels(Set<ShiftDetails> shiftDetailModels) {
//		this.shiftDetailModels = shiftDetailModels;
//	}
	public boolean isShiftDetailsExits() {
		return shiftDetailsExits;
	}
	public void setShiftDetailsExits(boolean shiftDetailsExits) {
		this.shiftDetailsExits = shiftDetailsExits;
	}
	
}
