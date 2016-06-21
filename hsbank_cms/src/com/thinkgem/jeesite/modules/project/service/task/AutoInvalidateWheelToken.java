package com.thinkgem.jeesite.modules.project.service.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.marketing.service.MarketingWheelPrizeInfoService;

/**
 * 自动将中奖超时且未注册的token置为失效状态
 * @author ydt
 *
 */
@Deprecated	//新转盘活动为已投资用户奖励，获奖即表示能赠送给用户，故奖品失效逻辑弃用
@Component
public class AutoInvalidateWheelToken {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private MarketingWheelPrizeInfoService marketingWheelPrizeInfoService;
	
	/**
	 * 将中奖超时且未注册的token置为失效状态，每15分钟执行一次
	 */
	public void job() {
		logger.info("=====autoInvalidateWheelToken start=====");
		marketingWheelPrizeInfoService.invalidateWheelToken();
		logger.info("=====autoInvalidateWheelToken end=====");
	}
}
