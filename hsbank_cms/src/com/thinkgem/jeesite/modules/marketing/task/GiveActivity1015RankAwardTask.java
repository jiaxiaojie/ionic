package com.thinkgem.jeesite.modules.marketing.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1015Handler;
import com.thinkgem.jeesite.modules.marketing.service.JoinMatchRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 将活动1015前一日用户应获得的奖励赠送给用户
 * @author ydt
 *
 */
@Component
public class GiveActivity1015RankAwardTask {
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private JoinMatchRecordService joinMatchRecordService;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 每日00:10:00将活动1015前一日用户应获得的奖励赠送给用户
	 * 	1.检测奖励时间（奖励时间需在(activity.beginDate + 1)~(activity.endDate + 2)时间段内）
	 * 	2.赠送奖励
	 */
	public void job() {
		logger.info("=====giveActivity1015RankAwardTask start=====");
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService
				.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1015Handler.class.getSimpleName()));
		Date beginDate = marketingActivityInfo.getBeginDate();
		Date endDate = marketingActivityInfo.getEndDate();
		Date now = new Date();
		if(DateUtils.addDays(beginDate, 1).getTime() <= now.getTime() && now.getTime() < DateUtils.addDays(endDate, 2).getTime()) {
			List<Map<String,Object>> investmentRank = joinMatchRecordService
					.getOnedayInvestmentRankByActivityId(marketingActivityInfo.getActicityId(), DateUtils.addDays(new Date(), -1), 10);
			int rank = 0;
			for(Map<String,Object> map : investmentRank) {
				joinMatchRecordService.giveRankAward(rank++, map, marketingActivityInfo.getActicityId(), ProjectConstant.OP_TERM_DICT_PC);
			}
		}
		logger.info("=====giveActivity1015RankAwardTask end=====");
	}
}
