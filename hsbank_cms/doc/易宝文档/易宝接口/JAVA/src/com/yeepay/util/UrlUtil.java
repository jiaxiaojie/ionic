package com.yeepay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UrlUtil {
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

	private static final String URL_PARAM_CONNECT_FLAG = "?";
	private static final String KEY_VALUE_CONNECT_FLAG = "=";
	private static final String PARAM_CONNECT_FLAG = "&";
	
	public static String createTotalURL(String strUrl, Map parameterMap, String reqEncodeCode)
	{
		String content = createContentURL(parameterMap, reqEncodeCode);
		return createTotalURL(strUrl, content);
	}
	public static String createTotalURL(String strUrl, String content)
	{
		String totalURL = strUrl;
		if(totalURL.indexOf(URL_PARAM_CONNECT_FLAG) == -1) {
			totalURL += URL_PARAM_CONNECT_FLAG;
		} else {
			totalURL += PARAM_CONNECT_FLAG;
		}
		totalURL += content;
		return totalURL;
	}
	public static String createContentURL(Map parameterMap, String reqEncodeCode){
		if (null == parameterMap || parameterMap.keySet().size() == 0) {
			return ("");
		}
		StringBuffer url = new StringBuffer();
		Set keys = parameterMap.keySet();
		for (Iterator i = keys.iterator(); i.hasNext(); ) {
			String key = String.valueOf(i.next());
			if (parameterMap.containsKey(key)) {
				Object val = parameterMap.get(key);
				String str = val!=null ? val.toString() : "";
				try {
					str = URLEncoder.encode(str, reqEncodeCode);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url.append(key).append(KEY_VALUE_CONNECT_FLAG).append(str).append(PARAM_CONNECT_FLAG);
			}
		}
		String strURL = "";
		strURL = url.toString();
		if (PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return strURL;
	}

}
