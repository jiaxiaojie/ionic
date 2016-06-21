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
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;

/**
 * 投资记录Controller
 * @author yangtao
 * @version 2015-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectInvestmentRecord")
public class ProjectInvestmentRecordController extends BaseController {

	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	
	@ModelAttribute
	public ProjectInvestmentRecord get(@RequestParam(required=false) String id) {
		ProjectInvestmentRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectInvestmentRecordService.get(id);
		}
		if (entity == null){
			entity = new ProjectInvestmentRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectInvestmentRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectInvestmentRecord projectInvestmentRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectInvestmentRecord> page = projectInvestmentRecordService.findPage(new Page<ProjectInvestmentRecord>(request, response), projectInvestmentRecord); 
		model.addAttribute("page", page);
		return "modules/project/projectInvestmentRecordList";
	}
	
	
	
	
	
	

	@RequiresPermissions("project:projectInvestmentRecord:view")
	@RequestMapping(value = "form")
	public String form(ProjectInvestmentRecord projectInvestmentRecord, Model model) {
		model.addAttribute("projectInvestmentRecord", projectInvestmentRecord);
		return "modules/project/projectInvestmentRecordForm";
	}

	@RequiresPermissions("project:projectInvestmentRecord:edit")
	@RequestMapping(value = "save")
	public String save(ProjectInvestmentRecord projectInvestmentRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectInvestmentRecord)){
			return form(projectInvestmentRecord, model);
		}
		projectInvestmentRecordService.save(projectInvestmentRecord);
		addMessage(redirectAttributes, "保存投资记录成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectInvestmentRecord/?repage";
	}
	
	@RequiresPermissions("project:projectInvestmentRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectInvestmentRecord projectInvestmentRecord, RedirectAttributes redirectAttributes) {
		projectInvestmentRecordService.delete(projectInvestmentRecord);
		addMessage(redirectAttributes, "删除投资记录成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectInvestmentRecord/?repage";
	}

}