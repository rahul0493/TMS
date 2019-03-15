package com.quinnox.flm.tms.global.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.CabRequestRemarkBean;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.service.CabRequestService;
import com.quinnox.flm.tms.module.shiftplanner.bean.EmployeeTrackBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.MonthBean;
import com.quinnox.flm.tms.module.shiftplanner.service.ShiftPlannerService;
import com.quinnox.flm.tms.module.util.DateUtil;
import com.quinnox.flm.tms.module.util.EncodeDecodeUtil;
import com.quinnox.flm.tms.module.util.JsonConverter;

@Controller
@RequestMapping("tms/external")
public class NoAuthController {

	public static final Logger logger = LoggerFactory.getLogger(NoAuthController.class);

	@Autowired
	private CabRequestService cabRequestService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private TmsMailSender tmsMailSend;
	
	@Autowired
	private ShiftPlannerService shiftPlannerService;

	@RequestMapping(value = "/managerRemark", method = RequestMethod.GET)
	public ModelAndView managerRemark(final HttpServletRequest request) {
	
		List<String> excludedDates = null;
		Integer docId = 0;
		String encodDedparam = null;
		Map<String, String[]> map = request.getParameterMap();
		if (map.size() > 0) {
			Set<String> keys = map.keySet();
			for (String key : keys) {
				encodDedparam = key;
				break;
			}
		}

		if (encodDedparam != null) {
			String decodedParam = EncodeDecodeUtil.decodeString(encodDedparam);
			String id = decodedParam.split("=")[1];
			docId = Integer.parseInt(id);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("noActiveRequest", false);
		AdhocCabRequestBean adhocBean = null;
		List<AdhocCabRequestBean> empCabRequestDetails = cabRequestService.findByCabRequestId(docId);
		int count = 0;
		if (empCabRequestDetails != null) {
			List<AdhocCabRequestBean> validEmpCabRequestDetails = DateUtil.checkValidUpcomingRequest(empCabRequestDetails);
			DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			excludedDates = new ArrayList<String>();
			if(validEmpCabRequestDetails!=null) {
			for (AdhocCabRequestBean details : validEmpCabRequestDetails) {
				if(details.getScheduleDate().getDate() < new Date().getDate()
						&& details.getScheduleDate().getMonth() == new Date().getMonth()
						&& details.getScheduleDate().getYear() == new Date().getYear()) {
					count++;
					if(count == validEmpCabRequestDetails.size()) {
						modelAndView.addObject("isOldRequest", true);
					}
					continue;
				}
				
				if(details.getActiveRequest() && (!details.getTravelStatus().contains(TmsConstant.CANCELED_B_USER) ||
						!details.getTravelStatus().contains(TmsConstant.REJECTED_B_FLM) 	)) {
					adhocBean = new AdhocCabRequestBean();
					if (details.getRequestedFor().equalsIgnoreCase("Others")) {
						String requestRaisedBy = employeeService.findById(details.getRequestedEmpId()).getName();
						adhocBean.setRequestedBy(requestRaisedBy);
					}
					adhocBean.setFromLocation(details.getFromLocation());
					adhocBean.setProjectName(details.getProjectName());
					adhocBean.setToLocation(details.getToLocation());
					adhocBean.setEmpMailId(details.getEmpMailId());
					adhocBean.setAdhocMonthly(details.getAdhocMonthly());
					adhocBean.setDropTime(details.getDropTime());
					adhocBean.setEmpName(details.getEmpName());
					adhocBean.setFromDate(format.format(details.getFrom()));
					adhocBean.setGuestUserName(details.getGuestUserName());
					adhocBean.setPickTime(details.getPickTime());
					adhocBean.setReason(details.getReason());
					adhocBean.setRequestType(details.getRequestType());
					adhocBean.setCreatedDate(format.format(details.getRequestDate()));
					adhocBean.setToDate(format.format(details.getTo()));
					adhocBean.setTravelType(details.getTravelType());
					adhocBean.setId(docId);
					adhocBean.setMobileNumber(details.getMobileNumber());
					adhocBean.setAction(details.getAction());
					adhocBean.setRequestStatus(details.getRequestStatus());
				}
				
				if(!details.getActiveRequest() && details.getTravelStatus().contains(TmsConstant.CANCELED_B_USER) ||
						details.getTravelStatus().contains(TmsConstant.REJECTED_B_FLM)) {
					
					excludedDates.add(format.format(details.getScheduleDate()));
				}
				//break;
			}
		
			}
			
			if(excludedDates.size() == 0)
			{
				excludedDates = null;
			}
			if(adhocBean == null) {
				modelAndView.addObject("noActiveRequest", true);
			} else if (adhocBean != null && adhocBean.getRequestStatus() != null
					&& (adhocBean.getRequestStatus().equalsIgnoreCase(TmsConstant.PENDING_W_MANAGER))) {
				modelAndView.addObject("isActionPerformed", false);
			} else {
				modelAndView.addObject("isActionPerformed", true);
			}
			
			modelAndView.addObject("isRecordFound", true);
			modelAndView.addObject("CabRequestDetails", adhocBean);
			modelAndView.addObject("ExcludedDates", excludedDates);
		} else {
			modelAndView.addObject("isRecordFound", false);
		}
		modelAndView.setViewName("tms/action");
		return modelAndView;
	}

	@RequestMapping(value = "/managerRemark", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView managerRemark(HttpServletRequest request, @RequestBody String actions) {
		
		ModelAndView modelAndView = new ModelAndView();
		Employee employee = null;
		String requestedFor = null;
		CabRequestRemarkBean requestBeans = JsonConverter.jsonToCabrequestRemark(actions);
		
		cabRequestService.updateCabRequestByManager(requestBeans);
		List<AdhocCabRequestBean> cabRequest = cabRequestService.findByCabRequestId(requestBeans.getCabRequestId());
		EmployeeBean empBean = new EmployeeBean();
		for (AdhocCabRequestBean bean : cabRequest) {
			employee = employeeService.findById(bean.getUserId());
			empBean.setEmail(employee.getEmail());
			empBean.setName(employee.getName());
			empBean.setManagerEmail(employee.getManagerEmail());
			empBean.setEmpcode(employee.getEmpcode());
			empBean.setProjectName(bean.getProjectName());
			requestedFor = bean.getRequestedFor();
			break;
		}

		// Mail Notification to Employee based on manager Remark
		// cabRequestService.managerRemarkMail(empBean, requestBeans.getCabRequestId(),
		// requestedFor, requestBeans);
		tmsMailSend.managerRemarkMail(empBean, requestBeans.getCabRequestId(), requestedFor, requestBeans);
		// Mail Notification to FLM IF Manager approves the request
		// if(requestBeans.getAction().equals("Approve")){
		// employee.setEmail("SampleFLMMailID@gmail.com");
		// cabRequestService.managerRemarkMail(employee, requestBeans.getCabRequestId(),
		// requestedFor, requestBeans);
		// }
		modelAndView.setViewName("tms/successPage");
		return modelAndView;
	}

	@RequestMapping(value = "/approve", method = RequestMethod.GET)
	ModelAndView approveRejectRequest(HttpServletRequest request) {
		final ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/tmsApp/tms/successPage");
		logger.info("Showing action page: {}", "/tmsApp/tms/successPage");
		return modelAndView;
	}

	
	@RequestMapping(value = "/ManagerRemarkForAddrChange", method = RequestMethod.GET)
	public ModelAndView ManagerRemarkForAddrChange(final HttpServletRequest request) {

		// ,int oldId, int newId
		Integer empId = 0;
		// Integer oldId = 0;
		Integer newId = 0;
		String secondaryApprover = null;
		// EmployeeAddressBean oldAddress = null;
		EmployeeAddressBean newAddress = null;
		// Employee employee = new Employee();
		EmployeeBean empBean = new EmployeeBean();
		String encodDedparam = null;
		Map<String, String[]> map = request.getParameterMap();
		if (map.size() > 0) {
			Set<String> keys = map.keySet();
			for (String key : keys) {
				encodDedparam = key;
				break;
			}
		}

		if (encodDedparam != null) {
			String decodedParam = EncodeDecodeUtil.decodeString(encodDedparam);
			String[] ids = decodedParam.split(",");

			empId = Integer.parseInt(ids[0]);
			// oldId = Integer.parseInt(ids[1]);
			newId = Integer.parseInt(ids[1]);

			// secondaryApprover = ids[2].toString();
		}

		ModelAndView modelAndView = new ModelAndView();
		if (empId != null && newId != null) { // && oldId != null
			// oldAddress = employeeService.findAddressByAddressId(oldId);
			// newAddress = employeeService.findAddressByAddressId(newId);
			// employee = employeeService.findEmpByEmpId(empId);
			empBean = employeeService.findEmployeeByEmpId(empId);

		//	for (EmployeeAddressBean addr : empBean.getEmpAddress()) {
			EmployeeAddressBean addr = employeeService.findAddressByAddressId(newId);
				// if (oldId.equals(addr.getEmpAddressBeanId())) {
				// // oldAddress = addr;
				// // EmployeeAddressBean bean = new EmployeeAddressBean();
				// oldAddress = new EmployeeAddressBean();
				// oldAddress.setAddress(addr.getAddress());
				// oldAddress.setAlias(addr.getAlias());
				// oldAddress.setCity(addr.getCity());
				// oldAddress.setDefaultAddress(addr.getDefaultAddress());
				// oldAddress.setEmpAddressBeanId(addr.getEmpAddressBeanId());
				// oldAddress.setEmployeeId(addr.getEmployeeId());
				// oldAddress.setLandmark(addr.getLandmark());
				// oldAddress.setLocation(addr.getLocation());
				// oldAddress.setPincode(addr.getPincode());
				// oldAddress.setStatus(addr.getStatus());
				// } else

				if (newId.equals(addr.getEmpAddressBeanId()) && addr.getComment() == null && !addr.getStatus()) {
					// newAddress = addr;
					// EmployeeAddressBean bean = new EmployeeAddressBean();
					newAddress = new EmployeeAddressBean();
					newAddress.setAddress(addr.getAddress());
					newAddress.setAlias(addr.getAlias());
					newAddress.setCity(addr.getCity());
					newAddress.setDefaultAddress(addr.getDefaultAddress());
					newAddress.setEmpAddressBeanId(addr.getEmpAddressBeanId());
					newAddress.setEmployeeId(addr.getEmployeeId());
					newAddress.setLandmark(addr.getLandmark());
					newAddress.setLocation(addr.getLocation());
					newAddress.setPincode(addr.getPincode());
					newAddress.setStatus(addr.getStatus());
				} else {
					modelAndView.addObject("isActionPerformed", true);
					modelAndView.setViewName("tms/addressUpdateAction");
					return modelAndView;
				}
		//	}

			if (newAddress != null && empBean != null) { // oldAddress != null &&
				// modelAndView.addObject("OldAddress", oldAddress);
				modelAndView.addObject("NewAddress", newAddress);
				modelAndView.addObject("EmployeeDetails", empBean);
				modelAndView.setViewName("tms/addressUpdateAction");
				modelAndView.addObject("isRecordFound", true);
				modelAndView.addObject("isActionPerformed", false);

				// Set<EmployeeAddressBean> addList = empBean.getEmpAddress();
				// Integer countAddr = 0;
				// for (EmployeeAddressBean bean : addList) {
				// if (bean.getAlias().contains(newAddress.getAlias())) {
				// ++countAddr;
				// }
				// }
				//
				// if (countAddr == 1) {
				// modelAndView.addObject("isRecordFound", false);
				// }

			} else {
				modelAndView.addObject("isActionPerformed", true);
			}
		} else {
			modelAndView.addObject("isActionPerformed", true);
		}

		modelAndView.setViewName("tms/addressUpdateAction");
		return modelAndView;
	}

	@RequestMapping(value = "/ManagerRemarkForAddrChangePost", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView ManagerRemarkForAddrChangePost(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		List<EmployeeAddress> addrList =  new ArrayList<EmployeeAddress>();
		EmployeeAddress addrModel = new EmployeeAddress();
		Employee employee = new Employee();

		String newId = request.getParameter("newId");
		String action = request.getParameter("action");
		String comment = request.getParameter("comment");
		
		EmployeeAddressBean addrBean = employeeService.findAddressByAddressId(Integer.parseInt(newId));
		
		addrModel.setAlias(addrBean.getAlias());
		addrModel.setDefaultAddress(addrBean.getDefaultAddress());
		addrModel.setLandmark(addrBean.getLandmark());
		addrModel.setLocation(addrBean.getLocation());
		addrModel.setPincode(addrBean.getPincode());
		addrModel.setEmpcode(addrBean.getEmpCode());
		addrModel.setComment(comment);
		addrModel.setCity(addrBean.getCity());
		addrModel.setAddress(addrBean.getAddress());
		employee.setId(addrBean.getEmployeeId());
		addrModel.setStatus(addrBean.getStatus());
		addrModel.setEmployee(employee);
		addrModel.setEffectiveDate(addrBean.getEffectiveDate());
		addrList.add(addrModel);
		if (action.contains(TmsConstant.APPROVED)) {
			addrBean.setStatus(true);

			if (comment == null)
				addrBean.setComment(TmsConstant.APPROVED);
			else
				addrBean.setComment(comment + " " + TmsConstant.APPROVED);

			employeeService.updateEmpAddressByFlmRemark(addrBean);
			cabRequestService.updateAddressByUserandDate(addrBean, TmsConstant.APPROVED_B_MANAGER);
		} else {
			EmployeeBean empBean = employeeService.findEmployeeByEmpId(addrBean.getEmployeeId());
			tmsMailSend.mgrRemarkForAddrRejection(addrList,
					empBean, TmsConstant.REJECTED_B_MANAGER);
			addrBean.setStatus(false);
			addrBean.setComment(comment + " " + TmsConstant.REJECTED);
			employeeService.updateEmpAddressByFlmRemark(addrBean);
			cabRequestService.updateAddressByUserandDate(addrBean, TmsConstant.REJECTED);
		}
		modelAndView.setViewName("tms/successPage");
		return modelAndView;
		
	}
	@RequestMapping(value = "/viewShiftPlanner", method = RequestMethod.GET)
	public ModelAndView shiftPlanner(ModelMap modal, HttpServletRequest request,@RequestParam("year") int year,
	@RequestParam("monthId") int monthId,@RequestParam("type") String type,@RequestParam("trackId") int trackId,@RequestParam("projectName") String projectName,@RequestParam("trackName") String trackName,@RequestParam("accountName") String accountName,
	@RequestParam("projectId") int projectId,@RequestParam("spocId") int spocId){


		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("year", year);
		modelAndView.addObject("monthId", monthId);
		modelAndView.addObject("trackName", trackName);
		modelAndView.addObject("trackId", trackId);
		modelAndView.addObject("accountName", accountName);
		modelAndView.addObject("projectName", projectName);
		modelAndView.addObject("type",type);
		modelAndView.addObject("projectId",projectId);
		modelAndView.addObject("spocId",spocId);
		modelAndView.setViewName("tms/shiftPlanner");
		return modelAndView;
		
		
}
	
	/*
	 * 
	 * Get the shiftplanner for the view
	 */

	@RequestMapping(value = "/getShiftDetails", method = RequestMethod.GET)
	@ResponseBody
	// @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getMonthlyData(HttpServletRequest request,
			@RequestParam("year") int year,
			@RequestParam("month_id") int monthId,
			@RequestParam("track_id") int trackId,
			@RequestParam("proj_id") int projectId) {

		

		List<MonthBean> monthBeanList = shiftPlannerService.getShiftPlanner(
				year, monthId, trackId,projectId);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {

			arrayToJson = objectMapper.writeValueAsString(monthBeanList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		/*LOG.debug(arrayToJson);
		LOG.debug("GetShiftPlan detail method executed successfully");*/
		return arrayToJson;

	}

	/*
	 * 
	 * Get the shiftplanner for the view
	 */

	@RequestMapping(value = "/getShiftDetailsByProject", method = RequestMethod.GET)
	@ResponseBody
	// @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getMonthlyDatabyProject(HttpServletRequest request,
			@RequestParam("year") int year,
			@RequestParam("month_id") int monthId,
			@RequestParam("proj_id") int projectId) {

	
		List<MonthBean> monthBeanList = shiftPlannerService
				.getShiftPlannerByProjectId(year, monthId, projectId,null);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {

			arrayToJson = objectMapper.writeValueAsString(monthBeanList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		/*LOG.debug(arrayToJson);
		LOG.debug("GetShiftPlan detail method executed successfully");*/
		return arrayToJson;
	}
	/*
	 * 
	 * get the list of employee by passing trackId
	 * 
	 */
	@RequestMapping(value = "/getEmployeeByProject", method = RequestMethod.GET)
	@ResponseBody

	
	public String getEmployeebyProject(HttpServletRequest request,@RequestParam("proj_id") int projectId,@RequestParam("spocId") int spocId) {
		// new one get track based on project
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
		Set<EmployeeTrackBean> trackBeanList = new HashSet<EmployeeTrackBean>();
		
		trackBeanList= shiftPlannerService.getEmployeebyProject(projectId,spocId);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(trackBeanList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		/*LOG.debug(arrayToJson);
		LOG.debug("executed successfully");*/
		return arrayToJson;
	}
}