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
import com.thinkgem.jeesite.modules.entity.ThirdPartySmsPara;
import com.thinkgem.jeesite.modules.sys.service.ThirdPartySmsParaService;

/**
 * 短信通道参数Controller
 * @author yangtao
 * @version 2015-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/thirdPartySmsPara")
public class ThirdPartySmsParaController extends BaseController {

	@Autowired
	private ThirdPartySmsParaService thirdPartySmsParaService;
	
	@ModelAttribute
	public ThirdPartySmsPara get(@RequestParam(required=false) String id) {
		ThirdPartySmsPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = thirdPartySmsParaService.get(id);
		}
		if (entity == null){
			entity = new ThirdPartySmsPara();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:thirdPartySmsPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(ThirdPartySmsPara thirdPartySmsPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ThirdPartySmsPara> page = thirdPartySmsParaService.findPage(new Page<ThirdPartySmsPara>(request, response), thirdPartySmsPara); 
		model.addAttribute("page", page);
		return "modules/sys/thirdPartySmsParaList";
	}

	@RequiresPermissions("sys:thirdPartySmsPara:view")
	@RequestMapping(value = "form")
	public String form(ThirdPartySmsPara thirdPartySmsPara, Model model) {
		model.addAttribute("thirdPartySmsPara", thirdPartySmsPara);
		return "modules/sys/thirdPartySmsParaForm";
	}

	@RequiresPermissions("sys:thirdPartySmsPara:edit")
	@RequestMapping(value = "save")
	public String save(ThirdPartySmsPara thirdPartySmsPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, thirdPartySmsPara)){
			return form(thirdPartySmsPara, model);
		}
		thirdPartySmsParaService.save(thirdPartySmsPara);
		addMessage(redirectAttributes, "保存短信通道参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/thirdPartySmsPara/form?id=1";
	}
	
	@RequiresPermissions("sys:thirdPartySmsPara:edit")
	@RequestMapping(value = "delete")
	public String delete(ThirdPartySmsPara thirdPartySmsPara, RedirectAttributes redirectAttributes) {
		thirdPartySmsParaService.delete(thirdPartySmsPara);
		addMessage(redirectAttributes, "删除短信通道参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/thirdPartySmsPara/?repage";
	}

}