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
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawProcess;
import com.thinkgem.jeesite.modules.customer.service.CustomerWithdrawProcessService;

/**
 * 会员提现审批Controller
 * @author yangtao
 * @version 2015-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerWithdrawProcess")
public class CustomerWithdrawProcessController extends BaseController {

	@Autowired
	private CustomerWithdrawProcessService customerWithdrawProcessService;
	
	@ModelAttribute
	public CustomerWithdrawProcess get(@RequestParam(required=false) String id) {
		CustomerWithdrawProcess entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerWithdrawProcessService.get(id);
		}
		if (entity == null){
			entity = new CustomerWithdrawProcess();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerWithdrawProcess:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerWithdrawProcess customerWithdrawProcess, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerWithdrawProcess> page = customerWithdrawProcessService.findPage(new Page<CustomerWithdrawProcess>(request, response), customerWithdrawProcess); 
		model.addAttribute("page", page);
		return "modules/customer/customerWithdrawProcessList";
	}

	@RequiresPermissions("customer:customerWithdrawProcess:view")
	@RequestMapping(value = "form")
	public String form(CustomerWithdrawProcess customerWithdrawProcess, Model model) {
		model.addAttribute("customerWithdrawProcess", customerWithdrawProcess);
		return "modules/customer/customerWithdrawProcessForm";
	}

	@RequiresPermissions("customer:customerWithdrawProcess:edit")
	@RequestMapping(value = "save")
	public String save(CustomerWithdrawProcess customerWithdrawProcess, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerWithdrawProcess)){
			return form(customerWithdrawProcess, model);
		}
		customerWithdrawProcessService.save(customerWithdrawProcess);
		addMessage(redirectAttributes, "保存会员提现审批成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerWithdrawProcess/?repage";
	}
	
	@RequiresPermissions("customer:customerWithdrawProcess:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerWithdrawProcess customerWithdrawProcess, RedirectAttributes redirectAttributes) {
		customerWithdrawProcessService.delete(customerWithdrawProcess);
		addMessage(redirectAttributes, "删除会员提现审批成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerWithdrawProcess/?repage";
	}

}