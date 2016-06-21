/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 汇率参数Entity
 * @author lzb
 * @version 2016-04-20
 */
public class SysExchangeRatePara extends DataEntity<SysExchangeRatePara> {
	
	private static final long serialVersionUID = 1L;
	private String rateType;		// 汇率类型
	private String rateName;		// 汇率名称
	private Double annualizedRate;		// 年化利率
	private Date createDt;		// 创建时间
	private String status;		// 状态
	
	public SysExchangeRatePara() {
		super();
	}

	public SysExchangeRatePara(String id){
		super(id);
	}

	@Length(min=0, max=2, message="汇率类型长度必须介于 0 和 2 之间")
	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	
	@Length(min=0, max=50, message="汇率名称长度必须介于 0 和 50 之间")
	public String getRateName() {
		return rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	
	
	
	public Double getAnnualizedRate() {
		return annualizedRate;
	}

	public void setAnnualizedRate(Double annualizedRate) {
		this.annualizedRate = annualizedRate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}