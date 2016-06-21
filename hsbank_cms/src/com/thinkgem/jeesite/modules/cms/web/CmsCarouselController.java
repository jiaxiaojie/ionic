/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web;

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
import com.thinkgem.jeesite.modules.entity.CmsCarousel;
import com.thinkgem.jeesite.modules.cms.service.CmsCarouselService;

/**
 * 首页轮播图Controller
 * @author ydt
 * @version 2015-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsCarousel")
public class CmsCarouselController extends BaseController {

	@Autowired
	private CmsCarouselService cmsCarouselService;
	
	@ModelAttribute
	public CmsCarousel get(@RequestParam(required=false) String id) {
		CmsCarousel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsCarouselService.get(id);
		}
		if (entity == null){
			entity = new CmsCarousel();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:cmsCarousel:view")
	@RequestMapping(value = {"list", ""})
	public String list(CmsCarousel cmsCarousel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsCarousel> page = cmsCarouselService.findPage(new Page<CmsCarousel>(request, response), cmsCarousel); 
		model.addAttribute("page", page);
		return "modules/cms/cmsCarouselList";
	}

	@RequiresPermissions("cms:cmsCarousel:view")
	@RequestMapping(value = "form")
	public String form(CmsCarousel cmsCarousel, Model model) {
		model.addAttribute("cmsCarousel", cmsCarousel);
		return "modules/cms/cmsCarouselForm";
	}

	@RequiresPermissions("cms:cmsCarousel:edit")
	@RequestMapping(value = "save")
	public String save(CmsCarousel cmsCarousel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cmsCarousel)){
			return form(cmsCarousel, model);
		}
		cmsCarouselService.save(cmsCarousel);
		addMessage(redirectAttributes, "保存首页轮播图成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsCarousel/?repage";
	}
	
	@RequiresPermissions("cms:cmsCarousel:edit")
	@RequestMapping(value = "delete")
	public String delete(CmsCarousel cmsCarousel, RedirectAttributes redirectAttributes) {
		cmsCarouselService.delete(cmsCarousel);
		addMessage(redirectAttributes, "删除首页轮播图成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsCarousel/?repage";
	}

}