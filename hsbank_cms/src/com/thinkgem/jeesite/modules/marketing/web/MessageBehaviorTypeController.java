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
import com.thinkgem.jeesite.modules.entity.MessageBehaviorType;
import com.thinkgem.jeesite.modules.marketing.service.MessageBehaviorTypeService;

/**
 * 行为编码Controller
 * @author ydt
 * @version 2016-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/messageBehaviorType")
public class MessageBehaviorTypeController extends BaseController {

	@Autowired
	private MessageBehaviorTypeService messageBehaviorTypeService;
	
	@ModelAttribute
	public MessageBehaviorType get(@RequestParam(required=false) String id) {
		MessageBehaviorType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = messageBehaviorTypeService.get(id);
		}
		if (entity == null){
			entity = new MessageBehaviorType();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:messageBehaviorType:view")
	@RequestMapping(value = {"list", ""})
	public String list(MessageBehaviorType messageBehaviorType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MessageBehaviorType> page = messageBehaviorTypeService.findPage(new Page<MessageBehaviorType>(request, response), messageBehaviorType); 
		model.addAttribute("page", page);
		return "modules/marketing/messageBehaviorTypeList";
	}

	@RequiresPermissions("marketing:messageBehaviorType:view")
	@RequestMapping(value = "form")
	public String form(MessageBehaviorType messageBehaviorType, Model model) {
		model.addAttribute("messageBehaviorType", messageBehaviorType);
		return "modules/marketing/messageBehaviorTypeForm";
	}

	@RequiresPermissions("marketing:messageBehaviorType:edit")
	@RequestMapping(value = "save")
	public String save(MessageBehaviorType messageBehaviorType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, messageBehaviorType)){
			return form(messageBehaviorType, model);
		}
		messageBehaviorTypeService.save(messageBehaviorType);
		addMessage(redirectAttributes, "保存行为编码成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/messageBehaviorType/?repage";
	}
	
	@RequiresPermissions("marketing:messageBehaviorType:edit")
	@RequestMapping(value = "delete")
	public String delete(MessageBehaviorType messageBehaviorType, RedirectAttributes redirectAttributes) {
		messageBehaviorTypeService.delete(messageBehaviorType);
		addMessage(redirectAttributes, "删除行为编码成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/messageBehaviorType/?repage";
	}

}