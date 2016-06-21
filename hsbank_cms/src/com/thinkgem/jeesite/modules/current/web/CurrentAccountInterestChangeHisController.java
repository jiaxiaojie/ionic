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
import com.thinkgem.jeesite.modules.entity.CurrentAccountInterestChangeHis;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountInterestChangeHisService;

/**
 * 活期账户利息变更历史Controller
 * @author ydt
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/current/currentAccountInterestChangeHis")
public class CurrentAccountInterestChangeHisController extends BaseController {

	@Autowired
	private CurrentAccountInterestChangeHisService currentAccountInterestChangeHisService;
	
	@ModelAttribute
	public CurrentAccountInterestChangeHis get(@RequestParam(required=false) String id) {
		CurrentAccountInterestChangeHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentAccountInterestChangeHisService.get(id);
		}
		if (entity == null){
			entity = new CurrentAccountInterestChangeHis();
		}
		return entity;
	}
	
	@RequiresPermissions("current:currentAccountInterestChangeHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(CurrentAccountInterestChangeHis currentAccountInterestChangeHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentAccountInterestChangeHis> page = currentAccountInterestChangeHisService.findPage(new Page<CurrentAccountInterestChangeHis>(request, response), currentAccountInterestChangeHis); 
		model.addAttribute("page", page);
		return "modules/current/currentAccountInterestChangeHisList";
	}

	@RequiresPermissions("current:currentAccountInterestChangeHis:view")
	@RequestMapping(value = "form")
	public String form(CurrentAccountInterestChangeHis currentAccountInterestChangeHis, Model model) {
		model.addAttribute("currentAccountInterestChangeHis", currentAccountInterestChangeHis);
		return "modules/current/currentAccountInterestChangeHisForm";
	}

	@RequiresPermissions("current:currentAccountInterestChangeHis:edit")
	@RequestMapping(value = "save")
	public String save(CurrentAccountInterestChangeHis currentAccountInterestChangeHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentAccountInterestChangeHis)){
			return form(currentAccountInterestChangeHis, model);
		}
		currentAccountInterestChangeHisService.save(currentAccountInterestChangeHis);
		addMessage(redirectAttributes, "保存活期账户利息变更历史成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentAccountInterestChangeHis/?repage";
	}
	
	@RequiresPermissions("current:currentAccountInterestChangeHis:edit")
	@RequestMapping(value = "delete")
	public String delete(CurrentAccountInterestChangeHis currentAccountInterestChangeHis, RedirectAttributes redirectAttributes) {
		currentAccountInterestChangeHisService.delete(currentAccountInterestChangeHis);
		addMessage(redirectAttributes, "删除活期账户利息变更历史成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentAccountInterestChangeHis/?repage";
	}

}