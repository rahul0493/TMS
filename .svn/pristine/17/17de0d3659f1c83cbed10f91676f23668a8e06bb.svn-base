package com.quinnox.flm.tms.global.dao;

import java.util.List;

import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.beans.SpocBean;
import com.quinnox.flm.tms.global.beans.TrackMappingBean;
import com.quinnox.flm.tms.global.beans.UserRoleBean;
import com.quinnox.flm.tms.global.beans.UserTrackBean;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.model.UserRole;
import com.quinnox.flm.tms.module.model.Account;

/**
 * @author AmareshP
 *
 */
public interface UserRoleDao extends GenericDao<UserRole, Integer> {

	UserRole findByRole(String role);
	List<Account> getAllAccount();
	//Boolean updateUserRole(List<UserRoleBean> userRoleBeanList);
	List<EmployeeBean> updateUserRole(List<UserRoleBean> userRoleBeanList);
	Boolean updateUserTrack(List<SpocBean> trackMappingBeans);
	Boolean updateSpocTrack(List<SpocBean> bean);
	Employee getRoleByEmpId(Integer id);
	void deleteSpocTrack(List<SpocBean> bean);
	
}
