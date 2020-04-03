package com.shch.demo.userinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shch.demo.userinfo.dto.UserInfo;
import com.shch.demo.userinfo.mapper.UserInfoMapper;

@Service
public class UserInfoService {

	@Autowired
	UserInfoMapper userInfoMapper;
    
	public List<UserInfo> getUserAll() throws Exception{
	    return userInfoMapper.getUserAll();
	}
	
}