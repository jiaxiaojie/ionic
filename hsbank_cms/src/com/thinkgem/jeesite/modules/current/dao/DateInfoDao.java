/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.DateInfo;

/**
 * 活期产品参数DAO接口
 * @author ydt
 * @version 2015-12-11
 */
@MyBatisDao
public interface DateInfoDao extends CrudDao<DateInfo> {
    /**
     * 根据日期查询
     * @param start
     * @param end
     * @return
     */
	List<DateInfo> queryByDate(Date start, Date end);


	/**
	 * 判断此天是否为工作日
	 * @param date
	 * @return
	 */
	boolean isWorkday(@Param("date") Date date);


	/**
	 *查询日期
	 * @param dateInfo
	 * @return
	 */
	DateInfo findByDate(DateInfo dateInfo);


	
}