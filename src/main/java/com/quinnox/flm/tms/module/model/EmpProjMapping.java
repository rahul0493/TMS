package com.quinnox.flm.tms.module.model;

import java.io.Serializable;

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

/**
 * @author ImranGS
 *
 */
@Entity
@Table(name="emp_prj")
public class EmpProjMapping implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer projectAllocation;
	private Employee employee;
	private Project project;
	private Boolean projectStatus;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "projectallocation")
	public Integer getProjectAllocation() {
		return projectAllocation;
	}
	public void setProjectAllocation(Integer projectAllocation) {
		this.projectAllocation = projectAllocation;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)  
	@JoinColumn(name="user_id",nullable=true)
	@JsonIgnore
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)  
	@JoinColumn(name="project_id",nullable=true)
	@JsonIgnore
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@Column(name = "projectstatus")
	public Boolean getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(Boolean projectStatus) {
		this.projectStatus = projectStatus;
	}
	
}
