/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerInvestmentTicket;

/**
 * 会员优惠券清单DAO接口
 * @author yangtao
 * @version 2015-07-21
 */
@MyBatisDao
public interface CustomerInvestmentTicketDao extends CrudDao<CustomerInvestmentTicket> {
	/**
	 * 得到指定账户可用优惠券列表
	 * @param accountId
	 * @return
	 */
	List<CustomerInvestmentTicket> findCanUseListByAccountId(Long accountId);
	
	/**
	 * 获取可以选择的现金券
	 * @param map
	 * @return
	 */
	List<CustomerInvestmentTicket> findCanChooseListByAccountId(Map<String, Object> map);
	
	/**
	 * 分页查询账户可用优惠卷列表
	 * @param map
	 * @return
	 */
	List<CustomerInvestmentTicket> findCanUsePageByAccountId(Map<String, Object> map);

	/**
	 * 得到指定账户已用优惠券列表
	 * @param accountId
	 * @return
	 */
	List<CustomerInvestmentTicket> findUsedListByAccountId(Map<String, Object> map);

	/**
	 * 得到指定账户过期优惠券列表
	 * @param accountId
	 * @return
	 */
	List<CustomerInvestmentTicket> findExpiredListByAccountId(Map<String, Object> map);

	/**
	 * 更新状态
	 * @param ticketId
	 * @param status
	 * @param useDt
	 */
	void updateStatus(@Param("ticketId") String ticketId, @Param("status") String status, @Param("useDt") Date useDt);
	
	/**
	 * 批量更新状态
	 * @param ticketIds
	 * @param status
	 * @return
	 */
    public int batchUpdateStatus(@Param("ticketIds") String ticketIds, @Param("status") String status);
    
    /**
	 * 将到期的投资券自动设置为已过期状态
	 */
	void autoChangeStatusToExpired();

	/**
	 * 根据accountId、remark查询券次数
	 * @param remark
	 * @param accountId
	 * @return
	 */
	long getTicketCountByAccountIdAndGetRemark(@Param("getRemark") String getRemark, @Param("accountId") Long accountId);

	/**
	 * 获取用户可用投资券价值总额
	 * @param accountId
	 * @return
	 */
	double getCustomerCanUseTicketTotalDenomination(@Param("accountId") Long accountId);

	Map<String, Object> getTicketStatistics(HashMap<String, Object> params);

	Integer findNeedClearSumByAccountId(@Param("accountId") String accountId);

	int getCountByAccountId(@Param("accountId") Long accountId, @Param("status") String status);

	void setNeedClearInvalidByAccountId(@Param("accountId") String accountId);
}