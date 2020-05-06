package com.shch.demo.inf.enportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shch.demo.inf.enportal.mapper.EnportalMapper;
import com.shch.demo.userinfo.dto.UserInfo;

@Service
public class EnportalService {

	@Autowired
	EnportalMapper enportalMapper;
	
	public List<UserInfo> getEnportalUserList() {
		return enportalMapper.getEnportalUserList();
	}
}
