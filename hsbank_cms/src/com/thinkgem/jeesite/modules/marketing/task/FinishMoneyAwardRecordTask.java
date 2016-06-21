package com.thinkgem.jeesite.modules.marketing.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.MarketingMoneyAwardRecord;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.service.MarketingMoneyAwardRecordService;

/**
 * 自动将活动现金奖励赠送给用户
 * @author ydt
 *
 */
@Component
public class FinishMoneyAwardRecordTask {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private MarketingMoneyAwardRecordService marketingMoneyAwardRecordService;
	
	/**
	 * 将活动现金奖励赠送给用户，每小时的20分执行一次
	 */
	public void job() {
		List<MarketingMoneyAwardRecord> marketingMoneyAwardRecordList = marketingMoneyAwardRecordService.findListByStatus(MarketConstant.MARKETING_MONEY_AWARD_STATUS_GIVING);
		for(MarketingMoneyAwardRecord marketingMoneyAwardRecord : marketingMoneyAwardRecordList) {
			try {
				marketingMoneyAwardRecordService.doGiveMoney(marketingMoneyAwardRecord);
			} catch(Exception e) {
				logger.info("do give money exception. marketingMoneyAwardRecord id:" + marketingMoneyAwardRecord.getId());
			}
		}
	}
}
