package com.shch.demo.userinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shch.demo.auth.dto.Auth;
import com.shch.demo.menu.dto.Menu;
import com.shch.demo.role.dto.Role;
import com.shch.demo.userinfo.dto.UserInfo;
import com.shch.demo.userinfo.dto.UserParam;
import com.shch.demo.userinfo.mapper.UserInfoMapper;
import com.shch.demo.utils.StringUtils;
import com.shch.demo.utils.keyGeneratorUtils;

@Service
public class UserInfoService {

	@Autowired
	UserInfoMapper userInfoMapper;
    
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder()
	{
	  return new BCryptPasswordEncoder();
	}
	
	public List<UserInfo> getUserList() throws Exception { 
		return userInfoMapper.getUserList(); 
	} 

	public UserInfo getGridUserInfo(UserParam user) throws Exception { 
		return userInfoMapper.getGridUserInfo(user); 
	} 
	
	public UserInfo getUserInfo(String username) throws Exception { 
		return userInfoMapper.getUserInfo(username); 
	} 

	public List<Role> userRoleList(UserParam userInfo){
		return userInfoMapper.userRoleList(userInfo);
	}
	
	public void mergeUserInfo(UserParam param) throws Exception { 
		String password =param.getPassword();
    	String encryptPassword = passwordEncoder.encode(password);
    	param.setPassword(encryptPassword);
    	if("".equals(StringUtils.nvl(param.getUserno(),""))) {
    		param.setUserno(keyGeneratorUtils.timeKey("USER"));
    	}
		userInfoMapper.mergeUserInfo(param); 
	}
	
	public void insertUser(UserParam userInfo) throws Exception { 
    	String password =userInfo.getPassword();
    	String encryptPassword = passwordEncoder.encode(password);
    	userInfo.setPassword(encryptPassword);
    	userInfo.setUserno(keyGeneratorUtils.timeKey("USER"));
		userInfoMapper.insertUser(userInfo); 
	}
	
	public void updateUser(UserParam userInfo) throws Exception { 
		userInfoMapper.updateUser(userInfo); 
	}

	public void deleteUser(String username) throws Exception { 
		userInfoMapper.deleteUser(username); 
	}
	
}