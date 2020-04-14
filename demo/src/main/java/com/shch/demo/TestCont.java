package com.shch.demo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class TestCont {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String fileName = "D:/PLM_LM/DSLS/R_20200227_110000.log";
		String  year = "2020";
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "euc-kr"));
		String s = null;
		List<Map<String, Object>> mapLicList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> mapLicUserList = new ArrayList<Map<String,Object>>();
		
		String licserver = "4084@10.32.150.101";
		String modulenm = "";
		String maxcredit = "";
		String usage = "";
		String userchk = "N";
		while ((s = in.readLine()) != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
	    	if(s.length() > 0){
	    		if(s.indexOf("maxReleaseNumber: ") > -1) {
	    			modulenm = s.substring(0,s.indexOf("maxReleaseNumber: ")).trim();
	    			usage = s.substring(s.indexOf("inuse:")+6,s.lastIndexOf("customerId:")).trim();
	    			maxcredit = s.substring(s.lastIndexOf("count:")+6,s.lastIndexOf("inuse:")).trim();
	    			map.put("licserver", licserver); 
					map.put("modulenm", modulenm);
					map.put("maxcredit", maxcredit); 
					map.put("usage", usage);
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
					mapLicUserList.add(map2);
	    		}
	    	}
	    }
		in.close();
		
		
		
		
		for(int i=0;i<mapLicList.size();i++) { System.out.println("licserver: " +
		mapLicList.get(i).get("licserver") + " ,modulenm: " +
		mapLicList.get(i).get("modulenm") + " ,maxcredit: " +
		mapLicList.get(i).get("maxcredit") + " ,usage: " +
		mapLicList.get(i).get("usage")); }
		
		
		
		System.out.println("-------------------------------------------------------");
		System.out.println("-------------------------------------------------------");
		System.out.println("-------------------------------------------------------");
		
		
		for(int i=0;i<mapLicUserList.size();i++) { System.out.println("licserver: " +
		mapLicUserList.get(i).get("licserver") + " ,modulenm: " +
		mapLicUserList.get(i).get("modulenm") + " ,id: " +
		mapLicUserList.get(i).get("id") + " ,logintime: " +
		mapLicUserList.get(i).get("logintime")); }
		
		
		
		
	}

}
/*
		
		String fileName = "D:/PLM_LM/LM_Tools/2020_04_14_07_00.log";
		String  year = "2020";
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
	    			maxcredit = s.substring(s.indexOf("(Total of ")+10,s.indexOf(" issued;")-8);
	    			usage = s.substring(s.indexOf(" Total of ")+10,s.indexOf(" in use)")-8).trim();
	    			map.put("licserver", licserver); 
					map.put("modulenm", modulenm);
					map.put("maxcredit", maxcredit); 
					map.put("usage", usage);
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
						mapLicUserList.add(map2);
	    			}
	    		}
	    	}
	    }
		in.close();
		String fileName = "D:/PLM_LM/ANSA/WIN32/2020_04_13_20_00.log";
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
	    			maxcredit = chk.substring(0,chk.indexOf(" |")).trim();
	    			usage = s.substring(s.lastIndexOf("|")+1).trim();
	    			map.put("licserver", licserver); 
					map.put("modulenm", modulenm);
					map.put("maxcredit", maxcredit); 
					map.put("usage", usage);
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
						mapLicUserList.add(map2);
	    			}	    			
	    		}
	    		
	    	}
	    	
	    }
		in.close();
		
		
		for(int i=0;i<mapLicList.size();i++) { System.out.println("licserver: " +
		mapLicList.get(i).get("licserver") + " ,modulenm: " +
		mapLicList.get(i).get("modulenm") + " ,maxcredit: " +
		mapLicList.get(i).get("maxcredit") + " ,usage: " +
		mapLicList.get(i).get("usage")); }
		
		
		
		for(int i=0;i<mapLicUserList.size();i++) { System.out.println("licserver: " +
		mapLicUserList.get(i).get("licserver") + " ,modulenm: " +
		mapLicUserList.get(i).get("modulenm") + " ,id: " +
		mapLicUserList.get(i).get("id") + " ,logintime: " +
		mapLicUserList.get(i).get("logintime")); }
		
		// TODO Auto-generated method stub
		String fileName = "D:/PLM_LM/RLM/2020_04_13_18_00.log";
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s = null;
		List<Map<String, Object>> mapLicList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> mapLicUserList = new ArrayList<Map<String,Object>>();
		Calendar cal = Calendar.getInstance();
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
		    			maxcredit = s.substring(s.indexOf("count:")+6,s.indexOf(",")).trim();	
		    			usage = s.substring(s.indexOf("inuse:")+6,s.indexOf("exp:")).trim();
						map.put("licserver", licserver); 
						map.put("modulenm", modulenm);
						map.put("maxcredit", maxcredit); 
						map.put("usage", usage);
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
		    			String year = String.valueOf(cal.get(Calendar.YEAR));
		    			String logintime = year + s.substring(s.indexOf("at ")+3,s.indexOf("at ")+14).replaceAll("/", "").replaceAll(" ", "").replaceAll(":", "");
		    			map2.put("id", id); 
		    			map2.put("licserver", licserver); 
						map2.put("modulenm", modulenm);	
						map2.put("logintime", logintime);
						mapLicUserList.add(map2);
	    			}
	    		}
	    		
	    	}
	    	
	    }
		in.close();
		
		for(int i=0;i<mapLicList.size();i++) { System.out.println("licserver: " +
		mapLicList.get(i).get("licserver") + " ,modulenm: " +
		mapLicList.get(i).get("modulenm") + " ,maxcredit: " +
		mapLicList.get(i).get("maxcredit") + " ,usage: " +
		mapLicList.get(i).get("usage")); }
		
		for(int i=0;i<mapLicUserList.size();i++) {
			System.out.println("licserver: " + mapLicUserList.get(i).get("licserver") + 
					           " ,modulenm: " + mapLicUserList.get(i).get("modulenm") +
					           " ,id: " + mapLicUserList.get(i).get("id") +
					           " ,logintime: " + mapLicUserList.get(i).get("logintime"));
		}
*/