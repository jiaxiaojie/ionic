/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.web;

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
import com.thinkgem.jeesite.modules.entity.IntegralMallProductHot;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductHotService;

/**
 * 产品活动标签Controller
 * @author lizibo
 * @version 2015-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/integral/integralMallProductHot")
public class IntegralMallProductHotController extends BaseController {

	@Autowired
	private IntegralMallProductHotService integralMallProductHotService;
	
	@ModelAttribute
	public IntegralMallProductHot get(@RequestParam(required=false) String id) {
		IntegralMallProductHot entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = integralMallProductHotService.get(id);
		}
		if (entity == null){
			entity = new IntegralMallProductHot();
		}
		return entity;
	}
	
	@RequiresPermissions("integral:integralMallProductHot:view")
	@RequestMapping(value = {"list", ""})
	public String list(IntegralMallProductHot integralMallProductHot, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IntegralMallProductHot> page = integralMallProductHotService.findPage(new Page<IntegralMallProductHot>(request, response), integralMallProductHot); 
		model.addAttribute("page", page);
		return "modules/integral/integralMallProductHotList";
	}
	
	/**
	 * 查看活动
	 * @param integralMallProductHot
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProductHot:view")
	@RequestMapping(value = {"queryList", ""})
	public String queryList(IntegralMallProductHot integralMallProductHot, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IntegralMallProductHot> page = integralMallProductHotService.findPage(new Page<IntegralMallProductHot>(request, response), integralMallProductHot); 
		model.addAttribute("page", page);
		return "modules/integral/integralMallProductHotQueryList";
	}

	@RequiresPermissions("integral:integralMallProductHot:view")
	@RequestMapping(value = "form")
	public String form(IntegralMallProductHot integralMallProductHot, Model model) {
		model.addAttribute("integralMallProductHot", integralMallProductHot);
		return "modules/integral/integralMallProductHotForm";
	}
	
	/**
	 * 查看详情
	 * @param integralMallProductHot
	 * @param model
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProductHot:view")
	@RequestMapping(value = "queryForm")
	public String queryForm(IntegralMallProductHot integralMallProductHot, Model model) {
		model.addAttribute("integralMallProductHot", integralMallProductHot);
		return "modules/integral/integralMallProductHotQueryForm";
	}

	@RequiresPermissions("integral:integralMallProductHot:edit")
	@RequestMapping(value = "save")
	public String save(IntegralMallProductHot integralMallProductHot, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralMallProductHot)){
			return form(integralMallProductHot, model);
		}
		integralMallProductHotService.save(integralMallProductHot);
		addMessage(redirectAttributes, "保存产品活动标签成功");
		return "redirect:"+Global.getAdminPath()+"/integral/integralMallProductHot/list?productId="+integralMallProductHot.getProductId();
	}
	
	@RequiresPermissions("integral:integralMallProductHot:edit")
	@RequestMapping(value = "delete")
	public String delete(IntegralMallProductHot integralMallProductHot, RedirectAttributes redirectAttributes) {
		integralMallProductHotService.delete(integralMallProductHot);
		addMessage(redirectAttributes, "删除产品活动标签成功");
		return "redirect:"+Global.getAdminPath()+"/integral/integralMallProductHot/list?productId="+integralMallProductHot.getProductId();
	}

}