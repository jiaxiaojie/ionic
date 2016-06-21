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
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;

/**
 * 合同执行快照Controller
 * @author yangtao
 * @version 2015-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectExecuteSnapshot")
public class ProjectExecuteSnapshotController extends BaseController {

	@Autowired
	private ProjectExecuteSnapshotService projectExecuteSnapshotService;
	
	@ModelAttribute
	public ProjectExecuteSnapshot get(@RequestParam(required=false) String id) {
		ProjectExecuteSnapshot entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectExecuteSnapshotService.get(id);
		}
		if (entity == null){
			entity = new ProjectExecuteSnapshot();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectExecuteSnapshot:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectExecuteSnapshot projectExecuteSnapshot, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectExecuteSnapshot> page = projectExecuteSnapshotService.findPage(new Page<ProjectExecuteSnapshot>(request, response), projectExecuteSnapshot); 
		model.addAttribute("page", page);
		return "modules/project/projectExecuteSnapshotList";
	}

	@RequiresPermissions("project:projectExecuteSnapshot:view")
	@RequestMapping(value = "form")
	public String form(ProjectExecuteSnapshot projectExecuteSnapshot, Model model) {
		model.addAttribute("projectExecuteSnapshot", projectExecuteSnapshot);
		return "modules/project/projectExecuteSnapshotForm";
	}
	
	@RequiresPermissions("project:projectExecuteSnapshot:view")
	@RequestMapping(value = "view")
	public String view(ProjectExecuteSnapshot projectExecuteSnapshot, Model model) {
		Long projectId = projectExecuteSnapshot.getQueryProjectId();
		projectExecuteSnapshot = projectExecuteSnapshotService.getByProjectIdAndTransferId(String.valueOf(projectId), "0");
		if(projectExecuteSnapshot == null ){
			projectExecuteSnapshot = new ProjectExecuteSnapshot();
		}
		projectExecuteSnapshot.setQueryProjectId(projectId);
		model.addAttribute("projectExecuteSnapshot", projectExecuteSnapshot);
		return "modules/project/projectExecuteSnapshotForm";
	}

	@RequiresPermissions("project:projectExecuteSnapshot:edit")
	@RequestMapping(value = "save")
	public String save(ProjectExecuteSnapshot projectExecuteSnapshot, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectExecuteSnapshot)){
			return form(projectExecuteSnapshot, model);
		}
		projectExecuteSnapshotService.save(projectExecuteSnapshot);
		addMessage(redirectAttributes, "保存合同执行快照成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectExecuteSnapshot/?repage";
	}
	
	@RequiresPermissions("project:projectExecuteSnapshot:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectExecuteSnapshot projectExecuteSnapshot, RedirectAttributes redirectAttributes) {
		projectExecuteSnapshotService.delete(projectExecuteSnapshot);
		addMessage(redirectAttributes, "删除合同执行快照成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectExecuteSnapshot/?repage";
	}

}