/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.CustomerInvestmentTicket;
import com.thinkgem.jeesite.modules.customer.service.CustomerInvestmentTicketService;

/**
 * 会员投资券清单Controller
 * @author yangtao
 * @version 2015-07-21
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerInvestmentTicket")
public class CustomerInvestmentTicketController extends BaseController {

	@Autowired
	private CustomerInvestmentTicketService customerInvestmentTicketService;
	
	@ModelAttribute
	public CustomerInvestmentTicket get(@RequestParam(required=false) String id) {
		CustomerInvestmentTicket entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerInvestmentTicketService.get(id);
		}
		if (entity == null){
			entity = new CustomerInvestmentTicket();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerInvestmentTicket:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerInvestmentTicket customerInvestmentTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerInvestmentTicket> page = customerInvestmentTicketService.findPage(new Page<CustomerInvestmentTicket>(request, response), customerInvestmentTicket); 
		model.addAttribute("page", page);
		return "modules/customer/customerInvestmentTicketList";
	}

	@RequiresPermissions("customer:customerInvestmentTicket:view")
	@RequestMapping(value = "form")
	public String form(CustomerInvestmentTicket customerInvestmentTicket, Model model) {
		model.addAttribute("customerInvestmentTicket", customerInvestmentTicket);
		return "modules/customer/customerInvestmentTicketForm";
	}

	@RequiresPermissions("customer:customerInvestmentTicket:edit")
	@RequestMapping(value = "save")
	public String save(CustomerInvestmentTicket customerInvestmentTicket, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerInvestmentTicket)){
			return form(customerInvestmentTicket, model);
		}
		customerInvestmentTicketService.save(customerInvestmentTicket);
		addMessage(redirectAttributes, "保存会员投资券成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerInvestmentTicket/?repage";
	}
	
	@RequiresPermissions("customer:customerInvestmentTicket:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerInvestmentTicket customerInvestmentTicket, RedirectAttributes redirectAttributes) {
		customerInvestmentTicketService.delete(customerInvestmentTicket);
		addMessage(redirectAttributes, "删除会员投资券成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerInvestmentTicket/?repage";
	}
	
	@RequiresPermissions("customer:customerInvestmentTicket:edit")
	@RequestMapping(value = "firstInvestmentGiveTicket")
	public String firstInvestmentGiveTicket() {
		return "modules/customer/customerInvestmentTicketGiveByFirstInvestment";
	}
	
	@RequiresPermissions("customer:customerInvestmentTicket:edit")
	@RequestMapping(value = "doFirstInvestmentGiveTicket")
	public String doFirstInvestmentGiveTicket(String mobiles, RedirectAttributes redirectAttributes) {
		String successMobiles = customerInvestmentTicketService.giveTicketByFirstInvestment(mobiles);
		String message = "要赠送的手机号为：" + mobiles.replace("\n", "")+ "<br/>";
		if(StringUtils.isBlank(successMobiles)) {
			message += "赠送投资券礼包失败：填写的手机号不符合赠送条件。";
		}else {
			message += "赠送投资券礼包成功的手机号有：" + successMobiles + "。";
		}
		addMessage(redirectAttributes, message);
		return "redirect:"+Global.getAdminPath()+"/customer/customerInvestmentTicket/firstInvestmentGiveTicket";
	}
	
	
	@RequiresPermissions("customer:customerInvestmentTicket:view")
	@RequestMapping(value = {"clearList", ""})
	public String clearList(CustomerInvestmentTicket customerInvestmentTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(customerInvestmentTicket.getAccountId() != null){
			Page<CustomerInvestmentTicket> page = customerInvestmentTicketService.findPage(new Page<CustomerInvestmentTicket>(request, response), customerInvestmentTicket); 
			model.addAttribute("page", page);
		}
		
		return "modules/customer/customerInvestmentTicketClearList";
	}
	
	@ResponseBody
	@RequiresPermissions("customer:customerInvestmentTicket:edit")
	@RequestMapping(value = "clearTicket")
	public HashMap<String, Object> clearTicket(String accountId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try{
			customerInvestmentTicketService.clearTicketByAccountId(accountId);
			result.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
		}
		
		return result;
	}
	
	
}