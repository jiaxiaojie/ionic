/**
 * 
 */
package com.thinkgem.jeesite.modules.yeepay;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.yeepay.DirectReqUtils;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoReq;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.common.yeepay.autoTranscation.AutoTranscationReq;
import com.thinkgem.jeesite.common.yeepay.cancelAuthorizeAutoRepayment.CancelAuthorizeAutoRepaymentReq;
import com.thinkgem.jeesite.common.yeepay.cancelAuthorizeAutoTransfer.CancelAuthorizeAutoTransferReq;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTranscationReq;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTranscationReq;
import com.thinkgem.jeesite.common.yeepay.directTranscation.MoneyDetail;
import com.thinkgem.jeesite.common.yeepay.freeze.FreezeReq;
import com.thinkgem.jeesite.common.yeepay.platformInfo.PlatformInfoReq;
import com.thinkgem.jeesite.common.yeepay.projectQuery.ProjectQueryReq;
import com.thinkgem.jeesite.common.yeepay.query.QueryReq;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.UnbindRecordResp;
import com.thinkgem.jeesite.common.yeepay.unfreeze.UnfreezeReq;
import com.thinkgem.jeesite.common.yeepay.whdebitnocardRecharge.WhdebitnocardRechargeReq;

/**
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/yeepay/direct")
public class YeepayDirectController {
	@Autowired
	private DirectReqUtils directReqUtils;
	
	public static String platformNo = YeepayConstant.YEEPAY_PLATFORM_NO;

	/**
	 * //接口文档3.1章节 账户查询
	 * @param platformUserNo
	 * @return
	 */
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "account_info" })
	@ResponseBody
	public AccountInfoResp accountInfo(String platformUserNo) {
		AccountInfoReq req = new AccountInfoReq();
		req.setPlatformNo(platformNo);
		req.setPlatformUserNo(platformUserNo);
		String resp = directReqUtils.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_REQ ,req.toReq(),
				YeepayConstant.PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_SERVICE);
		return JaxbMapper.fromXml(resp, AccountInfoResp.class);
	}
	
	/**
	 * //接口文档3.2章节 资金冻结
	 * @param platformUserNo
	 * @param amount
	 * @param expired
	 * @return
	 */
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "freeze" })
	@ResponseBody
	public String freeze(String platformUserNo, String amount, String expired) {
		FreezeReq req = new FreezeReq();
		req.setPlatformNo(platformNo);
		req.setPlatformUserNo(platformUserNo);
		req.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_FREEZE_REQ,
				platformUserNo));
		req.setAmount(amount);
		req.setExpired(expired);
		return directReqUtils.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_FREEZE_REQ ,req.toReq(),
				YeepayConstant.PROJECT_INTERFACE_DIRECT_FREEZE_SERVICE);
	}
	/**
	 * //接口文档3.3章节 资金解冻
	 * @param freezeRequestNo
	 * @return
	 */
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "unfreeze" })
	@ResponseBody
	public String unfreeze(String freezeRequestNo) {
		UnfreezeReq req = new UnfreezeReq();
		req.setPlatformNo(platformNo);
		req.setFreezeRequestNo(freezeRequestNo);
		return directReqUtils.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_UNFREEZE_REQ ,req.toReq(),
				YeepayConstant.PROJECT_INTERFACE_DIRECT_UNFREEZE_SERVICE);
	}
	/**
	 * //接口文档3.4章节 直接转账
	 * @param requestNo
	 * @return
	 */
	// 暂未
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "direct_transaction" })
	@ResponseBody
	public String directTransaction(String platformUserNo,String requestNo,String userType,String bizType,String d_amount,String d_targetUserType,String d_targetPlatformUserNo,String d_bizType,String notifyUrl) {
		DirectTranscationReq req = new DirectTranscationReq();
		req.setPlatformNo(platformNo);
		req.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ,
				platformUserNo));
		req.setPlatformUserNo(platformNo);
		req.setPlatformNo(platformNo);
		req.setUserType(userType);
		req.setBizType("TRANSFER");
		if((notifyUrl==null)||(notifyUrl.equals(""))){
			req.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"directTransaction");
		}else{
			req.setNotifyUrl(notifyUrl);
		}
		MoneyDetail md=new MoneyDetail();
		md.setAmount(d_amount);
		md.setBizType(d_bizType);
		md.setTargetPlatformUserNo(d_targetPlatformUserNo);
		md.setTargetUserType(d_targetUserType);
		List<MoneyDetail> list=new ArrayList<MoneyDetail>();
		list.add(md);
		req.setDetail(list);
		return directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ,req.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_SERVICE);
	}
	/**
	 * //接口文档3.5章节 自动转账授权
	 * @param requestNo
	 * @return
	 */
	// 暂未
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "auto_transcation" })
	@ResponseBody
	public String autoTranscation(String platformUserNo,String requestNo,String userType,String bizType,String d_amount,String d_targetUserType,String d_targetPlatformUserNo,String d_bizType,String notifyUrl){
		AutoTranscationReq req = new AutoTranscationReq();
		req.setPlatformNo(platformNo);
		req.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ,
				platformUserNo));
		req.setPlatformUserNo(platformUserNo);
		req.setUserType(userType);
		req.setBizType(bizType);
		if((notifyUrl==null)||(notifyUrl.equals(""))){
			req.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX+"autoTranscation");
		}else{
			req.setNotifyUrl(notifyUrl);
		}
		MoneyDetail md=new MoneyDetail();
		md.setAmount(d_amount);
		md.setBizType(d_bizType);
		md.setTargetPlatformUserNo(d_targetPlatformUserNo);
		md.setTargetUserType(d_targetUserType);
		List<MoneyDetail> list=new ArrayList<MoneyDetail>();
		list.add(md);
		req.setDetail(list);
		return directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_NORMAL_REQ,req.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_SERVICE);
	}
	/**
	 * //接口文档3.6章节 单笔业务查询
	 * @param requestNo
	 * @param mode
	 * @return
	 */
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "query" })
	@ResponseBody
	public String query(String requestNo,String mode) {
		QueryReq req = new QueryReq();
		req.setPlatformNo(platformNo);
		req.setRequestNo(requestNo);
		req.setMode(mode);
		return directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_QUERY_REQ,req.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_QUERY_SERVICE);
	}
	
	/**
	 * 接口文档3.6章节 解绑记录业务查询
	 * @param requestNo
	 * @return
	 */
	public UnbindRecordResp queryUnbindRecord(String requestNo){
		String respStr = query(requestNo,"UNBIND_RECORD");
		UnbindRecordResp unbindRecordResp = JaxbMapper.fromXml(
				respStr, UnbindRecordResp.class);
		return unbindRecordResp;
	}
	
	
	
	/**
	 * //接口文档3.7章节 转账确认
	 * @param requestNo
	 * @param mode
	 * @param notifyUrl
	 * @return
	 */
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "complete_transaction" })
	@ResponseBody
	public String completeTransaction(String requestNo,String mode,String platformUserNo,String notifyUrl) {
		CompleteTranscationReq req = new CompleteTranscationReq();
		req.setPlatformNo(platformNo);
		req.setRequestNo(requestNo);
//		req.setPlatformUserNo(platformUserNo);
		req.setMode(mode);
		req.setNotifyUrl(notifyUrl);
		return directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_REQ,req.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_SERVICE);
	}
	/**
	 * //接口文档3.8章节 取消自动投标授权
	 * @param platformUserNo
	 * @return
	 */
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "cancel_authorize_auto_transfer" })
	@ResponseBody
	public String cancelAuthorizeAutoRransfer(String platformUserNo) {
		CancelAuthorizeAutoTransferReq req = new CancelAuthorizeAutoTransferReq();
		req.setPlatformNo(platformNo);
		req.setPlatformUserNo(platformUserNo);
		req.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_REPAYMENT_REQ,
				platformUserNo));
		return directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_REPAYMENT_REQ,req.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_REPAYMENT_SERVICE);
	}
	/**
	 * //接口文档3.9章节 取消自动还款授权
	 * @param platformUserNo
	 * @param orderNo
	 * @return
	 */
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "cancel_authorize_auto_repayment" })
	@ResponseBody
	public String cancelAuthorizeAutoRepayment(String platformUserNo,String orderNo) {
		CancelAuthorizeAutoRepaymentReq req = new CancelAuthorizeAutoRepaymentReq();
		req.setPlatformNo(platformNo);
		req.setPlatformUserNo(platformUserNo);
		req.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_REPAYMENT_REQ,
				platformUserNo));
		req.setOrderNo(YeepayConstant.YEEPAY_TENDERORDERNO_PREFIX + orderNo);
		return directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_REPAYMENT_REQ,req.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_REPAYMENT_SERVICE);
	}
	/**
	 * //接口文档3.10章节 代扣充值
	 * @param platformUserNo
	 * @param payWay
	 * @param amount
	 * @param feeMode
	 * @param realName
	 * @param idCardNo
	 * @param bankCardNo
	 * @param notifyUrl
	 * @return
	 */
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "whdebitnocard_recharge" })
	@ResponseBody
	public String whdebitnocardRecharge(String platformUserNo,String payWay,String amount,String feeMode,String realName,String idCardNo,String bankCardNo,String notifyUrl) {
		WhdebitnocardRechargeReq req = new WhdebitnocardRechargeReq();
		req.setPlatformNo(platformNo);
		req.setPlatformUserNo(platformUserNo);
		req.setRequestNo(YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_REPAYMENT_REQ,
				platformUserNo));
		req.setPayWay(payWay);
		req.setAmount(amount);
		req.setFeeMode(feeMode);
		req.setRealName(realName);
		req.setIdCardNo(idCardNo);
		req.setBankCardNo(bankCardNo);
		req.setNotifyUrl(notifyUrl);
		return directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_REPAYMENT_REQ,req.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_WHDEBITNOCARD_RECHARGE_SERVICE);
	}
	/**
	 * //接口文档3.11章节 平台信息
	 * @param notifyUrl
	 * @return
	 */
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "platform_info" })
	@ResponseBody
	public String platformInfo(String notifyUrl) {
		PlatformInfoReq req = new PlatformInfoReq();
		req.setPlatformNo(platformNo);
		req.setNotifyUrl(notifyUrl);
		return directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_PLATFORM_INFO_REQ,req.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_PLATFORM_INFO_SERVICE);
	}
	/**
	 * //接口文档3.12章节 项目（标的）查询
	 * @param orderNo
	 * @return
	 */
	@RequiresPermissions("yeepay:test")
	@RequestMapping(value = { "project_query" })
	@ResponseBody
	public String projectQuery(String orderNo) {
		ProjectQueryReq req = new ProjectQueryReq();
		req.setPlatformNo(platformNo);
		req.setOrderNo(YeepayConstant.YEEPAY_TENDERORDERNO_PREFIX + orderNo);
		return directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_PROJECT_QUERY_REQ,req.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_PROJECT_QUERY_SERVICE);
	}
}
