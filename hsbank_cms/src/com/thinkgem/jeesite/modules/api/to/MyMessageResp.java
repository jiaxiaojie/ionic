package com.thinkgem.jeesite.modules.api.to;

public class MyMessageResp {

	private String messageId; // 消息编号
	private String messageChannel; // 发送渠道：web(0)、微信(1)、android(2)、ios(3)、短信(4)、邮件(5)
	private String title; // 标题
	private String content; // 内容
	private Long status; // 状态(2:已读、其它：未读)
	private String statusName; // 状态名称(已读、未读)
	private String createDt; // 创建时间
	
	private String target;     //目标参数
	private Integer isClick;    //是否可点击
	private Integer targetType;
	private Integer isMmergency;

	public Integer getIsMmergency() {
		return isMmergency;
	}

	public void setIsMmergency(Integer isMmergency) {
		this.isMmergency = isMmergency;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}



	public Integer getIsClick() {
		return isClick;
	}

	public void setIsClick(Integer isClick) {
		this.isClick = isClick;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageChannel() {
		return messageChannel;
	}

	public void setMessageChannel(String messageChannel) {
		this.messageChannel = messageChannel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getCreateDt() {
		return createDt;
	}

	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}

}
