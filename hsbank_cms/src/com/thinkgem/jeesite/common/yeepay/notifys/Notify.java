/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.notifys;

/**
 * @author yangtao
 *
 */

public abstract class Notify {
	// 请求内容体
	public String req;
	// 对内容体进行签名加密
	public String sign;

	// 校验签名加密是否符合约定
	public boolean checkSign() {
		
		return true;
	}

	// 校验内容是否符合约定
	public abstract boolean checkContent();

	// 响应通知，处理对应的业务逻辑
	public abstract boolean doNotify();
}
