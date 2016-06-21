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
import com.thinkgem.jeesite.modules.entity.CustomerGoldCoinSnapshot;
import com.thinkgem.jeesite.modules.customer.service.CustomerGoldCoinSnapshotService;

/**
 * 会员代金币汇总Controller
 * @author ydt
 * @version 2015-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerGoldCoinSnapshot")
public class CustomerGoldCoinSnapshotController extends BaseController {

	@Autowired
	private CustomerGoldCoinSnapshotService customerGoldCoinSnapshotService;
	
	@ModelAttribute
	public CustomerGoldCoinSnapshot get(@RequestParam(required=false) String id) {
		CustomerGoldCoinSnapshot entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerGoldCoinSnapshotService.get(id);
		}
		if (entity == null){
			entity = new CustomerGoldCoinSnapshot();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerGoldCoinSnapshot:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerGoldCoinSnapshot customerGoldCoinSnapshot, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerGoldCoinSnapshot> page = customerGoldCoinSnapshotService.findPage(new Page<CustomerGoldCoinSnapshot>(request, response), customerGoldCoinSnapshot); 
		model.addAttribute("page", page);
		return "modules/customer/customerGoldCoinSnapshotList";
	}

	@RequiresPermissions("customer:customerGoldCoinSnapshot:view")
	@RequestMapping(value = "form")
	public String form(CustomerGoldCoinSnapshot customerGoldCoinSnapshot, Model model) {
		model.addAttribute("customerGoldCoinSnapshot", customerGoldCoinSnapshot);
		return "modules/customer/customerGoldCoinSnapshotForm";
	}

	@RequiresPermissions("customer:customerGoldCoinSnapshot:edit")
	@RequestMapping(value = "save")
	public String save(CustomerGoldCoinSnapshot customerGoldCoinSnapshot, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerGoldCoinSnapshot)){
			return form(customerGoldCoinSnapshot, model);
		}
		customerGoldCoinSnapshotService.save(customerGoldCoinSnapshot);
		addMessage(redirectAttributes, "保存会员代金币汇总成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerGoldCoinSnapshot/?repage";
	}
	
	@RequiresPermissions("customer:customerGoldCoinSnapshot:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerGoldCoinSnapshot customerGoldCoinSnapshot, RedirectAttributes redirectAttributes) {
		customerGoldCoinSnapshotService.delete(customerGoldCoinSnapshot);
		addMessage(redirectAttributes, "删除会员代金币汇总成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerGoldCoinSnapshot/?repage";
	}

}