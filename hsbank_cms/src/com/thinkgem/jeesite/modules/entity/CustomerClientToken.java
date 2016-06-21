/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 客户端缓存信息Entity
 * @author lzb
 * @version 2015-10-14
 */
public class CustomerClientToken extends DataEntity<CustomerClientToken> {
	
	private static final long serialVersionUID = 1L;
	private Long customerId;		// 会员编号
	private String token;		// 令牌
	private String termType;    //终端类型
	private Date lastDt;		// 最后一次更改时间
	
	public CustomerClientToken() {
		super();
	}

	public CustomerClientToken(String id){
		super(id);
	}

	@NotNull(message="会员编号不能为空")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=500, message="令牌长度必须介于 0 和 500 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastDt() {
		return lastDt;
	}

	public void setLastDt(Date lastDt) {
		this.lastDt = lastDt;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}
	
	public Long getAccountId(){
		return getCustomerId();
	}
	
}