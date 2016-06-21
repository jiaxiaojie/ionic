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
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawDepositSnapshot;
import com.thinkgem.jeesite.modules.customer.service.CustomerWithdrawDepositSnapshotService;

/**
 * 会员提现额汇总Controller
 * @author yangtao
 * @version 2015-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerWithdrawDepositSnapshot")
public class CustomerWithdrawDepositSnapshotController extends BaseController {

	@Autowired
	private CustomerWithdrawDepositSnapshotService customerWithdrawDepositSnapshotService;
	
	@ModelAttribute
	public CustomerWithdrawDepositSnapshot get(@RequestParam(required=false) String id) {
		CustomerWithdrawDepositSnapshot entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerWithdrawDepositSnapshotService.get(id);
		}
		if (entity == null){
			entity = new CustomerWithdrawDepositSnapshot();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerWithdrawDepositSnapshot:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerWithdrawDepositSnapshot customerWithdrawDepositSnapshot, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerWithdrawDepositSnapshot> page = customerWithdrawDepositSnapshotService.findPage(new Page<CustomerWithdrawDepositSnapshot>(request, response), customerWithdrawDepositSnapshot); 
		model.addAttribute("page", page);
		return "modules/customer/customerWithdrawDepositSnapshotList";
	}

	@RequiresPermissions("customer:customerWithdrawDepositSnapshot:view")
	@RequestMapping(value = "form")
	public String form(CustomerWithdrawDepositSnapshot customerWithdrawDepositSnapshot, Model model) {
		model.addAttribute("customerWithdrawDepositSnapshot", customerWithdrawDepositSnapshot);
		return "modules/customer/customerWithdrawDepositSnapshotForm";
	}

	@RequiresPermissions("customer:customerWithdrawDepositSnapshot:edit")
	@RequestMapping(value = "save")
	public String save(CustomerWithdrawDepositSnapshot customerWithdrawDepositSnapshot, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerWithdrawDepositSnapshot)){
			return form(customerWithdrawDepositSnapshot, model);
		}
		customerWithdrawDepositSnapshotService.save(customerWithdrawDepositSnapshot);
		addMessage(redirectAttributes, "保存会员提现额汇总成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerWithdrawDepositSnapshot/?repage";
	}
	
	@RequiresPermissions("customer:customerWithdrawDepositSnapshot:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerWithdrawDepositSnapshot customerWithdrawDepositSnapshot, RedirectAttributes redirectAttributes) {
		customerWithdrawDepositSnapshotService.delete(customerWithdrawDepositSnapshot);
		addMessage(redirectAttributes, "删除会员提现额汇总成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerWithdrawDepositSnapshot/?repage";
	}

}