/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 异常交易记录表Entity
 * @author lzb
 * @version 2016-03-03
 */
public class ProjectTransErrorRecord extends DataEntity<ProjectTransErrorRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private String bizType;		// 业务类型
	private String thirdPartySeq;		// 第三方交易号
	private String thirdPartyResult;		// 返回报文
	private String status;		// 状态
	private Date createDt;		// 创建时间
	private Date modifyDt;		// 修改时间
	private Date beginCreateDt;		// 开始 创建时间
	private Date endCreateDt;		// 结束 创建时间
	
	private CustomerAccount ca;
	
	public ProjectTransErrorRecord() {
		super();
	}

	public ProjectTransErrorRecord(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=20, message="业务类型长度必须介于 0 和 20 之间")
	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
	@Length(min=0, max=100, message="第三方交易号长度必须介于 0 和 100 之间")
	public String getThirdPartySeq() {
		return thirdPartySeq;
	}

	public void setThirdPartySeq(String thirdPartySeq) {
		this.thirdPartySeq = thirdPartySeq;
	}
	
	@Length(min=0, max=1500, message="返回报文长度必须介于 0 和 1500 之间")
	public String getThirdPartyResult() {
		return thirdPartyResult;
	}

	public void setThirdPartyResult(String thirdPartyResult) {
		this.thirdPartyResult = thirdPartyResult;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}
	
	public Date getBeginCreateDt() {
		return beginCreateDt;
	}

	public void setBeginCreateDt(Date beginCreateDt) {
		this.beginCreateDt = beginCreateDt;
	}
	
	public Date getEndCreateDt() {
		return endCreateDt;
	}

	public void setEndCreateDt(Date endCreateDt) {
		this.endCreateDt = endCreateDt;
	}

	public CustomerAccount getCa() {
		return ca;
	}

	public void setCa(CustomerAccount ca) {
		this.ca = ca;
	}
		
}