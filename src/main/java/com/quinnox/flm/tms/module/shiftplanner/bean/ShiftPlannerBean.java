package com.quinnox.flm.tms.module.shiftplanner.bean;

import java.util.Set;

public class ShiftPlannerBean {

	private int monthId;
	private int day;
	private String monthName;
	private int  trackId;
	private int shiftId;
    private int employeeId;
    private String employeeIntials;
    private int year;
	private Set<OtherTrackBean> otherTrack;
	public int getMonthId() {
		return monthId;
	}
	public void setMonthId(int monthId) {
		this.monthId = monthId;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public int getTrackId() {
		return trackId;
	}
	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}
	public int getShiftId() {
		return shiftId;
	}
	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeIntials() {
		return employeeIntials;
	}
	public void setEmployeeIntials(String employeeIntials) {
		this.employeeIntials = employeeIntials;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Set<OtherTrackBean> getOtherTrack() {
		return otherTrack;
	}
	public void setOtherTrack(Set<OtherTrackBean> otherTrack) {
		this.otherTrack = otherTrack;
	}
	@Override
	public String toString() {
		return "ShiftPlannerBean [monthId=" + monthId + ", day=" + day
				+ ", monthName=" + monthName + ", trackId=" + trackId
				+ ", shiftId=" + shiftId + ", employeeId=" + employeeId
				+ ", employeeIntials=" + employeeIntials + ", year=" + year
				+ ", otherTrack=" + otherTrack + "]";
	}
	
	
}
