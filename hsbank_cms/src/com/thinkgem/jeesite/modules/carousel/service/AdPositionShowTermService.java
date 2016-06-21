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
import com.thinkgem.jeesite.modules.entity.AdPositionShowTerm;
import com.thinkgem.jeesite.modules.carousel.dao.AdPositionShowTermDao;

/**
 * 广告位显示终端Service
 * @author huangyuchen
 * @version 2016-05-17
 */
@Service
@Transactional(readOnly = true)
public class AdPositionShowTermService extends CrudService<AdPositionShowTermDao, AdPositionShowTerm> {
	@Autowired
	private AdPositionShowTermDao adPositionShowTermDao;

	public AdPositionShowTerm get(String id) {
		return super.get(id);
	}
	
	public List<AdPositionShowTerm> findList(AdPositionShowTerm adPositionShowTerm) {
		return super.findList(adPositionShowTerm);
	}
	
	public Page<AdPositionShowTerm> findPage(Page<AdPositionShowTerm> page, AdPositionShowTerm adPositionShowTerm) {
		return super.findPage(page, adPositionShowTerm);
	}
	
	@Transactional(readOnly = false)
	public void save(AdPositionShowTerm adPositionShowTerm) {
		super.save(adPositionShowTerm);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdPositionShowTerm adPositionShowTerm) {
		super.delete(adPositionShowTerm);
	}

	/**
	 * 根据轮播图编号查询列表
	 * @param
	 * @return
	 */
	public AdPositionShowTerm findListByAdPositionId(String id) {
		return adPositionShowTermDao.findListByAdPositionId(id);
	}



	public List<AdPositionShowTerm> getAdPositionShowTermTermCodeByTermCode(String code) {
		return adPositionShowTermDao.getAdPositionShowTermTermCodeByTermCode(code);
	}

}