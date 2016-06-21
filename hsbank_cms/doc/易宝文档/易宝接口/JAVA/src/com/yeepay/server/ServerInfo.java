package com.yeepay.server;

public class ServerInfo {
	private static String PageCharsetName = "gbk";
	private static String ServerCharsetName = "iso-8859-1";
	private static String YeepayCharsetName = "gbk";
	
	public static String getPageCharsetName(){
		return PageCharsetName;
	}
	public static void setPageCharsetName(String charsetName){
		PageCharsetName = charsetName;
	}
	public static String getServerCharsetName(){
		return ServerCharsetName;
	}
	public static void setServerCharsetName(String charsetName){
		ServerCharsetName = charsetName;
	}
	public static String getYeepayCharsetName(){
		return YeepayCharsetName;
	}
}
