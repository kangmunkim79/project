package com.shch.demo.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shch.demo.auth.service.AuthService;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	AuthService authService;
	
	@RequestMapping(value = "/authList", method = RequestMethod.GET)
	public String authList(Model model) throws Exception {
		return "auth/authList";
	}
	
}
