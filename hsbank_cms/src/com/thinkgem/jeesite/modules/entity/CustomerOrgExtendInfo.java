/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 组织会员扩展信息Entity
 * @author ydt
 * @version 2015-06-30
 */
public class CustomerOrgExtendInfo extends DataEntity<CustomerOrgExtendInfo> {
	
	private static final long serialVersionUID = 1L;
	private Long customerId;		// 会员编号
	private String orgCode;		// 组织机构代码
	private String orgBusinessLicense;		// 营业执照编号
	private String orgTaxRegistration;		// 税务登记号
	private Integer registeredIfe;		// 注册年限
	private Float registeredCapital;		// 注册资金（万元）
	private Float netWorth;		// 资产净值（万元）
	private Float cashInflows;		// 上年度经营现金流入（万元）
	private String industry;		// 行业
	private String businessActivities;		// 经营情况
	private String litigationSituatio;		// 涉诉情况
	private String creditRecord;		// 征信记录
	private Long createUserId;		// 创建人
	private Long modifyUserId;		// 修改人
	private Integer status;		// 状态
	
	public CustomerOrgExtendInfo() {
		super();
	}

	public CustomerOrgExtendInfo(String id){
		super(id);
	}

	@NotNull(message="会员编号不能为空")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=50, message="组织机构代码长度必须介于 0 和 50 之间")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Length(min=0, max=50, message="营业执照编号长度必须介于 0 和 50 之间")
	public String getOrgBusinessLicense() {
		return orgBusinessLicense;
	}

	public void setOrgBusinessLicense(String orgBusinessLicense) {
		this.orgBusinessLicense = orgBusinessLicense;
	}
	
	@Length(min=0, max=50, message="税务登记号长度必须介于 0 和 50 之间")
	public String getOrgTaxRegistration() {
		return orgTaxRegistration;
	}

	public void setOrgTaxRegistration(String orgTaxRegistration) {
		this.orgTaxRegistration = orgTaxRegistration;
	}
	
	public Integer getRegisteredIfe() {
		return registeredIfe;
	}

	public void setRegisteredIfe(Integer registeredIfe) {
		this.registeredIfe = registeredIfe;
	}
	
	public Float getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(Float registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	
	public Float getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(Float netWorth) {
		this.netWorth = netWorth;
	}

	public Float getCashInflows() {
		return cashInflows;
	}

	public void setCashInflows(Float cashInflows) {
		this.cashInflows = cashInflows;
	}
	
	@Length(min=0, max=500, message="行业长度必须介于 0 和 500 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Length(min=0, max=1000, message="经营情况长度必须介于 0 和 1000 之间")
	public String getBusinessActivities() {
		return businessActivities;
	}

	public void setBusinessActivities(String businessActivities) {
		this.businessActivities = businessActivities;
	}
	
	@Length(min=0, max=1000, message="涉诉情况长度必须介于 0 和 1000 之间")
	public String getLitigationSituatio() {
		return litigationSituatio;
	}

	public void setLitigationSituatio(String litigationSituatio) {
		this.litigationSituatio = litigationSituatio;
	}
	
	@Length(min=0, max=1000, message="征信记录长度必须介于 0 和 1000 之间")
	public String getCreditRecord() {
		return creditRecord;
	}

	public void setCreditRecord(String creditRecord) {
		this.creditRecord = creditRecord;
	}
	
	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	public Long getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}