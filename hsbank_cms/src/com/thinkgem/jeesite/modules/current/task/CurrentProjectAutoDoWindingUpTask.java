package com.thinkgem.jeesite.modules.current.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 自动处理待清盘的项目
 * @author ydt
 *
 */
@Component
public class CurrentProjectAutoDoWindingUpTask {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	
	/**
	 * 每日23:23:23处理待清盘的项目
	 */
	public void job() {
		List<CurrentProjectInfo> currentProjectInfoList = currentProjectInfoService.findListByWindingUpStatus(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS);
		for(CurrentProjectInfo currentProjectInfo : currentProjectInfoList) {
			try {
				currentProjectInfoService.doWindingUp(currentProjectInfo, ProjectConstant.OP_TERM_DICT_PC);
			} catch(Exception e) {
				e.printStackTrace();
				logger.info("=====doWindingUp error, currentProjectInfo id:" + currentProjectInfo.getId() + "=====");
			}
		}
	}

}
