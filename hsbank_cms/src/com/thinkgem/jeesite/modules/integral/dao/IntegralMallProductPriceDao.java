/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductPrice;

/**
 * 产品价格策略DAO接口
 * @author lizibo
 * @version 2015-09-21
 */
@MyBatisDao
public interface IntegralMallProductPriceDao extends CrudDao<IntegralMallProductPrice> {
	
	public List<IntegralMallProductPrice> getListByProductId(@Param("productId") String productId);
}