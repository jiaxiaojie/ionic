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

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentSplitRecordDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectTransferInfoDao;
import com.thinkgem.jeesite.modules.project.service.repayment.RepaymentService;
import com.hsbank.util.type.DatetimeUtil;

/**
 * @author yangtao 正常自动还款任务
 */
@Component("normalRepayTask")
public class NormalRepayTask {

	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ProjectRepaymentSplitRecordDao projectRepaymentSplitRecordDao;
	@Autowired
	ProjectExecuteSnapshotDao projectExecuteSnapshotDao;
	@Autowired
	RepaymentService repaymentService;
	@Autowired
	ProjectTransferInfoDao projectTransferInfoDao;
	@Autowired
	LogTimerTaskService logTimerTaskService;

	/**
	 * 必须执行多次 建议8 12 16 20 点执行
	 */
	public void job() {
		long start = System.currentTimeMillis();
		Date beginDt = DatetimeUtil.getCurrentDate();
		String startTime = DatetimeUtil.datetimeToString(beginDt);
		logger.info("【" + startTime + "】normalRepayTask start...");
		String theDayStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		// 查询当天有还款任务的项目清单
		List<ProjectRepaymentSplitRecord> projectList = projectRepaymentSplitRecordDao
				.getNormalRepayProjectListOfToday(theDayStr);
		String status = "1";
		String remark = "normalRepayTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		// 逐项目发起还款流程
		for (ProjectRepaymentSplitRecord item : projectList) {
			String projectId = item.getProjectId().longValue() + "";
			logger.debug("find projectId is "+projectId);
			try {
				Map<String, Object> result = repaymentService.repay(projectId);
				String code = (String) result.get("code");
				if (code.equals("OK")) {
					logger.debug("normalRepayTask repay project id is "
							+ projectId + " result is OK");
				} else {
					logger.debug("normalRepayTask repay project id is "
							+ projectId + " result is " + code + " desc is "
							+ result.get("desc"));
					// 判断是否为当天最后一次还款任务执行（依赖于配置时间）
					int nowTime = new Integer((DateUtils.getTime()).replaceAll(
							":", "")).intValue();
					int lastTime = 200000;//其实是晚上八点的意思
					if (nowTime > lastTime) {
						// 设置项目逾期
						ProjectExecuteSnapshot pes = projectExecuteSnapshotDao.getByProjectId(item.getProjectId(), 0L);
						pes.setStatus(ProjectConstant.PROJECT_EXECUTE_SNAPSHOT_STATUS_OVERDUE);
						projectExecuteSnapshotDao.update(pes);
						//下线所有此项目关联的债权转让项目
						projectTransferInfoDao.letDownTransfer(projectId);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				status = "0";
				failBuilder.append("normalRepayTask project id is " + projectId + " run err mes is :" + e.getMessage()).append(";");
				logger.error("normalRepayTask repay project id is "
						+ projectId + " run err mes is "+e.getMessage());
			}
		}
		Date endDt = DatetimeUtil.getCurrentDate();
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		//记录日志
		logTimerTaskService.logTimer(projectList, "normalRepayTask", beginDt, endDt, status, remark);
        String endTime = DatetimeUtil.datetimeToString(endDt);
        long end = System.currentTimeMillis();
		logger.info("【" + endTime + "】normalRepayTask end...");
		logger.info("normalRepayTask total time consuming：【" + (end - start) / 1000 + "s】");
	}
}
