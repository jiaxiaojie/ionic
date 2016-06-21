/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.customer.dao.CustomerBaseDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBalanceHandler;
import com.thinkgem.jeesite.modules.customer.handler.CustomerIntegralSnapshotHandler;
import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAwardRecordDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * @author lzb 
 * 
 *	女神升值季
 *
 */
@Component("marketActivity1011Handler")
public class MarketActivity1011Handler extends MarketActivityBaseSupportHandler{
	Logger logger = Logger.getLogger(this.getClass());
	
	
	@Autowired
	private CustomerIntegralSnapshotHandler customerIntegralSnapshotHandler;
	@Autowired
	private CustomerBaseDao customerBaseDao;
	@Autowired
	private MarketingActivityAwardRecordDao marketingActivityAwardRecordDao;
	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	@Autowired
	private CustomerBalanceHandler customerBalanceHandler;
	
	/**
	 * 投资完成
	 * 		1：女性用户投资即可获得双倍花生豆（投资项目不包括“活花生”）
	 * 		2：活动时间2016.3.8~3.20
	 */
	@Override
	@Transactional(readOnly=false)
	public Map<String,Object> investmentTenderOver(Map<String,Object> para) {
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		CustomerBase customerBase = customerBaseDao.getByAccountId(accountId);
		//女性用户
		if(ProjectConstant.SEX_FEMALE.equals(customerBase.getGenderCode())){
			logger.info("=====market activity 1011 investmentTenderOver, 女性用户投资送花生豆 start=====");
			//调用super.investmentTenderOver
			super.investmentTenderOver(para);
			//送用户花生豆
			int integralNumber = ((Double)para.get(MarketConstant.AMOUNT_PARA)).intValue();
			String remark = MarketConstant.ACTIVITY_1003_INVESTMENTTENDEROVER_GIVE_INTEGRAL_REMARK;
			logger.info("activity 1011 do give integral start. accountId:" + accountId + ", number:" + integralNumber);
			customerIntegralSnapshotHandler.changeIntegralValue(accountId, integralNumber, ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, remark, (String)para.get(MarketConstant.CHANNEL_PARA));
			logger.info("activity 1011 do give integral end.");
			//插入marketingActivityAwardRecord赠送记录
			logger.info("activity 1011 insert marketingActivityAwardRecord start.");
			super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER_OVER, ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL,
					integralNumber + "", remark);
			logger.info("activity 1011 insert marketingActivityAwardRecord end.");
			logger.info("=====market activity 1011 investmentTenderOver, 女性用户投资送花生豆 start送花生豆 end=====");
		}else{
			logger.info("activity 1011 female privilege can't get the more integral.");
		}
		return para;
	}
	
	@Override
	public Map<String, Object> luckDraw(Map<String, Object> para) {
		
		try{
			long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
			CustomerBase customerBase = customerBaseDao.getByAccountId(accountId);
			
			
			
			//如果是女性领取专享礼包活动
			if(MarketConstant.HANDLER_PARA_VAL_WOMAN_BAG.equals(para.get(MarketConstant.HANDLER_PARA)) ){
				
				para.put("result", "领取礼包失败！");
				para.put("resultCode", "3");
				para.put("isSuccess", true);
				
				if(customerBase.getGenderCode()==null || "".equals(customerBase.getGenderCode())){
					para.put("result", "女神？男神 ？先去完善下个人信息哦~");
				}
				else 
				if(ProjectConstant.SEX_FEMALE.equals(customerBase.getGenderCode())){//女性用户
					HashMap<String,Object> params = new HashMap<String,Object>();
					params.put("accountId", accountId);
					params.put("activityId", (long)para.get(MarketConstant.ACTICITY_PARA));
					//如果用户还未领取
					if(marketingActivityAwardRecordDao.getCountByAccountIdAndActivityId(params) == 0){
						logger.info("=====market activity 1011 investmentTenderOver, 女性用户女性领取专享礼包 start=====");
						String remark = MarketConstant.ACTIVITY_1011_WHEEL_GIVE_PRIZE_REMARK;
						
						//送50元现金券
						int[] ticketDenominations = MarketConstant.ACTIVITY_1011_WOMAN_BAG_TICKET_DENOMINATIONS;
						
						logger.info("do give investmentTicket start. accountId:" + accountId + ", denominations:" + ticketDenominations);
						customerInvestmentTicketHandler.giveCustomerTickets(accountId, ticketDenominations, remark);
						logger.info("do give investmentTicket end.");
						for(int denomination : ticketDenominations) {
							super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW, ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET,
									denomination + "", remark);
						}
						
						//送提现券1张
						int withdrawCount = MarketConstant.ACTIVITY_1011_WOMAN_BAG_WITHDRAW_TICKET_COUNT;
						logger.info("do give freeWithdrawCount start. accountId:" + accountId + ", value:" + withdrawCount);
						customerBalanceHandler.updateFreeWithCount(accountId, withdrawCount, ProjectConstant.FREE_WITHDRAW_COUNT_CHANGE_TYPE_ACTIVITY_GIVE);
						logger.info("do give freeWithdrawCount end.");
						super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW, ProjectConstant.MARKETING_AWARD_TYPE_FREE_WITHDRAW_COUNT,
								withdrawCount + "", remark);
						
						//送1000颗花生豆
						int peanutBeanCount = MarketConstant.ACTIVITY_1011_WOMAN_BAG_PEANUT_BEAN_COUNT;
						logger.info("do give integral start. accountId:" + accountId + ", number:" + peanutBeanCount);
						customerIntegralSnapshotHandler.changeIntegralValue(accountId, peanutBeanCount, ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, remark, (String)para.get(MarketConstant.CHANNEL_PARA));
						logger.info("do give integral end.");
						super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW, ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL,
								peanutBeanCount + "", remark);
						
						
						logger.info("=====market activity 1011 investmentTenderOver, 女性用户女性领取专享礼包 end=====");
						
						para.put("result", "恭喜女神！您已成功领取礼包！");
						para.put("resultCode", "2");
					}
					else{
						para.put("result", "女神，您已领取过礼包！");
						para.put("resultCode", "0");
					}
				}
				else{
					para.put("result", "女神的奶酪碰不得，快叫上你的女神来参与吧~");
					para.put("resultCode", "1");
				}
			}
		}catch(Exception e){
			para.put("isSuccess", false);
		}
		return super.luckDraw(para);
	}
}
