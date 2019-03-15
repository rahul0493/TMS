package com.quinnox.flm.tms.module.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="location_master")
public class LocationMaster {
	private Integer locationId;
	private String locationName;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "locationId", unique = true, nullable = false)
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	@Column(name="locationName")
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	
}
