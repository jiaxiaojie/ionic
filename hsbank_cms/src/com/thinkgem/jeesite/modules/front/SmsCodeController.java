package com.thinkgem.jeesite.modules.front;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.sms_auth.handler.SmsAuthHandler;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.LogSendValidateCode;
import com.thinkgem.jeesite.modules.log.service.LogSendValidateCodeService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.hsbank.util.collection.MapUtil;

/**
 * 短信验证码controller
 * @author ydt
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/smsCode")
public class SmsCodeController extends BaseController {
	@Autowired
	private LogSendValidateCodeService logSendValidateCodeService;
	/**
	 * 发送注册新用户短信验证码
	 * 		添加logSendValidateCode表新纪录
	 * @param mobile
	 * @param validateCode
	 * @param request
	 * @return
	 */
	@RequestMapping("sendToRegister")
	@ResponseBody
	public String sendToRegister(String mobile, String validateCode, HttpServletRequest request) {
		
		if(ValidateCodeServlet.validate(request, validateCode)) {
			if(!ValidateCodeServlet.validateCurrentCodeSentSmsCount(request)){
				return "overdueValidateCode";
			}
			String serviceTypeCode = ProjectConstant.SEND_VALIDATE_CODE_SERVICE_TYPE_REGISTER;
			sendSmsCode(mobile, serviceTypeCode, validateCode, request);
			return "success";
		} else {
			return "invalidateValidateCode";
		}
	}
	
	/**
	 * 发送重置密码短信验证码
	 * 		添加logSendValidateCode表新纪录
	 * @param mobile
	 * @param validateCode
	 * @param request
	 * @return
	 */
	@RequestMapping("sendToResetPassword")
	@ResponseBody
	public String sendToResetPassword(String mobile, String validateCode, HttpServletRequest request) {
		if(ValidateCodeServlet.validate(request, validateCode)) {
			if(!ValidateCodeServlet.validateCurrentCodeSentSmsCount(request)){
				return "overdueValidateCode";
			}
			String serviceTypeCode = ProjectConstant.SEND_VALIDATE_CODE_SERVICE_TYPE_RESET_PASSWORD;
			sendSmsCode(mobile, serviceTypeCode, validateCode, request);
			return "success";
		} else {
			return "invalidateValidateCode";
		}
	}

	/**
	 * 发送短信验证码并添加logSendValidateCode表新纪录
	 * @param mobile
	 * @param serviceTypeCode
	 * @param validateCode
	 * @param request
	 */
	private void sendSmsCode(String mobile, String serviceTypeCode, String validateCode, HttpServletRequest request) {
		SmsAuthHandler.getInstance().sendRequest(mobile);
		
		LogSendValidateCode logSendValidateCode = new LogSendValidateCode();
		logSendValidateCode.setMobile(mobile);
		logSendValidateCode.setServiceTypeCode(ProjectConstant.SEND_VALIDATE_CODE_SERVICE_TYPE_RESET_PASSWORD);
		logSendValidateCode.setOpDt(new Date());
		logSendValidateCode.setIp(request.getHeader("X-Real-IP"));
		logSendValidateCodeService.save(logSendValidateCode);
	}
	
	/**
	 * 验证短信验证码
	 * @param mobile
	 * @param smsCode
	 * @return
	 */
	@RequestMapping("auth")
	@ResponseBody
	public static boolean auth(String mobile, String smsCode) {
		return "200".equals(MapUtil.getString(SmsAuthHandler.getInstance().authRequest(mobile, smsCode), "statusCode"));
	}
}
