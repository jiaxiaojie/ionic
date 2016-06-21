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
import com.thinkgem.jeesite.modules.entity.JoinMatchRecord;
import com.thinkgem.jeesite.modules.marketing.service.JoinMatchRecordService;

/**
 * 参赛记录Controller
 * @author ydt
 * @version 2016-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/joinMatchRecord")
public class JoinMatchRecordController extends BaseController {

	@Autowired
	private JoinMatchRecordService joinMatchRecordService;
	
	@ModelAttribute
	public JoinMatchRecord get(@RequestParam(required=false) String id) {
		JoinMatchRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = joinMatchRecordService.get(id);
		}
		if (entity == null){
			entity = new JoinMatchRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:joinMatchRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(JoinMatchRecord joinMatchRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JoinMatchRecord> page = joinMatchRecordService.findPage(new Page<JoinMatchRecord>(request, response), joinMatchRecord); 
		model.addAttribute("page", page);
		return "modules/marketing/joinMatchRecordList";
	}

	@RequiresPermissions("marketing:joinMatchRecord:view")
	@RequestMapping(value = "form")
	public String form(JoinMatchRecord joinMatchRecord, Model model) {
		model.addAttribute("joinMatchRecord", joinMatchRecord);
		return "modules/marketing/joinMatchRecordForm";
	}

	@RequiresPermissions("marketing:joinMatchRecord:edit")
	@RequestMapping(value = "save")
	public String save(JoinMatchRecord joinMatchRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, joinMatchRecord)){
			return form(joinMatchRecord, model);
		}
		joinMatchRecordService.save(joinMatchRecord);
		addMessage(redirectAttributes, "保存参赛记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/joinMatchRecord/?repage";
	}
	
	@RequiresPermissions("marketing:joinMatchRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(JoinMatchRecord joinMatchRecord, RedirectAttributes redirectAttributes) {
		joinMatchRecordService.delete(joinMatchRecord);
		addMessage(redirectAttributes, "删除参赛记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/joinMatchRecord/?repage";
	}

}