/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 数据查询关联菜单Entity
 * @author ydt
 * @version 2016-03-21
 */
public class DataQueryMenu extends DataEntity<DataQueryMenu> {
	
	private static final long serialVersionUID = 1L;
	private Long queryId;		// 查询编号
	private String menuId;		// 菜单编号
	private String title;		// 标题
	private String exportable;	//能否导出数据
	
	public DataQueryMenu() {
		super();
	}

	public DataQueryMenu(String id){
		super(id);
	}

	public Long getQueryId() {
		return queryId;
	}

	public void setQueryId(Long queryId) {
		this.queryId = queryId;
	}
	
	@Length(min=0, max=64, message="菜单编号长度必须介于 0 和 64 之间")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	@Length(min=0, max=50, message="标题长度必须介于 0 和 50 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=10, message="能否导出数据必须介于 0 和 10 之间")
	public String getExportable() {
		return exportable;
	}

	public void setExportable(String exportable) {
		this.exportable = exportable;
	}
	
}