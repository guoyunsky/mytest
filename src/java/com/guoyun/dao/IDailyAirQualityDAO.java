package com.guoyun.dao;

import java.util.Date;
import java.util.List;

import com.guoyun.entity.DailyAirQuality;

public interface IDailyAirQualityDAO {
	public int saveDatas(List<DailyAirQuality> daqs);
	public int saveData(DailyAirQuality daq);
	
	// 查询一个sql
	public List<DailyAirQuality> queryDAQ(String sql);
	// 查询直辖市
	public List<DailyAirQuality> queryZhiXiaShi(String dateStr);
	// 查询华东
	public List<DailyAirQuality> queryHuaDong(String dateStr);
	// 查询华北
	public List<DailyAirQuality> queryHuaBei(String dateStr);
	// 查询华南
	public List<DailyAirQuality> queryHuaNan(String dateStr);
	// 查询华中
	public List<DailyAirQuality> queryHuaZhong(String dateStr);
	// 查询西南
	public List<DailyAirQuality> queryXiNan(String dateStr);
	// 查询东北
	public List<DailyAirQuality> queryDongBei(String dateStr);
	// 查询西北
	public List<DailyAirQuality> queryXiBei(String dateStr);
	
	// 查询当前数据最新的日期
	public Date queryMaxDate();
}
