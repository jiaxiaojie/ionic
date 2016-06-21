/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CmsCarousel;

/**
 * 首页轮播图DAO接口
 * @author ydt
 * @version 2015-07-03
 */
@MyBatisDao
public interface CmsCarouselDao extends CrudDao<CmsCarousel> {
	
}