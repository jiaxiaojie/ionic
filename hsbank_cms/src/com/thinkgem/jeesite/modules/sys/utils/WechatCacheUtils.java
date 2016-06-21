/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.HashMap;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.api.WechatCacheObject;

/**
 * 微信SDK 签名缓存工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class WechatCacheUtils {


	public static final String WECHAT_CACHE = "wechatApiCache";
	public static final String WECHAT_CACHE_KEY = "wechatCacheKey";
	

	
	/**
	 * 添加缓存
	 * @param marketingActivityInfo
	 */
	public static void add(WechatCacheObject wechatCacheObject) {
		if(wechatCacheObject != null) {
			Map<String,WechatCacheObject> map = getMap();
			map.put(wechatCacheObject.getCacheKey(), wechatCacheObject);
			CacheUtils.put(WECHAT_CACHE, WECHAT_CACHE_KEY, map);
		}
	}
	
	/**
	 * 移除缓存
	 * @param marketingActivityInfo
	 */
	public static void remove(WechatCacheObject wechatCacheObject) {
		Map<String,WechatCacheObject> map = getMap();
		if(wechatCacheObject != null && map.keySet().contains(wechatCacheObject.getCacheKey())) {
			map.remove(wechatCacheObject.getCacheKey());
		}
		CacheUtils.put(WECHAT_CACHE, WECHAT_CACHE_KEY, map);
	}
	
	public static WechatCacheObject get(String cacheKey) {
		Map<String,WechatCacheObject> map = getMap();
		return map.get(cacheKey);
	}
	
	/**
	 * 获取缓存map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,WechatCacheObject> getMap() {
		Map<String,WechatCacheObject> map = (Map<String,WechatCacheObject>)CacheUtils.get(WECHAT_CACHE, WECHAT_CACHE_KEY);
		if(map == null) {
			map = new HashMap<String,WechatCacheObject>();
			CacheUtils.put(WECHAT_CACHE, WECHAT_CACHE_KEY, map);
		}
		return map;
	}

}
