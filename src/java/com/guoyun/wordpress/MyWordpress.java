package com.guoyun.wordpress;

import java.net.MalformedURLException;
import java.util.List;

import com.guoyun.util.MyDateUtil;

import redstone.xmlrpc.XmlRpcArray;
import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.Page;
import net.bican.wordpress.Wordpress;

public class MyWordpress {
	public static final String BLOG_HAOKONGQI_USER_NAME = "好空气";
	public static final String BLOG_HAOKONGQI_USER_PWD = "521yangjuan";
	public static final String BLOG_HAOKONGQI_URL = "http://www.haokongqi.net/xmlrpc.php";
	public static final String BLOG_HAOKONGQI_DAILY_URL_PREFIX= 
		"http://www.haokongqi.net/kongqizhiliang/daily/";
	
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
			String blogContext, String blogExcept) throws XmlRpcFault {
		XmlRpcArray xra = new XmlRpcArray();
		int postId = 0;
		
		Page page = new Page();
		page.setTitle(blogTitle);
		page.setDescription(blogContext);
		
		//page.setLink("http://www.haokongqi/daily/"")
				
		/*Wordpress wp = getWordpress_Haokongqi();
		List<Page> pages = wp.getRecentPosts(1);
		Page page = pages.get(0);
		page.setTitle(blogTitle);
		page.setDescription(blogContext);*/
		
		xra.add("空气质量");
		page.setCategories(xra);
		/*page.setLink(BLOG_HAOKONGQI_DAILY_URL_PREFIX + 
				MyDateUtil.getTodayDateNoCharStr() + ".html");
		page.setPermaLink(BLOG_HAOKONGQI_DAILY_URL_PREFIX + 
				MyDateUtil.getTodayDateNoCharStr() + ".html");*/
		try {
			postId = Integer.parseInt("1" + 
					MyDateUtil.getTodayDateNoCharStr());
			page.setPostid(postId);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		page.setMt_allow_comments(1);
		page.setMt_allow_pings(1);
		page.setExcerpt(blogExcept);
		page.setMt_keywords("空气质量,空气质量日报,今日空气质量日报");
		page.setPost_status("publish");
		page.setUserid(1 + "");
		page.setWp_author("好空气");
		page.setWp_author_display_name("空气质量日报");
		page.setWp_author_id("1");
		
		return writeBLog_Haokongqi(page);
	}
	
	public static String writeLAirReportBlogByDay(String dateStr,
			String blogContext, String blogExcept) throws XmlRpcFault {
		String title = dateStr + ",空气质量日报,今日空气质量日报";
		return writeAirReportBlog(title,blogContext, blogExcept);
		
	}
	
	public static void main(String[] args) throws XmlRpcFault {
		Wordpress wp = getWordpress_Haokongqi();
		List<Page> pages = wp.getRecentPosts(1);
		Page page = pages.get(0);
		
	}
}
