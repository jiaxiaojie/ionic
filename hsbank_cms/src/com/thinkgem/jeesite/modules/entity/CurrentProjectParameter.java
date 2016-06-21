/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期产品参数Entity
 * @author ydt
 * @version 2015-12-09
 */
public class CurrentProjectParameter extends DataEntity<CurrentProjectParameter> {
	
	private static final long serialVersionUID = 1L;
	private Double maxInvestmentMoney;		// 最大持有金额
	private Double investmentFeeValue;		// 购买费用
	private String investmentFeeMode;		// 购买计费方式
	private Double oneDayMaxRedeemMoney;		// 单日最大赎回金额
	private Double redeemFeeValue;		// 赎回费用
	private String redeemFeeMode;		// 赎回计费方式
	private String redeemSeparateTime;		// 赎回到账时间分割点
	private Date createDt;		// 创建时间
	private Long createUserId;		// 创建人
	private Date modifyDt;		// 修改时间
	private Long modifyUserId;		// 修改人
	
	public CurrentProjectParameter() {
		super();
	}

	public CurrentProjectParameter(String id){
		super(id);
	}

	public Double getMaxInvestmentMoney() {
		return maxInvestmentMoney;
	}

	public void setMaxInvestmentMoney(Double maxInvestmentMoney) {
		this.maxInvestmentMoney = maxInvestmentMoney;
	}
	
	public Double getInvestmentFeeValue() {
		return investmentFeeValue;
	}

	public void setInvestmentFeeValue(Double investmentFeeValue) {
		this.investmentFeeValue = investmentFeeValue;
	}
	
	@Length(min=0, max=2, message="购买计费方式长度必须介于 0 和 2 之间")
	public String getInvestmentFeeMode() {
		return investmentFeeMode;
	}

	public void setInvestmentFeeMode(String investmentFeeMode) {
		this.investmentFeeMode = investmentFeeMode;
	}
	
	public Double getOneDayMaxRedeemMoney() {
		return oneDayMaxRedeemMoney;
	}

	public void setOneDayMaxRedeemMoney(Double oneDayMaxRedeemMoney) {
		this.oneDayMaxRedeemMoney = oneDayMaxRedeemMoney;
	}
	
	public Double getRedeemFeeValue() {
		return redeemFeeValue;
	}

	public void setRedeemFeeValue(Double redeemFeeValue) {
		this.redeemFeeValue = redeemFeeValue;
	}
	
	@Length(min=0, max=2, message="赎回计费方式长度必须介于 0 和 2 之间")
	public String getRedeemFeeMode() {
		return redeemFeeMode;
	}

	public void setRedeemFeeMode(String redeemFeeMode) {
		this.redeemFeeMode = redeemFeeMode;
	}
	
	public String getRedeemSeparateTime() {
		return redeemSeparateTime;
	}

	public void setRedeemSeparateTime(String redeemSeparateTime) {
		this.redeemSeparateTime = redeemSeparateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}
	
	public Long getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	
}