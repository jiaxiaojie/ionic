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
import com.thinkgem.jeesite.modules.entity.CurrentAccountSummary;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountSummaryService;

/**
 * 活期账户总览Controller
 * @author ydt
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/current/currentAccountSummary")
public class CurrentAccountSummaryController extends BaseController {

	@Autowired
	private CurrentAccountSummaryService currentAccountSummaryService;
	
	@ModelAttribute
	public CurrentAccountSummary get(@RequestParam(required=false) String id, @RequestParam(required=false) String accountId) {
		CurrentAccountSummary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentAccountSummaryService.get(id);
		}
		else if(StringUtils.isNotBlank(accountId)){
			entity = currentAccountSummaryService.getByAccountId(Long.parseLong(accountId));
		}
		if (entity == null){
			entity = new CurrentAccountSummary();
		}
		return entity;
	}
	
	@RequiresPermissions("current:currentAccountSummary:view")
	@RequestMapping(value = {"list", ""})
	public String list(CurrentAccountSummary currentAccountSummary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentAccountSummary> page = currentAccountSummaryService.findPage(new Page<CurrentAccountSummary>(request, response), currentAccountSummary); 
		model.addAttribute("page", page);
		return "modules/current/currentAccountSummaryList";
	}

	@RequiresPermissions("current:currentAccountSummary:view")
	@RequestMapping(value = "form")
	public String form(CurrentAccountSummary currentAccountSummary, Model model) {
		model.addAttribute("currentAccountSummary", currentAccountSummary);
		return "modules/current/currentAccountSummaryForm";
	}

	@RequiresPermissions("current:currentAccountSummary:edit")
	@RequestMapping(value = "save")
	public String save(CurrentAccountSummary currentAccountSummary, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentAccountSummary)){
			return form(currentAccountSummary, model);
		}
		currentAccountSummaryService.save(currentAccountSummary);
		addMessage(redirectAttributes, "保存活期账户总览成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentAccountSummary/?repage";
	}
	
	@RequiresPermissions("current:currentAccountSummary:edit")
	@RequestMapping(value = "delete")
	public String delete(CurrentAccountSummary currentAccountSummary, RedirectAttributes redirectAttributes) {
		currentAccountSummaryService.delete(currentAccountSummary);
		addMessage(redirectAttributes, "删除活期账户总览成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentAccountSummary/?repage";
	}

}