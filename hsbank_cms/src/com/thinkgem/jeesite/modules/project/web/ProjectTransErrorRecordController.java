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
import com.thinkgem.jeesite.modules.entity.ProjectTransErrorRecord;
import com.thinkgem.jeesite.modules.project.service.ProjectTransErrorRecordService;

/**
 * 异常交易记录表Controller
 * @author lzb
 * @version 2016-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectTransErrorRecord")
public class ProjectTransErrorRecordController extends BaseController {

	@Autowired
	private ProjectTransErrorRecordService projectTransErrorRecordService;
	
	@ModelAttribute
	public ProjectTransErrorRecord get(@RequestParam(required=false) String id) {
		ProjectTransErrorRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectTransErrorRecordService.get(id);
		}
		if (entity == null){
			entity = new ProjectTransErrorRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectTransErrorRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectTransErrorRecord projectTransErrorRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectTransErrorRecord> page = projectTransErrorRecordService.findPage(new Page<ProjectTransErrorRecord>(request, response), projectTransErrorRecord); 
		model.addAttribute("page", page);
		return "modules/project/projectTransErrorRecordList";
	}

	@RequiresPermissions("project:projectTransErrorRecord:view")
	@RequestMapping(value = "form")
	public String form(ProjectTransErrorRecord projectTransErrorRecord, Model model) {
		model.addAttribute("projectTransErrorRecord", projectTransErrorRecord);
		return "modules/project/projectTransErrorRecordForm";
	}

	@RequiresPermissions("project:projectTransErrorRecord:edit")
	@RequestMapping(value = "save")
	public String save(ProjectTransErrorRecord projectTransErrorRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectTransErrorRecord)){
			return form(projectTransErrorRecord, model);
		}
		projectTransErrorRecordService.save(projectTransErrorRecord);
		addMessage(redirectAttributes, "保存异常交易记录表成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectTransErrorRecord/?repage";
	}
	
	@RequiresPermissions("project:projectTransErrorRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectTransErrorRecord projectTransErrorRecord, RedirectAttributes redirectAttributes) {
		projectTransErrorRecordService.delete(projectTransErrorRecord);
		addMessage(redirectAttributes, "删除异常交易记录表成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectTransErrorRecord/?repage";
	}

}