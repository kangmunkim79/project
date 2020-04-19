package com.shch.demo.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shch.demo.menu.dto.Menu;
import com.shch.demo.role.dto.Role;
import com.shch.demo.role.mapper.RoleMapper;

@Service
public class RoleService {
	
	@Autowired
	RoleMapper roleMapper;
	
	public List<Role> roleList() {
		return roleMapper.roleList();
	}
	
	public void mergeRole(List<Role> updateList) {
		for(Role role:updateList) {
			roleMapper.mergeRole(role);
		}
	}
	
	public void deleteRole(List<Role> deleteList) {
		for(Role role:deleteList) {
			roleMapper.deleteRole(role);
		}
	}
	
	public List<Role> roleMenuList(Role data) {
		return roleMapper.roleMenuList(data);
	}
	
	public void mergeRoleMenu(List<Menu> updateList) {
		int delCnt = 0;
		for(Menu menu:updateList) {
			if(delCnt == 0) {
				roleMapper.deleteRoleMenu(menu);
				delCnt++;
			}
			roleMapper.insertRoleMenu(menu);
		}
	}
	
}
