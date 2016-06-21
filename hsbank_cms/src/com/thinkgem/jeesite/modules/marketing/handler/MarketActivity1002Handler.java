/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * 	@author yangtao
  	*2015-9-11中秋国庆双节活动规则
  	*规则1：活动期间用户摇一摇可随机获得10元或20元投资券一张，每天每人仅可摇一次，登录网站后，可在我的账户—我的现金券中查看。
	*规则2：单笔投资满2000元即可使用10元投资券，单笔投资满4000元可使用20元投资券。
	*规则3：所有用户投资交易，即可返现投资额的5‰到个人账户。
	*规则4：投资券有限期3个月，从发放日起开始计算。
	*规则5：此活动和新用户注册送券可叠加。
 */
@Deprecated
@Component("marketActivity1002Handler")
public class MarketActivity1002Handler extends MarketActivityBaseSupportHandler{
	Logger logger = Logger.getLogger(this.getClass());
	
	
	
	/**
	 * 赠送投资人千分之五的现金奖励
	 */
	/*@Override
	@Transactional(readOnly=false)
	public Map<String,Object> investmentTender(Map<String,Object> map) {
		logger.info("market 1002 investmentTender begin ...");
		super.investmentTender(map);
		String remark = "中秋活动赠送";
		long accountId = (Long)map.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		double amount = (Double)map.get(MarketConstant.AMOUNT_PARA);
		String recordId = (String)map.get(MarketConstant.RECORD_ID_PARA);
		CustomerAccount customerAccount = customerAccountDao.get(accountId + "");
		//计算赠送金额，保留两位小数
		double giveAmount = LoanUtil.formatAmount(amount*5 / 1000);
		logger.info("1002 activity 返现  "+accountId+" 账号 "+giveAmount+" 元  begin");
		yeepayCommonHandler.transferToCustomerFromPlatform(customerAccount, giveAmount, ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT, remark, recordId);
		logger.info("1002 activity 返现  "+accountId+" 账号 "+giveAmount+" 元  end");
		logger.info("market 1002 investmentTender end ...");
		return null;
	}*/
}
