package com.guoyun.dao.impl;

import org.junit.Test;

import com.guoyun.entity.DailyAirQuality;

public class TestDailyAirQualityDAOImpl {
	@Test
	public void testSaveDatas() {
		DailyAirQuality daq = new DailyAirQuality("test", "可吸入颗粒物",
				"II", "58", "良","2012-04-23");
		System.out.println(
				new DailyAirQualityDAOImpl().saveData(daq));
		
	}
	
	
}
