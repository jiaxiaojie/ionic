/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 银行信息Entity
 * @author wanduanrui
 * @version 2015-11-17
 */
public class BankInfo extends DataEntity<BankInfo> {
	
	private static final long serialVersionUID = 1L;
	private String bankCode;		// 银行代码
	private String bankName;		// 银行名称
	private Integer shortcutIndividual;		// 快捷单笔限额
	private Integer shortcutSingleDay;		// 快捷单日限额
	private Integer shortcutSingleMonth;		// 快捷当月限额
	private Integer beginShortcutIndividual;		// 开始 快捷单笔限额
	private Integer endShortcutIndividual;		// 结束 快捷单笔限额
	private Integer beginShortcutSingleDay;		// 开始 快捷单日限额
	private Integer endShortcutSingleDay;		// 结束 快捷单日限额
	private Integer beginShortcutSingleMonth;		// 开始 快捷当月限额
	private Integer endShortcutSingleMonth;		// 结束 快捷当月限额
	private String logo;
	private String status;		//银行卡状态（0：无效，1：正常）
	
	public BankInfo() {
		super();
	}

	public BankInfo(String id){
		super(id);
	}

	@Length(min=1, max=15, message="银行代码长度必须介于 1 和 15 之间")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Length(min=1, max=30, message="银行名称长度必须介于 1 和 30 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@NotNull(message="快捷单笔限额不能为空")
	public Integer getShortcutIndividual() {
		return shortcutIndividual;
	}

	public void setShortcutIndividual(Integer shortcutIndividual) {
		this.shortcutIndividual = shortcutIndividual;
	}
	
	@NotNull(message="快捷单日限额不能为空")
	public Integer getShortcutSingleDay() {
		return shortcutSingleDay;
	}

	public void setShortcutSingleDay(Integer shortcutSingleDay) {
		this.shortcutSingleDay = shortcutSingleDay;
	}
	
	@NotNull(message="快捷当月限额不能为空")
	public Integer getShortcutSingleMonth() {
		return shortcutSingleMonth;
	}

	public void setShortcutSingleMonth(Integer shortcutSingleMonth) {
		this.shortcutSingleMonth = shortcutSingleMonth;
	}
	
	public Integer getBeginShortcutIndividual() {
		return beginShortcutIndividual;
	}

	public void setBeginShortcutIndividual(Integer beginShortcutIndividual) {
		this.beginShortcutIndividual = beginShortcutIndividual;
	}
	
	public Integer getEndShortcutIndividual() {
		return endShortcutIndividual;
	}

	public void setEndShortcutIndividual(Integer endShortcutIndividual) {
		this.endShortcutIndividual = endShortcutIndividual;
	}
		
	public Integer getBeginShortcutSingleDay() {
		return beginShortcutSingleDay;
	}

	public void setBeginShortcutSingleDay(Integer beginShortcutSingleDay) {
		this.beginShortcutSingleDay = beginShortcutSingleDay;
	}
	
	public Integer getEndShortcutSingleDay() {
		return endShortcutSingleDay;
	}

	public void setEndShortcutSingleDay(Integer endShortcutSingleDay) {
		this.endShortcutSingleDay = endShortcutSingleDay;
	}
		
	public Integer getBeginShortcutSingleMonth() {
		return beginShortcutSingleMonth;
	}

	public void setBeginShortcutSingleMonth(Integer beginShortcutSingleMonth) {
		this.beginShortcutSingleMonth = beginShortcutSingleMonth;
	}
	
	public Integer getEndShortcutSingleMonth() {
		return endShortcutSingleMonth;
	}

	public void setEndShortcutSingleMonth(Integer endShortcutSingleMonth) {
		this.endShortcutSingleMonth = endShortcutSingleMonth;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
		
}