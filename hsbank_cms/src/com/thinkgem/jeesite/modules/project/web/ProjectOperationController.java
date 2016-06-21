/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.web;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentSplitRecordService;

/**
 * 项目运营数据Controller
 * @author huangyuchen
 * @version 2015-12-01
 */
@Controller
@RequestMapping(value = "${adminPath}/operation/projectOperation")
public class ProjectOperationController extends BaseController {

	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectRepaymentSplitRecordService projectRepaymentSplitRecordService;
	@Autowired
	private CustomerAccountService customerAccountService;
	
	@ModelAttribute
	public ProjectBaseInfo get(@RequestParam(required=false) String id) {
		ProjectBaseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectBaseInfoService.get(id);
		}
		if (entity == null){
			entity = new ProjectBaseInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("operation:projectOperation:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectBaseInfo projectBaseInfo,Date beginDt, Date endDt, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Map<String,Object>> page = projectBaseInfoService.findProjectOperationList(new Page<Map<String ,Object>>(request, response),projectBaseInfo,beginDt,endDt);
		model.addAttribute("beginDt", beginDt);
		model.addAttribute("endDt", endDt);
		model.addAttribute("page", page);
		return "modules/operation/projectOperationList";
	}
	
	/**
	 * 导出数据
	 * 
	 * @return
	 */
	@RequiresPermissions("operation:projectOperation:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目财务数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<Map<String,Object>> list = projectBaseInfoService.findProjectOperationListNoPage(projectBaseInfo);
            LinkedHashMap<String,String> fieldMap = new LinkedHashMap<String,String>();
            fieldMap.put("projectName", "项目名称");
            fieldMap.put("publishDt", "项目发布时间");
            fieldMap.put("biddingDeadline", "开始计息时间");
            fieldMap.put("financeMoney", "借款额");
            fieldMap.put("hasFinancedMoney", "募资额");
            fieldMap.put("hasRepaiedPrincipal", "已还本金");
            fieldMap.put("hasRepaiedInterest", "已还利息");
            fieldMap.put("projectStatus", "name=项目状态;dictType=project_status_dict");
    		new ExportExcel("项目运营数据", fieldMap).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出项目财务数据失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/modules/operation/projectOperationList?repage";
	}
	
	
	/**
	 * 导出数据
	 * 
	 * @return
	 */
	@RequiresPermissions("operation:projectOperation:view")
    @RequestMapping(value = "export1", method=RequestMethod.POST)
    public String exportFile1(Date startDate,Date endDate, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目还款数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		List<Map<String,Object>> list = projectRepaymentSplitRecordService.findProjectRepayPlanListNoPage( startDate, endDate);
            LinkedHashMap<String,String> fieldMap = new LinkedHashMap<String,String>();
            fieldMap.put("date", "还款日期");
            fieldMap.put("principal", "应还本金");
            fieldMap.put("interest", "应还利息");
            fieldMap.put("amount", "应还总额");
    		new ExportExcel("项目还款数据（起始时间："+StringUtils.replaceNull(DateUtils.formatDate(startDate, "yyyy-MM-dd"), "不限制")+",结束时间："+StringUtils.replaceNull(DateUtils.formatDate(endDate, "yyyy-MM-dd"), "不限制")+")", fieldMap).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出项目还款数据失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/modules/operation/projectRepayPlanList?repage";
	}
	
    /**
     * 项目还款计划列表及查询
     * @param startDate
     * @param endDate
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequiresPermissions("operation:projectOperation:view")//
	@RequestMapping(value = {"projectRepayPlanList"})
	public String projectRepayPlanList(ProjectRepaymentSplitRecord projectRepaymentSplitRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> accountNameList = customerAccountService.selectListByaccountType();
		model.addAttribute("accountNameList", accountNameList);
		//按用户查询用户在易宝的用户平台标示
		String  platformUserNo = null;
		try{
			platformUserNo =customerAccountService.selectPlatformUserNoByAccountName(projectRepaymentSplitRecord.getCustomerAccount().getAccountName());
			model.addAttribute("accountName", projectRepaymentSplitRecord.getCustomerAccount().getAccountName());
			model.addAttribute("platformUserNo", platformUserNo);
		}
		catch(Exception e){
		}
		Date startDate = projectRepaymentSplitRecord.getStartDate();
		Date endDate = projectRepaymentSplitRecord.getEndDate();
		//根据易宝平台标示或投资时间段查询还款计划列表
		Page<Map<String,Object>> page = projectRepaymentSplitRecordService.findProjectRepayPlanList(new Page<Map<String,Object>>(request, response), startDate, endDate,platformUserNo);
		model.addAttribute("page", page);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("projectRepaymentSplitRecord",projectRepaymentSplitRecord);
		
		model.addAttribute("customerAccount", new CustomerAccount()); 
		
		//查询三天内的还款计划
		Double last3DayAmount = projectRepaymentSplitRecordService.amountStatistics(new Date(), DateUtils.dateAddDay(new Date(), 2), platformUserNo);
		model.addAttribute("last3DayAmount", last3DayAmount);
		return "modules/operation/projectRepayPlanList";
		
	}
	

    /**
     * 查询某一天的还款计划
     * @param date
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequiresPermissions("operation:projectOperation:view")
	@RequestMapping(value = {"projectRepayPlanDetail", ""})
	public String projectRepayPlanDetail(Date date, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Map<String,Object>> page = projectRepaymentSplitRecordService.findProjectRepayPlanListByDate(new Page<Map<String,Object>>(request, response), date);
		model.addAttribute("page", page);
		model.addAttribute("date", date);
		return "modules/operation/projectRepayPlanDetail";
	}
}