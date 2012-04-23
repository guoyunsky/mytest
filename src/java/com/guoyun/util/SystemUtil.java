package com.guoyun.util;

public class SystemUtil {
	/**
	 * Get the System operation,true:Windows;false:Linux
	 * 
	 * @return
	 */
	public static boolean IsWindows() {
		if (System.getProperty("os.name").startsWith("Windows")) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("os.name"));
		System.out.println(IsWindows());
	}
}
