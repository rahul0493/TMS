package com.quinnox.flm.tms.module.shiftplanner.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.Track;

/**
 * @author RahulY
 *
 */
@Entity
@Table(name = "ShiftPlanDetails")
public class ShiftPlanDetail implements Serializable{
   
	//private Integer id;
	private ShiftKey key;
	private String emp_init;
	private String month_name;
	private Integer spocId;
    private ShiftPlanner shiftPlan;
	private Set<OtherTrack> otherTrack;
	private Track trackBelongsTo;
	private Shift shift;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trackBelongsTo", nullable = false)
	@JsonIgnore
	public Track getTrackBelongsTo() {
		return trackBelongsTo;
	}

	public void setTrackBelongsTo(Track trackBelongsTo) {
		this.trackBelongsTo = trackBelongsTo;
	}
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Shift_Id", nullable = false)
	@JsonIgnore
	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}
	
	/*//@Id
	@Column(name = "id",unique= true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}*/

	
	@OneToMany(cascade= {CascadeType.ALL},fetch = FetchType.LAZY, mappedBy = "shiftPlanDetail")
    public Set<OtherTrack> getOtherTrack() {
		return otherTrack;
	}

	public void setOtherTrack(Set<OtherTrack> otherTrack) {
		this.otherTrack = otherTrack;
	}

	@ManyToOne(fetch=FetchType.LAZY)  // changed
    @JoinColumn(name="Shift_plan_id",nullable=false)
		public ShiftPlanner getShiftPlan() {
		return shiftPlan;
	}

	public void setShiftPlan(ShiftPlanner shiftPlan) {
		this.shiftPlan = shiftPlan;
	}

	@Column(name="spoc_id")
	public Integer getSpocId() {
		return spocId;
	}

	public void setSpocId(Integer integer) {
		this.spocId = integer;
	}
	@Column(name = "month_Name")
	public String getMonth_name() {
		return month_name;
	}

	public void setMonth_name(String month_name) {
		this.month_name = month_name;
	}
	

	@EmbeddedId
	public ShiftKey getKey() {
		return key;
	}

	public void setKey(ShiftKey key) {
		this.key = key;
	}

	
	@Column(name = "Employee_Initials")
	public String getEmp_init() {
		return emp_init;
	}

	public void setEmp_init(String emp_init) {
		this.emp_init = emp_init;
	}

}