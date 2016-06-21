/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.Activity;

/**
 * 活动DAO接口
 * @author wanduanrui
 * @version 2015-11-24
 */
@MyBatisDao
public interface ActivityDao extends CrudDao<Activity> {

	Activity findLast();

	List<Activity> findNewActivity(Activity activity);
	
	List<Activity> getActivityListPage(Map<String, Object> map);
	/**
	 *活动数据查询
	 *@author 黄宇晨
	 *@Date 2016/6/16 17:39
	 */
	Activity findActivityInfo(Map<String, Object> map );
}