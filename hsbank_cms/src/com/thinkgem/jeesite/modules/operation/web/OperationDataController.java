/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.operation.web;

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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.entity.OperationData;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.operation.service.OperationDataService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;

/**
 * 运营数据Controller
 * @author huangyuchen
 * @version 2015-12-01
 */
@Controller
@RequestMapping(value = "${adminPath}/operation/operationData")
public class OperationDataController extends BaseController {

	@Autowired
	private OperationDataService operationDataService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	
	@ModelAttribute
	public OperationData get(@RequestParam(required=false) String id) {
		OperationData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = operationDataService.get(id);
		}
		if (entity == null){
			entity = new OperationData();
		}
		return entity;
	}
	
	@RequiresPermissions("operation:operationData:view")
	@RequestMapping(value = {"list", ""})
	public String list(OperationData operationData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OperationData> page = operationDataService.findPage(new Page<OperationData>(request, response), operationData); 
		model.addAttribute("page", page);
		return "modules/operation/operationDataList";
	}
	  /**
		*按投资范围查询用户的投资数据
		*累计投资额度分为四个档次：0＜累计投资额度≤5000,5000＜累计投资额度≤10000,累计投资额度≤20000,累计投资额度＞20000 并根据时间段范围查询相应的投资
		* @param projectInvestmentRecord
	    * @param beginOpDt
		* @param endOpDt
		* @param request
		* @param response
		* @param model
		* @return
		*/
	@RequiresPermissions("operation:operationData:customerInvestmentAmountDistribution")
	@RequestMapping(value = "customerInvestmentAmountDistributionList")
	public String customerInvestmentAmountDistributionList(ProjectInvestmentRecord projectInvestmentRecord,Date beginOpDt,Date endOpDt, HttpServletRequest request, HttpServletResponse response, Model model) {
		 //按各个投资额度范围的投资人数
		Map<String, Object> map = projectInvestmentRecordService.findCustomerInvestmentAmountDistributionNoPage(beginOpDt,endOpDt);
		  //查询在各个投资范围内的的平局投资额度
		  Map<String, Object> map1 = projectInvestmentRecordService.findCustomerInvestmentAmountDistributionAvgNoPage(beginOpDt,endOpDt);
		  //查询总投资额度
		  Map<String, Object> map2 = projectInvestmentRecordService.findCustomerInvestmentAmount(beginOpDt,endOpDt);
		  model.addAttribute("beginOpDt", beginOpDt);
		  model.addAttribute("endOpDt", endOpDt);
		  model.addAttribute("map",map);
		  model.addAttribute("map1",map1);
		  model.addAttribute("map2",map2);
		return "/modules/customer/customerInvestmentAmountDistributionList";
	}
	
	
	/**
	 * 日投资额度统计（包括全部用户投资总额度和刷单用户投资总额度）
	 * @param projectInvestmentRecord
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("operation:operationData:InvestmentStatistics")
	@RequestMapping(value = "InvestmentStatistics")
	public String InvestmentStatistics(ProjectInvestmentRecord projectInvestmentRecord,Date beginOpDt,Date endOpDt, HttpServletRequest request, HttpServletResponse response, Model model) {
		 Page<Map<String, Object>> page = projectInvestmentRecordService.findCustomerInvestmentStatistics(new Page<Map<String ,Object>>(request, response),beginOpDt,endOpDt);
		  model.addAttribute("beginOpDt", beginOpDt);
		  model.addAttribute("endOpDt", endOpDt);
		  model.addAttribute("page",page);
		return "/modules/operation/InvestmentStatisticsList";
	}
	
	/**
	 * 按日期查询全部用户每日投资额度清单
	 * @param projectInvestmentRecord
	 * @param date
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("operation:operationData:InvestmentStatistics")
	@RequestMapping(value = {"daytotalAmountClearList"})
	public String daytotalAmountClearList(Date date, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Map<String,Object>> page = projectInvestmentRecordService.findInvestmentRecordByDate(new Page<Map<String,Object>>(request, response), date);
		model.addAttribute("page", page);
		model.addAttribute("date", date);
		return "modules/operation/daytotalAmountClearList";
	}
	
	
	/**
	 * 按日期查询小伙伴每日投资额度清单
	 * @param projectInvestmentRecord
	 * @param date
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("operation:operationData:InvestmentStatistics")
	@RequestMapping(value = {"friendsDaytotalAmountClearList"})
	public String friendsDaytotalAmountClearList(Date date, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Map<String,Object>> page = projectInvestmentRecordService.findFriendsInvestmentRecordByDate(new Page<Map<String,Object>>(request, response), date);
		model.addAttribute("page", page);
		model.addAttribute("date", date);
		return "modules/operation/friendsDaytotalAmountClearList";
	}
	
	
	
	
	
	
	/**
	 * 导出数据
	 * 
	 * @return
	 */
	@RequiresPermissions("operation:projectOperation:view")
    @RequestMapping(value = "exports", method=RequestMethod.POST)
    public String exportFile(Date beginOpDt,Date endOpDt,HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "日投资额统计"+DateUtils.getDate("yyyyMMdd")+".xlsx";
            List<Map<String, Object>> list = projectInvestmentRecordService.findCustomerInvestmentStatisticsNoPage(beginOpDt,endOpDt);
            LinkedHashMap<String,String> fieldMap = new LinkedHashMap<String,String>();
            fieldMap.put("date", "日期");
            fieldMap.put("sumAmount", "日投资总额度");
            fieldMap.put("friendsSumAmount", "好朋友投资总额度");
            new ExportExcel("项目还款数据（起始时间："+StringUtils.replaceNull(DateUtils.formatDate(beginOpDt, "yyyy-MM-dd"), "不限制")+",结束时间："+StringUtils.replaceNull(DateUtils.formatDate(endOpDt, "yyyy-MM-dd"), "不限制")+")", fieldMap).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出日投资额统计数据失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/modules/operation/InvestmentStatisticsList?repage";
	}
	
	
	
	
	/**
	 * 导出数据
	 * 
	 * @return
	 */
	@RequiresPermissions("operation:operationData:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(OperationData operationData, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运营数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<OperationData> list = operationDataService.findList(operationData); 
    		new ExportExcel("运营数据（起始时间："+StringUtils.replaceNull(DateUtils.formatDate(operationData.getBeginDt(), "yyyy-MM-dd"), "不限制")+",结束时间："+StringUtils.replaceNull(DateUtils.formatDate(operationData.getEndDt(), "yyyy-MM-dd"), "不限制")+")", OperationData.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出运营数据失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/modules/operation/operationDataList?repage";
    }

	@RequiresPermissions("operation:operationData:view")
	@RequestMapping(value = "form")
	public String form(OperationData operationData, Model model) {
		model.addAttribute("operationData", operationData);
		return "modules/operation/operationDataForm";
	}

	@RequiresPermissions("operation:operationData:edit")
	@RequestMapping(value = "save")
	public String save(OperationData operationData, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, operationData)){
			return form(operationData, model);
		}
		operationDataService.save(operationData);
		addMessage(redirectAttributes, "保存运营数据成功");
		return "redirect:"+Global.getAdminPath()+"/operation/operationData/?repage";
	}
	
	@RequiresPermissions("operation:operationData:edit")
	@RequestMapping(value = "delete")
	public String delete(OperationData operationData, RedirectAttributes redirectAttributes) {
		operationDataService.delete(operationData);
		addMessage(redirectAttributes, "删除运营数据成功");
		return "redirect:"+Global.getAdminPath()+"/operation/operationData/?repage";
	}

}