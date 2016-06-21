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
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.customer.service.CustomerClientTokenService;

/**
 * 客户端缓存信息Controller
 * @author lzb
 * @version 2015-10-14
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerClientToken")
public class CustomerClientTokenController extends BaseController {

	@Autowired
	private CustomerClientTokenService customerClientTokenService;
	
	@ModelAttribute
	public CustomerClientToken get(@RequestParam(required=false) String id) {
		CustomerClientToken entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerClientTokenService.get(id);
		}
		if (entity == null){
			entity = new CustomerClientToken();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerClientToken:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerClientToken customerClientToken, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerClientToken> page = customerClientTokenService.findPage(new Page<CustomerClientToken>(request, response), customerClientToken); 
		model.addAttribute("page", page);
		return "modules/customer/customerClientTokenList";
	}

	@RequiresPermissions("customer:customerClientToken:view")
	@RequestMapping(value = "form")
	public String form(CustomerClientToken customerClientToken, Model model) {
		model.addAttribute("customerClientToken", customerClientToken);
		return "modules/customer/customerClientTokenForm";
	}

	@RequiresPermissions("customer:customerClientToken:edit")
	@RequestMapping(value = "save")
	public String save(CustomerClientToken customerClientToken, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerClientToken)){
			return form(customerClientToken, model);
		}
		customerClientTokenService.save(customerClientToken);
		addMessage(redirectAttributes, "保存客户端缓存信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerClientToken/?repage";
	}
	
	@RequiresPermissions("customer:customerClientToken:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerClientToken customerClientToken, RedirectAttributes redirectAttributes) {
		customerClientTokenService.delete(customerClientToken);
		addMessage(redirectAttributes, "删除客户端缓存信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerClientToken/?repage";
	}

}