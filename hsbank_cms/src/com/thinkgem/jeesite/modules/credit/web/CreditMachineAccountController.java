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
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.CreditMachineAccount;
import com.thinkgem.jeesite.modules.credit.service.CreditMachineAccountService;

/**
 * 债权台账Controller
 * @author wanduanrui
 * @version 2016-03-30
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditMachineAccount")
public class CreditMachineAccountController extends BaseController {

	@Autowired
	private CreditMachineAccountService creditMachineAccountService;
	
	@ModelAttribute
	public CreditMachineAccount get(@RequestParam(required=false) String id) {
		CreditMachineAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creditMachineAccountService.get(id);
		}
		if (entity == null){
			entity = new CreditMachineAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creditMachineAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreditMachineAccount creditMachineAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreditMachineAccount> page = creditMachineAccountService.findPage(new Page<CreditMachineAccount>(request, response), creditMachineAccount); 
		model.addAttribute("page", page);
		return "modules/credit/creditMachineAccountList";
	}

	@RequiresPermissions("credit:creditMachineAccount:view")
	@RequestMapping(value = "form")
	public String form(CreditMachineAccount creditMachineAccount, Model model) {
		model.addAttribute("creditMachineAccount", creditMachineAccount);
		return "modules/credit/creditMachineAccountForm";
	}

	@RequiresPermissions("credit:creditMachineAccount:edit")
	@RequestMapping(value = "save")
	public String save(CreditMachineAccount creditMachineAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creditMachineAccount)){
			return form(creditMachineAccount, model);
		}
		creditMachineAccount.setExpiringDate(DateUtils.addMonths(creditMachineAccount.getValueDate(), creditMachineAccount.getInvestmentHorizon()));
		try{
			creditMachineAccountService.saveAndUpdateCreditRaisedMoney(creditMachineAccount);
			addMessage(redirectAttributes, "保存债权台账成功");
		}catch(ServiceException e){
			addMessage(redirectAttributes, "保存债权台账失败："+e.getMessage());
		}
		
		return "redirect:"+Global.getAdminPath()+"/credit/creditMachineAccount/?repage";
	}
	
	@RequiresPermissions("credit:creditMachineAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(CreditMachineAccount creditMachineAccount, RedirectAttributes redirectAttributes) {
		creditMachineAccountService.delete(creditMachineAccount);
		addMessage(redirectAttributes, "删除债权台账成功");
		return "redirect:"+Global.getAdminPath()+"/credit/creditMachineAccount/?repage";
	}
	
	@ResponseBody
	@RequiresPermissions("credit:creditMachineAccount:edit")
	@RequestMapping(value = "checkContractNo")
	public String checkContractNo(String contractNo,String id) {
		if (contractNo !=null) {
			CreditMachineAccount creditMachineAccount = new CreditMachineAccount();
			creditMachineAccount.setContractNo(contractNo);
			creditMachineAccount = creditMachineAccountService.getCreditMachineAccountByInfo(creditMachineAccount);	
			if(creditMachineAccount == null){
				return "true";
			}
			else if(id != null && id.equals(creditMachineAccount.getId())){
				return "true";
			}
			
		}
		return "false";
	}

}