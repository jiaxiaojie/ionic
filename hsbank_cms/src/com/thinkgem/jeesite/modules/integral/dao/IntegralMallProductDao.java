/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.IntegralMallProduct;

/**
 * 花生乐园上架产品DAO接口
 * @author lizibo
 * @version 2015-09-22
 */
@MyBatisDao
public interface IntegralMallProductDao extends CrudDao<IntegralMallProduct> {
	/**
	 * 花生乐园产品首页
	 * @param searchType
	 * @param integralRange
	 * @param keywords
	 * @param orderBy
	 * @return
	 */
	public List<IntegralMallProduct> searchList(Map<String, Object> map);
	
	/**
	 * 分页查询列表信息
	 * @param map
	 * @return
	 */
	public List<IntegralMallProduct> searchPageList(Map<String, Object> map);

	List<IntegralMallProduct> queryList(Map<String, Object> params);
}