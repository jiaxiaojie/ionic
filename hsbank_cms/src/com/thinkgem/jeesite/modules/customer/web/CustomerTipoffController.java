/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.entity.CustomerTipoff;
import com.thinkgem.jeesite.modules.customer.service.CustomerTipoffService;

/**
 * 会员举报信息Controller
 * @author ydt
 * @version 2015-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerTipoff")
public class CustomerTipoffController extends BaseController {

	@Autowired
	private CustomerTipoffService customerTipoffService;
	
	@ModelAttribute
	public CustomerTipoff get(@RequestParam(required=false) String id) {
		CustomerTipoff entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerTipoffService.get(id);
		}
		if (entity == null){
			entity = new CustomerTipoff();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(CustomerTipoff customerTipoff, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerTipoff> page = customerTipoffService.findPage(new Page<CustomerTipoff>(request, response), customerTipoff); 
		model.addAttribute("page", page);
		return "modules/customer/customerTipoffList";
	}

	@RequestMapping(value = "form")
	public String form(CustomerTipoff customerTipoff, Model model) {
		model.addAttribute("customerTipoff", customerTipoff);
		return "modules/customer/customerTipoffForm";
	}

	@RequestMapping(value = "save")
	public String save(CustomerTipoff customerTipoff, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerTipoff)){
			return form(customerTipoff, model);
		}
		customerTipoffService.save(customerTipoff);
		addMessage(redirectAttributes, "保存会员举报信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerTipoff/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(CustomerTipoff customerTipoff, RedirectAttributes redirectAttributes) {
		customerTipoffService.delete(customerTipoff);
		addMessage(redirectAttributes, "删除会员举报信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerTipoff/?repage";
	}

}