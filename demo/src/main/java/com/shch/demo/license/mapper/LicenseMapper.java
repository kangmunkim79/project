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
	
	public List<Map<String, Object>> rawDataDownList(Map<String, Object> param);
	
	public List<Map<String, Object>> sheetCheckDateList(Map<String, Object> param);
	
	public List<Map<String, Object>> excelDownUserList(Map<String, Object> param);
	
	public List<Map<String, Object>> reportDownMap(Map<String, Object> param);
	
	public List<Map<String, Object>> reportDownUserList(Map<String, Object> param);
	
	public List<Map<String, Object>> selectExpirLicList();
	
	public List<Map<String, Object>> selectExpirModuleList(Map<String, Object> param);
	
	public Map<String, Object> selectDetail(Map<String, Object> param);
	
	public void saveLic(Map<String, Object> param);
	
	public void insertLicUserLog(Map<String, Object> param);
	
	public void insertLicTotalAmtLog(Map<String, Object> param);
	
	public void insertLicExpire(Map<String, Object> param);
	
	public void updateExpirDt(Map<String, Object> param);
	
	public void deleteLic(Map<String, Object> param);
}
