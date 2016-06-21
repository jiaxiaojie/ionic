/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.BankInfo;
import com.thinkgem.jeesite.modules.sys.dao.BankInfoDao;

/**
 * 银行信息Service
 * @author wanduanrui
 * @version 2015-11-17
 */
@Service
@Transactional(readOnly = true)
public class BankInfoService extends CrudService<BankInfoDao, BankInfo> {

	public BankInfo get(String id) {
		return super.get(id);
	}
	
	/**
	 * 根据银行代码查询信息
	 * @param bankCode
	 * @return
	 */
	public BankInfo getBankInfoByBankCode(String bankCode){
		return dao.getBankInfoByBankCode(bankCode);
	}
	
	public List<BankInfo> findList(BankInfo bankInfo) {
		return super.findList(bankInfo);
	}
	
	public Page<BankInfo> findPage(Page<BankInfo> page, BankInfo bankInfo) {
		return super.findPage(page, bankInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(BankInfo bankInfo) {
		super.save(bankInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(BankInfo bankInfo) {
		super.delete(bankInfo);
	}
	
}