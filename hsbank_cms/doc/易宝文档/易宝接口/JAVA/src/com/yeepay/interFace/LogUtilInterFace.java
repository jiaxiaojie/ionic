package com.yeepay.interFace;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yeepay.util.HttpUtil;
import com.yeepay.util.ProcessUtil;

public class LogUtilInterFace {
	private static String beforeChangeCode = "iso-8859-1";
	private static String afterChangeCode = "gbk";
	private static String logFileCode = "gbk";
	private Log log = LogFactory.getLog(HttpUtil.class);
	public LogUtilInterFace(Class inClass){
		this.log = LogFactory.getLog(inClass);
	}
	public static LogUtilInterFace createLog(Class inClass){
		return new LogUtilInterFace(inClass);
	}
	public void log(Object obj){
		log.debug(obj);
	}
	public void log(Object obj, String strCode){
		try{
			obj = ProcessUtil.chagneStringCode(obj.toString(), strCode, logFileCode);
		}catch(UnsupportedEncodingException ere) {
			log.debug(ere);
		} 
		log.debug(obj);
	}
	private void debug(Object obj){
		log.debug(obj);
	}
	private void error(Object obj){
		log.error(obj);
	}
	private void fatal(Object obj){
		log.fatal(obj);
	}
	private void info(Object obj){
		log.info(obj);
	}
	private void warn(Object obj){
		log.warn(obj);
	}
	
	private String test(String str){
		final String[] codeStr = {"iso-8859-1", "gb2312", "gbk", "utf-8"};
		for(int i = 0; i < codeStr.length; i++)
		{
			for(int j = 0; j < codeStr.length; j++)
			{
				try{
				log.debug("[" + codeStr[j]+ "] to [" + codeStr[i] + "]:" + "[" + new String(str.getBytes(codeStr[j]),codeStr[i] ) + "]");
				}catch(Exception ex){}
			}
		}
		return str;
		
	}
}
