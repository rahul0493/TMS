package com.quinnox.flm.tms.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author AmareshP
 *
 */
@Controller
public class AppController {

	@RequestMapping("/tms/{page}")
	String partialHandler(@PathVariable("page") final String page) {
		
		return page;
	
	}

}
