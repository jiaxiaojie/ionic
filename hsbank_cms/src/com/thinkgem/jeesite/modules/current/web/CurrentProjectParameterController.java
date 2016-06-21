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
import com.thinkgem.jeesite.modules.entity.CurrentProjectParameter;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectParameterService;

/**
 * 活期产品参数Controller
 * @author ydt
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/current/currentProjectParameter")
public class CurrentProjectParameterController extends BaseController {

	@Autowired
	private CurrentProjectParameterService currentProjectParameterService;
	
	@ModelAttribute
	public CurrentProjectParameter get(@RequestParam(required=false) String id) {
		CurrentProjectParameter entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentProjectParameterService.get(id);
		}
		if (entity == null){
			entity = new CurrentProjectParameter();
		}
		return entity;
	}
	
	@RequiresPermissions("current:currentProjectParameter:view")
	@RequestMapping(value = {"list", ""})
	public String list(CurrentProjectParameter currentProjectParameter, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentProjectParameter> page = currentProjectParameterService.findPage(new Page<CurrentProjectParameter>(request, response), currentProjectParameter); 
		model.addAttribute("page", page);
		return "modules/current/currentProjectParameterList";
	}

	@RequiresPermissions("current:currentProjectParameter:view")
	@RequestMapping(value = "form")
	public String form(CurrentProjectParameter currentProjectParameter, Model model) {
		model.addAttribute("currentProjectParameter", currentProjectParameter);
		return "modules/current/currentProjectParameterForm";
	}

	@RequiresPermissions("current:currentProjectParameter:edit")
	@RequestMapping(value = "save")
	public String save(CurrentProjectParameter currentProjectParameter, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectParameter)){
			return form(currentProjectParameter, model);
		}
		currentProjectParameterService.save(currentProjectParameter);
		addMessage(redirectAttributes, "保存活期产品参数成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectParameter/?repage";
	}
	
	@RequiresPermissions("current:currentProjectParameter:edit")
	@RequestMapping(value = "delete")
	public String delete(CurrentProjectParameter currentProjectParameter, RedirectAttributes redirectAttributes) {
		currentProjectParameterService.delete(currentProjectParameter);
		addMessage(redirectAttributes, "删除活期产品参数成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectParameter/?repage";
	}

}