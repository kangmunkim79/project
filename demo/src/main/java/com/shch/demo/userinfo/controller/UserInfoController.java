package com.shch.demo.userinfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shch.demo.auth.dto.Auth;
import com.shch.demo.userinfo.dto.UserInfo;
import com.shch.demo.userinfo.dto.UserParam;
import com.shch.demo.userinfo.service.UserInfoService;

@Controller
@RequestMapping(value = "/userInfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

	@RequestMapping(value = "/getGridUserList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> getGridUserList() throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		try { 
			result.put("uList", userInfoService.getUserList());
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
    
	@RequestMapping(value = "/getGridUserInfo", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> getGridUserInfo(@RequestBody UserParam data) throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		try { 
			result.put("uInfo", userInfoService.getGridUserInfo(data));
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
	
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET) 
    public String getUserList(Model model) throws Exception {  
    	return "userinfo/userInfoList"; 
    } 
    
	@RequestMapping(value = "/userRoleList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> userRoleList(@RequestBody UserParam data) throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		try { 
			result.put("rList", userInfoService.userRoleList(data));
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
    
    @RequestMapping(value = "/insertUser", method = RequestMethod.POST) 
    public String insertUser(@ModelAttribute("userInfo") UserParam userInfo , RedirectAttributes rttr) throws Exception {
    	userInfoService.insertUser(userInfo); 
    	return "redirect:/userInfo/getUserList"; 
    }
    
	@RequestMapping(value = "/saveUserInfo", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> saveUserInfo(@RequestBody UserParam data) throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		try { 
			userInfoService.mergeUserInfo(data);
			result.put("uInfo", userInfoService.getGridUserInfo(data));
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
    
    @RequestMapping(value = "/userInfoUpdate") 
    public String userInfoUpdate(Model model) throws Exception { 
		model.addAttribute("mode", "");
		model.addAttribute("userInfo", new UserInfo());
    	return "userinfo/userInfoUpdate"; 
    }
    	
}