package com.quinnox.flm.tms.module.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.mail.TmsMailSender;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.global.service.UserRoleService;
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.CabReportBean;
import com.quinnox.flm.tms.module.beans.EmpProjBean;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.beans.VendorBean;
import com.quinnox.flm.tms.module.beans.VendorSpocBean;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Vendor;
import com.quinnox.flm.tms.module.model.VendorSpoc;
import com.quinnox.flm.tms.module.service.CabRequestService;
import com.quinnox.flm.tms.module.service.FLMService;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.util.JsonConverter;

/**
 * @author AmareshP
 *
 */
@Controller
@RequestMapping("tms/flmDashboard") // adminPage/flm
public class FLMController {
	@Autowired
	private ProjectDetailsService projectDetailsService;
	@Autowired
	private CabRequestService cabRequestService;
	@Autowired
	private FLMService flmService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TmsMailSender tmsMailSend;
	@Autowired
	private ProjectDetailsService projectService;
	
	@Autowired
	private UserRoleService userRoleService;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	ModelAndView home(ModelMap modal) {
		ModelAndView modelAndView = new ModelAndView();
		List<Account> projectAccount = userRoleService.getAllAccount();
		modelAndView.addObject("accountList", projectAccount);
		modelAndView.setViewName("tms/adminPage");
		return modelAndView;
	}


	@RequestMapping(value = "/updateFLMAction", method = RequestMethod.POST)
	@ResponseBody
	public String updateFLMAction(@RequestBody String flmActionJson, HttpServletRequest request) {

		List<AdhocCabRequestBean> adRequestBeanList = JsonConverter.jsonToFLMAction(flmActionJson);
		for (AdhocCabRequestBean bean : adRequestBeanList) {
			EmpCabRequestDetails cabRequestDetails = cabRequestService.findByRequestId(bean.getCabDetailsId());
			//cabRequestDetails.setAction(bean.getAction());
			if (bean.getAction().contains(TmsConstant.APPROVED)) {
				cabRequestDetails.setAction(TmsConstant.APPROVED);
				
				if(!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
						!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
						!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
						!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
						)
				cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_PROCESSING);
			}
			else {
				if (bean.getAction().contains(TmsConstant.REJECTED)) {
					cabRequestDetails.setAction(TmsConstant.REJECTED);
					cabRequestDetails.setActiveRequest(false);
					
					if(!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
							!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
							!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
							!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
							)
					cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_Rejected_By_FLM);
				}
			}
			if(!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
					!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
					!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
					!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
					)
			{
				if (bean.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_PROCESSING))
					cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_PROCESSING);
				else if (bean.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED))
					cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_COMPLETED);
				else if (bean.getTravelStatus().contains(TmsConstant.REJECTED_B_FLM))
					cabRequestDetails.setTravelStatus(TmsConstant.REJECTED_B_FLM);
				else if (bean.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User))
					cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User);
				else if (bean.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor))
					cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor);
				else {
					if (bean.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm))
						cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm);
				}
			}
			
			cabRequestService.updateCabDetails(cabRequestDetails);
			Employee employee = employeeService.findById(cabRequestDetails.getEmpCabRequest().getUser().getId());
			Integer projId = cabRequestDetails.getProjectId();
			Project proj = projectService.findProjectById(projId);
			EmployeeBean employeeBean = new EmployeeBean();
			employeeBean.setProject(proj);
			employeeBean.setName(employee.getName());
			employeeBean.setManagerEmail(employee.getManagerEmail());
			employeeBean.setEmpcode(employee.getEmpcode());
			employeeBean.setEmail(employee.getEmail());
			employeeBean.setProjectName(proj.getProjectName());
			if (cabRequestDetails.getAction().contains("Rejected")) {
				tmsMailSend.fLMRejectedCabRequest(employeeBean, bean.getCabDetailsId());
			}
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("data", "Saved successfully.");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	
	
	
	@RequestMapping(value = "/ActionTravelStatusUpdateByFLM", method = RequestMethod.POST)
	@ResponseBody
	public String ActionTravelStatusUpdateByFLM(@RequestBody String flmActionJson, HttpServletRequest request) {

		List<AdhocCabRequestBean> adRequestBeanList = JsonConverter.jsonToFLMAction(flmActionJson);
		for (AdhocCabRequestBean bean : adRequestBeanList) {
			EmpCabRequestDetails cabRequestDetails = cabRequestService.findByRequestId(bean.getCabDetailsId());
			//cabRequestDetails.setAction(bean.getAction());
			if (bean.getAction().contains(TmsConstant.APPROVED)) {
				cabRequestDetails.setAction(TmsConstant.APPROVED);
				
//				if(!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
//						!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
//						!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
//						!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
//						)
				cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_PROCESSING);
			}
			else {
				if (bean.getAction().contains(TmsConstant.REJECTED)) {
					cabRequestDetails.setAction(TmsConstant.REJECTED);
					cabRequestDetails.setActiveRequest(false);
					
//					if(!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
//							!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
//							!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
//							!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
//							)
					cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_Rejected_By_FLM);
				}
			}
//			if(!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED) && 
//					!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm) && 
//					!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User) &&
//					!cabRequestDetails.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor)
//					)
			{
				if (bean.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_PROCESSING))
					cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_PROCESSING);
				else if (bean.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_COMPLETED))
					cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_COMPLETED);
				else if (bean.getTravelStatus().contains(TmsConstant.REJECTED_B_FLM))
					cabRequestDetails.setTravelStatus(TmsConstant.REJECTED_B_FLM);
				else if (bean.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User))
					cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_User);
				else if (bean.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor))
					cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Vendor);
				else {
					if (bean.getTravelStatus().contains(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm))
						cabRequestDetails.setTravelStatus(TmsConstant.TRAVEL_STATUS_NO_SHOW_B_Flm);
				}
			}
			
			cabRequestService.updateCabDetails(cabRequestDetails);
			Employee employee = employeeService.findById(cabRequestDetails.getEmpCabRequest().getUser().getId());
			Integer projId = cabRequestDetails.getProjectId();
			Project proj = projectService.findProjectById(projId);
			EmployeeBean employeeBean = new EmployeeBean();
			employeeBean.setProject(proj);
			employeeBean.setName(employee.getName());
			employeeBean.setManagerEmail(employee.getManagerEmail());
			employeeBean.setEmpcode(employee.getEmpcode());
			employeeBean.setEmail(employee.getEmail());
			employeeBean.setProjectName(proj.getProjectName());
			if (cabRequestDetails.getAction().contains("Rejected")) {
				tmsMailSend.fLMRejectedCabRequest(employeeBean, bean.getCabDetailsId());
			}
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("data", "Saved successfully.");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getProject", method = RequestMethod.POST)
	@ResponseBody
	public String getProjectsByAccount(@RequestBody Integer accountId,HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) session.getAttribute("user");
		SecurityContextImpl authenticationToken= (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authToken = authenticationToken.getAuthentication();
		Collection<GrantedAuthority> authorities= (Collection<GrantedAuthority>) authToken.getAuthorities();
		List<ProjectDetailsBean> projectList = new ArrayList<ProjectDetailsBean>();
		for(GrantedAuthority auth:authorities) {
			if(auth.getAuthority().equalsIgnoreCase("ADMIN") || auth.getAuthority().equalsIgnoreCase("FRONT_DESK")) {
				projectList = projectDetailsService.findProjectTracksByAccountId(accountId);
				break;
			}else {
				projectList = projectDetailsService.findProjectsByEmpAcc(accountId,profileBean.getEmployeeId());
			}
		}
		
		Collections.sort(projectList);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = "";
		try {
			arrayToJson = objectMapper.writeValueAsString(projectList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return arrayToJson;
	}
	
	
	
	@RequestMapping(value = "/vendor", method = RequestMethod.GET)
	public ModelAndView home(ModelMap modal, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tms/vendorPage");
		return modelAndView;
	}

	@RequestMapping(value = "/saveUpdateVendorDetails", method = RequestMethod.POST)
	@ResponseBody
	public String saveUpdateVendorDetails(HttpServletRequest request) {

		String updateVendorDetails = request.getParameter("updateVendorDetails");
		String getDetails = request.getParameter("getDetails");
		String vendorId = request.getParameter("vendorId");
		String vendorSpocId = request.getParameter("vendorSpocId");

		if (getDetails.equals("saveUpdate")) {

			VendorBean vendorDetailBean = JsonConverter.jsonToVendorDetails(updateVendorDetails);
			Vendor vendor = new Vendor();
			Set<VendorSpoc> vendorSpocs = new HashSet<VendorSpoc>();

			if (vendorDetailBean.getVendorId() != null)
				vendor.setVendorId(vendorDetailBean.getVendorId());
			vendor.setOfficeAddress(vendorDetailBean.getOfficeAddress());
			vendor.setOfficeNumber(vendorDetailBean.getOfficeNumber());
			vendor.setOwnerName(vendorDetailBean.getOwnerName());
			vendor.setTravelsName(vendorDetailBean.getTravelsName());
			vendor.setRateWithEscort(vendorDetailBean.getRateWithEscort());
			vendor.setRateWithoutEscort(vendorDetailBean.getRateWithoutEscort());

			for (VendorSpocBean spocBean : vendorDetailBean.getVendorSpocs()) {
				VendorSpoc spocModel = new VendorSpoc();
				if (spocBean.getSpocId() != null)
					spocModel.setSpocId(spocBean.getSpocId());
				spocModel.setSpocMobileNumber(spocBean.getSpocMobileNumber());
				spocModel.setSpocName(spocBean.getSpocName());
				spocModel.setVendor(vendor);
				vendorSpocs.add(spocModel);
			}
			vendor.setVendorSpocs(vendorSpocs);
			flmService.saveUpdateVendorDetails(vendor);
			JSONObject jsonObject = new JSONObject();

			try {
				jsonObject.put("data", "Saved successfully");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonObject.toString();
		} else {
			if (vendorId != null)
				flmService.deleteVendorDetails(Integer.parseInt(vendorId));
			if (vendorSpocId != null)
				flmService.deleteVendorSpocDetails(Integer.parseInt(vendorSpocId));

			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("data", "Deleted successfully.");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonObject.toString();
		}
	}

	@RequestMapping(value = "/getVendor", method = RequestMethod.GET)
	@ResponseBody
	public String getAddress(HttpServletRequest request) {

		String vendorId = request.getParameter("vendorId");
		List<Vendor> vendorList = new ArrayList<Vendor>();
		List<VendorBean> vendorLists = new ArrayList<VendorBean>();
		Vendor vendor = null;

		if (!vendorId.contains("null")) {
			vendor = flmService.getVendorById(Integer.parseInt(vendorId));
			vendorList.add(vendor);
		} else {
			vendorList = flmService.getAllVendor();
		}

		for (Vendor vendorModel : vendorList) {
			List<VendorSpocBean> spocBeanLists = new ArrayList<VendorSpocBean>();
			VendorBean vendorBean = new VendorBean();
			vendorBean.setOfficeAddress(vendorModel.getOfficeAddress());
			vendorBean.setOfficeNumber(vendorModel.getOfficeNumber());
			vendorBean.setOwnerName(vendorModel.getOwnerName());
			vendorBean.setTravelsName(vendorModel.getTravelsName());
			vendorBean.setRateWithEscort(vendorModel.getRateWithEscort());
			vendorBean.setRateWithoutEscort(vendorModel.getRateWithoutEscort());

			for (VendorSpoc spoc : vendorModel.getVendorSpocs()) {
				VendorSpocBean spocBean = new VendorSpocBean();
				spocBean.setSpocId(spoc.getSpocId());
				spocBean.setSpocMobileNumber(spoc.getSpocMobileNumber());
				spocBean.setSpocName(spoc.getSpocName());
				spocBeanLists.add(spocBean);
			}

			vendorBean.setVendorId(vendorModel.getVendorId());
			vendorBean.setVendorSpocs(spocBeanLists);
			vendorLists.add(vendorBean);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(vendorLists);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return arrayToJson;
	}

	// Daily cab requests report for view page
	@RequestMapping(value = "/getDailyReport", method = RequestMethod.GET)
	@ResponseBody
	public String getDailyReport(HttpServletRequest request) {
		String dayWiseList = request.getParameter("dayWiseList");
		List<AdhocCabRequestBean> cabRequestList =null;
		if(dayWiseList.equalsIgnoreCase("today")) {
			cabRequestList = cabRequestService.findTodaysCabRequest();
		}else if(dayWiseList.equalsIgnoreCase("tomorrow")) {
			cabRequestList = cabRequestService.findTomorrowsCabRequest();
		} else {
			cabRequestList = cabRequestService.findDailyCabRequest();
		}
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(cabRequestList);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return arrayToJson;
	}
	
	
	@RequestMapping(value = "/viewUpcomingRequestForFLM", method = RequestMethod.POST)
	@ResponseBody
	public String viewUpcomingRequest(HttpServletRequest request) throws ParseException {

		String status = request.getParameter("status");
		List<AdhocCabRequestBean> adhocCabRequestList = cabRequestService.findAllOpenRequestByEmpId(null, status);
		String[] tmrPickUpTimings = { "05:00 AM", "05:30 AM", "06:00 AM", "06:30 AM" };

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		date.setHours(5);
		date.setMinutes(30);
		date.setSeconds(0);

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();
		tomorrow.setHours(5);
		tomorrow.setMinutes(30);
		tomorrow.setSeconds(0);

		String date1 = df.format(date);
		String tmr = df.format(tomorrow);

		Date date2 = df.parse(date1);
		date2.setHours(5);
		date2.setMinutes(30);
		date2.setSeconds(0);
		Date tmr2 = df.parse(tmr);
		tmr2.setHours(5);
		tmr2.setMinutes(30);
		tmr2.setSeconds(0);

		List<AdhocCabRequestBean> upcomingReqList = new ArrayList<AdhocCabRequestBean>();
		for (AdhocCabRequestBean bean : adhocCabRequestList) {

			System.out.println("tmrw => " + tmr2 + " , schedule =>  " + bean.getScheduleDate() + " ,date => " + date2);

			if (bean.getScheduleDate().after(date2)) {

				if (bean.getScheduleDate().getDate() == tmr2.getDate()
						&& bean.getScheduleDate().getMonth() == tmr2.getMonth()
						&& bean.getScheduleDate().getYear() == tmr2.getYear()) {

					if (!Arrays.asList(tmrPickUpTimings).contains(bean.getReqTime())) {
						upcomingReqList.add(bean);
					}

				} else {
					upcomingReqList.add(bean);
				}
			}
		}
		String arrayToJson = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			arrayToJson = objectMapper.writeValueAsString(upcomingReqList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
	}

	
		// Flm Report Landing Page

	@RequestMapping(value = "/flmCabReportPage", method = RequestMethod.GET)
	public ModelAndView flmCabRequestHistoryPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("/tms/flmReportPage");

		List<Account> projectAccount = userRoleService.getAllAccount();
		modelAndView.addObject("accountList", projectAccount);

		return modelAndView;
	}

	@RequestMapping(value = "/flmCabRequestHistory", method = RequestMethod.POST)
	@ResponseBody
	public String flmCabRequestDetailsHistory(@RequestBody CabReportBean cabReportBean, HttpServletRequest request) {

		List<AdhocCabRequestBean> adhocCabRequestBeans = cabRequestService.getFlmCabReport(cabReportBean);

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
	}

	@RequestMapping(value = "/projectMapping", method = RequestMethod.GET)
	ModelAndView projectMapping(ModelMap modal) {
		ModelAndView modelAndView = new ModelAndView();
		Map<String, List<ProjectDetailsBean>> projectList = projectService.getAllProjectsAndAccounts();
		//System.out.println(employeeList);
		modelAndView.addObject("projectList", projectList);
		modelAndView.setViewName("tms/projectMapping");
		return modelAndView;
	}
	@RequestMapping(value = "/projectMappingEmpList", method = RequestMethod.GET)
	@ResponseBody
	public String empList() {
		List<EmployeeBean> empList = employeeService.getProjectAndEmp();
		
		String arrayToJson = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			arrayToJson = objectMapper.writeValueAsString(empList);
	} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
	}
	
	@RequestMapping(value = "/updateUserProjMapping", method = RequestMethod.POST)
	@ResponseBody
	public String updateUserProjMapping(@RequestBody String createJson,HttpServletRequest request) throws ParseException {

		EmpProjBean empProjBean=null;
		JSONObject jsonObject = new JSONObject();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			empProjBean = objectMapper.readValue(createJson, EmpProjBean.class);
			
			Employee emp = employeeService.findById(empProjBean.getUserId());
			Set<Project> projSet = new HashSet<Project>();
			for(Integer id:empProjBean.getProjects()) {
				Project proj = projectDetailsService.findProjectById(id);
				projSet.add(proj);
			}
			emp.setUpdatedEmpProj(projSet);
			
			
			employeeService.saveOrUpdate(emp);
			jsonObject.put("data", "Saved successfully");
			
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
		return jsonObject.toString();
	}
}