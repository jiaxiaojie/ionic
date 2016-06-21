package com.yeepay.interFace;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.yeepay.util.HttpUtil;
import com.yeepay.util.ProcessUtil;
import com.yeepay.util.UpgradeMap;

public class HttpUtilInterFace {
	private static final String Get = "get";
	private static final String Post = "post";
	private static final String reqEncodeCode = com.yeepay.server.ServerInfo.getYeepayCharsetName();
	private static LogUtilInterFace logUtil = LogUtilInterFace.createLog(HttpUtilInterFace.class);
	// 发送http通讯请求
	public static String sendRequest(String url, Map parameter, String method){
		String returnString = null;
		String content = HttpUtil.getContentURL(parameter, reqEncodeCode);
		returnString = sendRequest(url, content, method);
		return returnString;
	}
	public static String sendRequestDefault(String url, Map parameter){
		return sendRequest(url, parameter, Get);
	}
	// 发送http通讯请求
	public static String sendRequest(String url, String content, String method){
		String returnString = null;
		logUtil.log("[url]" + url + ",[content]" + content );
		try{
			if(Post.equalsIgnoreCase(method)){
				returnString = HttpUtil.URLPost(url, content).toString();
			}else{
				returnString = HttpUtil.URLGet(url, content).toString();
			}
		}catch(IOException ioe){
			logUtil.log("[IOException]" + ioe);
		}
		logUtil.log("[returnString]" + returnString);
		return returnString;
	}
	public static String sendRequest(String url, String method){
		String returnString = null;
		logUtil.log("[url]" + url);
		try{
			if(Post.equalsIgnoreCase(method)){
				returnString = HttpUtil.URLPost(url, "").toString();
			}else{
				returnString = HttpUtil.URLGet(url, "").toString();
			}
		}catch(IOException ioe){
			logUtil.log("[IOException]" + ioe);
		}
		logUtil.log("[returnString]" + returnString);
		return returnString;
	}
	// 发送http通讯请求
	public static String sendRequestDefault(String url, String[] HmacOrder, Map FixParameter, Map parameter, String keyValue){
		parameter = ProcessUtil.setFixParameterValue(parameter, FixParameter);
		parameter = DigestUtilInterFace.addHmac(HmacOrder, parameter, keyValue);
		String str = sendRequest(url, parameter, Get);
		return str;
	}
	public static Map formatMap(Map map, String byteCharsetName, String charsetName){
		Map returnMap = new UpgradeMap();
		java.util.Set set = map.keySet();
		Object[] objs = set.toArray();
		int index = objs.length;
		String key = "";
		String value = "";
		for(int i = 0; i < index; i++ )
		{
			key = objs[i].toString();
			value = (String)map.get(key);
			try {
				value = new String(value.getBytes(byteCharsetName), charsetName);
			} catch (UnsupportedEncodingException ere) {
				logUtil.log(ere);
			} 
			returnMap.put(key, value);
		}
		return returnMap;
	}
}
