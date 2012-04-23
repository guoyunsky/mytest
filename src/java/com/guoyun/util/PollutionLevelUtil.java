package com.guoyun.util;

import java.util.HashMap;
import java.util.Map;

public class PollutionLevelUtil {
	public static final Map<String, Integer> str2IntMap = 
		new HashMap<String, Integer>();
	
	static{
		str2IntMap.put("I",1 );
		str2IntMap.put("II",2 );
		str2IntMap.put("III",3 );
		str2IntMap.put("III1",4 );
		str2IntMap.put("III2",5 );
		str2IntMap.put("III3",6 );
		str2IntMap.put("V", 7);
		str2IntMap.put("V1", 7);
	}
	
	public static int parsePollutionLevel(String level) {
		Integer levelInt = str2IntMap.get(level);
		if(levelInt == null) {
			levelInt = 0;
		}
		return levelInt;
	}
	
	public static int parsePollutionIndex(String index) {
		int indexInt = 0;
		try {
			indexInt = Integer.parseInt(index);
		} catch (NumberFormatException e) {
			indexInt = 0;
		}
		return indexInt;
	}
}
