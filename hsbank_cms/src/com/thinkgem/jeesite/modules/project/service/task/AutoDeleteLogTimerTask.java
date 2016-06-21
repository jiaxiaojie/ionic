package com.thinkgem.jeesite.modules.project.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;

/**
 * 自动删除5天以前的定时任务日志数据
 * @author ydt
 *
 */
@Component
public class AutoDeleteLogTimerTask {
	
	@Autowired
	private LogTimerTaskService loTimerTaskService;
	
	/**
	 * 每天1:5执行一次
	 * 自动删除5天以前的定时任务日志数据
	 */
	public void job() {
		loTimerTaskService.deleteSomeDaysAgoData(5);
	}
}
