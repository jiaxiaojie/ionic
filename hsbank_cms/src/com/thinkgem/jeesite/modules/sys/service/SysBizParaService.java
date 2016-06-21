/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.SysBizPara;
import com.thinkgem.jeesite.modules.sys.dao.SysBizParaDao;

/**
 * 业务参数Service
 * @author yangtao
 * @version 2015-08-13
 */
@Service
@Transactional(readOnly = true)
public class SysBizParaService extends CrudService<SysBizParaDao, SysBizPara> {

	public SysBizPara get(String id) {
		return super.get(id);
	}
	
	public List<SysBizPara> findList(SysBizPara sysBizPara) {
		return super.findList(sysBizPara);
	}
	
	public Page<SysBizPara> findPage(Page<SysBizPara> page, SysBizPara sysBizPara) {
		return super.findPage(page, sysBizPara);
	}
	
	@Transactional(readOnly = false)
	public void save(SysBizPara sysBizPara) {
		super.save(sysBizPara);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysBizPara sysBizPara) {
		super.delete(sysBizPara);
	}
	
}