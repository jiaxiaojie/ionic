/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.web;

import java.util.Date;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectRedemptionApplyService;
import com.thinkgem.jeesite.modules.entity.CurrentProjectRedemptionApply;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 活期产品赎回申请Controller
 * @author ydt
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/current/currentProjectRedemptionApply")
public class CurrentProjectRedemptionApplyController extends BaseController {
	@Autowired
	private CurrentProjectRedemptionApplyService currentProjectRedemptionApplyService;
	@ModelAttribute
	public CurrentProjectRedemptionApply get(@RequestParam(required=false) String id) {
		CurrentProjectRedemptionApply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentProjectRedemptionApplyService.get(id);
		}
		if (entity == null){
			entity = new CurrentProjectRedemptionApply();
		}
		return entity;
	}
	
	@RequiresPermissions("current:currentProjectRedemptionApply:view")
	@RequestMapping(value = {"list", ""})
	public String list(CurrentProjectRedemptionApply currentProjectRedemptionApply, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentProjectRedemptionApply> page = currentProjectRedemptionApplyService.findPage(new Page<CurrentProjectRedemptionApply>(request, response), currentProjectRedemptionApply); 
		model.addAttribute("page", page);
		return "modules/current/currentProjectRedemptionApplyList";
	}
	/**
	 * 赎回审批列表
	 * @param currentProjectRedemptionApply
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("current:currentProjectRedemptionApply:review")
	@RequestMapping(value = {"reviewList"})
	public String reviewList(CurrentProjectRedemptionApply currentProjectRedemptionApply, HttpServletRequest request, HttpServletResponse response, Model model) {
		currentProjectRedemptionApply.setStatus(currentProjectRedemptionApply.getStatus());
		Page<CurrentProjectRedemptionApply> temppage = new Page<CurrentProjectRedemptionApply>(request, response);
		temppage.setOrderBy("statusTop0");
		Page<CurrentProjectRedemptionApply> page = currentProjectRedemptionApplyService.findPage(temppage, currentProjectRedemptionApply); 
		model.addAttribute("page", page);
		return "modules/current/currentProjectRedemptionApplyReviewList";
	}
	/**
	 * 审核查看列表
	 * @param currentProjectRedemptionApply
	 * @param model
	 * @return
	 */
	@RequiresPermissions("current:currentProjectRedemptionApply:review")
	@RequestMapping(value = "reviewForm")
	public String reviewForm(CurrentProjectRedemptionApply currentProjectRedemptionApply, Model model) {
		model.addAttribute("currentProjectRedemptionApply", currentProjectRedemptionApply);
		return "modules/current/currentProjectRedemptionApplyReviewForm";
	}
	/**
	 * 已审核通过列表(auditthrough(审核通过))
	 * @param currentProjectRedemptionApply
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("current:currentProjectRedemptionApply:auditthrough")
	@RequestMapping(value = {"auditthrough", ""})
	public String auditthrough(CurrentProjectRedemptionApply currentProjectRedemptionApply, HttpServletRequest request, HttpServletResponse response, Model model) {
		currentProjectRedemptionApply.setStatus(CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_REVIEW_PASS);
		Page<CurrentProjectRedemptionApply> page = currentProjectRedemptionApplyService.findPage(new Page<CurrentProjectRedemptionApply>(request, response), currentProjectRedemptionApply); 
		model.addAttribute("page", page);
		return "modules/current/currentProjectRedemptionApplyList";
	}
	
	/**
	 * 赎回审审批操作
	 * @param currentProjectRedemptionApply
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("current:currentProjectRedemptionApply:review")
	@RequestMapping(value = "review")
	public String reviewsave(CurrentProjectRedemptionApply currentProjectRedemptionApply, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectRedemptionApply)){
			return reviewForm(currentProjectRedemptionApply, model);
		}
		String status = currentProjectRedemptionApply.getStatus();
		currentProjectRedemptionApply =currentProjectRedemptionApplyService.get(currentProjectRedemptionApply);
		currentProjectRedemptionApply.setStatus(status);
		
		currentProjectRedemptionApply.setReviewDt(new Date());
        currentProjectRedemptionApply.setReviewUserId(new Long(UserUtils.getUser().getId()));
     /*   currentProjectRedemptionApply.setFinishDt(new Date());*/
		currentProjectRedemptionApplyService.review(currentProjectRedemptionApply);
		//赎回总额减去申请赎回本金
		if((CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_STATUS_FAILED).equals(status)){
			//CurrentProjectHoldInfo currentProjectHoldInfo = currentProjectHoldInfoservice.get(String.valueOf(currentProjectRedemptionApply.getHoldId())); 
			currentProjectRedemptionApplyService.updatePrincipal(currentProjectRedemptionApply.getId());
		}
		addMessage(redirectAttributes, "赎回审批成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectRedemptionApply/reviewList";
	}
	
	
	@RequiresPermissions("current:currentProjectRedemptionApply:view")
	@RequestMapping(value = "form")
	public String form(CurrentProjectRedemptionApply currentProjectRedemptionApply, Model model) {
		model.addAttribute("currentProjectRedemptionApply", currentProjectRedemptionApply);
		return "modules/current/currentProjectRedemptionApplyForm";
	}

	@RequiresPermissions("current:currentProjectRedemptionApply:edit")
	@RequestMapping(value = "save")
	public String save(CurrentProjectRedemptionApply currentProjectRedemptionApply, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectRedemptionApply)){
			return form(currentProjectRedemptionApply, model);
		}
		currentProjectRedemptionApplyService.save(currentProjectRedemptionApply);
		addMessage(redirectAttributes, "保存活期产品赎回申请成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectRedemptionApply/?repage";
	}
	
	@RequiresPermissions("current:currentProjectRedemptionApply:edit")
	@RequestMapping(value = "delete")
	public String delete(CurrentProjectRedemptionApply currentProjectRedemptionApply, RedirectAttributes redirectAttributes) {
		currentProjectRedemptionApplyService.delete(currentProjectRedemptionApply);
		addMessage(redirectAttributes, "删除活期产品赎回申请成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectRedemptionApply/?repage";
	}

}