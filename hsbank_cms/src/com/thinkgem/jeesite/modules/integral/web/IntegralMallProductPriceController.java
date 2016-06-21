/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.web;

import java.util.Date;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductPrice;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductPriceService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 产品价格策略Controller
 * @author lizibo
 * @version 2015-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/integral/integralMallProductPrice")
public class IntegralMallProductPriceController extends BaseController {

	@Autowired
	private IntegralMallProductPriceService integralMallProductPriceService;
	
	@ModelAttribute
	public IntegralMallProductPrice get(@RequestParam(required=false) String id) {
		IntegralMallProductPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = integralMallProductPriceService.get(id);
		}
		if (entity == null){
			entity = new IntegralMallProductPrice();
		}
		return entity;
	}
	
	@RequiresPermissions("integral:integralMallProductPrice:view")
	@RequestMapping(value = {"list", ""})
	public String list(IntegralMallProductPrice integralMallProductPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IntegralMallProductPrice> dd= new Page<IntegralMallProductPrice>(request, response);
		dd.setOrderBy(" a.id desc");
		Page<IntegralMallProductPrice> page = integralMallProductPriceService.findPage(dd, integralMallProductPrice); 
		model.addAttribute("page", page);
		return "modules/integral/integralMallProductPriceList";
	}
	
	/**
	 * 查看产品价格
	 * @param integralMallProductPrice
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProductPrice:view")
	@RequestMapping(value = {"queryList", ""})
	public String queryList(IntegralMallProductPrice integralMallProductPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IntegralMallProductPrice> dd= new Page<IntegralMallProductPrice>(request, response);
		dd.setOrderBy(" a.id desc");
		Page<IntegralMallProductPrice> page = integralMallProductPriceService.findPage(dd, integralMallProductPrice); 
		model.addAttribute("page", page);
		return "modules/integral/integralMallProductPriceQueryList";
	}

	@RequiresPermissions("integral:integralMallProductPrice:view")
	@RequestMapping(value = "form")
	public String form(IntegralMallProductPrice integralMallProductPrice, Model model) {
		model.addAttribute("integralMallProductPrice", integralMallProductPrice);
		return "modules/integral/integralMallProductPriceForm";
	}
	
	/**
	 * 查看详情
	 * @param integralMallProductPrice
	 * @param model
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProductPrice:view")
	@RequestMapping(value = "queryForm")
	public String queryForm(IntegralMallProductPrice integralMallProductPrice, Model model) {
		model.addAttribute("integralMallProductPrice", integralMallProductPrice);
		return "modules/integral/integralMallProductPriceQueryForm";
	}

	@RequiresPermissions("integral:integralMallProductPrice:edit")
	@RequestMapping(value = "save")
	public String save(IntegralMallProductPrice integralMallProductPrice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralMallProductPrice)){
			return form(integralMallProductPrice, model);
		}
		integralMallProductPrice.setCreateDt(new Date());
		integralMallProductPrice.setCreateUserId(new Long(UserUtils.getUser().getId()));
		integralMallProductPriceService.save(integralMallProductPrice);
		addMessage(redirectAttributes, "保存产品价格策略成功");
		return "redirect:"+Global.getAdminPath()+"/integral/integralMallProductPrice/list?productId="+integralMallProductPrice.getProductId();
	}
	
	@RequiresPermissions("integral:integralMallProductPrice:edit")
	@RequestMapping(value = "delete")
	public String delete(IntegralMallProductPrice integralMallProductPrice, RedirectAttributes redirectAttributes) {
		integralMallProductPriceService.delete(integralMallProductPrice);
		addMessage(redirectAttributes, "删除产品价格策略成功");
		return "redirect:"+Global.getAdminPath()+"/integral/integralMallProductPrice/list?productId="+integralMallProductPrice.getProductId();
	}

}