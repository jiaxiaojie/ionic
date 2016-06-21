/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.operation.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.DataQuery;
import com.thinkgem.jeesite.modules.entity.DataQueryForm;
import com.thinkgem.jeesite.modules.entity.DataQueryMenu;
import com.thinkgem.jeesite.modules.entity.DataQueryRow;
import com.thinkgem.jeesite.modules.gen.util.GenUtils;
import com.thinkgem.jeesite.modules.operation.service.DataQueryService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 数据查询Controller
 * @author ydt
 * @version 2016-03-17
 */
@Controller
@RequestMapping(value = "${adminPath}/operation/dataQuery")
public class DataQueryController extends BaseController {

	@Autowired
	private DataQueryService dataQueryService;
	
	@ModelAttribute
	public DataQuery get(@RequestParam(required=false) String id) {
		DataQuery entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dataQueryService.get(id);
		}
		if (entity == null){
			entity = new DataQuery();
		}
		return entity;
	}
	
	@RequiresPermissions("operation:dataQuery:view")
	@RequestMapping(value = {"list", ""})
	public String list(DataQuery dataQuery, HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println(request.getAttribute("data-href"));
		System.out.println(request.getParameter("data-href"));
		Page<DataQuery> page = dataQueryService.findPage(new Page<DataQuery>(request, response), dataQuery); 
		model.addAttribute("page", page);
		return "modules/operation/dataQueryList";
	}

	@RequiresPermissions("operation:dataQuery:view")
	@RequestMapping(value = "form")
	public String form(DataQuery dataQuery, Model model) {
		model.addAttribute("dataQuery", dataQuery);
		return "modules/operation/dataQueryForm";
	}

	@RequiresPermissions("operation:dataQuery:edit")
	@RequestMapping(value = "save")
	public String save(DataQuery dataQuery, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dataQuery)){
			return form(dataQuery, model);
		}
		dataQueryService.save(dataQuery);
		addMessage(redirectAttributes, "保存数据查询成功");
		return "redirect:"+Global.getAdminPath()+"/operation/dataQuery/?repage";
	}
	
	@RequiresPermissions("operation:dataQuery:edit")
	@RequestMapping(value = "delete")
	public String delete(DataQuery dataQuery, RedirectAttributes redirectAttributes) {
		dataQueryService.delete(dataQuery);
		addMessage(redirectAttributes, "删除数据查询成功");
		return "redirect:"+Global.getAdminPath()+"/operation/dataQuery/?repage";
	}
	
	@RequiresPermissions("operation:dataQuery:edit")
	@RequestMapping(value = "configRow")
	public String configRow(Long queryId, Model model) {
		DataQuery dataQuery = dataQueryService.get(queryId + "");
		List<DataQueryRow> dataQueryRowList = dataQueryService.getDataQueryRowList(queryId);
		dataQuery.setDataQueryRowList(dataQueryRowList);
		model.addAttribute("dataQuery", dataQuery);
		return "modules/operation/dataQueryRowList";
	}
	
	@RequiresPermissions("operation:dataQuery:edit")
	@RequestMapping(value = "saveDataQueryRow")
	public String saveDataQueryRow(DataQuery dataQuery, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dataQuery)){
			return form(dataQuery, model);
		}
		dataQueryService.saveDataQueryRowList(dataQuery);
		addMessage(redirectAttributes, "数据保存成功");
		return "redirect:"+Global.getAdminPath()+"/operation/dataQuery/?repage";
	}
	
	@RequiresPermissions("operation:dataQuery:edit")
	@RequestMapping(value = "configQueryForm")
	public String configQueryForm(Long queryId, Model model) {
		DataQuery dataQuery = dataQueryService.get(queryId + "");
		List<DataQueryForm> dataQueryFormList = dataQueryService.getDataQueryFormList(queryId);
		dataQuery.setDataQueryFormList(dataQueryFormList);
		model.addAttribute("dataQuery", dataQuery);
		model.addAttribute("config", GenUtils.getConfig());
		return "modules/operation/dataQueryFormList";
	}
	
	@RequiresPermissions("operation:dataQuery:edit")
	@RequestMapping(value = "saveDataQueryForm")
	public String saveDataQueryForm(DataQuery dataQuery, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dataQuery)){
			return form(dataQuery, model);
		}
		dataQueryService.saveDataQueryFormList(dataQuery);
		addMessage(redirectAttributes, "数据保存成功");
		return "redirect:"+Global.getAdminPath()+"/operation/dataQuery/?repage";
	}
	
	@RequiresPermissions("operation:dataQuery:edit")
	@RequestMapping(value = "configQueryMenu")
	public String configQueryMenu(Long queryId, Model model) {
		DataQuery dataQuery = dataQueryService.get(queryId + "");
		List<DataQueryMenu> dataQueryMenuList = dataQueryService.getDataQueryMenuList(queryId);
		dataQuery.setDataQueryMenuList(dataQueryMenuList);
		model.addAttribute("dataQuery", dataQuery);
		return "modules/operation/dataQueryMenuList";
	}
	
	@RequiresPermissions("operation:dataQuery:edit")
	@RequestMapping(value = "saveQueryMenu")
	public String saveQueryMenu(DataQuery dataQuery, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dataQuery)){
			return form(dataQuery, model);
		}
		dataQueryService.saveDataQueryMenuList(dataQuery);
		addMessage(redirectAttributes, "数据保存成功");
		return "redirect:"+Global.getAdminPath()+"/operation/dataQuery/?repage";
	}
	
	@RequiresPermissions("operation:dataQuery:view")
	@RequestMapping(value = "dataQuery")
	public String dataQuery(String menuId, HttpServletRequest request, HttpServletResponse response, Model model) {
		DataQuery dataQuery = dataQueryService.getByMenuId(menuId);
		List<DataQueryRow> dataQueryRowList = dataQueryService.getDataQueryRowList(Long.parseLong(dataQuery.getId()));
		List<DataQueryForm> dataQueryFormList = dataQueryService.getDataQueryFormList(Long.parseLong(dataQuery.getId()));
		Map<String,Object> para = getQueryPara(dataQuery, dataQueryRowList, dataQueryFormList, request);
		
		Page<Map<String,Object>> page = dataQueryService.query(para, new Page<Map<String,Object>>(request, response));
		model.addAttribute("menuId", menuId);
		model.addAttribute("title", dataQuery.getTitle());
		model.addAttribute("exportable", dataQuery.getExportable());
		model.addAttribute("page", page);
		model.addAttribute("dataQueryRowList", dataQueryRowList);
		model.addAttribute("dataQueryFormList", dataQueryFormList);
		return "modules/operation/dataQueryPage";
	}
	
	private Map<String,Object> getQueryPara(DataQuery dataQuery, List<DataQueryRow> dataQueryRowList, List<DataQueryForm> dataQueryFormList, HttpServletRequest request) {
		Map<String,Object> para = new HashMap<String,Object>();
		String fromContent = StringEscapeUtils.unescapeHtml4(dataQuery.getFromContent().replaceAll("\r|\n", " "));
		para.put("filterContent", dataQuery.getFilterContent());
		para.put("groupByContent", dataQuery.getGroupByContent());
		para.put("havingContent", dataQuery.getHavingContent());
		para.put("orderByContent", dataQuery.getOrderByContent());
		para.put("limitContent", dataQuery.getLimitContent());

		StringBuffer rows = new StringBuffer();
		for(DataQueryRow dataQueryRow : dataQueryRowList) {
			rows.append(dataQueryRow.getRowName()).append(" as ").append("'").append(dataQueryRow.getShowRowName()).append("'").append(",");
		}
		para.put("rows", rows.deleteCharAt(rows.length() - 1).toString());

		List<String> filterSqls = new ArrayList<String>();
		for(DataQueryForm dataQueryForm : dataQueryFormList) {
			String parameterValue = (String)request.getParameter(dataQueryForm.getParameter());
			if(StringUtils.isNotBlank(parameterValue)) {
				String expression = StringEscapeUtils.unescapeHtml4(dataQueryForm.getExpression());
				dataQueryForm.setParameterValue(parameterValue);
				String filterSql = expression.replace(" ", "").replace("#{" + dataQueryForm.getParameter() + "}", "'" + parameterValue + "'");
				fromContent = fromContent.replace("#{" + dataQueryForm.getParameter() + "}", "'" + parameterValue + "'");
				filterSqls.add(filterSql);
			} else if(!"1".equals(dataQueryForm.getNullable())) {
				filterSqls.add("1 = 2");
				fromContent = fromContent.replace("#{" + dataQueryForm.getParameter() + "}", "'-xxoo-'");
			}
		}
		para.put("filterSqls", filterSqls);
		para.put("fromContent", fromContent);
		return para;
	}
	
	@RequiresPermissions("operation:dataQuery:view")
	@RequestMapping(value = "dataQueryExport")
	public String dataQueryExport(String menuId, HttpServletRequest request, HttpServletResponse response, Model model) {
		DataQuery dataQuery = dataQueryService.getByMenuId(menuId);
		List<DataQueryRow> dataQueryRowList = dataQueryService.getDataQueryRowList(Long.parseLong(dataQuery.getId()));
		List<DataQueryForm> dataQueryFormList = dataQueryService.getDataQueryFormList(Long.parseLong(dataQuery.getId()));
		Map<String,Object> para = getQueryPara(dataQuery, dataQueryRowList, dataQueryFormList, request);
		
		List<Map<String,Object>> datas = dataQueryService.query(para);
		for(Map<String,Object> data : datas) {
			for(DataQueryRow dataQueryRow : dataQueryRowList) {
				if(StringUtils.isNotBlank(dataQueryRow.getDateFormat())) {
					String dateString = String.valueOf(data.get(dataQueryRow.getShowRowName()));
					dateString = dateString.indexOf(".") == -1 ? dateString : dateString.substring(0, dateString.indexOf(".") - 1);
					dateString = DateUtils.formatDate(DateUtils.parseDate(dateString), dataQueryRow.getDateFormat());
					data.put(dataQueryRow.getShowRowName(), dateString);
				}
				if(StringUtils.isNotBlank(dataQueryRow.getDictType())) {
					data.put(dataQueryRow.getShowRowName(), DictUtils.getDictLabel(String.valueOf(data.get(dataQueryRow.getShowRowName())), dataQueryRow.getDictType(), ""));
				}
			}
		}
		try {
			String fileName = dataQuery.getTitle() + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
			for(DataQueryRow dataQueryRow : dataQueryRowList) {
				fieldMap.put(dataQueryRow.getShowRowName(), dataQueryRow.getShowRowName());
			}
			new ExportExcel("", fieldMap).setDataList(datas).write(response, fileName).dispose();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "redirect:"+Global.getAdminPath()+"/operation/dataQuery/dataQuery?repage";
	}
}