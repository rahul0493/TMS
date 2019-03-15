package com.quinnox.flm.tms.global.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.beans.SpocBean;
import com.quinnox.flm.tms.global.beans.UserRoleBean;
import com.quinnox.flm.tms.global.dao.EmployeeDao;
import com.quinnox.flm.tms.global.dao.UserRoleDao;
import com.quinnox.flm.tms.global.mail.TmsMailSender;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.model.UserRole;
import com.quinnox.flm.tms.global.service.UserRoleService;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.Account;

/**
 * @author AmareshP
 *
 */

@Service("roleService")
public class UserRoleServiceImpl  extends GenericServiceImpl<UserRole, Integer> implements UserRoleService{

	private UserRoleDao roleDao;
	public UserRoleServiceImpl(@Qualifier("roleDao") UserRoleDao roleDao) {
		super(roleDao);
		this.roleDao=roleDao;
	}
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private TmsMailSender tmsMailSender;
	
	@Override
	public UserRole findByRole(String role) {
	return	roleDao.findByRole(role);
	}

	@Override
	public List<Account> getAllAccount() {
		return roleDao.getAllAccount();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<UserRoleBean> findEmployeeRolesByProjectId(Integer projectId) {
		List<Employee> empList = employeeDao.findEmployeeIdsByProjectId(projectId);
		List<UserRoleBean> userRoleList=Collections.EMPTY_LIST;
		if(empList.isEmpty()){
			return userRoleList;
		}else{
			userRoleList = new ArrayList<>();
			
			for(Employee emp:empList){
				UserRoleBean userRoleBean=new UserRoleBean();
				userRoleBean.setEmpId(emp.getId());
				userRoleBean.setEmpCode(emp.getEmpcode());
				userRoleBean.setEmpName(emp.getName());
				Set<UserRole> roles = emp.getRoles();
				for(UserRole role:roles){
					if(TmsConstant.SPOC.equalsIgnoreCase(role.getRole())){
						userRoleBean.setIsSpoc(true);
					}else if(TmsConstant.ADMIN_USER.equalsIgnoreCase(role.getRole())){
						userRoleBean.setIsAdmin(true);
					}else if(TmsConstant.FINANCE.equalsIgnoreCase(role.getRole())){
						userRoleBean.setIsFinance(true);
					}
					else if(TmsConstant.SENIORMANAGER.equalsIgnoreCase(role.getRole())){
						userRoleBean.setIsSeniorManager(true);
					}
					else if(TmsConstant.SUPERADMIN.equalsIgnoreCase(role.getRole())){
						userRoleBean.setIsSuperAdmin(true);
					}
					else if(TmsConstant.FRONTDESK.equalsIgnoreCase(role.getRole())){
						userRoleBean.setIsFrontDesk(true);
					}
				}
				userRoleList.add(userRoleBean);
			}
			return userRoleList;
		}
	}

	@Override
	public List<EmployeeBean> updateUserRole(List<UserRoleBean> userRoleBeanList) {
				
		return roleDao.updateUserRole(userRoleBeanList);
	}
	
	@Override
	@Transactional
    public Boolean updateUserTrack(List<SpocBean> trackMappingBeans) {
                    
                    return roleDao.updateUserTrack(trackMappingBeans);
    }

	@Override
	public Boolean updateSpocTrack(List<SpocBean> bean) {
		roleDao.deleteSpocTrack(bean);
		
		 return roleDao.updateSpocTrack(bean);
	}


	@Override
	public void sendEmailToUser(List<SpocBean> bean,UserProfileBean profileBean,String mailFor) {
	  
		for (SpocBean spocBean : bean) {
			//Need to change for Each Employee
			Employee employee = employeeDao.findById(spocBean.getEmpId());
			String trackName="";
			if(!spocBean.getTrackDetails().isEmpty()){
				for (String trackList : spocBean.getTrackDetails()) {
					System.out.println(trackList);
					trackName =trackName.concat(trackList+",");
				}
				tmsMailSender.sendMailToUser(employee,profileBean.getEmail(),trackName,mailFor);
			}else{
				
			}
			
	        
	        
		}
		
	}

	@Override
	public Employee getRoleByEmpId(Integer id) {
	return roleDao.getRoleByEmpId(id);
	}



}
