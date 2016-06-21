/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

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
import com.thinkgem.jeesite.modules.entity.SysExchangeRatePara;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.service.SysExchangeRateParaService;

/**
 * 汇率参数Controller
 * @author lzb
 * @version 2016-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysExchangeRatePara")
public class SysExchangeRateParaController extends BaseController {

	@Autowired
	private SysExchangeRateParaService sysExchangeRateParaService;
	
	@ModelAttribute
	public SysExchangeRatePara get(@RequestParam(required=false) String id) {
		SysExchangeRatePara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysExchangeRateParaService.get(id);
		}
		if (entity == null){
			entity = new SysExchangeRatePara();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysExchangeRatePara:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysExchangeRatePara sysExchangeRatePara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysExchangeRatePara> page = sysExchangeRateParaService.findPage(new Page<SysExchangeRatePara>(request, response), sysExchangeRatePara); 
		model.addAttribute("page", page);
		return "modules/sys/sysExchangeRateParaList";
	}

	@RequiresPermissions("sys:sysExchangeRatePara:view")
	@RequestMapping(value = "form")
	public String form(SysExchangeRatePara sysExchangeRatePara, Model model) {
		model.addAttribute("sysExchangeRatePara", sysExchangeRatePara);
		return "modules/sys/sysExchangeRateParaForm";
	}

	@RequiresPermissions("sys:sysExchangeRatePara:edit")
	@RequestMapping(value = "save")
	public String save(SysExchangeRatePara sysExchangeRatePara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysExchangeRatePara)){
			return form(sysExchangeRatePara, model);
		}
		sysExchangeRatePara.setCreateDt(new Date());
		sysExchangeRatePara.setStatus(ProjectConstant.DICT_DEFAULT_VALUE);
		sysExchangeRateParaService.save(sysExchangeRatePara);
		addMessage(redirectAttributes, "保存汇率参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysExchangeRatePara/?repage";
	}
	
	@RequiresPermissions("sys:sysExchangeRatePara:edit")
	@RequestMapping(value = "delete")
	public String delete(SysExchangeRatePara sysExchangeRatePara, RedirectAttributes redirectAttributes) {
		sysExchangeRateParaService.delete(sysExchangeRatePara);
		addMessage(redirectAttributes, "删除汇率参数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysExchangeRatePara/?repage";
	}

}