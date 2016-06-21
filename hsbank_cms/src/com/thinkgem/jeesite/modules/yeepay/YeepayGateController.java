/**
 * 
 */
package com.thinkgem.jeesite.modules.yeepay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.toAuthorizeAutoRepayment.ToAuthorizeAutoRepaymentReq;
import com.thinkgem.jeesite.common.yeepay.toAuthorizeAutoRepayment.ToAuthorizeAutoRepaymentResp;
import com.thinkgem.jeesite.common.yeepay.toAuthorizeAutoTransfer.ToAuthorizeAutoTransferReq;
import com.thinkgem.jeesite.common.yeepay.toBindBankCard.ToBindBankCardReq;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionDetail;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionReq;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionResp;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionTenderExtend;
import com.thinkgem.jeesite.common.yeepay.toEnterpriseRegister.ToEnterpriseRegisterReq;
import com.thinkgem.jeesite.common.yeepay.toRecharge.ToRechargeReq;
import com.thinkgem.jeesite.common.yeepay.toRegister.ToRegisterReq;
import com.thinkgem.jeesite.common.yeepay.toResetMobile.ToResetMobileReq;
import com.thinkgem.jeesite.common.yeepay.toResetPassword.ToResetPasswordReq;
import com.thinkgem.jeesite.common.yeepay.toUnbindBankCard.ToUnbindBankCardReq;
import com.thinkgem.jeesite.common.yeepay.toWithdraw.ToWithdrawReq;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;

/**
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/yeepay")
public class YeepayGateController extends BaseController {
	public static String platformNo = YeepayConstant.YEEPAY_PLATFORM_NO;
	@Autowired
	private LogThirdPartyService logThirdPartyService;
	@Autowired
	private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "index", "" })
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/yeepay/index";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "endResult", "" })
	public String resultEnd(ToResetMobileReq toResetMobileReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String resp=request.getParameter("resp");
		String sign=request.getParameter("sign");
		boolean flag=SignUtil.verifySign(resp,sign);
		logger.error("flag mes is "+flag);
		return "modules/yeepay/endResult";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "notifyResult", "" })
	public String notifyResult(ToResetMobileReq toResetMobileReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		return "modules/yeepay/notifyResult";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toRegister", "" })
	public String toRegister(ToRegisterReq toRegisterReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toRegisterReq.setPlatformNo(platformNo);
		toRegisterReq.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOREGISTER_REQ,
				"test"));
		toRegisterReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toRegisterReq.getRequestNo());
		toRegisterReq
				.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"toRegister");
		return "modules/yeepay/toRegister";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toRecharge", "" })
	public String toRecharge(ToRechargeReq toRechargeReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toRechargeReq.setPlatformNo(platformNo);
		toRechargeReq.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORECHARGE_REQ,
				"test"));
		toRechargeReq.setPlatformUserNo("yangtao");
		toRechargeReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toRechargeReq.getRequestNo());
		toRechargeReq
				.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"toRecharge");

		return "modules/yeepay/toRecharge";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toWithdraw", "" })
	public String toWithdraw(ToWithdrawReq toWithdrawReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toWithdrawReq.setPlatformNo(platformNo);
		toWithdrawReq.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOWITHDRAW_REQ,
				"test"));
		toWithdrawReq.setPlatformUserNo("yangtao");
		toWithdrawReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toWithdrawReq.getRequestNo());
		toWithdrawReq
				.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"toWithdraw");
		return "modules/yeepay/toWithdraw";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toBindBankCard", "" })
	public String toBindBankCard(ToBindBankCardReq toBindBankCardReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toBindBankCardReq.setPlatformNo(platformNo);
		toBindBankCardReq.setPlatformUserNo("yangtao");
		toBindBankCardReq.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_REQ,
				"test"));
		toBindBankCardReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toBindBankCardReq.getRequestNo());
		toBindBankCardReq
				.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"toBindBankCard");
		return "modules/yeepay/toBindBankCard";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toUnbindBankCard", "" })
	public String toUnbindBankCard(ToUnbindBankCardReq toUnbindBankCardReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toUnbindBankCardReq.setPlatformNo(platformNo);
		toUnbindBankCardReq.setPlatformUserNo("yangtao");
		toUnbindBankCardReq.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOUNBINDBANKCARD_REQ,
				"test"));
		toUnbindBankCardReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toUnbindBankCardReq.getRequestNo());
		return "modules/yeepay/toUnbindBankCard";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toCpTransaction", "" })
	public String toCpTransaction(ToCpTransactionReq toCpTransactionReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toCpTransactionReq.setPlatformNo(platformNo);
		toCpTransactionReq.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TENDER_REQ,
				"test"));
		toCpTransactionReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toCpTransactionReq.getRequestNo());
		toCpTransactionReq
				.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"toCpTransaction");
		return "modules/yeepay/toCpTransaction";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toAuthorizeAutoTransfer", "" })
	public String toAuthorizeAutoTransfer(
			ToAuthorizeAutoTransferReq toAuthorizeAutoTransferReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toAuthorizeAutoTransferReq.setPlatformNo(platformNo);
		toAuthorizeAutoTransferReq.setPlatformUserNo("yangtao");
		toAuthorizeAutoTransferReq
				.setRequestNo(YeepayUtils
						.getSequenceNumber(
								YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOTRANSFER_REQ,
								"test"));
		toAuthorizeAutoTransferReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toAuthorizeAutoTransferReq.getRequestNo());
		toAuthorizeAutoTransferReq
				.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"toAuthorizeAutoTransfer");
		return "modules/yeepay/toAuthorizeAutoTransfer";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toEnterpriseRegister", "" })
	public String toEnterpriseRegister(
			ToEnterpriseRegisterReq toEnterpriseRegisterReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toEnterpriseRegisterReq.setPlatformNo(platformNo);
		toEnterpriseRegisterReq.setPlatformUserNo("yangtao");
		toEnterpriseRegisterReq
				.setRequestNo(YeepayUtils
						.getSequenceNumber(
								YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOENTERPRISEREGISTER_REQ,
								"test"));
		toEnterpriseRegisterReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toEnterpriseRegisterReq.getRequestNo());
		toEnterpriseRegisterReq
				.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"toEnterpriseRegister");
		return "modules/yeepay/toEnterpriseRegister";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toAuthorizeAutoRepayment", "" })
	public String toAuthorizeAutoRepayment(
			ToAuthorizeAutoRepaymentReq toAuthorizeAutoRepaymentReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toAuthorizeAutoRepaymentReq.setPlatformNo(platformNo);
		toAuthorizeAutoRepaymentReq.setPlatformUserNo("yangtao");
		toAuthorizeAutoRepaymentReq
				.setRequestNo(YeepayUtils
						.getSequenceNumber(
								YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOREPAYMENT_REQ,
								"test"));
		toAuthorizeAutoRepaymentReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toAuthorizeAutoRepaymentReq.getRequestNo());
		toAuthorizeAutoRepaymentReq
				.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"toAuthorizeAutoRepayment");
		return "modules/yeepay/toAuthorizeAutoRepayment";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toResetPassword", "" })
	public String toResetPassword(ToResetPasswordReq toResetPasswordReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toResetPasswordReq.setPlatformNo(platformNo);
		toResetPasswordReq.setPlatformUserNo("yangtao");
		toResetPasswordReq.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETPASSWORD_REQ,
				"test"));
		toResetPasswordReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toResetPasswordReq.getRequestNo());
		return "modules/yeepay/toResetPassword";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toResetMobile", "" })
	public String toResetMobile(ToResetMobileReq toResetMobileReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toResetMobileReq.setPlatformNo(platformNo);
		toResetMobileReq.setPlatformUserNo("yangtao");
		toResetMobileReq.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETMOBILE_REQ,
				"test"));
		toResetMobileReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toResetMobileReq.getRequestNo());
		toResetMobileReq
				.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"toResetMobile");
		return "modules/yeepay/toResetMobile";
	}

	// 提交确认部分

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toRegister1", "" })
	public String toRegister1(ToRegisterReq toRegisterReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String req = toRegisterReq.toReq();
		String sign = SignUtil.sign(req);
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toRegister");
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toRegister1";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toRecharge1", "" })
	public String toRecharge1(ToRechargeReq toRechargeReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String req = toRechargeReq.toReq();
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toRecharge");
		String sign = SignUtil.sign(req);
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toRecharge1";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toWithdraw1", "" })
	public String toWithdraw1(ToWithdrawReq toWithdrawReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String req = toWithdrawReq.toReq();
		String sign = SignUtil.sign(req);
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toWithdraw");
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toWithdraw1";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toBindBankCard1", "" })
	public String toBindBankCard1(ToBindBankCardReq toBindBankCardReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String req = toBindBankCardReq.toReq();
		String sign = SignUtil.sign(req);
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toBindBankCard");
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toBindBankCard1";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toUnbindBankCard1", "" })
	public String toUnbindBankCard1(ToUnbindBankCardReq toUnbindBankCardReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String req = toUnbindBankCardReq.toReq();
		String sign = SignUtil.sign(req);
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toUnbindBankCard");
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toUnbindBankCard1";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toCpTransaction1", "" })
	public String toCpTransaction1(ToCpTransactionReq toCpTransactionReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String bizType=toCpTransactionReq.getBizType();
		String req="";
		String sign="";
		
		if(bizType.equals("TRANSFER")){
			model.addAttribute("detail", "此转账授权模拟为yangtao用户 给平台0.01元，给固定用户yudetao1元");
			toCpTransactionReq.setPlatformUserNo("yangtao") ;// 出款人平台用户编号
			toCpTransactionReq.setUserType("MEMBER") ;// 出款人用户类型，目前只支持传入 MEMBER
			List<ToCpTransactionDetail> details=new ArrayList<ToCpTransactionDetail>() ;// 资金明细记录
			ToCpTransactionDetail d1=new ToCpTransactionDetail();
			d1.setAmount("0.01");
			d1.setTargetUserType("MERCHANT");
			d1.setTargetPlatformUserNo(platformNo);
			d1.setBizType("COMMISSION");
			ToCpTransactionDetail d2=new ToCpTransactionDetail();
			d2.setAmount("1.00");
			d2.setTargetUserType("MEMBER");
			d2.setTargetPlatformUserNo("yudetao");
			d2.setBizType("TRANSFER");
			details.add(d1);
			details.add(d2);
			toCpTransactionReq.setDetail(details);
			req=toCpTransactionReq.toReq();
			sign=SignUtil.sign(req);
			
		}else if(bizType.equals("TENDER")){
			model.addAttribute("detail", "投标可以附加项目编号及名称等信息，模拟为yangtao投资编号为A00001的项目10元，其中1元给平台，9元给借款人yudetao");
			toCpTransactionReq.setPlatformUserNo("bf6c219") ;// 出款人平台用户编号
			toCpTransactionReq.setUserType("MEMBER") ;// 出款人用户类型，目前只支持传入 MEMBER
			List<ToCpTransactionDetail> details=new ArrayList<ToCpTransactionDetail>() ;// 资金明细记录
			
			String expireDate= DateFormatUtils.format( DateUtils.addMinutes(new Date(), 1), "yyyy-MM-dd HH:mm:ss");
			toCpTransactionReq.setExpired(expireDate);
			System.out.println("expireDate is "+expireDate);
			ToCpTransactionDetail d1=new ToCpTransactionDetail();
			d1.setAmount("0.05");
			d1.setTargetUserType("MERCHANT");
			d1.setTargetPlatformUserNo(platformNo);
			d1.setBizType("COMMISSION");
			ToCpTransactionDetail d2=new ToCpTransactionDetail();
			d2.setAmount("0.05");
			d2.setTargetUserType("MEMBER");
			d2.setTargetPlatformUserNo("yudetao");
			d2.setBizType("TRANSFER");
			details.add(d1);
			details.add(d2);
			toCpTransactionReq.setDetail(details);
			ToCpTransactionTenderExtend extend=new ToCpTransactionTenderExtend();
			extend.setBorrowerPlatformUserNo("yudetao");
			extend.setTenderAmount("10000");
			extend.setTenderDescription("平台测试使用");
			extend.setTenderName("A1000的貌似是个手机型号");
			extend.setTenderOrderNo("A1000");
			extend.setTenderSumLimit("10000");
			String extString=extend.toXml();
			System.out.println(extString);
			toCpTransactionReq.setExtend(extend.toList());
			req=toCpTransactionReq.toReq();
			sign=SignUtil.sign(req);
		}
//		req="<?xml version=\"1.0\" encoding=\"UTF-8\"?><request platformNo=\"10012467598\"><requestNo>9d1790a0eb654e4480cc4b4bdaf281ad200700100000000test</requestNo><platformUserNo>yangtao</platformUserNo><userType>MEMBER</userType><bizType>TENDER</bizType><expired></expired><details><detail><amount>1.00</amount><targetUserType>MERCHANT</targetUserType><targetPlatformUserNo>10012467598</targetPlatformUserNo><bizType>COMMISSION</bizType></detail><detail><amount>9.00</amount><targetPlatformUserNo>yudetao</targetPlatformUserNo><targetUserType>MEMBER</targetUserType><bizType>TRANSFER</bizType></detail></details><extend><property name=\"tenderOrderNo\" value=\"A1000\"/><property name=\"tenderName\" value=\"A1000的貌似是个手机型号\"/><property name=\"tenderAmount\" value=\"10000\"/><property name=\"tenderDescription\" value=\"平台测试使用\"/><property name=\"borrowerPlatformUserNo\" value=\"yangtao\"/></extend><notifyUrl>http://101.231.120.5:8080/fuding_p2p/a/notify/toCpTransaction</notifyUrl><callbackUrl>YeepayConstant.YEEPAY_CALLBACK_URL_PREFIXrequestNo=9d1790a0eb654e4480cc4b4bdaf281ad200700100000000test</callbackUrl></request>";
//		sign=SignUtil.sign(req);
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toCpTransaction");
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toCpTransaction1";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toAuthorizeAutoTransfer1", "" })
	public String toAuthorizeAutoTransfer1(
			ToAuthorizeAutoTransferReq toAuthorizeAutoTransferReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String req = toAuthorizeAutoTransferReq.toReq();
		String sign = SignUtil.sign(req);
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toAuthorizeAutoTransfer");
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toAuthorizeAutoTransfer1";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toEnterpriseRegister1", "" })
	public String toEnterpriseRegister1(
			ToEnterpriseRegisterReq toEnterpriseRegisterReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String req = toEnterpriseRegisterReq.toReq();
		String sign = SignUtil.sign(req);
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toEnterpriseRegister");
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toEnterpriseRegister1";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toAuthorizeAutoRepayment1", "" })
	public String toAuthorizeAutoRepayment1(
			ToAuthorizeAutoRepaymentReq toAuthorizeAutoRepaymentReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String req = toAuthorizeAutoRepaymentReq.toReq();
		String sign = SignUtil.sign(req);
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toAuthorizeAutoRepayment");
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toAuthorizeAutoRepayment1";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toResetPassword1", "" })
	public String toResetPassword1(ToResetPasswordReq toResetPasswordReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String req = toResetPasswordReq.toReq();
		String sign = SignUtil.sign(req);
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toResetPassword");
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toResetPassword1";
	}

	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toResetMobile1", "" })
	public String toResetMobile1(ToResetMobileReq toResetMobileReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String req = toResetMobileReq.toReq();
		String sign = SignUtil.sign(req);
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toResetMobile");
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toResetMobile1";
	}
	
	//新添加用户转账至平台账号的接口
	
	
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toGetMoney", "" })
	public String toGetMoney(ToCpTransactionReq toCpTransactionReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		toCpTransactionReq.setPlatformNo(platformNo);
		toCpTransactionReq.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TENDER_REQ,
				"test"));
		toCpTransactionReq
				.setCallbackUrl(YeepayConstant.YEEPAY_CALLBACK_URL_PREFIX+"requestNo="
						+ toCpTransactionReq.getRequestNo());
		toCpTransactionReq
				.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"toCpTransaction");
		return "modules/yeepay/toGetMoney";
	}
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toGetMoney1", "" })
	public String toGetMoney1(ToCpTransactionReq toCpTransactionReq,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String amount=request.getParameter("amount");;
//		String platformUserNo=request.getParameter("platformUserNo");
		//构造转账请求
		List<ToCpTransactionDetail> details=new ArrayList<ToCpTransactionDetail>() ;// 资金明细记录
		ToCpTransactionDetail d1=new ToCpTransactionDetail();
		d1.setAmount(amount);
		d1.setTargetUserType("MERCHANT");
		d1.setTargetPlatformUserNo(platformNo);
		d1.setBizType("TRANSFER");
		details.add(d1);
		toCpTransactionReq.setDetail(details);
		String req = toCpTransactionReq.toReq();
		String sign = SignUtil.sign(req);
		request.setAttribute("yeepayURL", YeepayConstant.YEEPAY_GATE_URL_PREFIX+"toCpTransaction");
		request.setAttribute("req", req);
		request.setAttribute("sign", sign);
		return "modules/yeepay/toGetMoney1";
	}
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "toGetMoneyCallBack", "" })
	public String toGetMoneyCallBack(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String resp=request.getParameter("resp");
		ToCpTransactionResp bean = JaxbMapper.fromXml(resp, ToCpTransactionResp.class);
		request.setAttribute("result", bean.getCode());
		return "";
	}
	
	/**
	 * 回调后将requestNo作为key "hasReceivedMes"作为value保存到session中
	 * @param request
	 * @param requestNo
	 */
	private void receiveMes(HttpServletRequest request, String requestNo) {
		HttpSession session = request.getSession();
		if(!"hasReceivedMes".equals(session.getAttribute(requestNo))) {
			session.setAttribute(requestNo, "hasReceivedMes");
		}
	}
	
	/**
	 * 活期产品：授权自动还款易宝callback操作
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toAuthorizeCurrentAutoRepayment")
	public String toAuthorizeAutoRepayment(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToAuthorizeAutoRepaymentResp bean=JaxbMapper.fromXml(resp, ToAuthorizeAutoRepaymentResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);

			if("1".equals(respCode)) {
				receiveMes(request, requestNo);
			}
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			return "modules/current/currentAutoRepaymentResult";
		}
		return "ERROR";
	}
	
	/**
	 * 活期产品-投资易宝callback操作
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toBackstageCurrentCpTransactionTender")
	public String toCurrentCpTransactionTender(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToCpTransactionResp bean = JaxbMapper.fromXml(resp, ToCpTransactionResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			String description = bean.getDescription();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			CurrentAccountPrincipalChangeHis cHis = currentAccountPrincipalChangeHisService.getByThirdPartyOrder(requestNo, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT);
			//投资记录
			model.addAttribute("cHis", cHis);
			CurrentProjectInfo cInfo = currentProjectInfoService.get(String.valueOf(cHis.getProjectId()));
			//项目信息
			model.addAttribute("cInfo", cInfo);
			if("1".equals(respCode)) {
				receiveMes(request, requestNo);
			}
			model.addAttribute("description", description);
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			return "modules/current/currentProjectBuyResult";
		}
		return "ERROR";
	}
}
