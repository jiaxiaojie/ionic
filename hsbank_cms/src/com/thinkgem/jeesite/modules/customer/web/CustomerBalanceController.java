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
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;

/**
 * 会员账户余额汇总Controller
 * @author ydt
 * @version 2015-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerBalance")
public class CustomerBalanceController extends BaseController {

	@Autowired
	private CustomerBalanceService customerBalanceService;
	
	@ModelAttribute
	public CustomerBalance get(@RequestParam(required=false) String id) {
		CustomerBalance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerBalanceService.get(id);
		}
		if (entity == null){
			entity = new CustomerBalance();
		}
		return entity;
	}

	@RequiresPermissions("customer:customerBalance:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerBalance customerBalance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerBalance> page = customerBalanceService.findPage(new Page<CustomerBalance>(request, response), customerBalance); 
		model.addAttribute("page", page);
		return "modules/customer/customerBalanceList";
	}

	@RequiresPermissions("customer:customerBalance:view")
	@RequestMapping(value = {"customerProjectView", ""})
	public String customerProjectView(CustomerBalance customerBalance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerBalance> page = customerBalanceService.findPage(new Page<CustomerBalance>(request, response), customerBalance); 
		model.addAttribute("page", page);
		return "modules/customer/customerProjectView";
	}

	@RequiresPermissions("customer:customerBalance:view")
	@RequestMapping(value = "form")
	public String form(CustomerBalance customerBalance, Model model) {
		model.addAttribute("customerBalance", customerBalance);
		return "modules/customer/customerBalanceForm";
	}

	@RequiresPermissions("customer:customerBalance:edit")
	@RequestMapping(value = "save")
	public String save(CustomerBalance customerBalance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerBalance)){
			return form(customerBalance, model);
		}
		customerBalanceService.save(customerBalance);
		addMessage(redirectAttributes, "保存会员账户余额汇总成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBalance/?repage";
	}
	
	@RequiresPermissions("customer:customerBalance:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerBalance customerBalance, RedirectAttributes redirectAttributes) {
		customerBalanceService.delete(customerBalance);
		addMessage(redirectAttributes, "删除会员账户余额汇总成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBalance/?repage";
	}

}