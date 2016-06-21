package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
/**
 * 
 * @author 万端瑞
 *
 */
@Component("marketActivity1017Handler")
public class MarketActivity1017Handler  extends MarketActivityBaseSupportHandler{

	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	
	/**
	 * 裘老板婚礼，用户扫码注册，送1800元现金券。
	 * 活动时间2016.4.29 0:00 ~2016.5.7 0:00
	 */
	@Override
	public Map<String, Object> register(Map<String, Object> para) {
		//判断赠送条件
		String sourceChannel =  ( para.get(MarketConstant.SOURCE_CHANNEL_PARA)==null ? null : (String)para.get(MarketConstant.SOURCE_CHANNEL_PARA) );
		String qiuMarryScanCodeRegistChannelNo = Global.getConfig("qiuMarryScanCodeRegistChannelNo");
		if(StringUtils.isNotBlank(qiuMarryScanCodeRegistChannelNo)){
			if(!qiuMarryScanCodeRegistChannelNo.trim().equals(sourceChannel)){
				return para;
			}
		}else{
			return para;
		}
		
		
		
		logger.info("=====market activity 1017 register, 裘老板婚礼，用户扫码注册，送1800元现金券 start=====");
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		
		String remark = MarketConstant.ACTIVITY_1017_QIU_LAO_BAN_MARRY_REMARK;
		int[] ticketDenominations = MarketConstant.ACTIVITY_1017_QIU_LAO_BAN_MARRY_TICKET_DENOMINATIONS;
		
		logger.info("do give investmentTicket start. accountId:" + accountId + ", denominations:" + ticketDenominations);
		customerInvestmentTicketHandler.giveCustomerTickets(accountId, ticketDenominations, remark);
		logger.info("do give investmentTicket end. accountId:" + accountId + ", denominations:" + ticketDenominations);
		
		for(int denomination : ticketDenominations) {
				super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW, ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET,
						denomination + "", remark);
		}
		
		logger.info("=====market activity 1017 register, 裘老板婚礼，用户扫码注册，送1800元现金券 end=====");
		return para;
	}
}
