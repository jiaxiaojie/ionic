package com.thinkgem.jeesite.common.cache;

/**
 * 
 * @author 万端瑞
 *
 */
public interface CacheManager extends org.apache.shiro.cache.CacheManager {
	public void addCache(String name);
	public Cache getCache(String name);
}
