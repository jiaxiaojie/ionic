/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 轮播图显示终端Entity
 * @author hyc
 * @version 2015-11-11
 */
public class CarouselShowTerm extends DataEntity<CarouselShowTerm> {
	
	private static final long serialVersionUID = 1L;
	private Long carouselId;		// 轮播图编号
	private Long termCode;		// 终端
	
	public CarouselShowTerm() {
		super();
	}

	public CarouselShowTerm(String id){
		super(id);
	}

	public Long getCarouselId() {
		return carouselId;
	}

	public void setCarouselId(Long carouselId) {
		this.carouselId = carouselId;
	}
	
	@Length(min=0, max=2, message="终端长度必须介于 0 和 2 之间")
	public Long getTermCode() {
		return termCode;
	}

	public void setTermCode(Long termCode) {
		this.termCode = termCode;
	}

}