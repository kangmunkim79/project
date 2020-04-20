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
	
	public List<Menu> getMenuList(Menu menu) throws Exception { 
		return menuMapper.getMenuList(menu); 
	}
	
	public List<Menu> getSubMenuList(Menu menu) throws Exception { 
		return menuMapper.getSubMenuList(menu); 
	}
	
	public List<Menu> getGridMenuList() throws Exception { 
		return menuMapper.getGridMenuList(); 
	}
	
	public void deleteMenuList(List<Menu> delList) throws Exception {
		for(Menu data: delList) {
			menuMapper.deleteMenu(data);
		} 
	}

	public void saveMenuList(List<Menu> saveList) throws Exception {
		for(Menu data: saveList) {
			menuMapper.saveMenu(data);
		} 
	}
}
