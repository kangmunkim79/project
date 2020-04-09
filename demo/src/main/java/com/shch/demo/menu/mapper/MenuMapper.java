package com.shch.demo.menu.mapper;

import java.util.List;

import com.shch.demo.menu.dto.Menu;

public interface MenuMapper {

	public List<Menu> getMenuList();
	
	public List<Menu> getSubMenuList();
	
	public List<Menu> getGridMenuList();
	
	public void deleteMenu(Menu param);
	
	public void saveMenu(Menu param);

}
