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
import com.thinkgem.jeesite.modules.entity.CustomerRateTicket;
import com.thinkgem.jeesite.modules.customer.service.CustomerRateTicketService;

/**
 * 会员加息券清单Controller
 * @author ydt
 * @version 2016-04-05
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerRateTicket")
public class CustomerRateTicketController extends BaseController {

	@Autowired
	private CustomerRateTicketService customerRateTicketService;
	
	@ModelAttribute
	public CustomerRateTicket get(@RequestParam(required=false) String id) {
		CustomerRateTicket entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerRateTicketService.get(id);
		}
		if (entity == null){
			entity = new CustomerRateTicket();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerRateTicket:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerRateTicket customerRateTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerRateTicket> page = customerRateTicketService.findPage(new Page<CustomerRateTicket>(request, response), customerRateTicket); 
		model.addAttribute("page", page);
		return "modules/customer/customerRateTicketList";
	}

	@RequiresPermissions("customer:customerRateTicket:view")
	@RequestMapping(value = "form")
	public String form(CustomerRateTicket customerRateTicket, Model model) {
		model.addAttribute("customerRateTicket", customerRateTicket);
		return "modules/customer/customerRateTicketForm";
	}

	@RequiresPermissions("customer:customerRateTicket:edit")
	@RequestMapping(value = "save")
	public String save(CustomerRateTicket customerRateTicket, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerRateTicket)){
			return form(customerRateTicket, model);
		}
		customerRateTicketService.save(customerRateTicket);
		addMessage(redirectAttributes, "保存会员加息券清单成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerRateTicket/?repage";
	}
	
	@RequiresPermissions("customer:customerRateTicket:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerRateTicket customerRateTicket, RedirectAttributes redirectAttributes) {
		customerRateTicketService.delete(customerRateTicket);
		addMessage(redirectAttributes, "删除会员加息券清单成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerRateTicket/?repage";
	}

}