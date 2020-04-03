package com.shch.demo.userinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shch.demo.userinfo.dto.UserInfo;
import com.shch.demo.userinfo.service.UserInfoService;

@Controller
@RequestMapping(value = "/userInfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;
    
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET) 
    public String getUserList(Model model) throws Exception { 
    	model.addAttribute("userList", userInfoService.getUserList()); 
    	return "userinfo/userInfoList"; 
    } 
    
    @RequestMapping(value = "/insertUser", method = RequestMethod.POST) 
    public String insertUser(@ModelAttribute("userInfo") UserInfo userInfo , RedirectAttributes rttr) throws Exception { 
    	userInfoService.insertUser(userInfo); 
    	return "redirect:/userInfo/getUserList"; 
    }
    
    @RequestMapping(value = "/userInfoAdd", method = RequestMethod.GET) 
    public String userInfoAdd(Model model) throws Exception { 
    	model.addAttribute("userInfo", new UserInfo()); 
    	return "userinfo/userInfoAdd"; 
    }
	
}