/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;

/**
 * @author ydt
 *
 * 推荐活动（长期活动）：
 *		1：被推荐人开通第三方账号，送推荐人2张10元投资券、1张5元券
 *		2：满5位被推荐人投资放款，送推荐人200元投资券（1张100元、1张50元、2张20元、1张10元）
 */
@Component
public class MarketActivity1013Handler extends MarketActivityBaseSupportHandler{
	Logger logger = Logger.getLogger(this.getClass());
	
	
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	
	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	
	/**
	 * 开通第三方账号
	 * 		1：送推荐人25元投资券
	 */
	@Override
	@Transactional(readOnly=false)
	public Map<String,Object> openThirdParty(Map<String,Object> para) {
		logger.info("=====market activity 1013 openThirdParty, 开通第三方账号送推荐人25元投资券 start=====");
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		int[] denominations = MarketConstant.ACTIVITY_1013_OPENTHIRDPARTY_GIVE_RECOMMENDER_INVESTMENT_TICKET_DENOMINATIONS;
		String remark = MarketConstant.ACTIVITY_1013_OPENTHIRDPARTY_GIVE_RECOMMENDER_INVESTMENT_TICKET_REMARK;
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
		logger.info("=====market activity 1013 openThirdParty, 开通第三方账号送推荐人25元投资券 end=====");
		return para;
	}

	/**
	 * 投资成功
	 * 		1：满5位被推荐人投资成功，送推荐人200元投资券
	 */
	@Override
	@Transactional(readOnly = false)
	public Map<String, Object> investmentTender(Map<String, Object> para) {
		logger.info("=====market activity 1013 investmentTender method start=====");
		//调用super.investmentTenderOver
		logger.info("super investmentTender method start");
		super.investmentTenderOver(para);
		logger.info("super investmentTender method end");
		
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		int[] giveInvestmentTicketDenominations = MarketConstant.ACTIVITY_1013_FIFTH_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_TICKET_DENOMINATIONS;
		String giveInvestmentTicketRemark = MarketConstant.ACTIVITY_1013_FIFTH_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_TICKET_REMARK;
		CustomerAccount customerAccount = customerAccountDao.get(accountId + "");		//投资人信息
		CustomerAccount recommender = customerAccountDao.getByMobile(customerAccount.getRecommenderMobile());		//推荐人信息
		String recordId = (String)para.get(MarketConstant.RECORD_ID_PARA);		//投资记录id
		Long fifthRecordId = projectInvestmentRecordDao.getFifthNormalRecordIdByRecommenderMobile(customerAccount.getRecommenderMobile());		//获取被推荐人的第5次正常投资记录Id（group by被推荐人）
		
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
		logger.info("=====market activity 1013 investmentTender method  end=====");
		return para;
	}
}
