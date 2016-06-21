package com.thinkgem.jeesite.modules.api.to;

/**
 * 交易记录
 * @author lzb
 */
public class TransRecordResp {
    private Long recordId;		//记录id
	private String opDate;		// 操作时间
	private Long type;		// 变更类型
	private String typeName;		// 变更类型名称
	private String details;		// 交易详情
	private Double amount;		//交易金额
	private Double balance;		//账户余额
	private String remarks;		//备注

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public String getOpDate() {
		return opDate;
	}

	public void setOpDate(String opDate) {
		this.opDate = opDate;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
