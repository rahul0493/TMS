/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.dao;

import java.util.List;

import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.model.HolidayList;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftAllowanceDetails;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanner;

/**
 * @author RahulY
 *
 */
public interface ShiftAllowanceDao extends GenericDao<Employee, Integer> {



	List<ShiftPlanner> GenerateShiftAllowance(int previousMonth, int currentYear);

	String approveShiftDetails(int monthId, int year, int trackId, int spocId,
			int projectId);

	List<ShiftPlanner> getShiftPlannerToGenerateShiftAllowance(int monthId,
			int projectId, int trackId, int year, int spocId);
	List<ShiftPlanner> getShiftPlannerToCalculateShiftAllowance(int monthId,
			int projectId, int trackId, int year, int spocId);

	void SaveShiftAllwance(List<ShiftAllowanceDetails> shiftAllowanceDetailList);

	List<HolidayList> getHolidayListByMonth(int monthId, int year);

	List<ShiftAllowanceDetails> getAllowance(Integer from, Integer to,
			Integer accountId, Integer projectId);

	List<Shift> getShiftDetails();

	List<ShiftPlanner> getShiftPlannerToCalculateShiftAllowanceForSpoc(
			int monthId, int projectId, int trackId, int year, int spocId);

}
