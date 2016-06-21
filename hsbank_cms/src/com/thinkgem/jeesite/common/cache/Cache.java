package com.thinkgem.jeesite.common.cache;

import java.io.Serializable;

import com.thinkgem.jeesite.common.cache.impl.Element;
/**
 * 
 * @author 万端瑞
 *
 */
public interface Cache extends org.apache.shiro.cache.Cache<Object,Object>  {
	public  Element get(Serializable key);

	
	 public  void put(Element element);
	 public Object remove(Object key);
	 
	 public Cache getCacheConfiguration();
	 public void setEternal(boolean param);
}
