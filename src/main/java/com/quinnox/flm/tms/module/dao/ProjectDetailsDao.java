package com.quinnox.flm.tms.module.dao;

import java.util.List;
import java.util.Set;


import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.model.Track;

public interface ProjectDetailsDao  extends GenericDao<Project, Integer>  {
	public void saveProjectDetails(Project project);
	public void updateProjectDetails(Project project);
	public void deleteProjectDetails(int projectId);
	public List<Project> findAllProjects();
	public Project findProjectById(int id);
	public List<Account> findAllAccounts();
	public List<Shift> findAllShifts(); 
	public void findShiftDetailsByProjectId(int projectId);
	public List<Project> findProjectTrackByAccountId(int accountId);
	public int saveAccount(Account account); 
	public Track findTrackByTd(int trackId);
	public Integer saveOrUpdateTrack(Track track);
	public List<Track> findTracksByProjectId(int id);
	public void deleteTrackById(int trackId);
	public void DeleteAllTracksByProjectId(int id);
	public void DeleteShiftDetailsByTrackId(int id);
	
	public List<Project> findProjectDetailsByAccountId(int id);
	public List<Project> findProjectsByAccountId(int id);
	public Project  findProjectByName(String projectName);
	public Account  findAccountByAccountName(String accountName);
	public List<Shift> findShiftByType(String value);
	public List<EmpProjMapping> findAccountByEmpId(Integer empId);
	public List<Project> findProjectsByEmpAcc(Integer accId,Integer empId);
	public List<Project> findProjectsByEmp(Integer empId);
	public List<EmpProjMapping> findEmpPrjByEmpId(Integer empId);
	public List<Project> findProjectDetailsByAccount(int id);
	public List<EmpProjMapping> findEmpPrjNameByEmpId(Integer empId);
	public List<EmpProjMapping> getEmpPrjNameByEmpId(Integer empId);
	
}
