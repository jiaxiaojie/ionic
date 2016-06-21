/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员举报信息Entity
 * @author ydt
 * @version 2015-06-26
 */
public class CustomerTipoff extends DataEntity<CustomerTipoff> {
	
	private static final long serialVersionUID = 1L;
	private String informantsName;		// 举报人
	private Date createDt;		// 举报时间
	private String informantsEmail;		// 举报人邮箱
	private String informantsMobile;		// 举报人联系电话
	private String content;		// 举报内容
	private String attr1;		// 举报附件1
	private String attr2;		// 举报附件2
	private String attr3;		// 举报附件3
	private String verifyName;		// 举报查证人员
	private String verifyReply;		// 查证结果回复
	private Date replyDt;		// 回复时间
	private String status;		// 举报状态
	private Date beginCreateDt;		// 开始 举报时间
	private Date endCreateDt;		// 结束 举报时间
	private Date beginReplyDt;		// 开始 回复时间
	private Date endReplyDt;		// 结束 回复时间
	
	public CustomerTipoff() {
		super();
	}

	public CustomerTipoff(String id){
		super(id);
	}

	@Length(min=0, max=50, message="举报人长度必须介于 0 和 50 之间")
	public String getInformantsName() {
		return informantsName;
	}

	public void setInformantsName(String informantsName) {
		this.informantsName = informantsName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	@Length(min=0, max=50, message="举报人邮箱长度必须介于 0 和 50 之间")
	public String getInformantsEmail() {
		return informantsEmail;
	}

	public void setInformantsEmail(String informantsEmail) {
		this.informantsEmail = informantsEmail;
	}
	
	@Length(min=0, max=20, message="举报人联系电话长度必须介于 0 和 20 之间")
	public String getInformantsMobile() {
		return informantsMobile;
	}

	public void setInformantsMobile(String informantsMobile) {
		this.informantsMobile = informantsMobile;
	}
	
	@Length(min=0, max=500, message="举报内容长度必须介于 0 和 500 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=200, message="举报附件1长度必须介于 0 和 200 之间")
	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	
	@Length(min=0, max=200, message="举报附件2长度必须介于 0 和 200 之间")
	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	
	@Length(min=0, max=200, message="举报附件3长度必须介于 0 和 200 之间")
	public String getAttr3() {
		return attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
	
	@Length(min=0, max=20, message="举报查证人员长度必须介于 0 和 20 之间")
	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}
	
	@Length(min=0, max=500, message="查证结果回复长度必须介于 0 和 500 之间")
	public String getVerifyReply() {
		return verifyReply;
	}

	public void setVerifyReply(String verifyReply) {
		this.verifyReply = verifyReply;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReplyDt() {
		return replyDt;
	}

	public void setReplyDt(Date replyDt) {
		this.replyDt = replyDt;
	}
	
	@Length(min=0, max=2, message="举报状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginCreateDt() {
		return beginCreateDt;
	}

	public void setBeginCreateDt(Date beginCreateDt) {
		this.beginCreateDt = beginCreateDt;
	}
	
	public Date getEndCreateDt() {
		return endCreateDt;
	}

	public void setEndCreateDt(Date endCreateDt) {
		this.endCreateDt = endCreateDt;
	}
		
	public Date getBeginReplyDt() {
		return beginReplyDt;
	}

	public void setBeginReplyDt(Date beginReplyDt) {
		this.beginReplyDt = beginReplyDt;
	}
	
	public Date getEndReplyDt() {
		return endReplyDt;
	}

	public void setEndReplyDt(Date endReplyDt) {
		this.endReplyDt = endReplyDt;
	}
		
}