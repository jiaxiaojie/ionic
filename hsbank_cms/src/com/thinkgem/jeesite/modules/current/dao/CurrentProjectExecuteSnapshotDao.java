/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CurrentProjectExecuteSnapshot;

/**
 * 活期项目执行快照DAO接口
 * @author ydt
 * @version 2015-12-09
 */
@MyBatisDao
public interface CurrentProjectExecuteSnapshotDao extends CrudDao<CurrentProjectExecuteSnapshot> {
	/**
	 * 根据项目流水号获取信息
	 * @param projectId
	 * @return
	 */
	public CurrentProjectExecuteSnapshot getByProjectId(@Param("projectId") Long projectId);
	
	/**
	 * 更新已融资金额、当前实际本金
	 * @param projectId		项目id
	 * @param amount		变化金额
	 * @return
	 */
	public int updateFinancedMoneyAndRealPrincipal(@Param("projectId") Long projectId, @Param("amount") Double amount);

	/**
	 * 还款时更新快照信息
	 * @param projectId
	 * @param repayInterest
	 * @return
	 */
	public Long updateByRepay(@Param("projectId") long projectId, @Param("repayInterest") double repayInterest);

	/**
	 * 将用户申请赎回的本金从平台打款给用户时的更新操作
	 * @param projectId
	 * @param redeemPrincipal
	 */
	public long updateByPayRedeem(@Param("projectId") Long projectId, @Param("redeemPrincipal") Double redeemPrincipal);

	/**
	 * 提取利息时做的更新操作
	 * @param projectId
	 * @param pollInterest
	 */
	public long updateByPollInterest(@Param("projectId") Long projectId, @Param("pollInterest") Double pollInterest);
	
	
	/**
	 * 获取当前实际本金
	 * @return
	 */
	public Double getStatRealPrincipal();
	/**
	 * 获取已产生的利息
	 * @return
	 */
	public Double getStatRepaidMoney();

	
}