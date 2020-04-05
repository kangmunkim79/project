package com.shch.demo.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Session {

	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	UserDetails userDetails = (UserDetails)principal; 
	String username = ((UserDetails) principal).getUsername();
	
	private String sid = username;

	public String getSid() {
		return sid;
	}

}
