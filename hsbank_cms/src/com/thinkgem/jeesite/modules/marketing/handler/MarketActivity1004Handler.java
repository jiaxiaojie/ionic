/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * @author yangtao
 *	2015-11-11双十一活动：
 *		1：用户注册并认证后送1.88元； 
 *		2：用户首次投资后送8元（活动开始后，满足条件即送）
 *		3：邀请好友注册，可获得1.88元/人，好友首次投资后还可获得2.88元（与长期活动--邀请好友规则叠加）
 */
@Deprecated
@Component("marketActivity1004Handler")
public class MarketActivity1004Handler extends MarketActivityBaseSupportHandler {
	Logger logger = Logger.getLogger(this.getClass());

	
	/**
	 * 开通第三方账号，送1.88元
	 *//*
	@Override
	@Transactional(readOnly = false)
	public Map<String, Object> openThirdParty(Map<String, Object> para) {
//		double giveAmount = 1.88;
		logger.info("market 1004 openThirdParty begin ...");
		super.openThirdParty(para);
		Long accountId = (Long) para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		CustomerAccount customerAccount = customerAccountDao.get(accountId);
//		// 注册并开通第三方账号赠送现金
//		String getRemark = "新用户注册并完成实名认证";
//		logger.info("---------" + DateUtils.formatDateTime(new Date())
//				+ "activity 1004:开通第三方账号，送1.88元，用户id:"
//				+ accountId);
//		yeepayCommonHandler.transferToCustomerFromPlatform(customerAccount,
//				giveAmount,
//				ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT,
//				getRemark, "");
//		// 若推荐人为普通用户
//		if (ProjectConstant.RECOMMENDER_TYPE_NORMAL.equals(customerAccount
//				.getRecommenderType())) {
//			// 推荐人信息
//			CustomerAccount recommender = customerAccountDao
//					.getByMobile(customerAccount.getRecommenderMobile());
//			// 推荐人奖励1.88
//			if(recommender!=null){
//				logger.info("activity 1004:奖励推荐人1.88，推荐人id:"
//						+ recommender.getAccountId());
//				yeepayCommonHandler
//						.transferToCustomerFromPlatform(
//								recommender,
//								giveAmount,
//								ProjectConstant.CHANGE_TYPE_BALANCE_RECHAGE_RECOMMEND_INVESTMENT,
//								"好友注册奖励", "");
//			}
//		}
		// 注册并开通第三方账号赠送投资券


		// 若推荐人为普通用户
		if (ProjectConstant.RECOMMENDER_TYPE_NORMAL.equals(customerAccount
				.getRecommenderType())) {
			// 推荐人信息
			CustomerAccount recommender = customerAccountDao
					.getByMobile(customerAccount.getRecommenderMobile());
			// 推荐人奖励5元券
			if(recommender!=null){
				logger.info("activity 1004:奖励推荐人5元券，推荐人id:"
						+ recommender.getAccountId());
				giveInvestmentTicket(recommender.getAccountId(), 5, "奖励推荐人5元券");
			}
		}
		
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":market 1004 openThirdParty end--------");
		return para;
	}

	*//**
	 * 投资送花生豆，用户首次投资后送8元 邀请人收2.88元
	 *//*
	@Override
	@Transactional(readOnly = false)
	public Map<String, Object> investmentTenderOver(Map<String, Object> map) {
		double giveAmount = 8.00;// 首次投资用户获得
		double giveAmount1 = 2.88;// 邀请人获得
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":marketing activity 1004 investmentTender start--------");
		super.investmentTender(map);
		long accountId = (Long) map.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		String recordId = (String) map.get(MarketConstant.RECORD_ID_PARA);
		// 投资人信息
		CustomerAccount customerAccount = customerAccountDao
				.get(accountId + "");
		// 投资人的首次投资记录
		ProjectInvestmentRecord firstRecord = projectInvestmentRecordDao
				.getCustomerFirstNormalRecord(accountId);
		if (recordId.equals(firstRecord.getId())) {
			// 投资人奖励 8.00
			logger.info("activity 1004:投资人8.00，投资记录id:" + recordId);
			yeepayCommonHandler
					.transferToCustomerFromPlatform(
							customerAccount,
							giveAmount,
							ProjectConstant.CHANGE_TYPE_BALANCE_RECHAGE_RECOMMEND_INVESTMENT,
							"新手首次投资返现", recordId);
		}
		// 若推荐人为普通用户
		if (ProjectConstant.RECOMMENDER_TYPE_NORMAL.equals(customerAccount
				.getRecommenderType())) {
			super.investmentTenderOver(map);
			// 推荐人信息
			CustomerAccount recommender = customerAccountDao
					.getByMobile(customerAccount.getRecommenderMobile());
			// 若是首次投资
			if ((recordId.equals(firstRecord.getId()))&&(recommender!=null)) {
				// 推荐人奖励2.88
				logger.info("activity 1004:投资奖励推荐人2.88，推荐人id:"
						+ recommender.getAccountId() + ",投资记录id:" + recordId);
				yeepayCommonHandler
						.transferToCustomerFromPlatform(
								recommender,
								giveAmount1,
								ProjectConstant.CHANGE_TYPE_BALANCE_RECHAGE_RECOMMEND_INVESTMENT,
								"好友首次投资返利", recordId);
			}else{
				logger.info("activity 1004:投资奖励推荐人2.88，推荐人为空,投资记录id:" + recordId);
			}
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":marketing activity 1004 investmentTender end--------");
		return map;
	}
	private void giveInvestmentTicket(long accountId, int ticketDenomination, String remark) {
		InvestmentTicketType investmentTicketType = investmentTicketTypeDao.getByDenomination(ticketDenomination);
		CustomerInvestmentTicket customerInvestmentTicket = new CustomerInvestmentTicket();
		customerInvestmentTicket.setAccountId(accountId);
		customerInvestmentTicket.setTicketTypeId(new Long(investmentTicketType.getId()));
		customerInvestmentTicket.setGetDt(new Date());
		customerInvestmentTicket.setGetRemark(remark);
		customerInvestmentTicket.setInvalidDt(DateUtils.dateAddDay(DateUtils.dateFormate(new Date()), investmentTicketType.getTermOfValidity()));
		customerInvestmentTicket.setStatus(ProjectConstant.CUSTOMER_INVESTMENT_TICKET_STATUS_NORMAL);
		customerInvestmentTicketDao.insert(customerInvestmentTicket);
	}
	*//**
	 * 注册入口，邀请人收到1.88元
	 * 
	 * @param para
	 * @return
	 *//*
	@Transactional(readOnly = false)
	public Map<String, Object> register(Map<String, Object> map) {
		
		 * double giveAmount = 1.88;// 推荐用户注册，推荐人获得奖励
		 * logger.info("---------" + DateUtils.formatDateTime(new Date()) +
		 * ":marketing activity 1004 register start--------");
		 * super.register(map); long accountId = (Long)
		 * map.get(MarketConstant.CUSTOMER_ACCOUNT_PARA); String opTerm =
		 * (String) map.get(MarketConstant.CHANNEL_PARA); CustomerAccount
		 * customerAccount = customerAccountDao .get(accountId + ""); //
		 * 若推荐人为普通用户 if
		 * (ProjectConstant.RECOMMENDER_TYPE_NORMAL.equals(customerAccount
		 * .getRecommenderType())) { // 推荐人信息 CustomerAccount recommender =
		 * customerAccountDao
		 * .getByMobile(customerAccount.getRecommenderMobile()); // 推荐人奖励1.88
		 * logger.info("activity 1004:奖励推荐人1.88，推荐人id:" +
		 * recommender.getAccountId()); yeepayCommonHandler
		 * .transferToCustomerFromPlatform( recommender, giveAmount,
		 * ProjectConstant.CHANGE_TYPE_BALANCE_RECHAGE_RECOMMEND_INVESTMENT,
		 * "好友注册奖励", ""); } logger.info("---------" +
		 * DateUtils.formatDateTime(new Date()) +
		 * ":marketing activity 1004 register end--------");
		 
		return map;
	}*/

}
