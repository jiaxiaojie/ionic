package com.thinkgem.jeesite.modules.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.utils.mail.handler.Mail;
import com.thinkgem.jeesite.common.utils.mail.handler.MailSendHandler;
import com.thinkgem.jeesite.common.utils.mail.handler.VelocityTemplate;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${frontPath}")
public class MailController extends BaseController {

	@Autowired
	private MailSendHandler mailSendHandler;
	@Autowired
	private VelocityTemplate velocityTemplate;
	
	
	
	@RequestMapping(value = "sendMail")
	@ResponseBody
	public String sendMail(Mail mail, HttpServletRequest request, HttpServletResponse response, Model model) {
		mail.setReceivers("abc@qq.com;abc@163.com");
		mail.setSubject("富定金服");
		mail.setTemplate(velocityTemplate.getMailTemplate("resetPassword","zhangsan", "www.baidu.com"));
		mailSendHandler.sendEmail(mail);
		return "";
	}
}
