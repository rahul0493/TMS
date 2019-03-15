package com.quinnox.flm.tms.module.shiftplanner.model;



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

import org.hibernate.annotations.Cascade;

import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Track;

/**
 * @author AmareshP
 *
 */
@Entity
@Table(name = "Shift_Planner")
public class ShiftPlanner implements Serializable {

	private static final long serialVersionUID = -7934313164402714503L;
	private Integer id;
	private Set<ShiftPlanDetail> shiftPlanDetails;
	
	private Boolean aprroved = false;
	private Project projectId;
	private Account accountName;
   	private Integer requestingEmpId;
    private Integer monthId;
    private Integer year;
    private Track trackId;
    private Date reqDate;
    private Boolean cabGenerated =false;
	
    @Id   
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Shift_plan_id",nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.EAGER)  // changed
	@JoinColumn(name="Project_id",nullable=true)
	public Project getProjectId() {
		return projectId;
	}

	public void setProjectId(Project projectId) {
		this.projectId = projectId;
	}
	
	@OneToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY, mappedBy = "shiftPlan",orphanRemoval=true)
	public Set<ShiftPlanDetail> getShiftPlanDetails() {
		return shiftPlanDetails;
	}
	
	public void setShiftPlanDetails(Set<ShiftPlanDetail> shiftPlanDetails) {
		this.shiftPlanDetails = shiftPlanDetails;
	}

	@Column(name = "request_date")
	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date timestamp) {
		this.reqDate = timestamp;
	}

	@Column(name = "Aprrove_status", columnDefinition="Boolean default false",nullable=false)
	public Boolean getAprroved() {
		return aprroved;
	}

	public void setAprroved(Boolean aprroved) {
		this.aprroved = aprroved;
	}
    
	@Column(name = "Cab_status", columnDefinition="Boolean default false",nullable=false)
	public Boolean getCabGenerated() {
		return cabGenerated;
	}

	public void setCabGenerated(Boolean cabGenerated) {
		this.cabGenerated = cabGenerated;
	}

	@ManyToOne(fetch=FetchType.EAGER)  // changed
	@JoinColumn(name="Account_id",nullable=true)
	public Account getAccountName() {
		return accountName;
	}

	public void setAccountName(Account accountName) {
		this.accountName = accountName;
	}
	@Column(name = "month_Id")
	public Integer getMonthId() {
		return monthId;
	}

	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}
	@Column(name = "year")
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@ManyToOne(fetch=FetchType.EAGER)  // changed
	@JoinColumn(name="track",nullable=true)
	public Track getTrackId() {
		return trackId;
	}

	public void setTrackId(Track trackId) {
		this.trackId = trackId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name="requesting_Emp")
	public Integer getRequestingEmpId() {
		return requestingEmpId;
	}

	public void setRequestingEmpId(Integer requestingEmpId) {
		this.requestingEmpId = requestingEmpId;
	}
}
