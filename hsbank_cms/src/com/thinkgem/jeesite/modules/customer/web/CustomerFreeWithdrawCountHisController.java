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
import com.thinkgem.jeesite.modules.entity.CustomerFreeWithdrawCountHis;
import com.thinkgem.jeesite.modules.customer.service.CustomerFreeWithdrawCountHisService;

/**
 * 会员可免费提现次数变更流水Controller
 * @author ydt
 * @version 2015-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerFreeWithdrawCountHis")
public class CustomerFreeWithdrawCountHisController extends BaseController {

	@Autowired
	private CustomerFreeWithdrawCountHisService customerFreeWithdrawCountHisService;
	
	@ModelAttribute
	public CustomerFreeWithdrawCountHis get(@RequestParam(required=false) String id) {
		CustomerFreeWithdrawCountHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerFreeWithdrawCountHisService.get(id);
		}
		if (entity == null){
			entity = new CustomerFreeWithdrawCountHis();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerFreeWithdrawCountHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerFreeWithdrawCountHis customerFreeWithdrawCountHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerFreeWithdrawCountHis> page = customerFreeWithdrawCountHisService.findPage(new Page<CustomerFreeWithdrawCountHis>(request, response), customerFreeWithdrawCountHis); 
		model.addAttribute("page", page);
		return "modules/customer/customerFreeWithdrawCountHisList";
	}

	@RequiresPermissions("customer:customerFreeWithdrawCountHis:view")
	@RequestMapping(value = "form")
	public String form(CustomerFreeWithdrawCountHis customerFreeWithdrawCountHis, Model model) {
		model.addAttribute("customerFreeWithdrawCountHis", customerFreeWithdrawCountHis);
		return "modules/customer/customerFreeWithdrawCountHisForm";
	}

	@RequiresPermissions("customer:customerFreeWithdrawCountHis:edit")
	@RequestMapping(value = "save")
	public String save(CustomerFreeWithdrawCountHis customerFreeWithdrawCountHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerFreeWithdrawCountHis)){
			return form(customerFreeWithdrawCountHis, model);
		}
		customerFreeWithdrawCountHisService.save(customerFreeWithdrawCountHis);
		addMessage(redirectAttributes, "保存会员可免费提现次数变更流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerFreeWithdrawCountHis/?repage";
	}
	
	@RequiresPermissions("customer:customerFreeWithdrawCountHis:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerFreeWithdrawCountHis customerFreeWithdrawCountHis, RedirectAttributes redirectAttributes) {
		customerFreeWithdrawCountHisService.delete(customerFreeWithdrawCountHis);
		addMessage(redirectAttributes, "删除会员可免费提现次数变更流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerFreeWithdrawCountHis/?repage";
	}

}