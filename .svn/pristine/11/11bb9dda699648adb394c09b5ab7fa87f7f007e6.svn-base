package com.quinnox.flm.tms.module.beans;

import java.io.Serializable;

public class ShiftDetailsBean implements Serializable, Comparable<ShiftDetailsBean> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7945598193337660701L;
	private Integer shiftDetailsId;
	private Integer shiftId;
	private String shiftName;
	private String shiftInitials;
	private String startTime;
	private String endTime;
	private Boolean pickup;
	private Boolean drop;
	private String enumValues;
	private Double allowance;
	private String trackName;
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getShiftDetailsId() {
		return shiftDetailsId;
	}
	public void setShiftDetailsId(Integer shiftDetailsId) {
		this.shiftDetailsId = shiftDetailsId;
	}
	public Integer getShiftId() {
		return shiftId;
	}
	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getShiftInitials() {
		return shiftInitials;
	}
	public void setShiftInitials(String shiftInitials) {
		this.shiftInitials = shiftInitials;
	}
	
	public Boolean getPickup() {
		return pickup;
	}
	public void setPickup(Boolean pickup) {
		this.pickup = pickup;
	}
	public Boolean getDrop() {
		return drop;
	}
	public void setDrop(Boolean drop) {
		this.drop = drop;
	}
	public String getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}
	public Double getAllowance() {
		return allowance;
	}
	public void setAllowance(Double allowance) {
		this.allowance = allowance;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	
	@Override
	public int compareTo(ShiftDetailsBean shiftBean) {
		return this.startTime.compareToIgnoreCase(shiftBean.getStartTime());
		
	}
	
}
