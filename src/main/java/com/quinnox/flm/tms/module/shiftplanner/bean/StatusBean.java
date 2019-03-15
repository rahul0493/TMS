/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.bean;

/**
 * @author RahulY
 *
 */
public class StatusBean {

	private String cabGeneratedStatus ="false";
	private String approveStatus="false";
	private Integer shiftPlanId;
	private String  draftStatus="false"; 
	private Integer trackId;
	private String trackName;
	
	
	public String getCabGeneratedStatus() {
		return cabGeneratedStatus;
	}
	public void setCabGeneratedStatus(String cabGeneratedStatus) {
		this.cabGeneratedStatus = cabGeneratedStatus;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public String getDraftStatus() {
		return draftStatus;
	}
	public void setDraftStatus(String draftStatus) {
		this.draftStatus = draftStatus;
	}
	public Integer getTrackId() {
		return trackId;
	}
	public void setTrackId(Integer trackId) {
		this.trackId = trackId;
	}
	
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public Integer getShiftPlanId() {
		return shiftPlanId;
	}
	public void setShiftPlanId(Integer shiftPlanId) {
		this.shiftPlanId = shiftPlanId;
	}
	
}
