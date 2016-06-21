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
import com.thinkgem.jeesite.modules.entity.CustomerOrgExtendInfo;
import com.thinkgem.jeesite.modules.customer.service.CustomerOrgExtendInfoService;

/**
 * 组织会员扩展信息Controller
 * @author ydt
 * @version 2015-06-30
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerOrgExtendInfo")
public class CustomerOrgExtendInfoController extends BaseController {

	@Autowired
	private CustomerOrgExtendInfoService customerOrgExtendInfoService;
	
	@ModelAttribute
	public CustomerOrgExtendInfo get(@RequestParam(required=false) String id) {
		CustomerOrgExtendInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerOrgExtendInfoService.get(id);
		}
		if (entity == null){
			entity = new CustomerOrgExtendInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerOrgExtendInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerOrgExtendInfo customerOrgExtendInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerOrgExtendInfo> page = customerOrgExtendInfoService.findPage(new Page<CustomerOrgExtendInfo>(request, response), customerOrgExtendInfo); 
		model.addAttribute("page", page);
		return "modules/customer/customerOrgExtendInfoList";
	}

	@RequiresPermissions("customer:customerOrgExtendInfo:view")
	@RequestMapping(value = "form")
	public String form(CustomerOrgExtendInfo customerOrgExtendInfo, Model model) {
		model.addAttribute("customerOrgExtendInfo", customerOrgExtendInfo);
		return "modules/customer/customerOrgExtendInfoForm";
	}

	@RequiresPermissions("customer:customerOrgExtendInfo:edit")
	@RequestMapping(value = "save")
	public String save(CustomerOrgExtendInfo customerOrgExtendInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerOrgExtendInfo)){
			return form(customerOrgExtendInfo, model);
		}
		customerOrgExtendInfoService.save(customerOrgExtendInfo);
		addMessage(redirectAttributes, "保存组织会员扩展信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerOrgExtendInfo/?repage";
	}
	
	@RequiresPermissions("customer:customerOrgExtendInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerOrgExtendInfo customerOrgExtendInfo, RedirectAttributes redirectAttributes) {
		customerOrgExtendInfoService.delete(customerOrgExtendInfo);
		addMessage(redirectAttributes, "删除组织会员扩展信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerOrgExtendInfo/?repage";
	}

}