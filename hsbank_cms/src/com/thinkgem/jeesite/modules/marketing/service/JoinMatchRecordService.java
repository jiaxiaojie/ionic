/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

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
import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.entity.JoinMatchRecord;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAwardRecord;
import com.thinkgem.jeesite.modules.entity.MessageCreateRule;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.JoinMatchRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAwardRecordDao;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.dao.MessageCreateRuleChannelDao;
import com.thinkgem.jeesite.modules.message.dao.MessageCreateRuleDao;
import com.thinkgem.jeesite.modules.message.dao.MessageCreateRuleTermDao;
import com.thinkgem.jeesite.modules.message.handler.MessageCreateRule1001Handler;
import com.thinkgem.jeesite.modules.message.handler.MessageCreateRule1002Handler;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 参赛记录Service
 * @author ydt
 * @version 2016-04-20
 */
@Service
@Transactional(readOnly = true)
public class JoinMatchRecordService extends CrudService<JoinMatchRecordDao, JoinMatchRecord> {
	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	@Autowired
	private MarketingActivityAwardRecordDao marketingActivityAwardRecordDao;
	@Autowired
	private MessageCreateRule1001Handler messageCreateRule1001Handler;
	@Autowired
	private MessageCreateRule1002Handler messageCreateRule1002Handler;
	@Autowired
	private MessageCreateRuleDao messageCreateRuleDao;
	@Autowired
	private MessageCreateRuleChannelDao messageCreateRuleChannelDao;
	@Autowired
	private MessageCreateRuleTermDao messageCreateRuleTermDao;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	
	public JoinMatchRecord get(String id) {
		return super.get(id);
	}
	
	public List<JoinMatchRecord> findList(JoinMatchRecord joinMatchRecord) {
		return super.findList(joinMatchRecord);
	}
	
	public Page<JoinMatchRecord> findPage(Page<JoinMatchRecord> page, JoinMatchRecord joinMatchRecord) {
		return super.findPage(page, joinMatchRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(JoinMatchRecord joinMatchRecord) {
		super.save(joinMatchRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(JoinMatchRecord joinMatchRecord) {
		super.delete(joinMatchRecord);
	}

	/**
	 * 根据活动，及活动的参赛情况 获取有效投资排行榜（有效投资额：参赛后的投资）
	 * @param activityId
	 * @return
	 */
	public List<Map<String, Object>> getOnedayInvestmentRankByActivityId(Long activityId, Date date, int limit) {
		return dao.getOnedayInvestmentRankByActivityId(activityId, date, limit);
	}

	/**
	 * 	赠送排行榜奖励
	 * 	1.赠送奖励
	 * 	2.添加奖励记录
	 * 	3.产生消息
	 * @param rank
	 * @param map
	 * @param activityId
	 * @param opTerm
	 */
	@Transactional(readOnly = false)
	public void giveRankAward(int rank, Map<String, Object> map, long activityId, String opTerm) {
		Long accountId = (Long)map.get("accountId");
		int[] ticketDenominations = MarketConstant.ACTIVITY_1015_INVESTMENT_RANK_AWARD_TICKET_DENOMINATIONS[rank];
		customerInvestmentTicketHandler.giveCustomerTickets(accountId, ticketDenominations, MarketConstant.ACTIVITY_1015_INVESTMENT_RANK_AWARD_TICKET_REMARK);
		int awardTicketAmount = 0;
		for(int denomination : ticketDenominations) {
			awardTicketAmount += denomination;
			MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
			marketingActivityAwardRecord.setAccountId(accountId);
			marketingActivityAwardRecord.setActivityId(activityId);
			marketingActivityAwardRecord.setAwardDt(new Date());
			marketingActivityAwardRecord.setAwardReason(MarketConstant.ACTIVITY_1015_INVESTMENT_RANK_AWARD_TICKET_REMARK);
			marketingActivityAwardRecord.setAwardType(ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET);
			marketingActivityAwardRecord.setAwardValue(String.valueOf(denomination));
			marketingActivityAwardRecord.setBehaviorCode(MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER);
			marketingActivityAwardRecord.setChannelId(opTerm);
			marketingActivityAwardRecord.setCauseType(ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF);
			marketingActivityAwardRecord.setCauseId(null);
			marketingActivityAwardRecordDao.insert(marketingActivityAwardRecord);
		}
		MessageCreateRule messageCreateRule = messageCreateRuleDao
				.getByClassName(StringUtils.lowerCaseFirstLetter(MessageCreateRule1001Handler.class.getSimpleName()));
		List<String> messageChannelList = messageCreateRuleChannelDao.findMessageChannelListByRuleId(Long.parseLong(messageCreateRule.getId()));
		messageCreateRule.setMessageChannelList(messageChannelList);
		List<String> termList = messageCreateRuleTermDao.findTermListByRuleId(Long.parseLong(messageCreateRule.getId()));
		messageCreateRule.setTermList(termList);
		Map<String,Object> para = new HashMap<String,Object>();
		para.put(MessageConstant.PARA_MESSAGE_CREATE_RULE, messageCreateRule);
		para.put(MessageConstant.PARA_ACCOUNT_ID, accountId);
		para.put(MessageConstant.PARA_AMOUNT, awardTicketAmount);
		para.put(MessageConstant.PARA_BEHAVIOR_CODE, MessageConstant.BEHAVIOR_INVESTMENT_TENDER);
		para.put(MessageConstant.PARA_OP_TERM, ProjectConstant.OP_TERM_DICT_PC);
		para.put(MessageConstant.PARA_DATE, DateUtils.addDays(new Date(), -1));
		para.put("rank", rank + 1);
		messageCreateRule1001Handler.investmentTender(para);
	}

	/**
	 * 获取时间段内某队有效投资额（时间段：date(startDate)<=date(investmentDt)<=date(endDate)）（有效投资额：参赛后的投资）
	 * @param activityId
	 * @param side
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Double getSideInvestmentAmount(Long activityId, String side, Date startDate, Date endDate) {
		return dao.getSideInvestmentAmount(activityId, side, startDate, endDate);
	}

	/**
	 * 根据activityId获取参赛记录
	 * @param activityId
	 * @return
	 */
	public List<JoinMatchRecord> findListByActivityId(Long activityId) {
		return dao.findListByActivityId(activityId);
	}

	/**
	 * 获取用户某活动时间段内有效投资额（时间段：date(startDate)<=date(investmentDt)<=date(endDate)）（有效投资额：参赛后的投资）
	 * @param acticityId
	 * @param accountId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public double getUserInvestmentAmount(Long acticityId, Long accountId, Date startDate, Date endDate) {
		return dao.getUserInvestmentAmount(acticityId, accountId, startDate, endDate);
	}

	/**
	 * 赠送拔河比赛奖励
	 * 	1.添加奖励记录
	 * 	2.生成消息
	 * 	3.赠送奖励
	 * @param accountId
	 * @param activityId
	 * @param awardAmount
	 * @param opTerm
	 */
	@Transactional
	public void giveTugWarAward(Long accountId, Long activityId, double awardAmount, String opTerm) {
		MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
		marketingActivityAwardRecord.setAccountId(accountId);
		marketingActivityAwardRecord.setActivityId(activityId);
		marketingActivityAwardRecord.setAwardDt(new Date());
		marketingActivityAwardRecord.setAwardReason(MarketConstant.ACTIVITY_1015_INVESTMENT_MATCH_AWARD_REMARK);
		marketingActivityAwardRecord.setAwardType(ProjectConstant.MARKETING_AWARD_TYPE_MONEY);
		marketingActivityAwardRecord.setAwardValue(String.valueOf(awardAmount));
		marketingActivityAwardRecord.setBehaviorCode(MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER);
		marketingActivityAwardRecord.setChannelId(opTerm);
		marketingActivityAwardRecord.setCauseType(ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF);
		marketingActivityAwardRecord.setCauseId(null);
		marketingActivityAwardRecordDao.insert(marketingActivityAwardRecord);
			
		MessageCreateRule messageCreateRule = messageCreateRuleDao
				.getByClassName(StringUtils.lowerCaseFirstLetter(MessageCreateRule1002Handler.class.getSimpleName()));
		List<String> messageChannelList = messageCreateRuleChannelDao.findMessageChannelListByRuleId(Long.parseLong(messageCreateRule.getId()));
		messageCreateRule.setMessageChannelList(messageChannelList);
		List<String> termList = messageCreateRuleTermDao.findTermListByRuleId(Long.parseLong(messageCreateRule.getId()));
		messageCreateRule.setTermList(termList);
		Map<String,Object> para = new HashMap<String,Object>();
		para.put(MessageConstant.PARA_MESSAGE_CREATE_RULE, messageCreateRule);
		para.put(MessageConstant.PARA_ACCOUNT_ID, accountId);
		para.put(MessageConstant.PARA_AMOUNT, awardAmount);
		para.put(MessageConstant.PARA_BEHAVIOR_CODE, MessageConstant.BEHAVIOR_INVESTMENT_TENDER);
		para.put(MessageConstant.PARA_OP_TERM, ProjectConstant.OP_TERM_DICT_PC);
		messageCreateRule1002Handler.investmentTender(para);
		yeepayCommonHandler.transferToCustomerFromPlatform(accountId, awardAmount, ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT,
				MarketConstant.ACTIVITY_1015_INVESTMENT_MATCH_AWARD_REMARK, null);
	}

	public JoinMatchRecord getByActivityIdAndAccountId(Long activityId, Long accountId) {
		return dao.getByActivityIdAndAccountId(activityId, accountId);
	}

	/**
	 * 根据活动，及活动的参赛情况 获取时间段内有效投资排行榜（有效投资额：参赛后的投资）
	 * @param activityId
	 * @return
	 */
	public List<Map<String, Object>> getInvestmentRankByActivityId(Long activityId, String side, Date startDate, Date endDate, int limit) {
		return dao.getInvestmentRankByActivityId(activityId, side, startDate, endDate, limit);
	}

}