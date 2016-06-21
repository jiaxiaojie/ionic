package com.thinkgem.jeesite.modules.project.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.operation.service.ProjectOperationSummaryService;

/**
 * 自动将数据插入到项目运营数据汇总表中
 * @author ydt
 *
 */
@Component
public class AutoInsertProjectOperationSummaryDataTask {
	@Autowired
	private ProjectOperationSummaryService projectOperationSummaryService;
	
	/**
	 * 将数据插入到项目运营数据汇总表中，每日00:25:25执行
	 */
	public void job() {
		projectOperationSummaryService.insertNeedInsertData();
	}
}
