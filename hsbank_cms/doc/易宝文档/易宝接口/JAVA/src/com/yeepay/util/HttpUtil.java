package com.yeepay.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

// http通讯类
public class HttpUtil {
	// url中每组参数间的分隔符
	private static final String PARAM_CONNECT_FLAG = "&";
	// url中地址与参数间的分隔符
	private static final String URL_PARAM_CONNECT_FLAG = "?";
	// url中每组参数中键与值间的分隔符
	private static final String KEY_VALUE_CONNECT_FLAG = "=";
	// 取文本流时的缓存大小
	private static int tempLength = 1024; 
	private static String encodeCode = "iso-8859-1";
	
	private HttpUtil() {	
	}
	public static StringBuffer URLGet(String strUrl, Map parameterMap, String encodeCode, String reqEncodeCode) throws IOException {
		String strTotalURL = "";
		strTotalURL = getTotalURL( strUrl, parameterMap, reqEncodeCode);
		URL url = new URL(strTotalURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setFollowRedirects(true);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		return getStringBufferFromBufferedReader(in);
	}
	public static StringBuffer URLGet(String strUrl, String content) throws IOException {
		String strTotalURL = "";
		strTotalURL = getTotalURL( strUrl, content);
		URL url = new URL(strTotalURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setFollowRedirects(true);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		return getStringBufferFromBufferedReader(in);
	}
	public static StringBuffer URLPost(String strUrl, Map parameterMap, String reqEncodeCode) throws IOException {
		String content = getContentURL(parameterMap, reqEncodeCode);
		URL url = new URL(strUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		int length = con.getContentLength();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setAllowUserInteraction(false);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
		BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		bout.write(content);
		bout.flush();
		bout.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		return getStringBufferFromBufferedReader(in);
	}
	public static StringBuffer URLPost(String strUrl, String content) throws IOException {
		URL url = new URL(strUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		//int length = con.getContentLength();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setAllowUserInteraction(false);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
		BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		bout.write(content);
		bout.flush();
		bout.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		return getStringBufferFromBufferedReader(in);
	}
	private static StringBuffer getStringBufferFromBufferedReader(BufferedReader in) throws IOException{
		StringBuffer returnStringBuffer = new StringBuffer();
		char[] tmpbuf= new char[tempLength];
		int num = in.read(tmpbuf);
		while(num != -1){
			returnStringBuffer.append(tmpbuf, 0, num);
			num = in.read(tmpbuf);
		}
		in.close();
		return returnStringBuffer;
		
	}
	public static String getTotalURL(String strUrl, Map parameterMap, String reqEncodeCode)
	{
		String content = getContentURL(parameterMap, reqEncodeCode);
		return getTotalURL(strUrl, content);
	}
	public static String getTotalURL(String strUrl, String content)
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
	public static String getContentURL(Map parameterMap, String reqEncodeCode){
		if (null == parameterMap || parameterMap.keySet().size() == 0) {
			return ("");
		}
		StringBuffer url = new StringBuffer();
		Set keys = parameterMap.keySet();
		for (Iterator i = keys.iterator(); i.hasNext(); ) {
			String key = String.valueOf(i.next());
			if (parameterMap.containsKey(key)) {
				Object val = parameterMap.get(key);
				String str = val!=null?val.toString():"";
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