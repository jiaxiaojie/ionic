/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 查询数据列Entity
 * @author ydt
 * @version 2016-03-17
 */
public class DataQueryRow extends DataEntity<DataQueryRow> {
	
	private static final long serialVersionUID = 1L;
	private Long queryId;		// 查询编号
	private String rowName;		// 列名
	private String showRowName;		// 列显示名
	private Integer sort;		// 排序
	private String sortable;		// 是否可排序
	private String dictType;		// 数据字典
	private String dateFormat;		//日期格式化
	
	public DataQueryRow() {
		super();
	}

	public DataQueryRow(String id){
		super(id);
	}

	public Long getQueryId() {
		return queryId;
	}

	public void setQueryId(Long queryId) {
		this.queryId = queryId;
	}
	
	@Length(min=0, max=100, message="列名长度必须介于 0 和 100 之间")
	public String getRowName() {
		return rowName;
	}

	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	
	@Length(min=0, max=100, message="列显示名长度必须介于 0 和 100 之间")
	public String getShowRowName() {
		return showRowName;
	}

	public void setShowRowName(String showRowName) {
		this.showRowName = showRowName;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=10, message="是否可排序长度必须介于 0 和 10 之间")
	public String getSortable() {
		return sortable;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	@Length(min=0, max=100, message="数据字典长度必须介于 0 和 100 之间")
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	@Length(min=0, max=50, message="日期格式化长度必须介于 0 和 50 之间")
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

}