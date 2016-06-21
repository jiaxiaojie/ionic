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
import com.thinkgem.jeesite.modules.entity.MarketingActivityOpHis;
import com.thinkgem.jeesite.modules.entity.MarketingBehaviorType;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityOpHisService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingBehaviorTypeService;

/**
 * 营销活动操作流水Controller
 * @author lizibo
 * @version 2015-09-09
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/marketingActivityOpHis")
public class MarketingActivityOpHisController extends BaseController {

	@Autowired
	private MarketingActivityOpHisService marketingActivityOpHisService;
	@Autowired
	private MarketingBehaviorTypeService marketingBehaviorTypeService;
	
	@ModelAttribute
	public MarketingActivityOpHis get(@RequestParam(required=false) String id) {
		MarketingActivityOpHis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = marketingActivityOpHisService.get(id);
		}
		if (entity == null){
			entity = new MarketingActivityOpHis();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:marketingActivityOpHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(MarketingActivityOpHis marketingActivityOpHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MarketingActivityOpHis> page = marketingActivityOpHisService.findPage(new Page<MarketingActivityOpHis>(request, response), marketingActivityOpHis); 
		model.addAttribute("page", page);
		model.addAttribute("marketingActivityOpHis", marketingActivityOpHis);
		MarketingBehaviorType marketingBehaviorType = new MarketingBehaviorType();
		//活动行为列表
		model.addAttribute("actionTypeList", marketingBehaviorTypeService.findList(marketingBehaviorType));
		return "modules/marketing/marketingActivityOpHisList";
	}

	@RequiresPermissions("marketing:marketingActivityOpHis:view")
	@RequestMapping(value = "form")
	public String form(MarketingActivityOpHis marketingActivityOpHis, Model model) {
		model.addAttribute("marketingActivityOpHis", marketingActivityOpHis);
		return "modules/marketing/marketingActivityOpHisForm";
	}

	@RequiresPermissions("marketing:marketingActivityOpHis:edit")
	@RequestMapping(value = "save")
	public String save(MarketingActivityOpHis marketingActivityOpHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, marketingActivityOpHis)){
			return form(marketingActivityOpHis, model);
		}
		marketingActivityOpHisService.save(marketingActivityOpHis);
		addMessage(redirectAttributes, "保存营销活动操作流水成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityOpHis/?repage";
	}
	
	@RequiresPermissions("marketing:marketingActivityOpHis:edit")
	@RequestMapping(value = "delete")
	public String delete(MarketingActivityOpHis marketingActivityOpHis, RedirectAttributes redirectAttributes) {
		marketingActivityOpHisService.delete(marketingActivityOpHis);
		addMessage(redirectAttributes, "删除营销活动操作流水成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityOpHis/?repage";
	}

}