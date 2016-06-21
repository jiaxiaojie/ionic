package com.thinkgem.jeesite.modules.api.to;

public class MyCurChangeHisResp {

	private String opDate;		// 操作时间
	private String type;		// 变更类型
	private String typeName;		// 变更类型名称
	private Double amount;		// 金额
	private String remarks;		//备注

	public String getOpDate() {
		return opDate;
	}

	public void setOpDate(String opDate) {
		this.opDate = opDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
