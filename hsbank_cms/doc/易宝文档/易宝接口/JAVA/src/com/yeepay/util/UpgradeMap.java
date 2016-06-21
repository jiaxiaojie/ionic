package com.yeepay.util;

import java.util.HashMap;

public class UpgradeMap extends HashMap {
	public Object get(Object key){
		Object str = super.get(key);
		return ((str != null) ? (str) : (""));
	}
}
