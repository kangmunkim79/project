package com.shch.demo.license.controller;

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

import com.shch.demo.license.service.LicenseService;

@Controller
@RequestMapping(value = "license")
public class LicenseController {

	@Autowired
	LicenseService licenseService;
	
	@RequestMapping(value = "/licenseList", method = RequestMethod.GET)
	public String licenseList(Model model) throws Exception {
		return "license/licenseList";
	}
	
	@RequestMapping(value = "/licServerMgtList", method = RequestMethod.GET)
	public String licServerMgtList(Model model) throws Exception {
		return "license/licServerMgtList";
	}
	
	@RequestMapping(value = "/getGridLicenseList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> getGridLicenseList(@RequestBody Map<String, Object> param) throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		try { 
			List<Map<String, Object>> list = licenseService.getGridLicenseList(param);
			result.put("lList", list);
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
	
	@RequestMapping(value = "/getGridModuleList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> getGridModuleList(@RequestBody Map<String, Object> param) throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			List<Map<String, Object>> list = licenseService.getGridModuleList(param);
			result.put("mList", list);
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
	
	@RequestMapping(value = "/getGridLicUserList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> getGridLicUserList(@RequestBody Map<String, Object> param) throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			List<Map<String, Object>> list = licenseService.getGridLicUserList(param);
			result.put("uList", list);
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
	
	@RequestMapping(value = "/getGridLicServerList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> getGridLicServerList(@RequestBody Map<String, Object> param) throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		try { 
			List<Map<String, Object>> list = licenseService.getGridLicServerList(param);
			result.put("mList", list);
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
}
