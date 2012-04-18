package com.guoyun.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class HtmlCleanerTest {

	/**
	 * @param args
	 * @throws XPatherException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws XPatherException, IOException {
		HtmlCleaner cleaner = new HtmlCleaner();  
		  
        TagNode node = cleaner.clean(new URL("http://www.huanqiu.com"),"UTF-8");  
        //按tag取.  
        Object[] ns = node.getElementsByName("title", true);    //标题  
  
        if(ns.length > 0) {  
            System.out.println("title="+((TagNode)ns[0]).getText());  
        }  
        
        /*System.out.println("ul/li:");  
        //按xpath取  
        ns = node.evaluateXPath("//div[@class='d_1']//li");  
        for(Object on : ns) {  
            TagNode n = (TagNode) on;  
            System.out.println("\ttext="+n.getText());  
        }  
        System.out.println("a:");  
        //按属性值取  
        ns = node.getElementsByAttValue("name", "my_href", true, true);  
        for(Object on : ns) {  
            TagNode n = (TagNode) on;  
            System.out.println("\thref="+n.getAttributeByName("href")+", text="+n.getText());  
        }  */

	}

}
