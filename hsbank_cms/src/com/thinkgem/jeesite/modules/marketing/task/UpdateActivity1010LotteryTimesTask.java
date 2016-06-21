package com.thinkgem.jeesite.modules.marketing.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.MarketingUtils;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1010Handler;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingWheelLotteryTimesService;

/**
 * 更新活动1010的抽奖次数
 * @author ydt
 *
 */
@Component
public class UpdateActivity1010LotteryTimesTask {
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private MarketingWheelLotteryTimesService marketingWheelLotteryTimesService;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 活动期间 每日00:00:01将用户活动1010的总抽奖次数置为2、已抽奖次数置为0
	 */
	public void job() {
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService.getByBizClassName(
				StringUtils.lowerCaseFirstLetter(MarketActivity1010Handler.class.getSimpleName()));
		if(MarketingUtils.check(marketingActivityInfo)) {
			marketingWheelLotteryTimesService.updateLotteryTimesByActivityId(2, 0, marketingActivityInfo.getActicityId());
		}
	}
}
