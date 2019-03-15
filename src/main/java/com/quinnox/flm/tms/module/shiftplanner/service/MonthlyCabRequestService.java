/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.service;

import java.util.List;
import java.util.Map;

import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.shiftplanner.bean.CabRequestBean;

/**
 * @author RahulY
 *
 */
public interface MonthlyCabRequestService extends GenericService<EmpCabRequest, Integer> {

	
	public int saveMonthlyCabRequest(EmpCabRequest empCabRequest);

	public List<Employee> isEmployeeRequiredCab(int id);

	public List<ShiftDetails> isShiftRequired(int shiftId, int trackId);

	//public Map<Integer, List<CabRequestBean>> createCabRequest(CabRequestBean cabRequestBean);

	public void deletePreviousCabRequest(int day, int month, int year, int EmpId);

	public void cabRequest(String createJson, Integer employeeId,
			int projectId, int trackId);

	public void updateCabRequest(String createJson, int spocId, int projectId,
			int trackId);

	
}
