package com.shch.demo.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shch.demo.auth.dto.Auth;
import com.shch.demo.auth.mapper.AuthMapper;
import com.shch.demo.utils.StringUtils;

@Service
public class AuthService {

	@Autowired
	AuthMapper authMapper;
	
	public void mergeRoleAuth(List<Auth> updateList) {
		int delCnt = 0;
		for(Auth auth:updateList) {
			if(delCnt == 0) {
				authMapper.deleteRoleAuth(auth);
				delCnt++;
			}
			if("Y".equals(StringUtils.nvl(auth.getUseflag(),""))) {
				authMapper.insertRoleAuth(auth);
			}
		}
	}
}
