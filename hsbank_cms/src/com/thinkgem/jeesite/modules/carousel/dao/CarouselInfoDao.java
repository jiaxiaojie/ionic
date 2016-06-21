/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.carousel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CarouselInfo;

/**
 * 轮播图管理DAO接口
 * @author hyc
 * @version 2015-11-11
 */
@MyBatisDao
public interface CarouselInfoDao extends CrudDao<CarouselInfo> {
	
	/**
	 * 根据状态及操作终端获取列表
	 * @param status
	 * @param term
	 * @return
	 */
	public List<CarouselInfo> getCarouselListByStatusAndTerm(@Param("status") String status, @Param("term") String term, @Param("isNewWebsite") String isNewWebsite, @Param("theDate") String theDate);
	
	/**
	 * 获取轮播图列表
	 * @param status
	 * @param term
	 * @return
	 */
	public List<CarouselInfo> getCarouselListByStatusAndTermAndShow(@Param("status") String status, @Param("term") String term);
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<CarouselInfo> getCarouselListPage(Map<String, Object> map);
	
	
}