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
@Table(name="project")
public class Project implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String projectName;
	private Set<Track> track;
	private Account account;
	//private Set<EmpProjMapping> empProjMapping;
	
	//private Set<ShiftDetails> shiftDetails;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "project_id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "project_name")
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
//	@OneToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE }, fetch = FetchType.EAGER, mappedBy = "project")
//	public Set<ShiftDetails> getShiftDetails() {
//		return shiftDetails;
//	}
//	
//	public void setShiftDetails(Set<ShiftDetails> shiftDetails) {
//		this.shiftDetails = shiftDetails;
//	}
	
	@ManyToOne(fetch=FetchType.EAGER)  // changed
	@JoinColumn(name="account_id",nullable=false)
	@JsonIgnore
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	@OneToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Track> getTrack() {
		return track;
	}
	public void setTrack(Set<Track> track) {
		this.track = track;
	}
	
//	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "project")
//	public Set<EmpProjMapping> getEmpProjMapping() {
//		return empProjMapping;
//	}
//	public void setEmpProjMapping(Set<EmpProjMapping> empProjMapping) {
//		this.empProjMapping = empProjMapping;
//	}
}
