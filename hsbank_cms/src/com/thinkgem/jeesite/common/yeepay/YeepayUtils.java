package com.thinkgem.jeesite.common.yeepay;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionCreditAssignmentExtend;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionDetail;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionReq;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionTenderExtend;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.hsbank.util.type.DatetimeUtil;
import com.hsbank.util.constant.DatetimeField;

public class YeepayUtils {
	public static void main(String[] args){
		System.out.println(getSequenceNumber("2001001","9090").length());
		System.out.println(getSequenceNumber("222999","9090"));
	}
	
	/**
	 * 生成交易流水号
	 * @return
	 */
	public static String getSequenceNumber(String busiCode,String userId) {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		StringBuffer sb = new StringBuffer().append(str.substring(0, 8)).append(str.substring(9, 13))
				.append(str.substring(14, 18)).append(str.substring(19, 23)).append(str.substring(24));
		sb.append(busiCode).append(StringUtils.fixLength(userId,11));
		return sb.toString();
	}
	
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
			toCpTransactionReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX + "toCurrentCpTransactionTender?requestNo=" + toCpTransactionReq.getRequestNo());
		}else{
			toCpTransactionReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX + "toCurrentCpTransactionTender?requestNo=" + toCpTransactionReq.getRequestNo());
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
	
	/**
	 * 生成易宝转账授权接口报文：投标
	 * @param projectName					项目名称
	 * @param projectCode					项目编号
	 * @param projectIntroduce				项目介绍
	 * @param requestNo						请求流水号
	 * @param platformUserNo				债权出让人
	 * @param borrowerPlatformUserNo		借款人平台用户编号
	 * @param amount						项目总额
	 * @param serviceCharge					本次投资的服务费
	 * @param financingAmount				本次投资扣除服务费后的金额
	 * @return
	 */
	public static String generationXml_for_tender(String projectName, String projectId,String operaTerm, String projectCode, 
			String requestNo, String platformUserNo, String borrowerPlatformUserNo, Double amount, 
			Double serviceCharge, Double financingAmount){
		ToCpTransactionReq toCpTransactionReq = getToCpTransactionReq(YeepayConstant.BIZ_TYPE_TENDER, operaTerm,
			requestNo, platformUserNo, borrowerPlatformUserNo, serviceCharge, financingAmount);
		//扩展信息
		ToCpTransactionTenderExtend extend = new ToCpTransactionTenderExtend();
		extend.setBorrowerPlatformUserNo(borrowerPlatformUserNo);
		extend.setTenderAmount(String.valueOf(amount));
		extend.setTenderDescription(projectCode);
		extend.setTenderName(projectName);
		extend.setTenderOrderNo(YeepayConstant.YEEPAY_TENDERORDERNO_PREFIX + projectId);
		extend.setTenderSumLimit(String.valueOf(amount));
		String extString = extend.toXml();
		System.out.println(extString);
		toCpTransactionReq.setExtend(extend.toList());
		return toCpTransactionReq.toReq();
	}
	
	/**
	 * 生成易宝转账授权接口报文：债权转让
	 * @param projectCode					项目编号
	 * @param requestNo						请求流水号
	 * @param platformUserNo				债权出让人
	 * @param oldPlatformUserNo				原债权出让人
	 * @param borrowerPlatformUserNo		借款人平台用户编号
	 * @param serviceCharge					本次投资的服务费
	 * @param financingAmount				本次投资扣除服务费后的金额
	 * @return
	 */
	public static String generationXml_for_assignment(String projectId, String operaTerm, String requestNo, String platformUserNo, String oldPlatformUserNo,
			String borrowerPlatformUserNo, Double serviceCharge, Double financingAmount, String thirdPartyOrder){
		ToCpTransactionReq toCpTransactionReq = getToCpTransactionReq(YeepayConstant.BIZ_TYPE_CREDIT_ASSIGNMENT,operaTerm, 
			requestNo, platformUserNo, oldPlatformUserNo, serviceCharge, financingAmount);
		//扩展信息
		ToCpTransactionCreditAssignmentExtend extend = new ToCpTransactionCreditAssignmentExtend();
		extend.setTenderOrderNo(YeepayConstant.YEEPAY_TENDERORDERNO_PREFIX + projectId);
		extend.setOriginalRequestNo(thirdPartyOrder);
		extend.setCreditorPlatformUserNo(oldPlatformUserNo);
		String extString = extend.toXml();
		System.out.println(extString);
		toCpTransactionReq.setExtend(extend.toList());
		return toCpTransactionReq.toReq();
	}
	
	/**
	 * 得到易宝转账授权接口的公共部分
	 * @param bizType							业务类型
	 * @param requestNo							请求流水号
	 * @param platformUserNo					出款人平台用户编号
	 * @param borrowerPlatformUserNo			借款人平台用户编号
	 * @param serviceCharge						本次投资的服务费
	 * @param financingAmount					本次投资扣除服务费后的金额
	 * @return
	 */
	private static ToCpTransactionReq getToCpTransactionReq(String bizType, String operaTerm, String requestNo, String platformUserNo, 
			String borrowerPlatformUserNo, Double serviceCharge, Double financingAmount) {
		ToCpTransactionReq toCpTransactionReq = new ToCpTransactionReq();
		// 商户编号
		toCpTransactionReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		// 请求流水号
		toCpTransactionReq.setRequestNo(requestNo);
		// 页面回跳 URL
		if(ProjectConstant.OP_TERM_DICT_PC.equals(operaTerm)){
			if(YeepayConstant.BIZ_TYPE_TENDER.equals(bizType)){
				toCpTransactionReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX + "toCpTransactionTender?requestNo=" + toCpTransactionReq.getRequestNo());
			}else if(YeepayConstant.BIZ_TYPE_CREDIT_ASSIGNMENT.equals(bizType)){
				toCpTransactionReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX + "toCpTransactionAssignment?requestNo=" + toCpTransactionReq.getRequestNo());
			}
		}else{
			if(YeepayConstant.BIZ_TYPE_TENDER.equals(bizType)){
				toCpTransactionReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX + "toCpTransactionTender?requestNo=" + toCpTransactionReq.getRequestNo());
			}else if(YeepayConstant.BIZ_TYPE_CREDIT_ASSIGNMENT.equals(bizType)){
				toCpTransactionReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX + "toCpTransactionAssignment?requestNo=" + toCpTransactionReq.getRequestNo());
			}
		}
		// 服务器通知 URL
		toCpTransactionReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toCpTransaction");
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
