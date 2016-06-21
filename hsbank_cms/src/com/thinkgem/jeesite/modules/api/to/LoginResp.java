package com.thinkgem.jeesite.modules.api.to;

/**
 * 登录返回
 * @author lizibo
 *
 */
public class LoginResp {
	private String token;		// 令牌

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginResp(String token) {
		super();
		this.token = token;
	}

	public LoginResp() {
		super();
	}
	
	
}
