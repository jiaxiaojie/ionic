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
import com.thinkgem.jeesite.modules.entity.ThirdPartyMailPara;
import com.thinkgem.jeesite.modules.sys.service.ThirdPartyMailParaService;

/**
 * 邮件发送参数Controller
 * @author yangtao
 * @version 2015-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/thirdPartyMailPara")
public class ThirdPartyMailParaController extends BaseController {

	@Autowired
	private ThirdPartyMailParaService thirdPartyMailParaService;
	
	@ModelAttribute
	public ThirdPartyMailPara get(@RequestParam(required=false) String id) {
		ThirdPartyMailPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = thirdPartyMailParaService.get(id);
		}
		if (entity == null){
			entity = new ThirdPartyMailPara();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:thirdPartyMailPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(ThirdPartyMailPara thirdPartyMailPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ThirdPartyMailPara> page = thirdPartyMailParaService.findPage(new Page<ThirdPartyMailPara>(request, response), thirdPartyMailPara); 
		model.addAttribute("page", page);
		return "modules/sys/thirdPartyMailParaList";
	}

	@RequiresPermissions("sys:thirdPartyMailPara:view")
	@RequestMapping(value = "form")
	public String form(ThirdPartyMailPara thirdPartyMailPara, Model model) {
		model.addAttribute("thirdPartyMailPara", thirdPartyMailPara);
		return "modules/sys/thirdPartyMailParaForm";
	}

	@RequiresPermissions("sys:thirdPartyMailPara:edit")
	@RequestMapping(value = "save")
	public String save(ThirdPartyMailPara thirdPartyMailPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, thirdPartyMailPara)){
			return form(thirdPartyMailPara, model);
		}
		thirdPartyMailParaService.save(thirdPartyMailPara);
		addMessage(redirectAttributes, "保存邮件发送参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/thirdPartyMailPara/form?id=1";
	}
	
	@RequiresPermissions("sys:thirdPartyMailPara:edit")
	@RequestMapping(value = "delete")
	public String delete(ThirdPartyMailPara thirdPartyMailPara, RedirectAttributes redirectAttributes) {
		thirdPartyMailParaService.delete(thirdPartyMailPara);
		addMessage(redirectAttributes, "删除邮件发送参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/thirdPartyMailPara/?repage";
	}

}