/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountInterestChangeHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountSummaryDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectHoldInfoDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectInfoDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectReviewHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectWindingUpReviewHisDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountInterestChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.CurrentProjectHoldInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectReviewHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectWindingUpReviewHis;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 活期项目信息Service
 * @author ydt
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true)
public class CurrentProjectInfoService extends CrudService<CurrentProjectInfoDao, CurrentProjectInfo> {
	@Autowired
	private CurrentProjectInfoDao currentProjectInfoDao;
	@Autowired
	private CurrentProjectReviewHisDao currentProjectReviewHisDao;
	@Autowired
	private CurrentProjectExecuteSnapshotDao currentProjectExecuteSnapshotDao;
	@Autowired
	private CurrentProjectWindingUpReviewHisDao currentProjectWindingUpReviewHisDao;
	@Autowired
	private CurrentProjectHoldInfoDao currentProjectHoldInfoDao;
	@Autowired
	private CurrentAccountSummaryDao currentAccountSummaryDao;
	@Autowired
	private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;
	@Autowired
	private CurrentAccountInterestChangeHisDao currentAccountInterestChangeHisDao;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	
	public CurrentProjectInfo get(String id) {
		return super.get(id);
	}
	
	public List<CurrentProjectInfo> findList(CurrentProjectInfo currentProjectInfo) {
		return super.findList(currentProjectInfo);
	}
	
	public Page<CurrentProjectInfo> findPage(Page<CurrentProjectInfo> page, CurrentProjectInfo currentProjectInfo) {
		page.setOrderBy(page.getOrderBy() == null || "".equals(page.getOrderBy())?" a.create_dt desc ":page.getOrderBy());
		return super.findPage(page, currentProjectInfo);
	}
	
	/**
	 * 列表信息
	 * @param rate
	 * @param status
	 * @param pageSearchBean
	 * @return
	 */
	public Page<CurrentProjectInfo> searchPage(String rate, String status, PageSearchBean pageSearchBean) {
		
		return searchPage( rate,  status,pageSearchBean.getPageNo(),ProjectConstant.FRONT_PAGE_SIZE,null);
	}
	
	public Page<CurrentProjectInfo> searchPage(String rate, String status,Integer pageNumber,Integer pageSize,String orderBy) {
		Page<CurrentProjectInfo> page = new Page<CurrentProjectInfo>(pageNumber, pageSize);
		page.setOrderBy(orderBy);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("rate", rate);
		map.put("status", status);
		page.setList(dao.searchList(map));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(CurrentProjectInfo currentProjectInfo) {
		super.save(currentProjectInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CurrentProjectInfo currentProjectInfo) {
		super.delete(currentProjectInfo);
	}

	public CurrentProjectInfo getCurrentProjectInfoByInfo(CurrentProjectInfo currentProjectInfo) {
		
		return currentProjectInfoDao.getCurrentProjectInfoByInfo(currentProjectInfo);
	}
	
	/**
	 * 得到最新的投标中的活期项目
	 * @return
	 */
	public CurrentProjectInfo getFirstTenderingCurrentProjectInfo() {
		Page<CurrentProjectInfo> page = new Page<CurrentProjectInfo>(1, 1);
		page.setOrderBy(" a.review_dt desc");
		CurrentProjectInfo queryEntity = new CurrentProjectInfo();
		queryEntity.setStatus(CurrentProjectConstant.CURRENT_PROJECT_STATUS_TENDERING);
		page = this.findPage(page, queryEntity);
		return Collections3.getFirst(page.getList());
	}
	
//	public Page<CurrentProjectInfo> getTenderingCurrentProjectInfos(Page<CurrentProjectInfo> page){
//		page.setOrderBy(" a.review_dt desc");
//		CurrentProjectInfo queryEntity = new CurrentProjectInfo();
//		queryEntity.setStatus(CurrentProjectConstant.CURRENT_PROJECT_STATUS_TENDERING);
//		page = this.findPage(page, queryEntity);
//		return page;
//	}
	

	
	@Transactional(readOnly = false)
	public void review(CurrentProjectInfo currentProjectInfo) {
		//更新活期产品
		CurrentProjectInfo entity = currentProjectInfoDao.get(currentProjectInfo);
		entity.setStatus(CurrentProjectConstant.CURRENT_PROJECT_STATUS_REVIEW_PASS.equals(currentProjectInfo.getStatus())?
				CurrentProjectConstant.CURRENT_PROJECT_STATUS_REVIEW_PASS
				:CurrentProjectConstant.CURRENT_PROJECT_STATUS_CREATED);
		entity.setReviewRemark(currentProjectInfo.getReviewRemark());
		entity.setReviewDt(new Date());
		String userId=UserUtils.getUser().getId();
		entity.setReviewUserId(new Long(userId));
		currentProjectInfoDao.update(entity);
		
		//添加活期项目审批历史
		CurrentProjectReviewHis currentProjectReviewHis = new CurrentProjectReviewHis();
		currentProjectReviewHis.setProjectId(Long.valueOf(entity.getId()));
		currentProjectReviewHis.setReviewDt(new Date());
		currentProjectReviewHis.setReviewRemark(currentProjectInfo.getReviewRemark());
		currentProjectReviewHis.setReviewUserId(entity.getReviewUserId());
		currentProjectReviewHis.setReviewResult(entity.getStatus());
		currentProjectReviewHisDao.insert(currentProjectReviewHis);
		
		//如果审批通过
		if(CurrentProjectConstant.CURRENT_PROJECT_STATUS_REVIEW_PASS.equals(entity.getStatus())){
			//添加执行快照
			CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot = new CurrentProjectExecuteSnapshot();
			currentProjectExecuteSnapshot.setProjectId(Long.valueOf(entity.getId()));
			currentProjectExecuteSnapshot.setHasFinancedMoney(0.0);
			currentProjectExecuteSnapshot.setRealPrincipal(0.0);
			currentProjectExecuteSnapshot.setHasRepaidMoney(0.0);
			currentProjectExecuteSnapshot.setHasRedeemInterest(0.0);
			
			currentProjectExecuteSnapshotDao.insert(currentProjectExecuteSnapshot);
		}
		
		
	}

	@Transactional(readOnly = false)
	public void clear(CurrentProjectInfo currentProjectInfo) {
		CurrentProjectInfo entity = currentProjectInfoDao.get(currentProjectInfo);
		entity.setWindingUpStatus(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEWING);
		entity.setWindingUpApplyDt(new Date());
		entity.setWindingUpApplyReason(currentProjectInfo.getWindingUpApplyReason());
		entity.setWindingUpApplyUserId(new Long(UserUtils.getUser().getId()));
		currentProjectInfoDao.update(entity);
	}

	@Transactional(readOnly = false)
	public void clearReview(CurrentProjectInfo currentProjectInfo) {
		//更新活期产品
		CurrentProjectInfo entity = currentProjectInfoDao.get(currentProjectInfo);
		
		
		
		entity.setWindingUpStatus(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS.equals(currentProjectInfo.getWindingUpStatus())?
				CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS
				:CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_NO_APPLY);
		entity.setWindingUpReviewDt(new Date());
		entity.setWindingUpReviewRemark(currentProjectInfo.getWindingUpReviewRemark());
		entity.setWindingUpReviewUserId(new Long(UserUtils.getUser().getId()));
		currentProjectInfoDao.update(entity);
		
		//添加活期产品清盘审批历史
		CurrentProjectWindingUpReviewHis CurrentProjectWindingUpReviewHis = new CurrentProjectWindingUpReviewHis();
		CurrentProjectWindingUpReviewHis.setProjectId(Long.valueOf(entity.getId()));
		CurrentProjectWindingUpReviewHis.setReviewDt(new Date());
		CurrentProjectWindingUpReviewHis.setReviewRemark(currentProjectInfo.getWindingUpReviewRemark());
		CurrentProjectWindingUpReviewHis.setReviewUserId(new Long(UserUtils.getUser().getId()));
		CurrentProjectWindingUpReviewHis.setReviewResult(entity.getWindingUpStatus());
		currentProjectWindingUpReviewHisDao.insert(CurrentProjectWindingUpReviewHis);
	}

	public List<CurrentProjectInfo> querySimpleList(CurrentProjectInfo qa) {
		return currentProjectInfoDao.querySimpleList(qa);
	}

	/**
	 * 根据windingUpStatus查找列表
	 * @param windingUpStatus
	 * @return
	 */
	public List<CurrentProjectInfo> findListByWindingUpStatus(String windingUpStatus) {
		return dao.findListByWindingUpStatus(windingUpStatus);
	}

	/**
	 * 清盘
	 * @param currentProjectInfo
	 */
	@Transactional(readOnly = false)
	public void doWindingUp(CurrentProjectInfo currentProjectInfo, String opTerm) {
		if(!CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS.equals(currentProjectInfo.getWindingUpStatus())) {
			throw new ServiceException("wrong winding up status when current project do winding up.");
		}
		currentProjectInfoDao.updateByWindingUp(currentProjectInfo.getId());
		List<CurrentProjectHoldInfo> currentProjectHoldInfoList = currentProjectHoldInfoDao.findListByProjectIdAndStatus(currentProjectInfo.getId(), CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		currentProjectHoldInfoList = currentProjectHoldInfoList == null ? new ArrayList<CurrentProjectHoldInfo>() : currentProjectHoldInfoList;
		for(CurrentProjectHoldInfo currentProjectHoldInfo : currentProjectHoldInfoList) {
			double deltaPrincipal = NumberUtils.sub(currentProjectHoldInfo.getPrincipal(), currentProjectHoldInfo.getApplyRedeemPrincipal());
			double deltaInterestWithTwoScale = NumberUtils.formatWithTwoScale(currentProjectHoldInfo.getInterest());
			if(deltaPrincipal > 0 || deltaInterestWithTwoScale > 0) {
				currentAccountSummaryDao.updateByWindingUp(currentProjectHoldInfo.getAccountId(), deltaPrincipal, deltaInterestWithTwoScale);
				currentProjectHoldInfoDao.updateByWindingUp(currentProjectHoldInfo.getId());
				
				String requestNo = yeepayCommonHandler.currentProjectRepayMoney(currentProjectHoldInfo.getProjectId(),
						currentProjectHoldInfo.getAccountId(), NumberUtils.add(deltaPrincipal, deltaInterestWithTwoScale),
						ProjectConstant.CHANGE_TYPE_BALANCE_CURRENT_PROJECT_WINDING_UP,
						currentProjectInfo.getName() + "--" + CurrentProjectConstant.PRINCIPAL_CHANGE_REASON_WINDING_UP, opTerm);
				
				if(deltaPrincipal > 0) {
					CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis = new CurrentAccountPrincipalChangeHis();
					currentAccountPrincipalChangeHis.setAccountId(currentProjectHoldInfo.getAccountId());
					currentAccountPrincipalChangeHis.setProjectId(currentProjectHoldInfo.getProjectId());
					currentAccountPrincipalChangeHis.setChangeType(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_WINDING_UP);
					currentAccountPrincipalChangeHis.setChangeValue(-deltaPrincipal);
					currentAccountPrincipalChangeHis.setChangeReason(CurrentProjectConstant.PRINCIPAL_CHANGE_REASON_WINDING_UP);
					currentAccountPrincipalChangeHis.setStatus(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_NORMAL);
					currentAccountPrincipalChangeHis.setOpDt(new Date());
					currentAccountPrincipalChangeHis.setOpTerm(opTerm);
					currentAccountPrincipalChangeHis.setThirdAccountRequestNo(requestNo);
					currentAccountPrincipalChangeHisDao.insert(currentAccountPrincipalChangeHis);
				}
				if(deltaInterestWithTwoScale > 0) {
					CurrentAccountInterestChangeHis currentAccountInterestChangeHis = new CurrentAccountInterestChangeHis();
					currentAccountInterestChangeHis.setAccountId(currentProjectHoldInfo.getAccountId());
					currentAccountInterestChangeHis.setProjectId(currentProjectHoldInfo.getProjectId());
					currentAccountInterestChangeHis.setChangeType(CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_WINDING_UP);
					currentAccountInterestChangeHis.setChangeValue(-deltaInterestWithTwoScale);
					currentAccountInterestChangeHis.setChangeReason(CurrentProjectConstant.INTEREST_CHANGE_REASON_WINDING_UP);
					currentAccountInterestChangeHis.setOpDt(new Date());
					currentAccountInterestChangeHis.setOpTerm(opTerm);
					currentAccountInterestChangeHis.setThirdAccountRequestNo(requestNo);
					currentAccountInterestChangeHisDao.insert(currentAccountInterestChangeHis);
				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void updateAutoRepay(String projectId, String isAutoRepay){
		currentProjectInfoDao.updateAutoRepay(projectId, isAutoRepay);
	}

	/**
	 * 将处于"审批通过"状态 且到发布时间的项目状态设置为"投标中"
	 */
	@Transactional(readOnly = false)
	public void updateStatusToInvestmenting() {
		dao.updateStatusToInvestmenting();
	}

	/**
	 * 将处于"投标中"状态 且到投标截止时间的项目状态设置为"投标结束"
	 */
	@Transactional(readOnly = false)
	public void updateStatusToInvestmentOver() {
		dao.updateStatusToInvestmentOver();
	}
	
}