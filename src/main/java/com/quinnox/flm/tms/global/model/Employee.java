package com.quinnox.flm.tms.global.model;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.LocationMaster;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Track;


/**
 * @author AmareshP
 *
 */
@Entity
@Table(name = "emp_master")
public class Employee implements Serializable,Comparable<Employee> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2616854291214929823L;

	private Integer id;
	private String empcode;
	private String email;
	private String name;
	private Boolean active;
	private String gender;
	private Long phoneNumber;
	private String empDesignation;
	private Set<EmployeeAddress> employeeAddresses;
	private Set<UserRole> roles;
	private Set<Project> updatedEmpProj;
	private String managerEmail;
	private Boolean isCabRequired;
	private Set<Track> tracks;
	private Set<Track> tracksEmpMapping;
	//private Integer TrackId;
	private Set<EmpProjMapping> empProjMapping;
	private LocationMaster location;
	
  //  private Set<EmployeeTrackMapping> employeeTrackMapping;
	

	/*@Column(name = "track_id")
	public Integer getTrackId() {
		return TrackId;
	}

	public void setTrackId(Integer trackId) {
		TrackId = trackId;
	}
*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id; 
	}

	@Column(name = "empcode",unique=true)
	public String getEmpcode() {
		return empcode;
	}

	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}

	// @ManyToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "project_id", referencedColumnName =
	// "project_id",nullable = false)
	// public Project getProject() {
	// return project;
	// }
	//
	// public void setProject(Project project) {
	// this.project = project;
	// }

	@Column(name = "email",unique=true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "active")
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = "gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	@Column(name = "phoneNumber")
	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<EmployeeAddress> getEmployeeAddresses() {
		return employeeAddresses;
	}

	public void setEmployeeAddresses(Set<EmployeeAddress> employeeAddresses) {
		this.employeeAddresses = employeeAddresses;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "employee")
	public Set<EmpProjMapping> getEmpProjMapping() {
		return empProjMapping;
	}

	public void setEmpProjMapping(Set<EmpProjMapping> empProjMapping) {
		this.empProjMapping = empProjMapping;
	}

	@Column(name = "emp_designation")
	public String getEmpDesignation() {
		return empDesignation;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	@Column(name = "manager_email")
	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	@Column(name = "isCabRequired")
	public Boolean getIsCabRequired() {
		return isCabRequired;
	}

	public void setIsCabRequired(Boolean isCabRequired) {
		this.isCabRequired = isCabRequired;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "spoc_table", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "track_Id"))
	public Set<Track> getTracks() {
		return tracks;
	}

	public void setTracks(Set<Track> tracks) {
		this.tracks = tracks;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "employee_track_table", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "track_Id"))
	public Set<Track> getTracksEmpMapping() {
		return tracksEmpMapping;
	}

	public void setTracksEmpMapping(Set<Track> tracksEmpMapping) {
		this.tracksEmpMapping = tracksEmpMapping;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)  // changed
	@JoinColumn(name="loc_id",nullable=false)
	@JsonIgnore
	public LocationMaster getLocation() {
		return location;
	}
	public void setLocation(LocationMaster location) {
		this.location = location;
	}
	

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "updated_emp_proj", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	
	public Set<Project> getUpdatedEmpProj() {
		return updatedEmpProj;
	}

	public void setUpdatedEmpProj(Set<Project> updatedEmpProj) {
		this.updatedEmpProj = updatedEmpProj;
	}

	@Override
	public int compareTo(Employee emp) {
		return this.name.compareTo(emp.getName());
	}

}
