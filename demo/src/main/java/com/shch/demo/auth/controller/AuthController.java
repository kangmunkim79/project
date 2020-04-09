package com.shch.demo.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shch.demo.auth.service.AuthService;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	AuthService authService;
}
