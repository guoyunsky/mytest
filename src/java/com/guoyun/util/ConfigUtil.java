package com.guoyun.util;


/**
 * Read the config value from the memory,if you use this class,you must use new
 * CrawlConfig(true) to save all the config value into memory
 * 
 * 
 */
public class ConfigUtil {
	static boolean hasConfiged = false;
	static {
		if (!hasConfiged) {
			new CrawlConfig(true);
			hasConfiged=true;
		}

	}

	public static String getValue(String key) {
		return System.getProperty(key);
	}
}
