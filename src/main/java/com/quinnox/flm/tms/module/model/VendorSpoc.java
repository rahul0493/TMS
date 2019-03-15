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

/**
 * @author ImranGS
 *
 */
@Entity
@Table(name="vendorSpoc")
public class VendorSpoc implements Serializable{
	
	private static final long serialVersionUID = -326155457199516217L;
	private Integer spocId;
	private String spocName;
	private String spocMobileNumber;
	private Vendor vendor;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "spoc_id", unique = true, nullable = false)
	public Integer getSpocId() {
		return spocId;
	}
	public void setSpocId(Integer spocId) {
		this.spocId = spocId;
	}
	
	@Column(name = "spoc_name")
	public String getSpocName() {
		return spocName;
	}
	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}
	
	@Column(name = "spoc_number")
	public String getSpocMobileNumber() {
		return spocMobileNumber;
	}
	public void setSpocMobileNumber(String spocMobileNumber) {
		this.spocMobileNumber = spocMobileNumber;
	}
	
	@ManyToOne(cascade = {CascadeType.DETACH},fetch=FetchType.EAGER)  
	@JoinColumn(name="vendor_id",nullable=false)
	@JsonIgnore
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	 
}
