package com.thinkgem.jeesite.modules.api.to;


public class MyIntegralResp {
	private  Integer count;            //记录总数
	private String changeVal;		// 变更分值
	private String changeReason;		// 变更原因
	private String type;			//类型（1收，2兑）
	private String typeName;		//类型名称
	private String opDt; 	//操作时间
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getChangeVal() {
		return changeVal;
	}
	public void setChangeVal(String changeVal) {
		this.changeVal = changeVal;
	}
	public String getChangeReason() {
		return changeReason;
	}
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	public String getOpDt() {
		return opDt;
	}
	public void setOpDt(String opDt) {
		this.opDt = opDt;
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
	
    
    
}
