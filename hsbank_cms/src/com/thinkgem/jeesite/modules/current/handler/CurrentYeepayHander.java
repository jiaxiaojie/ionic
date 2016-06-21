package com.thinkgem.jeesite.modules.current.handler;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionDetail;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionReq;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionTenderExtend;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.hsbank.util.type.DatetimeUtil;
import com.hsbank.util.constant.DatetimeField;

public class CurrentYeepayHander {
	
	
	/**
	 * 生成易宝转账授权接口报文：购买活期产品
	 * @param projectName
	 * @param projectId
	 * @param operaTerm
	 * @param projectCode
	 * @param requestNo
	 * @param platformUserNo
	 * @param borrowerPlatformUserNo
	 * @param amount
	 * @param serviceCharge
	 * @param financingAmount
	 * @return
	 */
	public static String generationCurrentXml_for_tender(String projectName, String projectId,String operaTerm, String projectCode, 
			String requestNo, String platformUserNo, String borrowerPlatformUserNo, Double amount, 
			Double serviceCharge, Double financingAmount){
		ToCpTransactionReq toCpTransactionReq = getCurrentToCpTransactionReq(YeepayConstant.BIZ_TYPE_TENDER, operaTerm,
			requestNo, platformUserNo, borrowerPlatformUserNo, serviceCharge, financingAmount);
		//扩展信息
		ToCpTransactionTenderExtend extend = new ToCpTransactionTenderExtend();
		extend.setBorrowerPlatformUserNo(borrowerPlatformUserNo);
		extend.setTenderAmount(String.valueOf(amount));
		extend.setTenderDescription(projectCode);
		extend.setTenderName(projectName);
		extend.setTenderOrderNo(YeepayConstant.YEEPAY_CURRENT_TENDERORDERNO_PREFIX + projectId);
		extend.setTenderSumLimit(String.valueOf(amount));
		String extString = extend.toXml();
		System.out.println(extString);
		toCpTransactionReq.setExtend(extend.toList());
		return toCpTransactionReq.toReq();
	}
	
	
	/**
	 * 组织报文：活期产品
	 * @param bizType
	 * @param operaTerm
	 * @param requestNo
	 * @param platformUserNo
	 * @param borrowerPlatformUserNo
	 * @param serviceCharge
	 * @param financingAmount
	 * @return
	 */
	private static ToCpTransactionReq getCurrentToCpTransactionReq(String bizType, String operaTerm, String requestNo, String platformUserNo, 
			String borrowerPlatformUserNo, Double serviceCharge, Double financingAmount) {
		ToCpTransactionReq toCpTransactionReq = new ToCpTransactionReq();
		// 商户编号
		toCpTransactionReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		// 请求流水号
		toCpTransactionReq.setRequestNo(requestNo);
		// 页面回跳 URL
		if(ProjectConstant.OP_TERM_DICT_PC.equals(operaTerm)){
			toCpTransactionReq.setCallbackUrl(YeepayConstant.YEEPAY_BACKSTAGE_CALLBACK_URL_PREFIX + "toBackstageCurrentCpTransactionTender?requestNo=" + toCpTransactionReq.getRequestNo());
		}else{
			toCpTransactionReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX + "toCpTransactionTender?requestNo=" + toCpTransactionReq.getRequestNo());
		}
		// 服务器通知 URL
		toCpTransactionReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toCurrentCpTransaction");
		// 出款人平台用户编号
		toCpTransactionReq.setPlatformUserNo(platformUserNo);
		// 出款人用户类型，目前只支持传入 MEMBER
		toCpTransactionReq.setUserType("MEMBER");
		toCpTransactionReq.setBizType(bizType);
		//10分钟后超时
		String expireDate = DatetimeUtil.datetimeToString(DatetimeUtil.getDate(DatetimeField.MINUTE, 10));
		toCpTransactionReq.setExpired(expireDate);
		// 资金明细记录
		List<ToCpTransactionDetail> details=new ArrayList<ToCpTransactionDetail>() ;
		//平台提成
		ToCpTransactionDetail platFormDetail = new ToCpTransactionDetail();
		//计算平台服务费
		platFormDetail.setAmount(String.valueOf(serviceCharge));
		platFormDetail.setTargetUserType("MERCHANT");
		platFormDetail.setTargetPlatformUserNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		platFormDetail.setBizType("COMMISSION");
		//融资方收款
		ToCpTransactionDetail financingDetail = new ToCpTransactionDetail();
		financingDetail.setAmount(String.valueOf(LoanUtil.formatAmount(financingAmount)));
		financingDetail.setTargetUserType("MEMBER");
		financingDetail.setTargetPlatformUserNo(borrowerPlatformUserNo);
		financingDetail.setBizType(bizType);
		details.add(platFormDetail);
		details.add(financingDetail);
		toCpTransactionReq.setDetail(details);
		return toCpTransactionReq;
	}
	
	
}
