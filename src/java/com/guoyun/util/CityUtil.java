package com.guoyun.util;

import java.util.HashMap;
import java.util.Map;

import com.guoyun.air.CityBelong;

public class CityUtil {
	/**
	 * 城市
	 */
	// 各个直辖市
	public static final String CITY_CHONG_QING = "重庆";
	public static final String CITY_BEI_JING = "北京";
	public static final String CITY_TIAN_JIN = "天津";
	public static final String CITY_SHANG_HAI = "上海";
	
	// 河南省各个城市
	public static final String CITY_LUO_YANG = "洛阳";
	public static final String CITY_PING_DING_SHAN = "平顶山";
	public static final String CITY_AN_YANG = "安阳";
	public static final String CITY_JIAO_ZUO = "焦作";
	public static final String CITY_SAN_MEN_XIA = "三门峡";
	// 湖北省各个城市
	public static final String CITY_WU_HAN = "武汉";
	public static final String CITY_YI_CHANG = "宜昌";
	public static final String CITY_JING_ZHOU = "荆州";
	// 湖南省各个城市
	public static final String CITY_CHANG_SHA = "长沙";
	public static final String CITY_ZHU_ZHOU = "株洲";
	public static final String CITY_XIANG_TAN = "湘潭";
	public static final String CITY_YUE_YANG = "岳阳";
	public static final String CITY_CHANG_DE = "常德";
	public static final String CITY_ZHANG_JIA_JIE = "张家界";
	// 广东省各个城市
	public static final String CITY_GUANG_ZHOU = "广州";
	public static final String CITY_SHAO_GUAN = "韶关";
	public static final String CITY_SHEN_ZHEN = "深圳";
	public static final String CITY_ZHU_HAI = "珠海";
	public static final String CITY_SHAN_TOU = "汕头";
	public static final String CITY_FO_SHAN = "佛山";
	public static final String CITY_ZHAN_JIANG = "湛江";
	public static final String CITY_ZHONG_SHAN = "中山";
	// 广西省各个城市
	public static final String CITY_NAN_NING = "南宁";
	public static final String CITY_LIU_ZHOU = "柳州";
	public static final String CITY_GUI_LIN = "桂林";
	public static final String CITY_BEI_HAI = "北海";
	// 海南省各个城市
	public static final String CITY_HAI_KOU = "海口";
	public static final String CITY_SAN_YA = "三亚";
	// 四川省各个城市
	public static final String CITY_CHENG_DU = "成都";
	public static final String CITY_ZI_GONG = "自贡";
	public static final String CITY_PAN_ZHI_HUA = "攀枝花";
	public static final String CITY_LU_ZHOU = "泸州";
	
	
	
	
	/**
	 * 各个省
	 */
	public static final String PROVINCE_HE_NAN = "河南";
	public static final String PROVINCE_HU_BEI = "湖北";
	public static final String PROVINCE_HU_NAN = "湖南";
	public static final String PROVINCE_GUANG_DONG = "广东";
	public static final String PROVINCE_GUANG_XI = "广西";
	public static final String PROVINCE_HAI_NAN = "海南";
	public static final String PROVINCE_SI_CHUAN = "四川";
	
	/**
	 * 各个区域
	 */
	public static final String DEPOT_HUA_ZHONG = "华中";
	public static final String DEPOT_HUA_NAN = "华南";
	public static final String DEPOT_HUA_DONG = "华东";
	public static final String DEPOT_HUA_BEI = "华北";
	public static final String DEPOT_XI_NAN = "西南";
	public static final String DEPOT_XI_BEI = "西北";
	
	
	public static final Map<String, CityBelong> cityBelongs = 
		new HashMap<String, CityBelong>();
	static{
		cityBelongs.put("洛阳", new CityBelong("洛阳","河南省","华中"))
	}
}
