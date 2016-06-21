/**
 * 
 */
package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentSplitRecordDao;
import com.hsbank.util.type.DatetimeUtil;

/**
 * @author yangtao 计算逾期罚息任务
 */
@Component("computeOverduePenaltyTask")
public class ComputeOverduePenaltyTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ProjectRepaymentSplitRecordDao projectRepaymentSplitRecordDao;
	@Autowired
	LogTimerTaskService logTimerTaskService;

	/**
	 * 只能执行一次 建议每天凌晨2点钟执行
	 */
	public void job() {
		long start = System.currentTimeMillis();
		Date beginDt = DatetimeUtil.getCurrentDate();
		String startTime = DatetimeUtil.datetimeToString(beginDt);
		logger.info("【" + startTime + "】computeOverduePenaltyTask start...");
		Date toDay = new Date();
		// 获得逾期状态的项目未还款记录列表
		List<ProjectRepaymentSplitRecord> splitList = projectRepaymentSplitRecordDao
				.getSplitRecordListOfTodayOverdueRepayProject(toDay);
		String status = "1";
		String remark = "computeOverduePenaltyTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		for(ProjectRepaymentSplitRecord item:splitList){
			logger.debug("find projectId is "+item.getProjectId());
			Date repayDt=item.getRepaymentDt();
			try {
				//计算两天的间隔日期
				int intervalDays=(int)DateUtils.getDistanceOfTwoDate(DateUtils.dateFormate(repayDt), DateUtils.dateFormate(toDay));
				double sumMoney=item.getMoney().doubleValue();
				double defaultMoney=sumMoney*intervalDays*(ProjectConstant.PROJECT_OVERDUE_REPAY_DEFAULT_PAYMENT_RATIO);
				item.setLatePenaltyMoney(defaultMoney);
				projectRepaymentSplitRecordDao.update(item);
			} catch (Exception e) {
				status = "0";
				failBuilder.append("computeOverduePenaltyTask project id is " + item.getProjectId() + " run err mes is :" + e.getMessage()).append(";");
				logger.error("computeOverduePenaltyTask project id is "
						+ item.getProjectId() + " run err mes is "+e.getMessage());
			}
		}
		Date endDt = DatetimeUtil.getCurrentDate();
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		//记录日志
		logTimerTaskService.logTimer(splitList, "computeOverduePenaltyTask", beginDt, endDt, status, remark);
		String endTime = DatetimeUtil.datetimeToString(endDt);
        long end = System.currentTimeMillis();
		logger.info("【" + endTime + "】computeOverduePenaltyTask end...");
		logger.info("computeOverduePenaltyTask total time consuming：【" + (end - start) / 1000 + "s】");

	}
}
