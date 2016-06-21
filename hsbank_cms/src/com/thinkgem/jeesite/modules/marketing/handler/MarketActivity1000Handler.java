/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBalanceHandler;
import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.MarketingMoneyAwardRecord;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.marketing.MarketingMoneyAwardRecordParameters;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingMoneyAwardRecordDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;

/**
 * @author yangtao
 * 
 *	新用户奖励（长期活动）：
 *		1：用户注册，送5元投资券
 *		2：用户开通第三方账号，送10元投资券
 *		3：首次充值，送10元投资券
 *		4：首次投资，送200元投资券（1张100元，1张50元，2张20元，1张10元）
 *		5：首次投资放款，送2.88元现金
 *		6：首次投资放款，送10次免费提现次数
 *
 */
@Component("marketActivity1000Handler")
public class MarketActivity1000Handler extends MarketActivityBaseSupportHandler{
	
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	
	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	@Autowired
	private CustomerBalanceHandler customerBalanceHandler;
	@Autowired
	private MarketingMoneyAwardRecordDao marketingMoneyAwardRecordDao;
	
	/**
	 * 注册
	 * 		1：送5元投资券
	 */
	@Override
	@Transactional(readOnly=false)
	public Map<String,Object> register(Map<String,Object> para) {
		logger.info("=====market activity 1000 register, 注册送5元投资券 start=====");
		//调用super.register
		logger.info("super register method start");
		super.register(para);
		logger.info("super register method end");
		//送用户投资券
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		int[] denominations = MarketConstant.ACTIVITY_1000_REGISTER_GIVE_INVESTMENT_TICKET_DENOMINATIONS;
		String remark = MarketConstant.ACTIVITY_1000_REGISTER_GIVE_INVESTMENT_TICKET_REMARK;
		logger.info("do give investmentTicket start. accountId:" + accountId + ", denominations:" + denominations);
		customerInvestmentTicketHandler.giveCustomerTickets(accountId, denominations, remark);
		logger.info("do give investmentTicket end.");
		//插入marketingActivityAwardRecord赠送记录
		logger.info("insert marketingActivityAwardRecord start.");
		for(int denomination : denominations) {
			super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_REGISTER, ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET,
					denomination + "", remark);
		}
		logger.info("insert marketingActivityAwardRecord end.");
		logger.info("=====market activity 1000, 注册送5元投资券 end=====");
		return para;
	}
	
	/**
	 * 开通第三方账号
	 * 		1：送10元投资券
	 */
	@Override
	@Transactional(readOnly=false)
	public Map<String,Object> openThirdParty(Map<String,Object> para) {
		logger.info("=====market activity 1000 openThirdParty, 开通第三方账号送10元投资券 start=====");
		//调用super.openThirdParty
		logger.info("super openThirdParty method start");
		super.openThirdParty(para);
		logger.info("super openThirdParty method end");
		//送用户投资券
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		int[] denominations = MarketConstant.ACTIVITY_1000_OPENTHIRDPARTY_GIVE_INVESTMENT_TICKET_DENOMINATIONS;
		String remark = MarketConstant.ACTIVITY_1000_OPENTHIRDPARTY_GIVE_INVESTMENT_TICKET_REMARK;
		logger.info("do give investmentTicket start. accountId:" + accountId + ", denominations:" + denominations);
		customerInvestmentTicketHandler.giveCustomerTickets(accountId, denominations, remark);
		logger.info("do give investmentTicket end.");
		//插入marketingActivityAwardRecord赠送记录
		logger.info("insert marketingActivityAwardRecord start.");
		for(int denomination : denominations) {
			super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_OPEN_THIRD_PARTY, ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET,
					denomination + "", remark);
		}
		logger.info("insert marketingActivityAwardRecord end.");
		logger.info("=====market activity 1000 openThirdParty, 开通第三方账号送10元投资券 end=====");
		return para;
	}
	
	/**
	 * 充值
	 * 		1：首次充值，送10元投资券
	 */
	@Override
	@Transactional(readOnly=false)
	public Map<String,Object> recharge(Map<String,Object> para) {
		logger.info("=====market activity 1000 recharge, 首次充值送10元投资券 start=====");
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		int[] denominations = MarketConstant.ACTIVITY_1000_FIRST_RECHARGE_GIVE_INVESTMENT_TICKET_DENOMINATIONS;
		String remark = MarketConstant.ACTIVITY_1000_FIRST_RECHARGE_GIVE_INVESTMENT_TICKET_REMARK;
		CustomerBalance customerBalance = customerBalanceDao.get(accountId + "");
		if(customerBalance != null && customerBalance.getRechargeCount() == 1) {		//是首次充值投资券
			//调用super.recharge
			logger.info("super recharge method start");
			super.recharge(para);
			logger.info("super recharge method end");
			//送用户投资券
			logger.info("do give investmentTicket start. accountId:" + accountId + ", denominations:" + denominations);
			customerInvestmentTicketHandler.giveCustomerTickets(accountId, denominations, remark);
			logger.info("do give investmentTicket end.");
			//插入marketingActivityAwardRecord赠送记录
			logger.info("insert marketingActivityAwardRecord start.");
			for(int denomination : denominations) {
				super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_RECHARGE, ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET,
						denomination + "", remark);
			}
			logger.info("insert marketingActivityAwardRecord end.");
		} else {
			logger.info("ungive customer investment ticket, because this recharge is not first recharge");
		}
		logger.info("=====market activity 1000 recharge, 首次充值送10元投资券 end=====");
		return para;
	}
	
	/**
	 * 投标入口
	 * 		1：首次投资，送200元投资券
	 */
	@Override
	@Transactional(readOnly=false)
	public Map<String,Object> investmentTender(Map<String,Object> para) {
		logger.info("=====market activity 1000 investmentTender, 首次投资送200元投资券 start=====");
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		int[] denominations = MarketConstant.ACTIVITY_1000_FIRST_INVESTMENTTENDER_GIVE_INVESTMENT_TICKET_DENOMINATIONS;
		String remark = MarketConstant.ACTIVITY_1000_FIRST_INVESTMENTTENDER_GIVE_INVESTMENT_TICKET_REMARK;
		String recordId = (String)para.get(MarketConstant.RECORD_ID_PARA);		//投资记录id
		ProjectInvestmentRecord firstRecord = projectInvestmentRecordDao.getCustomerFirstNormalRecord(accountId);		//获取用户首次投资记录
		if(firstRecord != null && recordId.equals(firstRecord.getId())) {		//是首次投资送投资券
			//调用super.investmentTender
			logger.info("super investmentTender method start");
			super.investmentTender(para);
			logger.info("super investmentTender method end");
			//送用户投资券
			logger.info("do give investmentTicket start. accountId:" + accountId + ", denominations:" + denominations);
			customerInvestmentTicketHandler.giveCustomerTickets(accountId, denominations, remark);
			logger.info("do give investmentTicket end.");
			//插入marketingActivityAwardRecord赠送记录
			logger.info("insert marketingActivityAwardRecord start.");
			for(int denomination : denominations) {
				super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER, ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET,
						denomination + "", remark);
			}
			logger.info("insert marketingActivityAwardRecord end.");
		} else {
			logger.info("ungive customer investment ticket, because this investment is not first investment");
		}
		logger.info("=====market activity 1000 investmentTender, 首次投资送200元投资券 end=====");
		return para;
	}

	/**
	 * 投资放款
	 * 		1：首次投资放款，送2.88元现金
	 * 		2：首次投资放款，送10次免费提现次数
	 */
	@Override
	@Transactional(readOnly = false)
	public Map<String, Object> investmentTenderOver(Map<String, Object> para) {
		logger.info("=====market activity 1000 investmentTenderOver, 首次投资放款送2.88元现金、10次免费提现次数 start=====");
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		double giveMoneyAmount = MarketConstant.ACTIVITY_1000_FIRST_INVESTMENTTENDEROVER_GIVE_MONEY_AMOUNT;
		String giveMoneyRemark = MarketConstant.ACTIVITY_1000_FIRST_INVESTMENTTENDEROVER_GIVE_MONEY_REMARK;
		int giveFreeWithdrawCountValue = MarketConstant.ACTIVITY_1000_FIRST_INVESTMENTTENDEROVER_GIVE_FREE_WITHDRAW_COUNT_VALUE;
		String giveFreeWithdrawCountRemark = MarketConstant.ACTIVITY_1000_FIRST_INVESTMENTTENDEROVER_GIVE_FREE_WITHDRAW_COUNT_REMARK;
		String recordId = (String)para.get(MarketConstant.RECORD_ID_PARA);		//投资记录id
		ProjectInvestmentRecord firstRecord = projectInvestmentRecordDao.getCustomerFirstNormalRecord(accountId);		//获取用户首次投资记录
		if(firstRecord != null && recordId.equals(firstRecord.getId())) {		//是首次投资送投资券
			//调用super.investmentTenderOver
			logger.info("super investmentTenderOver method start");
			super.investmentTenderOver(para);
			logger.info("super investmentTenderOver method end");
			//送用户现金
			offlineGiveMoney(accountId, giveMoneyAmount, giveMoneyRemark, Long.parseLong(recordId), ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF,
					MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER, (String)para.get(MarketConstant.CHANNEL_PARA), (long)para.get(MarketConstant.ACTICITY_PARA));
			//送用户免费提现次数
			logger.info("do give freeWithdrawCount start. accountId:" + accountId + ", value:" + giveFreeWithdrawCountValue);
			customerBalanceHandler.updateFreeWithCount(accountId, giveFreeWithdrawCountValue, ProjectConstant.FREE_WITHDRAW_COUNT_CHANGE_TYPE_ACTIVITY_GIVE);
			logger.info("do give freeWithdrawCount end.");
			//插入marketingActivityAwardRecord赠送记录
			logger.info("insert marketingActivityAwardRecord start.");
			super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER, ProjectConstant.MARKETING_AWARD_TYPE_FREE_WITHDRAW_COUNT,
					giveFreeWithdrawCountValue + "", giveFreeWithdrawCountRemark);
			logger.info("insert marketingActivityAwardRecord end.");
		} else {
			logger.info("ungive customer investment ticket, because this investment is not first investment");
		}
		logger.info("=====market activity 1000 investmentTenderOver, 首次投资放款送2.88元现金、10次免费提现次数 end=====");
		return para;
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
	private void offlineGiveMoney(long accountId, double amount, String remark, long causeId, String causeType,
			String behaviorCode, String channelId, long marketingActivityId) {
		MarketingMoneyAwardRecordParameters marketingMoneyAwardRecordParameters = new MarketingMoneyAwardRecordParameters();
		marketingMoneyAwardRecordParameters.setAwardCauseId(causeId);
		marketingMoneyAwardRecordParameters.setAwardCauseType(causeType);
		marketingMoneyAwardRecordParameters.setAwardReason(remark);
		marketingMoneyAwardRecordParameters.setBehaviorCode(behaviorCode);
		marketingMoneyAwardRecordParameters.setChannelId(channelId);
		marketingMoneyAwardRecordParameters.setMarketingActivityId(marketingActivityId);
		
		MarketingMoneyAwardRecord marketingMoneyAwardRecord = new MarketingMoneyAwardRecord();
		marketingMoneyAwardRecord.setAccountId(accountId);
		marketingMoneyAwardRecord.setAmount(amount);
		marketingMoneyAwardRecord.setStatus(MarketConstant.MARKETING_MONEY_AWARD_STATUS_GIVING);
		marketingMoneyAwardRecord.setCreateDt(new Date());
		marketingMoneyAwardRecord.setParameters(JsonMapper.toJsonString(marketingMoneyAwardRecordParameters));
		marketingMoneyAwardRecordDao.insert(marketingMoneyAwardRecord);
	}
}
