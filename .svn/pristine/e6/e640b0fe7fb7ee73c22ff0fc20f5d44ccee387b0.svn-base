package com.quinnox.flm.tms.global.dao;

import java.util.Date;
import java.util.List;

import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.model.Employee;

import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
/**
 * @author AmareshP
 *
 */
public interface EmployeeDao extends GenericDao<Employee, Integer> {

	Employee findByEmail(String email);

	void saveOrUpdate(Employee user);
	
	public Employee findById(int id); 
	Integer saveUserAddress(EmployeeAddress employeeAddress);
	void updateUser(Employee employee);
	void updateUserAddress(EmployeeAddress employeeAddress);
	List<EmployeeAddress> findAddressByEmpCode(String employeeCode);
	List<Employee> findAllEmployees();
	void deleteEmployeeById(int employeeId);
	void deleteEmployeeAddressByEmpCode(EmployeeAddress employeeAddress);
	void deleteEmployeeAddress(EmployeeAddress employeeAddress);
	List<EmployeeAddress> findAddressByEmpIdAndStatus(int employeeId);
	public Employee findUserByEmpCode(String managerCode); 
	List<Employee> findEmployeeIdsByProjectId(Integer projectId);
	void updateUserDetails(EmployeeBean employeeBean); 

	EmployeeAddress findDefaultAddressById(int id);
	EmployeeAddress findAddressByAddressId(int id);
	void swapDefaultAddress(int employeeId,int employeeAddressId);
	Employee findEmpByEmpId(int empId);
	List<String> findAllAddressAliasNames(int EmployeeId);
	List<EmployeeAddress> findAddressByEmpId(int employeeId);
	EmployeeAddress findAddressForUpdation( String alias, Integer employeeId);
    public Integer findAddressByEmpIdandAlias(int employeeId,String alias);
    public void updateEmpAddressByFlmRemark(EmployeeAddressBean address);
    public List<EmployeeAddress> findAddressByEmpIdandAliasandStatus(int employeeId,String alias);
    void saveOrUpdateProjectMapping(EmpProjMapping mapping);
    EmpProjMapping findEmpProjById(Integer empId, Integer projId);
    void updateProjStatus(EmpProjMapping empProj);
    public List<EmployeeAddress> findAddressByEmpIdAndGeneralAddress(int employeeId);
    void setDefaultAddress(Integer id, Boolean value);
    void updateCabDetailsByUserIdByDate(Integer id,Date date,String alias);
    void setCabRequiredForEmp(Integer userId,Boolean isCabRequired);
    EmployeeAddress getOfficeAddress();
    public List<Employee> getProjectAndEmp();
    }
