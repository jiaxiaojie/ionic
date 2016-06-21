/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;

/**
 * 会员账户余额变更流水DAO接口
 * @author ydt
 * @version 2015-06-26
 */
@MyBatisDao
public interface CustomerBalanceHisDao extends CrudDao<CustomerBalanceHis> {

	/**
	 * 根据platformUserNo获取用户最后一条余额变更记录
	 * @return
	 */
	CustomerBalanceHis getCustomerLastRecordByPlatformUserNo(@Param("platformUserNo") String platformUserNo);

	/**
	 * 根据accountId获取用户最后一条余额变更记录
	 * @return
	 */
	CustomerBalanceHis getCustomerLastRecordByAccountId(@Param("accountId") Long accountId);

	/**
	 * 根据查询字段得到列表：【accountId】【changeType】【startDateTime】【endDateTime】
	 * @param map
	 * @return
	 */
	List<CustomerBalanceHis> searchList(Map<String, Object> map);


	/**
	 * 分页查询交易记录
	 * @param map
	 * @return
     */
	List<CustomerBalanceHis> findPageList(Map<String, Object> map);
	
	/**
	 * 我的交易记录api
	 * @param map
	 * @return
	 */
	List<CustomerBalanceHis> getTransactionRecordList(Map<String, Object> map);
	
	/**
	 * 获取时间段内指定用户的提现次数
	 * @param accountId
	 * @param startDateTime
	 * @param endDateTime
	 * @return
	 */
	int getCustomerWithdrawCountDuringDateTime(@Param("accountId") Long accountId, @Param("startDateTime") Date startDateTime, @Param("endDateTime") Date endDateTime);

	/**
	 * 根据accountId、变更值和变更原因获取用户余额变更列表
	 * @param accountId
	 * @param changeValue
	 * @param changeReason
	 * @return
	 */
	List<CustomerBalanceHis> getListByAccountIdAndChangeValAndChangeReason(@Param("accountId") long accountId, @Param("changeValue") double changeValue, @Param("changeReason") String changeReason);
	
	/**
	 * 
	 * @param accountId
	 * @param changeType
	 * @param changeReason
	 * @return
	 */
	public Double getActivityReward(Map<String, Object> map);
}