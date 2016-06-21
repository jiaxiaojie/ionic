package com.thinkgem.jeesite.modules.api.to;


public class MobileVersionResp {

	private String needUpdate;		// 是否有更新（0是，其它不是）
	private String needForcedUpdate;	// 是否强制更新（0是，其它不是）
	private String url;		// apk文件URL
	private String version;		// 版本
	private String androidAppSize;		// 版本大小
	private String versionInfo;		// App版本说明
	public String getNeedUpdate() {
		return needUpdate;
	}
	public void setNeedUpdate(String needUpdate) {
		this.needUpdate = needUpdate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersionInfo() {
		return versionInfo;
	}
	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}
	public String getAndroidAppSize() {
		return androidAppSize;
	}
	public void setAndroidAppSize(String androidAppSize) {
		this.androidAppSize = androidAppSize;
	}
	public String getNeedForcedUpdate() {
		return needForcedUpdate;
	}
	public void setNeedForcedUpdate(String needForcedUpdate) {
		this.needForcedUpdate = needForcedUpdate;
	}
	
	
	
    
    
}
