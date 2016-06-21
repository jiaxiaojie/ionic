package com.thinkgem.jeesite.modules.api.service;

import com.hsbank.util.collection.MapUtil;
import com.thinkgem.jeesite.common.utils.sms_auth.handler.SmsAuthHandler;
import com.thinkgem.jeesite.modules.entity.LogSendValidateCode;
import com.thinkgem.jeesite.modules.log.service.LogSendValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class SmsCodeService {
	@Autowired
	private LogSendValidateCodeService logSendValidateCodeService;
	
	
	/**
	 *  验证短信验证码
	 * <p>
	 * Description:描述<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年6月6日
	 * @param mobile
	 * @param smsCode
	 * @return
	 * boolean
	 */
	public boolean auth(String mobile, String smsCode) {
		return "200".equals(MapUtil.getString(SmsAuthHandler.getInstance().authRequest(mobile, smsCode), "statusCode"));
	}
	/**
	 *  
	 * <p>
	 * Description:发送短信验证码<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年6月13日
	 * @param request
	 * @param mobile
	 * @param serviceTypeCode
	 * void
	 */
	public void sendSmsCode(HttpServletRequest request, String mobile, String serviceTypeCode) {
		SmsAuthHandler.getInstance().sendRequest(mobile);
		//记录日志
		LogSendValidateCode logSendValidateCode = new LogSendValidateCode();
		logSendValidateCode.setMobile(mobile);
		logSendValidateCode.setServiceTypeCode(serviceTypeCode);
		logSendValidateCode.setOpDt(new Date());
		logSendValidateCode.setIp(request.getHeader("X-Real-IP"));
		logSendValidateCodeService.save(logSendValidateCode);
	}
}
