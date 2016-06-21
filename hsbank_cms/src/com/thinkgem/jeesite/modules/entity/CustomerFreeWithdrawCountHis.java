/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员可免费提现次数变更流水Entity
 * @author ydt
 * @version 2015-08-15
 */
public class CustomerFreeWithdrawCountHis extends DataEntity<CustomerFreeWithdrawCountHis> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Integer changeVal;		// 变更值
	private String changeTypeCode;		// 变更类型
	private Date getDt;		// 获取时间
	private String requestNo;		// 提现请求流水号
	private Date useDt;		// 使用时间
	private Date beginGetDt;		// 开始 获取时间
	private Date endGetDt;		// 结束 获取时间
	private Date beginUseDt;		// 开始 使用时间
	private Date endUseDt;		// 结束 使用时间
	
	private Date changeDt;		//变更时间
	
	public CustomerFreeWithdrawCountHis() {
		super();
	}

	public CustomerFreeWithdrawCountHis(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Integer getChangeVal() {
		return changeVal;
	}

	public void setChangeVal(Integer changeVal) {
		this.changeVal = changeVal;
	}
	
	@Length(min=0, max=2, message="变更类型长度必须介于 0 和 2 之间")
	public String getChangeTypeCode() {
		return changeTypeCode;
	}

	public void setChangeTypeCode(String changeTypeCode) {
		this.changeTypeCode = changeTypeCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGetDt() {
		return getDt;
	}

	public void setGetDt(Date getDt) {
		this.getDt = getDt;
	}
	
	@Length(min=0, max=50, message="提现请求流水号长度必须介于 0 和 50 之间")
	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUseDt() {
		return useDt;
	}

	public void setUseDt(Date useDt) {
		this.useDt = useDt;
	}
	
	public Date getBeginGetDt() {
		return beginGetDt;
	}

	public void setBeginGetDt(Date beginGetDt) {
		this.beginGetDt = beginGetDt;
	}
	
	public Date getEndGetDt() {
		return endGetDt;
	}

	public void setEndGetDt(Date endGetDt) {
		this.endGetDt = endGetDt;
	}
		
	public Date getBeginUseDt() {
		return beginUseDt;
	}

	public void setBeginUseDt(Date beginUseDt) {
		this.beginUseDt = beginUseDt;
	}
	
	public Date getEndUseDt() {
		return endUseDt;
	}

	public void setEndUseDt(Date endUseDt) {
		this.endUseDt = endUseDt;
	}

	public Date getChangeDt() {
		return changeDt;
	}

	public void setChangeDt(Date changeDt) {
		this.changeDt = changeDt;
	}
		
}