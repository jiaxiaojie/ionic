/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.web;

import java.util.Date;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerNewTaskService;
import com.thinkgem.jeesite.modules.entity.CustomerNewTask;

/**
 * 新手任务Controller
 * @author lzb
 * @version 2015-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerNewTask")
public class CustomerNewTaskController extends BaseController {

	@Autowired
	private CustomerNewTaskService customerNewTaskService;
	
	@ModelAttribute
	public CustomerNewTask get(@RequestParam(required=false) String id) {
		CustomerNewTask entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerNewTaskService.get(id);
		}
		if (entity == null){
			entity = new CustomerNewTask();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerNewTask:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerNewTask customerNewTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerNewTask> page = customerNewTaskService.findPage(new Page<CustomerNewTask>(request, response), customerNewTask); 
		model.addAttribute("page", page);
		return "modules/customer/customerNewTaskList";
	}

	@RequiresPermissions("customer:customerNewTask:view")
	@RequestMapping(value = "form")
	public String form(CustomerNewTask customerNewTask, Model model) {
		model.addAttribute("customerNewTask", customerNewTask);
		return "modules/customer/customerNewTaskForm";
	}

	@RequiresPermissions("customer:customerNewTask:edit")
	@RequestMapping(value = "save")
	public String save(CustomerNewTask customerNewTask, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerNewTask)){
			return form(customerNewTask, model);
		}
		if(customerNewTask.getIsNewRecord()){
			customerNewTask.setCreateDt(new Date());
		}else{
			customerNewTask.setModifyDt(new Date());
		}
		customerNewTaskService.save(customerNewTask);
		addMessage(redirectAttributes, "保存新手任务成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerNewTask/list";
	}
	
	@RequiresPermissions("customer:customerNewTask:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerNewTask customerNewTask, RedirectAttributes redirectAttributes) {
		customerNewTaskService.delete(customerNewTask);
		addMessage(redirectAttributes, "删除新手任务成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerNewTask/list";
	}

}