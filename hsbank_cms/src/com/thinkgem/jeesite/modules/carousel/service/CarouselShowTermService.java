/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.carousel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CarouselShowTerm;
import com.thinkgem.jeesite.modules.carousel.dao.CarouselShowTermDao;

/**
 * 轮播图显示终端Service
 * @author hyc
 * @version 2015-11-11
 */
@Service
@Transactional(readOnly = true)
public class CarouselShowTermService extends CrudService<CarouselShowTermDao, CarouselShowTerm> {

	
	  @Autowired
	    private CarouselShowTermDao carouselShowTermDao;
	
	
	
	public CarouselShowTerm get(String id) {
		return super.get(id);
	}
	
	public List<CarouselShowTerm> findList(CarouselShowTerm carouselShowTerm) {
		return super.findList(carouselShowTerm);
	}
	
	public Page<CarouselShowTerm> findPage(Page<CarouselShowTerm> page, CarouselShowTerm carouselShowTerm) {
		return super.findPage(page, carouselShowTerm);
	}
	
	@Transactional(readOnly = false)
	public void save(CarouselShowTerm carouselShowTerm) {
		super.save(carouselShowTerm);
	}
	
	@Transactional(readOnly = false)
	public void delete(CarouselShowTerm carouselShowTerm) {
		super.delete(carouselShowTerm);
	}
	
	/**
	 * 根据轮播图编号查询列表
	 * @param activityId
	 * @return
	 */
	public List<CarouselShowTerm> findListByCarouselId(Long carouselId) {
		return carouselShowTermDao.findListByCarouselId(carouselId);
	}


	
	
}