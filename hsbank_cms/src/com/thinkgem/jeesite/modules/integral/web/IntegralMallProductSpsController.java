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
import com.thinkgem.jeesite.modules.entity.IntegralMallProductSps;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductSpsService;

/**
 * 产品规格参数Controller
 * @author lizibo
 * @version 2015-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/integral/integralMallProductSps")
public class IntegralMallProductSpsController extends BaseController {

	@Autowired
	private IntegralMallProductSpsService integralMallProductSpsService;
	
	@ModelAttribute
	public IntegralMallProductSps get(@RequestParam(required=false) String id) {
		IntegralMallProductSps entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = integralMallProductSpsService.get(id);
		}
		if (entity == null){
			entity = new IntegralMallProductSps();
		}
		return entity;
	}
	
	@RequiresPermissions("integral:integralMallProductSps:view")
	@RequestMapping(value = {"list", ""})
	public String list(IntegralMallProductSps integralMallProductSps, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IntegralMallProductSps> dd= new Page<IntegralMallProductSps>(request, response);
		dd.setOrderBy(" para_id desc");
		Page<IntegralMallProductSps> page = integralMallProductSpsService.findPage(dd, integralMallProductSps); 
		model.addAttribute("page", page);
		model.addAttribute("integralMallProductSps", integralMallProductSps);
		return "modules/integral/integralMallProductSpsList";
	}
	
	/**
	 * 查看产品规格
	 * @param integralMallProductSps
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProductSps:view")
	@RequestMapping(value = {"queryList", ""})
	public String queryList(IntegralMallProductSps integralMallProductSps, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IntegralMallProductSps> dd= new Page<IntegralMallProductSps>(request, response);
		dd.setOrderBy(" para_id desc");
		Page<IntegralMallProductSps> page = integralMallProductSpsService.findPage(dd, integralMallProductSps); 
		model.addAttribute("page", page);
		model.addAttribute("integralMallProductSps", integralMallProductSps);
		return "modules/integral/integralMallProductSpsQueryList";
	}
	
	/**
	 * 查看详情
	 * @param integralMallProductSps
	 * @param model
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProductSps:view")
	@RequestMapping(value = "queryForm")
	public String queryForm(IntegralMallProductSps integralMallProductSps, Model model) {		
		model.addAttribute("integralMallProductSps", integralMallProductSps);
		return "modules/integral/integralMallProductSpsQueryForm";
	}

	@RequiresPermissions("integral:integralMallProductSps:view")
	@RequestMapping(value = "form")
	public String form(IntegralMallProductSps integralMallProductSps, Model model) {
		IntegralMallProductSps theLastProductSps = new IntegralMallProductSps();
		theLastProductSps = integralMallProductSpsService.getLastOneByProductId(integralMallProductSps.getProductId());
		if(theLastProductSps != null && theLastProductSps.getParaSeq() != null &&  !"".equals(theLastProductSps.getParaSeq())){
			integralMallProductSps.setParaSeq(String.valueOf(StringUtils.toInteger(theLastProductSps.getParaSeq()) + 30));
		}else{
			integralMallProductSps.setParaSeq("30");
		}
		model.addAttribute("integralMallProductSps", integralMallProductSps);
		return "modules/integral/integralMallProductSpsForm";
	}

	@RequiresPermissions("integral:integralMallProductSps:edit")
	@RequestMapping(value = "save")
	public String save(IntegralMallProductSps integralMallProductSps, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralMallProductSps)){
			return form(integralMallProductSps, model);
		}
		integralMallProductSpsService.save(integralMallProductSps);
		addMessage(redirectAttributes, "保存产品规格参数成功");
		return "redirect:"+Global.getAdminPath()+"/integral/integralMallProductSps/list?productId="+integralMallProductSps.getProductId();
	}
	
	@RequiresPermissions("integral:integralMallProductSps:edit")
	@RequestMapping(value = "delete")
	public String delete(IntegralMallProductSps integralMallProductSps, RedirectAttributes redirectAttributes) {
		integralMallProductSpsService.delete(integralMallProductSps);
		addMessage(redirectAttributes, "删除产品规格参数成功");
		return "redirect:"+Global.getAdminPath()+"/integral/integralMallProductSps/list?productId="+integralMallProductSps.getProductId();
	}

}