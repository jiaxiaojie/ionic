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
import com.thinkgem.jeesite.modules.entity.ReceiveSmsMessageTime;
import com.thinkgem.jeesite.modules.message.service.ReceiveSmsMessageTimeService;

/**
 * 用户接收短信时间段Controller
 * @author ydt
 * @version 2016-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/message/receiveSmsMessageTime")
public class ReceiveSmsMessageTimeController extends BaseController {

	@Autowired
	private ReceiveSmsMessageTimeService receiveSmsMessageTimeService;
	
	@ModelAttribute
	public ReceiveSmsMessageTime get(@RequestParam(required=false) String id,@RequestParam(required=false) String accountId) {
		ReceiveSmsMessageTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = receiveSmsMessageTimeService.get(id);
		}
		else if(StringUtils.isNotBlank(accountId)){
			entity = receiveSmsMessageTimeService.getByAccountId(accountId);
		}
		if (entity == null){
			entity = new ReceiveSmsMessageTime();
		}
		return entity;
	}
	
	@RequiresPermissions("message:receiveSmsMessageTime:view")
	@RequestMapping(value = "toSettingTime")
	public String getSettingTime(ReceiveSmsMessageTime receiveSmsMessageTime, Model model) {
		ReceiveSmsMessageTime entity = receiveSmsMessageTimeService.getSettingTimeByAccountId(receiveSmsMessageTime.getAccountId());
		if(entity == null){
			entity = new ReceiveSmsMessageTime();
		}
		model.addAttribute("receiveSmsMessageTime", entity);
		return "modules/message/receiveSmsMessageTimeSetting";
	}
	
	
	@RequiresPermissions("message:receiveSmsMessageTime:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReceiveSmsMessageTime receiveSmsMessageTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReceiveSmsMessageTime> page = receiveSmsMessageTimeService.findPage(new Page<ReceiveSmsMessageTime>(request, response), receiveSmsMessageTime); 
		model.addAttribute("page", page);
		return "modules/message/receiveSmsMessageTimeList";
	}

	@RequiresPermissions("message:receiveSmsMessageTime:view")
	@RequestMapping(value = "form")
	public String form(ReceiveSmsMessageTime receiveSmsMessageTime, Model model) {
		model.addAttribute("receiveSmsMessageTime", receiveSmsMessageTime);
		return "modules/message/receiveSmsMessageTimeForm";
	}

	@RequiresPermissions("message:receiveSmsMessageTime:edit")
	@RequestMapping(value = "save")
	public String save(ReceiveSmsMessageTime receiveSmsMessageTime, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, receiveSmsMessageTime)){
			return form(receiveSmsMessageTime, model);
		}
		receiveSmsMessageTimeService.save(receiveSmsMessageTime);
		addMessage(redirectAttributes, "保存用户接收短信时间段成功");
		return "redirect:"+Global.getAdminPath()+"/message/receiveSmsMessageTime/?repage";
	}
	
	@RequiresPermissions("message:receiveSmsMessageTime:edit")
	@RequestMapping(value = "delete")
	public String delete(ReceiveSmsMessageTime receiveSmsMessageTime, RedirectAttributes redirectAttributes) {
		receiveSmsMessageTimeService.delete(receiveSmsMessageTime);
		addMessage(redirectAttributes, "删除用户接收短信时间段成功");
		return "redirect:"+Global.getAdminPath()+"/message/receiveSmsMessageTime/?repage";
	}

}