package com.guoyun.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;


public class CrawlConfig {

	private static String filePath;

	static {
		new CrawlConfig(true);
	}

	public static String getValue(String key) {
		String value = null;
		if ((value = System.getProperty(key)) != null) {
			return value;
		}
		Properties props = null;
		FileInputStream in = null;

		try {
			props = new Properties();
			in = new FileInputStream(new File(filePath));
			if (in != null) {
				props.load(in);
			}

			value = props.getProperty(key);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return value;
	}


	public static boolean IsSystemWindows() {
		boolean result = true;
		String systemName = System.getProperty("os.name");
		if (systemName != null
				&& !systemName.toLowerCase().startsWith("windows")) {
			result = false;
		}
		return result;
	}

	public static void readAndSaveProperties() {
		Properties props = null;
		InputStream in = null;
		FileInputStream fis = null;
		Enumeration en = null;

		String key = null;
		String property = null;
		try {
			props = new Properties();
			fis = new FileInputStream(new File(filePath));
			in = new BufferedInputStream(fis);
			props.load(in);
			en = props.propertyNames();
			while (en.hasMoreElements()) {
				key = (String) en.nextElement();
				property = props.getProperty(key);
				// 为空才覆盖
				if (ConfigUtil.getValue(key) == null
						|| ConfigUtil.getValue(key).equals("")) {
					System.setProperty(key, property);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void SystemInit() {
		new CrawlConfig(true);
	}

	public static void writeProperties(String parameterName,
			String parameterValue) {
		Properties props = null;
		InputStream fis = null;
		OutputStream fos = null;

		try {
			props = new Properties();
			fis = new FileInputStream(new File(filePath));
			if (fis != null) {
				props.load(fis);
			}

			fos = new FileOutputStream(new File(filePath));
			props.setProperty(parameterName, parameterValue);
			props.store(fos, "");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public CrawlConfig() {
		init(null, false);
	}

	public CrawlConfig(boolean isSave) {
		init(null, isSave);

	}

	public CrawlConfig(String fPath, boolean isSave) {
		init(fPath, isSave);
	}

	/**
	 * Initiation
	 * 
	 * @param fPath
	 *            the path of config file
	 * @param isSave
	 *            is save all the properties to memory
	 */
	public void init(String fPath, boolean isSave) {
		if (fPath != null && !fPath.equals("")) {
			CrawlConfig.filePath = filePath;
		} else {
			filePath = Thread.currentThread().getContextClassLoader()
					.getResource(CrawlGlobal.CONFIG_FILE_NAME).getPath();
		}
		if (isSave) {
			// save config
			readAndSaveProperties();
		}
		// init log4j
		String log4jFileName = "log4j_linux.properties";
		if (SystemUtil.IsWindows()) {
			log4jFileName = "log4j_windows.properties";
		}
		String log4jPath = Thread.currentThread().getContextClassLoader()
				.getResource(log4jFileName).getPath();
		PropertyConfigurator.configure(log4jPath);
	}
	
   public static void main(String[] args) {	        
	     CrawlConfig config=new CrawlConfig();
	     System.out.println(CrawlConfig.filePath);
	     System.out.println(CrawlConfig.getValue("StartGetSeedsFrom"));
	     String systemName = System.getProperty("os.name");
	     System.out.println(systemName);
	     

    }

}
