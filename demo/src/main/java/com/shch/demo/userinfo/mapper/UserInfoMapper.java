package com.shch.demo.userinfo.mapper;

import java.util.List;

import com.shch.demo.userinfo.dto.UserInfo;

public interface UserInfoMapper {

	public UserInfo readUser(String username);
	
	public List<String> readAuthority(String username);
	
	public List<UserInfo> getUserAll();
	
}