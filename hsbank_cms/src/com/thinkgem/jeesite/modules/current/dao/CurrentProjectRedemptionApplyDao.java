/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CurrentProjectRedemptionApply;

/**
 * 活期产品赎回申请DAO接口
 * @author ydt
 * @version 2015-12-09
 */
@MyBatisDao
public interface CurrentProjectRedemptionApplyDao extends CrudDao<CurrentProjectRedemptionApply> {

	/**
	 * 查询用户某天的赎回申请列表
	 * @param accountId
	 * @param date
	 * @param statuses
	 * @return
	 */
	List<CurrentProjectRedemptionApply> findListByAccountId(@Param("accountId") Long accountId, @Param("date") Date date, @Param("statuses") String[] statuses);

	/**
	 * 查询状态为status的赎回申请列表
	 * @param status
	 * @return
	 */
	List<CurrentProjectRedemptionApply> findListByStatus(@Param("status") String status);

	/**
	 * 更新申请状态
	 * @param id
	 * @param status
	 */
	long updateByPayRedeem(@Param("id") String id);
	
	
	/**
	 * 赎回信息列表
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findRedemptionInfoList(Map<String, Object> map);

	/**
	 * 根据项目状态
	 * @param status
	 * @return
	 */
	long updateprintain(@Param("status") String status);

	void updatePrincipalById(@Param("id") String id);
   /**
     * 根据用户id查询用户赎回本金
     * @param accountId
     * @return
     */
	Double selectRedeemPrincipalByAccountId(Long accountId);
	
	
	
	
}