package com.guoyun.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtil {
	public static Date String2Date(String date) throws ParseException{
		SimpleDateFormat formatter = 
			new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(date);
	}
	
	public static Date getTodayDate()  {
		Calendar d = Calendar.getInstance();
		return d.getTime();
	}
	
	public static String getTodayDateStr() {
		return Date2String(getTodayDate());
	}
	
	public static String Date2String(Date date) {
		SimpleDateFormat formatter = 
			new SimpleDateFormat("yyyy-MM-dd");
		
		return formatter.format(date);
	}
	
	public static long calDiffDay(Date from, Date to) {
		long timeFrom = from.getTime();
		long timeTo = to.getTime();
		
		return (timeTo-timeFrom)/(1000*60*60*24);
	}
	
	public static long calDiffToday(Date from) throws ParseException {
		return calDiffDay(from, getTodayDate());
	}
}
