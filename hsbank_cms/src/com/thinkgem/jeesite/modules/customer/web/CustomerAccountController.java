/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;

/**
 * 会员账号信息Controller
 * @author ydt
 * @version 2015-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerAccount")
public class CustomerAccountController extends BaseController {

	@Autowired
	private CustomerAccountService customerAccountService;
	
	@ModelAttribute
	public CustomerAccount get(@RequestParam(required=false) String id) {
		CustomerAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerAccountService.get(id);
		}
		if (entity == null){
			entity = new CustomerAccount();
		}
		return entity;
	}
	
	@ResponseBody
	@RequiresPermissions("customer:customerAccount:view")
	@RequestMapping(value = "queryById")
	public CustomerAccount queryById(String id){
		return get(id);
	}
	
	@RequiresPermissions("customer:customerAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerAccount customerAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerAccount> page = customerAccountService.findPage(new Page<CustomerAccount>(request, response), customerAccount); 
		model.addAttribute("page", page);
		return "modules/customer/customerAccountList";
	}

	@RequestMapping(value = "form")
	public String form(CustomerAccount customerAccount, Model model) {
		customerAccount.setDefaultValue();
		model.addAttribute("customerAccount", customerAccount);
		return "modules/customer/customerAccountForm";
	}

	@RequiresPermissions("customer:customerAccount:edit")
	@RequestMapping(value = "save")
	public String save(CustomerAccount customerAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerAccount)){
			return form(customerAccount, model);
		}
		if(!customerAccountService.checkAccountNameCanUse(customerAccount)) {
			addMessage(model, "数据验证失败：<br/>登录名已存在，请重新输入");
			return form(customerAccount, model);
		}
		// 如果新密码为空，则不更换密码
		/*if (StringUtils.isNotBlank(customerAccount.getResetPwdVal())) {
			customerAccount.setAccountPwd(SystemService.entryptPassword(customerAccount.getResetPwdVal()));
		}*/
		customerAccountService.save(customerAccount);
		addMessage(redirectAttributes, "保存会员账号信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerAccount/?repage";
	}
	
	@RequestMapping(value = "checkPlatformUserNoCanUse", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkPlatformUserNoCanUse(Long accountId, String platformUserNo) {
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setAccountId(accountId);
		customerAccount.setPlatformUserNo(platformUserNo);
		return customerAccountService.checkPlatformUserNoCanUse(customerAccount);
	}
	
	@RequestMapping(value = "checkAccountNameCanUse", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkAccountNameCanUse(Long accountId, String accountName) {
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setAccountId(accountId);
		customerAccount.setAccountName(accountName);
		return customerAccountService.checkAccountNameCanUse(customerAccount);
	}
	
	@RequiresPermissions("customer:customerAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerAccount customerAccount, RedirectAttributes redirectAttributes) {
		customerAccountService.delete(customerAccount);
		addMessage(redirectAttributes, "删除会员账号信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerAccount/?repage";
	}
	
	@RequiresPermissions("customer:customerAccount:view")
	@RequestMapping(value = "query")
	@ResponseBody
	public List<CustomerAccount> querySimpleList(String queryParas) {
		CustomerAccount qa=new CustomerAccount();
		if(queryParas==null){
			return new ArrayList<CustomerAccount>();
		}
		queryParas=queryParas.trim();
		qa.setQueryParas(queryParas);
		return customerAccountService.querySimpleList(qa);
	}
	
	@RequiresPermissions("customer:customerAccount:view")
	@RequestMapping("add")
	public String addAccount(Model model) {
		return "modules/customer/customerAccountAdd";
	}
	
	@RequiresPermissions("customer:customerAccount:edit")
	@RequestMapping("addSave")
	public String addAccountSave(String accountName, String customerName, String mobile, String email, String platformUserNo, String hasOpenThirdAccount, RedirectAttributes redirectAttributes) {
		customerAccountService.addAccount(accountName, customerName, mobile, email, platformUserNo, hasOpenThirdAccount);
		addMessage(redirectAttributes, "添加会员信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerAccount/add";
	}
}