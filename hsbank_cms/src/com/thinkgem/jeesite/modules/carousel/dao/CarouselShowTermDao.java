/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.carousel.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CarouselShowTerm;

/**
 * 轮播图显示终端DAO接口
 * @author hyc
 * @version 2015-11-11
 */
@MyBatisDao
public interface CarouselShowTermDao extends CrudDao<CarouselShowTerm> {
	
	/**
	 * 批量插入终端限制
	 * @param list
	 * @return
	 */
	public int insertBatch(List<CarouselShowTerm> list);
	/**
	 * 根据轮播图编号删除
	 * @param carouselId
	 * @return
	 */
	public int deleteByCarouselId(Long carouselId);
	
	
	/**
	 * 根据轮播图编号查询列表
	 * @param carouselId
	 * @return
	 */
	public List<CarouselShowTerm> findListByCarouselId(Long carouselId);
	
}