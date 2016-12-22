package com.xh.FLogger.strategy;

import java.util.ArrayList;

public class LogFileItem {
	/* not include path */
	public String logFileName = "";
	
	/* full log file path */
	public String fullLogFileName = "";
	
	public long currLogSize = 0;
	
	public char currLogBuff = 'A';
	
	/* log cache list A */
	public ArrayList<StringBuffer> alLogBufA = new ArrayList<StringBuffer>();
	
	/* log cache list B */
	public ArrayList<StringBuffer> alLogBufB = new ArrayList<StringBuffer>();
	
	/* next flush time */
	public long nextWriteTime = 0;
	
	/*  */
	public String lastPCDate = "";
	
	public long currCacheSize = 0;
}