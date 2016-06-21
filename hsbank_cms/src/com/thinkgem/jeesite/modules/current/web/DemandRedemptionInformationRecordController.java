/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.web;

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
import com.thinkgem.jeesite.modules.entity.DemandRedemptionInformationRecord;
import com.thinkgem.jeesite.modules.current.service.DemandRedemptionInformationRecordService;

/**
 * 活期赎回信息记录Controller
 * @author huangyuchen
 * @version 2016-04-11
 */
@Controller
@RequestMapping(value = "${adminPath}/current/demandRedemptionInformationRecord")
public class DemandRedemptionInformationRecordController extends BaseController {

	@Autowired
	private DemandRedemptionInformationRecordService demandRedemptionInformationRecordService;
	
	@ModelAttribute
	public DemandRedemptionInformationRecord get(@RequestParam(required=false) String id) {
		DemandRedemptionInformationRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = demandRedemptionInformationRecordService.get(id);
		}
		if (entity == null){
			entity = new DemandRedemptionInformationRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("current:demandRedemptionInformationRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(DemandRedemptionInformationRecord demandRedemptionInformationRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DemandRedemptionInformationRecord> page = demandRedemptionInformationRecordService.findPage(new Page<DemandRedemptionInformationRecord>(request, response), demandRedemptionInformationRecord); 
		model.addAttribute("page", page);
		return "modules/current/demandRedemptionInformationRecordList";
	}

	@RequiresPermissions("current:demandRedemptionInformationRecord:view")
	@RequestMapping(value = "form")
	public String form(DemandRedemptionInformationRecord demandRedemptionInformationRecord, Model model) {
		model.addAttribute("demandRedemptionInformationRecord", demandRedemptionInformationRecord);
		return "modules/current/demandRedemptionInformationRecordForm";
	}

	@RequiresPermissions("current:demandRedemptionInformationRecord:edit")
	@RequestMapping(value = "save")
	public String save(DemandRedemptionInformationRecord demandRedemptionInformationRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, demandRedemptionInformationRecord)){
			return form(demandRedemptionInformationRecord, model);
		}
		demandRedemptionInformationRecordService.save(demandRedemptionInformationRecord);
		addMessage(redirectAttributes, "保存活期赎回信息记录成功");
		return "redirect:"+Global.getAdminPath()+"/current/demandRedemptionInformationRecord/?repage";
	}
	
	@RequiresPermissions("current:demandRedemptionInformationRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(DemandRedemptionInformationRecord demandRedemptionInformationRecord, RedirectAttributes redirectAttributes) {
		demandRedemptionInformationRecordService.delete(demandRedemptionInformationRecord);
		addMessage(redirectAttributes, "删除活期赎回信息记录成功");
		return "redirect:"+Global.getAdminPath()+"/current/demandRedemptionInformationRecord/?repage";
	}

}