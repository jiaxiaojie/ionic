package com.thinkgem.jeesite.modules.project.service.task;


/**
 * 定时任务服务
 * <p/>
 * 本接口，提供定时任务的入口
 * <p/>
 * @author Arthur.Xie
 * 2015-07-28
 */
public interface ITaskService {
	/**
	 * 融资项目是否募集结束
	 * <1>.
	 * @return
	 */
	public void loanFinishedTask();
}
