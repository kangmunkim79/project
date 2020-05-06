package com.shch.demo.userinfo.mapper;

import java.util.List;

import com.shch.demo.role.dto.Role;
import com.shch.demo.userinfo.dto.UserInfo;
import com.shch.demo.userinfo.dto.UserParam;

public interface UserInfoMapper {

	public UserInfo readUser(String username);
	
	public List<String> readAuthority(String username);
	
	public List<UserInfo> getUserList(); 
	
	public UserInfo getGridUserInfo(UserParam userInfo);
	
	public UserInfo getUserInfo(String username); 
	
	public List<Role> userRoleList(UserParam userInfo);
	
	public void mergeUserInfo(UserParam userInfo);
	
	public void insertUser(UserParam userInfo); 
	
	public void updateUser(UserParam userInfo); 
	
	public void deleteUser(String username);
	
	public void userUpdateBatch(UserInfo param);
	
}