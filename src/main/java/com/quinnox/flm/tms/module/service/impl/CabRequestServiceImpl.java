package com.quinnox.flm.tms.module.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.CabReportBean;
import com.quinnox.flm.tms.module.beans.CabRequestRemarkBean;
import com.quinnox.flm.tms.module.beans.TripSheetDetailsBean;
import com.quinnox.flm.tms.module.dao.CabRequestDao;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.TripSheetDetails;
import com.quinnox.flm.tms.module.service.CabRequestService;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.service.TripSheetDetailsService;

/**
 * @author AmareshP
 *
 */
@Service
public class CabRequestServiceImpl extends GenericServiceImpl<EmpCabRequest, Integer> implements CabRequestService {

	private CabRequestDao cabRequestDao;
	@Autowired
	private ProjectDetailsService projectDetailsService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private TripSheetDetailsService tripSheetDetailsService;

	@Autowired
	public CabRequestServiceImpl(@Qualifier("cabRequestDao") CabRequestDao cabRequestDao) {
		super(cabRequestDao);
		this.cabRequestDao = cabRequestDao;
	}

	// @Autowired
	// private TmsMailSender tmsMailSend;

	@Override
	public Integer saveEmpCabRequest(EmpCabRequest empCabRequest) {
		return cabRequestDao.saveCabRequest(empCabRequest);
	}

	// @Override
	// public void sendMail(UserProfileBean profileBean, Integer cabReqId, String
	// requestFor) {
	//
	// tmsMailSend.sendMail(profileBean, cabReqId, requestFor);
	// }

	@Override
	public void updateCabRequest(AdhocCabRequestBean adhocCabRequestBean) {
		cabRequestDao.updateCabRequest(adhocCabRequestBean);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AdhocCabRequestBean> findAllOpenRequestByEmpId(final Integer empId, String status) {

		List<EmpCabRequestDetails> cabRequestDetailList;
		
		List<EmpCabRequestDetails> cabRequestDetailListByStatus = new ArrayList<EmpCabRequestDetails>();

		if (empId != null) {

			cabRequestDetailList = cabRequestDao.findAllOpenRequestByEmpId(empId);
			
		
		
			
			for (EmpCabRequestDetails details : cabRequestDetailList) {

				if (status != null) {

					if (status.contains(TmsConstant.APPROVED)) {

						if ((details.getTravelStatus().contains(TmsConstant.PENDING_W_FLM)
								&& details.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_PROCESSING))
								|| details.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_PROCESSING)
								) {
							cabRequestDetailListByStatus.add(details);
						}

					} else if (status.contains(TmsConstant.PENDING)) {

						if (details.getTravelStatus().contains(TmsConstant.PENDING_W_MANAGER) 
								|| details.getTravelStatus().contains(TmsConstant.PENDING_W_FLM) 
								) {
							cabRequestDetailListByStatus.add(details);
						}

					}

					 else if (status.contains("Used")) {

						if ((details.getScheduleDate().getDate() < new Date().getDate()
								&& details.getScheduleDate().getMonth() == new Date().getMonth()
								&& details.getScheduleDate().getYear() == new Date().getYear())
								&& details.getActiveRequest()
								&& details.getEmpCabRequest().getAdhocOrMonthly().equals("Monthly")
								&& (details.getActiveRequest() && TmsConstant.TRAVEL_STATUS_COMPLETED.equalsIgnoreCase
										(details.getTravelStatus()))) {
							cabRequestDetailListByStatus.add(details);
						}

					} else if (status.contains("Unused")) {
						if ((details.getScheduleDate().getDate() >= new Date().getDate()
								&& details.getScheduleDate().getMonth() == new Date().getMonth()
								&& details.getScheduleDate().getYear() == new Date().getYear())
								&& details.getActiveRequest()
								&& details.getEmpCabRequest().getAdhocOrMonthly().equals("Monthly")) {
							cabRequestDetailListByStatus.add(details);
						}

					} else if (status.contains("NoShow")) {
						if ((details.getScheduleDate().getDate() >= new Date().getDate()
								&& details.getScheduleDate().getMonth() == new Date().getMonth()
								&& details.getScheduleDate().getYear() == new Date().getYear())
								&& details.getEmpCabRequest().getAdhocOrMonthly().equals("Monthly")
								&& (TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User.equalsIgnoreCase(details.getTravelStatus())
										|| TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm.equalsIgnoreCase(details.getTravelStatus())
										|| TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor.equalsIgnoreCase(details.getTravelStatus()))) {
							cabRequestDetailListByStatus.add(details);
						}
						else if ((details.getScheduleDate().getDate() >= new Date().getDate()
								&& details.getScheduleDate().getMonth() == new Date().getMonth()
								&& details.getScheduleDate().getYear() == new Date().getYear())
								&& details.getEmpCabRequest().getAdhocOrMonthly().equals("Adhoc")
								&& (TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User.equalsIgnoreCase(details.getTravelStatus())
										|| TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm.equalsIgnoreCase(details.getTravelStatus())
										|| TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor.equalsIgnoreCase(details.getTravelStatus()))) {
							cabRequestDetailListByStatus.add(details);
						}
							
						
					}

					else {
						cabRequestDetailListByStatus = cabRequestDetailList;
					}

				} else {
					cabRequestDetailListByStatus = cabRequestDetailList;
				}

			}

		} else {
			cabRequestDetailListByStatus = cabRequestDao.findAllOpenRequest();

		}

		cabRequestDetailListByStatus.sort(Comparator.comparing(EmpCabRequestDetails::getScheduleDate)
				.thenComparing(EmpCabRequestDetails::getRequestime));

		List<AdhocCabRequestBean> adhocCabRequestsBeanList = Collections.EMPTY_LIST;
		if (cabRequestDetailListByStatus.size() > 0) {
			adhocCabRequestsBeanList = new ArrayList<AdhocCabRequestBean>();

			for (EmpCabRequestDetails cabrequestdetail : cabRequestDetailListByStatus) {
				Date scheduleDate = cabrequestdetail.getScheduleDate();
				if (cabrequestdetail.getActiveRequest()
						&& (DateUtils.isSameDay(scheduleDate, new Date()) || scheduleDate.after((new Date())))) {
					AdhocCabRequestBean adhocObj = new AdhocCabRequestBean();
					String projectName = projectDetailsService.findProjectById(cabrequestdetail.getProjectId())
							.getProjectName();
					TripSheetDetails sheetDetails = null;
					if (cabrequestdetail.getTripSheetId() != null) {
						sheetDetails = tripSheetDetailsService.findByTripSheetId(cabrequestdetail.getTripSheetId());
						adhocObj.setTripSheetNumber(sheetDetails.getTripSheetNumber());
						if (sheetDetails != null) {
							adhocObj.setIsEscort(sheetDetails.getIsEscort());
							adhocObj.setEscortName(sheetDetails.getEscortName());
						}
					}
					adhocObj.setUserId(cabrequestdetail.getEmpCabRequest().getUser().getId());
					adhocObj.setEmpGender(employeeService
							.findEmpByEmpId(cabrequestdetail.getEmpCabRequest().getUser().getId()).getGender());
					adhocObj.setEmpName(employeeService
							.findEmpByEmpId(cabrequestdetail.getEmpCabRequest().getUser().getId()).getName());
					adhocObj.setId(cabrequestdetail.getRequestId());
					adhocObj.setRequestType(cabrequestdetail.getPickOrDrop());

					adhocObj.setScheduleDate(scheduleDate);
					adhocObj.setReason(cabrequestdetail.getEmpCabRequest().getReason());
					adhocObj.setRequestDate(cabrequestdetail.getEmpCabRequest().getReqDate());
					adhocObj.setManagerRemark(cabrequestdetail.getEmpCabRequest().getManagerRemark());
					adhocObj.setRequestStatus(cabrequestdetail.getReqStatus());
					adhocObj.setMobileNumber(cabrequestdetail.getMobileNumber());
					adhocObj.setAdhocMonthly(cabrequestdetail.getEmpCabRequest().getAdhocOrMonthly());
					adhocObj.setReqTime(cabrequestdetail.getRequestime());
					adhocObj.setProjectId(cabrequestdetail.getProjectId());
					adhocObj.setFromAddress(cabrequestdetail.getFromAddress());
					adhocObj.setFromAliasName(cabrequestdetail.getFromAliasName());
					adhocObj.setFromCity(cabrequestdetail.getFromCity());
					adhocObj.setFromLandmark(cabrequestdetail.getFromLandmark());
					adhocObj.setFromLocation(cabrequestdetail.getFromLocation());
					adhocObj.setFromPincode(cabrequestdetail.getFromPincode());
					adhocObj.setToAddress(cabrequestdetail.getToAddress());
					adhocObj.setToAliasName(cabrequestdetail.getToAliasName());
					adhocObj.setToCity(cabrequestdetail.getToCity());
					adhocObj.setToLandmark(cabrequestdetail.getToLandmark());
					adhocObj.setToLocation(cabrequestdetail.getToLocation());
					adhocObj.setToPincode(cabrequestdetail.getToPincode());
					adhocObj.setTripSheetId(cabrequestdetail.getTripSheetId());
					adhocObj.setAction(cabrequestdetail.getAction());
					adhocObj.setTravelStatus(cabrequestdetail.getTravelStatus());
					adhocObj.setProjectName(projectName);
					adhocCabRequestsBeanList.add(adhocObj);
				}
			}
		}

		adhocCabRequestsBeanList.sort(Comparator.comparing(AdhocCabRequestBean::getScheduleDate)
				.thenComparing(AdhocCabRequestBean::getReqTime));

		return adhocCabRequestsBeanList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<EmpCabRequest> findAllRequestByEmpId(String empId) {
		List<EmpCabRequest> cabReq = cabRequestDao.findAllRequestByEmpId(empId);
		 for(EmpCabRequest empCabRequest : cabReq) {
			 if(empCabRequest.getAdhocOrMonthly().equalsIgnoreCase("Monthly")) {
				 Boolean isActive= false;
				  for(EmpCabRequestDetails cabRequestDetails : empCabRequest.getEmpCabRequestDetails()) {
					  if(cabRequestDetails.getActiveRequest()) {
						  isActive = true;
						  break;
					  }
				  }
				  empCabRequest.setActiveRequest(isActive);
				  cabRequestDao.updateCabRequest(empCabRequest);
			 }
		  }
		
		 return cabReq;
	}

	@Override
	public void deleteEmpCabRequest(int cabRequestId) {
		cabRequestDao.deleteCabRequest(cabRequestId);
	}

	@Override
	public void deleteIndivitualCabRequest(int requestId) {
		cabRequestDao.deleteIndivitualCabRequest(requestId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AdhocCabRequestBean> findByCabRequestId(int cabRequestId) {

		List<AdhocCabRequestBean> cabRequestBeanList = null;
		EmpCabRequest cabRequest = cabRequestDao.findByCabRequestId(cabRequestId);

		if (cabRequest != null) {
			cabRequestBeanList = new ArrayList<>();

			 Set<EmpCabRequestDetails> cabRequestDetails = cabRequest.getEmpCabRequestDetails();

			for (EmpCabRequestDetails details : cabRequestDetails) {
				AdhocCabRequestBean adhocCabRequestBean = new AdhocCabRequestBean();

				// From Main table
				adhocCabRequestBean
						.setProjectName(projectDetailsService.findProjectById(details.getProjectId()).getProjectName());
				adhocCabRequestBean.setAdhocMonthly(details.getEmpCabRequest().getAdhocOrMonthly());
				adhocCabRequestBean.setManagerRemark(details.getEmpCabRequest().getManagerRemark());
				adhocCabRequestBean.setRequestedBy(details.getEmpCabRequest().getUser().getName());
				adhocCabRequestBean.setUserId(details.getEmpCabRequest().getUser().getId());
				adhocCabRequestBean.setGuestUserName(details.getEmpCabRequest().getGuestUserName());
				adhocCabRequestBean.setManagerRemark(details.getEmpCabRequest().getManagerRemark());
				
				// Imran : Removed from main table  ---------------------------------------------
			//	adhocCabRequestBean.setDropTime(details.getEmpCabRequest().getDropTime());
			//	adhocCabRequestBean.setPickTime(details.getEmpCabRequest().getPickupTime());
				// ------------------------------------------------------------------------------
				
				adhocCabRequestBean.setReason(details.getEmpCabRequest().getReason());
				adhocCabRequestBean.setRequestDate(details.getEmpCabRequest().getReqDate());
				adhocCabRequestBean.setRequestedFor(details.getEmpCabRequest().getRequestFor());
				adhocCabRequestBean.setRequestType(details.getEmpCabRequest().getRequestType());
				// adhocCabRequestBean.setTravelType(details.getEmpCabRequest().getTravelType());
				adhocCabRequestBean.setEmpName(details.getEmpCabRequest().getUser().getName());
				adhocCabRequestBean.setEmpMailId(details.getEmpCabRequest().getUser().getEmail());
				adhocCabRequestBean.setFrom(details.getEmpCabRequest().getFromDate());
				adhocCabRequestBean.setTo(details.getEmpCabRequest().getToDate());
				adhocCabRequestBean.setMobileNumber(details.getMobileNumber());
				// From Sub Table
				adhocCabRequestBean.setAction(details.getAction());
				adhocCabRequestBean.setActiveRequest(details.getActiveRequest());
				adhocCabRequestBean.setFromAddress(details.getFromAddress());
				adhocCabRequestBean.setFromAliasName(details.getFromAliasName());
				adhocCabRequestBean.setFromCity(details.getFromCity());
				adhocCabRequestBean.setFromLandmark(details.getFromLandmark());
				adhocCabRequestBean.setFromLocation(details.getFromLocation());
				adhocCabRequestBean.setFromPincode(details.getFromPincode());
				adhocCabRequestBean.setId(details.getRequestId());
				adhocCabRequestBean.setMobileNumber(details.getMobileNumber());
				adhocCabRequestBean.setReqTime(details.getRequestime());
				adhocCabRequestBean.setRequestStatus(details.getReqStatus());
				adhocCabRequestBean.setScheduleDate(details.getScheduleDate());
				adhocCabRequestBean.setToAddress(details.getToAddress());
				adhocCabRequestBean.setToAliasName(details.getToAliasName());
				adhocCabRequestBean.setToCity(details.getToCity());
				adhocCabRequestBean.setToLandmark(details.getToLandmark());
				adhocCabRequestBean.setToLocation(details.getToLocation());
				adhocCabRequestBean.setToPincode(details.getToPincode());
				adhocCabRequestBean.setTravelStatus(details.getTravelStatus());
				adhocCabRequestBean.setProjectId(details.getProjectId());
				
				if(details.getEmpCabRequest().getRequestType().contains("pickup"))
				adhocCabRequestBean.setPickTime(details.getRequestime());
				else if(details.getEmpCabRequest().getRequestType().contains("drop"))
				adhocCabRequestBean.setDropTime(details.getRequestime());
				else {
					adhocCabRequestBean.setDropTime(details.getEmpCabRequest().getDropTime());
					adhocCabRequestBean.setPickTime(details.getEmpCabRequest().getPickupTime());
				}
				cabRequestBeanList.add(adhocCabRequestBean);

			}
		}

		return cabRequestBeanList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AdhocCabRequestBean> getEmpCabRequest(CabReportBean cabReport) {
		// DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		List<EmpCabRequestDetails> cabRequestDetails = cabRequestDao.getEmpCabRequest(cabReport);

		List<AdhocCabRequestBean> cabRequestBeanList = new LinkedList<>();

		for (EmpCabRequestDetails details : cabRequestDetails) {
			AdhocCabRequestBean adhocCabRequestBean = new AdhocCabRequestBean();
			Date scheduledDate = details.getScheduleDate();
			adhocCabRequestBean.setId(details.getRequestId());
			adhocCabRequestBean.setRequestType(details.getPickOrDrop());
			adhocCabRequestBean.setRequestStatus(details.getReqStatus());
			adhocCabRequestBean.setMobileNumber(details.getMobileNumber());
			adhocCabRequestBean.setReqTime(details.getRequestime());
			adhocCabRequestBean.setProjectId(details.getProjectId());
			adhocCabRequestBean
					.setProjectName(projectDetailsService.findProjectById(details.getProjectId()).getProjectName());
			adhocCabRequestBean.setFromAddress(details.getFromAddress());
			adhocCabRequestBean.setFromAliasName(details.getFromAliasName());
			adhocCabRequestBean.setFromCity(details.getFromCity());
			adhocCabRequestBean.setFromLandmark(details.getFromLandmark());
			adhocCabRequestBean.setFromLocation(details.getFromLocation());
			adhocCabRequestBean.setFromPincode(details.getFromPincode());
			adhocCabRequestBean.setToAddress(details.getToAddress());
			adhocCabRequestBean.setToAliasName(details.getToAliasName());
			adhocCabRequestBean.setToCity(details.getToCity());
			adhocCabRequestBean.setToLandmark(details.getToLandmark());
			adhocCabRequestBean.setToLocation(details.getToLocation());
			adhocCabRequestBean.setToPincode(details.getToPincode());
			adhocCabRequestBean.setScheduleDate(scheduledDate);
			adhocCabRequestBean.setTravelStatus(details.getTravelStatus());
			adhocCabRequestBean.setRequestDate(details.getEmpCabRequest().getReqDate());
			cabRequestBeanList.add(adhocCabRequestBean);
		}
		cabRequestBeanList.sort(Comparator.comparing(AdhocCabRequestBean::getScheduleDate)
				.thenComparing(AdhocCabRequestBean::getReqTime));

		return cabRequestBeanList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AdhocCabRequestBean> getFlmCabReport(CabReportBean cabReport) {
		// DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		List<EmpCabRequestDetails> cabRequestDetails = cabRequestDao.getEmpCabRequest(cabReport);

		List<AdhocCabRequestBean> cabRequestBeanList = new LinkedList<>();

		for (EmpCabRequestDetails details : cabRequestDetails) {
			/* if(TmsConstant.APPROVED.equalsIgnoreCase(details.getReqStatus())){ */

			AdhocCabRequestBean adhocCabRequestBean = new AdhocCabRequestBean();
			if (details.getTripSheetId() != null) {
				TripSheetDetails sheetDetails = tripSheetDetailsService.findByTripSheetId(details.getTripSheetId());
				adhocCabRequestBean.setTripSheetNumber(sheetDetails.getTripSheetNumber());
			}

			adhocCabRequestBean.setAction(details.getAction());
			adhocCabRequestBean.setActiveRequest(details.getActiveRequest());
			adhocCabRequestBean.setAdhocMonthly(details.getEmpCabRequest().getAdhocOrMonthly());
			
			if(details.getPickOrDrop().contains("pickup"))
			adhocCabRequestBean.setDropTime(details.getRequestime());
			else
			adhocCabRequestBean.setPickTime(details.getRequestime());
			
			adhocCabRequestBean
					.setProjectName(projectDetailsService.findProjectById(details.getProjectId()).getProjectName());
			adhocCabRequestBean.setRequestedBy(details.getEmpCabRequest().getUser().getName());
			adhocCabRequestBean.setUserId(details.getEmpCabRequest().getUser().getId());
			adhocCabRequestBean.setFromAddress(details.getFromAddress());
			adhocCabRequestBean.setFromAliasName(details.getFromAliasName());
			adhocCabRequestBean.setFromCity(details.getFromCity());
			adhocCabRequestBean.setFromLandmark(details.getFromLandmark());
			adhocCabRequestBean.setFromLocation(details.getFromLocation());
			adhocCabRequestBean.setFromPincode(details.getFromPincode());
			adhocCabRequestBean.setGuestUserName(details.getEmpCabRequest().getGuestUserName());
			adhocCabRequestBean.setId(details.getRequestId());
			adhocCabRequestBean.setManagerRemark(details.getEmpCabRequest().getManagerRemark());
			adhocCabRequestBean.setMobileNumber(details.getMobileNumber());
			
			adhocCabRequestBean.setReason(details.getEmpCabRequest().getReason());
			adhocCabRequestBean.setReqTime(details.getRequestime());
			adhocCabRequestBean.setRequestDate(details.getEmpCabRequest().getReqDate());
			adhocCabRequestBean.setRequestedFor(details.getEmpCabRequest().getRequestFor());
			adhocCabRequestBean.setRequestStatus(details.getReqStatus());
			adhocCabRequestBean.setRequestType(details.getPickOrDrop());
			adhocCabRequestBean.setScheduleDate(details.getScheduleDate());
			adhocCabRequestBean.setToAddress(details.getToAddress());
			adhocCabRequestBean.setToAliasName(details.getToAliasName());
			adhocCabRequestBean.setToCity(details.getToCity());
			adhocCabRequestBean.setToLandmark(details.getToLandmark());
			adhocCabRequestBean.setToLocation(details.getToLocation());
			adhocCabRequestBean.setToPincode(details.getToPincode());
			adhocCabRequestBean.setTravelStatus(details.getTravelStatus());
			adhocCabRequestBean.setTravelType(details.getEmpCabRequest().getTravelType());
			adhocCabRequestBean.setEmpName(details.getEmpCabRequest().getUser().getName());
			adhocCabRequestBean.setEmpMailId(details.getEmpCabRequest().getUser().getEmail());
			adhocCabRequestBean.setTripSheetId(details.getTripSheetId());
			adhocCabRequestBean.setEmpGender(details.getEmpCabRequest().getUser().getGender());

			cabRequestBeanList.add(adhocCabRequestBean);
		}
		cabRequestBeanList.sort(Comparator.comparing(AdhocCabRequestBean::getScheduleDate)
				.thenComparing(AdhocCabRequestBean::getReqTime));

		return cabRequestBeanList;
	}

	@Override
	public EmpCabRequestDetails findByRequestId(int requestId) {
		return cabRequestDao.findByRequestId(requestId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Integer[][] getAdhocRequestCount(String empId) {
		return cabRequestDao.getAdhocRequestCount(empId);
	}

	@Override
	public void updateCabRequestByManager(CabRequestRemarkBean details) {
		cabRequestDao.updateCabRequestByManager(details);
	}

	@Override
	public List<EmpCabRequestDetails> findAllCabRequestDetails(int cabRequestId) {
		return cabRequestDao.findAllCabRequestDetails(cabRequestId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AdhocCabRequestBean> findAllOtherOpenRequest(int requestedById) {
		List<EmpCabRequestDetails> cabRequestDetailList = cabRequestDao.findAllOtherOpenRequest(requestedById);
		List<AdhocCabRequestBean> adhocCabRequestsBeanList = Collections.EMPTY_LIST;
		if (cabRequestDetailList.size() > 0) {
			adhocCabRequestsBeanList = new ArrayList<AdhocCabRequestBean>();
			String requestedFor = null;
			String requestedBy = null;
			// for (EmpCabRequest cabRequest : cabRequests) {
			// Set<EmpCabRequestDetails> cabRequestDetailList =
			// cabRequest.getEmpCabRequestDetails();
			for (EmpCabRequestDetails cabrequestdetail : cabRequestDetailList) {
				Date scheduleDate = cabrequestdetail.getScheduleDate();
				if (cabrequestdetail.getActiveRequest()
						&& (DateUtils.isSameDay(scheduleDate, new Date()) || scheduleDate.after((new Date())))) {
					AdhocCabRequestBean adhocObj = new AdhocCabRequestBean();
					if (cabrequestdetail.getEmpCabRequest().getRequestFor().equalsIgnoreCase("others")) {
						requestedBy = employeeService.findById(cabrequestdetail.getEmpCabRequest().getRequestingEmpId())
								.getName();
						requestedFor = employeeService.findById(cabrequestdetail.getEmpCabRequest().getUser().getId())
								.getName();
						adhocObj.setRequestedBy(requestedBy);
					} else {
						if (cabrequestdetail.getEmpCabRequest().getRequestFor().equalsIgnoreCase("guest")) {
							requestedFor = cabrequestdetail.getEmpCabRequest().getGuestUserName();
						}
					}

					adhocObj.setId(cabrequestdetail.getRequestId());
					adhocObj.setRequestType(cabrequestdetail.getPickOrDrop());
					adhocObj.setScheduleDate(scheduleDate);
					adhocObj.setReason(cabrequestdetail.getEmpCabRequest().getReason());
					adhocObj.setManagerRemark(cabrequestdetail.getEmpCabRequest().getManagerRemark());
					adhocObj.setRequestStatus(cabrequestdetail.getReqStatus());
					adhocObj.setMobileNumber(cabrequestdetail.getMobileNumber());
					adhocObj.setAdhocMonthly(cabrequestdetail.getEmpCabRequest().getAdhocOrMonthly());
					adhocObj.setReqTime(cabrequestdetail.getRequestime());
					adhocObj.setProjectId(cabrequestdetail.getProjectId());
					Project project = projectDetailsService.findProjectById(cabrequestdetail.getProjectId());
					adhocObj.setProjectName(project.getProjectName());
					adhocObj.setFromAddress(cabrequestdetail.getFromAddress());
					adhocObj.setFromAliasName(cabrequestdetail.getFromAliasName());
					adhocObj.setFromCity(cabrequestdetail.getFromCity());
					adhocObj.setFromLandmark(cabrequestdetail.getFromLandmark());
					adhocObj.setFromLocation(cabrequestdetail.getFromLocation());
					adhocObj.setFromPincode(cabrequestdetail.getFromPincode());
					adhocObj.setToAddress(cabrequestdetail.getToAddress());
					adhocObj.setToAliasName(cabrequestdetail.getToAliasName());
					adhocObj.setToCity(cabrequestdetail.getToCity());
					adhocObj.setToLandmark(cabrequestdetail.getToLandmark());
					adhocObj.setToLocation(cabrequestdetail.getToLocation());
					adhocObj.setToPincode(cabrequestdetail.getToPincode());
					adhocObj.setEmpName(cabrequestdetail.getEmpCabRequest().getUser().getName());
					adhocObj.setRequestedFor(requestedFor);
					adhocObj.setTravelStatus(cabrequestdetail.getTravelStatus());
					adhocObj.setRequestDate(cabrequestdetail.getEmpCabRequest().getReqDate());
					adhocCabRequestsBeanList.add(adhocObj);
					// }
				}
			}
		}
		adhocCabRequestsBeanList.sort(Comparator.comparing(AdhocCabRequestBean::getScheduleDate)
				.thenComparing(AdhocCabRequestBean::getReqTime));

		// Collections.sort(adhocCabRequestsBeanList, new AdhocCabRequestBean());
		return adhocCabRequestsBeanList;

	}

	@Override
	public void updateCabReqTripSheetDetails(TripSheetDetailsBean cabDetails, int tripId) {// List<EmpCabRequestDetails>
																							// cabDetailsId
		cabRequestDao.updateCabReqTripSheetDetails(cabDetails, tripId);
	}

	@Override
	public void updateCabDetails(EmpCabRequestDetails details) {
		cabRequestDao.updateCabDetails(details);
	}

	// @Override
	// public void empAdhocAcknowledgeMail(UserProfileBean profileBean, Integer
	// cabReqId, String requestFor) {
	// tmsMailSend.empAdhocAcknowledgeMail(profileBean, cabReqId, requestFor);
	// }
	//
	// @Override
	// public void managerRemarkMail(EmployeeBean employee, Integer cabReqId, String
	// requestFor,
	// CabRequestRemarkBean cabRemarkBean) {
	// tmsMailSend.managerRemarkMail(employee, cabReqId, requestFor, cabRemarkBean);
	// }

	@Override
	public List<EmpCabRequestDetails> findCabRequestByTripId(int tripId) {
		return cabRequestDao.findCabRequestByTripId(tripId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AdhocCabRequestBean> findDailyCabRequest() {

		List<AdhocCabRequestBean> beanList = new ArrayList<AdhocCabRequestBean>();

		int today = new Date().getDate();

		String[] todayPickUpTimings = {  "07:00 PM", "07:30 PM", "08:00 PM", "08:30 PM", "09:00 PM", "09:30 PM", "10:00 PM", "10:30 PM",
				"11:00 PM","11:30 PM"};

		String[] todayDropTimings = { "09:30 PM", "10:00 PM", "10:30 PM","11:00 PM","11:30 PM" };

		String[] tmrPickUpTimings = { "12:00 AM", "12:30 AM", "01:00 AM", "01:30 AM",
				"02:00 AM", "02:30 AM", "03:00 AM", "03:30 AM",
				"04:00 AM", "04:30 AM","05:00 AM", "05:30 AM", "06:00 AM", "06:30 AM" };

		String[] tmrDropTimings = { "12:00 AM", "12:30 AM", "01:00 AM", "01:30 AM",
				"02:00 AM", "02:30 AM", "03:00 AM", "03:30 AM",
				"04:00 AM", "04:30 AM","05:00 AM", "05:30 AM", "06:00 AM", "06:30 AM" };

		boolean check = false;

		for (EmpCabRequestDetails details : cabRequestDao.findDailyCabRequest()) {

			if (details.getPickOrDrop().contains("pickup")) {
				if (today == details.getScheduleDate().getDate()
						&& Arrays.asList(todayPickUpTimings).contains(details.getRequestime()))
					check = true;
				else {
					if (today != details.getScheduleDate().getDate()
							&& Arrays.asList(tmrPickUpTimings).contains(details.getRequestime()))
						check = true;
				}
			} else {
				if (today == details.getScheduleDate().getDate()
						&& Arrays.asList(todayDropTimings).contains(details.getRequestime()))
					check = true;
				else {
					if (today != details.getScheduleDate().getDate()
							&& Arrays.asList(tmrDropTimings).contains(details.getRequestime()))
						check = true;
				}
			}
			if (check) {
				AdhocCabRequestBean adhocObj = new AdhocCabRequestBean();

				if (details.getTripSheetId() != null) {
					TripSheetDetails sheetDetails = tripSheetDetailsService.findByTripSheetId(details.getTripSheetId());
					adhocObj.setTripSheetNumber(sheetDetails.getTripSheetNumber());
					if (sheetDetails != null) {
						adhocObj.setIsEscort(sheetDetails.getIsEscort());
						adhocObj.setEscortName(sheetDetails.getEscortName());
					}
				}

				adhocObj.setEmpName(details.getEmpCabRequest().getUser().getName());
				adhocObj.setMobileNumber(details.getMobileNumber());
				Project proj = projectDetailsService.findProjectById(details.getProjectId());
				adhocObj.setProjectName(proj.getProjectName());
				adhocObj.setScheduleDate(details.getScheduleDate());
				adhocObj.setReqTime(details.getRequestime());
				adhocObj.setFromLocation(details.getFromLocation());
				adhocObj.setToLocation(details.getToLocation());
				adhocObj.setUserId(details.getEmpCabRequest().getUser().getId());
				adhocObj.setEmpGender(
						employeeService.findEmpByEmpId(details.getEmpCabRequest().getUser().getId()).getGender());
				adhocObj.setEmpName(
						employeeService.findEmpByEmpId(details.getEmpCabRequest().getUser().getId()).getName());
				adhocObj.setId(details.getRequestId());
				adhocObj.setRequestType(details.getPickOrDrop());
				adhocObj.setReason(details.getEmpCabRequest().getReason());
				adhocObj.setRequestDate(details.getEmpCabRequest().getReqDate());
				adhocObj.setManagerRemark(details.getEmpCabRequest().getManagerRemark());
				adhocObj.setRequestStatus(details.getReqStatus());
				adhocObj.setMobileNumber(details.getMobileNumber());
				adhocObj.setAdhocMonthly(details.getEmpCabRequest().getAdhocOrMonthly());
				adhocObj.setReqTime(details.getRequestime());
				adhocObj.setProjectId(details.getProjectId());
				adhocObj.setFromAddress(details.getFromAddress());
				adhocObj.setFromAliasName(details.getFromAliasName());
				adhocObj.setFromCity(details.getFromCity());
				adhocObj.setFromLandmark(details.getFromLandmark());
				adhocObj.setFromLocation(details.getFromLocation());
				adhocObj.setFromPincode(details.getFromPincode());
				adhocObj.setToAddress(details.getToAddress());
				adhocObj.setToAliasName(details.getToAliasName());
				adhocObj.setToCity(details.getToCity());
				adhocObj.setToLandmark(details.getToLandmark());
				adhocObj.setToLocation(details.getToLocation());
				adhocObj.setToPincode(details.getToPincode());
				adhocObj.setTripSheetId(details.getTripSheetId());
				adhocObj.setAction(details.getAction());
				adhocObj.setTravelStatus(details.getTravelStatus());

				beanList.add(adhocObj);
				beanList.sort(Comparator.comparing(AdhocCabRequestBean::getScheduleDate).thenComparing(AdhocCabRequestBean::getRequestType)
						.thenComparing(AdhocCabRequestBean::getReqTime));

				check = false;
			}
		}
		return beanList;
	}

	@Override
	public void updateAddressByUserandDate(EmployeeAddressBean addrBean, String travelStatus) {
		cabRequestDao.updateAddressByUserandDate(addrBean, travelStatus);

	}

	@Override
	public void removeTripId(Integer detailsId, Integer tripId) {
		cabRequestDao.removeTripId(detailsId, tripId);
	}

	@Override
	public Integer getCabRequestCountByDate(String date) {
		return cabRequestDao.getCabRequestCountByDate(date);
	}

	@Override
	public List<EmpCabRequestDetails> findAllRequestsByMonth(int month ,int year) {
		return cabRequestDao.findAllRequestsByMonth(month,year);
	}

	@Override
	public List<AdhocCabRequestBean> findAllMonthlyRequestByEmpId(Integer empId, String status) {
		List<EmpCabRequestDetails> cabRequestDetailList;
		List<EmpCabRequestDetails> cabRequestDetailListByStatus = new ArrayList<EmpCabRequestDetails>();

		if (empId != null) {

			cabRequestDetailList = cabRequestDao.findAllMonthlyRequestByEmpId(empId);

			for (EmpCabRequestDetails details : cabRequestDetailList) {

				if (status != null) {
					if (status.contains("Used")) {

						if ((details.getScheduleDate().getDate() <= new Date().getDate()
								&& details.getScheduleDate().getMonth() == new Date().getMonth()
								&& details.getScheduleDate().getYear() == new Date().getYear())
								&& details.getActiveRequest()
								&& details.getEmpCabRequest().getAdhocOrMonthly().equals("Monthly")
								&& TmsConstant.TRAVEL_STATUS_COMPLETED.equalsIgnoreCase
								(details.getTravelStatus())) {
							cabRequestDetailListByStatus.add(details);
						}
					}

						else if (status.contains("Unused")) {
							if ((details.getScheduleDate().getDate() >= new Date().getDate()
									&& details.getScheduleDate().getMonth() == new Date().getMonth()
									&& details.getScheduleDate().getYear() == new Date().getYear())
									&& details.getActiveRequest()
									&& details.getEmpCabRequest().getAdhocOrMonthly().equals("Monthly")) {
								cabRequestDetailListByStatus.add(details);
							}
						} else if (status.contains("NoShow")) {
							if (details.getEmpCabRequest().getAdhocOrMonthly().equals("Monthly")
									&& (TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User
											.equalsIgnoreCase(details.getTravelStatus())|| TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm.equalsIgnoreCase(details.getTravelStatus())
											|| TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor.equalsIgnoreCase(details.getTravelStatus()))) {
								cabRequestDetailListByStatus.add(details);
							}
						}
				}
			}
		}
		List<AdhocCabRequestBean> adhocCabRequestsBeanList = Collections.EMPTY_LIST;
		if (cabRequestDetailListByStatus.size() > 0) {
			adhocCabRequestsBeanList = new ArrayList<AdhocCabRequestBean>();
			for (EmpCabRequestDetails cabrequestdetail : cabRequestDetailListByStatus) {
				Date scheduleDate = cabrequestdetail.getScheduleDate();
				AdhocCabRequestBean adhocObj = new AdhocCabRequestBean();
				String projectName = projectDetailsService.findProjectById(cabrequestdetail.getProjectId())
						.getProjectName();
				adhocObj.setUserId(cabrequestdetail.getEmpCabRequest().getUser().getId());
				adhocObj.setEmpGender(employeeService
						.findEmpByEmpId(cabrequestdetail.getEmpCabRequest().getUser().getId()).getGender());
				adhocObj.setEmpName(employeeService
						.findEmpByEmpId(cabrequestdetail.getEmpCabRequest().getUser().getId()).getName());
				adhocObj.setId(cabrequestdetail.getRequestId());
				adhocObj.setRequestType(cabrequestdetail.getPickOrDrop());

				adhocObj.setScheduleDate(scheduleDate);
				adhocObj.setReason(cabrequestdetail.getEmpCabRequest().getReason());
				adhocObj.setRequestDate(cabrequestdetail.getEmpCabRequest().getReqDate());
				adhocObj.setManagerRemark(cabrequestdetail.getEmpCabRequest().getManagerRemark());
				adhocObj.setRequestStatus(cabrequestdetail.getReqStatus());
				adhocObj.setMobileNumber(cabrequestdetail.getMobileNumber());
				adhocObj.setAdhocMonthly(cabrequestdetail.getEmpCabRequest().getAdhocOrMonthly());
				adhocObj.setReqTime(cabrequestdetail.getRequestime());
				adhocObj.setProjectId(cabrequestdetail.getProjectId());
				adhocObj.setFromAddress(cabrequestdetail.getFromAddress());
				adhocObj.setFromAliasName(cabrequestdetail.getFromAliasName());
				adhocObj.setFromCity(cabrequestdetail.getFromCity());
				adhocObj.setFromLandmark(cabrequestdetail.getFromLandmark());
				adhocObj.setFromLocation(cabrequestdetail.getFromLocation());
				adhocObj.setFromPincode(cabrequestdetail.getFromPincode());
				adhocObj.setToAddress(cabrequestdetail.getToAddress());
				adhocObj.setToAliasName(cabrequestdetail.getToAliasName());
				adhocObj.setToCity(cabrequestdetail.getToCity());
				adhocObj.setToLandmark(cabrequestdetail.getToLandmark());
				adhocObj.setToLocation(cabrequestdetail.getToLocation());
				adhocObj.setToPincode(cabrequestdetail.getToPincode());
				adhocObj.setTripSheetId(cabrequestdetail.getTripSheetId());
				adhocObj.setAction(cabrequestdetail.getAction());
				adhocObj.setTravelStatus(cabrequestdetail.getTravelStatus());
				adhocObj.setProjectName(projectName);
				adhocCabRequestsBeanList.add(adhocObj);

			}
		}

		adhocCabRequestsBeanList.sort(Comparator.comparing(AdhocCabRequestBean::getScheduleDate)
				.thenComparing(AdhocCabRequestBean::getReqTime));

		return adhocCabRequestsBeanList;

	}

	@Override
	public List<EmpCabRequestDetails> findAllOpenRequestByEmpId(Integer empId) {
		
		return cabRequestDao.findAllOpenRequestByEmpId(empId);
	}

	@Override
	public void updateCabRequest(EmpCabRequest request) {
		cabRequestDao.updateCabRequest(request);
		
	}
	@Override
	public List<AdhocCabRequestBean> findAllInActiveRequestByEmpId(Integer empId, String status) {
		List<EmpCabRequestDetails> cabRequestDetailList;
		List<EmpCabRequestDetails> cabRequestDetailListByStatus = new ArrayList<EmpCabRequestDetails>();

		if (empId != null) {

			cabRequestDetailList = cabRequestDao.findAllInActiveRequestByEmpId(empId);

			for (EmpCabRequestDetails details : cabRequestDetailList) {

				if (status != null) {
					if (status.contains(TmsConstant.CANCELED)) {

						if ((details.getScheduleDate().getDate() >= new Date().getDate()
								&& details.getScheduleDate().getMonth() == new Date().getMonth()
								&& details.getScheduleDate().getYear() == new Date().getYear())
								&& !details.getActiveRequest()
								&& details.getEmpCabRequest().getAdhocOrMonthly().equals("Adhoc") &&
								details.getTravelStatus().contains(TmsConstant.CANCELED_B_USER)) {
							cabRequestDetailListByStatus.add(details);
						}
					}

						else if (status.contains(TmsConstant.REJECTED)) {
							if ((details.getScheduleDate().getDate() >= new Date().getDate()
									&& details.getScheduleDate().getMonth() == new Date().getMonth()
									&& details.getScheduleDate().getYear() == new Date().getYear())
									&& !details.getActiveRequest()
									&& details.getEmpCabRequest().getAdhocOrMonthly().equals("Adhoc") &&
									(details.getTravelStatus().contains(TmsConstant.REJECTED_B_MANAGER) || 
											details.getTravelStatus().contains(TmsConstant.REJECTED_B_FLM))) {
								cabRequestDetailListByStatus.add(details);
							}
						} 
				}
			}
		}
		List<AdhocCabRequestBean> adhocCabRequestsBeanList = Collections.EMPTY_LIST;
		if (cabRequestDetailListByStatus.size() > 0) {
			adhocCabRequestsBeanList = new ArrayList<AdhocCabRequestBean>();
			for (EmpCabRequestDetails cabrequestdetail : cabRequestDetailListByStatus) {
				Date scheduleDate = cabrequestdetail.getScheduleDate();
				AdhocCabRequestBean adhocObj = new AdhocCabRequestBean();
				String projectName = projectDetailsService.findProjectById(cabrequestdetail.getProjectId())
						.getProjectName();
				adhocObj.setUserId(cabrequestdetail.getEmpCabRequest().getUser().getId());
				adhocObj.setEmpGender(employeeService
						.findEmpByEmpId(cabrequestdetail.getEmpCabRequest().getUser().getId()).getGender());
				adhocObj.setEmpName(employeeService
						.findEmpByEmpId(cabrequestdetail.getEmpCabRequest().getUser().getId()).getName());
				adhocObj.setId(cabrequestdetail.getRequestId());
				adhocObj.setRequestType(cabrequestdetail.getPickOrDrop());

				adhocObj.setScheduleDate(scheduleDate);
				adhocObj.setReason(cabrequestdetail.getEmpCabRequest().getReason());
				adhocObj.setRequestDate(cabrequestdetail.getEmpCabRequest().getReqDate());
				adhocObj.setManagerRemark(cabrequestdetail.getEmpCabRequest().getManagerRemark());
				adhocObj.setRequestStatus(cabrequestdetail.getReqStatus());
				adhocObj.setMobileNumber(cabrequestdetail.getMobileNumber());
				adhocObj.setAdhocMonthly(cabrequestdetail.getEmpCabRequest().getAdhocOrMonthly());
				adhocObj.setReqTime(cabrequestdetail.getRequestime());
				adhocObj.setProjectId(cabrequestdetail.getProjectId());
				adhocObj.setFromAddress(cabrequestdetail.getFromAddress());
				adhocObj.setFromAliasName(cabrequestdetail.getFromAliasName());
				adhocObj.setFromCity(cabrequestdetail.getFromCity());
				adhocObj.setFromLandmark(cabrequestdetail.getFromLandmark());
				adhocObj.setFromLocation(cabrequestdetail.getFromLocation());
				adhocObj.setFromPincode(cabrequestdetail.getFromPincode());
				adhocObj.setToAddress(cabrequestdetail.getToAddress());
				adhocObj.setToAliasName(cabrequestdetail.getToAliasName());
				adhocObj.setToCity(cabrequestdetail.getToCity());
				adhocObj.setToLandmark(cabrequestdetail.getToLandmark());
				adhocObj.setToLocation(cabrequestdetail.getToLocation());
				adhocObj.setToPincode(cabrequestdetail.getToPincode());
				adhocObj.setTripSheetId(cabrequestdetail.getTripSheetId());
				adhocObj.setAction(cabrequestdetail.getAction());
				adhocObj.setTravelStatus(cabrequestdetail.getTravelStatus());
				adhocObj.setProjectName(projectName);
				adhocCabRequestsBeanList.add(adhocObj);

			}
		}

		adhocCabRequestsBeanList.sort(Comparator.comparing(AdhocCabRequestBean::getScheduleDate)
				.thenComparing(AdhocCabRequestBean::getReqTime));

		return adhocCabRequestsBeanList;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AdhocCabRequestBean> findTodaysCabRequest() {

		List<AdhocCabRequestBean> beanList = new ArrayList<AdhocCabRequestBean>();
		int today = new Date().getDate();
		String[] todayPickUpTimings = { "07:00 PM", "07:30 PM", "08:00 PM", "08:30 PM", "09:00 PM", "09:30 PM", "10:00 PM", "10:30 PM",
				"11:00 PM","11:30 PM"};
		String[] todayDropTimings = { "09:30 PM", "10:00 PM", "10:30 PM","11:00 PM","11:30 PM" };
		boolean check = false;
		for (EmpCabRequestDetails details : cabRequestDao.findTodaysCabRequest()) {
			if (details.getPickOrDrop().contains("pickup")) {
				if (today == details.getScheduleDate().getDate()
						&& Arrays.asList(todayPickUpTimings).contains(details.getRequestime()))
					check = true;
				
			} else {
				if (today == details.getScheduleDate().getDate()
						&& Arrays.asList(todayDropTimings).contains(details.getRequestime()))
					check = true;
			}
			if (check) {
				AdhocCabRequestBean adhocObj = new AdhocCabRequestBean();

				if (details.getTripSheetId() != null) {
					TripSheetDetails sheetDetails = tripSheetDetailsService.findByTripSheetId(details.getTripSheetId());
					adhocObj.setTripSheetNumber(sheetDetails.getTripSheetNumber());
					if (sheetDetails != null) {
						adhocObj.setIsEscort(sheetDetails.getIsEscort());
						adhocObj.setEscortName(sheetDetails.getEscortName());
					}
				}

				adhocObj.setEmpName(details.getEmpCabRequest().getUser().getName());
				adhocObj.setMobileNumber(details.getMobileNumber());
				Project proj = projectDetailsService.findProjectById(details.getProjectId());
				adhocObj.setProjectName(proj.getProjectName());
				adhocObj.setScheduleDate(details.getScheduleDate());
				adhocObj.setReqTime(details.getRequestime());
				adhocObj.setFromLocation(details.getFromLocation());
				adhocObj.setToLocation(details.getToLocation());
				adhocObj.setUserId(details.getEmpCabRequest().getUser().getId());
				adhocObj.setEmpGender(
						employeeService.findEmpByEmpId(details.getEmpCabRequest().getUser().getId()).getGender());
				adhocObj.setEmpName(
						employeeService.findEmpByEmpId(details.getEmpCabRequest().getUser().getId()).getName());
				adhocObj.setId(details.getRequestId());
				adhocObj.setRequestType(details.getPickOrDrop());
				adhocObj.setReason(details.getEmpCabRequest().getReason());
				adhocObj.setRequestDate(details.getEmpCabRequest().getReqDate());
				adhocObj.setManagerRemark(details.getEmpCabRequest().getManagerRemark());
				adhocObj.setRequestStatus(details.getReqStatus());
				adhocObj.setMobileNumber(details.getMobileNumber());
				adhocObj.setAdhocMonthly(details.getEmpCabRequest().getAdhocOrMonthly());
				adhocObj.setReqTime(details.getRequestime());
				adhocObj.setProjectId(details.getProjectId());
				adhocObj.setFromAddress(details.getFromAddress());
				adhocObj.setFromAliasName(details.getFromAliasName());
				adhocObj.setFromCity(details.getFromCity());
				adhocObj.setFromLandmark(details.getFromLandmark());
				adhocObj.setFromLocation(details.getFromLocation());
				adhocObj.setFromPincode(details.getFromPincode());
				adhocObj.setToAddress(details.getToAddress());
				adhocObj.setToAliasName(details.getToAliasName());
				adhocObj.setToCity(details.getToCity());
				adhocObj.setToLandmark(details.getToLandmark());
				adhocObj.setToLocation(details.getToLocation());
				adhocObj.setToPincode(details.getToPincode());
				adhocObj.setTripSheetId(details.getTripSheetId());
				adhocObj.setAction(details.getAction());
				adhocObj.setTravelStatus(details.getTravelStatus());

				beanList.add(adhocObj);
				beanList.sort(Comparator.comparing(AdhocCabRequestBean::getScheduleDate).thenComparing(AdhocCabRequestBean::getRequestType)
						.thenComparing(AdhocCabRequestBean::getReqTime));

				check = false;
			}
		}
		return beanList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AdhocCabRequestBean> findTomorrowsCabRequest() {

		List<AdhocCabRequestBean> beanList = new ArrayList<AdhocCabRequestBean>();

		int today = new Date().getDate();

		String[] tmrPickUpTimings = {"12:00 AM", "12:30 AM", "01:00 AM", "01:30 AM",
				"02:00 AM", "02:30 AM", "03:00 AM", "03:30 AM",
				"04:00 AM", "04:30 AM","05:00 AM", "05:30 AM", "06:00 AM", "06:30 AM" };

		String[] tmrDropTimings = { "12:00 AM", "12:30 AM", "01:00 AM", "01:30 AM",
				"02:00 AM", "02:30 AM", "03:00 AM", "03:30 AM",
				"04:00 AM", "04:30 AM","05:00 AM", "05:30 AM","06:00 AM", "06:30 AM" };

		boolean check = false;

		for (EmpCabRequestDetails details : cabRequestDao.findDailyCabRequest()) {

			if (details.getPickOrDrop().contains("pickup")) {
					if (today != details.getScheduleDate().getDate()
							&& Arrays.asList(tmrPickUpTimings).contains(details.getRequestime()))
						check = true;
				
			} else {
					if (today != details.getScheduleDate().getDate()
							&& Arrays.asList(tmrDropTimings).contains(details.getRequestime()))
						check = true;
				
			}
			if (check) {
				AdhocCabRequestBean adhocObj = new AdhocCabRequestBean();

				if (details.getTripSheetId() != null) {
					TripSheetDetails sheetDetails = tripSheetDetailsService.findByTripSheetId(details.getTripSheetId());
					adhocObj.setTripSheetNumber(sheetDetails.getTripSheetNumber());
					if (sheetDetails != null) {
						adhocObj.setIsEscort(sheetDetails.getIsEscort());
						adhocObj.setEscortName(sheetDetails.getEscortName());
					}
				}

				adhocObj.setEmpName(details.getEmpCabRequest().getUser().getName());
				adhocObj.setMobileNumber(details.getMobileNumber());
				Project proj = projectDetailsService.findProjectById(details.getProjectId());
				adhocObj.setProjectName(proj.getProjectName());
				adhocObj.setScheduleDate(details.getScheduleDate());
				adhocObj.setReqTime(details.getRequestime());
				adhocObj.setFromLocation(details.getFromLocation());
				adhocObj.setToLocation(details.getToLocation());
				adhocObj.setUserId(details.getEmpCabRequest().getUser().getId());
				adhocObj.setEmpGender(
						employeeService.findEmpByEmpId(details.getEmpCabRequest().getUser().getId()).getGender());
				adhocObj.setEmpName(
						employeeService.findEmpByEmpId(details.getEmpCabRequest().getUser().getId()).getName());
				adhocObj.setId(details.getRequestId());
				adhocObj.setRequestType(details.getPickOrDrop());
				adhocObj.setReason(details.getEmpCabRequest().getReason());
				adhocObj.setRequestDate(details.getEmpCabRequest().getReqDate());
				adhocObj.setManagerRemark(details.getEmpCabRequest().getManagerRemark());
				adhocObj.setRequestStatus(details.getReqStatus());
				adhocObj.setMobileNumber(details.getMobileNumber());
				adhocObj.setAdhocMonthly(details.getEmpCabRequest().getAdhocOrMonthly());
				adhocObj.setReqTime(details.getRequestime());
				adhocObj.setProjectId(details.getProjectId());
				adhocObj.setFromAddress(details.getFromAddress());
				adhocObj.setFromAliasName(details.getFromAliasName());
				adhocObj.setFromCity(details.getFromCity());
				adhocObj.setFromLandmark(details.getFromLandmark());
				adhocObj.setFromLocation(details.getFromLocation());
				adhocObj.setFromPincode(details.getFromPincode());
				adhocObj.setToAddress(details.getToAddress());
				adhocObj.setToAliasName(details.getToAliasName());
				adhocObj.setToCity(details.getToCity());
				adhocObj.setToLandmark(details.getToLandmark());
				adhocObj.setToLocation(details.getToLocation());
				adhocObj.setToPincode(details.getToPincode());
				adhocObj.setTripSheetId(details.getTripSheetId());
				adhocObj.setAction(details.getAction());
				adhocObj.setTravelStatus(details.getTravelStatus());

				beanList.add(adhocObj);
				beanList.sort(Comparator.comparing(AdhocCabRequestBean::getScheduleDate).thenComparing(AdhocCabRequestBean::getRequestType)
						.thenComparing(AdhocCabRequestBean::getReqTime));

				check = false;
			}
		}
		return beanList;
	}
}
