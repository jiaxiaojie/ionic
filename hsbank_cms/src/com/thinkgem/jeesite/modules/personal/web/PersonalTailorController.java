/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.PersonalTailor;
import com.thinkgem.jeesite.modules.personal.service.PersonalTailorService;

/**
 * 私人订制项目Controller
 * @author yubin
 * @version 2016-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/personal/personalTailor")
public class PersonalTailorController extends BaseController {

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
	
	@RequiresPermissions("personal:personalTailor:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonalTailor personalTailor, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PersonalTailor> page = personalTailorService.findPage(new Page<PersonalTailor>(request, response), personalTailor); 
		model.addAttribute("page", page);
		return "modules/personal/personalTailorList";
	}

	@RequiresPermissions("personal:personalTailor:view")
	@RequestMapping(value = "form")
	public String form(PersonalTailor personalTailor, Model model) {
		model.addAttribute("personalTailor", personalTailor);
		return "modules/personal/personalTailorForm";
	}

	@RequiresPermissions("personal:personalTailor:edit")
	@RequestMapping(value = "save")
	public String save(PersonalTailor personalTailor, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, personalTailor)){
			return form(personalTailor, model);
		}
		personalTailorService.save(personalTailor);
		addMessage(redirectAttributes, "保存私人订制项目成功");
		return "redirect:"+Global.getAdminPath()+"/personal/personalTailor/?repage";
	}
	
	@RequiresPermissions("personal:personalTailor:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonalTailor personalTailor, RedirectAttributes redirectAttributes) {
		personalTailorService.delete(personalTailor);
		addMessage(redirectAttributes, "删除私人订制项目成功");
		return "redirect:"+Global.getAdminPath()+"/personal/personalTailor/?repage";
	}
	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("personal:personalTailor:edit")
	@RequestMapping(value = "checkName")
	public String checkName(String name) {
		PersonalTailor personalTailor= personalTailorService.gePersonalTailorByName(name); 
		if(personalTailor==null){
			return "true";
		}
		return "false";
	}
	

}