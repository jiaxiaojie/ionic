/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.web;

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
import com.thinkgem.jeesite.modules.entity.MobileAwardRecord;
import com.thinkgem.jeesite.modules.marketing.service.MobileAwardRecordService;

/**
 * 手机号中奖记录Controller
 * @author ydt
 * @version 2016-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/mobileAwardRecord")
public class MobileAwardRecordController extends BaseController {

	@Autowired
	private MobileAwardRecordService mobileAwardRecordService;
	
	@ModelAttribute
	public MobileAwardRecord get(@RequestParam(required=false) String id) {
		MobileAwardRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mobileAwardRecordService.get(id);
		}
		if (entity == null){
			entity = new MobileAwardRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:mobileAwardRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(MobileAwardRecord mobileAwardRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MobileAwardRecord> page = mobileAwardRecordService.findPage(new Page<MobileAwardRecord>(request, response), mobileAwardRecord); 
		model.addAttribute("page", page);
		return "modules/marketing/mobileAwardRecordList";
	}

	@RequiresPermissions("marketing:mobileAwardRecord:view")
	@RequestMapping(value = "form")
	public String form(MobileAwardRecord mobileAwardRecord, Model model) {
		model.addAttribute("mobileAwardRecord", mobileAwardRecord);
		return "modules/marketing/mobileAwardRecordForm";
	}

	@RequiresPermissions("marketing:mobileAwardRecord:edit")
	@RequestMapping(value = "save")
	public String save(MobileAwardRecord mobileAwardRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, mobileAwardRecord)){
			return form(mobileAwardRecord, model);
		}
		mobileAwardRecordService.save(mobileAwardRecord);
		addMessage(redirectAttributes, "保存手机号中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/mobileAwardRecord/?repage";
	}
	
	@RequiresPermissions("marketing:mobileAwardRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(MobileAwardRecord mobileAwardRecord, RedirectAttributes redirectAttributes) {
		mobileAwardRecordService.delete(mobileAwardRecord);
		addMessage(redirectAttributes, "删除手机号中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/mobileAwardRecord/?repage";
	}

}