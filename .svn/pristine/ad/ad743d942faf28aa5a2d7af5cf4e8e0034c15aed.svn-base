package com.quinnox.flm.tms.global.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author AmareshP
 *
 */
@Controller
public class ErrorHandler {

	@RequestMapping("/403")
	public ModelAndView accesssDenied(Principal user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			
			model.addObject("msg", "Hi " + user.getName()
			+ ", you do not have permission to access this page!");
			
		} else {
			model.addObject("msg",
			"You do not have permission to access this page!");
		}

		model.setViewName("/error/403");
		
		return model;

	}

}
