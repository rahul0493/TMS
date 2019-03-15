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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ImranGS
 *
 */
@Entity
@Table(name="vendor")
public class Vendor implements Serializable{
	
	private static final long serialVersionUID = -326155457199516217L;
	private Integer vendorId;
	private String travelsName;
	private String ownerName;
	private String officeAddress;
	private String officeNumber;
	private Set<VendorSpoc> vendorSpocs;
	private double rateWithoutEscort;
	private double rateWithEscort;
	//private TripSheetDetails tripSheet;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "vendor_id", unique = true, nullable = false)
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
	@Column(name = "travels_name")
	public String getTravelsName() {
		return travelsName;
	}
	public void setTravelsName(String travelsName) {
		this.travelsName = travelsName;
	}
	
	@Column(name = "office_address")
	public String getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}
	
	@Column(name = "office_number")
	public String getOfficeNumber() {
		return officeNumber;
	}
	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}
	
	@OneToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE }, fetch = FetchType.EAGER, mappedBy = "vendor")
	public Set<VendorSpoc> getVendorSpocs() {
		return vendorSpocs;
	}
	public void setVendorSpocs(Set<VendorSpoc> vendorSpocs) {
		this.vendorSpocs = vendorSpocs;
	}
	@Column(name = "owner_name")
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public double getRateWithoutEscort() {
		return rateWithoutEscort;
	}
	public void setRateWithoutEscort(double rateWithoutEscort) {
		this.rateWithoutEscort = rateWithoutEscort;
	}
	public double getRateWithEscort() {
		return rateWithEscort;
	}
	public void setRateWithEscort(double rateWithEscort) {
		this.rateWithEscort = rateWithEscort;
	}
//	 @OneToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE }, fetch = FetchType.EAGER, mappedBy = "vendorDetails")
//	public TripSheetDetails getTripSheet() {
//		return tripSheet;
//	}
//	public void setTripSheet(TripSheetDetails tripSheet) {
//		this.tripSheet = tripSheet;
//	}
	 
}
