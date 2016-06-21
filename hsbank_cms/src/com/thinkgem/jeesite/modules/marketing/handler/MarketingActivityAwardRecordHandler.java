package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.MarketingActivityAwardRecord;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAwardRecordDao;

/**
 * 
 * @author ydt
 *
 */
@Component
public class MarketingActivityAwardRecordHandler {
	@Autowired
	private MarketingActivityAwardRecordDao marketingActivityAwardRecordDao;
	
	public void insertRecordWhenGiveAward(long accountId, long activityId, String behaviorCode, String channelId, String awardType,
			String awardValue, String awardReason, String causeType, Long causeId) {
		MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
		marketingActivityAwardRecord.setAccountId(accountId);
		marketingActivityAwardRecord.setActivityId(activityId);
		marketingActivityAwardRecord.setAwardDt(new Date());
		marketingActivityAwardRecord.setAwardReason(awardReason);
		marketingActivityAwardRecord.setAwardType(awardType);
		marketingActivityAwardRecord.setAwardValue(awardValue);
		marketingActivityAwardRecord.setBehaviorCode(behaviorCode);
		marketingActivityAwardRecord.setChannelId(channelId);
		marketingActivityAwardRecord.setCauseType(causeType);
		marketingActivityAwardRecord.setCauseId(causeId);
		marketingActivityAwardRecordDao.insert(marketingActivityAwardRecord);
	}
}
