package com.guoyun.main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import redstone.xmlrpc.XmlRpcFault;

import com.guoyun.dao.IDailyAirQualityDAO;
import com.guoyun.dao.impl.DailyAirQualityDAOImpl;
import com.guoyun.entity.DailyAirQuality;
import com.guoyun.extractor.ExtractorCnemc;
import com.guoyun.util.MyDateUtil;
import com.guoyun.wordpress.MyWordpress;


public class CnemcMain {
	public static final int DEFAULT_PAGE_NO = 10*4;
	private Date curMaxDate = null;
	private Date todayDate = null;
	private boolean hasInsertTodayDatas = false;
	private IDailyAirQualityDAO iaqdao = null;
	private ExtractorCnemc extractor = null;
	
	public CnemcMain() throws ParseException {
		init();
	}
	
	private void init() throws ParseException {
		iaqdao = new DailyAirQualityDAOImpl();
		curMaxDate = iaqdao.queryMaxDate();
		todayDate = MyDateUtil.getTodayDate();
		extractor = new ExtractorCnemc();
	}
	
	public boolean crawlDaqs() throws ParseException {
		return crawlDaqs(calPage());
	}
	
	/**
	 * 
	 * @param pages
	 * @return 是否要抓取今天的数据
	 * @throws ParseException 
	 */
	public boolean crawlDaqs(int pages) throws ParseException {
		List<DailyAirQuality> result = 
			new ArrayList<DailyAirQuality>();
		
		if(pages <=0) {
			pages = DEFAULT_PAGE_NO;
		}
		
		for(int i=1 ; i<pages ; i++) {
			result.addAll(extractor.getAirQualities(i));
			while(insertDatas(result)) {
				result.clear();
				result = new ArrayList<DailyAirQuality>();
				result.addAll(extractor.getAirQualities(i));
				continue;
			}
		}
		
		this.curMaxDate = iaqdao.queryMaxDate();
		
		return this.curMaxDate.before(this.todayDate);
		
	}
	/**
	 * 
	 * @param datas
	 * @return 是否还继续抓取
	 * @throws ParseException 
	 */
	public boolean insertDatas(List<DailyAirQuality> datas) 
		throws ParseException {
		boolean result = false;
		Date maxDate = null;
		List<DailyAirQuality> insertDatas = null;
		
		insertDatas = new ArrayList<DailyAirQuality>();
		maxDate = MyDateUtil.String2Date("1900-01-01");
		
		for(DailyAirQuality daq : datas) {
			if(daq.getDate().after(this.curMaxDate)) {
				insertDatas.add(daq);
				result =true;
				if(daq.getDate().after(maxDate)) {
					maxDate = daq.getDate();
				}
			}else {
				result = false;
			}
				
		}
		// 插入数据
		if(insertDatas.size() > 0) {
			iaqdao.saveDatas(insertDatas);
		}
		
		return result;
	}
	
	private int calPage()  {
		long result = 0;
		try {
			result =7*MyDateUtil.calDiffToday(curMaxDate);
		} catch (ParseException e) {
			result = DEFAULT_PAGE_NO;
		}
		return (int)result;
	}
	
	public void writeBlog() {
		
	}
	
	
	public boolean insertTodayDatas() throws ParseException {
		return crawlDaqs(4);
	}
	
	public void writeMeta(StringBuilder sb,String dateStr) {
		sb.append("<div align=\"center\">今日空气质量日报" + dateStr +" </div>");
	}
	
	public void writeMeta(StringBuilder sb,Date date) {
		sb.append("<div align=\"center\">今日空气质量日报" + 
				MyDateUtil.Date2String(date) +" </div>");
	}
	
	public void writeDepot(StringBuilder sb, 
			List<DailyAirQuality> daqs) {
		writeTableHead(sb);
		writeAirQuality(sb, daqs);
		writeTableTail(sb);
	}
	
	public void writeTableHead(StringBuilder sb) {
		sb.append("<table border=\"2\" style=\"width: 100%; border-collapse: collapse;\">");
		sb.append("\r\t<thead>");
		sb.append("\r\t<td width=\"20%\">省份</td>");
		sb.append("\r\t<td width=\"20%\">城市</td>");
		sb.append("\r\t<td width=\"20%\" align=\"center\">空气污染指数</td>");
		sb.append("\r\t<td width=\"20%\">空气污染类型</td>");
		sb.append("\r\t<td width=\"10%\">空气污染级别</td>");
		sb.append("\r\t<td width=\"10%\">空气质量</td>");
		sb.append("\r</thead>");
		sb.append("\r<tbody>");
	}
	
	public void writeTableTail(StringBuilder sb) {
		sb.append("\r</tbody>");
		sb.append("\r</table>");
	}
	
	public void writeAirQuality(StringBuilder sb, 
			List<DailyAirQuality> daqs) {
		for(DailyAirQuality daq : daqs) {
			sb.append("\r\t<tr>");
			sb.append(tdCreate(daq.getProvinceName()+"省"));
			sb.append(tdCreate("<strong>"+daq.getCityName()+"</strong>空气质量"));
			sb.append(tdCreate(daq.getPollutionIndex()));
			sb.append(tdCreate(daq.getPollutionType()));
			sb.append(tdCreate(daq.getPollutionLevel()));
			sb.append(tdCreate(daq.getAirQuality()));
			sb.append("\r\t</tr>");
		}
		
	}
	
	private String tdCreate(String from) {
		return "\r\t<td>".concat(from).concat("</td>");
	}
	
	public boolean writeTodayBlog() throws XmlRpcFault {
		return writeBlogByDay(MyDateUtil.getTodayDate());
	}
	
	public boolean writeCurMaxDateBlog() throws XmlRpcFault {
		return writeBlogByDay(this.curMaxDate);
	}
	
	public boolean writeBlogByDay(Date date) throws XmlRpcFault {
		boolean writeBlog = false;
		StringBuilder sb = null;
		List<DailyAirQuality> daqs = null;
		String dateStr = null;
		
		sb = new StringBuilder();
		daqs = new ArrayList<DailyAirQuality>();
		dateStr = MyDateUtil.Date2String(date);
		writeMeta(sb,dateStr);
		
		// 获取直辖市数据
		daqs = iaqdao.queryZhiXiaShi(dateStr);
		if(daqs!=null && daqs.size() > 0) {
			sb.append("<br>直辖市 空气质量");
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		
		// 获取华东数据
		daqs = iaqdao.queryHuaDong(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append("<br>华东 空气质量");
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取华北数据
		daqs = iaqdao.queryHuaBei(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append("<br>华北 空气质量");
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取华南数据
		daqs = iaqdao.queryHuaNan(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append("<br>华南 空气质量");
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取华南数据
		daqs = iaqdao.queryHuaZhong(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append("<br>华中 空气质量");
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取西南数据
		sb.append("<br>西南 空气质量");
		if(daqs!=null && daqs.size()>0) {
			daqs = iaqdao.queryXiNan(dateStr);
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取东北数据
		daqs = iaqdao.queryDongBei(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append("<br>东北 空气质量");
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取西北数据
		daqs = iaqdao.queryXiBei(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append("<br>西北 空气质量");
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		if(writeBlog == true) {
			System.out.println(sb.toString());
			MyWordpress.writeLAirReportBlogByDay(dateStr, sb.toString());
			
		}
		
		return true;
	}

	public static void main(String[] args) throws InterruptedException {
		CnemcMain main = null;
		
		try {
			main = new CnemcMain();
			
			// 插入数据
			/*if(main.crawlDaqs()) {
				while(true){
					if(!main.insertTodayDatas()){
						Thread.sleep(10*60*1000);
						System.out.println("抓取完毕！");
						break;
					}
				}
			}*/
			
			// main.writeTodayBlog();
			main.writeCurMaxDateBlog();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlRpcFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
