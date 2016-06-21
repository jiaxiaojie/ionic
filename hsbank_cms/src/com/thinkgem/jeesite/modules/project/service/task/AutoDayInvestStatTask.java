package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRank;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRankService;

/**
 * 日投资排行统计
 *
 * @author lizibo
 * @since 2015/11/23
 */
@Component("autoDayInvestStatTask")
public class AutoDayInvestStatTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectInvestmentRankService projectInvestmentRankService;
	private static String remark = "日投资统计";

	
	
	/**
	 * 每天凌晨1点执行(统计昨天的数据)
	 */
	public void job() {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】autoDayInvestStatTask start...");
		//统计日期
		Date theDate = org.apache.commons.lang3.time.DateUtils.addDays(new Date(), -1);
		Date beginStatDt = DateUtils.dateFormateDayOfTheBeginTime(theDate);
		Date endStatDt = DateUtils.dateFormateDayOfTheLastTime(theDate);
		ProjectInvestmentRank investmentRank = new ProjectInvestmentRank();
		investmentRank.setBeginStatDt(beginStatDt);
		investmentRank.setEndStatDt(endStatDt);
		investmentRank.setType(ProjectConstant.INVESTMENT_RANK_TYPE_DAY);
		investmentRank.setOpTerm(ProjectConstant.OP_TERM_DICT_PC);
		investmentRank.setDataDt(theDate);
		investmentRank.setCreateDt(new Date());
		investmentRank.setRemark(remark);
		investmentRank.setLimit(30);
		projectInvestmentRankService.investmentDayStat(investmentRank);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】autoDayInvestStatTask end...");
		logger.info("autoDayInvestStatTask total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
	}
}
