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
import com.guoyun.util.HtmlUtil;
import com.guoyun.util.MyDateUtil;
import com.guoyun.wordpress.MyWordpress;


public class CnemcMain {
	public static final int DEFAULT_PAGE_NO = 10*4;
	private Date curMaxDate = null;
	private Date todayDate = null;
	private Date dbMaxDate = null;
	private boolean hasInsertTodayDatas = false;
	private IDailyAirQualityDAO iaqdao = null;
	private ExtractorCnemc extractor = null;
	
	public CnemcMain() throws ParseException {
		init();
	}
	
	private void init() throws ParseException {
		iaqdao = new DailyAirQualityDAOImpl();
		curMaxDate = iaqdao.queryMaxDate();
		dbMaxDate = new Date(curMaxDate.getTime());
		todayDate = MyDateUtil.getTodayDate();
		extractor = new ExtractorCnemc();
	}
	
	public boolean crawlDaqs() throws ParseException {
		return crawlDaqs(calPage());
	}
	
	
	public Date getCurMaxDate() {
		return curMaxDate;
	}

	public void setCurMaxDate(Date curMaxDate) {
		this.curMaxDate = curMaxDate;
	}

	public Date getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(Date todayDate) {
		this.todayDate = todayDate;
	}

	public Date getDbMaxDate() {
		return dbMaxDate;
	}

	public void setDbMaxDate(Date dbMaxDate) {
		this.dbMaxDate = dbMaxDate;
	}

	public boolean isHasInsertTodayDatas() {
		return hasInsertTodayDatas;
	}

	public void setHasInsertTodayDatas(boolean hasInsertTodayDatas) {
		this.hasInsertTodayDatas = hasInsertTodayDatas;
	}

	public IDailyAirQualityDAO getIaqdao() {
		return iaqdao;
	}

	public void setIaqdao(IDailyAirQualityDAO iaqdao) {
		this.iaqdao = iaqdao;
	}

	public ExtractorCnemc getExtractor() {
		return extractor;
	}

	public void setExtractor(ExtractorCnemc extractor) {
		this.extractor = extractor;
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
		boolean insert = false;
		
		if(pages <=0) {
			pages = DEFAULT_PAGE_NO;
		}
		
		for(int i=1 ; i<=pages ; i++) {
			result.addAll(extractor.getAirQualities(i));
			if(insertDatas(result)) {
				result.clear();
				result = new ArrayList<DailyAirQuality>();
				insert = true;
			}else {
				break;
			}
				
		}
		
		this.curMaxDate = iaqdao.queryMaxDate();
		
		return this.curMaxDate.before(this.todayDate) && insert;
		
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
		sb.append("<table border=\"2\" style=\"width: 100%;\">");
		sb.append("\r\t<thead>");
		sb.append("\r\t<th width=\"20%\">省份</th>");
		sb.append("\r\t<th width=\"25%\">城市</th>");
		sb.append("\r\t<th width=\"15%\" align=\"center\">空气污染指数</th>");
		sb.append("\r\t<th width=\"20%\">空气污染类型</th>");
		sb.append("\r\t<th width=\"10%\">空气污染级别</th>");
		sb.append("\r\t<th width=\"10%\">空气质量</th>");
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
		return "\r\t<td alitn=\"center\">".concat(from).concat("</td>");
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
		
		// 获取摘要
		String zhaiyao = iaqdao.queryExcept(dateStr);
		sb.append(zhaiyao);
		sb.append("<br></br>");
		
		// 获取直辖市数据
		daqs = iaqdao.queryZhiXiaShi(dateStr);
		sb.append("<ul>");
		if(daqs!=null && daqs.size() > 0) {
			sb.append(HtmlUtil.printDepot("直辖市"));
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		
		// 获取华东数据
		daqs = iaqdao.queryHuaDong(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append(HtmlUtil.printDepot("华东地区"));
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取华北数据
		daqs = iaqdao.queryHuaBei(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append(HtmlUtil.printDepot("华北地区"));
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取华南数据
		daqs = iaqdao.queryHuaNan(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append(HtmlUtil.printDepot("华南地区"));
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取华南数据
		daqs = iaqdao.queryHuaZhong(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append(HtmlUtil.printDepot("华中地区"));
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取西南数据
		sb.append(HtmlUtil.printDepot("西南地区"));
		if(daqs!=null && daqs.size()>0) {
			daqs = iaqdao.queryXiNan(dateStr);
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取东北数据
		daqs = iaqdao.queryDongBei(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append(HtmlUtil.printDepot("东北地区"));
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		
		// 获取西北数据
		daqs = iaqdao.queryXiBei(dateStr);
		if(daqs!=null && daqs.size() >0) {
			sb.append(HtmlUtil.printDepot("西东地区"));
			writeDepot(sb, daqs);
			writeBlog = true;
		}
		sb.append("</ul>");
		
		if(writeBlog == true) {
			System.out.println(sb.toString());
			/*MyWordpress.writeLAirReportBlogByDay(dateStr, 
					sb.toString(),zhaiyao);*/
			
		}
		
		return true;
	}
	
	

	public static void main(String[] args) throws InterruptedException {
		CnemcMain main = null;
		
		try {
			main = new CnemcMain();
			
			// 插入数据
			if(main.crawlDaqs()) {
				while(true){
					if(!main.insertTodayDatas()){
						System.out.println("抓取完毕！");
						break;
					} else {
						Thread.sleep(10*60*1000);
					}
				}
			}
			
			// main.writeTodayBlog();
			Date d = main.curMaxDate;
			while(!d.after(main.todayDate)) {
				if(d.after(main.dbMaxDate)) {
					main.writeBlogByDay(d);
					d = new Date(main.curMaxDate.getTime() + 
							1000   *   60   *   60   *   24);
				} else {
					break;
				}
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlRpcFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
