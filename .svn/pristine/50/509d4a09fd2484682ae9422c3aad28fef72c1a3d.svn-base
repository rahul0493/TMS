/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.Map.Entry;




import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.shiftplanner.bean.CabRequestBean;
import com.quinnox.flm.tms.module.shiftplanner.controller.ShiftPlannerController;
import com.quinnox.flm.tms.module.shiftplanner.dao.MonthlyCabRequestDao;
import com.quinnox.flm.tms.module.shiftplanner.dao.ShiftPlannerDao;
import com.quinnox.flm.tms.module.shiftplanner.service.MonthlyCabRequestService;

/**
 * @author RahulY
 *
 */
@Service
public class MonthlyCabRequestServiceImpl extends GenericServiceImpl<EmpCabRequest,Integer> implements MonthlyCabRequestService {

	private MonthlyCabRequestDao MonthlycabRequestDao;
	
	private static final Logger LOG = Logger
			.getLogger(MonthlyCabRequestServiceImpl.class);
	
	public MonthlyCabRequestServiceImpl(@Qualifier("MonthlycabRequestDao") MonthlyCabRequestDao MonthlycabRequestDao) {
		super(MonthlycabRequestDao);
		this.MonthlycabRequestDao = MonthlycabRequestDao;
	}

	@Override
	public int saveMonthlyCabRequest(EmpCabRequest empCabRequest) {
		// TODO Auto-generated method stub
		return MonthlycabRequestDao.saveCabRequest(empCabRequest);
	}

	@Override
	public List<Employee> isEmployeeRequiredCab(int id) {
		return MonthlycabRequestDao.isEmployeeRequiredCab(id);
		
		
	}

	@Override
	public List<ShiftDetails> isShiftRequired(int shiftId,int trackId) {
		// TODO Auto-generated method stub
		return MonthlycabRequestDao.isShiftRequired(shiftId,trackId);
	}

	@Override
	public void deletePreviousCabRequest(int day, int month, int year, int empId) {
		
		/*Calendar c = Calendar.getInstance();
		c.set(year, month, day, 0, 0,0);*/
		List<Integer> CabReqID =MonthlycabRequestDao.deletePreviousCabRequest(month,year,empId);
		System.out.println("CabReqID.size()"+CabReqID.size());
		for (Integer integer : CabReqID) {
		 String S =  MonthlycabRequestDao.DeleteFutureCabrequest(integer);
		System.out.println("after delete"+S);
	}
		
	  }

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void cabRequest(String createJson, Integer employeeId,
			int projectId, int trackId) {
		// Cab Request
		List<CabRequestBean> cabRequestBeanList = new ArrayList<CabRequestBean>();
		Map<Integer, List<CabRequestBean>> map = new HashMap<Integer, List<CabRequestBean>>();
		JSONArray jsonArray1 = new JSONArray(createJson);
		CabRequestBean cabRequestBean = null;

		for (int i = 0; i < jsonArray1.length(); i++) {
			org.json.JSONObject jsonObject = jsonArray1.getJSONObject(i);
			LOG.debug(jsonObject.toString());
            if(jsonObject.getInt("track_id") == trackId){
			cabRequestBean = new CabRequestBean();
			cabRequestBean.setDay(jsonObject.getInt("date"));
			cabRequestBean.setEmployeeIntials(jsonObject.getString("emp_init"));
			cabRequestBean.setShiftId(jsonObject.getInt("shift_id"));
			cabRequestBean.setTrackId(jsonObject.getInt("track_id"));
			cabRequestBean.setEmployeeId(jsonObject.getInt("emp_id"));
			cabRequestBean.setMonthId(jsonObject.getInt("month_id"));
			
			cabRequestBean.setYear(jsonObject.getInt("year"));
            }
			Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
			int day = calendar.get(Calendar.DATE);
			if (cabRequestBean.getDay() > day) {
				LOG.debug("future day");
			if (map.containsKey(cabRequestBean.getEmployeeId())) {

				cabRequestBeanList = map.get(cabRequestBean.getEmployeeId());

				cabRequestBeanList.add(cabRequestBean);
			} else {
				List<CabRequestBean> addValues = new ArrayList<CabRequestBean>();
				addValues.add(cabRequestBean);
				map.put(cabRequestBean.getEmployeeId(), addValues);

			}
			}
		}

		for (Entry<Integer, List<CabRequestBean>> entrySet : map.entrySet()) {
			LOG.debug("Key" + entrySet.getKey() + "Value:"
					+ entrySet.getValue());
            
			EmpCabRequest empCabRequest = new EmpCabRequest();

			empCabRequest.setActiveRequest(true);
			empCabRequest.setAdhocOrMonthly("Monthly");

			empCabRequest.setRequestFor("Others");
			empCabRequest.setManagerRemark("Approved");
			empCabRequest.setReason("Shift Needs cab");
			empCabRequest.setRequestType("both");
			empCabRequest.setTravelType("Share");
			Calendar calander = Calendar.getInstance();

			empCabRequest.setReqDate(Calendar.getInstance().getTime());

			calander.set(cabRequestBean.getYear(),
					cabRequestBean.getMonthId() + 1,
					calander.getActualMaximum(cabRequestBean.getMonthId() + 1),
					0, 0, 0);
			LOG.debug("Date" + calander.getTime() + "month"
					+ cabRequestBean.getYear());
			empCabRequest.setToDate(calander.getTime());

			calander.set(cabRequestBean.getYear(),
					cabRequestBean.getMonthId() + 1,
					calander.getActualMinimum(cabRequestBean.getMonthId()), 0,
					0, 0);
			LOG.debug("Date" + calander.getTime());
			empCabRequest.setFromDate(calander.getTime());

			List<Employee> empList = isEmployeeRequiredCab(entrySet
					.getKey());
			Employee employee = new Employee();
			employee.setId(entrySet.getKey());
			for (Employee emp : empList) {
				LOG.debug(emp.getId() + "isrequired" + emp.getIsCabRequired());

				empCabRequest.setRequestingEmpId(employeeId);
				empCabRequest.setMobileNumber(emp.getPhoneNumber());

				Employee empId = new Employee();
				empId.setId(employee.getId());
				empCabRequest.setUser(empId);

				if (emp.getIsCabRequired() == true && emp.getLocation().getLocationName().equalsIgnoreCase("bangalore")) {
					Set<EmpCabRequestDetails> cabRequestDetails = new HashSet<EmpCabRequestDetails>();
					for (CabRequestBean cabBean : entrySet.getValue()) {
						
						Shift shift = new Shift();
						shift.setShiftId(cabBean.getShiftId());
						List<ShiftDetails> shiftDetailslist = isShiftRequired(cabBean.getShiftId(),cabBean.getTrackId());
						for (ShiftDetails shiftdetail : shiftDetailslist) {
							LOG.debug("shiftdetails" + shiftdetail.getId()
									+ "shiftdetailpickup"
									+ shiftdetail.getPickUpEligible()
									+ "shiftDrop"
									+ shiftdetail.getDropEligible());
							if (shiftdetail.getPickUpEligible() == true
									&& shiftdetail.getDropEligible() == true) {
								EmpCabRequestDetails empCabRequestDetails = new EmpCabRequestDetails();
								empCabRequestDetails.setActiveRequest(true);
								empCabRequestDetails
										.setEmpCabRequest(empCabRequest);
										empCabRequestDetails
												.setReqStatus("Approved");
										empCabRequestDetails
										.setAction("Pending with FLM");
										empCabRequestDetails
										.setTravelStatus("Pending with FLM");
										empCabRequestDetails
												.setPickOrDrop("pickup");
										empCabRequestDetails
												.setRequestime(shiftdetail
														.getStartTime());
										empCabRequestDetails = setHomeAddress(emp
												.getEmployeeAddresses(),empCabRequestDetails);
										Calendar c = Calendar.getInstance();
										c.set(cabBean.getYear(),
												cabBean.getMonthId()-1,
												cabBean.getDay(),5, 30, 00);
										empCabRequestDetails.setScheduleDate(c
												.getTime());
										
										empCabRequestDetails
												.setMobileNumber(emp
														.getPhoneNumber());
										empCabRequestDetails.setProjectId(projectId);
										empCabRequestDetails = setOfficeAddressAsDestination(emp
												.getEmployeeAddresses(),empCabRequestDetails);
										
										cabRequestDetails
												.add(empCabRequestDetails);
										// for Drop
										EmpCabRequestDetails empcabDetails = new EmpCabRequestDetails();
										empcabDetails.setReqStatus("Approved");
										empcabDetails.setActiveRequest(true);
										empcabDetails
										.setAction("Pending with FLM");
										empcabDetails
										.setTravelStatus("Pending with FLM");
										empcabDetails
												.setEmpCabRequest(empCabRequest);
										empcabDetails.setPickOrDrop("drop");
										empcabDetails.setProjectId(projectId);

										empcabDetails
												.setRequestime((shiftdetail
														.getEndTime()));
										empcabDetails.setMobileNumber(emp
												.getPhoneNumber());
										c.set(cabBean.getYear(),
												cabBean.getMonthId()-1,
												cabBean.getDay(),5, 30, 00);
										empcabDetails.setScheduleDate(c
												.getTime());
										empcabDetails =setOfficeAddress(emp
												.getEmployeeAddresses(), empcabDetails);
										empcabDetails=setHomeAddressAsDestination(emp
												.getEmployeeAddresses(), empcabDetails);
										cabRequestDetails.add(empcabDetails);

								
								empCabRequest
										.setEmpCabRequestDetails(cabRequestDetails);

							} else if (shiftdetail.getPickUpEligible() == true
									&& shiftdetail.getDropEligible() == false) {

								LOG.debug("Pickup");
								EmpCabRequestDetails empCabRequestDetails = new EmpCabRequestDetails();
								empCabRequestDetails.setActiveRequest(true);
								empCabRequestDetails
										.setEmpCabRequest(empCabRequest);
										empCabRequestDetails
												.setReqStatus("Approved");
										empCabRequestDetails
												.setPickOrDrop("pickup");
															empCabRequestDetails
												.setRequestime(shiftdetail
														.getStartTime());
										empCabRequestDetails
										.setAction("Pending with FLM");
										empCabRequestDetails
										.setTravelStatus("Pending with FLM");
										
										Calendar c = Calendar.getInstance();
										c.set(cabBean.getYear(),
												cabBean.getMonthId()-1,
												cabBean.getDay(),5, 30, 00);
										empCabRequestDetails.setScheduleDate(c
												.getTime());
										empCabRequestDetails = setHomeAddress(emp
												.getEmployeeAddresses(), empCabRequestDetails);
										empCabRequestDetails
												.setMobileNumber(emp
														.getPhoneNumber());
										empCabRequestDetails
												.setProjectId(projectId);
										empCabRequestDetails = setOfficeAddressAsDestination(emp
												.getEmployeeAddresses(), empCabRequestDetails);
										cabRequestDetails
												.add(empCabRequestDetails);
								cabRequestDetails.add(empCabRequestDetails);
								empCabRequest
										.setEmpCabRequestDetails(cabRequestDetails);

							} else if (shiftdetail.getPickUpEligible() == false
									&& shiftdetail.getDropEligible() == true)

							{
								LOG.debug("Drop");

								EmpCabRequestDetails empcabDetails = new EmpCabRequestDetails();
								empcabDetails.setActiveRequest(true);
								empcabDetails.setEmpCabRequest(empCabRequest);

							
										System.out
												.println("Drop+default address");

										empcabDetails.setReqStatus("Approved");
										empcabDetails.setActiveRequest(true);
										empcabDetails
										.setAction("Pending with FLM");
										empcabDetails
										.setTravelStatus("Pending with FLM");
										empcabDetails
												.setEmpCabRequest(empCabRequest);
										empcabDetails.setPickOrDrop("drop");
										empcabDetails.setProjectId(projectId);

										empcabDetails
												.setRequestime((shiftdetail
														.getEndTime()));
										empcabDetails.setMobileNumber(emp
												.getPhoneNumber());
										Calendar c = Calendar.getInstance();
										c.set(cabBean.getYear(),
												cabBean.getMonthId()-1,
												cabBean.getDay(),5, 30, 00);
										empcabDetails.setScheduleDate(c
												.getTime());
										empcabDetails = setOfficeAddress(emp
										.getEmployeeAddresses(), empcabDetails);
										empcabDetails = setHomeAddressAsDestination(emp
										.getEmployeeAddresses(), empcabDetails);
												
										cabRequestDetails.add(empcabDetails);
										empCabRequest
												.setEmpCabRequestDetails(cabRequestDetails);
								

							} else {
								System.out
										.println("Shift is not eligible for Cab");
							}

						}
					}

				}

				else {
					LOG.debug("emp.getId()" + emp.getId() + "no cab required");
				}
				int saveMonthlyCabRequest = saveMonthlyCabRequest(empCabRequest);
			LOG.debug(saveMonthlyCabRequest);

			}

		}
	
	}

	private EmpCabRequestDetails setOfficeAddress(
			Set<EmployeeAddress> setEmployeeAddress,
			EmpCabRequestDetails empCabRequestDetails) {
		for (EmployeeAddress employeeAddress : setEmployeeAddress) {
			
		
		if (employeeAddress.getAlias().equalsIgnoreCase("Client") && employeeAddress.getDefaultAddress() == true) {
			empCabRequestDetails
				.setFromAddress(employeeAddress
						.getAddress());
		empCabRequestDetails
				.setFromAliasName(employeeAddress
						.getAlias());
		
		empCabRequestDetails
				.setFromCity(employeeAddress
						.getCity());
		empCabRequestDetails
				.setFromLandmark(employeeAddress
						.getLandmark());
		empCabRequestDetails
				.setFromLocation(employeeAddress
						.getLocation());
		empCabRequestDetails
				.setFromPincode(employeeAddress
						.getPincode());
		}else{
			EmployeeAddress officeAddress = MonthlycabRequestDao.getOfficeAddress();
			empCabRequestDetails
			.setFromAddress(officeAddress
					.getAddress());
	empCabRequestDetails
			.setFromAliasName(officeAddress
					.getAlias());
	
	empCabRequestDetails
			.setFromCity(officeAddress
					.getCity());
	empCabRequestDetails
			.setFromLandmark(officeAddress
					.getLandmark());
	empCabRequestDetails
			.setFromLocation(officeAddress
					.getLocation());
	empCabRequestDetails
			.setFromPincode(officeAddress
					.getPincode());
		}
		}
		return empCabRequestDetails;
}

	private EmpCabRequestDetails setHomeAddress(Set<EmployeeAddress> setOfEmployeeAddress,EmpCabRequestDetails empCabRequestDetails) {
		//EmpCabRequestDetails empCabRequestDetails = new EmpCabRequestDetails();
		for (EmployeeAddress employeeAddress : setOfEmployeeAddress) {
		if (employeeAddress.getAlias().equalsIgnoreCase("Home") && employeeAddress.getDefaultAddress() == true) {
				empCabRequestDetails
					.setFromAddress(employeeAddress
							.getAddress());
			empCabRequestDetails
					.setFromAliasName(employeeAddress
							.getAlias());
			
			empCabRequestDetails
					.setFromCity(employeeAddress
							.getCity());
			empCabRequestDetails
					.setFromLandmark(employeeAddress
							.getLandmark());
			empCabRequestDetails
					.setFromLocation(employeeAddress
							.getLocation());
			empCabRequestDetails
					.setFromPincode(employeeAddress
							.getPincode());
			}}
		return empCabRequestDetails;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCabRequest(String createJson, int spocId, int projectId,
			int trackId) {
		// Cab Request should delete previous entry
				List<CabRequestBean> cabRequestBeanList = new ArrayList<CabRequestBean>();
				Map<Integer, List<CabRequestBean>> map = new HashMap<Integer, List<CabRequestBean>>();
				JSONArray jsonArray1 = new JSONArray(createJson);
				for (int i = 0; i < jsonArray1.length(); i++) {
					org.json.JSONObject jsonObject = jsonArray1.getJSONObject(i);
					LOG.debug(jsonObject.toString());
					CabRequestBean cabRequestBean = new CabRequestBean();
                    if(jsonObject.getInt("track_id")==trackId){
					cabRequestBean.setDay(jsonObject.getInt("date"));
					cabRequestBean.setEmployeeIntials(jsonObject.getString("emp_init"));
					cabRequestBean.setShiftId(jsonObject.getInt("shift_id"));
					cabRequestBean.setTrackId(jsonObject.getInt("track_id"));
					cabRequestBean.setEmployeeId(jsonObject.getInt("emp_id"));
					cabRequestBean.setMonthId(jsonObject.getInt("month_id"));
					cabRequestBean.setYear(jsonObject.getInt("year"));
                    }
					Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
					
					int day = calendar.get(Calendar.DATE);
					if (cabRequestBean.getDay() > day) {
						if (map.containsKey(cabRequestBean.getEmployeeId())) {

							cabRequestBeanList = map
									.get(cabRequestBean.getEmployeeId());

							cabRequestBeanList.add(cabRequestBean);
						} else {
							List<CabRequestBean> addValues = new ArrayList<CabRequestBean>();
							addValues.add(cabRequestBean);
							map.put(cabRequestBean.getEmployeeId(), addValues);

						}

					}
				}
				for (Entry<Integer, List<CabRequestBean>> entrySet : map.entrySet()) {
					LOG.debug("Key" + entrySet.getKey() + "Value:"
							+ entrySet.getValue());

					Employee employee = new Employee();
					employee.setId(entrySet.getKey());
					EmpCabRequest empCabRequest = new EmpCabRequest();

					empCabRequest.setActiveRequest(true);
					empCabRequest.setAdhocOrMonthly("Monthly");

					empCabRequest.setRequestFor("Others");
					empCabRequest.setManagerRemark("Approved");
					empCabRequest.setReason("Shift Needs cab");
					empCabRequest.setRequestingEmpId(spocId);

					empCabRequest.setRequestType("both");
					empCabRequest.setTravelType("Share");

					Calendar calander = Calendar.getInstance();

					empCabRequest.setReqDate(Calendar.getInstance().getTime());

					CabRequestBean bean = new CabRequestBean();
					calander.set(bean.getYear(), bean.getMonthId() + 1, 31, 0, 0, 0);
					LOG.debug("Date" + calander.getTime());
					empCabRequest.setToDate(calander.getTime());

					calander.set(bean.getYear(), bean.getMonthId() + 1, 1, 0, 0, 0);
					LOG.debug("Date" + calander.getTime());
					empCabRequest.setFromDate(calander.getTime());

					List<Employee> empList = isEmployeeRequiredCab(entrySet
							.getKey());

					for (Employee emp : empList) {
						LOG.debug(emp.getId() + "isrequired" + emp.getIsCabRequired());
  
						Employee empId = new Employee();
						empCabRequest.setMobileNumber(emp.getPhoneNumber());
						empId.setId(employee.getId());
						empCabRequest.setUser(empId);
						if (emp.getIsCabRequired() == true && emp.getLocation().getLocationName().equalsIgnoreCase("bangalore")) {
							Set<EmpCabRequestDetails> cabRequestDetails = new HashSet<EmpCabRequestDetails>();
							for (CabRequestBean cabBean : entrySet.getValue()) {
								// Delete previous cab request
								deletePreviousCabRequest(cabBean.getDay(),
										cabBean.getMonthId(), cabBean.getYear(),
										cabBean.getEmployeeId());
								Shift shift = new Shift();
								shift.setShiftId(cabBean.getShiftId());
								List<ShiftDetails> shiftDetailslist = isShiftRequired(cabBean.getShiftId(),cabBean.getTrackId());
								for (ShiftDetails shiftdetail : shiftDetailslist) {
									LOG.debug("shiftdetails" + shiftdetail.getId()
											+ "shiftdetailpickup"
											+ shiftdetail.getPickUpEligible()
											+ "shiftDrop"
											+ shiftdetail.getDropEligible());
									if (shiftdetail.getPickUpEligible() == true
											&& shiftdetail.getDropEligible() == true) {
										EmpCabRequestDetails empCabRequestDetails = new EmpCabRequestDetails();
										empCabRequestDetails.setActiveRequest(true);
										empCabRequestDetails
												.setEmpCabRequest(empCabRequest);
												empCabRequestDetails
														.setReqStatus("Approved");
												empCabRequestDetails
												.setAction("Pending with FLM");
												empCabRequestDetails
												.setTravelStatus("Pending with FLM");
												
												empCabRequestDetails
														.setPickOrDrop("pickup");
												empCabRequestDetails
														.setRequestime(shiftdetail
																.getStartTime());
												empCabRequestDetails = setHomeAddress(emp
														.getEmployeeAddresses(),empCabRequestDetails);
												Calendar c = Calendar.getInstance();
												c.set(cabBean.getYear(),
														cabBean.getMonthId()-1,
														cabBean.getDay(), 5, 30, 00);
												empCabRequestDetails.setScheduleDate(c
														.getTime());
												
												empCabRequestDetails
														.setMobileNumber(emp
																.getPhoneNumber());
												empCabRequestDetails.setProjectId(projectId);
												empCabRequestDetails = setOfficeAddressAsDestination(emp
														.getEmployeeAddresses(),empCabRequestDetails);
												
												cabRequestDetails
														.add(empCabRequestDetails);
												// for Drop
												EmpCabRequestDetails empcabDetails = new EmpCabRequestDetails();
												empcabDetails.setReqStatus("Approved");
												empcabDetails.setActiveRequest(true);
												empcabDetails
												.setAction("Pending with FLM");
												empcabDetails
												.setTravelStatus("Pending with FLM");
												
												empcabDetails
														.setEmpCabRequest(empCabRequest);
												empcabDetails.setPickOrDrop("drop");
												empcabDetails.setProjectId(projectId);

												empcabDetails
														.setRequestime((shiftdetail
																.getEndTime()));
												empcabDetails.setMobileNumber(emp
														.getPhoneNumber());
												c.set(cabBean.getYear(),
														cabBean.getMonthId()-1,
														cabBean.getDay()+1,5, 30, 00);
												empcabDetails.setScheduleDate(c
														.getTime());
												empcabDetails =setOfficeAddress(emp
														.getEmployeeAddresses(), empcabDetails);
												empcabDetails=setHomeAddressAsDestination(emp
														.getEmployeeAddresses(), empcabDetails);
												cabRequestDetails.add(empcabDetails);

										
										empCabRequest
												.setEmpCabRequestDetails(cabRequestDetails);

									} else if (shiftdetail.getPickUpEligible() == true
											&& shiftdetail.getDropEligible() == false) {

										LOG.debug("Pickup");
										EmpCabRequestDetails empCabRequestDetails = new EmpCabRequestDetails();
										empCabRequestDetails.setActiveRequest(true);
										empCabRequestDetails
												.setEmpCabRequest(empCabRequest);
												empCabRequestDetails
														.setReqStatus("Approved");
												empCabRequestDetails
														.setPickOrDrop("pickup");
												empCabRequestDetails
												.setAction("Pending with FLM");
												empCabRequestDetails
												.setTravelStatus("Pending with FLM");
												
												empCabRequestDetails
														.setRequestime(shiftdetail
																.getStartTime());
												Calendar c = Calendar.getInstance();
												c.set(cabBean.getYear(),
														cabBean.getMonthId()-1,
														cabBean.getDay(),5, 30, 00);
												empCabRequestDetails.setScheduleDate(c
														.getTime());
												empCabRequestDetails = setHomeAddress(emp
														.getEmployeeAddresses(), empCabRequestDetails);
												empCabRequestDetails
														.setMobileNumber(emp
																.getPhoneNumber());
												empCabRequestDetails
														.setProjectId(projectId);
												empCabRequestDetails = setOfficeAddressAsDestination(emp
														.getEmployeeAddresses(), empCabRequestDetails);
												cabRequestDetails
														.add(empCabRequestDetails);
										cabRequestDetails.add(empCabRequestDetails);
										empCabRequest
												.setEmpCabRequestDetails(cabRequestDetails);

									} else if (shiftdetail.getPickUpEligible() == false
											&& shiftdetail.getDropEligible() == true)

									{
										LOG.debug("Drop");

										EmpCabRequestDetails empcabDetails = new EmpCabRequestDetails();
										empcabDetails.setActiveRequest(true);
										empcabDetails.setEmpCabRequest(empCabRequest);

									
												System.out
														.println("Drop+default address");

												empcabDetails.setReqStatus("Approved");
												empcabDetails.setActiveRequest(true);
												empcabDetails
												.setAction("Pending with FLM");
												empcabDetails
												.setTravelStatus("Pending with FLM");
												
												empcabDetails
														.setEmpCabRequest(empCabRequest);
												empcabDetails.setPickOrDrop("drop");
												empcabDetails.setProjectId(projectId);

												empcabDetails
														.setRequestime((shiftdetail
																.getEndTime()));
												empcabDetails.setMobileNumber(emp
														.getPhoneNumber());
												Calendar c = Calendar.getInstance();
												c.set(cabBean.getYear(),
														cabBean.getMonthId()-1,
														cabBean.getDay(),5, 30, 00);
												empcabDetails.setScheduleDate(c
														.getTime());
												empcabDetails = setOfficeAddress(emp
												.getEmployeeAddresses(), empcabDetails);
												empcabDetails = setHomeAddressAsDestination(emp
												.getEmployeeAddresses(), empcabDetails);
														
												cabRequestDetails.add(empcabDetails);
												empCabRequest
														.setEmpCabRequestDetails(cabRequestDetails);
										

									} else {
										System.out
												.println("Shift is not eligible for Cab");
									}

								}
							}

						}

						else {
							LOG.debug("employeeCode:" + emp.getEmpcode() + "no cab required or employee does not belongs to bangalore");
						}
						int saveMonthlyCabRequest = saveMonthlyCabRequest(empCabRequest);
					LOG.debug(saveMonthlyCabRequest);

					}

				}
		
	}

	private EmpCabRequestDetails setHomeAddressAsDestination(
			Set<EmployeeAddress> setOfEmployeeAddress,
			EmpCabRequestDetails empCabRequestDetails) {
		for (EmployeeAddress employeeAddress : setOfEmployeeAddress) {
			if (employeeAddress.getAlias().equalsIgnoreCase("Home") && employeeAddress.getDefaultAddress() == true) {
					empCabRequestDetails
						.setToAddress(employeeAddress
								.getAddress());
				empCabRequestDetails
						.setToAliasName(employeeAddress
								.getAlias());
				
				empCabRequestDetails
						.setToCity(employeeAddress
								.getCity());
				empCabRequestDetails
						.setToLandmark(employeeAddress
								.getLandmark());
				empCabRequestDetails
						.setToLocation(employeeAddress
								.getLocation());
				empCabRequestDetails
						.setToPincode(employeeAddress
								.getPincode());
				}}
			return empCabRequestDetails;
		}

	private EmpCabRequestDetails setOfficeAddressAsDestination(
			Set<EmployeeAddress> setEmployeeAddresses,
			EmpCabRequestDetails empCabRequestDetails) {
		for (EmployeeAddress employeeAddress : setEmployeeAddresses) {
			
			
			if (employeeAddress.getAlias().equalsIgnoreCase("Client") && employeeAddress.getDefaultAddress() == true) {
				empCabRequestDetails
					.setToAddress(employeeAddress
							.getAddress());
			empCabRequestDetails
					.setToAliasName(employeeAddress
							.getAlias());
			
			empCabRequestDetails
					.setToCity(employeeAddress
							.getCity());
			empCabRequestDetails
					.setToLandmark(employeeAddress
							.getLandmark());
			empCabRequestDetails
					.setToLocation(employeeAddress
							.getLocation());
			empCabRequestDetails
					.setToPincode(employeeAddress
							.getPincode());
			}else{
				EmployeeAddress officeAddress = MonthlycabRequestDao.getOfficeAddress();
				empCabRequestDetails
				.setToAddress(officeAddress
						.getAddress());
		empCabRequestDetails
				.setToAliasName(officeAddress
						.getAlias());
		
		empCabRequestDetails
				.setToCity(officeAddress
						.getCity());
		empCabRequestDetails
				.setToLandmark(officeAddress
						.getLandmark());
		empCabRequestDetails
				.setToLocation(officeAddress
						.getLocation());
		empCabRequestDetails
				.setToPincode(officeAddress
						.getPincode());
			}
			}
			return empCabRequestDetails;
	}
	

	
}
