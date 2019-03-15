/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.service;

import java.util.List;

import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.shiftplanner.bean.AllowanceBean;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftAllowanceDetails;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanner;

/**
 * @author RahulY
 *
 */
public interface ShiftAllowanceService extends GenericService<Employee, Integer>{

	List<AllowanceBean> FindAllShiftAllowance();
	public void GenerateShiftAllowance(int monthId, int year, int trackId, int spocId, int projectId);
	public List<ShiftAllowanceDetails> calculateShiftAllowance(int monthId, int year, int trackId, int spocId, int projectId);
	String approveShiftDetails(int monthId, int year, int trackId, int i,
			int projectId);
	List<ShiftPlanner> getShiftPlannerToGenerateShiftAllowance(int monthId, int projectId,
			int trackId, int year, int spocId);
	List<ShiftPlanner> getShiftPlannerToCalculateShiftAllowance(int monthId, int projectId,
			int trackId, int year, int spocId);
	List<AllowanceBean> getAllowance(Integer from, Integer to,
			Integer accountId, Integer projectId);
	List<AllowanceBean> calcAllowance(List<ShiftAllowanceDetails> shiftAllowanceDetailList, Integer fromDate, Integer toDate);
	List<ShiftPlanner> getShiftPlannerToCalculateShiftAllowanceForSpoc(
			int monthId, int projectId, int trackId, int year, int spocId);
}
