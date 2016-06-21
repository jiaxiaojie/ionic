package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;
import com.thinkgem.jeesite.modules.project.service.ProjectPayMoneyService;
import com.hsbank.util.type.DatetimeUtil;

/**
 * @author 合同放款
 */
@Component("autoLoanProjectTask")
public class AutoLoanProjectTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	private ProjectPayMoneyService projectPayMoneyService;
	@Autowired
	LogTimerTaskService logTimerTaskService;
	/**
	 * 每天晚上22点执行
	 */
	public void job() {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】autoLoanProjectTask start...");
		//查询待放款的项目列表
		ProjectBaseInfo projectBaseInfo = new ProjectBaseInfo();
		projectBaseInfo.setIsLoan("1");
		List<ProjectBaseInfo> projectList=projectBaseInfoDao.findLoanList(projectBaseInfo);
		String status = "1";
		String remark = "autoLoanProjectTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		for(ProjectBaseInfo pbi:projectList){
			logger.debug("find projectId is "+pbi.getProjectId());
			try {
				//合同放款
				projectPayMoneyService.confirm(pbi.getProjectId());
			} catch (Exception e) {
				e.printStackTrace();
				status = "0";
				failBuilder.append("autoLoanProjectTask project id is " + pbi.getProjectId() + " run err mes is :" + e.getMessage()).append(";");
				logger.error("autoLoanProjectTask project id is "
						+ pbi.getProjectId() + " run err mes is "+e.getMessage());
			}
		}
		Date endDt = DatetimeUtil.getCurrentDate();
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		//记录日志
		Date endTime = new Date();
		logTimerTaskService.logTimer(projectList, "autoLoanProjectTask", startTime, endDt, status, remark);
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】autoLoanProjectTask end...");
		logger.info("autoLoanProjectTask total time consuming：【" + (startTime.getTime() - endTime.getTime()) / 1000 + "s】");
	}
}
