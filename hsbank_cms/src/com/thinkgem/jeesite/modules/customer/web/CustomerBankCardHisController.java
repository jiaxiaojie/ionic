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
import com.thinkgem.jeesite.modules.entity.CustomerBankCardHis;
import com.thinkgem.jeesite.modules.customer.service.CustomerBankCardHisService;

/**
 * 会员银行卡历史变更Controller
 * @author ydt
 * @version 2015-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerBankCardHis")
public class CustomerBankCardHisController extends BaseController {

	@Autowired
	private CustomerBankCardHisService customerBankCardHisService;
	
	@ModelAttribute
	public CustomerBankCardHis get(@RequestParam(required=false) String id) {
		CustomerBankCardHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerBankCardHisService.get(id);
		}
		if (entity == null){
			entity = new CustomerBankCardHis();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerBankCardHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerBankCardHis customerBankCardHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerBankCardHis> page = customerBankCardHisService.findPage(new Page<CustomerBankCardHis>(request, response), customerBankCardHis); 
		model.addAttribute("page", page);
		model.addAttribute("accountId", customerBankCardHis.getAccountId());
		return "modules/customer/customerBankCardHisList";
	}

	@RequiresPermissions("customer:customerBankCardHis:view")
	@RequestMapping(value = "form")
	public String form(CustomerBankCardHis customerBankCardHis, Model model) {
		model.addAttribute("customerBankCardHis", customerBankCardHis);
		return "modules/customer/customerBankCardHisForm";
	}

	@RequiresPermissions("customer:customerBankCardHis:edit")
	@RequestMapping(value = "save")
	public String save(CustomerBankCardHis customerBankCardHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerBankCardHis)){
			return form(customerBankCardHis, model);
		}
		customerBankCardHisService.save(customerBankCardHis);
		addMessage(redirectAttributes, "保存会员银行卡历史变更成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBankCardHis/?repage";
	}
	
	@RequiresPermissions("customer:customerBankCardHis:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerBankCardHis customerBankCardHis, RedirectAttributes redirectAttributes) {
		customerBankCardHisService.delete(customerBankCardHis);
		addMessage(redirectAttributes, "删除会员银行卡历史变更成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBankCardHis/?repage";
	}

}