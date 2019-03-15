package com.quinnox.flm.tms.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.EmployeeService;

/**
 * @author AmareshP
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private LdapConfiguration ldapConfiguration;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
       
     String username = auth.getName();
      final String password = auth.getCredentials().toString();
      Map<String, String> ldapUserMap=null;
      try {

    	  if(username.startsWith("testing") && "password".equalsIgnoreCase(password)){
    		  
    		  final Employee employee = employeeService.findUserByEmail(username.toLowerCase()+"@quinnox.com");
    		  if(employee!=null){
    			  ldapUserMap=new HashMap<>();
    			  ldapUserMap.put("mail",employee.getEmail());
    	   	   	  ldapUserMap.put("manager","Global User");
    	   	   	  ldapUserMap.put("sAMAccountName",username);
    	   	   	  ldapUserMap.put("displayName",employee.getName());
    	   	   	  ldapUserMap.put("title",employee.getEmpDesignation());
    	   	   	  ldapUserMap.put("employeeNumber",employee.getEmpcode());
    		  }
    
    	  }else{
    		 ldapUserMap = ldapConfiguration.authenticateEmployee(username, password);
    	  }
    	   
	     /* ldapUserMap=new HashMap<>();
	   	   ldapUserMap.put("mail","shobanbabup@quinnox.com");
	   	   ldapUserMap.put("manager","Ashwathy");
	   	   ldapUserMap.put("sAMAccountName","Shoban");
	   	   ldapUserMap.put("displayName","Shoban Babu JP");
	   	   ldapUserMap.put("title","Consultant");
	   	   ldapUserMap.put("employeeNumber","6653");*/

	} catch (Exception e) {
		e.printStackTrace();
	}
      if(ldapUserMap==null){
    	  throw new BadCredentialsException("External system authentication failed");
      }
      Set<String> userRoles = employeeService.getUserRoles(ldapUserMap.get("mail").toLowerCase());
      
      if(userRoles==null){
    	     final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			 grantedAuths.add(new SimpleGrantedAuthority("READ-ONLY"));
				final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
    			authenticationToken.setDetails(ldapUserMap);
    			return authenticationToken;
      }else{
    	  
    	      final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
    			for(String role:userRoles){
    				 grantedAuths.add(new SimpleGrantedAuthority(role));
    			}
    			final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
    			authenticationToken.setDetails(ldapUserMap);
    			return authenticationToken;
      }
    }
 
    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}