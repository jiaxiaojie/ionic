/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * @author yangtao
 *
 * 推荐活动（长期活动）：
 *		1：被推荐人开通第三方账号，送推荐人2张10元投资券、1张5元券
 *		2：被推荐人首次投资放款，送推荐人2.88元现金
 *		3：被推荐人首次投资放款，送推荐人千分之一现金
 *		4：满5位被推荐人投资放款，送推荐人200元投资券（1张100元、1张50元、2张20元、1张10元）
 *
 */
@Component("marketActivity1001Handler")
public class MarketActivity1001Handler extends MarketActivityBaseSupportHandler{
	Logger logger = Logger.getLogger(this.getClass());
	
	
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	
	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	
	/**
	 * 开通第三方账号
	 * 		1：送推荐人25元投资券
	 */
	@Override
	@Transactional(readOnly=false)
	public Map<String,Object> openThirdParty(Map<String,Object> para) {
		logger.info("=====market activity 1001 openThirdParty, 开通第三方账号送推荐人25元投资券 start=====");
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		int[] denominations = MarketConstant.ACTIVITY_1001_OPENTHIRDPARTY_GIVE_RECOMMENDER_INVESTMENT_TICKET_DENOMINATIONS;
		String remark = MarketConstant.ACTIVITY_1001_OPENTHIRDPARTY_GIVE_RECOMMENDER_INVESTMENT_TICKET_REMARK;
		CustomerAccount customerAccount = customerAccountDao.get(accountId + "");
		CustomerAccount recommender = customerAccountDao.getByMobile(customerAccount.getRecommenderMobile());
		if(ProjectConstant.RECOMMENDER_TYPE_NORMAL.equals(customerAccount.getRecommenderType()) && recommender!=null) {		//推荐人存在并为普通用户时赠送
			//调用super.openThirdParty
			logger.info("super openThirdParty method start");
			super.openThirdParty(para);
			logger.info("super openThirdParty method end");
			logger.info("do give investmentTicket start. accountId:" + recommender.getAccountId() + ", denominations:" + denominations);
			customerInvestmentTicketHandler.giveCustomerTickets(recommender.getAccountId(), denominations, remark);
			logger.info("do give investmentTicket end.");
			//插入marketingActivityAwardRecord赠送记录
			logger.info("insert marketingActivityAwardRecord start.");
			for(int denomination : denominations) {
				super.insertRecommenderRecordInMarketingActivityAwardRecord(para, recommender.getAccountId(), MarketConstant.CUSTOMER_BEHAVIOR_OPEN_THIRD_PARTY, ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET,
						denomination + "", remark, ProjectConstant.MARKET_AWARD_CAUSE_TYPE_FRIEND_OTHER_BEHAVIOR, accountId);
			}
			logger.info("insert marketingActivityAwardRecord end.");
		}
		logger.info("=====market activity 1001 openThirdParty, 开通第三方账号送推荐人25元投资券 end=====");
		return para;
	}

	/**
	 * 投资放款
	 * 		1：被推荐人首次投资放款，送推荐人2.88元现金
	 * 		2：被推荐人首次投资放款，送推荐人千分之一现金
	 * 		3：满5位被推荐人投资放款，送推荐人200元投资券
	 */
	@Override
	@Transactional(readOnly = false)
	public Map<String, Object> investmentTenderOver(Map<String, Object> para) {
		logger.info("=====market activity 1001 investmentTenderOver method start=====");
		//调用super.investmentTenderOver
		logger.info("super investmentTenderOver method start");
		super.investmentTenderOver(para);
		logger.info("super investmentTenderOver method end");
		
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		double giveMoneyAmount = MarketConstant.ACTIVITY_1001_FIRST_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_MONEY_AMOUNT;
		String giveMoneyRemark = MarketConstant.ACTIVITY_1001_FIRST_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_MONEY_REMARK;
		double giveInvestmentMoneyAmount = LoanUtil.formatAmount((Double)para.get(MarketConstant.AMOUNT_PARA) * MarketConstant.ACTIVITY_1001_FIRST_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_MONEY_RATE);
		String giveInvestmentMoneyRemark = MarketConstant.ACTIVITY_1001_FIRST_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_MONEY_REMARK;
		int[] giveInvestmentTicketDenominations = MarketConstant.ACTIVITY_1001_FIFTH_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_TICKET_DENOMINATIONS;
		String giveInvestmentTicketRemark = MarketConstant.ACTIVITY_1001_FIFTH_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_TICKET_REMARK;
		CustomerAccount customerAccount = customerAccountDao.get(accountId + "");		//投资人信息
		CustomerAccount recommender = customerAccountDao.getByMobile(customerAccount.getRecommenderMobile());		//推荐人信息
		String recordId = (String)para.get(MarketConstant.RECORD_ID_PARA);		//投资记录id
		ProjectInvestmentRecord firstRecord = projectInvestmentRecordDao.getCustomerFirstNormalRecord(accountId);		//获取用户首次投资记录
		Long fifthRecordId = projectInvestmentRecordDao.getFifthNormalRecordIdByRecommenderMobile(customerAccount.getRecommenderMobile());		//获取被推荐人的第5次正常投资记录Id（group by被推荐人）
		
		if(firstRecord != null && recordId.equals(firstRecord.getId()) && ProjectConstant.RECOMMENDER_TYPE_NORMAL.equals(customerAccount.getRecommenderType()) && recommender!=null) {		//是首次投资且推荐人为正常用户
			//送推荐人现金
			logger.info("do give amount start. accountId:" + recommender.getAccountId() + ", amount:" + giveMoneyAmount);
			yeepayCommonHandler.transferToCustomerFromPlatform(recommender.getAccountId(), giveMoneyAmount, ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT, giveMoneyRemark, recordId);
			logger.info("do give amount end.");
			//插入marketingActivityAwardRecord赠送记录
			logger.info("insert marketingActivityAwardRecord start.");
			super.insertRecommenderRecordInMarketingActivityAwardRecord(para, recommender.getAccountId(), MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER_OVER, ProjectConstant.MARKETING_AWARD_TYPE_MONEY,
					giveMoneyAmount + "", giveMoneyRemark, ProjectConstant.MARKET_AWARD_CAUSE_TYPE_FRIEND_INVESTMENT, Long.parseLong(recordId));
			logger.info("insert marketingActivityAwardRecord end.");
			//送推荐人千分之一返现
			logger.info("do give amount start. accountId:" + recommender.getAccountId() + ", amount:" + giveInvestmentMoneyAmount);
			yeepayCommonHandler.transferToCustomerFromPlatform(recommender.getAccountId(), giveInvestmentMoneyAmount, ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT, giveInvestmentMoneyRemark, recordId);
			logger.info("do give amount end.");
			//插入marketingActivityAwardRecord赠送记录
			logger.info("insert marketingActivityAwardRecord start.");
			super.insertRecommenderRecordInMarketingActivityAwardRecord(para, recommender.getAccountId(), MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER_OVER, ProjectConstant.MARKETING_AWARD_TYPE_MONEY,
					giveInvestmentMoneyAmount + "", giveInvestmentMoneyRemark, ProjectConstant.MARKET_AWARD_CAUSE_TYPE_FRIEND_INVESTMENT, Long.parseLong(recordId));
			logger.info("insert marketingActivityAwardRecord end.");
		}
		
		if(recordId.equals(fifthRecordId + "") && ProjectConstant.RECOMMENDER_TYPE_NORMAL.equals(customerAccount.getRecommenderType()) && recommender!=null) {		//是被推荐人第5次投资且推荐人为正常用户
			//送推荐人投资券
			logger.info("do give investmentTicket start. accountId:" + recommender.getAccountId() + ", denominations:" + giveInvestmentTicketDenominations);
			customerInvestmentTicketHandler.giveCustomerTickets(recommender.getAccountId(), giveInvestmentTicketDenominations, giveInvestmentTicketRemark);
			logger.info("do give investmentTicket end.");
			//插入marketingActivityAwardRecord赠送记录
			logger.info("insert marketingActivityAwardRecord start.");
			for(int denomination : giveInvestmentTicketDenominations) {
				super.insertRecommenderRecordInMarketingActivityAwardRecord(para, recommender.getAccountId(), MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER_OVER, ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET,
						denomination + "", giveInvestmentTicketRemark, ProjectConstant.MARKET_AWARD_CAUSE_TYPE_FRIEND_INVESTMENT, Long.parseLong(recordId));
			}
			logger.info("insert marketingActivityAwardRecord end.");
		}
		logger.info("=====market activity 1001 investmentTenderOver method  end=====");
		return para;
	}
}
