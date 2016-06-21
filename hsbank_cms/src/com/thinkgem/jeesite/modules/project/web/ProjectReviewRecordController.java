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
import com.thinkgem.jeesite.modules.entity.ProjectReviewRecord;
import com.thinkgem.jeesite.modules.project.service.ProjectReviewRecordService;

/**
 * 借贷产品审核记录Controller
 * @author yangtao
 * @version 2015-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectReviewRecord")
public class ProjectReviewRecordController extends BaseController {

	@Autowired
	private ProjectReviewRecordService projectReviewRecordService;
	
	@ModelAttribute
	public ProjectReviewRecord get(@RequestParam(required=false) String id) {
		ProjectReviewRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectReviewRecordService.get(id);
		}
		if (entity == null){
			entity = new ProjectReviewRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectBaseInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectReviewRecord projectReviewRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectReviewRecord> page = projectReviewRecordService.findPage(new Page<ProjectReviewRecord>(request, response), projectReviewRecord); 
		model.addAttribute("page", page);
		return "modules/project/projectReviewRecordList";
	}

	@RequiresPermissions("project:projectReviewRecord:view")
	@RequestMapping(value = "form")
	public String form(ProjectReviewRecord projectReviewRecord, Model model) {
		model.addAttribute("projectReviewRecord", projectReviewRecord);
		return "modules/project/projectReviewRecordForm";
	}

	@RequiresPermissions("project:projectReviewRecord:edit")
	@RequestMapping(value = "save")
	public String save(ProjectReviewRecord projectReviewRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectReviewRecord)){
			return form(projectReviewRecord, model);
		}
		projectReviewRecordService.save(projectReviewRecord);
		addMessage(redirectAttributes, "保存借贷产品审核记录成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectReviewRecord/?repage";
	}
	
	@RequiresPermissions("project:projectReviewRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectReviewRecord projectReviewRecord, RedirectAttributes redirectAttributes) {
		projectReviewRecordService.delete(projectReviewRecord);
		addMessage(redirectAttributes, "删除借贷产品审核记录成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectReviewRecord/?repage";
	}

}