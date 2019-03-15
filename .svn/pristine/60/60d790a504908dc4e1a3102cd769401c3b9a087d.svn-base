package com.quinnox.flm.tms.global.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.service.CabRequestService;
import com.quinnox.flm.tms.module.util.DateUtil;

/**
 * @author AmareshP
 *
 */

@Controller
public class LoginController {

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CabRequestService cabRequestService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tms/login");
		return modelAndView;
	}

	@RequestMapping(value = "/tms/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request)  {

		ModelAndView modelAndView = new ModelAndView();
		final UserProfileBean profileBean = (UserProfileBean) request.getSession().getAttribute("user");
		if (profileBean != null) {
			Integer[][] cabStatusCount = cabRequestService.getAdhocRequestCount(profileBean.getEmpCode());
			List<AdhocCabRequestBean> adhocCabRequestList = cabRequestService
					.findAllOpenRequestByEmpId(profileBean.getEmployeeId()," ");

			//String[] pickTimings = { "05:00 AM", "05:30 AM", "06:00 AM", "06:30 AM", "06:00 PM", "06:30 PM", "07:00 PM",
			//		"07:30 PM", "08:00 PM", "08:30 PM", "09:00 PM", "09:30 PM" };
			//String[] dropTimings = { "06:00 AM", "06:30 AM", "09:30 PM", "10:00 PM", "10:30 PM" };

			Date today = new Date();

			Integer currentHour = today.getHours();
			Integer currentMin = today.getMinutes();
			String currentMinFormated = null;
			Integer currHr = currentHour - 12;
			String currentHourFormated = currentHour > 12 ? "0" + currHr.toString() :  "0" + currentHour.toString();

			String currAmPM = currentHour <= 12 ? "AM" : "PM";
		
			
			if (currentMin < 30)
				currentMinFormated = "30";
			else {
				currentMinFormated = "00";
				++currentHour;
			}
			
			String currentTime = currentHourFormated + ":" + currentMinFormated + " " + currAmPM;

			today.setHours(5);
			today.setMinutes(30);
			today.setSeconds(0);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date tomorrow = calendar.getTime();
			tomorrow.setHours(5);
			tomorrow.setMinutes(30);
			tomorrow.setSeconds(0);

			try {
				List<AdhocCabRequestBean> upcomingReqList = new ArrayList<AdhocCabRequestBean>();

				adhocCabRequestList.sort(Comparator.comparing(com.quinnox.flm.tms.module.beans.AdhocCabRequestBean::getScheduleDate));
				Integer countUpcomingReq = 0;
				Boolean validReq = false;
				for (AdhocCabRequestBean bean : adhocCabRequestList) {
					
					if(countUpcomingReq<2) {
						String reqAmPm = bean.getReqTime().substring(6, 8);
						Integer reqHour = Integer.parseInt(bean.getReqTime().substring(0, 2));
						validReq = DateUtil.todayValidReq(reqAmPm,currAmPM,reqHour,Integer.parseInt(currentHourFormated));
						if (bean.getScheduleDate().getDate() == today.getDate() &&
                              bean.getScheduleDate().getMonth() == today.getMonth() && bean.getScheduleDate().getYear() == today.getYear()
                            ) {
							if(validReq) {
								++countUpcomingReq;
								upcomingReqList.add(bean);
							}
							else {
								continue;
							}
						}else {
							++countUpcomingReq;
							upcomingReqList.add(bean);
						}
						
					}

             }
				String arrayToJson = null;
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
					arrayToJson = objectMapper.writeValueAsString(upcomingReqList);
					
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}	
				modelAndView.addObject("upcomingReq", upcomingReqList);
				System.out.println(arrayToJson);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String arrayToJson1 = null;
			ObjectMapper objectMapper1 = new ObjectMapper();

			if (cabStatusCount != null) {
				if (cabStatusCount[0] != null) {
					modelAndView.addObject("adhocPending", cabStatusCount[0][1]);
					modelAndView.addObject("adhocApproved", cabStatusCount[0][0]);
					modelAndView.addObject("adhocRejected", cabStatusCount[0][2]);
					modelAndView.addObject("adhocCancelled", cabStatusCount[0][3]);
					modelAndView.addObject("adhocNoShow", cabStatusCount[0][4]);
					
				}

				if (cabStatusCount[1] != null) {
					modelAndView.addObject("monthlyUsed", cabStatusCount[1][0]);
					modelAndView.addObject("monthlyUnused", cabStatusCount[1][1]);
					modelAndView.addObject("monthlyNoshow", cabStatusCount[1][2]);
				}
			}

			try {
				arrayToJson1 = objectMapper1.writeValueAsString(cabStatusCount);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			System.out.println(arrayToJson1);
		}
		modelAndView.setViewName("tms/home");
		logger.info("Showing Tms landing page: {}", "tms/home");
		return modelAndView;
	}

	@RequestMapping(value = "/userPage", method = RequestMethod.GET)
	String userPage(ModelMap modal) {
		return "tms/userPage";
	}

	// TODO Remove this method after development complete
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		Employee user = new Employee();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}



}
