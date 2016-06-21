package com.thinkgem.jeesite.modules.api.client;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class AndroidProperty extends DataEntity<AndroidProperty>{
	private static final long serialVersionUID = 1L;
	public String channel;		//发布渠道
	public String md5;		//apk签名的md5值
	public String deviceModel;		//设备型号
	public String deviceNumber;		//设备号码
	public String platformVersion;		//操作系统的版本，如：Android 4.3s
	public String sdkVersion;		//SDK的版本，如：19
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	public String getPlatformVersion() {
		return platformVersion;
	}
	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}
	public String getSdkVersion() {
		return sdkVersion;
	}
	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}
    
	
}
