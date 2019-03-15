/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.bean;

import java.util.Comparator;
import java.util.List;

/**
 * @author RahulY
 *
 */
public class EmployeeTrackBean implements Comparator<EmployeeTrackBean> {

	public Integer emp_trackid;
	
	public List<EmployeeBean> emp;
	
	public String emp_trcknm;

	public Integer getEmp_trackid() {
		return emp_trackid;
	}

	

	public String getEmp_trcknm() {
		return emp_trcknm;
	}



	public void setEmp_trcknm(String emp_trcknm) {
		this.emp_trcknm = emp_trcknm;
	}



	public void setEmp_trackid(Integer emp_trackid) {
		this.emp_trackid = emp_trackid;
	}

	public List<EmployeeBean> getEmp() {
		return emp;
	}

	public void setEmp(List<EmployeeBean> emp) {
		this.emp = emp;
	}

	@Override
	public int compare(EmployeeTrackBean o1, EmployeeTrackBean o2) {
		if(o1.getEmp_trcknm()!=null && o1.getEmp_trackid()!=null ){
			int reqDComp=o1.getEmp_trcknm().compareTo(o2.getEmp_trcknm());
			if(reqDComp==0){
					}
			return reqDComp;
		}else{
			return 0;
		}
	}

}
