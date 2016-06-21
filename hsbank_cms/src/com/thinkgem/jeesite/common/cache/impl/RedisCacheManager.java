package com.thinkgem.jeesite.common.cache.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.thinkgem.jeesite.common.cache.Cache;
import com.thinkgem.jeesite.common.cache.CacheManager;
import com.thinkgem.jeesite.common.cache.util.RedisManager;

import org.apache.shiro.cache.CacheException;
/**
 * 
 * @author 万端瑞
 *
 */
public class RedisCacheManager  implements CacheManager {

	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

	private RedisManager redisManager = null;

	
	private String keyPrefix = "shiro_redis_cache:";
	
	
	public String getKeyPrefix() {
		return keyPrefix;
	}

	
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}
	
	@Override
	public Cache getCache(String name) throws CacheException {
//		logger.debug("获取名称为: " + name + " 的RedisCache实例");
		
		Cache c = caches.get(name);
		
		if (c == null) {

			c = new RedisCache(redisManager, getCachePrefixBy(keyPrefix,name));
			
			caches.put(name, c);
		}
		return c;
	}

	public RedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}

	@Override
	public void addCache(String name) {
		Cache c = caches.get(name);
		
		if (c == null) {

			c = new RedisCache(redisManager, keyPrefix);
			
			caches.put(name, c);
		}
		
	}
	   
	private static String getCachePrefixBy(String keyPrefix,String cacheName){
		return keyPrefix+cacheName+":";
	}
}
