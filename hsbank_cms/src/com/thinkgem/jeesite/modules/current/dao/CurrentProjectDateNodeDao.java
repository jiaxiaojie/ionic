/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CurrentProjectDateNode;

/**
 * 活期产品时间节点信息DAO接口
 * @author ydt
 * @version 2015-12-09
 */
@MyBatisDao
public interface CurrentProjectDateNodeDao extends CrudDao<CurrentProjectDateNode> {
	
	/**
	 * 更新幕资结束时间
	 * @param projectId
	 * @param theDate
	 */
	public void updateEndFundingDt(@Param("projectId") Long projectId, @Param("theDate") Date theDate);
	
}