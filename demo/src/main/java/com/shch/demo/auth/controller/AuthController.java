package com.shch.demo.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shch.demo.auth.dto.Auth;
import com.shch.demo.auth.service.AuthService;

@Controller
@RequestMapping(value = "auth")
public class AuthController {

	@Autowired
	AuthService authService;
	
	@RequestMapping(value = "/mergeRoleAuth", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> mergeRoleAuth(@RequestBody List<Auth> updateList) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			authService.mergeRoleAuth(updateList);
			result.put("status", "Ok");
		} catch (Exception e) { 
			result.put("status", "False"); 
		}
		return result;		
	}
}
