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
import com.thinkgem.jeesite.modules.entity.MarketingActivityUserBehaviorLimit;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityUserBehaviorLimitService;

/**
 * 会员行为限制Controller
 * @author lizibo
 * @version 2015-09-10
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/marketingActivityUserBehaviorLimit")
public class MarketingActivityUserBehaviorLimitController extends BaseController {

	@Autowired
	private MarketingActivityUserBehaviorLimitService marketingActivityUserBehaviorLimitService;
	
	@ModelAttribute
	public MarketingActivityUserBehaviorLimit get(@RequestParam(required=false) String id) {
		MarketingActivityUserBehaviorLimit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = marketingActivityUserBehaviorLimitService.get(id);
		}
		if (entity == null){
			entity = new MarketingActivityUserBehaviorLimit();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:marketingActivityUserBehaviorLimit:view")
	@RequestMapping(value = {"list", ""})
	public String list(MarketingActivityUserBehaviorLimit marketingActivityUserBehaviorLimit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MarketingActivityUserBehaviorLimit> page = marketingActivityUserBehaviorLimitService.findPage(new Page<MarketingActivityUserBehaviorLimit>(request, response), marketingActivityUserBehaviorLimit); 
		model.addAttribute("page", page);
		return "modules/marketing/marketingActivityUserBehaviorLimitList";
	}

	@RequiresPermissions("marketing:marketingActivityUserBehaviorLimit:view")
	@RequestMapping(value = "form")
	public String form(MarketingActivityUserBehaviorLimit marketingActivityUserBehaviorLimit, Model model) {
		model.addAttribute("marketingActivityUserBehaviorLimit", marketingActivityUserBehaviorLimit);
		return "modules/marketing/marketingActivityUserBehaviorLimitForm";
	}

	@RequiresPermissions("marketing:marketingActivityUserBehaviorLimit:edit")
	@RequestMapping(value = "save")
	public String save(MarketingActivityUserBehaviorLimit marketingActivityUserBehaviorLimit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, marketingActivityUserBehaviorLimit)){
			return form(marketingActivityUserBehaviorLimit, model);
		}
		marketingActivityUserBehaviorLimitService.save(marketingActivityUserBehaviorLimit);
		addMessage(redirectAttributes, "保存会员行为限制成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityUserBehaviorLimit/?repage";
	}
	
	@RequiresPermissions("marketing:marketingActivityUserBehaviorLimit:edit")
	@RequestMapping(value = "delete")
	public String delete(MarketingActivityUserBehaviorLimit marketingActivityUserBehaviorLimit, RedirectAttributes redirectAttributes) {
		marketingActivityUserBehaviorLimitService.delete(marketingActivityUserBehaviorLimit);
		addMessage(redirectAttributes, "删除会员行为限制成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityUserBehaviorLimit/?repage";
	}

}