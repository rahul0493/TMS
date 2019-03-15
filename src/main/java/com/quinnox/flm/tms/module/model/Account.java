package com.quinnox.flm.tms.module.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author SaleenaK
 *
 */
@Entity
@Table(name="account")
public class Account implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String accountName;
	//private Set<Project> project;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "account_name")
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
//  @OneToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE }, fetch = FetchType.EAGER, mappedBy = "account")
//	public Set<Project> getProject() {
//		return project;
//	}
//	public void setProject(Set<Project> project) {
//		this.project = project;
//	}
	
}
