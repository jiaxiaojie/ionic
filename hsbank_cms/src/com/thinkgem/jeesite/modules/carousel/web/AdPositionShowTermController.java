/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.carousel.web;

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
import com.thinkgem.jeesite.modules.entity.AdPositionShowTerm;
import com.thinkgem.jeesite.modules.carousel.service.AdPositionShowTermService;

/**
 * 广告位显示终端Controller
 * @author huangyuchen
 * @version 2016-05-17
 */
@Controller
@RequestMapping(value = "${adminPath}/carousel/adPositionShowTerm")
public class AdPositionShowTermController extends BaseController {

	@Autowired
	private AdPositionShowTermService adPositionShowTermService;
	
	@ModelAttribute
	public AdPositionShowTerm get(@RequestParam(required=false) String id) {
		AdPositionShowTerm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = adPositionShowTermService.get(id);
		}
		if (entity == null){
			entity = new AdPositionShowTerm();
		}
		return entity;
	}
	
	@RequiresPermissions("carousel:adPositionShowTerm:view")
	@RequestMapping(value = {"list", ""})
	public String list(AdPositionShowTerm adPositionShowTerm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdPositionShowTerm> page = adPositionShowTermService.findPage(new Page<AdPositionShowTerm>(request, response), adPositionShowTerm); 
		model.addAttribute("page", page);
		return "modules/carousel/adPositionShowTermList";
	}

	@RequiresPermissions("carousel:adPositionShowTerm:view")
	@RequestMapping(value = "form")
	public String form(AdPositionShowTerm adPositionShowTerm, Model model) {
		model.addAttribute("adPositionShowTerm", adPositionShowTerm);
		return "modules/carousel/adPositionShowTermForm";
	}

	@RequiresPermissions("carousel:adPositionShowTerm:edit")
	@RequestMapping(value = "save")
	public String save(AdPositionShowTerm adPositionShowTerm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, adPositionShowTerm)){
			return form(adPositionShowTerm, model);
		}
		adPositionShowTermService.save(adPositionShowTerm);
		addMessage(redirectAttributes, "保存广告位显示终端成功");
		return "redirect:"+Global.getAdminPath()+"/carousel/adPositionShowTerm/?repage";
	}
	
	@RequiresPermissions("carousel:adPositionShowTerm:edit")
	@RequestMapping(value = "delete")
	public String delete(AdPositionShowTerm adPositionShowTerm, RedirectAttributes redirectAttributes) {
		adPositionShowTermService.delete(adPositionShowTerm);
		addMessage(redirectAttributes, "删除广告位显示终端成功");
		return "redirect:"+Global.getAdminPath()+"/carousel/adPositionShowTerm/?repage";
	}

}