package com.xh.FLogger.strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xh.FLogger.constants.Constant;
import com.xh.FLogger.utils.CommUtil;
import com.xh.FLogger.utils.TimeUtil;

public class LogManager extends Thread {
	
	/* singleton */
	private static LogManager instance = null;
	
	/* */
	private Map<String, LogFileItem> logFileMap = new ConcurrentHashMap<String, LogFileItem>();
	
//	private static LogFileItem logFileItem = null;
	
	public final static long WRITE_LOG_INV_TIME = CommUtil.getConfigByLong("WRITE_LOG_INV_TIME", 1000);
	public final static long SINGLE_LOG_FILE_SIZE = CommUtil.getConfigByLong("SINGLE_LOG_FILE_SIZE", 10 * 1024 * 1024);
	public final static long SINGLE_LOG_CACHE_SIZE = CommUtil.getConfigByLong("SINGLE_LOG_CACHE_SIZE", 10 * 1024);
	
	private boolean bIsRun = true;
	
	private LogManager() {
		
	}
	
	public static LogManager getInstance() {
		if (instance == null) {
			instance = new LogManager();
			instance.setName("FLogger");
			instance.start();
		}
		
		return instance;
	}
	
	/**
	 * 添加日志
	 * @param logFileName  日志文件名称
	 * @param logMsg      日志内容
	 */
	public void addLog(String logFileName, StringBuffer logMsg) {
		LogFileItem lfi = logFileMap.get(logFileName);
		if (lfi == null) {
			synchronized (this) {
				lfi = logFileMap.get(logFileName);
				if (lfi == null) {
					lfi = new LogFileItem();
					lfi.nextWriteTime = System.currentTimeMillis() + WRITE_LOG_INV_TIME;
					lfi.logFileName = logFileName;
					logFileMap.put(logFileName, lfi);
				}
			}
		}
		
		synchronized (lfi) {
			if (lfi.currLogBuff == 'A') {
				lfi.alLogBufA.add(logMsg);
			} else {
				lfi.alLogBufB.add(logMsg);
			}
			
			lfi.currCacheSize += CommUtil.StringToBytes(logMsg.toString()).length;
		}
	}
	
	public void run() {
		int i = 0;
		while (bIsRun) {
			try {
				flush(false);
				// reload log level
				if (i++ % 100 == 0) {
					Constant.CFG_LOG_LEVEL = CommUtil.getConfigByString("LOG_LEVEL", "2");
					i = 1;
				}
			} catch (Exception e) {
				System.out.println("start log service failed ...");
				e.printStackTrace();
			}
		}
	}
	
	public void close() {
		bIsRun = false;
		try {
			flush(true);
		} catch (Exception e) {
			System.out.println("close log service failed ...");
			e.printStackTrace();
		}
	}
	
	private void flush(boolean bIsForce) throws IOException {
		long currTime = System.currentTimeMillis();
		Iterator<String> iter = logFileMap.keySet().iterator();
		while (iter.hasNext()) {
			LogFileItem lfi = logFileMap.get(iter.next());
			if (currTime >= lfi.nextWriteTime
			|| SINGLE_LOG_CACHE_SIZE <= lfi.currCacheSize
			|| bIsForce) {
				ArrayList<StringBuffer> alWriteLog = null;
				synchronized (lfi) {
					if (lfi.currLogBuff == 'A') {
						alWriteLog = lfi.alLogBufA;
						lfi.currLogBuff = 'B';
					} else {
						alWriteLog = lfi.alLogBufB;
						lfi.currLogBuff = 'A';
					}
					lfi.currCacheSize = 0;
				}
				
				createLogFile(lfi);
				
				int iWriteSize = writeToFile(lfi.fullLogFileName, alWriteLog);
				lfi.currLogSize += iWriteSize;
			}
		}
	}
	
	private void createLogFile(LogFileItem lfi) {
		// current system date
		String currPCDate = TimeUtil.getPCDate('-');
		
		// rotate
		if (lfi.fullLogFileName != null
		&& lfi.fullLogFileName.length() > 0
		&& lfi.currLogSize >= LogManager.SINGLE_LOG_FILE_SIZE) {
			File oldFile = new File(lfi.fullLogFileName);
			if (oldFile.exists()) {
				String newFileName = Constant.CFG_LOG_PATH + "/"
						+ lfi.lastPCDate + "/"
						+ lfi.logFileName + "_"
						+ TimeUtil.getPCDate() + "_"
						+ TimeUtil.getCurrTime() + ".log";
				File newFile = new File(newFileName);
				oldFile.renameTo(newFile);
				lfi.fullLogFileName = "";
				lfi.currLogSize = 0;
			}
		}
		
		if (lfi.fullLogFileName == null
		|| lfi.fullLogFileName.length() <= 0
		|| lfi.lastPCDate.equals(currPCDate) == false) {
			String sDir = Constant.CFG_LOG_PATH + "/" + currPCDate;
			File file = new File(sDir);
			if (!file.exists()) {
				file.mkdirs();
			}
			lfi.fullLogFileName = sDir + "/" + lfi.logFileName + ".log";
			lfi.lastPCDate = currPCDate;
			
			file = new File(lfi.fullLogFileName);
			if (file.exists()) {
				lfi.currLogSize = file.length();
			} else {
				lfi.currLogSize = 0;
			}
		}
	}
	
	private int writeToFile(String sFullFileName, ArrayList<StringBuffer> sbLogMsg) throws IOException {
		OutputStream fos = null;
		int size = 0;
		
		try {
			fos = new FileOutputStream(sFullFileName, true);
			for (StringBuffer sb : sbLogMsg) {
				byte [] tmpBytes = CommUtil.StringToBytes(sb.toString());
				fos.write(tmpBytes);
				size += tmpBytes.length;
			}
			fos.flush();
			sbLogMsg.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
		
		return size;
	}
}
