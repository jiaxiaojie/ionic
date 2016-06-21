/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import com.thinkgem.jeesite.modules.sys.entity.Area;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 花生乐园用户地址Entity
 * @author lizibo
 * @version 2015-09-21
 */
public class CustomerAddress extends DataEntity<CustomerAddress> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 用户编号
	private String showName;		// 收件人名称
	private String mobile;		// 收件人手机号
	private String address;		// 收件人地址
	private String postCode;		// 收件人邮编
	private String isDefault = ProjectConstant.CUSTOMER_ADDRESS_IS_DEFAULT_NOT;		// 是否缺省收件地址
	private Date createDt;		// 创建时间
	private String status;		// 状态
	private String districtId;   //区、县Id

	private Area  province;      //地址省份
	private Area city;           //地址城市
	private Area district;      //区县
	
	public CustomerAddress() {
		super();
	}

	public CustomerAddress(String id){
		super(id);
	}
	
	public CustomerAddress(Long accountId,String status){
		this.accountId = accountId;
		this.status= status;
	}

	public Area getDistrict() {
		return district;
	}

	public void setDistrict(Area district) {
		this.district = district;
	}

	public Area getProvince() {
		return province;
	}

	public void setProvince(Area province) {
		this.province = province;
	}

	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@NotBlank(message="收件人名称不能为空")
	@Length(min=0, max=100, message="收件人名称长度必须介于 0 和 100 之间")
	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
	
	@NotBlank(message="收件人手机号不能为空")
	@Length(min=0, max=20, message="收件人手机号长度必须介于 0 和 20 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@NotBlank(message="收件人地址不能为空")
	@Length(min=0, max=500, message="收件人地址长度必须介于 0 和 500 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@Length(min=0, max=6, message="收件人邮编长度必须介于 0 和 6 之间")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	
	@Length(min=0, max=1, message="是否缺省收件地址长度必须介于 0 和 1 之间")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
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