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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBlanceAlignmentService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBlanceAlignment;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 会员账户余额对齐Controller
 * @author lzb
 * @version 2015-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerBlanceAlignment")
public class CustomerBlanceAlignmentController extends BaseController {

	@Autowired
	private CustomerBlanceAlignmentService customerBlanceAlignmentService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	
	@ModelAttribute
	public CustomerBlanceAlignment get(@RequestParam(required=false) String id) {
		CustomerBlanceAlignment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerBlanceAlignmentService.get(id);
		}
		if (entity == null){
			entity = new CustomerBlanceAlignment();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerBlanceAlignment:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerBlanceAlignment customerBlanceAlignment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerBlanceAlignment> page = customerBlanceAlignmentService.findPage(new Page<CustomerBlanceAlignment>(request, response), customerBlanceAlignment); 
		model.addAttribute("page", page);
		return "modules/customer/customerBlanceAlignmentList";
	}

	@RequiresPermissions("customer:customerBlanceAlignment:view")
	@RequestMapping(value = "form")
	public String form(CustomerBlanceAlignment customerBlanceAlignment, Model model) {
		model.addAttribute("customerBlanceAlignment", customerBlanceAlignment);
		return "modules/customer/customerBlanceAlignmentForm";
	}

	@RequiresPermissions("customer:customerBlanceAlignment:edit")
	@RequestMapping(value = "save")
	public String save(CustomerBlanceAlignment customerBlanceAlignment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerBlanceAlignment)){
			return form(customerBlanceAlignment, model);
		}
		customerBlanceAlignmentService.save(customerBlanceAlignment);
		addMessage(redirectAttributes, "保存会员账户余额对齐成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBlanceAlignment/?repage";
	}
	
	@RequiresPermissions("customer:customerBlanceAlignment:edit")
	@RequestMapping(value = "alignment")
	public String alignment(CustomerBlanceAlignment customerBlanceAlignment, Model model, RedirectAttributes redirectAttributes) {
		customerBlanceAlignment = this.get(customerBlanceAlignment.getId());
		CustomerAccount account = customerAccountService.get(customerBlanceAlignment.getAccountId());
		AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(account.getPlatformUserNo());
		String message = customerBlanceAlignmentService.alignment(customerBlanceAlignment, account, accountInfoResp);
		addMessage(redirectAttributes, message);
		return "redirect:"+Global.getAdminPath()+"/customer/customerBlanceAlignment/list";
	}
	
	@RequiresPermissions("customer:customerBlanceAlignment:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerBlanceAlignment customerBlanceAlignment, RedirectAttributes redirectAttributes) {
		customerBlanceAlignmentService.delete(customerBlanceAlignment);
		addMessage(redirectAttributes, "删除会员账户余额对齐成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBlanceAlignment/?repage";
	}

}