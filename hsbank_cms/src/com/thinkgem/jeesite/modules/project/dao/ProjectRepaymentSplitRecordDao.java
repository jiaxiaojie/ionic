/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;

/**
 * 还款记录查分明细DAO接口
 * 
 * @author yangtao
 * @version 2015-07-13
 */
@MyBatisDao
public interface ProjectRepaymentSplitRecordDao extends CrudDao<ProjectRepaymentSplitRecord> {
	/**
	 * 获得正在进行的项目中，用户已获得投资收益总和
	 * 
	 * @param accountId
	 * @return
	 */
	public Float getSumProfitInRunningProjectByAccountId(Long accountId);

	/**
	 * 获得正在进行的项目中，用户待还资金总和
	 * 
	 * @param accountId
	 * @return
	 */
	public Float getSumLoanInRunningProjectByAccountId(Long accountId);

	/**
	 * 获得用户待还款项目概况
	 * 
	 * @param accountId
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getLoanListInRunningProjectByAccountId(
			Long accountId);
	
	/**
	 * 得到指定投资记录的指定状态的收款列表
	 * @param investmentRecordId
	 * @param status
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord>  getRepaymentListByInvestmentRecord(@Param("investmentRecordId") String investmentRecordId, @Param("status") String status);
	
	/**
	 * 获得用户将收到的资金和已收到的资金清单
	 * @param accountId
	 * @param status
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord>  getRepaymentListByAccountIdAndStatus(@Param("accountId") String accountId, @Param("status") String status, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * 还款信息列表
	 * @param map
	 * @return
     */
	public List<ProjectRepaymentSplitRecord>  getProjectRepaymentInfoList(Map<String, Object> map);

	/**
	 * 根据账号汇总还款本金、利息总额
	 * @param map
	 * @return
     */
	public Map<String,Object> getSumProjectRepaymentInfo(Map<String, Object> map);


	
	/**
	 * 根据投资流水号更新状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateStatusByInvestmentRecordId(@Param("investmentRecordId") String investmentRecordId, @Param("status") String status);
	
	/**
	 * 根据Id更新状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateStatusById(@Param("id") String id, @Param("status") String status);
	
	/**
	 * 获取指定项目指定日期的投资人员列表清单
	 * @param projectId
	 * @param date
	 * @return
	 */
	public List<String> getRepaymentPayeeUserListByProjectAndDate(@Param("projectId") String projectId, @Param("theDate") String theDate);
	/**
	 * 获取指定项目指定日期的投资人员收款列表
	 * @param projectId
	 * @param theDate
	 * @param userId
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getRepaymentListByProjectAndUser(@Param("projectId") String projectId, @Param("userId") String userId);
	
	/**
	 * 获取指定项目、指定收款人、指定投资记录的还款列表
	 * @param projectId
	 * @param accountId
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getRepaymentListByProjectAndccountId(@Param("projectId") String projectId, @Param("accountId") Long accountId, @Param("recordId") Long recordId);
	
	/**
	 * 根据第三方还款记录编号获得还款明细列表
	 * @param recordId
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getRepaymentListByRecordId(@Param("recordId") String recordId, @Param("status") String status);
	
	
	/**
	 * 根据第三方还款记录编号获得还款明细列表
	 * @param recordId
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getRepaymentListBySumRecordId(@Param("recordId") String recordId, @Param("status") String status);
	
	/**
	 * 获得最近上一期的还款记录
	 * @param date
	 * @return
	 */
	public  List<ProjectRepaymentSplitRecord> getNextRepayRecord(@Param("projectId") String projectId, @Param("theDate") Date date);
	/**
	 * 获得最近下一期的还款记录
	 * @param date
	 * @return
	 */
	public  List<ProjectRepaymentSplitRecord> getOldRepayRecord(@Param("projectId") String projectId, @Param("theDate") Date date);
	/**
	 * 获得尚未归还的本金
	 * @param projectId
	 * @param date
	 * @return
	 */
	public Double getWillRepayPrincipal(@Param("projectId") String projectId, @Param("theDate") Date date);
	/**
	 * 获得尚未归还的利息
	 * @param projectId
	 * @param date
	 * @return
	 */
	public Double getWillRepayInterest(@Param("projectId") String projectId, @Param("theDate") Date date);
	
	/**
	 * 获得正常还款当天应还资金情况
	 * @param projectId
	 * @param date
	 * @return
	 */
	public ProjectRepaymentSplitRecord getWillReapyInfoOfTheTerm(@Param("projectId") String projectId, @Param("theDate") String date);
	/**
	 * 获得指定项目指定日期的应还款列表
	 * @param projectId
	 * @param date
	 * @return
	 */
	public  List<ProjectRepaymentSplitRecord>  getRepaymentListByProjectAndDate(@Param("projectId") String projectId, @Param("theDate") String date);
	
	/**
	 * 获得制定项目指定日期之后的未还款数量
	 * @param projectId
	 * @param date
	 * @return
	 */
	public int getAfterTheDayRecordCount(@Param("projectId") String projectId, @Param("theDate") Date date);
	/**
	 * 获得有还款任务的正常还款的项目编号
	 * @param date
	 * @return
	 */
	public List <ProjectRepaymentSplitRecord> getNormalRepayProjectListOfToday(@Param("theDate") String date);
	
	/**
	 * 获得逾期还款的明细清单
	 * @param date
	 * @return
	 */
	public List <ProjectRepaymentSplitRecord> getSplitRecordListOfTodayOverdueRepayProject(@Param("theDate") Date date);
	/**
	 * 获得逾期的还款额
	 * @param projectId
	 * @param date
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getTremMoneyListOfOverdueProject(@Param("projectId") String projectId, @Param("theDate") Date date);
	/**
	 * 获得指定项目指定日期的代还款清单
	 * @param projectId
	 * @param date
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getTremSplitListOfProjectAndDate(@Param("projectId") String projectId, @Param("theDate") String date);
	/**
	 * 获取指定投资记录对应的查分明细尚未还款的记录条数
	 * @param recordId
	 * @return
	 */
	public Integer getNotRapayCountByInvestmentRecordId(@Param("investmentRecordId") String recordId);
	
	
	/**
	 * 获得指定投资记录的指定日期后的待还款记录列表
	 * @param recordId
	 * @param date
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getWillReapyListByInvestmentRecordId(@Param("recordId") String recordId, @Param("theDate") Date date);
	/**
	 * 冻结代还款记录
	 * @param recordId
	 */
	public void updateToFreeze(@Param("recordId") String recordId);
    
	/**
	 * 根据查询字段得到列表
	 * @param map
	 * @return
	 */
	List<ProjectRepaymentSplitRecord> searchList(Map<String, Object> map);
	
//	/**
//	 * 根据投资记录编号获取还款计划
//	 * @param recordId
//	 * @return
//	 */
//	public List<ProjectRepaymentSplitRecord> getRepaymentListByRecordId(@Param("recordId") String recordId);
	/**
	 * 获取还款日历数据
	 * @param accountId
	 * @param year
	 * @param month
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord>  getRepaymentCalendarList(@Param("accountId") Long accountId, @Param("year") String year, @Param("month") String month);
	
	/**
	 * 获取账户某一天还款计划列表
	 * @param accountId
	 * @param year
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord>  getRepaymentCalendarDetailList(@Param("accountId") Long accountId, @Param("year") String year, @Param("month") String month);

	/**
	 * 获取用户待收款金额汇总信息（待收款总额 money、待收本金 principal、待收利息 interest）
	 * @param accountId
	 * @return
	 */
	public Map<String,Object> getUnReceivedMoneySummaryByAccountId(@Param("accountId") Long accountId);
	
	/**
	 * 获取用户待收款金额汇总信息（已收款总额 money、已收本金 principal、已收利息 interest）
	 * @param accountId
	 * @return
	 */
	public Map<String,Object> getReceivedMoneySummaryByAccountId(@Param("accountId") Long accountId);

	/**
	 * 获取项目还款计划（以天为单位，得到某天总的还款额）
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findProjectRepayPlanList(Map<String, Object> map);

	/**
	 * 查询某一天的还款计划
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findProjectRepayPlanListByDate(Map<String, Object> map);
	/**
	 * 根据项目id获取项目本金和项目利息
	 * @param projectId
	 * @return
	 */
	public Map<String,Object> findSumPrincipalAndSumInterest(@Param("projectId") String projectId);
    
	

	public List<Map<String, Object>> selectSumPrincipalAndSumInterest(Map<String, Object> map);
	/**
	 * 查询三天内的还款计划
	 * @param params
	 * @return
	 */
	public Double amountStatistics(HashMap<String, Object> params);

}