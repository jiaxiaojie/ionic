/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.Activity;
import com.thinkgem.jeesite.modules.api.PageBean;
import com.thinkgem.jeesite.modules.cms.dao.ActivityDao;

/**
 * 活动Service
 * @author wanduanrui
 * @version 2015-11-24
 */
@Service
@Transactional(readOnly = true)
public class ActivityService extends CrudService<ActivityDao, Activity> {

	@Autowired
	private ActivityDao activityDao;
	
	public Activity get(String id) {
		return super.get(id);
	}
	
	public List<Activity> findList(Activity activity) {
		return super.findList(activity);
	}
	
	public Page<Activity> findPage(Page<Activity> page, Activity activity) {
		
		return super.findPage(page, activity);
	}
	
	@Transactional(readOnly = false)
	public void save(Activity activity) {
		super.save(activity);
		
	}
	
	@Transactional(readOnly = false)
	public void delete(Activity activity) {
		super.delete(activity);
	}

	public void review(Activity activity) {
		
		
	}

	public Activity findLast() {
		
		return activityDao.findLast();
	}

	public Page<Activity> findNewActivity(Page<Activity> page, Activity activity) {
		
		activity.setPage(page);
		page.setList(dao.findNewActivity(activity));
		return page;
	}
	
	/**
	 * 分页查询
	 * @param termCode
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<Activity> getActivityListPage(String termCode, Integer pageNumber, Integer pageSize){
		Map<String, Object> map = new HashMap<String,Object>();
		Activity activity = new Activity();
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		map.put("startNumber", pageBean.getStartNumber());
		map.put("endNumber", pageBean.getEndNumber());
		map.put("termCode", termCode);
		map.put("dbName", activity.getDbName());
		map.put("DEL_FLAG_NORMAL", Activity.DEL_FLAG_NORMAL);
		return dao.getActivityListPage(map);
	}
		/**
		 * 查询活动数据
		 *@author 黄宇晨
		 *@Date 2016/6/16 17:38
		 */
	public Activity findActivityInfo(String termCodes) {
		Map<String, Object> map = new HashMap<String,Object>();
		Activity activity = new Activity();
		map.put("termCodes", termCodes);
		map.put("dbName", activity.getDbName());
		map.put("DEL_FLAG_NORMAL", Activity.DEL_FLAG_NORMAL);
		return dao.findActivityInfo(map);
	}
	
}