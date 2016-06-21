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
import com.thinkgem.jeesite.modules.entity.CustomerBaseHis;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseHisService;

/**
 * 会员基本信息变更历史Controller
 * @author ydt
 * @version 2015-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerBaseHis")
public class CustomerBaseHisController extends BaseController {

	@Autowired
	private CustomerBaseHisService customerBaseHisService;
	
	@ModelAttribute
	public CustomerBaseHis get(@RequestParam(required=false) String id) {
		CustomerBaseHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerBaseHisService.get(id);
		}
		if (entity == null){
			entity = new CustomerBaseHis();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerBaseHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerBaseHis customerBaseHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerBaseHis> page = customerBaseHisService.findPage(new Page<CustomerBaseHis>(request, response), customerBaseHis); 
		model.addAttribute("page", page);
		model.addAttribute("customerId", customerBaseHis.getCustomerBase().getCustomerId());
		return "modules/customer/customerBaseHisList";
	}

	@RequiresPermissions("customer:customerBaseHis:view")
	@RequestMapping(value = "form")
	public String form(CustomerBaseHis customerBaseHis, Model model) {
		model.addAttribute("customerBaseHis", customerBaseHis);
		return "modules/customer/customerBaseHisForm";
	}

	@RequiresPermissions("customer:customerBaseHis:edit")
	@RequestMapping(value = "save")
	public String save(CustomerBaseHis customerBaseHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerBaseHis)){
			return form(customerBaseHis, model);
		}
		customerBaseHisService.save(customerBaseHis);
		addMessage(redirectAttributes, "保存会员基本信息变更历史成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBaseHis/?repage";
	}
	
	@RequiresPermissions("customer:customerBaseHis:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerBaseHis customerBaseHis, RedirectAttributes redirectAttributes) {
		customerBaseHisService.delete(customerBaseHis);
		addMessage(redirectAttributes, "删除会员基本信息变更历史成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBaseHis/?repage";
	}

}