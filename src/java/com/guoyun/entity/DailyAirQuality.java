package com.guoyun.entity;

import java.text.ParseException;
import java.util.Date;

import com.guoyun.util.CityUtil;
import com.guoyun.util.MyDateUtil;
import com.guoyun.util.PollutionLevelUtil;
/**
 * 空气指数类
 * @author guoyun
 *
 */
public class DailyAirQuality {
	public static final int INDEX_YOU_FROM = 1;
	public static final int INDEX_YOU_TO = 50;
	public static final int INDEX_LIANG_FROM = 51;
	public static final int INDEX_LIANG_TO = 100;
	public static final int INDEX_QING_DU_FROM = 101;
	public static final int INDEX_QING_DU_TO = 150;
	public static final int INDEX_ZHONG_DU_FROM = 151;
	public static final int INDEX_ZHONG_DU_TO = 200;
	public static final int INDEX_ZHONG_DU_4_FROM = 201;
	public static final int INDEX_ZHONG_DU_4_TO = 300;
	public static final int INDEX_YAN_ZHONG_FROM = 300;
	public static final int INDEX_YAN_ZHONG_TO = Integer.MAX_VALUE;
	
	public static final String AIR_YOU = "优";
	public static final String AIR_LIANG = "良";
	public static final String AIR_QING_DU = "轻度污染";
	public static final String AIR_ZHONG_DU = "中度污染";
	public static final String AIR_ZHONG_DU_4 = "重度污染";
	public static final String AIR_YAN_ZHONG = "严重污染";
	
	private String cityName;		// 城市
	private String provinceName;	// 省份
	private String depotName;		// 华南，华北
	
	private String pollutionType;	// 污染类型
	private String pollutionLevel;	// 污染级别
	private String pollutionIndex;	// 污染指数
	private String airQuality;		// 空气质量
	private String dateStr;			// 时间
	
	private int pollutionLevelInt;	// 等级,int
	private int pollutionIndexInt;	// 指数,int
	private Date date;				// 时间,java.util.Date
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getDepotName() {
		return depotName;
	}
	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}
	public String getPollutionType() {
		return pollutionType;
	}
	public void setPollutionType(String pollutionType) {
		this.pollutionType = pollutionType;
	}
	public String getPollutionLevel() {
		return pollutionLevel;
	}
	public void setPollutionLevel(String pollutionLevel) {
		this.pollutionLevel = pollutionLevel;
	}
	public String getPollutionIndex() {
		return pollutionIndex;
	}
	public void setPollutionIndex(String pollutionIndex) {
		this.pollutionIndex = pollutionIndex;
	}
	public String getAirQuality() {
		return airQuality;
	}
	public void setAirQuality(String airQuality) {
		this.airQuality = airQuality;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
	public Date getDate() {
		if(date == null) {
			try {
				this.date = MyDateUtil.String2Date(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public DailyAirQuality() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DailyAirQuality(String cityName, String provinceName, String depotName,
			String pollutionType, String pollutionLevel, String pollutionIndex,
			String airQuality, String dateStr) {
		this.cityName = cityName;
		this.provinceName = provinceName;
		this.depotName = depotName;
		this.pollutionType = pollutionType;
		this.pollutionLevel = pollutionLevel;
		this.pollutionIndex = pollutionIndex;
		this.airQuality = airQuality;
		this.dateStr = dateStr;
		
		try {
			this.date = MyDateUtil.String2Date(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		calPollution();
	}
	
	public DailyAirQuality(String cityName, String pollutionType,
			String pollutionLevel, String pollutionIndex, String airQuality,
			String dateStr) {
		this.cityName = cityName;
		this.pollutionType = pollutionType;
		this.pollutionLevel = pollutionLevel;
		this.pollutionIndex = pollutionIndex;
		this.airQuality = airQuality;
		this.dateStr = dateStr;
		
		try {
			this.date = MyDateUtil.String2Date(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CityBelong cb = CityUtil.getCityBelong(this.cityName);
		if(cb != null) {
			this.provinceName = cb.getProvinceName();
			this.depotName = cb.getDepotName();
		}else {
			System.err.println("error city name :" + this.cityName);
		}
		
		
		calPollution();
	}
	
	private void calPollution() {
		this.pollutionIndexInt = PollutionLevelUtil.
			parsePollutionIndex(this.pollutionIndex);
		this.pollutionLevelInt = PollutionLevelUtil.
			parsePollutionLevel(this.pollutionLevel);
	}
	
	public String toString(){
		return cityName +"\t" + provinceName + "\t" + depotName +
			"\t" + pollutionIndex+"\t"+pollutionType+"\t"+
			pollutionLevel+"\t"+airQuality+"\t"+dateStr;
	}
	
	public int getPollutionLevelInt() {
		return pollutionLevelInt;
	}
	public void setPollutionLevelInt(int pollutionLevelInt) {
		this.pollutionLevelInt = pollutionLevelInt;
	}
	public int getPollutionIndexInt() {
		return pollutionIndexInt;
	}
	public void setPollutionIndexInt(int pollutionIndexInt) {
		this.pollutionIndexInt = pollutionIndexInt;
	}
	
}
