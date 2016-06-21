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
import com.thinkgem.jeesite.modules.entity.CustomerLoginLog;
import com.thinkgem.jeesite.modules.customer.service.CustomerLoginLogService;

/**
 * 会员客户端访问流水Controller
 * @author ydt
 * @version 2015-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerLoginLog")
public class CustomerLoginLogController extends BaseController {

	@Autowired
	private CustomerLoginLogService customerLoginLogService;
	
	@ModelAttribute
	public CustomerLoginLog get(@RequestParam(required=false) String id) {
		CustomerLoginLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerLoginLogService.get(id);
		}
		if (entity == null){
			entity = new CustomerLoginLog();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerLoginLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerLoginLog customerLoginLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerLoginLog> page = customerLoginLogService.findPage(new Page<CustomerLoginLog>(request, response), customerLoginLog); 
		model.addAttribute("page", page);
		return "modules/customer/customerLoginLogList";
	}

	@RequiresPermissions("customer:customerLoginLog:view")
	@RequestMapping(value = "form")
	public String form(CustomerLoginLog customerLoginLog, Model model) {
		model.addAttribute("customerLoginLog", customerLoginLog);
		return "modules/customer/customerLoginLogForm";
	}

	@RequiresPermissions("customer:customerLoginLog:edit")
	@RequestMapping(value = "save")
	public String save(CustomerLoginLog customerLoginLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerLoginLog)){
			return form(customerLoginLog, model);
		}
		customerLoginLogService.save(customerLoginLog);
		addMessage(redirectAttributes, "保存会员客户端访问流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerLoginLog/?repage";
	}
	
	@RequiresPermissions("customer:customerLoginLog:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerLoginLog customerLoginLog, RedirectAttributes redirectAttributes) {
		customerLoginLogService.delete(customerLoginLog);
		addMessage(redirectAttributes, "删除会员客户端访问流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerLoginLog/?repage";
	}

}