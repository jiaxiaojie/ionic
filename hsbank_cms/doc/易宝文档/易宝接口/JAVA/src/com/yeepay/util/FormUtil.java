package com.yeepay.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class FormUtil {
	// 标签名
	private static String FORM = "form";
	private static String INPUT = "input";
	// 属性名
	private static String ACTION = "action";
	private static String METHOD = "method";
	private static String NAME = "name";
	private static String TYPE = "type";
	private static String VALUE = "value";
	// 属性值
	private static String POST = "post";
	private static String GET = "get";
	private static String HIDDEN = "hidden";

	private static String Less = "<";
	private static String Than = ">";
	private static String Slash = "/";
	private static String Space = " ";
	private static String Equal = "=";
	
	public static String createForm(String url, String method, Map parameterMap,String formName, String submitValue){
		StringBuffer returnStringBuffer = new StringBuffer();
		returnStringBuffer.append("<form action=\"" + url + "\" name=" + formName + " method=\"" + method + "\" >\n");
		
		Set set = parameterMap.keySet();
		java.util.Iterator iterator  = set.iterator();
		String name = "";
		String value = "";
		while(iterator.hasNext()){
			name = (String)iterator.next();
			value = (String)parameterMap.get(name);
			returnStringBuffer.append(creatHidden(name, value));
		}
		returnStringBuffer.append(creatSubmit("", submitValue));
		returnStringBuffer.append("</form>\n");
		return returnStringBuffer.toString();
	}
	
	public static StringBuffer createInput(String name, String value, String type){
		StringBuffer returnStringBuffer = new StringBuffer();
			returnStringBuffer.append("<input name=\"" + name + "\" value=\"" + value + "\" type=\"" + type + "\" />\n");

		return returnStringBuffer;
	}
	public static StringBuffer creatSubmit(String name, String value){
		StringBuffer returnStringBuffer = new StringBuffer();
		returnStringBuffer.append("<input type=\"submit\" value=\"" + value + "\" />\n");
		//returnStringBuffer.append(createInput(name, value, "submit"));
		return returnStringBuffer;
	}
	public static StringBuffer creatButton(String name, String value){
		StringBuffer returnStringBuffer = new StringBuffer();
		returnStringBuffer.append(createInput(name, value, "button"));
		return returnStringBuffer;
	}
	public static StringBuffer creatHidden(String name, String value){
		StringBuffer returnStringBuffer = new StringBuffer();
		returnStringBuffer.append(createInput(name, value, "hidden"));
		return returnStringBuffer;
	}
}
