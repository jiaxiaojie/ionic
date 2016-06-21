/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.entity.api.ProjectSearch;
import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.front.ProjectSearchBean;

/**
 * 借贷合同管理DAO接口
 * @author yangtao
 * @version 2015-06-24
 */
@MyBatisDao
public interface ProjectBaseInfoDao extends CrudDao<ProjectBaseInfo> {
	public List<ProjectBaseInfo> findCreateList(ProjectBaseInfo pbi);
	public List<ProjectBaseInfo> findLoanList(ProjectBaseInfo pbi);
	public void submit(String projectId);
	public int checkDuplicate(ProjectBaseInfo projectBaseInfo);
	public List<ProjectBaseInfo> findCustomerAsBorrowerProjectList(ProjectBaseInfo projectBaseInfo);
	public List<ProjectBaseInfo> findCustomerAsInvestmentProjectList(ProjectBaseInfo projectBaseInfo);
	public List<ProjectBaseInfo> findCustomerAsTransferProjectList(ProjectBaseInfo projectBaseInfo);
	public List<ProjectBaseInfo> findRecommendProjectList();
	public List<ProjectBaseInfo> findIsNewUserList(ProjectBaseInfo pbi);
	
	/**
	 * 复制产品基本信息
	 * @param projectBaseInfo
	 * @return
	 */
	public int copy(ProjectBaseInfo projectBaseInfo);
	/**
	 * 查询我借款的项目
	 * @param accountId
	 * @param statusFlag
	 * @return
	 */
	public List<ProjectBaseInfo> findMyProjectList(@Param("accountId") String accountId, @Param("statusFlag") String statusFlag);
	
	/**
	 * 我的投资项目
	 * @param accountId
	 * @return
	 */
	public List<ProjectBaseInfo> findMyInvestmentList(@Param("accountId") String accountId, @Param("flag") String flag);
	
	/**
	 * 根据外部编号获取投资列表
	 * @param extendNo
	 * @param flag
	 * @return
	 */
	public List<ProjectBaseInfo> findMyInvestmentByExtendNoList(@Param("extendNo") String extendNo, @Param("flag") String flag);
	
	/**
	 * 根据投资记录编号查询项目信息
	 * @param recordId
	 * @return
	 */
	public ProjectBaseInfo getProjectByRecordId(@Param("recordId") String recordId);
	
	/**
	 * 获取推荐项目列表（项目状态应为：投标中（3）或投标结束（4）或还款中（5）或还款结束（6））
	 * @param limit 列表数量限制
	 * @return
	 */
	public List<ProjectBaseInfo> getRecommendList(@Param("limit") int limit);
	
	/**
	 * 获取推荐项目列表（项目状态应为：投标中（3））
	 * @param limit
	 * @return
	 */
	public List<ProjectBaseInfo> getApiRecommendList(@Param("limit") int limit);
	
	/**
	 * 获取前端融资项目数量
	 * 		项目状态在3,4,5,6
	 * @return
	 */
	public int getOnlineProjectCount();
	/**
	 * 获取投标中项目数量
	 * 		项目状态为3
	 * @return
	 */
	public int getTenderingProjectCount();
	/**
	 * 获取投标结束项目数量
	 * 		项目状态为4
	 * @return
	 */
	public int getTenderedProjectCount();
	/**
	 * 获取还款中项目数量
	 * 		项目状态为5
	 * @return
	 */
	public int getRepaymentingProjectCount();
	/**
	 * 获取还款结束项目数量
	 * 		项目状态为6
	 * @return
	 */
	public int getRepaymentedProjectCount();
	/**
	 * 根据projectSearchBean中的搜索项，取得项目列表
	 * @param projectSearchBean
	 * @return
	 */
	public List<ProjectBaseInfo> searchList(ProjectSearchBean projectSearchBean);
	
	/**
	 * 分页查询项目数据
	 * @param map
	 * @return
	 */
	public List<ProjectBaseInfo> searchPageList(Map<String, Object> map);
	
	/**
	 * 获得
	 * @param accountId
	 * @return
	 */
	public Double getNotEndProjectMyCreditProject(String accountId);
	/**
	 * 到期上线的项目
	 * @param date
	 * @return
	 */
	public List<ProjectBaseInfo> getNeedToInvestmentProject(@Param("theDate") Date date);
	/**
	 * 到期募资结束的项目
	 * @param date
	 * @return
	 */
	public List<ProjectBaseInfo> getNeedToInvestmentFinishProject(@Param("theDate") Date date);
	/**
	 * 更改状态为3
	 * @param projectId
	 */
	public void updateProjectToInvestment(@Param("projectId") String projectId);
	/**
	 * 更改状态为4
	 * @param projectId
	 */
	public void updateProjectToInvestmentFinish(@Param("projectId") String projectId);

	/**
	 * 根据借款人的accountId及项目状态查询项目
	 * @param map中的信息：Page<ProjectBaseInfo> page、Long accountId、String[] projectStatuses
	 * @return
	 */
	public List<ProjectBaseInfo> findProjectByBorrowerAccountIdAndProjectStatus(Map<String, Object> map);
	
	/**
	 * 更新projectBaseInfo表isAutoRepay状态（"0"为未授权；"1"为已授权）
	 * @param projectId
	 * @param isAutoRepay
	 */
	public void updateIsAutoRepay(@Param("projectId") String projectId, @Param("isAutoRepay") String isAutoRepay);
	

	/**
	 * 更新projectBaseInfo表autoRepayCode（即为授权自动还款的requestNo）
	 * @param projectId
	 * @param requestNo
	 */
	public void updateAutoRepayCode(@Param("projectId") String projectId, @Param("autoRepayCode") String requestNo);

	/**
	 * 根据借款人的accountId查询到其募集中的借款
	 * 		（说明：查询出projectBaseInfo中的autoRepayCode代表的是：此项目是否可以做授权自动还款操作（"1"代表可以；其它：不可以））
	 * @param accountId
	 * @param pageNo
	 * @return
	 */
	public List<ProjectBaseInfo> findFundingProjectByBorrowerAccountId(ProjectBaseInfo projectBaseInfo);
	
	/**
	 * 更新项目状态
	 * @param projectId
	 * @param projectStatus
	 * @return
	 */
	public int updateProjectStatus(@Param("projectId") Long projectId, @Param("projectStatus") String projectStatus);
	
	/**
	 * 更新项目是否放款
	 * @param projectId
	 * @param isLoan
	 * @return
	 */
	public int updateProjectIsLoan(@Param("projectId") Long projectId, @Param("isLoan") String isLoan);
	
	/**
	 * 获得运行中的项目
	 * @return
	 */
	public List<ProjectBaseInfo> findRunningProject();
	/**
	 * 获得逾期还款中的项目列表
	 * @return
	 */
	public List<ProjectBaseInfo> findOverDueProject();
	
	/**
	 * 更新项目月还本息
	 * @param projectId
	 * @param monthRepayMoney
	 * @return
	 */
	public int updateProjectMonthRepayMoney(@Param("projectId") String projectId, @Param("monthRepayMoney") Double monthRepayMoney);
	
	/**
	 * 更新还款总额
	 * @param projectId
	 * @param repaymentMoney
	 * @return
	 */
	public int updateProjectRepaymentMoney(@Param("projectId") String projectId, @Param("repaymentMoney") Double repaymentMoney);
	public List<ProjectBaseInfo> findSortedList(ProjectBaseInfo projectBaseInfo);
	
	/**
	 * 更新项目sortInList字段
	 * @param projectId
	 * @param sort
	 */
	public void updateSortInList(@Param("projectId") int projectId, @Param("sortInList") int sortInList);
	
	/**
	 * 更新项目sortInIndex字段
	 * @param projectId
	 * @param sort
	 */
	public void updateSortInIndex(@Param("projectId") int projectId, @Param("sortInIndex") int sortInIndex);
	
	/**
	 * 查询运营数据列表
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findProjectOperationList(HashMap<String, Object> params);
	public List<Map<String,Object>> findAlreadyLoanList(Map<String, Object> map);
	public Integer raisingCount(@Param("creditId") Long creditId);

	/**
	 * 查询项目列表
	 * @param projectSearchBean
	 */
	List<Map<String,Object>> findProjectList(com.thinkgem.jeesite.modules.entity.api.ProjectSearchBean projectSearchBean);

	/**
	 * 查询项目数量
	 * @param projectSearchBean
	 * @return
	 */
	Integer getCount(com.thinkgem.jeesite.modules.entity.api.ProjectSearchBean projectSearchBean);

	/**
	 * 查询可投资项目数量
	 * @param projectSearch
	 * @return
	 */
	Integer getCanInvestmentProjectCount(ProjectSearch projectSearch);
	Map<String,Object> getAboutFiles(@Param("projectId") Integer projectId);

	/**
	 * 查询投资列表
	 * @param
	 * @return
     */
	List<ProjectBaseInfo> findInvestmentList(Map<String, Object> params);
}