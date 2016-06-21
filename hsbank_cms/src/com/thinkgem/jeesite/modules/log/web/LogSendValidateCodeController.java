/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.web;

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
import com.thinkgem.jeesite.modules.entity.LogSendValidateCode;
import com.thinkgem.jeesite.modules.log.service.LogSendValidateCodeService;

/**
 * 验证码发送日志Controller
 * @author ydt
 * @version 2015-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/log/logSendValidateCode")
public class LogSendValidateCodeController extends BaseController {

	@Autowired
	private LogSendValidateCodeService logSendValidateCodeService;
	
	@ModelAttribute
	public LogSendValidateCode get(@RequestParam(required=false) String id) {
		LogSendValidateCode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = logSendValidateCodeService.get(id);
		}
		if (entity == null){
			entity = new LogSendValidateCode();
		}
		return entity;
	}
	
	@RequiresPermissions("log:logSendValidateCode:view")
	@RequestMapping(value = {"list", ""})
	public String list(LogSendValidateCode logSendValidateCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LogSendValidateCode> page = logSendValidateCodeService.findPage(new Page<LogSendValidateCode>(request, response), logSendValidateCode); 
		model.addAttribute("page", page);
		return "modules/log/logSendValidateCodeList";
	}

	@RequiresPermissions("log:logSendValidateCode:view")
	@RequestMapping(value = "form")
	public String form(LogSendValidateCode logSendValidateCode, Model model) {
		model.addAttribute("logSendValidateCode", logSendValidateCode);
		return "modules/log/logSendValidateCodeForm";
	}

	@RequiresPermissions("log:logSendValidateCode:edit")
	@RequestMapping(value = "save")
	public String save(LogSendValidateCode logSendValidateCode, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, logSendValidateCode)){
			return form(logSendValidateCode, model);
		}
		logSendValidateCodeService.save(logSendValidateCode);
		addMessage(redirectAttributes, "保存验证码发送日志成功");
		return "redirect:"+Global.getAdminPath()+"/log/logSendValidateCode/?repage";
	}
	
	@RequiresPermissions("log:logSendValidateCode:edit")
	@RequestMapping(value = "delete")
	public String delete(LogSendValidateCode logSendValidateCode, RedirectAttributes redirectAttributes) {
		logSendValidateCodeService.delete(logSendValidateCode);
		addMessage(redirectAttributes, "删除验证码发送日志成功");
		return "redirect:"+Global.getAdminPath()+"/log/logSendValidateCode/?repage";
	}

}