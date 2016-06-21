/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAwardRecord;
import com.thinkgem.jeesite.modules.entity.MarketingMoneyAwardRecord;
import com.thinkgem.jeesite.modules.entity.marketing.MarketingMoneyAwardRecordParameters;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAwardRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingMoneyAwardRecordDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 活动现金奖励记录Service
 * @author ydt
 * @version 2016-01-18
 */
@Service
@Transactional(readOnly = true)
public class MarketingMoneyAwardRecordService extends CrudService<MarketingMoneyAwardRecordDao, MarketingMoneyAwardRecord> {

	@Autowired
	private MarketingActivityAwardRecordDao marketingActivityAwardRecordDao;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	
	public MarketingMoneyAwardRecord get(String id) {
		return super.get(id);
	}
	
	public List<MarketingMoneyAwardRecord> findList(MarketingMoneyAwardRecord marketingMoneyAwardRecord) {
		return super.findList(marketingMoneyAwardRecord);
	}
	
	public Page<MarketingMoneyAwardRecord> findPage(Page<MarketingMoneyAwardRecord> page, MarketingMoneyAwardRecord marketingMoneyAwardRecord) {
		return super.findPage(page, marketingMoneyAwardRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(MarketingMoneyAwardRecord marketingMoneyAwardRecord) {
		super.save(marketingMoneyAwardRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(MarketingMoneyAwardRecord marketingMoneyAwardRecord) {
		super.delete(marketingMoneyAwardRecord);
	}

	public List<MarketingMoneyAwardRecord> findListByStatus(String status) {
		return dao.findListByStatus(status);
	}

	/**
	 * 赠送现金奖励
	 * @param marketingMoneyAwardRecord
	 */
	@Transactional(readOnly = false)
	public void doGiveMoney (MarketingMoneyAwardRecord marketingMoneyAwardRecord) throws Exception {
		if(!MarketConstant.MARKETING_MONEY_AWARD_STATUS_GIVING.equals(marketingMoneyAwardRecord.getStatus())) {
			return;
		}
		long accountId = marketingMoneyAwardRecord.getAccountId();
		double amount = marketingMoneyAwardRecord.getAmount();
		logger.info("=====do give money start, accountId:" + accountId + ", amount:" + amount + "=====");
		CustomerAccount customerAccount = customerAccountDao.get(accountId);
		if (amount <= 0 || !ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount())) {
			return;
		}
		MarketingMoneyAwardRecordParameters moneyAwardRecordParameters =
				(MarketingMoneyAwardRecordParameters)JsonMapper.fromJsonString(marketingMoneyAwardRecord.getParameters(), MarketingMoneyAwardRecordParameters.class);
		Long marketingActivityId = moneyAwardRecordParameters.getMarketingActivityId();
		String awardCauseType = moneyAwardRecordParameters.getAwardCauseType();
		Long awardCauseId = moneyAwardRecordParameters.getAwardCauseId();
		String awardReason = moneyAwardRecordParameters.getAwardReason();
		String channelId = moneyAwardRecordParameters.getChannelId();
		String behaviorCode = moneyAwardRecordParameters.getBehaviorCode();
		
		MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
		marketingActivityAwardRecord.setAccountId(accountId);
		marketingActivityAwardRecord.setActivityId(marketingActivityId);
		marketingActivityAwardRecord.setAwardDt(new Date());
		marketingActivityAwardRecord.setAwardReason(awardReason);
		marketingActivityAwardRecord.setAwardType(ProjectConstant.MARKETING_AWARD_TYPE_MONEY);
		marketingActivityAwardRecord.setAwardValue(String.valueOf(amount));
		marketingActivityAwardRecord.setBehaviorCode(behaviorCode);
		marketingActivityAwardRecord.setChannelId(channelId);
		marketingActivityAwardRecord.setCauseType(awardCauseType);
		marketingActivityAwardRecord.setCauseId(awardCauseId);
		marketingActivityAwardRecordDao.insert(marketingActivityAwardRecord);
		
		dao.updateStatus(marketingMoneyAwardRecord.getId(), MarketConstant.MARKETING_MONEY_AWARD_STATUS_GIVEN, new Date());
		
		yeepayCommonHandler.transferToCustomerFromPlatform(accountId, amount, ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT,
				awardReason, String.valueOf(awardCauseId == null ? "" : awardCauseId));
		logger.info("=====do give money end, accountId:" + accountId + ", amount:" + amount + "=====");
	}


	/**
	 * 插入到现金奖励记录中
	 * @param accountId
	 * @param amount
	 * @param remark
	 * @param causeId
	 * @param causeType
	 * @param behaviorCode
	 * @param channelId
     * @param marketingActivityId
     */
	@Transactional(readOnly = false)
	public void offlineDoGiveMoney(long accountId, double amount, String remark, Long causeId, String causeType,
								   String behaviorCode, String channelId, long marketingActivityId){
		this.offlineGiveMoney(accountId,amount,remark,causeId,causeType,behaviorCode,channelId,marketingActivityId);
	}
	/**
	 * 将现金奖励插入到现金奖励记录中，随后由task完成现金赠送动作
	 * @param accountId
	 * @param amount
	 * @param remark
	 * @param causeId
	 * @param causeType
	 * @param behaviorCode
	 * @param channelId
     * @param marketingActivityId
     */
	public void offlineGiveMoney(long accountId, double amount, String remark, Long causeId, String causeType,
								  String behaviorCode, String channelId, long marketingActivityId) {
		MarketingMoneyAwardRecordParameters parameters = new MarketingMoneyAwardRecordParameters();
		parameters.setAwardCauseId(causeId);
		parameters.setAwardCauseType(causeType);
		parameters.setAwardReason(remark);
		parameters.setBehaviorCode(behaviorCode);
		parameters.setChannelId(channelId);
		parameters.setMarketingActivityId(marketingActivityId);

		MarketingMoneyAwardRecord marketingMoneyAwardRecord = new MarketingMoneyAwardRecord();
		marketingMoneyAwardRecord.setAccountId(accountId);
		marketingMoneyAwardRecord.setAmount(amount);
		marketingMoneyAwardRecord.setStatus(MarketConstant.MARKETING_MONEY_AWARD_STATUS_GIVING);
		marketingMoneyAwardRecord.setCreateDt(new Date());
		marketingMoneyAwardRecord.setParameters(JsonMapper.toJsonString(parameters));
		dao.insert(marketingMoneyAwardRecord);
	}
}