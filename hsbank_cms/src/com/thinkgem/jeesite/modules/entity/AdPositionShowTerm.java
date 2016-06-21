/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 广告位显示终端Entity
 * @author huangyuchen
 * @version 2016-05-17
 */
public class AdPositionShowTerm extends DataEntity<AdPositionShowTerm> {
	
	private static final long serialVersionUID = 1L;
	private Long adPositionId;		// 广告位编号
	private String termCode;		// 终端
	
	public AdPositionShowTerm() {
		super();
	}

	public AdPositionShowTerm(String id){
		super(id);
	}

	public Long getAdPositionId() {
		return adPositionId;
	}

	public void setAdPositionId(Long adPositionId) {
		this.adPositionId = adPositionId;
	}
	
	@Length(min=0, max=2, message="终端长度必须介于 0 和 2 之间")
	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	
}