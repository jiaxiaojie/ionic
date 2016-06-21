/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.web;

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
import com.thinkgem.jeesite.modules.entity.UserFeedbackInfo;
import com.thinkgem.jeesite.modules.feedback.service.UserFeedbackInfoService;

/**
 * 用户意见反馈Controller
 * @author lizibo
 * @version 2015-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/feedback/userFeedbackInfo")
public class UserFeedbackInfoController extends BaseController {

	@Autowired
	private UserFeedbackInfoService userFeedbackInfoService;
	
	@ModelAttribute
	public UserFeedbackInfo get(@RequestParam(required=false) String id) {
		UserFeedbackInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userFeedbackInfoService.get(id);
		}
		if (entity == null){
			entity = new UserFeedbackInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("feedback:userFeedbackInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserFeedbackInfo userFeedbackInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserFeedbackInfo> page = userFeedbackInfoService.findPage(new Page<UserFeedbackInfo>(request, response), userFeedbackInfo); 
		model.addAttribute("page", page);
		return "modules/feedback/userFeedbackInfoList";
	}

	@RequiresPermissions("feedback:userFeedbackInfo:view")
	@RequestMapping(value = "form")
	public String form(UserFeedbackInfo userFeedbackInfo, Model model) {
		model.addAttribute("userFeedbackInfo", userFeedbackInfo);
		return "modules/feedback/userFeedbackInfoForm";
	}
	
	/**
	 * 意见反馈查看
	 * @param userFeedbackInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("feedback:userFeedbackInfo:view")
	@RequestMapping(value = "view")
	public String view(UserFeedbackInfo userFeedbackInfo, Model model) {
		model.addAttribute("userFeedbackInfo", userFeedbackInfo);
		return "modules/feedback/userFeedbackInfoView";
	}
	
	/**
	 * 意见反馈审核
	 * @param userFeedbackInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("feedback:userFeedbackInfo:view")
	@RequestMapping(value = "reviewform")
	public String reviewform(UserFeedbackInfo userFeedbackInfo, Model model) {
		model.addAttribute("userFeedbackInfo", userFeedbackInfo);
		return "modules/feedback/userFeedbackInfoReviewForm";
	}

	@RequiresPermissions("feedback:userFeedbackInfo:edit")
	@RequestMapping(value = "save")
	public String save(UserFeedbackInfo userFeedbackInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userFeedbackInfo)){
			return form(userFeedbackInfo, model);
		}
		userFeedbackInfoService.save(userFeedbackInfo);
		addMessage(redirectAttributes, "保存用户意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/feedback/userFeedbackInfo/?repage";
	}
	
	@RequiresPermissions("feedback:userFeedbackInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(UserFeedbackInfo userFeedbackInfo, RedirectAttributes redirectAttributes) {
		userFeedbackInfoService.delete(userFeedbackInfo);
		addMessage(redirectAttributes, "删除用户意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/feedback/userFeedbackInfo/?repage";
	}

}