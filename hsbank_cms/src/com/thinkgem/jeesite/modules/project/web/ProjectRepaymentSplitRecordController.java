/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.web;

import java.util.Map;

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
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentSplitRecordService;

/**
 * 还款记录查分明细Controller
 * @author yangtao
 * @version 2015-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectRepaymentSplitRecord")
public class ProjectRepaymentSplitRecordController extends BaseController {

	@Autowired
	private ProjectRepaymentSplitRecordService projectRepaymentSplitRecordService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@ModelAttribute
	public ProjectRepaymentSplitRecord get(@RequestParam(required=false) String id) {
		ProjectRepaymentSplitRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectRepaymentSplitRecordService.get(id);
		}
		if (entity == null){
			entity = new ProjectRepaymentSplitRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectRepaymentSplitRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectRepaymentSplitRecord projectRepaymentSplitRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectRepaymentSplitRecord> page = projectRepaymentSplitRecordService.findPage(new Page<ProjectRepaymentSplitRecord>(request, response), projectRepaymentSplitRecord); 
		model.addAttribute("page", page);
		return "modules/project/projectRepaymentSplitRecordList";
	}
	@RequiresPermissions("project:projectRepaymentSplitRecord:view")
	@RequestMapping(value = {"projectRepaymentSplitRecordInfoList"})
	public String projectRepaymentSplitRecordInfoList(ProjectRepaymentSplitRecord projectRepaymentSplitRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectRepaymentSplitRecord.setProjectId(projectRepaymentSplitRecord.getQueryProjectId());
		Page<ProjectRepaymentSplitRecord> page = projectRepaymentSplitRecordService.findPage(new Page<ProjectRepaymentSplitRecord>(request, response), projectRepaymentSplitRecord); 
		model.addAttribute("page", page);
		return "modules/project/projectRepaymentSplitRecordInfoList";
	}
	
	

	/**
	 *还款计划查看列表
	 * @param repaymentList
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("project:projectRepaymentSplitRecord:view")
	@RequestMapping(value = {"repaymentList"})
	public String repaymentList(ProjectRepaymentSplitRecord projectRepaymentSplitRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectRepaymentSplitRecord.setProjectId(new Long(projectRepaymentSplitRecord.getQueryProjectId()));
		String projectId=String.valueOf(projectRepaymentSplitRecord.getQueryProjectId());
		Page<Map<String,Object>> page = projectRepaymentSplitRecordService.selectSumPrincipalAndSumInterest(new Page<Map<String,Object>>(request, response), projectRepaymentSplitRecord);
		model.addAttribute("page", page);
		//根据项目id获取项目的本金和项目利息
		Map<String,Object> repaymentPlans = projectRepaymentSplitRecordService.findSumPrincipalAndSumInterest(projectId);
		model.addAttribute("repaymentPlans", repaymentPlans);
		//查询项目
		ProjectBaseInfo projectBaseInfo = projectBaseInfoService.get(String.valueOf(projectRepaymentSplitRecord.getQueryProjectId()));
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		return "modules/operation/repaymentPlanList";
	}

	@RequiresPermissions("project:projectRepaymentSplitRecord:view")
	@RequestMapping(value = "form")
	public String form(ProjectRepaymentSplitRecord projectRepaymentSplitRecord, Model model) {
		model.addAttribute("projectRepaymentSplitRecord", projectRepaymentSplitRecord);
		return "modules/project/projectRepaymentSplitRecordForm";
	}

	@RequiresPermissions("project:projectRepaymentSplitRecord:edit")
	@RequestMapping(value = "save")
	public String save(ProjectRepaymentSplitRecord projectRepaymentSplitRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectRepaymentSplitRecord)){
			return form(projectRepaymentSplitRecord, model);
		}
		projectRepaymentSplitRecordService.save(projectRepaymentSplitRecord);
		addMessage(redirectAttributes, "保存还款明细成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectRepaymentSplitRecord/?repage";
	}
	
	@RequiresPermissions("project:projectRepaymentSplitRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectRepaymentSplitRecord projectRepaymentSplitRecord, RedirectAttributes redirectAttributes) {
		projectRepaymentSplitRecordService.delete(projectRepaymentSplitRecord);
		addMessage(redirectAttributes, "删除还款明细成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectRepaymentSplitRecord/?repage";
	}

}