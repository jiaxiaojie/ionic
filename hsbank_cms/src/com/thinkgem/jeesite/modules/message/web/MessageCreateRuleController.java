/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.web;

import java.util.ArrayList;
import java.util.List;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.MessageBehaviorType;
import com.thinkgem.jeesite.modules.entity.MessageCreateRule;
import com.thinkgem.jeesite.modules.marketing.service.MessageBehaviorTypeService;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.MessageUtils;
import com.thinkgem.jeesite.modules.message.service.MessageCreateRuleService;

/**
 * 消息产生规则Controller
 * @author ydt
 * @version 2016-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/message/messageCreateRule")
public class MessageCreateRuleController extends BaseController {

	@Autowired
	private MessageCreateRuleService messageCreateRuleService;
	@Autowired
	private MessageBehaviorTypeService messageBehaviorTypeService;
	
	@ModelAttribute
	public MessageCreateRule get(@RequestParam(required=false) String id) {
		MessageCreateRule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = messageCreateRuleService.get(id);
		}
		if (entity == null){
			entity = new MessageCreateRule();
		}
		return entity;
	}
	
	@ResponseBody
	@RequiresPermissions("message:messageCreateRule:view")
	@RequestMapping(value = "queryById")
	public MessageCreateRule queryById(String id){
		return get(id);
	}
	
	@RequiresPermissions("message:messageCreateRule:view")
	@RequestMapping(value = "query")
	@ResponseBody
	public List<MessageCreateRule> querySimpleList(String queryParas) {
		MessageCreateRule qa=new MessageCreateRule();
		if(queryParas==null){
			return new ArrayList<MessageCreateRule>();
		}
		queryParas=queryParas.trim();
		qa.setQueryParas(queryParas);
		return messageCreateRuleService.querySimpleList(qa);
	}
	
	@RequiresPermissions("message:messageCreateRule:view")
	@RequestMapping(value = {"list", ""})
	public String list(MessageCreateRule messageCreateRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MessageCreateRule> page = messageCreateRuleService.findPage(new Page<MessageCreateRule>(request, response), messageCreateRule); 
		model.addAttribute("page", page);
		return "modules/message/messageCreateRuleList";
	}

	@RequiresPermissions("message:messageCreateRule:view")
	@RequestMapping(value = "form")
	public String form(MessageCreateRule messageCreateRule, Model model) {
		model.addAttribute("messageCreateRule", messageCreateRule);
		List<MessageBehaviorType> messageBehaviorTypeList = messageBehaviorTypeService.findList(new MessageBehaviorType());
		model.addAttribute("messageBehaviorTypeList", messageBehaviorTypeList);
		return "modules/message/messageCreateRuleForm";
	}
	
	@RequiresPermissions("message:messageCreateRule:view")
	@RequestMapping(value = {"reviewList", ""})
	public String reviewList(MessageCreateRule messageCreateRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		messageCreateRule.setStatus(MessageConstant.MESSAGE_RULE_STATUS_CREATE);
		Page<MessageCreateRule> page = messageCreateRuleService.findPage(new Page<MessageCreateRule>(request, response), messageCreateRule); 
		model.addAttribute("page", page);
		return "modules/message/messageCreateRuleReviewList";
	}

	@RequiresPermissions("message:messageCreateRule:view")
	@RequestMapping(value = "reviewForm")
	public String reviewForm(MessageCreateRule messageCreateRule, Model model) {
		model.addAttribute("messageCreateRule", messageCreateRule);
		List<MessageBehaviorType> messageBehaviorTypeList = messageBehaviorTypeService.findList(new MessageBehaviorType());
		model.addAttribute("messageBehaviorTypeList", messageBehaviorTypeList);
		return "modules/message/messageCreateRuleReviewForm";
	}

	@RequiresPermissions("message:messageCreateRule:edit")
	@RequestMapping(value = "save")
	public String save(MessageCreateRule messageCreateRule, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, messageCreateRule)){
			return form(messageCreateRule, model);
		}
		messageCreateRuleService.save(messageCreateRule);
		MessageUtils.remove(messageCreateRule);
		addMessage(redirectAttributes, "保存消息产生规则成功");
		return "redirect:"+Global.getAdminPath()+"/message/messageCreateRule/list?repage";
	}

	@RequiresPermissions("message:messageCreateRule:edit")
	@RequestMapping(value = "review")
	public String review(String id, String status, Model model, RedirectAttributes redirectAttributes) {
		messageCreateRuleService.review(id, status);
		addMessage(redirectAttributes, "审批消息产生规则成功");
		return "redirect:"+Global.getAdminPath()+"/message/messageCreateRule/reviewList?repage";
	}
	
	@RequiresPermissions("message:messageCreateRule:edit")
	@RequestMapping(value = "delete")
	public String delete(MessageCreateRule messageCreateRule, RedirectAttributes redirectAttributes) {
		messageCreateRuleService.delete(messageCreateRule);
		addMessage(redirectAttributes, "删除消息产生规则成功");
		return "redirect:"+Global.getAdminPath()+"/message/messageCreateRule/?repage";
	}

}