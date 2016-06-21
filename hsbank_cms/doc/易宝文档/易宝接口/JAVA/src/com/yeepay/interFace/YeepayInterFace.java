package com.yeepay.interFace;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yeepay.server.ServerInfo;
import com.yeepay.util.ProcessUtil;
import com.yeepay.util.UpgradeMap;
import com.yeepay.interFace.FormUtilInterFace;
import com.yeepay.interFace.UrlUtilInterFace;

public class YeepayInterFace {
	public static final String CHECKHMAC = "checkHmac";
	public static final String ResultString = "resultString";
	public static final String Parameter = "parameter";
	public static final String HTTPConnection = "httpConnection";

	public static final String GroupFlag = "\n";
	public static final String KeyValueFlag = "=";

	private static LogUtilInterFace logUtil = LogUtilInterFace.createLog(YeepayInterFace.class);
	
	// 获得通讯的返回文本流
	public static String getRequestBackString(HttpServletRequest request, Map fixParameter, String[] hmacOrder, String url, String keyValue ){
		Map parameter = ProcessUtil.processParameterMap(request, ServerInfo.getServerCharsetName(), ServerInfo.getPageCharsetName());
		parameter = ProcessUtil.setFixParameterValue(parameter, fixParameter);
		parameter = DigestUtilInterFace.addHmac(hmacOrder, parameter, keyValue);
		String returnString = HttpUtilInterFace.sendRequestDefault(url, parameter);

		return returnString;
	}
	// 获得通讯的返回提交参数
	public static Map getRequestBackMap(HttpServletRequest request, Map fixParameter, String[] hmacOrder, String[] backHmacOrder, String url, String keyValue ){
		Map returnMap = new UpgradeMap();
		
		String reqResult = getRequestBackString(request, fixParameter, hmacOrder, url, keyValue );
		if(reqResult == null){
			returnMap.put(HTTPConnection, false);
		}else{
			returnMap.put(HTTPConnection, true);
			Map parameterMap = ProcessUtil.formatReqReturnString(reqResult, GroupFlag, KeyValueFlag);
			returnMap.put(CHECKHMAC, DigestUtilInterFace.checkHmac(backHmacOrder, parameterMap,  keyValue));
			returnMap.put(Parameter, parameterMap);
			//returnMap.put(resultString, reqResult);
		}
		return returnMap;
	}
	// 获得提交的url
	public static String getRequestUrl(HttpServletRequest request, Map fixParameter, String url, String[] hmacOrder, String keyValue){
		Map parameterMap = ProcessUtil.processParameterMap(request, ServerInfo.getServerCharsetName(), ServerInfo.getYeepayCharsetName());
		parameterMap = ProcessUtil.setFixParameterValue(parameterMap, fixParameter);
		parameterMap = DigestUtilInterFace.addHmac(hmacOrder, parameterMap, keyValue);

		return UrlUtilInterFace.createURL(url, parameterMap);
	}
	// 获得提交的form表单
	public static String getRequestForm(HttpServletRequest request, String formName, String submitValue, Map fixParameter, String[] hmacOrder, String keyValue, String url){
		Map parameterMap = ProcessUtil.processParameterMap(request, ServerInfo.getServerCharsetName(), ServerInfo.getYeepayCharsetName());
		parameterMap = ProcessUtil.setFixParameterValue(parameterMap, fixParameter);
		parameterMap = DigestUtilInterFace.addHmac(hmacOrder, parameterMap, keyValue);

		return FormUtilInterFace.createFormDefault(url, ProcessUtil.changeMapCode(parameterMap, ServerInfo.getYeepayCharsetName(), ServerInfo.getServerCharsetName()), formName, submitValue);
	}
	// 获得callback接收到的返回参数
	public static Map getCallbackMap(HttpServletRequest request, String[] callbackHmacOrder, String keyValue ){
		logUtil.log("[method]" + request.getMethod() + ",[queryString]" + request.getQueryString() + ",[requestURL]" + request.getRequestURL());// + ",[Parameter]" + request.getParameterNames());
		Map returnMap = new UpgradeMap();
		Map parameterMap = ProcessUtil.processParameterMap(request, ServerInfo.getServerCharsetName(), ServerInfo.getYeepayCharsetName());
		returnMap.put(CHECKHMAC, DigestUtilInterFace.checkHmac(callbackHmacOrder, parameterMap, keyValue));
		returnMap.put(Parameter, parameterMap);
		return returnMap;
	}
	
	public static String getParameterString(HttpServletRequest request, String serverCharsetName, String pageCharsetName){
		StringBuffer returnString = new StringBuffer();
		Enumeration names = request.getParameterNames();
		String key = "";
		String value = "";
		while(names.hasMoreElements()){
			key = (String)names.nextElement();
			value = request.getParameter(key);
			try {
				value = new String(value.getBytes(serverCharsetName), pageCharsetName);
			} catch (UnsupportedEncodingException uee) {
				uee.printStackTrace();
			}
			returnString.append(key + ":" + value + "|");
		  }
		return returnString.toString();
	}
	
}
