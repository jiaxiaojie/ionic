package com.thinkgem.jeesite.modules.marketing.handler;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.handler.CustomerIntegralSnapshotHandler;
import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.entity.EuropeanCupTopScorer;
import com.thinkgem.jeesite.modules.entity.MarketingWheelGetPrizeRecord;
import com.thinkgem.jeesite.modules.entity.MarketingWheelPrizeInfo;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketingWheelUtils;
import com.thinkgem.jeesite.modules.marketing.dao.EuropeanCupTopScorerDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityInfoDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelGetPrizeRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelPrizeInfoDao;
import com.thinkgem.jeesite.modules.marketing.service.MarketingMoneyAwardRecordService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 欧洲杯-刮奖
 * Created by lzb on 2016/6/13.
 */
@Component
public class MarketActivity1021Handler extends MarketActivityBaseSupportHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MarketingActivityInfoDao marketingActivityInfoDao;
	@Autowired
	private MarketingWheelPrizeInfoDao marketingWheelPrizeInfoDao;
	@Autowired
	private MarketingWheelGetPrizeRecordDao marketingWheelGetPrizeRecordDao;
	@Autowired
	private EuropeanCupTopScorerDao europeanCupTopScorerDao;
	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	@Autowired
	private CustomerIntegralSnapshotHandler customerIntegralSnapshotHandler;
	@Autowired
	private MarketingActivityAwardRecordHandler marketingActivityAwardRecordHandler;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	@Autowired
	private MarketingMoneyAwardRecordService marketingMoneyAwardRecordService;


	/**
	 * 刮奖前置信息
	 * @param accountId
	 * @param activityId
     * @return
     */
	public  Map<String, Object> lotteryInfo(Long accountId, Long activityId){
		Map<String,Object> data = new HashMap<String,Object>();
		EuropeanCupTopScorer topScorer = europeanCupTopScorerDao.getByAccountIdAndActivityId(accountId, activityId);
		int count = topScorer != null ? topScorer.getTotalGoals() : 0;
		int used = topScorer != null ? topScorer.getUsedScratchTimes() : 0;
		int over = count >= used ? count - used : 0;
		data.put("count", count);
		data.put("used", used);
		data.put("over", over);
		return data;
	}

	@Override
	@Transactional(readOnly = false)
	public Map<String,Object> luckDraw(Map<String,Object> map) {
		logger.info("=====market activity 1021 luckDraw start=====");
		Long accountId = (Long)map.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		Long activityId = marketingActivityInfoDao.getByBizClassName(StringUtils.lowerCaseFirstLetter(this.getClass().getSimpleName())).getActicityId();
		Long activity1020Id = marketingActivityInfoDao.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1020Handler.class.getSimpleName())).getActicityId();
		String handlerClassName = map.get(MarketConstant.HANDLER_PARA) == null ? null : (String)map.get(MarketConstant.HANDLER_PARA);
		if(!StringUtils.lowerCaseFirstLetter(this.getClass().getSimpleName()).equals(handlerClassName)) {
			return map;
		}
		EuropeanCupTopScorer topScorer = europeanCupTopScorerDao.getByAccountIdAndActivityId(accountId, activity1020Id);
		if(topScorer == null || (topScorer.getTotalGoals().intValue() - topScorer.getUsedScratchTimes().intValue()) <= 0) {
			map.put("isSuccess", false);
			map.put("message", "哎呦！您的刮奖机会为0次，分享至朋友圈参与活动赢取更多刮奖机会！");
			return map;
		}
		Long instanceId = 0L;
		MarketingWheelGetPrizeRecord prizeRecord = marketingWheelGetPrizeRecordDao.getPrizeRecordByAccountIdAndActivityId(accountId, activityId, MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVING);
		if(prizeRecord != null){
			instanceId = prizeRecord.getPrizeInstanceId();
		}else{
			//刮奖
			instanceId = MarketingWheelUtils.poll(accountId, activityId, String.valueOf(map.get(MarketConstant.CHANNEL_PARA)));
		}

		MarketingWheelPrizeInfo marketingWheelPrizeInfo = marketingWheelPrizeInfoDao.getByInstanceId(instanceId);
		map.put("isSuccess", true);
		map.put("result", marketingWheelPrizeInfo.getGetTips());
		logger.info("=====market activity 1021 luckDraw end=====");
		return map;
	}


	/**
	 * 领取刮刮奖
	 * @param accountId
	 * @param activityId
     * @return
     */
	@Transactional(readOnly = false)
	public  Map<String, Object> getPrize(Long accountId, Long activityId){
		Map<String,Object> data = new HashMap<String,Object>();
		Long activity1020Id = marketingActivityInfoDao.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1020Handler.class.getSimpleName())).getActicityId();
		MarketingWheelGetPrizeRecord prizeRecord = marketingWheelGetPrizeRecordDao.getPrizeRecordByAccountIdAndActivityId(accountId, activityId, MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVING);
        if(prizeRecord != null){
			Long instanceId = prizeRecord.getPrizeInstanceId();
			MarketingWheelPrizeInfo marketingWheelPrizeInfo = marketingWheelPrizeInfoDao.getByInstanceId(instanceId);
			europeanCupTopScorerDao.updateUsedScratchTimesByAccountIdAndActivityId(accountId,activity1020Id,1);
			logger.info("market activity 1021 poll getPrize instance id:" + instanceId + ", prize id:" + marketingWheelPrizeInfo.getId());
			EuropeanCupTopScorer topScorer = europeanCupTopScorerDao.getByAccountIdAndActivityId(accountId, activity1020Id);
			if((topScorer.getTotalGoals().intValue() - topScorer.getUsedScratchTimes().intValue()) < 0) {
				logger.info("=====market activity 1021 getPrize exception, because remaining times is negative number=====");
				throw new ServiceException("data error.");
			}
			//将刮到的花生豆、现金券实时送给用户
			if(MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVING.equals(prizeRecord.getStatus())){
				boolean givenPrize = false;
				if(ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET.equals(marketingWheelPrizeInfo.getType())) {
					logger.info("market activity 1021 getPrize do give customer investment ticket start. denomination:" + marketingWheelPrizeInfo.getValue());
					customerInvestmentTicketHandler.giveCustomerTickets(accountId, new int[]{Integer.parseInt(marketingWheelPrizeInfo.getValue())}, MarketConstant.ACTIVITY_1021_EUROPEAN_CUP_SCRATCH_REWARD_REMARK);
					givenPrize = true;
				}else if(ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL.equals(marketingWheelPrizeInfo.getType())) {
					logger.info("market activity 1021 getPrize do give customer integral start. value:" + Integer.parseInt(marketingWheelPrizeInfo.getValue()));
					customerIntegralSnapshotHandler.changeIntegralValue(accountId, Integer.parseInt(marketingWheelPrizeInfo.getValue()),
							ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, MarketConstant.ACTIVITY_1021_EUROPEAN_CUP_SCRATCH_REWARD_REMARK, prizeRecord.getOpTerm());
					givenPrize = true;
				}else if(ProjectConstant.MARKETING_AWARD_TYPE_MONEY.equals(marketingWheelPrizeInfo.getType())) {
					logger.info("market activity 1021 getPrize do give customer money start. amount:" + marketingWheelPrizeInfo.getValue());
					//现金奖励插入到现金奖励记录表中，随后由task完成现金赠送动作
					marketingMoneyAwardRecordService.offlineGiveMoney(accountId, Double.parseDouble(marketingWheelPrizeInfo.getValue()), MarketConstant.ACTIVITY_1021_EUROPEAN_CUP_SCRATCH_REWARD_REMARK, Long.parseLong(prizeRecord.getId()), ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF,
							MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW, prizeRecord.getOpTerm(), activityId);
					givenPrize = true;
				}
				if(givenPrize){
					marketingWheelGetPrizeRecordDao.updateStatusToGiven(prizeRecord.getId());
					marketingActivityAwardRecordHandler.insertRecordWhenGiveAward(accountId, activityId,
							MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW, prizeRecord.getOpTerm(),
							marketingWheelPrizeInfo.getType(), marketingWheelPrizeInfo.getValue(),
							MarketConstant.ACTIVITY_1021_EUROPEAN_CUP_SCRATCH_REWARD_REMARK, ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF, null);
				}
			}
		}
		data.put("isSuccess", true);
		return data;
	}


}
