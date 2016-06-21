package com.thinkgem.jeesite.modules.api.client;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ClientProperty extends DataEntity<ClientProperty>{
	private static final long serialVersionUID = 1L;
	public String language;		//语言
	/**类型：website、wechat、android、ios*/
	public String type;
	public String version;		//版本
	public WechatProperty website;		//网站参数
	public WechatProperty wechat;		//微信参数
	public AndroidProperty android;	//android属性
	public IosProperty ios;	//ios属性
	public String localId;//pc端生成
	 
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	
	public WechatProperty getWebsite() {
		return website;
	}
	public void setWebsite(WechatProperty website) {
		this.website = website;
	}
	public WechatProperty getWechat() {
		return wechat;
	}
	public void setWechat(WechatProperty wechat) {
		this.wechat = wechat;
	}
	public AndroidProperty getAndroid() {
		return android;
	}
	public void setAndroid(AndroidProperty android) {
		this.android = android;
	}
	public IosProperty getIos() {
		return ios;
	}
	public void setIos(IosProperty ios) {
		this.ios = ios;
	}

	
    
}
