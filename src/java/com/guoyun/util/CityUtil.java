package com.guoyun.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.guoyun.entity.CityBelong;


public class CityUtil {
	public static final Map<String, CityBelong> cityBelongs =
		new HashMap<String,CityBelong>();
	
	
	static{
		String filePath = Thread.currentThread().
			getContextClassLoader().getResource("city.txt").getFile();
		String str = null;
		String[] tmp = null;
		CityBelong city = null;
		
		Iterator<String> iter = MyFileUtil.getFileToIterator(
				new File(filePath));
		while(iter.hasNext()){
			str = iter.next();
			tmp = str.split(",");
			if(tmp != null && tmp.length==3){
				city = new CityBelong(tmp[0],tmp[1],tmp[2]);
				cityBelongs.put(tmp[0], city);
			}
		}
	}
	
	public static CityBelong getCityBelong(String city) {
		CityBelong cb = cityBelongs.get(city);
		if(cb == null) {
			cb = new CityBelong();
		}
		
		return cb;
	}
	
	public static void main(String[] args){
		
	}
	
}
