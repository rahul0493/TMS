package com.quinnox.flm.tms.module.shiftplanner.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;














import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import com.quinnox.flm.tms.global.mail.TmsMailSender;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.model.Track;
import com.quinnox.flm.tms.module.shiftplanner.bean.AccountBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.CabRequestBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.EmployeeTrackBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.MonthBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.OtherTrackBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.ProjectBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.ShiftPlannerBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.StatusBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.TrackBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.TrackShift;
import com.quinnox.flm.tms.module.shiftplanner.model.OtherTrack;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanDetail;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanner;
import com.quinnox.flm.tms.module.shiftplanner.service.MonthlyCabRequestService;
import com.quinnox.flm.tms.module.shiftplanner.service.ShiftAllowanceService;
import com.quinnox.flm.tms.module.shiftplanner.service.ShiftPlannerService;
import com.quinnox.flm.tms.module.util.SortByTrack;

@Controller
@RequestMapping("tms/shiftPlanner")
public class ShiftPlannerController {

	@Autowired
	private ShiftPlannerService shiftPlannerService;

	@Autowired
	private MonthlyCabRequestService cabRequest;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private TmsMailSender tmsMailSender;
	
	@Autowired
	private ShiftAllowanceService shiftAllowanceService;

	private static final Logger LOG = Logger
			.getLogger(ShiftPlannerController.class);

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView shiftPlanner(ModelMap modal, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("tms/shiftPlanner");
		return modelAndView;

	}

	/*
	 * This method will get the account list of all the account spoc is handling
	 */
	@RequestMapping(value = "/getAllAcount", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getAllAccount(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession
				.getAttribute("user");

		Set<AccountBean> accountBeanList = new HashSet<AccountBean>();

		LOG.debug("get the list of employee by emloyeee Id ");
		List<Employee> employeeList = shiftPlannerService
				.findSpocDetails(profileBean.getEmployeeId());
		LOG.debug("iterate the list to get the list of account ");
		for (Employee emp : employeeList) {
			for (Track track : emp.getTracks()) {
				AccountBean accountBean = new AccountBean();
				accountBean.setId(track.getProject().getAccount().getId());
				accountBean.setAccountName(track.getProject().getAccount()
						.getAccountName());
				accountBeanList.add(accountBean);
			}
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {

			arrayToJson = objectMapper.writeValueAsString(accountBeanList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		LOG.debug(arrayToJson);
		LOG.debug("Get account list executed successfully");
		return arrayToJson;

	}

	/*
	 * 
	 * Get the list of project under the account by passing projectId
	 */

	@RequestMapping(value = "/getProjectByAccount", method = RequestMethod.GET)
	@ResponseBody
	// @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getProjbyAccount(HttpServletRequest request,
			@RequestParam("acc_id") int accountId) {

		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession
				.getAttribute("user");
		/*
		 * List<Employee> employeeDetails = shiftPlannerService
		 * .findSpocDetails(profileBean.getEmployeeId());
		 */  
		Set<ProjectBean> projectBeanSet = new TreeSet<ProjectBean>();
		LOG.debug("GetProjbyAccount for account" + profileBean.getEmployeeId());
		 projectBeanSet = shiftPlannerService
				.getProjectByAccount(profileBean.getEmployeeId(), accountId);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(projectBeanSet);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		LOG.debug(arrayToJson);
		LOG.debug("getProjbyAccount method executed successfully");
		return arrayToJson;
	}

	@RequestMapping(value = "/getTrackByProject", method = RequestMethod.GET)
	@ResponseBody
	// @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getTrack(HttpServletRequest request,
			@RequestParam("proj_id") int projectId) {

		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession
				.getAttribute("user");
		LOG.debug("Getting list of track under project");
		Set<TrackBean> trackBeanSet = new TreeSet<TrackBean>();
		 trackBeanSet = shiftPlannerService
				.findAllSpocTrack(profileBean.getEmployeeId(),projectId);
         
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(trackBeanSet);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		LOG.debug(arrayToJson);
		LOG.debug("track by project id method executed successfully");
		return arrayToJson;
	}

	/*
	 * this method will get the shift of selected track by trackid
	 */

	@RequestMapping(value = "/getShiftByTrack", method = RequestMethod.GET)
	@ResponseBody
	// @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getShiftByTrack(HttpServletRequest request,
			@RequestParam("track_id") int trackId) {
		Set<TrackShift> BeanList = new TreeSet<TrackShift>();
		LOG.debug("get all the shift of the track :=" + trackId);
		BeanList = shiftPlannerService.FindShiftbyTrack(trackId);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(BeanList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		LOG.debug(arrayToJson);
		LOG.debug("get shift by track Id method executed successfully");
		return arrayToJson;
	}

	/*
	 * 
	 * Get the shift For Project need to get shift according to selected project
	 */

	@RequestMapping(value = "/getShiftForProject", method = RequestMethod.GET)
	@ResponseBody
	// @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getShiftForProject(HttpServletRequest request,
			@RequestParam("proj_id") int projectId) {
		Set<TrackShift> trackShiftList = new TreeSet<TrackShift>();
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession
				.getAttribute("user");

		LOG.debug("getting list of track assigned to spoc");

		trackShiftList = shiftPlannerService.getShiftForProject(profileBean,
				projectId);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(trackShiftList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		LOG.debug(arrayToJson);
		LOG.debug("getShiftByProject method executed successfully");
		return arrayToJson;
	}

	/*
	 * 
	 * get the list of employee by passing trackId
	 */
	@RequestMapping(value = "/getEmployeeByProject", method = RequestMethod.GET)
	@ResponseBody
	// @Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public String getEmployeebyProject(HttpServletRequest request,
			@RequestParam("proj_id") int projectId/*,@RequestParam(value ="spocId",required=false) int spocId*/) {
		// new one get track based on project
		 HttpSession httpSession = request.getSession();
		 UserProfileBean profileBean = (UserProfileBean)
		 httpSession.getAttribute("user");
		Set<EmployeeTrackBean> trackBeanList = new TreeSet<EmployeeTrackBean>();

		trackBeanList = shiftPlannerService.getEmployeebyProject(projectId,profileBean.getEmployeeId());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(trackBeanList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		LOG.debug(arrayToJson);
		LOG.debug("executed successfully");
		return arrayToJson;
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

		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession
				.getAttribute("user");

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

		LOG.debug(arrayToJson);
		LOG.debug("GetShiftPlan detail method executed successfully");
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

		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession
				.getAttribute("user");

		List<MonthBean> monthBeanList = shiftPlannerService
				.getShiftPlannerByProjectId(year, monthId, projectId,profileBean.getEmployeeId());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {

			arrayToJson = objectMapper.writeValueAsString(monthBeanList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		LOG.debug(arrayToJson);
		LOG.debug("GetShiftPlan detail method executed successfully");
		return arrayToJson;

	}

	public Integer updateShiftPlanner(int accountId, Map<Integer, List<ShiftPlannerBean>> map,
			UserProfileBean profileBean, int trackId, int projectId,
			int monthId, int year,String cabReq) {
		System.out.println("inside update shift Plan ");
		Integer message=0;
		try{
			
		List<ShiftPlanner> previousShiftPlan = new ArrayList<ShiftPlanner>();
		List<ShiftPlanDetail> previousShiftPlannnerDetails = new ArrayList<ShiftPlanDetail>();
		//int shiftPlanId = 0;
		//List<ShiftPlanDetail> shiftPlanDetailList = new ArrayList<ShiftPlanDetail>();
       if(trackId !=0){
    	   //previousShiftPlan= shiftPlannerService.getShiftPlannerByTrackId(profileBean,trackId,projectId,monthId,year);
		previousShiftPlannnerDetails = shiftPlannerService.findShiftDetailByMonth(year,
				monthId, trackId);
		shiftPlannerService.deleteShiftPlanerDetails(previousShiftPlannnerDetails);
		shiftPlannerService.deleteMasterEntery(year,monthId,projectId,profileBean.getEmployeeId(),trackId);
		}else{
			previousShiftPlan = shiftPlannerService.shiftPlanByProjectId(year,monthId,projectId,profileBean.getEmployeeId());
			shiftPlannerService.deleteShiftPlan(previousShiftPlan);
			
		}
       
		
		 message = shiftPlannerService.createShiftPlanner(accountId, monthId, trackId,
				year, projectId, map, profileBean,cabReq);
		
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return message;
	}


	/*
	 * send mail to all the employees of track
	 */
	@RequestMapping(value = "/sendShiftPlannerEmailToEmployee", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView sendShiftPlannerEmailToEmployee(
			HttpServletRequest request, @RequestParam("year") int year,
			@RequestParam("month_id") int monthId,
			@RequestParam("trackName") String trackName,
			@RequestParam("accountName") String accountName,
			@RequestParam("projectName") String projectName,
			@RequestParam("trackId") int trackId,
			@RequestParam("projectId") int projectId) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession
				.getAttribute("user");
		/*Employee requestedEmpDetails = employeeService
				.findEmpByEmpId(profileBean.getEmployeeId());*/
		List<Employee> employee = shiftPlannerService
				.getEmployeeByTrackId(trackId);
		 for (Employee requestedEmpDetails : employee) {
		UserProfileBean userProfileBean = new UserProfileBean();
		userProfileBean.setReqEmpEmail(requestedEmpDetails.getEmail());

		userProfileBean
				.setReqEmpMgrEmail(requestedEmpDetails.getManagerEmail());

		// need to change for each Employee insted of spoc
		tmsMailSender.empMonthlyShiftPlanMail(userProfileBean, trackName,
				monthId, year, accountName, projectName, trackId, projectId,profileBean.getEmployeeId());
		 }

		modelAndView.setViewName("tms/successPage");
		return modelAndView;
	}

	/*
	 * save shift planner details
	 */
	@RequestMapping(value = "/saveShiftDetailsProject", method = RequestMethod.POST)
	@ResponseBody
	// @Transactional(propagation = Propagation.REQUIRED)
	public String saveMonthlyProjectData(@RequestBody String createJson,
			HttpServletRequest request, @RequestParam("action") String update,
			@RequestParam("month_id") int monthId,
			@RequestParam("track_id") int trackId,
			@RequestParam("year") int year,
			@RequestParam("proj_id") int projectId,
			@RequestParam("acc_id") int accountId,
	         @RequestParam("cab_req") String cabReq){
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession
				.getAttribute("user");
		List<ShiftPlannerBean> shiftPlannerBeanList = new ArrayList<ShiftPlannerBean>();    
		Map<Integer,List<ShiftPlannerBean>> map = new HashMap<Integer,List<ShiftPlannerBean>>();
		JSONArray jsonArray1 = new JSONArray(createJson);
		ShiftPlannerBean shiftPlannerBean = null;

		for (int i = 0; i < jsonArray1.length(); i++) {
			org.json.JSONObject jsonObject = jsonArray1.getJSONObject(i);
			LOG.debug(jsonObject.toString());

			shiftPlannerBean = new ShiftPlannerBean();
			shiftPlannerBean.setDay(jsonObject.getInt("date"));
			shiftPlannerBean.setEmployeeIntials(jsonObject.getString("emp_init"));
			shiftPlannerBean.setShiftId(jsonObject.getInt("shift_id"));
			shiftPlannerBean.setTrackId(jsonObject.getInt("track_id"));
			shiftPlannerBean.setEmployeeId(jsonObject.getInt("emp_id"));
			shiftPlannerBean.setMonthId(jsonObject.getInt("month_id"));
			shiftPlannerBean.setYear(jsonObject.getInt("year"));
            shiftPlannerBean.setMonthName(jsonObject.getString("month"));
         Set<OtherTrackBean> otherTrackBeans = new HashSet<OtherTrackBean>();
            for (int j = 0; j < jsonObject.getJSONArray("other_trck").length(); j++) {
				if(!jsonObject.getJSONArray("other_trck").toString().equalsIgnoreCase("[{}]")){
				JSONObject jObject = jsonObject.getJSONArray("other_trck").getJSONObject(j);
				OtherTrackBean otherTrack = new OtherTrackBean();
				otherTrack.setTrck_id(jObject.getInt("trck_id"));
				
				otherTrackBeans.add(otherTrack);
				}
            shiftPlannerBean.setOtherTrack(otherTrackBeans);
            }
			if (map.containsKey(shiftPlannerBean.getTrackId())) {

				shiftPlannerBeanList = map.get(shiftPlannerBean.getTrackId());

				shiftPlannerBeanList.add(shiftPlannerBean);
			} else {
				List<ShiftPlannerBean> addValues = new ArrayList<ShiftPlannerBean>();
				addValues.add(shiftPlannerBean);
				map.put(shiftPlannerBean.getTrackId(), addValues);

			}

		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		JSONObject jsonObject = new JSONObject();
		String arrayToJson = null;
		
		if (!update.equalsIgnoreCase("update")) {
			Integer message = shiftPlannerService.createShiftPlanner(accountId, monthId,
					trackId, year, projectId, map, profileBean,cabReq);
               
			if(message!=0){
				
				arrayToJson = "shiftPlan saved Successfully";
				jsonObject.put("data", arrayToJson);
			}else{
				arrayToJson = "please check your shiftplan there is some issue";
				jsonObject.put("data", arrayToJson);
			}
			if (message!= 0 && cabReq.equalsIgnoreCase("true")) {
				try {

					System.out.println("creating cab request for the Shift Planner");
					cabRequest.cabRequest(createJson,
							profileBean.getEmployeeId(), projectId, trackId);
					arrayToJson="cabRequest and shiftPlan saved successfully";
					jsonObject.put("data", arrayToJson);
} catch (Exception e) {					
					e.printStackTrace();
					arrayToJson="shiftPlan saved successfully but cab request didnt got generated"+e.getMessage();
					jsonObject.put("data", arrayToJson);
				}
			} 
           
		} else {
			LOG.debug("executing update shiftPlanner method");
			System.out.println("inide update shift planner");
		Integer message	= updateShiftPlanner(accountId, map, profileBean, trackId,
					projectId, monthId, year,cabReq);
		
		if(message!=0){
			arrayToJson = "shiftPlan saved Successfully";
			jsonObject.put("data", arrayToJson);
		}else{
			arrayToJson = "please check your shiftplan there is some issue";
			jsonObject.put("data", arrayToJson);
		}
		if(message!=0 && cabReq.equalsIgnoreCase("true")){
			try {
		LOG.debug("creating cab request for the Shift Planner");
			cabRequest.updateCabRequest(createJson, profileBean.getEmployeeId(), projectId, trackId);
			arrayToJson="cabRequest and shiftPlan saved successfullly";
			jsonObject.put("data", arrayToJson);
			
			}catch(Exception e){
				e.printStackTrace();
				arrayToJson="Somthing went wrong while saving cabrequest";
				jsonObject.put("data", arrayToJson);
				
			}
			}
			
		}
		return jsonObject.toString();
	}
	
	/*
	 *
	 * Get status of shiftPlanner
	 *
	 */
	@RequestMapping(value = "/getStatus", method = RequestMethod.GET)
	@ResponseBody
     public String getStatusOfShiftPlanner(HttpServletRequest httpServletRequest,
    		 @RequestParam("proj_id") int projectId,
    		 @RequestParam("month_id") int monthId,
    		 @RequestParam("year") int year){
    	 
		HttpSession httpSession = httpServletRequest.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession
				.getAttribute("user");
		  List<StatusBean> statusBean = shiftPlannerService.getStatusOfShiftPlanner(projectId,monthId,year,profileBean.getEmployeeId());
		  Collections.sort(statusBean, new SortByTrack());
		  ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			String arrayToJson = null;
			try {

				arrayToJson = objectMapper.writeValueAsString(statusBean);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				arrayToJson = "somthing went wrong";
			}

			LOG.debug(arrayToJson);
			LOG.debug("get shiftPlanner Status method executed successfully");
			return arrayToJson;

		}
	@RequestMapping(value = "/ApproveShiftDetails", method = RequestMethod.POST)
	@ResponseBody
	// need to atted approval by project
	public void approveShiftDetails(@RequestParam("monthId") int monthId,
			@RequestParam("year") int year, HttpServletRequest request,
			@RequestParam("trackId") int trackId,@RequestParam("proj_id") int projectId) {
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
try{
		String status = shiftAllowanceService.approveShiftDetails(monthId, year,
				trackId, profileBean.getEmployeeId(),projectId);

		System.out.println("status" + status);
		 shiftAllowanceService.GenerateShiftAllowance(monthId, year,
					trackId,profileBean.getEmployeeId(),projectId);
		 arrayToJson="succesfull";
}
catch(Exception e){
	e.printStackTrace();
	arrayToJson ="somthing went wrong";
}
	}
	
	/*
	 *
	 * Get status of shiftPlanner
	 *
	 */
	@RequestMapping(value = "/deleteShiftPlan", method = RequestMethod.GET)
	@ResponseBody
    public String deleteShiftPlanner(HttpServletRequest request,
   		 @RequestParam("proj_id") int projectId,
   		 @RequestParam("month_id") int monthId,
   		 @RequestParam("year") int year,@RequestParam("track") int track){

		
		List<ShiftPlanner> previousShiftPlan = new ArrayList<ShiftPlanner>();
		List<ShiftPlanDetail> previousShiftPlannnerDetails = new ArrayList<ShiftPlanDetail>();
		 if(track !=0){
	    	   //previousShiftPlan= shiftPlannerService.getShiftPlannerByTrackId(profileBean,trackId,projectId,monthId,year);
			previousShiftPlannnerDetails = shiftPlannerService.findShiftDetailByMonth(year,
					monthId, track);
			shiftPlannerService.deleteShiftPlanerDetails(previousShiftPlannnerDetails);
			}else{
				previousShiftPlan = shiftPlannerService.shiftPlanByProjectId(year,monthId,projectId,null);
				shiftPlannerService.deleteShiftPlan(previousShiftPlan);
			}
		  ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			String arrayToJson = null;
			try {

				arrayToJson = objectMapper.writeValueAsString("Deleted sucessfully");
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				arrayToJson = "somthing went wrong";
			}

			LOG.debug(arrayToJson);
			LOG.debug("get shiftPlanner Status method executed successfully");
			return arrayToJson;

		}
}




