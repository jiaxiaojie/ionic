package com.thinkgem.jeesite.modules.api;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class WechatCacheObject extends DataEntity<WechatCacheObject>{
	private static final long serialVersionUID = 1L;
    public String cacheKey; //cacheKey
    public String access_token;
    public String jsapi_ticket;
	public Date lastDt;	// 更新时间
	public String errorMsg;		//错误提示信息
	public String getCacheKey() {
		return cacheKey;
	}
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	public Date getLastDt() {
		return lastDt;
	}
	public void setLastDt(Date lastDt) {
		this.lastDt = lastDt;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getJsapi_ticket() {
		return jsapi_ticket;
	}
	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}
	
	
	
	

}
