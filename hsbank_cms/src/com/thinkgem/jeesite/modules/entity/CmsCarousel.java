/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 首页轮播图Entity
 * @author ydt
 * @version 2015-07-03
 */
public class CmsCarousel extends DataEntity<CmsCarousel> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String image;		// 图片
	private String href;		// 链接
	private String target;		// 目标
	private Integer sort;		// 排序
	
	public CmsCarousel() {
		super();
	}

	public CmsCarousel(String id){
		super(id);
	}

	@Length(min=0, max=50, message="名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1000, message="图片长度必须介于 0 和 1000 之间")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Length(min=0, max=1000, message="链接长度必须介于 0 和 1000 之间")
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	@Length(min=0, max=50, message="目标长度必须介于 0 和 50 之间")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}