/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 运营数据Entity
 * @author huangyuchen
 * @version 2015-12-01
 */
public class OperationData extends DataEntity<OperationData> {
	
	private static final long serialVersionUID = 1L;
	private Date date;		// 日期
	private String registerCount;		// 注册人数
	private String openThirdAccountCount;		// 开通第三方账号人数
	private String bindBankCardCount;		// 绑卡用户数
	private String rechargeAmount;		// 充值额度
	private Date   beginDt;             //数据开始时间
	private Date   endDt;               //数据结束时间
	private String withdrawAmount;		// 提现额度
	private String investmentTimes;		// 投资次数
	private String investmentAmount;		// 投资额度
	private String investmentFistNumber;
	private String  investmentFistTotalAmount;
	private String  repeatedInvestmentNumber;
	private String  repeatedInvestmentTotalAmount;

	public void setRepeatedInvestmentNumber(String repeatedInvestmentNumber) {
		this.repeatedInvestmentNumber = repeatedInvestmentNumber;
	}

	public void setRepeatedInvestmentTotalAmount(String repeatedInvestmentTotalAmount) {
		this.repeatedInvestmentTotalAmount = repeatedInvestmentTotalAmount;
	}
	

	public void setInvestmentFistTotalAmount(String investmentFistTotalAmount) {
		this.investmentFistTotalAmount = investmentFistTotalAmount;
	}

	public OperationData() {
		super();
	}

	public OperationData(String id){
		super(id);
	}

	public void setInvestmentFistNumber(String investmentFistNumber) {
		this.investmentFistNumber = investmentFistNumber;
	}

	@ExcelField(title="日期", align=2, sort=5)
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@ExcelField(title="注册人数", align=2, sort=10)
	@Length(min=0, max=11, message="注册人数长度必须介于 0 和 11 之间")
	public String getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(String registerCount) {
		this.registerCount = registerCount;
	}
	
	@ExcelField(title="开通第三方账号人数", align=2, sort=15)
	@Length(min=0, max=11, message="开通第三方账号人数长度必须介于 0 和 11 之间")
	public String getOpenThirdAccountCount() {
		return openThirdAccountCount;
	}

	public void setOpenThirdAccountCount(String openThirdAccountCount) {
		this.openThirdAccountCount = openThirdAccountCount;
	}
	
	public Date getBeginDt() {
		return beginDt;
	}

	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	
	
	@ExcelField(title="绑卡用户数", align=2, sort=20)
	@Length(min=0, max=11, message="绑卡用户数长度必须介于 0 和 11 之间")
	public String getBindBankCardCount() {
		return bindBankCardCount;
	}

	public void setBindBankCardCount(String bindBankCardCount) {
		this.bindBankCardCount = bindBankCardCount;
	}
	
	@ExcelField(title="充值额度", align=2, sort=30)
	public String getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	
	@ExcelField(title="提现额度", align=2, sort=35)
	public String getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(String withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	
	@ExcelField(title="首次投资人数", align=2, sort=40)
	public String getInvestmentFistNumber() {
		return investmentFistNumber;
	}
	@ExcelField(title="首次投资总额度", align=2, sort=45)
	public String getInvestmentFistTotalAmount() {
		return investmentFistTotalAmount;
	}
	
	@ExcelField(title="复投用户人数", align=2, sort=50)
	public String getRepeatedInvestmentNumber() {
		return repeatedInvestmentNumber;
	}
	@ExcelField(title="复投用户投资总额度", align=2, sort=55)
	public String getRepeatedInvestmentTotalAmount() {
		return repeatedInvestmentTotalAmount;
	}
	
	@ExcelField(title="投资次数", align=2, sort=60)
	@Length(min=0, max=50, message="投资次数长度必须介于 0 和 50 之间")
	public String getInvestmentTimes() {
		return investmentTimes;
	}

	public void setInvestmentTimes(String investmentTimes) {
		this.investmentTimes = investmentTimes;
	}
	
	@ExcelField(title="投资额度", align=2, sort=65)
	public String getInvestmentAmount() {
		return investmentAmount;
	}

	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	
}