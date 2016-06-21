/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.web;

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
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRankCalendar;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRankCalendarService;

/**
 * 投资排行日历Controller
 * @author lizibo
 * @version 2015-11-25
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectInvestmentRankCalendar")
public class ProjectInvestmentRankCalendarController extends BaseController {

	@Autowired
	private ProjectInvestmentRankCalendarService projectInvestmentRankCalendarService;
	
	@ModelAttribute
	public ProjectInvestmentRankCalendar get(@RequestParam(required=false) String id) {
		ProjectInvestmentRankCalendar entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectInvestmentRankCalendarService.get(id);
		}
		if (entity == null){
			entity = new ProjectInvestmentRankCalendar();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectInvestmentRankCalendar:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectInvestmentRankCalendar projectInvestmentRankCalendar, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectInvestmentRankCalendar> page = projectInvestmentRankCalendarService.findPage(new Page<ProjectInvestmentRankCalendar>(request, response), projectInvestmentRankCalendar); 
		model.addAttribute("page", page);
		return "modules/project/projectInvestmentRankCalendarList";
	}

	@RequiresPermissions("project:projectInvestmentRankCalendar:view")
	@RequestMapping(value = "form")
	public String form(ProjectInvestmentRankCalendar projectInvestmentRankCalendar, Model model) {
		model.addAttribute("projectInvestmentRankCalendar", projectInvestmentRankCalendar);
		return "modules/project/projectInvestmentRankCalendarForm";
	}

	@RequiresPermissions("project:projectInvestmentRankCalendar:edit")
	@RequestMapping(value = "save")
	public String save(ProjectInvestmentRankCalendar projectInvestmentRankCalendar, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectInvestmentRankCalendar)){
			return form(projectInvestmentRankCalendar, model);
		}
		projectInvestmentRankCalendarService.save(projectInvestmentRankCalendar);
		addMessage(redirectAttributes, "保存投资排行日历成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectInvestmentRankCalendar/?repage";
	}
	
	@RequiresPermissions("project:projectInvestmentRankCalendar:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectInvestmentRankCalendar projectInvestmentRankCalendar, RedirectAttributes redirectAttributes) {
		projectInvestmentRankCalendarService.delete(projectInvestmentRankCalendar);
		addMessage(redirectAttributes, "删除投资排行日历成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectInvestmentRankCalendar/?repage";
	}
	
	
	

}