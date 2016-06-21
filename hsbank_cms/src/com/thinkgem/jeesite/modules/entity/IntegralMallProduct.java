/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 花生乐园上架产品Entity
 * @author lizibo
 * @version 2015-09-22
 */
public class IntegralMallProduct extends DataEntity<IntegralMallProduct> {
	
	private static final long serialVersionUID = 1L;
	private Long productId;		// 产品编号
	private String productName;		// 产品名称
	private Long productTypeId;		// 产品类别
	private String productTypeName;		//产品类别名称
	private String productLogoMin;		// 产品logo1
	private String productLogoNormal;		// 产品logo2
	private String productLogoMax;		// 产品logo3
	private String productIntroduction;		// 产品简介
	private String isRecommend;		// 是否重点推荐
	private Integer price;		// 兑换价格
	private Date upDt;		// 上架时间
	private Date dowDt;		// 下架时间
	private Integer productCount;		// 上架数量
	private Integer productSurplus;		// 剩余数量
	private Date createDt;		// 创建时间
	private Long createUserId;		// 创建人
	private Date reviewDt;		// 审批时间
	private Long reviewUserId;		// 审批人
	private String reviewRemark;		// 审批意见
	private String status;		// 产品状态
	private Date beginUpDt;		// 开始 上架时间
	private Date endUpDt;		// 结束 上架时间
	private Integer showPrice; 	//生效的价格
	private Long relTicketId;	//关联券id
	private Integer productParValue;//产品面值
	
	
	private IntegralMallProductPrice integralMallProductPrice;//产品价格策略
	
	
	
	public IntegralMallProductPrice getIntegralMallProductPrice() {
		return integralMallProductPrice;
	}

	public void setIntegralMallProductPrice(IntegralMallProductPrice integralMallProductPrice) {
		this.integralMallProductPrice = integralMallProductPrice;
	}

	public Integer getProductParValue() {
		return productParValue;
	}

	public void setProductParValue(Integer productParValue) {
		this.productParValue = productParValue;
	}

	public Integer getShowPrice() {
		return showPrice;
	}

	public void setShowPrice(Integer showPrice) {
		this.showPrice = showPrice;
	}

	public IntegralMallProduct() {
		super();
	}

	public IntegralMallProduct(String id){
		super(id);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=300, message="产品名称长度必须介于 0 和 300 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}
	
	@Length(min=0, max=300, message="产品logo1长度必须介于 0 和 300 之间")
	public String getProductLogoMin() {
		return productLogoMin;
	}

	public void setProductLogoMin(String productLogoMin) {
		this.productLogoMin = productLogoMin;
	}
	
	@Length(min=0, max=300, message="产品logo2长度必须介于 0 和 300 之间")
	public String getProductLogoNormal() {
		return productLogoNormal;
	}

	public void setProductLogoNormal(String productLogoNormal) {
		this.productLogoNormal = productLogoNormal;
	}
	
	@Length(min=0, max=300, message="产品logo3长度必须介于 0 和 300 之间")
	public String getProductLogoMax() {
		return productLogoMax;
	}

	public void setProductLogoMax(String productLogoMax) {
		this.productLogoMax = productLogoMax;
	}
	
	public String getProductIntroduction() {
		return productIntroduction;
	}

	public void setProductIntroduction(String productIntroduction) {
		this.productIntroduction = productIntroduction;
	}
	
	@Length(min=0, max=2, message="是否重点推荐长度必须介于 0 和 2 之间")
	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpDt() {
		return upDt;
	}

	public void setUpDt(Date upDt) {
		this.upDt = upDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDowDt() {
		return dowDt;
	}

	public void setDowDt(Date dowDt) {
		this.dowDt = dowDt;
	}
	
	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	
	public Integer getProductSurplus() {
		return productSurplus;
	}

	public void setProductSurplus(Integer productSurplus) {
		this.productSurplus = productSurplus;
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
	public Date getReviewDt() {
		return reviewDt;
	}

	public void setReviewDt(Date reviewDt) {
		this.reviewDt = reviewDt;
	}
	
	public Long getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(Long reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	
	@Length(min=0, max=200, message="审批意见长度必须介于 0 和 200 之间")
	public String getReviewRemark() {
		return reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}
	
	@Length(min=0, max=2, message="产品状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginUpDt() {
		return beginUpDt;
	}

	public void setBeginUpDt(Date beginUpDt) {
		this.beginUpDt = beginUpDt;
	}
	
	public Date getEndUpDt() {
		return endUpDt;
	}

	public void setEndUpDt(Date endUpDt) {
		this.endUpDt = endUpDt;
	}

	public Long getRelTicketId() {
		return relTicketId;
	}

	public void setRelTicketId(Long relTicketId) {
		this.relTicketId = relTicketId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	
	
}