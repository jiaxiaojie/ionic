/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员工作信息Entity
 * @author ydt
 * @version 2015-07-13
 */
public class CustomerWork extends DataEntity<CustomerWork> {
	
	private static final long serialVersionUID = 1L;
	private Long customerId;		// 会员编号
	private String companyName;		// 公司名称
	private String companyTypeCode;		// 公司性质
	private String companySizeCode;		// 公司规模
	private String position;		// 职位
	private String positionFile;		// 在职证明
	private String incomeCode;		// 年收入
	private String incomeFile;		// 收入证明
	private Integer workYears;		// 在岗时间（单位：年）
	private String contacts;		// 公司联系人
	private String companyPhone;		// 公司联系电话
	private String companyAddress;		// 公司所在城市
	private Date createDt;		// 创建时间
	private Date lastModifyDt;		// 最后一次修改时间
	
	private Long accountId;
	private String accountType;
	
	public CustomerWork() {
		super();
	}

	public CustomerWork(String id){
		super(id);
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=50, message="公司名称长度必须介于 0 和 50 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=2, message="公司性质长度必须介于 0 和 2 之间")
	public String getCompanyTypeCode() {
		return companyTypeCode;
	}

	public void setCompanyTypeCode(String companyTypeCode) {
		this.companyTypeCode = companyTypeCode;
	}
	
	@Length(min=0, max=2, message="公司规模长度必须介于 0 和 2 之间")
	public String getCompanySizeCode() {
		return companySizeCode;
	}

	public void setCompanySizeCode(String companySizeCode) {
		this.companySizeCode = companySizeCode;
	}
	
	@Length(min=0, max=30, message="职位长度必须介于 0 和 30 之间")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=500, message="在职证明长度必须介于 0 和 500 之间")
	public String getPositionFile() {
		return positionFile;
	}

	public void setPositionFile(String positionFile) {
		this.positionFile = positionFile;
	}
	
	@Length(min=0, max=2, message="年收入长度必须介于 0 和 2 之间")
	public String getIncomeCode() {
		return incomeCode;
	}

	public void setIncomeCode(String incomeCode) {
		this.incomeCode = incomeCode;
	}
	
	@Length(min=0, max=500, message="收入证明长度必须介于 0 和 500 之间")
	public String getIncomeFile() {
		return incomeFile;
	}

	public void setIncomeFile(String incomeFile) {
		this.incomeFile = incomeFile;
	}
	
	public Integer getWorkYears() {
		return workYears;
	}

	public void setWorkYears(Integer workYears) {
		this.workYears = workYears;
	}
	
	@Length(min=0, max=20, message="公司联系人长度必须介于 0 和 20 之间")
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@Length(min=0, max=20, message="公司联系电话长度必须介于 0 和 20 之间")
	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	
	@Length(min=0, max=50, message="公司所在城市长度必须介于 0 和 50 之间")
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastModifyDt() {
		return lastModifyDt;
	}

	public void setLastModifyDt(Date lastModifyDt) {
		this.lastModifyDt = lastModifyDt;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
}