package com.quinnox.flm.tms.module.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="holiday")
public class HolidayList {
	private Integer holidayId;
	private Integer year;
	private Date holidayDate;
	private String holidayName;
	private Boolean isPublicHoliday;
	private Boolean isNationalHoliday;
	private LocationMaster location;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "holidayId", unique = true, nullable = false)
	public Integer getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(Integer holidayId) {
		this.holidayId = holidayId;
	}
	
	
	
	@Column(name="year")
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Column(name="holidayDate")
	public Date getHolidayDate() {
		return holidayDate;
	}
	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}
	
	@Column(name="holidayName")
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	
	@Column(name="isPublicHoliday")
	public Boolean getIsPublicHoliday() {
		return isPublicHoliday;
	}
	public void setIsPublicHoliday(Boolean isPublicHoliday) {
		this.isPublicHoliday = isPublicHoliday;
	}
	
	@Column(name="isNationalHoliday")
	public Boolean getIsNationalHoliday() {
		return isNationalHoliday;
	}
	public void setIsNationalHoliday(Boolean isNationalHoliday) {
		this.isNationalHoliday = isNationalHoliday;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)  // changed
	@JoinColumn(name="loc_id",nullable=false)
	@JsonIgnore
	public LocationMaster getLocation() {
		return location;
	}
	public void setLocation(LocationMaster location) {
		this.location = location;
	}
	
	
}
