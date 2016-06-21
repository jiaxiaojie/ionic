/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 第三方交互日志Entity
 * 
 * @author yangtao
 * @version 2015-08-03
 */
public class LogThirdParty extends DataEntity<LogThirdParty> {

	private static final long serialVersionUID = 1L;
	private String requestNo; // 请求流水号
	private String service; // 关联业务编号
	private String reqContent; // 请求报文
	private Date reqDt; // 请求时间
	private String respContent; // 返回报文
	private String respCode; // 返回结果
	private Date respDt; // 返回时间
	private String notifyContent; // 通知报文
	private String notifyCode; // 通知结果
	private Date notifyDt; // 通知时间
	private Date queryDt; // 查询时间
	private String queryReq; // 查询报文
	private String queryResp; // 查询相应报文
	private String queryCode; // 查询结果
	private Date beginReqDt; // 开始 请求时间
	private Date endReqDt; // 结束 请求时间

	private Date confirmDt; // 确认时间
	private String confirmCode; // 确认结果
	private String confirmReq; // 确认请求报文
	private String confirmResp; // 确认返回报文

	public Date getConfirmDt() {
		return confirmDt;
	}

	public void setConfirmDt(Date confirmDt) {
		this.confirmDt = confirmDt;
	}

	public String getConfirmCode() {
		return confirmCode;
	}

	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}

	public String getConfirmReq() {
		return confirmReq;
	}

	public void setConfirmReq(String confirmReq) {
		this.confirmReq = confirmReq;
	}

	public String getConfirmResp() {
		return confirmResp;
	}

	public void setConfirmResp(String confirmResp) {
		this.confirmResp = confirmResp;
	}

	public LogThirdParty() {
		super();
	}

	public LogThirdParty(String id) {
		super(id);
	}

	@Length(min = 0, max = 50, message = "请求流水号长度必须介于 0 和 50 之间")
	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	@Length(min = 0, max = 50, message = "关联业务编号长度必须介于 0 和 50 之间")
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getReqContent() {
		return reqContent;
	}

	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReqDt() {
		return reqDt;
	}

	public void setReqDt(Date reqDt) {
		this.reqDt = reqDt;
	}

	public String getRespContent() {
		return respContent;
	}

	public void setRespContent(String respContent) {
		this.respContent = respContent;
	}

	@Length(min = 0, max = 5, message = "返回结果长度必须介于 0 和 5 之间")
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRespDt() {
		return respDt;
	}

	public void setRespDt(Date respDt) {
		this.respDt = respDt;
	}

	public String getNotifyContent() {
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	@Length(min = 0, max = 5, message = "通知结果长度必须介于 0 和 5 之间")
	public String getNotifyCode() {
		return notifyCode;
	}

	public void setNotifyCode(String notifyCode) {
		this.notifyCode = notifyCode;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getNotifyDt() {
		return notifyDt;
	}

	public void setNotifyDt(Date notifyDt) {
		this.notifyDt = notifyDt;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getQueryDt() {
		return queryDt;
	}

	public void setQueryDt(Date queryDt) {
		this.queryDt = queryDt;
	}

	public String getQueryCode() {
		return queryCode;
	}

	public void setQueryCode(String queryCode) {
		this.queryCode = queryCode;
	}

	public Date getBeginReqDt() {
		return beginReqDt;
	}

	public void setBeginReqDt(Date beginReqDt) {
		this.beginReqDt = beginReqDt;
	}

	public Date getEndReqDt() {
		return endReqDt;
	}

	public void setEndReqDt(Date endReqDt) {
		this.endReqDt = endReqDt;
	}

	public String getQueryReq() {
		return queryReq;
	}

	public void setQueryReq(String queryReq) {
		this.queryReq = queryReq;
	}

	public String getQueryResp() {
		return queryResp;
	}

	public void setQueryResp(String queryResp) {
		this.queryResp = queryResp;
	}

}