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
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentRecord;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentRecordService;

/**
 * 还款记录Controller
 * @author yangtao
 * @version 2015-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectRepaymentRecord")
public class ProjectRepaymentRecordController extends BaseController {

	@Autowired
	private ProjectRepaymentRecordService projectRepaymentRecordService;
	
	@ModelAttribute
	public ProjectRepaymentRecord get(@RequestParam(required=false) String id) {
		ProjectRepaymentRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectRepaymentRecordService.get(id);
		}
		if (entity == null){
			entity = new ProjectRepaymentRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectRepaymentRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectRepaymentRecord projectRepaymentRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectRepaymentRecord> page = projectRepaymentRecordService.findPage(new Page<ProjectRepaymentRecord>(request, response), projectRepaymentRecord); 
		model.addAttribute("page", page);
		return "modules/project/projectRepaymentRecordList";
	}

	@RequiresPermissions("project:projectRepaymentRecord:view")
	@RequestMapping(value = "form")
	public String form(ProjectRepaymentRecord projectRepaymentRecord, Model model) {
		model.addAttribute("projectRepaymentRecord", projectRepaymentRecord);
		return "modules/project/projectRepaymentRecordForm";
	}

	@RequiresPermissions("project:projectRepaymentRecord:edit")
	@RequestMapping(value = "save")
	public String save(ProjectRepaymentRecord projectRepaymentRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectRepaymentRecord)){
			return form(projectRepaymentRecord, model);
		}
		projectRepaymentRecordService.save(projectRepaymentRecord);
		addMessage(redirectAttributes, "保存还款记录成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectRepaymentRecord/?repage";
	}
	
	@RequiresPermissions("project:projectRepaymentRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectRepaymentRecord projectRepaymentRecord, RedirectAttributes redirectAttributes) {
		projectRepaymentRecordService.delete(projectRepaymentRecord);
		addMessage(redirectAttributes, "删除还款记录成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectRepaymentRecord/?repage";
	}

}