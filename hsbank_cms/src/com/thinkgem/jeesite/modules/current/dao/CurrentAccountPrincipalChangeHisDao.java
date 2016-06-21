/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.entity.CurrentAccountInterestChangeHis;
import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;

/**
 * 活期账户本金变更历史DAO接口
 * @author ydt
 * @version 2015-12-09
 */
@MyBatisDao
public interface CurrentAccountPrincipalChangeHisDao extends CrudDao<CurrentAccountPrincipalChangeHis> {
	
	/**
	 * 更新状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("id") String id, @Param("status") String status);
	
	/**
	 * 获取列表信息
	 * @param projectId
	 * @param changeType
	 * @param status
	 * @return
	 */
	public List<CurrentAccountPrincipalChangeHis> getPrincipalChangeHisList(@Param("projectId") String projectId, @Param("changeType") String changeType, @Param("status") String status);
	
	/**
	 * 账户总览我的活花生
	 * @param accountId
	 * @param changeType
	 * @param status
	 * @param limit
	 * @return
	 */
	public List<CurrentAccountPrincipalChangeHis> getMyPeanutList(@Param("accountId") Long accountId, @Param("changeType") String changeType, @Param("status") String status, @Param("limit") int limit);
	
	/**
	 * 获取待回滚的信息列表
	 * @param changeType
	 * @param status
	 * @param date
	 * @return
	 */
	public List<CurrentAccountPrincipalChangeHis> getPrincipalChangeHisListByTypeAndStatus(@Param("changeType") String changeType, @Param("status") String status, @Param("theDate") Date date);
	
	/**
	 * 根据第三方流水号获取信息
	 * @param thirdPartyOrder
	 * @return
	 */
	public CurrentAccountPrincipalChangeHis getByThirdPartyOrder(@Param("thirdPartyOrder") String thirdPartyOrder, @Param("changeType") String changeType);

	public Integer getCount(CurrentAccountPrincipalChangeHis queryEntity);
	
	/**
	 * 投资列表(转入本金)
	 * @param map
	 * @return
	 */
	public List<CurrentAccountPrincipalChangeHis> searchMyPrincipalPageList(Map<String, Object> map);
	
	/**
	 * 转出列表(本金、利息)
	 * @param map
	 * @return
	 */
	public List<CurrentAccountPrincipalChangeHis> searchMyPrincipalAndInterestPageList(Map<String, Object> map);


	/**
	 * 每日收益列表
	 * @param map
	 * @return
	 */
	public List<CurrentAccountPrincipalChangeHis> searchMyGetInterestPageList(Map<String, Object> map);
	
	/**
	 * 投资列表(投资/赎回)
	 * @param map
	 * @return
	 */
	public List<CurrentAccountPrincipalChangeHis> getPrincipalPageList(Map<String, Object> map);

	public Double getChangeValueSum(CurrentAccountPrincipalChangeHis queryEntity);
	
	/**
	 * 统计投资金额
	 * @param accountId
	 * @param changeType
	 * @param status
	 * @return
	 */
	public Double getSumCurrentPrincipal(@Param("accountId") Long accountId, @Param("changeType") String changeType, @Param("status") String status);

	/**
	 * 平台的活期投资总额(累计募集)
	 * @param changeType
	 * @param status
	 * @return
	 *
	 * @author liuguoqing
	 */
	public String getCurrentAmount(@Param("changeType") String changeType, @Param("status") String status);

}