/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.Message;
import com.thinkgem.jeesite.modules.entity.MessageInstance;
import com.thinkgem.jeesite.modules.message.service.MessageInstanceService;

/**
 * 消息实例Controller
 * @author ydt
 * @version 2016-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/message/messageInstance")
public class MessageInstanceController extends BaseController {

	@Autowired
	private MessageInstanceService messageInstanceService;
	
	@ModelAttribute
	public MessageInstance get(@RequestParam(required=false) String id) {
		MessageInstance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = messageInstanceService.get(id);
		}
		if (entity == null){
			entity = new MessageInstance();
		}
		return entity;
	}
	
	@RequiresPermissions("message:messageInstance:view")
	@RequestMapping(value = {"list", ""})
	public String list(MessageInstance messageInstance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MessageInstance> page = messageInstanceService.findPage(new Page<MessageInstance>(request, response), messageInstance); 
		model.addAttribute("page", page);
		return "modules/message/messageInstanceList";
	}
	
	/**
	 * 消息发送统计
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("message:message:messageStatistics")
	@RequestMapping(value = {"messageStatistics"})
	public String messageStatistics(MessageInstance messageInstance,CustomerBase customerBase,Message message, Date beginOpDt, Date endOpDt,HttpServletRequest request, HttpServletResponse response, Model model) {
		//查询数据显示的条数
        List<Map<String, Object>> list = messageInstanceService.selectMessageNum(messageInstance,beginOpDt,endOpDt,customerBase,message);
        model.addAttribute("messageNum", list.size());
        model.addAttribute("messageInstance", messageInstance);
        model.addAttribute("beginOpDt", beginOpDt);
		model.addAttribute("endOpDt", endOpDt);
		return "modules/message/customMessageStatisticsList";
	}
	
	
	
	
	
	
	/**
	 * 日志查询
	 * @param messageInstance
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("message:messageInstance:view")
	@RequestMapping(value = {"searchPageList", ""})
	public String searchPageList(MessageInstance messageInstance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MessageInstance> page = messageInstanceService.searchPageList(new Page<MessageInstance>(request, response), messageInstance); 
		model.addAttribute("page", page);
		return "modules/message/messageInstanceViewList";
	}

	@RequiresPermissions("message:messageInstance:view")
	@RequestMapping(value = "form")
	public String form(MessageInstance messageInstance, Model model) {
		model.addAttribute("messageInstance", messageInstance);
		return "modules/message/messageInstanceForm";
	}
	
	@RequiresPermissions("message:messageInstance:view")
	@RequestMapping(value = "viewForm")
	public String viewForm(MessageInstance messageInstance, Model model) {
		model.addAttribute("messageInstance", messageInstance);
		return "modules/message/messageInstanceViewForm";
	}

	@RequiresPermissions("message:messageInstance:edit")
	@RequestMapping(value = "save")
	public String save(MessageInstance messageInstance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, messageInstance)){
			return form(messageInstance, model);
		}
		messageInstanceService.save(messageInstance);
		addMessage(redirectAttributes, "保存消息实例成功");
		return "redirect:"+Global.getAdminPath()+"/message/messageInstance/?repage";
	}
	
	@RequiresPermissions("message:messageInstance:edit")
	@RequestMapping(value = "delete")
	public String delete(MessageInstance messageInstance, RedirectAttributes redirectAttributes) {
		messageInstanceService.delete(messageInstance);
		addMessage(redirectAttributes, "删除消息实例成功");
		return "redirect:"+Global.getAdminPath()+"/message/messageInstance/?repage";
	}

}