package com.guoyun.main;

import java.text.ParseException;

import redstone.xmlrpc.XmlRpcFault;

public class CnemcMain2 {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws XmlRpcFault 
	 */
	public static void main(String[] args) throws ParseException, XmlRpcFault {
		CnemcMain main = new CnemcMain();
		
		main.writeBlogByDay(main.getTodayDate());
		// main.getIaqdao().queryExcept("2010-05-07");
		
	}

}
