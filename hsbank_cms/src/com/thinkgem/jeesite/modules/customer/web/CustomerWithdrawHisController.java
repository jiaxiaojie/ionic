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
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawHis;
import com.thinkgem.jeesite.modules.customer.service.CustomerWithdrawHisService;

/**
 * 会员体现记录表Controller
 * @author lzb
 * @version 2016-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerWithdrawHis")
public class CustomerWithdrawHisController extends BaseController {

	@Autowired
	private CustomerWithdrawHisService customerWithdrawHisService;
	
	@ModelAttribute
	public CustomerWithdrawHis get(@RequestParam(required=false) String id) {
		CustomerWithdrawHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerWithdrawHisService.get(id);
		}
		if (entity == null){
			entity = new CustomerWithdrawHis();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerWithdrawHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerWithdrawHis customerWithdrawHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerWithdrawHis> page = customerWithdrawHisService.findPage(new Page<CustomerWithdrawHis>(request, response), customerWithdrawHis); 
		model.addAttribute("page", page);
		return "modules/customer/customerWithdrawHisList";
	}

	@RequiresPermissions("customer:customerWithdrawHis:view")
	@RequestMapping(value = "form")
	public String form(CustomerWithdrawHis customerWithdrawHis, Model model) {
		model.addAttribute("customerWithdrawHis", customerWithdrawHis);
		return "modules/customer/customerWithdrawHisForm";
	}

	@RequiresPermissions("customer:customerWithdrawHis:edit")
	@RequestMapping(value = "save")
	public String save(CustomerWithdrawHis customerWithdrawHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerWithdrawHis)){
			return form(customerWithdrawHis, model);
		}
		customerWithdrawHisService.save(customerWithdrawHis);
		addMessage(redirectAttributes, "保存会员体现记录表成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerWithdrawHis/?repage";
	}
	
	@RequiresPermissions("customer:customerWithdrawHis:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerWithdrawHis customerWithdrawHis, RedirectAttributes redirectAttributes) {
		customerWithdrawHisService.delete(customerWithdrawHis);
		addMessage(redirectAttributes, "删除会员体现记录表成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerWithdrawHis/?repage";
	}

}