package com.shch.demo.menu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shch.demo.menu.dto.Menu;
import com.shch.demo.menu.mapper.MenuMapper;

@Service
public class MenuService {
	
	@Autowired
	MenuMapper menuMapper;
	
	public List<Menu> getMenuList() throws Exception { 
		return menuMapper.getMenuList(); 
	}
	
	public List<Menu> getSubMenuList() throws Exception { 
		return menuMapper.getSubMenuList(); 
	}
	
	public List<Menu> getGridMenuList() throws Exception { 
		return menuMapper.getGridMenuList(); 
	}
	
}
