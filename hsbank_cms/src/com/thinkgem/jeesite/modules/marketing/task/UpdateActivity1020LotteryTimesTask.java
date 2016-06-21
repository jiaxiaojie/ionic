package com.thinkgem.jeesite.modules.marketing.task;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.MarketingUtils;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1020Handler;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingWheelLotteryTimesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 更新活动1020的抽奖次数
 * @author lzb
 *
 */
@Component
public class UpdateActivity1020LotteryTimesTask {
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private MarketingWheelLotteryTimesService marketingWheelLotteryTimesService;
	
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 活动期间 每日00:00:01将用户活动1020的总抽奖次数置为1、已抽奖次数置为0
	 */
	public void job() {
		logger.info("【" + DateUtils.formatDateTime(new Date()) + "】 updateActivity1020LotteryTimesTask start=====");
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService.getByBizClassName(
				StringUtils.lowerCaseFirstLetter(MarketActivity1020Handler.class.getSimpleName()));
		if(marketingActivityInfo != null && MarketingUtils.check(marketingActivityInfo)) {
			logger.info("updateActivity1020LotteryTimesTask acticityId :" + marketingActivityInfo.getActicityId());
			marketingWheelLotteryTimesService.updateLotteryTimesByActivityId(1, 0, marketingActivityInfo.getActicityId());
		}
		logger.info("【" + DateUtils.formatDateTime(new Date()) + "】 updateActivity1020LotteryTimesTask end=====");
	}
}
