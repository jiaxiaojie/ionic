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
import com.thinkgem.jeesite.modules.entity.CurrentProjectHoldInfo;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectHoldInfoService;

/**
 * 活期产品持有信息Controller
 * @author ydt
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/current/currentProjectHoldInfo")
public class CurrentProjectHoldInfoController extends BaseController {

	@Autowired
	private CurrentProjectHoldInfoService currentProjectHoldInfoService;
	
	@ModelAttribute
	public CurrentProjectHoldInfo get(@RequestParam(required=false) String id) {
		CurrentProjectHoldInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentProjectHoldInfoService.get(id);
		}
		if (entity == null){
			entity = new CurrentProjectHoldInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("current:currentProjectHoldInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CurrentProjectHoldInfo currentProjectHoldInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentProjectHoldInfo> page = currentProjectHoldInfoService.findPage(new Page<CurrentProjectHoldInfo>(request, response), currentProjectHoldInfo); 
		model.addAttribute("page", page);
		return "modules/current/currentProjectHoldInfoList";
	}

	@RequiresPermissions("current:currentProjectHoldInfo:view")
	@RequestMapping(value = "form")
	public String form(CurrentProjectHoldInfo currentProjectHoldInfo, Model model) {
		model.addAttribute("currentProjectHoldInfo", currentProjectHoldInfo);
		return "modules/current/currentProjectHoldInfoForm";
	}

	@RequiresPermissions("current:currentProjectHoldInfo:edit")
	@RequestMapping(value = "save")
	public String save(CurrentProjectHoldInfo currentProjectHoldInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectHoldInfo)){
			return form(currentProjectHoldInfo, model);
		}
		currentProjectHoldInfoService.save(currentProjectHoldInfo);
		addMessage(redirectAttributes, "保存活期产品持有信息成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectHoldInfo/?repage";
	}
	
	@RequiresPermissions("current:currentProjectHoldInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CurrentProjectHoldInfo currentProjectHoldInfo, RedirectAttributes redirectAttributes) {
		currentProjectHoldInfoService.delete(currentProjectHoldInfo);
		addMessage(redirectAttributes, "删除活期产品持有信息成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectHoldInfo/?repage";
	}

}