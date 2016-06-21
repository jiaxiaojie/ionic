package com.thinkgem.jeesite.modules.carousel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.carousel.service.CarouselInfoService;
import com.thinkgem.jeesite.modules.entity.CarouselInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

public class CarouselUtils {
	private static CarouselInfoService carouselInfoService = SpringContextHolder.getBean(CarouselInfoService.class);
	
	public static final String MARKETING_CACHE = "marketingCache";
	public static final String MARKETING_CACHE_KEY = "marketingCacheKey";
	
	/**
	 * 添加轮播图
	 * @param CarouselInfo
	 */
	public static void add(CarouselInfo carouselInfo) {
		if(carouselInfo != null) {
			Map<Long,CarouselInfo> map = getMap();
			map.put(carouselInfo.getCarouselId(), carouselInfo);
		}
	}
	
	/**
	 * 移除轮播图
	 * @param CarouselInfo
	 */
	public static void remove(CarouselInfo carouselInfo) {
		Map<Long,CarouselInfo> map = getMap();
		if(carouselInfo != null && map.keySet().contains(carouselInfo.getCarouselId())) {
			map.remove(carouselInfo.getCarouselId());
		}
	}
	
	/**
	 * 获取轮播图
	 * @param carouseId
	 * @return
	 */
	public static CarouselInfo get(long carouseId) {
		Map<Long,CarouselInfo> map = getMap();
		return map.get(carouseId);
	}
	
	/**
	 * 检查cache中的轮播图在此时间是否有效，若无效在则移除cache
	 */
	public static void check() {
		Map<Long,CarouselInfo> map = getMap();
		Iterator<Map.Entry<Long,CarouselInfo>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Long,CarouselInfo> entry = it.next();
			CarouselInfo carouselInfo = carouselInfoService.get(entry.getKey() + "");
			//检查时间和状态
			if(carouselInfo == null || !ProjectConstant.CAROUSEL_INFO_STATUS_PASS.equals(carouselInfo.getStatus()) || !check(carouselInfo)) {
				it.remove();
			}
		}
	}

	/**
	 * 检查轮播图在此时间是否有效
	 * @param CarouselInfo
	 * @return
	 */
	public static boolean check(CarouselInfo carouselInfo) {
		return (new Date().getTime() >= carouselInfo.getStartDt().getTime()) && (new Date().getTime() <= carouselInfo.getEndDt().getTime());
/*				&& (DateUtils.getTime().compareTo(carouselInfo.getStartDt()) >= 0) && (DateUtils.getTime().CompareTo(carouselInfo.getEndTime()) <= 0);
*/	}
	
	/**
	 * 更新cache中的活动，若cache中原本无此轮播图则不做操作
	 * @param CarouselInfo
	 */
	public static void update(CarouselInfo carouselInfo) {
		Map<Long,CarouselInfo> map = getMap();
		if(map.keySet().contains(carouselInfo.getCarouselId())) {
			map.put(carouselInfo.getCarouselId(), carouselInfo);
		}
	}
	
	/**
	 * 获取轮播图map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<Long,CarouselInfo> getMap() {
		Map<Long,CarouselInfo> map = (Map<Long,CarouselInfo>)CacheUtils.get(MARKETING_CACHE, MARKETING_CACHE_KEY);
		if(map == null) {
			map = new HashMap<Long,CarouselInfo>();
			CacheUtils.put(MARKETING_CACHE, MARKETING_CACHE_KEY, map);
		}
		return map;
	}
	
	/**
	 * 获取轮播图list
	 * @return
	 */
	public static List<CarouselInfo> getList() {
		Map<Long,CarouselInfo> map = getMap();
		List<CarouselInfo> list = new ArrayList<CarouselInfo>(map.values());
		return list;
	}
}
