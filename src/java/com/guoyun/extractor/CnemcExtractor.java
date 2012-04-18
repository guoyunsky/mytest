package com.guoyun.extractor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.guoyun.air.DailyAirQuality;

public class CnemcExtractor {
	public static final String URL_CNEMC = "http://www.cnemc.cn/citystatus/airDailyReportMore.jsp?selmCity=&chkmCity=checkbox&selYear=2012&selMonth=3&selDay=27&txtPolluteRataMin=&txtPolluteRataMax=&selPolluteStyle=%BF%C9%CE%FC%C8%EB%BF%C5%C1%A3%CE%EF&selAirQuality=%C7%E1%CE%A2%CE%DB%C8%BE&selPolluteLevel=I";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HtmlCleaner cleaner=null;
        TagNode root=null;
        Object[] tmpNodes=null;
        Object[] tdNodes = null;
        TagNode tmpNode=null;
        
        DailyAirQuality daq = null;
        String cityName;		// 城市
    	String pollutionType;	// 污染类型
    	String pollutionLevel;	// 污染级别
    	String pollutionIndex;	// 污染指数
    	String airQuality;		// 空气质量
    	String dateTime;		// 时间
        
        try {
			cleaner=new HtmlCleaner();
			root=cleaner.clean(new URL(URL_CNEMC),"GBK");
			tmpNodes=root.evaluateXPath("//table[@vid='DataGrid1']");
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
							
							System.out.println(daq.toString());
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
		}
	}

}
