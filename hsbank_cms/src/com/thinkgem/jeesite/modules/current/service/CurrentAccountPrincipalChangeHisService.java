/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.to.MyCurChangeHisResp;
import com.thinkgem.jeesite.modules.api.to.PageResponse;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountInterestChangeHisDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountInterestChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectHoldInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.api.PageBean;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 活期账户本金变更历史Service
 * @author ydt
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true)
public class CurrentAccountPrincipalChangeHisService extends CrudService<CurrentAccountPrincipalChangeHisDao, CurrentAccountPrincipalChangeHis> {

	@Autowired
	private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;

	public CurrentAccountPrincipalChangeHis get(String id) {
		return super.get(id);
	}
	
	/**
	 * 根据第三方流水号获取信息
	 * @param thirdPartyOrder
	 * @param changeType
	 * @return
	 */
	public CurrentAccountPrincipalChangeHis getByThirdPartyOrder(String thirdPartyOrder, String changeType){
		return dao.getByThirdPartyOrder(thirdPartyOrder, changeType);
	}
	
	/**
	 * 根据id获取投资列表信息
	 * @param projectId
	 * @param changeType
	 * @param status
	 * @return
	 */
	public List<CurrentAccountPrincipalChangeHis> getPrincipalChangeHisList(String projectId, String changeType, String status){
		return dao.getPrincipalChangeHisList(projectId, changeType, status);
	}
	
	/**
	 * 账户总览我的活花生
	 * @param accountId
	 * @param changeType
	 * @param status
	 * @param limit
	 * @return
	 */
	public List<CurrentAccountPrincipalChangeHis> getMyPeanutList(Long accountId, String changeType, String status, int limit){
		return dao.getMyPeanutList(accountId, changeType, status, limit);
	}
	
	public List<CurrentAccountPrincipalChangeHis> findList(CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis) {
		return super.findList(currentAccountPrincipalChangeHis);
	}
	
	public Page<CurrentAccountPrincipalChangeHis> findPage(Page<CurrentAccountPrincipalChangeHis> page, CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis) {
		page.setOrderBy(" a.op_dt desc ");
		return super.findPage(page, currentAccountPrincipalChangeHis);
	}
	
	@Transactional(readOnly = false)
	public void save(CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis) {
		super.save(currentAccountPrincipalChangeHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis) {
		super.delete(currentAccountPrincipalChangeHis);
	}

	public Integer getCount(CurrentAccountPrincipalChangeHis queryEntity) {
		
		return currentAccountPrincipalChangeHisDao.getCount(queryEntity);
	}
	
	/**
	 * 投资列表(转入)
	 * @param accountId
	 * @param projectId
	 * @param changeType
	 * @param pageSearchBean
	 * @return
	 */
	public Page<CurrentAccountPrincipalChangeHis> searchMyPrincipalPageList(Long accountId,String projectId, String changeType, PageSearchBean pageSearchBean) {
		Page<CurrentAccountPrincipalChangeHis> page = new Page<CurrentAccountPrincipalChangeHis>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("accountId", accountId);
		map.put("projectId", projectId);
		map.put("changeType", changeType);
		page.setList(dao.searchMyPrincipalPageList(map));
		return page;
	}
	
	/**
	 * 转出列表(本金、利息)
	 * @param accountId
	 * @param projectId
	 * @param changeType
	 * @param pageSearchBean
	 * @return
	 */
	public Page<CurrentAccountPrincipalChangeHis> searchMyPrincipalAndInterestPageList(Long accountId,String projectId, String changeType, PageSearchBean pageSearchBean) {
		Page<CurrentAccountPrincipalChangeHis> page = new Page<CurrentAccountPrincipalChangeHis>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("accountId", accountId);
		map.put("projectId", projectId);
		map.put("changeType", changeType);
		page.setList(dao.searchMyPrincipalAndInterestPageList(map));
		return page;
	}

	/**
	 *我的投资-活期理财(每日信息分页列表)
	 * @param accountId
	 * @param projectId
	 * @param type
	 * @param page
     * @return
     */
	public PageResponse findMyChangeHisPageList(Long accountId, String projectId, String type, Page<CurrentAccountPrincipalChangeHis> page){
		PageResponse<MyCurChangeHisResp> pageResponse = new PageResponse<MyCurChangeHisResp>();
		List<MyCurChangeHisResp> changeHisRespList = new ArrayList<MyCurChangeHisResp>();
		List<CurrentAccountPrincipalChangeHis> list = new ArrayList<CurrentAccountPrincipalChangeHis>();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("accountId", accountId);
		map.put("projectId", projectId);
		if(ApiConstant.CURRENT_MY_ACCOUNT_SEARCH_TYPE_GET_INTEREST.equals(type)){
            //收息
			String changeType = CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST;
			map.put("changeType", changeType);
			list = dao.searchMyGetInterestPageList(map);
		}else if(ApiConstant.CURRENT_MY_ACCOUNT_SEARCH_TYPE_INVESTMENT.equals(type)){
			//投资
			String changeType = CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT;
			map.put("changeType", changeType);
			list = dao.searchMyPrincipalPageList(map);

		}else if(ApiConstant.CURRENT_MY_ACCOUNT_SEARCH_TYPE_REDEEM.equals(type)){
			//赎回
			String changeType = CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_REDEEM;
			map.put("changeType", changeType);
			list = dao.searchMyPrincipalAndInterestPageList(map);

		}
		page.setList(list);
		for(CurrentAccountPrincipalChangeHis cHis:list){
			MyCurChangeHisResp resp = new MyCurChangeHisResp();
			resp.setOpDate(DateUtils.formatDateTime(cHis.getOpDt()));
			resp.setAmount(cHis.getChangeValue());
			resp.setRemarks(cHis.getChangeReason());
			changeHisRespList.add(resp);
		}
		pageResponse.setCount(new Long(page.getCount()).intValue());
		pageResponse.setResultList(changeHisRespList);
		return pageResponse;

	}
	
	
	/**
	 * 投资列表(投资/赎回)
	 * @param accountId
	 * @param projectId
	 * @param changeType
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<CurrentAccountPrincipalChangeHis> getPrincipalPageList(Long accountId,String projectId, String changeType, Integer pageNumber, Integer pageSize) {
		Map<String, Object> map = new HashMap<String,Object>();
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		map.put("startNumber", pageBean.getStartNumber());
		map.put("endNumber", pageBean.getEndNumber());
		map.put("accountId", accountId);
		map.put("projectId", projectId);
		map.put("changeType", changeType);
		return dao.getPrincipalPageList(map);
	}

	public Double getChangeValueSum(CurrentAccountPrincipalChangeHis queryEntity) {
		return currentAccountPrincipalChangeHisDao.getChangeValueSum(queryEntity);
	}
	
	/**
	 * 投资：冻结资金
	 * @param accountId
	 * @return
	 */
	public Double getFrozenAmount(Long accountId){
		return dao.getSumCurrentPrincipal(accountId, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_FREEZE);
	}
	
}