package com.quinnox.flm.tms.module.beans;

import java.io.Serializable;

/**
 * @author ImranGS
 *
 */

public class VendorSpocBean implements Serializable{
	
	private static final long serialVersionUID = 167566252415511932L;
	private Integer spocId;
	private String spocName;
	private String spocMobileNumber;

	public Integer getSpocId() {
		return spocId;
	}
	public void setSpocId(Integer spocId) {
		this.spocId = spocId;
	}
	
	public String getSpocName() {
		return spocName;
	}
	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}
	
	public String getSpocMobileNumber() {
		return spocMobileNumber;
	}
	public void setSpocMobileNumber(String spocMobileNumber) {
		this.spocMobileNumber = spocMobileNumber;
	}
		
}
