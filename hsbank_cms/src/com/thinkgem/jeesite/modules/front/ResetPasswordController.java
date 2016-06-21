/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

/**
 * 忘记密码Controller
 * 
 * @author ydt
 * @version 2013-8-13
 */
@Controller
@RequestMapping("${frontPath}/resetPassword")
public class ResetPasswordController extends BaseController {
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private CustomerAccountService customerAccountService;
	

	@RequestMapping(value = "mobile", method = RequestMethod.GET)
	public String toResetPasswordByMobile(HttpServletRequest request, Model model) {
		if(!StringUtils.isBlank(request.getParameter("errorMsg"))) {
			model.addAttribute("errorMsg", "短信验证码输入错误，请重新输入.");
		}
		return "modules/front/resetPasswordByMobile";
	}

	@RequestMapping("checkMobileExist")
	@ResponseBody
	public boolean checkMobileExist(String mobile) {
		return customerBaseService.checkMobileExist(mobile);
	}

	@RequestMapping(value = "mobile", method = RequestMethod.POST)
	public String resetPasswordByMobile(String mobile, String smsCode, String newPassword, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		boolean isSuccess = false;
		boolean smsCodeAuth = SmsCodeController.auth(mobile, smsCode);
		//信息验证
		if(smsCodeAuth) {
			CustomerAccount customerAccount = customerAccountService.resetPasswordByMobile(mobile, newPassword);
			isSuccess = true;
			CustomerUtils.clearCache(customerAccount);
			CustomerUtils.get(customerAccount.getAccountId());
			CustomerUtils.getSubject().logout();
			model.addAttribute("isSuccess",isSuccess);
			return "redirect:" + frontPath + "/resetPassword/result?isSuccess=" + isSuccess;
		} else {
			return "redirect:" + frontPath + "/resetPassword/mobile?errorMsg=error";
		}
	}

	@RequestMapping(value = "email", method = RequestMethod.GET)
	public String toResetPasswordByEmail() {
		return "modules/front/resetPasswordByEmail";
	}

	@RequestMapping("checkEmailHasAuthed")
	@ResponseBody
	public boolean checkEmailHasAuthed(String email) {
		return customerBaseService.checkEmailHasAuthed(email);
	}

	@RequestMapping(value = "email", method = RequestMethod.POST)
	public String resetPasswordByEmail(String email, String emailCode,
			String newPassword, Model model,
			RedirectAttributes redirectAttributes) {
		boolean isSuccess = false;
		if (customerAccountService.auth(email, emailCode)) {
			customerAccountService.resetPasswordByEmail(email, newPassword);
			isSuccess = true;
			CustomerUtils.getSubject().logout();
		}
		model.addAttribute("isSuccess", isSuccess);
		return "redirect:" + frontPath + "/resetPassword/result?isSuccess="
				+ isSuccess;
	}

	@RequestMapping("result")
	public String result(boolean isSuccess, Model model) {
		model.addAttribute("isSuccess", isSuccess);
		return "modules/front/resetPasswordResult";
	}
}
