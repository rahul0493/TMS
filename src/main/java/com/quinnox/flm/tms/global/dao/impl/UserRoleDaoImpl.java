package com.quinnox.flm.tms.global.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.beans.SpocBean;
import com.quinnox.flm.tms.global.beans.UserRoleBean;
import com.quinnox.flm.tms.global.dao.UserRoleDao;
import com.quinnox.flm.tms.global.mail.TmsMailSender;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.model.UserRole;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Track;

/**
 * @author AmareshP
 *
 */
@Repository("roleDao")
@Transactional
public class UserRoleDaoImpl extends GenericDaoImpl<UserRole, Integer> implements UserRoleDao {

	
	@Autowired
	private TmsMailSender tmsMailSend;
	
	public UserRoleDaoImpl() {
		super(UserRole.class);
	}

	@Override
	public UserRole findByRole(String role) {

		return (UserRole) getCurrentSession().createQuery("select r from UserRole r where r.role=:role")
				.setString("role", role).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getAllAccount() {

		return getCurrentSession().createCriteria(Account.class).addOrder(Order.asc("accountName")).list();
	}

	@Override
	//public Boolean updateUserRole(List<UserRoleBean> userRoleBeanList) {
	public List<EmployeeBean> updateUserRole(List<UserRoleBean> userRoleBeanList) {
		Boolean status = true;

		List<EmployeeBean> empBeans = new ArrayList<EmployeeBean>();
		try {
			for (UserRoleBean userRoleBean : userRoleBeanList) {
				String roles = "USER";
				EmployeeBean empBean = new EmployeeBean();
				Employee employee = getCurrentSession().get(Employee.class, userRoleBean.getEmpId());
				if (employee != null) {
					
					empBean.setEmail(employee.getEmail());
					empBean.setEmpcode(employee.getEmpcode());
					empBean.setName(employee.getName());

					Set<UserRole> userAllRoles = employee.getRoles();
					userAllRoles.clear();
					

					if (userRoleBean.getIsAdmin()) {
						UserRole adminRole = findByRole(TmsConstant.ADMIN_USER);
						userAllRoles.add(adminRole);
						roles += ", ADMIN";
					}
					if (userRoleBean.getIsSpoc()) {
						UserRole spocRole = findByRole(TmsConstant.SPOC);
						userAllRoles.add(spocRole);
						roles += ", SPOC";
					}
					if (userRoleBean.getIsFinance()) {
						UserRole finRole = findByRole(TmsConstant.FINANCE);
						userAllRoles.add(finRole);
						roles += ", FINANCE";

					}
					if (userRoleBean.getIsSeniorManager()) {
						UserRole smRole = findByRole(TmsConstant.SENIORMANAGER);
						userAllRoles.add(smRole);
						roles += ", SENIOR_MANAGER";

					}
					if (userRoleBean.getIsSuperAdmin()) {
						UserRole saRole = findByRole(TmsConstant.SUPERADMIN);
						userAllRoles.add(saRole);
						roles += ", SUPER_ADMIN";

					}
					if (userRoleBean.getIsFrontDesk()) {
						UserRole faRole = findByRole(TmsConstant.FRONTDESK);
						userAllRoles.add(faRole);
						roles += ", FRONT_DESK";

					}
					
					UserRole userRole = findByRole(TmsConstant.USER);
					userAllRoles.add(userRole);
					employee.setRoles(userAllRoles);
					empBean.setActiveRoles(roles);
					getCurrentSession().update(employee);
					//tmsMailSend.userRoleMapMail(empBean);
					empBeans.add(empBean);
					System.out.println(userAllRoles);
				}
			}
		} catch (Exception ex) {
			status = false;
		}
		return empBeans;

	}

	@Override
	public Boolean updateUserTrack(List<SpocBean> trackMappingBean) {

		try {
			System.out.println("inside doa");
			for (SpocBean trackMapping : trackMappingBean) {

				Employee employee = getCurrentSession().get(Employee.class, trackMapping.getEmpId());
				Set<Track> tracks = employee.getTracksEmpMapping();
                 
				   tracks.clear();
				   
				for (String string : trackMapping.getTrackDetails()) {
					if(!string.isEmpty()){
                     Track track = getTrackId(string);
                  
                    	 tracks.add(track);
                     }
				}
			
				getCurrentSession().update(employee);
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
		

	}
    
	@Transactional
	private void deleteSpocTrack(int empId,int trackId) {
		try{
		getCurrentSession().createSQLQuery("delete from spoc_table where "
				+ "track_Id="+trackId+" and user_id="+empId).executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private Track getTrackId(String key) {
		System.out.println("inside trakid");
		return (Track) getCurrentSession().createQuery("from Track r where r.trackName=:trackName")
				.setString("trackName", key).uniqueResult();
		

	}

	@Override
	@Transactional
	public Boolean updateSpocTrack(List<SpocBean> spocBean) {
		
		Boolean success = false;
		try {
			System.out.println("inside update spocTrack Doa");
			for (SpocBean bean : spocBean) {
				Employee employee = getCurrentSession().get(Employee.class, bean.getEmpId());
				  Set<Track> spocSetTrack = employee.getTracks();
								for (String string : bean.getTrackDetails()) {
				                     Track track = getTrackId(string);
				                     spocSetTrack.add(track);
				                    
				                     }
								 employee.setTracks(spocSetTrack);
								 for (Track track : employee.getTracks()) {
									System.out.println(track.getTrackName());
								}
				getCurrentSession().update(employee);
				success = true;
				System.out.println("Going out of SpocTrack Doa"+spocSetTrack);
				
			
			}
			}catch (Exception ex) {
			ex.printStackTrace();
		}
		return success;

	}

	@Override
	@Transactional
	public Employee getRoleByEmpId(Integer id) {
		return (Employee) getCurrentSession().createQuery("from Employee emp where emp.id=:id").setInteger("id", id)
				.uniqueResult();
	}

	@Override
	@Transactional
	public void deleteSpocTrack(List<SpocBean> spocBean) {
		for (SpocBean bean : spocBean) {
			
		
		Employee employee = getCurrentSession().get(Employee.class, bean.getEmpId());
		Set<Track> tracks = employee.getTracks();
		//Set<Track> spocSetTrack = new HashSet<Track>();
              			      
		  for(Track spocTrack : employee.getTracks()){
			  
                 if(spocTrack.getProject().getId() == bean.getProjectId()){
                	 System.out.println("spocTrack.getTrackId()"+spocTrack.getTrackId());
                	deleteSpocTrack(employee.getId(),spocTrack.getTrackId());
                 }
		  }
	}
	}
}
