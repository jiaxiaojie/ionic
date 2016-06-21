/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.web;

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
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.entity.CurrentProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 活期项目执行快照Controller
 * @author ydt
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/current/currentProjectExecuteSnapshot")
public class CurrentProjectExecuteSnapshotController extends BaseController {

	@Autowired
	private CurrentProjectExecuteSnapshotService currentProjectExecuteSnapshotService;
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	
	@ModelAttribute
	public CurrentProjectExecuteSnapshot get(@RequestParam(required=false) String id,@RequestParam(required=false) String projectId) {
		CurrentProjectExecuteSnapshot entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentProjectExecuteSnapshotService.get(id);
		}
		else if(StringUtils.isNotBlank(projectId)){
			entity = currentProjectExecuteSnapshotService.getByProjectId(projectId);
		}
		if (entity == null){
			entity = new CurrentProjectExecuteSnapshot();
		}
		return entity;
	}
	
	@RequiresPermissions("current:currentProjectExecuteSnapshot:view")
	@RequestMapping(value = {"list", ""})
	public String list(CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentProjectExecuteSnapshot> page = currentProjectExecuteSnapshotService.findPage(new Page<CurrentProjectExecuteSnapshot>(request, response), currentProjectExecuteSnapshot); 
		model.addAttribute("page", page);
		return "modules/current/currentProjectExecuteSnapshotList";
	}

	@RequiresPermissions("current:currentProjectExecuteSnapshot:view")
	@RequestMapping(value = "form")
	public String form(CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot, Model model) {
		model.addAttribute("currentProjectExecuteSnapshot", currentProjectExecuteSnapshot);
		model.addAttribute("model", currentProjectExecuteSnapshot);
		return "modules/current/currentProjectExecuteSnapshotForm";
	}

	@RequiresPermissions("current:currentProjectExecuteSnapshot:edit")
	@RequestMapping(value = "save")
	public String save(CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectExecuteSnapshot)){
			return form(currentProjectExecuteSnapshot, model);
		}
		currentProjectExecuteSnapshotService.save(currentProjectExecuteSnapshot);
		addMessage(redirectAttributes, "保存活期项目执行快照成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectExecuteSnapshot/?repage";
	}
	
	@RequiresPermissions("current:currentProjectExecuteSnapshot:edit")
	@RequestMapping(value = "delete")
	public String delete(CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot, RedirectAttributes redirectAttributes) {
		currentProjectExecuteSnapshotService.delete(currentProjectExecuteSnapshot);
		addMessage(redirectAttributes, "删除活期项目执行快照成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectExecuteSnapshot/?repage";
	}
	
	/**
	 * 通过项目id，查找融资人余额
	 * @param currentProjectExecuteSnapshot
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("current:currentProjectExecuteSnapshot:view")
	@RequestMapping(value = "borrowerAccountInfoByProjectId")
	public AccountInfoResp borrowerAccountInfoByProjectId(CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot, Model model) {
		String platFormUserNo = null;
		try{
			CurrentProjectInfo currentProjectInfo = currentProjectInfoService.get(currentProjectExecuteSnapshot.getProjectId()+"");
			CustomerAccount customerAccount = customerAccountService.get(currentProjectInfo.getBorrowerAccountId());
			platFormUserNo =  customerAccount.getPlatformUserNo();
		}catch(Exception e){
			
		}
		AccountInfoResp resp = yeepayCommonHandler.accountInfo(platFormUserNo);
		return resp;
	}
	

}