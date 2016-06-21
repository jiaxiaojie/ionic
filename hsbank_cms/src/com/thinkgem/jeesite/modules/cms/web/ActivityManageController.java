/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web;

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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.Activity;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.cms.ActivityConstant;
import com.thinkgem.jeesite.modules.cms.service.ActivityService;

/**
 * 活动Controller
 * @author wanduanrui
 * @version 2015-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/content/activity")
public class ActivityManageController extends BaseController {

	@Autowired
	private ActivityService activityService;
	
	@ModelAttribute
	public Activity get(@RequestParam(required=false) String id) {
		Activity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = activityService.get(id);
		}
		if (entity == null){
			entity = new Activity();
		}
		return entity;
	}
	
	@RequiresPermissions("content:activity:view")
	@RequestMapping(value = {"list", ""})
	public String list(Activity activity, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<Activity> page = new Page<Activity>(request, response);
		page.setOrderBy("create_dt desc");
		page = activityService.findPage(page, activity); 
		model.addAttribute("page", page);
		return "modules/cms/activityList";
	}

	@RequiresPermissions("content:activity:view")
	@RequestMapping(value = "form")
	public String form(Activity activity, Model model) {
		model.addAttribute("activity", activity);
		return "modules/cms/activityForm";
	}

	@RequiresPermissions("content:activity:edit")
	@RequestMapping(value = "save")
	public String save(Activity activity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, activity)){
			return form(activity, model);
		}
		Activity lastActivity = activityService.findLast();
		
		activity.setActivityStatus(ActivityConstant.ACTIVITY_STATUS_CREATE);
		activity.setCreateUserId(UserUtils.getUser().getId());
		activity.setCreateDt(new Date());
		activity.setSort(lastActivity==null?0:Integer.parseInt(lastActivity.getId())*10);
		activityService.save(activity);
		
		addMessage(redirectAttributes, "保存活动成功");
		
		return "redirect:"+Global.getAdminPath()+"/content/activity/?repage";
	}
	
	@RequiresPermissions("content:activity:edit")
	@RequestMapping(value = "delete")
	public String delete(Activity activity, RedirectAttributes redirectAttributes) {
		activityService.delete(activity);
		addMessage(redirectAttributes, "删除活动成功");
		return "redirect:"+Global.getAdminPath()+"/content/activity/?repage";
	}
	
	/**
	 * 审批列表
	 * @param activity
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("content:activity:review:view")
	@RequestMapping(value = {"reviewList"})
	public String reviewList(Activity activity, HttpServletRequest request, HttpServletResponse response, Model model) {
		activity.setActivityStatus(ActivityConstant.ACTIVITY_STATUS_CREATE);
		Page<Activity> page = activityService.findPage(new Page<Activity>(request, response), activity); 
		model.addAttribute("page", page);
		model.addAttribute("method", "review");
		return "modules/cms/activityReviewList";
	}
	
	/**
	 * 审批
	 * @param 
	 * @param model
	 * @param 
	 * @return
	 */
	@RequiresPermissions("content:activity:review:edit")
	@RequestMapping(value = "review")
	public String review(Activity activity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, activity)){
			return form(activity, model);
		}
		activity.setReviewUserId(UserUtils.getUser().getId());
		activity.setReviewDt(new Date());
		activityService.save(activity);
		addMessage(redirectAttributes, "审批活动成功");
		return "redirect:"+Global.getAdminPath()+"/content/activity/reviewList";
	}
	
	/**
	 * 审批活动明细
	 * @param activity
	 * @param model
	 * @return
	 */
	@RequiresPermissions("content:activity:review:edit")
	@RequestMapping(value = "reviewForm")
	public String reviewForm(Activity activity, Model model) {
		model.addAttribute("activity", activity);
		return "modules/cms/activityReviewForm";
	}
	
	/**
	 * 活动排序列表
	 * @param activity
	 * @param model
	 * @return
	 */
	@RequiresPermissions("content:activity:view")
	@RequestMapping(value = "sortList")
	public String sortList(Activity activity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Activity> page = new Page<Activity>(request, response);
		page.setOrderBy("create_dt desc");
		//activity.setActivityStatus(ActivityConstant.ACTIVITY_STATUS_PASS);
		page = activityService.findPage(page, activity); 
		model.addAttribute("page", page);
		return "modules/cms/activitySortList";
	}
	
	/**
	 * 批量修改栏目排序
	 */
	@RequiresPermissions("content:activity:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
    	int len = ids.length;
    	Activity[] entitys = new Activity[len];
    	for (int i = 0; i < len; i++) {
    		entitys[i] = activityService.get(ids[i]);
    		entitys[i].setSort(sorts[i]);
    		activityService.save(entitys[i]);
    	}
    	addMessage(redirectAttributes, "保存活动排序成功!");
		return "redirect:" + adminPath + "/content/activity/sortList";
	}

}