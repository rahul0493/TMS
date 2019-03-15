package com.quinnox.flm.tms.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author AmareshP
 *
 */
@Controller
@RequestMapping("tms/homePage")
public class HomePageController {
	
	@RequestMapping( value="/home" ,method = RequestMethod.GET)
	String home(ModelMap modal) {
		
		modal.addAttribute("title","Home Page");
		return "tms/homePage";
	
	}
}
