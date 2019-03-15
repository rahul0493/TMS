package com.quinnox.flm.tms.global.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.dao.EmployeeDao;
import com.quinnox.flm.tms.global.model.Employee;

import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.EmployeeAddress;

/**
 * @author AmareshP
 *
 */
@Repository("employeeDao")
@Transactional
public class EmployeeDaoImpl extends GenericDaoImpl<Employee, Integer> implements EmployeeDao {

	public EmployeeDaoImpl() {
		super(Employee.class);
	}

	@Override
	public Employee findByEmail(String email) {

		return (Employee) getCurrentSession().createQuery("select emp from Employee emp where emp.email=:email").setString("email", email)
				.uniqueResult();
	}

	@Override
	public void saveOrUpdate(Employee employee) {
		getCurrentSession().saveOrUpdate(employee);

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Employee findById(int id) {
		return (Employee) getCurrentSession().createQuery("select emp from Employee emp where emp.id=:id").setInteger("id", id)
				.uniqueResult(); 
	}
	@Override
	public Integer saveUserAddress(EmployeeAddress employeeAddress) {
		Integer id = (Integer)getCurrentSession().save(employeeAddress);
		return id;
	}
	@Override
	@Transactional
	public void updateUser(Employee employee) {
		getCurrentSession().update(employee);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateUserAddress(EmployeeAddress employeeAddress) {
		getCurrentSession().merge(employeeAddress);
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmployeeAddress> findAddressByEmpCode(String employeeCode) {
		return (List<EmployeeAddress>) getCurrentSession().createQuery("from EmployeeAddress emp_add where emp_add.empcode=:empcode").setString("empcode", employeeCode)
				.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly = true)
	public List<Employee> findAllEmployees() {
		return (List<Employee>) getCurrentSession().createQuery("from Employee")
				.list();
	}
	@Override
	@Transactional
	public void deleteEmployeeById(int employeeId) {
		getCurrentSession().get(Employee.class, employeeId).setActive(false);
	}
	@Override
	@Transactional
	public void deleteEmployeeAddressByEmpCode(EmployeeAddress employeeAddress) {
		getCurrentSession().delete(employeeAddress);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteEmployeeAddress(EmployeeAddress employeeAddress) {
		EmployeeAddress address = getCurrentSession().get(EmployeeAddress.class, employeeAddress.getId());
		getCurrentSession().delete(address);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmployeeAddress> findAddressByEmpIdAndStatus(int employeeId) {


		return (List<EmployeeAddress>) getCurrentSession().createQuery("from EmployeeAddress emp_add where (emp_add.employee.id=:id or emp_add.employee.id=null) and emp_add.status=true").setInteger("id", employeeId)
				.list(); 
	}
	
	@Override
	@Transactional
	public Integer findAddressByEmpIdandAlias(int employeeId,String alias) {
		Integer count = getCurrentSession().createQuery("from EmployeeAddress emp_add where emp_add.employee.id=:id"
				+  " and emp_add.alias=:alias")
				.setInteger("id", employeeId)
				.setString("alias", alias)
				.list().size(); 
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmployeeAddress> findAddressByEmpIdandAliasandStatus(int employeeId,String alias) {
		List<EmployeeAddress> model =  (List<EmployeeAddress>) getCurrentSession().createQuery("from EmployeeAddress emp_add where emp_add.employee.id=:id"
				+  " and emp_add.alias=:alias ")
				.setInteger("id", employeeId)
				.setString("alias", alias)
				.list();
				
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmployeeAddress> findAddressByEmpId(int employeeId) {
		return (List<EmployeeAddress>) getCurrentSession().createQuery("from EmployeeAddress emp_add where emp_add.employee.id=:id").setInteger("id", employeeId)
				.list(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmployeeAddress> findAddressByEmpIdAndGeneralAddress(int employeeId) {
		return (List<EmployeeAddress>) getCurrentSession().createQuery("from EmployeeAddress emp_add where emp_add.employee.id=:id or emp_add.alias = 'Office' ").setInteger("id", employeeId)
				.list(); 
	}
	
	@Override
	@Transactional
	public Employee findUserByEmpCode(String empcode) {
		return   (Employee) getCurrentSession().createQuery("from Employee emp where emp.empcode =:empcode").setString("empcode", empcode)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> findEmployeeIdsByProjectId(Integer projectId) {

		List<Employee> empList=Collections.EMPTY_LIST;
		
		List<EmpProjMapping> empProject= getCurrentSession().createCriteria(EmpProjMapping.class)
				.add(Restrictions.eq("project.id", projectId))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		if(empProject!=null && !empProject.isEmpty()){
			empList = new ArrayList<>();
			for(EmpProjMapping empProj:empProject){
				empList.add(empProj.getEmployee());
			}
			Collections.sort(empList);
		}
		
		
		return empList;
	}

	@Override
	@Transactional
	public void updateUserDetails(EmployeeBean employeeBean) {
		getCurrentSession().merge(employeeBean);

	}

	//	@Override
	//	@Transactional
	//	public Employee findManagerByEmail(String empEmail) {
	//		return   (Employee) getCurrentSession().createQuery("from Employee emp where emp.email =:email").setString("email", empEmail)
	//				.uniqueResult();
	//	}


	@Override
	@Transactional
	public EmployeeAddress findDefaultAddressById(int employeeId) {

		return (EmployeeAddress) getCurrentSession().createQuery("from EmployeeAddress emp_add where emp_add.employee.id=:id and emp_add.defaultAddress is true ").setInteger("id", employeeId)
				.uniqueResult();
	}

	@Override
	@Transactional
	public EmployeeAddress findAddressByAddressId(int id) {
		return getCurrentSession().get(EmployeeAddress.class, id);
	}

	@Override
	@Transactional
	public void swapDefaultAddress(int employeeId, int employeeAddressId) {
		EmployeeAddress employeeAddress =  (EmployeeAddress) getCurrentSession().createQuery("from EmployeeAddress emp_add where emp_add.employee.id=:id and emp_add.defaultAddress is true ").setInteger("id", employeeId)
				.uniqueResult();
		employeeAddress.setDefaultAddress(false);
		getCurrentSession().update(employeeAddress);
		getCurrentSession().get(EmployeeAddress.class, employeeAddressId).setDefaultAddress(true);

	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Employee findEmpByEmpId(int empId) {
		return getCurrentSession().get(Employee.class, empId);
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> findAllAddressAliasNames(int EmployeeId) {
		return (List<String>) getCurrentSession().createQuery("select emp_add.alias from EmployeeAddress emp_add where emp_add.employee.id=:id or emp_add.employee.id=null").setInteger("id", EmployeeId)
				.list();

	}

	@Override
	@Transactional
	public EmployeeAddress findAddressForUpdation( String alias, Integer employeeId){
		EmployeeAddress address = (EmployeeAddress)getCurrentSession().createQuery("from EmployeeAddress emp_add where emp_add.alias=:alias and emp_add.employee.id=:id and emp_add.status is true")
				.setString("alias", alias)
				.setInteger("id",employeeId)
				.uniqueResult();
		return address;
	}

	@Override
	@Transactional
	public void updateEmpAddressByFlmRemark(EmployeeAddressBean address) {
		getCurrentSession().get(EmployeeAddress.class, address.getEmpAddressBeanId()).setStatus(address.getStatus());
		getCurrentSession().get(EmployeeAddress.class, address.getEmpAddressBeanId()).setComment(address.getComment());
	}


	@Override
	@Transactional
	public void saveOrUpdateProjectMapping(EmpProjMapping mapping) {
		getCurrentSession().saveOrUpdate(mapping);
		
	}

	@Override
	@Transactional
	public EmpProjMapping findEmpProjById(Integer empId, Integer projId) {
		EmpProjMapping empProjMapping = (EmpProjMapping)getCurrentSession().createQuery("from EmpProjMapping emp_proj where emp_proj.employee.id=:empId and emp_proj.project.id=:projId")
				.setInteger("empId", empId)
				.setInteger("projId",projId)
				.uniqueResult();
		return empProjMapping;
	}

	@Override
	@Transactional
	public void updateProjStatus(EmpProjMapping empProj) {
//	 List<EmpProjMapping> mappingList = getCurrentSession().createQuery("from EmpProjMapping").list();
//	 for(int i=0;i<mappingList.size();i++){
//		 EmpProjMapping mapping=mappingList.get(i);
//		 mapping.setProjectStatus(false);
//			getCurrentSession().update(mapping);
//		 }
		getCurrentSession().get(EmpProjMapping.class, empProj.getId()).setProjectStatus(false);;
		//getCurrentSession().merge(empProj);
	}

	@Override
	@Transactional
	public void setDefaultAddress(Integer id, Boolean value) {
		 getCurrentSession().get(EmployeeAddress.class, id).setDefaultAddress(value);
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public void updateCabDetailsByUserIdByDate(Integer id, Date date, String alias) {
		List<EmpCabRequestDetails> detailsList = getCurrentSession().createQuery("from EmpCabRequestDetails request "
				+ "where request.empCabRequest.user.id=:id and request.activeRequest is true "
			+ " and (request.scheduleDate >=:date )"  
				+ " and (request.fromAliasName =:alias or request.toAliasName =:alias)")
				.setInteger("id", id)
				.setDate("date", date)
				.setString("alias", alias)
				.list();
		for(EmpCabRequestDetails model : detailsList)
		{
			model.setTravelStatus(TmsConstant.ON_HOLD_W_MANAGER);
			getCurrentSession().update(model);
		}
	}

	@Override
	@Transactional
	public void setCabRequiredForEmp(Integer userId, Boolean isCabRequired) {
		getCurrentSession().get(Employee.class, userId).setIsCabRequired(isCabRequired);
	}

	@Override
	@Transactional
	public EmployeeAddress getOfficeAddress() {
		return  (EmployeeAddress) getCurrentSession().createQuery("from EmployeeAddress emp_add where emp_add.alias = 'Office'").uniqueResult();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Employee> getProjectAndEmp() {
		return (List<Employee>) getCurrentSession().createQuery("from Employee")
				.list();
	}
}
