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
import com.thinkgem.jeesite.modules.entity.CustomerIntegralHis;
import com.thinkgem.jeesite.modules.customer.service.CustomerIntegralHisService;

/**
 * 会员花生豆变更流水Controller
 * @author ydt
 * @version 2015-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerIntegralHis")
public class CustomerIntegralHisController extends BaseController {

	@Autowired
	private CustomerIntegralHisService customerIntegralHisService;
	
	@ModelAttribute
	public CustomerIntegralHis get(@RequestParam(required=false) String id) {
		CustomerIntegralHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerIntegralHisService.get(id);
		}
		if (entity == null){
			entity = new CustomerIntegralHis();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerIntegralHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerIntegralHis customerIntegralHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerIntegralHis> page = customerIntegralHisService.findPage(new Page<CustomerIntegralHis>(request, response), customerIntegralHis); 
		model.addAttribute("page", page);
		return "modules/customer/customerIntegralHisList";
	}

	@RequiresPermissions("customer:customerIntegralHis:view")
	@RequestMapping(value = "form")
	public String form(CustomerIntegralHis customerIntegralHis, Model model) {
		model.addAttribute("customerIntegralHis", customerIntegralHis);
		return "modules/customer/customerIntegralHisForm";
	}

	@RequiresPermissions("customer:customerIntegralHis:edit")
	@RequestMapping(value = "save")
	public String save(CustomerIntegralHis customerIntegralHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerIntegralHis)){
			return form(customerIntegralHis, model);
		}
		customerIntegralHisService.save(customerIntegralHis);
		addMessage(redirectAttributes, "保存会员花生豆变更流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerIntegralHis/?repage";
	}
	
	@RequiresPermissions("customer:customerIntegralHis:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerIntegralHis customerIntegralHis, RedirectAttributes redirectAttributes) {
		customerIntegralHisService.delete(customerIntegralHis);
		addMessage(redirectAttributes, "删除会员花生豆变更流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerIntegralHis/?repage";
	}

}