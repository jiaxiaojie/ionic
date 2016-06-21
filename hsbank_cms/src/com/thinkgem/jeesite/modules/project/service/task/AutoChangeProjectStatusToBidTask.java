package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

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
 * @author yangtao 到期自动设置项目为投标中的项目状态
 */
@Component("autoChangeProjectStatusToBidTask")
public class AutoChangeProjectStatusToBidTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	ProjectDateNodeDao projectDateNodeDao;
	@Autowired
	LogTimerTaskService logTimerTaskService;
	
	
	/**
	 * 每半小时执行一次
	 */
	public void job() {
		//设置项目状态2到3
		long start = System.currentTimeMillis();
		Date beginDt = DatetimeUtil.getCurrentDate();
		String startTime = DatetimeUtil.datetimeToString(beginDt);
		logger.info("【" + startTime + "】autoChangeProjectStatusToBidTask start...");
		//查询发布时间已经超过当前时间，同时状态为审核通过的项目列表
		List<ProjectBaseInfo> projectList=projectBaseInfoDao.getNeedToInvestmentProject(new Date());
		String status = "1";
		String remark = "autoChangeProjectStatusToBidTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		for(ProjectBaseInfo pbi:projectList){
			logger.debug("find projectId is "+pbi.getProjectId());
			try {
				//更改项目状态
				projectBaseInfoDao.updateProjectToInvestment(pbi.getProjectId());
				
				
				
				//创建项目时间节点信息
				ProjectDateNode pNode=new ProjectDateNode();
				pNode.setProjectId(new Long(pbi.getProjectId()));
				pNode.setOnLineDt(pbi.getPublishDt());
				pNode.setStartFundingDt(new Date());
				projectDateNodeDao.insert(pNode);
			} catch (NumberFormatException e) {
				status = "0";
				failBuilder.append("autoChangeProjectStatusToBidTask project id is " + pbi.getProjectId() + " run err mes is :" + e.getMessage()).append(";");
				logger.error("autoChangeProjectStatusToBidTask project id is "
						+ pbi.getProjectId() + " run err mes is "+e.getMessage());
			}
		}
		Date endDt = DatetimeUtil.getCurrentDate();
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		//记录日志
		logTimerTaskService.logTimer(projectList, "autoChangeProjectStatusToBidTask", beginDt, endDt, status, remark);
		String endTime = DatetimeUtil.datetimeToString(endDt);
        long end = System.currentTimeMillis();
		logger.info("【" + endTime + "】autoChangeProjectStatusToBidTask end...");
		logger.info("autoChangeProjectStatusToBidTask total time consuming：【" + (end - start) / 1000 + "s】");
	}
}
