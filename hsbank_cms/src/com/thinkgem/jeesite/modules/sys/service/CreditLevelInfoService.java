/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CreditLevelInfo;
import com.thinkgem.jeesite.modules.sys.dao.CreditLevelInfoDao;

/**
 * 信用等级信息Service
 * @author ydt
 * @version 2015-08-04
 */
@Service
@Transactional(readOnly = true)
public class CreditLevelInfoService extends CrudService<CreditLevelInfoDao, CreditLevelInfo> {

	public CreditLevelInfo get(String id) {
		return super.get(id);
	}
	
	public List<CreditLevelInfo> findList(CreditLevelInfo creditLevelInfo) {
		return super.findList(creditLevelInfo);
	}
	
	public Page<CreditLevelInfo> findPage(Page<CreditLevelInfo> page, CreditLevelInfo creditLevelInfo) {
		return super.findPage(page, creditLevelInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CreditLevelInfo creditLevelInfo) {
		super.save(creditLevelInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreditLevelInfo creditLevelInfo) {
		super.delete(creditLevelInfo);
	}

	/**
	 * 查询score所在范围内的信用等级信息
	 * @param score
	 * @return
	 */
	public CreditLevelInfo getByScore(Double score) {
		return dao.getByScore(score);
	}
	
}