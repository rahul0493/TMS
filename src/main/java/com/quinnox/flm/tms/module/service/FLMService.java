package com.quinnox.flm.tms.module.service;

import java.util.List;
import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.Vendor;

/**
 * @author AmareshP
 *
 */
public interface FLMService extends GenericService<EmpCabRequest, Integer> {

	public List<EmpCabRequest> findAllRequest();
	public List<EmpCabRequestDetails> findRequestByType(String requestType, String fromDate, String toDate);
	public void updateAddressStatus(int addressId,Boolean remark);
	public void saveUpdateVendorDetails(Vendor vendor);
	public void deleteVendorDetails(Integer id);
	public void deleteVendorSpocDetails(Integer id);
	public Vendor getVendorById(Integer id);
	public List<Vendor> getAllVendor();
}
