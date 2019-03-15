package com.quinnox.flm.tms.global.service;

import java.util.List;

import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.beans.SpocBean;
import com.quinnox.flm.tms.global.beans.UserRoleBean;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.model.UserRole;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.Account;

/**
 * @author AmareshP
 *
 */
public interface UserRoleService extends GenericService<UserRole, Integer>{

	UserRole findByRole(String role);
	List<Account> getAllAccount();
	List<UserRoleBean> findEmployeeRolesByProjectId(Integer id);
	List<EmployeeBean> updateUserRole(List<UserRoleBean> userRoleBeanList);
	Boolean updateUserTrack(List<SpocBean> bean);
	Boolean updateSpocTrack(List<SpocBean> bean);
	void sendEmailToUser(List<SpocBean> bean, UserProfileBean profileBean, String mailFor);
	Employee getRoleByEmpId(Integer id);
}
