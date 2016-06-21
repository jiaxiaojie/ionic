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
import com.thinkgem.jeesite.modules.entity.CustomerFinance;
import com.thinkgem.jeesite.modules.customer.service.CustomerFinanceService;

/**
 * 会员财务信息Controller
 * @author ydt
 * @version 2015-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerFinance")
public class CustomerFinanceController extends BaseController {

	@Autowired
	private CustomerFinanceService customerFinanceService;
	
	@ModelAttribute
	public CustomerFinance get(@RequestParam(required=false) String id) {
		CustomerFinance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerFinanceService.get(id);
		}
		if (entity == null){
			entity = new CustomerFinance();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerFinance:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerFinance customerFinance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerFinance> page = customerFinanceService.findPage(new Page<CustomerFinance>(request, response), customerFinance); 
		model.addAttribute("page", page);
		return "modules/customer/customerFinanceList";
	}

	@RequiresPermissions("customer:customerFinance:view")
	@RequestMapping(value = "form")
	public String form(CustomerFinance customerFinance, Model model) {
		model.addAttribute("customerFinance", customerFinance);
		return "modules/customer/customerFinanceForm";
	}

	@RequiresPermissions("customer:customerFinance:view")
	@RequestMapping(value = "formByCustomerId")
	public String formByCustomerId(CustomerFinance customerFinance, Model model) {
		customerFinance = customerFinanceService.getByCustomerId(customerFinance.getCustomerId());
		model.addAttribute("customerFinance", customerFinance);
		return "modules/customer/customerFinanceForm";
	}

	@RequiresPermissions("customer:customerFinance:edit")
	@RequestMapping(value = "save")
	public String save(CustomerFinance customerFinance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerFinance)){
			return form(customerFinance, model);
		}
		customerFinanceService.save(customerFinance);
		addMessage(redirectAttributes, "保存会员财务信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerAccount/?repage";
	}
	
	@RequiresPermissions("customer:customerFinance:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerFinance customerFinance, RedirectAttributes redirectAttributes) {
		customerFinanceService.delete(customerFinance);
		addMessage(redirectAttributes, "删除会员财务信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerFinance/?repage";
	}

}