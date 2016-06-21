/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAwardRecord;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAwardRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityInfoDao;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1008Handler;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1013Handler;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1014Handler;
import com.thinkgem.jeesite.modules.marketing.handler.MarketingActivityAwardRecordHandler;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 营销活动奖励记录Service
 * @author ydt
 * @version 2015-11-12
 */
@Service
@Transactional(readOnly = true)
public class MarketingActivityAwardRecordService extends CrudService<MarketingActivityAwardRecordDao, MarketingActivityAwardRecord> {
	@Autowired
	private MarketingActivityAwardRecordHandler marketingActivityAwardRecordHandler;
	@Autowired
	CustomerAccountDao customerAccountDao;
	@Autowired
	YeepayCommonHandler yeepayCommonHandler;
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	private MarketingActivityInfoDao marketingActivityInfoDao;
	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;

	public MarketingActivityAwardRecord get(String id) {
		return super.get(id);
	}
	
	public List<MarketingActivityAwardRecord> findList(MarketingActivityAwardRecord marketingActivityAwardRecord) {
		return super.findList(marketingActivityAwardRecord);
	}
	
	public Page<MarketingActivityAwardRecord> findPage(Page<MarketingActivityAwardRecord> page, MarketingActivityAwardRecord marketingActivityAwardRecord) {
		return super.findPage(page, marketingActivityAwardRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(MarketingActivityAwardRecord marketingActivityAwardRecord) {
		super.save(marketingActivityAwardRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(MarketingActivityAwardRecord marketingActivityAwardRecord) {
		super.delete(marketingActivityAwardRecord);
	}
	
	/**
	 * 添加得奖记录
	 * @param accountId
	 * @param activityId
	 * @param behaviorCode
	 * @param channelId
	 * @param awardType
	 * @param awardValue
	 * @param awardReason
	 * @param causeType
	 * @param causeId
	 */
	@Transactional(readOnly = false)
	public void insertRecordWhenGiveAward(long accountId, long activityId, String behaviorCode, String channelId, String awardType,
			String awardValue, String awardReason, String causeType, Long causeId) {
		marketingActivityAwardRecordHandler.insertRecordWhenGiveAward(accountId, activityId, behaviorCode, channelId, awardType, awardValue, awardReason, causeType, causeId);
	}

	/**
	 * 查询用户邀请好友的收益
	 * @param accountId
	 * @param pageSearchBean
	 * @return
	 */
	public Page<MarketingActivityAwardRecord> findCustomerEarningCauseByFriendPage(Long accountId, PageSearchBean pageSearchBean) {
		Page<MarketingActivityAwardRecord> page = new Page<MarketingActivityAwardRecord>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("accountId", accountId);
		map.put("startDateTime", pageSearchBean.getStartDateTime());
		map.put("endDateTime", pageSearchBean.getEndDateTime());
		page.setList(dao.findCustomerEarningCauseByFriendPage(map));
		return page;
	}
	
	/**
	 * 选择查询好友参加活动福利
	 * @param awardType
	 * @param causeType
	 * @return
	 */
	public List<MarketingActivityAwardRecord> findListByFriendSelected(Long accountId, String causeType) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("causeType", causeType);
		return dao.findListByFriendSelected(map);
	}

	/**
	 * 获取用户某一活动得到的奖励列表
	 * @param accountId
	 * @param activityId
	 * @return
	 */
	public List<MarketingActivityAwardRecord> findListByAccountIdAndActivityId(Long accountId, Long activityId) {
		return dao.findListByAccountIdAndActivityId(accountId, activityId);
	}
	
	/**
	 * 获取用户某一活动得到的奖励次数
	 * @param accountId
	 * @param activityId
	 * @return
	 */
	public Integer getCountByAccountIdAndActivityId(Long accountId, Long activityId){
		return dao.getCountByAccountIdAndActivityId(accountId, activityId);
	}
	
	/**
	 * 将双旦活动的奖励赠送给用户
	 * @param projectInvestmentRecord
	 */
	@Transactional(readOnly = false)
	public void doGiveDoubleEggPrize(ProjectInvestmentRecord projectInvestmentRecord, MarketingActivityInfo marketingActivityInfo) {
		try {
			logger.info("do give double egg prize start. projectInvestmentRecord id:" + projectInvestmentRecord.getId() + ", investmentUserId:" + projectInvestmentRecord.getInvestmentUserId() + ".");
			Long recommenderAccountId = customerAccountDao.getRecommenderAccountIdByAccountId(projectInvestmentRecord.getInvestmentUserId());
			if(recommenderAccountId == null) {
				logger.info("Un given recommender prize, because recommender is doesn't exist. projectInvestmentRecord id:" + projectInvestmentRecord.getId() + ", investmentUserId:" + projectInvestmentRecord.getInvestmentUserId() + ".");
				return;
			}
			List<MarketingActivityAwardRecord> marketingActivityAwardRecordList = dao.findListByAccountIdAndActivityId(recommenderAccountId, marketingActivityInfo.getActicityId());
			marketingActivityAwardRecordList = marketingActivityAwardRecordList == null ? new ArrayList<MarketingActivityAwardRecord>() : marketingActivityAwardRecordList;
			if(marketingActivityAwardRecordList.size() > 0 && hasAwarded(recommenderAccountId, marketingActivityInfo.getActicityId(), projectInvestmentRecord.getInvestmentUserId())) {
				logger.info("Un given recommender prize, because has awarded. projectInvestmentRecord id:" + projectInvestmentRecord.getId() + ", investmentUserId:" + projectInvestmentRecord.getInvestmentUserId() + ".");
				return;
			}
			double awardAmount = getAwardAmount(marketingActivityAwardRecordList.size());
			logger.info("this is " + (marketingActivityAwardRecordList.size() + 1) + "th award, the awardAmount:" + awardAmount);
			logger.info("giving recommender prize start. amount:" + awardAmount + ", projectInvestmentRecord id:" + projectInvestmentRecord.getId() + ", investmentUserId:" + projectInvestmentRecord.getInvestmentUserId() + ".");
			yeepayCommonHandler.transferToCustomerFromPlatform(recommenderAccountId, awardAmount,
					ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT, MarketConstant.ACTIVITY_1014_GIVE_RECOMMENDER_MONEY_REMARK, "");
			logger.info("giving recommender prize end.");
			logger.info("marketingActivityAwardRecord start.");
			MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
			marketingActivityAwardRecord.setAccountId(recommenderAccountId);
			marketingActivityAwardRecord.setActivityId(marketingActivityInfo.getActicityId());
			marketingActivityAwardRecord.setAwardDt(new Date());
			marketingActivityAwardRecord.setAwardReason(MarketConstant.ACTIVITY_1014_GIVE_RECOMMENDER_MONEY_REMARK);
			marketingActivityAwardRecord.setAwardType(ProjectConstant.MARKETING_AWARD_TYPE_MONEY);
			marketingActivityAwardRecord.setAwardValue(String.valueOf(awardAmount));
			marketingActivityAwardRecord.setBehaviorCode(MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER);
			marketingActivityAwardRecord.setChannelId(projectInvestmentRecord.getOpTerm());
			marketingActivityAwardRecord.setCauseType(ProjectConstant.MARKET_AWARD_CAUSE_TYPE_FRIEND_INVESTMENT);
			marketingActivityAwardRecord.setCauseId(Long.parseLong(projectInvestmentRecord.getId()));
			dao.insert(marketingActivityAwardRecord);
			logger.info("insert marketingActivityAwardRecord end.");
			logger.info("do give double egg prize end. projectInvestmentRecord id:" + projectInvestmentRecord.getId() + ", investmentUserId:" + projectInvestmentRecord.getInvestmentUserId() + ".");
		} catch(Exception e) {
			e.printStackTrace();
			logger.info("=====doGiveDoubleEggPrize error, projectInvestmentRecord id:" + projectInvestmentRecord.getId() + ", investmentUserId:" + projectInvestmentRecord.getInvestmentUserId() + ".=====");
		}
	}

	private boolean hasAwarded(long accountId, long activityId, long friendId) {
		//获取用户某一活动因某一好友得到奖励的次数
		int awardCount = dao.getCountByActivityIdAndFriendId(accountId, activityId, friendId);
		return awardCount > 0 ? true : false;
	}
	
	private static double getAwardAmount(int hasAwardedTimes) {
		if(hasAwardedTimes % 5 == 4) {
			return 50d;
		}
		return 10d;
	}

	/**
	 * 获取一月份投资奖励奖励（在活动期间内完成一笔投资，即送5元现金券（投标后即，只送1次））
	 * @param accountId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> getJanuaryActivityAward(Long accountId) {
		Map<String,Object> result = new HashMap<String,Object>();
		if(accountId == null) {
			result.put("isSuccess", false);
			result.put("message", "发生了意外错误，请重试");
			return result;
		}
		if(hasGetJanuaryAward(accountId)) {
			result.put("isSuccess", false);
			result.put("message", "已获得现金券奖励，不能再次获取");
			return result;
		}
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoDao.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1008Handler.class.getSimpleName()));
		ProjectInvestmentRecord firstRecordDuringJanuaryActivity = projectInvestmentRecordDao.getCustomerFirstNormalRecordDuringTime(accountId,
				DateUtils.dateFormateDayOfTheBeginTime(marketingActivityInfo.getBeginDate()),
				DateUtils.dateFormateDayOfTheLastTime(marketingActivityInfo.getEndDate()));		//获取用户首次投资记录
		if(firstRecordDuringJanuaryActivity != null) {		//活动期间内有首次投资送现金券
			//送用户现金券
			customerInvestmentTicketHandler.giveCustomerTickets(accountId, MarketConstant.ACTIVITY_1008_FIRST_INVESTMENTTENDER_GIVE_INVESTMENT_TICKET_DENOMINATIONS,
					MarketConstant.ACTIVITY_1008_FIRST_INVESTMENTTENDER_GIVE_INVESTMENT_TICKET_REMARK);
			int awardValue = 0;
			//插入marketingActivityAwardRecord赠送记录
			for(int denomination : MarketConstant.ACTIVITY_1008_FIRST_INVESTMENTTENDER_GIVE_INVESTMENT_TICKET_DENOMINATIONS) {
				awardValue += denomination;
				MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
				marketingActivityAwardRecord.setAccountId(accountId);
				marketingActivityAwardRecord.setActivityId(marketingActivityInfo.getActicityId());
				marketingActivityAwardRecord.setAwardDt(new Date());
				marketingActivityAwardRecord.setAwardReason(MarketConstant.ACTIVITY_1008_FIRST_INVESTMENTTENDER_GIVE_INVESTMENT_TICKET_REMARK);
				marketingActivityAwardRecord.setAwardType(ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET);
				marketingActivityAwardRecord.setAwardValue(String.valueOf(denomination));
				marketingActivityAwardRecord.setBehaviorCode(MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER);
				marketingActivityAwardRecord.setChannelId(firstRecordDuringJanuaryActivity.getOpTerm());
				marketingActivityAwardRecord.setCauseType(ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF);
				marketingActivityAwardRecord.setCauseId(Long.parseLong(firstRecordDuringJanuaryActivity.getId()));
				dao.insert(marketingActivityAwardRecord);
			}
			result.put("isSuccess", true);
			result.put("message", "恭喜您，现金券获取成功");
			result.put("awardValue", awardValue);
		} else {
			result.put("isSuccess", false);
			result.put("message", "活动期间内未投资不能获取现金券，请先去投资");
		}
		return result;
	}

	/**
	 * 查询用户是否获得过一月份奖励
	 * @param accountId
	 * @return
	 */
	public boolean hasGetJanuaryAward(Long accountId) {
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoDao.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1008Handler.class.getSimpleName()));
		return dao.getCountByAccountIdAndActivityId(accountId, marketingActivityInfo.getActicityId()) > 0 ? true : false;
	}
	

	/**
	 * 统计奖励 现金金额及现金券金额
	 * @param accountId
	 * @return
	 */
	public Map<String,Object> getFriendsTotalAwardValue(long accountId){
		Map<String,Object> map = new HashMap<String,Object>();
		MarketingActivityInfo activityInfo1013 = marketingActivityInfoDao.getByBizClassName(StringUtils.lowerCaseFirstLetter(new MarketActivity1013Handler().getClass().getSimpleName()));
		MarketingActivityInfo activityInfo1014 = marketingActivityInfoDao.getByBizClassName(StringUtils.lowerCaseFirstLetter(new MarketActivity1014Handler().getClass().getSimpleName()));
		map.put("ticketMoney", getTotalAwardValue(activityInfo1013, ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET, accountId));
		map.put("cashMoney", getTotalAwardValue(activityInfo1014, ProjectConstant.MARKETING_AWARD_TYPE_MONEY, accountId));
		return map;
	}
	
	/**
	 * 获取奖励值
	 * @param activityInfo
	 * @param accountId
	 * @return
	 */
	public BigDecimal getTotalAwardValue(MarketingActivityInfo activityInfo,String awardType, long accountId){
		BigDecimal totalAwardValue = new BigDecimal(0);
		if(activityInfo!=null){
			Date startDateTime = DateUtils.parseDate(DateUtils.formatDate(activityInfo.getBeginDate(),"yyyy-MM-dd") + " " + activityInfo.getBeginTime());
			Date endDateTime = DateUtils.parseDate(DateUtils.formatDate(activityInfo.getEndDate(),"yyyy-MM-dd") + " " + activityInfo.getEndTime());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("accountId", accountId);
			map.put("awardType", awardType);
			map.put("activityId", activityInfo.getActicityId());
			map.put("startDateTime", startDateTime);
			map.put("endDateTime", endDateTime);
			Double awardValue = dao.getFriendsTotalAwardValue(map);
			awardValue = awardValue !=null ? awardValue : 0.00;
			totalAwardValue = new BigDecimal(awardValue).setScale(0,   BigDecimal.ROUND_HALF_UP);
		}
		return totalAwardValue;
	}
	
	/**
	 * 统计奖励总额
	 * @param accountId
	 * @param awardType
	 * @return
	 */
	public Double getSumAwardValueByAccountId(long accountId, String awardType){
		return dao.getSumAwardValueByAccountId(accountId, awardType);
	}
}