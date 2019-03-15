package com.quinnox.flm.tms.configuration;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.EmpProjMapping;

/**
 * 
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
 
	@Autowired
	private EmployeeService employeeService;
  
  @SuppressWarnings("unchecked")
  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //the user successfully logs in.
	    Map<String, String> userMap = (Map<String, String>) authentication.getDetails();
    	final Employee employee = employeeService.findUserByEmail(userMap.get("mail").toLowerCase());
    	
    	if(employee!=null){
    		// final Employee manager = employeeService.findManagerByEmail(employee.getManagerEmail());
        	final Set<EmpProjMapping> projectLists = employee.getEmpProjMapping();
        	Integer size = 0;
        	EmpProjMapping primaryProject = null;
        	Iterator<EmpProjMapping> project = projectLists.iterator();
        	while (project.hasNext()) {
				EmpProjMapping empProjMapping = project.next();
				if(size < empProjMapping.getProjectAllocation()) {
					primaryProject = empProjMapping;
					size = empProjMapping.getProjectAllocation();
				}
			}
        	   
        	UserProfileBean userProfileBean = new UserProfileBean();
        	
        	if(primaryProject != null) {
        		 userProfileBean.setProjectName(primaryProject.getProject().getProjectName());
             userProfileBean.setProjectId(primaryProject.getProject().getId());
             userProfileBean.setAccount(primaryProject.getProject().getAccount().getAccountName());
             userProfileBean.setAccountId(primaryProject.getProject().getAccount().getId());
        	}
           	userProfileBean.setEmployeeName(userMap.get("displayName"));
           	userProfileBean.setEmail(userMap.get("mail").toLowerCase());
           	userProfileBean.setEmpCode(userMap.get("employeeNumber"));
           	userProfileBean.setMobileNumber(employee.getPhoneNumber());
            userProfileBean.setEmployeeId(employee.getId());
            userProfileBean.setManagerEmail(employee.getManagerEmail());
           	HttpSession session = httpServletRequest.getSession();
            session.setAttribute("user", userProfileBean);
            session.setAttribute("userEmail", userMap.get("mail").toLowerCase());
            session.setAttribute("userName", userMap.get("displayName"));
            session.setAttribute("title",userMap.get("title"));
            List<String> authority = new LinkedList<>();
            Boolean isFrontDesk = false;
            
            for(GrantedAuthority ga: authentication.getAuthorities()){
            	switch(ga.getAuthority()){
            	case TmsConstant.SUPERADMIN:
            		session.setAttribute("isSuperAdmin", ga.getAuthority());
            		break;
            	case TmsConstant.ADMIN_USER: 
            		session.setAttribute("isAdmin", ga.getAuthority());
            		break;
            	case TmsConstant.SPOC:
            		session.setAttribute("isSpoc", ga.getAuthority());
            		break;
            	case TmsConstant.FINANCE:
               	session.setAttribute("isFinance", ga.getAuthority());
               	break;
            	case TmsConstant.SENIORMANAGER:
               	session.setAttribute("isManager", ga.getAuthority());
               	break;
            	case TmsConstant.FRONTDESK:
            	 session.setAttribute("isFrontDesk", ga.getAuthority());
            	 isFrontDesk = true;
            	}
            	authority.add(ga.getAuthority());
            }
            Collections.sort(authority);
            session.setAttribute("authorities", authority);
            session.setAttribute("empId",userProfileBean.getEmpCode());
            
            //set our response to OK status
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            //Redirect the user after successfully login
            if(isFrontDesk){
            	 httpServletResponse.sendRedirect("tms/flmDashboard/admin");
            }else{
            	 httpServletResponse.sendRedirect("tms/home");
            }
    	}else{
    		HttpSession session = httpServletRequest.getSession();
            session.setAttribute("user", null);
            session.setAttribute("userEmail", userMap.get("mail").toLowerCase());
            session.setAttribute("userName", userMap.get("displayName"));
            session.setAttribute("title",userMap.get("title"));
            session.setAttribute("authorities", authentication.getAuthorities());
            session.setAttribute("empId",userMap.get("employeeNumber"));
            
            //set the response status to OK 
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            //we will redirect the user after successfully login
            httpServletResponse.sendRedirect("tms/employee/employeeRegistraion");
    	}
    }
}