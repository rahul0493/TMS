package com.quinnox.flm.tms.module.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.module.dao.FLMDao;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.Vendor;


/**
 * @author AmareshP
 *
 */
@Repository("flmDao")
public class FLMDaoImpl extends GenericDaoImpl<EmpCabRequest, Integer> implements FLMDao{

	public FLMDaoImpl() {
		super(EmpCabRequest.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmpCabRequest> findAllRequest(){
		return  (List<EmpCabRequest>) getCurrentSession().createQuery("from EmpCabRequest")
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmpCabRequestDetails> findRequestByType(String requestType, String fromDate, String toDate){
		
		 return  (List<EmpCabRequestDetails>) getCurrentSession().createSQLQuery(
				 "Select * from cab_req_details where request_type = 'monthly' and schedule_date between '2017-06-15 00:00:00' and '2017-10-15 00:00:00'").list();
	
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateAddressStatus(Integer addressId, Boolean remark) {
		
		getCurrentSession().get(EmployeeAddress.class, addressId).setStatus(remark);
	}

	@Override
	@Transactional
	public void saveUpdateVendorDetails(Vendor vendor) {
		 getCurrentSession().saveOrUpdate(vendor);
	}

	@Override
	@Transactional
	public void deleteVendorDetails(Integer id) {
		 getCurrentSession().createQuery("Delete from VendorSpoc vendorspoc where vendorspoc.vendor.vendorId=:id").setInteger("id", id).executeUpdate();
		 getCurrentSession().createQuery("Delete from Vendor vendor where vendor.vendorId=:id").setInteger("id", id).executeUpdate();
	}

	
	@Override
	@Transactional
	public Vendor getVendorById(Integer id) {
		return getCurrentSession().get(Vendor.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Vendor> getAllVendor() {
		
		return  (List<Vendor>) getCurrentSession().createQuery("from Vendor").list();
				
	}

	@Override
	@Transactional
	public void deleteVendorSpocDetails(Integer id) {
		 getCurrentSession().createQuery("Delete from VendorSpoc vendor where vendor.spocId=:id").setInteger("id", id).executeUpdate();
		
	}
}
