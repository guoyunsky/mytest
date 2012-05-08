package com.guoyun.util;

import java.util.Collection;
import java.util.List;

public class MyStringUtil {
	public static String Collection2String(Collection<Object> collection) {
		if(collection == null || collection.size() == 0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		int i = 1;
		int size = collection.size();
		for(Object obj : collection) {
			sb.append(obj.toString());
			if(i<size) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	public static String Collection2Html(Collection<Object> 
		collection,int lengthSplit,String splitStr) {
		if(collection == null || collection.size() == 0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		int i = 1;
		int length = 0;
		String str = null;
		int size = collection.size();
		for(Object obj : collection) {
			str = obj.toString();
			length += (str.length()+1);
			if(length >= lengthSplit) {
				sb.append(splitStr);
				length = 0;
			}
			
			sb.append(str);
			if(i<size) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	public static String Collection2Html(Collection<String> 
		collection,int lengthIgnore) {
		if(collection == null || collection.size() == 0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		int i = 1;
		int length = 0;
		String str = null;
		int size = collection.size();
		for(Object obj : collection) {
			str = obj.toString();
			length += (str.length()+1);
			if(length >= lengthIgnore) {
				return sb.toString();
			}
			
			sb.append(str);
			if(i<size) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
