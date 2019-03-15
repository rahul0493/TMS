package com.quinnox.flm.tms.module.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.beans.ShiftDetailsBean;
import com.quinnox.flm.tms.module.beans.TrackDetailsBean;
import com.quinnox.flm.tms.module.dao.ProjectDetailsDao;
import com.quinnox.flm.tms.module.dao.ShiftDetailsDao;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.model.Track;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.shiftplanner.bean.ProjectBean;

@Service("projectService")
public class ProjectDetailsServiceImpl extends GenericServiceImpl<Project, Integer> implements ProjectDetailsService {

	private ProjectDetailsDao projectDetailsDao;
	
	private ShiftDetailsDao shiftDetailsDao;

	@Autowired
	public ProjectDetailsServiceImpl(
			@Qualifier("projectDetailsDao") ProjectDetailsDao projectDetailsDao) {
		super(projectDetailsDao);
		this.projectDetailsDao = projectDetailsDao;
	}

	@Override
	public void saveProject(Project project) {
		projectDetailsDao.saveProjectDetails(project);
	}

	@Override
	public void updateProject(Project project) {
		projectDetailsDao.updateProjectDetails(project);
	}

	@Override
	public void deleteProjectById(int projectId) {
		projectDetailsDao.deleteProjectDetails(projectId);
	}

	@Override
	public List<Project> findAllProjects() {
		return projectDetailsDao.findAllProjects();
	}

	@Override
	public Project findProjectById(int id) {
		return projectDetailsDao.findProjectById(id);
	}

	@Override
	public List<Account> findAllAccounts() {
		return projectDetailsDao.findAllAccounts();
	}

	@Override
	public List<Shift> findAllShifts() {
		return projectDetailsDao.findAllShifts();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<ProjectDetailsBean> findProjectDetailsByAccountId(int id) {

		List<Project> projectList =  projectDetailsDao.findProjectDetailsByAccountId(id);

		List<ProjectDetailsBean> projectBeanList = new ArrayList<ProjectDetailsBean>();
		Set<TrackDetailsBean> trackBeanSet = null;

		ProjectDetailsBean projectBean = null;

		for(Project project : projectList) {

			if(project.getTrack().size() <= 0)
			{
				continue;
			}

			projectBean = new ProjectDetailsBean();
			projectBean.setAccountId(project.getAccount().getId());   
			projectBean.setAccountName(project.getAccount().getAccountName());   
			projectBean.setProjectId(project.getId());
			projectBean.setProjectName(project.getProjectName());

			ShiftDetailsBean shiftBean = null;
			trackBeanSet = new HashSet<TrackDetailsBean>();
			TrackDetailsBean trackBean = null;
			boolean shiftExists = true;
			for(Track trackDetails : project.getTrack()) {
				Set<ShiftDetailsBean> shiftBeanSet = new HashSet<ShiftDetailsBean>();
				trackBean = new TrackDetailsBean();
				trackBean.setTrackDetailsId(trackDetails.getTrackId());
				trackBean.setTrackName(trackDetails.getTrackName());
				if(trackDetails.getShiftDetails().size() <= 0)
				{
					shiftExists = false;
					continue;
				}
				for(ShiftDetails shiftDetails : trackDetails.getShiftDetails()) {
					shiftExists = true;
					shiftBean = new ShiftDetailsBean();
					shiftBean.setShiftId(shiftDetails.getShift().getShiftId());
					shiftBean.setShiftDetailsId(shiftDetails.getId());
					shiftBean.setShiftInitials(shiftDetails.getShift().getShiftInitials());
					shiftBean.setShiftName(shiftDetails.getShift().getShiftName());
					shiftBean.setStartTime(shiftDetails.getStartTime());
					shiftBean.setEndTime(shiftDetails.getEndTime());
					shiftBean.setPickup(shiftDetails.getPickUpEligible());
					shiftBean.setDrop(shiftDetails.getDropEligible());
					shiftBeanSet.add(shiftBean);
				}

				if(shiftExists)
				{
					trackBean.setShiftDetails(shiftBeanSet);
					trackBeanSet.add(trackBean);
				}
			}
			if( !trackBeanSet.isEmpty())
			{
				projectBean.setTrackDetails(trackBeanSet);
				projectBeanList.add(projectBean);
			}
		}
		return projectBeanList;
	}

	@Override
	public void findShiftDetailsByProjectId(int projectId) {
		projectDetailsDao.findShiftDetailsByProjectId(projectId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<ProjectDetailsBean> findProjectTrackByAccountId(int accountId) {

		Set<TrackDetailsBean> trackBeanSet = new HashSet<TrackDetailsBean>();
		List<ProjectDetailsBean> projectList =  new ArrayList<ProjectDetailsBean>();
		final List<Project> projects = projectDetailsDao.findProjectTrackByAccountId(accountId);
		for(Project projModel : projects)
		{
			ProjectDetailsBean projBean = new ProjectDetailsBean();
			projBean.setAccountId(accountId);
			projBean.setAccountName(projModel.getAccount().getAccountName());
			projBean.setProjectId(projModel.getId());
			projBean.setProjectName(projModel.getProjectName());
			TrackDetailsBean trackBean = null;
			for(Track trackDetails : projModel.getTrack()) {
				trackBean = new TrackDetailsBean();
				trackBean.setTrackDetailsId(trackDetails.getTrackId());
				trackBean.setTrackName(trackDetails.getTrackName());
				trackBeanSet.add(trackBean);
			}
			projBean.setTrackDetails(trackBeanSet);
			projectList.add(projBean);
		}
		return projectList;
	}

	@Override
	public int saveAccount(Account account) {
		return projectDetailsDao.saveAccount(account);

	}

	@Override
	public Integer saveOrUpdateTrack(Track track) {
		return projectDetailsDao.saveOrUpdateTrack(track);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<TrackDetailsBean> findTracksByProjectId(int id) {

		List<TrackDetailsBean> trackList =  new ArrayList<TrackDetailsBean>();
		final List<Track> tracks = projectDetailsDao.findTracksByProjectId(id);

		for(Track trackModel : tracks)
		{
			TrackDetailsBean trackBean = new TrackDetailsBean();
			trackBean.setTrackDetailsId(trackModel.getTrackId());
			trackBean.setTrackName(trackModel.getTrackName());
			trackList.add(trackBean);
		}
		return trackList;
	}

	@Override
	public Track findTrackByTd(int trackId) {
		return projectDetailsDao.findTrackByTd(trackId);
	}

	@Override
	public String deleteTrackById(int trackId) {
		try {
		
		     projectDetailsDao.deleteTrackById(trackId);
		     return "success";
         
		}catch(final DataAccessException ex) {
			
			return "error";
		}

	}

	@Override
	public void DeleteAllTracksByProjectId(int id) {
		projectDetailsDao.DeleteAllTracksByProjectId(id);		
	}

	@Override
	public void DeleteShiftDetailsByTrackId(int id) {
		projectDetailsDao.DeleteShiftDetailsByTrackId(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<ProjectDetailsBean> findProjectsByAccountId(int id) {
		List<ProjectDetailsBean> projectbeans = new ArrayList<ProjectDetailsBean>();
		List<Project> projects = projectDetailsDao.findProjectsByAccountId(id);
		for(Project project : projects)
		{
			ProjectDetailsBean bean = new ProjectDetailsBean();
			bean.setProjectId(project.getId());
			bean.setProjectName(project.getProjectName());
			projectbeans.add(bean);
		}
		return projectbeans;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<ProjectDetailsBean> findProjectTracksByAccountId(int accountId) {

		List<ProjectDetailsBean> projectbeans = new ArrayList<ProjectDetailsBean>();
		List<Project> projects = projectDetailsDao.findProjectsByAccountId(accountId);

		for(Project project : projects)
		{
			List<TrackDetailsBean> trackbeans = new ArrayList<TrackDetailsBean>();
			ProjectDetailsBean bean = new ProjectDetailsBean();
			bean.setProjectId(project.getId());
			bean.setProjectName(project.getProjectName());

			for(Track track : project.getTrack()) {
				TrackDetailsBean trackBean = new TrackDetailsBean();
				trackBean.setTrackDetailsId(track.getTrackId());
				trackBean.setTrackName(track.getTrackName());
				trackbeans.add(trackBean);
			}
			Collections.sort(trackbeans);
			bean.setTrackDetailsList(trackbeans);
			projectbeans.add(bean);
		}
		return projectbeans;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<ProjectDetailsBean> findProjectsIFTrackExistsByAccountId(int accountId) {
		List<ProjectDetailsBean> projectbeans = new ArrayList<ProjectDetailsBean>();
		
		List<Project> projects = projectDetailsDao.findProjectsByAccountId(accountId);
		for(Project project : projects)
		{
			Set<TrackDetailsBean> trackbeans = new HashSet<TrackDetailsBean>();
			if(project.getTrack().size() <= 0)
			{
				continue;
			}
			ProjectDetailsBean bean = new ProjectDetailsBean();
			bean.setProjectId(project.getId());
			bean.setProjectName(project.getProjectName());

			for(Track track : project.getTrack()) {
				TrackDetailsBean trackBean = new TrackDetailsBean();
				trackBean.setTrackDetailsId(track.getTrackId());
				trackBean.setTrackName(track.getTrackName());
				trackbeans.add(trackBean);
			}
			bean.setTrackDetails(trackbeans);
			projectbeans.add(bean);
		}
		return projectbeans;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public ProjectDetailsBean findProjectShiftByTrackId(Integer trackId) {
		
		List<ShiftDetailsBean> shiftDetailsBeans =  new ArrayList<ShiftDetailsBean>();
		List<ShiftDetails> shiftDetails = shiftDetailsDao.findByTrackId(trackId);
		ProjectDetailsBean projectBean = new ProjectDetailsBean();
		Project project = new Project();
		
		for(ShiftDetails shiftDetailsModel : shiftDetails)
		{
			ShiftDetailsBean shiftDetailsBean = new ShiftDetailsBean();
			project = shiftDetailsModel.getProject();
			projectBean.setProjectId(project.getId());
			projectBean.setAccountId(project.getAccount().getId());
			projectBean.setTrackId(trackId);

			shiftDetailsBean.setEndTime(shiftDetailsModel.getEndTime());
			shiftDetailsBean.setStartTime(shiftDetailsModel.getStartTime());
			shiftDetailsBean.setShiftDetailsId(shiftDetailsModel.getId());
			shiftDetailsBean.setShiftInitials(shiftDetailsModel.getShift().getShiftInitials());
			shiftDetailsBean.setShiftId(shiftDetailsModel.getShift().getShiftId());
			shiftDetailsBean.setShiftName(shiftDetailsModel.getShift().getShiftName());
			shiftDetailsBean.setPickup(shiftDetailsModel.getPickUpEligible());
			shiftDetailsBean.setDrop(shiftDetailsModel.getDropEligible());
			shiftDetailsBeans.add(shiftDetailsBean);
		}
		return projectBean;
		
	}

	@Override
	public Project findProjectByName(String projectName) {
		return projectDetailsDao.findProjectByName(projectName);
	}

	@Override
	public Account findAccountByAccountName(String accountName) {
		return projectDetailsDao.findAccountByAccountName(accountName);
	}

	@Override
	public List<Shift> findShiftByType(String value) {
		return  projectDetailsDao.findShiftByType(value);
	}

	@Override
	public Set<EmpProjMapping> findAccountByEmpId(Integer empId) {
		List<EmpProjMapping> accList =  projectDetailsDao.findAccountByEmpId(empId);
		Set<EmpProjMapping> accSet = new LinkedHashSet<EmpProjMapping>(accList);
		return  accSet;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<ProjectDetailsBean> findProjectsByEmpAcc(Integer accId,Integer empId) {
		List<ProjectDetailsBean> projectbeans = new ArrayList<ProjectDetailsBean>();
		List<Project> projects = projectDetailsDao.findProjectsByEmpAcc(accId,empId);
		for(Project project : projects)
		{
			List<TrackDetailsBean> trackbeans = new ArrayList<TrackDetailsBean>();
			ProjectDetailsBean bean = new ProjectDetailsBean();
			bean.setProjectId(project.getId());
			bean.setProjectName(project.getProjectName());
			for(Track track : project.getTrack()) {
				
				TrackDetailsBean trackBean = new TrackDetailsBean();
				trackBean.setTrackDetailsId(track.getTrackId());
				trackBean.setTrackName(track.getTrackName());
				trackbeans.add(trackBean);
			}
			Collections.sort(trackbeans);
			bean.setTrackDetailsList(trackbeans);
			projectbeans.add(bean);
		}
		return projectbeans;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public String findProjectsByEmp(Integer empId) {
		List<ProjectDetailsBean> projectbeans = new ArrayList<ProjectDetailsBean>();
		List<Project> accList = projectDetailsDao.findProjectsByEmp(empId);
		
		for(Project project : accList)
		{
			List<TrackDetailsBean> trackbeans = new ArrayList<TrackDetailsBean>();
			ProjectDetailsBean bean = new ProjectDetailsBean();
			bean.setProjectId(project.getId());
			bean.setProjectName(project.getProjectName());
			bean.setAccountName(project.getAccount().getAccountName());

			for(Track track : project.getTrack()) {
				TrackDetailsBean trackBean = new TrackDetailsBean();
				trackBean.setTrackDetailsId(track.getTrackId());
				trackBean.setTrackName(track.getTrackName());
				if(track.getShiftDetails().size() > 0)
				trackBean.setShiftDetailsExits(true);
				else
				trackBean.setShiftDetailsExits(false);	
				trackbeans.add(trackBean);
			}
		    Collections.sort(trackbeans);
			bean.setTrackDetailsList(trackbeans);
			projectbeans.add(bean);
		}
		
		
		
		Map<String, List<ProjectDetailsBean>> grpByAcc = projectbeans.stream()
				.collect(Collectors.groupingBy(p -> p.getAccountName(),Collectors.toList()));
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(grpByAcc);
			System.out.println(arrayToJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return arrayToJson;
	
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public String findProjectDetailsByAccount(int id) {
		
		List<Project> projectList =null;
		if(id==0) {
			projectList =  projectDetailsDao.findAllProjects();
		}else {
			projectList =  projectDetailsDao.findProjectDetailsByAccount(id);
		}
		

		List<ProjectDetailsBean> projectBeanList = new ArrayList<ProjectDetailsBean>();
		List<TrackDetailsBean> trackBeanList = null;

		ProjectDetailsBean projectBean = null;

		for(Project project : projectList) {

			if(project.getTrack().size() <= 0)
			{
				continue;
			}

			projectBean = new ProjectDetailsBean();
			projectBean.setAccountId(project.getAccount().getId());   
			projectBean.setAccountName(project.getAccount().getAccountName());   
			projectBean.setProjectId(project.getId());
			projectBean.setProjectName(project.getProjectName());

			ShiftDetailsBean shiftBean = null;
			trackBeanList = new ArrayList<TrackDetailsBean>();
			TrackDetailsBean trackBean = null;
			boolean shiftExists = true;
			for(Track trackDetails : project.getTrack()) {
				List<ShiftDetailsBean> shiftBeanList = new ArrayList<ShiftDetailsBean>();
				trackBean = new TrackDetailsBean();
				trackBean.setTrackDetailsId(trackDetails.getTrackId());
				trackBean.setTrackName(trackDetails.getTrackName());
				if(trackDetails.getShiftDetails().size() <= 0)
				{
					shiftExists = false;
					continue;
				}
				for(ShiftDetails shiftDetails : trackDetails.getShiftDetails()) {
					shiftExists = true;
					shiftBean = new ShiftDetailsBean();
					shiftBean.setShiftId(shiftDetails.getShift().getShiftId());
					shiftBean.setShiftDetailsId(shiftDetails.getId());
					shiftBean.setShiftInitials(shiftDetails.getShift().getShiftInitials());
					shiftBean.setShiftName(shiftDetails.getShift().getShiftName());
					shiftBean.setStartTime(shiftDetails.getStartTime());
					shiftBean.setEndTime(shiftDetails.getEndTime());
					shiftBean.setPickup(shiftDetails.getPickUpEligible());
					shiftBean.setDrop(shiftDetails.getDropEligible());
					shiftBeanList.add(shiftBean);
				}

				if(shiftExists)
				{
					Collections.sort(shiftBeanList);
					trackBean.setShiftDetailsList(shiftBeanList);
						
					trackBeanList.add(trackBean);
				}
			}
			if( !trackBeanList.isEmpty())
			{
				Collections.sort(trackBeanList);
				projectBean.setTrackDetailsList(trackBeanList);
				projectBeanList.add(projectBean);
			}
		}
		
		
		Map<String, List<ProjectDetailsBean>> grpByAcc = projectBeanList.stream()
				.collect(Collectors.groupingBy(p -> p.getAccountName(),Collectors.toList()));
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(grpByAcc);
			System.out.println(arrayToJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return arrayToJson;
	}

	@Override
	public List<EmpProjMapping> findEmpPrjNameByEmpId(Integer empId) {
		return projectDetailsDao.findEmpPrjNameByEmpId(empId);
	}
	
	@Override
	public List<ProjectDetailsBean> getEmpPrjNameByEmpId(Integer empId) {
		List<ProjectDetailsBean> beanLists = new ArrayList<ProjectDetailsBean>();
		List<EmpProjMapping> employeeProjectLists = projectDetailsDao.getEmpPrjNameByEmpId(empId);
		for (EmpProjMapping empProj : employeeProjectLists) {
			ProjectDetailsBean projBean = new ProjectDetailsBean();
			projBean.setProjectId(empProj.getProject().getId());
			projBean.setProjectName(empProj.getProject().getProjectName());
			beanLists.add(projBean);
		}
		
		
		return beanLists;
	}

	@Override
	public Map<String, List<ProjectDetailsBean>> getAllProjectsAndAccounts() {
		List<ProjectDetailsBean> projectbeans = new ArrayList<ProjectDetailsBean>();
		List<Project> projList = projectDetailsDao.findAllProjects();
		
		for(Project project : projList)
		{
		
			ProjectDetailsBean bean = new ProjectDetailsBean();
			bean.setProjectId(project.getId());
			bean.setProjectName(project.getProjectName());
			bean.setAccountName(project.getAccount().getAccountName());
			projectbeans.add(bean);
		}
		
		
		
		Map<String, List<ProjectDetailsBean>> grpByAcc = projectbeans.stream()
				.collect(Collectors.groupingBy(p -> p.getAccountName(),Collectors.toList()));
		return grpByAcc;
	}
	

}
