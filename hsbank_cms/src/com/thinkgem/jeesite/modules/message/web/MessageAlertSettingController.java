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
import com.thinkgem.jeesite.modules.entity.MessageAlertSetting;
import com.thinkgem.jeesite.modules.message.service.MessageAlertSettingService;

import java.util.Date;

/**
 * 消息提醒设置Controller
 * @author hyc
 * @version 2016-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/message/messageAlertSetting")
public class MessageAlertSettingController extends BaseController {

	@Autowired
	private MessageAlertSettingService messageAlertSettingService;
	
	@ModelAttribute
	public MessageAlertSetting get(@RequestParam(required=false) String id) {
		MessageAlertSetting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = messageAlertSettingService.get(id);
		}
		if (entity == null){
			entity = new MessageAlertSetting();
		}
		return entity;
	}
	
	@RequiresPermissions("message:messageAlertSetting:view")
	@RequestMapping(value = {"list", ""})
	public String list(MessageAlertSetting messageAlertSetting, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MessageAlertSetting> page = messageAlertSettingService.findPage(new Page<MessageAlertSetting>(request, response), messageAlertSetting); 
		model.addAttribute("page", page);
		return "modules/message/messageAlertSettingList";
	}

	@RequiresPermissions("message:messageAlertSetting:view")
	@RequestMapping(value = "form")
	public String form(MessageAlertSetting messageAlertSetting, Model model) {
		model.addAttribute("messageAlertSetting", messageAlertSetting);
		return "modules/message/messageAlertSettingForm";
	}

	@RequiresPermissions("message:messageAlertSetting:edit")
	@RequestMapping(value = "save")
	public String save(MessageAlertSetting messageAlertSetting, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, messageAlertSetting)){
			return form(messageAlertSetting, model);
		}
		messageAlertSettingService.save(messageAlertSetting);
		addMessage(redirectAttributes, "保存消息提醒设置成功");
		return "redirect:"+Global.getAdminPath()+"/message/messageAlertSetting/?repage";
	}
	
	@RequiresPermissions("message:messageAlertSetting:edit")
	@RequestMapping(value = "delete")
	public String delete(MessageAlertSetting messageAlertSetting, RedirectAttributes redirectAttributes) {
		messageAlertSettingService.delete(messageAlertSetting);
		addMessage(redirectAttributes, "删除消息提醒设置成功");
		return "redirect:"+Global.getAdminPath()+"/message/messageAlertSetting/?repage";
	}

}