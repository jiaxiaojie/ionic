package com.thinkgem.jeesite.modules.api.to;


public class MyTicketsResp {
	private Long ticketId;			//优惠券Id
	private Long type;				//类型
	private String typeName;		// 类型名称
	private String useInfo;		// 使用说明
	private String getRemark;		// 来源备注
	private Integer useLimit;		// 使用限制
	private Integer amount;		// 面值
	private Long status;		// 状态(0, 1, 2)
	private String statusName;		// 状态名称(正常,已使用,过期)
	private String invalidDt; 	//失效时间
	private String beginValidDate;	//有效期-开始日期"
	private String endValidDate;	//有效期-结束日期
	private String isSelect;		//是否选中(1:是,0:否)
	private String applyProject;		//适用项目
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
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
	public String getUseInfo() {
		return useInfo;
	}
	public void setUseInfo(String useInfo) {
		this.useInfo = useInfo;
	}
	public Integer getUseLimit() {
		return useLimit;
	}
	public void setUseLimit(Integer useLimit) {
		this.useLimit = useLimit;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getInvalidDt() {
		return invalidDt;
	}
	public void setInvalidDt(String invalidDt) {
		this.invalidDt = invalidDt;
	}
	public String getGetRemark() {
		return getRemark;
	}
	public void setGetRemark(String getRemark) {
		this.getRemark = getRemark;
	}

	public String getBeginValidDate() {
		return beginValidDate;
	}

	public void setBeginValidDate(String beginValidDate) {
		this.beginValidDate = beginValidDate;
	}

	public String getEndValidDate() {
		return endValidDate;
	}

	public void setEndValidDate(String endValidDate) {
		this.endValidDate = endValidDate;
	}

	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	public String getApplyProject() {
		return applyProject;
	}

	public void setApplyProject(String applyProject) {
		this.applyProject = applyProject;
	}
}
