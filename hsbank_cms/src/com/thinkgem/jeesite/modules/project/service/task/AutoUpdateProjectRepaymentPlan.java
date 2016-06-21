package com.thinkgem.jeesite.modules.project.service.task;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentPlan;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentPlanService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author lzb 维护cache中的今日投资列表
 */
@Component
public class AutoUpdateProjectRepaymentPlan implements ApplicationListener<ContextRefreshedEvent> {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ProjectRepaymentPlanService projectRepaymentPlanService;

	/**
	 * 每5分执行一次
	 */
	public void job() {
		Date startDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTime) + "】AutoUpdateProjectRepaymentPlan start...");
		List<ProjectRepaymentPlan> list = projectRepaymentPlanService.findListGroupByProjectId();
		for(ProjectRepaymentPlan repaymentPlan : list){
			projectRepaymentPlanService.updateRemainingPrincipalInterest(repaymentPlan);
		}
		Date endDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTiem) + "】AutoUpdateProjectRepaymentPlan end...");
		logger.info("AutoUpdateProjectRepaymentPlan total time consuming：【" + (endDateTiem.getTime() - startDateTime.getTime()) / 1000 + "s】");
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
			//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			job();
		}
	}
}
