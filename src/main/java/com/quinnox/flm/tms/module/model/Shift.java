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
@Table(name="shift")
public class Shift implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1351273448072644880L;
	private Integer shiftId;
	private String shiftName;
	private String shiftInitials;
	private String enumValues;
	private Double allowance;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "shift_id", unique = true, nullable = false)
	public Integer getShiftId() {
		return shiftId;
	}
	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
	}
	@Column(name="shift_name")
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	@Column(name="shift_initials")
	public String getShiftInitials() {
		return shiftInitials;
	}
	public void setShiftInitials(String shiftInitials) {
		this.shiftInitials = shiftInitials;
	}
	@Column(name="enum_values")
	public String getEnumValues() {
		return enumValues;
	}
	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}
	@Column(name="allowance")
	public Double getAllowance() {
		return allowance;
	}
	public void setAllowance(Double allowance) {
		this.allowance = allowance;
	}
	

}
