package com.shch.demo.license.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shch.demo.license.service.LicenseService;
import com.shch.demo.security.Session;
import com.shch.demo.utils.keyGeneratorUtils;

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
	
	@RequestMapping(value = "/getlicchartList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> getlicchartList(@RequestBody Map<String, Object> param) throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			List<Map<String, Object>> list = licenseService.getlicchartList(param);
			result.put("gList", list);
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}	
	
	@RequestMapping(value = "/getGridLicServerList", method = RequestMethod.GET) 
	public @ResponseBody Map<String, Object> getGridLicServerList() throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		try { 
			List<Map<String, Object>> list = licenseService.getGridLicServerList();
			result.put("mList", list);
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
	
	@RequestMapping(value = "/saveLicList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> saveLicList(@RequestBody List<Map<String, Object>> saveList, Session user) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			licenseService.saveLicList(saveList, user);
			result.put("status", "Ok");
		} catch (Exception e) { 
			result.put("status", "False"); 
		}
		return result;		
	}
	
	@RequestMapping(value = "/rawDataDown", method = RequestMethod.POST) 
	@ResponseBody
	public void rawDataDown(@RequestBody Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try { 
			String url = "D:/excel/";
			String filenm = keyGeneratorUtils.timeKey("RawData_") + ".xls";
			String fileUrlName = url + filenm;		
			File file = new File(url);
	        if(!file.exists()){
	            file.mkdirs();
	        }
			HSSFWorkbook workbook = licenseService.rawDataDown(param);	        
			File xlsFile = new File(fileUrlName);
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            workbook.write(fileOut);
            out.println(fileUrlName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }	
	}
	
	@RequestMapping(value = "/reportDown", method = RequestMethod.POST) 
	@ResponseBody
	public void reportDown(@RequestBody Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
        try {
        	String url = "D:/excel/";
    		String filenm = keyGeneratorUtils.timeKey("ReportData_") + ".xls";
    		String fileUrlName = url + filenm;		
    		File file = new File(url);
            if(!file.exists()){
                file.mkdirs();
            }
            HSSFWorkbook workbook = licenseService.reportDown(param);
            File xlsFile = new File(fileUrlName);
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            workbook.write(fileOut);
            out.println(fileUrlName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}	
	
	@RequestMapping(value = "/detailPop", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> detailPop(@RequestBody Map<String, Object> param) throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			Map<String, Object> detail = licenseService.selectDetail(param);
			result.put("detail", detail);
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
	
}
