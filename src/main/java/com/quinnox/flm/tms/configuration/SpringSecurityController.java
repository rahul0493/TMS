package com.quinnox.flm.tms.configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.EmpProjMapping;

@Controller
@RequestMapping("tms/security")
public class SpringSecurityController {

	
	@Autowired
	private EmployeeService employeeService;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/updateUserAuthority", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		SecurityContextImpl authenticationToken= (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authToken = authenticationToken.getAuthentication();
		Map<String, String> userMap = (Map<String, String>) authToken.getDetails();
	
		final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		grantedAuths.add(new SimpleGrantedAuthority("USER"));
		final UsernamePasswordAuthenticationToken newAuthToken = new UsernamePasswordAuthenticationToken(authToken.getName(), authToken.getCredentials(), grantedAuths);
		newAuthToken.setDetails(userMap);
		
        final Employee employee = employeeService.findUserByEmail(userMap.get("mail").toLowerCase());
  		//final Employee manager = employeeService.findManagerByEmail(employee.getManagerEmail());
  		
        Set<EmpProjMapping> projectLists = employee.getEmpProjMapping();
    	Integer size = 0;
    	EmpProjMapping primaryProject = null;
    	Iterator<EmpProjMapping> project = projectLists.iterator();
    	while (project.hasNext()) {
			EmpProjMapping empProjMapping = project.next();
			if(size < empProjMapping.getProjectAllocation())
			{
				primaryProject = empProjMapping;
				size = empProjMapping.getProjectAllocation();
			}
		}
    	
    	UserProfileBean userProfileBean = new UserProfileBean();
    	
    	if(primaryProject != null)
    	{
    		
    		userProfileBean.setProjectId(primaryProject.getProject().getId());
            userProfileBean.setProjectName(primaryProject.getProject().getProjectName());
            userProfileBean.setAccount(primaryProject.getProject().getAccount().getAccountName());
            userProfileBean.setAccountId(primaryProject.getProject().getAccount().getId());
    	}
    	userProfileBean.setEmployeeName(userMap.get("displayName"));
    	userProfileBean.setEmail(userMap.get("mail").toLowerCase());
    	userProfileBean.setEmpCode(userMap.get("employeeNumber"));
    	userProfileBean.setMobileNumber(employee.getPhoneNumber());
        userProfileBean.setEmployeeId(employee.getId());
        userProfileBean.setManagerEmail(employee.getManagerEmail());
        session.setAttribute("user", userProfileBean);
        session.setAttribute("authorities", newAuthToken.getAuthorities());
	    authenticationToken.setAuthentication(newAuthToken);
	   // userProfileBean.setProject(employee.getProject());
	   // userProfileBean.setManagerCode(employee.getManagerCode());
	   // userProfileBean.setManagerName(manager.getName());
	    
		return new ModelAndView("redirect:/tms/home");
	}
	
}
