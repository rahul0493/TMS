package com.quinnox.flm.tms.module.dao;


import java.util.List;

import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.Vendor;

/**
 * @author AmareshP
 *
 */
public interface FLMDao extends GenericDao<EmpCabRequest, Integer> {
	
	public List<EmpCabRequest> findAllRequest();
	List<EmpCabRequestDetails> findRequestByType(String requestType, String fromDate, String toDate);
	public void updateAddressStatus(Integer addressId,Boolean remark);
	public void saveUpdateVendorDetails(Vendor vendor);
	public void deleteVendorDetails(Integer id);
	public void deleteVendorSpocDetails(Integer id);
	public Vendor getVendorById(Integer id);
	public List<Vendor> getAllVendor();
}
