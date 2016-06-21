/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerFreeWithdrawCountHis;
import org.apache.ibatis.annotations.Param;

/**
 * 会员可免费提现次数变更流水DAO接口
 * @author ydt
 * @version 2015-08-15
 */
@MyBatisDao
public interface CustomerFreeWithdrawCountHisDao extends CrudDao<CustomerFreeWithdrawCountHis> {
	
	/**
	 * 查询列表
	 * @param map
	 * @return
	 */
	List<CustomerFreeWithdrawCountHis> searchList(Map<String, Object> map);

	Map<String,Object> statistics(@Param("accountId") Long accountId);
}