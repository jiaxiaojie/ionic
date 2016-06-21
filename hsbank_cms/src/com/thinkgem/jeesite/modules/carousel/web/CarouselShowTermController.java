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
import com.thinkgem.jeesite.modules.entity.CarouselShowTerm;
import com.thinkgem.jeesite.modules.carousel.service.CarouselShowTermService;

/**
 * 轮播图显示终端Controller
 * @author hyc
 * @version 2015-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/carousel/carouselShowTerm")
public class CarouselShowTermController extends BaseController {

	@Autowired
	private CarouselShowTermService carouselShowTermService;
	
	@ModelAttribute
	public CarouselShowTerm get(@RequestParam(required=false) String id) {
		CarouselShowTerm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carouselShowTermService.get(id);
		}
		if (entity == null){
			entity = new CarouselShowTerm();
		}
		return entity;
	}
	/**
	 * 轮播图信息列表
	 * @param carouselShowTerm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("carousel:carouselShowTerm:view")
	@RequestMapping(value = {"list", ""})
	public String list(CarouselShowTerm carouselShowTerm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CarouselShowTerm> page = carouselShowTermService.findPage(new Page<CarouselShowTerm>(request, response), carouselShowTerm); 
		model.addAttribute("page", page);
		return "modules/carousel/carouselShowTermList";
	}
    /**
     * 轮播图信息查看列表
     * @param carouselShowTerm
     * @param model
     * @return
     */
	@RequiresPermissions("carousel:carouselShowTerm:view")
	@RequestMapping(value = "form")
	public String form(CarouselShowTerm carouselShowTerm, Model model) {
		model.addAttribute("carouselShowTerm", carouselShowTerm);
		return "modules/carousel/carouselShowTermForm";
	}

	/**
	 * 增加及修改轮播图信息页面权限
	 * @param carouselShowTerm
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("carousel:carouselShowTerm:edit")
	@RequestMapping(value = "save")
	public String save(CarouselShowTerm carouselShowTerm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, carouselShowTerm)){
			return form(carouselShowTerm, model);
		}
		carouselShowTermService.save(carouselShowTerm);
		addMessage(redirectAttributes, "保存轮播图显示终端成功");
		return "redirect:"+Global.getAdminPath()+"/carousel/carouselShowTerm/?repage";
	}
	
	/**
	 * 删除权限
	 * @param carouselShowTerm
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("carousel:carouselShowTerm:edit")
	@RequestMapping(value = "delete")
	public String delete(CarouselShowTerm carouselShowTerm, RedirectAttributes redirectAttributes) {
		carouselShowTermService.delete(carouselShowTerm);
		addMessage(redirectAttributes, "删除轮播图显示终端成功");
		return "redirect:"+Global.getAdminPath()+"/carousel/carouselShowTerm/?repage";
	}

}