package com.quinnox.flm.tms.module.shiftplanner.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.shiftplanner.bean.EmployeeTrackBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.MonthBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.ProjectBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.ShiftPlannerBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.StatusBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.TrackBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.TrackShift;
import com.quinnox.flm.tms.module.model.Track;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanDetail;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanner;

public interface ShiftPlannerService extends GenericService<Employee, Integer>{

	List<Employee> findSpocDetails(int id);
	Set<TrackShift> FindShiftbyTrack(int id);
	
	
	List<ShiftPlanDetail> findShiftDetailByMonth(int id, int monthId, int trackId);
	Set<TrackBean> findTrackbyProject(int id);
	List<TrackBean> findAllProjectTrackByProgetStatusOfShiftPlannerjectId(int project_id,int trackId);
	List<Employee> getEmployeeByTrackId(Integer trackId);
	Set<ProjectBean> getProjectByAccount(int empId, int accountId);
	public List<MonthBean> getShiftPlanner(int year, int monthId, int trackId,int projectId); 
	Set<EmployeeTrackBean> getEmployeebyProject(int projectId, Integer spocId);
	Integer createShiftPlanner(int accountId,int monthId, int trackId, int year, int projectId, Map<Integer, List<ShiftPlannerBean>> map, UserProfileBean profileBean, String cabReq);
	
	//for update shiftPlan
	List<MonthBean> getShiftPlannerByProjectId(int year, int monthId,
			int projectId, Integer spocId);
	
	void deleteShiftPlan(List<ShiftPlanner> previousShiftPlan);
	Set<TrackShift> getShiftForProject(UserProfileBean profileBean, int projectId);
	//for update shiftPlan
	List<ShiftPlanner> getShiftPlannerByTrackId(UserProfileBean profileBean, int trackId,
			int projectId, int monthId, int year);
	List<ShiftPlanner> shiftPlanByProjectId(int year, int monthId,
			int projectId, Integer spocId);
	List<StatusBean> getStatusOfShiftPlanner (int projectId, int monthId, int year, Integer spocId);
	Set<TrackBean> findAllSpocTrack(Integer employeeId, int projectId);
	void deleteShiftPlanerDetails(List<ShiftPlanDetail> previousShiftPlannnerDetails);
	void deleteMasterEntery(int year, int monthId, int projectId,
			Integer employeeId, int trackId);
	public List<TrackBean> findAllProjectTrackByProjectId(int projectId,int trackId);
  }
