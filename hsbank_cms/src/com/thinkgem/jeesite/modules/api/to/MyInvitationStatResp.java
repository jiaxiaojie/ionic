package com.thinkgem.jeesite.modules.api.to;
/**
 * 我的信息
 * @author lizibo
 *
 */
public class MyInvitationStatResp {
	private int registerCount;	//注册人数
	private int nameAuthCount;  //实名人数
	private int investCount;  //投资人数
	private int investAccount;	//投资人数(旧版即将废除)
	private Double registerAmount;	//好友注册得现金券额度
	private Double nameAuthAmount;	//好友实名得现金券额度
	private Double investAmount;		//好友投资得现金券额度
	private Double earningAmount;		// 奖励金额
	private Double earningTicketAmount;		// 奖励投资券额度
	
	public int getRegisterCount() {
		return registerCount;
	}
	public void setRegisterCount(int registerCount) {
		this.registerCount = registerCount;
	}
	public int getNameAuthCount() {
		return nameAuthCount;
	}
	public void setNameAuthCount(int nameAuthCount) {
		this.nameAuthCount = nameAuthCount;
	}

	public Double getEarningAmount() {
		return earningAmount;
	}
	public void setEarningAmount(Double earningAmount) {
		this.earningAmount = earningAmount;
	}
	public Double getEarningTicketAmount() {
		return earningTicketAmount;
	}
	public void setEarningTicketAmount(Double earningTicketAmount) {
		this.earningTicketAmount = earningTicketAmount;
	}
	public int getInvestCount() {
		return investCount;
	}
	public void setInvestCount(int investCount) {
		this.investCount = investCount;
	}
	public Double getRegisterAmount() {
		return registerAmount;
	}
	public void setRegisterAmount(Double registerAmount) {
		this.registerAmount = registerAmount;
	}
	public Double getNameAuthAmount() {
		return nameAuthAmount;
	}
	public void setNameAuthAmount(Double nameAuthAmount) {
		this.nameAuthAmount = nameAuthAmount;
	}
	public Double getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}
	public int getInvestAccount() {
		return investAccount;
	}
	public void setInvestAccount(int investAccount) {
		this.investAccount = investAccount;
	}
	
	
	
	
}
