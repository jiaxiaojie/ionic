/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.DateInfo;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.DateInfoService;

/**
 * 活期产品参数Controller
 * @author ydt
 * @version 2015-12-11
 */
@Controller
@RequestMapping(value = "${adminPath}/current/dateInfo")
public class DateInfoController extends BaseController {

	@Autowired
	private DateInfoService dateInfoService;
	
	@ModelAttribute
	public DateInfo get(@RequestParam(required=false) String id) {
		DateInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dateInfoService.get(id);
		}
		if (entity == null){
			entity = new DateInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("current:dateInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(DateInfo dateInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DateInfo> page = dateInfoService.findPage(new Page<DateInfo>(request, response), dateInfo); 
		model.addAttribute("page", page);
		return "modules/current/dateInfoList";
	}
	
	/**
	 * 修改工作日状态
	 * @param dateInfo
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("current:dateInfo:view")
	@RequestMapping(value = {"updateisworkday"})
	public String findByDate(DateInfo dateInfo, HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dateInfo)){
			return form(dateInfo, model);
		}
		DateInfo entity = dateInfoService.findByDate(dateInfo);
		entity.setIsWorkday(CurrentProjectConstant.IS_WORKDAY_NO.equals(entity.getIsWorkday())?CurrentProjectConstant.IS_WORKDAY_YES:CurrentProjectConstant.IS_WORKDAY_NO);
		dateInfoService.update(entity);
		addMessage(redirectAttributes, "工作日状态修改成功");
		return "modules/current/dateInfo";
	}
	
	
	/**
	 * 查询日期数据并设置数据的样式
	 * @param start
	 * @param end
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("current:dateInfo:view")
	@RequestMapping(value = {"queryByDate"})
	public String queryByDate(Date start,Date end, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<DateInfo> dateInfos = dateInfoService.queryByDate( start, end);
		List<Object> list = new ArrayList<Object>();
		for(DateInfo dateInfo: dateInfos){
			HashMap<String,String> map =  new HashMap<String,String>();
			map.put("title", DictUtils.getDictLabel(dateInfo.getIsWorkday(), "is_workday", ""));
			map.put("color", !"0".equals(dateInfo.getIsWorkday())?"#FF9F89":"#FFF68F");
			map.put("rendering", "background");
			map.put("start", DateUtils.formatDate(dateInfo.getDate(), "yyyy-MM-dd"));
			list.add(map);
		}
		return renderString(response,list);
	}	

	@RequiresPermissions("current:dateInfo:view")
	@RequestMapping(value = "form")
	public String form(DateInfo dateInfo, Model model) {
		model.addAttribute("dateInfo", dateInfo);
		return "modules/current/dateInfoForm";
	}
	
	
	

	@RequiresPermissions("current:dateInfo:edit")
	@RequestMapping(value = "save")
	public String save(DateInfo dateInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dateInfo)){
			return form(dateInfo, model);
		}
		dateInfoService.save(dateInfo);
		addMessage(redirectAttributes, "保存活期产品参数成功");
		return "redirect:"+Global.getAdminPath()+"/current/dateInfo/?repage";
	}
	
	@RequiresPermissions("current:dateInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(DateInfo dateInfo, RedirectAttributes redirectAttributes) {
		dateInfoService.delete(dateInfo);
		addMessage(redirectAttributes, "删除活期产品参数成功");
		return "redirect:"+Global.getAdminPath()+"/current/dateInfo/?repage";
	}

}