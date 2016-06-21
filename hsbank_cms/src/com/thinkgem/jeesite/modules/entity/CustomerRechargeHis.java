/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员充值记录Entity
 * @author yangtao
 * @version 2015-07-23
 */
public class CustomerRechargeHis extends DataEntity<CustomerRechargeHis> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Date requestDt;		// 充值发起时间
	private String money;		// 充值金额
	private String rechargeType;		// 充值方式
	private String remark;		// 充值备注
	private Date responseDt;		// 充值响应时间
	private String responseCode;		// 充值响应状态
	private String thirdPartyReq;		// 充值流水号
	private Date queryDt;		// 充值状态查询时间
	private String queryResult;		// 充值状态查询响应状态
	private String status;		// 充值状态
	private Date beginRequestDt;		// 开始 充值发起时间
	private Date endRequestDt;		// 结束 充值发起时间
	
	public CustomerRechargeHis() {
		super();
	}

	public CustomerRechargeHis(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRequestDt() {
		return requestDt;
	}

	public void setRequestDt(Date requestDt) {
		this.requestDt = requestDt;
	}
	
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@Length(min=0, max=2, message="充值方式长度必须介于 0 和 2 之间")
	public String getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}
	
	@Length(min=0, max=200, message="充值备注长度必须介于 0 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getResponseDt() {
		return responseDt;
	}

	public void setResponseDt(Date responseDt) {
		this.responseDt = responseDt;
	}
	
	@Length(min=0, max=20, message="充值响应状态长度必须介于 0 和 20 之间")
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	@Length(min=0, max=50, message="充值流水号长度必须介于 0 和 50 之间")
	public String getThirdPartyReq() {
		return thirdPartyReq;
	}

	public void setThirdPartyReq(String thirdPartyReq) {
		this.thirdPartyReq = thirdPartyReq;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getQueryDt() {
		return queryDt;
	}

	public void setQueryDt(Date queryDt) {
		this.queryDt = queryDt;
	}
	
	@Length(min=0, max=20, message="充值状态查询响应状态长度必须介于 0 和 20 之间")
	public String getQueryResult() {
		return queryResult;
	}

	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}
	
	@Length(min=0, max=2, message="充值状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginRequestDt() {
		return beginRequestDt;
	}

	public void setBeginRequestDt(Date beginRequestDt) {
		this.beginRequestDt = beginRequestDt;
	}
	
	public Date getEndRequestDt() {
		return endRequestDt;
	}

	public void setEndRequestDt(Date endRequestDt) {
		this.endRequestDt = endRequestDt;
	}
		
}