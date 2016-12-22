package com.xh.FLogger;

public class TestFLogger {
	public static void main(String [] args) {
		FLogger myLog = FLogger.getInstance();
		myLog.debug("This is a debug log message");
		myLog.info("This is a info log message");
		myLog.warn("This is a warning log message");
		myLog.error("This is a error log message");
		myLog.fatal("This is a fatal log message");
	}
}
