package com.quinnox.flm.tms.module.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ProjectDetailsBean implements Serializable , Comparable<ProjectDetailsBean> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3968594615729203326L;
	//private int trackId;
	//private String trackName;
	private int projectId;
	private String projectName;
	private int accountId;
	private String accountName;
	private int trackId;
	private String trackName; 
	//private Set<ShiftDetailsBean> shiftDetails;
	private Set<TrackDetailsBean> trackDetails;
	
	private List<TrackDetailsBean> trackDetailsList;
/*	private Set<TrackDetailsBean> trackDetails;
*/	
	
//	public int getTrackId() {
//		return trackId;
//	}
//	public void setTrackId(int trackId) {
//		this.trackId = trackId;
//	}
//	public String getTrackName() {
//		return trackName;
//	}
//	public void setTrackName(String trackName) {
//		this.trackName = trackName;
//	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
//	public Set<ShiftDetailsBean> getShiftDetails() {
//		return shiftDetails;
//	}
//	public void setShiftDetails(Set<ShiftDetailsBean> shiftDetails) {
//		this.shiftDetails = shiftDetails;
//	}
	public Set<TrackDetailsBean> getTrackDetails() {
		return trackDetails;
	}
	public void setTrackDetails(Set<TrackDetailsBean> trackDetails) {
		this.trackDetails = trackDetails;
	}
	public List<TrackDetailsBean> getTrackDetailsList() {
		return trackDetailsList;
	}
	public void setTrackDetailsList(List<TrackDetailsBean> trackDetailsList) {
		this.trackDetailsList = trackDetailsList;
	}
/*	public Set<TrackDetailsBean> getTrackDetails() {
		return trackDetails;
	}
	public void setTrackDetails(Set<TrackDetailsBean> trackDetails) {
		this.trackDetails = trackDetails;
	}*/
	public int getTrackId() {
		return trackId;
	}
	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	@Override
	public int compareTo(ProjectDetailsBean projBean) {
		return this.projectName.compareToIgnoreCase(projBean.getProjectName());
	}
}
