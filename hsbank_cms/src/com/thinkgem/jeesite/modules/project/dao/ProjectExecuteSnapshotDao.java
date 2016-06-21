/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;

/**
 * 合同执行快照DAO接口
 * @author yangtao
 * @version 2015-06-24
 */
@MyBatisDao
public interface ProjectExecuteSnapshotDao extends CrudDao<ProjectExecuteSnapshot> {
	public void deleteByProjectId(String projectId);
	
	/**
	 * 更新投资或转让相关金额：已融资金额、已冻结平台垫付金额、已冻结现金券金额、已冻结服务费
	 * @param projectId
	 * @param transferProjectId
	 * @param amount
	 * @param ticketAmount
	 * @param platformAmount
	 * @param toPlatformMoney
	 * @return
	 */
	public int updateAmount(@Param("projectId") Long projectId, @Param("transferProjectId") Long transferProjectId, @Param("amount") Double amount,
							@Param("ticketAmount") Double ticketAmount, @Param("platformAmount") Double platformAmount, @Param("toPlatformMoney") Double toPlatformMoney);
	
	/**
	 * 更新过程状态
	 * @param projectId
	 * @param transferProjectId
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("projectId") Long projectId, @Param("transferProjectId") Long transferProjectId, @Param("status") String status);
	
	/**
	 * 根据项目流水号获取信息
	 * @param projectId
	 * @return
	 */
	public ProjectExecuteSnapshot getByProjectId(@Param("projectId") Long projectId, @Param("transferProjectId") Long transferProjectId);

	/**
	 * 根据projectId transferProjectId获取执行快照信息
	 * @param projectId
	 * @param transferProjectId
	 * @return
	 */
	public ProjectExecuteSnapshot getTransferSnapshot(@Param("projectId") Long projectId, @Param("transferProjectId") Long transferProjectId);
}