package com.shch.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/logout") 
	public String loout(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if (auth != null) { 
			new SecurityContextLogoutHandler().logout(request, response, auth); 
		} 
		return "redirect:/"; 
	}
}
