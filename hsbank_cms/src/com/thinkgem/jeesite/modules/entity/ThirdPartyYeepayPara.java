/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 托管账号参数Entity
 * @author yangtao
 * @version 2015-08-03
 */
public class ThirdPartyYeepayPara extends DataEntity<ThirdPartyYeepayPara> {
	
	private static final long serialVersionUID = 1L;
	private String yeepayPlatformNo;		// 易宝商户编号
	private String yeepayGateUrlPrefix;		// 易宝浏览器网关地址前缀
	private String yeepayDirectUrlPrefix;		// 易宝直接接口地址前缀
	private String yeepaySignRulPrefix;		// 易宝加密校验地址前缀
	private String yeepayCallbackUrlPrefixDemo;		// 易宝浏览器返回地址前缀(验证)
	private String yeepayNotifyUrlPrefixDemo;		// 易宝浏览器通知地址前缀(验证)
	private String yeepayCallbackUrlPrefix;		  // 易宝浏览器返回地址前缀(生产)
	private String yeepayNotifyUrlPrefix;		  // 易宝浏览器通知地址前缀(生产)
	private String yeepayTenderordernoPrefix;    //测试环境传给易宝trenderNo前缀(测试)
	private String yeepayGateWayCallbackUrlPrefix; //易宝前端浏览器callback地址前缀
	private String yeepayGateWayNotifyPrefix;      //易宝前端浏览器notify地址前缀
	private String yeepayDirectNotifyUrlPrefix;    //易宝直连notify地址前缀
	private String yeepayGateWayWirelessCallbackUrlPrefix;  //易宝前端浏览器移动端callback地址前缀
	
	public ThirdPartyYeepayPara() {
		super();
	}

	public ThirdPartyYeepayPara(String id){
		super(id);
	}

	@Length(min=0, max=50, message="易宝商户编号长度必须介于 0 和 50 之间")
	public String getYeepayPlatformNo() {
		return yeepayPlatformNo;
	}

	public void setYeepayPlatformNo(String yeepayPlatformNo) {
		this.yeepayPlatformNo = yeepayPlatformNo;
	}
	
	@Length(min=0, max=500, message="易宝浏览器网关地址前缀长度必须介于 0 和 500 之间")
	public String getYeepayGateUrlPrefix() {
		return yeepayGateUrlPrefix;
	}

	public void setYeepayGateUrlPrefix(String yeepayGateUrlPrefix) {
		this.yeepayGateUrlPrefix = yeepayGateUrlPrefix;
	}
	
	@Length(min=0, max=500, message="易宝直接接口地址前缀长度必须介于 0 和 500 之间")
	public String getYeepayDirectUrlPrefix() {
		return yeepayDirectUrlPrefix;
	}

	public void setYeepayDirectUrlPrefix(String yeepayDirectUrlPrefix) {
		this.yeepayDirectUrlPrefix = yeepayDirectUrlPrefix;
	}
	
	@Length(min=0, max=500, message="易宝加密校验地址前缀长度必须介于 0 和 500 之间")
	public String getYeepaySignRulPrefix() {
		return yeepaySignRulPrefix;
	}

	public void setYeepaySignRulPrefix(String yeepaySignRulPrefix) {
		this.yeepaySignRulPrefix = yeepaySignRulPrefix;
	}
	
	@Length(min=0, max=500, message="易宝浏览器返回地址前缀(生产)长度必须介于 0 和 500 之间")
	public String getYeepayCallbackUrlPrefix() {
		return yeepayCallbackUrlPrefix;
	}

	public void setYeepayCallbackUrlPrefix(String yeepayCallbackUrlPrefix) {
		this.yeepayCallbackUrlPrefix = yeepayCallbackUrlPrefix;
	}
	
	@Length(min=0, max=500, message="易宝浏览器通知地址前缀(生产)长度必须介于 0 和 500 之间")
	public String getYeepayNotifyUrlPrefix() {
		return yeepayNotifyUrlPrefix;
	}
	
	public void setYeepayNotifyUrlPrefix(String yeepayNotifyUrlPrefix) {
		this.yeepayNotifyUrlPrefix = yeepayNotifyUrlPrefix;
	}
	
	@Length(min=0, max=500, message="易宝返回地址前缀(验证)长度必须介于 0 和 500 之间")
	public String getYeepayCallbackUrlPrefixDemo() {
		return yeepayCallbackUrlPrefixDemo;
	}

	public void setYeepayCallbackUrlPrefixDemo(String yeepayCallbackUrlPrefixDemo) {
		this.yeepayCallbackUrlPrefixDemo = yeepayCallbackUrlPrefixDemo;
	}
	@Length(min=0, max=500, message="易宝通知地址前缀(验证)长度必须介于 0 和 500 之间")
	public String getYeepayNotifyUrlPrefixDemo() {
		return yeepayNotifyUrlPrefixDemo;
	}

	public void setYeepayNotifyUrlPrefixDemo(String yeepayNotifyUrlPrefixDemo) {
		this.yeepayNotifyUrlPrefixDemo = yeepayNotifyUrlPrefixDemo;
	}
	
	@Length(min=0, max=500, message="测试环境传给易宝trenderNo前缀长度必须介于 0 和 500 之间")
	public String getYeepayTenderordernoPrefix() {
		return yeepayTenderordernoPrefix;
	}

	public void setYeepayTenderordernoPrefix(String yeepayTenderordernoPrefix) {
		this.yeepayTenderordernoPrefix = yeepayTenderordernoPrefix;
	}
	
	@Length(min=0, max=500, message="易宝前端浏览器callback地址前缀长度必须介于 0 和 500 之间")
	public String getYeepayGateWayCallbackUrlPrefix() {
		return yeepayGateWayCallbackUrlPrefix;
	}

	public void setYeepayGateWayCallbackUrlPrefix(String yeepayGateWayCallbackUrlPrefix) {
		this.yeepayGateWayCallbackUrlPrefix = yeepayGateWayCallbackUrlPrefix;
	}
	@Length(min=0, max=500, message="易宝前端浏览器notify地址前缀长度必须介于 0 和 500 之间")
	public String getYeepayGateWayNotifyPrefix() {
		return yeepayGateWayNotifyPrefix;
	}

	public void setYeepayGateWayNotifyPrefix(String yeepayGateWayNotifyPrefix) {
		this.yeepayGateWayNotifyPrefix = yeepayGateWayNotifyPrefix;
	}
	@Length(min=0, max=500, message="易宝直连notify地址前缀长度必须介于 0 和 500 之间")
	public String getYeepayDirectNotifyUrlPrefix() {
		return yeepayDirectNotifyUrlPrefix;
	}

	public void setYeepayDirectNotifyUrlPrefix(String yeepayDirectNotifyUrlPrefix) {
		this.yeepayDirectNotifyUrlPrefix = yeepayDirectNotifyUrlPrefix;
	}
	
	@Length(min=0, max=500, message="易宝前端浏览器移动端callback地址前缀长度必须介于 0 和 500 之间")
	public String getYeepayGateWayWirelessCallbackUrlPrefix() {
		return yeepayGateWayWirelessCallbackUrlPrefix;
	}

	public void setYeepayGateWayWirelessCallbackUrlPrefix(String yeepayGateWayWirelessCallbackUrlPrefix) {
		this.yeepayGateWayWirelessCallbackUrlPrefix = yeepayGateWayWirelessCallbackUrlPrefix;
	}
	
	
}