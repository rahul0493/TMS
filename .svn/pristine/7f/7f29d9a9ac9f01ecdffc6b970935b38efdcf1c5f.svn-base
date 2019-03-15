package com.quinnox.flm.tms.module.shiftplanner.bean;

import java.util.Comparator;

public class TrackShift implements Comparator<TrackShift> {

	
	private Integer shift_id;
	private String shift_display_nm;
	private String shift_master_nm;
	private String shift_from_time;
	private String shift_to_time;
	private String shift_type;
	/**
	 * @return the shift_id
	 */
	public Integer getShift_id() {
		return shift_id;
	}
	
	public String getShift_type() {
		return shift_type;
	}

	public void setShift_type(String shift_type) {
		this.shift_type = shift_type;
	}

	/**
	 * @param shift_id the shift_id to set
	 */
	public void setShift_id(Integer shift_id) {
		this.shift_id = shift_id;
	}
	/**
	 * @return the shift_display_nm
	 */
	public String getShift_display_nm() {
		return shift_display_nm;
	}
	/**
	 * @param shift_display_nm the shift_display_nm to set
	 */
	public void setShift_display_nm(String shift_display_nm) {
		this.shift_display_nm = shift_display_nm;
	}
	/**
	 * @return the shift_master_nm
	 */
	public String getShift_master_nm() {
		return shift_master_nm;
	}
	/**
	 * @param shift_master_nm the shift_master_nm to set
	 */
	public void setShift_master_nm(String shift_master_nm) {
		this.shift_master_nm = shift_master_nm;
	}
	/**
	 * @return the shift_from_time
	 */
	public String getShift_from_time() {
		return shift_from_time;
	}
	/**
	 * @param shift_from_time the shift_from_time to set
	 */
	public void setShift_from_time(String shift_from_time) {
		this.shift_from_time = shift_from_time;
	}
	/**
	 * @return the shift_to_time
	 */
	public String getShift_to_time() {
		return shift_to_time;
	}
	/**
	 * @param shift_to_time the shift_to_time to set
	 */
	public void setShift_to_time(String shift_to_time) {
		this.shift_to_time = shift_to_time;
	}
	@Override
	public String toString() {
		return "TrackShift [shift_id=" + shift_id + ", shift_display_nm="
				+ shift_display_nm + ", shift_master_nm=" + shift_master_nm
				+ ", shift_from_time=" + shift_from_time + ", shift_to_time="
				+ shift_to_time + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		/*result = prime
				* result
				+ ((shift_display_nm == null) ? 0 : shift_display_nm.hashCode());*/
		result = prime * result
				+ ((shift_from_time == null) ? 0 : shift_from_time.hashCode());
		result = prime * result
				+ ((shift_id == null) ? 0 : shift_id.hashCode());
		result = prime * result
				+ ((shift_master_nm == null) ? 0 : shift_master_nm.hashCode());
		result = prime * result
				+ ((shift_to_time == null) ? 0 : shift_to_time.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrackShift other = (TrackShift) obj;
		/*if (shift_display_nm == null) {
			if (other.shift_display_nm != null)
				return false;
		} else if (!shift_display_nm.equals(other.shift_display_nm))
			return false;*/
		if (shift_from_time == null) {
			if (other.shift_from_time != null)
				return false;
		} else if (!shift_from_time.equals(other.shift_from_time))
			return false;
		if (shift_id == null) {
			if (other.shift_id != null)
				return false;
		} else if (!shift_id.equals(other.shift_id))
			return false;
		if (shift_master_nm == null) {
			if (other.shift_master_nm != null)
				return false;
		} else if (!shift_master_nm.equals(other.shift_master_nm))
			return false;
		if (shift_to_time == null) {
			if (other.shift_to_time != null)
				return false;
		} else if (!shift_to_time.equals(other.shift_to_time))
			return false;
		return true;
	}
	@Override
	public int compare(TrackShift o1, TrackShift o2) {
		if(o1.getShift_id()!=null){
			int reqDComp=o1.getShift_master_nm().compareTo(o2.getShift_master_nm());
			if(reqDComp==0){
					}
			return reqDComp;
		}else{
			return 0;
		}
	}	
}
