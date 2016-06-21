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
import com.thinkgem.jeesite.modules.customer.service.CustomerBankCardService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.customer.service.CustomerCarService;
import com.thinkgem.jeesite.modules.customer.service.CustomerCreditAuthService;
import com.thinkgem.jeesite.modules.customer.service.CustomerHousingService;
import com.thinkgem.jeesite.modules.customer.service.CustomerWorkService;
import com.thinkgem.jeesite.modules.entity.CustomerBankCard;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.CustomerCar;
import com.thinkgem.jeesite.modules.entity.CustomerCreditAuth;
import com.thinkgem.jeesite.modules.entity.CustomerHousing;
import com.thinkgem.jeesite.modules.entity.CustomerWork;

/**
 * 会员信用认证信息Controller
 * @author ydt
 * @version 2015-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerCreditAuth")
public class CustomerCreditAuthController extends BaseController {

	@Autowired
	private CustomerCreditAuthService customerCreditAuthService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private CustomerWorkService customerWorkService;
	@Autowired
	private CustomerHousingService customerHousingService;
	@Autowired
	private CustomerCarService customerCarService;
	@Autowired
	private CustomerBankCardService customerBankCardService;
	
	@ModelAttribute
	public CustomerCreditAuth get(@RequestParam(required=false) String id) {
		CustomerCreditAuth entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerCreditAuthService.get(id);
		}
		if (entity == null){
			entity = new CustomerCreditAuth();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerCreditAuth:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerCreditAuth customerCreditAuth, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerCreditAuth> page = customerCreditAuthService.findPage(new Page<CustomerCreditAuth>(request, response), customerCreditAuth); 
		model.addAttribute("page", page);
		return "modules/customer/customerCreditAuthList";
	}

	@RequiresPermissions("customer:customerCreditAuth:view")
	@RequestMapping(value = "form")
	public String form(CustomerCreditAuth customerCreditAuth, Model model) {
		model.addAttribute("customerCreditAuth", customerCreditAuth);
		return "modules/customer/customerCreditAuthForm";
	}

	@RequiresPermissions("customer:customerCreditAuth:view")
	@RequestMapping(value = "formBase")
	public String formBase(CustomerCreditAuth customerCreditAuth, Model model) {
		CustomerBase customerBase = customerBaseService.getByAccountId(customerCreditAuth.getAccountId());
		model.addAttribute("customerBase", customerBase);
		if(customerCreditAuth.getPersonScore() == null) {
			customerCreditAuth.setPersonScore(customerBase.getAgeCreditAuthScore());
		}
		model.addAttribute("customerCreditAuth", customerCreditAuth);
		return "modules/customer/customerCreditAuthFormBase";
	}

	@RequiresPermissions("customer:customerCreditAuth:view")
	@RequestMapping(value = "formIdentity")
	public String formIdentity(CustomerCreditAuth customerCreditAuth, Model model) {
		CustomerBase customerBase = customerBaseService.getByAccountId(customerCreditAuth.getAccountId());
		model.addAttribute("customerBase", customerBase);
		if(customerCreditAuth.getIdentityScore() == null) {
			customerCreditAuth.setIdentityScore(customerBase.getIdentityScore());
		}
		model.addAttribute("customerCreditAuth", customerCreditAuth);
		return "modules/customer/customerCreditAuthFormIdentity";
	}

	@RequiresPermissions("customer:customerCreditAuth:view")
	@RequestMapping(value = "formEducation")
	public String formEducation(CustomerCreditAuth customerCreditAuth, Model model) {
		CustomerBase customerBase = customerBaseService.getByAccountId(customerCreditAuth.getAccountId());
		model.addAttribute("customerBase", customerBase);
		model.addAttribute("customerCreditAuth", customerCreditAuth);
		return "modules/customer/customerCreditAuthFormEducation";
	}

	@RequiresPermissions("customer:customerCreditAuth:view")
	@RequestMapping(value = "formAddress")
	public String formAddress(CustomerCreditAuth customerCreditAuth, Model model) {
		CustomerBase customerBase = customerBaseService.getByAccountId(customerCreditAuth.getAccountId());
		model.addAttribute("customerBase", customerBase);
		model.addAttribute("customerCreditAuth", customerCreditAuth);
		return "modules/customer/customerCreditAuthFormAddress";
	}

	@RequiresPermissions("customer:customerCreditAuth:view")
	@RequestMapping(value = "formIncome")
	public String formIncome(CustomerCreditAuth customerCreditAuth, Model model) {
		CustomerWork customerWork = customerWorkService.getByCustomerId(customerCreditAuth.getCustomerId());
		model.addAttribute("customerWork", customerWork);
		model.addAttribute("customerCreditAuth", customerCreditAuth);
		return "modules/customer/customerCreditAuthFormIncome";
	}

	@RequiresPermissions("customer:customerCreditAuth:view")
	@RequestMapping(value = "formHousing")
	public String formHousing(CustomerCreditAuth customerCreditAuth, Model model) {
		CustomerHousing customerHousing = customerHousingService.getByCustomerId(customerCreditAuth.getCustomerId());
		model.addAttribute("customerHousing", customerHousing);
//		customerCreditAuth.setHousingScore(customerHousing.getHousingScore());
		model.addAttribute("customerCreditAuth", customerCreditAuth);
		return "modules/customer/customerCreditAuthFormHousing";
	}

	@RequiresPermissions("customer:customerCreditAuth:view")
	@RequestMapping(value = "formCar")
	public String formCar(CustomerCreditAuth customerCreditAuth, Model model) {
		CustomerCar customerCar = customerCarService.getByCustomerId(customerCreditAuth.getCustomerId());
		model.addAttribute("customerCar", customerCar);
		model.addAttribute("customerCreditAuth", customerCreditAuth);
		return "modules/customer/customerCreditAuthFormCar";
	}

	@RequiresPermissions("customer:customerCreditAuth:view")
	@RequestMapping(value = "formCreditCard")
	public String formCreditCard(CustomerCreditAuth customerCreditAuth, Model model) {
		CustomerBase customerBase = customerBaseService.getByAccountId(customerCreditAuth.getAccountId());
		model.addAttribute("customerBase", customerBase);
		model.addAttribute("customerCreditAuth", customerCreditAuth);
		return "modules/customer/customerCreditAuthFormCreditCard";
	}

	@RequiresPermissions("customer:customerCreditAuth:view")
	@RequestMapping(value = "formCreditReport")
	public String formCreditReport(CustomerCreditAuth customerCreditAuth, Model model) {
		CustomerBankCard customerBankCard = customerBankCardService.getByAccountId(customerCreditAuth.getAccountId());
		model.addAttribute("customerBankCard", customerBankCard);
		model.addAttribute("customerCreditAuth", customerCreditAuth);
		return "modules/customer/customerCreditAuthFormCreditReport";
	}

	@RequiresPermissions("customer:customerCreditAuth:edit")
	@RequestMapping(value = "save")
	public String save(CustomerCreditAuth customerCreditAuth, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerCreditAuth)){
			return form(customerCreditAuth, model);
		}
		customerCreditAuthService.save(customerCreditAuth);
		addMessage(redirectAttributes, "保存会员信用认证信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerCreditAuth/?repage";
	}
	
	@RequiresPermissions("customer:customerCreditAuth:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerCreditAuth customerCreditAuth, RedirectAttributes redirectAttributes) {
		customerCreditAuthService.delete(customerCreditAuth);
		addMessage(redirectAttributes, "删除会员信用认证信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerCreditAuth/?repage";
	}

}