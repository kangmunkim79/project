package com.shch.demo.license.mapper;

import java.util.List;
import java.util.Map;

public interface LicenseMapper {

	public List<Map<String, Object>> getGridLicenseList(Map<String, Object> param);
	
	public List<Map<String, Object>> getGridModuleList(Map<String, Object> param);
	
	public List<Map<String, Object>> getGridLicUserList(Map<String, Object> param);
	
	public List<Map<String, Object>> getGridLicServerList();
	
	public List<Map<String, Object>> getlicchartList(Map<String, Object> param);
	
	public List<Map<String, Object>> getLogFileNameUrlList();
	
	public void saveLic(Map<String, Object> param);
	
	public void insertLicUserLog(Map<String, Object> param);
	
	public void insertLicTotalAmtLog(Map<String, Object> param);
	
}
