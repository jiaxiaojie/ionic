/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductSps;

/**
 * 产品规格参数DAO接口
 * @author lizibo
 * @version 2015-09-21
 */
@MyBatisDao
public interface IntegralMallProductSpsDao extends CrudDao<IntegralMallProductSps> {
	
	/**
	 * 根据产品编号获取最后一条数据
	 * @param productId
	 * @return
	 */
	public IntegralMallProductSps getLastOneByProductId(Long productId);

	/**
	 * 获取产品的规格参数
	 * @param productId
	 * @return
	 */
	public List<IntegralMallProductSps> findListByProductId(String productId);
}