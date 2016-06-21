/**
 * 
 */
package com.thinkgem.jeesite.modules.yeepay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.autoTranscation.AutoTransactionNotify;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTransactionNotify;
import com.thinkgem.jeesite.common.yeepay.toAuthorizeAutoRepayment.ToAuthorizeAutoRepaymentNotify;
import com.thinkgem.jeesite.common.yeepay.toAuthorizeAutoTransfer.ToAuthorizeAutoTransferNotify;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionNotify;
import com.thinkgem.jeesite.common.yeepay.toEnterpriseRegister.ToEnterpriseRegisterNotify;
import com.thinkgem.jeesite.common.yeepay.toRecharge.ToRechargeNotify;
import com.thinkgem.jeesite.common.yeepay.toRegister.ToRegisterNotify;
import com.thinkgem.jeesite.common.yeepay.toResetMobile.ToResetMobileNotify;
import com.thinkgem.jeesite.common.yeepay.toWithdraw.ToWithdrawNotify;

/**
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/notify")
public class YeepayNotifyController {
	// 2.1. 注册
	@RequestMapping(value = { "toRegister", "" })
	public String toRegister(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("toRegister notify="+notify+" sign="+sign +" verify ok");
			ToRegisterNotify bean=JaxbMapper.fromXml(notify, ToRegisterNotify.class);
			System.out.println("toRegister bean is ="+bean.getCode());
		} else {
			System.out.println("toRegister notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

	// 2.2 充值
	@RequestMapping(value = { "toRecharge", "" })
	public String toRecharge(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("toRecharge notify="+notify+" sign="+sign +" verify ok");
			ToRechargeNotify bean=JaxbMapper.fromXml(notify, ToRechargeNotify.class);
			System.out.println("toRecharge bean is ="+bean.getCode());
		} else {
			System.out.println("toRecharge notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

	// 2.3提现
	@RequestMapping(value = { "toWithdraw", "" })
	public String toWithdraw(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("toWithdraw notify="+notify+" sign="+sign +" verify ok");
			ToWithdrawNotify bean=JaxbMapper.fromXml(notify, ToWithdrawNotify.class);
			System.out.println("toWithdraw bean is ="+bean.getCode());
		} else {
			System.out.println("toWithdraw notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

	// 2.4. 绑卡
	@RequestMapping(value = { "toBindBankCard", "" })
	public String toBindBankCard(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("toBindBankCard notify="+notify+" sign="+sign +" verify ok");
		} else {
			System.out.println("toBindBankCard notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

//	// 2.5. 取消绑卡
//	@RequestMapping(value = { "toUnbindBankCard", "" })
//	public String toUnbindBankCard(HttpServletRequest request,
//			HttpServletResponse response, Model model) {
//		String notify = request.getParameter("notify");
//		String sign = request.getParameter("sign");
//		if (SignUtil.verifySign(notify, sign)) {
//			System.out.println("toRegister notify="+notify+" sign="+sign +" verify ok");
//			ToUnbindBankCardNotify bean=XStreamHandle.toBean(notify, ToUnbindBankCardNotify.class);
//		} else {
//			System.out.println("toRegister notify="+notify+" sign="+sign +" verify err");
//		}
//		return "";
//	}

	// 2.6. 企业用户注册
	@RequestMapping(value = { "toEnterpriseRegister", "" })
	public String toEnterpriseRegister(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("toEnterpriseRegister notify="+notify+" sign="+sign +" verify ok");
			ToEnterpriseRegisterNotify bean=JaxbMapper.fromXml(notify, ToEnterpriseRegisterNotify.class);
			System.out.println("toEnterpriseRegister bean is "+bean.getCode());
		} else {
			System.out.println("toEnterpriseRegister notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

	// 2.7转账授权
	@RequestMapping(value = { "toCpTransaction", "" })
	public String toCpTransaction(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("toCpTransaction notify="+notify+" sign="+sign +" verify ok");
			ToCpTransactionNotify bean=JaxbMapper.fromXml(notify, ToCpTransactionNotify.class);
			System.out.println("toCpTransaction bean is "+bean.getCode());
		} else {
			System.out.println("toCpTransaction notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

	// 2.8. 自动投标授权
	@RequestMapping(value = { "toAuthorizeAutoTransfer", "" })
	public String toAuthorizeAutoTransfer(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("toAuthorizeAutoTransfer notify="+notify+" sign="+sign +" verify ok");
			ToAuthorizeAutoTransferNotify bean=JaxbMapper.fromXml(notify, ToAuthorizeAutoTransferNotify.class);
			System.out.println("toAuthorizeAutoTransfer bean is "+bean.getCode());
		} else {
			System.out.println("toAuthorizeAutoTransfer notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

	// 2.9. 自动还款授权
	@RequestMapping(value = { "toAuthorizeAutoRepayment", "" })
	public String toAuthorizeAutoRepayment(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("toAuthorizeAutoRepayment notify="+notify+" sign="+sign +" verify ok");
			ToAuthorizeAutoRepaymentNotify bean=JaxbMapper.fromXml(notify, ToAuthorizeAutoRepaymentNotify.class);
			System.out.println("toAuthorizeAutoRepayment bean is "+bean.getCode());
		} else {
			System.out.println("toAuthorizeAutoRepayment notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

//	// 2.10.重置密码
//	@RequestMapping(value = { "toResetPassword", "" })
//	public String toResetPassword(HttpServletRequest request,
//			HttpServletResponse response, Model model) {
//		String notify = request.getParameter("notify");
//		String sign = request.getParameter("sign");
//		if (SignUtil.verifySign(notify, sign)) {
//			System.out.println("toRegister notify="+notify+" sign="+sign +" verify ok");
//			ToResetPasswordNotify bean=XStreamHandle.toBean(notify, ToResetPasswordNotify.class);
//		} else {
//			System.out.println("toRegister notify="+notify+" sign="+sign +" verify err");
//		}
//		return "";
//	}

	// 2.11.修改手机号
	@RequestMapping(value = { "toResetMobile", "" })
	public String toResetMobile(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("toResetMobile notify="+notify+" sign="+sign +" verify ok");
			ToResetMobileNotify bean=JaxbMapper.fromXml(notify, ToResetMobileNotify.class);
			System.out.println("toResetMobile bean is "+bean.getCode());
		} else {
			System.out.println("toResetMobile notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

	// 3.4. 直接转账
	@RequestMapping(value = { "directTransaction", "" })
	public String directTransaction(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("directTransaction notify="+notify+" sign="+sign +" verify ok");
			DirectTransactionNotify bean=JaxbMapper.fromXml(notify, DirectTransactionNotify.class);
			System.out.println("directTransaction bean is "+bean.getCode());
		} else {
			System.out.println("directTransaction notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

	// 3.5. 自动转账授权
	@RequestMapping(value = { "autoTransaction", "" })
	public String autoTransaction(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("autoTransaction notify="+notify+" sign="+sign +" verify ok");
			AutoTransactionNotify bean=JaxbMapper.fromXml(notify, AutoTransactionNotify.class);
			System.out.println("autoTransaction bean is "+bean.getCode());
		} else {
			System.out.println("autoTransaction notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

	// 3.7. 转账确认
	@RequestMapping(value = { "completeTransaction", "" })
	public String completeTransaction(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("completeTransaction notify="+notify+" sign="+sign +" verify ok");
		} else {
			System.out.println("completeTransaction notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}

//	// 3.10.代扣充值
//	@RequestMapping(value = { "whdebitnocardRecharge", "" })
//	public String whdebitnocardRecharge(HttpServletRequest request,
//			HttpServletResponse response, Model model) {
//		String notify = request.getParameter("notify");
//		String sign = request.getParameter("sign");
//		if (SignUtil.verifySign(notify, sign)) {
//			System.out.println("toRegister notify="+notify+" sign="+sign +" verify ok");
//		} else {
//			System.out.println("toRegister notify="+notify+" sign="+sign +" verify err");
//		}
//		return "";
//	}

	// 3.11.平台信息值
	@RequestMapping(value = { "platformInfo", "" })
	public String platformInfo(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		if (SignUtil.verifySign(notify, sign)) {
			System.out.println("platformInfo notify="+notify+" sign="+sign +" verify ok");
		} else {
			System.out.println("platformInfo notify="+notify+" sign="+sign +" verify err");
		}
		return "";
	}
}
