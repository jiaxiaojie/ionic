/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.ClientCacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.customer.dao.CustomerClientTokenDao;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class CustomerClientUtils {

	private static CustomerClientTokenDao customerClientTokenDao = SpringContextHolder.getBean(CustomerClientTokenDao.class);

	public static final String CLIENT_CACHE = "clientCache";
	public static final String CLIENT_CACHE_KEY = "clientCacheKey";
	public static final String CLIENT_CACHE_ID_ = "_id_";
	public static final String CLIENT_CACHE_TOKEN_ = "token";
	

	
	/**
	 * 根据ID获取用户
	 * @param customerId
	 * @return
	 */
	public static CustomerClientToken getByCustomerId(Long customerId,String opTerm){
		CustomerClientToken customerClientToken = (CustomerClientToken)ClientCacheUtils.get(CLIENT_CACHE, opTerm + CLIENT_CACHE_ID_ + customerId);
		return customerClientToken;
	}
	
	/**
	 * 根据token获取用户
	 * @param token
	 * @return
	 */
	public static CustomerClientToken getByToken(String token, String termType){
		CustomerClientToken customerClientToken = (CustomerClientToken)ClientCacheUtils.get(CLIENT_CACHE, CLIENT_CACHE_TOKEN_ + token);
		Date theDate = DateUtils.addMinutes(new Date(), ApiConstant.API_MOBILE_CACHE_TOKEN_INVALID_TIME);
		if (customerClientToken == null){
			if(ProjectConstant.OP_TERM_DICT_PC.equals(termType)){
				theDate = DateUtils.addMinutes(new Date(), ApiConstant.API_WEBSITE_CACHE_TOKEN_INVALID_TIME);
			}
			customerClientToken = customerClientTokenDao.getByTokenAndTheDate(token, theDate);
			if (customerClientToken == null){
				return null;
			}
		}
		customerClientToken.setLastDt(new Date());
		refreshCache(customerClientToken);
		return customerClientToken;
	}
	

	/**
	 * 缓存列表
	 * @return
	 */
	public static List<CustomerClientToken> getCatchList(){
		Map<String,CustomerClientToken> map = getMap();
		List<CustomerClientToken> list = new ArrayList<CustomerClientToken>(map.values());
		return list;
	}
	
	
	public static Map<String,CustomerClientToken> getMap() {
		@SuppressWarnings("unchecked")
		Map<String, CustomerClientToken> map = (Map<String, CustomerClientToken>)CacheUtils.get(CLIENT_CACHE, CLIENT_CACHE_KEY);
		if(map == null) {
			map = new HashMap<String, CustomerClientToken>();
			ClientCacheUtils.put(CLIENT_CACHE, CLIENT_CACHE_KEY, map);
		}
		return map;
	}
	
	/**
	 * 清除指定用户缓存
	 * @param customerAccount
	 */
	public static void clearCache(CustomerClientToken customerClientToken){
		ClientCacheUtils.remove(CLIENT_CACHE, customerClientToken.getTermType() + CLIENT_CACHE_ID_ + customerClientToken.getCustomerId());
		ClientCacheUtils.remove(CLIENT_CACHE, CLIENT_CACHE_TOKEN_ + customerClientToken.getToken());
		Map<String, CustomerClientToken> map = getMap();
		if(customerClientToken != null && map.keySet().contains(customerClientToken.getTermType() + CLIENT_CACHE_ID_ + customerClientToken.getCustomerId())){
			map.remove(customerClientToken.getTermType() + CLIENT_CACHE_ID_ + customerClientToken.getCustomerId());
		}
		ClientCacheUtils.put(CLIENT_CACHE, CLIENT_CACHE_KEY, map);
	}

	/**
	 * 添加用户缓存
	 * @param customerClientToken
	 */
	public static void addCache(CustomerClientToken customerClientToken){
		ClientCacheUtils.put(CLIENT_CACHE, customerClientToken.getTermType() + CLIENT_CACHE_ID_ + customerClientToken.getCustomerId(), customerClientToken);
		ClientCacheUtils.put(CLIENT_CACHE, CLIENT_CACHE_TOKEN_ + customerClientToken.getToken(), customerClientToken);
		Map<String, CustomerClientToken> map = getMap();
		map.put(customerClientToken.getTermType() + CLIENT_CACHE_ID_ + customerClientToken.getCustomerId(), customerClientToken);
		ClientCacheUtils.put(CLIENT_CACHE, CLIENT_CACHE_KEY, map);
	}


	/**
	 * 刷新缓存信息
	 * @param customerClientToken
     */
	public static void refreshCache(CustomerClientToken customerClientToken) {
		//先清除缓存
		clearCache(customerClientToken);
		//重新添加缓存
		addCache(customerClientToken);

	}
	
	/**
	 * 检测token
	 * @param token
	 * @return
	 */
	public static boolean checkToken(String token){
		CustomerClientToken customerClientToken = (CustomerClientToken)ClientCacheUtils.get(CLIENT_CACHE, CLIENT_CACHE_TOKEN_ + token);
		if(customerClientToken != null ){
			return true;
		}
		return false;
	}

}
