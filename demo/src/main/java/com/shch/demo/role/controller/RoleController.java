package com.shch.demo.role.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shch.demo.menu.dto.Menu;
import com.shch.demo.role.dto.Role;
import com.shch.demo.role.service.RoleService;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	RoleService roleService;
	
	@RequestMapping(value = "/roleUI", method = RequestMethod.GET)
	public String roleUI(Model model) throws Exception {
		return "role/roleList";
	}
	
	@RequestMapping(value = "/roleList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> roleList() throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		try { 
			List<Role> list = roleService.roleList();
			result.put("roleList", list);
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
	
	@RequestMapping(value = "/mergeRole", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> mergeRole(@RequestBody List<Role> updateList) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			roleService.mergeRole(updateList);
			result.put("status", "Ok");
		} catch (Exception e) { 
			result.put("status", "False"); 
		}
		return result;		
	}
	
	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> deleteRole(@RequestBody List<Role> deleteList) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			roleService.deleteRole(deleteList);
			result.put("status", "Ok");
		} catch (Exception e) { 
			result.put("status", "False"); 
		}
		return result;		
	}
	
	@RequestMapping(value = "/roleMenuList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> roleMenuList(@RequestBody Role data) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			List<Role> list = roleService.roleMenuList(data);
			result.put("menuList", list);
		} catch (Exception e) { 
			result.put("status", "False"); 
		}
		return result;		
	}
	
	@RequestMapping(value = "/mergeRoleMenu", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> mergeRoleMenu(@RequestBody List<Menu> updateList) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			roleService.mergeRoleMenu(updateList);
			result.put("status", "Ok");
		} catch (Exception e) { 
			result.put("status", "False"); 
		}
		return result;		
	}
	
}
