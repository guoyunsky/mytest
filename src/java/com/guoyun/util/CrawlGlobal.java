package com.guoyun.util;


/**
 * The global value of this project
 * 
 * @author Mickey
 * 
 */
public final class CrawlGlobal {
	public static String CONFIG_FILE_NAME = "crawl_linux.properties";
	// 爬虫启动获取种子方式：DATABASE，SEED_WITH_KIND，SEED
	public static final String CRAWL_START_GET_SEEDS_FROM = "CRAWL_START_GET_SEEDS_FROM";
	public static final String CRAWL_START_BY_SEED = "0"; // 通过seeds.txt启动
	public static final String CRAWL_START_BY_DATABASE = "1"; // 通过数据库启动
	public static final String CRAWL_START_BY_SEED_ADN_DATABASE = "2"; // 通过seeds.txt和数据库一起启动

	// 获得种子后是否强制进行抓取,0:不强制；1:强制
	public static final String GET_SEED_FORCE_CRAWL = "GET_SEED_FORCE_CRAWL";
	public static final String IMPORT_TABLE_NAME_START_PRODUCT = "TMP_PRODUCT_START";
	public static final String IMPORT_TABLE_NAME_START_VENDOR = "TMP_VENDOR_START";

	public static final String IMPORT_TABLE_NAME_TASK_PRODUCT = "TMP_PRODUCT_TASK";
	public static final String IMPORT_TABLE_NAME_TASK_VENDOR = "TMP_VENDOR_TASK";

	public static final String IMPORT_TABLE_PRODUCT_FILE_NAME = "product_export.txt";
	public static final String IMPORT_TABLE_VENDOR_FILE_NAME = "vendor_export.txt";

	public static boolean IS_START_SYNCHRONIZED_PRODUCT_OVER = false;
	public static boolean IS_START_SYNCHRONIZED_VENDOR_OVER = false;

	public static final String TABLE_COL_SPLIT = "\t";

	public static final String TABLE_FIELD_ECLOSED = "\"";
	public static final String NEW_LINE = "\n";

	public static final String CRAWL_INSERT_DATAS = "INSERT_DATAS";    // 爬虫结果集，需要插入数据库
	public static final String CRAWL_NEW_URLS = "NEW_URLS";            // 爬虫URLS
	
	public static final String EXTRACTOR_URL="eUrl";   // 抽取出来的URL
	public static final String WRITE_URL="wUrl";       // 可写URL,数据可能要保存到数据库
	
	public static final String SITE_ID="sID";
	public static final String CHARSET="charset";
	
	public static final String URL_MD5="urlMD5";
	public static final String FETCH_SPECIAL="fetchSpecial";           // 需要特殊获取网页内容的URL

	static {
		if (SystemUtil.IsWindows()) {
			CONFIG_FILE_NAME = "crawl_windows.properties";
		}
	}

}
