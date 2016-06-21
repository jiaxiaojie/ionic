/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.web;

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
import com.thinkgem.jeesite.modules.entity.MarketingActivityChannelLimit;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityChannelLimitService;

/**
 * 活动渠道限制Controller
 * @author lizibo
 * @version 2015-09-10
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/marketingActivityChannelLimit")
public class MarketingActivityChannelLimitController extends BaseController {

	@Autowired
	private MarketingActivityChannelLimitService marketingActivityChannelLimitService;
	
	@ModelAttribute
	public MarketingActivityChannelLimit get(@RequestParam(required=false) String id) {
		MarketingActivityChannelLimit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = marketingActivityChannelLimitService.get(id);
		}
		if (entity == null){
			entity = new MarketingActivityChannelLimit();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:marketingActivityChannelLimit:view")
	@RequestMapping(value = {"list", ""})
	public String list(MarketingActivityChannelLimit marketingActivityChannelLimit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MarketingActivityChannelLimit> page = marketingActivityChannelLimitService.findPage(new Page<MarketingActivityChannelLimit>(request, response), marketingActivityChannelLimit); 
		model.addAttribute("page", page);
		return "modules/marketing/marketingActivityChannelLimitList";
	}

	@RequiresPermissions("marketing:marketingActivityChannelLimit:view")
	@RequestMapping(value = "form")
	public String form(MarketingActivityChannelLimit marketingActivityChannelLimit, Model model) {
		model.addAttribute("marketingActivityChannelLimit", marketingActivityChannelLimit);
		return "modules/marketing/marketingActivityChannelLimitForm";
	}

	@RequiresPermissions("marketing:marketingActivityChannelLimit:edit")
	@RequestMapping(value = "save")
	public String save(MarketingActivityChannelLimit marketingActivityChannelLimit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, marketingActivityChannelLimit)){
			return form(marketingActivityChannelLimit, model);
		}
		marketingActivityChannelLimitService.save(marketingActivityChannelLimit);
		addMessage(redirectAttributes, "保存活动渠道限制成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityChannelLimit/?repage";
	}
	
	@RequiresPermissions("marketing:marketingActivityChannelLimit:edit")
	@RequestMapping(value = "delete")
	public String delete(MarketingActivityChannelLimit marketingActivityChannelLimit, RedirectAttributes redirectAttributes) {
		marketingActivityChannelLimitService.delete(marketingActivityChannelLimit);
		addMessage(redirectAttributes, "删除活动渠道限制成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityChannelLimit/?repage";
	}

}