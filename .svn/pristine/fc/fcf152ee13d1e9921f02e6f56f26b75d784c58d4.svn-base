package com.quinnox.flm.tms.global.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.model.UserDetails;

import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.EmployeeAddress;

/**
 * @author AmareshP
 *
 */
public interface EmployeeService extends GenericService<Employee, Integer>{
	
	public Employee findUserByEmail(String email);
	
	public void saveUser(EmployeeBean user);
	
	
	UserDetails findById(long id);
	
	UserDetails findByName(String name);
	
	void saveUserDetails(UserDetails user);
	
	void updateUser(UserDetails user);
	
	void deleteUserById(long id);

	List<UserDetails> findAllUsers(); 
	
	void deleteAllUsers();
	
	public boolean isUserExist(UserDetails userDetails);
	
	Employee findById(int id);
	
	void saveUserAddress(EmployeeAddress employeeAddress);
	void updateUser(Employee employee);
	void updateUserAddress(EmployeeAddress employeeAddress);
	List<EmployeeAddress> findAddressByEmpCode(String employeeCode);
	List<Employee> findAllEmployees(); 
	void deleteEmployeeById(int employeeId);
	void deleteEmployeeAddressByEmpCode(EmployeeAddress employeeAddress);
	void deleteEmployeeAddress(EmployeeAddress employeeAddress);
	public Employee findUserByEmpCode(String managerCode);
	void updateUserDetails(EmployeeBean employeeBean); 
	// Employee findManagerByEmail(String managerEmail); 

	EmployeeAddress findDefaultAddressById(int id);
	EmployeeAddressBean findAddressByAddressId(int id);
	void SwapDefaultAddress(int employeeId,int employeeAddressId);
	Employee findEmpByEmpId(int empId);
	List<String> findAllAddressAliasNames(int EmployeeId);
	EmployeeBean addEmployee(String EmpCode);
	public Set<String> getUserRoles(String email);
	public Map<String,Object> saveOrUpdateEmployeeAddress(EmployeeAddressBean employeeAddressBean);
	List<EmployeeAddressBean> findAddressByEmpIdAndStatus(int employeeId);
	List<EmployeeAddressBean> findAddressByEmpId(int employeeId);
	EmployeeAddress findAddressForUpdation( String alias, Integer employeeId);
	public void updateEmpAddressByFlmRemark(EmployeeAddressBean address);
	void saveOrUpdate(Employee user);
	void saveOrUpdateProjectMapping(EmpProjMapping mapping);
	EmpProjMapping findEmpProjById(Integer empId, Integer projId);
	void updateProjStatus(EmpProjMapping empProj);
	EmployeeBean findEmployeeByEmpId(int empId);
	List<EmployeeAddressBean> findAddressByEmpIdAndGeneralAddress(int employeeId);
	void setDefaultAddress(Integer id, Boolean value);
	void setCabRequiredForEmp(Integer userId,Boolean isCabRequired);
	EmployeeAddressBean getOfficeAddress();
	public List<EmployeeBean> getProjectAndEmp();
}