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
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceHisService;

/**
 * 会员账户余额变更流水Controller
 * @author ydt
 * @version 2015-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerBalanceHis")
public class CustomerBalanceHisController extends BaseController {

	@Autowired
	private CustomerBalanceHisService customerBalanceHisService;
	
	@ModelAttribute
	public CustomerBalanceHis get(@RequestParam(required=false) String id) {
		CustomerBalanceHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerBalanceHisService.get(id);
		}
		if (entity == null){
			entity = new CustomerBalanceHis();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerBalanceHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerBalanceHis customerBalanceHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerBalanceHis> page = customerBalanceHisService.findPage(new Page<CustomerBalanceHis>(request, response), customerBalanceHis); 
		model.addAttribute("page", page);
		return "modules/customer/customerBalanceHisList";
	}

	@RequiresPermissions("customer:customerBalanceHis:view")
	@RequestMapping(value = "form")
	public String form(CustomerBalanceHis customerBalanceHis, Model model) {
		model.addAttribute("customerBalanceHis", customerBalanceHis);
		return "modules/customer/customerBalanceHisForm";
	}

	@RequiresPermissions("customer:customerBalanceHis:edit")
	@RequestMapping(value = "save")
	public String save(CustomerBalanceHis customerBalanceHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerBalanceHis)){
			return form(customerBalanceHis, model);
		}
		customerBalanceHisService.save(customerBalanceHis);
		addMessage(redirectAttributes, "保存会员账户余额变更流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBalanceHis/?repage";
	}
	
	@RequiresPermissions("customer:customerBalanceHis:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerBalanceHis customerBalanceHis, RedirectAttributes redirectAttributes) {
		customerBalanceHisService.delete(customerBalanceHis);
		addMessage(redirectAttributes, "删除会员账户余额变更流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerBalanceHis/?repage";
	}

}