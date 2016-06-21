/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.entity.api.ProjectSearchBean;
import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.entity.front.ProjectTransferSearchBean;

/**
 * 债权转让DAO接口
 * @author yangtao
 * @version 2015-06-25
 */
@MyBatisDao
public interface ProjectTransferInfoDao extends CrudDao<ProjectTransferInfo> {
	public boolean setRecommend(String flag, String transferId);
	
	public int getTransferingProjectCount(@Param("accountId") String accountId);
	
	public int getTransferEndProjectCount(@Param("accountId") String accountId);

	public int getProjectCount(@Param("accountId") String accountId);


	/**
	 * 获取推荐项目列表（转让状态应为：正常转让（0））
	 * @param limit	列表数量限制
	 * @return
	 */
	public List<ProjectTransferInfo> getRecommendList(@Param("limit") int limit);

	/**
	 * 撤下转让中的项目
	 * @param projectId
	 */
	public void letDownTransfer(@Param("projectId") String projectId);
	
	/**
	 * 更新剩余债权
	 * @param projectId
	 * @param changeValue
	 */
	public void updateRemainderCreditor(@Param("transferProjectId") Long transferProjectId, @Param("changeValue") Double changeValue);
	
	/**
	 * 获得过期仍在执行状态的转让项目列表
	 * @param date
	 * @return
	 */
	public List<ProjectTransferInfo> getRuningTransferList(@Param("theDay") Date date);
	
	/**
	 * 根据transferProjectSearchBean中的搜索项，取得债权转让项目列表
	 * @param projectTransferSearchBean
	 * @return
	 */
	public List<ProjectTransferInfo> searchList(ProjectTransferSearchBean projectTransferSearchBean);
	
	/**
	 * 根据transferProjectSearchBean中的搜索项，取得债权转让项目分页列表
	 * @param map
	 * @return
	 */
	public List<ProjectTransferInfo> searchPageList(Map<String, Object> map);

	/**
	 * 新版债权分页
	 * @param map1
     * @return
     */
	public List<ProjectTransferInfo> findCreditPageList(Map<String, Object> map1);
	/**
	 * 转出中的投资记录
	 * @param accountId
	 * @return
	 */
	public List<ProjectTransferInfo> findTransferingProjectListByAccountId(Map<String, Object> map);
	
	/**
	 * 已转出的项目
	 * @param map
	 * @return
	 */
	public List<ProjectTransferInfo> findTransferEndProjectListByAccountId(Map<String, Object> map);
	/**
	 * 更新转让项目状态
	 * @param transferProjectId
	 * @param status
	 */
	public void updateStatus(@Param("transferProjectId") String transferProjectId, @Param("status") String status);
	/**
	 * 获得转让中的项目
	 * @return
	 */
	public List<ProjectTransferInfo> findTransferingProjectList();
	/**
	 * 获得指定投资记录对应的转让记录条数
	 * @param investmentId
	 * @return
	 */
	public Integer getTransferingCountByInvestmentId(@Param("investmentId") String investmentId);


	/**
	 * 根据状态获取转让项目数量
	 * @param status
	 */
	Integer getCountByStatus(@Param("status") String status);

	/**
	 * 查询转让项目列表
	 * @param projectSearchBean
	 * @return
	 */
	List<Map<String,Object>> findTransferProjectList(ProjectSearchBean projectSearchBean);

	/**
	 * 得到转让项目数量
	 * @param projectSearchBean
	 * @return
	 */
	Integer getCount(ProjectSearchBean projectSearchBean);

	/**
	 *查询债权项目数量
	 * @param map
	 * @return
     */
	Integer getCreditCount(Map<String, Object> map);
}