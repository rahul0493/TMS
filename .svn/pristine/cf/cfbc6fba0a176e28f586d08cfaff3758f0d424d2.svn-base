package com.quinnox.flm.tms.module.model;


import java.io.Serializable;
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
@Table(name = "trip_sheet_details")
public class TripSheetDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8166091163105135204L;
	private Integer tripId;
	private Integer tripSheetNumber;
	private String reportingPlace;
	private String reportingTime;
	private String vehicleNumber;
	private String vehicleType;
	private String driverName;
	private Long driverMobileNumber;
	private String vendorName;
	private String tripDate;
	private Boolean isEscort;
   	private String escortName;
   	private Vendor vendorDetails;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "trip_sheet_id", unique = true, nullable = false)
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}
	@Column(name = "trip_sheet_number")
	public Integer getTripSheetNumber() {
		return tripSheetNumber;
	}
	public void setTripSheetNumber(Integer tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}
	@Column(name = "reporting_place")
	public String getReportingPlace() {
		return reportingPlace;
	}
	public void setReportingPlace(String reportingPlace) {
		this.reportingPlace = reportingPlace;
	}
	@Column(name = "reporting_time")
	public String getReportingTime() {
		return reportingTime;
	}
	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}
	@Column(name = "vehicle_number")
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	@Column(name = "vehicle_type")
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	@Column(name = "driver_name")
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	@Column(name = "driver_mobile_number")
	public Long getDriverMobileNumber() {
		return driverMobileNumber;
	}
	public void setDriverMobileNumber(Long driverMobileNumber) {
		this.driverMobileNumber = driverMobileNumber;
	}
	@Column(name = "vendor_name")
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	@Column(name = "trip_date")
	public String getTripDate() {
		return tripDate;
	}
	public void setTripDate(String tripDate) {
		this.tripDate = tripDate;
	}
	@Column(name = "is_escort")
	public Boolean getIsEscort() {
		return isEscort;
	}
	public void setIsEscort(Boolean isEscort) {
		this.isEscort = isEscort;
	}
	@Column(name = "escort_name")
	public String getEscortName() {
		return escortName;
	}
	public void setEscortName(String escortName) {
		this.escortName = escortName;
	}
	@ManyToOne(fetch=FetchType.EAGER)  
	@JoinColumn(name="vendor_id",nullable=false)
	@JsonIgnore
	public Vendor getVendorDetails() {
		return vendorDetails;
	}
	public void setVendorDetails(Vendor vendorDetails) {
		this.vendorDetails = vendorDetails;
	}
}
