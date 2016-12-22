package com.xh.FLogger.constants;

import java.util.HashMap;
import java.util.Map;

import com.xh.FLogger.utils.CommUtil;

public final class Constant {
	// ------ log level
	public final static int FATAL	= 0;
	public final static int ERROR	= 1;
	public final static int WARN	= 2;
	public final static int INFO	= 3;
	public final static int DEBUG	= 4;
	
	/* log level */
	public static String CFG_LOG_LEVEL = CommUtil.getConfigByString("LOG_LEVEL", "2");
	
	/* char set */
	public static String CFG_CHARSET_NAME = CommUtil.getConfigByString("CHARSET_NAME", "UTF-8");
	
	/* log file path */
	public static String CFG_LOG_PATH = CommUtil.getConfigByString("LOG_PATH", "./log");
	
	@SuppressWarnings("serial")
	public static Map<String, String> LOG_DESC_MAP = new HashMap<String, String>() {{
		put("0", "FATAL");
		put("1", "ERROR");
		put("2", "WARN");
		put("3", "INFO");
		put("4", "DEBUG");
	}};
}