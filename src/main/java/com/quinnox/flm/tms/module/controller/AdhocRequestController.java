package com.quinnox.flm.tms.module.controller;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.formula.functions.Today;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.mail.TmsMailSender;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.module.beans.AddressProjectBean;
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.CabReportBean;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.service.CabRequestService;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.shiftplanner.bean.ProjectBean;
import com.quinnox.flm.tms.module.util.DateUtil;
import com.quinnox.flm.tms.module.util.JsonConverter;

/**
 * @author AmareshP
 *
 */

@Controller
@RequestMapping("tms/adhocRequest")
public class AdhocRequestController {

	@Autowired
	private CabRequestService cabRequestService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ProjectDetailsService projectService;
	
	@Autowired
	private TmsMailSender tmsMailSend;



	@RequestMapping(value = "/adhoc", method = RequestMethod.GET)
	public ModelAndView home(ModelMap modal, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		EmpCabRequest empCabRequest = new EmpCabRequest();
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");

		List<EmployeeAddressBean> employeeAddressesLists = employeeService
				.findAddressByEmpIdAndStatus(profileBean.getEmployeeId());
		List<Employee> employeesList = employeeService.findAllEmployees();
		List<Employee> emplList = new ArrayList<Employee>();

		for (Employee emp : employeesList) {
			if (!emp.getEmail().equalsIgnoreCase(profileBean.getEmail())) {
				emplList.add(emp);
			}
		}
		
		Collections.sort(emplList);
		List<Project> projectList = projectService.findAllProjects();
		modelAndView.addObject("empCabRequest", empCabRequest);
		modelAndView.addObject("employeeAddressesLists", employeeAddressesLists);
		modelAndView.addObject("employeesList", emplList);
		modelAndView.addObject("projectList", projectList);
		modelAndView.setViewName("tms/userPage");
		return modelAndView;
	}

	@RequestMapping(value = "/getAddress", method = RequestMethod.POST)
	@ResponseBody
	public String getAddress(HttpServletRequest request) {
		String empId = request.getParameter("empId");
		Integer employeeId = Integer.parseInt(empId);
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
		List<EmployeeAddressBean> employeeAddress = null;
		List<ProjectDetailsBean> projectList = null;
		AddressProjectBean bean = new AddressProjectBean();
		if (employeeId != 0) {
			employeeAddress = employeeService.findAddressByEmpIdAndStatus(employeeId);
			projectList = projectService.getEmpPrjNameByEmpId(employeeId);			
		}
		else {
			employeeAddress = employeeService.findAddressByEmpIdAndStatus(profileBean.getEmployeeId());
			projectList = projectService.getEmpPrjNameByEmpId(profileBean.getEmployeeId());
		}
		bean.setEmployeeAddress(employeeAddress);
		bean.setProjectList(projectList);
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		String arrayToJson = null;
		try {
			
			arrayToJson = objectMapper.writeValueAsString(bean);
			
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("{'Address':" + arrayToJson +"}");
		System.out.println("executed successfully");
		return arrayToJson;
	}

	@RequestMapping(value = "/saveAdhocRequest", method = RequestMethod.POST)
	@ResponseBody
	public String saveadhocData(@RequestBody String createJson, HttpServletRequest request) {

		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
	
		AdhocCabRequestBean requestBeans = JsonConverter.jsonToCabrequest(createJson);
		Boolean validPickUpReq = false;
		Boolean validDropReq = false;
		Boolean tmrwValidDropReq = false;
		Boolean tmrwValidPickUpReq = false;
		String pickupAmPm = null , dropAmPm = null;
		Integer pickHour = null , dropHour = null;
		String retVal = null;
		Date dt=tmrwDate(new Date());
		if ((requestBeans.getFrom().getDate() == new Date().getDate() && requestBeans.getFrom().getMonth() == new Date().getMonth()
				&& requestBeans.getFrom().getYear() == new Date().getYear())) {

			if(requestBeans.getPickTime()!=null) {
				pickupAmPm = requestBeans.getPickTime().substring(6, 8);
				pickHour = Integer.parseInt(requestBeans.getPickTime().substring(0, 2));
				tmrwValidPickUpReq = true;
			}
			if(requestBeans.getDropTime()!=null) {
				dropAmPm = requestBeans.getDropTime().substring(6, 8);
				dropHour = Integer.parseInt(requestBeans.getDropTime().substring(0, 2));
				tmrwValidDropReq =  true;
			}
			
			Integer currentHour = 16 == 24 ? 0 :16;
			Integer currHr = currentHour - 12;
			String currentHourFormated = currentHour >= 12 ?  "0"+currHr.toString() : currentHour.toString();
			
			String currTimeAmPM = currentHour < 12 ? "AM" : "PM";
		
			if(currentHour <= 17) {
				if(pickupAmPm!=null && pickHour!=null) {
				validPickUpReq = 	DateUtil.todayValidReq(pickupAmPm,currTimeAmPM,pickHour,Integer.parseInt(currentHourFormated));
				}
				if(dropAmPm!=null && dropHour!=null) {
				validDropReq = 	DateUtil.todayValidReq(dropAmPm,currTimeAmPM,dropHour,Integer.parseInt(currentHourFormated));
				}
			}
			
		} 
		if ((requestBeans.getFrom().getDate() == dt.getDate() && requestBeans.getFrom().getMonth() == dt.getMonth()
				&& requestBeans.getFrom().getYear() == dt.getYear())){
			if(requestBeans.getPickTime()!=null) {
				pickupAmPm = requestBeans.getPickTime().substring(6, 8);
				pickHour = Integer.parseInt(requestBeans.getPickTime().substring(0, 2));
			}
			if(requestBeans.getDropTime()!=null) {
				dropAmPm = requestBeans.getDropTime().substring(6, 8);
				dropHour = Integer.parseInt(requestBeans.getDropTime().substring(0, 2));
			}
			Integer currentHour = new Date().getHours();
			Integer currHr = currentHour - 12;
			String currentHourFormated = currentHour > 12 ?  "0"+currHr.toString() : currentHour.toString();
			
			String currTimeAmPM = currentHour < 12 ? "AM" : "PM";
			if(pickupAmPm!=null && pickHour!=null) {
				tmrwValidPickUpReq = 	DateUtil.tmrsValidReq(pickupAmPm,currTimeAmPM,pickHour,Integer.parseInt(currentHourFormated),currentHour);
			}
			
			if(dropAmPm!=null && dropHour!=null) {
				tmrwValidDropReq = 	DateUtil.tmrsValidReq(dropAmPm,currTimeAmPM,dropHour,Integer.parseInt(currentHourFormated),currentHour);
			}
				
			
		}
		retVal= saveData(requestBeans, profileBean , validPickUpReq, validDropReq,tmrwValidPickUpReq,tmrwValidDropReq);
		
		return retVal;
	}

	@RequestMapping(value = "/deleteRequests", method = RequestMethod.POST)
	@ResponseBody
	private String deleteRequests(@RequestBody int cabRequestId) {
		cabRequestService.deleteEmpCabRequest(cabRequestId);
		System.out.println("Deleted Successfully");
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("data", "Request is cancelled successfully.");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject.toString();
	}

	@RequestMapping(value = "/deleteIndividualRequest", method = RequestMethod.POST)
	@ResponseBody
	private String deleteIndividualRequest(@RequestBody int requestId) {

		cabRequestService.deleteIndivitualCabRequest(requestId);
		System.out.println("Deleted Successfully");
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("data", "Request is cancelled successfully.");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject.toString();
	}

	private void beanToEmpCabModel(AdhocCabRequestBean requestBeans, EmpCabRequest empCabRequest) {
		empCabRequest.setReason(requestBeans.getReason());
		empCabRequest.setRequestType(requestBeans.getRequestType());
		empCabRequest.setTravelType(requestBeans.getTravelType());
		 empCabRequest.setPickupTime(requestBeans.getPickTime());
		 empCabRequest.setDropTime(requestBeans.getDropTime());
		empCabRequest.setMobileNumber(requestBeans.getMobileNumber());
		java.util.Date today = new java.util.Date();
		empCabRequest.setReqDate(new java.sql.Timestamp(today.getTime()));
		empCabRequest.setActiveRequest(true);
		empCabRequest.setAdhocOrMonthly("Adhoc");
		empCabRequest.setManagerRemark("Pending");
	}
	
	private String saveData(final AdhocCabRequestBean requestBeans,final UserProfileBean profileBean,final Boolean validPickUpReq,
			final Boolean validDropReq, final Boolean tmrwValidPickUpReq,final Boolean tmrwValidDropReq) {
		
		// This bool is to check valid weekend req
		Boolean checkValidDay = false;
		
		final JSONObject jsonObject = new JSONObject();
		String requestTime = null;
		final Set<EmpCabRequestDetails> setempcabreqdetails = new HashSet<EmpCabRequestDetails>();
		Boolean sat = requestBeans.getSaturday();
		Boolean sun = requestBeans.getSunday();
		String requestFor = requestBeans.getRequestedFor();
		Date fromDate = requestBeans.getFrom();
		Date toDate = requestBeans.getTo();
		Date tmrwDate = tmrwDate(new Date());
		
		final Employee employee = employeeService.findUserByEmail(profileBean.getEmail());
		EmpCabRequest empCabRequest = new EmpCabRequest();
		beanToEmpCabModel(requestBeans, empCabRequest);
		empCabRequest.setUser(employee);

		if (requestFor!=null && requestFor.equals("self")) {
			empCabRequest.setRequestFor("self");
		} else if (requestFor!=null && requestFor.equals("employee")) {
			empCabRequest.setRequestFor("Others");
			empCabRequest.setRequestingEmpId(employee.getId());
			Employee reqEmp = new Employee();
			reqEmp.setId(requestBeans.getRequestedEmpId());
			empCabRequest.setUser(reqEmp);
		} else {
			empCabRequest.setRequestFor("guest");
			empCabRequest.setGuestUserName(requestBeans.getGuestUserName());
		}

		try {
			Calendar c = Calendar.getInstance();
			Date newDate = null;
			Date todaysdate = null;
			Boolean skipDate = false;

			
			empCabRequest.setFromDate(fromDate);
			empCabRequest.setToDate(toDate);

			long diff = toDate.getTime() - fromDate.getTime();
			int numOfDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		
			for (int i = 0; i <= numOfDays; i++) {
				
				if (requestBeans.getFromAliasName()!=null && requestBeans.getFromAliasName().contains("Home"))
					requestTime = requestBeans.getPickTime();
				else
					requestTime = requestBeans.getDropTime();

				

				EmpCabRequestDetails empCabRequestDetails = new EmpCabRequestDetails();
				skipDate = false;
				todaysdate = fromDate;
				c.setTime(todaysdate);

				
				// check if cab needed for Saturday and check if Day is Saturday
				if (sat.equals(false) && Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
					skipDate = true;
				}
				// check if cab needed for Sunday and check if Day is Sunday
				else if (sun.equals(false) && Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
					skipDate = true;
				}
				else
				{
					checkValidDay = true;
				}

				c.add(Calendar.DATE, 1);
				newDate = (Date) c.getTime();

				if (skipDate) {
					fromDate = newDate;
					continue;
				}
				empCabRequestDetails.setRequestime((requestTime));
				empCabRequestDetails.setMobileNumber(requestBeans.getMobileNumber());
				empCabRequestDetails.setFromAddress(requestBeans.getFromAddress());
				empCabRequestDetails.setFromAliasName(requestBeans.getFromAliasName());
				empCabRequestDetails.setFromCity(requestBeans.getFromCity());
				empCabRequestDetails.setFromLandmark(requestBeans.getFromLandmark());
				empCabRequestDetails.setFromLocation(requestBeans.getFromLocation());
				empCabRequestDetails.setFromPincode(requestBeans.getFromPincode());
				empCabRequestDetails.setToAddress(requestBeans.getToAddress());
				empCabRequestDetails.setToAliasName(requestBeans.getToAliasName());
				empCabRequestDetails.setToCity(requestBeans.getToCity());
				empCabRequestDetails.setToLandmark(requestBeans.getToLandmark());
				empCabRequestDetails.setToLocation(requestBeans.getToLocation());
				empCabRequestDetails.setToPincode(requestBeans.getToPincode());
				empCabRequestDetails.setProjectId(requestBeans.getProjectId());
				empCabRequestDetails.setScheduleDate(fromDate);
				empCabRequestDetails.setReqStatus(TmsConstant.PENDING_W_MANAGER);
				empCabRequestDetails.setAction("Pending");
				empCabRequestDetails.setTravelStatus("Pending with Manager");
				empCabRequestDetails.setActiveRequest(true);
				empCabRequestDetails.setEmpCabRequest(empCabRequest);
				if (requestBeans.getRequestType()!=null && requestBeans.getRequestType().equals("pickup")) {
					
					if((fromDate.getDate() == new Date().getDate() && fromDate.getMonth() == new Date().getMonth()
							&& fromDate.getYear() == new Date().getYear())){
						if(validPickUpReq) {
							empCabRequestDetails.setPickOrDrop("pickup");
							setempcabreqdetails.add(empCabRequestDetails);
						}
					
					} else if(fromDate.getDate() == tmrwDate.getDate() && fromDate.getMonth() == tmrwDate.getMonth()
							&& fromDate.getYear() == tmrwDate.getYear()) {
						if(tmrwValidPickUpReq) {
							empCabRequestDetails.setPickOrDrop("pickup");
							setempcabreqdetails.add(empCabRequestDetails);
						}
					}
					else {
						empCabRequestDetails.setPickOrDrop("pickup");
						setempcabreqdetails.add(empCabRequestDetails);
					}
				}
				
				else if (requestBeans.getRequestType() != null && requestBeans.getRequestType().equals("drop")) {
					if((fromDate.getDate() == new Date().getDate() && fromDate.getMonth() == new Date().getMonth()
							&& fromDate.getYear() == new Date().getYear())){
						if(validDropReq) {
							empCabRequestDetails.setPickOrDrop("drop");
							empCabRequestDetails.setRequestime(requestTime);
							setempcabreqdetails.add(empCabRequestDetails);
						}
					
					} else if(fromDate.getDate() == tmrwDate.getDate() && fromDate.getMonth() == tmrwDate.getMonth()
							&& fromDate.getYear() == tmrwDate.getYear()) {
						if(tmrwValidDropReq) {
							empCabRequestDetails.setPickOrDrop("drop");
							empCabRequestDetails.setRequestime(requestTime);
							setempcabreqdetails.add(empCabRequestDetails);
						}
					}
					else {
						empCabRequestDetails.setPickOrDrop("drop");
						empCabRequestDetails.setRequestime(requestTime);
						setempcabreqdetails.add(empCabRequestDetails);
					}
					
				} else {
					EmpCabRequestDetails empcabDetails = new EmpCabRequestDetails();
					// If round trip
					// for Pickup
					if((fromDate.getDate() == new Date().getDate() && fromDate.getMonth() == new Date().getMonth()
							&& fromDate.getYear() == new Date().getYear())){
						if(validPickUpReq) {
							empCabRequestDetails.setPickOrDrop("pickup");
							empCabRequestDetails.setRequestime(requestBeans.getPickTime());
							setempcabreqdetails.add(empCabRequestDetails);
						} if(validDropReq) {
							empcabDetails = setDropReqData(requestBeans,fromDate,empCabRequest);
							empcabDetails.setPickOrDrop("drop");
							empcabDetails.setRequestime(requestBeans.getDropTime());
							setempcabreqdetails.add(empcabDetails);
						}
									
					} else if(fromDate.getDate() == tmrwDate.getDate() && fromDate.getMonth() == tmrwDate.getMonth()
							&& fromDate.getYear() == tmrwDate.getYear()) {
						if(tmrwValidPickUpReq) {
							empCabRequestDetails.setPickOrDrop("pickup");
							empCabRequestDetails.setRequestime(requestBeans.getPickTime());
							setempcabreqdetails.add(empCabRequestDetails);
						} if(tmrwValidDropReq) {
							empcabDetails = setDropReqData(requestBeans,fromDate,empCabRequest);
							empcabDetails.setPickOrDrop("drop");
							empcabDetails.setRequestime(requestBeans.getDropTime());
							setempcabreqdetails.add(empcabDetails);
						}
					}
					else {
						if (requestBeans.getFromAliasName()!=null && requestBeans.getFromAliasName().contains("Home")) {
							empcabDetails = setDropReqData(requestBeans,fromDate,empCabRequest);
							empCabRequestDetails.setPickOrDrop("pickup");
							empcabDetails.setPickOrDrop("drop");
							empCabRequestDetails.setRequestime(requestBeans.getPickTime());
							empcabDetails.setRequestime(requestBeans.getDropTime());
							setempcabreqdetails.add(empCabRequestDetails);
							setempcabreqdetails.add(empcabDetails);
						} else {
							empcabDetails = setDropReqData(requestBeans,fromDate,empCabRequest);
							empCabRequestDetails.setPickOrDrop("pickup");
							empcabDetails.setPickOrDrop("drop");
							empCabRequestDetails.setRequestime(requestBeans.getPickTime());
							empcabDetails.setRequestime(requestBeans.getDropTime());
							setempcabreqdetails.add(empCabRequestDetails);
							setempcabreqdetails.add(empcabDetails);
						}
					}
				
				}
				fromDate = newDate;
			}

			if (sat.equals(true) && sun.equals(true)) {
				empCabRequest.setWeekends("Both");
			} else {
				if (sat.equals(true)) {
					empCabRequest.setWeekends("Saturday");
				} else if (sun.equals(true)) {
					empCabRequest.setWeekends("Sunday");
				} else {
					empCabRequest.setWeekends("NoCabs");
				}
			}

			Integer cabRequestId = null;
			if(checkValidDay)
			{
				empCabRequest.setEmpCabRequestDetails(setempcabreqdetails);
				cabRequestId = cabRequestService.saveEmpCabRequest(empCabRequest);
			}
			else
			{
				jsonObject.put("data", "Request was not valid.. Cannot be raised");
				return jsonObject.toString();
			}
			
			// Send an notification mail to manager to review the request
			if (cabRequestId != null) {
				if (requestFor.equals("employee")) {
					Employee otherEmp = employeeService.findEmpByEmpId(requestBeans.getRequestedEmpId());
					UserProfileBean userProfileBean = new UserProfileBean();
				
					userProfileBean.setReqEmpEmail(otherEmp.getEmail());
					//userProfileBean.setReqEmpMgrEmail(requestedEmpDetails.getManagerEmail());
					userProfileBean.setManagerEmail(otherEmp.getManagerEmail());
					userProfileBean.setProjectName(requestBeans.getProjectName());
					userProfileBean.setEmpCode(otherEmp.getEmpcode());
					userProfileBean.setEmployeeName(otherEmp.getName());
					

					// for acknowledge to employee
					// cabRequestService.empAdhocAcknowledgeMail(
					// profileBean,cabRequestId,requestFor);
					// for manager to app/disappr the request
					// cabRequestService.sendMail(userProfileBean, cabRequestId,requestFor);

					// uncomment below 2 lines
					 tmsMailSend.empAdhocAcknowledgeMail(userProfileBean, cabRequestId, requestFor);
					 tmsMailSend.sendMail(userProfileBean, cabRequestId,requestFor);
				} else {
					profileBean.setProjectName(requestBeans.getProjectName());
					// for acknowledge to employee
					// cabRequestService.empAdhocAcknowledgeMail(
					// profileBean,cabRequestId,requestFor);

					// for manager to app/disappr the request
					// cabRequestService.sendMail(profileBean, cabRequestId,requestFor);

					// uncomment below 2 lines
					 tmsMailSend.empAdhocAcknowledgeMail(profileBean, cabRequestId, requestFor);
					 tmsMailSend.sendMail(profileBean, cabRequestId,requestFor);
				}
			}
			jsonObject.put("data", "Request has raised successfully.<br>Approval is pending with Manager");
			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	
		jsonObject.put("data", "something went wrong.. Please try again!!");
		return jsonObject.toString();
	}
	
	public Date tmrwDate(Date dt) {
	
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		return dt;
	}
	
	public EmpCabRequestDetails setDropReqData(AdhocCabRequestBean requestBeans,Date fromDate,EmpCabRequest empCabRequest) {

		EmpCabRequestDetails empcabDetails = new EmpCabRequestDetails();
		empcabDetails.setReqStatus("Pending with Manager");
		empcabDetails.setAction("Pending");
		empcabDetails.setTravelStatus("Pending with Manager");
		empcabDetails.setActiveRequest(true);
		empcabDetails.setScheduleDate(fromDate);
		empcabDetails.setEmpCabRequest(empCabRequest);
		empcabDetails.setProjectId(requestBeans.getProjectId());
		empcabDetails.setRequestime((requestBeans.getDropTime()));
		empcabDetails.setMobileNumber(requestBeans.getMobileNumber());
		empcabDetails.setFromAddress(requestBeans.getToAddress());
		empcabDetails.setFromAliasName(requestBeans.getToAliasName());
		empcabDetails.setFromCity(requestBeans.getToCity());
		empcabDetails.setFromLandmark(requestBeans.getToLandmark());
		empcabDetails.setFromLocation(requestBeans.getToLocation());
		empcabDetails.setFromPincode(requestBeans.getToPincode());
		empcabDetails.setToAddress(requestBeans.getFromAddress());
		empcabDetails.setToAliasName(requestBeans.getFromAliasName());
		empcabDetails.setToCity(requestBeans.getFromCity());
		empcabDetails.setToLandmark(requestBeans.getFromLandmark());
		empcabDetails.setToLocation(requestBeans.getFromLocation());
		empcabDetails.setToPincode(requestBeans.getFromPincode());
		return empcabDetails;
	}

	@RequestMapping(value = "/cabRequestHistory", method = RequestMethod.POST)
	@ResponseBody
	public String cabRequestDetailsHistory(@RequestBody CabReportBean cabReportBean, HttpServletRequest request) {

		String empCode = (String) request.getSession(false).getAttribute("empId");
		cabReportBean.setEmpCode(empCode);

		List<AdhocCabRequestBean> adhocCabRequestBeans = cabRequestService.getEmpCabRequest(cabReportBean);
		String arrayToJson = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			arrayToJson = objectMapper.writeValueAsString(adhocCabRequestBeans);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
		// return JsonConverter.getJsonCabRequestDetails(adhocCabRequestBeans);
	}

	@RequestMapping(value = "/viewOtherCabDetails", method = RequestMethod.GET)
	@ResponseBody
	public String viewOtherCabRequestDetails(HttpServletRequest request) {

		final UserProfileBean profileBean = (UserProfileBean) request.getSession().getAttribute("user");
		List<AdhocCabRequestBean> adhocCabRequestList = cabRequestService
				.findAllOtherOpenRequest(profileBean.getEmployeeId());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(adhocCabRequestList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;

	}
	
	@RequestMapping(value = "/getAdhocCabRequestList", method = RequestMethod.GET)
	@ResponseBody
	public String adhocCabRequestList(HttpServletRequest request) {
	
		final UserProfileBean profileBean = (UserProfileBean) request.getSession().getAttribute("user");
		String status = request.getParameter("status");
		List<AdhocCabRequestBean> adhocCabRequestList =null;
		List<AdhocCabRequestBean> cabReqList = null;
	
		if(status.equals(TmsConstant.APPROVED) || status.equals(TmsConstant.PENDING)) {
			cabReqList = cabRequestService
					.findAllOpenRequestByEmpId(profileBean.getEmployeeId(), status);
			adhocCabRequestList = DateUtil.checkValidUpcomingRequest(cabReqList);
		}else if(status.equals(TmsConstant.REJECTED) || status.equals(TmsConstant.CANCELED)){
			cabReqList = cabRequestService
					.findAllInActiveRequestByEmpId(profileBean.getEmployeeId(), status);
			adhocCabRequestList =  DateUtil.checkValidUpcomingRequest(cabReqList);
		
		}
		else
		{
			adhocCabRequestList = cabRequestService
					.findAllOpenRequestByEmpId(profileBean.getEmployeeId(), null); 
		//	adhocCabRequestList = DateUtil.checkValidUpcomingRequest(cabReqList);
			
			
		}

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;

		try {
			arrayToJson = objectMapper.writeValueAsString(adhocCabRequestList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;

	}

	
	@RequestMapping(value = "/cabRequestHistoryPage", method = RequestMethod.GET)
	public ModelAndView cabRequestHistoryPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("/tms/adhocHistory");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		final UserProfileBean profileBean = (UserProfileBean) request.getSession().getAttribute("user");
		List<String> aliasNameList = employeeService.findAllAddressAliasNames(profileBean.getEmployeeId());
		List<EmpProjMapping> projList = projectService.findEmpPrjNameByEmpId(profileBean.getEmployeeId());
		modelAndView.addObject("projList", projList);
		modelAndView.addObject("aliasNameList", aliasNameList);
		modelAndView.addObject("status", status);
		modelAndView.addObject("type", type);
		return modelAndView;
	}

	

	@RequestMapping(value = "/adhocReqByUserId", method = RequestMethod.GET)
	public ModelAndView adhocReqByUserId(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("/tms/adhocview");
		final UserProfileBean profileBean = (UserProfileBean) request.getSession().getAttribute("user");
		String status = request.getParameter("status");
		List<AdhocCabRequestBean> adhocCabRequestList = cabRequestService
				.findAllOpenRequestByEmpId(profileBean.getEmployeeId(), status);
		List<String> aliasNameList = employeeService.findAllAddressAliasNames(profileBean.getEmployeeId());
		modelAndView.addObject("aliasNameList", aliasNameList);
		modelAndView.addObject("cabRequestDetails", adhocCabRequestList);
		return modelAndView;
	}
	
	@RequestMapping(value = "/monthlyReqByUserId", method = RequestMethod.GET)
	@ResponseBody
	public String monthlyReqByUserId(HttpServletRequest request) {
		final UserProfileBean profileBean = (UserProfileBean) request.getSession().getAttribute("user");
		String status = request.getParameter("status");
		List<AdhocCabRequestBean> cabReqList = null;
		List<AdhocCabRequestBean> adhocCabRequestList = null;
		cabReqList=	cabRequestService
				.findAllMonthlyRequestByEmpId(profileBean.getEmployeeId(), status);
		adhocCabRequestList =  DateUtil.checkValidUpcomingRequest(cabReqList);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;

		try {
			if(status.equalsIgnoreCase("Used")) {
				arrayToJson = objectMapper.writeValueAsString(cabReqList);
			}else {
				arrayToJson = objectMapper.writeValueAsString(adhocCabRequestList);
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
	}
	
}