package com.quinnox.flm.tms.module.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.CabReportBean;
import com.quinnox.flm.tms.module.beans.CabRequestRemarkBean;
import com.quinnox.flm.tms.module.beans.TripSheetDetailsBean;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;

/**
 * @author AmareshP
 *
 */
public interface CabRequestService extends GenericService<EmpCabRequest, Integer> {

	public Integer saveEmpCabRequest(EmpCabRequest empCabRequest);
	//public void sendMail(UserProfileBean userProfile,Integer cabReqId,String requestFor);
	public void updateCabRequest(AdhocCabRequestBean adhocCabRequestBean);
	public List<AdhocCabRequestBean> findAllOpenRequestByEmpId(Integer empId,String status);
	public List<EmpCabRequest> findAllRequestByEmpId(String empId);
	public void deleteEmpCabRequest(int cabRequestId);
	public List<AdhocCabRequestBean> findByCabRequestId(int cabRequestId);
	public void deleteIndivitualCabRequest(int requestId);
	public List<AdhocCabRequestBean> getEmpCabRequest(final CabReportBean cabReport);
	public EmpCabRequestDetails findByRequestId(int requestId);
	public Integer[][] getAdhocRequestCount(String empId);
	public void updateCabRequestByManager(CabRequestRemarkBean details);
	List<AdhocCabRequestBean> getFlmCabReport(CabReportBean cabReport);
	List<EmpCabRequestDetails> findAllCabRequestDetails(int cabRequestId);
	List<AdhocCabRequestBean> findAllOtherOpenRequest(int requestedById);
	void updateCabReqTripSheetDetails(TripSheetDetailsBean  cabDetails,int tripId);  
	public void updateCabDetails(EmpCabRequestDetails details); 
	//public void empAdhocAcknowledgeMail(UserProfileBean profileBean, Integer cabReqId,String requestFor);
	//public void managerRemarkMail(EmployeeBean employee, Integer cabReqId, String requestFor,CabRequestRemarkBean cabRemarkBean);
	List<EmpCabRequestDetails> findCabRequestByTripId(int tripId);
	List<AdhocCabRequestBean> findDailyCabRequest();
	//public List<EmpCabRequest> findAllRequest(int employeeId);
	//public EmpCabRequest findById(int id);
	//public void saveEmpCabRequestDetails(EmpCabRequestDetails cabRequestDetails);	
	//public Set<CabRequestService> findByUserId(Integer id);
	//public Set<EmpCabRequestDetails> findByUserId(Employee id);
	//public List<EmpCabRequest> findAllRequest();
	//public List<EmpCabRequestDetails> findRequestByType(String requestType, String fromDate, String toDate);
	void updateAddressByUserandDate(EmployeeAddressBean addrBean ,String travelStatus);
	void removeTripId(Integer detailsId,Integer tripId);
	Integer getCabRequestCountByDate(String date);
	List<EmpCabRequestDetails> findAllRequestsByMonth(int month,int year);
	public List<AdhocCabRequestBean> findAllMonthlyRequestByEmpId(Integer empId,String status);
	public List<EmpCabRequestDetails> findAllOpenRequestByEmpId(Integer empId);
	void updateCabRequest(EmpCabRequest request);
	public List<AdhocCabRequestBean> findAllInActiveRequestByEmpId(Integer empId,String status);
	List<AdhocCabRequestBean> findTodaysCabRequest();
	List<AdhocCabRequestBean> findTomorrowsCabRequest();

}
