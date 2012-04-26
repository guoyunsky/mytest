package com.guoyun.wordpress;

import java.net.MalformedURLException;
import java.util.List;

import com.guoyun.util.MyDateUtil;

import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.Page;
import net.bican.wordpress.Wordpress;

public class MyWordpress {
	public static final String BLOG_HAOKONGQI_USER_NAME = "好空气";
	public static final String BLOG_HAOKONGQI_USER_PWD = "521yangjuan";
	public static final String BLOG_HAOKONGQI_URL = "http://www.haokongqi.net/xmlrpc.php";
	
	public static Wordpress getWordpress(String userName, String passwd,String xmlRpcUrl) {
		Wordpress wp = null;
		 
		try {
			wp = new Wordpress(userName, passwd, xmlRpcUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return wp; 
	}
	
	public static Wordpress getWordpress_Haokongqi(){
		return getWordpress(BLOG_HAOKONGQI_USER_NAME, 
				BLOG_HAOKONGQI_USER_PWD, BLOG_HAOKONGQI_URL);
	}
	
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
	
	public static String writeAirReportBlog(String blogTitle ,
			String blogContext) throws XmlRpcFault {
		/*Page page = new Page();
		page.setTitle(blogTitle);
		page.setDescription(blogContext);
		//page.setLink("http://www.haokongqi/daily/"")
		*/		
		Wordpress wp = getWordpress_Haokongqi();
		List<Page> pages = wp.getRecentPosts(1);
		Page page = pages.get(0);
		page.setTitle(blogTitle);
		page.setDescription(blogContext);
		return writeBLog_Haokongqi(page);
	}
	
	public static String writeLAirReportBlogByDay(String dateStr,
			String blogContext) throws XmlRpcFault {
		String title = dateStr + ",空气质量日报,今日空气质量日报";
		return writeAirReportBlog(title,blogContext);
		
	}
	
	public static void main(String[] args) throws XmlRpcFault {
		Wordpress wp = getWordpress_Haokongqi();
		List<Page> pages = wp.getRecentPosts(1);
		Page page = pages.get(0);
		
	}
}
