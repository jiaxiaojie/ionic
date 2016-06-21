/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.credit.web;

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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.CreditBaseInfo;
import com.thinkgem.jeesite.modules.credit.CreditConstant;
import com.thinkgem.jeesite.modules.credit.service.CreditBaseInfoService;

/**
 * 债券基本信息Controller
 * @author wanduanrui
 * @version 2016-03-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditBaseInfo")
public class CreditBaseInfoController extends BaseController {

	@Autowired
	private CreditBaseInfoService creditBaseInfoService;
	
	@ModelAttribute
	public CreditBaseInfo get(@RequestParam(required=false) String id) {
		CreditBaseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creditBaseInfoService.get(id);
		}
		if (entity == null){
			entity = new CreditBaseInfo();
		}
		return entity;
	}
	
	@ResponseBody
	@RequiresPermissions("credit:creditBaseInfo:view")
	@RequestMapping(value = {"getById"})
	public CreditBaseInfo getById(String id) {
		return get(id);
	}
	
	@RequiresPermissions("credit:creditBaseInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreditBaseInfo creditBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		
		if(creditBaseInfo.getIsDraft() == null)
		{
			creditBaseInfo.setIsDraft(CreditConstant.NO);
		}
		
		Page<CreditBaseInfo> page = creditBaseInfoService.findPage(new Page<CreditBaseInfo>(request, response), creditBaseInfo); 
		model.addAttribute("page", page);
		return "modules/credit/creditBaseInfoList";
	}

	@RequiresPermissions("credit:creditBaseInfo:view")
	@RequestMapping(value = "form")
	public String form(CreditBaseInfo creditBaseInfo, Model model) {
		Boolean IsDraft = CreditConstant.YES.equals(creditBaseInfo.getIsDraft());
		if(!StringUtils.isNotBlank(creditBaseInfo.getId()) || IsDraft){
			creditBaseInfo.setCreditStatus(CreditConstant.CREDIT_STATUS_RAISING);
		}
		
		model.addAttribute("creditBaseInfo", creditBaseInfo);
		return "modules/credit/creditBaseInfoForm";
	}

	@RequiresPermissions("credit:creditBaseInfo:edit")
	@RequestMapping(value = "save")
	public String save(CreditBaseInfo creditBaseInfo, Model model, RedirectAttributes redirectAttributes) {
		Boolean IsDraft = CreditConstant.YES.equals(creditBaseInfo.getIsDraft());
		String message = null;
		
		
		if(!IsDraft){
			if (!beanValidator(model, creditBaseInfo)){
				return form(creditBaseInfo, model);
			}
			
			CreditBaseInfo entity = creditBaseInfoService.get(creditBaseInfo.getId());
			if((!StringUtils.isNotBlank(creditBaseInfo.getId()) || CreditConstant.YES.equals(entity.getIsDraft()))){
				creditBaseInfo.setCreditStatus(CreditConstant.CREDIT_STATUS_RAISING);
				creditBaseInfo.setToRaiseMoney(creditBaseInfo.getCreditFinancingMoney());
				creditBaseInfo.setRaisedMoneyOnLine(0d);
				creditBaseInfo.setRaisedMoneyBelowLine(0d);
				creditBaseInfo.setRaisingMoney(0d);
			}
			message = "保存债权基本信息成功";
		} else{
			if(!StringUtils.isNotBlank(creditBaseInfo.getCreditName())){
				addMessage(redirectAttributes, "保存债权草稿失败，债权名称不能为空！");
			}
			
			creditBaseInfo.setCreditStatus(CreditConstant.CREDIT_STATUS_RAISE_STOP);
			message = "保存债权草稿成功";
		}
		
		creditBaseInfoService.save(creditBaseInfo);
		addMessage(redirectAttributes, message);
		return "redirect:"+Global.getAdminPath()+"/credit/creditBaseInfo/?repage";
	}
	
	@RequiresPermissions("credit:creditBaseInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CreditBaseInfo creditBaseInfo, RedirectAttributes redirectAttributes) {
		creditBaseInfoService.delete(creditBaseInfo);
		addMessage(redirectAttributes, "删除债券基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/credit/creditBaseInfo/?repage";
	}
	
	@ResponseBody
	@RequiresPermissions("credit:creditBaseInfo:edit")
	@RequestMapping(value = "checkName")
	public String checkName(String name,String id) {
		if (name !=null) {
			CreditBaseInfo creditBaseInfo = new CreditBaseInfo();
			creditBaseInfo.setCreditName(name);
			creditBaseInfo = creditBaseInfoService.getCreditBaseInfoByInfo(creditBaseInfo);	
			if(creditBaseInfo == null){
				return "true";
			}
			else if(id != null && id.equals(creditBaseInfo.getId())){
				return "true";
			}
			
		}
		return "false";
	}

}