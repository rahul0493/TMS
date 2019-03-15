/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.dao;

import java.util.List;

import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.ShiftDetails;

/**
 * @author RahulY
 *
 */
public interface MonthlyCabRequestDao extends GenericDao<EmpCabRequest, Integer>{

	
	public Integer saveCabRequest(EmpCabRequest empCabRequest);

	public List<Employee> isEmployeeRequiredCab(int id);

	public List<ShiftDetails> isShiftRequired(int shiftId, int trackId);

	public List<Integer> deletePreviousCabRequest(int month, int year, int empId);

	public String DeleteFutureCabrequest(Integer integer);

	public EmployeeAddress getOfficeAddress();
	
}
