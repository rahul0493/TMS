package com.quinnox.flm.tms.module.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.CabReportBean;
import com.quinnox.flm.tms.module.beans.CabRequestRemarkBean;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.beans.TripSheetDetailsBean;
import com.quinnox.flm.tms.module.dao.CabRequestDao;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.util.DateUtil;

/**
 * @author AmareshP
 *
 */
@Repository("cabRequestDao")
public class CabRequestDaoImpl extends GenericDaoImpl<EmpCabRequest, Integer> implements CabRequestDao {
	@Autowired
	private ProjectDetailsService projectDetailsService;

	public CabRequestDaoImpl() {
		super(EmpCabRequest.class);
	}

	@Override
	@Transactional
	public Integer saveCabRequest(EmpCabRequest cabRequest) {
		return (Integer) getCurrentSession().save(cabRequest);
	}

	@Override
	@Transactional
	public void updateCabRequest(AdhocCabRequestBean adhocBean) {
		// getCurrentSession().saveOrUpdate(empCabRequest);
		EmpCabRequestDetails empCabRequestDetails = getCurrentSession().get(EmpCabRequestDetails.class,
				adhocBean.getId());
		// empCabRequestDetails.setAddress(adhocBean.getAddress());
		empCabRequestDetails.setMobileNumber(adhocBean.getMobileNumber());
		empCabRequestDetails.setFromAddress(adhocBean.getFromAddress());
		empCabRequestDetails.setFromAliasName(adhocBean.getFromAliasName());
		empCabRequestDetails.setFromCity(adhocBean.getFromCity());
		empCabRequestDetails.setFromLandmark(adhocBean.getFromLandmark());
		empCabRequestDetails.setFromLocation(adhocBean.getFromLocation());
		empCabRequestDetails.setFromPincode(adhocBean.getFromPincode());

		empCabRequestDetails.setToAddress(adhocBean.getToAddress());
		empCabRequestDetails.setToAliasName(adhocBean.getToAliasName());
		empCabRequestDetails.setToCity(adhocBean.getToCity());
		empCabRequestDetails.setToLandmark(adhocBean.getToLandmark());
		empCabRequestDetails.setToLocation(adhocBean.getToLocation());
		empCabRequestDetails.setToPincode(adhocBean.getToPincode());

		// empCabRequestDetails.setLandmark(adhocBean.getLandmark());
		// empCabRequestDetails.setLocation(adhocBean.getLocation());
		// empCabRequestDetails.setPincode(adhocBean.getPincode());
		getCurrentSession().update(empCabRequestDetails);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmpCabRequestDetails> findAllOpenRequestByEmpId(Integer employeeId) {

		List<EmpCabRequestDetails> empCabRequestDetails = (List<EmpCabRequestDetails>) getCurrentSession().createQuery(
				"from EmpCabRequestDetails emp_cab_details where emp_cab_details.activeRequest is true and  emp_cab_details.empCabRequest.user.id=:id and  emp_cab_details.scheduleDate >=:currentDate")
				.setInteger("id", employeeId).setDate("currentDate", new Date()).list();

		return empCabRequestDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmpCabRequest> findAllRequestByEmpId(String employeeId) {
		return (List<EmpCabRequest>) getCurrentSession().createQuery("from EmpCabRequest emp where emp.user.id=:id")
				.setString("id", employeeId).list();
	}

	@Override
	@Transactional
	public void deleteCabRequest(int cabRequestId) {
		getCurrentSession().get(EmpCabRequest.class, cabRequestId).setActiveRequest(false);
	}

	@Override
	@Transactional
	public void deleteIndivitualCabRequest(int requestId) {
		//getCurrentSession().get(EmpCabRequestDetails.class, requestId).setActiveRequest(false);
		
		Integer countInActiveReq = 0;
		EmpCabRequestDetails empCabRequestDetails = getCurrentSession().get(EmpCabRequestDetails.class,
				requestId);
		empCabRequestDetails.setActiveRequest(false);
		if(!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
				!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
				!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
				!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
				)
		empCabRequestDetails.setTravelStatus(TmsConstant.CANCELED_B_USER);
		EmpCabRequest model = empCabRequestDetails.getEmpCabRequest();
		for(EmpCabRequestDetails details : model.getEmpCabRequestDetails())
		{
			if(!details.getActiveRequest())
			{
				++countInActiveReq;
			}
		}
		if(countInActiveReq == model.getEmpCabRequestDetails().size())
		{
			model.setActiveRequest(false);
		}
		getCurrentSession().update(empCabRequestDetails);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public EmpCabRequest findByCabRequestId(int cabRequestId) {
		return getCurrentSession().get(EmpCabRequest.class, cabRequestId);
	}

	@Override
	public List<EmpCabRequestDetails> getEmpCabRequest(CabReportBean cabReport) {

		Criteria criteria = getCurrentSession().createCriteria(EmpCabRequestDetails.class)
				.createAlias("empCabRequest.user", "user").createAlias("empCabRequest", "empCab");

		Conjunction criDisjunction = Restrictions.conjunction();
		Disjunction objDisjunction = Restrictions.disjunction();
		//criDisjunction.add(Restrictions.eq("activeRequest", true));

		if (cabReport.getEmpCode() != null) {
			criDisjunction.add(Restrictions.eq("user.empcode", cabReport.getEmpCode()));
		}
		if (cabReport.getRequestType() != null) {
			criDisjunction.add(Restrictions.eq("pickOrDrop", cabReport.getRequestType()));
		}
		if (cabReport.getFromDate() != null && cabReport.getToDate() != null) {
			criDisjunction.add(Restrictions.between("scheduleDate", cabReport.getFromDate(), cabReport.getToDate()));
		} else if (cabReport.getFromDate() != null) {
			criDisjunction.add(Restrictions.ge("scheduleDate", cabReport.getFromDate()));
		} else if (cabReport.getToDate() != null) {
			criDisjunction.add(Restrictions.le("scheduleDate", cabReport.getToDate()));
		}
		if (cabReport.getProjectId() != null) {
			criDisjunction.add(Restrictions.eq("projectId", cabReport.getProjectId()));
		}
		if (cabReport.getAccountId() != null) {

			List<ProjectDetailsBean> projectList = projectDetailsService
					.findProjectsByAccountId(cabReport.getAccountId());
			for (ProjectDetailsBean project : projectList) {
				objDisjunction.add(Restrictions.eq("projectId", project.getProjectId()));
			}

		}
		if (cabReport.getRequestNature() != null) {
			criDisjunction.add(Restrictions.eq("empCab.adhocOrMonthly", cabReport.getRequestNature()));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(criDisjunction);
		criteria.add(objDisjunction);
		criteria.addOrder(Order.asc("scheduleDate"));
		criteria.addOrder(Order.asc("requestime"));

		return criteria.list();

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public EmpCabRequestDetails findByRequestId(int requestId) {
		return getCurrentSession().get(EmpCabRequestDetails.class, requestId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.quinnox.flm.tms.module.dao.CabRequestDao#getAdhocRequestCount(java.lang.
	 * String)
	 */
	@Override
	public Integer[][] getAdhocRequestCount(String empId) {

		Criteria criteria = getCurrentSession().createCriteria(EmpCabRequestDetails.class)
				.createAlias("empCabRequest.user", "user").createAlias("empCabRequest", "empCab")
				.add(Restrictions.ge("scheduleDate", DateUtil.getTime()));

		criteria.add(Restrictions.eq("user.empcode", empId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<EmpCabRequestDetails> cabReqList = criteria.list();
		List<EmpCabRequestDetails> cabRequestDetails = DateUtil.checkValidUpcomingRequestCount(cabReqList);
		
		Integer[] cabAdhocStatusCount = new Integer[] {0,0,0,0,0};
		int usedMonthlyCab = 0;
		int unUsedMonthlyCab = 0;
		int noShow = 0;
		int adhocPending = 0;
		int adhocApproved = 0;
		int adhocRejected = 0;
		int adhocCancelled = 0;
		int adhocNoShow = 0;
		
		Integer[] cabMonthlyStatusCount = new Integer[]{0,0,0};
		Integer[][]  adhocMonthlyStatus = {cabAdhocStatusCount,cabMonthlyStatusCount};
		
		if (cabRequestDetails.size() > 0) {

			for (EmpCabRequestDetails details : cabRequestDetails) {
				if (TmsConstant.ADHOC.equalsIgnoreCase(details.getEmpCabRequest().getAdhocOrMonthly())) {

					if (((details.getTravelStatus().contains(TmsConstant.PENDING_W_FLM)
							&& details.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_PROCESSING))
							|| details.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_PROCESSING))
							&& details.getActiveRequest()) {
						adhocApproved++;
					} else if ((details.getTravelStatus().contains(TmsConstant.PENDING_W_MANAGER) 
							|| details.getTravelStatus().contains(TmsConstant.PENDING_W_FLM) )
							&& details.getActiveRequest()) {
						adhocPending++;
					}
					else if ((details.getTravelStatus().contains(TmsConstant.REJECTED_B_MANAGER) 
							|| details.getTravelStatus().contains(TmsConstant.REJECTED_B_FLM) )
							&& !details.getActiveRequest()) {
						adhocRejected++;
					}
					else if ((details.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) 
							|| details.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm)
							|| details.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor))
							&& details.getActiveRequest()) {
						adhocNoShow++;
					}
					else if (details.getTravelStatus().contains(TmsConstant.CANCELED_B_USER) 
							&& !details.getActiveRequest()) {
						adhocCancelled++;
					}
					
				} 
			}
			cabAdhocStatusCount[0] = adhocApproved;
			cabAdhocStatusCount[1] = adhocPending;
			cabAdhocStatusCount[2] = adhocRejected;
			cabAdhocStatusCount[3] = adhocCancelled;
			cabAdhocStatusCount[4] = adhocNoShow;
			
			adhocMonthlyStatus[0] = cabAdhocStatusCount;
		}
		
		//--------------------------------------------------------------------------------------------------------------
		
		Date date1 = new Date();
		Date date2 = new Date();
		Calendar calendar = Calendar.getInstance();
		date1.setDate(calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		date2.setDate(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		date1.setHours(0);
		date1.setMinutes(01);
		date1.setSeconds(0);
		date2.setHours(23);
		date2.setMinutes(59);
		date2.setSeconds(0);

		Criteria monthlyCriteria = getCurrentSession().createCriteria(EmpCabRequestDetails.class)
				.createAlias("empCabRequest.user", "user").createAlias("empCabRequest", "empCab")
				.add(Restrictions.eq("empCab.adhocOrMonthly", "Monthly"))
				.add(Restrictions.ge("scheduleDate", date1))
				.add(Restrictions.lt("scheduleDate", date2));
		
		monthlyCriteria.add(Restrictions.eq("user.empcode", empId));
		monthlyCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<EmpCabRequestDetails> monthlyCabReqList = monthlyCriteria.list();
		List<EmpCabRequestDetails> requestDetails = DateUtil.checkValidUpcomingRequestCount(monthlyCabReqList);
		if (requestDetails.size() > 0) {

			for (EmpCabRequestDetails details : monthlyCabReqList) {
				
				
					if (TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User.equalsIgnoreCase(details.getTravelStatus())
							|| TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm.equalsIgnoreCase(details.getTravelStatus())
									|| TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor.equalsIgnoreCase(details.getTravelStatus())) {
						noShow++;
					}
					else if((details.getScheduleDate().getDate() >= new Date().getDate())   &&
							details.getActiveRequest() && !TmsConstant.TRAVEL_STATUS_COMPLETED.equalsIgnoreCase
							(details.getTravelStatus())) {
						unUsedMonthlyCab++;
					}
					else if((details.getScheduleDate().getDate() < new Date().getDate()) && 
							details.getActiveRequest() && TmsConstant.TRAVEL_STATUS_COMPLETED.equalsIgnoreCase
							(details.getTravelStatus())){
						usedMonthlyCab++;
					} else if(details.getScheduleDate().getDate()== new Date().getDate()
							&& details.getActiveRequest() && TmsConstant.TRAVEL_STATUS_COMPLETED.equalsIgnoreCase
							(details.getTravelStatus())) {
						usedMonthlyCab++;
					}
					
			}
				cabMonthlyStatusCount[0] = usedMonthlyCab;
				cabMonthlyStatusCount[1] = unUsedMonthlyCab;
				cabMonthlyStatusCount[2] = noShow;
				adhocMonthlyStatus[1] = cabMonthlyStatusCount;
		}
    		return adhocMonthlyStatus;
	}

	@Override
	@Transactional
	public void updateCabRequestByManager(CabRequestRemarkBean details) {

		//empCabObj.setManagerRemark(details.getComment());
		
		EmpCabRequest empCabObj = getCurrentSession().get(EmpCabRequest.class, details.getCabRequestId());
		
		
		if(empCabObj.getActiveRequest()) {
		if(details.getAction().contains(TmsConstant.APPROVED)) {
			empCabObj.setManagerRemark(TmsConstant.APPROVED);
		}
		else {
			empCabObj.setManagerRemark(details.getComment());
			empCabObj.setActiveRequest(false);
		}
		List<EmpCabRequestDetails> cabList = new ArrayList<EmpCabRequestDetails>();
		cabList.addAll(empCabObj.getEmpCabRequestDetails());
		List<EmpCabRequestDetails> validCabList = DateUtil.checkValidUpcomingRequestCount(cabList);
		for (EmpCabRequestDetails empCabRequestDetails : validCabList) {
			if(!(empCabRequestDetails.getScheduleDate().getDate() < new Date().getDate()
					&& empCabRequestDetails.getScheduleDate().getMonth() == new Date().getMonth()
					&& empCabRequestDetails.getScheduleDate().getYear() == new Date().getYear())) {
			if(empCabRequestDetails.getActiveRequest()) {
			if(details.getAction().contains(TmsConstant.APPROVED)) {
				//empCabObj.setManagerRemark(TmsConstant.APPROVED);
				if(!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
						!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
						!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
						!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
						)

				empCabRequestDetails.setTravelStatus(TmsConstant.PENDING_W_FLM);
			}
			else {
				//empCabObj.setManagerRemark(TmsConstant.REJECTED);
				if(!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
						!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
						!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
						!empCabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
						)
				empCabRequestDetails.setTravelStatus(TmsConstant.REJECTED_B_MANAGER);
				empCabRequestDetails.setActiveRequest(false);
			}
			empCabRequestDetails.setReqStatus(details.getAction());
			}
			}
		}
		}
		getCurrentSession().update(empCabObj);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmpCabRequestDetails> findAllCabRequestDetails(int id) {
		return (List<EmpCabRequestDetails>) getCurrentSession()
				.createQuery("from EmpCabRequestDetails cab_details where cab_details.empCabRequest.id=:id")
				.setInteger("id", id).list();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<EmpCabRequestDetails> findAllOtherOpenRequest(int requestedById) {

		List<EmpCabRequestDetails> empCabRequestDetails = (List<EmpCabRequestDetails>) getCurrentSession().createQuery(
				"from EmpCabRequestDetails emp_cab_details where emp_cab_details.empCabRequest.activeRequest is true and  emp_cab_details.empCabRequest.requestingEmpId=:requestingEmpId and  emp_cab_details.scheduleDate >=:currentDate")
				.setInteger("requestingEmpId", requestedById).setDate("currentDate", new Date()).list();
		return empCabRequestDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmpCabRequestDetails> findAllOpenRequest() {
		List<EmpCabRequestDetails> empCabRequestDetails = (List<EmpCabRequestDetails>) getCurrentSession().createQuery(
				"from EmpCabRequestDetails emp_cab_details where emp_cab_details.empCabRequest.activeRequest is true and  emp_cab_details.scheduleDate >=:currentDate and  emp_cab_details.reqStatus != 'Rejected'")
				.setDate("currentDate", new Date()).list();
		return empCabRequestDetails;
	}

	@Override
	@Transactional
	public void updateCabReqTripSheetDetails(TripSheetDetailsBean cabDetails, int tripId) { // List<EmpCabRequestDetails>

		for (AdhocCabRequestBean details : cabDetails.getEmpCabRequestDetails()) {
			if (details != null) {
				EmpCabRequestDetails empCabRequestObj = (EmpCabRequestDetails) getCurrentSession()
						.createQuery(
								"select details from EmpCabRequestDetails details where details.requestId=:requestId")
						.setInteger("requestId", details.getCabDetailsId()).uniqueResult();
				empCabRequestObj.setTripSheetId(tripId);
				getCurrentSession().update(empCabRequestObj);
			}
		}
	}

	@Override
	@Transactional
	public void updateCabDetails(EmpCabRequestDetails details) {
		getCurrentSession().update(details);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmpCabRequestDetails> findCabRequestByTripId(int tripId) {
		return (List<EmpCabRequestDetails>) getCurrentSession()
				.createQuery("from EmpCabRequestDetails cab_details where cab_details.tripSheetId=:tripSheetId")
				.setInteger("tripSheetId", tripId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmpCabRequestDetails> findDailyCabRequest() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();
		tomorrow.setHours(11);
		tomorrow.setMinutes(0);
		tomorrow.setSeconds(0);
		Date currentDate = new Date();
		currentDate.setHours(5);
		currentDate.setMinutes(0);
		currentDate.setSeconds(0);

		List<EmpCabRequestDetails> lists = (List<EmpCabRequestDetails>) getCurrentSession()
				.createQuery("from EmpCabRequestDetails cab_details"
						+ " where cab_details.activeRequest is true AND cab_details.scheduleDate BETWEEN :currentDate AND :tomorrow AND cab_details.reqStatus != 'Rejected' ")
				.setParameter("tomorrow", tomorrow).setParameter("currentDate", currentDate).list();
		System.out.println("No of daily reports : " + lists.size());
		return lists;

	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public void updateAddressByUserandDate(EmployeeAddressBean addrBean, String travelStatus) {
		List<EmpCabRequestDetails> detailsList = getCurrentSession()
				.createQuery("from EmpCabRequestDetails request " + "where request.empCabRequest.user.id=:id and"
						+ " request.activeRequest is true"
						+ " and (request.scheduleDate >=:date )"
						+ " and (request.fromAliasName =:alias or request.toAliasName =:alias)")
				.setInteger("id", addrBean.getEmployeeId()).setDate("date", addrBean.getEffectiveDate())
				.setString("alias", addrBean.getAlias()).list();
		if(addrBean.getStatus()) {
			for (EmpCabRequestDetails model : detailsList) {
				if (model.getFromAliasName().contains(addrBean.getAlias())) {
					model.setFromAddress(addrBean.getAddress());
					model.setFromCity(addrBean.getCity());
					model.setFromLandmark(addrBean.getLandmark());
					model.setFromLocation(addrBean.getLocation());
					model.setFromPincode(addrBean.getPincode());
				} else {
					model.setToAddress(addrBean.getAddress());
					model.setToCity(addrBean.getCity());
					model.setToLandmark(addrBean.getLandmark());
					model.setToLocation(addrBean.getLocation());
					model.setToPincode(addrBean.getPincode());
				}
				if(model.getReqStatus().equalsIgnoreCase(TmsConstant.PENDING_W_MANAGER) && 
						model.getAction().equalsIgnoreCase(TmsConstant.PENDING)) {
					if(!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
							)
					model.setTravelStatus(TmsConstant.PENDING_W_MANAGER);
				}else if(model.getReqStatus().equalsIgnoreCase(TmsConstant.APPROVED) 
						&& model.getAction().equalsIgnoreCase(TmsConstant.PENDING))// change it to approved by mgr
				{
					if(!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
							)

					model.setTravelStatus(TmsConstant.PENDING_W_FLM);
				}
				else if(model.getReqStatus().equalsIgnoreCase(TmsConstant.PENDING_W_MANAGER) && 
						model.getAction().equalsIgnoreCase(TmsConstant.APPROVED)) {
					if(!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
							)
					model.setTravelStatus(TmsConstant.APPROVED_BY_FLM);
				
				}
				
				else {
					if(!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
							!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
							)

					model.setTravelStatus(TmsConstant.PENDING_W_FLM);  // change it to approved by flm 
					model.setAction(TmsConstant.PENDING);
				}
				getCurrentSession().update(model);
			}
		} else {
			for (EmpCabRequestDetails model : detailsList) {
				if( !model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
						!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
								!model.getTravelStatus() .contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User)  &&
										!model.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
						)
				{
					model.setTravelStatus(TmsConstant.ON_HOLD+" due to "+TmsConstant.HOLD_REASON);
				}
			
			}
		}
		

	}

	@Override
	@Transactional
	public void removeTripId(Integer detailsId, Integer tripId) {
		getCurrentSession().get(EmpCabRequestDetails.class, detailsId).setTripSheetId(null);
		
		
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public Integer getCabRequestCountByDate(String date) {
		
		List<EmpCabRequestDetails> cabList = getCurrentSession().createQuery("FROM EmpCabRequestDetails\r\n" + 
				"WHERE DATE(schedule_date)=:schDate and tripSheetId!=null").setString("schDate",date).list();
		return cabList.size();
	}


	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<EmpCabRequestDetails> findAllRequestsByMonth(int month,int year) {
		List<EmpCabRequestDetails> cabList =getCurrentSession().createQuery("from EmpCabRequestDetails "
				+ "where Month(schedule_date)=:month and Year(schedule_date)=:year")
				.setInteger("month", month)
				.setInteger("year", year)
				.list();
				
		return cabList;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<EmpCabRequestDetails> findAllMonthlyRequestByEmpId(Integer empId) {
		
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		List<EmpCabRequestDetails> empCabRequestDetails = (List<EmpCabRequestDetails>) getCurrentSession().createQuery(
				" from EmpCabRequestDetails emp_cab_details where emp_cab_details.empCabRequest.activeRequest is true and emp_cab_details.empCabRequest.adhocOrMonthly = 'Monthly' and  emp_cab_details.empCabRequest.user.id=:id and"
				+ "  Month(emp_cab_details.scheduleDate) =:currentMonth "
				+ "and Year(emp_cab_details.scheduleDate) =:currentYear")
				.setInteger("id", empId)
				.setInteger("currentMonth", new Date().getMonth()+1)
				.setInteger("currentYear", year)
				.list();

		return empCabRequestDetails;
	}

	@Override
	@Transactional
	public void updateCabRequest(EmpCabRequest request) {
		getCurrentSession().update(request);
		
	}
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<EmpCabRequestDetails> findAllInActiveRequestByEmpId(Integer employeeId) {
		List<EmpCabRequestDetails> empCabRequestDetails = (List<EmpCabRequestDetails>) getCurrentSession().createQuery(
				"from EmpCabRequestDetails emp_cab_details where emp_cab_details.activeRequest is false and  emp_cab_details.empCabRequest.user.id=:id and  emp_cab_details.scheduleDate >=:currentDate")
				.setInteger("id", employeeId).setDate("currentDate", new Date()).list();
		return empCabRequestDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmpCabRequestDetails> findTodaysCabRequest() {
		Date startDate = new Date();
		startDate.setHours(5);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		Date endDate = new Date();
		endDate.setHours(11);
		endDate.setMinutes(0);
		endDate.setSeconds(0);

		List<EmpCabRequestDetails> lists = (List<EmpCabRequestDetails>) getCurrentSession()
				.createQuery("from EmpCabRequestDetails cab_details"
						+ " where cab_details.activeRequest is true AND cab_details.scheduleDate BETWEEN :startDate AND :endDate AND cab_details.reqStatus != 'Rejected' ")
				.setParameter("endDate", endDate).setParameter("startDate", startDate).list();
		System.out.println("No of daily reports : " + lists.size());
		return lists;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmpCabRequestDetails> findTomorrowsCabRequest() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date startDate = calendar.getTime();
		startDate.setHours(5);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		Date endDate = calendar.getTime();
		endDate.setHours(10);
		endDate.setMinutes(0);
		endDate.setSeconds(0);

		List<EmpCabRequestDetails> lists = (List<EmpCabRequestDetails>) getCurrentSession()
				.createQuery("from EmpCabRequestDetails cab_details"
						+ " where cab_details.activeRequest is true AND cab_details.scheduleDate BETWEEN :startDate AND :endDate AND cab_details.reqStatus != 'Rejected' ")
				.setParameter("endDate", endDate).setParameter("startDate", startDate).list();
		System.out.println("No of daily reports : " + lists.size());
		return lists;
	}

	// @Override
	// @Transactional
	// public EmpCabRequest findById(Integer id) {
	// return (EmpCabRequest) getCurrentSession().createQuery
	// (" SELECT x.dropLocation, y.remark FROM EmpCabRequestDetails x ,
	// EmpCabRequest y "
	// + "where x.cab_req_id IN (select y.cab_req_id from tms_dev.emp_cab_request
	// where y.cab_req_id =:id)")
	// .setInteger("id", id)
	// .uniqueResult();
	// }

	// @SuppressWarnings("unchecked")
	// @Override
	// @Transactional
	// public List<EmpCabRequestDetails> findRequestByType(String requestType,
	// String fromDate, String toDate){
	// // return (List<EmpCabRequestDetails>) getCurrentSession().createQuery("from
	// EmpCabRequestDetails where requestType =" + requestType)
	// // .list();
	//
	// return (List<EmpCabRequestDetails>) getCurrentSession().createSQLQuery(
	// "Select * from cab_req_details where request_type = 'monthly' and
	// schedule_date between '2017-06-15 00:00:00' and '2017-10-15
	// 00:00:00'").list();
	//
	//// return (List<EmpCabRequestDetails>)
	// getCurrentSession().createSQLQuery("Select * from cab_req_details where
	// request_type = " + requestType +
	//// "and schedule_date between " + fromDate + " and " + toDate).list();
	//
	//
	// }

	// @Override
	// @Transactional
	// public EmpCabRequest findById(int id) {
	// return getCurrentSession().get(EmpCabRequest.class, id);
	// }

	// @SuppressWarnings("unchecked")
	// @Override
	// @Transactional
	// public List<EmpCabRequest> findAllOpenRequest() {
	//
	// return (List<EmpCabRequest>) getCurrentSession().
	// createSQLQuery("select * from tms_dev.emp_cab_request emp where
	// emp.cab_req_id IN (select empDetails.cab_req_id from tms_dev.cab_req_details
	// empDetails where empDetails.req_status = 'Pending With Manager')").list();
	// }

}
