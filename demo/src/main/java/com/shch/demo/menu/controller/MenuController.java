package com.shch.demo.menu.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shch.demo.menu.service.MenuService;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

	@Autowired
	MenuService menuService;

	@RequestMapping(value = "/getMenuList", method = RequestMethod.POST) 
	public Map<String, Object> getMenuList() throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		try { 
			result.put("menuList", menuService.getMenuList()); 
			result.put("subMenuList", menuService.getSubMenuList()); 
			result.put("status", "OK"); 
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}

}
