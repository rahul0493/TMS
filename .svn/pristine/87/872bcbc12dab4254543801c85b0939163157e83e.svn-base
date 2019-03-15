package com.quinnox.flm.tms.module.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.dao.EmployeeDao;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.module.dao.FLMDao;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.Vendor;
import com.quinnox.flm.tms.module.service.FLMService;

/**
 * @author AmareshP
 *
 */
 @Service
public class FLMServiceImpl extends GenericServiceImpl<EmpCabRequest, Integer> implements FLMService{

	 private FLMDao flmDao;
	 @Autowired
		private EmployeeService employeeService;
	 private EmployeeDao employeeDao;
		
		@Autowired
		public FLMServiceImpl(
				@Qualifier("flmDao") FLMDao flmDao) {
			super(flmDao);
			this.flmDao = flmDao;
		}

		@Override
		public List<EmpCabRequest> findAllRequest() {
			return flmDao.findAllRequest();
		}
		
		@Override
		public List<EmpCabRequestDetails> findRequestByType(String requestType, String fromDate, String toDate) {
			return flmDao.findRequestByType(requestType, fromDate, toDate);
		}

		@Override
		@Transactional(propagation=Propagation.REQUIRED)
		public void updateAddressStatus(int addressId, Boolean remark) {

			EmployeeAddressBean newAddress = employeeService.findAddressByAddressId(addressId);
			System.out.println(newAddress);
			EmployeeAddress oldAddress = employeeService.findAddressForUpdation(newAddress.getAlias(),newAddress.getEmployeeId());
			if(remark)
			{
				employeeService.deleteEmployeeAddress(oldAddress);
				flmDao.updateAddressStatus(addressId,remark);
			}
			else
			{
				// To Do :
				// Mail has to sent to Employee => Subject : change of address is rejected by FLM 
				EmployeeAddress address = new EmployeeAddress();
				address.setAddress(newAddress.getAddress());
				address.setAlias(newAddress.getAlias());
				address.setCity(newAddress.getCity());
				address.setDefaultAddress(newAddress.getDefaultAddress());
				address.setEmpcode(newAddress.getEmpCode());
				Employee employee = new Employee();
				employee.setId(newAddress.getEmployeeId());
				address.setEmployee(employee);
				address.setId(newAddress.getEmpAddressBeanId());
				address.setLandmark(newAddress.getLandmark());
				address.setLocation(newAddress.getLocation());
				address.setPincode(newAddress.getPincode());
				address.setStatus(newAddress.getStatus());
				
			employeeDao.deleteEmployeeAddress(address);
			
			}
			
		}

		@Override
		public void saveUpdateVendorDetails(Vendor vendor) {
			flmDao.saveUpdateVendorDetails(vendor);
		}

		@Override
		public void deleteVendorDetails(Integer id) {
			flmDao.deleteVendorDetails(id);
		}

		@Override
		public Vendor getVendorById(Integer id) {
			return flmDao.getVendorById(id);
		}

		@Override
		public List<Vendor> getAllVendor() {
			return flmDao.getAllVendor();
		}

		@Override
		public void deleteVendorSpocDetails(Integer id) {
			flmDao.deleteVendorSpocDetails(id);
			
		}
}
