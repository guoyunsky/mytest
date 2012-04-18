package com.guoyun.air;

public class DailyAirQuality {
	private String cityName;		// 城市
	private String provinceName;	// 省份
	private String depotName;		// 华南，华北
	
	private String pollutionType;	// 污染类型
	private String pollutionLevel;	// 污染级别
	private String pollutionIndex;	// 污染指数
	private String airQuality;		// 空气质量
	private String dateTime;		// 时间
	
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
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	public DailyAirQuality() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DailyAirQuality(String cityName, String provinceName, String depotName,
			String pollutionType, String pollutionLevel, String pollutionIndex,
			String airQuality, String dateTime) {
		super();
		this.cityName = cityName;
		this.provinceName = provinceName;
		this.depotName = depotName;
		this.pollutionType = pollutionType;
		this.pollutionLevel = pollutionLevel;
		this.pollutionIndex = pollutionIndex;
		this.airQuality = airQuality;
		this.dateTime = dateTime;
	}
	
	public DailyAirQuality(String cityName, String pollutionType,
			String pollutionLevel, String pollutionIndex, String airQuality,
			String dateTime) {
		super();
		this.cityName = cityName;
		this.pollutionType = pollutionType;
		this.pollutionLevel = pollutionLevel;
		this.pollutionIndex = pollutionIndex;
		this.airQuality = airQuality;
		this.dateTime = dateTime;
	}
	
	public String toString(){
		return cityName+"\t"+pollutionIndex+"\t"+pollutionType+"\t"+
			pollutionLevel+"\t"+airQuality+"\t"+dateTime;
	}
}
