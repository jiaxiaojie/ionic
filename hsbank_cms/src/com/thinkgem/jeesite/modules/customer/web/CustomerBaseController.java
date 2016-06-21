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
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;

/**
 * 会员基本信息Controller
 * @author ydt
 * @version 2015-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerBase")
public class CustomerBaseController extends BaseController {

	@Autowired
	private CustomerBaseService customerBaseService;
	
	@ModelAttribute
	public CustomerBase get(@RequestParam(required=false) String id) {
		CustomerBase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerBaseService.get(id);
		}
		if (entity == null){
			entity = new CustomerBase();
		}
		return entity;
	}

	@RequiresPermissions("customer:customerBase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerBase customerBase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerBase> page = customerBaseService.findPage(new Page<CustomerBase>(request, response), customerBase); 
		model.addAttribute("page", page);
		return "modules/customer/customerBaseList";
	}

	@RequiresPermissions("customer:customerBase:view")
	@RequestMapping(value = "form")
	public String form(CustomerBase customerBase, Model model) {
		model.addAttribute("customerBase", customerBase);
		return "modules/customer/customerBaseForm";
	}

	@RequiresPermissions("customer:customerBase:view")
	@RequestMapping(value = "formByAccountId")
	public String formByAccountId(CustomerBase customerBase, Model model) {
		customerBase = customerBaseService.getByAccountId(customerBase.getAccountId());
		customerBase.setDefaultValue();
		model.addAttribute("customerBase", customerBase);
		return "modules/customer/customerBaseForm";
	}

	@RequiresPermissions("customer:customerBase:edit")
	@RequestMapping(value = "save")
	public String save(CustomerBase customerBase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerBase)){
			return form(customerBase, model);
		}
		customerBaseService.save(customerBase);
		addMessage(redirectAttributes, "保存会员基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerAccount/?repage";
	}
	
	@RequiresPermissions("customer:customerBase:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerBase customerBase, RedirectAttributes redirectAttributes) {
		customerBaseService.delete(customerBase);
		addMessage(redirectAttributes, "删除会员基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBase/?repage";
	}

}