package com.xh.FLogger.utils;

import java.util.Calendar;

/**
 * Time tools
 * @author xiaohe
 *
 */
public class TimeUtil {
	/**
	 * 获取完全格式的的日期格式
	 * @return 格式如 2015-10-31 10:33:25:012
	 */
	public static String getFullDateTime() {
		StringBuffer sb = new StringBuffer(30);
		Calendar c = Calendar.getInstance();

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);	// 24 hours
		int min = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		int milliSec = c.get(Calendar.MILLISECOND);
		
		// append year
		sb.append(year);
		
		sb.append("-");
		
		// append month
		if (month < 10) {
			sb.append("0" + month);
		} else {
			sb.append(month);
		}
		
		sb.append("-");
		
		// append day
		if (day < 10) {
			sb.append("0" + day);
		} else {
			sb.append(day);
		}
		
		sb.append(" ");
		
		// append hour
		if (hour < 10) {
			sb.append("0" + hour);
		} else {
			sb.append(hour);
		}
		
		sb.append(":");
		
		// append minute
		if (min < 10) {
			sb.append("0" + min);
		} else {
			sb.append(min);
		}
		
		sb.append(":");
		
		// append second
		if (second < 10) {
			sb.append("0" + second);
		} else {
			sb.append(second);
		}
		
		sb.append(",");
		
		// append millisecond
		if (milliSec < 100) {
			sb.append("0" + milliSec);
		} else if (milliSec < 10) {
			sb.append("00" + milliSec);
		} else {
			sb.append(milliSec);
		}
		
		return sb.toString();
	}
	
	/**
	 * 获取机器时间
	 * @param c 分隔符
	 * @return 年月日，格式如2015-10-31
	 */
	public static String getPCDate(char sep) {
		StringBuffer sb = new StringBuffer(20);
		Calendar c = Calendar.getInstance();
		
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		sb.append(year);
		
		sb.append(sep);
		
		if (month < 10) {
			sb.append("0");
		}
		sb.append(month);
		
		sb.append(sep);
		
		if (day < 10) {
			sb.append("0");
		}
		sb.append(day);
		
		return sb.toString();
	}
	
	/**
	 * 获取机器时间
	 * @return 年月日，格式如20151031
	 */
	public static String getPCDate() {
		StringBuffer sb = new StringBuffer(20);
		Calendar c = Calendar.getInstance();
		
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		sb.append(year);
		
		if (month < 10) {
			sb.append("0");
		}
		sb.append(month);
		
		if (day < 10) {
			sb.append("0");
		}
		sb.append(day);
		
		return sb.toString();
	}
	
	/**
	 * 获取当前时间
	 * @return 时分秒，格式如122415，12时24分15秒
	 */
	public static String getCurrTime() {
		StringBuffer sb = new StringBuffer(20);
		Calendar c = Calendar.getInstance();
		
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);
		
		if (hour < 10) {
			sb.append("0");
		}
		sb.append(hour);
		
		if (min < 10) {
			sb.append("0");
		}
		sb.append(min);
		
		if (sec < 10) {
			sb.append("0");
		}
		sb.append(sec);
		
		return sb.toString();
	}
}
