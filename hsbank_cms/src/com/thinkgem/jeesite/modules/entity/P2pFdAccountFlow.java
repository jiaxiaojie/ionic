/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 平台账号流水Entity
 * @author yangtao
 * @version 2015-08-12
 */
public class P2pFdAccountFlow extends DataEntity<P2pFdAccountFlow> {
	
	private static final long serialVersionUID = 1L;
	private String changeVal;		// 变化值
	private Long projectId;		// 项目编号
	private Long transferProjectId;		// 转让项目编号
	private Long investmentId;		// 投资编号
	private Long accountId;		// 关联会员编号
	private String detail;		// 详情
	private Date createDt;		// 变化时间
	private String thirdPartySeq;		// 第三方交易号
	private String thirdPartyResult;		// 交易返回报文
	private String result;		// 交易结果
	private String notifyContent;//通知报文
	private String notifyCode;//通知code
	

	private String beginChangeVal;		// 开始 变化值
	private String endChangeVal;		// 结束 变化值
	
	public P2pFdAccountFlow() {
		super();
	}

	public P2pFdAccountFlow(String id){
		super(id);
	}

	public String getChangeVal() {
		return changeVal;
	}

	public void setChangeVal(String changeVal) {
		this.changeVal = changeVal;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Long getTransferProjectId() {
		return transferProjectId;
	}

	public void setTransferProjectId(Long transferProjectId) {
		this.transferProjectId = transferProjectId;
	}
	
	public Long getInvestmentId() {
		return investmentId;
	}

	public void setInvestmentId(Long investmentId) {
		this.investmentId = investmentId;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=500, message="详情长度必须介于 0 和 500 之间")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	@Length(min=0, max=50, message="第三方交易号长度必须介于 0 和 50 之间")
	public String getThirdPartySeq() {
		return thirdPartySeq;
	}

	public void setThirdPartySeq(String thirdPartySeq) {
		this.thirdPartySeq = thirdPartySeq;
	}
	
	@Length(min=0, max=500, message="交易返回报文长度必须介于 0 和 500 之间")
	public String getThirdPartyResult() {
		return thirdPartyResult;
	}

	public void setThirdPartyResult(String thirdPartyResult) {
		this.thirdPartyResult = thirdPartyResult;
	}
	
	@Length(min=0, max=100, message="交易结果长度必须介于 0 和 100 之间")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getBeginChangeVal() {
		return beginChangeVal;
	}

	public void setBeginChangeVal(String beginChangeVal) {
		this.beginChangeVal = beginChangeVal;
	}
	
	public String getEndChangeVal() {
		return endChangeVal;
	}

	public void setEndChangeVal(String endChangeVal) {
		this.endChangeVal = endChangeVal;
	}
	
	public String getNotifyContent() {
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	public String getNotifyCode() {
		return notifyCode;
	}

	public void setNotifyCode(String notifyCode) {
		this.notifyCode = notifyCode;
	}
		
}