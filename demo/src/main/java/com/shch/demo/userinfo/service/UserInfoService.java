package com.shch.demo.userinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shch.demo.userinfo.dto.UserInfo;
import com.shch.demo.userinfo.mapper.UserInfoMapper;

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

	public UserInfo getUserInfo(String username) throws Exception { 
		return userInfoMapper.getUserInfo(username); 
	} 

	public void insertUser(UserInfo userInfo) throws Exception { 
    	String password =userInfo.getPassword();
    	String encryptPassword = passwordEncoder.encode(password);
    	userInfo.setPassword(encryptPassword);
		userInfoMapper.insertUser(userInfo); 
	}
	
	public void updateUser(UserInfo userInfo) throws Exception { 
		userInfoMapper.updateUser(userInfo); 
	}

	public void deleteUser(String username) throws Exception { 
		userInfoMapper.deleteUser(username); 
	}

}