/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品价格策略Entity
 * @author lizibo
 * @version 2015-09-21
 */
public class IntegralMallProductPrice extends DataEntity<IntegralMallProductPrice> {
	
	private static final long serialVersionUID = 1L;
	private Long productId;		// 产品编号
	private String priceType;		// 产品价格类型
	private Integer marketNewPrice;		// 活动价格
	private Double marketDiscount;		// 活动折扣
	private Date beginDt;		// 开始时间
	private Date endDt;		// 结束时间
	private String status;		// 状态
	private Long createUserId;		// 创建人
	private Date createDt;		// 创建时间
	
	public IntegralMallProductPrice() {
		super();
	}

	public IntegralMallProductPrice(String id){
		super(id);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=2, message="产品价格类型长度必须介于 0 和 2 之间")
	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	public Integer getMarketNewPrice() {
		return marketNewPrice;
	}

	public void setMarketNewPrice(Integer marketNewPrice) {
		this.marketNewPrice = marketNewPrice;
	}
	
	public Double getMarketDiscount() {
		return marketDiscount;
	}

	public void setMarketDiscount(Double marketDiscount) {
		this.marketDiscount = marketDiscount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDt() {
		return beginDt;
	}

	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
}