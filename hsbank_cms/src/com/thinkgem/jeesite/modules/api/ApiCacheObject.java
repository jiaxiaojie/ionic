package com.thinkgem.jeesite.modules.api;

import java.util.Date;
import java.util.HashMap;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ApiCacheObject extends DataEntity<ApiCacheObject>{
	private static final long serialVersionUID = 1L;
    public String uuid; //UUID
	public HashMap<String, Object> map = new HashMap<String, Object>();
	public String req;		// 请求内容
	public String sign;		// 签名加密串
	public String type;		// 业务类型(1:注册，2:绑卡，3:充值，4:投资，5:体现，6:重置交易密码)
	public Date lastDt;	// 更新时间
	public String errorMsg;		//错误提示信息
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public HashMap<String, Object> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}
	public String getReq() {
		return req;
	}
	public void setReq(String req) {
		this.req = req;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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

}
