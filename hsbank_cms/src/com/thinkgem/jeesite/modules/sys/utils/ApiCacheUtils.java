/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.ClientCacheUtils;
import com.thinkgem.jeesite.modules.api.ApiCacheObject;

/**
 * yeepayApi工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class ApiCacheUtils {


	public static final String YEEPAY_CACHE = "yeepayApiCache";
	public static final String YEEPAY_CACHE_KEY = "yeepayApiCacheKey";
	public static final String YEEPAY_CACHE_UUID_ = "uuid_";
	

	
	/**
	 * 根据UUID获取
	 * @param uuid
	 * @return
	 */
	public static ApiCacheObject getByUUID(String  uuid){
		ApiCacheObject apiCacheObject = (ApiCacheObject)ClientCacheUtils.get(YEEPAY_CACHE, YEEPAY_CACHE_UUID_ + uuid);
		return apiCacheObject;
	}
	
	
	/**
	 * 清除指定UUID缓存
	 * @param apiCacheObject
	 */
	public static void clearCache(ApiCacheObject apiCacheObject){
		ClientCacheUtils.remove(YEEPAY_CACHE, YEEPAY_CACHE_UUID_ + apiCacheObject.getUuid());
		Map<String, ApiCacheObject> map = getMap();
		if(apiCacheObject != null && map.keySet().contains(apiCacheObject.getUuid())){
			map.remove(apiCacheObject.getUuid());
		}
		ClientCacheUtils.put(YEEPAY_CACHE, YEEPAY_CACHE_KEY, map);
	}

	/**
	 * 添加缓存
	 * @param apiCacheObject
	 */
	public static void addCache(ApiCacheObject apiCacheObject){
		ClientCacheUtils.put(YEEPAY_CACHE, YEEPAY_CACHE_UUID_ + apiCacheObject.getUuid(), apiCacheObject);
		Map<String, ApiCacheObject> map = getMap();
		map.put(apiCacheObject.getUuid(), apiCacheObject);
		ClientCacheUtils.put(YEEPAY_CACHE, YEEPAY_CACHE_KEY, map);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,ApiCacheObject> getMap() {
		Map<String, ApiCacheObject> map = (Map<String, ApiCacheObject>)CacheUtils.get(YEEPAY_CACHE, YEEPAY_CACHE_KEY);
		if(map == null) {
			map = new HashMap<String, ApiCacheObject>();
			ClientCacheUtils.put(YEEPAY_CACHE, YEEPAY_CACHE_KEY, map);
		}
		return map;
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<ApiCacheObject> getCatchList(){
		Map<String,ApiCacheObject> map = getMap();
		List<ApiCacheObject> list = new ArrayList<ApiCacheObject>(map.values());
		return list;
	}
	
	public static void refreshCache(String uuid) {
		ApiCacheObject apiCacheObject = getByUUID(uuid);
		clearCache(apiCacheObject);
		getByUUID(uuid);
	}
	
	/**
	 * 检测uuid
	 * @param uuid
	 * @return
	 */
	public static boolean checkUUID(String uuid){
		ApiCacheObject apiCacheObject = (ApiCacheObject)ClientCacheUtils.get(YEEPAY_CACHE, YEEPAY_CACHE_UUID_ + uuid);
		if(apiCacheObject != null ){
			return true;
		}
		return false;
	}

}
