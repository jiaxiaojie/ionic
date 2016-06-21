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
import com.thinkgem.jeesite.modules.entity.InvestmentTicketType;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.service.InvestmentTicketTypeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 投资券类型管理Controller
 * @author yangtao
 * @version 2015-07-21
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/investmentTicketType")
public class InvestmentTicketTypeController extends BaseController {

	@Autowired
	private InvestmentTicketTypeService investmentTicketTypeService;
	
	@ModelAttribute
	public InvestmentTicketType get(@RequestParam(required=false) String id) {
		InvestmentTicketType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = investmentTicketTypeService.get(id);
		}
		if (entity == null){
			entity = new InvestmentTicketType();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:investmentTicketType:view")
	@RequestMapping(value = {"list", ""})
	public String list(InvestmentTicketType InvestmentTicketType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InvestmentTicketType> page = investmentTicketTypeService.findPage(new Page<InvestmentTicketType>(request, response), InvestmentTicketType); 
		model.addAttribute("page", page);
		return "modules/sys/investmentTicketTypeList";
	}

	@RequiresPermissions("sys:investmentTicketType:view")
	@RequestMapping(value = "form")
	public String form(InvestmentTicketType investmentTicketType, Model model) {
		model.addAttribute("investmentTicketType", investmentTicketType);
		return "modules/sys/investmentTicketTypeForm";
	}

	@RequiresPermissions("sys:investmentTicketType:edit")
	@RequestMapping(value = "save")
	public String save(InvestmentTicketType investmentTicketType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, investmentTicketType)){
			return form(investmentTicketType, model);
		}
		if(investmentTicketType.getIsNewRecord()){
			investmentTicketType.setCreateUser(new Long(UserUtils.getUser().getId()));
			investmentTicketType.setCreateDt(new Date());
			investmentTicketType.setLastModifyUser(new Long(UserUtils.getUser().getId()));
			investmentTicketType.setLastModifyDt(new Date());
			
		}else{
			investmentTicketType.setLastModifyUser(new Long(UserUtils.getUser().getId()));
			investmentTicketType.setLastModifyDt(new Date());
		}
		investmentTicketType.setStatus(ProjectConstant.INVESTMENT_TICKET_TYPE_STATUS_NORMAL);
		investmentTicketTypeService.save(investmentTicketType);
		addMessage(redirectAttributes, "保存投资券类型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/investmentTicketType/?repage";
	}
	
	@RequiresPermissions("sys:investmentTicketType:edit")
	@RequestMapping(value = "delete")
	public String delete(InvestmentTicketType investmentTicketType, RedirectAttributes redirectAttributes) {
		investmentTicketType.setLastModifyUser(new Long(UserUtils.getUser().getId()));
		investmentTicketType.setLastModifyDt(new Date());
		investmentTicketTypeService.delete(investmentTicketType);
		addMessage(redirectAttributes, "删除投资券类型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/investmentTicketType/?repage";
	}

}