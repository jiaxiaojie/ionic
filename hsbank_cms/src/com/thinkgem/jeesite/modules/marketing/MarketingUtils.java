package com.thinkgem.jeesite.modules.marketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;

public class MarketingUtils {
	private static MarketingActivityInfoService marketingActivityInfoService = SpringContextHolder.getBean(MarketingActivityInfoService.class);
	
	public static final String MARKETING_CACHE = "marketingCache";
	public static final String MARKETING_CACHE_KEY = "marketingCacheKey";
	
	/**
	 * 添加活动
	 * @param marketingActivityInfo
	 */
	public static void add(MarketingActivityInfo marketingActivityInfo) {
		if(marketingActivityInfo != null) {
			Map<Long,MarketingActivityInfo> map = getMap();
			map.put(marketingActivityInfo.getActicityId(), marketingActivityInfo);
			CacheUtils.put(MARKETING_CACHE, MARKETING_CACHE_KEY, map);
		}
	}
	
	/**
	 * 移除活动
	 * @param marketingActivityInfo
	 */
	public static void remove(MarketingActivityInfo marketingActivityInfo) {
		Map<Long,MarketingActivityInfo> map = getMap();
		if(marketingActivityInfo != null && map.keySet().contains(marketingActivityInfo.getActicityId())) {
			map.remove(marketingActivityInfo.getActicityId());
		}
		CacheUtils.put(MARKETING_CACHE, MARKETING_CACHE_KEY, map);
	}
	
	/**
	 * 获取活动
	 * @param activityId
	 * @return
	 */
	public static MarketingActivityInfo get(long activityId) {
		Map<Long,MarketingActivityInfo> map = getMap();
		return map.get(activityId);
	}
	
	/**
	 * 检查cache中的活动在此时间是否有效，若无效在则移除cache
	 */
	public static void check() {
		Map<Long,MarketingActivityInfo> map = getMap();
		Iterator<Map.Entry<Long,MarketingActivityInfo>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Long,MarketingActivityInfo> entry = it.next();
			MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService.get(entry.getKey() + "");
			//检查时间和状态
			if(marketingActivityInfo == null || !MarketConstant.MARKETING_ACTIVITY_STATUS_PASS.equals(marketingActivityInfo.getStatus()) || !check(marketingActivityInfo)) {
				it.remove();
			}
		}
		CacheUtils.put(MARKETING_CACHE, MARKETING_CACHE_KEY, map);
	}

	/**
	 * 检查活动在此时间是否有效
	 * @param marketingActivityInfo
	 * @return
	 */
	public static boolean check(MarketingActivityInfo marketingActivityInfo) {
		return	DateUtils.getDate().compareTo(DateUtils.formatDate(marketingActivityInfo.getBeginDate(), "yyyy-MM-dd")) >= 0
				&& DateUtils.getDate().compareTo(DateUtils.formatDate(marketingActivityInfo.getEndDate(), "yyyy-MM-dd")) <= 0
				&& (DateUtils.getTime().compareTo(marketingActivityInfo.getBeginTime()) >= 0)
				&& (DateUtils.getTime().compareTo(marketingActivityInfo.getEndTime()) <= 0);
	}
	
	/**
	 * 更新cache中的活动，若cache中原本无此活动则不做操作
	 * @param marketingActivityInfo
	 */
	public static void update(MarketingActivityInfo marketingActivityInfo) {
		Map<Long,MarketingActivityInfo> map = getMap();
		if(map.keySet().contains(marketingActivityInfo.getActicityId())) {
			map.put(marketingActivityInfo.getActicityId(), marketingActivityInfo);
		}
		CacheUtils.put(MARKETING_CACHE, MARKETING_CACHE_KEY, map);
	}
	
	/**
	 * 获取活动map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<Long,MarketingActivityInfo> getMap() {
		Map<Long,MarketingActivityInfo> map = (Map<Long,MarketingActivityInfo>)CacheUtils.get(MARKETING_CACHE, MARKETING_CACHE_KEY);
		if(map == null) {
			map = new HashMap<Long,MarketingActivityInfo>();
			CacheUtils.put(MARKETING_CACHE, MARKETING_CACHE_KEY, map);
		}
		return map;
	}
	
	/**
	 * 获取活动list
	 * @return
	 */
	public static List<MarketingActivityInfo> getList() {
		Map<Long,MarketingActivityInfo> map = getMap();
		List<MarketingActivityInfo> list = new ArrayList<MarketingActivityInfo>(map.values());
		return list;
	}
}
