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
import com.thinkgem.jeesite.modules.entity.BankInfo;
import com.thinkgem.jeesite.modules.sys.service.BankInfoService;

/**
 * 银行信息Controller
 * @author wanduanrui
 * @version 2015-11-17
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/bankInfo")
public class BankInfoController extends BaseController {

	@Autowired
	private BankInfoService bankInfoService;
	
	@ModelAttribute
	public BankInfo get(@RequestParam(required=false) String id) {
		BankInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankInfoService.get(id);
		}
		if (entity == null){
			entity = new BankInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:bankInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(BankInfo bankInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BankInfo> page = bankInfoService.findPage(new Page<BankInfo>(request, response), bankInfo); 
		model.addAttribute("page", page);
		return "modules/sys/bankInfoList";
	}

	@RequiresPermissions("sys:bankInfo:view")
	@RequestMapping(value = "form")
	public String form(BankInfo bankInfo, Model model) {
		model.addAttribute("bankInfo", bankInfo);
		return "modules/sys/bankInfoForm";
	}

	@RequiresPermissions("sys:bankInfo:edit")
	@RequestMapping(value = "save")
	public String save(BankInfo bankInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bankInfo)){
			return form(bankInfo, model);
		}
		bankInfoService.save(bankInfo);
		addMessage(redirectAttributes, "保存银行信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bankInfo/?repage";
	}
	
	@RequiresPermissions("sys:bankInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(BankInfo bankInfo, RedirectAttributes redirectAttributes) {
		bankInfoService.delete(bankInfo);
		addMessage(redirectAttributes, "删除银行信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bankInfo/?repage";
	}

}