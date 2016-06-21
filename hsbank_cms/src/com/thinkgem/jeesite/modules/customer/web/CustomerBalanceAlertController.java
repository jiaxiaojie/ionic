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
import com.thinkgem.jeesite.modules.entity.CustomerBalanceAlert;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceAlertService;

/**
 * 账户余额警戒Controller
 * @author huangyuchen
 * @version 2016-04-14
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerBalanceAlert")
public class CustomerBalanceAlertController extends BaseController {

	@Autowired
	private CustomerBalanceAlertService customerBalanceAlertService;
	
	@ModelAttribute
	public CustomerBalanceAlert get(@RequestParam(required=false) String id) {
		CustomerBalanceAlert entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerBalanceAlertService.get(id);
		}
		if (entity == null){
			entity = new CustomerBalanceAlert();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerBalanceAlert:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerBalanceAlert customerBalanceAlert, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerBalanceAlert> page = customerBalanceAlertService.findPage(new Page<CustomerBalanceAlert>(request, response), customerBalanceAlert); 
		model.addAttribute("page", page);
		return "modules/customer/customerBalanceAlertList";
	}

	@RequiresPermissions("customer:customerBalanceAlert:view")
	@RequestMapping(value = "form")
	public String form(CustomerBalanceAlert customerBalanceAlert, Model model) {
		model.addAttribute("customerBalanceAlert", customerBalanceAlert);
		return "modules/customer/customerBalanceAlertForm";
		
	}
	@RequiresPermissions("customer:customerBalanceAlert:edit")
	@RequestMapping(value = "save")
	public String save(CustomerBalanceAlert customerBalanceAlert, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerBalanceAlert)){
			return form(customerBalanceAlert, model);
		}
		customerBalanceAlertService.save(customerBalanceAlert);
		addMessage(redirectAttributes, "保存账户余额警戒成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBalanceAlert/?repage";
	}
	
	@RequiresPermissions("customer:customerBalanceAlert:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerBalanceAlert customerBalanceAlert, RedirectAttributes redirectAttributes) {
		customerBalanceAlertService.delete(customerBalanceAlert);
		addMessage(redirectAttributes, "删除账户余额警戒成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBalanceAlert/?repage";
	}

}