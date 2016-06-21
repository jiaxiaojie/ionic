/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.ThirdPartySmsPara;
import com.thinkgem.jeesite.modules.sys.dao.ThirdPartySmsParaDao;

/**
 * 短信通道参数Service
 * @author yangtao
 * @version 2015-08-03
 */
@Service
@Transactional(readOnly = true)
public class ThirdPartySmsParaService extends CrudService<ThirdPartySmsParaDao, ThirdPartySmsPara> {

	public ThirdPartySmsPara get(String id) {
		return super.get(id);
	}
	
	public List<ThirdPartySmsPara> findList(ThirdPartySmsPara thirdPartySmsPara) {
		return super.findList(thirdPartySmsPara);
	}
	
	public Page<ThirdPartySmsPara> findPage(Page<ThirdPartySmsPara> page, ThirdPartySmsPara thirdPartySmsPara) {
		return super.findPage(page, thirdPartySmsPara);
	}
	
	@Transactional(readOnly = false)
	public void save(ThirdPartySmsPara thirdPartySmsPara) {
		super.save(thirdPartySmsPara);
	}
	
	@Transactional(readOnly = false)
	public void delete(ThirdPartySmsPara thirdPartySmsPara) {
		super.delete(thirdPartySmsPara);
	}
	
}