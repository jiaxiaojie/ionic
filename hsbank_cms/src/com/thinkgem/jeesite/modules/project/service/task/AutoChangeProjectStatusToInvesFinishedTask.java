/**
 * 
 */
package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.message.service.MessageAlertSettingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectDateNode;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectDateNodeDao;
import com.hsbank.util.type.DatetimeUtil;

/**
 * @author yangtao
 *
 */
@Component("autoChangeProjectStatusToInvesFinishedTask")
public class AutoChangeProjectStatusToInvesFinishedTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	ProjectDateNodeDao projectDateNodeDao;
	@Autowired
	LogTimerTaskService logTimerTaskService;
	@Autowired
	private MessageAlertSettingService messageAlertSettingService;


	public void job() {
		// 设置项目状态3到4
		long start = System.currentTimeMillis();
		Date beginDt = DatetimeUtil.getCurrentDate();
		String startTime = DatetimeUtil.datetimeToString(beginDt);
		logger.info("【" + startTime + "】AutoChangeProjectStatusToInvesFinishedTask start...");
		// 查询发布时间已经超过当前时间，同时状态为审核通过的项目列表
		List<ProjectBaseInfo> projectList = projectBaseInfoDao
				.getNeedToInvestmentFinishProject(new Date());
		String status = "1";
		String remark = "AutoChangeProjectStatusToInvesFinishedTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		for (ProjectBaseInfo pbi : projectList) {
			logger.debug("find projectId is "+pbi.getProjectId());
			try {
				// 更改项目状态
				projectBaseInfoDao.updateProjectToInvestmentFinish(pbi
						.getProjectId());
				//若项目满标或者项目过期则短信提醒风控发标
				messageAlertSettingService.sendSmsFromFengControl(pbi.getProjectName());
				// 创建项目时间节点信息
				ProjectDateNode pNode = projectDateNodeDao.get(pbi
						.getProjectId());
				if(pNode==null){
					logger.error("project id="+pbi.getProjectId()+" projectdatenode is null");
				}else{
					pNode.setId(pbi.getProjectId());
					pNode.setEndFundingDt(new Date());
					projectDateNodeDao.update(pNode);
				}
			} catch (Exception e) {
				status = "0";
				failBuilder.append("autoChangeProjectStatusToInvesFinishedTask project id is " + pbi.getProjectId() + " run err mes is :" + e.getMessage()).append(";");
				logger.error("autoChangeProjectStatusToInvesFinishedTask project id is "
						+ pbi.getProjectId() + " run err mes is "+e.getMessage());
			}
		}
		Date endDt = DatetimeUtil.getCurrentDate();
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		//记录日志
		logTimerTaskService.logTimer(projectList, "AutoChangeProjectStatusToInvesFinishedTask", beginDt, endDt, status, remark);
		String endTime = DatetimeUtil.datetimeToString(endDt);
        long end = System.currentTimeMillis();
		logger.info("【" + endTime + "】AutoChangeProjectStatusToInvesFinishedTask end...");
		logger.info("AutoChangeProjectStatusToInvesFinishedTask total time consuming：【" + (end - start) / 1000 + "s】");
	}
}
