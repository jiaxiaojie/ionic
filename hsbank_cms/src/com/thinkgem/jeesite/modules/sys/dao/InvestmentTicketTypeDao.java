/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.InvestmentTicketType;

/**
 * 投资券类型管理DAO接口
 * @author yangtao
 * @version 2015-07-21
 */
@MyBatisDao
public interface InvestmentTicketTypeDao extends CrudDao<InvestmentTicketType> {

	/**
	 * 根据面额获取投资券类型
	 * @param ticketDenomination
	 * @return
	 */
	InvestmentTicketType getByDenomination(@Param("denomination") int denomination);

	/**
	 * 根据status查询投资券类型列表
	 * @param status
	 * @return
	 */
	List<InvestmentTicketType> findListByStatus(@Param("status") String status);
	
}