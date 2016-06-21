/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.entity.CustomerLeaveMessage;
import com.thinkgem.jeesite.modules.feedback.service.CustomerLeaveMessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 预约Controller
 * @author yubin
 * @version 2016-05-19
 */
@Controller
@RequestMapping(value = "${adminPath}/personal/personalTailorSubs")
public class PersonalTailorSubsController extends BaseController {

	@Autowired
	private CustomerLeaveMessageService customerLeaveMessageService;

	@ModelAttribute
	public CustomerLeaveMessage get(@RequestParam(required=false) String id) {
		CustomerLeaveMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerLeaveMessageService.get(id);
		}
		if (entity == null){
			entity = new CustomerLeaveMessage();
		}
		return entity;
	}
	
	@RequiresPermissions("personal:personalTailorSubs:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerLeaveMessage customerLeaveMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		customerLeaveMessage.setType("2");
		Page<CustomerLeaveMessage> page = customerLeaveMessageService.findPage(new Page<CustomerLeaveMessage>(request, response), customerLeaveMessage);
		model.addAttribute("page", page);
		return "modules/personal/personalTailorSubsList";
	}

	@RequiresPermissions("personal:personalTailorSubs:view")
	@RequestMapping(value = "form")
	public String form(CustomerLeaveMessage customerLeaveMessage, Model model) {
		model.addAttribute("customerLeaveMessage", customerLeaveMessage);
		return "modules/personal/personalTailorSubsForm";
	}

	@RequiresPermissions("personal:personalTailorSubs:edit")
	@RequestMapping(value = "save")
	public String save(CustomerLeaveMessage customerLeaveMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerLeaveMessage)){
			return form(customerLeaveMessage, model);
		}
		customerLeaveMessageService.save(customerLeaveMessage);
		addMessage(redirectAttributes, "保存私人订制预约成功");
		return "redirect:"+Global.getAdminPath()+"/personal/personalTailorSubs/?repage";
	}
	
	@RequiresPermissions("personal:personalTailorSubs:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerLeaveMessage customerLeaveMessage, RedirectAttributes redirectAttributes) {
		customerLeaveMessageService.delete(customerLeaveMessage);
		addMessage(redirectAttributes, "删除私人订制预约成功");
		return "redirect:"+Global.getAdminPath()+"/personal/personalTailorSubs/?repage";
	}
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("personal:personalTailorSubs:view")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportFile(CustomerLeaveMessage customerLeaveMessage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "私人预约数据;"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<CustomerLeaveMessage> page = customerLeaveMessageService.findPage(new Page<CustomerLeaveMessage>(request, response), customerLeaveMessage);
			new ExportExcel("用户数据", CustomerLeaveMessage.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

}