/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 会员银行卡历史变更Entity
 * @author ydt
 * @version 2015-06-26
 */
public class CustomerBankCardHis extends DataEntity<CustomerBankCardHis> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private String code;	//易宝返回码
	private String cardNo;	//绑定的卡号
	private String cardStatusCode;	//银行卡绑定状态
	private String bankCode;	//银行卡所属银行
	private String message;	//描述信息
	private String requestNo;	//请求流水号
	private Date opDt;	//银行卡填写时间
	private String serviceCode;	//易宝服务类型
	private String opTermCode;		// 操作终端
	private String creditReportFile;	//信用报告
	
	private Date changeDt;
	private Date beginChangeDt;
	private Date endChangeDt;
	
	
	public Date getBeginChangeDt() {
		return beginChangeDt;
	}

	public void setBeginChangeDt(Date beginChangeDt) {
		this.beginChangeDt = beginChangeDt;
	}

	public Date getEndChangeDt() {
		return endChangeDt;
	}

	public void setEndChangeDt(Date endChangeDt) {
		this.endChangeDt = endChangeDt;
	}

	public Date getChangeDt() {
		return changeDt;
	}

	public void setChangeDt(Date changeDt) {
		this.changeDt = changeDt;
	}
	private String accountName;	//登录名
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public CustomerBankCardHis() {
		super();
	}

	public CustomerBankCardHis(String id){
		super(id);
	}

	@NotNull(message="账号编号不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=2, message="操作终端长度必须介于 0 和 2 之间")
	public String getOpTermCode() {
		return opTermCode;
	}

	public void setOpTermCode(String opTermCode) {
		this.opTermCode = opTermCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardStatusCode() {
		return cardStatusCode;
	}

	public void setCardStatusCode(String cardStatusCode) {
		this.cardStatusCode = cardStatusCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

    @Length(min=0, max=500, message="信用报告长度必须介于 0 和 500 之间")
	public String getCreditReportFile() {
		return creditReportFile;
	}

	public void setCreditReportFile(String creditReportFile) {
		this.creditReportFile = creditReportFile;
	}
	/**
	 * 对应该有默认值而为空的属性设置默认值
	 */
	public void setDefaultValue() {
		if(StringUtils.isBlank(opTermCode)) {
			opTermCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
	}
	
}