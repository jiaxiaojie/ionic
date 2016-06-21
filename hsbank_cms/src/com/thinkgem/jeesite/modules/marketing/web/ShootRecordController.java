/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.web;

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
import com.thinkgem.jeesite.modules.entity.ShootRecord;
import com.thinkgem.jeesite.modules.marketing.service.ShootRecordService;

/**
 * 射门记录Controller
 * @author lzb
 * @version 2016-06-13
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/shootRecord")
public class ShootRecordController extends BaseController {

	@Autowired
	private ShootRecordService shootRecordService;
	
	@ModelAttribute
	public ShootRecord get(@RequestParam(required=false) String id) {
		ShootRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shootRecordService.get(id);
		}
		if (entity == null){
			entity = new ShootRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:shootRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShootRecord shootRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShootRecord> page = shootRecordService.findPage(new Page<ShootRecord>(request, response), shootRecord); 
		model.addAttribute("page", page);
		return "modules/marketing/shootRecordList";
	}

	@RequiresPermissions("marketing:shootRecord:view")
	@RequestMapping(value = "form")
	public String form(ShootRecord shootRecord, Model model) {
		model.addAttribute("shootRecord", shootRecord);
		return "modules/marketing/shootRecordForm";
	}

	@RequiresPermissions("marketing:shootRecord:edit")
	@RequestMapping(value = "save")
	public String save(ShootRecord shootRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shootRecord)){
			return form(shootRecord, model);
		}
		shootRecordService.save(shootRecord);
		addMessage(redirectAttributes, "保存射门记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/shootRecord/?repage";
	}
	
	@RequiresPermissions("marketing:shootRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ShootRecord shootRecord, RedirectAttributes redirectAttributes) {
		shootRecordService.delete(shootRecord);
		addMessage(redirectAttributes, "删除射门记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/shootRecord/?repage";
	}

}