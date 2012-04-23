package com.guoyun.wordpress;

import java.net.MalformedURLException;

import com.guoyun.util.MyDateUtil;

import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.Page;
import net.bican.wordpress.Wordpress;

public class MyWordpress {
	public static final String BLOG_HAOKONGQI_USER_NAME = "";
	public static final String BLOG_HAOKONGQI_USER_PWD = "";
	public static final String BLOG_HAOKONGQI_URL = "http://www.haokongqi.net/xmlrpc.php";
	
	public static String writeBlog(String userName , String passwd, 
			String xmlRpcUrl ,Page page) {
		 Wordpress wp = null;
		 String result = "false";
		 
		 try {
			wp = new Wordpress(userName, passwd, xmlRpcUrl);
			result = wp.newPost(page, false);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlRpcFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String writeBLog_Haokongqi(Page page){
		return writeBlog(BLOG_HAOKONGQI_USER_NAME,
				BLOG_HAOKONGQI_USER_PWD, BLOG_HAOKONGQI_URL, page);
		
	}
	
	public static String writeTodayBlog(String blogContext) {
		Page page = new Page();
		page.setTitle("空气质量日报,今日空气质量日报," + 
				MyDateUtil.getTodayDate());
		page.setDescription(blogContext);
		//page.setLink("http://www.haokongqi/daily/"")
		return writeBLog_Haokongqi(page);
	}
}
