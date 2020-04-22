package com.shch.demo.license.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shch.demo.license.mapper.LicenseMapper;
import com.shch.demo.security.Session;
import com.shch.demo.utils.StringUtils;
import com.shch.demo.utils.keyGeneratorUtils;

@Service
public class LicenseService {

	@Autowired
	LicenseMapper licenseMapper;
	
	public List<Map<String, Object>> getGridLicenseList(Map<String, Object> param){
		return licenseMapper.getGridLicenseList(param);
	}
	
	public List<Map<String, Object>> getGridModuleList(Map<String, Object> param){
		return licenseMapper.getGridModuleList(param);
	}
	
	public List<Map<String, Object>> getGridLicUserList(Map<String, Object> param){
		return licenseMapper.getGridLicUserList(param);
	}
	
	public List<Map<String, Object>> getlicchartList(Map<String, Object> param){
		return licenseMapper.getlicchartList(param);
	}
	
	public List<Map<String, Object>> getGridLicServerList(){
		return licenseMapper.getGridLicServerList();
	}
	
	public List<Map<String, Object>> getExpirLicList(){ 
		return licenseMapper.selectExpirLicList();
	}
	
	public List<Map<String, Object>> getExpirModuleList(Map<String, Object> param){
		return licenseMapper.selectExpirModuleList(param);
	}
	
	public void saveLicList(List<Map<String, Object>> saveList, Session user) {
		for(Map<String, Object> data: saveList) {
			String oid = StringUtils.nvl(data.get("oid"),"");
			data.put("sid", user.getSid());
			if("".equals(oid)) {
				oid = keyGeneratorUtils.timeKey("LIC");
				data.put("oid", oid);
			}
			licenseMapper.saveLic(data);
		} 
	}

	public void miglogFileReadWrite() throws Exception  {
		// TODO Auto-generated method stub
        final String DATE_PATTERN = "yyyy_MM_dd_HH";
        String inputStartDate = "2018_03_25_00";
        String inputEndDate = "2020_04_17_23";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        Date startDate = sdf.parse(inputStartDate);
        Date endDate = sdf.parse(inputEndDate);
        ArrayList<String> dates = new ArrayList<String>();

        Date currentDate = startDate;
        while (currentDate.compareTo(endDate) <= 0) {
            dates.add(sdf.format(currentDate));
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.HOUR_OF_DAY, 1);
            currentDate = c.getTime();
        }
        for (String date : dates) {
            String fname = date + "_00.log";
            String timename = date.replaceAll("_", "") + "0000";
            List<Map<String, Object>> logFileNameUrlList = licenseMapper.getLogFileNameUrlList();
    		for(Map<String, Object> map : logFileNameUrlList) {
    			String fileurl = StringUtils.nvl(map.get("fileurl"), "");
    			String fileName = fileurl + fname;
    			String functionurl = StringUtils.nvl(map.get("functionurl"), "");    			
    			
    			if("LM_Tools".equals(functionurl)) {
    				try {
    					readFileLM(fileName,timename);
    				}catch (Exception e) {
    					// TODO: handle exception
    				}
    			} else if("LMX_Tools".equals(functionurl)) {
    				try {
    					readFileLMX(fileName,timename);
    				}catch (Exception e) {
    					// TODO: handle exception
    				}
    			} else if("RLM".equals(functionurl)) {
    				try {
    					readFileRLM(fileName,timename);
    				}catch (Exception e) {
    					// TODO: handle exception
    				}
    			} else if("ANSA".equals(functionurl)) {
    				try {
    					readFileANSA(fileName,timename);
    				}catch (Exception e) {
    					// TODO: handle exception
    				}
    			/*} else if("DSLS".equals(functionurl)) {
    				try {
    					readFileGPDM(fileName,timename);
    				}catch (Exception e) {
    					// TODO: handle exception
    				}
    			} else if("smd_lm".equals(functionurl)) {
    				try {
    					readFileSMD(fileName,timename);
    				}catch (Exception e) {
    					// TODO: handle exception
    				}*/
    			}
    		}
    		Map<String, Object> expMap = new HashMap<String, Object>();
    		expMap.put("timename", timename);
    		licenseMapper.insertLicExpire(expMap);
        }
	}	
	
	public void logFileReadWrite() throws Exception  {
		List<Map<String, Object>> logFileNameUrlList = licenseMapper.getLogFileNameUrlList();
		String timename = StringUtils.nvl(logFileNameUrlList.get(0).get("timename"), "");;
		for(Map<String, Object> map : logFileNameUrlList) {
			String filename = StringUtils.nvl(map.get("filename"), "");
			String functionurl = StringUtils.nvl(map.get("functionurl"), "");
			if("LM_Tools".equals(functionurl)) {
				try {
					readFileLM(filename,timename);
				}catch (Exception e) {
					// TODO: handle exception
				}
			} else if("LMX_Tools".equals(functionurl)) {
				try {
					readFileLMX(filename,timename);
				}catch (Exception e) {
					// TODO: handle exception
				}
			} else if("RLM".equals(functionurl)) {
				try {
					readFileRLM(filename,timename);
				}catch (Exception e) {
					// TODO: handle exception
				}
			} else if("ANSA".equals(functionurl)) {
				try {
					readFileANSA(filename,timename);
				}catch (Exception e) {
					// TODO: handle exception
				}
			} else if("DSLS".equals(functionurl)) {
				try {
					readFileGPDM(filename,timename);
				}catch (Exception e) {
					// TODO: handle exception
				}
			} else if("smd_lm".equals(functionurl)) {
				try {
					readFileSMD(filename,timename);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		Map<String, Object> expMap = new HashMap<String, Object>();
		expMap.put("timename", timename);
		licenseMapper.insertLicExpire(expMap);
	}
	
	public void readFileGPDM(String fileName, String timeName) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "euc-kr"));
		String s = null;
		List<Map<String, Object>> mapLicList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> mapLicUserList = new ArrayList<Map<String,Object>>();
		
		String licserver = "4084@10.32.150.101";
		String modulenm = "";
		String maxcredit = "";
		String usage = "";

		while ((s = in.readLine()) != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
	    	if(s.length() > 0){
	    		if(s.indexOf("maxReleaseNumber: ") > -1) {
	    			modulenm = s.substring(0,s.indexOf("maxReleaseNumber: ")).trim();
	    			usage = s.substring(s.indexOf("inuse:")+6,s.lastIndexOf("customerId:")).replaceAll("[^0-9]", "").trim();
	    			maxcredit = s.substring(s.lastIndexOf("count:")+6,s.lastIndexOf("inuse:")).replaceAll("unlimited","9999999").replaceAll("[^0-9]", "").trim();
	    			map.put("licserver", licserver); 
					map.put("modulenm", modulenm);
					map.put("maxcredit", maxcredit); 
					map.put("usage", usage);
					map.put("logedtime", timeName);
					mapLicList.add(map);
	    		} else if(s.indexOf("internal Id:") > -1) {
	    			String id = s.substring(s.indexOf("by user:")+8, s.indexOf("on host:")).trim();
	    			String logintime = s.substring(s.indexOf("last used at:")+13, s.indexOf("by user:")).trim();
	    			logintime = logintime.replace(". ", "/");	    			
	    			DateFormat dateParser = new SimpleDateFormat("yyyy/MM/dd a KK:mm:ss");	    			 
	    			logintime = String.valueOf(dateParser.parse(logintime));
	    			SimpleDateFormat recvSimpleFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);	    			 
	    	        SimpleDateFormat tranSimpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	    	        Date data = recvSimpleFormat.parse(logintime);
	    	        logintime = tranSimpleFormat.format(data);
	    	        logintime = logintime.replace("-", "/");
	    			java.util.Date date = new Date(logintime); 
    				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); // yyyy-MM-dd HH:mm:ss 
    				String format = formatter.format(date);
    				
    				map2.put("id", id);
	    			map2.put("licserver", licserver); 
					map2.put("modulenm", modulenm);	
					map2.put("logintime", format);
					map2.put("logedtime", timeName);
					mapLicUserList.add(map2);
	    		}
	    	}
	    }
		in.close();
		insertUserLog(mapLicUserList, mapLicList);
	}	
	
	public void readFileSMD(String fileName, String timeName) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s = null;
		List<Map<String, Object>> mapLicList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> mapLicUserList = new ArrayList<Map<String,Object>>();
		
		String licserver = "";
		String modulenm = "";
		String maxcredit = "";
		String usage = "";
		String modulechk = "N";
		String userchk = "N";
		while ((s = in.readLine()) != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
	    	if(s.length() > 0){
	    		if(s.indexOf(" @ ") > -1) {
	    			licserver = s.substring(s.indexOf("(port")+5,s.indexOf(")")) + "@" + s.substring(s.indexOf("@ ")+2,s.indexOf("(port"));
	    		}
	    		if(s.indexOf("product key  :") > -1) {
	    			modulechk = "Y";
	    			userchk = "N";
	    		} else if(s.indexOf("min ") > -1) {
	    			userchk = "Y";
	    			modulechk = "N";
	    		}	    			
	    		if(modulechk == "Y") {
	    			if(s.indexOf("product key  :") == -1) {
		    			modulenm = s.substring(0, s.indexOf(":")).trim();
		    			usage = s.substring(s.indexOf(":")+1,s.lastIndexOf("/")).replaceAll("[^0-9]", "").trim();
		    			maxcredit = s.substring(s.lastIndexOf("/")+1,s.lastIndexOf("(")).replaceAll("unlimited","9999999").replaceAll("[^0-9]", "").trim();
		    			map.put("licserver", licserver); 
						map.put("modulenm", modulenm);
						map.put("maxcredit", maxcredit); 
						map.put("usage", usage);
						map.put("logedtime", timeName);
						mapLicList.add(map);
	    			}
	    		}

	    		if(userchk == "Y") {
	    			String id = s.substring(0, s.indexOf("@")).trim();
	    			modulenm = s.trim().substring(s.lastIndexOf("min")+3).trim();
    				map2.put("id", id);
	    			map2.put("licserver", licserver); 
					map2.put("modulenm", modulenm);	
					map2.put("logintime", timeName);
					map2.put("logedtime", timeName);
					mapLicUserList.add(map2);
	    		}
	    	}
	    }
		in.close();
		insertUserLog(mapLicUserList, mapLicList);
	}	
	
	public void readFileLMX(String fileName, String timeName) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s = null;
		List<Map<String, Object>> mapLicList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> mapLicUserList = new ArrayList<Map<String,Object>>();
		
		String licserver = "";
		String modulenm = "";
		String maxcredit = "";
		String usage = "";
		String id = "";
		while ((s = in.readLine()) != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
	    	if(s.length() > 0){	    			    		
	    		if(s.indexOf("LM-X License Server on") > -1) {
	    			licserver = s.replace("LM-X License Server on ", "").replace(":", "").trim();	    			
	    		} else if(s.indexOf("Feature:") > -1) {
	    			modulenm = s.substring(s.indexOf("Feature: ")+9,s.indexOf(" Version:")).trim();
	    		} else if(s.indexOf("license(s) used by") > -1) {
	    			id = s.substring(s.indexOf("by ")+3, s.indexOf("@")).trim();	    			
	    		} else if(s.indexOf("Login time:") > -1) {
	    			String logintime = s.substring(s.indexOf(":")+1, s.indexOf("   Checkout")).trim().replace("-", "/");
	    			java.util.Date date = new Date(logintime); 
    				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); // yyyy-MM-dd HH:mm:ss 
    				String format = formatter.format(date);	    				
    				map2.put("id", id);
	    			map2.put("licserver", licserver); 
					map2.put("modulenm", modulenm);	
					map2.put("logintime", format);
					map2.put("logedtime", timeName);
					mapLicUserList.add(map2);
	    		} else if(s.indexOf("license(s) used") > -1) {
	    			if(s.indexOf("used by") == -1 && s.indexOf("Checkout time:") == -1) {
		    			usage = s.substring(0,s.indexOf(" of")).replaceAll("[^0-9]", "").trim();
		    			maxcredit = s.substring(s.indexOf("of")+2,s.indexOf("license(s)")).replaceAll("unlimited","9999999").replaceAll("[^0-9]", "").trim();	    			
		    			map.put("licserver", licserver); 
						map.put("modulenm", modulenm);
						map.put("maxcredit", maxcredit); 
						map.put("usage", usage);
						map.put("logedtime", timeName);
						mapLicList.add(map);
	    			}
	    		}
	    	}
	    }
		in.close();
		insertUserLog(mapLicUserList, mapLicList);
	}
	
	public void readFileLM(String fileName, String timeName) throws Exception {
		String  year = timeName.substring(0,4);
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s = null;
		List<Map<String, Object>> mapLicList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> mapLicUserList = new ArrayList<Map<String,Object>>();
		
		String licserver = "";
		String modulenm = "";
		String maxcredit = "";
		String usage = "";
		String chk = "N";
		while ((s = in.readLine()) != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
	    	if(s.length() > 0){
	    		if(s.indexOf("License server status:") > -1) {
	    			licserver = s.replace("License server status: ", "").trim();	    			
	    		} else if(s.indexOf("Users of ") > -1) {
	    			chk = "N";
	    			if(s.indexOf("Error:") > -1 || s.indexOf("(Uncounted, node-locked)") > -1) {
	    				continue;
	    			}
	    			modulenm = s.substring(s.indexOf("Users of ")+9,s.indexOf(":  (")).trim();
	    			maxcredit = s.substring(s.indexOf("(Total of ")+10,s.indexOf(" issued;")-8).replaceAll("unlimited","9999999").replaceAll("[^0-9]", "").trim();
	    			usage = s.substring(s.indexOf(" Total of ")+10,s.indexOf(" in use)")-8).replaceAll("[^0-9]", "").trim();
	    			map.put("licserver", licserver); 
					map.put("modulenm", modulenm);
					map.put("maxcredit", maxcredit); 
					map.put("usage", usage);
					map.put("logedtime", timeName);
					mapLicList.add(map);
	    		} else if(s.indexOf("  floating license") > -1) {
	    			chk = "Y";
	    		}
	    		
	    		if(chk == "Y") {
	    			if(s.indexOf("  floating license") == -1) {
	    				String id = s.substring(2).trim();
	    				id = id.substring(0,id.indexOf(" ")).trim();
	    				String logintime = year + "/" + s.substring(s.lastIndexOf("start")+10).trim();
	    				if(logintime.indexOf(",") > -1) {
	    					logintime = logintime.substring(0, logintime.indexOf(","));
	    				}
	    				java.util.Date date = new Date(logintime); 
	    				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); // yyyy-MM-dd HH:mm:ss 
	    				String format = formatter.format(date);	    				
	    				
		    			map2.put("id", id); 
		    			map2.put("licserver", licserver); 
						map2.put("modulenm", modulenm);	
						map2.put("logintime", format);
						map2.put("logedtime", timeName);
						mapLicUserList.add(map2);
	    			}
	    		}
	    	}
	    }
		in.close();
		insertUserLog(mapLicUserList, mapLicList);
	}
	
	public void readFileANSA(String fileName, String timeName) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s = null;
		List<Map<String, Object>> mapLicList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> mapLicUserList = new ArrayList<Map<String,Object>>();
		String licserver = "";
		String modulenm = "";
		String maxcredit = "";
		String usage = "";
		while ((s = in.readLine()) != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
	    	if(s.length() > 0){
	    		if(s.indexOf("Trying") > -1) {
	    			licserver = s.substring(0,s.indexOf("....")-1).trim().replace("Trying ", "");	    			
	    		} else if(s.indexOf("PRE_POST:") > -1) {
	    			modulenm = s.substring(s.indexOf("PRE_POST:")+9,s.indexOf(" |")).trim();
	    			String chk = s.substring(s.indexOf("|")+1);
	    			maxcredit = chk.substring(0,chk.indexOf(" |")).replaceAll("unlimited","9999999").replaceAll("[^0-9]", "").trim();
	    			usage = s.substring(s.lastIndexOf("|")+1).replaceAll("[^0-9]", "").trim();
	    			map.put("licserver", licserver); 
					map.put("modulenm", modulenm);
					map.put("maxcredit", Integer.valueOf(maxcredit)/100); 
					map.put("usage", usage);
					map.put("logedtime", timeName);
					mapLicList.add(map);
	    		} else if(s.indexOf("@") > -1) {
	    			if(s.indexOf("USER NAME@HOST") == -1) {
	    				String id = s.substring(0,s.indexOf("@")).trim();
	    				String chk = s.substring(s.indexOf("|")+1);
	    				modulenm = chk.substring(0,chk.indexOf(" |")).trim();
	    				String logintime = s.substring(s.lastIndexOf("|")+1).trim();
	    				
	    				java.util.Date date = new Date(logintime); 
	    				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); // yyyy-MM-dd HH:mm:ss 
	    				String format = formatter.format(date);	    				
	    				
		    			map2.put("id", id); 
		    			map2.put("licserver", licserver); 
						map2.put("modulenm", modulenm);	
						map2.put("logintime", format);
						map2.put("logedtime", timeName);
						mapLicUserList.add(map2);
	    			}	    			
	    		}	    		
	    	}	    	
	    }
		in.close();
		insertUserLog(mapLicUserList, mapLicList);
	}
	
	public void readFileRLM(String fileName, String timeName) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String  year = timeName.substring(0,4);
		String s = null;
		List<Map<String, Object>> mapLicList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> mapLicUserList = new ArrayList<Map<String,Object>>();
		String licserver = "";
		String modulenm = "";
		String maxcredit = "";
		String usage = "";
		String mChk = "N";
		String uChk = "N";
		while ((s = in.readLine()) != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
	    	if(s.length() > 0){
	    		if(s.indexOf("Setting license file path to") > -1) {
	    			uChk = "N";
	    			licserver = s.replace("Setting license file path to ", "").trim();	    			
	    		} else if(s.indexOf("license pool status on") > -1) {
	    			mChk = "Y";	    			
	    		} else if(s.indexOf("--------------------") > -1) {
	    			mChk = "N";
	    		} else if(s.indexOf("license usage status on") > -1) {
	    			uChk = "Y";
	    		}
	    		
	    		if(mChk == "Y") {
		    		if(s.indexOf("count:") > -1) {
		    			maxcredit = s.substring(s.indexOf("count:")+6,s.indexOf(",")).replaceAll("unlimited","9999999").replaceAll("[^0-9]", "").trim();	
		    			usage = s.substring(s.indexOf("inuse:")+6,s.indexOf("exp:")).replaceAll("[^0-9]", "").trim();
						map.put("licserver", licserver); 
						map.put("modulenm", modulenm);
						map.put("maxcredit", maxcredit); 
						map.put("usage", usage);
						map.put("logedtime", timeName);
						mapLicList.add(map);
		    		} else {
		    			if(s.indexOf("obsolete:") == -1 && s.indexOf("UNCOUNTED") == -1) {
		    				modulenm = s.trim().substring(0,s.indexOf(" ")).trim();
		    			}
		    		}
	    		}
	    		if(uChk == "Y") {
	    			if(s.indexOf("license usage status on") == -1) { 
		    			modulenm = s.trim().substring(0,s.indexOf(" ")).trim();
		    			String id = s.trim().substring(s.indexOf(":")+1,s.indexOf("@")-1).trim();
		    			String logintime = year + s.substring(s.indexOf("at ")+3,s.indexOf("at ")+14).replaceAll("/", "").replaceAll(" ", "").replaceAll(":", "")+"00";
		    			map2.put("id", id); 
		    			map2.put("licserver", licserver); 
						map2.put("modulenm", modulenm);	
						map2.put("logintime", logintime);
						map2.put("logedtime", timeName);
						mapLicUserList.add(map2);
	    			}
	    		}
	    	}
	    }
		in.close();
		insertUserLog(mapLicUserList, mapLicList);
	}
	
	public void insertUserLog(List<Map<String, Object>> ulist, List<Map<String, Object>> tlist) {
		for(int i=0;i<ulist.size();i++){
			licenseMapper.insertLicUserLog(ulist.get(i));
		}

		for(int i=0;i<tlist.size();i++){
			licenseMapper.insertLicTotalAmtLog(tlist.get(i));				
		}		
	}
	
	public HSSFWorkbook rawDataDown(Map<String, Object> map) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;
					
		cell = row.createCell(0);
		cell.setCellValue("License Name");
		
		cell = row.createCell(1);
		cell.setCellValue("Module Name");
		
		cell = row.createCell(2);
		cell.setCellValue("License Count");
		
		cell = row.createCell(3);
		cell.setCellValue("Use Count");
		
		cell = row.createCell(4);
		cell.setCellValue("Percent");
		
		cell = row.createCell(5);
		cell.setCellValue("Loged Time");
		
		List<Map<String,Object>> list = licenseMapper.rawDataDownList(map);			
		
		for(int rowIdx=0;rowIdx<list.size();rowIdx++){
			Map<String, Object> param = list.get(rowIdx);
			
			row = sheet.createRow(rowIdx+1);

			cell = row.createCell(0);
			cell.setCellValue(String.valueOf(param.get("licnm")));
			
			cell = row.createCell(1);
			cell.setCellValue(String.valueOf(param.get("modulenm")));
			
			cell = row.createCell(2);
			cell.setCellValue(String.valueOf(param.get("maxcredit")));
			
			cell = row.createCell(3);
			cell.setCellValue(String.valueOf(param.get("usage")));
			
			cell = row.createCell(4);
			cell.setCellValue(String.valueOf(param.get("percentage")));
			
			cell = row.createCell(5);
			cell.setCellValue(String.valueOf(param.get("logedtime")));
			
			sheet.autoSizeColumn(rowIdx,true);
		}
		
		List<Map<String,Object>> sheetList = licenseMapper.sheetCheckDateList(map);
		
		for(int sIdx=0;sIdx<sheetList.size();sIdx++){
			Map<String, Object> param = sheetList.get(sIdx);
			sheet = workbook.createSheet(String.valueOf(param.get("checkdt")));
			
			row = sheet.createRow(0);
			
			cell = row.createCell(0);
			cell.setCellValue("ID");
			
			cell = row.createCell(1);
			cell.setCellValue("NAME");
			
			cell = row.createCell(2);
			cell.setCellValue("DEPT");
			
			cell = row.createCell(3);
			cell.setCellValue("Login Time");
			
			cell = row.createCell(4);
			cell.setCellValue("Loged Time");
			
			map.put("checkdt", String.valueOf(param.get("checkdt")));			
			List<Map<String,Object>> userList = licenseMapper.excelDownUserList(map);
			for(int uIdx=0;uIdx<userList.size();uIdx++){
				Map<String, Object> param2 = userList.get(uIdx);
				row = sheet.createRow(uIdx+1);

				cell = row.createCell(0);
				cell.setCellValue(String.valueOf(param2.get("id")));
				
				cell = row.createCell(1);
				cell.setCellValue(String.valueOf(param2.get("name")));
				
				cell = row.createCell(2);
				cell.setCellValue(String.valueOf(param2.get("deptnm")));
				
				cell = row.createCell(3);
				cell.setCellValue(String.valueOf(param2.get("logintime")));
				
				cell = row.createCell(4);
				cell.setCellValue(String.valueOf(param2.get("logedtime")));
							
			}
			sheet.setColumnWidth(2, 15000);
		}
		return workbook;

	}
	
	public HSSFWorkbook reportDown(Map<String, Object> map) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;
					
		List<Map<String,Object>> list = licenseMapper.reportDownMap(map);
		
		String lic = "";
		String mod = "";
		String pc = "";
		String mc = "";
		String ac = "";
		String per = "";
		String stdate = "";
		String etdate = "";
		
		if(list.size() > 0){
			lic = String.valueOf(list.get(0).get("lic"));   
			mod = String.valueOf(list.get(0).get("mod"));   
			pc = String.valueOf(list.get(0).get("pc"));    
			mc = String.valueOf(list.get(0).get("mc"));    
			ac = String.valueOf(list.get(0).get("ac"));    
			per = String.valueOf(list.get(0).get("per"));   
			stdate = String.valueOf(list.get(0).get("stdate"));
			etdate = String.valueOf(list.get(0).get("etdate"));
		}
		
		sheet.addMergedRegion(new CellRangeAddress(0,3,0,0));
		
		cell = row.createCell(0);
		cell.setCellValue("라이선스 사용정보");
		
		cell = row.createCell(1);
		cell.setCellValue("License Name");
		
		cell = row.createCell(2);
		cell.setCellValue(lic);
		
		cell = row.createCell(3);
		cell.setCellValue("Module Name");
		
		cell = row.createCell(4);
		cell.setCellValue(mod);
		
		row = sheet.createRow(1);
		
		cell = row.createCell(1);
		cell.setCellValue("라이선스 총 개수");
		
		cell = row.createCell(2);
		cell.setCellValue(mc);
		
		cell = row.createCell(3);
		cell.setCellValue("Peak 사용량");
		
		cell = row.createCell(4);
		cell.setCellValue(pc);
		
		row = sheet.createRow(2);
		
		cell = row.createCell(1);
		cell.setCellValue("평균 사용개수");
		
		cell = row.createCell(2);
		cell.setCellValue(ac);
		
		cell = row.createCell(3);
		cell.setCellValue("분석시간");
		
		cell = row.createCell(4);
		cell.setCellValue("08:00 ~ 18:00");
		
		row = sheet.createRow(3);
		
		cell = row.createCell(1);
		cell.setCellValue("평균 백분율");
		
		cell = row.createCell(2);
		cell.setCellValue(per);
		
		cell = row.createCell(3);
		cell.setCellValue("분석기간");
		
		cell = row.createCell(4);
		cell.setCellValue(stdate + " ~ " + etdate);
		
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
			
		List<Map<String,Object>> sheetList = licenseMapper.sheetCheckDateList(map);
		
		for(int sIdx=0;sIdx<sheetList.size();sIdx++){
			Map<String, Object> param = sheetList.get(sIdx);
			sheet = workbook.createSheet(String.valueOf(param.get("checkdt")));
			
			map.put("checkdt", String.valueOf(param.get("checkdt")));			
			List<Map<String,Object>> userList = licenseMapper.reportDownUserList(map);
			
			row = sheet.createRow(0);
			
			sheet.addMergedRegion(new CellRangeAddress(0,0,1,2));
			sheet.addMergedRegion(new CellRangeAddress(3,3,5,6));
			
			cell = row.createCell(0);
			cell.setCellValue("총 라이선스");
			
			cell = row.createCell(1);
			cell.setCellValue("현재사용량");
			
			row = sheet.createRow(1);
			
			cell = row.createCell(0);
			cell.setCellValue(mc);
			
			cell = row.createCell(1);
			cell.setCellValue(userList.size());
			
			row = sheet.createRow(2);
			row = sheet.createRow(3);
			
			cell = row.createCell(0);
			cell.setCellValue("AD_NAME");
						
			cell = row.createCell(1);
			cell.setCellValue("USER_NAME");
			
			cell = row.createCell(2);
			cell.setCellValue("BG_NAME");
			
			cell = row.createCell(3);
			cell.setCellValue("DEPARTMENT_NAME");
			
			cell = row.createCell(4);
			cell.setCellValue("EMPLOYEE_TYPE");
			
			cell = row.createCell(5);
			cell.setCellValue("COMPANY_NAME");
			
			cell = row.createCell(7);
			cell.setCellValue("LICENSE_QUANTITY");
			
			cell = row.createCell(8);
			cell.setCellValue("PERMISSION");
			
			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(4, 5000);
			sheet.setColumnWidth(5, 5000);
			sheet.setColumnWidth(6, 5000);
			sheet.setColumnWidth(7, 5000);
			sheet.setColumnWidth(8, 5000);
			
			
			for(int uIdx=0;uIdx<userList.size();uIdx++){
				Map<String, Object> param2 = userList.get(uIdx);
	
				row = sheet.createRow(uIdx+4);

				cell = row.createCell(0);
				cell.setCellValue(String.valueOf(param2.get("id")));
				
				cell = row.createCell(1);
				cell.setCellValue(String.valueOf(param2.get("name")));
				
				cell = row.createCell(3);
				cell.setCellValue(String.valueOf(param2.get("deptnm")));
				
				cell = row.createCell(5);
				cell.setCellValue(String.valueOf(param2.get("companynm")));			
				
			}
			sheet.setColumnWidth(3, 15000);
		}
		return workbook;

	}
	
	public Map<String, Object> selectDetail(Map<String, Object> param) {
		return licenseMapper.selectDetail(param);
	}
	
	public void mergeExpirDt(List<Map<String, Object>> updateList) {
		for(Map<String, Object> param:updateList) {
			licenseMapper.updateExpirDt(param);
		}
	}
}
