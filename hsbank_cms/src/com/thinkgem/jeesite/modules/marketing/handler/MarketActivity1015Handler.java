/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.entity.JoinMatchRecord;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAwardRecord;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.JoinMatchRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAwardRecordDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * @author ydt
 * 
 * 四月邀请好友
 * 活动期间内好友注册并且单次投资满1000元，赠送推荐人现金奖励，每邀请1位好友奖10元现金，第5位及第5的倍数位奖50元现金
 *
 */
@Component
public class MarketActivity1015Handler extends MarketActivityBaseSupportHandler {
	@Autowired
	private JoinMatchRecordDao joinMatchRecordDao;
	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	@Autowired
	private MarketingActivityAwardRecordDao marketingActivityAwardRecordDao;
	
	/**
	 * 参加比赛
	 * 	1.添加参赛记录
	 * 	2.赠送奖励
	 * 	3.添加奖励记录
	 * 	4.校验是否重复参赛
	 * @param para
	 */
	@Override
	@Transactional
	public Map<String,Object> luckDraw(Map<String,Object> para) {
		if(!StringUtils.lowerCaseFirstLetter(this.getClass().getSimpleName()).equals(para.get(MarketConstant.HANDLER_PARA))) {
			return para;
		}
		Long accountId = (Long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		Long activityId = (Long)para.get(MarketConstant.ACTICITY_PARA);
		String side = (String)para.get("side");
		String opTerm = (String)para.get(MarketConstant.CHANNEL_PARA);
		
		if(!MarketConstant.MATCH_SIDE_RED.equals(side) && !MarketConstant.MATCH_SIDE_BLUE.equals(side)) {
			para.put("isSuccess", false);
			para.put("message", "请选择正确的队伍");
			return para;
		}
		JoinMatchRecord joinMatchRecord = new JoinMatchRecord();
		joinMatchRecord.setActivityId(activityId);
		joinMatchRecord.setAccountId(accountId);
		joinMatchRecord.setSide(side);
		joinMatchRecord.setOpDt(new Date());
		joinMatchRecord.setOpTerm(opTerm);
		joinMatchRecordDao.insert(joinMatchRecord);
		
		customerInvestmentTicketHandler.giveCustomerTickets(accountId, new int[]{MarketConstant.ACTIVITY_1015_INVESTMENT_JOIN_MATCH_AWARD_TICKET_DENOMINATIONS},
				MarketConstant.ACTIVITY_1015_INVESTMENT_JOIN_MATCH_AWARD_TICKET_REMARK);
		MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
		marketingActivityAwardRecord.setAccountId(accountId);
		marketingActivityAwardRecord.setActivityId(activityId);
		marketingActivityAwardRecord.setAwardDt(new Date());
		marketingActivityAwardRecord.setAwardReason(MarketConstant.ACTIVITY_1015_INVESTMENT_JOIN_MATCH_AWARD_TICKET_REMARK);
		marketingActivityAwardRecord.setAwardType(ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET);
		marketingActivityAwardRecord.setAwardValue(String.valueOf(MarketConstant.ACTIVITY_1015_INVESTMENT_JOIN_MATCH_AWARD_TICKET_DENOMINATIONS));
		marketingActivityAwardRecord.setBehaviorCode(MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
		marketingActivityAwardRecord.setChannelId(opTerm);
		marketingActivityAwardRecord.setCauseType(ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF);
		marketingActivityAwardRecord.setCauseId(null);
		marketingActivityAwardRecordDao.insert(marketingActivityAwardRecord);
		
		//若同一用户多次参见同一活动 抛出异常（可能发生了重复提交，重复提交的数据不应该添加到数据库中）
		List<JoinMatchRecord> joinMatchRecordList = joinMatchRecordDao.findListByActivityIdAndAccountId(activityId, accountId);
		if(joinMatchRecordList.size() > 1) {
			throw new ServiceException("系统异常，请稍后重试");
		}
		para.put("isSuccess", true);
		para.put("prizeType", 2);
		para.put("prizeTypeName", "现金券");
		para.put("prize", MarketConstant.ACTIVITY_1015_INVESTMENT_JOIN_MATCH_AWARD_TICKET_DENOMINATIONS);
		return para;
	}
}
