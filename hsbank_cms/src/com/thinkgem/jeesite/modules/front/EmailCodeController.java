package com.thinkgem.jeesite.modules.front;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.mail.handler.Mail;
import com.thinkgem.jeesite.common.utils.mail.handler.MailSendHandler;
import com.thinkgem.jeesite.common.utils.mail.handler.VelocityTemplate;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.LogSendValidateCode;
import com.thinkgem.jeesite.modules.log.service.LogSendValidateCodeService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 邮箱验证码controller
 * @author ydt
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/emailCode")
public class EmailCodeController extends BaseController {
	@Autowired
	private MailSendHandler mailSendHandler;
	@Autowired
	private VelocityTemplate velocityTemplate;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private LogSendValidateCodeService logSendValidateCodeService;
	@Autowired
	private CustomerAccountService customerAccountService;
	
	//邮箱验证码超时时间（单位：秒）
	public static final long EMAIL_VALIDATE_CODE_TIMEOUT = 300;

	/**
	 * 发送重置密码邮箱验证码
	 * 		添加logSendValidateCode表新纪录
	 * @param eamil
	 * @param validateCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "sendToResetPassword")
	@ResponseBody
	public String sendToResetPassword(String eamil, String validateCode, HttpServletRequest request) {
		if(ValidateCodeServlet.validate(request, validateCode)) {
			CustomerBase customerBase = customerBaseService.getByEmail(eamil);
			String emailValidateCode = StringUtils.createValidateCode(6);
			Mail mail = new Mail();
			mail.setReceivers(eamil);
			mail.setSubject("花生金服，重置密码");
			mail.setTemplate(velocityTemplate.getMailTemplate("resetPassword",customerBase.getCustomerName(), emailValidateCode));
			mailSendHandler.sendEmail(mail);
			
			LogSendValidateCode logSendValidateCode = new LogSendValidateCode();
			logSendValidateCode.setEmail(eamil);
			logSendValidateCode.setValidateCode(emailValidateCode);
			logSendValidateCode.setServiceTypeCode(ProjectConstant.SEND_VALIDATE_CODE_SERVICE_TYPE_RESET_PASSWORD);
			logSendValidateCode.setOpDt(new Date());
			logSendValidateCode.setIp(request.getHeader("X-Real-IP"));
			logSendValidateCodeService.save(logSendValidateCode);
			
			return "success";
		} else {
			return "invalidateValidateCode";
		}
	}

	@RequestMapping(value = "sendToChangeEmail")
	@ResponseBody
	public String sendToChangeEmail(HttpServletRequest request, String email, Long accountId) {
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		String emailValidateCode =StringUtils.createValidateCode(6);
		Mail mail = new Mail();
		mail.setReceivers(email);
		mail.setSubject("花生金服，修改邮箱");
		mail.setTemplate(velocityTemplate.getMailTemplate("changeEmail",customerBase.getCustomerName(), emailValidateCode));
		mailSendHandler.sendEmail(mail);
		
		LogSendValidateCode logSendValidateCode = new LogSendValidateCode();
		logSendValidateCode.setEmail(email);
		logSendValidateCode.setValidateCode(emailValidateCode);
		logSendValidateCode.setServiceTypeCode(ProjectConstant.SEND_VALIDATE_CODE_SERVICE_TYPE_CHANGE_EMAIL);
		logSendValidateCode.setOpDt(new Date());
		logSendValidateCode.setIp(request.getHeader("X-Real-IP"));
		logSendValidateCodeService.save(logSendValidateCode);
		return "success";
	}
	
	/**
	 * 验证邮箱验证码
	 * @param email
	 * @param emailCode
	 * @return
	 */
	@RequestMapping("auth")
	@ResponseBody
	public boolean auth(String email, String emailCode) {
		return customerAccountService.auth(email, emailCode);
	}
}
