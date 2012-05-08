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
	// 查询某天的摘要
	public String queryExcept(String dateStr);
	// 查询某天的平均空气指数
	public double queryAvgIndex(String dateStr);
	// 查询某天空气级别为优的空气(空气污染指数为0-50)
	public List<DailyAirQuality> queryYouAir(String dateStr);
	// 查询某天空气级别为良的空气（空气污染指数为50-100）
	public List<DailyAirQuality> queryLiangAir(String dateStr);
	// 查询某天空气级别为轻微污染的空气(空气污染指数为100-150)
	public List<DailyAirQuality> queryQingDuAir(String dateStr);
	// 查询某天空气级别为中度污染的空气(空气污染指数为150-200)
	public List<DailyAirQuality> queryZhongDuAir(String dateStr);
	// 查询某天空气级别为重度污染的空气(空气污染指数为200-300)
	public List<DailyAirQuality> queryZhong4DuAir(String dateSt);
	// 查询某天空气级别为严重污染的空气(空气污染指数为大于300)
	public List<DailyAirQuality> queryYanZhongAir(String dateSt);
	
	// 查询当前数据最新的日期
	public Date queryMaxDate();
}
