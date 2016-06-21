package com.thinkgem.jeesite.modules.marketing.handler;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerIntegralSnapshotHandler;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.*;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import com.hsbank.util.tool.MobileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 一呼百医活动
 * Created by ydt on 2016/5/5.
 */
@Component
public class MarketActivity1018Handler extends MarketActivityBaseSupportHandler {
	//手机号奖励记录状态：待赠送
	public static String MOBILE_AWARD_RECORD_STATUS_GIVING = "giving";
	//手机号奖励记录状态：已赠送
	public static String MOBILE_AWARD_RECORD_STATUS_GIVEN = "given";
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	private MarketingWheelPrizeInfoDao marketingWheelPrizeInfoDao;
	@Autowired
	private MarketingWheelPrizeInstanceDao marketingWheelPrizeInstanceDao;
	@Autowired
	private MobileAwardRecordDao mobileAwardRecordDao;
	@Autowired
	private CustomerIntegralSnapshotHandler customerIntegralSnapshotHandler;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	@Autowired
	private MarketingWheelGetPrizeRecordDao marketingWheelGetPrizeRecordDao;
	@Autowired
	private MarketingActivityAwardRecordDao marketingActivityAwardRecordDao;

	/**
	 * 得到用户手机号，若手机号中奖纪录中有此未赠送的非现金奖励。则
	 * 		1.赠送奖励
	 * 		2.更新此手机号中奖纪录状态
	 * @param para
	 * @return
	 */
	@Override
	public Map<String,Object> register(Map<String,Object> para) {
		Long accountId = (Long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		Long activityId = (Long)para.get(MarketConstant.ACTICITY_PARA);
		CustomerAccount customerAccount = customerAccountDao.get(accountId);
		MobileAwardRecord mobileAwardRecord = mobileAwardRecordDao.getByActivityIdAndMobileAndStatus(activityId,
				customerAccount.getCustomerBase().getMobile(), MOBILE_AWARD_RECORD_STATUS_GIVING);
		if(mobileAwardRecord == null) {
			return para;
		}
		MarketingWheelPrizeInstance marketingWheelPrizeInstance = marketingWheelPrizeInstanceDao.getInfo(mobileAwardRecord.getPrizeInstanceId());
		if(ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL.equals(marketingWheelPrizeInstance.getPrizeType())) {
			customerIntegralSnapshotHandler.changeIntegralValue(customerAccount.getAccountId(), Integer.parseInt(marketingWheelPrizeInstance.getPrizeValue()),
					ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, "活动奖励", mobileAwardRecord.getOpTerm());
			marketingWheelPrizeInstanceDao.updateStatus(marketingWheelPrizeInstance.getId(), MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_GOT);
			insertMarketingGetPrizeRecord(Long.parseLong(marketingWheelPrizeInstance.getId()), customerAccount.getAccountId(),
					MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVEN, mobileAwardRecord.getOpTerm());
			insertMarketingActivityAwardRecord(activityId, customerAccount.getAccountId(), marketingWheelPrizeInstance, mobileAwardRecord.getOpTerm());
			finishMobileAward(mobileAwardRecord, customerAccount.getAccountId());
		} else if(ProjectConstant.MARKETING_AWARD_TYPE_OBJECT.equals(marketingWheelPrizeInstance.getPrizeType())) {
			marketingWheelPrizeInstanceDao.updateStatus(marketingWheelPrizeInstance.getId(), MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_LOCK);
			insertMarketingGetPrizeRecord(Long.parseLong(marketingWheelPrizeInstance.getId()), customerAccount.getAccountId(),
					MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVING, mobileAwardRecord.getOpTerm());
			finishMobileAward(mobileAwardRecord, customerAccount.getAccountId());
		}
		return para;
	}

	/**
	 * 得到用户手机号，若手机号中奖纪录中有此未赠送的现金奖励。则
	 * 		1.赠送奖励
	 * 		2.更新此手机号中奖纪录状态
	 * @param para
	 * @return
	 */
	@Override
	public Map<String,Object> openThirdParty(Map<String,Object> para) {
		Long accountId = (Long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		Long activityId = (Long)para.get(MarketConstant.ACTICITY_PARA);
		CustomerAccount customerAccount = customerAccountDao.get(accountId);
		MobileAwardRecord mobileAwardRecord = mobileAwardRecordDao.getByActivityIdAndMobileAndStatus(activityId,
				customerAccount.getCustomerBase().getMobile(), MOBILE_AWARD_RECORD_STATUS_GIVING);
		if(mobileAwardRecord == null) {
			return para;
		}
		MarketingWheelPrizeInstance marketingWheelPrizeInstance = marketingWheelPrizeInstanceDao.getInfo(mobileAwardRecord.getPrizeInstanceId());
		if(ProjectConstant.MARKETING_AWARD_TYPE_MONEY.equals(marketingWheelPrizeInstance.getPrizeType())) {
			marketingWheelPrizeInstanceDao.updateStatus(marketingWheelPrizeInstance.getId(), MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_GOT);
			insertMarketingGetPrizeRecord(Long.parseLong(marketingWheelPrizeInstance.getId()), customerAccount.getAccountId(),
					MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVEN, mobileAwardRecord.getOpTerm());
			insertMarketingActivityAwardRecord(activityId, customerAccount.getAccountId(), marketingWheelPrizeInstance, mobileAwardRecord.getOpTerm());
			finishMobileAward(mobileAwardRecord, customerAccount.getAccountId());
			yeepayCommonHandler.transferToCustomerFromPlatform(customerAccount.getAccountId(), Double.parseDouble(marketingWheelPrizeInstance.getPrizeValue()),
					ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT, "活动奖励", null);
		}
		return para;
	}

	/**
	 * 		抽奖
	 * 			(1)非法手机号
	 * 				a.抽奖失败，返回信息
	 * 			(2)查询mobile中奖数量
	 * 				a.大于0：抽奖失败，返回信息
	 * 			(3)抽奖
	 * 				a.插入手机号奖励记录
	 * 				b.若可赠送：赠送奖励
	 * 			(4)查询此手机号奖励记录，若有多笔记录则抛出异常
	 * @param para
	 * @return
	 */
	@Override
	public synchronized Map<String,Object> luckDraw(Map<String,Object> para) {
		if(!StringUtils.lowerCaseFirstLetter(this.getClass().getSimpleName()).equals(para.get(MarketConstant.HANDLER_PARA))) {
			return para;
		}
		String mobile = (String)para.get("mobile");
		Long activityId = (Long)para.get(MarketConstant.ACTICITY_PARA);
		String opTerm = (String)para.get(MarketConstant.CHANNEL_PARA);
		if(!MobileUtil.isMobile(mobile)) {
			para.put("isSuccess", false);
			para.put("message", "手机号非法");
			return para;
		}
		if(mobileAwardRecordDao.getCountByActivityIdAndMobile(activityId, mobile) > 0) {
			MobileAwardRecord mobileAwardRecord = mobileAwardRecordDao.getByActivityIdAndMobileAndStatus(activityId, mobile, null);
			MarketingWheelPrizeInfo marketingWheelPrizeInfo = marketingWheelPrizeInfoDao.getByInstanceId(mobileAwardRecord.getPrizeInstanceId());
			para.put("isSuccess", false);
			para.put("message", marketingWheelPrizeInfo.getGetTips());
			return para;
		}
		//抽奖
		int index = 0;
		CustomerAccount customerAccount = customerAccountDao.getByMobile(mobile);
		MarketingWheelPrizeInstance marketingWheelPrizeInstance = luckDraw(activityId, index);
		//如果奖品为实物且已赠送过非投资用户实物且此用户未非投资用户，则重新获取奖品，直至奖品不为实物
		while(ProjectConstant.MARKETING_AWARD_TYPE_OBJECT.equals(marketingWheelPrizeInstance.getPrizeType())
				&& mobileAwardRecordDao.getCountByActivityIdAndAwardType(activityId, ProjectConstant.MARKETING_AWARD_TYPE_OBJECT) > 0
				&& (customerAccount == null || projectInvestmentRecordDao.getInvestCountByAccountId(customerAccount.getAccountId()) < 1)) {
			marketingWheelPrizeInstance = luckDraw(activityId, index++);
		}
		//插入手机号奖励记录（待赠送）
		MobileAwardRecord mobileAwardRecord = new MobileAwardRecord();
		mobileAwardRecord.setActivityId(activityId);
		mobileAwardRecord.setMobile(mobile);
		mobileAwardRecord.setPrizeInstanceId(Long.parseLong(marketingWheelPrizeInstance.getId()));
		mobileAwardRecord.setOpDt(new Date());
		mobileAwardRecord.setOpTerm(opTerm);
		mobileAwardRecord.setStatus(MOBILE_AWARD_RECORD_STATUS_GIVING);
		mobileAwardRecordDao.insert(mobileAwardRecord);
		//更新奖品实例状态
		marketingWheelPrizeInstanceDao.updateStatus(marketingWheelPrizeInstance.getId(), MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_LOCK);
		//如果可以赠送奖励，则直接赠送。（用户已注册且当奖励为现金是用户必须开通第三方账号）
		if(customerAccount != null && ProjectConstant.MARKETING_AWARD_TYPE_OBJECT.equals(marketingWheelPrizeInstance.getPrizeType())) {
			insertMarketingGetPrizeRecord(Long.parseLong(marketingWheelPrizeInstance.getId()), customerAccount.getAccountId(),
					MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVING, opTerm);
			finishMobileAward(mobileAwardRecord, customerAccount.getAccountId());
		} else if(customerAccount != null && (!ProjectConstant.MARKETING_AWARD_TYPE_MONEY.equals(marketingWheelPrizeInstance.getPrizeType())
				|| ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount()))) {
			marketingWheelPrizeInstanceDao.updateStatus(marketingWheelPrizeInstance.getId(), MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_GOT);
			insertMarketingGetPrizeRecord(Long.parseLong(marketingWheelPrizeInstance.getId()), customerAccount.getAccountId(),
					MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVEN, opTerm);
			insertMarketingActivityAwardRecord(activityId, customerAccount.getAccountId(), marketingWheelPrizeInstance, opTerm);
			finishMobileAward(mobileAwardRecord, customerAccount.getAccountId());
		}
		if(mobileAwardRecordDao.getCountByActivityIdAndMobile(activityId, mobile) > 1) {
			throw new ServiceException("系统异常，请稍后重试");
		}
		//检测未重复提交后再赠送奖励，防止重复赠送用户现金
		if(MOBILE_AWARD_RECORD_STATUS_GIVEN.equals(mobileAwardRecord.getStatus())
				&& ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL.equals(marketingWheelPrizeInstance.getPrizeType())) {
			customerIntegralSnapshotHandler.changeIntegralValue(customerAccount.getAccountId(), Integer.parseInt(marketingWheelPrizeInstance.getPrizeValue()),
					ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, "活动奖励", opTerm);
		} else if(MOBILE_AWARD_RECORD_STATUS_GIVEN.equals(mobileAwardRecord.getStatus())
				&& ProjectConstant.MARKETING_AWARD_TYPE_MONEY.equals(marketingWheelPrizeInstance.getPrizeType())) {
			yeepayCommonHandler.transferToCustomerFromPlatform(customerAccount.getAccountId(), Double.parseDouble(marketingWheelPrizeInstance.getPrizeValue()),
					ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT, "活动奖励", null);
		}
		para.put("isSuccess", true);
		para.put("prizeInstanceId", Long.parseLong(marketingWheelPrizeInstance.getId()));
		return para;
	}

	private void finishMobileAward(MobileAwardRecord mobileAwardRecord, Long accountId) {
		mobileAwardRecord.setStatus(MOBILE_AWARD_RECORD_STATUS_GIVEN);
		mobileAwardRecord.setAccountId(accountId);
		mobileAwardRecord.setAwardDt(new Date());
		mobileAwardRecordDao.update(mobileAwardRecord);
	}

	private void insertMarketingActivityAwardRecord(Long activityId, Long accountId, MarketingWheelPrizeInstance marketingWheelPrizeInstance, String opTerm) {
		MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
		marketingActivityAwardRecord.setActivityId(activityId);
		marketingActivityAwardRecord.setBehaviorCode(MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
		marketingActivityAwardRecord.setChannelId(opTerm);
		marketingActivityAwardRecord.setAccountId(accountId);
		marketingActivityAwardRecord.setAwardType(marketingWheelPrizeInstance.getPrizeType());
		marketingActivityAwardRecord.setAwardDt(new Date());
		marketingActivityAwardRecord.setAwardValue(marketingWheelPrizeInstance.getPrizeValue());
		marketingActivityAwardRecord.setAwardReason(MarketConstant.ACTIVITY_1018_AWARD_REMARK);
		marketingActivityAwardRecord.setCauseType(ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF);
		marketingActivityAwardRecordDao.insert(marketingActivityAwardRecord);
	}

	private void insertMarketingGetPrizeRecord(Long prizeInstanceId, Long accountId, String status, String opTerm) {
		MarketingWheelGetPrizeRecord marketingGetPrizeRecord = new MarketingWheelGetPrizeRecord();
		marketingGetPrizeRecord.setPrizeInstanceId(prizeInstanceId);
		marketingGetPrizeRecord.setGetDt(new Date());
		marketingGetPrizeRecord.setOpTerm(opTerm);
		marketingGetPrizeRecord.setAccountId(accountId);
		marketingGetPrizeRecord.setStatus(status);
		if(MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVEN.equals(status)) {
			marketingGetPrizeRecord.setGiveDt(new Date());
		}
		marketingWheelGetPrizeRecordDao.insert(marketingGetPrizeRecord);
	}

	/**
	 * 抽奖
	 * 		1.获取指定条数、状态为status的奖品实例
	 * 		2.若实例为空，则创建一条新的奖品实例
	 * @param activityId
	 * @param index 第几条奖品，从0开始
	 * @return
	 */
	private MarketingWheelPrizeInstance luckDraw(long activityId, int index) {
		MarketingWheelPrizeInstance marketingWheelPrizeInstance = marketingWheelPrizeInstanceDao.getByActivityIdAndStatus(activityId, MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_NORMAL, index);
		if(marketingWheelPrizeInstance == null) {
			MarketingWheelPrizeInfo marketingWheelPrizeInfo = marketingWheelPrizeInfoDao.getDefaultPrize(activityId);
			marketingWheelPrizeInstance = new MarketingWheelPrizeInstance();
			marketingWheelPrizeInstance.setPrizeId(Long.parseLong(marketingWheelPrizeInfo.getId()));
			marketingWheelPrizeInstance.setStatus(MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_NORMAL);
			marketingWheelPrizeInstance.setPrizeType(marketingWheelPrizeInfo.getType());
			marketingWheelPrizeInstance.setPrizeValue(marketingWheelPrizeInfo.getValue());
			marketingWheelPrizeInstanceDao.insert(marketingWheelPrizeInstance);
		}
		return marketingWheelPrizeInstance;
	}
}
