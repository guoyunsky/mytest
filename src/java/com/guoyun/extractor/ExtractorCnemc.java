package com.guoyun.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.guoyun.dao.impl.DailyAirQualityDAOImpl;
import com.guoyun.entity.DailyAirQuality;

public class ExtractorCnemc {
	public static final String URL_CNEMC_URL = "http://www.cnemc.cn/" +
	"citystatus/airDailyReportMore.jsp?selmCity=&" +
	"chkmCity=checkbox&selYear=2012&selMonth=3&selDay=27&" +
	"txtPolluteRataMin=&txtPolluteRataMax=&selPolluteStyle=" +
	"%BF%C9%CE%FC%C8%EB%BF%C5%C1%A3%CE%EF&selAirQuality=" +
	"%C7%E1%CE%A2%CE%DB%C8%BE&selPolluteLevel=I&pageno=";
	
	
	public static String buildUrl(int pageNo) {
		return URL_CNEMC_URL+pageNo;
	}
	
	public List<DailyAirQuality> getAirQualities(int pageNo) {
		return  getAirQualities(buildUrl(pageNo));
	}
	
	public  List<DailyAirQuality> getAirQualities(String url){
		HtmlCleaner cleaner=null;
        TagNode root=null;
        Object[] tmpNodes=null;
        Object[] tdNodes = null;
        TagNode tmpNode=null;
        List<DailyAirQuality> result = null;
        URL u = null;
        URLConnection conn = null;
        InputStream is = null;
        DailyAirQuality daq = null;
        String cityName;		// 城市
    	String pollutionType;	// 污染类型
    	String pollutionLevel;	// 污染级别
    	String pollutionIndex;	// 污染指数
    	String airQuality;		// 空气质量
    	String dateTime;		// 时间
        
        try {
			cleaner=new HtmlCleaner();
			u = new URL(url);
			conn = u.openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (" +
					"Macintosh; U; Intel Mac OS X 10.4; en-US; " +
					"rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			conn.setConnectTimeout(20000);
			is = conn.getInputStream();
			
			root=cleaner.clean(is,"GBK");
			tmpNodes=root.evaluateXPath("//table[@vid='DataGrid1']");
			result = new ArrayList<DailyAirQuality>();
			
			if(tmpNodes != null && tmpNodes.length > 1) {
				tmpNode = (TagNode)tmpNodes[1];
				tmpNodes = tmpNode.evaluateXPath("//tr");
				
				if(tmpNodes != null && tmpNodes.length>1){
					for(int i=1 ; i<tmpNodes.length ; i++){
						tdNodes = ((TagNode)tmpNodes[i]).
							evaluateXPath("//td");
						
						if(tdNodes != null && tdNodes.length == 6) {
							cityName = ((TagNode)tdNodes[0]).
								getText().toString().trim();
							pollutionIndex = ((TagNode)tdNodes[1]).
								getText().toString().trim();
							pollutionType = ((TagNode)tdNodes[2]).
								getText().toString().trim();
							pollutionLevel = ((TagNode)tdNodes[3]).
								getText().toString().trim();
							airQuality = ((TagNode)tdNodes[4]).
								getText().toString().trim();
							dateTime = ((TagNode)tdNodes[5]).
								getText().toString().trim();
							
							daq = new DailyAirQuality(cityName, 
								pollutionType, pollutionLevel, 
								pollutionIndex, airQuality, dateTime);
							System.out.println(daq);
							result.add(daq);
						}
					}
				}
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
		return result;
	}
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		List<DailyAirQuality> result = 
			new ArrayList<DailyAirQuality>();
		Set<String> levels = new HashSet<String>();
		
		for(int i=2656 ; i<=4527; i++){
			try {
				result.addAll(new ExtractorCnemc().getAirQualities(buildUrl(i)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread.sleep(3000);
			if(i%4==0) {
				try {
					System.out.println(new DailyAirQualityDAOImpl().saveDatas(result));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.clear();
				result = new ArrayList();
			}
		}
		
		System.out.println(new DailyAirQualityDAOImpl().saveDatas(result));
		
		/*for(DailyAirQuality daq : result){
			if(!levels.contains(daq.getPollutionLevel())){
				levels.add(daq.getPollutionLevel());
				System.out.println(daq.getPollutionLevel());
			}
		}*/
		
		
	}

}
