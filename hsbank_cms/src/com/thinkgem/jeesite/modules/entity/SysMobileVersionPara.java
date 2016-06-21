/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员账户余额对齐Entity
 * @author lzb
 * @version 2015-11-10
 */
public class SysMobileVersionPara extends DataEntity<SysMobileVersionPara> {
	
	private static final long serialVersionUID = 1L;
	private String version;		// 版本
	private String versionSize;		// 版本大小
	private String versionInfo;		// 版本说明
	private String url;		// apk文件URL
	private String channel;		// 渠道
	private String isForcedUpdate;	//是否强制更新
	private String type;		// 移动端类型
	private String mark;		// 启用标示(0:停用；1：启用)
	
	
	public SysMobileVersionPara(String channel, String mark) {
		super();
		this.channel = channel;
		this.mark = mark;
	}

	public SysMobileVersionPara() {
		super();
	}

	public SysMobileVersionPara(String id){
		super(id);
	}

	@Length(min=0, max=20, message="版本长度必须介于 0 和 20 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=0, max=500, message="版本说明长度必须介于 0 和 500 之间")
	public String getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}
	
	@Length(min=0, max=200, message="apk文件URL长度必须介于 0 和 200 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=20, message="渠道长度必须介于 0 和 20 之间")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Length(min=0, max=20, message="移动端类型长度必须介于 0 和 20 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=2, message="启用标示(0:停用；1：启用)长度必须介于 0 和 2 之间")
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getVersionSize() {
		return versionSize;
	}

	public void setVersionSize(String versionSize) {
		this.versionSize = versionSize;
	}

	public String getIsForcedUpdate() {
		return isForcedUpdate;
	}

	public void setIsForcedUpdate(String isForcedUpdate) {
		this.isForcedUpdate = isForcedUpdate;
	}
	
	
}