package com.thinkgem.jeesite.modules.api.to;

public class MallOrderResp {

	private String orderCode;	//订单编号
	private String productId;	//商品Id
	private String productName;	//商品名称
	private String productCount;	//商品数量
	private String logoMin;	//商品图片
	private Integer price;	//花生豆
	private String createDt;	//交易时间
	private Long status;	//状态
	private String statusName;	//状态名称
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
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
	public String getProductCount() {
		return productCount;
	}
	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
	public String getLogoMin() {
		return logoMin;
	}
	public void setLogoMin(String logoMin) {
		this.logoMin = logoMin;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
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
	
	
}
