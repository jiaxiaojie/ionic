/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

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
import com.thinkgem.jeesite.modules.entity.ThirdPartyYeepayPara;
import com.thinkgem.jeesite.modules.sys.service.ThirdPartyYeepayParaService;

/**
 * 托管账号参数Controller
 * @author yangtao
 * @version 2015-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/thirdPartyYeepayPara")
public class ThirdPartyYeepayParaController extends BaseController {

	@Autowired
	private ThirdPartyYeepayParaService thirdPartyYeepayParaService;
	
	@ModelAttribute
	public ThirdPartyYeepayPara get(@RequestParam(required=false) String id) {
		ThirdPartyYeepayPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = thirdPartyYeepayParaService.get(id);
		}
		if (entity == null){
			entity = new ThirdPartyYeepayPara();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:thirdPartyYeepayPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(ThirdPartyYeepayPara thirdPartyYeepayPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ThirdPartyYeepayPara> page = thirdPartyYeepayParaService.findPage(new Page<ThirdPartyYeepayPara>(request, response), thirdPartyYeepayPara); 
		model.addAttribute("page", page);
		return "modules/sys/thirdPartyYeepayParaList";
	}

	@RequiresPermissions("sys:thirdPartyYeepayPara:view")
	@RequestMapping(value = "form")
	public String form(ThirdPartyYeepayPara thirdPartyYeepayPara, Model model) {
		model.addAttribute("thirdPartyYeepayPara", thirdPartyYeepayPara);
		return "modules/sys/thirdPartyYeepayParaForm";
	}

	@RequiresPermissions("sys:thirdPartyYeepayPara:edit")
	@RequestMapping(value = "save")
	public String save(ThirdPartyYeepayPara thirdPartyYeepayPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, thirdPartyYeepayPara)){
			return form(thirdPartyYeepayPara, model);
		}
		thirdPartyYeepayParaService.save(thirdPartyYeepayPara);
		addMessage(redirectAttributes, "保存托管账号参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/thirdPartyYeepayPara/form?id=1";
	}
	
	@RequiresPermissions("sys:thirdPartyYeepayPara:edit")
	@RequestMapping(value = "delete")
	public String delete(ThirdPartyYeepayPara thirdPartyYeepayPara, RedirectAttributes redirectAttributes) {
		thirdPartyYeepayParaService.delete(thirdPartyYeepayPara);
		addMessage(redirectAttributes, "删除托管账号参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/thirdPartyYeepayPara/?repage";
	}

}