package com.quinnox.flm.tms.module.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.beans.TrackDetailsBean;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.Track;

public interface ProjectDetailsService extends GenericService<Project, Integer> {

	public void saveProject(Project project);
	void updateProject(Project project);
	void deleteProjectById(int projectId);
	List<Project> findAllProjects(); 
	List<Account> findAllAccounts(); 
	List<Shift> findAllShifts(); 
	public Project findProjectById(int id);
	public void findShiftDetailsByProjectId(int projectId);

	public int saveAccount(Account account); 
	public Track findTrackByTd(int trackId);
	public Integer saveOrUpdateTrack(Track track);

	public String deleteTrackById(int trackId);
	public void DeleteAllTracksByProjectId(int id);
	public void DeleteShiftDetailsByTrackId(int id);

	public List<ProjectDetailsBean> findProjectDetailsByAccountId(int id);
	public List<ProjectDetailsBean> findProjectsByAccountId(int id);
	public List<ProjectDetailsBean> findProjectTracksByAccountId(int accountId);
	public List<TrackDetailsBean> findTracksByProjectId(int id);
	public List<ProjectDetailsBean> findProjectTrackByAccountId(int accountId);
	public List<ProjectDetailsBean> findProjectsIFTrackExistsByAccountId(int accountId);
	public ProjectDetailsBean findProjectShiftByTrackId(Integer trackId);
	
	public Project  findProjectByName(String projectName);
	public Account  findAccountByAccountName(String accountName);
	
	public List<Shift> findShiftByType(String value);
	public Set<EmpProjMapping> findAccountByEmpId(Integer empId);
	public List<ProjectDetailsBean> findProjectsByEmpAcc(Integer accId,Integer empId);
	public String findProjectsByEmp(Integer empId);
	public String findProjectDetailsByAccount(int id);
	public List<EmpProjMapping> findEmpPrjNameByEmpId(Integer empId);
	public List<ProjectDetailsBean> getEmpPrjNameByEmpId(Integer empId);
	public Map<String, List<ProjectDetailsBean>> getAllProjectsAndAccounts();
}
