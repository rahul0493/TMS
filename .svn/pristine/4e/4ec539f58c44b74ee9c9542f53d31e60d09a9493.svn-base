package com.quinnox.flm.tms.module.model;

import java.io.Serializable;
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

/**
 * @author AmareshP
 *
 */
@Entity
@Table(name = "cab_req_details")
public class EmpCabRequestDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -400638708061433589L;


	private Integer requestId;
	private EmpCabRequest empCabRequest;
	private Date scheduleDate;
    private String reqStatus;
    private Boolean activeRequest;
    private String requestime;
   	private Long mobileNumber;
	private String pickOrDrop;
   	private Integer projectId;
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
   	private String fromAliasName;
   	private String toAliasName;
 	private Integer tripSheetId; 
   	private String action;
   	private String travelStatus; 
//   	private Boolean isEscort;
//   	private String escortName;
   	
    @Column(name="active_request")
    public Boolean getActiveRequest() {
		return activeRequest;
	}

	public void setActiveRequest(Boolean activeRequest) {
		this.activeRequest = activeRequest;
	}
	
	@Column(name="req_time")
	public String getRequestime() {
		return requestime;
	}

	public void setRequestime(String requestime) {
		this.requestime = requestime;
	}

	@Column(name="req_status")
	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	@Column(name="pick_or_drop")
	public String getPickOrDrop() {
		return pickOrDrop;
	}

	public void setPickOrDrop(String pickOrDrop) {
		this.pickOrDrop = pickOrDrop;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "req_id", unique = true, nullable = false)
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}	
	@ManyToOne(fetch=FetchType.EAGER)  // changed
	@JoinColumn(name="cab_req_id",nullable=false)
	@JsonIgnore
	public EmpCabRequest getEmpCabRequest() {
		return empCabRequest;
	}	
	public void setEmpCabRequest(EmpCabRequest empCabRequest) {
		this.empCabRequest = empCabRequest;
	}
	@Column(name="schedule_date")
    public Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	@Column(name="mobile_number")
	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	 @Column(name="project_id")
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	 @Column(name="from_address")
	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	 @Column(name="from_landmark")
	public String getFromLandmark() {
		return fromLandmark;
	}

	public void setFromLandmark(String fromLandmark) {
		this.fromLandmark = fromLandmark;
	}
	 @Column(name="from_location")
	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	 @Column(name="from_city")
	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}
	 @Column(name="from_pincode")
	public Long getFromPincode() {
		return fromPincode;
	}

	public void setFromPincode(Long fromPincode) {
		this.fromPincode = fromPincode;
	}
	 @Column(name="to_address")
	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	 @Column(name="to_landmark")
	public String getToLandmark() {
		return toLandmark;
	}

	public void setToLandmark(String toLandmark) {
		this.toLandmark = toLandmark;
	}
	 @Column(name="to_location")
	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	 @Column(name="to_city")
	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}
	 @Column(name="to_pincode")
	public Long getToPincode() {
		return toPincode;
	}

	public void setToPincode(Long toPincode) {
		this.toPincode = toPincode;
	}

	
	@Column(name="from_alias")
	public String getFromAliasName() {
		return fromAliasName;
	}

	public void setFromAliasName(String fromAliasName) {
		this.fromAliasName = fromAliasName;
	}
	@Column(name="to_alias")
	public String getToAliasName() {
		return toAliasName;
	}

	public void setToAliasName(String toAliasName) {
		this.toAliasName = toAliasName;
	}
	@Column(name="trip_id")
	public Integer getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Integer tripSheetId) {
		this.tripSheetId = tripSheetId;
	}
	@Column(name="flm_action") 
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	@Column(name="travel_status") 
	public String getTravelStatus() {
		return travelStatus;
	}

	public void setTravelStatus(String travelStatus) {
		this.travelStatus = travelStatus;
	} 
    
//	@Column(name = "is_escort")
//	public Boolean getIsEscort() {
//		return isEscort;
//	}
//	public void setIsEscort(Boolean isEscort) {
//		this.isEscort = isEscort;
//	}
//	@Column(name = "escort_name")
//	public String getEscortName() {
//		return escortName;
//	}
//	public void setEscortName(String escortName) {
//		this.escortName = escortName;
//	}
    
	
}