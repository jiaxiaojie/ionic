package com.thinkgem.jeesite.modules.project.service.assignment;

import java.util.Date;
import java.util.Map;

import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;

/**
 * 【债权转让成功】服务
 * <p/>
 * 本接口服务，提供对单次债权转让操作相关信息的痕迹保留和数据更新等操作
 * <p/>
 * @author Arthur.Xie
 * 2015-07-28
 */
public interface IAssignmentService {
	/**
	 * 债权是否可转让
	 * <1>.非正常状态不允许转让
	 * <2>.距下一个还款日期 N 天内的不允许转让
	 * <3>.事先约定不能转让的项目，债权不允许转让
	 * <4>.债权持有时间少于约定时间的，不允许转让
	 * @param projectInfo
	 * @param investmentRecord
	 * @return
	 */
	public boolean isAssignable(ProjectBaseInfo projectInfo, ProjectInvestmentRecord investmentRecord);
	
	/**
	 * 申请转让：生成债权转让项目
	 * <1>.债权是否可转让 ? 【下一步】：【返回】
	 * <2>.生成债权转让合同
	 * @param projectInfo
	 * @param investmentRecord
	 */
	public void applyAssign(ProjectBaseInfo projectInfo, ProjectInvestmentRecord investmentRecord);
	
	/**
	 * 债权转让前置服务
	 * <p/>
	 * 
	 * 一、参数校验
	 * ==================================
	 * <1>.项目校验：
	 * 	   if (不可转让) {抛出异常，终止操作};
	 * <2>.投资券校验：
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
	 * <1>.生成转让部分的投资记录
	 * <2>.生成转让剩余部分的投资记录
	 * <3>.更新原投资记录的状态为【已转让】
	 * <4>.生成转让的两条投资记录对应的的还款计划，并将原标的的待还款计划状态更新为【已转让】
	 * <5>.更新投资券状态
	 * <6>.更新平台垫付金额
	 * <7>.新增【平台垫付金额】使用记录
	 * <8>.更新余额
	 * <9>.新增余额变更记录
	 * <10>.更新进度处理
	 */
	public void beforeAssign(ProjectTransferInfo projectTransferInfo, String opTerm, String requestNo, Long accountId, String ticketIds, Double amount, Double ticketAmount, Double platformAmount, String rateTicketIds, Date beginInterestDate);
	
	/**
	 * 债权转让服务
	 * 调用资金托管方债权转让转账授权接口，提交债权转让请求
	 * @param accountId					账户Id
	 * @param projectId                 项目Id
	 * @param totalAssignment			债权转让总额
	 * @param ticketAmount				债权转让券金额
	 * @param ticketIds					债权转让券Ids，多个Id以半角逗号间隔
	 * @param platformAmount            平台抵扣金额
	 * @return
	 */
	public Map<String, Object> assign(Long accountId, Long projectId, Double totalAssignment, Double ticketAmount, String ticketIds, Double platformAmount);
	
	/**
	 * 债权转让后置服务
	 * <1>.债权转让成功处理
	 * <2>.债权转让失败处理
	 * @param projectTransferInfo
	 * @param recordAssignment
	 * @param recordRemaining
	 * @param isSuccess
	 */
	public void afterAssign(ProjectTransferInfo projectTransferInfo, ProjectInvestmentRecord recordAssignment, ProjectInvestmentRecord recordRemaining, boolean isSuccess);
}
