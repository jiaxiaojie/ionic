/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.operation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.DataQuery;

/**
 * 数据查询DAO接口
 * @author ydt
 * @version 2016-03-17
 */
@MyBatisDao
public interface DataQueryDao extends CrudDao<DataQuery> {

	List<Map<String,Object>> query(Map<String, Object> para);

	DataQuery getByMenuId(@Param("menuId") String menuId);
	
}