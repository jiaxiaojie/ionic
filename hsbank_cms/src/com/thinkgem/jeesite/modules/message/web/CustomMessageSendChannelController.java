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
import com.thinkgem.jeesite.modules.entity.CustomMessageSendChannel;
import com.thinkgem.jeesite.modules.message.service.CustomMessageSendChannelService;

/**
 * 信息渠道Controller
 * @author huangyuchen
 * @version 2016-01-18
 */
@Controller
@RequestMapping(value = "${adminPath}/message/customMessageSendChannel")
public class CustomMessageSendChannelController extends BaseController {

	@Autowired
	private CustomMessageSendChannelService customMessageSendChannelService;
	
	@ModelAttribute
	public CustomMessageSendChannel get(@RequestParam(required=false) String id) {
		CustomMessageSendChannel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customMessageSendChannelService.get(id);
		}
		if (entity == null){
			entity = new CustomMessageSendChannel();
		}
		return entity;
	}
	
	@RequiresPermissions("message:customMessageSendChannel:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomMessageSendChannel customMessageSendChannel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomMessageSendChannel> page = customMessageSendChannelService.findPage(new Page<CustomMessageSendChannel>(request, response), customMessageSendChannel); 
		model.addAttribute("page", page);
		return "modules/message/customMessageSendChannelList";
	}

	@RequiresPermissions("message:customMessageSendChannel:view")
	@RequestMapping(value = "form")
	public String form(CustomMessageSendChannel customMessageSendChannel, Model model) {
		model.addAttribute("customMessageSendChannel", customMessageSendChannel);
		return "modules/message/customMessageSendChannelForm";
	}

	@RequiresPermissions("message:customMessageSendChannel:edit")
	@RequestMapping(value = "save")
	public String save(CustomMessageSendChannel customMessageSendChannel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customMessageSendChannel)){
			return form(customMessageSendChannel, model);
		}
		customMessageSendChannelService.save(customMessageSendChannel);
		addMessage(redirectAttributes, "保存信息渠道成功");
		return "redirect:"+Global.getAdminPath()+"/message/customMessageSendChannel/?repage";
	}
	
	@RequiresPermissions("message:customMessageSendChannel:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomMessageSendChannel customMessageSendChannel, RedirectAttributes redirectAttributes) {
		customMessageSendChannelService.delete(customMessageSendChannel);
		addMessage(redirectAttributes, "删除信息渠道成功");
		return "redirect:"+Global.getAdminPath()+"/message/customMessageSendChannel/?repage";
	}

}