/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personal.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.api.base.BusinessConstant;
import com.thinkgem.jeesite.modules.entity.PersonalTailor;
import com.thinkgem.jeesite.modules.personal.service.PersonalTailorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 私人订制项目审批Controller
 * @author yubin
 * @version 2016-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/personal/personalApproved")
public class PersonalApprovedController extends BaseController {

	@Autowired
	private PersonalTailorService personalTailorService;
	
	@ModelAttribute
	public PersonalTailor get(@RequestParam(required=false) String id) {
		PersonalTailor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personalTailorService.get(id);
		}
		if (entity == null){
			entity = new PersonalTailor();
		}
		return entity;
	}
	
	@RequiresPermissions("personal:personalApproved:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonalTailor personalTailor, HttpServletRequest request, HttpServletResponse response, Model model) {
		personalTailor.setState(BusinessConstant.PERSONAL_APPROVED);
		Page<PersonalTailor> page = personalTailorService.findPage(new Page<PersonalTailor>(request, response), personalTailor);
		model.addAttribute("page", page);
		return "modules/personal/personalApprovedList";
	}

	@RequiresPermissions("personal:personalApproved:view")
	@RequestMapping(value = "form")
	public String form(PersonalTailor personalTailor, Model model) {
		model.addAttribute("personalTailor", personalTailor);
		return "modules/personal/personalApprovedForm";
	}

	@RequiresPermissions("personal:personalApproved:edit")
	@RequestMapping(value = "save")
	public String save(PersonalTailor personalTailor, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, personalTailor)){
			return form(personalTailor, model);
		}
		personalTailorService.save(personalTailor);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/personal/personalApproved/?repage";
	}
	
	@RequiresPermissions("personal:personalApproved:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonalTailor personalTailor, RedirectAttributes redirectAttributes) {
		personalTailorService.delete(personalTailor);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/personal/personalApproved/?repage";
	}

}