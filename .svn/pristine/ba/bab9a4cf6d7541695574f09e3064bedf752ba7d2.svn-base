/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.model;

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
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.Track;

/**
 * @author RahulY
 *
 */
@Entity
@Table(name = "shift_allowance_details")
public class ShiftAllowanceDetails {

	private Integer id;
	private Employee empId;
	private String employeeName;
	private Integer totalNoOfDaysWorked;
	private Integer totalNoOfNightShiftWorked;
	private Integer totalNoOfDayShiftWorked;
	private Integer totalNoOfPublicHolidayWorked;
	private Integer totalNoOfNationalHolidayWorked;
	private Project projectId;
	private Track trackId;
	private Integer monthId;
	private Employee spocId;
	private ShiftPlanner shiftPlanId;
	
	 @Id   
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Shift_Allowance_id",nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", nullable = false)
	public Employee getEmpId() {
		return empId;
	}
	public void setEmpId(Employee empId) {
		this.empId = empId;
	}
	@Column(name = "employeeName")
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	@Column(name = "totalNoOfDaysWorked")
	public Integer getTotalNoOfDaysWorked() {
		return totalNoOfDaysWorked;
	}
	public void setTotalNoOfDaysWorked(Integer totalNoOfDaysWorked) {
		this.totalNoOfDaysWorked = totalNoOfDaysWorked;
	}
	
	@Column(name = "totalNoOfNightShiftWorked")
	public Integer getTotalNoOfNightShiftWorked() {
		return totalNoOfNightShiftWorked;
	}
	public void setTotalNoOfNightShiftWorked(Integer totalNoOfNightShiftWorked) {
		this.totalNoOfNightShiftWorked = totalNoOfNightShiftWorked;
	}
	@Column(name = "totalNoOfDayShiftWorked")
	public Integer getTotalNoOfDayShiftWorked() {
		return totalNoOfDayShiftWorked;
	}
	
	public void setTotalNoOfDayShiftWorked(Integer totalNoOfDayShiftWorked) {
		this.totalNoOfDayShiftWorked = totalNoOfDayShiftWorked;
	}
	@Column(name = "totalNoOfPublicHolidayWorked")
	public Integer getTotalNoOfPublicHolidayWorked() {
		return totalNoOfPublicHolidayWorked;
	}
	public void setTotalNoOfPublicHolidayWorked(Integer totalNoOfPublicHolidayWorked) {
		this.totalNoOfPublicHolidayWorked = totalNoOfPublicHolidayWorked;
	}
	@Column(name = "totalNoOfNationalHolidayWorked")
	public Integer getTotalNoOfNationalHolidayWorked() {
		return totalNoOfNationalHolidayWorked;
	}
	public void setTotalNoOfNationalHolidayWorked(
			Integer totalNoOfNationalHolidayWorked) {
		this.totalNoOfNationalHolidayWorked = totalNoOfNationalHolidayWorked;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "projectId", nullable = false)
	public Project getProjectId() {
		return projectId;
	}
	public void setProjectId(Project projectId) {
		this.projectId = projectId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trackId", nullable = false)
	public Track getTrackId() {
		return trackId;
	}
	public void setTrackId(Track trackId) {
		this.trackId = trackId;
	}
	
	@Column(name = "monthId")
	public Integer getMonthId() {
		return monthId;
	}
	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "spoc_Id", nullable = false)
	public Employee getSpocId() {
		return spocId;
	}
	public void setSpocId(Employee spocId) {
		this.spocId = spocId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shiftPlanId", nullable = false)
	public ShiftPlanner getShiftPlanId() {
		return shiftPlanId;
	}
	public void setShiftPlanId(ShiftPlanner shiftPlanId) {
		this.shiftPlanId = shiftPlanId;
	}

	
}
