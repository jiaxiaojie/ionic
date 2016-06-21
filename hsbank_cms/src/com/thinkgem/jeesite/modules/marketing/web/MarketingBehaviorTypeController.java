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
import com.thinkgem.jeesite.modules.entity.MarketingBehaviorType;
import com.thinkgem.jeesite.modules.marketing.service.MarketingBehaviorTypeService;

/**
 * 会员行为编码Controller
 * @author lizibo
 * @version 2015-09-10
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/marketingBehaviorType")
public class MarketingBehaviorTypeController extends BaseController {

	@Autowired
	private MarketingBehaviorTypeService marketingBehaviorTypeService;
	
	@ModelAttribute
	public MarketingBehaviorType get(@RequestParam(required=false) String id) {
		MarketingBehaviorType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = marketingBehaviorTypeService.get(id);
		}
		if (entity == null){
			entity = new MarketingBehaviorType();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:marketingBehaviorType:view")
	@RequestMapping(value = {"list", ""})
	public String list(MarketingBehaviorType marketingBehaviorType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MarketingBehaviorType> page = marketingBehaviorTypeService.findPage(new Page<MarketingBehaviorType>(request, response), marketingBehaviorType); 
		model.addAttribute("page", page);
		return "modules/marketing/marketingBehaviorTypeList";
	}

	@RequiresPermissions("marketing:marketingBehaviorType:view")
	@RequestMapping(value = "form")
	public String form(MarketingBehaviorType marketingBehaviorType, Model model) {
		model.addAttribute("marketingBehaviorType", marketingBehaviorType);
		return "modules/marketing/marketingBehaviorTypeForm";
	}

	@RequiresPermissions("marketing:marketingBehaviorType:edit")
	@RequestMapping(value = "save")
	public String save(MarketingBehaviorType marketingBehaviorType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, marketingBehaviorType)){
			return form(marketingBehaviorType, model);
		}
		marketingBehaviorTypeService.save(marketingBehaviorType);
		addMessage(redirectAttributes, "保存会员行为编码成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingBehaviorType/?repage";
	}
	
	@RequiresPermissions("marketing:marketingBehaviorType:edit")
	@RequestMapping(value = "delete")
	public String delete(MarketingBehaviorType marketingBehaviorType, RedirectAttributes redirectAttributes) {
		marketingBehaviorTypeService.delete(marketingBehaviorType);
		addMessage(redirectAttributes, "删除会员行为编码成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingBehaviorType/?repage";
	}

}