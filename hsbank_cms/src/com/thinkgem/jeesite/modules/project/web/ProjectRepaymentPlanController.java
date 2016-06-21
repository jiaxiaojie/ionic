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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentPlan;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentPlanService;

/**
 * 还款记录Controller
 * @author yangtao
 * @version 2015-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectRepaymentPlan")
public class ProjectRepaymentPlanController extends BaseController {

	@Autowired
	private ProjectRepaymentPlanService projectRepaymentPlanService;
	
	
	@ModelAttribute
	public ProjectRepaymentPlan get(@RequestParam(required=false) String id) {
		ProjectRepaymentPlan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectRepaymentPlanService.get(id);
		}
		if (entity == null){
			entity = new ProjectRepaymentPlan();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectRepaymentPlan:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectRepaymentPlan projectRepaymentPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectRepaymentPlan.setProjectId(new Long(projectRepaymentPlan.getQueryProjectId()));
		Page<ProjectRepaymentPlan> page = projectRepaymentPlanService.findPage(new Page<ProjectRepaymentPlan>(request, response), projectRepaymentPlan); 
		model.addAttribute("page", page);
		return "modules/project/projectRepaymentPlanList";
	}

	@RequiresPermissions("project:projectRepaymentPlan:view")
	@RequestMapping(value = "form")
	public String form(ProjectRepaymentPlan projectRepaymentPlan, Model model) {
		model.addAttribute("projectRepaymentPlan", projectRepaymentPlan);
		return "modules/project/projectRepaymentPlanForm";
	}

	@RequiresPermissions("project:projectRepaymentPlan:edit")
	@RequestMapping(value = "save")
	public String save(ProjectRepaymentPlan projectRepaymentPlan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectRepaymentPlan)){
			return form(projectRepaymentPlan, model);
		}
		projectRepaymentPlanService.save(projectRepaymentPlan);
		addMessage(redirectAttributes, "保存还款记录成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectRepaymentPlan/?repage";
	}
	
	@RequiresPermissions("project:projectRepaymentPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectRepaymentPlan projectRepaymentPlan, RedirectAttributes redirectAttributes) {
		projectRepaymentPlanService.delete(projectRepaymentPlan);
		addMessage(redirectAttributes, "删除还款记录成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectRepaymentPlan/?repage";
	}

}