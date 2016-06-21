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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectNoticeService;
import com.thinkgem.jeesite.modules.entity.CurrentProjectNotice;

/**
 * 活期产品公告Controller
 * @author ydt
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/current/currentProjectNotice")
public class CurrentProjectNoticeController extends BaseController {

	@Autowired
	private CurrentProjectNoticeService currentProjectNoticeService;
	
	
	@ModelAttribute
	public CurrentProjectNotice get(@RequestParam(required=false) String id) {
		CurrentProjectNotice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentProjectNoticeService.get(id);
		}
		if (entity == null){
			entity = new CurrentProjectNotice();
		}
		return entity;
	}
	

	
	@RequiresPermissions("current:currentProjectNotice:view")
	@RequestMapping(value = {"list", ""})
	public String list(CurrentProjectNotice currentProjectNotice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentProjectNotice> page = currentProjectNoticeService.findPage(new Page<CurrentProjectNotice>(request, response), currentProjectNotice); 
		model.addAttribute("page", page);
		return "modules/current/currentProjectNoticeList";
	}

	@RequiresPermissions("current:currentProjectNotice:view")
	@RequestMapping(value = "form")
	public String form(CurrentProjectNotice currentProjectNotice, Model model) {
		model.addAttribute("currentProjectNotice", currentProjectNotice);
		return "modules/current/currentProjectNoticeForm";
	}

	@RequiresPermissions("current:currentProjectNotice:edit")
	@RequestMapping(value = "save")
	public String save(CurrentProjectNotice currentProjectNotice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectNotice)){
			return form(currentProjectNotice, model);
		}
		currentProjectNoticeService.save(currentProjectNotice);
		addMessage(redirectAttributes, "保存活期产品公告成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectNotice/?repage";
	}
	
	@RequiresPermissions("current:currentProjectNotice:edit")
	@RequestMapping(value = "delete")
	public String delete(CurrentProjectNotice currentProjectNotice, RedirectAttributes redirectAttributes) {
		currentProjectNoticeService.delete(currentProjectNotice);
		addMessage(redirectAttributes, "删除活期产品公告成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectNotice/?repage";
	}

}