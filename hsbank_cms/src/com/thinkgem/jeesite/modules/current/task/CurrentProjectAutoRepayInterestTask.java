package com.thinkgem.jeesite.modules.current.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.current.service.CurrentProjectHoldInfoService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 自动将每日产生的利息打给用户（仅打到本平台账号，未打到用户易宝账号，当用户发起提取利息操作时才进行真实的打款操作）
 * @author ydt
 *
 */
@Component
public class CurrentProjectAutoRepayInterestTask {
	@Autowired
	private CurrentProjectHoldInfoService currentProjectHoldInfoService;

	/**
	 * 每日00:00:10自动付前日产生的利息
	 */
	public void job() {
		currentProjectHoldInfoService.repayInterest(ProjectConstant.OP_TERM_DICT_PC);
	}

}
