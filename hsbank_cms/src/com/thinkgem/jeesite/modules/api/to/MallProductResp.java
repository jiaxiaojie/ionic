package com.thinkgem.jeesite.modules.api.to;

import java.util.List;


public class MallProductResp {

	private String productId;		// 产品编号
	private String productName;		// 产品名称
	private String typeId;		// 产品类别
	private String typeName;		// 产品类别名称
	private String logoMin;		// 商品图片
	private String logoNormal;	//商品图片(中图）
	private String introduction;		// 产品简介
	private Integer price;		// 原价
	private Integer showPrice; 	//现价
	private String upDt;		// 上架时间
	private String dowDt;		// 下架时间
	private Integer productCount;		// 上架数量
	private Integer productSurplus;		// 剩余数量
	private Long status;		// 产品状态
	private String statusName;	//状态名称
	private String isRecommend;		// 是否重点推荐
	private List<MallProductParaResp> paraList;	//参数列表
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getLogoMin() {
		return logoMin;
	}
	public void setLogoMin(String logoMin) {
		this.logoMin = logoMin;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(Integer showPrice) {
		this.showPrice = showPrice;
	}
	public String getUpDt() {
		return upDt;
	}
	public void setUpDt(String upDt) {
		this.upDt = upDt;
	}
	public String getDowDt() {
		return dowDt;
	}
	public void setDowDt(String dowDt) {
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
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	public String getLogoNormal() {
		return logoNormal;
	}
	public void setLogoNormal(String logoNormal) {
		this.logoNormal = logoNormal;
	}
	public List<MallProductParaResp> getParaList() {
		return paraList;
	}
	public void setParaList(List<MallProductParaResp> paraList) {
		this.paraList = paraList;
	}

}
