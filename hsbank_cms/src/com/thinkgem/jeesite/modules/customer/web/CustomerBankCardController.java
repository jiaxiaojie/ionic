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
import com.thinkgem.jeesite.modules.entity.CustomerBankCard;
import com.thinkgem.jeesite.modules.customer.service.CustomerBankCardService;

/**
 * 会员银行卡信息Controller
 * @author ydt
 * @version 2015-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerBankCard")
public class CustomerBankCardController extends BaseController {

	@Autowired
	private CustomerBankCardService customerBankCardService;
	
	@ModelAttribute
	public CustomerBankCard get(@RequestParam(required=false) String id) {
		CustomerBankCard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerBankCardService.get(id);
		}
		if (entity == null){
			entity = new CustomerBankCard();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerBankCard:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerBankCard customerBankCard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerBankCard> page = customerBankCardService.findPage(new Page<CustomerBankCard>(request, response), customerBankCard); 
		model.addAttribute("page", page);
		return "modules/customer/customerBankCardList";
	}

	@RequiresPermissions("customer:customerBankCard:view")
	@RequestMapping(value = "form")
	public String form(CustomerBankCard customerBankCard, Model model) {
		model.addAttribute("customerBankCard", customerBankCard);
		return "modules/customer/customerBankCardForm";
	}

	@RequiresPermissions("customer:customerBankCard:view")
	@RequestMapping(value = "formByAccountId")
	public String formByAccountId(CustomerBankCard customerBankCard, Model model) {
		customerBankCard = customerBankCardService.getByAccountId(customerBankCard.getAccountId());
		model.addAttribute("customerBankCard", customerBankCard);
		return "modules/customer/customerBankCardForm";
	}

	@RequiresPermissions("customer:customerBankCard:edit")
	@RequestMapping(value = "save")
	public String save(CustomerBankCard customerBankCard, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerBankCard)){
			return form(customerBankCard, model);
		}
		customerBankCardService.save(customerBankCard);
		addMessage(redirectAttributes, "保存会员银行卡信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerAccount/?repage";
	}
	
	@RequiresPermissions("customer:customerBankCard:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerBankCard customerBankCard, RedirectAttributes redirectAttributes) {
		customerBankCardService.delete(customerBankCard);
		addMessage(redirectAttributes, "删除会员银行卡信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBankCard/?repage";
	}

}