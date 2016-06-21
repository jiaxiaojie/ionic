package com.thinkgem.jeesite.modules.project.service.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1014Handler;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityAwardRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;

/**
 * 自动将双旦活动的奖励赠送给用户（活动期间内好友注册并且单次投资满1000元，赠送推荐人现金奖励）
 * 四月活动奖励继续使用此任务完成
 * @author ydt
 *
 */
@Component
public class AutoGiveDoubleEggPrize {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private MarketingActivityAwardRecordService marketingActivityAwardRecordService;
	
	/**
	 * 将双旦活动的奖励赠送给用户（每日02:05:00执行）
	 */
	public void job() {
		logger.info("=====autoGiveDoubleEggPrize start=====");
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1014Handler.class.getSimpleName()));
		List<ProjectInvestmentRecord> projectInvestmentRecordList = projectInvestmentRecordService.findDoubleEggList(marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
		for(ProjectInvestmentRecord projectInvestmentRecord : projectInvestmentRecordList) {
			marketingActivityAwardRecordService.doGiveDoubleEggPrize(projectInvestmentRecord, marketingActivityInfo);
		}
		logger.info("=====autoGiveDoubleEggPrize end=====");
	}
}
