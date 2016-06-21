package com.yeepay.interFace;

import java.util.Map;

import com.yeepay.util.FormUtil;

public class FormUtilInterFace {
	private static LogUtilInterFace logUtil = LogUtilInterFace.createLog(FormUtilInterFace.class);
	private static String pageEncodeCode = "iso-8859-1";
	public static String createForm(String url, String method, Map parameterMap, String formName, String submitValue){
		String returnString = FormUtil.createForm(url, method, parameterMap, formName, submitValue);
		logUtil.log("[form]" + returnString, pageEncodeCode);
		return returnString;
	}
	public static String createFormDefault(String url, Map parameterMap,String formName, String submitValue){
		String returnString = createForm(url, "post", parameterMap, formName, submitValue);
		return returnString;
	}
	
}
