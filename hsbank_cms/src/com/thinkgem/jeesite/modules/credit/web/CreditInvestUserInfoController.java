/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.credit.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.entity.CreditInvestUserInfo;
import com.thinkgem.jeesite.modules.credit.service.CreditInvestUserInfoService;

/**
 * 债权投资用户信息Controller
 * @author wanduanrui
 * @version 2016-03-30
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditInvestUserInfo")
public class CreditInvestUserInfoController extends BaseController {

	@Autowired
	private CreditInvestUserInfoService creditInvestUserInfoService;
	
	@ModelAttribute
	public CreditInvestUserInfo get(@RequestParam(required=false) String id) {
		CreditInvestUserInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creditInvestUserInfoService.get(id);
		}
		if (entity == null){
			entity = new CreditInvestUserInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creditInvestUserInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreditInvestUserInfo creditInvestUserInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreditInvestUserInfo> page = creditInvestUserInfoService.findPage(new Page<CreditInvestUserInfo>(request, response), creditInvestUserInfo); 
		model.addAttribute("page", page);
		return "modules/credit/creditInvestUserInfoList";
	}

	@RequiresPermissions("credit:creditInvestUserInfo:view")
	@RequestMapping(value = "form")
	public String form(CreditInvestUserInfo creditInvestUserInfo, Model model) {
		model.addAttribute("creditInvestUserInfo", creditInvestUserInfo);
		return "modules/credit/creditInvestUserInfoForm";
	}

	@RequiresPermissions("credit:creditInvestUserInfo:edit")
	@RequestMapping(value = "save")
	public String save(CreditInvestUserInfo creditInvestUserInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creditInvestUserInfo)){
			return form(creditInvestUserInfo, model);
		}
		
		creditInvestUserInfo.initInfoByIdNumber();
		creditInvestUserInfoService.save(creditInvestUserInfo);
		addMessage(redirectAttributes, "保存债权投资用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/credit/creditInvestUserInfo/?repage";
	}
	
	@RequiresPermissions("credit:creditInvestUserInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CreditInvestUserInfo creditInvestUserInfo, RedirectAttributes redirectAttributes) {
		try{
			creditInvestUserInfoService.delete(creditInvestUserInfo);
			addMessage(redirectAttributes, "删除债权投资用户信息成功");
		}catch(Exception e){
			addMessage(redirectAttributes, e.getMessage());
		}
		
		return "redirect:"+Global.getAdminPath()+"/credit/creditInvestUserInfo/?repage";
	}

	@RequiresPermissions("credit:creditInvestUserInfo:view")
	@RequestMapping(value = {"repaymentList"})
	public String repaymentList(CreditInvestUserInfo creditInvestUserInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(creditInvestUserInfo.getCreditMachineAccount().getBeginExpiringDate() == null){
			creditInvestUserInfo.getCreditMachineAccount().setBeginExpiringDate(DateUtils.parseDate(DateUtils.getDate("yyyy-MM-dd")));
		}
		if(creditInvestUserInfo.getCreditMachineAccount().getEndExpiringDate() == null){
			creditInvestUserInfo.getCreditMachineAccount().setEndExpiringDate(DateUtils.dateAddDay(DateUtils.parseDate(DateUtils.getDate("yyyy-MM-dd")), 9));
		}
		Page<CreditInvestUserInfo> page = creditInvestUserInfoService.repaymentRemindPage(new Page<CreditInvestUserInfo>(request, response), creditInvestUserInfo); 
		model.addAttribute("page", page);
		return "modules/credit/creditRepaymentInfoList";
	}
	
	@RequiresPermissions("credit:creditInvestUserInfo:view")
	@RequestMapping(value = "birthdayRemindList")
	public String birthdayRemindList(CreditInvestUserInfo creditInvestUserInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(!StringUtils.isNotBlank(creditInvestUserInfo.getBeginInvestorBirthday())){
			creditInvestUserInfo.setBeginInvestorBirthday(DateUtils.getDate("MM-dd"));
		}
		if(!StringUtils.isNotBlank(creditInvestUserInfo.getEndInvestorBirthday())){
			creditInvestUserInfo.setEndInvestorBirthday(DateUtils.formatDate(DateUtils.dateAddDay(new Date() , 9),"MM-dd"));
		}
		Page<CreditInvestUserInfo> page = creditInvestUserInfoService.birthdayRemindPage(new Page<CreditInvestUserInfo>(request, response), creditInvestUserInfo); 
		model.addAttribute("page", page);
		return "modules/credit/birthdayRemindList";
	}
	
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("credit:creditInvestUserInfo:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CreditInvestUserInfo creditInvestUserInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CreditInvestUserInfo> page = creditInvestUserInfoService.findPage(new Page<CreditInvestUserInfo>(request, response, -1), creditInvestUserInfo);
    		new ExportExcel("用户数据", CreditInvestUserInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	@ResponseBody
	@RequiresPermissions("credit:creditInvestUserInfo:edit")
	@RequestMapping(value = "checkIdNumber")
	public Boolean checkIdNumber(String idNumber,String idType,String id) {
		if (StringUtils.isNotBlank(idNumber) && StringUtils.isNotBlank(idType)) {
			CreditInvestUserInfo creditInvestUserInfo = new CreditInvestUserInfo();
			creditInvestUserInfo.setIdNumber(idNumber);
			creditInvestUserInfo.setIdType(idType);
			creditInvestUserInfo = creditInvestUserInfoService.getCreditInvestUserInfoByInfo(creditInvestUserInfo);	
			if(creditInvestUserInfo == null){
				return true;
			}
			else if(id != null && id.equals(creditInvestUserInfo.getId())){
				return true;
			}else{
				return false;
			}
			
		}
		return true;
	}
}