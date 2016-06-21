package com.yeepay.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import com.yeepay.util.UpgradeMap;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Enumeration;

// 处理对象的类
public class ProcessUtil {
	private static Log log = LogFactory.getLog(ProcessUtil.class);
	// 从request.getParameterMap取出的Map传入获得所有参数与参数值Map的方法
	public static Map processParameterMap(Map parameterMap){
		Map returnMap = new UpgradeMap();
		java.util.Set set = parameterMap.keySet();
		Object[] objs = set.toArray();
		int index = objs.length;
		String key = "";
		String value = "";
		for(int i = 0; i < index; i++ )
		{
			key = objs[i].toString();
			value = ((String[])parameterMap.get(key))[0]; 
			returnMap.put(key, value);
		}
		return returnMap;
	}
	// 将HttpServletRequest传入获得所有参数与参数值Map的方法
	public static Map processParameterMap(HttpServletRequest request, String serverCharsetName, String pageCharsetName){
		final Map returnMap = new UpgradeMap();
		String key = "";
		String value = "";
		Enumeration names = request.getParameterNames();
		while(names.hasMoreElements()){
			key = (String)names.nextElement();
			value = request.getParameter(key);
			try {
				value = new String(value.getBytes(serverCharsetName), pageCharsetName);
			} catch (UnsupportedEncodingException uee) {
				uee.printStackTrace();
			}
			returnMap.put(key, value);
		  }
		return returnMap;
	}
	// 格式化String将null转化为""
	public static String formatString(String str){
		if(str == null){
			str = "";
		}
		return str;
	}

	public static String formatString(Object str){
		String returnString = (String)str;
		if(returnString == null){
			returnString = "";
		}
		return returnString;
	}
	// 中文转码
	public static String chagneStringCode(String str, String beforeChangeCode, String afterChangeCode) throws UnsupportedEncodingException{
		return new String(str.getBytes(beforeChangeCode), afterChangeCode); 
	}
	// 设置固定值的方法
	public static Map setFixParameterValue(Map parameterMap, Map fixParameterMap){
		Object[] keys = fixParameterMap.keySet().toArray();
		int index = keys.length;
		String key = "";
		String value = "";
		for(int i = 0; i < index; i++){
			key = keys[i].toString();
			value = fixParameterMap.get(key).toString();
			parameterMap.put(key, value);
		}
		return parameterMap;
	}
	// 格式化http通讯返回文本流的方法
	public static Map formatReqReturnString(String str, String groupFlag, String keyValueFlag ){
		Map returnMap = new UpgradeMap();
		String[] groups = str.split(groupFlag);
		String[] group = new String[2];
		String key = "";
		String value = "";
		int index = groups.length;

		for(int i = 0; i < index; i++){
			group = groups[i].split(keyValueFlag);
			if(group.length >= 1 ){
				key = group[0];
			}
			if(group.length >= 2 ){
				value = group[1];
			}else{
				value = "";
			}
			returnMap.put(key, value);
		}
		return returnMap; 
	}
	public static void test(String str) throws UnsupportedEncodingException{
		String returnString = "";
							log.debug("[" + str + "];\r\n");
							log.debug("[" + java.net.URLEncoder.encode(str) + "];\r\n");
							log.debug("[" + java.net.URLEncoder.encode(str,"gb2312") + "];\r\n");
							log.debug("[" + java.net.URLEncoder.encode(str,"utf-8") + "];\r\n");
							log.debug("<br/>");
				String[] codeStr = {"iso-8859-1", "gb2312", "gbk", "utf-8"};
				try{
					for(int i = 0; i < codeStr.length; i++)
					{
						for(int j = 0; j < codeStr.length; j++)
						{
							log.debug("[" + codeStr[j]+ "] to [" + codeStr[i] + "];\r\n");
							log.debug("[" + new String(str.getBytes(codeStr[j]),codeStr[i] ) + "];\r\n");
							log.debug("[" + new String(str.getBytes(codeStr[j]),codeStr[i] ) + "];\r\n");
							log.debug("[" + java.net.URLEncoder.encode(new String(str.getBytes(codeStr[j]),codeStr[i] )) + "];\r\n");
							log.debug("[" + java.net.URLEncoder.encode(new String(str.getBytes(codeStr[j]),codeStr[i] ),"gb2312") + "];\r\n");
							log.debug("[" + java.net.URLEncoder.encode(new String(str.getBytes(codeStr[j]),codeStr[i] ),"utf-8") + "];\r\n");
							log.debug("<br/>");
						}
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
	}
	
	public static Map changeMapCode(Map parameterMap, String beforeChangeCode, String afterChangeCode){
		Map returnMap = new UpgradeMap();
		java.util.Set set = parameterMap.keySet();
		java.util.Iterator iterator  = set.iterator();
		String key = "";
		String value = "";
		while(iterator.hasNext()){
			key = (String)iterator.next();
			value = (String)parameterMap.get(key);
			value = changeStringCode(value, beforeChangeCode, afterChangeCode);
			returnMap.put(key, value);
		}
		return returnMap;
	}
	public static String changeStringCode(String string, String beforeChangeCode, String afterChangeCode){
		try {
			string = new String(string.getBytes(beforeChangeCode), afterChangeCode);
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
		return string;
	}
	public static void logTotalURL(HttpServletRequest request){
		String queryString = request.getQueryString();
		if(queryString == null){
			queryString = "";
		}
		log.debug("[RequestURL]" + request.getRequestURL() + "[QueryString]" + queryString);
	}
}
