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
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;

/**
 * 活期账户本金变更历史Controller
 * @author ydt
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/current/currentAccountPrincipalChangeHis")
public class CurrentAccountPrincipalChangeHisController extends BaseController {

	@Autowired
	private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;
	
	@ModelAttribute
	public CurrentAccountPrincipalChangeHis get(@RequestParam(required=false) String id) {
		CurrentAccountPrincipalChangeHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentAccountPrincipalChangeHisService.get(id);
		}
		if (entity == null){
			entity = new CurrentAccountPrincipalChangeHis();
		}
		return entity;
	}
	
	@RequiresPermissions("current:currentAccountPrincipalChangeHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentAccountPrincipalChangeHis> page = currentAccountPrincipalChangeHisService.findPage(new Page<CurrentAccountPrincipalChangeHis>(request, response), currentAccountPrincipalChangeHis); 
		model.addAttribute("page", page);
		return "modules/current/currentAccountPrincipalChangeHisList";
	}

	@RequiresPermissions("current:currentAccountPrincipalChangeHis:view")
	@RequestMapping(value = "form")
	public String form(CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis, Model model) {
		model.addAttribute("currentAccountPrincipalChangeHis", currentAccountPrincipalChangeHis);
		return "modules/current/currentAccountPrincipalChangeHisForm";
	}

	@RequiresPermissions("current:currentAccountPrincipalChangeHis:edit")
	@RequestMapping(value = "save")
	public String save(CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentAccountPrincipalChangeHis)){
			return form(currentAccountPrincipalChangeHis, model);
		}
		currentAccountPrincipalChangeHisService.save(currentAccountPrincipalChangeHis);
		addMessage(redirectAttributes, "保存活期账户本金变更历史成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentAccountPrincipalChangeHis/?repage";
	}
	
	@RequiresPermissions("current:currentAccountPrincipalChangeHis:edit")
	@RequestMapping(value = "delete")
	public String delete(CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis, RedirectAttributes redirectAttributes) {
		currentAccountPrincipalChangeHisService.delete(currentAccountPrincipalChangeHis);
		addMessage(redirectAttributes, "删除活期账户本金变更历史成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentAccountPrincipalChangeHis/?repage";
	}

}