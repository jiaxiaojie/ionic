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
import com.thinkgem.jeesite.modules.entity.LogThirdParty;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;

/**
 * 第三方交互日志Controller
 * @author yangtao
 * @version 2015-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/log/logThirdParty")
public class LogThirdPartyController extends BaseController {

	@Autowired
	private LogThirdPartyService logThirdPartyService;
	
	@ModelAttribute
	public LogThirdParty get(@RequestParam(required=false) String id) {
		LogThirdParty entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = logThirdPartyService.get(id);
		}
		if (entity == null){
			entity = new LogThirdParty();
		}
		return entity;
	}
	
	@RequiresPermissions("log:logThirdParty:view")
	@RequestMapping(value = {"list", ""})
	public String list(LogThirdParty logThirdParty, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LogThirdParty> page = logThirdPartyService.findPage(new Page<LogThirdParty>(request, response), logThirdParty); 
		model.addAttribute("page", page);
		return "modules/log/logThirdPartyList";
	}

	@RequiresPermissions("log:logThirdParty:view")
	@RequestMapping(value = "form")
	public String form(LogThirdParty logThirdParty, Model model) {
		model.addAttribute("logThirdParty", logThirdParty);
		return "modules/log/logThirdPartyForm";
	}

	@RequiresPermissions("log:logThirdParty:edit")
	@RequestMapping(value = "save")
	public String save(LogThirdParty logThirdParty, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, logThirdParty)){
			return form(logThirdParty, model);
		}
		logThirdPartyService.save(logThirdParty);
		addMessage(redirectAttributes, "保存第三方交互日志成功");
		return "redirect:"+Global.getAdminPath()+"/log/logThirdParty/?repage";
	}
	
	@RequiresPermissions("log:logThirdParty:edit")
	@RequestMapping(value = "delete")
	public String delete(LogThirdParty logThirdParty, RedirectAttributes redirectAttributes) {
		logThirdPartyService.delete(logThirdParty);
		addMessage(redirectAttributes, "删除第三方交互日志成功");
		return "redirect:"+Global.getAdminPath()+"/log/logThirdParty/?repage";
	}

}