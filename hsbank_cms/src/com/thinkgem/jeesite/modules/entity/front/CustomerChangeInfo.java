package com.thinkgem.jeesite.modules.entity.front;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.CustomerCreditAuth;
import com.thinkgem.jeesite.modules.entity.CustomerWork;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

public class CustomerChangeInfo {
	private Long accountId;
	private Long customerId;
	private String genderCode;
	private boolean genderCodeCanChange;
	private String educationCode;
	private boolean educationCodeCanChange;
	private String educationSchool;
	private boolean educationSchoolCanChange;
	private String marriageCode;
	private boolean marriageCodeCanChange;
	private String address;
	private boolean addressCanChange;
	private String companyTypeCode;
	private boolean companyTypeCodeCanChange;
	private String companySizeCode;
	private boolean companySizeCodeCanChange;
	private String position;
	private boolean positionCanChange;
	private String incomeCode;
	private boolean incomeCodeCanChange;
	
	public CustomerChangeInfo() {}
	
	public CustomerChangeInfo(CustomerBase customerBase, CustomerWork customerWork, CustomerCreditAuth customerCreditAuth) {
		accountId = customerBase.getAccountId();
		customerId = customerBase.getCustomerId();
		genderCode = customerBase.getGenderCode();
		educationCode = customerBase.getEducationCode();
		educationSchool = customerBase.getEducationSchool();
		marriageCode = customerBase.getMarriageCode();
		address = customerBase.getAddress();
		companyTypeCode = customerWork.getCompanyTypeCode();
		companySizeCode = customerWork.getCompanySizeCode();
		position = customerWork.getPosition();
		incomeCode = customerWork.getIncomeCode();

		genderCodeCanChange = canChange(genderCode, customerCreditAuth.getPersonCreditAuthCode());
		educationCodeCanChange = canChange(educationCode, customerCreditAuth.getEducationCreditAuthCode());
		educationSchoolCanChange = canChange(educationSchool, customerCreditAuth.getEducationCreditAuthCode());
		marriageCodeCanChange = canChange(marriageCode, customerCreditAuth.getAddressCreditAuthCode());
		addressCanChange = canChange(address, customerCreditAuth.getAddressCreditAuthCode());
		companyTypeCodeCanChange = canChange(companyTypeCode, customerCreditAuth.getIncomeCreditAuthCode());
		companySizeCodeCanChange = canChange(companySizeCode, customerCreditAuth.getIncomeCreditAuthCode());
		positionCanChange = canChange(position, customerCreditAuth.getIncomeCreditAuthCode());
		incomeCodeCanChange = canChange(incomeCode, customerCreditAuth.getIncomeCreditAuthCode());
	}
	
	/**
	 * 如果信息为空，且未处于提交认证或认证通过状态，则可修改
	 * @param info
	 * @param creditAuthCode
	 * @return
	 */
	private boolean canChange(String info, String creditAuthCode) {
		return StringUtils.isBlank(info) || !(ProjectConstant.CUSTOMER_CREDIT_AUTH_STATUS_AUTHING.equals(creditAuthCode) || ProjectConstant.CUSTOMER_CREDIT_AUTH_STATUS_AUTHED.equals(creditAuthCode));
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getGenderCode() {
		return genderCode;
	}
	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}
	public String getEducationCode() {
		return educationCode;
	}

	public void setEducationCode(String educationCode) {
		this.educationCode = educationCode;
	}

	public boolean isEducationCodeCanChange() {
		return educationCodeCanChange;
	}

	public void setEducationCodeCanChange(boolean educationCodeCanChange) {
		this.educationCodeCanChange = educationCodeCanChange;
	}

	public boolean isGenderCodeCanChange() {
		return genderCodeCanChange;
	}
	public void setGenderCodeCanChange(boolean genderCodeCanChange) {
		this.genderCodeCanChange = genderCodeCanChange;
	}
	public String getEducationSchool() {
		return educationSchool;
	}
	public void setEducationSchool(String educationSchool) {
		this.educationSchool = educationSchool;
	}
	public boolean getEducationSchoolCanChange() {
		return educationSchoolCanChange;
	}
	public void setEducationSchoolCanChange(boolean educationSchoolCanChange) {
		this.educationSchoolCanChange = educationSchoolCanChange;
	}
	public String getMarriageCode() {
		return marriageCode;
	}
	public void setMarriageCode(String marriageCode) {
		this.marriageCode = marriageCode;
	}
	public boolean isMarriageCodeCanChange() {
		return marriageCodeCanChange;
	}
	public void setMarriageCodeCanChange(boolean marriageCodeCanChange) {
		this.marriageCodeCanChange = marriageCodeCanChange;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isAddressCanChange() {
		return addressCanChange;
	}
	public void setAddressCanChange(boolean addressCanChange) {
		this.addressCanChange = addressCanChange;
	}
	public String getCompanyTypeCode() {
		return companyTypeCode;
	}
	public void setCompanyTypeCode(String companyTypeCode) {
		this.companyTypeCode = companyTypeCode;
	}
	public boolean isCompanyTypeCodeCanChange() {
		return companyTypeCodeCanChange;
	}
	public void setCompanyTypeCodeCanChange(boolean companyTypeCodeCanChange) {
		this.companyTypeCodeCanChange = companyTypeCodeCanChange;
	}
	public String getCompanySizeCode() {
		return companySizeCode;
	}
	public void setCompanySizeCode(String companySizeCode) {
		this.companySizeCode = companySizeCode;
	}
	public boolean isCompanySizeCodeCanChange() {
		return companySizeCodeCanChange;
	}
	public void setCompanySizeCodeCanChange(boolean companySizeCodeCanChange) {
		this.companySizeCodeCanChange = companySizeCodeCanChange;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public boolean isPositionCanChange() {
		return positionCanChange;
	}
	public void setPositionCanChange(boolean positionCanChange) {
		this.positionCanChange = positionCanChange;
	}
	public String getIncomeCode() {
		return incomeCode;
	}
	public void setIncomeCode(String incomeCode) {
		this.incomeCode = incomeCode;
	}
	public boolean isIncomeCodeCanChange() {
		return incomeCodeCanChange;
	}
	public void setIncomeCodeCanChange(boolean incomeCodeCanChange) {
		this.incomeCodeCanChange = incomeCodeCanChange;
	}
}
