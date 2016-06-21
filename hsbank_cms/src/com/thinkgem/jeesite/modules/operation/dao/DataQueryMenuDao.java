/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.operation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.DataQueryMenu;

/**
 * 数据查询关联菜单DAO接口
 * @author ydt
 * @version 2016-03-21
 */
@MyBatisDao
public interface DataQueryMenuDao extends CrudDao<DataQueryMenu> {

	List<DataQueryMenu> getListByQueryId(@Param("queryId") Long queryId);

	void deleteByQueryId(@Param("queryId") String queryId);
	
}