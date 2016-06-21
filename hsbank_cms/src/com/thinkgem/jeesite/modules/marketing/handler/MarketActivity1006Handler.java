/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAwardRecordDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * @author ydt
 * 
 * 首次登录客户端（android,ios）送20元现金券（2张10元券）
 *
 */
@Component("marketActivity1006Handler")
public class MarketActivity1006Handler extends MarketActivityBaseSupportHandler{

	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	@Autowired
	private MarketingActivityAwardRecordDao marketingActivityAwardRecordDao;
	
	/**
	 * 首次登录客户端送20元现金券
	 */
	@Override
	@Transactional(readOnly = false)
	public Map<String, Object> login(Map<String, Object> para) {
		logger.info("=====market activity 1006 login start=====");
		long thisActivityId = (Long)para.get(MarketConstant.ACTICITY_PARA);
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		int[] denominations = MarketConstant.ACTIVITY_1006_FIRST_LOGIN_CLIENT_GIVE_INVESTMENT_TICKET_DENOMINATIONS;
		String remark = MarketConstant.ACTIVITY_1006_FIRST_LOGIN_CLIENT_GIVE_INVESTMENT_TICKET_REMARK;
		if(marketingActivityAwardRecordDao.getCountByAccountIdAndActivityId(accountId, thisActivityId) == 0) {		//若之前奖励未赠送，则赠送奖励（因符合条件的客户端才能进入此活动，故无需判断登录客户端是否符合条件）
			//调用super.login
			logger.info("super login method start");
			super.login(para);
			logger.info("super login method end");
			//送用户现金券
			logger.info("do give investmentTicket start. accountId:" + accountId + ", denominations:" + denominations);
			customerInvestmentTicketHandler.giveCustomerTickets(accountId, denominations, remark);
			logger.info("do give investmentTicket end.");
			//插入marketingActivityAwardRecord赠送记录
			logger.info("insert marketingActivityAwardRecord start.");
			for(int denomination : denominations) {
				super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_LOGIN, ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET,
						denomination + "", remark);
			}
			logger.info("insert marketingActivityAwardRecord end.");
		}
		logger.info("=====market activity 1006 luckDraw end=====");
		return para;
	}
}
