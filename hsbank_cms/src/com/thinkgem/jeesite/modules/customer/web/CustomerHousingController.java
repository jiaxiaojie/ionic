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
import com.thinkgem.jeesite.modules.entity.CustomerHousing;
import com.thinkgem.jeesite.modules.customer.service.CustomerHousingService;

/**
 * 会员房产信息Controller
 * @author ydt
 * @version 2015-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerHousing")
public class CustomerHousingController extends BaseController {

	@Autowired
	private CustomerHousingService customerHousingService;
	
	@ModelAttribute
	public CustomerHousing get(@RequestParam(required=false) String id) {
		CustomerHousing entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerHousingService.get(id);
		}
		if (entity == null){
			entity = new CustomerHousing();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerHousing:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerHousing customerHousing, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerHousing> page = customerHousingService.findPage(new Page<CustomerHousing>(request, response), customerHousing); 
		model.addAttribute("page", page);
		return "modules/customer/customerHousingList";
	}

	@RequiresPermissions("customer:customerHousing:view")
	@RequestMapping(value = "form")
	public String form(CustomerHousing customerHousing, Model model) {
		model.addAttribute("customerHousing", customerHousing);
		return "modules/customer/customerHousingForm";
	}

	@RequiresPermissions("customer:customerHousing:view")
	@RequestMapping(value = "formByCustomerId")
	public String formByCustomerId(CustomerHousing customerHousing, Model model) {
		customerHousing = customerHousingService.getByCustomerId(customerHousing.getCustomerId());
		model.addAttribute("customerHousing", customerHousing);
		return "modules/customer/customerHousingForm";
	}

	@RequiresPermissions("customer:customerHousing:edit")
	@RequestMapping(value = "save")
	public String save(CustomerHousing customerHousing, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerHousing)){
			return form(customerHousing, model);
		}
		customerHousingService.save(customerHousing);
		addMessage(redirectAttributes, "保存会员房产信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerAccount/?repage";
	}
	
	@RequiresPermissions("customer:customerHousing:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerHousing customerHousing, RedirectAttributes redirectAttributes) {
		customerHousingService.delete(customerHousing);
		addMessage(redirectAttributes, "删除会员房产信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerHousing/?repage";
	}

}