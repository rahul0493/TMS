package com.quinnox.flm.tms.global.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.dao.EmployeeDao;
import com.quinnox.flm.tms.global.dao.UserRoleDao;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.model.UserDetails;
import com.quinnox.flm.tms.global.model.UserRole;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.module.beans.EmpProjBean;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.LocationMaster;
import com.quinnox.flm.tms.module.model.Project;

/**
 * @author AmareshP
 *
 */
@Service("employeeService")
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, Integer> implements EmployeeService {

	public EmployeeServiceImpl(@Qualifier("employeeDao") EmployeeDao employeeDao) {
		super(employeeDao);
		this.employeeDao = employeeDao;
	}

	private EmployeeDao employeeDao;

	@Autowired
	private UserRoleDao roleDao;

	// @Autowired
	// private TmsMailSender tmsMailSend;

	@Override
	public Employee findUserByEmail(String email) {
		return employeeDao.findByEmail(email);
	}

	@Override
	@Transactional
	public void saveUser(EmployeeBean employeeBean) {

		Employee employee = new Employee();
		Project project = new Project();
		EmployeeAddress empAddress = null;
		LocationMaster locationMaster = new LocationMaster();
		Set<EmployeeAddress> employeeAllAddress = null;
		Set<EmpProjMapping> empProjMapping = null;
		EmpProjMapping mapping = null;
		if (employeeBean.getId() == null) {
			mapping = new EmpProjMapping();
			employee = new Employee();
			empAddress = new EmployeeAddress();
			empAddress.setDefaultAddress(true);
			empAddress.setStatus(true);
			employeeAllAddress = new HashSet<>();
			empProjMapping = new HashSet<EmpProjMapping>();
			if(employeeBean.getDesignationName().equalsIgnoreCase("Senior Manager")) {
				Set<UserRole> userRoles= new HashSet<UserRole>();
				UserRole mgrRole = roleDao.findByRole(TmsConstant.SENIORMANAGER);
				userRoles.add(mgrRole);
				UserRole userRole = roleDao.findByRole(TmsConstant.USER);
				userRoles.add(userRole);
				employee.setRoles(userRoles);
				
			}else {
				UserRole userRole = roleDao.findByRole(TmsConstant.USER);
				employee.setRoles(new HashSet<UserRole>(Arrays.asList(userRole)));
			}
			
		} else {

			employee = employeeDao.findById(employeeBean.getId());
			employeeAllAddress = employee.getEmployeeAddresses();
			for (EmployeeAddress address : employeeAllAddress) {
				if (address.getDefaultAddress()) {
					empAddress = address;
					break;
				}
			}
		}

		employee.setEmpDesignation(employeeBean.getDesignationName());
		employee.setEmail(employeeBean.getEmail());
		employee.setEmpcode(employeeBean.getEmpcode());
		employee.setGender(employeeBean.getGender());
		employee.setManagerEmail(employeeBean.getManagerEmail());
		employee.setName(employeeBean.getName());
		employee.setPhoneNumber(employeeBean.getPhoneNumber());
		locationMaster.setLocationId(employeeBean.getLocationId());
		employee.setLocation(locationMaster);
		// project.setId(employeeBean.getProjectId());
		// employee.setProject(project);
		employee.setActive(true);

		// address creation code added  !!
		empAddress.setAlias(employeeBean.getAlias());
		empAddress.setAddress(employeeBean.getAddress());
		empAddress.setLandmark(employeeBean.getLandmark());
		empAddress.setLocation(employeeBean.getLocation());
		empAddress.setPincode(employeeBean.getPincode());
		empAddress.setEmployee(employee);
		empAddress.setCity(employeeBean.getCity());
		empAddress.setEmpcode(employee.getEmpcode());
		empAddress.setAlias("Home");
		employeeAllAddress.add(empAddress);
		employee.setEmployeeAddresses(employeeAllAddress);
		
		employee.setIsCabRequired(employeeBean.getIsCabRequired());

		// Change of project mapping
		project.setId(employeeBean.getProjectId());
		mapping.setProjectStatus(true);
		mapping.setProject(project);
		mapping.setProjectAllocation(100);
		mapping.setEmployee(employee);
		empProjMapping.add(mapping);
		employee.setEmpProjMapping(empProjMapping);

		employeeDao.saveOrUpdate(employee);
	}

	@Override
	public boolean isUserExist(UserDetails user) {
		return findByName(user.getName()) != null;
	}

	@Override
	public UserDetails findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDetails findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUserDetails(UserDetails user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUserById(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserDetails> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllUsers() {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee findById(int id) {
		return employeeDao.findById(id);
	}

	@Override
	public void saveUserAddress(EmployeeAddress employeeAddress) {
		employeeDao.saveUserAddress(employeeAddress);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> saveOrUpdateEmployeeAddress(EmployeeAddressBean bean) {
		EmployeeAddress employeeAddress = new EmployeeAddress();
		Employee employee = new Employee();

		employeeAddress.setAlias(bean.getAlias());
		employeeAddress.setDefaultAddress(bean.getDefaultAddress());
		employeeAddress.setLandmark(bean.getLandmark());
		employeeAddress.setLocation(bean.getLocation());
		employeeAddress.setPincode(bean.getPincode());
		employeeAddress.setEmpcode(bean.getEmpCode());
		employeeAddress.setCity(bean.getCity());
		employeeAddress.setAddress(bean.getAddress());
		employee.setId(bean.getEmployeeId());
		employeeAddress.setEmployee(employee);
		

		if (bean.getEmpAddressBeanId() != null) {
			employeeAddress.setStatus(false);
			employeeAddress.setComment(null);
			List<EmployeeAddress> addList = employeeDao.findAddressByEmpIdandAliasandStatus(
					employeeAddress.getEmployee().getId(), employeeAddress.getAlias());
			Employee employeeDetails = employeeDao.findById(employeeAddress.getEmployee().getId());

			EmployeeBean empBean = new EmployeeBean();
			Project prj = new Project();
			prj.setProjectName(bean.getProjectName());
			prj.setId(bean.getProjectId());
			empBean.setProject(prj);
			empBean.setEmail(employeeDetails.getEmail());
			empBean.setName(employeeDetails.getName());
			empBean.setEmpcode(employeeDetails.getEmpcode());
			empBean.setManagerEmail(employeeDetails.getManagerEmail());
			empBean.setId(employeeDetails.getId());
			
			Map<String, Object> map = new HashMap<>();
			map.put("addList", addList);
			map.put("empBean", empBean);
			map.put("pending", "Pending");

			employeeAddress.setComment(null);
			employeeAddress.setId((bean.getEmpAddressBeanId()));
			employeeAddress.setEffectiveDate(bean.getEffectiveDate());
			employeeDao.updateUserAddress(employeeAddress);
			
			// Change Travel status of CabDetails table to " ON HOLD BY MANAGER " due to ADDRESS CHANGE 
			employeeDao.updateCabDetailsByUserIdByDate(employeeDetails.getId(),bean.getEffectiveDate(),bean.getAlias());

			return map;

			// TO DO : Send mail to FLM and Employee that an address is updated by an
			// employee
			// tmsMailSend.achknowlegdeToEmpForAddressChange(addList, empBean, "Pending");
			// tmsMailSend.sendMailToFlmforAddressRemark(addList, empBean);
		} else {
			// add new address
			if (employeeAddress.getAlias().contains("Office"))
				employeeAddress.setDefaultAddress(true);
			if (employeeAddress.getAlias().contains("Client"))
				employeeAddress.setDefaultAddress(false);
			if (employeeAddress.getAlias().contains("Home"))
				employeeAddress.setDefaultAddress(true);
			employeeAddress.setStatus(true);
			employeeAddress.setComment(TmsConstant.APPROVED + "  ");
			employeeDao.saveUserAddress(employeeAddress);
		}

		return null;

	}

	@Override
	public void updateEmpAddressByFlmRemark(EmployeeAddressBean address) {
		employeeDao.updateEmpAddressByFlmRemark(address);
	}

	@Override
	public void updateUser(Employee employee) {

		employeeDao.updateUser(employee);
	}

	@Override
	public void updateUserAddress(EmployeeAddress employeeAddress) {
		employeeDao.updateUserAddress(employeeAddress);

	}

	@Override
	public List<EmployeeAddress> findAddressByEmpCode(String employeeCode) {
		return employeeDao.findAddressByEmpCode(employeeCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Employee> findAllEmployees() {
		return employeeDao.findAllEmployees();
	}

	@Override
	public void deleteEmployeeById(int employeeId) {
		employeeDao.deleteEmployeeById(employeeId);
	}

	@Override
	public void deleteEmployeeAddressByEmpCode(EmployeeAddress employeeAddress) {
		employeeDao.deleteEmployeeAddressByEmpCode(employeeAddress);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteEmployeeAddress(EmployeeAddress employeeAddress) {
		employeeDao.deleteEmployeeAddress(employeeAddress);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<EmployeeAddressBean> findAddressByEmpIdAndStatus(int employeeId) {
		List<EmployeeAddressBean> beanLists = new ArrayList<EmployeeAddressBean>();
		List<EmployeeAddress> employeeAddressesLists = employeeDao.findAddressByEmpIdAndStatus(employeeId);
		for (EmployeeAddress empAddress : employeeAddressesLists) {
			EmployeeAddressBean addressBean = new EmployeeAddressBean();
			addressBean.setAddress(empAddress.getAddress());
			addressBean.setAlias(empAddress.getAlias());
			addressBean.setCity(empAddress.getCity());
			addressBean.setDefaultAddress(empAddress.getDefaultAddress());
			addressBean.setEmpAddressBeanId(empAddress.getId());
			addressBean.setLandmark(empAddress.getLandmark());
			addressBean.setLocation(empAddress.getLocation());
			addressBean.setPincode(empAddress.getPincode());
			addressBean.setStatus(empAddress.getStatus());
			if (empAddress.getEmployee() != null) {
				addressBean.setEmployeeId(empAddress.getEmployee().getId());
			}
			beanLists.add(addressBean);
		}
		return beanLists;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<EmployeeAddressBean> findAddressByEmpId(int employeeId) {
		List<EmployeeAddressBean> beanLists = new ArrayList<EmployeeAddressBean>();
		List<EmployeeAddress> employeeAddressesLists = employeeDao.findAddressByEmpId(employeeId);
		for (EmployeeAddress empAddress : employeeAddressesLists) {
			EmployeeAddressBean addressBean = new EmployeeAddressBean();
			addressBean.setAddress(empAddress.getAddress());
			addressBean.setAlias(empAddress.getAlias());
			addressBean.setCity(empAddress.getCity());
			addressBean.setDefaultAddress(empAddress.getDefaultAddress());
			addressBean.setEmpAddressBeanId(empAddress.getId());
			addressBean.setLandmark(empAddress.getLandmark());
			addressBean.setLocation(empAddress.getLocation());
			addressBean.setPincode(empAddress.getPincode());
			if (empAddress.getStatus()) {
				addressBean.setStatusValue("Approved");
			} else {
				addressBean.setStatusValue("Pending");
			}
			beanLists.add(addressBean);
		}
		return beanLists;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<EmployeeAddressBean> findAddressByEmpIdAndGeneralAddress(int employeeId) {
		List<EmployeeAddressBean> beanLists = new ArrayList<EmployeeAddressBean>();
		// List<EmployeeAddress> employeeAddressesLists =
		// employeeDao.findAddressByEmpId(employeeId);
		List<EmployeeAddressBean> finalList = new ArrayList<EmployeeAddressBean>();
		
		
		List<EmployeeAddress> employeeAddressesLists = employeeDao.findAddressByEmpIdAndGeneralAddress(employeeId);
		for (EmployeeAddress empAddress : employeeAddressesLists) {
			EmployeeAddressBean addressBean = new EmployeeAddressBean();
			
			if(empAddress.getEmployee() != null)
			addressBean.setEmployeeId(empAddress.getEmployee().getId());
			addressBean.setAddress(empAddress.getAddress());
			addressBean.setAlias(empAddress.getAlias());
			addressBean.setCity(empAddress.getCity());
			addressBean.setDefaultAddress(empAddress.getDefaultAddress());
			addressBean.setEmpAddressBeanId(empAddress.getId());
			addressBean.setLandmark(empAddress.getLandmark());
			addressBean.setLocation(empAddress.getLocation());
			addressBean.setPincode(empAddress.getPincode());
			addressBean.setEffectiveDate(empAddress.getEffectiveDate());
			addressBean.setComment(empAddress.getComment());
			addressBean.setStatus(empAddress.getStatus());
			if(empAddress.getEmployee() != null)
			addressBean.setIsCabRequired(empAddress.getEmployee().getIsCabRequired());
			if (empAddress.getStatus()) {
				addressBean.setStatusValue("Approved");
			} else {
				addressBean.setStatusValue("Pending");
			}
			beanLists.add(addressBean);
		}

		for (EmployeeAddressBean bean : beanLists) {
               
			if (bean.getAlias().contains("Home")) {
				finalList.add(bean);
			}
		}
		for (EmployeeAddressBean bean : beanLists) {

			if (bean.getAlias().contains("Client")) {
				finalList.add(bean);
			}
		}
		for (EmployeeAddressBean bean : beanLists) {

			if (bean.getAlias().contains("Office")) {
				finalList.add(bean);
			}
		}
		return finalList;
	}

	@Override
	public Employee findUserByEmpCode(String managerCode) {
		return employeeDao.findUserByEmpCode(managerCode);
	}

	@Override
	public void updateUserDetails(EmployeeBean employeeBean) {
		employeeDao.updateUserDetails(employeeBean);
	}

	

	@Override
	public EmployeeAddress findDefaultAddressById(int id) {
		return employeeDao.findDefaultAddressById(id);
	}

	@Override
	public EmployeeAddressBean findAddressByAddressId(int id) {
		EmployeeAddress address = employeeDao.findAddressByAddressId(id);
		if (address != null) {
			EmployeeAddressBean addressBean = new EmployeeAddressBean();
			addressBean.setAddress(address.getAddress());
			addressBean.setAlias(address.getAlias());
			addressBean.setCity(address.getCity());
			addressBean.setDefaultAddress(address.getDefaultAddress());
			addressBean.setEmpAddressBeanId(address.getId());
			addressBean.setEmpCode(address.getEmpcode());
			addressBean.setEmployeeId(address.getEmployee().getId());
			addressBean.setLandmark(address.getLandmark());
			addressBean.setLocation(address.getLocation());
			addressBean.setPincode(address.getPincode());
			addressBean.setStatus(address.getStatus());
			addressBean.setEffectiveDate(address.getEffectiveDate());
			addressBean.setComment(address.getComment());
			return addressBean;
		} else {
			return null;
		}

	}

	@Override
	public void SwapDefaultAddress(int employeeId, int employeeAddressId) {
		employeeDao.swapDefaultAddress(employeeId, employeeAddressId);
	}

	@Override
	public Employee findEmpByEmpId(int empId) {
		return employeeDao.findEmpByEmpId(empId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public EmployeeBean findEmployeeByEmpId(int empId) {
		Employee model = employeeDao.findEmpByEmpId(empId);
		Set<EmployeeAddressBean> addressSet = new HashSet<EmployeeAddressBean>();
		EmployeeBean bean = new EmployeeBean();
		bean.setActive(model.getActive());
		bean.setDesignationName(model.getEmpDesignation());
		bean.setEmail(model.getEmail());
		for (EmployeeAddress address : model.getEmployeeAddresses()) {
			EmployeeAddressBean addressBean = new EmployeeAddressBean();
			addressBean.setAddress(address.getAddress());
			addressBean.setAlias(address.getAlias());
			addressBean.setCity(address.getCity());
			addressBean.setDefaultAddress(address.getDefaultAddress());
			addressBean.setEmpAddressBeanId(address.getId());
			addressBean.setEmpCode(address.getEmpcode());
			addressBean.setEmployeeId(address.getEmployee().getId());
			addressBean.setLandmark(address.getLandmark());
			addressBean.setLocation(address.getLocation());
			addressBean.setPincode(address.getPincode());
			addressBean.setStatus(address.getStatus());
			addressBean.setComment(address.getComment());
			addressSet.add(addressBean);
		}
		bean.setEmpAddress(addressSet);
		bean.setEmpcode(model.getEmpcode());
		bean.setEmpProjMapping(model.getEmpProjMapping());
		bean.setGender(model.getGender());
		bean.setId(model.getId());
		bean.setIsCabRequired(model.getIsCabRequired());
		bean.setManagerEmail(model.getManagerEmail());
		bean.setName(model.getName());
		bean.setPhoneNumber(model.getPhoneNumber());
		return bean;
	}

	@Override
	public List<String> findAllAddressAliasNames(int EmployeeId) {
		return employeeDao.findAllAddressAliasNames(EmployeeId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Set<String> getUserRoles(String email) {
		final Employee user = employeeDao.findByEmail(email);
		if (user != null) {
			Set<String> roles = new HashSet<>();
			for (UserRole role : user.getRoles()) {
				roles.add(role.getRole());
			}
			return roles;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public EmployeeBean addEmployee(String EmpCode) {

		final Employee employee = employeeDao.findUserByEmpCode(EmpCode);
		EmployeeBean empBean = new EmployeeBean();
		if (employee != null) {
			if ((employee.getEmpProjMapping() != null)) {
				Set<EmpProjMapping> projectLists = employee.getEmpProjMapping();
				Integer size = 0;
				EmpProjMapping primaryProject = null;
				Iterator<EmpProjMapping> project = projectLists.iterator();
				while (project.hasNext()) {
					EmpProjMapping empProjMapping = project.next();
					if (size < empProjMapping.getProjectAllocation()) {
						primaryProject = empProjMapping;
						size = empProjMapping.getProjectAllocation();
					}
				}
				// EmployeeBean empBean = new EmployeeBean();

				if (primaryProject != null) {
					empBean.setAccountId(primaryProject.getProject().getAccount().getId());
					empBean.setProjectId(primaryProject.getProject().getId());
					empBean.setProjectName(primaryProject.getProject().getProjectName());
				}
				empBean.setId(employee.getId());
				empBean.setGender(employee.getGender());
				empBean.setPhoneNumber(employee.getPhoneNumber());
				empBean.setManagerEmail(employee.getManagerEmail());
				empBean.setIsCabRequired(employee.getIsCabRequired());
				empBean.setEmpProjMapping(projectLists);

//				for (EmployeeAddress address : employee.getEmployeeAddresses()) {
//					if (address.getAlias().contains("Home") && address.getStatus()) {
//						empBean.setAddress(address.getAddress());
//						empBean.setLandmark(address.getLandmark());
//						empBean.setLocation(address.getLocation());
//						empBean.setPincode(address.getPincode());
//						empBean.setAlias(address.getAlias());
//						empBean.setCity(address.getCity());
//						break;
//					}
//				}

				// return empBean;
			}
		}
		return empBean;
	}

	@Override
	public EmployeeAddress findAddressForUpdation(String alias, Integer employeeId) {
		return employeeDao.findAddressForUpdation(alias, employeeId);
	}

	@Override
	public void saveOrUpdate(Employee user) {
		employeeDao.saveOrUpdate(user);
	}

	@Override
	public void saveOrUpdateProjectMapping(EmpProjMapping mapping) {
		employeeDao.saveOrUpdateProjectMapping(mapping);
	}

	@Override
	public EmpProjMapping findEmpProjById(Integer empId, Integer projId) {
		return employeeDao.findEmpProjById(empId, projId);
	}

	@Override
	public void updateProjStatus(EmpProjMapping empProj) {
		employeeDao.updateProjStatus(empProj);

	}

	@Override
	public void setDefaultAddress(Integer id, Boolean value) {
		employeeDao.setDefaultAddress(id, value);
	}

	@Override
	public void setCabRequiredForEmp(Integer userId, Boolean isCabRequired) {
		employeeDao.setCabRequiredForEmp(userId, isCabRequired);
	}

	@Override
	public EmployeeAddressBean getOfficeAddress() {
		EmployeeAddress address =  employeeDao.getOfficeAddress();
		if (address != null) {
			EmployeeAddressBean addressBean = new EmployeeAddressBean();
			addressBean.setAddress(address.getAddress());
			addressBean.setAlias(address.getAlias());
			addressBean.setCity(address.getCity());
			addressBean.setDefaultAddress(address.getDefaultAddress());
			addressBean.setEmpAddressBeanId(address.getId());
			addressBean.setLandmark(address.getLandmark());
			addressBean.setLocation(address.getLocation());
			addressBean.setPincode(address.getPincode());
			addressBean.setStatus(address.getStatus());
			addressBean.setEffectiveDate(address.getEffectiveDate());
			addressBean.setComment(address.getComment());
			return addressBean;
		}
		else {
			return null;
		}
	
		
	}

	@Override
	public List<EmployeeBean> getProjectAndEmp() {
		List<Employee> empList = employeeDao.getProjectAndEmp();
		List<EmployeeBean> empBeanList = new ArrayList<EmployeeBean>();
	
		for(Employee emp: empList) {
			EmployeeBean empBean = new EmployeeBean();
			Set<EmpProjBean> empProjMapping = new HashSet<EmpProjBean>();
			empBean.setId(emp.getId());
			empBean.setName(emp.getName());
			for(EmpProjMapping emMapping : emp.getEmpProjMapping()) {
				EmpProjBean empProjBean = new EmpProjBean();
				empProjBean.setId(emMapping.getId());
				empProjBean.setProjectAllocation(emMapping.getProjectAllocation());
				empProjBean.setProjectStatus(emMapping.getProjectStatus());
				empProjBean.setProjectId(emMapping.getProject().getId());
				empProjBean.setProjectName(emMapping.getProject().getProjectName());
				empProjMapping.add(empProjBean);
				
			}
			for(Project projMapping: emp.getUpdatedEmpProj()) {
				EmpProjBean projBean = new EmpProjBean();
				projBean.setProjectId(projMapping.getId());
				projBean.setProjectName(projMapping.getProjectName());
				empProjMapping.add(projBean);
			}
			empBean.setEmpProjBean(empProjMapping);
			empBeanList.add(empBean);
		}
		return empBeanList;
	}
	
}