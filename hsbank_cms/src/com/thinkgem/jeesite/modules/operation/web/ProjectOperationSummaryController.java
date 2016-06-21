/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.operation.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.ProjectOperationSummary;
import com.thinkgem.jeesite.modules.operation.service.ProjectOperationSummaryService;

/**
 * 项目运营数据汇总Controller
 * @author ydt
 * @version 2015-12-25
 */
@Controller
@RequestMapping(value = "${adminPath}/operation/projectOperationSummary")
public class ProjectOperationSummaryController extends BaseController {

	@Autowired
	private ProjectOperationSummaryService projectOperationSummaryService;
	
	@ModelAttribute
	public ProjectOperationSummary get(@RequestParam(required=false) String id) {
		ProjectOperationSummary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectOperationSummaryService.get(id);
		}
		if (entity == null){
			entity = new ProjectOperationSummary();
		}
		return entity;
	}
	
	/**
	 * 导出数据
	 * 
	 * @return
	 */
	@RequiresPermissions("operation:projectOperationSummary:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ProjectOperationSummary projectOperationSummary, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运营数据汇总"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<ProjectOperationSummary> list = projectOperationSummaryService.findList(projectOperationSummary); 
    		new ExportExcel("运营数据汇总（起始时间："+StringUtils.replaceNull(DateUtils.formatDate(projectOperationSummary.getBeginDate(), "yyyy-MM-dd"), "不限制")+",结束时间："+StringUtils.replaceNull(DateUtils.formatDate(projectOperationSummary.getEndDate(), "yyyy-MM-dd"), "不限制")+")", ProjectOperationSummary.class)
    		.setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出运营数据失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/modules/operation/projectOperationSummaryList?repage";
    }
	
	


	@RequiresPermissions("operation:projectOperationSummary:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectOperationSummary projectOperationSummary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectOperationSummary> page = projectOperationSummaryService.findPage(new Page<ProjectOperationSummary>(request, response), projectOperationSummary); 
		model.addAttribute("page", page);
		return "modules/operation/projectOperationSummaryList";
	}

	@RequiresPermissions("operation:projectOperationSummary:view")
	@RequestMapping(value = "form")
	public String form(ProjectOperationSummary projectOperationSummary, Model model) {
		model.addAttribute("projectOperationSummary", projectOperationSummary);
		return "modules/operation/projectOperationSummaryForm";
	}

	@RequiresPermissions("operation:projectOperationSummary:edit")
	@RequestMapping(value = "save")
	public String save(ProjectOperationSummary projectOperationSummary, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectOperationSummary)){
			return form(projectOperationSummary, model);
		}
		projectOperationSummaryService.save(projectOperationSummary);
		addMessage(redirectAttributes, "保存项目运营数据汇总成功");
		return "redirect:"+Global.getAdminPath()+"/operation/projectOperationSummary/?repage";
	}
	
	@RequiresPermissions("operation:projectOperationSummary:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectOperationSummary projectOperationSummary, RedirectAttributes redirectAttributes) {
		projectOperationSummaryService.delete(projectOperationSummary);
		addMessage(redirectAttributes, "删除项目运营数据汇总成功");
		return "redirect:"+Global.getAdminPath()+"/operation/projectOperationSummary/?repage";
	}

}