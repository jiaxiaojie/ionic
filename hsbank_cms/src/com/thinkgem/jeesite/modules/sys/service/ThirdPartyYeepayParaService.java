/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.ThirdPartyYeepayPara;
import com.thinkgem.jeesite.modules.sys.dao.ThirdPartyYeepayParaDao;

/**
 * 托管账号参数Service
 * @author yangtao
 * @version 2015-08-03
 */
@Service
@Transactional(readOnly = true)
public class ThirdPartyYeepayParaService extends CrudService<ThirdPartyYeepayParaDao, ThirdPartyYeepayPara> {

	public ThirdPartyYeepayPara get(String id) {
		return super.get(id);
	}
	
	public List<ThirdPartyYeepayPara> findList(ThirdPartyYeepayPara thirdPartyYeepayPara) {
		return super.findList(thirdPartyYeepayPara);
	}
	
	public Page<ThirdPartyYeepayPara> findPage(Page<ThirdPartyYeepayPara> page, ThirdPartyYeepayPara thirdPartyYeepayPara) {
		return super.findPage(page, thirdPartyYeepayPara);
	}
	
	@Transactional(readOnly = false)
	public void save(ThirdPartyYeepayPara thirdPartyYeepayPara) {
		super.save(thirdPartyYeepayPara);
	}
	
	@Transactional(readOnly = false)
	public void delete(ThirdPartyYeepayPara thirdPartyYeepayPara) {
		super.delete(thirdPartyYeepayPara);
	}
	
}