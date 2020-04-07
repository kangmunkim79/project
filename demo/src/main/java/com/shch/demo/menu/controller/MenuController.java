package com.shch.demo.menu.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shch.demo.menu.service.MenuService;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

	@Autowired
	MenuService menuService;

	@RequestMapping(value = "/menuList", method = RequestMethod.GET)
	public String getBoardList(Model model) throws Exception {
		return "menu/menuList";
	}
	
	@RequestMapping(value = "/getMenuList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> getMenuList() throws Exception { 
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

	@RequestMapping(value = "/getGridMenuList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> getGridMenuList() throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		try { 
			result.put("mList", menuService.getGridMenuList());  
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
}
