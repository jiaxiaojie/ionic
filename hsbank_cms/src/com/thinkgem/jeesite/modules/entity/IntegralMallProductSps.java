/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品规格参数Entity
 * @author lizibo
 * @version 2015-09-21
 */
public class IntegralMallProductSps extends DataEntity<IntegralMallProductSps> {
	
	private static final long serialVersionUID = 1L;
	private Long paraId;		// 参数编号
	private Long productId;		// 产品编号
	private String paraName;		// 参数名称
	private String paraVal;		// 参数值
	private String paraSeq;		// 参数顺序
	private String status;		// 状态
	
	public IntegralMallProductSps() {
		super();
	}

	public IntegralMallProductSps(String id){
		super(id);
	}

	public Long getParaId() {
		return paraId;
	}

	public void setParaId(Long paraId) {
		this.paraId = paraId;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=40, message="参数名称长度必须介于 0 和 40 之间")
	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	
	@Length(min=0, max=200, message="参数值长度必须介于 0 和 200 之间")
	public String getParaVal() {
		return paraVal;
	}

	public void setParaVal(String paraVal) {
		this.paraVal = paraVal;
	}
	
	@Length(min=0, max=11, message="参数顺序长度必须介于 0 和 11 之间")
	public String getParaSeq() {
		return paraSeq;
	}

	public void setParaSeq(String paraSeq) {
		this.paraSeq = paraSeq;
	}
	
	@Length(min=0, max=11, message="状态长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}