/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.IdcardUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 会员基本信息Entity
 * @author ydt
 * @version 2015-06-23
 */
@XmlRootElement(name="customerBase")
public class CustomerBase extends DataEntity<CustomerBase> {
	
	private static final long serialVersionUID = 1L;
	private Long customerId;		// 会员编号
	private Long accountId;			// 会员账号编号
	private String customerName;		// 会员名称
	private String genderCode;		//性别
	private String nationalityCode;		// 国籍
	private String certNum;			// 身份证号码
	private String certFile;			// 身份证照片
	private String nameAuthCode;		// 是否实名认证
	private Integer age;		// 年龄
	private String mobile;			// 手机号
	private String mobileAuthCode;		// 是否手机认证
	private String email;			// 电子邮箱
	private String emailAuthCode;		// 是否邮箱认证
	private String educationCode;		// 最高学历
	private String educationSchool;	//毕业院校
	private String educationFile;		//学历附件
	private String marriageCode;		// 婚姻状态
	private String address;			// 居住地址
	private String familyRegister;	//户籍
	private String familyRegisterFile;	//户籍附件
	private String creditCardBankCode;	//信用卡银行
	private String creditCardNo;	//信用卡卡号
	private Integer creditCardLimit;	//信用卡额度
	private String creditCardAuthCode;	//信用卡认证
	private Date createDt;		// 创建时间
	private Date lastModifyDt;		// 最后一次修改时间
	
	private String accountType;	//账号类型
	
	private CustomerOrgExtendInfo customerOrgExtendInfo;
	
	public CustomerOrgExtendInfo getCustomerOrgExtendInfo() {
		return customerOrgExtendInfo;
	}

	public void setCustomerOrgExtendInfo(CustomerOrgExtendInfo customerOrgExtendInfo) {
		this.customerOrgExtendInfo = customerOrgExtendInfo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public CustomerBase() {
		super();
	}

	public CustomerBase(String customerId){
		super(customerId);
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=20, message="会员名称长度必须介于 0 和 20 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=1, message="是否实名认证长度必须介于 0 和 1 之间")
	public String getNameAuthCode() {
		return nameAuthCode;
	}

	public void setNameAuthCode(String nameAuthCode) {
		this.nameAuthCode = nameAuthCode;
	}
	
	@Length(min=0, max=20, message="国籍长度必须介于 0 和 20 之间")
	public String getNationalityCode() {
		return nationalityCode;
	}

	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}
	
	@Length(min=0, max=20, message="证件号码长度必须介于 0 和 20 之间")
	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
		if(StringUtils.isNoneBlank(certNum)) {
			setAge(getAge(certNum));
			setGenderCode(getGender(certNum));
		}
	}

	@Length(min=0, max=500, message="身份证照片长度必须介于 0 和 500 之间")
	public String getCertFile() {
		return certFile;
	}

	public void setCertFile(String certFile) {
		this.certFile = certFile;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Length(min=0, max=2, message="性别长度必须介于 0 和 2 之间")
	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}
	
	@Length(min=0, max=2, message="婚姻状态长度必须介于 0 和 2 之间")
	public String getMarriageCode() {
		return marriageCode;
	}

	public void setMarriageCode(String marriageCode) {
		this.marriageCode = marriageCode;
	}
	
	@Length(min=0, max=2, message="最高学历长度必须介于 0 和 2 之间")
	public String getEducationCode() {
		return educationCode;
	}

	public void setEducationCode(String educationCode) {
		this.educationCode = educationCode;
	}
	
	@ExcelField(title="手机号", align=1, sort=20,type=2)
	@Length(min=0, max=11, message="手机号长度必须介于 0 和 11之间")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=1, message="是否手机认证长度必须介于 0 和 1 之间")
	public String getMobileAuthCode() {
		return mobileAuthCode;
	}

	public void setMobileAuthCode(String mobileAuthCode) {
		this.mobileAuthCode = mobileAuthCode;
	}
	
	@Length(min=0, max=200, message="居住地址长度必须介于 0 和 200 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=100, message="电子邮箱长度必须介于 0 和 100 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=1, message="是否邮箱认证长度必须介于 0 和 1 之间")
	public String getEmailAuthCode() {
		return emailAuthCode;
	}

	public void setEmailAuthCode(String emailAuthCode) {
		this.emailAuthCode = emailAuthCode;
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

	@Length(min=0, max=2, message="信用卡银行长度必须介于 0 和 2 之间")
	public String getCreditCardBankCode() {
		return creditCardBankCode;
	}

	public void setCreditCardBankCode(String creditCardBankCode) {
		this.creditCardBankCode = creditCardBankCode;
	}

	@Length(min=0, max=20, message="信用卡卡号长度必须介于 0 和 20 之间")
	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	@Length(min=0, max=1, message="是否信用卡认证长度必须介于 0 和 1 之间")
	public String getCreditCardAuthCode() {
		return creditCardAuthCode;
	}

	public void setCreditCardAuthCode(String creditCardAuthCode) {
		this.creditCardAuthCode = creditCardAuthCode;
	}

	@Length(min=0, max=30, message="毕业院校长度必须介于 0 和 30 之间")
	public String getEducationSchool() {
		return educationSchool;
	}

	public void setEducationSchool(String educationSchool) {
		this.educationSchool = educationSchool;
	}

	@Length(min=0, max=500, message="学历附件长度必须介于 0 和 500 之间")
	public String getEducationFile() {
		return educationFile;
	}

	public void setEducationFile(String educationFile) {
		this.educationFile = educationFile;
	}

	@Length(min=0, max=20, message="户籍长度必须介于 0 和 20 之间")
	public String getFamilyRegister() {
		return familyRegister;
	}

	public void setFamilyRegister(String familyRegister) {
		this.familyRegister = familyRegister;
	}

	@Length(min=0, max=500, message="户籍附件长度必须介于 0 和 500 之间")
	public String getFamilyRegisterFile() {
		return familyRegisterFile;
	}

	public void setFamilyRegisterFile(String familyRegisterFile) {
		this.familyRegisterFile = familyRegisterFile;
	}

	public Integer getCreditCardLimit() {
		return creditCardLimit;
	}

	public void setCreditCardLimit(Integer creditCardLimit) {
		this.creditCardLimit = creditCardLimit;
	}

	/**
	 * 对应该有默认值而为空的属性设置默认值
	 */
	public void setDefaultValue() {
		if(StringUtils.isBlank(nameAuthCode)) {
			nameAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(mobileAuthCode)) {
			mobileAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(emailAuthCode)) {
			emailAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(creditCardAuthCode)) {
			creditCardAuthCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
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
	
	public String getBirthday() {
		
		return IdcardUtils.getBirthByIdCard(certNum);
	}
	
	/**
	 * 年龄得分
	 * 女30岁以上 5分 男30岁以上 4.5分 女30岁以下 3分 男30岁以下 2.5分
	 * sex 1:男 2:女
	 * @return
	 */
	public Double getAgeCreditAuthScore() {
		return getAgeCreditAuthScore(this);
	}
	public Double getAgeCreditAuthScore(CustomerBase customerBase) {
		if(ProjectConstant.SEX_MALE.equals(customerBase.getGenderCode())) {
			if(customerBase.getAge() >= 30 && customerBase.getAge() < 200) {
				return 3D;
			}else if(customerBase.getAge() > 0 && customerBase.getAge() < 30) {
				return 2.5D;
			}
		}
		if(ProjectConstant.SEX_FEMALE.equals(customerBase.getGenderCode())) {
			if(customerBase.getAge() >= 30 && customerBase.getAge() < 200) {
				return 5D;
			}else if(customerBase.getAge() > 0 && customerBase.getAge() < 30) {
				return 3D;
			}
		}
		return 0D;
	}
	/**
	 * 模糊化用户信息
	 * 姓名
	 * 手机号码
	 * 出生日期
	 * 邮箱
	 * 地址
	 * 信用卡卡号
	 */
	public CustomerBase vague(){
		this.customerName=StringUtils.vagueName(this.customerName);		// 会员名称
		this.mobile=StringUtils.vagueMobile(this.mobile);			// 手机号
		this.email=StringUtils.vagueEmail(this.email);			// 电子邮箱
		this.educationSchool=StringUtils.vagueEducationSchool(this.educationSchool);	//毕业院校
		this.address=StringUtils.vagueAddress(this.address);			// 居住地址
		this.familyRegister=StringUtils.vagueFamilyRegister(this.familyRegister);	//户籍
		this.creditCardNo=StringUtils.vagueCreditCardNo(this.creditCardNo);	//信用卡卡号
		return this;
	}
	/**
	 * 身份认证得分
	 * 此项暂无加分
	 * @return
	 */
	public Double getIdentityScore() {
		return "1".equals(nameAuthCode) ? 0D : 0D ;
	}
	
	/**
	 * 学历认证得分
	 * 初中及以下 1分 高中 2分 中专 4分 大学及以上 5分
	 * 0	初中;1	高中;2	专科;3	本科;4	硕士
	 * @return
	 */
	public Double getEducationScore() {
		if("1".equals(educationCode)) {
			return 2D;
		}else if("2".equals(educationCode)) {
			return 4D;
		}else if("3".equals(educationCode) || "4".equals(educationCode)) {
			return 5D;
		}
		return 1D;
	}

	/**
	 * 家庭情况得分
	 * 未婚 2分 已婚无子女 3分 已婚有子女 4分  加上  一线城市 5分 其它 2分
	 * 0	未婚；1	已婚无子女；2	已婚有子女
	 * @return
	 */
	public Double getAddressScore() {
		Double addressScore = 0D;
		if("0".equals(marriageCode)) {
			addressScore += 2;
		}else if("1".equals(marriageCode)) {
			addressScore += 3;
		}else if("2".equals(marriageCode)) {
			addressScore += 4;
		}
		//暂未判断是否是一线城市，加2分
		addressScore += 2;
		return addressScore;
	}
}