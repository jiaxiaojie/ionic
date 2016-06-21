/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 会员信用认证信息Entity
 * @author ydt
 * @version 2015-07-13
 */
public class CustomerCreditAuth extends DataEntity<CustomerCreditAuth> {
	
	private static final long serialVersionUID = 1L;
	private Long customerId;		// 会员编号
	private String personCreditAuthCode;		// 个人基本信息认证
	private String personCreditAuthRemark;		// 个人基本信息认证说明
	private Double personScore;		// 个人基本信息信用得分
	private String identityCreditAuthCode;		// 身份认证
	private String identityCreditAuthRemark;		// 身份认证说明
	private Double identityScore;		// 身份认证得分
	private String incomeCreditAuthCode;		// 收入认证
	private String incomeCreditAuthRemark;		// 收入认证说明
	private Double incomeScore;		// 收入认证得分
	private String creditAuthCode;		// 信用卡认证
	private String creditAuthRemark;		// 信用卡认证说明
	private Double creditCardScore;		// 信用卡认证得分
	private String housingCreditAuthCode;		// 房产证明认证
	private String housingCreditAuthRemark;		// 房产证明认证说明
	private Double housingScore;		// 房产证明认证得分
	private String carCreditAuthCode;		// 车辆证明认证
	private String carCreditAuthRemark;		// 车辆证明认证说明
	private Double carScore;		// 车辆证明认证得分
	private String addressCreditAuthCode;		// 居住地认证
	private String addressCreditAuthRemark;		// 居住地认证说明
	private Double addressScore;		// 居住地认证得分
	private String educationCreditAuthCode;		// 学历认证
	private String educationCreditAuthRemark;		// 学历认证说明
	private Double educationScore;		// 学历认证得分
	private String creditReportAuthCode;		// 信用报告认证
	private String creditReportAuthRemark;		// 信用报告认证说明
	private Double creditReportScore;		// 信用报告认证得分
	
	private String creditLevel;		// 信用等级
	private Double creditScore;		// 信用得分
	private Double creditLimit;		// 信用额度（单位：元）
	private String remark;		// 其他说明

	private Long accountId;			// 会员账号编号
	private String accountName;		//登录名
	private String customerName;	//用户姓名
	private String mobile;			//会员手机号
	
	public CustomerCreditAuth() {
		super();
	}

	public CustomerCreditAuth(String id){
		super(id);
	}

	@NotNull(message="会员编号不能为空")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=2, message="个人基本信息认证长度必须介于 0 和 2 之间")
	public String getPersonCreditAuthCode() {
		return personCreditAuthCode;
	}

	public void setPersonCreditAuthCode(String personCreditAuthCode) {
		this.personCreditAuthCode = personCreditAuthCode;
	}
	
	@Length(min=0, max=500, message="个人基本信息认证说明长度必须介于 0 和 500 之间")
	public String getPersonCreditAuthRemark() {
		return personCreditAuthRemark;
	}

	public void setPersonCreditAuthRemark(String personCreditAuthRemark) {
		this.personCreditAuthRemark = personCreditAuthRemark;
	}
	
	public Double getPersonScore() {
		return personScore;
	}

	public void setPersonScore(Double personScore) {
		this.personScore = personScore;
	}
	
	@Length(min=0, max=2, message="身份认证长度必须介于 0 和 2 之间")
	public String getIdentityCreditAuthCode() {
		return identityCreditAuthCode;
	}

	public void setIdentityCreditAuthCode(String identityCreditAuthCode) {
		this.identityCreditAuthCode = identityCreditAuthCode;
	}
	
	@Length(min=0, max=500, message="身份认证说明长度必须介于 0 和 500 之间")
	public String getIdentityCreditAuthRemark() {
		return identityCreditAuthRemark;
	}

	public void setIdentityCreditAuthRemark(String identityCreditAuthRemark) {
		this.identityCreditAuthRemark = identityCreditAuthRemark;
	}
	
	public Double getIdentityScore() {
		return identityScore;
	}

	public void setIdentityScore(Double identityScore) {
		this.identityScore = identityScore;
	}
	
	@Length(min=0, max=2, message="收入认证长度必须介于 0 和 2 之间")
	public String getIncomeCreditAuthCode() {
		return incomeCreditAuthCode;
	}

	public void setIncomeCreditAuthCode(String incomeCreditAuthCode) {
		this.incomeCreditAuthCode = incomeCreditAuthCode;
	}
	
	@Length(min=0, max=500, message="收入认证说明长度必须介于 0 和 500 之间")
	public String getIncomeCreditAuthRemark() {
		return incomeCreditAuthRemark;
	}

	public void setIncomeCreditAuthRemark(String incomeCreditAuthRemark) {
		this.incomeCreditAuthRemark = incomeCreditAuthRemark;
	}
	
	public Double getIncomeScore() {
		return incomeScore;
	}

	public void setIncomeScore(Double incomeScore) {
		this.incomeScore = incomeScore;
	}
	
	@Length(min=0, max=2, message="信用卡认证长度必须介于 0 和 2 之间")
	public String getCreditAuthCode() {
		return creditAuthCode;
	}

	public void setCreditAuthCode(String creditAuthCode) {
		this.creditAuthCode = creditAuthCode;
	}
	
	@Length(min=0, max=500, message="信用卡认证说明长度必须介于 0 和 500 之间")
	public String getCreditAuthRemark() {
		return creditAuthRemark;
	}

	public void setCreditAuthRemark(String creditAuthRemark) {
		this.creditAuthRemark = creditAuthRemark;
	}
	
	public Double getCreditCardScore() {
		return creditCardScore;
	}

	public void setCreditCardScore(Double creditCardScore) {
		this.creditCardScore = creditCardScore;
	}
	
	@Length(min=0, max=2, message="房产证明认证长度必须介于 0 和 2 之间")
	public String getHousingCreditAuthCode() {
		return housingCreditAuthCode;
	}

	public void setHousingCreditAuthCode(String housingCreditAuthCode) {
		this.housingCreditAuthCode = housingCreditAuthCode;
	}
	
	@Length(min=0, max=500, message="房产证明认证说明长度必须介于 0 和 500 之间")
	public String getHousingCreditAuthRemark() {
		return housingCreditAuthRemark;
	}

	public void setHousingCreditAuthRemark(String housingCreditAuthRemark) {
		this.housingCreditAuthRemark = housingCreditAuthRemark;
	}
	
	public Double getHousingScore() {
		return housingScore;
	}

	public void setHousingScore(Double housingScore) {
		this.housingScore = housingScore;
	}
	
	@Length(min=0, max=2, message="车辆证明认证长度必须介于 0 和 2 之间")
	public String getCarCreditAuthCode() {
		return carCreditAuthCode;
	}

	public void setCarCreditAuthCode(String carCreditAuthCode) {
		this.carCreditAuthCode = carCreditAuthCode;
	}
	
	@Length(min=0, max=500, message="车辆证明认证说明长度必须介于 0 和 500 之间")
	public String getCarCreditAuthRemark() {
		return carCreditAuthRemark;
	}

	public void setCarCreditAuthRemark(String carCreditAuthRemark) {
		this.carCreditAuthRemark = carCreditAuthRemark;
	}
	
	public Double getCarScore() {
		return carScore;
	}

	public void setCarScore(Double carScore) {
		this.carScore = carScore;
	}
	
	@Length(min=0, max=2, message="居住地认证长度必须介于 0 和 2 之间")
	public String getAddressCreditAuthCode() {
		return addressCreditAuthCode;
	}

	public void setAddressCreditAuthCode(String addressCreditAuthCode) {
		this.addressCreditAuthCode = addressCreditAuthCode;
	}
	
	@Length(min=0, max=500, message="居住地认证说明长度必须介于 0 和 500 之间")
	public String getAddressCreditAuthRemark() {
		return addressCreditAuthRemark;
	}

	public void setAddressCreditAuthRemark(String addressCreditAuthRemark) {
		this.addressCreditAuthRemark = addressCreditAuthRemark;
	}
	
	public Double getAddressScore() {
		return addressScore;
	}

	public void setAddressScore(Double addressScore) {
		this.addressScore = addressScore;
	}
	
	@Length(min=0, max=2, message="学历认证长度必须介于 0 和 2 之间")
	public String getEducationCreditAuthCode() {
		return educationCreditAuthCode;
	}

	public void setEducationCreditAuthCode(String educationCreditAuthCode) {
		this.educationCreditAuthCode = educationCreditAuthCode;
	}
	
	@Length(min=0, max=500, message="学历认证说明长度必须介于 0 和 500 之间")
	public String getEducationCreditAuthRemark() {
		return educationCreditAuthRemark;
	}

	public void setEducationCreditAuthRemark(String educationCreditAuthRemark) {
		this.educationCreditAuthRemark = educationCreditAuthRemark;
	}
	
	public Double getEducationScore() {
		return educationScore;
	}

	public void setEducationScore(Double educationScore) {
		this.educationScore = educationScore;
	}

	@Length(min=0, max=2, message="信用报告认证长度必须介于 0 和 2 之间")
	public String getCreditReportAuthCode() {
		return creditReportAuthCode;
	}

	public void setCreditReportAuthCode(String creditReportAuthCode) {
		this.creditReportAuthCode = creditReportAuthCode;
	}

	@Length(min=0, max=500, message="信用报告认证说明长度必须介于 0 和 500 之间")
	public String getCreditReportAuthRemark() {
		return creditReportAuthRemark;
	}

	public void setCreditReportAuthRemark(String creditReportAuthRemark) {
		this.creditReportAuthRemark = creditReportAuthRemark;
	}

	public Double getCreditReportScore() {
		return creditReportScore;
	}

	public void setCreditReportScore(Double creditReportScore) {
		this.creditReportScore = creditReportScore;
	}

	@Length(min=0, max=2, message="信用等级长度必须介于 0 和 20 之间")
	public String getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	
	public Double getCreditScore() {
		return creditScore;
	}
	
	public void setCreditScore(Double creditScore) {
		this.creditScore = creditScore;
	}
	
	public Double getTotalScore() {
		Double totalScore = 0D;
		if(personScore != null && "2".equals(personCreditAuthCode)) {
			totalScore += personScore;
		}
		if(identityScore != null && "2".equals(identityCreditAuthCode)) {
			totalScore += identityScore;
		}
		if(incomeScore != null && "2".equals(incomeCreditAuthCode)) {
			totalScore += incomeScore;
		}
		if(creditCardScore != null && "2".equals(creditAuthCode)) {
			totalScore += creditCardScore;
		}
		if(housingScore != null && "2".equals(housingCreditAuthCode)) {
			totalScore += housingScore;
		}
		if(carScore != null && "2".equals(carCreditAuthCode)) {
			totalScore += carScore;
		}
		if(addressScore != null && "2".equals(addressCreditAuthCode)) {
			totalScore += addressScore;
		}
		if(educationScore != null && "2".equals(educationCreditAuthCode)) {
			totalScore += educationScore;
		}
		if(creditReportScore != null && "2".equals(creditReportAuthCode)) {
			totalScore += creditReportScore;
		}
		return totalScore;
	}
	
	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	
	@Length(min=0, max=1000, message="其他说明长度必须介于 0 和 1000 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * 设置默认值
	 */
	public void setDefaultValue() {
		if(StringUtils.isBlank(personCreditAuthCode)) {
			personCreditAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(identityCreditAuthCode)) {
			identityCreditAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(incomeCreditAuthCode)) {
			incomeCreditAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(creditAuthCode)) {
			creditAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(housingCreditAuthCode)) {
			housingCreditAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(carCreditAuthCode)) {
			carCreditAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(addressCreditAuthCode)) {
			addressCreditAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(educationCreditAuthCode)) {
			educationCreditAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(creditReportAuthCode)) {
			creditReportAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}