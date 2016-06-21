package com.yeepay.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// »’÷æ¿‡
public class LogUtil {
	private static Log log = LogFactory.getLog(LogUtil.class);
	
	public static void debug(String msg) {
		log.debug(msg);
	}
	public static void error(String msg,Throwable e) {
		log.error(msg,e);
	}
	public static void info(String msg) {
		log.error(msg);
	}
}
