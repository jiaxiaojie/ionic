package com.thinkgem.jeesite.modules.marketing;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.entity.MarketingWheelGetPrizeRecord;
import com.thinkgem.jeesite.modules.entity.MarketingWheelPrizeInfo;
import com.thinkgem.jeesite.modules.entity.MarketingWheelPrizeInstance;
import com.thinkgem.jeesite.modules.marketing.service.MarketingWheelGetPrizeRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingWheelPrizeInfoService;

/**
 * 大转盘工具类
 * @author ydt
 *
 */
public class MarketingWheelUtils {
	private static MarketingWheelPrizeInfoService marketingWheelPrizeInfoService = SpringContextHolder.getBean(MarketingWheelPrizeInfoService.class);

	private static MarketingWheelGetPrizeRecordService marketingWheelGetPrizeRecordService = SpringContextHolder.getBean(MarketingWheelGetPrizeRecordService.class);
	
	public static final String MARKETING_WHEEL_CACHE = "marketingWheelCache";
	public static final String MARKETING_WHEEL_CACHE_KEY = "marketingWheelCacheKey";
	public static final int QUEUE_SIZE = 1000;
	public static final int MIN_QUEUE_SIZE = 300;
	
	Queue<MarketingWheelPrizeInstance> queue = new LinkedList<MarketingWheelPrizeInstance>();
	
	/**
	 * 获取队列中的奖品实例，此时代表用户已经抽到了奖
	 * 		1.判断cache中的队列数量是否小于最小值，若小于则队列 = 数据库中读取的队列
	 * 		2.获取并移除队列的元素
	 * 		3.若获取的实例不为空 则将数据库实例状态置为锁定，否则生成默认实例
	 * 		4.添加中奖记录
	 * @param opTerm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Long poll(Long accountId, Long activityId, String opTerm) {
		Queue<MarketingWheelPrizeInstance> queue = (Queue<MarketingWheelPrizeInstance>)CacheUtils.get(MARKETING_WHEEL_CACHE, MARKETING_WHEEL_CACHE_KEY + activityId);
		if(queue == null || queue.size() < MIN_QUEUE_SIZE) {
			List<MarketingWheelPrizeInstance> marketingWheelPrizeInstanceList = marketingWheelPrizeInfoService.findListByActivityIdAndStatus(activityId, MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_NORMAL, QUEUE_SIZE);
			queue = new LinkedList<MarketingWheelPrizeInstance>(marketingWheelPrizeInstanceList);
		}
		String token = UUID.randomUUID().toString();
		MarketingWheelPrizeInstance marketingWheelPrizeInstance = queue.poll();
		if(marketingWheelPrizeInstance != null) {
			marketingWheelPrizeInfoService.updateInstanceStatus(marketingWheelPrizeInstance.getId(), MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_GOT);
		} else {
			marketingWheelPrizeInstance = createDefaultInstance(activityId);
		}
		marketingWheelPrizeInstance.setToken(token);
		
		MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord = new MarketingWheelGetPrizeRecord();
		marketingWheelGetPrizeRecord.setPrizeInstanceId(Long.parseLong(marketingWheelPrizeInstance.getId()));
		marketingWheelGetPrizeRecord.setStatus(MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVING);
		marketingWheelGetPrizeRecord.setToken(token);
		marketingWheelGetPrizeRecord.setOpTerm(opTerm);
		marketingWheelGetPrizeRecord.setInvalidDt(DateUtils.dateAddMinute(new Date(), MarketConstant.WHEEL_TOKEN_TIME_OUT));
		marketingWheelGetPrizeRecord.setGetDt(new Date());
		marketingWheelGetPrizeRecord.setAccountId(accountId);
		marketingWheelGetPrizeRecordService.insert(marketingWheelGetPrizeRecord);
		
		CacheUtils.put(MARKETING_WHEEL_CACHE, MARKETING_WHEEL_CACHE_KEY + activityId, queue);
		return Long.parseLong(marketingWheelPrizeInstance.getId());
	}
	
	private static MarketingWheelPrizeInstance createDefaultInstance(Long activityId) {
		MarketingWheelPrizeInfo marketingWheelPrizeInfo = marketingWheelPrizeInfoService.getDefaultPrize(activityId);
		MarketingWheelPrizeInstance marketingWheelPrizeInstance = new MarketingWheelPrizeInstance();
		marketingWheelPrizeInstance.setPrizeId(Long.parseLong(marketingWheelPrizeInfo.getId()));
		marketingWheelPrizeInstance.setStatus(MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_GOT);
		marketingWheelPrizeInfoService.insertPrizeInstance(marketingWheelPrizeInstance);
		return marketingWheelPrizeInstance;
	}
}
