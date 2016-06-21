package com.thinkgem.jeesite.modules.project.service.task;

import com.hsbank.util.type.DatetimeUtil;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountSummaryService;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 活期产品：资金告警
 *
 * @author lizibo
 * @since 2015/12/21
 */
@Component("autoCurrentCapitalAlertTask")
public class AutoCurrentCapitalAlertTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CurrentAccountSummaryService currentAccountSummaryService;
	@Autowired
	private LogTimerTaskService logTimerTaskService;
	
	
	/**
	 * 每15分钟执行一次
	 */
	public void job() {
		long start = System.currentTimeMillis();
		Date beginDt = DatetimeUtil.getCurrentDate();
		String startTime = DatetimeUtil.datetimeToString(beginDt);
		logger.info("【" + startTime + "】autoCurrentCapitalAlertTask start...");
		String status = "1";
		String remark = "autoCurrentCapitalAlertTask result is OK";
		//资金累计比较
		currentAccountSummaryService.totalCapitalCompare();
		//资金明细比较
		currentAccountSummaryService.detailCapitalCompare();
		Date endDt = DatetimeUtil.getCurrentDate();
		//记录日志
		logTimerTaskService.insert("autoCurrentCapitalAlertTask", beginDt, endDt, status, remark);
		String endTime = DatetimeUtil.datetimeToString(endDt);
        long end = System.currentTimeMillis();
		logger.info("【" + endTime + "】autoCurrentCapitalAlertTask end...");
		logger.info("autoCurrentCapitalAlertTask total time consuming：【" + (end - start) / 1000 + "s】");
	}
}
