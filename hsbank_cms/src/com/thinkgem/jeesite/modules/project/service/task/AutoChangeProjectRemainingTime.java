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
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentPlanService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.hsbank.util.type.DatetimeUtil;

/**
 * @author yangtao
 *
 */
@Component("autoChangeProjectRemainingTime")
public class AutoChangeProjectRemainingTime {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	ProjectExecuteSnapshotService projectExecuteSnapshotService;
	@Autowired
	ProjectTransferInfoService projectTransferInfoService;
	@Autowired
	LogTimerTaskService logTimerTaskService;
	@Autowired
	ProjectRepaymentPlanService projectRepaymentPlanService;

	/**
	 * 每天分钟执行一次
	 */
	public void job() {
		long start = System.currentTimeMillis();
		Date beginDt = DatetimeUtil.getCurrentDate();
		String startTime = DatetimeUtil.datetimeToString(beginDt);
		logger.info("【" + startTime
				+ "】autoChangeProjectRemainingTime start...");

		Date today = new Date();
		String status = "1";
		String remark = "autoChangeProjectRemainingTime result is OK";
		StringBuilder failBuilder = new StringBuilder();
		// 处理项目
		List<ProjectBaseInfo> runningList = projectBaseInfoService
				.findRunningProject();
		for (ProjectBaseInfo item : runningList) {

			Date endDate = item.getLastRepaymentDate();
			if (endDate == null) {
				logger.info("autoChangeProjectRemainingTime project id is "
						+ item.getProjectId()
						+ " run err mes is endDate is null");
			} else {
				ProjectExecuteSnapshot pes = projectExecuteSnapshotService
						.getByProjectIdAndTransferId(item.getProjectId(), "0");
				if (pes == null) {
					logger.info("autoChangeProjectRemainingTime project id is "
							+ item.getProjectId()
							+ " run err mes is pes is null");
				} else {
					try {
						int pesRemainningTime = projectRepaymentPlanService.getRemaining(item.getProjectId(),today);
						pes.setRemainingTime(pesRemainningTime);
						projectExecuteSnapshotService.save(pes);
					} catch (Exception e) {
						status = "0";
						failBuilder.append("autoChangeProjectRemainingTime project id is "
								+ item.getProjectId()
								+ " run err mes is :"
								+ e.getMessage()).append(";");
						logger.info("autoChangeProjectRemainingTime project id is "
								+ item.getProjectId()
								+ " run err mes is "
								+ e.getMessage());
					}
				}
			}
		}
		// 处理债权转让
		List<ProjectTransferInfo> runningTransferList = projectTransferInfoService
				.findTransferingProjectList();
		for (ProjectTransferInfo item : runningTransferList) {
			Date endDate = item.getDiscountDate();
			ProjectExecuteSnapshot pes = projectExecuteSnapshotService
					.getByProjectIdAndTransferId(item.getProjectId()
							.longValue() + "", item.getTransferProjectId()
							.longValue() + "");
			try {
				int pesRemainningTime = DateUtils.getDifferMonthOfTwoDate(
						DatetimeUtil.datetimeToString(today),
						DatetimeUtil.datetimeToString(endDate));
				pes.setRemainingTime(pesRemainningTime);
				projectExecuteSnapshotService.save(pes);
			} catch (Exception e) {
				status = "0";
				failBuilder.append("autoChangeProjectRemainingTime project id is"
						+ item.getProjectId() + " run err mes is :"
						+ e.getMessage()).append(";");
				logger.info("autoChangeProjectRemainingTime project id is "
						+ item.getProjectId() + " run err mes is "
						+ e.getMessage());
			}
		}
		Date endDt = DatetimeUtil.getCurrentDate();
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		if ((runningList != null && runningList.size() > 0)
				|| (runningTransferList != null && runningTransferList.size() > 0)) {
			// 记录任务日志
			logTimerTaskService.insert("autoChangeProjectRemainingTime",
					beginDt, endDt, status, remark);
		}else{
			remark = "autoChangeProjectRemainingTime dataList is 0";
			logTimerTaskService.insert("autoChangeProjectRemainingTime",
					beginDt, endDt, status, remark);
		}
		String endTime = DatetimeUtil.datetimeToString(endDt);
		long end = System.currentTimeMillis();
		logger.info("【" + endTime + "】autoChangeProjectRemainingTime end...");
		logger.info("autoChangeProjectRemainingTime total time consuming：【"
				+ (end - start) / 1000 + "s】");
	}
}
