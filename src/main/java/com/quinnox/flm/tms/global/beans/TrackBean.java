package com.quinnox.flm.tms.global.beans;

import java.io.Serializable;

public class TrackBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3153457482444472973L;
	private Integer TrackId;
	private String TrackName;
	public Integer getTrackId() {
		return TrackId;
	}
	public void setTrackId(Integer trackId) {
		TrackId = trackId;
	}
	public String getTrackName() {
		return TrackName;
	}
	public void setTrackName(String trackName) {
		TrackName = trackName;
	}
	
	

}
