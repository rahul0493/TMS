package com.quinnox.flm.tms.global.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author AmareshP
 *
 */
@Entity
@Table(name = "role")
public class UserRole implements Serializable,Comparable<UserRole> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1563941899565139896L;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="role_id")
	private Integer id;
	
	@Column(name="role")
	private String role;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public int compareTo(UserRole userRole) {
		return this.role.compareTo(userRole.getRole());
	}
}
