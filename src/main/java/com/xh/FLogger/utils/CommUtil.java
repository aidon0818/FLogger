package com.xh.FLogger.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;

import com.xh.FLogger.constants.Constant;

/**
 * common tools
 * @author xiaohe
 *
 */
public class CommUtil {
	private static HashMap<String, Object[]> propsMap = new HashMap<String, Object[]>();
	
	public static String getConfigByString(String keyName, String defaultValue) {
		String value = getConfig(keyName);
		if (value != null && value.length() > 0) {
			return value;
		}
		return defaultValue;
	}
	
	public static long getConfigByLong(String keyName, long defaultValue) {
		String value = getConfig(keyName);
		if (value != null && value.length() > 0) {
			return Long.parseLong(value);
		}
		
		return defaultValue;
	}
	
	private static String getConfig(String keyName) {
		return CommUtil.getConfig("log.properties", keyName);
	}
	
	private static String getConfig(String filePath, String keyName) {
		Properties props = null;
		boolean isNeedLoadCfg = false;
		
		File f = new File(filePath);
		if (! f.exists()) {
			return null;
		}
		Object [] arr = propsMap.get(filePath);
		if (arr == null) {
			isNeedLoadCfg = true;
		} else {
			
			Long lastModify = (Long)arr[0];
			if (lastModify != f.lastModified()) {
				isNeedLoadCfg = true;
			} else {
				props = (Properties)arr[1];
			}
		}
		
		if (isNeedLoadCfg) {
			FileInputStream cfgFIS = null;
			try {
				cfgFIS = new FileInputStream(f);
				props = new Properties();
				props.load(cfgFIS);
				propsMap.put(filePath, new Object[]{f.lastModified(), props});
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					cfgFIS.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		return props.getProperty(keyName, "");
	}
	
	public static byte [] StringToBytes(String str) {
		try {
			if (str == null || str.length() <= 0) {
				return new byte[0];
			} else {
				return str.getBytes(Constant.CFG_CHARSET_NAME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getExpStack(Exception e) {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(bo);
		e.printStackTrace(pw);
		pw.flush();
		pw.close();
		return bo.toString();
	}
}
