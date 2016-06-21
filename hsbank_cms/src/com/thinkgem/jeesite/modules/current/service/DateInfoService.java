/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.DateInfo;
import com.thinkgem.jeesite.modules.current.dao.DateInfoDao;

/**
 * 活期产品参数Service
 * @author ydt
 * @version 2015-12-11
 */
@Service
@Transactional(readOnly = true)
public class DateInfoService extends CrudService<DateInfoDao, DateInfo> {
	@Autowired
	private DateInfoDao dateInfoDao;
	public DateInfo get(String id) {
		return super.get(id);
	}
	
	public List<DateInfo> findList(DateInfo dateInfo) {
		return super.findList(dateInfo);
	}
	
	public Page<DateInfo> findPage(Page<DateInfo> page, DateInfo dateInfo) {
		return super.findPage(page, dateInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(DateInfo dateInfo) {
		super.save(dateInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(DateInfo dateInfo) {
		super.delete(dateInfo);
	}
    
	public List<DateInfo> queryByDate(Date start, Date end) {
		
		return dateInfoDao.queryByDate(start,end);
	}
   /**
    * 查询日期
    * @param dateInfo
    * @return
    */
	public DateInfo findByDate(DateInfo dateInfo) {
		return dateInfoDao.findByDate(dateInfo);
	}

	@Transactional(readOnly = false)
	public void update(DateInfo dateInfo) {
		dateInfoDao.update(dateInfo);
		
	}

}