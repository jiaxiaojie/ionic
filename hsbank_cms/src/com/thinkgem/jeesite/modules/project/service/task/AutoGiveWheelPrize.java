package com.thinkgem.jeesite.modules.project.service.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.customer.service.CustomerIntegralSnapshotService;
import com.thinkgem.jeesite.modules.customer.service.CustomerInvestmentTicketService;
import com.thinkgem.jeesite.modules.entity.MarketingWheelGetPrizeRecord;
import com.thinkgem.jeesite.modules.entity.MarketingWheelPrizeInfo;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityAwardRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingWheelGetPrizeRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingWheelPrizeInfoService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 自动将中奖奖品发给用户
 * @author ydt
 *
 */
@Component
public class AutoGiveWheelPrize {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private MarketingWheelPrizeInfoService marketingWheelPrizeInfoService;
	@Autowired
	private MarketingWheelGetPrizeRecordService marketingWheelGetPrizeRecordService;
	@Autowired
	private CustomerInvestmentTicketService customerInvestmentTicketService;
	@Autowired
	private MarketingActivityAwardRecordService marketingActivityAwardRecordService;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	@Autowired
	private CustomerIntegralSnapshotService customerIntegralSnapshotService;
	
	/**
	 * 将待送奖的记录发放给用户，每日22:22:00执行一次
	 * 		奖品类型为：
	 * 			投资券：赠送给用户并更改中奖记录为已赠送状态，并添加活动奖励记录数据
	 * 			现金：赠送并更改中奖记录为已赠送状态，并添加活动奖励记录数据
	 * 			花生豆：赠送花生豆，并添加花生豆流水记录，并添加活动奖励记录数据
	 * 			实物：不做处理
	 */
	public void job() {
		logger.info("=====autoGiveWheelPrize start=====");
		List<MarketingWheelGetPrizeRecord> list = marketingWheelGetPrizeRecordService.findListByStatus(MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVING);
		for(MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord : list) {
			MarketingWheelPrizeInfo marketingWheelPrizeInfo = marketingWheelPrizeInfoService.getByInstanceId(marketingWheelGetPrizeRecord.getPrizeInstanceId());
			long accountId = marketingWheelGetPrizeRecord.getAccountId();
			boolean givenPrize = false;
			logger.info("marketingWheelGetPrizeRecordId:" + marketingWheelGetPrizeRecord.getId() + ", accountId:" + accountId
					+ ", prizeId:" + marketingWheelPrizeInfo.getId());
			if(ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET.equals(marketingWheelPrizeInfo.getType())) {
				logger.info("do give customer investment ticket start. denomination:" + marketingWheelPrizeInfo.getValue());
				customerInvestmentTicketService.giveCustomerTickets(accountId, new int[]{Integer.parseInt(marketingWheelPrizeInfo.getValue())}, "活动奖励");
				givenPrize = true;
				logger.info("do give customer investment ticket end");
			} else if(ProjectConstant.MARKETING_AWARD_TYPE_MONEY.equals(marketingWheelPrizeInfo.getType())) {
				logger.info("do give customer money start. amount:" + marketingWheelPrizeInfo.getValue());
				yeepayCommonHandler.transferToCustomerFromPlatform(accountId, Double.parseDouble(marketingWheelPrizeInfo.getValue()),
						ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT, "活动奖励", null);
				givenPrize = true;
				logger.info("do give customer money end");
			} else if(ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL.equals(marketingWheelPrizeInfo.getType())) {
				logger.info("do give customer integral start. value:" + Integer.parseInt(marketingWheelPrizeInfo.getValue()));
				customerIntegralSnapshotService.giveIntegral(accountId, Integer.parseInt(marketingWheelPrizeInfo.getValue()),
						ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, "活动奖励", marketingWheelGetPrizeRecord.getOpTerm());
				givenPrize = true;
				logger.info("do give customer integral end.");
			}
			if(givenPrize) {
				logger.info("update getPrizeRecord status and insert activityAwardRecord start");
				marketingWheelGetPrizeRecordService.updateStatusToGiven(marketingWheelGetPrizeRecord.getId());
				marketingActivityAwardRecordService.insertRecordWhenGiveAward(accountId, marketingWheelPrizeInfo.getActivityId(),
						MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW, marketingWheelGetPrizeRecord.getOpTerm(),
						marketingWheelPrizeInfo.getType(), marketingWheelPrizeInfo.getValue(),
						"活动奖励", ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF, null);
				logger.info("update getPrizeRecord status and insert activityAwardRecord end");
			}
		}
		logger.info("=====autoGiveWheelPrize end=====");
	}
}
