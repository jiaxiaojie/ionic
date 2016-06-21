/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.credit.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.modules.entity.CreditBaseInfo;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;
import com.thinkgem.jeesite.modules.credit.CreditConstant;
import com.thinkgem.jeesite.modules.credit.dao.CreditBaseInfoDao;

/**
 * 债券基本信息Service
 * @author wanduanrui
 * @version 2016-03-29
 */
@Service
@Transactional(readOnly = true)
public class CreditBaseInfoService extends CrudService<CreditBaseInfoDao, CreditBaseInfo> {
	@Autowired
	private CreditBaseInfoDao creditBaseInfoDao;
	@Autowired
	ProjectBaseInfoDao projectBaseInfoDao;
	
	public CreditBaseInfo get(String id) {
		return super.get(id);
	}
	
	public List<CreditBaseInfo> findList(CreditBaseInfo creditBaseInfo) {
		return super.findList(creditBaseInfo);
	}
	
	public Page<CreditBaseInfo> findPage(Page<CreditBaseInfo> page, CreditBaseInfo creditBaseInfo) {
		page.setOrderBy("a.create_date desc");
		return super.findPage(page, creditBaseInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CreditBaseInfo creditBaseInfo) {
		super.save(creditBaseInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreditBaseInfo creditBaseInfo) {
		super.delete(creditBaseInfo);
	}

	public CreditBaseInfo getCreditBaseInfoByInfo(CreditBaseInfo creditBaseInfo) {
		
		return creditBaseInfoDao.getByEntity(creditBaseInfo);
	}

	@Transactional(readOnly = false)
	public void updateStatusByCurrentDate() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("currentDate", new Date());
		params.put("CREDIT_STATUS_RAISE_END", CreditConstant.CREDIT_STATUS_RAISE_END);
		creditBaseInfoDao.updateStatus(params);
	}
	
	/**
	 * 更新募集金额，在确保已经募集成功后执行
	 * 1.将募集中的金额减去
	 * 2.向线上已经募集金额中增加实际募集金额
	 * 3.如果实际募集金额小于预计募集金额，则将金额还回去
	 * @param creditId 债权id
	 * @param financeMoney 预计募集金额
	 * @param endFinanceMoney 实际募集金额
	 */
	@Transactional(readOnly = false)
	public void updateRaiseMoney(Long creditId, Double financeMoney, Double endFinanceMoney){
		if(creditId == null){
			return;
		}
		//更新募集中金额
		creditBaseInfoDao.addRaisingMoney(creditId, -1*financeMoney);
		//更新线上已募集金额
		creditBaseInfoDao.addRaisedMoneyOnLine(creditId, endFinanceMoney);
		//更新待募集金额
		addToRaiseMoney(creditId,financeMoney-endFinanceMoney);
	}

	/**
	 * 更新募集中金额
	 * 1.向募集中金额中加上当前预计募集金额
	 * 2.从待募集金额中减去当前预计募集金额
	 * @param creditId 债权id
	 * @param financeMoney 预计募集金额
	 */
	@Transactional(readOnly = false)
	public void addRaisingMoney(Long creditId, Double financeMoney) {
		if(creditId == null){
			return;
		}
		//更新募集中金额
		creditBaseInfoDao.addRaisingMoney(creditId, financeMoney);
		//更新待募集金额
		addToRaiseMoney(creditId,-1*financeMoney);
	}
	
	/**
	 * 更新待募集金额
	 * 1.如果更新后待募集金额大于等于0，则更新
	 * 2.如果更新后待募集金额等于0,且募集中金额为0，则将债权状态设置为结束
	 * @param creditId 债权id
	 * @param money 要增加的金额数
	 */
	@Transactional(readOnly = false)
	public void addToRaiseMoney(Long creditId, Double money) {
		if(creditId == null){
			return;
		}
		
		CreditBaseInfo creditBaseInfo = creditBaseInfoDao.get(creditId.toString());
		if(creditBaseInfo != null){
			Double toRaiseMoney = towMoneyAdd(creditBaseInfo.getToRaiseMoney(), money);
			
			
			if(toRaiseMoney >= 0){
				creditBaseInfoDao.addToRaiseMoney(creditId,money);
				
				//如果待募集金额为0,且募集中金额为0，则将债权状态变为结束
				if(toRaiseMoney == 0 && creditBaseInfo.getRaisingMoney() == 0){
					creditBaseInfoDao.updateStatusByIdAndStatus(creditId,CreditConstant.CREDIT_STATUS_RAISE_END);
				}
				
			}
			else{
				throw new ServiceException("债权待募集金额不足！");
			}
		}
		
	}
	

	
	/**
	 * 更新线上已募集金额
	 * @param creditId
	 * @param endFinanceMoney
	 */
	@Transactional(readOnly = false)
	public void addRaisedMoneyOnLine(Long creditId, Double endFinanceMoney) {
		if(creditId == null){
			return;
		}
		//更新线上已募集金额
		creditBaseInfoDao.addRaisedMoneyOnLine(creditId, endFinanceMoney);
		//更新待募集金额
		creditBaseInfoDao.addToRaiseMoney(creditId,-1*endFinanceMoney);
	}
	
	/**
	 * 更新线下已募集金额
	 * @param beforeCreditId
	 * @param money
	 */
	public void addRaisedMoneyBelowLine(Long beforeCreditId, Double money) {
		if(beforeCreditId == null){
			return;
		}
		creditBaseInfoDao.addRaisedMoneyBelowLine(beforeCreditId, money);
		//更新待募集金额
		addToRaiseMoney(beforeCreditId,-1*money);
	}
	
	
	private Double towMoneyAdd(Double money1, Double money2){
		Double result = 0d;
		
		if(money1 != null){
			result += money1;
		}
		
		if(money2 != null){
			result += money2;
		}
		
		return result;
	}
	
	
}