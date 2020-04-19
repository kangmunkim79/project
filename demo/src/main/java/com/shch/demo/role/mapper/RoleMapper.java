package com.shch.demo.role.mapper;

import java.util.List;

import com.shch.demo.menu.dto.Menu;
import com.shch.demo.role.dto.Role;

public interface RoleMapper {
	
	public List<Role> roleList();
	
	public void mergeRole(Role role);
	
	public void deleteRole(Role role);
	
	public List<Role> roleMenuList(Role data);
	
	public void insertRoleMenu(Menu menu);
	
	public void deleteRoleMenu(Menu menu);
	
}
