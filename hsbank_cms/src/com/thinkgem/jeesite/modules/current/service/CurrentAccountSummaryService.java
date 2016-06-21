/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.service;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountSummaryDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectHoldInfoDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活期账户总览Service
 * @author ydt
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true)
public class CurrentAccountSummaryService extends CrudService<CurrentAccountSummaryDao, CurrentAccountSummary> {
	@Autowired
	private CurrentProjectExecuteSnapshotDao currentProjectExecuteSnapshotDao;
	@Autowired
	private CurrentProjectHoldInfoDao currentProjectHoldInfoDao;
	private static Double posValue =1.0;	//正差值
	private static Double negValue = -1.0;	//负差值
	
	public CurrentAccountSummary get(String id) {
		return super.get(id);
	}
	
	public List<CurrentAccountSummary> findList(CurrentAccountSummary currentAccountSummary) {
		return super.findList(currentAccountSummary);
	}
	
	public Page<CurrentAccountSummary> findPage(Page<CurrentAccountSummary> page, CurrentAccountSummary currentAccountSummary) {
		return super.findPage(page, currentAccountSummary);
	}
	
	@Transactional(readOnly = false)
	public void save(CurrentAccountSummary currentAccountSummary) {
		super.save(currentAccountSummary);
	}
	
	@Transactional(readOnly = false)
	public void delete(CurrentAccountSummary currentAccountSummary) {
		super.delete(currentAccountSummary);
	}

	public CurrentAccountSummary getByAccountId(long accountId) {
		return dao.getByAccountId(accountId);
	}
	
	/**
	 * 资金累计比较
	 */
	public void totalCapitalCompare(){
		Map<String,Object> summaryMap = dao.getStatCurrentPrincipalAndInterestMoney();
		//当前实际本金
		Double realPrincipal = currentProjectExecuteSnapshotDao.getStatRealPrincipal();
		//当前持有本金
		Double currentPrincipal = NumberUtil.toDouble(String.valueOf(summaryMap.get("currentPrincipal")), 0.0);
		//已产生的利息
		Double repaidMoney = currentProjectExecuteSnapshotDao.getStatRepaidMoney();
		//累计获取利息
		Double interestMoney = NumberUtil.toDouble(String.valueOf(summaryMap.get("interestMoney")), 0.0);
		if(realPrincipal.compareTo(currentPrincipal) !=0){
			logger.info("执行快照累计当前实际本金【" + realPrincipal + "】,活期账户当前持有本金【" + currentPrincipal + "】不相等");
		}
		if(repaidMoney.compareTo(interestMoney) > posValue || repaidMoney.compareTo(interestMoney) < negValue){
			logger.info("累计利息误差大于【"+posValue+"】元，执行快照累计已产生的利息【" + repaidMoney + "】,活期账户累计获取利息【" + interestMoney + "】");
		}
	}
	
	/**
	 * 组装活期账户总揽map数据
	 * @return
	 */
	public Map<String,Object> getAccountSummaryMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> list =dao.getStatCurrentPrincipalListByAccountId(map);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		for(Map<String,Object> objMap:list){
			Double currentPrincipal = NumberUtil.toDouble(String.valueOf(objMap.get("currentPrincipal")), 0.0);
			String accountId= String.valueOf(objMap.get("accountId"));
			dataMap.put(accountId, currentPrincipal);
		}
		return dataMap;
	}
	
	/**
	 * 资金明细比较
	 */
	public void detailCapitalCompare(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> list = currentProjectHoldInfoDao.getStatPrincipalListByAccountId(map);
		//活期账户总揽数据
		Map<String,Object> summaryMap = getAccountSummaryMap();
		for(Map<String,Object> objMap:list){
			Double principal = NumberUtil.toDouble(String.valueOf(objMap.get("principal")), 0.0);
			String accountId= String.valueOf(objMap.get("accountId"));
			Double currentPrincipal =NumberUtil.toDouble(String.valueOf(summaryMap.get(accountId)), 0.0);
			if(principal.compareTo(currentPrincipal) != 0){
				logger.info("账号【"+accountId+"】,活期产品持有本金【" + principal + "】,活期账户当前持有本金【" + currentPrincipal + "】不相等");
			}
		}
	}
}