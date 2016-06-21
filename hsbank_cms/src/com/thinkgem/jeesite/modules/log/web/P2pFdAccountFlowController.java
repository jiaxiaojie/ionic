/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.web;

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
import com.thinkgem.jeesite.modules.entity.P2pFdAccountFlow;
import com.thinkgem.jeesite.modules.log.service.P2pFdAccountFlowService;

/**
 * 平台账号流水Controller
 * @author yangtao
 * @version 2015-08-12
 */
@Controller
@RequestMapping(value = "${adminPath}/log/p2pFdAccountFlow")
public class P2pFdAccountFlowController extends BaseController {

	@Autowired
	private P2pFdAccountFlowService p2pFdAccountFlowService;
	
	@ModelAttribute
	public P2pFdAccountFlow get(@RequestParam(required=false) String id) {
		P2pFdAccountFlow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = p2pFdAccountFlowService.get(id);
		}
		if (entity == null){
			entity = new P2pFdAccountFlow();
		}
		return entity;
	}
	
	@RequiresPermissions("log:p2pFdAccountFlow:view")
	@RequestMapping(value = {"list", ""})
	public String list(P2pFdAccountFlow p2pFdAccountFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<P2pFdAccountFlow> page = p2pFdAccountFlowService.findPage(new Page<P2pFdAccountFlow>(request, response), p2pFdAccountFlow); 
		model.addAttribute("page", page);
		return "modules/log/p2pFdAccountFlowList";
	}

	@RequiresPermissions("log:p2pFdAccountFlow:view")
	@RequestMapping(value = "form")
	public String form(P2pFdAccountFlow p2pFdAccountFlow, Model model) {
		model.addAttribute("p2pFdAccountFlow", p2pFdAccountFlow);
		return "modules/log/p2pFdAccountFlowForm";
	}

	@RequiresPermissions("log:p2pFdAccountFlow:edit")
	@RequestMapping(value = "save")
	public String save(P2pFdAccountFlow p2pFdAccountFlow, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, p2pFdAccountFlow)){
			return form(p2pFdAccountFlow, model);
		}
		p2pFdAccountFlowService.save(p2pFdAccountFlow);
		addMessage(redirectAttributes, "保存平台账号流水成功");
		return "redirect:"+Global.getAdminPath()+"/log/p2pFdAccountFlow/?repage";
	}
	
	@RequiresPermissions("log:p2pFdAccountFlow:edit")
	@RequestMapping(value = "delete")
	public String delete(P2pFdAccountFlow p2pFdAccountFlow, RedirectAttributes redirectAttributes) {
		p2pFdAccountFlowService.delete(p2pFdAccountFlow);
		addMessage(redirectAttributes, "删除平台账号流水成功");
		return "redirect:"+Global.getAdminPath()+"/log/p2pFdAccountFlow/?repage";
	}

}