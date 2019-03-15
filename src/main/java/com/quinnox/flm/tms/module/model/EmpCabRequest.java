package com.quinnox.flm.tms.module.model;

import java.io.Serializable;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.quinnox.flm.tms.global.model.Employee;

/**
 * @author AmareshP
 *
 */
@Entity
@Table(name = "emp_cab_request")
public class EmpCabRequest implements Serializable {

	private static final long serialVersionUID = -7934313164402714503L;
	private Integer id;
	private Employee user;
	private Set<EmpCabRequestDetails> empCabRequestDetails;
	private Date reqDate;
	private String reason;
	private String managerRemark;
	private String pickupTime;
	private String dropTime;
	private Date fromDate;
	private Boolean activeRequest;
	private Date toDate;
	private Long mobileNumber;
	private String requestType;
	private String travelType;
	private String adhocOrMonthly;
	private String weekends;
	private String requestFor;
   	private Integer requestingEmpId;
   	private String guestUserName;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cab_req_id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne( fetch = FetchType.EAGER) // changed
	@JoinColumn(name = "id")
	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	@OneToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "empCabRequest")
	public Set<EmpCabRequestDetails> getEmpCabRequestDetails() {
		return empCabRequestDetails;
	}

	public void setEmpCabRequestDetails(Set<EmpCabRequestDetails> empCabRequestDetails) {
		this.empCabRequestDetails = empCabRequestDetails;
	}

	@Column(name = "request_date")
	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date timestamp) {
		this.reqDate = timestamp;
	}

	@Column(name = "req_reason")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "from_date")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	@Column(name = "to_date")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Column(name = "pickup_time")
	public String getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	@Column(name = "drop_time")
	public String getDropTime() {
		return dropTime;
	}

	public void setDropTime(String dropTime) {
		this.dropTime = dropTime;
	}

	@Column(name = "active_request")
	public Boolean getActiveRequest() {
		return activeRequest;
	}

	public void setActiveRequest(Boolean activeRequest) {
		this.activeRequest = activeRequest;
	}

	@Column(name = "adhoc_or_monthly")
	public String getAdhocOrMonthly() {
		return adhocOrMonthly;
	}

	public void setAdhocOrMonthly(String adhocOrMonthly) {
		this.adhocOrMonthly = adhocOrMonthly;
	}

	@Column(name = "mobile_number")
	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	@Column(name = "requestType")
	@NotEmpty(message = "*Please provide your requestType")
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	@Column(name = "travelType")
	//@NotEmpty(message = "*Please provide your travelType")
	public String getTravelType() {
		return travelType;
	}

	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}

	@Column(name = "weekends")
	public String getWeekends() {
		return weekends;
	}

	public void setWeekends(String weekends) {
		this.weekends = weekends;
	}

	@Column(name = "manager_remark")
	public String getManagerRemark() {
		return managerRemark;
	}

	public void setManagerRemark(String managerRemark) {
		this.managerRemark = managerRemark;
	}
	@Column(name="requesting_for")
	public String getRequestFor() {
		return requestFor;
	}

	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
	}

	@Column(name="guest_username")
	public String getGuestUserName() {
		return guestUserName;
	}

	public void setGuestUserName(String guestUserName) {
		this.guestUserName = guestUserName;
	}
	@Column(name="requesting_Emp")
	public Integer getRequestingEmpId() {
		return requestingEmpId;
	}

	public void setRequestingEmpId(Integer requestingEmpId) {
		this.requestingEmpId = requestingEmpId;
	}
}
