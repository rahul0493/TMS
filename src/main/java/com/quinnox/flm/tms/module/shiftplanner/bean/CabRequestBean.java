package com.quinnox.flm.tms.module.shiftplanner.bean;

public class CabRequestBean {

	private int monthId;
	private int day;
	private int  TrackId;
	private int ShiftId;
    private int EmployeeId;
    private String ShiftName;
    private String EmployeeIntials;
    private int year;
	/**
	 * @return the monthId
	 */
    
    
	public int getMonthId() {
		return monthId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + EmployeeId;
		result = prime * result
				+ ((EmployeeIntials == null) ? 0 : EmployeeIntials.hashCode());
		result = prime * result + ShiftId;
		result = prime * result
				+ ((ShiftName == null) ? 0 : ShiftName.hashCode());
		result = prime * result + TrackId;
		result = prime * result + day;
		result = prime * result + monthId;
		result = prime * result + year;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CabRequestBean other = (CabRequestBean) obj;
		if (EmployeeId != other.EmployeeId)
			return false;
		if (EmployeeIntials == null) {
			if (other.EmployeeIntials != null)
				return false;
		} else if (!EmployeeIntials.equals(other.EmployeeIntials))
			return false;
		if (ShiftId != other.ShiftId)
			return false;
		if (ShiftName == null) {
			if (other.ShiftName != null)
				return false;
		} else if (!ShiftName.equals(other.ShiftName))
			return false;
		if (TrackId != other.TrackId)
			return false;
		if (day != other.day)
			return false;
		if (monthId != other.monthId)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	/**
	 * @param monthId the monthId to set
	 */
	public void setMonthId(int monthId) {
		this.monthId = monthId;
	}
	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	/**
	 * @return the trackId
	 */
	public int getTrackId() {
		return TrackId;
	}
	/**
	 * @param trackId the trackId to set
	 */
	public void setTrackId(int trackId) {
		TrackId = trackId;
	}
	/**
	 * @return the shiftId
	 */
	public int getShiftId() {
		return ShiftId;
	}
	/**
	 * @param shiftId the shiftId to set
	 */
	public void setShiftId(int shiftId) {
		ShiftId = shiftId;
	}
	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return EmployeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		EmployeeId = employeeId;
	}
	/**
	 * @return the shiftName
	 */
	public String getShiftName() {
		return ShiftName;
	}
	/**
	 * @param shiftName the shiftName to set
	 */
	public void setShiftName(String shiftName) {
		ShiftName = shiftName;
	}
	/**
	 * @return the employeeIntials
	 */
	public String getEmployeeIntials() {
		return EmployeeIntials;
	}
	/**
	 * @param employeeIntials the employeeIntials to set
	 */
	public void setEmployeeIntials(String employeeIntials) {
		EmployeeIntials = employeeIntials;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CabRequestBean [monthId=" + monthId + ", day=" + day
				+ ", TrackId=" + TrackId + ", ShiftId=" + ShiftId
				+ ", EmployeeId=" + EmployeeId + ", ShiftName=" + ShiftName
				+ ", EmployeeIntials=" + EmployeeIntials + ", year=" + year
				+ "]";
	}
	
}
