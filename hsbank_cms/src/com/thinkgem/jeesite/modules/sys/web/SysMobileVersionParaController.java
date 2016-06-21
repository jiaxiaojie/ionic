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
import com.thinkgem.jeesite.modules.entity.SysMobileVersionPara;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.service.SysMobileVersionParaService;

/**
 * 会员账户余额对齐Controller
 * @author lzb
 * @version 2015-11-10
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysMobileVersionPara")
public class SysMobileVersionParaController extends BaseController {

	@Autowired
	private SysMobileVersionParaService sysMobileVersionParaService;
	
	@ModelAttribute
	public SysMobileVersionPara get(@RequestParam(required=false) String id) {
		SysMobileVersionPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysMobileVersionParaService.get(id);
		}
		if (entity == null){
			entity = new SysMobileVersionPara();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysMobileVersionPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysMobileVersionPara sysMobileVersionPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysMobileVersionPara> page = sysMobileVersionParaService.findPage(new Page<SysMobileVersionPara>(request, response), sysMobileVersionPara); 
		model.addAttribute("page", page);
		return "modules/sys/sysMobileVersionParaList";
	}

	@RequiresPermissions("sys:sysMobileVersionPara:view")
	@RequestMapping(value = "form")
	public String form(SysMobileVersionPara sysMobileVersionPara, Model model) {
		if(StringUtils.isBlank(sysMobileVersionPara.getIsForcedUpdate())){
			sysMobileVersionPara.setIsForcedUpdate(ProjectConstant.DICT_DEFAULT_VALUE);
		}
		model.addAttribute("sysMobileVersionPara", sysMobileVersionPara);
		return "modules/sys/sysMobileVersionParaForm";
	}

	@RequiresPermissions("sys:sysMobileVersionPara:edit")
	@RequestMapping(value = "save")
	public String save(SysMobileVersionPara sysMobileVersionPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysMobileVersionPara)){
			return form(sysMobileVersionPara, model);
		}
		sysMobileVersionParaService.save(sysMobileVersionPara);
		addMessage(redirectAttributes, "保存版本参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysMobileVersionPara/?repage";
	}
	
	@RequiresPermissions("sys:sysMobileVersionPara:edit")
	@RequestMapping(value = "delete")
	public String delete(SysMobileVersionPara sysMobileVersionPara, RedirectAttributes redirectAttributes) {
		sysMobileVersionParaService.delete(sysMobileVersionPara);
		addMessage(redirectAttributes, "删除版本参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysMobileVersionPara/?repage";
	}
	
	@RequiresPermissions("sys:sysMobileVersionPara:edit")
	@RequestMapping(value = "updateMark")
	public String updateMark(SysMobileVersionPara sysMobileVersionPara, RedirectAttributes redirectAttributes) {
		sysMobileVersionParaService.updateMark(sysMobileVersionPara);
		
		addMessage(redirectAttributes, "版本参数已启用");
		return "redirect:"+Global.getAdminPath()+"/sys/sysMobileVersionPara/?repage";
	}
	
	

}