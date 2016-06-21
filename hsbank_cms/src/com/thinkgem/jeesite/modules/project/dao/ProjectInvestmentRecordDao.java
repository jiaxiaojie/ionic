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
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;

/**
 * 投资记录DAO接口
 * @author yangtao
 * @version 2015-06-24
 */
@MyBatisDao
public interface ProjectInvestmentRecordDao extends CrudDao<ProjectInvestmentRecord> {
	/**
	 * 得到投资者投资的运行中的项目清单
	 * @param accountId
	 * @return
	 */
	public List<ProjectInvestmentRecord> findInvestmentRunningProjectListByAccountId(String accountId);
	
	/**
	 * 得到投资者投资的结束的项目清单
	 * @param accountId
	 * @return
	 */
	public List<ProjectInvestmentRecord> findInvestmentEndProjectListByAccountId(String accountId);

	/**
	 * 获取今日排行列表
	 * @param limit
     * @return
     */
	public List<ProjectInvestmentRecord> getTodayRankingList(@Param("limit") int limit);
	
	/**
	 * 得到用户投资冻结状态项目数量
	 * @param accountId
	 * @return
	 */
	public int findCongealProjectCountByAccountId(String accountId);
	
	/**
	 * 得到结束项目中提前还款的项目数量
	 * @param accountId
	 * @return
	 */
	public int getPreRepaymentProjectCount(String accountId);

	/**
	 * 得到指定项目的投资记录列表
	 * 排序：时间 降序
	 * @param projectId 	项目流水号
	 * @param status 		投资记录状态, 传null或空则查询全部
	 * @return
	 */
	public List<ProjectInvestmentRecord> findListByProjectId(@Param("projectId") String projectId, @Param("status") String status);
	
	
	public List<ProjectInvestmentRecord> findListByProjectIdAndStatuses(@Param("projectId") String projectId);
	
	/**
	 * 获取项目的投资人数
	 * @param projectId
	 * @return
	 */
	public int getCountByProjectIdAndStatuses(@Param("projectId") String projectId);
	
	/**
	 * 更新投资记录状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("id") String id, @Param("status") String status);
	
	/**
	 * 根据投资交易第三方流水号获取信息
	 * @param thirdPartyOrder
	 * @return
	 */
	public ProjectInvestmentRecord getByThirdPartyOrder(String thirdPartyOrder);
	
	/**
	 * 根据第三方流水号查询记录列表
	 * @param thirdPartyOrder
	 * @return
	 */
	public List<ProjectInvestmentRecord> findListByThirdPartyOrder(@Param("thirdPartyOrder") String thirdPartyOrder);

	/**
	 * 根据projectId transferProjectId status查询投资记录
	 * @param projectId
	 * @param transferProjectId
	 * @param status
	 * @return
	 */
	public List<ProjectInvestmentRecord> findListByProjectIdAndTransferProjectId(
			@Param("projectId") String projectId, @Param("transferProjectId") Long transferProjectId, @Param("status") String status);
	
	/**
	 * 获取债权投资记录个数
	 * @param projectId
	 * @param transferProjectId
	 * @return
	 */
	public int getCountByProjectIdAndTransferProjectId(
			@Param("projectId") String projectId, @Param("transferProjectId") Long transferProjectId);
	
	
	/**
	 * 获得指定会员的投资记录(持有中)
	 * @param accountId
	 * @return
	 */
	public List<ProjectInvestmentRecord> findListForCyz(Map<String, Object> map);

	/**
	 * 获得指定会员的投资记录(投标中)
	 * @param map
	 * @return
	 */
	public List<ProjectInvestmentRecord> findListForTbz(Map<String, Object> map);

	/**
	 * 获得指定会员的投资记录(已结束)
	 * @param map
	 * @return
	 */
	public List<ProjectInvestmentRecord> findListForYjs(Map<String, Object> map);
	
	/**
	 * 查询指定转让记录对应的投资记录中的上家给平台的服务费
	 * @param transferProjectId
	 * @return
	 */
	public Double getUpServiceCharge(@Param("transferProjectId") String transferProjectId);
	
	/**
	 * 获得投资时间超过30分钟、投资状态为"冻结中"的投资记录
	 * @param accountId
	 * @return
	 */
	public List<ProjectInvestmentRecord> findInvestmentListByOpDtAndStatus(@Param("status") String status, @Param("theDate") Date date);
	
	/**
	 * 根据投资时间获取投资列表
	 * @param status
	 * @param date
	 * @return
	 */
	public List<ProjectInvestmentRecord> findListByTheDateAndStatus(@Param("status") String status, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

	/**
	 * 最新投资列表
	 * @param map
	 * @return
     */
	public List<ProjectInvestmentRecord> findLatestInvestPageList(Map<String, Object> map);
	/**
	 * 查询第三方流水号不为空的记录
	 * @return
	 */
	public List<ProjectInvestmentRecord> findInvestmentListThirdPartyOrderNotEmpty();
	
	/**
	 * 获取指定用户的投资记录数量
	 * @param accountId
	 * @return
	 */
	public Integer getInvestCountByAccountId(@Param("accountId") Long accountId);
	
	/**
	 * 查询投资记录(投资成功+已还款)
	 * @param accountId
	 * @return
	 */
	public Integer getInvestCountIsNewCustomerByAccountId(@Param("accountId") String accountId);

	/**
	 * 获取项目总的待还本息
	 * @param projectId
	 * @param transferProjectId
	 * @param status
	 * @return
	 */
	public Double getSumWillProfit(@Param("projectId") String projectId, @Param("transferProjectId") String transferProjectId, @Param("status") String status);
	
	/**
	 * 获取用户对某个项目的投资总额
	 * @param projectId
	 * @param transferProjectId
	 * @param accountId
	 * @return
	 */
	public Double getSumAmount(@Param("projectId") String projectId, @Param("transferProjectId") String transferProjectId, @Param("accountId") Long accountId);
	
	/**
	 * 投资统计
	 * @param map
	 * @return
	 */
	public Double getSumAmountWithStat(Map<String, Object> map);
	
	/**
	 * 更新投资记录的待还本金利息为零
	 * @param investRecordId
	 */
	public void updateWillReceiveInfoClean(@Param("investRecordId") String investRecordId);
	
	/**
	 * 更新投资记录的待还本金利息信息
	 * @param investRecordId
	 */
	public void updateWillReceiveInfo(@Param("investRecordId") String investRecordId, @Param("willPrincipal") String willPrincipal, @Param("willInterest") String willInterest);

	/**
	 * 获取用户首条正常投资记录
	 * @param accountId
	 * @return
	 */
	public ProjectInvestmentRecord getCustomerFirstNormalRecord(long accountId);

	/**
	 * 获取推荐人所有推荐对象（被推荐人）的第5次投资记录Id（group by被推荐人）
	 * @param recommenderMobile
	 * @return
	 */
	public Long getFifthNormalRecordIdByRecommenderMobile(@Param("recommenderMobile") String recommenderMobile);

	/**
	 * 获取指定用户的（项目处于放款及其以后状态）的投资记录数量
	 * @param accountId
	 * @return
	 */
	public int getRecordCountAfterRepaymentByAccountId(Long accountId);

	/**
	 * 获取双旦期间注册用户符合条件的投资列表（投资额>=1000，group by investment_user_id）
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<ProjectInvestmentRecord> findDoubleEggList(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

	/**
	 * 获取用户时间段内的首次投资记录
	 * @param accountId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public ProjectInvestmentRecord getCustomerFirstNormalRecordDuringTime(@Param("accountId") long accountId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);
	
	/**
	 * 获得用户某一时间段内的年化投资额
	 * @param accountId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public Double getCustomerAnnualizedAmountDuringTime(@Param("accountId") long accountId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	public String getCustomerStatusByAccountId(Long accountId);

	public Map<String, Object> findCustomerInvestmentAmountDistributionNoPage(Map<String, Object> map);
   
	/**
	 * 每日投资总额（所有用户的投资总额及又好用户的投资总额）
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findCustomerInvestmentStatistics(Map<String, Object> map);

	/**
	 *  按日期查询全部用户每日投资额度清单
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findInvestmentRecordByDate(
			Map<String, Object> map);

	/**
	 * 按日期查询小伙伴每日投资额度清单
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findFriendsInvestmentRecordByDate(
			Map<String, Object> map);

	
	
	/**
	 * 投资统计
	 * @param params
	 */
	public Double sumAmount(HashMap<String, Object> params);

	/**
	 * 用户平均投资额度
	 * @param map
	 * @return
	 */
	public Map<String, Object> findCustomerInvestmentAmountDistributionAvgNoPage(
			Map<String, Object> map1);
		/**
		 * 投资总额度
		 * @param map2
		 * @return
		 */
	public Map<String, Object> findCustomerInvestmentAmount(
			Map<String, Object> map2);

	/**
	 * 获取平台投资总额(累计募集)
	 * @return
	 */
	public String getInvestmentAmount();
}