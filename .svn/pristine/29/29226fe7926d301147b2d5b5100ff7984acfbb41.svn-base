package com.quinnox.flm.tms.global.controller;

import java.util.HashSet;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.quinnox.flm.tms.global.beans.SpocBean;
import com.quinnox.flm.tms.global.beans.TrackMappingBean;
import com.quinnox.flm.tms.global.beans.UserRoleBean;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.model.UserRole;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.global.service.TrackService;
import com.quinnox.flm.tms.global.service.UserRoleService;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.util.JsonConverter;

/**
 * @author AmareshP
 *
 */
@Controller
@RequestMapping("tms/adminPage")
public class AdminController {

	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ProjectDetailsService projectDetailService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private TrackService trackserivce;

	
	

	@RequestMapping(value = "/viewEmployeeDetails", method = RequestMethod.GET)
	public ModelAndView viewEmployeeDetails(ModelMap modal, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		List<Employee> empList = employeeService.findAllEmployees();
		modelAndView.addObject("empList", empList);
		modelAndView.setViewName("tms/viewAllEmpDetails");
		return modelAndView;
	}	

	

	

}
