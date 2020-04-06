package com.shch.demo.menu.mapper;

import java.util.List;

import com.shch.demo.menu.dto.Menu;

public interface MenuMapper {

	public List<Menu> getMenuList();
	
	public List<Menu> getSubMenuList();

}
