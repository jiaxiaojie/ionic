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
import com.thinkgem.jeesite.modules.entity.EuropeanCupTopScorer;
import com.thinkgem.jeesite.modules.marketing.service.EuropeanCupTopScorerService;

/**
 * 欧洲杯射手榜Controller
 * @author lzb
 * @version 2016-06-13
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/europeanCupTopScorer")
public class EuropeanCupTopScorerController extends BaseController {

	@Autowired
	private EuropeanCupTopScorerService europeanCupTopScorerService;
	
	@ModelAttribute
	public EuropeanCupTopScorer get(@RequestParam(required=false) String id) {
		EuropeanCupTopScorer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = europeanCupTopScorerService.get(id);
		}
		if (entity == null){
			entity = new EuropeanCupTopScorer();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:europeanCupTopScorer:view")
	@RequestMapping(value = {"list", ""})
	public String list(EuropeanCupTopScorer europeanCupTopScorer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EuropeanCupTopScorer> page = europeanCupTopScorerService.findPage(new Page<EuropeanCupTopScorer>(request, response), europeanCupTopScorer); 
		model.addAttribute("page", page);
		return "modules/marketing/europeanCupTopScorerList";
	}

	@RequiresPermissions("marketing:europeanCupTopScorer:view")
	@RequestMapping(value = "form")
	public String form(EuropeanCupTopScorer europeanCupTopScorer, Model model) {
		model.addAttribute("europeanCupTopScorer", europeanCupTopScorer);
		return "modules/marketing/europeanCupTopScorerForm";
	}

	@RequiresPermissions("marketing:europeanCupTopScorer:edit")
	@RequestMapping(value = "save")
	public String save(EuropeanCupTopScorer europeanCupTopScorer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, europeanCupTopScorer)){
			return form(europeanCupTopScorer, model);
		}
		europeanCupTopScorerService.save(europeanCupTopScorer);
		addMessage(redirectAttributes, "保存欧洲杯射手榜成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/europeanCupTopScorer/?repage";
	}
	
	@RequiresPermissions("marketing:europeanCupTopScorer:edit")
	@RequestMapping(value = "delete")
	public String delete(EuropeanCupTopScorer europeanCupTopScorer, RedirectAttributes redirectAttributes) {
		europeanCupTopScorerService.delete(europeanCupTopScorer);
		addMessage(redirectAttributes, "删除欧洲杯射手榜成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/europeanCupTopScorer/?repage";
	}

}