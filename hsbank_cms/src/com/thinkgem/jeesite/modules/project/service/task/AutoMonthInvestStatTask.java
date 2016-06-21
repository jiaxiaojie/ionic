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
 * 月投资排行统计
 *
 * @author lizibo
 * @since 2015/11/23
 */
@Component("autoMonthInvestStatTask")
public class AutoMonthInvestStatTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectInvestmentRankService projectInvestmentRankService;
	private static String remark = "月投资统计";
	
	
	/**
	 * 每天12、24点执行
	 */
	public void job() {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】autoMonthInvestStatTask start...");
		//统计时间
		Date theDate = new Date();
		Date beginStatDt = DateUtils.dateFormateDayOfTheBeginTime(DateUtils.getMonthBegin(theDate));
		Date endStatDt = DateUtils.dateFormateDayOfTheLastTime(DateUtils.getMonthEnd(theDate));
		ProjectInvestmentRank investmentRank = new ProjectInvestmentRank();
		investmentRank.setBeginStatDt(beginStatDt);
		investmentRank.setEndStatDt(endStatDt);
		investmentRank.setType(ProjectConstant.INVESTMENT_RANK_TYPE_MONTH);
		investmentRank.setOpTerm(ProjectConstant.OP_TERM_DICT_PC);
		//数据时间= 统计时间 -1h
		investmentRank.setDataDt(DateUtils.addHours(theDate, -1));	
		investmentRank.setCreateDt(theDate);
		investmentRank.setRemark(remark);
		investmentRank.setLimit(30);
		projectInvestmentRankService.investmentMonthStat(investmentRank);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】autoMonthInvestStatTask end...");
		logger.info("autoMonthInvestStatTask total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
	}
}
