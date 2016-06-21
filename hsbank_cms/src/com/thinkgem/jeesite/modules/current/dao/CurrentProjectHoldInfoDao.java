/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CurrentProjectHoldInfo;

/**
 * 活期产品持有信息DAO接口
 * @author ydt
 * @version 2015-12-09
 */
@MyBatisDao
public interface CurrentProjectHoldInfoDao extends CrudDao<CurrentProjectHoldInfo> {

	/**
	 * 获取用户的某一项目信息
	 * @param accountId
	 * @param projectId
	 * @param status
	 * @return
	 */
	CurrentProjectHoldInfo getByAccountIdAndProjectId(@Param("accountId") long accountId, @Param("projectId") long projectId, @Param("status") String status);

	/**
	 * 执行申请赎回本金操作
	 * @param accountId
	 * @param projectId
	 * @param redeemPrincipal
	 * @param redeemInterest
	 */
	Long doRedeemPrincipal(@Param("accountId") long accountId, @Param("projectId") long projectId, @Param("redeemPrincipal") double redeemPrincipal);
	
	public int updatePrincipal(@Param("projectId") Long projectId, @Param("accountId") Long accountId, @Param("amount") Double amount);

	/**
	 * 获取状态为statuses的列表
	 * @param statuses
	 * @return
	 */
	List<CurrentProjectHoldInfo> findListByStatuses(@Param("statuses") String... statuses);
	
	/**
	 * 我的活花生
	 * @param map
	 * @return
	 */
	public List<CurrentProjectHoldInfo> searchMyPageList(Map<String, Object> map);
	
	/**
	 * 我的活期持有信息
	 * @param accountId
	 * @param projectId
	 * @param status
	 * @return
	 */
	public CurrentProjectHoldInfo getMyCurrentProjectHoldInfo(@Param("accountId") long accountId, @Param("projectId") String projectId, @Param("status") String status);

	/**
	 * 付每日产生的利息时做的更新操作
	 * @param id
	 * @param reayInterest
	 */
	Long updateByRepay(@Param("id") String id, @Param("reayInterest") double reayInterest);

	/**
	 * 将用户申请赎回的本金从平台打款给用户时的更新操作
	 * @param id
	 * @param redeemPrincipal
	 */
	long updateByPayRedeem(@Param("id") String id, @Param("redeemPrincipal") Double redeemPrincipal);

	/**
	 * 提取利息时做的更新操作
	 * @param accountId
	 * @param projectId
	 * @param pollInterest
	 * @return
	 */
	long updateByPollInterest(@Param("accountId") long accountId, @Param("projectId") long projectId, @Param("pollInterest") double pollInterest);

	/**
	 * 根据projectId和status获取列表
	 * @param id
	 * @param status
	 * @return
	 */
	List<CurrentProjectHoldInfo> findListByProjectIdAndStatus(@Param("projectId") String projectId, @Param("status") String status);

	/**
	 * 清盘时做的更新操作
	 * @param id
	 * @return
	 */
	long updateByWindingUp(@Param("id") String id);

	/**
	 * 查询到accountId用户的持有列表
	 * @param accountId
	 * @return
	 */
	List<CurrentProjectHoldInfo> findListByAccountId(long accountId);
	
	/**
	 * 统计持有本金
	 * @param accountId
	 * @return
	 */
	public Double getSumPrincipalByAccountId(@Param("accountId") Long accountId);
	
	/**
	 * 按账户统计持有本金
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getStatPrincipalListByAccountId(Map<String, Object> map);

	

	

	

	
	
}