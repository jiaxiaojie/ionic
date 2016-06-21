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
import com.thinkgem.jeesite.modules.entity.CustomerIntegralHis;

/**
 * 会员花生豆变更流水DAO接口
 * @author ydt
 * @version 2015-06-26
 */
@MyBatisDao
public interface CustomerIntegralHisDao extends CrudDao<CustomerIntegralHis> {

	Integer getTotalIntegral(Long accountId);

	Integer getIntegralDuringTime(@Param("accountId") Long accountId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

	List<CustomerIntegralHis> findListWithQuery(Map<String, Object> map);
	
	/**
	 * 分页获取花生豆数据
	 * @param map
	 * @return
	 */
	List<CustomerIntegralHis> findPageList(Map<String, Object> map);

	/**
	 * 指定时间内是否进行过签到操作
	 * @param accountId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	boolean hasSigned(@Param("accountId") Long accountId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	
	/**
	 * 获取最后一条签到记录
	 * @param accountId
	 * @return
	 */
	CustomerIntegralHis getLastSignData(@Param("accountId") Long accountId);
     //获取总条数
	 Integer getCount(@Param("customerId") Long customerId, @Param("type") String type);

	/*boolean hasGiveIntegralNumber(Long accountId, Date dateFormate, Date date);

	boolean canGiveIntegralNumber(Long accountId, Date dateFormate, Date date);*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}