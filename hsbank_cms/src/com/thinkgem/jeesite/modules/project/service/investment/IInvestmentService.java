package com.thinkgem.jeesite.modules.project.service.investment;

import java.util.Date;
import java.util.Map;

import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;

/**
 * 【投资成功】服务
 * <p/>
 * 本接口，提供对单次投资操作相关信息的参数校验、数据入库等操作
 * <p/>
 * @author Arthur.Xie
 * 2015-07-28
 */
public interface IInvestmentService {
	
	/**
	 * 投资前置服务
	 * <p/>
	 * 
	 * 一、参数校验
	 * ==================================
	 * <1>.投资券校验：
	 * 	   ------------------------------
	 * 	   对每一张券都要做如下检查
	 *     a.券是不是自己的
	 *     b.券状态是不是可用状态
	 *     任何一条没有满足，都要抛出异常，终止操作
	 *     ------------------------------
	 * <2>.平台垫付金额校验
	 * 	   ------------------------------
	 * 	   if(【本次使用的平台垫付金额】> 0) {
	 *     		if(【本次使用的平台垫付金额】>【当前用户总的平台垫付金额】) {
	 *     			抛出异常，终止操作
	 *     		}
	 *     }
	 *     ------------------------------
	 * <3>.余额校验
	 * 	   a.计算实际投资额：投资总金额 - 用券金额 - 平台垫付金额
	 * 	   b.if(【实际投资额】>【当前用户余额】) {抛出异常;} 
	 * 
	 * 二、数据入库
	 * ==================================
	 * <1>.生成投资记录
	 * <2>.生成投资记录对应的还款计划
	 * <3>.更新当前用户的投资券状态、使用日期时间、备注（投资记录Id）
	 * <4>.更新当前用户的【平台垫付金额】：【当前用户总的平台垫付金额】-=【本次使用的平台垫付金额】
	 * <5>.新增当前用户的【平台垫付金额】使用记录
	 * <6>.更新余额
	 * <7>.新增余额变更记录
	 * <8>.更新项目进度
	 * @param accountId					用户账户Id
	 * @param projectId					项目Id
	 * @param ticketIds					投资券的id，多张券以","分割
	 * @param platformAmount			平台垫付金额
	 * @param payAmount					实际支付金额
	 */
	public void beforeInvest(ProjectBaseInfo projectInfo, String opTerm, String requestNo, Long accountId, String ticketIds, Double amount, Double ticketAmount, Double platformAmount, String rateTicketIds, Date beginInterestDate);
	
	/**
	 * 投资服务
	 * 调用资金托管方投资转账授权接口，提交投资请求
	 * @param accountId					账户Id
	 * @param projectId                 项目Id
	 * @param totalInvestment			投资总额
	 * @param ticketAmount				投资券金额
	 * @param ticketIds					投资券Ids，多个Id以半角逗号间隔
	 * @param platformAmount            平台抵扣金额
	 * @return
	 */
	public Map<String, Object> invest(Long accountId, Long projectId, Double totalInvestment, Double ticketAmount, String ticketIds, Double platformAmount);
	
	
	/**
	 * 投资后置服务
	 * <1>.投资成功处理
	 * <2>.投资失败处理
	 * @param projectInfo
	 * @param investmentRecord
	 * @param result
	 */
	public void afterInvest(ProjectBaseInfo projectInfo, ProjectInvestmentRecord investmentRecord, boolean result);
}
