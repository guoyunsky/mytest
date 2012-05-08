package com.guoyun.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.guoyun.dao.IDailyAirQualityDAO;
import com.guoyun.entity.DailyAirQuality;
import com.guoyun.util.DailyAirQualityUtil;
import com.guoyun.util.DatabaseUtil;
import com.guoyun.util.MyDateUtil;
import com.guoyun.util.MyStringUtil;

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


public class DailyAirQualityDAOImpl implements IDailyAirQualityDAO {
	public static final String SQL_ADD_AIR_QUALITY="INSERT INTO " +
			"AIR_QUALITY(CITY_NAME,PROVINCE_NAME,DEPOT_NAME," +
			"POLLUTION_TYPE,POLLUTION_LEVEL,POLLUTION_INDEX," +
			"AIR_QUALITY,DATE_TIME) VALUES(?,?,?,?,?,?,?,?)";
	public static final String SQL_QUERY_PREFIX = "SELECT CITY_NAME," +
			"PROVINCE_NAME,DEPOT_NAME,POLLUTION_TYPE,POLLUTION_LEVEL," +
			"POLLUTION_INDEX,AIR_QUALITY,DATE_TIME FROM AIR_QUALITY";
	public static final String SQL_QUERY_MAX_DATE = "" +
			"SELECT MAX(DATE_TIME) FROM AIR_QUALITY;";
	
	@Override
	public int saveDatas(List<DailyAirQuality> daqs) {
		Connection con=null;
        PreparedStatement pstmt=null;
      
        try {
            con=DatabaseUtil.getConnection_Mysql_CrawlServer_Local();
            pstmt=con.prepareStatement(SQL_ADD_AIR_QUALITY);
            
            for(DailyAirQuality daq : daqs){
                pstmt.setString(1,daq.getCityName());
                pstmt.setString(2, daq.getProvinceName());
                pstmt.setString(3, daq.getDepotName());
                pstmt.setString(4, daq.getPollutionType());
                pstmt.setString(5, daq.getPollutionLevel());
                pstmt.setInt(6, daq.getPollutionIndexInt());
                pstmt.setString(7, daq.getAirQuality());
                pstmt.setString(8, daq.getDateStr());
                pstmt.addBatch();
            }
            return pstmt.executeBatch().length;
            
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            DatabaseUtil.closeConnection(con, null, pstmt, null);
        }
        return -1;

	}
	
	private ResultSet executeQuery(String querySql) {
		return executeQuery(querySql, null ,false);
	}
	
	private ResultSet executeQuery(String querySql, Connection conn,
			boolean closeConn) {
		ResultSet rs = null;
		Statement stmt = null;
		try {
			if(conn == null){
				conn = DatabaseUtil.getConnection_Mysql_CrawlServer_Local();
			}
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(closeConn){
				DatabaseUtil.closeConnection(conn, stmt, null);
			}
		}
		
		return rs;
	}

	@Override
	public int saveData(DailyAirQuality daq) {
		List<DailyAirQuality> daqs = new ArrayList<DailyAirQuality>();
		daqs.add(daq);
		return this.saveDatas(daqs);

	}

	@Override
	public List<DailyAirQuality> queryDAQ(String sql) {
		ResultSet rs = null;
		
		List<DailyAirQuality> result = null;
		DailyAirQuality daq = null;
		String cityName;		// 城市
		String provinceName;	// 省份
		String depotName;		// 华南，华北
		String pollutionType;	// 污染类型
		String pollutionLevel;	// 污染级别
		String pollutionIndex;	// 污染指数
		String airQuality;		// 空气质量
		String dateTime;		// 时间
		
		 try {
			 result = new ArrayList<DailyAirQuality>();
			 rs = executeQuery(sql);
			 
			 if(rs !=null) {
				 while(rs.next()){
					 cityName = rs.getString("CITY_NAME");
					 provinceName = rs.getString("PROVINCE_NAME");
					 depotName = rs.getString("DEPOT_NAME");
					 pollutionType = rs.getString("POLLUTION_TYPE");
					 pollutionLevel = rs.getString("POLLUTION_LEVEL");
					 pollutionIndex = rs.getString("POLLUTION_INDEX");
					 airQuality = rs.getString("AIR_QUALITY");
					 dateTime = rs.getString("DATE_TIME");
					 daq = new DailyAirQuality(cityName, provinceName,
							 depotName,pollutionType, pollutionLevel, 
							 pollutionIndex, airQuality, dateTime);
					 result.add(daq);
				 }
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public List<DailyAirQuality> queryDongBei(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='东北' " +
					"AND DATE_TIME=CURDATE() ORDER BY PROVINCE_NAME;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='东北' " +
			"AND DATE_TIME='" + dateStr +"' ORDER BY PROVINCE_NAME;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public List<DailyAirQuality> queryHuaBei(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='华北' " +
					"AND DATE_TIME=CURDATE() ORDER BY PROVINCE_NAME;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='华北' " +
			"AND DATE_TIME='" + dateStr +"' ORDER BY PROVINCE_NAME;");
		}
		
		return queryDAQ(sql);
	}
	
	@Override
	public List<DailyAirQuality> queryHuaZhong(String dateStr) {		
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='华中' " +
					"AND DATE_TIME=CURDATE() ORDER BY PROVINCE_NAME;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='华中' " +
			"AND DATE_TIME='" + dateStr +"' ORDER BY PROVINCE_NAME;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public List<DailyAirQuality> queryHuaDong(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='华东' " +
					"AND DATE_TIME=CURDATE() ORDER BY PROVINCE_NAME;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='华东' " +
			"AND DATE_TIME='" + dateStr +"' ORDER BY PROVINCE_NAME;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public List<DailyAirQuality> queryHuaNan(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='华南' " +
					"AND DATE_TIME=CURDATE() ORDER BY PROVINCE_NAME;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='华南' " +
			"AND DATE_TIME='" + dateStr +"' ORDER BY PROVINCE_NAME;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public List<DailyAirQuality> queryXiBei(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='西北' " +
					"AND DATE_TIME=CURDATE() ORDER BY PROVINCE_NAME;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='西北' " +
			"AND DATE_TIME='" + dateStr +"' ORDER BY PROVINCE_NAME;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public List<DailyAirQuality> queryXiNan(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='西南' " +
					"AND DATE_TIME=CURDATE() ORDER BY PROVINCE_NAME;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE DEPOT_NAME='西南' " +
			"AND DATE_TIME='" + dateStr +"' ORDER BY PROVINCE_NAME;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public List<DailyAirQuality> queryZhiXiaShi(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE PROVINCE_NAME  = " +
					"'直辖市' AND DATE_TIME=CURDATE() ORDER BY " +
					"PROVINCE_NAME;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE PROVINCE_NAME = " +
					"'直辖市' AND DATE_TIME='" + dateStr +"' ORDER BY " +
					"PROVINCE_NAME;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public Date queryMaxDate() {
		Connection conn = null;
		ResultSet rs = null;
		String dateStr = null;
		Date result = null;
		try {
			conn = DatabaseUtil.getConnection_Mysql_CrawlServer_Local();
			result = MyDateUtil.getTodayDate();
			rs = executeQuery(SQL_QUERY_MAX_DATE);
			if(rs !=null) {
				if(rs.next()) {
					dateStr = rs.getString(1);
					result = MyDateUtil.String2Date(dateStr);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(conn, null, rs);
		}
		
		return result;
	}
	
	

	@Override
	public double queryAvgIndex(String dateStr) {
		double result = 0;
		String sql = null;
		ResultSet rs = null;
		if(dateStr == null) {
			sql = "SELECT AVG(POLLUTION_INDEX) FROM AIR_QUALITY".
			concat("WHERE DATE_TIME = CURDATE();");
		} else {
			sql = "SELECT AVG(POLLUTION_INDEX) FROM AIR_QUALITY".
			concat(" WHERE DATE_TIME = '" + dateStr + "';");
		}
		
		try {
			rs = this.executeQuery(sql);
			if(rs != null && rs.next()) {
				result = rs.getDouble(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<DailyAirQuality> queryLiangAir(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>=" +
					INDEX_LIANG_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_LIANG_TO +" AND DATE_TIME=CURDATE() " +
					"ORDER BY POLLUTION_INDEX;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>" +
					INDEX_LIANG_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_LIANG_TO +" AND DATE_TIME='" + dateStr  +
					"' ORDER BY POLLUTION_INDEX;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public List<DailyAirQuality> queryQingDuAir(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>=" +
					INDEX_QING_DU_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_QING_DU_TO +" AND DATE_TIME=CURDATE() " +
					"ORDER BY POLLUTION_INDEX;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>" +
					INDEX_QING_DU_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_QING_DU_TO +" AND DATE_TIME='" + dateStr  +
					"' ORDER BY POLLUTION_INDEX;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public List<DailyAirQuality> queryYanZhongAir(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>=" +
					INDEX_YAN_ZHONG_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_YAN_ZHONG_TO +" AND DATE_TIME=CURDATE() " +
					"ORDER BY POLLUTION_INDEX;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>" +
					INDEX_YAN_ZHONG_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_YAN_ZHONG_TO +" AND DATE_TIME='" + dateStr  +
					"' ORDER BY POLLUTION_INDEX;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public List<DailyAirQuality> queryYouAir(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>=" +
					INDEX_YOU_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_YOU_TO +" AND DATE_TIME=CURDATE() " +
					"ORDER BY POLLUTION_INDEX;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>" +
					INDEX_YOU_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_YOU_TO +" AND DATE_TIME='" + dateStr  +
					"' ORDER BY POLLUTION_INDEX;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public List<DailyAirQuality> queryZhong4DuAir(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>=" +
					INDEX_ZHONG_DU_4_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_ZHONG_DU_4_TO +" AND DATE_TIME=CURDATE() " +
					"ORDER BY POLLUTION_INDEX;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>" +
					INDEX_ZHONG_DU_4_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_ZHONG_DU_4_TO +" AND DATE_TIME='" + dateStr  +
					"' ORDER BY POLLUTION_INDEX;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public List<DailyAirQuality> queryZhongDuAir(String dateStr) {
		String sql = null;
		if(dateStr == null) {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>=" +
					INDEX_ZHONG_DU_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_ZHONG_DU_TO +" AND DATE_TIME=CURDATE() " +
					"ORDER BY POLLUTION_INDEX;");
		}else {
			sql = SQL_QUERY_PREFIX.concat(" WHERE POLLUTION_INDEX>" +
					INDEX_ZHONG_DU_FROM + " AND POLLUTION_INDEX<=" + 
					INDEX_ZHONG_DU_TO +" AND DATE_TIME='" + dateStr  +
					"' ORDER BY POLLUTION_INDEX;");
		}
		
		return queryDAQ(sql);
	}

	@Override
	public String queryExcept(String dateStr) {
		StringBuilder sb = null;new StringBuilder();
		double avg = 0;
		String avgAirHtml = null;
		List<DailyAirQuality> tmpList = null;
		int size = 0;
		
		sb = new StringBuilder();
		avg = this.queryAvgIndex(dateStr);
		avgAirHtml = DailyAirQualityUtil.getAirLevelHtml(avg);
		sb.append("nbsp;nbsp;nbsp;nbsp;今日全国空气质量整体为 ");
		sb.append(avgAirHtml);
		sb.append(",全国平均空气污染指数为 ");
		sb.append(DailyAirQualityUtil.reportHtml(((int)avg)+""));
		sb.append("<br></br>");
		sb.append("<ul>");
		
		sb.append("<li>其中空气质量为优的城市有 ");
		tmpList = this.queryYouAir(dateStr);
		size = tmpList.size();
		sb.append(DailyAirQualityUtil.reportHtml(size));
		sb.append(" 个,分别为:"+ DailyAirQualityUtil.airCityReport(
				tmpList, 100));
		sb.append("</li>");
		
		sb.append("<li>其中空气质量为良的城市有 ");
		tmpList = this.queryLiangAir(dateStr);
		size = tmpList.size();
		sb.append(DailyAirQualityUtil.reportHtml(size));
		sb.append("个,分别为:"+ DailyAirQualityUtil.airCityReport(
				tmpList, 40));
		sb.append("</li>");
		
		sb.append("<li>");
		tmpList = this.queryQingDuAir(dateStr);
		size = tmpList.size();
		if(size > 0) {
			sb.append("其中空气质量为轻度污染的城市有 ");
			sb.append(DailyAirQualityUtil.reportHtml(size));
			sb.append("个,分别为:"+ DailyAirQualityUtil.airCityReport(
					tmpList, 40));
		}else {
			sb.append("今日没有空气质量为轻度污染的城市");
			sb.append(DailyAirQualityUtil.reportHtml("轻度污染"));
			sb.append("的城市");
		}
		sb.append("</li>");
		
		sb.append("<li>");
		tmpList = this.queryZhongDuAir(dateStr);
		size = tmpList.size();
		if(size > 0 ) {
			sb.append("其中空气质量为中度污染的城市有 ");
			sb.append(DailyAirQualityUtil.reportHtml(size));
			sb.append("个,分别为:"+ DailyAirQualityUtil.airCityReport(
					tmpList, 40));
		} else {
			sb.append("今日没有空气质量为中度污染的城市");
			sb.append(DailyAirQualityUtil.reportHtml("中度污染"));
			sb.append("的城市");
		}
		sb.append("</li>");
		
		sb.append("<li>");
		tmpList = this.queryZhong4DuAir(dateStr);
		size = tmpList.size();
		if(size > 0) {
			sb.append("其中空气质量为重度污染的城市有 ");
			sb.append(DailyAirQualityUtil.reportHtml(size));
			sb.append("个,分别为:"+ DailyAirQualityUtil.airCityReport(
					tmpList, 40));
		} else {
			sb.append("今日没有空气质量为重度污染的城市");
			sb.append(DailyAirQualityUtil.reportHtml("重度污染"));
			sb.append("的城市");
		}
		sb.append("</li>");
		
		sb.append("<li>");
		tmpList = this.queryYanZhongAir(dateStr);
		size = tmpList.size();
		if(size > 0) {
			sb.append("<li>其中空气质量为严重污染的城市有 ");
			sb.append(DailyAirQualityUtil.reportHtml(size));
			sb.append("个,分别为:"+ DailyAirQualityUtil.airCityReport(
					tmpList, 40));
		} else {
			sb.append("今日没有空气质量为严重污染的城市");
			sb.append(DailyAirQualityUtil.reportHtml("严重污染"));
			sb.append("的城市");
		}
		
		sb.append("</li>");
		
		sb.append("</ul>");
		
		return sb.toString();
	}
	
	
}
