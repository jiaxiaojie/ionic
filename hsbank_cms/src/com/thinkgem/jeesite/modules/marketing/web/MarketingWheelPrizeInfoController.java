/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.entity.MarketingWheelPrizeInfo;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingWheelPrizeInfoService;

/**
 * 大转盘奖品信息Controller
 * @author ydt
 * @version 2015-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/marketingWheelPrizeInfo")
public class MarketingWheelPrizeInfoController extends BaseController {

	@Autowired
	private MarketingWheelPrizeInfoService marketingWheelPrizeInfoService;
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	
	@ModelAttribute
	public MarketingWheelPrizeInfo get(@RequestParam(required=false) String id) {
		MarketingWheelPrizeInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = marketingWheelPrizeInfoService.get(id);
		}
		if (entity == null){
			entity = new MarketingWheelPrizeInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:marketingWheelPrizeInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(MarketingWheelPrizeInfo marketingWheelPrizeInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MarketingWheelPrizeInfo> page = new Page<MarketingWheelPrizeInfo>(request, response);
		boolean canMadePrizeInstance = false;
		if(marketingWheelPrizeInfo.getActivityId() != null) {
			marketingWheelPrizeInfo.setActivityId(marketingWheelPrizeInfo.getActivityId());
			page = marketingWheelPrizeInfoService.findPage(new Page<MarketingWheelPrizeInfo>(request, response), marketingWheelPrizeInfo); 
			if(page.getCount() > 0) {
				canMadePrizeInstance = !marketingWheelPrizeInfoService.hasMadePrizeInstance(marketingWheelPrizeInfo.getActivityId());
			}
		}
		List<MarketingActivityInfo> activityList = marketingActivityInfoService.findListByStatus(MarketConstant.MARKETING_ACTIVITY_STATUS_PASS);
		model.addAttribute("activityList", activityList);
		model.addAttribute("marketingWheelPrizeInfo", marketingWheelPrizeInfo);
		model.addAttribute("page", page);
		model.addAttribute("canMadePrizeInstance", canMadePrizeInstance);
		return "modules/marketing/marketingWheelPrizeInfoList";
	}

	@RequiresPermissions("marketing:marketingWheelPrizeInfo:view")
	@RequestMapping(value = "form")
	public String form(MarketingWheelPrizeInfo marketingWheelPrizeInfo, Model model) {
		model.addAttribute("marketingWheelPrizeInfo", marketingWheelPrizeInfo);
		List<MarketingActivityInfo> activityList = marketingActivityInfoService.findListByStatus(MarketConstant.MARKETING_ACTIVITY_STATUS_PASS);
		model.addAttribute("activityList", activityList);
		return "modules/marketing/marketingWheelPrizeInfoForm";
	}

	@RequiresPermissions("marketing:marketingWheelPrizeInfo:edit")
	@RequestMapping(value = "save")
	public String save(MarketingWheelPrizeInfo marketingWheelPrizeInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, marketingWheelPrizeInfo)){
			return form(marketingWheelPrizeInfo, model);
		}
		marketingWheelPrizeInfoService.save(marketingWheelPrizeInfo);
		addMessage(redirectAttributes, "保存大转盘中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingWheelPrizeInfo/?repage";
	}
	
	@RequiresPermissions("marketing:marketingWheelPrizeInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(MarketingWheelPrizeInfo marketingWheelPrizeInfo, RedirectAttributes redirectAttributes) {
		marketingWheelPrizeInfoService.delete(marketingWheelPrizeInfo);
		addMessage(redirectAttributes, "删除大转盘中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingWheelPrizeInfo/?repage";
	}
	
	@RequiresPermissions("marketing:marketingWheelPrizeInfo:edit")
	@RequestMapping(value = "makePrizeInstance")
	@ResponseBody
	public boolean makePrizeInstance(Long activityId) {
		try {
			marketingWheelPrizeInfoService.makePrizeInstance(activityId);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	@RequiresPermissions("marketing:marketingWheelPrizeInfo:edit")
	@RequestMapping(value = "setDefaultPrize")
	@ResponseBody
	public String setDefaultPrize(String id) {
		marketingWheelPrizeInfoService.setDefaultPrize(id);
		return "success";
	}
}