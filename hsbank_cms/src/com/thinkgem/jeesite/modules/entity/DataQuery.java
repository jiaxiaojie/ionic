/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 数据查询Entity
 * @author ydt
 * @version 2016-03-17
 */
public class DataQuery extends DataEntity<DataQuery> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String fromContent;		// fromContent
	private String filterContent;		// filterContent
	private String groupByContent;		// groupByContent
	private String havingContent;		// havingContent
	private String orderByContent;		// orderByContent
	private String limitContent;		// limitContent
	private String description;		// 说明
	
	private String title;		//标题
	private String exportable;	//能否导出数据

	private List<DataQueryRow> dataQueryRowList = new ArrayList<DataQueryRow>();
	private List<DataQueryForm> dataQueryFormList = new ArrayList<DataQueryForm>();
	private List<DataQueryMenu> dataQueryMenuList = new ArrayList<DataQueryMenu>();
	
	public DataQuery() {
		super();
	}

	public DataQuery(String id){
		super(id);
	}

	@Length(min=0, max=50, message="名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFromContent() {
		return fromContent;
	}

	public void setFromContent(String fromContent) {
		this.fromContent = fromContent;
	}
	
	public String getFilterContent() {
		return filterContent;
	}

	public void setFilterContent(String filterContent) {
		this.filterContent = filterContent;
	}
	
	@Length(min=0, max=500, message="orderByContent长度必须介于 0 和 500 之间")
	public String getOrderByContent() {
		return orderByContent;
	}

	public void setOrderByContent(String orderByContent) {
		this.orderByContent = orderByContent;
	}
	
	@Length(min=0, max=500, message="limitContent长度必须介于 0 和 500 之间")
	public String getLimitContent() {
		return limitContent;
	}

	public void setLimitContent(String limitContent) {
		this.limitContent = limitContent;
	}
	
	@Length(min=0, max=500, message="groupByContent长度必须介于 0 和 500 之间")
	public String getGroupByContent() {
		return groupByContent;
	}

	public void setGroupByContent(String groupByContent) {
		this.groupByContent = groupByContent;
	}
	
	@Length(min=0, max=500, message="havingContent长度必须介于 0 和 500 之间")
	public String getHavingContent() {
		return havingContent;
	}

	public void setHavingContent(String havingContent) {
		this.havingContent = havingContent;
	}
	
	@Length(min=0, max=500, message="说明长度必须介于 0 和 500 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DataQueryRow> getDataQueryRowList() {
		return dataQueryRowList;
	}

	public void setDataQueryRowList(List<DataQueryRow> dataQueryRowList) {
		this.dataQueryRowList = dataQueryRowList;
	}

	public List<DataQueryForm> getDataQueryFormList() {
		return dataQueryFormList;
	}

	public void setDataQueryFormList(List<DataQueryForm> dataQueryFormList) {
		this.dataQueryFormList = dataQueryFormList;
	}

	public List<DataQueryMenu> getDataQueryMenuList() {
		return dataQueryMenuList;
	}

	public void setDataQueryMenuList(List<DataQueryMenu> dataQueryMenuList) {
		this.dataQueryMenuList = dataQueryMenuList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExportable() {
		return exportable;
	}

	public void setExportable(String exportable) {
		this.exportable = exportable;
	}
	
}