package com.guoyun.util;

public class HtmlUtil {
	public static String printDepot(String depotName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<li><br><font color=\"red\" size=\"3\">");
		sb.append(depotName);
		sb.append("</font>");
		sb.append(" 空气质量");
		sb.append("</br></li>");
		
		return sb.toString();
	}
}
