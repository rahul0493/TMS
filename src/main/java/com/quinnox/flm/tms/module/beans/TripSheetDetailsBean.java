package com.quinnox.flm.tms.module.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TripSheetDetailsBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6635832519010592625L;
	private Integer tripId;
	private Integer tripSheetNumber;
	private String reportingPlace;
	private String reportingTime;
	private String vehicleNumber;
	private String vehicleType;
	private Boolean isEscort;
	private String escortName;
	private String driverName;
	private Long driverMobileNumber;
	private String vendorName;
//	private List<Integer> cabDetailsId;
	private List<AdhocCabRequestBean> empCabRequestDetails;
	private String tripDate;
	private Date tripScheduleDate;
	private Integer vendorId;
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}
	public Integer getTripSheetNumber() {
		return tripSheetNumber;
	}
	public void setTripSheetNumber(Integer tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}
	public String getReportingPlace() {
		return reportingPlace;
	}
	public void setReportingPlace(String reportingPlace) {
		this.reportingPlace = reportingPlace;
	}
	public String getReportingTime() {
		return reportingTime;
	}
	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public Boolean getIsEscort() {
		return isEscort;
	}
	public void setIsEscort(Boolean isEscort) {
		this.isEscort = isEscort;
	}
	public String getEscortName() {
		return escortName;
	}
	public void setEscortName(String escortName) {
		this.escortName = escortName;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Long getDriverMobileNumber() {
		return driverMobileNumber;
	}
	public void setDriverMobileNumber(Long driverMobileNumber) {
		this.driverMobileNumber = driverMobileNumber;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getTripDate() {
		return tripDate;
	}
	public void setTripDate(String tripDate) {
		this.tripDate = tripDate;
	}
//	public List<Integer> getCabDetailsId() {
//		return cabDetailsId;
//	}
//	public void setCabDetailsId(List<Integer> cabDetailsId) {
//		this.cabDetailsId = cabDetailsId;
//	}
	public List<AdhocCabRequestBean> getEmpCabRequestDetails() {
		return empCabRequestDetails;
	}
	public void setEmpCabRequestDetails(List<AdhocCabRequestBean> empCabRequestDetails) {
		this.empCabRequestDetails = empCabRequestDetails;
	}
	public Date getTripScheduleDate() {
		return tripScheduleDate;
	}
	public void setTripScheduleDate(Date tripScheduleDate) {
		this.tripScheduleDate = tripScheduleDate;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
}
