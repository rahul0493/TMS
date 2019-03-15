package com.quinnox.flm.tms.module.shiftplanner.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.Track;

@Embeddable
public class ShiftKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int month;
	private int date;
	private Employee employee;
	private int Year;
	
	//private Integer id;

	
	
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE.AUTO)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
*/	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trackAssignedTO", nullable = true)
	@JsonIgnore
	public Track getTrackAssignedTO() {
		return trackAssignedTO;
	}

	public void setTrackAssignedTO(Track trackAssignedTO) {
		this.trackAssignedTO = trackAssignedTO;
	}*/

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Employee_Id", nullable = false)
	@JsonIgnore
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Column(name = "Year")
	public int getYear() {
		return Year;
	}

	public void setYear(int year) {
		Year = year;
	}

	@Column(name = "Month")
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	@Column(name = "Day")
	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ShiftKey [month=" + month + ", date=" + date + ", employee="
				+ employee + ", Year=" + Year + "]";
	}

}
