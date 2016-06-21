/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.web;

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
import com.thinkgem.jeesite.modules.entity.CustomerAddress;
import com.thinkgem.jeesite.modules.integral.service.CustomerAddressService;

/**
 * 花生乐园用户地址Controller
 * @author lizibo
 * @version 2015-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/integral/customerAddress")
public class CustomerAddressController extends BaseController {

	@Autowired
	private CustomerAddressService customerAddressService;
	
	@ModelAttribute
	public CustomerAddress get(@RequestParam(required=false) String id) {
		CustomerAddress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerAddressService.get(id);
		}
		if (entity == null){
			entity = new CustomerAddress();
		}
		return entity;
	}
	
	@RequiresPermissions("integral:customerAddress:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerAddress customerAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerAddress> page = customerAddressService.findPage(new Page<CustomerAddress>(request, response), customerAddress); 
		model.addAttribute("page", page);
		return "modules/integral/customerAddressList";
	}

	@RequiresPermissions("integral:customerAddress:view")
	@RequestMapping(value = "form")
	public String form(CustomerAddress customerAddress, Model model) {
		model.addAttribute("customerAddress", customerAddress);
		return "modules/integral/customerAddressForm";
	}

	@RequiresPermissions("integral:customerAddress:edit")
	@RequestMapping(value = "save")
	public String save(CustomerAddress customerAddress, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerAddress)){
			return form(customerAddress, model);
		}
		customerAddressService.save(customerAddress);
		addMessage(redirectAttributes, "保存用户地址成功");
		return "redirect:"+Global.getAdminPath()+"/integral/customerAddress/?repage";
	}
	
	@RequiresPermissions("integral:customerAddress:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerAddress customerAddress, RedirectAttributes redirectAttributes) {
		customerAddressService.delete(customerAddress);
		addMessage(redirectAttributes, "删除用户地址成功");
		return "redirect:"+Global.getAdminPath()+"/integral/customerAddress/?repage";
	}

}