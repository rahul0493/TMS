package com.quinnox.flm.tms.listener;

import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TmsContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("ServletContextListener destroyed");
	//	ServletContext context=event.getServletContext();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		/*System.out.println("ServletContextListener started");	*/
		ServletContext context=event.getServletContext();
		Calendar cal=Calendar.getInstance();
		context.setAttribute("serverStartTime", cal.getTimeInMillis());
		/*System.out.println(cal.getTimeInMillis());*/
	}

}