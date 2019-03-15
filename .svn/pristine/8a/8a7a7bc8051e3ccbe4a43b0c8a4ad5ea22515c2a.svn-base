package com.quinnox.flm.tms.module.shiftplanner.bean;

public class ProjectBean {
	private int projId;
	private String proj_nm;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + projId;
		result = prime * result + ((proj_nm == null) ? 0 : proj_nm.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProjectBean))
			return false;
		ProjectBean other = (ProjectBean) obj;
		if (projId != other.projId)
			return false;
		if (proj_nm == null) {
			if (other.proj_nm != null)
				return false;
		} else if (!proj_nm.equals(other.proj_nm))
			return false;
		return true;
	}
	public int getProjId() {
		return projId;
	}
	public void setProjId(int projId) {
		this.projId = projId;
	}
	public String getProj_nm() {
		return proj_nm;
	}
	public void setProj_nm(String proj_nm) {
		this.proj_nm = proj_nm;
	}
}
