package com.quinnox.flm.tms.module.beans;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

import com.quinnox.flm.tms.module.model.EmpProjMapping;

public class AdhocCabRequestBean implements Comparator<AdhocCabRequestBean>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -227492230542802118L;
	private int id;
	private int userId;
	private Date requestDate;
	private String managerRemark;
	private Set<AdhocCabRequestDetailsBean> cabRequestDetails;
	private Date from;
	private Date to;
	private String reason;
	private String requestType;
	private String travelType;
	private Boolean saturday;
	private Boolean sunday;
	private String pickTime;
	private String dropTime;
	private Long mobileNumber;
	private Boolean activeRequest;
	private String adhocMonthly;
	private String requestStatus;
	private Date scheduleDate;
	private String reqTime;
	private Integer projectId;
	private String projectName;
	private String empName;
	private String empMailId;
	private String fromAddress;
	private String fromLandmark;
	private String fromLocation;
	private String fromCity;
	private Long fromPincode;
	private String toAddress;
	private String toLandmark;
	private String toLocation;
	private String toCity;
	private Long toPincode;
	private String requestedFor;
	private Integer requestedEmpId;
	private String guestUserName;
	private String fromAliasName;
	private String toAliasName;
	private String requestedBy;
	private String action;
	private String travelStatus; 
	private Integer tripSheetId; 
	private Integer cabDetailsId;
	private Integer tripSheetNumber;
	private String empGender;
	private Boolean isEscort;
	private String escortName;
	private Date convertedScheduleDate;
	private String fromDate;
	private String toDate;
	private String createdDate;
	private Set<EmpProjMapping> projectLists;

	public Boolean getActiveRequest() {
		return activeRequest;
	}
	public void setActiveRequest(Boolean activeRequest) {
		this.activeRequest = activeRequest;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getAdhocMonthly() {
		return adhocMonthly;
	}
	public void setAdhocMonthly(String adhocMonthly) {
		this.adhocMonthly = adhocMonthly;
	}
	public Set<AdhocCabRequestDetailsBean> getCabRequestDetails() {
		return cabRequestDetails;
	}
	public void setCabRequestDetails(Set<AdhocCabRequestDetailsBean> cabRequestDetails) {
		this.cabRequestDetails = cabRequestDetails;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	//	public String getAddress() {
	//		return address;
	//	}
	//	public void setAddress(String address) {
	//		this.address = address;
	//	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getTravelType() {
		return travelType;
	}
	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}
	public Boolean getSaturday() {
		return saturday;
	}
	public void setSaturday(Boolean saturday) {
		this.saturday = saturday;
	}
	public Boolean getSunday() {
		return sunday;
	}
	public void setSunday(Boolean sunday) {
		this.sunday = sunday;
	}

	public String getPickTime() {
		return pickTime;
	}
	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}
	public String getDropTime() {
		return dropTime;
	}
	public void setDropTime(String dropTime) {
		this.dropTime = dropTime;
	}
	public Long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getManagerRemark() {
		return managerRemark;
	}
	public void setManagerRemark(String managerRemark) {
		this.managerRemark = managerRemark;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	public Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpMailId() {
		return empMailId;
	}
	public void setEmpMailId(String empMailId) {
		this.empMailId = empMailId;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getFromLandmark() {
		return fromLandmark;
	}
	public void setFromLandmark(String fromLandmark) {
		this.fromLandmark = fromLandmark;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getFromCity() {
		return fromCity;
	}
	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}
	public Long getFromPincode() {
		return fromPincode;
	}
	public void setFromPincode(Long fromPincode) {
		this.fromPincode = fromPincode;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getToLandmark() {
		return toLandmark;
	}
	public void setToLandmark(String toLandmark) {
		this.toLandmark = toLandmark;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public String getToCity() {
		return toCity;
	}
	public void setToCity(String toCity) {
		this.toCity = toCity;
	}
	public Long getToPincode() {
		return toPincode;
	}
	public void setToPincode(Long toPincode) {
		this.toPincode = toPincode;
	}

	public Integer getRequestedEmpId() {
		return requestedEmpId;
	}
	public void setRequestedEmpId(Integer requestedEmpId) {
		this.requestedEmpId = requestedEmpId;
	}
	public String getGuestUserName() {
		return guestUserName;
	}
	public void setGuestUserName(String guestUserName) {
		this.guestUserName = guestUserName;
	}
	public String getFromAliasName() {
		return fromAliasName;
	}
	public void setFromAliasName(String fromAliasName) {
		this.fromAliasName = fromAliasName;
	}
	public String getToAliasName() {
		return toAliasName;
	}
	public void setToAliasName(String toAliasName) {
		this.toAliasName = toAliasName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestedFor() {
		return requestedFor;
	}
	public void setRequestedFor(String requestedFor) {
		this.requestedFor = requestedFor;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTravelStatus() {
		return travelStatus;
	}
	public void setTravelStatus(String travelStatus) {
		this.travelStatus = travelStatus;
	}
	public Integer getTripSheetId() {
		return tripSheetId;
	}
	public void setTripSheetId(Integer tripSheetId) {
		this.tripSheetId = tripSheetId;
	}
	public Integer getCabDetailsId() {
		return cabDetailsId;
	}
	public void setCabDetailsId(Integer cabDetailsId) {
		this.cabDetailsId = cabDetailsId;
	}
	public Integer getTripSheetNumber() {
		return tripSheetNumber;
	}
	public void setTripSheetNumber(Integer tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}
	public String getEmpGender() {
		return empGender;
	}
	public void setEmpGender(String empGender) {
		this.empGender = empGender;
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

	public Date getConvertedScheduleDate() {
		return convertedScheduleDate;
	}
	public void setConvertedScheduleDate(Date convertedScheduleDate) {
		Date scheduledDate = this.getScheduleDate();
		String Datetime = scheduledDate.getDate() + this.getReqTime();
		
				
	}
	
	
	
	@Override
	public int compare(AdhocCabRequestBean o1, AdhocCabRequestBean o2) {
		if(o1.scheduleDate!=null && o1.reqTime!=null ){
			int reqDComp=o1.scheduleDate.compareTo(o2.scheduleDate);
			if(reqDComp==0){
				if(reqTime!=null){
					return o1.reqTime.compareTo(o2.reqTime);
				}
			}
			return reqDComp;
		}else{
			return 0;
		}

	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Set<EmpProjMapping> getProjectLists() {
		return projectLists;
	}
	public void setProjectLists(Set<EmpProjMapping> projectLists) {
		this.projectLists = projectLists;
	}
}
