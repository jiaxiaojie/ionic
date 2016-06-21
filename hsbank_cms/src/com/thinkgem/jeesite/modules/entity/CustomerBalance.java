/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员账户余额汇总Entity
 * @author ydt
 * @version 2015-06-25
 */
public class CustomerBalance extends DataEntity<CustomerBalance> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Double goldBalance;		// 当前账户余额值
	private Double platformAmount;	//可抵用投资金额
	private Double congealVal;		// 冻结余额值
	private Double willLoan;		// 待还借款
	private Double sumLoan;		// 累计融资
	private Double repaymentMoney;		// 还款总额
	private Double repaymentPrincipal;		// 还款本息
	private Double repaymentLateMoney;		// 还款逾期罚款
	private Double repaymentPreMoney;		// 还款提前还款
	private Double repayment30dWill;		// 30天内应还款项
	private Double willProfit;		// 待收收益
	private Double willPrincipal;	//待收本金
	private Double sumProfit;		// 累计收益
	private Double sumInvestment;		// 累计投资
	private Double receiveMoney;		// 收款总额
	private Double receivePrincipal;		// 收款本息
	private Double receiveLateMoney;		// 收款逾期罚款
	private Double receivePreMoney;		// 收款提前还款
	private Double receiveTransferMoney;		// 收款转让金
	private Double netAssets;		// 账户净资产
	private Double financialAssets;		// 理财资产
	private Double sumRecharge;		// 已充值总额
	private Double sumWithdraw;		// 已提现总额
	private Integer rechargeCount;		// 充值次数
	private Integer withdrawCount;		// 提现次数
	private Integer investmentCount;		// 投资次数
	private Integer cancelCount;		// 撤消次数
	private Integer transferCount;		// 转让次数
	private Integer acceptCount;		// 受转让次数
	private Integer freeWithdrawCount;	//免费提现次数
	private Date firstGetDt;		// 第一笔充值时间
	private Date lastChangeDt;		// 最后一笔变更时间
	private Double beginGoldBalance;		// 开始 当前账户余额值
	private Double endGoldBalance;		// 结束 当前账户余额值
	private String accountName;
	private String customerName;
	private String avatarImage;		// 头像
	private String hasOpenThirdAccount;		// 是否开通第三方账号（0：未开通；1：已开通）
	private CustomerIntegralSnapshot customerIntegralSnapshot;
	private CustomerGoldCoinSnapshot customerGoldCoinSnapshot;
	
	public CustomerBalance() {
		super();
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public CustomerIntegralSnapshot getCustomerIntegralSnapshot() {
		return customerIntegralSnapshot;
	}

	public void setCustomerIntegralSnapshot(
			CustomerIntegralSnapshot customerIntegralSnapshot) {
		this.customerIntegralSnapshot = customerIntegralSnapshot;
	}

	public CustomerGoldCoinSnapshot getCustomerGoldCoinSnapshot() {
		return customerGoldCoinSnapshot;
	}

	public void setCustomerGoldCoinSnapshot(
			CustomerGoldCoinSnapshot customerGoldCoinSnapshot) {
		this.customerGoldCoinSnapshot = customerGoldCoinSnapshot;
	}

	public CustomerBalance(String id){
		super(id);
	}

	@NotNull(message="账号编号不能为空")
	public Long getAccountId() {
		return accountId;
	}


	public Double getGoldBalance() {
		return goldBalance;
	}


	public void setGoldBalance(Double goldBalance) {
		this.goldBalance = goldBalance;
	}


	public Double getCongealVal() {
		return congealVal;
	}


	public void setCongealVal(Double congealVal) {
		this.congealVal = congealVal;
	}


	public Double getWillLoan() {
		return willLoan;
	}


	public void setWillLoan(Double willLoan) {
		this.willLoan = willLoan;
	}


	public Double getSumLoan() {
		return sumLoan;
	}


	public void setSumLoan(Double sumLoan) {
		this.sumLoan = sumLoan;
	}


	public Double getRepaymentMoney() {
		return repaymentMoney;
	}


	public void setRepaymentMoney(Double repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}


	public Double getRepaymentPrincipal() {
		return repaymentPrincipal;
	}


	public void setRepaymentPrincipal(Double repaymentPrincipal) {
		this.repaymentPrincipal = repaymentPrincipal;
	}


	public Double getRepaymentLateMoney() {
		return repaymentLateMoney;
	}


	public void setRepaymentLateMoney(Double repaymentLateMoney) {
		this.repaymentLateMoney = repaymentLateMoney;
	}


	public Double getRepaymentPreMoney() {
		return repaymentPreMoney;
	}


	public void setRepaymentPreMoney(Double repaymentPreMoney) {
		this.repaymentPreMoney = repaymentPreMoney;
	}


	public Double getRepayment30dWill() {
		return repayment30dWill;
	}


	public void setRepayment30dWill(Double repayment30dWill) {
		this.repayment30dWill = repayment30dWill;
	}


	public Double getWillProfit() {
		return willProfit;
	}


	public void setWillProfit(Double willProfit) {
		this.willProfit = willProfit;
	}


	public Double getSumProfit() {
		return sumProfit;
	}


	public void setSumProfit(Double sumProfit) {
		this.sumProfit = sumProfit;
	}


	public Double getSumInvestment() {
		return sumInvestment;
	}


	public void setSumInvestment(Double sumInvestment) {
		this.sumInvestment = sumInvestment;
	}


	public Double getReceiveMoney() {
		return receiveMoney;
	}


	public void setReceiveMoney(Double receiveMoney) {
		this.receiveMoney = receiveMoney;
	}


	public Double getReceivePrincipal() {
		return receivePrincipal;
	}


	public void setReceivePrincipal(Double receivePrincipal) {
		this.receivePrincipal = receivePrincipal;
	}


	public Double getReceiveLateMoney() {
		return receiveLateMoney;
	}


	public void setReceiveLateMoney(Double receiveLateMoney) {
		this.receiveLateMoney = receiveLateMoney;
	}


	public Double getReceivePreMoney() {
		return receivePreMoney;
	}


	public void setReceivePreMoney(Double receivePreMoney) {
		this.receivePreMoney = receivePreMoney;
	}


	public Double getReceiveTransferMoney() {
		return receiveTransferMoney;
	}


	public void setReceiveTransferMoney(Double receiveTransferMoney) {
		this.receiveTransferMoney = receiveTransferMoney;
	}


	public Double getNetAssets() {
		return netAssets;
	}


	public void setNetAssets(Double netAssets) {
		this.netAssets = netAssets;
	}


	public Double getFinancialAssets() {
		return financialAssets;
	}


	public void setFinancialAssets(Double financialAssets) {
		this.financialAssets = financialAssets;
	}


	public Double getSumRecharge() {
		return sumRecharge;
	}


	public void setSumRecharge(Double sumRecharge) {
		this.sumRecharge = sumRecharge;
	}


	public Double getSumWithdraw() {
		return sumWithdraw;
	}


	public void setSumWithdraw(Double sumWithdraw) {
		this.sumWithdraw = sumWithdraw;
	}


	public Integer getRechargeCount() {
		return rechargeCount;
	}


	public void setRechargeCount(Integer rechargeCount) {
		this.rechargeCount = rechargeCount;
	}


	public Integer getWithdrawCount() {
		return withdrawCount;
	}


	public void setWithdrawCount(Integer withdrawCount) {
		this.withdrawCount = withdrawCount;
	}


	public Integer getInvestmentCount() {
		return investmentCount;
	}


	public void setInvestmentCount(Integer investmentCount) {
		this.investmentCount = investmentCount;
	}


	public Integer getCancelCount() {
		return cancelCount;
	}


	public void setCancelCount(Integer cancelCount) {
		this.cancelCount = cancelCount;
	}


	public Integer getTransferCount() {
		return transferCount;
	}


	public void setTransferCount(Integer transferCount) {
		this.transferCount = transferCount;
	}


	public Integer getAcceptCount() {
		return acceptCount;
	}


	public void setAcceptCount(Integer acceptCount) {
		this.acceptCount = acceptCount;
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFirstGetDt() {
		return firstGetDt;
	}


	public void setFirstGetDt(Date firstGetDt) {
		this.firstGetDt = firstGetDt;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastChangeDt() {
		return lastChangeDt;
	}


	public void setLastChangeDt(Date lastChangeDt) {
		this.lastChangeDt = lastChangeDt;
	}


	public Double getBeginGoldBalance() {
		return beginGoldBalance;
	}


	public void setBeginGoldBalance(Double beginGoldBalance) {
		this.beginGoldBalance = beginGoldBalance;
	}


	public Double getEndGoldBalance() {
		return endGoldBalance;
	}


	public void setEndGoldBalance(Double endGoldBalance) {
		this.endGoldBalance = endGoldBalance;
	}


	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}


	public Double getPlatformAmount() {
		return platformAmount;
	}


	public void setPlatformAmount(Double platformAmount) {
		this.platformAmount = platformAmount;
	}

	public Integer getFreeWithdrawCount() {
		return freeWithdrawCount;
	}


	public void setFreeWithdrawCount(Integer freeWithdrawCount) {
		this.freeWithdrawCount = freeWithdrawCount;
	}

	/**
	 * 设置默认值
	 */
	public void setDefaultValue() {
		if(goldBalance == null) {
			goldBalance = 0D;
		}
		if(platformAmount == null) {
			platformAmount = 0D;
		}
		if(congealVal == null) {
			congealVal = 0D;
		}
		if(willLoan == null) {
			willLoan = 0D;
		}
		if(sumLoan == null) {
			sumLoan = 0D;
		}
		if(repaymentMoney == null) {
			repaymentMoney = 0D;
		}
		if(repaymentPrincipal == null) {
			repaymentPrincipal = 0D;
		}
		if(repaymentLateMoney == null) {
			repaymentLateMoney = 0D;
		}
		if(repaymentPreMoney == null) {
			repaymentPreMoney = 0D;
		}
		if(repayment30dWill == null) {
			repayment30dWill = 0D;
		}
		if(willProfit == null) {
			willProfit = 0D;
		}
		if(willPrincipal == null) {
			willPrincipal = 0D;
		}
		if(sumProfit == null) {
			sumProfit = 0D;
		}
		if(sumInvestment == null) {
			sumInvestment = 0D;
		}
		if(receiveMoney == null) {
			receiveMoney = 0D;
		}
		if(receivePrincipal == null) {
			receivePrincipal = 0D;
		}
		if(receiveLateMoney == null) {
			receiveLateMoney = 0D;
		}
		if(receivePreMoney == null) {
			receivePreMoney = 0D;
		}
		if(receiveTransferMoney == null) {
			receiveTransferMoney = 0D;
		}
		if(netAssets == null) {
			netAssets = 0D;
		}
		if(financialAssets == null) {
			financialAssets = 0D;
		}
		if(sumRecharge == null) {
			sumRecharge = 0D;
		}
		if(sumWithdraw == null) {
			sumWithdraw = 0D;
		}
		if(rechargeCount == null) {
			rechargeCount = 0;
		}
		if(withdrawCount == null) {
			withdrawCount = 0;
		}
		if(cancelCount == null) {
			cancelCount = 0;
		}
		if(transferCount == null) {
			transferCount = 0;
		}
		if(acceptCount == null) {
			acceptCount = 0;
		}
		if(freeWithdrawCount == null) {
			freeWithdrawCount = 0;
		}
		if(investmentCount == null) {
			investmentCount = 0;
		}
		
	}

	public String getAvatarImage() {
		return avatarImage;
	}

	public void setAvatarImage(String avatarImage) {
		this.avatarImage = avatarImage;
	}

	public String getHasOpenThirdAccount() {
		return hasOpenThirdAccount;
	}

	public void setHasOpenThirdAccount(String hasOpenThirdAccount) {
		this.hasOpenThirdAccount = hasOpenThirdAccount;
	}

	public Double getWillPrincipal() {
		return willPrincipal;
	}

	public void setWillPrincipal(Double willPrincipal) {
		this.willPrincipal = willPrincipal;
	}
	
	
}