package com.thinkgem.jeesite.modules.front;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.IdcardUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.toRegister.ToRegisterReq;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

/**
 * 账号管理
 * @author ydt
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/customer/thirdAccount")
public class CustomerThirdAccountController extends BaseController {
	//身份证类型为二代身份证
	public static final String ID_CARD_TYPE = "G2_IDCARD";
	
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private LogThirdPartyService logThirdPartyService;
	
	/**
	 * 如果未开通第三方账号，则跳转到开通页面
	 * 否则跳转到账号已开通，无需再次开通提示页面
	 * @param customerAccount
	 * @param model
	 * @return
	 */
	@RequestMapping("open")
	public String operThirdAccount(Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		model.addAttribute("customerAccount", customerAccount);
		if("1".equals(customerAccount.getHasOpenThirdAccount())) {
			return "modules/front/wdzh/openThirdAccountFinishedTips";
		}
		CustomerBase customerBase = customerBaseService.getByAccountId(customerAccount.getAccountId());
		model.addAttribute("customerBase", customerBase);
		return "modules/front/wdzh/openThirdAccount";
	}
	
	/**
	 * 对数据进行签名，并返回给前端用户
	 * 		1.更新customerAccount表requestNo
	 * 		2.插入logThirdParty表注册纪录
	 * @param customerAccount
	 * @param toRegisterReq
	 * @param model
	 * @return
	 */
	@RequestMapping("sign")
	public String sign(ToRegisterReq toRegisterReq, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		//验证是否是18位身份证
		if(!IdcardUtils.validateIdCard18(toRegisterReq.getIdCardNo())) {
			throw new ServiceException("invalidate idCard");
		}
		if(!customerAccountService.isIdCardNoLessThanUseTimesLimit(toRegisterReq.getIdCardNo())) {
			throw new ServiceException("身份证号码超过最大使用次数");
		}
		String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOREGISTER_REQ, customerAccount.getPlatformUserNo());
		toRegisterReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		toRegisterReq.setIdCardType(ID_CARD_TYPE);
		toRegisterReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
		toRegisterReq.setRequestNo(requestNo);
		toRegisterReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX + "toRegister?requestNo=" + requestNo);
		toRegisterReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toRegister");
		
		String req = toRegisterReq.toReq();
		String sign = SignUtil.sign(req);
		model.addAttribute("toRegisterReq", toRegisterReq);
		model.addAttribute("req", req);
		model.addAttribute("sign", sign);
		model.addAttribute("registerUrl", YeepayConstant.YEEPAY_GATE_URL_PREFIX + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOREGISTER_ACTION);
		
		customerAccount.setRequestNo(requestNo);
		customerAccountService.updateRequestNo(customerAccount);
		CustomerBase customerBase = new CustomerBase();
		customerBase.setAccountId(customerAccount.getAccountId());
		customerBase.setCustomerName(toRegisterReq.getRealName());
		customerBase.setCertNum(toRegisterReq.getIdCardNo());
		customerBaseService.updateNameAndCertNum(customerBase);
		
		logThirdPartyService.insertToRegisterReq(requestNo, req);

		model.addAttribute("menu", "wdzh");
		return "modules/front/wdzh/openThirdAccountConfirm";
	}

	@RequestMapping("isIdCardNoLessThanUseTimesLimit")
	@ResponseBody
	public boolean isIdCardNoLessThanUseTimesLimit(String idCardNo) {
		return customerAccountService.isIdCardNoLessThanUseTimesLimit(idCardNo);
	}
	
}
