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
import com.thinkgem.jeesite.modules.customer.service.CustomerShakeActivityService;
import com.thinkgem.jeesite.modules.entity.CustomerShakeActivity;

/**
 * 摇一摇活动Controller
 * @author ydt
 * @version 2015-09-10
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerShakeActivity")
public class CustomerShakeActivityController extends BaseController {

	@Autowired
	private CustomerShakeActivityService customerShakeActivityService;
	
	@ModelAttribute
	public CustomerShakeActivity get(@RequestParam(required=false) String id) {
		CustomerShakeActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerShakeActivityService.get(id);
		}
		if (entity == null){
			entity = new CustomerShakeActivity();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerShakeActivity:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerShakeActivity customerShakeActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerShakeActivity> page = customerShakeActivityService.findPage(new Page<CustomerShakeActivity>(request, response), customerShakeActivity); 
		model.addAttribute("page", page);
		return "modules/customer/customerShakeActivityList";
	}

	@RequiresPermissions("customer:customerShakeActivity:view")
	@RequestMapping(value = "form")
	public String form(CustomerShakeActivity customerShakeActivity, Model model) {
		model.addAttribute("customerShakeActivity", customerShakeActivity);
		return "modules/customer/customerShakeActivityForm";
	}
	
	@RequiresPermissions("customer:customerShakeActivity:view")
	@RequestMapping(value = {"superlist", ""})
	public String superlist(CustomerShakeActivity customerShakeActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerShakeActivity> page = customerShakeActivityService.findSuperPage(new Page<CustomerShakeActivity>(request, response), customerShakeActivity); 
		model.addAttribute("page", page);
		return "modules/customer/customerShakeActivityList";
	}

	/**
	 * 赠送投资券
	 * @param customerShakeActivity
	 * @param model
	 * @return
	 */
	@RequiresPermissions("customer:customerShakeActivity:edit")
	@RequestMapping(value = "give")
	public String give(long id, RedirectAttributes redirectAttributes) {
		String message = customerShakeActivityService.give(id);
		addMessage(redirectAttributes, message);
		return "redirect:"+Global.getAdminPath()+"/customer/customerShakeActivity/superlist";
	}

	@RequiresPermissions("customer:customerShakeActivity:edit")
	@RequestMapping(value = "save")
	public String save(CustomerShakeActivity customerShakeActivity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerShakeActivity)){
			return form(customerShakeActivity, model);
		}
		customerShakeActivityService.save(customerShakeActivity);
		addMessage(redirectAttributes, "保存摇一摇活动成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerShakeActivity/superlist";
	}
	
	@RequiresPermissions("customer:customerShakeActivity:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerShakeActivity customerShakeActivity, RedirectAttributes redirectAttributes) {
		customerShakeActivityService.delete(customerShakeActivity);
		addMessage(redirectAttributes, "删除摇一摇活动成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerShakeActivity/superlist";
	}

}