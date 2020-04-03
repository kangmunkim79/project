package com.shch.demo.userinfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shch.demo.userinfo.dto.UserInfo;
import com.shch.demo.userinfo.service.UserInfoService;

@Controller
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;
    
    @RequestMapping("/query")
    public @ResponseBody List<UserInfo> query() throws Exception{
        return userInfoService.getUserAll();
    }
	
}