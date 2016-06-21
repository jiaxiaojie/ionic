/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.web;

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
import com.thinkgem.jeesite.modules.entity.AppMessagePushRecord;
import com.thinkgem.jeesite.modules.message.service.AppMessagePushRecordService;

/**
 * 客户端消息推送记录Controller
 * @author ydt
 * @version 2016-02-19
 */
@Controller
@RequestMapping(value = "${adminPath}/message/appMessagePushRecord")
public class AppMessagePushRecordController extends BaseController {

	@Autowired
	private AppMessagePushRecordService appMessagePushRecordService;
	
	@ModelAttribute
	public AppMessagePushRecord get(@RequestParam(required=false) String id) {
		AppMessagePushRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = appMessagePushRecordService.get(id);
		}
		if (entity == null){
			entity = new AppMessagePushRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("message:appMessagePushRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(AppMessagePushRecord appMessagePushRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppMessagePushRecord> page = appMessagePushRecordService.findPage(new Page<AppMessagePushRecord>(request, response), appMessagePushRecord); 
		model.addAttribute("page", page);
		return "modules/message/appMessagePushRecordList";
	}

	@RequiresPermissions("message:appMessagePushRecord:view")
	@RequestMapping(value = "form")
	public String form(AppMessagePushRecord appMessagePushRecord, Model model) {
		model.addAttribute("appMessagePushRecord", appMessagePushRecord);
		return "modules/message/appMessagePushRecordForm";
	}

	@RequiresPermissions("message:appMessagePushRecord:edit")
	@RequestMapping(value = "save")
	public String save(AppMessagePushRecord appMessagePushRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appMessagePushRecord)){
			return form(appMessagePushRecord, model);
		}
		appMessagePushRecordService.save(appMessagePushRecord);
		addMessage(redirectAttributes, "保存客户端消息推送记录成功");
		return "redirect:"+Global.getAdminPath()+"/message/appMessagePushRecord/?repage";
	}
	
	@RequiresPermissions("message:appMessagePushRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(AppMessagePushRecord appMessagePushRecord, RedirectAttributes redirectAttributes) {
		appMessagePushRecordService.delete(appMessagePushRecord);
		addMessage(redirectAttributes, "删除客户端消息推送记录成功");
		return "redirect:"+Global.getAdminPath()+"/message/appMessagePushRecord/?repage";
	}

}