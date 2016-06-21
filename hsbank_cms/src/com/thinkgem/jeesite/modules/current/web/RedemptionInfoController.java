/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.web;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectRedemptionApplyService;
import com.thinkgem.jeesite.modules.entity.CurrentProjectRedemptionApply;

/**
 * 赎回数据Controller
 * @author ydt
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/current/redemptionInfo")
public class RedemptionInfoController extends BaseController {

	@Autowired
	private CurrentProjectRedemptionApplyService currentProjectRedemptionApplyService;
	
	@ModelAttribute
	public CurrentProjectRedemptionApply get(@RequestParam(required=false) String id) {
		CurrentProjectRedemptionApply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentProjectRedemptionApplyService.get(id);
		}
		if (entity == null){
			entity = new CurrentProjectRedemptionApply();
		}
		return entity;
	}
	/**
	 * 查询赎回信息
	 * @param currentProjectRedemptionApply
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("current:redemptionInfo:view")
	@RequestMapping(value = {"redemptionInfoList"})
	public String redemptionInfoList(CurrentProjectRedemptionApply currentProjectRedemptionApply,Date startDate,Date endDate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Map<String,Object>> page = currentProjectRedemptionApplyService.findRedemptionInfoList(new Page<Map<String,Object>>(request, response),currentProjectRedemptionApply,startDate,endDate);
		model.addAttribute("page", page);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "modules/current/redemptionInfoList";
	}


}