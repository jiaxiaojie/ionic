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
import com.thinkgem.jeesite.modules.entity.RateTicketType;
import com.thinkgem.jeesite.modules.sys.service.RateTicketTypeService;

/**
 * 加息券类型Controller
 * @author ydt
 * @version 2016-04-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/rateTicketType")
public class RateTicketTypeController extends BaseController {

	@Autowired
	private RateTicketTypeService rateTicketTypeService;
	
	@ModelAttribute
	public RateTicketType get(@RequestParam(required=false) String id) {
		RateTicketType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rateTicketTypeService.get(id);
		}
		if (entity == null){
			entity = new RateTicketType();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:rateTicketType:view")
	@RequestMapping(value = {"list", ""})
	public String list(RateTicketType rateTicketType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RateTicketType> page = rateTicketTypeService.findPage(new Page<RateTicketType>(request, response), rateTicketType); 
		model.addAttribute("page", page);
		return "modules/sys/rateTicketTypeList";
	}

	@RequiresPermissions("sys:rateTicketType:view")
	@RequestMapping(value = "form")
	public String form(RateTicketType rateTicketType, Model model) {
		model.addAttribute("rateTicketType", rateTicketType);
		return "modules/sys/rateTicketTypeForm";
	}

	@RequiresPermissions("sys:rateTicketType:edit")
	@RequestMapping(value = "save")
	public String save(RateTicketType rateTicketType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rateTicketType)){
			return form(rateTicketType, model);
		}
		rateTicketTypeService.save(rateTicketType);
		addMessage(redirectAttributes, "保存加息券类型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/rateTicketType/?repage";
	}
	
	@RequiresPermissions("sys:rateTicketType:edit")
	@RequestMapping(value = "delete")
	public String delete(RateTicketType rateTicketType, RedirectAttributes redirectAttributes) {
		rateTicketTypeService.delete(rateTicketType);
		addMessage(redirectAttributes, "删除加息券类型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/rateTicketType/?repage";
	}

}