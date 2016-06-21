package com.yeepay.interFace;

import com.yeepay.util.Configuration;

public class ConfigurationInterFace {
	public static String getValue(String key) {
		return (Configuration.getInstance().getValue(key));
	}
}
