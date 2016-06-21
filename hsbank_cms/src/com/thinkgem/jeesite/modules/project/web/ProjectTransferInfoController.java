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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;

/**
 * 债权转让Controller
 * @author yangtao
 * @version 2015-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectTransferInfo")
public class ProjectTransferInfoController extends BaseController {

	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;
	
	@ModelAttribute
	public ProjectTransferInfo get(@RequestParam(required=false) String id) {
		ProjectTransferInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectTransferInfoService.get(id);
		}
		if (entity == null){
			entity = new ProjectTransferInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectTransferInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectTransferInfo projectTransferInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectTransferInfo> page = projectTransferInfoService.findPage(new Page<ProjectTransferInfo>(request, response), projectTransferInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectTransferInfoList";
	}

	@RequiresPermissions("project:projectTransferInfo:view")
	@RequestMapping(value = "form")
	public String form(ProjectTransferInfo projectTransferInfo, Model model) {
		model.addAttribute("projectTransferInfo", projectTransferInfo);
		return "modules/project/projectTransferInfoForm";
	}

	@RequiresPermissions("project:projectTransferInfo:edit")
	@RequestMapping(value = "save")
	public String save(ProjectTransferInfo projectTransferInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectTransferInfo)){
			return form(projectTransferInfo, model);
		}
		projectTransferInfoService.save(projectTransferInfo);
		addMessage(redirectAttributes, "保存债权转让成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectTransferInfo/?repage";
	}
	
	@RequiresPermissions("project:projectTransferInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectTransferInfo projectTransferInfo, RedirectAttributes redirectAttributes) {
		projectTransferInfoService.delete(projectTransferInfo);
		addMessage(redirectAttributes, "删除债权转让成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectTransferInfo/?repage";
	}
	
	@RequiresPermissions("project:projectBaseInfo:transferRecommend")
	@RequestMapping(value = "recommend")
	@ResponseBody
	public boolean setRecommend(String flag,String transferId) {
		return projectTransferInfoService.setRecommend(flag,transferId);
	}

}