/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.carousel.web;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.CarouselInfo;
import com.thinkgem.jeesite.modules.entity.CarouselShowTerm;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.carousel.service.CarouselInfoService;
import com.thinkgem.jeesite.modules.carousel.service.CarouselShowTermService;

/**
 * 轮播图管理Controller
 * @author hyc
 * @version 2015-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/carousel/carouselInfo")
public class CarouselInfoController extends BaseController {

	@Autowired
	private CarouselInfoService carouselInfoService;
	@Autowired
	private CarouselShowTermService carouselShowTermService;
	
	@ModelAttribute
	public CarouselInfo get(@RequestParam(required=false) String id) {
		CarouselInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carouselInfoService.get(id);
		}
		if (entity == null){
			entity = new CarouselInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("carousel:carouselInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CarouselInfo carouselInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CarouselInfo> page = carouselInfoService.findPage(new Page<CarouselInfo>(request, response), carouselInfo); 
		model.addAttribute("page", page);
		return "modules/carousel/carouselInfoList";
	}
	
	
	/**
	 * 轮播图审核查看页面
	 * @param carouselInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("carousel:carouselInfo:view")
	@RequestMapping(value = "reviewForm")
	public String reviewForm(CarouselInfo carouselInfo, Model model) {
		Long carouselId = carouselInfo.getCarouselId();
		carouselInfo = this.get(String.valueOf(carouselId));
		//根据轮播图编号查询列表
		List<CarouselShowTerm> showTermList = carouselShowTermService.findListByCarouselId(carouselId);
		List<String> termCodeList = new ArrayList<String>();
		for(CarouselShowTerm showTerm : showTermList){
			termCodeList.add(String.valueOf(showTerm.getTermCode()));
		}
		carouselInfo.setTermCodeList(termCodeList);
		model.addAttribute("carouselInfo",carouselInfo);
		return "modules/carousel/carouselInfoReviewForm";
	}
	
	
	/**
	 * 审核列表
	 * @param carouselInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("carousel:carouselInfo:view")
	@RequestMapping(value = {"reviewList"})
	public String reviewList(CarouselInfo carouselInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		carouselInfo.setStatus(ProjectConstant.CAROUSEL_INFO_STATUS_CREATE);
		Page<CarouselInfo> page = carouselInfoService.findPage(new Page<CarouselInfo>(request, response), carouselInfo); 
		model.addAttribute("page", page);
		return "modules/carousel/carouselInfoReviewList";
	}
	
	/**
	 * 审核
	 * @param carouselInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("carousel:carouselInfo:edit")
	@RequestMapping(value = "review")
	public String review(CarouselInfo carouselInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, carouselInfo)){
			return form(carouselInfo, model);
		}
		String status = carouselInfo.getStatus();
		carouselInfo = carouselInfoService.get(carouselInfo);
		carouselInfo.setStatus(status);
		carouselInfo.setReviewUserId(new Long(UserUtils.getUser().getId()));
		carouselInfo.setReviewDt(new Date());
		carouselInfoService.review(carouselInfo);
		addMessage(redirectAttributes, "审批轮播图成功");
		return "redirect:"+Global.getAdminPath()+"/carousel/carouselInfo/reviewList";
	}
	

	@RequiresPermissions("carousel:carouselInfo:view")
	@RequestMapping(value = "form")
	public String form(CarouselInfo carouselInfo, Model model) {
		Long carouselId = carouselInfo.getCarouselId();
		carouselInfo = this.get(String.valueOf(carouselId));
		//根据轮播图编号查询列表
		List<CarouselShowTerm> carouselShowList = carouselShowTermService.findListByCarouselId(carouselId);
		List<String> termCodeList = new ArrayList<String>();
		for(CarouselShowTerm showTerm : carouselShowList){
			termCodeList.add(String.valueOf(showTerm.getTermCode()));
		}
		carouselInfo.setTermCodeList(termCodeList);
		model.addAttribute("carouselInfo", carouselInfo);
		return "modules/carousel/carouselInfoForm";
	}

	@RequiresPermissions("carousel:carouselInfo:edit")
	@RequestMapping(value = "save")
	public String save(CarouselInfo carouselInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, carouselInfo)){
			return form(carouselInfo, model);
		}
		carouselInfo.setStatus(ProjectConstant.CAROUSEL_INFO_STATUS_CREATE);
		carouselInfo.setCreateUserId(UserUtils.getUser().getId());
		carouselInfo.setCreateDt(new Date());
		carouselInfoService.save(carouselInfo);
		addMessage(redirectAttributes, "保存轮播图成功");
		return "redirect:"+Global.getAdminPath()+"/carousel/carouselInfo/?repage";
	}
	
	
	@RequiresPermissions("carousel:carouselInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CarouselInfo carouselInfo, RedirectAttributes redirectAttributes) {
		carouselInfoService.delete(carouselInfo);
		addMessage(redirectAttributes, "删除轮播图成功");
		return "redirect:"+Global.getAdminPath()+"/carousel/carouselInfo/list";
	}

}