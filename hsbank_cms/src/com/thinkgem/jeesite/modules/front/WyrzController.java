/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.modules.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 首页Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping(value = "${frontPath}")
public class WyrzController extends BaseController {

	@RequestMapping("wyrz")
	public String wyrz(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "wyrz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='#'>我要融资</a>");
		return "modules/front/wyrz";
	}
	
	/**
	 * 立即申请
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "wyrz/apply")
	public String project_detail(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/wyrz'>我要融资</a>&nbsp;&gt;&nbsp;<a href='#'>立即申请</a>");
		return "modules/front/wyrz/apply";
	}
}
