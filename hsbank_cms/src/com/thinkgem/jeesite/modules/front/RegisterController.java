package com.thinkgem.jeesite.modules.front;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.RequstUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilterFront;
import com.thinkgem.jeesite.modules.sys.security.UsernamePasswordTokenFront;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

@Controller
@RequestMapping(value = "${frontPath}/register")
public class RegisterController extends BaseController {
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private MarketFacadeService marketFacadeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String register(User user, Model model) {
		return "modules/front/register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registerSubmit(String mobile, String password, String recommendMobile, String smsCode, HttpServletRequest request, String validateCode, Model model, RedirectAttributes redirectAttributes) {
		boolean smsCodeAuth = SmsCodeController.auth(mobile, smsCode);
		if(smsCodeAuth && ValidateCodeServlet.validate(request, validateCode)) {
			//获得用户渠道Cookie
			Cookie channelCookie = RequstUtils.getCookieByName(request, "channel");
			String channel = channelCookie==null?null:channelCookie.getValue();
			
			String accountName = customerAccountService.registerWithMobileAndReturnAccountName(request, ProjectConstant.OP_TERM_DICT_PC, mobile, password, recommendMobile, channel, null,"","");
			if(StringUtils.isNotBlank(accountName)) {
				CustomerAccount customerAccount = customerAccountService.getByMobile(mobile);
				//调用注册活动
				doRegisterActivity(customerAccount);
				//注册成功自动登录
				Subject subject = SecurityUtils.getSubject();
				String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
				boolean isMobile = WebUtils.isTrue(request, FormAuthenticationFilterFront.DEFAULT_MOBILE_PARAM);
				UsernamePasswordTokenFront token = new UsernamePasswordTokenFront(accountName, password.toCharArray(), false, host, null, isMobile,null,false);  
				token.setRememberMe(false);
				subject.login(token);
				//更新用户登录信息
				systemService.updateCustomerSuccessLoginInfo(request);
				return "redirect:"+Global.getFrontPath()+"/index";
			}
		} else if(!ValidateCodeServlet.validate(request, validateCode)) {
			addMessage(model, "图片验证码输入有误，请重新输入。");
		} else if(!smsCodeAuth){
			addMessage(model, "短信验证码输入有误，请重新输入。");
		} else {
			addMessage(model, "发生了点意外，注册失败，请重新注册。");
		}
		return "modules/front/register";
	}
	
	/**
	 * 调用注册活动
	 * @param username
	 * @return
	 */
	private void doRegisterActivity(CustomerAccount customerAccount) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, customerAccount.getAccountId());
		map.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_REGISTER);
		map.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
		marketFacadeService.register(map);
	}
	
	@RequestMapping(value = "checkUsernameCanUse")
	@ResponseBody
	public boolean checkUsernameCanUse(String username) {
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setAccountName(username);
		return customerAccountService.checkAccountNameCanUse(customerAccount);
	}
	
	@RequestMapping(value = "checkMobileCanUse")
	@ResponseBody
	public boolean checkMobileCanUse(String mobile) {
		CustomerBase customerBase = new CustomerBase();
		customerBase.setMobile(mobile);
		return customerBaseService.checkMobileCanUse(customerBase);
	}
}
