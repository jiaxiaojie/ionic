/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.SysMobileVersionPara;
import com.thinkgem.jeesite.modules.sys.dao.SysMobileVersionParaDao;

/**
 * 会员账户余额对齐Service
 * @author lzb
 * @version 2015-11-10
 */
@Service
@Transactional(readOnly = true)
public class SysMobileVersionParaService extends CrudService<SysMobileVersionParaDao, SysMobileVersionPara> {

	@Autowired
	SysMobileVersionParaDao sysMobileVersionParaDao;
	
	public SysMobileVersionPara get(String id) {
		return super.get(id);
	}


	public SysMobileVersionPara getSysMobileVersionPara(String channel, String type){
		return dao.getSysMobileVersionPara(channel, type);
	}
	public List<SysMobileVersionPara> findList(SysMobileVersionPara sysMobileVersionPara) {
		return super.findList(sysMobileVersionPara);
	}
	
	public Page<SysMobileVersionPara> findPage(Page<SysMobileVersionPara> page, SysMobileVersionPara sysMobileVersionPara) {
		return super.findPage(page, sysMobileVersionPara);
	}
	
	@Transactional(readOnly = false)
	public void save(SysMobileVersionPara sysMobileVersionPara) {
		if("1".equals(sysMobileVersionPara.getMark())){
			sysMobileVersionParaDao.updateAllMark(new SysMobileVersionPara(sysMobileVersionPara.getChannel(),"0"));
		}
		super.save(sysMobileVersionPara);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysMobileVersionPara sysMobileVersionPara) {
		super.delete(sysMobileVersionPara);
	}


	/**
	 * @author 万端瑞
	 * @param sysMobileVersionPara
	 */
	@Transactional(readOnly = false)
	public void updateMark(SysMobileVersionPara sysMobileVersionPara) {
		SysMobileVersionPara sysMobileVersionParaEntity = this.get(sysMobileVersionPara.getId());
		
		if("1".equals(sysMobileVersionPara.getMark())){
			//如果同渠道下有已经开启的，则将其关闭
			sysMobileVersionParaDao.updateAllMark(new SysMobileVersionPara(sysMobileVersionParaEntity.getChannel(),"0"));
		}
		
		//更新mark
		sysMobileVersionParaEntity.setMark(sysMobileVersionPara.getMark());
		sysMobileVersionParaDao.update(sysMobileVersionParaEntity);
	}
	
}