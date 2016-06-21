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
import com.thinkgem.jeesite.modules.entity.IntegralMallProductType;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductTypeService;

/**
 * 花生乐园上架产品类别Controller
 * @author lizibo
 * @version 2015-09-23
 */
@Controller
@RequestMapping(value = "${adminPath}/integral/integralMallProductType")
public class IntegralMallProductTypeController extends BaseController {

	@Autowired
	private IntegralMallProductTypeService integralMallProductTypeService;
	
	@ModelAttribute
	public IntegralMallProductType get(@RequestParam(required=false) String id) {
		IntegralMallProductType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = integralMallProductTypeService.get(id);
		}
		if (entity == null){
			entity = new IntegralMallProductType();
		}
		return entity;
	}
	
	@RequiresPermissions("integral:integralMallProductType:view")
	@RequestMapping(value = {"list", ""})
	public String list(IntegralMallProductType integralMallProductType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IntegralMallProductType> dd= new Page<IntegralMallProductType>(request, response);
		dd.setOrderBy(" type_id desc");
		Page<IntegralMallProductType> page = integralMallProductTypeService.findPage(dd, integralMallProductType); 
		model.addAttribute("page", page);
		return "modules/integral/integralMallProductTypeList";
	}

	@RequiresPermissions("integral:integralMallProductType:view")
	@RequestMapping(value = "form")
	public String form(IntegralMallProductType integralMallProductType, Model model) {
		model.addAttribute("integralMallProductType", integralMallProductType);
		return "modules/integral/integralMallProductTypeForm";
	}

	@RequiresPermissions("integral:integralMallProductType:edit")
	@RequestMapping(value = "save")
	public String save(IntegralMallProductType integralMallProductType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralMallProductType)){
			return form(integralMallProductType, model);
		}
		integralMallProductType.setParentTypeId(0L);
		integralMallProductTypeService.save(integralMallProductType);
		addMessage(redirectAttributes, "保存花生乐园上架产品类别成功");
		return "redirect:"+Global.getAdminPath()+"/integral/integralMallProductType/?repage";
	}
	
	@RequiresPermissions("integral:integralMallProductType:edit")
	@RequestMapping(value = "delete")
	public String delete(IntegralMallProductType integralMallProductType, RedirectAttributes redirectAttributes) {
		integralMallProductTypeService.delete(integralMallProductType);
		addMessage(redirectAttributes, "删除花生乐园上架产品类别成功");
		return "redirect:"+Global.getAdminPath()+"/integral/integralMallProductType/?repage";
	}

}