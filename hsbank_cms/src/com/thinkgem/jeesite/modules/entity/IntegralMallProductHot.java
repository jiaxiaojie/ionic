/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品活动标签Entity
 * @author lizibo
 * @version 2015-09-21
 */
public class IntegralMallProductHot extends DataEntity<IntegralMallProductHot> {
	
	private static final long serialVersionUID = 1L;
	private Long productId;		// 产品编号
	private String hotType;		// 标签类型
	private String hotValue;		// 标签值
	
	public IntegralMallProductHot() {
		super();
	}

	public IntegralMallProductHot(String id){
		super(id);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=2, message="标签类型长度必须介于 0 和 2 之间")
	public String getHotType() {
		return hotType;
	}

	public void setHotType(String hotType) {
		this.hotType = hotType;
	}
	
	@Length(min=0, max=200, message="标签值长度必须介于 0 和 200 之间")
	public String getHotValue() {
		return hotValue;
	}

	public void setHotValue(String hotValue) {
		this.hotValue = hotValue;
	}
	
}