package com.quinnox.flm.tms.module.dao.impl;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Query;

import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.module.dao.ProjectDetailsDao;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.Track;

@Repository("projectDetailsDao")
public class ProjectDetailsDaoImpl extends GenericDaoImpl<Project, Integer> implements ProjectDetailsDao{
	
	public ProjectDetailsDaoImpl() {
		super(Project.class);
	}

	@Override
	@Transactional
	public void saveProjectDetails(Project project) {
		getCurrentSession().saveOrUpdate(project);
	}

	
	@Override
	@Transactional
	public void updateProjectDetails(Project project) {
		getCurrentSession().update(project);
		
	}

	@Override
	@Transactional
	public void deleteProjectDetails(int projectId) {
		getCurrentSession().get(Project.class, projectId);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Project> findAllProjects() {
				
		return (List<Project>) getCurrentSession().createQuery("from Project proj order by proj.projectName")
				.list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Project findProjectById(int id) {
				
		//return (Project) getCurrentSession().createQuery("from Project where id=:id").setInteger("id", id);
		return getCurrentSession().get(Project.class, id);
			
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Account> findAllAccounts() {
		return (List<Account>) getCurrentSession().createQuery("from Account acc order by acc.accountName asc")
		.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Shift> findAllShifts() {
		return (List<Shift>) getCurrentSession().createQuery("from Shift")
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Project> findProjectDetailsByAccountId(int id) {
		return (List<Project>) getCurrentSession().createQuery("from Project proj where proj.account.id=:id").setInteger("id", id)
				.list();
	}
	
	@Override
	@Transactional
	public void findShiftDetailsByProjectId(int id) {
		Query q = getCurrentSession().createQuery("Delete from ShiftDetails  shiftdetails where shiftdetails.project.id =:id").setInteger("id", id)
				;
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Project> findProjectTrackByAccountId(int id) {
		return (List<Project>) getCurrentSession().createQuery("from Project proj where proj.account.id=:id").setInteger("id", id)
				.list();
	}
	
	@Override
	@Transactional
	public int saveAccount(Account account) {
		Integer accountId = (Integer) getCurrentSession().save(account);
		return accountId;
	} 
	
	@Override
	@Transactional
	public Integer saveOrUpdateTrack(Track track) {
		if(track.getTrackId() == null)
		{
			return (Integer)getCurrentSession().save(track);
		}
		else
		{
			getCurrentSession().update(track);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Track> findTracksByProjectId(int id) {
		return (List<Track>) getCurrentSession().createQuery("from Track track where track.project.id=:id").setInteger("id", id)
				.list();
	}

	@Override
	@Transactional
	public Track findTrackByTd(int trackId) {
		return getCurrentSession().get(Track.class, trackId);
	}

	@Override
	@Transactional
	public void deleteTrackById(int trackId) {
		 //getCurrentSession().createQuery("Delete from ShiftDetails sf where sf.track.trackId=:trackId").setInteger("trackId", trackId).executeUpdate();
		
			 Track track = getCurrentSession().get(Track.class, trackId);
			 getCurrentSession().delete(track);
			
		
	}

	@Override
	@Transactional
	public void DeleteAllTracksByProjectId(int id) {
		 getCurrentSession().createQuery("Delete from Track track where track.project.id=:id").setInteger("id", id).executeUpdate();
		 
		 
		 //createQuery("Delete from Track track where track.project.id=:id").setInteger("id", id).executeUpdate();
	}

	@Override
	@Transactional
	public void DeleteShiftDetailsByTrackId(int trackId) {
		 getCurrentSession().createQuery("Delete from ShiftDetails shiftDetails where shiftDetails.track.trackId=:trackId").setInteger("trackId", trackId).executeUpdate();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Project> findProjectsByAccountId(int id) {
		return (List<Project>) getCurrentSession().createQuery("from Project proj where proj.account.id=:id").setInteger("id", id)
				.list();
	}

	@Override
	@Transactional
	public Project findProjectByName(String projectName) {
		return  (Project) getCurrentSession().createQuery("from Project proj where proj.projectName=:projectName").setString("projectName", projectName).uniqueResult();
	}

	@Override
	@Transactional
	public Account findAccountByAccountName(String accountName) {
		return  (Account) getCurrentSession().createQuery("from Account acc where acc.accountName=:accountName").setString("accountName", accountName).uniqueResult();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Shift> findShiftByType(String value) {
		return (List<Shift>) getCurrentSession().createQuery("from Shift shift where shift.enumValues=:enumValues").setString("enumValues", value)
				.list();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<EmpProjMapping> findAccountByEmpId(Integer empId) {
		return (List<EmpProjMapping>) getCurrentSession().createQuery("select empprj.project.account from EmpProjMapping empprj  where empprj.employee.id=:id order by empprj.project.account.accountName").setInteger("id", empId).list();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Project> findProjectsByEmpAcc(Integer accId,Integer empId) {
		return (List<Project>) getCurrentSession().createQuery("select empprj.project from EmpProjMapping empprj where empprj.employee.id=:empId and empprj.project.account.id=:accId")
				.setInteger("empId", empId)
				.setInteger("accId", accId)
				.list();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Project> findProjectsByEmp(Integer empId) {
		return (List<Project>) getCurrentSession().createQuery("select empprj.project from EmpProjMapping empprj where empprj.employee.id=:empId")
				.setInteger("empId", empId)
				.list();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<EmpProjMapping> findEmpPrjByEmpId(Integer empId) {
		return (List<EmpProjMapping>) getCurrentSession().createQuery("from EmpProjMapping empprj where empprj.employee.id=:empId")
				.setInteger("empId", empId)
				.list();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Project> findProjectDetailsByAccount(int id) {
		return (List<Project>) getCurrentSession().createQuery("select empprj.project from EmpProjMapping empprj where empprj.employee.id=:empId")
				.setInteger("empId", id)
				.list();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<EmpProjMapping> findEmpPrjNameByEmpId(Integer empId) {
		return (List<EmpProjMapping>) getCurrentSession().createQuery("select empprj.project from EmpProjMapping empprj where empprj.employee.id=:empId order by empprj.project.projectName")
				.setInteger("empId", empId)
				.list();
	}
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<EmpProjMapping> getEmpPrjNameByEmpId(Integer empId) {
		return (List<EmpProjMapping>) getCurrentSession().createQuery("from EmpProjMapping empprj where empprj.employee.id=:empId order by empprj.project.projectName")
				.setInteger("empId", empId)
				.list();
	}
	
}
