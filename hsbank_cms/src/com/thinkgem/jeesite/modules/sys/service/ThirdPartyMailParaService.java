/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.ThirdPartyMailPara;
import com.thinkgem.jeesite.modules.sys.dao.ThirdPartyMailParaDao;

/**
 * 邮件发送参数Service
 * @author yangtao
 * @version 2015-08-13
 */
@Service
@Transactional(readOnly = true)
public class ThirdPartyMailParaService extends CrudService<ThirdPartyMailParaDao, ThirdPartyMailPara> {

	public ThirdPartyMailPara get(String id) {
		return super.get(id);
	}
	
	public List<ThirdPartyMailPara> findList(ThirdPartyMailPara thirdPartyMailPara) {
		return super.findList(thirdPartyMailPara);
	}
	
	public Page<ThirdPartyMailPara> findPage(Page<ThirdPartyMailPara> page, ThirdPartyMailPara thirdPartyMailPara) {
		return super.findPage(page, thirdPartyMailPara);
	}
	
	@Transactional(readOnly = false)
	public void save(ThirdPartyMailPara thirdPartyMailPara) {
		super.save(thirdPartyMailPara);
	}
	
	@Transactional(readOnly = false)
	public void delete(ThirdPartyMailPara thirdPartyMailPara) {
		super.delete(thirdPartyMailPara);
	}
	
}