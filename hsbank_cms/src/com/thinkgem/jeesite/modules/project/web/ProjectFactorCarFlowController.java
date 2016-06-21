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
import com.thinkgem.jeesite.modules.entity.ProjectFactorCarFlow;
import com.thinkgem.jeesite.modules.project.service.ProjectFactorCarFlowService;

/**
 * 车贷项目要素Controller
 * @author yangtao
 * @version 2015-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectFactorCarFlow")
public class ProjectFactorCarFlowController extends BaseController {

	@Autowired
	private ProjectFactorCarFlowService projectFactorCarFlowService;
	
	@ModelAttribute
	public ProjectFactorCarFlow get(@RequestParam(required=false) String id) {
		ProjectFactorCarFlow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectFactorCarFlowService.get(id);
		}
		if (entity == null){
			entity = new ProjectFactorCarFlow();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectFactorCarFlow:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectFactorCarFlow projectFactorCarFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectFactorCarFlow> page = projectFactorCarFlowService.findPage(new Page<ProjectFactorCarFlow>(request, response), projectFactorCarFlow); 
		model.addAttribute("page", page);
		return "modules/project/projectFactorCarFlowList";
	}

	@RequiresPermissions("project:projectFactorCarFlow:view")
	@RequestMapping(value = "form")
	public String form(ProjectFactorCarFlow projectFactorCarFlow, Model model) {
		model.addAttribute("projectFactorCarFlow", projectFactorCarFlow);
		return "modules/project/projectFactorCarFlowForm";
	}

	@RequiresPermissions("project:projectFactorCarFlow:edit")
	@RequestMapping(value = "save")
	public String save(ProjectFactorCarFlow projectFactorCarFlow, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectFactorCarFlow)){
			return form(projectFactorCarFlow, model);
		}
		projectFactorCarFlowService.save(projectFactorCarFlow);
		addMessage(redirectAttributes, "保存车贷成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectFactorCarFlow/?repage";
	}
	
	@RequiresPermissions("project:projectFactorCarFlow:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectFactorCarFlow projectFactorCarFlow, RedirectAttributes redirectAttributes) {
		projectFactorCarFlowService.delete(projectFactorCarFlow);
		addMessage(redirectAttributes, "删除车贷成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectFactorCarFlow/?repage";
	}

}