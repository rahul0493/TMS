package com.quinnox.flm.tms.configuration;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.beans.factory.annotation.Value;*/
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author AmareshP
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.
			authorizeRequests()
				.antMatchers("/").denyAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/tms/external/**").permitAll()
				.antMatchers("/tms/employee/employeeRegistraion").hasAnyAuthority("READ-ONLY","USER")
				.antMatchers("/tms/security/**").hasAnyAuthority("READ-ONLY")
				.antMatchers("/tms/employee/**").hasAnyAuthority("USER","READ-ONLY")
				.antMatchers("/tms/homePage/**").hasAuthority("USER")
				.antMatchers("/tms/adhocRequest/**").hasAuthority("USER")
				.antMatchers("/tms/home").hasAuthority("USER")
				.antMatchers("/tms/reportRequest/**").hasAnyAuthority("SUPER_ADMIN","ADMIN")
				.antMatchers("/tms/project/**").hasAnyAuthority("SUPER_ADMIN","ADMIN","SPOC","SENIOR_MANAGER")
				.antMatchers("/tms/userPage/**").hasAuthority("USER")
				.antMatchers("/tms/adminPage/**").hasAnyAuthority("SUPER_ADMIN","ADMIN")
				.antMatchers("/tms/financePage/**").hasAnyAuthority("SUPER_ADMIN","FINANCE")
				.antMatchers("/tms/monthlyRequest/**").hasAnyAuthority("SUPER_ADMIN","SPOC")
				.antMatchers("/tms/shiftPlanner/**").hasAnyAuthority("SUPER_ADMIN","SPOC")
				.antMatchers("/tms/track/**").hasAnyAuthority("SUPER_ADMIN","SENIOR_MANAGER")
				.antMatchers("/tms/shift/**").hasAnyAuthority("SUPER_ADMIN","SPOC")
				.antMatchers("/tms/shiftAllowance/**").hasAnyAuthority("SUPER_ADMIN","FINANCE")
				.antMatchers("/tms/mappings/**").hasAnyAuthority("SUPER_ADMIN","SENIOR_MANAGER")
				.antMatchers("/admin/**").hasAnyAuthority("SUPER_ADMIN","ADMIN")
				.antMatchers("/tms/tripSheetDetails/**").hasAnyAuthority("SUPER_ADMIN","ADMIN","FRONT_DESK")
				.antMatchers("/tms/flmDashboard/**").hasAnyAuthority("SUPER_ADMIN","ADMIN","FRONT_DESK")
				.anyRequest()
				.authenticated().and().csrf().disable().formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/tms/home")
				.failureUrl("/login?error=true")
				.successHandler(customAuthenticationSuccessHandler)
				.usernameParameter("email")
				.passwordParameter("password")
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/403")
				;
   	 http
         .sessionManagement()
           .maximumSessions(1)
           .expiredUrl("/tmsApp/login?expired")
           .and()
           .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
           .invalidSessionUrl("/tmsApp/login");

	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}
