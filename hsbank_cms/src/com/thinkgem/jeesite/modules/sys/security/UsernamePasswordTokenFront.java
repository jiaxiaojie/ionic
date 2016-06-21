/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

/**
 * 用户和密码（包含验证码）令牌类
 * @author ThinkGem
 * @version 2013-5-19
 */
public class UsernamePasswordTokenFront extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;
	private boolean mobileLogin;
	private String client;
	private boolean isNew; 
    private String token;
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public UsernamePasswordTokenFront() {
		super();
	}

	public UsernamePasswordTokenFront(String accountName, char[] accountPwd,
			boolean rememberMe, String host, String captcha, boolean mobileLogin,String client,boolean isNew) {
		super(accountName, accountPwd, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
		this.client= client;
		this.isNew=isNew;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}
    
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getAccountName() {
		return super.getUsername();
	}
	
	@Deprecated
	public String getUsername() {
		return super.getUsername();
	}
	
}