/**
 * 
 */
package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.dao.ProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.repayment.RepaymentService;
import com.hsbank.util.type.DatetimeUtil;

/**
 * @author yangtao 逾期自动还款任务
 */
@Component("overDueRepayTask")
public class OverDueRepayTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	ProjectExecuteSnapshotDao projectExecuteSnapshotDao;
	@Autowired
	RepaymentService repaymentService;
	@Autowired
	LogTimerTaskService logTimerTaskService;

	/**
	 * 必须执行多次 建议9 21 点执行
	 */
	public void job() {
		long start = System.currentTimeMillis();
		Date beginDt = DatetimeUtil.getCurrentDate();
		String startTime = DatetimeUtil.datetimeToString(beginDt);
		logger.info("【" + startTime + "】overDueRepayTask start...");
		// 获得逾期项目清单
		List<ProjectBaseInfo> projectList = projectBaseInfoService
				.findOverDueProject();
		String status = "1";
		String remark = "overDueRepayTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		// 逐项目发起逾期还款
		Date endDt = DatetimeUtil.getCurrentDate();
		if (failBuilder.length() > 0) {
			remark = failBuilder.toString();
		}
		// 逐项目发起还款流程
		for (ProjectBaseInfo item : projectList) {
			String projectId = item.getProjectId();
			logger.debug("overDueRepayTask find projectId is " + projectId);
			try {
				Map<String, Object> result = repaymentService.overdueRepay(projectId);
				String code = (String) result.get("code");
				if (code.equals("OK")) {
					logger.debug("overDueRepayTask repay project id is "
							+ projectId + " result is OK");
				} else {
					logger.debug("overDueRepayTask repay project id is "
							+ projectId + " result is " + code + " desc is "
							+ result.get("desc"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				status = "0";
				failBuilder.append("overDueRepayTask project id is " + projectId + " run err mes is :" + e.getMessage()).append(";");
				logger.error("overDueRepayTask repay project id is "
						+ projectId + " run err mes is "+e.getMessage());
			}
			
		}
		// 记录日志
		logTimerTaskService.logTimer(projectList, "overDueRepayTask", beginDt,
				endDt, status, remark);
		String endTime = DatetimeUtil.datetimeToString(endDt);
		long end = System.currentTimeMillis();
		logger.info("【" + endTime + "】overDueRepayTask end...");
		logger.info("overDueRepayTask total time consuming：【" + (end - start)
				/ 1000 + "s】");
	}
}
