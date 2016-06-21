package com.thinkgem.jeesite.modules.api.to;

public class CurrentChangeHisResp {

	private String opDt;		// 操作时间
	private String changeType;		// 变更类型
	private String changeTypeName;		// 变更类型名称
	private String changeVal;		// 变更值
	
	public String getOpDt() {
		return opDt;
	}
	public void setOpDt(String opDt) {
		this.opDt = opDt;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getChangeTypeName() {
		return changeTypeName;
	}
	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}
	public String getChangeVal() {
		return changeVal;
	}
	public void setChangeVal(String changeVal) {
		this.changeVal = changeVal;
	}

}
