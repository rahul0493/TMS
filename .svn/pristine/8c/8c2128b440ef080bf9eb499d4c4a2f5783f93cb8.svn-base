package com.quinnox.flm.tms.module.shiftplanner.dao;

import java.sql.SQLException;
import java.util.List;

import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanDetail;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanner;
import com.quinnox.flm.tms.module.model.Track;

public interface ShiftPlannerDao extends GenericDao<Employee, Integer> {

	public List<Employee> findAllAccounts(int id);

	

	public List<ShiftDetails> findShiftByTrack(int id);

	
	public List<ShiftPlanDetail> getShiftDetails(int year, int monthId, int trackId);
	

	public Integer saveShiftPlan(ShiftPlanner shiftPlanner) throws SQLException;
	List<Employee> getEmployeeByTrackId(Integer trackId);

	public List<Project> getProjectByAccount(int accountId, int accountId2);

	public List<Track> findTrackbyProject(int id);

	public List<ShiftPlanner> findShiftDetailByProjectId(int year, int monthId,
			int projectId, Integer spocId);

	public List<ShiftPlanner> getMonthlyShiftPlan(int int1, int trackId,
			int int2, Integer employeeId);



	public void deleteShiftPlanDetails(List<ShiftPlanDetail> shiftPlanDetails);

	public int deleteMasterRecord(Integer spocId, int trackId,
			int projectId, int monthId, int year);

	public int deleteShiftPlannerDetails(List<ShiftPlanner> previousShiftPlan);

	public List<ShiftPlanner> getStatusOfShiftPlanner(int projectId, int monthId, int year,
			Integer spocId);

	public List<Shift> getPrevilegedandHolidayShift();

	
}
