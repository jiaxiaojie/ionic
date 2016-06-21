/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.thinkgem.jeesite.modules.entity.MarketingActivityChannelLimit;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.entity.MarketingActivityUserBehaviorLimit;
import com.thinkgem.jeesite.modules.entity.MarketingBehaviorType;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketingUtils;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityChannelLimitService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityUserBehaviorLimitService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingBehaviorTypeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 营销活动Controller
 * @author lizibo
 * @version 2015-09-09
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/marketingActivityInfo")
public class MarketingActivityInfoController extends BaseController {

	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private MarketingBehaviorTypeService marketingBehaviorTypeService;
	@Autowired
	private MarketingActivityChannelLimitService marketingActivityChannelLimitService;
	@Autowired
	private MarketingActivityUserBehaviorLimitService marketingActivityUserBehaviorLimitService;
	
	@ModelAttribute
	public MarketingActivityInfo get(@RequestParam(required=false) String acticityId) {
		MarketingActivityInfo entity = null;
		if (StringUtils.isNotBlank(acticityId)){
			entity = marketingActivityInfoService.get(acticityId);
		}
		if (entity == null){
			entity = new MarketingActivityInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:marketingActivityInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(MarketingActivityInfo marketingActivityInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MarketingActivityInfo> page = marketingActivityInfoService.findPage(new Page<MarketingActivityInfo>(request, response), marketingActivityInfo); 
		model.addAttribute("page", page);
		return "modules/marketing/marketingActivityInfoList";
	}
	
	/**
	 * 审核查看
	 * @param marketingActivityInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("marketing:marketingActivityInfo:view")
	@RequestMapping(value = "reviewForm")
	public String reviewForm(MarketingActivityInfo marketingActivityInfo, Model model) {
		Long acticityId = marketingActivityInfo.getActicityId();
		//根据活动编号查询列表
		List<MarketingActivityChannelLimit> activityChannelLimitList = marketingActivityChannelLimitService.findListByActivityId(acticityId);
		List<String> channelIdList = new ArrayList<String>();
		for(MarketingActivityChannelLimit activityChannelLimit : activityChannelLimitList){
			channelIdList.add(String.valueOf(activityChannelLimit.getChannelId()));
		}
		marketingActivityInfo.setChannelIdList(channelIdList);
		//根据活动编号查询列表
		List<MarketingActivityUserBehaviorLimit> activityUserBehaviorLimitList = marketingActivityUserBehaviorLimitService.findListByActivityCode(acticityId);
		List<String> actionTypeList = new ArrayList<String>();
		for(MarketingActivityUserBehaviorLimit activityUserBehaviorLimit : activityUserBehaviorLimitList){
			actionTypeList.add(activityUserBehaviorLimit.getActionType());
		}
		marketingActivityInfo.setActionTypeList(actionTypeList);
		model.addAttribute("marketingActivityInfo", marketingActivityInfo);
		MarketingBehaviorType marketingBehaviorType = new MarketingBehaviorType();
		//活动行为列表
		model.addAttribute("actionTypeList", marketingBehaviorTypeService.findList(marketingBehaviorType));
		return "modules/marketing/marketingActivityInfoReviewForm";
	}
	
	/**
	 * 审核列表
	 * @param marketingActivityInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("marketing:marketingActivityInfo:view")
	@RequestMapping(value = {"reviewList", ""})
	public String reviewList(MarketingActivityInfo marketingActivityInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		marketingActivityInfo.setStatus(MarketConstant.MARKETING_ACTIVITY_STATUS_CREATE);
		Page<MarketingActivityInfo> page = marketingActivityInfoService.findPage(new Page<MarketingActivityInfo>(request, response), marketingActivityInfo); 
		model.addAttribute("page", page);
		return "modules/marketing/marketingActivityInfoReviewList";
	}
	
	/**
	 * 审核
	 * @param marketingActivityInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("marketing:marketingActivityInfo:edit")
	@RequestMapping(value = "review")
	public String review(MarketingActivityInfo marketingActivityInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, marketingActivityInfo)){
			return form(marketingActivityInfo, model);
		}
		marketingActivityInfo.setReviewUserId(new Long(UserUtils.getUser().getId()));
		marketingActivityInfo.setReviewDt(new Date());
		marketingActivityInfoService.review(marketingActivityInfo);
		addMessage(redirectAttributes, "审批营销活动成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityInfo/reviewList";
	}

	@RequiresPermissions("marketing:marketingActivityInfo:view")
	@RequestMapping(value = "form")
	public String form(MarketingActivityInfo marketingActivityInfo, Model model) {
		Long acticityId = marketingActivityInfo.getActicityId();
		//根据活动编号查询列表
		List<MarketingActivityChannelLimit> activityChannelLimitList = marketingActivityChannelLimitService.findListByActivityId(acticityId);
		List<String> channelIdList = new ArrayList<String>();
		for(MarketingActivityChannelLimit activityChannelLimit : activityChannelLimitList){
			channelIdList.add(String.valueOf(activityChannelLimit.getChannelId()));
		}
		marketingActivityInfo.setChannelIdList(channelIdList);
		//根据活动编号查询列表
		List<MarketingActivityUserBehaviorLimit> activityUserBehaviorLimitList = marketingActivityUserBehaviorLimitService.findListByActivityCode(acticityId);
		List<String> actionTypeList = new ArrayList<String>();
		for(MarketingActivityUserBehaviorLimit activityUserBehaviorLimit : activityUserBehaviorLimitList){
			actionTypeList.add(activityUserBehaviorLimit.getActionType());
		}
		marketingActivityInfo.setActionTypeList(actionTypeList);
		model.addAttribute("marketingActivityInfo", marketingActivityInfo);
		MarketingBehaviorType marketingBehaviorType = new MarketingBehaviorType();
		//活动行为列表
		model.addAttribute("actionTypeList", marketingBehaviorTypeService.findList(marketingBehaviorType));
		return "modules/marketing/marketingActivityInfoForm";
	}

	@RequiresPermissions("marketing:marketingActivityInfo:edit")
	@RequestMapping(value = "save")
	public String save(MarketingActivityInfo marketingActivityInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, marketingActivityInfo)){
			return form(marketingActivityInfo, model);
		}
		marketingActivityInfo.setCreateUserId(new Long(UserUtils.getUser().getId()));
		marketingActivityInfo.setCreateDt(new Date());
		marketingActivityInfo.setStatus(MarketConstant.MARKETING_ACTIVITY_STATUS_CREATE);
		marketingActivityInfoService.save(marketingActivityInfo);
		addMessage(redirectAttributes, "保存营销活动成功");
		MarketingUtils.remove(marketingActivityInfo);
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityInfo/list";
	}
	
	@RequiresPermissions("marketing:marketingActivityInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(MarketingActivityInfo marketingActivityInfo, RedirectAttributes redirectAttributes) {
		marketingActivityInfoService.delete(marketingActivityInfo);
		addMessage(redirectAttributes, "删除营销活动成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityInfo/list";
	}

}