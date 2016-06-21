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
import com.thinkgem.jeesite.modules.entity.CustomerWork;
import com.thinkgem.jeesite.modules.customer.service.CustomerWorkService;

/**
 * 会员工作信息Controller
 * @author ydt
 * @version 2015-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerWork")
public class CustomerWorkController extends BaseController {

	@Autowired
	private CustomerWorkService customerWorkService;
	
	@ModelAttribute
	public CustomerWork get(@RequestParam(required=false) String id) {
		CustomerWork entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerWorkService.get(id);
		}
		if (entity == null){
			entity = new CustomerWork();
		}
		return entity;
	}

	@RequiresPermissions("customer:customerWork:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerWork customerWork, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerWork> page = customerWorkService.findPage(new Page<CustomerWork>(request, response), customerWork); 
		model.addAttribute("page", page);
		return "modules/customer/customerWorkList";
	}

	@RequiresPermissions("customer:customerWork:view")
	@RequestMapping(value = "form")
	public String form(CustomerWork customerWork, Model model) {
		model.addAttribute("customerWork", customerWork);
		return "modules/customer/customerWorkForm";
	}

	@RequiresPermissions("customer:customerWork:view")
	@RequestMapping(value = "formByCustomerId")
	public String formByCustomerId(CustomerWork customerWork, Model model) {
		customerWork = customerWorkService.getByCustomerId(customerWork.getCustomerId());
		model.addAttribute("customerWork", customerWork);
		return "modules/customer/customerWorkForm";
	}

	@RequiresPermissions("customer:customerWork:edit")
	@RequestMapping(value = "save")
	public String save(CustomerWork customerWork, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerWork)){
			return form(customerWork, model);
		}
		customerWorkService.save(customerWork);
		addMessage(redirectAttributes, "保存会员单位信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerAccount/?repage";
	}
	
	@RequiresPermissions("customer:customerWork:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerWork customerWork, RedirectAttributes redirectAttributes) {
		customerWorkService.delete(customerWork);
		addMessage(redirectAttributes, "删除会员单位信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerWork/?repage";
	}

}