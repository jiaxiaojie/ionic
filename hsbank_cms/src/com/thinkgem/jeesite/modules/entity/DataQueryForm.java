/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 数据查询表单Entity
 * @author ydt
 * @version 2016-03-18
 */
public class DataQueryForm extends DataEntity<DataQueryForm> {
	
	private static final long serialVersionUID = 1L;
	private Long queryId;		// 查询编号
	private String label;		// 标签
	private String parameter;		// 变量名
	private String showType;		// 表单显示类型
	private String dictType;		// 数据字典
	private String expression;		// 表达式
	private String nullable;		// 是否可空
	private Integer sort;		// 排序
	private String dateFormat;		//日期格式化
	
	private String parameterValue;
	
	public DataQueryForm() {
		super();
	}

	public DataQueryForm(String id){
		super(id);
	}

	public Long getQueryId() {
		return queryId;
	}

	public void setQueryId(Long queryId) {
		this.queryId = queryId;
	}
	
	@Length(min=0, max=50, message="标签长度必须介于 0 和 50 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Length(min=0, max=50, message="变量名长度必须介于 0 和 50 之间")
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	@Length(min=0, max=20, message="表单显示类型长度必须介于 0 和 20 之间")
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
	
	@Length(min=0, max=100, message="数据字典长度必须介于 0 和 100 之间")
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	@Length(min=0, max=10, message="是否可空长度必须介于 0 和 10 之间")
	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	@Length(min=0, max=50, message="日期格式化长度必须介于 0 和 50 之间")
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
}