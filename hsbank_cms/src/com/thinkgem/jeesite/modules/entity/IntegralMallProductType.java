/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 花生乐园上架产品类别Entity
 * @author lizibo
 * @version 2015-09-23
 */
public class IntegralMallProductType extends DataEntity<IntegralMallProductType> {
	
	private static final long serialVersionUID = 1L;
	private Long typeId;		// type_id
	private String typeName;		// type_name
	private Long parentTypeId;		// parent_type_id
	private String typeLog;		// type_log
	
	public IntegralMallProductType() {
		super();
	}

	public IntegralMallProductType(String id){
		super(id);
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	@Length(min=0, max=200, message="type_name长度必须介于 0 和 200 之间")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Long getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(Long parentTypeId) {
		this.parentTypeId = parentTypeId;
	}
	
	public String getTypeLog() {
		return typeLog;
	}

	public void setTypeLog(String typeLog) {
		this.typeLog = typeLog;
	}
	
}