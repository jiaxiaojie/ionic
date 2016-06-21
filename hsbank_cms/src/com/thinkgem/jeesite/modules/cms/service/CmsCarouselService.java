/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CmsCarousel;
import com.thinkgem.jeesite.modules.cms.dao.CmsCarouselDao;

/**
 * 首页轮播图Service
 * @author ydt
 * @version 2015-07-03
 */
@Service
@Transactional(readOnly = true)
public class CmsCarouselService extends CrudService<CmsCarouselDao, CmsCarousel> {

	public CmsCarousel get(String id) {
		return super.get(id);
	}
	
	public List<CmsCarousel> findList(CmsCarousel cmsCarousel) {
		return super.findList(cmsCarousel);
	}
	
	public Page<CmsCarousel> findPage(Page<CmsCarousel> page, CmsCarousel cmsCarousel) {
		return super.findPage(page, cmsCarousel);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsCarousel cmsCarousel) {
		super.save(cmsCarousel);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsCarousel cmsCarousel) {
		super.delete(cmsCarousel);
	}
	
}