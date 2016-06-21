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
import com.thinkgem.jeesite.modules.entity.SysBizPara;
import com.thinkgem.jeesite.modules.sys.service.SysBizParaService;

/**
 * 业务参数Controller
 * @author yangtao
 * @version 2015-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysBizPara")
public class SysBizParaController extends BaseController {

	@Autowired
	private SysBizParaService sysBizParaService;
	
	@ModelAttribute
	public SysBizPara get(@RequestParam(required=false) String id) {
		SysBizPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysBizParaService.get(id);
		}
		if (entity == null){
			entity = new SysBizPara();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysBizPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysBizPara sysBizPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysBizPara> page = sysBizParaService.findPage(new Page<SysBizPara>(request, response), sysBizPara); 
		model.addAttribute("page", page);
		return "modules/sys/sysBizParaList";
	}

	@RequiresPermissions("sys:sysBizPara:view")
	@RequestMapping(value = "form")
	public String form(SysBizPara sysBizPara, Model model) {
		model.addAttribute("sysBizPara", sysBizPara);
		return "modules/sys/sysBizParaForm";
	}

	@RequiresPermissions("sys:sysBizPara:edit")
	@RequestMapping(value = "save")
	public String save(SysBizPara sysBizPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysBizPara)){
			return form(sysBizPara, model);
		}
		sysBizParaService.save(sysBizPara);
		addMessage(redirectAttributes, "保存业务参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysBizPara/form?id=1";
	}
	
	@RequiresPermissions("sys:sysBizPara:edit")
	@RequestMapping(value = "delete")
	public String delete(SysBizPara sysBizPara, RedirectAttributes redirectAttributes) {
		sysBizParaService.delete(sysBizPara);
		addMessage(redirectAttributes, "删除业务参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysBizPara/?repage";
	}

}