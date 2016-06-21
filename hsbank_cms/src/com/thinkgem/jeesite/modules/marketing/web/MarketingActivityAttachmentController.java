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
import com.thinkgem.jeesite.modules.entity.MarketingActivityAttachment;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityAttachmentService;

/**
 * 活动关联附件Controller
 * @author lizibo
 * @version 2015-09-09
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/marketingActivityAttachment")
public class MarketingActivityAttachmentController extends BaseController {

	@Autowired
	private MarketingActivityAttachmentService marketingActivityAttachmentService;
	
	@ModelAttribute
	public MarketingActivityAttachment get(@RequestParam(required=false) String id) {
		MarketingActivityAttachment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = marketingActivityAttachmentService.get(id);
		}
		if (entity == null){
			entity = new MarketingActivityAttachment();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:marketingActivityAttachment:view")
	@RequestMapping(value = {"list", ""})
	public String list(MarketingActivityAttachment marketingActivityAttachment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MarketingActivityAttachment> page = marketingActivityAttachmentService.findPage(new Page<MarketingActivityAttachment>(request, response), marketingActivityAttachment); 
		model.addAttribute("page", page);
		return "modules/marketing/marketingActivityAttachmentList";
	}

	@RequiresPermissions("marketing:marketingActivityAttachment:view")
	@RequestMapping(value = "form")
	public String form(MarketingActivityAttachment marketingActivityAttachment, Model model) {
		model.addAttribute("marketingActivityAttachment", marketingActivityAttachment);
		return "modules/marketing/marketingActivityAttachmentForm";
	}

	@RequiresPermissions("marketing:marketingActivityAttachment:edit")
	@RequestMapping(value = "save")
	public String save(MarketingActivityAttachment marketingActivityAttachment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, marketingActivityAttachment)){
			return form(marketingActivityAttachment, model);
		}
		marketingActivityAttachmentService.save(marketingActivityAttachment);
		addMessage(redirectAttributes, "保存活动关联附件成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityAttachment/?repage";
	}
	
	@RequiresPermissions("marketing:marketingActivityAttachment:edit")
	@RequestMapping(value = "delete")
	public String delete(MarketingActivityAttachment marketingActivityAttachment, RedirectAttributes redirectAttributes) {
		marketingActivityAttachmentService.delete(marketingActivityAttachment);
		addMessage(redirectAttributes, "删除活动关联附件成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityAttachment/?repage";
	}

}