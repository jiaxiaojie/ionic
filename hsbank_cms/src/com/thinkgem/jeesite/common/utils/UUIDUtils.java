package com.thinkgem.jeesite.common.utils;


public class UUIDUtils {
	public static void main(String[] args){
		System.out.println(getToken().length());
		System.out.println(getToken());
	}
	
	/**
	 * 生成token
	 * @return
	 */
	public static String getToken() {
		String str = IdGen.uuid();
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		return sb.toString();
	}
}
