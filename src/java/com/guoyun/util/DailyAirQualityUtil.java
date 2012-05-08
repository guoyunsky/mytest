package com.guoyun.util;

import static com.guoyun.entity.DailyAirQuality.INDEX_YOU_FROM;
import static com.guoyun.entity.DailyAirQuality.INDEX_YOU_TO;
import static com.guoyun.entity.DailyAirQuality.INDEX_LIANG_FROM;
import static com.guoyun.entity.DailyAirQuality.INDEX_LIANG_TO;
import static com.guoyun.entity.DailyAirQuality.INDEX_QING_DU_FROM;
import static com.guoyun.entity.DailyAirQuality.INDEX_QING_DU_TO;
import static com.guoyun.entity.DailyAirQuality.INDEX_ZHONG_DU_FROM;
import static com.guoyun.entity.DailyAirQuality.INDEX_ZHONG_DU_TO;
import static com.guoyun.entity.DailyAirQuality.INDEX_ZHONG_DU_4_FROM;
import static com.guoyun.entity.DailyAirQuality.INDEX_ZHONG_DU_4_TO;
import static com.guoyun.entity.DailyAirQuality.INDEX_YAN_ZHONG_FROM;
import static com.guoyun.entity.DailyAirQuality.INDEX_YAN_ZHONG_TO;

import java.util.ArrayList;
import java.util.List;

import com.guoyun.entity.DailyAirQuality;

import static com.guoyun.entity.DailyAirQuality.AIR_YOU;
import static com.guoyun.entity.DailyAirQuality.AIR_LIANG;
import static com.guoyun.entity.DailyAirQuality.AIR_QING_DU;
import static com.guoyun.entity.DailyAirQuality.AIR_ZHONG_DU;
import static com.guoyun.entity.DailyAirQuality.AIR_ZHONG_DU_4;
import static com.guoyun.entity.DailyAirQuality.AIR_YAN_ZHONG;


public class DailyAirQualityUtil {
	public static String getAirLevel(double airIndex) {
		String result = AIR_YOU;
		if (airIndex>=INDEX_YOU_FROM && airIndex<=INDEX_YOU_TO) {
			result =  AIR_YOU;
		} else if (airIndex>=INDEX_LIANG_FROM && airIndex<=INDEX_LIANG_TO) {
			result =  AIR_LIANG;
		} else if (airIndex>=INDEX_QING_DU_FROM && airIndex<=INDEX_QING_DU_TO) {
			result =  AIR_QING_DU;
		} else if (airIndex>=INDEX_ZHONG_DU_FROM && airIndex<=INDEX_ZHONG_DU_TO) {
			result =  AIR_ZHONG_DU;
		} else if (airIndex>=INDEX_ZHONG_DU_4_FROM && airIndex<=INDEX_ZHONG_DU_4_TO) {
			result =  AIR_ZHONG_DU_4;
		} else if (airIndex>=INDEX_YAN_ZHONG_FROM && airIndex<=INDEX_YAN_ZHONG_TO) {
			result =  AIR_YAN_ZHONG;
		}
		
		return result;
	}
	
	/**
	 * 输出空气质量
	 * 
	 * @param airIndex
	 * @return
	 */
	public static String getAirLevelHtml(double airIndex){
		return reportHtml(getAirLevel(airIndex));
	}
	
	/**
	 * 输出html
	 * 
	 * @param obj
	 * @return
	 */
	public static String reportHtml(Object obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("<strong><font color='red' size=4>");
		sb.append(obj.toString());
		sb.append("</font></strong>");
		
		return sb.toString();
	}
	
	public static String airCityReport(List<DailyAirQuality> daqs,
			int lengthIgnore) {
		List<String> cities = new ArrayList<String>();
		for(DailyAirQuality daq : daqs) {
			cities.add(daq.getCityName());
		}
		
		return MyStringUtil.Collection2Html(cities, lengthIgnore);
	}
}
