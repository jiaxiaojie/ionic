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
import com.thinkgem.jeesite.modules.entity.CustomerRechargeHis;
import com.thinkgem.jeesite.modules.customer.service.CustomerRechargeHisService;

/**
 * 会员充值记录Controller
 * @author yangtao
 * @version 2015-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerRechargeHis")
public class CustomerRechargeHisController extends BaseController {

	@Autowired
	private CustomerRechargeHisService customerRechargeHisService;
	
	@ModelAttribute
	public CustomerRechargeHis get(@RequestParam(required=false) String id) {
		CustomerRechargeHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerRechargeHisService.get(id);
		}
		if (entity == null){
			entity = new CustomerRechargeHis();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerRechargeHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerRechargeHis customerRechargeHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerRechargeHis> page = customerRechargeHisService.findPage(new Page<CustomerRechargeHis>(request, response), customerRechargeHis); 
		model.addAttribute("page", page);
		return "modules/customer/customerRechargeHisList";
	}

	@RequiresPermissions("customer:customerRechargeHis:view")
	@RequestMapping(value = "form")
	public String form(CustomerRechargeHis customerRechargeHis, Model model) {
		model.addAttribute("customerRechargeHis", customerRechargeHis);
		return "modules/customer/customerRechargeHisForm";
	}

	@RequiresPermissions("customer:customerRechargeHis:edit")
	@RequestMapping(value = "save")
	public String save(CustomerRechargeHis customerRechargeHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerRechargeHis)){
			return form(customerRechargeHis, model);
		}
		customerRechargeHisService.save(customerRechargeHis);
		addMessage(redirectAttributes, "保存会员充值记录成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerRechargeHis/?repage";
	}
	
	@RequiresPermissions("customer:customerRechargeHis:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerRechargeHis customerRechargeHis, RedirectAttributes redirectAttributes) {
		customerRechargeHisService.delete(customerRechargeHis);
		addMessage(redirectAttributes, "删除会员充值记录成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerRechargeHis/?repage";
	}

}