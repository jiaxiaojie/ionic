/**
 * 
 */
package com.thinkgem.jeesite.common.utils;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author 万端瑞
 *
 */
public class PropertiesUtils {
	
	/**
	 * 保存全局属性值
	 */
	private  Map<String, String> map = Maps.newHashMap();
	
	private PropertiesLoader loader = null;
	
	public PropertiesUtils(String propertiesName){
		loader = new PropertiesLoader(propertiesName);
	}
	
	/**
	 * 获取配置
	 * @see ${fns:getAPIConfig('adminPath')}
	 */
	public String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
}
