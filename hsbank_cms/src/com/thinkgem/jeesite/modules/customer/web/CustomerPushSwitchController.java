/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.web;

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
import com.thinkgem.jeesite.modules.entity.CustomerPushSwitch;
import com.thinkgem.jeesite.modules.customer.service.CustomerPushSwitchService;

/**
 * 用户接收push消息开关Controller
 * @author lzb
 * @version 2016-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerPushSwitch")
public class CustomerPushSwitchController extends BaseController {

	@Autowired
	private CustomerPushSwitchService customerPushSwitchService;
	
	@ModelAttribute
	public CustomerPushSwitch get(@RequestParam(required=false) String id) {
		CustomerPushSwitch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerPushSwitchService.get(id);
		}
		if (entity == null){
			entity = new CustomerPushSwitch();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerPushSwitch:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerPushSwitch customerPushSwitch, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerPushSwitch> page = customerPushSwitchService.findPage(new Page<CustomerPushSwitch>(request, response), customerPushSwitch); 
		model.addAttribute("page", page);
		return "modules/customer/customerPushSwitchList";
	}

	@RequiresPermissions("customer:customerPushSwitch:view")
	@RequestMapping(value = "form")
	public String form(CustomerPushSwitch customerPushSwitch, Model model) {
		model.addAttribute("customerPushSwitch", customerPushSwitch);
		return "modules/customer/customerPushSwitchForm";
	}

	@RequiresPermissions("customer:customerPushSwitch:edit")
	@RequestMapping(value = "save")
	public String save(CustomerPushSwitch customerPushSwitch, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerPushSwitch)){
			return form(customerPushSwitch, model);
		}
		customerPushSwitchService.save(customerPushSwitch);
		addMessage(redirectAttributes, "保存用户接收push消息开关成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerPushSwitch/?repage";
	}
	
	@RequiresPermissions("customer:customerPushSwitch:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerPushSwitch customerPushSwitch, RedirectAttributes redirectAttributes) {
		customerPushSwitchService.delete(customerPushSwitch);
		addMessage(redirectAttributes, "删除用户接收push消息开关成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerPushSwitch/?repage";
	}

}