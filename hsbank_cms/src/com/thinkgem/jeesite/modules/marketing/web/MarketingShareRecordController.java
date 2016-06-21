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
import com.thinkgem.jeesite.modules.entity.MarketingShareRecord;
import com.thinkgem.jeesite.modules.marketing.service.MarketingShareRecordService;

/**
 * 营销活动分享记录Controller
 * @author lzb
 * @version 2016-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/marketingShareRecord")
public class MarketingShareRecordController extends BaseController {

	@Autowired
	private MarketingShareRecordService marketingShareRecordService;
	
	@ModelAttribute
	public MarketingShareRecord get(@RequestParam(required=false) String id) {
		MarketingShareRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = marketingShareRecordService.get(id);
		}
		if (entity == null){
			entity = new MarketingShareRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:marketingShareRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(MarketingShareRecord marketingShareRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MarketingShareRecord> page = marketingShareRecordService.findPage(new Page<MarketingShareRecord>(request, response), marketingShareRecord); 
		model.addAttribute("page", page);
		return "modules/marketing/marketingShareRecordList";
	}

	@RequiresPermissions("marketing:marketingShareRecord:view")
	@RequestMapping(value = "form")
	public String form(MarketingShareRecord marketingShareRecord, Model model) {
		model.addAttribute("marketingShareRecord", marketingShareRecord);
		return "modules/marketing/marketingShareRecordForm";
	}

	@RequiresPermissions("marketing:marketingShareRecord:edit")
	@RequestMapping(value = "save")
	public String save(MarketingShareRecord marketingShareRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, marketingShareRecord)){
			return form(marketingShareRecord, model);
		}
		marketingShareRecordService.save(marketingShareRecord);
		addMessage(redirectAttributes, "保存营销活动分享记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingShareRecord/?repage";
	}
	
	@RequiresPermissions("marketing:marketingShareRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(MarketingShareRecord marketingShareRecord, RedirectAttributes redirectAttributes) {
		marketingShareRecordService.delete(marketingShareRecord);
		addMessage(redirectAttributes, "删除营销活动分享记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingShareRecord/?repage";
	}

}