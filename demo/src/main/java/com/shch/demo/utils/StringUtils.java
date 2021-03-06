package com.shch.demo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;

public class StringUtils {
	
	public static String nvl(String str, String defaultStr) {
        return str == null ? defaultStr : str ;
	}
	
	public static String nvl(Object str, String defaultStr) {
        return str == null ? defaultStr : String.valueOf(str) ;
	}
	
	public static String[] nvl(String[] input) {
		if(input == null) {
			return new String[0];
		}  
		return input;
	}
	
	public static String nvl2(String str, String defaultStr) {
        return str == null ? defaultStr : (str == (null))?defaultStr: (str == "null") ?defaultStr:(str == "(null)") ?defaultStr:str ;
	}
	
	public static String enterToNull(String str) {
		return str == null ? "" : str.replaceAll("\n", "");
	}	
	
	public static String decode (String msg, String type) throws UnsupportedEncodingException {
		return URLDecoder.decode (msg, type);
	}
	
	/**
	 * toString();
	 * @param value
	 * @return
	 */
	public static String toString(int value) {
		try {
			return value+"";
		} catch (Exception e) {
			return "";
		}
	}
	
	public static int toInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static long toLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static String LPad(Object value, int len, String str) {
		String rVal = value.toString();
		int sz = len - value.toString().length();
		if(sz <= 0) {
			rVal = value.toString();
		} else {		
			rVal = str + value.toString();
			for(int i=rVal.length();i<len;i++) {
				rVal = str + rVal;
			}
		}
		return rVal;
	}
	
	public static String RPad(Object value, int len, String str) {
		String rVal = value.toString();
		int sz = len - value.toString().length();
		if(sz <= 0) {
			rVal = value.toString();
		} else {		
			rVal = value.toString() + str;
			for(int i=rVal.length();i<len;i++) {
				rVal = rVal + str;
			}
		}
		return rVal;
	}
	
	public static String cutText(String text, int length, String suffix) {
		StringBuffer sb = new StringBuffer();
		
		if (!text.isEmpty()) {
			if (text.length() > length) {
				sb.append(text.substring(0, length)).append(suffix);
			} else {
				sb.append(text);
			}
		} else {
			sb.append(text);
		}
		return sb.toString();
	}

	//String이 비었거나 null인지 검사
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static String replaceText(String text, String originTxt, String replaceTxt) {
		return text.replaceAll(originTxt, replaceTxt);
	}


	public static String replaceBrTag(String text) {
		return text.replaceAll("\\n", "<br/>");
	}
	
	public static String removeAllTag(String txt) {
        return txt.replaceAll("(?:<!.*?(?:--.*?--\\s*)*.*?>)|(?:<(?:[^>'\"]*|\".*?\"|'.*?')+>)","");
    }
	 
	
	public static boolean isNullCheck(String str) {
		boolean bool = false;
		
		if(str != null && !"".equals(str)) {
			bool = true;
		}
		
		return bool;
	}
	
	/**
	 * 검색 결과 날짜표기 MM/DD 
	 * @param value
	 * @return
	 */
	public static String historyDate(String value) {
		return !(nvl(value, "").length() == 0) && value.length() == 8 ? value = value.substring(4, 6) + "/" + value.substring(6, 8) : "";
	}
	
	
	/**
	 * 검색 결과 날짜표기 YYYY.MM.DD 
	 * @param value
	 * @return
	 */
	public static String historyDate2(String value) {
		return !(nvl(value, "").length() == 0) && value.length() == 8 ? value = value.substring(0, 4) + "." + value.substring(4, 6) + "." + value.substring(6, 8) : "";
	}	
	/**
	 * 콤마 추가
	 * @param data
	 * @return
	 */
	public static String addComma(long data) {
		
		return new DecimalFormat("#,###").format(data);
	}

}
