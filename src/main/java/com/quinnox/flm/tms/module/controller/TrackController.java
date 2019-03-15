package com.quinnox.flm.tms.module.controller;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.model.UserRole;
import com.quinnox.flm.tms.global.service.UserRoleService;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.beans.TrackDetailsBean;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Track;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.util.JsonConverter;

@Controller
@RequestMapping("tms/track")
public class TrackController {

	@Autowired
	private ProjectDetailsService projectDetailsService;
	@Autowired
	private UserRoleService userRoleService;
	
	@RequestMapping(value = "/track_details", method = RequestMethod.GET)
	public ModelAndView home(ModelMap modal,HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		List<Account> adminAcc = null;
		Set<EmpProjMapping> spocAcc = null;
		HttpSession session = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) session.getAttribute("user");
		SecurityContextImpl authenticationToken= (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authToken = authenticationToken.getAuthentication();
		Collection<GrantedAuthority> authorities= (Collection<GrantedAuthority>) authToken.getAuthorities();
		for(GrantedAuthority auth:authorities) {
			if(auth.getAuthority().equalsIgnoreCase("ADMIN")) {
				adminAcc=  userRoleService.getAllAccount();
				modelAndView.addObject("accountList", adminAcc);
				break;
			}else {
				spocAcc =  projectDetailsService.findAccountByEmpId(profileBean.getEmployeeId());
				modelAndView.addObject("accountList", spocAcc);
			}
		}
		modelAndView.setViewName("tms/createTracker");
		return modelAndView;
		
	//	HttpSession httpSession = request.getSession();
	//	UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");

		
		//	List<Shift> shiftList = projectDetailsService.findAllShifts();
	
	//	 List<TrackDetailsBean> trackList = projectDetailsService.findTracksByProjectId(profileBean.getProjectId());

	//	List<ProjectDetailsBean> projectBeanList = new ArrayList<ProjectDetailsBean>();
	//	ProjectDetailsBean projectBean = null;
	//	Set<TrackDetailsBean> trackBeanSet = new HashSet<TrackDetailsBean>();
//		List<Project> projectList = projectDetailsService.findProjectDetailsByAccountId(profileBean.getAccountId());
//		for(Project project : projectList) {
//
//			projectBean = new ProjectDetailsBean();
//			projectBean.setAccountId(project.getAccount().getId());   
//			projectBean.setAccountName(project.getAccount().getAccountName());   
//			projectBean.setProjectId(project.getId());
//			projectBean.setProjectName(project.getProjectName());
//
//			Set<ShiftDetailsBean> shiftBeanSet = new HashSet<ShiftDetailsBean>();
//
//			ShiftDetailsBean shiftBean = null;
//
//			TrackDetailsBean trackBean = null;
//
//			for(Track trackDetails : project.getTrack()) {
//
//				trackBean = new TrackDetailsBean();
//				trackBean.setTrackDetailsId(trackDetails.getTrackId());
//				trackBean.setTrackName(trackDetails.getTrackName());
//
//				//				if(trackDetails.getShiftDetails().size() <= 0)
//				//				{
//				//					continue;
//				//				}
//
//				for(ShiftDetails shiftDetails : trackDetails.getShiftDetails()) {
//
//					shiftBean = new ShiftDetailsBean();
//					shiftBean.setShiftId(shiftDetails.getShift().getShiftId());
//					// To check
//					shiftBean.setShiftDetailsId(shiftDetails.getId());
//					shiftBean.setShiftInitials(shiftDetails.getShift().getShiftInitials());
//					shiftBean.setShiftName(shiftDetails.getShift().getShiftName());
//					shiftBean.setStartTime(shiftDetails.getStartTime());
//					shiftBean.setEndTime(shiftDetails.getEndTime());
//					shiftBean.setPickup(shiftDetails.getPickUpEligible());
//					shiftBean.setDrop(shiftDetails.getDropEligible());
//					shiftBeanSet.add(shiftBean);
//
//				}
//
//				trackBean.setShiftDetails(shiftBeanSet);
//
//				trackBeanSet.add(trackBean);
//
//			}
//			projectBean.setTrackDetails(trackBeanSet);
//
//			projectBeanList.add(projectBean);
//		}
	//	modelAndView.addObject("projectList", projectList);
 //		modelAndView.addObject("projectBeanList", projectBeanList);
		//	modelAndView.addObject("shiftList", shiftList);
	//	modelAndView.addObject("trackList", trackList);
		
	}

	@RequestMapping(value = "/save_track_details", method = RequestMethod.POST)
	@ResponseBody
	public String saveProjectDetails( @RequestBody String projectDetailsJSON, HttpServletRequest request) {

		ProjectDetailsBean projectDetailsBean = JsonConverter.jsonToProjectDetails(projectDetailsJSON);

		for(TrackDetailsBean bean : projectDetailsBean.getTrackDetails())
		{
			Track track = new Track();
			Project project = new Project();
			project.setId(projectDetailsBean.getProjectId());
			track.setTrackName(bean.getTrackName());
			track.setProject(project);
			projectDetailsService.saveOrUpdateTrack(track);
		}

		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("data", "Saved successfully.");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject.toString();
	}

	
	@RequestMapping(value = "/updateTrackDetails", method = RequestMethod.POST)
	@ResponseBody
	public String updateRequest(HttpServletRequest request) { 

 		String updateTrackDetails = request.getParameter("updateTrackDetails");
		String getDetails = request.getParameter("getDetails");
	//	String projectId = request.getParameter("projectId");
		String trackId = request.getParameter("trackId");
		if(getDetails.equals("update")){

			ProjectDetailsBean projectDetailBean = JsonConverter.jsonToProjectDetails(updateTrackDetails);

			
			//projectDetailsService.DeleteAllTracksByProjectId(projectDetailBean.getProjectId());

			Project project = new Project();
			project.setId(projectDetailBean.getProjectId());

			for(TrackDetailsBean TrackBean : projectDetailBean.getTrackDetails())
			{
				Track track = new Track();
				
				track.setProject(project);
				track.setTrackName(TrackBean.getTrackName());
				
				if(TrackBean.getTrackDetailsId() != null)
				{
					track.setTrackId(TrackBean.getTrackDetailsId());
				}
				
				projectDetailsService.saveOrUpdateTrack(track);

			}
			JSONObject jsonObject = new JSONObject();

			try {
				jsonObject.put("data", "Saved successfully.");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return jsonObject.toString();

		}
		else
		{
			String resultMessage = projectDetailsService.deleteTrackById(Integer.parseInt(trackId));

 			JSONObject jsonObject = new JSONObject();

			try {
				jsonObject.put("data", resultMessage);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return jsonObject.toString();
		}


	}

	// page load , prj tracks existing for editing 
	@RequestMapping(value = "/getExistProjectTrackerList", method = RequestMethod.POST)
	@ResponseBody
	public String getProjectTracksByAccount(HttpServletRequest request) {  
		
		HttpSession session = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) session.getAttribute("user");
		String arrayToJson  = projectDetailsService.findProjectsByEmp(profileBean.getEmployeeId());
		return arrayToJson;
	}
	
	// prj id name based on acc id 
	@RequestMapping(value = "/getProjectTrackerList", method = RequestMethod.POST)
	@ResponseBody
	public String getProjectListByAccount(@RequestBody Integer accountId,HttpServletRequest request) { 
		HttpSession session = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) session.getAttribute("user");
		final List<ProjectDetailsBean> projects = projectDetailsService.findProjectsByEmpAcc(accountId,profileBean.getEmployeeId());
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(projects);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
	}
	
// based on prj id  existing tracks to show 
	@RequestMapping(value = "/getTrackList", method = RequestMethod.POST)
	@ResponseBody
	public String getTracksByProject(@RequestBody Integer projectId) {  

		List<TrackDetailsBean> trackList = projectDetailsService.findTracksByProjectId(projectId);

		trackList.sort(Comparator.comparing(TrackDetailsBean::getTrackName));
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(trackList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
	}
	

}
