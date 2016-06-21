package com.thinkgem.jeesite.modules.project.service.task;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.utils.InvestRankCacheUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lzb 维护cache中的今日投资列表
 */
@Component
public class AutoInvestmentRankList{
	Logger logger = Logger.getLogger(this.getClass());


	/**
	 * 每5分执行一次
	 */
	public void job() {
		Date startDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTime) + "】AutoInvestmentRankList start...");

		InvestRankCacheUtils.refreshInvestmentRankCache();

		Date endDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTiem) + "】AutoInvestmentRankList end...");
		logger.info("AutoInvestmentRankList total time consuming：【" + (endDateTiem.getTime() - startDateTime.getTime()) / 1000 + "s】");
	}
}
