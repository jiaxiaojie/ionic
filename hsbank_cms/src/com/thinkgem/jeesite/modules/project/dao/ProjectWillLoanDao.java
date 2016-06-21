/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectWillLoan;

/**
 * 借贷意向管理DAO接口
 * @author yangtao
 * @version 2015-06-24
 */
@MyBatisDao
public interface ProjectWillLoanDao extends CrudDao<ProjectWillLoan> {
	
	/**
	 * 查询用户的借款申请意向
	 * @param projectWillLoan
	 * @return
	 */
	public List<ProjectWillLoan> findMyList11(@Param("accountId") Long accountId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("status") String status) ;
	
	/**
	 * 查询用户的借贷申请中的额度
	 * @param accountId
	 * @return
	 */
	public Double findNotEndApplyMoneyOfCustomer(@Param("accountId") String accountId);
	
	/**
	 * 设置关联项目编号
	 * @param projectId
	 * @param id
	 */
	public void changeRelProjectId(@Param("projectCode") String projectCode, @Param("id") String id);
	/**
	 * 设置创建项目标志位
	 * @param id
	 */
	public void updateCreateRelProjectFlag(@Param("id") String id);

	/**
	 * 查询用户的借款申请意向
	 * @param map
	 * @return
	 */
	public List<ProjectWillLoan> findListByAccountIdAndStatus(Map<String, Object> map);

	/**
	 * 取消借款申请，此时借款申请应处于审核中状态，即projectWillLoan.status为（0）
	 * @param id
	 */
	public void cancelLoan(String id);
}