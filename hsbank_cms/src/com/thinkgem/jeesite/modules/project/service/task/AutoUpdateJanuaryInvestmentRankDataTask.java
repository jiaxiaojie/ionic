package com.thinkgem.jeesite.modules.project.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRankService;

/**
 * 自动更新一月份投资年化排行榜数据
 * @author ydt
 *
 */
@Component
public class AutoUpdateJanuaryInvestmentRankDataTask {
	
	@Autowired
	private ProjectInvestmentRankService projectInvestmentRankService;
	
	/**
	 * 更新一月份投资年化排行榜数据，每日00:16:00,12:16:00执行
	 */
	public void job() {
		projectInvestmentRankService.updateJanuaryInvestmentRankData();
	}
	
}
