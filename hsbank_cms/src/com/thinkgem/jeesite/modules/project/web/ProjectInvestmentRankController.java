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
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRank;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRankService;

/**
 * 投资排行Controller
 * @author lizibo
 * @version 2015-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectInvestmentRank")
public class ProjectInvestmentRankController extends BaseController {

	@Autowired
	private ProjectInvestmentRankService projectInvestmentRankService;
	
	@ModelAttribute
	public ProjectInvestmentRank get(@RequestParam(required=false) String id) {
		ProjectInvestmentRank entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectInvestmentRankService.get(id);
		}
		if (entity == null){
			entity = new ProjectInvestmentRank();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectInvestmentRank:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectInvestmentRank projectInvestmentRank, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectInvestmentRank> page = projectInvestmentRankService.findPage(new Page<ProjectInvestmentRank>(request, response), projectInvestmentRank); 
		model.addAttribute("page", page);
		return "modules/project/projectInvestmentRankList";
	}

	@RequiresPermissions("project:projectInvestmentRank:view")
	@RequestMapping(value = "form")
	public String form(ProjectInvestmentRank projectInvestmentRank, Model model) {
		model.addAttribute("projectInvestmentRank", projectInvestmentRank);
		return "modules/project/projectInvestmentRankForm";
	}

	@RequiresPermissions("project:projectInvestmentRank:edit")
	@RequestMapping(value = "save")
	public String save(ProjectInvestmentRank projectInvestmentRank, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectInvestmentRank)){
			return form(projectInvestmentRank, model);
		}
		projectInvestmentRankService.save(projectInvestmentRank);
		addMessage(redirectAttributes, "保存投资排行成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectInvestmentRank/?repage";
	}
	
	@RequiresPermissions("project:projectInvestmentRank:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectInvestmentRank projectInvestmentRank, RedirectAttributes redirectAttributes) {
		projectInvestmentRankService.delete(projectInvestmentRank);
		addMessage(redirectAttributes, "删除投资排行成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectInvestmentRank/?repage";
	}

}