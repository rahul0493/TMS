package com.quinnox.flm.tms.module.beans;

import java.io.Serializable;

public class SubViewRequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7855409393543540252L;
	private int requestId;
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	private String scheduledDate;
	
}
