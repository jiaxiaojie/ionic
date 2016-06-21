/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.MarketingWheelLotteryTimes;
import com.thinkgem.jeesite.modules.entity.MarketingWheelPrizeInfo;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketingWheelUtils;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityInfoDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelLotteryTimesDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelPrizeInfoDao;

/**
 * @author ydt
 * 
 *大转盘抽奖
 *
 */
@Component("marketActivity1010Handler")
public class MarketActivity1010Handler extends MarketActivityBaseSupportHandler{

	@Autowired
	private MarketingWheelPrizeInfoDao marketingWheelPrizeInfoDao;
	@Autowired
	private MarketingWheelLotteryTimesDao marketingWheelLotteryTimesDao;
	@Autowired
	private MarketingActivityInfoDao marketingActivityInfoDao;
	
	/**
	 * 抽奖
	 */
	@Override
	@Transactional(readOnly = false)
	public Map<String, Object> luckDraw(Map<String, Object> para) {
		logger.info("=====market activity 1010 luckDraw start=====");
		Long accountId = (Long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		Long activityId = marketingActivityInfoDao.getByBizClassName(StringUtils.lowerCaseFirstLetter(this.getClass().getSimpleName())).getActicityId();
		String handlerClassName = para.get(MarketConstant.HANDLER_PARA) == null ? null : (String)para.get(MarketConstant.HANDLER_PARA);
		if(!StringUtils.lowerCaseFirstLetter(this.getClass().getSimpleName()).equals(handlerClassName)) {
			return para;
		}
		MarketingWheelLotteryTimes lotteryTimes = marketingWheelLotteryTimesDao.getByAccountIdAndActivityId(accountId, activityId);
		if(lotteryTimes == null || (lotteryTimes.getTotalTimes().intValue() - lotteryTimes.getUsedTimes().intValue()) <= 0) {
			para.put("isSuccess", false);
			para.put("message", "立即投资赢更多机会！分享至朋友圈每天能多1次机会哦！");
			return para;
		}
		Long instanceId = MarketingWheelUtils.poll(accountId, activityId, String.valueOf(para.get(MarketConstant.CHANNEL_PARA)));
		MarketingWheelPrizeInfo marketingWheelPrizeInfo = marketingWheelPrizeInfoDao.getByInstanceId(instanceId);
		marketingWheelLotteryTimesDao.updateUsedTimesByAccountIdAndActivityId(accountId, activityId, 1);
		lotteryTimes = marketingWheelLotteryTimesDao.getByAccountIdAndActivityId(accountId, activityId);
		if((lotteryTimes.getTotalTimes().intValue() - lotteryTimes.getUsedTimes().intValue()) < 0) {
			logger.info("=====market activity 1010 luckDraw exception, because remaining times is negative number=====");
			throw new ServiceException("data error.");
		}
		logger.info("poll prize instance id:" + instanceId + ", prize id:" + marketingWheelPrizeInfo.getId());
		para.put("isSuccess", true);
		para.put("angle", marketingWheelPrizeInfo.getRotate());
		para.put("result", marketingWheelPrizeInfo.getGetTips());
		logger.info("=====market activity 1010 luckDraw end=====");
		return para;
	}
	
	/**
	 * 投资获取抽奖次数
	 */
	@Override
	@Transactional(readOnly=false)
	public Map<String,Object> investmentTender(Map<String,Object> para) {
		logger.info("=====market activity 1010 investmentTender, 投资获取摇钱树抽奖次数 start=====");
		Long activityId = marketingActivityInfoDao.getByBizClassName(StringUtils.lowerCaseFirstLetter(this.getClass().getSimpleName()))
				.getActicityId();
		String recordId = String.valueOf(para.get(MarketConstant.RECORD_ID_PARA));
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		Double amount = (Double)para.get(MarketConstant.AMOUNT_PARA);
		amount = amount !=null ? amount : 0.00;
		int times = (int) (amount/MarketConstant.ACTIVITY_1010_REQUIRE_INVESTMENT_AMOUNT);
		logger.info("market activity 1010 the recordId:" + recordId + ",accountId:" + accountId + ",investment amount:" + amount);
		logger.info("get times:"+times);
		if(times > 0){
			MarketingWheelLotteryTimes mLotteryTimes = marketingWheelLotteryTimesDao.getByAccountIdAndActivityId(accountId, activityId);
			if(mLotteryTimes != null){
				marketingWheelLotteryTimesDao.updateTotalTimesByAccountIdAndActivityId(accountId, activityId, times);
			}else{
				MarketingWheelLotteryTimes mWheelLotteryTimes = new MarketingWheelLotteryTimes();
				mWheelLotteryTimes.setAccountId(accountId);
				mWheelLotteryTimes.setTotalTimes(2 + times);
				mWheelLotteryTimes.setUsedTimes(0);
				mWheelLotteryTimes.setActivityId(activityId);
				marketingWheelLotteryTimesDao.insert(mWheelLotteryTimes);
			}
		}else{
			logger.info("the accountId " + accountId + "投资金额小于" + MarketConstant.ACTIVITY_1009_REQUIRE_INVESTMENT_AMOUNT + ",无法获取抽奖机会.");
		}
		logger.info("=====market activity 1010 investmentTender, 投资获取摇钱树抽奖次数 end=====");
		return para;
	}
}
