/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.web;

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
import com.thinkgem.jeesite.modules.entity.CurrentProjectRepayRecord;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectRepayRecordService;

/**
 * 活期产品付款记录Controller
 * @author ydt
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/current/currentProjectRepayRecord")
public class CurrentProjectRepayRecordController extends BaseController {

	@Autowired
	private CurrentProjectRepayRecordService currentProjectRepayRecordService;
	
	@ModelAttribute
	public CurrentProjectRepayRecord get(@RequestParam(required=false) String id) {
		CurrentProjectRepayRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentProjectRepayRecordService.get(id);
		}
		if (entity == null){
			entity = new CurrentProjectRepayRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("current:currentProjectRepayRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(CurrentProjectRepayRecord currentProjectRepayRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentProjectRepayRecord> page = currentProjectRepayRecordService.findPage(new Page<CurrentProjectRepayRecord>(request, response), currentProjectRepayRecord); 
		model.addAttribute("page", page);
		return "modules/current/currentProjectRepayRecordList";
	}

	@RequiresPermissions("current:currentProjectRepayRecord:view")
	@RequestMapping(value = "form")
	public String form(CurrentProjectRepayRecord currentProjectRepayRecord, Model model) {
		model.addAttribute("currentProjectRepayRecord", currentProjectRepayRecord);
		return "modules/current/currentProjectRepayRecordForm";
	}

	@RequiresPermissions("current:currentProjectRepayRecord:edit")
	@RequestMapping(value = "save")
	public String save(CurrentProjectRepayRecord currentProjectRepayRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectRepayRecord)){
			return form(currentProjectRepayRecord, model);
		}
		currentProjectRepayRecordService.save(currentProjectRepayRecord);
		addMessage(redirectAttributes, "保存活期产品付款记录成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectRepayRecord/?repage";
	}
	
	@RequiresPermissions("current:currentProjectRepayRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(CurrentProjectRepayRecord currentProjectRepayRecord, RedirectAttributes redirectAttributes) {
		currentProjectRepayRecordService.delete(currentProjectRepayRecord);
		addMessage(redirectAttributes, "删除活期产品付款记录成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectRepayRecord/?repage";
	}

}