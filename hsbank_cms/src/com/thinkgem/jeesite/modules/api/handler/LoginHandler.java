package com.thinkgem.jeesite.modules.api.handler;

import com.hsbank.api.util.ApiParameterConstant;
import com.hsbank.api.util.ApiStatusConstant;
import com.hsbank.api.util.ApiUtil;
import com.hsbank.util.collection.MapUtil;
import com.hsbank.util.tool.LogUtil;
import com.hsbank.util.tool.MobileUtil;
import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.sms_auth.handler.SmsAuthHandler;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录处理器
 * 2016-06-16
 */
@Component
public class LoginHandler {
	/**日志对象*/
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CustomerAccountDao customerAccountDao;

	/**
	 * 【手机号码 + 密码】登录
	 * @param client
	 * @param mobile
	 * @param password
     * @return
     */
	public Map<String, Object> loginByAccount(String client, String mobile, String password) {
		Map<String, Object> resultValue = new HashMap<String, Object>();
		logger.info("client = " + client);
		logger.info("mobile = " + mobile);
		logger.info("password = " + password);
		LogUtil.start("AccountController.loginByAccount");
		String token = "";
		mobile = StringUtil.dealString(mobile);
		if ("".equals(mobile)) {
			resultValue = ApiUtil.fail(resultValue, "手机号码不能为空");
		} else if (MobileUtil.isMobile(mobile)){
			//用手机号码查询相应的帐号记录
			CustomerAccount customerAccount = customerAccountDao.getByLoginName(mobile);
			if (customerAccount == null) {
				resultValue = ApiUtil.fail(resultValue, "用户不存在");
			} else {
				if (Global.NO.equals(customerAccount.getStatusCode())) {
					//终端类型
					String opTerm = com.thinkgem.jeesite.modules.api.ApiUtil.getOperTerm(client);
					password = StringUtil.dealString(password);
					if ("".equals(password)) {
						resultValue = ApiUtil.fail(resultValue, "密码不能为空");
					} else {
						//用户名 + 密码，登录
						if (SystemService.validatePassword(password, customerAccount.getAccountPwd())) {
							resultValue.put("customerAccount", customerAccount);
							resultValue.put("opTerm", opTerm);
							resultValue = ApiUtil.success(resultValue);
						} else {
							resultValue = ApiUtil.fail(resultValue, "请输入正确的密码");
						}
					}
				} else {
					resultValue = ApiUtil.fail(resultValue, "用户不允许登录");
				}
			}
		} else {
			resultValue = ApiUtil.fail(resultValue, "请输入正确的手机号码");
		}
		LogUtil.end("AccountController.loginByAccount");
		return resultValue;
	}

	/**
	 * 【手机号码 + 短信验证码】登录
	 * @param client
	 * @param mobile
	 * @param smsCode
     * @return
     */
	public Map<String, Object> loginBySmsCode(String client, String mobile, String smsCode) {
		Map<String, Object> resultValue = new HashMap<String, Object>();
		logger.info("client = " + client);
		logger.info("mobile = " + mobile);
		logger.info("smsCode = " + smsCode);
		LogUtil.start("AccountController.loginBySmsCode");
		String token = "";
		mobile = StringUtil.dealString(mobile);
		if ("".equals(mobile)) {
			resultValue = ApiUtil.fail(resultValue, "手机号码不能为空");
		} else if (MobileUtil.isMobile(mobile)){
			//用手机号码查询相应的帐号记录
			CustomerAccount customerAccount = customerAccountDao.getByLoginName(mobile);
			if (customerAccount == null) {
				resultValue = ApiUtil.fail(resultValue, "用户不存在");
			} else {
				if (Global.NO.equals(customerAccount.getStatusCode())) {
					//终端类型
					String opTerm = com.thinkgem.jeesite.modules.api.ApiUtil.getOperTerm(client);
					smsCode = StringUtil.dealString(smsCode);
					if ("".equals(smsCode)) {
						resultValue = ApiUtil.fail(resultValue, "短信验证码不能为空");
					} else {
						//短信验证登录
						Map<String, Object> smsAuthMap = SmsAuthHandler.getInstance().authRequest(mobile, smsCode);
						String statusCode = MapUtil.getString(smsAuthMap, ApiParameterConstant.STATUS_CODE);
						if (statusCode.equals(ApiStatusConstant.SUCCESS_CODE)) {
							//登录成功
							resultValue.put("customerAccount", customerAccount);
							resultValue.put("opTerm", opTerm);
							resultValue = ApiUtil.success(resultValue);
						} else {
							resultValue = ApiUtil.fail(resultValue, "请输入正确的验证码");
						}
					}
				} else {
					resultValue = ApiUtil.fail(resultValue, "用户不允许登录");
				}
			}
		} else {
			resultValue = ApiUtil.fail(resultValue, "请输入正确的手机号码");
		}
		LogUtil.end("AccountController.loginBySmsCode");
		return resultValue;
	}
}
