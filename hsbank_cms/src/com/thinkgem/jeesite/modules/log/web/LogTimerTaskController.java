/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.LogTimerTask;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;

/**
 * 定时任务日志Controller
 * @author lizibo
 * @version 2015-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/log/logTimerTask")
public class LogTimerTaskController extends BaseController {

	@Autowired
	private LogTimerTaskService logTimerTaskService;
	
	@ModelAttribute
	public LogTimerTask get(@RequestParam(required=false) String id) {
		LogTimerTask entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = logTimerTaskService.get(id);
		}
		if (entity == null){
			entity = new LogTimerTask();
		}
		return entity;
	}
	
	@RequiresPermissions("log:logTimerTask:view")
	@RequestMapping(value = {"list", ""})
	public String list(LogTimerTask logTimerTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LogTimerTask> page = logTimerTaskService.findPage(new Page<LogTimerTask>(request, response), logTimerTask); 
		model.addAttribute("page", page);
		return "modules/log/logTimerTaskList";
	}

	@RequiresPermissions("log:logTimerTask:view")
	@RequestMapping(value = "form")
	public String form(LogTimerTask logTimerTask, Model model) {
		model.addAttribute("logTimerTask", logTimerTask);
		return "modules/log/logTimerTaskForm";
	}

	@RequiresPermissions("log:logTimerTask:edit")
	@RequestMapping(value = "save")
	public String save(LogTimerTask logTimerTask, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, logTimerTask)){
			return form(logTimerTask, model);
		}
		logTimerTaskService.save(logTimerTask);
		addMessage(redirectAttributes, "保存定时任务日志成功");
		return "redirect:"+Global.getAdminPath()+"/log/logTimerTask/?repage";
	}
	
	@RequiresPermissions("log:logTimerTask:edit")
	@RequestMapping(value = "delete")
	public String delete(LogTimerTask logTimerTask, RedirectAttributes redirectAttributes) {
		logTimerTaskService.delete(logTimerTask);
		addMessage(redirectAttributes, "删除定时任务日志成功");
		return "redirect:"+Global.getAdminPath()+"/log/logTimerTask/?repage";
	}

}