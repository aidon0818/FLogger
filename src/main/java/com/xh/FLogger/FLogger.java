package com.xh.FLogger;

import com.xh.FLogger.constants.Constant;
import com.xh.FLogger.strategy.LogManager;
import com.xh.FLogger.utils.CommUtil;
import com.xh.FLogger.utils.TimeUtil;

public class FLogger {
	
	private static FLogger instance = null;
	private static LogManager logManager;
	
	static {
		logManager = LogManager.getInstance();
	}
	
	public FLogger() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() { 
				close();
			}
		}));
	}
	
	public static synchronized FLogger getInstance() {
		if (instance == null) {
			instance = new FLogger();
		}
		
		return instance;
	}
	
	public void debug(String logMsg) {
		writeLog("debug", Constant.DEBUG, logMsg);
	}
	
	public void info(String logMsg) {
		writeLog("info", Constant.INFO, logMsg);
	}
	
	public void warn(String logMsg) {
		writeLog("warn", Constant.WARN, logMsg);
	}
	
	public void error(String logMsg) {
		writeLog("error", Constant.ERROR, logMsg);
	}
	
	public void fatal(String logMsg) {
		writeLog("fatal", Constant.FATAL, logMsg);
	}

	public void writeLog(String logLevel, String logMsg) {
		
	}
	
	private void writeLog(String logFileName, int logLevel, String logMsg) {
		if (logMsg != null
		&& logLevel <= Integer.valueOf(Constant.CFG_LOG_LEVEL)) {
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			sb.append(Constant.LOG_DESC_MAP.get(String.valueOf(logLevel)));
			sb.append("] ");
			sb.append(TimeUtil.getFullDateTime());
			sb.append(" [");
			sb.append(Thread.currentThread().getName());
			sb.append("] ");
			sb.append(logMsg);
			sb.append("\n");
			logManager.addLog(logFileName, sb);
			
			// print error and fatal msg to terminal
			if (logLevel == Constant.ERROR || logLevel == Constant.FATAL) {
				try {
					System.out.print(new String(sb.toString().getBytes(Constant.CFG_CHARSET_NAME), Constant.CFG_CHARSET_NAME));
				} catch (Exception e) {
					System.out.print(CommUtil.getExpStack(e));
				}
			}
		}
	}
	
	private void close() {
		logManager.close();
	}
}