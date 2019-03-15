package com.quinnox.flm.tms.module.shiftplanner.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quinnox.flm.tms.module.model.Track;

@Entity
@Table(name = "other_track")
public class OtherTrack implements Serializable {

	private Integer otherId;
	
	private Track trackAssignedTo;
	private ShiftPlanDetail shiftPlanDetail;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "other_track_id")
	public Integer getId() {
		return otherId;
	}
	public void setId(Integer id) {
		this.otherId = id;
	}
			
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "track_Assigned_To", nullable = false)
	@JsonIgnore
	public Track getTrackAssignedTo() {
		return trackAssignedTo;
	}
	public void setTrackAssignedTo(Track trackAssignedTo) {
		this.trackAssignedTo = trackAssignedTo;
	}
	
	
	@ManyToOne(cascade = {javax.persistence.CascadeType.ALL},fetch=FetchType.EAGER)  // changed
	@JoinColumns({@JoinColumn(name="date",nullable=false),
			@JoinColumn(name="employee",nullable=false),
			@JoinColumn(name="month",nullable=false),
	/*@JoinColumn(name="shift",nullable=false),*/
	@JoinColumn(name="Year",nullable=false)})	
	public ShiftPlanDetail getShiftPlanDetail() {
		return shiftPlanDetail;
	}
	
	public void setShiftPlanDetail(ShiftPlanDetail shiftPlanDetail) {
		this.shiftPlanDetail = shiftPlanDetail;
	}
	
	
	
}
