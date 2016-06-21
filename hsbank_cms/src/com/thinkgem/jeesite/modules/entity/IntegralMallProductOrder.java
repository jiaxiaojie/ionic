/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 花生乐园订单Entity
 * @author lizibo
 * @version 2015-09-21
 */
public class IntegralMallProductOrder extends DataEntity<IntegralMallProductOrder> {
	
	private static final long serialVersionUID = 1L;
	private String productTypeId;		// 产品类型
	private Long productId;		// 产品编号
	private Integer productCount;		// 产品数量
	private Long customerAccount;		// 用户编号
	private Long addressId;		// 用户住址编号
	private String orderStatus;		// 订单状态
	private Long flowUserId;		// 跟踪人员
	private Date createDt;		// 创建时间
	private Date createDtStart;
	private Date createDtEnd;
	private String createChannelId;		// 创建渠道
	private CustomerAddress address; //花生乐园用户地址
	private IntegralMallProduct product; //产品信息
	private CustomerBase customerBase; //会员基本信息
	private String customerAccountShow;//会员账号信息
	private String districtIds;   //区、县Id

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDistrictIds() {
		return districtIds;
	}

	public void setDistrictIds(String districtIds) {
		this.districtIds = districtIds;
	}

	private IntegralMallProductType productType; //产品类型
	private String productName;	//产品名称
	private String customerName; //用户
	private Integer productPrice; //产品价格
	private Integer price;//订单总价
	public String getCustomerAccountShow() {
		return customerAccountShow;
	}
	public void setCustomerAccountShow(String customerAccountShow) {
		this.customerAccountShow = customerAccountShow;
	}
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	private String orderNo; //订单编号
	private String showName; //显示名称
	private String mobile; //联系手机号
	private String addressShow; //下单时地址
	private String postCode; //邮编
	
	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddressShow() {
		return addressShow;
	}

	public void setAddressShow(String addressShow) {
		this.addressShow = addressShow;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public IntegralMallProductOrder() {
		super();
	}

	public IntegralMallProductOrder(String id){
		super(id);
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	
	public Long getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(Long customerAccount) {
		this.customerAccount = customerAccount;
	}
	
	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	
	@Length(min=0, max=2, message="订单状态长度必须介于 0 和 2 之间")
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public Long getFlowUserId() {
		return flowUserId;
	}

	public void setFlowUserId(Long flowUserId) {
		this.flowUserId = flowUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	@Length(min=0, max=2, message="创建渠道长度必须介于 0 和 2 之间")
	public String getCreateChannelId() {
		return createChannelId;
	}

	public void setCreateChannelId(String createChannelId) {
		this.createChannelId = createChannelId;
	}

	public CustomerAddress getAddress() {
		return address;
	}

	public void setAddress(CustomerAddress address) {
		this.address = address;
	}

	public IntegralMallProduct getProduct() {
		return product;
	}

	public void setProduct(IntegralMallProduct product) {
		this.product = product;
	}

	public CustomerBase getCustomerBase() {
		return customerBase;
	}

	public void setCustomerBase(CustomerBase customerBase) {
		this.customerBase = customerBase;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public IntegralMallProductType getProductType() {
		return productType;
	}

	public void setProductType(IntegralMallProductType productType) {
		this.productType = productType;
	}
	public Date getCreateDtStart() {
		return createDtStart;
	}
	public void setCreateDtStart(Date createDtStart) {
		this.createDtStart = createDtStart;
	}
	public Date getCreateDtEnd() {
		return createDtEnd;
	}
	public void setCreateDtEnd(Date createDtEnd) {
		this.createDtEnd = createDtEnd;
	}
}