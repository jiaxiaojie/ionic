package com.thinkgem.jeesite.modules.marketing.handler;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.handler.CustomerIntegralSnapshotHandler;
import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAwardRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.SachetRecordDao;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.dao.MessageCreateRuleChannelDao;
import com.thinkgem.jeesite.modules.message.dao.MessageCreateRuleDao;
import com.thinkgem.jeesite.modules.message.dao.MessageCreateRuleTermDao;
import com.thinkgem.jeesite.modules.message.handler.MessageCreateRule1002Handler;
import com.thinkgem.jeesite.modules.message.handler.MessageCreateRule1003Handler;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.hsbank.util.type.DatetimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 欢乐粽动员
 * Created by ydt on 2016/5/23.
 */
@Component
public class MarketActivity1019Handler extends MarketActivityBaseSupportHandler {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SachetRecordDao sachetRecordDao;
	@Autowired
	private CustomerIntegralSnapshotHandler customerIntegralSnapshotHandler;
	@Autowired
	private MarketingActivityAwardRecordDao marketingActivityAwardRecordDao;
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	private ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	@Autowired
	private MessageCreateRuleDao messageCreateRuleDao;
	@Autowired
	private MessageCreateRuleChannelDao messageCreateRuleChannelDao;
	@Autowired
	private MessageCreateRuleTermDao messageCreateRuleTermDao;
	@Autowired
	private MessageCreateRule1003Handler messageCreateRule1003Handler;

	@Override
	@Transactional(readOnly = false)
	public Map<String,Object> luckDraw(Map<String,Object> map) {
		if(!StringUtils.lowerCaseFirstLetter(this.getClass().getSimpleName()).equals(map.get(MarketConstant.HANDLER_PARA))) {
			return map;
		}
		Long accountId = (Long)map.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		Long activityId = (Long)map.get(MarketConstant.ACTICITY_PARA);
		String opTerm = (String)map.get(MarketConstant.CHANNEL_PARA);
		if("get".equals(map.get("action"))) {
			//领取香囊
			if(sachetRecordDao.getGetSachetCountByAccountIdAndDate(activityId, accountId, new Date()) > 0) {
				map.put("isSuccess", false);
				map.put("message", "每天只能领取一个香囊哦");
				return map;
			}
			//添加领取香囊记录
			this.addSachetRecord(activityId, accountId, 1, opTerm, "", "领取香囊");
			if(sachetRecordDao.getGetSachetCountByAccountIdAndDate(activityId, accountId, new Date()) > 1) {
				throw new ServiceException("系统异常，请稍后重试");
			}
			map.put("isSuccess", true);
			map.put("result", 1);
			logger.info("the date 【"+ DatetimeUtil.datetimeToString(new Date())+"】, accountId +" + accountId +"领取香囊");
		} else if("exchange".equals(map.get("action"))) {
			//兑换奖品
			int sachetCount = sachetRecordDao.getSachetCountByAccountId(activityId, accountId);
			int spendSachet;
			int integral;
			double randomNumber = (new Random()).nextDouble();
			if("1".equals(map.get("prize"))) {
				spendSachet = 1;
				integral = randomNumber >= 0.7 ? 100 : 50;
			} else if("2".equals(map.get("prize"))) {
				spendSachet = 3;
				integral = randomNumber >= 0.7 ? 500 : 200;
			} else if("3".equals(map.get("prize"))) {
				spendSachet = 5;
				integral = randomNumber >= 0.7 ? 800 : 600;
			} else {
				throw new ServiceException("系统异常，请稍后重试");
			}
			if(sachetCount < spendSachet) {
				map.put("isSuccess", false);
				map.put("message", "您的香囊数量不够，无法打开这个礼物呦～!");
				return map;
			}

			//添加香囊兑换奖品记录
			String prize = integral + "花生豆";
			this.addSachetRecord(activityId, accountId, -spendSachet, opTerm, prize, "兑换奖品");
			//兑换花生豆
			customerIntegralSnapshotHandler.changeIntegralValue(accountId, integral, ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, "欢乐“粽”动员", opTerm);
			//增加活动奖励记录
			this.addActivityAwardRecord(activityId, accountId, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW, opTerm, ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL, String.valueOf(integral));
			//发送消息
			this.sendMessage(prize, accountId);
			if(sachetRecordDao.getSachetCountByAccountId(activityId, accountId) < 0) {
				throw new ServiceException("系统异常，请稍后重试");
			}
			map.put("isSuccess", true);
			map.put("result", integral);
		} else {
			throw new ServiceException("系统异常，请稍后重试");
		}
		return map;
	}

	@Override
	@Transactional(readOnly = false)
	public Map<String,Object> investmentTender(Map<String,Object> map) {
		Long activityId = (Long)map.get(MarketConstant.ACTICITY_PARA);
		Long accountId = (Long)map.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		String recordId = String.valueOf(map.get(MarketConstant.RECORD_ID_PARA));
		String channelId = String.valueOf(map.get(MarketConstant.CHANNEL_PARA));
		double amount = (Double)map.get(MarketConstant.AMOUNT_PARA);
		ProjectInvestmentRecord projectInvestmentRecord = projectInvestmentRecordDao.get(recordId);
		ProjectBaseInfo projectBaseInfo = projectBaseInfoDao.get(projectInvestmentRecord.getProjectId() + "");
		if(amount >= MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[5] && "1".equals(projectBaseInfo.getIsNewUser())) {
			int awardIntegral = 2000;
			int[] awardInvestmentTickets = null;
			if(amount >= MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[0]) {
				awardIntegral = 15000;
				awardInvestmentTickets = new int[]{100, 50, 50};
			} else if(amount >= MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[1]) {
				awardIntegral = 7000;
				awardInvestmentTickets = new int[]{50, 50};
			} else if(amount >= MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[2]) {
				awardIntegral = 4000;
				awardInvestmentTickets = new int[]{50, 10};
			} else if(amount >= MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[3]) {
				awardIntegral = 1000;
				awardInvestmentTickets = new int[]{20};
			} else if(amount >= MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[4]) {
				awardIntegral = 0;
				awardInvestmentTickets = new int[]{10};
			}
			if(awardIntegral > 0) {
				//赠送花生豆
				customerIntegralSnapshotHandler.changeIntegralValue(accountId, awardIntegral,
						ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, "欢乐“粽”动员", (String)map.get(MarketConstant.CHANNEL_PARA));
                //增加活动奖励记录
				this.addActivityAwardRecord(activityId, accountId, MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER, channelId, ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL, String.valueOf(awardIntegral));
			}
			if(awardInvestmentTickets != null) {
				//赠送现金券
				customerInvestmentTicketHandler.giveCustomerTickets(accountId, awardInvestmentTickets, "欢乐“粽”动员");
				for(int awardInvestmentTicket : awardInvestmentTickets) {
					//增加活动奖励记录
					this.addActivityAwardRecord(activityId, accountId, MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER, channelId, ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET, String.valueOf(awardInvestmentTicket));
				}
			}
			//发送消息
			int sumTickets = 0;
			if(awardInvestmentTickets != null){
				for(int ticket : awardInvestmentTickets){
					sumTickets +=ticket;
				}
			}
			StringBuffer strBuf = new StringBuffer();
			if(sumTickets > 0 && awardIntegral > 0){
				strBuf.append(sumTickets).append("元现金券,").append(awardIntegral).append("花生豆");
			}else if(sumTickets > 0){
				strBuf.append(sumTickets).append("元现金券");
			}else if(awardIntegral > 0){
				strBuf.append(awardIntegral).append("花生豆");
			}
			this.sendMessage(strBuf.toString(),accountId);
		}
		return map;
	}

	/**
	 * 发送消息
	 * @param params
     */
	public void sendMessage(String params, Long accountId){
		MessageCreateRule messageCreateRule = messageCreateRuleDao
				.getByClassName(StringUtils.lowerCaseFirstLetter(MessageCreateRule1003Handler.class.getSimpleName()));
		List<String> messageChannelList = messageCreateRuleChannelDao.findMessageChannelListByRuleId(Long.parseLong(messageCreateRule.getId()));
		messageCreateRule.setMessageChannelList(messageChannelList);
		List<String> termList = messageCreateRuleTermDao.findTermListByRuleId(Long.parseLong(messageCreateRule.getId()));
		messageCreateRule.setTermList(termList);
		Map<String,Object> para = new HashMap<String,Object>();
		para.put(MessageConstant.PARA_MESSAGE_CREATE_RULE, messageCreateRule);
		para.put(MessageConstant.PARA_BEHAVIOR_CODE, MessageConstant.BEHAVIOR_LUCK_DRAW);
		para.put(MessageConstant.PARA_ACCOUNT_ID, accountId);
		para.put(MessageConstant.PARA_OP_TERM, ProjectConstant.OP_TERM_DICT_PC);
		para.put(MessageConstant.PARA_PARAMS, params);
		messageCreateRule1003Handler.luckDraw(para);
	}

	/**
	 * 增加活动奖励记录
	 * @param activityId
	 * @param accountId
	 * @param behaviorCode
	 * @param channelId
	 * @param awardType
     * @param awardValue
     */
	public void addActivityAwardRecord(Long activityId, Long accountId, String behaviorCode,
									   String channelId, String awardType, String awardValue){
		MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
		marketingActivityAwardRecord.setActivityId(activityId);
		marketingActivityAwardRecord.setBehaviorCode(behaviorCode);
		marketingActivityAwardRecord.setChannelId(channelId);
		marketingActivityAwardRecord.setAccountId(accountId);
		marketingActivityAwardRecord.setAwardType(awardType);
		marketingActivityAwardRecord.setAwardDt(new Date());
		marketingActivityAwardRecord.setAwardValue(awardValue);
		marketingActivityAwardRecord.setAwardReason("欢乐“粽”动员");
		marketingActivityAwardRecord.setCauseType(ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF);
		marketingActivityAwardRecordDao.insert(marketingActivityAwardRecord);
	}

	/**
	 * 添加香囊记录
	 * @param activityId
	 * @param accountId
	 * @param changeValue
	 * @param opTerm
     * @param changeReason
     */
	public void addSachetRecord(Long activityId, Long accountId, Integer changeValue, String opTerm,String prize, String changeReason){
		SachetRecord sachetRecord = new SachetRecord();
		sachetRecord.setActivityId(activityId);
		sachetRecord.setAccountId(accountId);
		sachetRecord.setChangeValue(changeValue);
		sachetRecord.setChangeReason(changeReason);
		sachetRecord.setOpDt(new Date());
		sachetRecord.setOpTerm(opTerm);
		sachetRecord.setPrize(prize);
		sachetRecordDao.insert(sachetRecord);
	}
}
