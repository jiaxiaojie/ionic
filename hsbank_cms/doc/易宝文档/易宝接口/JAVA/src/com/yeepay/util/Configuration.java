package com.yeepay.util;

import java.util.ResourceBundle;

// 从配置文件中获得配置信息
public class Configuration {
	private static Object lock = new Object();
	private static Configuration config = null;
	private static ResourceBundle rb = null;
	private static final String CONFIG_FILE = "merchantInfo";
	
	private Configuration() {
		rb = ResourceBundle.getBundle(CONFIG_FILE);
	}
	public static Configuration getInstance() {
		synchronized(lock) {
			if(null == config) {
				config = new Configuration();
			}
		}
		return (config);
	}
	public String getValue(String key) {
		return (rb.getString(key));
	}
}