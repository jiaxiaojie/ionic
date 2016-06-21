/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.api.PageBean;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountInterestChangeHisDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountInterestChangeHis;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 活期账户利息变更历史Service
 * @author ydt
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true)
public class CurrentAccountInterestChangeHisService extends CrudService<CurrentAccountInterestChangeHisDao, CurrentAccountInterestChangeHis> {

	public CurrentAccountInterestChangeHis get(String id) {
		return super.get(id);
	}
	
	public List<CurrentAccountInterestChangeHis> findList(CurrentAccountInterestChangeHis currentAccountInterestChangeHis) {
		return super.findList(currentAccountInterestChangeHis);
	}
	
	public Page<CurrentAccountInterestChangeHis> findPage(Page<CurrentAccountInterestChangeHis> page, CurrentAccountInterestChangeHis currentAccountInterestChangeHis) {
		page.setOrderBy(" a.op_dt desc ");
		return super.findPage(page, currentAccountInterestChangeHis);
	}
	
	@Transactional(readOnly = false)
	public void save(CurrentAccountInterestChangeHis currentAccountInterestChangeHis) {
		super.save(currentAccountInterestChangeHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(CurrentAccountInterestChangeHis currentAccountInterestChangeHis) {
		super.delete(currentAccountInterestChangeHis);
	}
	
	/**
	 * 根据账号、项目id获取昨日收益
	 * @param accountId
	 * @param changeType
	 * @param date
	 * @return
	 */
	public Double getYesterdayProfitByAccountIdAndProjectId(Long accountId, String projectId, String changeType){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("projectId", projectId);
		map.put("accountId", accountId);
		map.put("changeType", changeType);
		map.put("date", new Date());
		return dao.getYesterdayProfit(map);
	}
	
	/**
	 * 根据账号、项目id获取累计收益
	 * @param accountId
	 * @param projectId
	 * @param changeType
	 * @return
	 */
	public Double getSumProfitByAccountIdAndProjectId(Long accountId, String projectId, String changeType){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("projectId", projectId);
		map.put("accountId", accountId);
		map.put("changeType", changeType);
		return dao.getSumProfit(map);
	}
	
	/**
	 * 根据账号获取昨日收益
	 * @param accountId
	 * @param changeType
	 * @param date
	 * @return
	 */
	public Double getYesterdayProfitByAccountId(Long accountId, String changeType){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("changeType", changeType);
		map.put("date", new Date());
		return dao.getYesterdayProfit(map);
	}
	
	/**
	 * 每日收益列表
	 * @param accountId
	 * @param projectId
	 * @param changeType
	 * @param pageSearchBean
	 * @return
	 */
	public Page<CurrentAccountInterestChangeHis> searchMyInterestPageList(Long accountId,String projectId, String changeType, PageSearchBean pageSearchBean) {
		Page<CurrentAccountInterestChangeHis> page = new Page<CurrentAccountInterestChangeHis>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("accountId", accountId);
		map.put("projectId", projectId);
		map.put("changeType", changeType);
		page.setList(dao.searchMyInterestPageList(map));
		return page;
	}
	
	/**
	 * 收益分页列表
	 * @param accountId
	 * @param projectId
	 * @param changeType
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<CurrentAccountInterestChangeHis> getInterestPageList(Long accountId,String projectId, String changeType, Integer pageNumber, Integer pageSize) {
		Map<String, Object> map = new HashMap<String,Object>();
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		map.put("startNumber", pageBean.getStartNumber());
		map.put("endNumber", pageBean.getEndNumber());
		map.put("accountId", accountId);
		map.put("projectId", projectId);
		map.put("changeType", changeType);
		return dao.getInterestPageList(map);
	}
	
}