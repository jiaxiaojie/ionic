/**
 * 
 */
package com.thinkgem.jeesite.common.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.PropertiesUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * @author 万端瑞
 *
 */
public class EntityVoMapping {
	public static PropertiesUtils propertiesUtils = null;
	
	static{
		propertiesUtils = new PropertiesUtils("entity-vo-mapping.properties");
	}
	
	private static Map<String,Map<String,String>> fieldMappings = new LinkedHashMap<String, Map<String,String>>();
	public static Map<String,String>  getFieldMapping(String key,Class<?> clazz){
		if(key == null){
			key = clazz.getSimpleName();
		}
		Map<String,String> fieldMapping = fieldMappings.get(key);
		if(fieldMapping==null){
			fieldMapping = StringUtils.toMap(propertiesUtils.getConfig(key), ";", ":", false);
			fieldMappings.put(key, fieldMapping);
		}
		return fieldMapping;
	}
	public static Map<String,String> getFieldMapping(Class<?> clazz){
		return getFieldMapping(null,clazz);
	}
	
	public static Map<String,String> getFieldMapping(String key){
		return getFieldMapping(key,null);
	}
	
	public static void main(String[] args) {
		System.out.println(EntityVoMapping.getFieldMapping("CurrentProjectInfoDetail"));
	}
}
