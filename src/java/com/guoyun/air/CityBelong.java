package com.guoyun.air;
/**
 * 城市归属
 * @author guoyun
 *
 */
public class CityBelong {
	private String cityName;
	private String provinceName;
	private String depotName;
	
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
	
	public CityBelong(String cityName, String provinceName, String depotName) {
		this.cityName = cityName;
		this.provinceName = provinceName;
		this.depotName = depotName;
	}
	
	
}
