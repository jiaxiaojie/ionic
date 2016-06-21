/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.entity.CustomerAddress;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.IntegralMallProduct;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductType;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 花生乐园-订单记录（参与记录）参数
 * @author liuguoqing
 * @version 2016-05-24
 */
public class IntegralProductOrderResp {

	private Long productId;		// 产品编号
	private String productName;	//产品名称
	private Integer productCount; // 产品数量
	private String productLogoMin;// 产品logo
	private String customerName; //用户

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public String getProductLogoMin() {
		return productLogoMin;
	}

	public void setProductLogoMin(String productLogoMin) {
		this.productLogoMin = productLogoMin;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("IntegralProductOrderResp{");
		sb.append("productId=").append(productId);
		sb.append(", productName='").append(productName).append('\'');
		sb.append(", productCount=").append(productCount);
		sb.append(", productLogoMin='").append(productLogoMin).append('\'');
		sb.append(", customerName='").append(customerName).append('\'');
		sb.append('}');
		return sb.toString();
	}
}