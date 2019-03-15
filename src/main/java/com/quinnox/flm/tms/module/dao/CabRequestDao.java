package com.quinnox.flm.tms.module.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.CabReportBean;
import com.quinnox.flm.tms.module.beans.CabRequestRemarkBean;
import com.quinnox.flm.tms.module.beans.TripSheetDetailsBean;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.TripSheetDetails;

/**
 * @author AmareshP
 *
 */
public interface CabRequestDao extends GenericDao<EmpCabRequest, Integer> {

	public Integer saveCabRequest(EmpCabRequest empCabRequest);
	public List<EmpCabRequest> findAllRequestByEmpId(String employeeId);
	public void updateCabRequest(AdhocCabRequestBean adhocBean);
	public List<EmpCabRequestDetails> findAllOpenRequestByEmpId(Integer employeeId);
	public void deleteCabRequest(int cabRequestId);
	public EmpCabRequest findByCabRequestId(int cabRequestId);
	public void deleteIndivitualCabRequest(int requestId);
	public List<EmpCabRequestDetails>  getEmpCabRequest(final CabReportBean cabReport);
	public EmpCabRequestDetails findByRequestId(int requestId);
	public Integer[][] getAdhocRequestCount(String empId) ;
	public void updateCabRequestByManager(CabRequestRemarkBean details);
	List<EmpCabRequestDetails> findAllCabRequestDetails(int cabRequestId);
	List<EmpCabRequestDetails> findAllOtherOpenRequest(int requestedById);
//	public List<EmpCabRequest> findAllRequest();
	//public EmpCabRequest findById(Integer id);
		//public List<EmpCabRequestDetails> findRequestByType(String requestType, String fromDate, String toDate);
	//public EmpCabRequest findById(int id);
		//public List<EmpCabRequest> findAllOpenRequest();
	public List<EmpCabRequestDetails> findAllOpenRequest();
	void updateCabReqTripSheetDetails(TripSheetDetailsBean  cabDetails,int tripId); 
	public void updateCabDetails(EmpCabRequestDetails details); 
	List<EmpCabRequestDetails> findCabRequestByTripId(int tripId);
	List<EmpCabRequestDetails> findDailyCabRequest();
	void updateAddressByUserandDate(EmployeeAddressBean addrBean , String travelStatus);
	void removeTripId(Integer detailsId,Integer tripId);
	Integer getCabRequestCountByDate(String date);
	List<EmpCabRequestDetails> findAllRequestsByMonth(int month,int year);
	public List<EmpCabRequestDetails> findAllMonthlyRequestByEmpId(Integer empId);
	void updateCabRequest(EmpCabRequest request);
	public List<EmpCabRequestDetails> findAllInActiveRequestByEmpId(Integer employeeId);
	List<EmpCabRequestDetails> findTodaysCabRequest();
	List<EmpCabRequestDetails> findTomorrowsCabRequest();
}
