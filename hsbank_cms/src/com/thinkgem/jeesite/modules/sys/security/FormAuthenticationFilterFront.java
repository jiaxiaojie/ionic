/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

/**
 * 表单验证（包含验证码）过滤类
 * @author ThinkGem
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilterFront extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
	private SystemService systemService;
	private MarketFacadeService marketFacadeService;

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";
    
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String accountName = getUsername(request);
		String accountPwd = getPassword(request);
		if (accountPwd==null){
			accountPwd = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		String captcha = getCaptcha(request);
		String client= getClient(request);
		boolean mobile = isMobileLogin(request);
		return new UsernamePasswordTokenFront(accountName, accountPwd.toCharArray(), rememberMe, host, captcha, mobile,client,false);
	}

	private String getClient(ServletRequest request) {
		// TODO Auto-generated method stub
		return "client";
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}
	
	protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }
	
	public String getMessageParam() {
		return messageParam;
	}
	
	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl(ServletRequest request) {
		/*if(WebUtils.getSavedRequest(request) != null) {
			System.out.println(WebUtils.getSavedRequest(request).getRequestUrl());
		}*/
		return super.getSuccessUrl();
	}
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		//获取登录用户信息
		CustomerAccount customerAccount = CustomerUtils.get();
		// 更新登录IP和时间
		getSystemService().updateCustomerSuccessLoginInfo(request);
		//调用登录活动
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, customerAccount.getAccountId());
		map.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LOGIN);
		map.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
		getMarketFacadeService().login(map);
		
//		Principal p = UserUtils.getPrincipal();
//		if (p != null && !p.isMobileLogin()){
			 WebUtils.issueRedirect(request, response, getSuccessUrl(request), null, true);
//		}else{
//			super.issueSuccessRedirect(request, response);
//		}
	}

	public MarketFacadeService getMarketFacadeService() {
		if (marketFacadeService == null){
			marketFacadeService = SpringContextHolder.getBean(MarketFacadeService.class);
		}
		return marketFacadeService;
	}
	
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
	
	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request, ServletResponse response) {
		UsernamePasswordTokenFront upToken = (UsernamePasswordTokenFront) token;
		getSystemService().updateCustomerFailureLoginInfo(upToken.getAccountName(), request);
		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)){
			message = "用户或密码错误, 请重试.";
		}
		else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		}
		else{
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
	}
	
}