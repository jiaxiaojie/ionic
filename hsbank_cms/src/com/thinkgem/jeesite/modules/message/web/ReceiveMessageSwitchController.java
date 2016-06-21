/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.ReceiveMessageSwitch;
import com.thinkgem.jeesite.modules.message.service.ReceiveMessageSwitchService;

/**
 * 用户接收消息开关Controller
 * @author ydt
 * @version 2016-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/message/receiveMessageSwitch")
public class ReceiveMessageSwitchController extends BaseController {

	@Autowired
	private ReceiveMessageSwitchService receiveMessageSwitchService;
	
	@ModelAttribute
	public ReceiveMessageSwitch get(@RequestParam(required=false) String id) {
		ReceiveMessageSwitch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = receiveMessageSwitchService.get(id);
		}
		if (entity == null){
			entity = new ReceiveMessageSwitch();
		}
		return entity;
	}
	
	@RequiresPermissions("message:receiveMessageSwitch:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReceiveMessageSwitch receiveMessageSwitch, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReceiveMessageSwitch> page = receiveMessageSwitchService.findPage(new Page<ReceiveMessageSwitch>(request, response), receiveMessageSwitch); 
		model.addAttribute("page", page);
		return "modules/message/receiveMessageSwitchList";
	}

	@RequiresPermissions("message:receiveMessageSwitch:view")
	@RequestMapping(value = "form")
	public String form(ReceiveMessageSwitch receiveMessageSwitch, Model model) {
		model.addAttribute("receiveMessageSwitch", receiveMessageSwitch);
		return "modules/message/receiveMessageSwitchForm";
	}

	@RequiresPermissions("message:receiveMessageSwitch:edit")
	@RequestMapping(value = "save")
	public String save(ReceiveMessageSwitch receiveMessageSwitch, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, receiveMessageSwitch)){
			return form(receiveMessageSwitch, model);
		}
		receiveMessageSwitchService.save(receiveMessageSwitch);
		addMessage(redirectAttributes, "保存用户接收消息开关成功");
		return "redirect:"+Global.getAdminPath()+"/message/receiveMessageSwitch/?repage";
	}
	
	@RequiresPermissions("message:receiveMessageSwitch:edit")
	@RequestMapping(value = "delete")
	public String delete(ReceiveMessageSwitch receiveMessageSwitch, RedirectAttributes redirectAttributes) {
		receiveMessageSwitchService.delete(receiveMessageSwitch);
		addMessage(redirectAttributes, "删除用户接收消息开关成功");
		return "redirect:"+Global.getAdminPath()+"/message/receiveMessageSwitch/?repage";
	}
	
	/**
	 * 会员消息开关设置列表
	 * @param receiveMessageSwitch
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("message:receiveMessageSwitch:view")
	@RequestMapping(value = "customerSwitchSettingList")
	public String customerSwitchSettingList(ReceiveMessageSwitch receiveMessageSwitch, HttpServletRequest request, HttpServletResponse response, Model model) {
		//receiveMessageSwitch.setRuleId(receiveMessageSwitch.getRuleId()==null?1L:receiveMessageSwitch.getRuleId());
		Page<Map<String,Object>> page = receiveMessageSwitchService.findCustomerSwitchPage(new Page<Map<String,Object>>(request, response), receiveMessageSwitch); 
		model.addAttribute("page", page);
		model.addAttribute("params", receiveMessageSwitch);
		return "modules/message/customReceiveMsgSwitchSetting";
	}
	
	@ResponseBody
	@RequiresPermissions("message:receiveMessageSwitch:edit")
	@RequestMapping(value = "customReceiveMsgSwitchSetting")
	public HashMap<String,Object> customReceiveMsgSwitchSetting(ReceiveMessageSwitch receiveMessageSwitch, Model model, RedirectAttributes redirectAttributes) {
		HashMap<String,Object> msg = new HashMap<String,Object>();
		msg.put("msg", "设置消息开关失败");
		msg.put("refresh", true);
		if (!beanValidator(model, receiveMessageSwitch)){
			return msg;
		}
		
		
		Boolean result = receiveMessageSwitchService.settingMessageSwitch(receiveMessageSwitch);
		msg.put("refresh", result);
		msg.put("msg", "设置消息开关成功!");
		
		return msg;
	}

}