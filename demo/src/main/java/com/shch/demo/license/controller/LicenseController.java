package com.shch.demo.license.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shch.demo.GlobalPropertySource;
import com.shch.demo.license.service.LicenseService;
import com.shch.demo.menu.dto.Menu;
import com.shch.demo.security.Session;

@Controller
@RequestMapping(value = "license")
public class LicenseController {

	@Autowired
    GlobalPropertySource globalPropertySource;
	
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
	
	@RequestMapping(value = "/licExpirDtMgtList", method = RequestMethod.GET)
	public String licExpirDtMgtList(Model model) throws Exception {
		return "license/licExpirDtMgtList";
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
	
	@RequestMapping(value = "/getExpirLicList", method = RequestMethod.GET) 
	public @ResponseBody Map<String, Object> getExpirLicList() throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		try { 
			List<Map<String, Object>> list = licenseService.getExpirLicList();
			result.put("eList", list);
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
	
	@RequestMapping(value = "/getExpirModuleList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> getExpirModuleList(@RequestBody Map<String, Object> param) throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			List<Map<String, Object>> list = licenseService.getExpirModuleList(param);
			result.put("eList", list);
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
			String url = globalPropertySource.getExfilepath();
    		String fileUrlName = url + "RawData.xls";
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
        	String url = globalPropertySource.getExfilepath();
    		String fileUrlName = url + "ReportData.xls";		
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
	
	@RequestMapping(value = "/mergeExpirDt", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> mergeExpirDt(@RequestBody List<Map<String, Object>> updateList) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			licenseService.mergeExpirDt(updateList);
			result.put("status", "Ok");
		} catch (Exception e) { 
			result.put("status", "False"); 
		}
		return result;		
	}
	
	@RequestMapping(value = "/deleteLicList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> deleteLicList(@RequestBody List<Map<String, Object>> deleteList) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			licenseService.deleteLicList(deleteList);
			result.put("status", "Ok");
		} catch (Exception e) { 
			result.put("status", "False"); 
		}
		return result;		
	}
	
}
