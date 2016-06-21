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
import com.thinkgem.jeesite.modules.entity.CustomerCar;
import com.thinkgem.jeesite.modules.customer.service.CustomerCarService;

/**
 * 会员车辆信息Controller
 * @author ydt
 * @version 2015-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerCar")
public class CustomerCarController extends BaseController {

	@Autowired
	private CustomerCarService customerCarService;
	
	@ModelAttribute
	public CustomerCar get(@RequestParam(required=false) String id) {
		CustomerCar entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerCarService.get(id);
		}
		if (entity == null){
			entity = new CustomerCar();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerCar:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerCar customerCar, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerCar> page = customerCarService.findPage(new Page<CustomerCar>(request, response), customerCar); 
		model.addAttribute("page", page);
		return "modules/customer/customerCarList";
	}

	@RequiresPermissions("customer:customerCar:view")
	@RequestMapping(value = "form")
	public String form(CustomerCar customerCar, Model model) {
		model.addAttribute("customerCar", customerCar);
		return "modules/customer/customerCarForm";
	}

	@RequiresPermissions("customer:customerCar:view")
	@RequestMapping(value = "formByCustomerId")
	public String formByCustomerId(CustomerCar customerCar, Model model) {
		customerCar = customerCarService.getByCustomerId(customerCar.getCustomerId());
		model.addAttribute("customerCar", customerCar);
		return "modules/customer/customerCarForm";
	}

	@RequiresPermissions("customer:customerCar:edit")
	@RequestMapping(value = "save")
	public String save(CustomerCar customerCar, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerCar)){
			return form(customerCar, model);
		}
		customerCarService.save(customerCar);
		addMessage(redirectAttributes, "保存会员车辆信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerAccount/?repage";
	}
	
	@RequiresPermissions("customer:customerCar:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerCar customerCar, RedirectAttributes redirectAttributes) {
		customerCarService.delete(customerCar);
		addMessage(redirectAttributes, "删除会员车辆信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerCar/?repage";
	}

}