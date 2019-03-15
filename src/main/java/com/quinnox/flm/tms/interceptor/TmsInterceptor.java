package com.quinnox.flm.tms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author AmareshP
 *
 */
@Component
public class TmsInterceptor implements HandlerInterceptor
{

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {

		final String uri = request.getRequestURI();
		final HttpSession session = request.getSession(false);
		      Object obj=null;
		if(session!=null){
			obj = session.getAttribute("SPRING_SECURITY_CONTEXT");
		}
		if (session==null || obj == null){
			response.setHeader("Log_Out", "true");
			return true;
		}else{
			response.setHeader("Log_Out", "false");
		}
		System.out.println(uri);
		return true;
	}

	@Override
	public void afterCompletion(final HttpServletRequest arg0, final HttpServletResponse arg1, final Object arg2,
			final Exception arg3) throws Exception
	{
		// System.out.println("After-Completion");

	}

	@Override
	public void postHandle(final HttpServletRequest arg0, final HttpServletResponse arg1, final Object arg2,
			final ModelAndView arg3) throws Exception
	{
		// System.out.println("Post-handle");
	}
}
