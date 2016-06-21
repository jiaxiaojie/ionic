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
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawDeposiHis;
import com.thinkgem.jeesite.modules.customer.service.CustomerWithdrawDeposiHisService;

/**
 * 会员提现额流水Controller
 * @author yangtao
 * @version 2015-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerWithdrawDeposiHis")
public class CustomerWithdrawDeposiHisController extends BaseController {

	@Autowired
	private CustomerWithdrawDeposiHisService customerWithdrawDeposiHisService;
	
	@ModelAttribute
	public CustomerWithdrawDeposiHis get(@RequestParam(required=false) String id) {
		CustomerWithdrawDeposiHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerWithdrawDeposiHisService.get(id);
		}
		if (entity == null){
			entity = new CustomerWithdrawDeposiHis();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerWithdrawDeposiHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerWithdrawDeposiHis customerWithdrawDeposiHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerWithdrawDeposiHis> page = customerWithdrawDeposiHisService.findPage(new Page<CustomerWithdrawDeposiHis>(request, response), customerWithdrawDeposiHis); 
		model.addAttribute("page", page);
		return "modules/customer/customerWithdrawDeposiHisList";
	}

	@RequiresPermissions("customer:customerWithdrawDeposiHis:view")
	@RequestMapping(value = "form")
	public String form(CustomerWithdrawDeposiHis customerWithdrawDeposiHis, Model model) {
		model.addAttribute("customerWithdrawDeposiHis", customerWithdrawDeposiHis);
		return "modules/customer/customerWithdrawDeposiHisForm";
	}

	@RequiresPermissions("customer:customerWithdrawDeposiHis:edit")
	@RequestMapping(value = "save")
	public String save(CustomerWithdrawDeposiHis customerWithdrawDeposiHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerWithdrawDeposiHis)){
			return form(customerWithdrawDeposiHis, model);
		}
		customerWithdrawDeposiHisService.save(customerWithdrawDeposiHis);
		addMessage(redirectAttributes, "保存会员提现额流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerWithdrawDeposiHis/?repage";
	}
	
	@RequiresPermissions("customer:customerWithdrawDeposiHis:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerWithdrawDeposiHis customerWithdrawDeposiHis, RedirectAttributes redirectAttributes) {
		customerWithdrawDeposiHisService.delete(customerWithdrawDeposiHis);
		addMessage(redirectAttributes, "删除会员提现额流水成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerWithdrawDeposiHis/?repage";
	}

}