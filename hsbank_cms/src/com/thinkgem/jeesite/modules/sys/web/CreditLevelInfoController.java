/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

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
import com.thinkgem.jeesite.modules.entity.CreditLevelInfo;
import com.thinkgem.jeesite.modules.sys.service.CreditLevelInfoService;

/**
 * 信用等级信息Controller
 * @author ydt
 * @version 2015-08-04
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/creditLevelInfo")
public class CreditLevelInfoController extends BaseController {

	@Autowired
	private CreditLevelInfoService creditLevelInfoService;
	
	@ModelAttribute
	public CreditLevelInfo get(@RequestParam(required=false) String id) {
		CreditLevelInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creditLevelInfoService.get(id);
		}
		if (entity == null){
			entity = new CreditLevelInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:creditLevelInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreditLevelInfo creditLevelInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreditLevelInfo> page = creditLevelInfoService.findPage(new Page<CreditLevelInfo>(request, response), creditLevelInfo); 
		model.addAttribute("page", page);
		return "modules/sys/creditLevelInfoList";
	}

	@RequiresPermissions("sys:creditLevelInfo:view")
	@RequestMapping(value = "form")
	public String form(CreditLevelInfo creditLevelInfo, Model model) {
		model.addAttribute("creditLevelInfo", creditLevelInfo);
		return "modules/sys/creditLevelInfoForm";
	}

	@RequiresPermissions("sys:creditLevelInfo:edit")
	@RequestMapping(value = "save")
	public String save(CreditLevelInfo creditLevelInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creditLevelInfo)){
			return form(creditLevelInfo, model);
		}
		creditLevelInfoService.save(creditLevelInfo);
		addMessage(redirectAttributes, "保存信用等级信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/creditLevelInfo/?repage";
	}
	
	@RequiresPermissions("sys:creditLevelInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CreditLevelInfo creditLevelInfo, RedirectAttributes redirectAttributes) {
		creditLevelInfoService.delete(creditLevelInfo);
		addMessage(redirectAttributes, "删除信用等级信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/creditLevelInfo/?repage";
	}

}