package com.yeepay.interFace;

import java.util.Map;

import com.yeepay.util.UrlUtil;
import com.yeepay.server.ServerInfo;

public class UrlUtilInterFace {
	private static LogUtilInterFace logUtil = LogUtilInterFace.createLog(UrlUtilInterFace.class);
	private static String reqEncodeCode = ServerInfo.getYeepayCharsetName();
	private static final String URL_PARAM_CONNECT_FLAG = "?";

	public static String createURL(String strUrl, Map parameterMap){
		String returnString = strUrl + URL_PARAM_CONNECT_FLAG + UrlUtil.createContentURL(parameterMap, reqEncodeCode);
		logUtil.log("[URL]" + returnString);
		return returnString;
	}
}
