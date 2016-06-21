package com.thinkgem.jeesite.modules.marketing.task;


import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1015Handler;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1016Handler;
import com.thinkgem.jeesite.modules.marketing.service.GambleService;
import com.thinkgem.jeesite.modules.marketing.service.JoinMatchRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;


/**
 * 更新活动1016竞猜记录结果信息
 * @author hyc
 *
 */
@Component
public class UpdateActivity1016GambleResultTask {
	
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private JoinMatchRecordService joinMatchRecordService;
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private GambleService gambleService;
	
	/**
	 * 每日05:00:00将活动1016前一日用户竞猜记录结果更新到表中
	 * 	1.检测更新时间（更新时间需在(activity.beginDate + 1)~(activity.endDate + 2)时间段内）
	 * 	2.更新记录
	 */
	public void job() {
		logger.info("=====updateActivity1016GambleResultTask start=====");
		MarketingActivityInfo activity1015 = marketingActivityInfoService
				.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1015Handler.class.getSimpleName()));
		MarketingActivityInfo activity1016 = marketingActivityInfoService
				.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1016Handler.class.getSimpleName()));
		Date beginDate = activity1015.getBeginDate();
		Date endDate = activity1015.getEndDate();
		Date now = new Date();
		if(DateUtils.addDays(beginDate, 1).getTime() <= now.getTime() && now.getTime() < DateUtils.addDays(endDate, 2).getTime()) {
			//昨日红队投资额
			double redSideAmount = joinMatchRecordService
					.getSideInvestmentAmount(activity1015.getActicityId(), MarketConstant.MATCH_SIDE_RED, DateUtils.addDays(new Date(), -1), DateUtils.addDays(new Date(), -1));
			//昨日蓝队投资额
			double blueSideAmount = joinMatchRecordService
					.getSideInvestmentAmount(activity1015.getActicityId(), MarketConstant.MATCH_SIDE_BLUE, DateUtils.addDays(new Date(), -1), DateUtils.addDays(new Date(), -1));
			String yesterdayResult = redSideAmount >= blueSideAmount ? MarketConstant.MATCH_SIDE_RED : MarketConstant.MATCH_SIDE_BLUE;
			//将昨日竞猜记录结果更新到表中
			gambleService.updateRightSideByActivityIdAndOpDt(activity1016.getActicityId(), yesterdayResult, DateUtils.addDays(new Date(), -1));
		}
		logger.info("=====updateActivity1016GambleResultTask end=====");
	}
}