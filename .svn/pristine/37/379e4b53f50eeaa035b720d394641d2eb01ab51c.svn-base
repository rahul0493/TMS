package com.quinnox.flm.tms.module.model;


import java.io.Serializable;

import javax.persistence.CascadeType;
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
@Table(name="shiftDetails")
public class ShiftDetails  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6536315979775372286L;
	private Integer id;
	private String startTime;
	private String endTime;
	private Boolean pickUpEligible;
	private Boolean dropEligible;
    private Project project;
	private Shift shift;
	private Track track;
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "shiftDetails_id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="start_time")
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(name="end_time")
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	@ManyToOne(cascade = {CascadeType.DETACH},fetch=FetchType.LAZY)  // changed
	@JoinColumn(name="project_id")
	@JsonIgnore
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	//@ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
	@ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.DETACH})  // changed
	@JoinColumn(name = "shift_id")
	public Shift getShift() {
		return shift;
	}
	public void setShift(Shift shift) {
		this.shift = shift;
	}
	@ManyToOne(cascade = {CascadeType.DETACH},fetch=FetchType.LAZY)  // changed
	@JoinColumn(name="track_id")
	@JsonIgnore
	public Track getTrack() {
		return track;
	}
	public void setTrack(Track track) {
		this.track = track;
	}
	@Column(name="pickUp_Eligible")
	public Boolean getPickUpEligible() {
		return pickUpEligible;
	}
	public void setPickUpEligible(Boolean pickUpEligible) {
		this.pickUpEligible = pickUpEligible;
	}
	@Column(name="drop_Eligible")
	public Boolean getDropEligible() {
		return dropEligible;
	}
	public void setDropEligible(Boolean dropEligible) {
		this.dropEligible = dropEligible;
	}
	
}
