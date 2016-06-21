/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;

/**
 * 活期项目信息DAO接口
 * @author ydt
 * @version 2015-12-09
 */
@MyBatisDao
public interface CurrentProjectInfoDao extends CrudDao<CurrentProjectInfo> {


	CurrentProjectInfo getCurrentProjectInfoByInfo(CurrentProjectInfo currentProjectInfo);
	
	/**
	 * 更新项目状态
	 * @param projectId
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("projectId") Long projectId, @Param("status") String status);
	
	/**
	 * 更新自动还款授权
	 * @param projectId
	 * @param isAutoRepay
	 * @return
	 */
	public int updateAutoRepay(@Param("projectId") String projectId, @Param("isAutoRepay") String isAutoRepay);
	
	/**
	 * 查询列表
	 * @param map
	 * @return
	 */
	List<CurrentProjectInfo> searchList(Map<String, Object> map);

	List<CurrentProjectInfo> querySimpleList(CurrentProjectInfo qa);

	/**
	 * 根据windingUpStatus查找列表
	 * @param windingUpStatus
	 * @return
	 */
	List<CurrentProjectInfo> findListByWindingUpStatus(@Param("windingUpStatus") String windingUpStatus);

	/**
	 * 清盘时做的更新操作
	 * @param id
	 * @return
	 */
	long updateByWindingUp(@Param("id") String id);

	/**
	 * 将处于"审批通过"状态 且到发布时间的项目状态设置为"投标中"
	 */
	void updateStatusToInvestmenting();

	/**
	 * 将处于"投标中"状态 且到投标截止时间的项目状态设置为"投标结束"
	 */
	void updateStatusToInvestmentOver();

	Long selectBorrowerAccountIdByProjectId(Long projectId);
	
}