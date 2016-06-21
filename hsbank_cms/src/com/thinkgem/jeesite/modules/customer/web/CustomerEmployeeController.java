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
import com.thinkgem.jeesite.modules.entity.CustomerEmployee;
import com.thinkgem.jeesite.modules.customer.service.CustomerEmployeeService;

/**
 * 员工信息Controller
 * @author ydt
 * @version 2015-09-15
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerEmployee")
public class CustomerEmployeeController extends BaseController {

	@Autowired
	private CustomerEmployeeService customerEmployeeService;
	
	@ModelAttribute
	public CustomerEmployee get(@RequestParam(required=false) String id) {
		CustomerEmployee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerEmployeeService.get(id);
		}
		if (entity == null){
			entity = new CustomerEmployee();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerEmployee:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerEmployee customerEmployee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerEmployee> page = customerEmployeeService.findPage(new Page<CustomerEmployee>(request, response), customerEmployee); 
		model.addAttribute("page", page);
		return "modules/customer/customerEmployeeList";
	}

	@RequiresPermissions("customer:customerEmployee:view")
	@RequestMapping(value = "form")
	public String form(CustomerEmployee customerEmployee, Model model) {
		model.addAttribute("customerEmployee", customerEmployee);
		return "modules/customer/customerEmployeeForm";
	}

	@RequiresPermissions("customer:customerEmployee:edit")
	@RequestMapping(value = "save")
	public String save(CustomerEmployee customerEmployee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerEmployee)){
			return form(customerEmployee, model);
		}
		customerEmployeeService.save(customerEmployee);
		addMessage(redirectAttributes, "保存员工信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerEmployee/?repage";
	}
	
	@RequiresPermissions("customer:customerEmployee:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerEmployee customerEmployee, RedirectAttributes redirectAttributes) {
		customerEmployeeService.delete(customerEmployee);
		addMessage(redirectAttributes, "删除员工信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerEmployee/?repage";
	}

}