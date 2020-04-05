package com.shch.demo.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class keyGeneratorUtils {

	public static String timeKey(String type) {
		String timeKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"));
		String key = type + timeKey;
		return key;
	}
}
