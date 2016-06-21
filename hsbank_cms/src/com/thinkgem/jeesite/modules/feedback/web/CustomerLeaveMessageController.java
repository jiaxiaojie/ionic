/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.CustomerLeaveMessage;
import com.thinkgem.jeesite.modules.feedback.service.CustomerLeaveMessageService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 客户留言Controller
 * @author ydt
 * @version 2016-02-22
 */
@Controller
@RequestMapping(value = "${adminPath}/feedback/customerLeaveMessage")
public class CustomerLeaveMessageController extends BaseController {

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
	
	@RequiresPermissions("feedback:customerLeaveMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerLeaveMessage customerLeaveMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerLeaveMessage> page = customerLeaveMessageService.findPage(new Page<CustomerLeaveMessage>(request, response), customerLeaveMessage); 
		model.addAttribute("page", page);
		return "modules/feedback/customerLeaveMessageList";
	}
	
	@RequiresPermissions("feedback:customerLeaveMessage:view")
	@RequestMapping(value = {"hanworldList", ""})
	public String hanworldList(CustomerLeaveMessage customerLeaveMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		customerLeaveMessage.setStatus(ProjectConstant.CUSTOMER_LEAVE_MESSAGE_TYPE_HANWORLD_REALTY);
		Page<CustomerLeaveMessage> page = customerLeaveMessageService.findPage(new Page<CustomerLeaveMessage>(request, response), customerLeaveMessage); 
		model.addAttribute("page", page);
		return "modules/feedback/customerLeaveMessageList";
	}
	
	/**
	 * 导出数据
	 * @return
	 */
	@RequiresPermissions("operation:operationData:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(CustomerLeaveMessage customerLeaveMessage, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		try {
			customerLeaveMessage.setStatus(ProjectConstant.CUSTOMER_LEAVE_MESSAGE_TYPE_HANWORLD_REALTY);
			List<CustomerLeaveMessage> list = customerLeaveMessageService.findList(customerLeaveMessage);
			String fileName = "留言数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			new ExportExcel("留言数据（起始时间：" + StringUtils.replaceNull(DateUtils.formatDate(customerLeaveMessage.getBeginOpDt(), "yyyy-MM-dd"), "不限制")
					+ ",结束时间：" + StringUtils.replaceNull(DateUtils.formatDate(customerLeaveMessage.getEndOpDt(), "yyyy-MM-dd"), "不限制") + ")",
					CustomerLeaveMessage.class).setDataList(list).write(response, fileName).dispose();
			return "redirect:" + adminPath + "/feedback/customerLeaveMessage/hanworldList?repage";
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出留言数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/feedback/customerLeaveMessage/hanworldList?repage";
	}

	@RequiresPermissions("feedback:customerLeaveMessage:view")
	@RequestMapping(value = "form")
	public String form(CustomerLeaveMessage customerLeaveMessage, Model model) {
		model.addAttribute("customerLeaveMessage", customerLeaveMessage);
		return "modules/feedback/customerLeaveMessageForm";
	}

	@RequiresPermissions("feedback:customerLeaveMessage:edit")
	@RequestMapping(value = "save")
	public String save(CustomerLeaveMessage customerLeaveMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerLeaveMessage)){
			return form(customerLeaveMessage, model);
		}
		customerLeaveMessageService.save(customerLeaveMessage);
		addMessage(redirectAttributes, "保存客户留言成功");
		return "redirect:"+Global.getAdminPath()+"/feedback/customerLeaveMessage/?repage";
	}
	
	@RequiresPermissions("feedback:customerLeaveMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerLeaveMessage customerLeaveMessage, RedirectAttributes redirectAttributes) {
		customerLeaveMessageService.delete(customerLeaveMessage);
		addMessage(redirectAttributes, "删除客户留言成功");
		return "redirect:"+Global.getAdminPath()+"/feedback/customerLeaveMessage/?repage";
	}

}