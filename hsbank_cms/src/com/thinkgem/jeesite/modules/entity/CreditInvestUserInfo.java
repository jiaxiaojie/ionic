/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdcardUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.credit.CreditConstant;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 债权投资用户信息Entity
 * @author wanduanrui
 * @version 2016-03-30
 */
public class CreditInvestUserInfo extends DataEntity<CreditInvestUserInfo> {
	
	private static final long serialVersionUID = 1L;
	private String investorName;		// 投资人姓名
	private String investorGender;		// 投资人性别
	private Date investorBirthday;		// 投资人生日
	private String investorContactInfo;		// 联系方式(手机号)
	private String idNumber;		// 证件号
	private String idAddress;		// 证件地址
	private String idType;		// 证件类型
	private String bankCardNo;		// 银行卡号
	private String openingBank;		// 开户行
	
	private String beginInvestorBirthday;
	private String endInvestorBirthday;
	private String investorBirthYear; //投资人出生年份
	
	private CreditMachineAccount creditMachineAccount;
	
	
	


	public String getInvestorBirthYear() {
		return investorBirthYear;
	}

	public void setInvestorBirthYear(String investorBirthYear) {
		this.investorBirthYear = investorBirthYear;
	}

	public String getBeginInvestorBirthday() {
		return beginInvestorBirthday;
	}

	public void setBeginInvestorBirthday(String beginInvestorBirthday) {
		this.beginInvestorBirthday = beginInvestorBirthday;
	}

	public String getEndInvestorBirthday() {
		return endInvestorBirthday;
	}

	public void setEndInvestorBirthday(String endInvestorBirthday) {
		this.endInvestorBirthday = endInvestorBirthday;
	}

	public CreditMachineAccount getCreditMachineAccount() {
		return creditMachineAccount;
	}

	public void setCreditMachineAccount(CreditMachineAccount creditMachineAccount) {
		this.creditMachineAccount = creditMachineAccount;
	}

	public CreditInvestUserInfo() {
		super();
	}

	public CreditInvestUserInfo(String id){
		super(id);
	}

	@ExcelField(title="姓名", type=1, align=2, sort=1)
	@Length(min=1, max=100, message="投资人姓名长度必须介于 1 和 100 之间")
	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}
	
	@ExcelField(title="性别", type=1, align=2, sort=2, dictType="sex",defaultValue="不详")
	@Length(min=0, max=2, message="投资人性别长度必须介于 0 和 2 之间")
	public String getInvestorGender() {
		
		if(StringUtils.isNoneBlank(idNumber) && StringUtils.isNoneBlank(idType) && CreditConstant.CREDIT_ID_TYPE_SHENFENZHENG.equals(idType) ) {
			setInvestorGender(getGender(idNumber));
		}
		else{
			setInvestorGender(null);
		}
		return investorGender;
	}

	public void setInvestorGender(String investorGender) {
		this.investorGender = investorGender;
	}
	
	@ExcelField(title="生日", type=1, align=2, sort=4,defaultValue="不详",dateFormat="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInvestorBirthday() {
		
		if(StringUtils.isNoneBlank(idNumber) && StringUtils.isNoneBlank(idType) && CreditConstant.CREDIT_ID_TYPE_SHENFENZHENG.equals(idType) ) {
			setInvestorBirthday(getBirthByIdCard(idNumber));
		}
		else{
			setInvestorBirthday(null);
		}
		
		return investorBirthday;
	}

	public void setInvestorBirthday(Date investorBirthday) {
		this.investorBirthday = investorBirthday;
	}
	
	@ExcelField(title="联系方式", type=1, align=2, sort=3)
	@Length(min=1, max=50, message="联系方式(手机号)长度必须介于 1 和 50 之间")
	public String getInvestorContactInfo() {
		return investorContactInfo;
	}

	public void setInvestorContactInfo(String investorContactInfo) {
		this.investorContactInfo = investorContactInfo;
	}
	
	@Length(min=1, max=100, message="证件号长度必须介于 1 和 100 之间")
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
		
		initInfoByIdNumber();
	}
	
	public void initInfoByIdNumber(){
		if(StringUtils.isNoneBlank(idNumber) && StringUtils.isNoneBlank(idType) && CreditConstant.CREDIT_ID_TYPE_SHENFENZHENG.equals(idType) ) {
			setInvestorBirthday(getBirthByIdCard(idNumber));
			setInvestorGender(getGender(idNumber));
		}
		else{
			setInvestorBirthday(null);
			setInvestorGender(null);
		}
	}
	
	@Length(min=0, max=100, message="证件地址长度必须介于 0 和 100 之间")
	public String getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(String idAddress) {
		this.idAddress = idAddress;
	}
	
	@Length(min=1, max=11, message="证件类型长度必须介于 1 和 11 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
		initInfoByIdNumber();
	}
	
	@Length(min=1, max=50, message="银行卡号长度必须介于 1 和 50 之间")
	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	
	@Length(min=1, max=2, message="开户行长度必须介于 1 和 2 之间")
	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}
	
	/**
	 * 根据身份证号获取年龄
	 * @param IDCardNum
	 * @return
	 */
	static Integer getAge(String IDCardNum){
		Integer result = null;
		try{
			result = IdcardUtils.getAgeByIdCard(IDCardNum);
		}catch(Exception e){
			
		}
		return result;
	}
	
	/**
	 * 根据身份证号获取性别
	 * @param IDCardNum
	 * @return
	 */
	static String getGender(String IDCardNum){
		String result = "";
		try{
			String gender = IdcardUtils.getGenderByIdCard(IDCardNum);
			result = "M".equals(gender) ? ProjectConstant.SEX_MALE : ProjectConstant.SEX_FEMALE;
		}catch(Exception e){
			
		}
		return result;
	}
	
	
	/**
	 * 根据身份证号获取生日
	 * @param IDCardNum
	 * @return
	 */
	static Date getBirthByIdCard(String IDCardNum){
		Date result = null;
		try{
			result = DateUtils.parseDate(IdcardUtils.getBirthByIdCard(IDCardNum));
		}catch(Exception e){
			
		}
		return result;
	}
}