package com.shch.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TestCont {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        final String DATE_PATTERN = "yyyy_MM_dd_HH";
        String inputStartDate = "2017_02_28_00";
        String inputEndDate = "2017_03_05_11";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        Date startDate = sdf.parse(inputStartDate);
        Date endDate = sdf.parse(inputEndDate);
        ArrayList<String> dates = new ArrayList<String>();
        System.out.println(startDate + " ::: " + endDate);
        Date currentDate = startDate;
        while (currentDate.compareTo(endDate) <= 0) {
            dates.add(sdf.format(currentDate));
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.HOUR_OF_DAY, 1);
            currentDate = c.getTime();
        }
        for (String date : dates) {
            System.out.println(date + "_00.log");
        }

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