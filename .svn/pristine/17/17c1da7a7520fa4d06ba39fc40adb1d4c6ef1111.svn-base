package com.quinnox.flm.tms.module.model;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ImranGS
 *
 */
@Entity
@Table(name="track")
public class Track implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5026225363590398037L;
	private Integer trackId;
	private String trackName;
	private Project project;
	private Set<ShiftDetails> shiftDetails;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "track_id", unique = true, nullable = false)
	public Integer getTrackId() {
		return trackId;
	}
	public void setTrackId(Integer trackId) {
		this.trackId = trackId;
	}
	@Column(name="track_name")
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	@ManyToOne(cascade = {CascadeType.DETACH},fetch=FetchType.EAGER)  // changed
	@JoinColumn(name="project_id",nullable=false)
	@JsonIgnore
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@OneToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "track")
	public Set<ShiftDetails> getShiftDetails() {
		return shiftDetails;
	}
	
	public void setShiftDetails(Set<ShiftDetails> shiftDetails) {
		this.shiftDetails = shiftDetails;
	}
 
}
