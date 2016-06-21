/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.modules.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

/**
 * 我的账户Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping(value = "${frontPath}/wdzh")
public class WdzhController extends BaseController {
	@Autowired
	private CustomerAccountService customerAccountService;
	/**
	 * 融资管理-我的借款
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("rzgl_wdjk")
	public String rzgl_wdjk(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "rzgl");
		model.addAttribute("two_menu", "wdjk");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;融资管理&nbsp;&gt;&nbsp;<a href='#'>我的借款</a>");
		return "modules/front/wdzh/rzgl_wdjk";
	}
	
	/**
	 * 融资管理-借款申请查询
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("rzgl_jksqcx")
	public String rzgl_jksqcx(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "rzgl");
		model.addAttribute("two_menu", "jksqcx");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;融资管理&nbsp;&gt;&nbsp;<a href='#'>借款申请查询</a>");
		return "modules/front/wdzh/rzgl_jksqcx";
	}
	
	/**
	 * 融资管理-借款统计
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("rzgl_jktj")
	public String rzgl_jktj(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "rzgl");
		model.addAttribute("two_menu", "jktj");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;融资管理&nbsp;&gt;&nbsp;<a href='#'>借款统计</a>");
		return "modules/front/wdzh/rzgl_jktj";
	}
	
	/**
	 * 我的优惠券
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("wdyhj")
	public String wdyhj(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdyhj");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;<a href='#'>我的现金券</a>");
		return "modules/front/wdzh/wdyhj";
	}
	
	/**
	 * 我的消息
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("wdxx")
	public String wdxx(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId));
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdxx");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;<a href='#'>我的消息</a>");
		return "modules/front/wdzh/wdxx";
	}
}
