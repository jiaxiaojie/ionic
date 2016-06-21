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
import com.thinkgem.jeesite.modules.entity.CustomerGoldCoinHis;
import com.thinkgem.jeesite.modules.customer.service.CustomerGoldCoinHisService;

/**
 * 会员代币变更流水Controller
 * @author ydt
 * @version 2015-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerGoldCoinHis")
public class CustomerGoldCoinHisController extends BaseController {

	@Autowired
	private CustomerGoldCoinHisService customerGoldCoinHisService;
	
	@ModelAttribute
	public CustomerGoldCoinHis get(@RequestParam(required=false) String id) {
		CustomerGoldCoinHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerGoldCoinHisService.get(id);
		}
		if (entity == null){
			entity = new CustomerGoldCoinHis();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerGoldCoinHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerGoldCoinHis customerGoldCoinHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerGoldCoinHis> page = customerGoldCoinHisService.findPage(new Page<CustomerGoldCoinHis>(request, response), customerGoldCoinHis); 
		model.addAttribute("page", page);
		return "modules/customer/customerGoldCoinHisList";
	}

	@RequiresPermissions("customer:customerGoldCoinHis:view")
	@RequestMapping(value = "form")
	public String form(CustomerGoldCoinHis customerGoldCoinHis, Model model) {
		model.addAttribute("customerGoldCoinHis", customerGoldCoinHis);
		return "modules/customer/customerGoldCoinHisForm";
	}

	@RequiresPermissions("customer:customerGoldCoinHis:edit")
	@RequestMapping(value = "save")
	public String save(CustomerGoldCoinHis customerGoldCoinHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerGoldCoinHis)){
			return form(customerGoldCoinHis, model);
		}
		customerGoldCoinHisService.save(customerGoldCoinHis);
		addMessage(redirectAttributes, "保存会员代币变更流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerGoldCoinHis/?repage";
	}
	
	@RequiresPermissions("customer:customerGoldCoinHis:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerGoldCoinHis customerGoldCoinHis, RedirectAttributes redirectAttributes) {
		customerGoldCoinHisService.delete(customerGoldCoinHis);
		addMessage(redirectAttributes, "删除会员代币变更流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerGoldCoinHis/?repage";
	}

}