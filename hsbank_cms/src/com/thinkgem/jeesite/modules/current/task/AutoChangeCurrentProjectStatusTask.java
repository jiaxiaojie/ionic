package com.thinkgem.jeesite.modules.current.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;

/**
 * 自动将活期产品设置为相应状态
 * @author ydt
 *
 */
@Component
public class AutoChangeCurrentProjectStatusTask {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	
	/**
	 * 每5分钟执行一次
	 * 		1.将处于"审批通过"状态 且到发布时间的项目状态设置为"投标中"
	 * 		2.将处于"投标中"状态 且到投标截止时间的项目状态设置为"投标结束"
	 */
	public void job() {
		logger.info("=====autoChangeCurrentProjectStatusTask start=====");
		currentProjectInfoService.updateStatusToInvestmenting();
		currentProjectInfoService.updateStatusToInvestmentOver();
		logger.info("=====autoChangeCurrentProjectStatusTask end=====");
	}

}
