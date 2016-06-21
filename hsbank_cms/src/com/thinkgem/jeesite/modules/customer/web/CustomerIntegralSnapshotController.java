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
import com.thinkgem.jeesite.modules.entity.CustomerIntegralSnapshot;
import com.thinkgem.jeesite.modules.customer.service.CustomerIntegralSnapshotService;

/**
 * 会员花生豆汇总Controller
 * @author ydt
 * @version 2015-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerIntegralSnapshot")
public class CustomerIntegralSnapshotController extends BaseController {

	@Autowired
	private CustomerIntegralSnapshotService customerIntegralSnapshotService;
	
	@ModelAttribute
	public CustomerIntegralSnapshot get(@RequestParam(required=false) String id) {
		CustomerIntegralSnapshot entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerIntegralSnapshotService.get(id);
		}
		if (entity == null){
			entity = new CustomerIntegralSnapshot();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerIntegralSnapshot:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerIntegralSnapshot customerIntegralSnapshot, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerIntegralSnapshot> page = customerIntegralSnapshotService.findPage(new Page<CustomerIntegralSnapshot>(request, response), customerIntegralSnapshot); 
		model.addAttribute("page", page);
		return "modules/customer/customerIntegralSnapshotList";
	}

	@RequiresPermissions("customer:customerIntegralSnapshot:view")
	@RequestMapping(value = "form")
	public String form(CustomerIntegralSnapshot customerIntegralSnapshot, Model model) {
		model.addAttribute("customerIntegralSnapshot", customerIntegralSnapshot);
		return "modules/customer/customerIntegralSnapshotForm";
	}

	@RequiresPermissions("customer:customerIntegralSnapshot:edit")
	@RequestMapping(value = "save")
	public String save(CustomerIntegralSnapshot customerIntegralSnapshot, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerIntegralSnapshot)){
			return form(customerIntegralSnapshot, model);
		}
		customerIntegralSnapshotService.save(customerIntegralSnapshot);
		addMessage(redirectAttributes, "保存会员花生豆汇总成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerIntegralSnapshot/?repage";
	}
	
	@RequiresPermissions("customer:customerIntegralSnapshot:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerIntegralSnapshot customerIntegralSnapshot, RedirectAttributes redirectAttributes) {
		customerIntegralSnapshotService.delete(customerIntegralSnapshot);
		addMessage(redirectAttributes, "删除会员花生豆汇总成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerIntegralSnapshot/?repage";
	}

}