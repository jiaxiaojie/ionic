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
import com.thinkgem.jeesite.modules.entity.CustomerOrgFinanceYearRecord;
import com.thinkgem.jeesite.modules.customer.service.CustomerOrgFinanceYearRecordService;

/**
 * 企业会员财务年表Controller
 * @author ydt
 * @version 2015-06-30
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerOrgFinanceYearRecord")
public class CustomerOrgFinanceYearRecordController extends BaseController {

	@Autowired
	private CustomerOrgFinanceYearRecordService customerOrgFinanceYearRecordService;
	
	@ModelAttribute
	public CustomerOrgFinanceYearRecord get(@RequestParam(required=false) String id) {
		CustomerOrgFinanceYearRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerOrgFinanceYearRecordService.get(id);
		}
		if (entity == null){
			entity = new CustomerOrgFinanceYearRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerOrgFinanceYearRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerOrgFinanceYearRecord customerOrgFinanceYearRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerOrgFinanceYearRecord> page = customerOrgFinanceYearRecordService.findPage(new Page<CustomerOrgFinanceYearRecord>(request, response), customerOrgFinanceYearRecord); 
		model.addAttribute("page", page);
		model.addAttribute("customerId", customerOrgFinanceYearRecord.getCustomerId());
		return "modules/customer/customerOrgFinanceYearRecordList";
	}

	@RequiresPermissions("customer:customerOrgFinanceYearRecord:view")
	@RequestMapping(value = "form")
	public String form(CustomerOrgFinanceYearRecord customerOrgFinanceYearRecord, Model model) {
		model.addAttribute("customerOrgFinanceYearRecord", customerOrgFinanceYearRecord);
		return "modules/customer/customerOrgFinanceYearRecordForm";
	}

	@RequiresPermissions("customer:customerOrgFinanceYearRecord:edit")
	@RequestMapping(value = "save")
	public String save(CustomerOrgFinanceYearRecord customerOrgFinanceYearRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerOrgFinanceYearRecord)){
			return form(customerOrgFinanceYearRecord, model);
		}
		customerOrgFinanceYearRecordService.save(customerOrgFinanceYearRecord);
		addMessage(redirectAttributes, "保存企业会员财务年表成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerAccount/?repage";
	}
	
	@RequiresPermissions("customer:customerOrgFinanceYearRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerOrgFinanceYearRecord customerOrgFinanceYearRecord, RedirectAttributes redirectAttributes) {
		customerOrgFinanceYearRecordService.delete(customerOrgFinanceYearRecord);
		addMessage(redirectAttributes, "删除企业会员财务年表成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerAccount/?repage";
	}

}