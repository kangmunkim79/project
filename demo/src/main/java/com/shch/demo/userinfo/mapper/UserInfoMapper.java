package com.shch.demo.userinfo.mapper;

import java.util.List;

import com.shch.demo.userinfo.dto.UserInfo;

public interface UserInfoMapper {

	public UserInfo readUser(String username);
	
	public List<String> readAuthority(String username);
	
	public List<UserInfo> getUserList(); 
	
	public UserInfo getUserInfo(String username); 
	
	public void insertUser(UserInfo userInfo); 
	
	public void updateUser(UserInfo userInfo); 
	
	public void deleteUser(String username);
	
}