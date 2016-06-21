/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.service;

import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.api.to.MyCurProjectInfoResp;
import com.thinkgem.jeesite.modules.api.to.PageResponse;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.*;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 活期产品持有信息Service
 * @author ydt
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true)
public class CurrentProjectHoldInfoService extends CrudService<CurrentProjectHoldInfoDao, CurrentProjectHoldInfo> {

	@Autowired
	private CurrentProjectRedemptionApplyDao currentProjectRedemptionApplyDao;
	@Autowired
	private CurrentAccountSummaryDao currentAccountSummaryDao;
	@Autowired
	private CurrentProjectInfoDao currentProjectInfoDao;
	@Autowired
	private CurrentAccountInterestChangeHisDao currentAccountInterestChangeHisDao;
	@Autowired
	private CurrentProjectRepayRecordDao currentProjectRepayRecordDao;
	@Autowired
	private CurrentProjectExecuteSnapshotDao currentProjectExecuteSnapshotDao;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	
	
	
	public CurrentProjectHoldInfo get(String id) {
		return super.get(id);
	}
	
	public List<CurrentProjectHoldInfo> findList(CurrentProjectHoldInfo currentProjectHoldInfo) {
		return super.findList(currentProjectHoldInfo);
	}
	
	/**
	 * 我的活花生
	 * @param accountId
	 * @param pageSearchBean
	 * @return
	 */
	public Page<CurrentProjectHoldInfo> searchMyPageList(Long accountId,PageSearchBean pageSearchBean) {
		Page<CurrentProjectHoldInfo> page = new Page<CurrentProjectHoldInfo>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("accountId", accountId);
		page.setList(dao.searchMyPageList(map));
		return page;
	}

	/**
	 * 我的活花生项目分页
	 * @param accountId
	 * @param page
     * @return
     */
	public PageResponse findMyPageList(Long accountId, Page<CurrentProjectHoldInfo> page){
		PageResponse<MyCurProjectInfoResp> pageResponse =  new PageResponse<MyCurProjectInfoResp>();
		List<MyCurProjectInfoResp> holdInfoList = new ArrayList<MyCurProjectInfoResp>();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("accountId", accountId);
		List<CurrentProjectHoldInfo> list = dao.searchMyPageList(map);
		page.setList(list);
		for(CurrentProjectHoldInfo holdInfo: list){
			MyCurProjectInfoResp myCurResp = new MyCurProjectInfoResp();
			myCurResp.setProjectId(holdInfo.getProjectId());
			myCurResp.setProjectName(holdInfo.getCurrentProjectInfo().getName());
			myCurResp.setAnnualizedRate(holdInfo.getCurrentProjectInfo().getRate());
			myCurResp.setAmount(holdInfo.getPrincipal());
			myCurResp.setApplyRedeemPrincipal(holdInfo.getApplyRedeemPrincipal());
			myCurResp.setReceivedProfit(holdInfo.getTotalProfit());
			myCurResp.setInterest(holdInfo.getInterest());
			String status= StringUtil.dealString(holdInfo.getCurrentProjectInfo().getStatus());
			myCurResp.setStatus(Integer.parseInt(status));
			myCurResp.setStatusName(DictUtils.getDictLabel(status, "current_project_status", ""));
			holdInfoList.add(myCurResp);
		}
		pageResponse.setCount(new Long(page.getCount()).intValue());
		pageResponse.setResultList(holdInfoList);
		return pageResponse;
	}


	/**
	 * 我的所有火花生
	 * @param accountId
	 * @return
	 */
	public List<CurrentProjectHoldInfo> searchMyList(Long accountId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		return dao.searchMyPageList(map);
	}
	
	/**
	 * 我的活期持有信息
	 * @param accountId
	 * @param projectId
	 * @param status
	 * @return
	 */
	public CurrentProjectHoldInfo getMyCurrentProjectHoldInfo(long accountId, String projectId, String status){
		return dao.getMyCurrentProjectHoldInfo(accountId, projectId, status);
	}
	
	public Page<CurrentProjectHoldInfo> findPage(Page<CurrentProjectHoldInfo> page, CurrentProjectHoldInfo currentProjectHoldInfo) {
		return super.findPage(page, currentProjectHoldInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CurrentProjectHoldInfo currentProjectHoldInfo) {
		super.save(currentProjectHoldInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CurrentProjectHoldInfo currentProjectHoldInfo) {
		super.delete(currentProjectHoldInfo);
	}

	/**
	 * 赎回本金操作
	 * @param accountId
	 * @param projectId
	 * @param principal
	 * @param applyTerm
	 */
	@Transactional(readOnly = false)
	public Map<String,Object> doRedeemPrincipal(Long accountId, Long projectId, Double principal, String applyTerm) {
		Map<String,Object> result = new HashMap<String,Object>();
		if(projectId == null || NumberUtils.compare(projectId, 0L) < 1
				|| principal == null || NumberUtils.compare(principal, 0d) != 1 || NumberUtils.getScale(principal) > 2) {
			result.put("isSuccess", false);
			result.put("message", "参数错误");
			return result;
		}
		if(!NumberUtils.isIntMulOfOne(principal)) {
			result.put("isSuccess", false);
			result.put("message", "赎回本金必须为1的整数倍");
			return result;
		}
		CurrentProjectHoldInfo currentProjectHoldInfo = dao.getByAccountIdAndProjectId(accountId, projectId, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		if(currentProjectHoldInfo == null
				|| NumberUtils.compare(principal, NumberUtils.sub(currentProjectHoldInfo.getPrincipal(), currentProjectHoldInfo.getApplyRedeemPrincipal())) == 1) {
			result.put("isSuccess", false);
			result.put("message", "申请赎回金额大于可赎回金额");
			return result;
		}
		BigDecimal oneDayMaxMoney = new BigDecimal(CurrentProjectConstant.ONE_DAY_MAX_REDEEM_MONEY);
		oneDayMaxMoney = oneDayMaxMoney.setScale(0,   BigDecimal.ROUND_HALF_UP);
		if(NumberUtils.compare(principal, CurrentProjectConstant.ONE_DAY_MAX_REDEEM_MONEY) == 1) {
			result.put("isSuccess", false);
			result.put("message", "单次申请赎回金额大于单日" + oneDayMaxMoney + "元最大赎回金额");
			return result;
		}
		List<CurrentProjectRedemptionApply> applyList = currentProjectRedemptionApplyDao.findListByAccountId(accountId, new Date(),
				new String[]{
				/*CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_DOING,*/
				CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_REVIEW_PASS,
				CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_REDEEMED
			});
		double totalHasRedeemAmount = 0;
		applyList = applyList == null ? new ArrayList<CurrentProjectRedemptionApply>() : applyList;
		for(CurrentProjectRedemptionApply apply : applyList) {
			totalHasRedeemAmount = NumberUtils.add(totalHasRedeemAmount, apply.getRedeemPrincipal());
		}
		if(NumberUtils.compare(NumberUtils.add(principal, totalHasRedeemAmount), CurrentProjectConstant.ONE_DAY_MAX_REDEEM_MONEY) == 1) {
			result.put("isSuccess", false);
			result.put("message", "当日累计申请赎回金额大于单日" + oneDayMaxMoney + "元最大赎回金额");
			return result;
		}
		dao.doRedeemPrincipal(accountId, projectId, principal);
		CurrentProjectRedemptionApply currentProjectRedemptionApply = new CurrentProjectRedemptionApply();
		currentProjectRedemptionApply.setHoldId(Long.parseLong(currentProjectHoldInfo.getId()));
		currentProjectRedemptionApply.setRedeemPrincipal(principal);
		currentProjectRedemptionApply.setRedeemInterest(0d);
		currentProjectRedemptionApply.setApplyTerm(applyTerm);
		currentProjectRedemptionApply.setApplyDt(new Date());
		currentProjectRedemptionApply.setReviewDt(new Date());
		//用户申请赎回后直接将申请状态改为申请通过（以前为申请中）
		/*currentProjectRedemptionApply.setStatus(CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_DOING);*/
		currentProjectRedemptionApply.setStatus(CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_REVIEW_PASS);
		currentProjectRedemptionApplyDao.insert(currentProjectRedemptionApply);
		CurrentProjectHoldInfo cphi = dao.getByAccountIdAndProjectId(accountId, projectId, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		if(NumberUtils.compare(cphi.getPrincipal(), cphi.getApplyRedeemPrincipal()) == -1) {
			throw new ServiceException("data error.");
		}
		result.put("isSuccess", true);
		// result.put("message", "申请赎回成功");
		//禅道需求675,修改赎回本金成功提示语。
		result.put("message", "本次赎回资金预计半小时内转入您的可用余额。");
		return result;
	}

	/**
	 * 获取状态为status的CurrentProjectHoldInfo列表
	 * @param status
	 * @return
	 */
	public List<CurrentProjectHoldInfo> findListByStatuses(String... statuses) {
		return dao.findListByStatuses(statuses);
	}

	/**
	 * 付每日产生的利息
	 */
	@Transactional(readOnly = false)
	public void repayInterest(String term) {
		List<CurrentProjectHoldInfo> holdList = dao.findListByStatuses(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		holdList = holdList == null ? new ArrayList<CurrentProjectHoldInfo>() : holdList;
		Map<Long,Double> projectIdRepayInterestMap = new HashMap<Long,Double>();
		for(CurrentProjectHoldInfo currentProjectHoldInfo : holdList) {
			long todayRepayCountWithHold = currentAccountInterestChangeHisDao.getCountByAccountIdAndProjectId(currentProjectHoldInfo.getAccountId(),
					currentProjectHoldInfo.getProjectId(), CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST, new Date());
			if(todayRepayCountWithHold > 0) {
				continue;
			}
			double shouldReayInterest = NumberUtils.formatWithScale(NumberUtils.mul(
						NumberUtils.div(currentProjectInfoDao.get(currentProjectHoldInfo.getProjectId() + "").getRate(), new Double(CurrentProjectConstant.DAYS_IN_YEAR)),
						NumberUtils.sub(currentProjectHoldInfo.getPrincipal(), currentProjectHoldInfo.getApplyRedeemPrincipal())), 4);
			if(NumberUtils.compare(shouldReayInterest, 0d) == 1) {
				dao.updateByRepay(currentProjectHoldInfo.getId(), shouldReayInterest);
				currentAccountSummaryDao.updateByRepay(currentProjectHoldInfo.getAccountId(), shouldReayInterest);
				CurrentAccountInterestChangeHis currentAccountInterestChangeHis = new CurrentAccountInterestChangeHis();
				currentAccountInterestChangeHis.setAccountId(currentProjectHoldInfo.getAccountId());
				currentAccountInterestChangeHis.setProjectId(currentProjectHoldInfo.getProjectId());
				currentAccountInterestChangeHis.setChangeValue(shouldReayInterest);
				currentAccountInterestChangeHis.setChangeType(CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST);
				currentAccountInterestChangeHis.setOpTerm(term);
				currentAccountInterestChangeHis.setOpDt(new Date());
				currentAccountInterestChangeHis.setChangeReason("收益");
				currentAccountInterestChangeHisDao.insert(currentAccountInterestChangeHis);
			}
			projectIdRepayInterestMap.put(currentProjectHoldInfo.getProjectId(),
					projectIdRepayInterestMap.get(currentProjectHoldInfo.getProjectId()) == null
						? shouldReayInterest
						: NumberUtils.add(projectIdRepayInterestMap.get(currentProjectHoldInfo.getProjectId()), shouldReayInterest));
		}
		for(long projectId : projectIdRepayInterestMap.keySet()) {
			if(projectIdRepayInterestMap.get(projectId) != null) {
				CurrentProjectRepayRecord currentProjectRepayRecord = new CurrentProjectRepayRecord();
				currentProjectRepayRecord.setProjectId(projectId);
				currentProjectRepayRecord.setPrincipal(0d);
				currentProjectRepayRecord.setInterest(projectIdRepayInterestMap.get(projectId));
				currentProjectRepayRecord.setRepayDt(new Date());
				currentProjectRepayRecordDao.insert(currentProjectRepayRecord);
				currentProjectExecuteSnapshotDao.updateByRepay(projectId, projectIdRepayInterestMap.get(projectId));
			}
		}
	}

	/**
	 * 提取利息操作
	 * @param accountId
	 * @param projectId
	 * @param interest
	 * @param opTerm
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> doPollInterest(long accountId, Long projectId, Double interest, String opTerm) {
		Map<String,Object> result = new HashMap<String,Object>();
		if(projectId == null || NumberUtils.compare(projectId, 0L) < 1
				|| interest == null || NumberUtils.compare(interest, 0d) != 1 || NumberUtils.getScale(interest) > 2) {
			result.put("isSuccess", false);
			result.put("message", "参数错误");
			return result;
		}
		CurrentProjectInfo currentProjectInfo = currentProjectInfoDao.get(String.valueOf(projectId));
		CurrentProjectHoldInfo currentProjectHoldInfo = dao.getByAccountIdAndProjectId(accountId, projectId, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		if(currentProjectHoldInfo == null
				|| NumberUtils.compare(interest, currentProjectHoldInfo.getInterest()) == 1) {
			result.put("isSuccess", false);
			result.put("message", "提取利息大于持有利息");
			return result;
		}
		currentAccountSummaryDao.updateByPollInterest(accountId, interest);
		dao.updateByPollInterest(accountId, projectId, interest);
		
		currentProjectExecuteSnapshotDao.updateByPollInterest(projectId, interest);
		
		CurrentProjectHoldInfo cphi = dao.getByAccountIdAndProjectId(accountId, projectId, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		if(NumberUtils.compare(cphi.getInterest(), 0d) == -1) {
			throw new ServiceException("data error.");
		}
		String requestNo = yeepayCommonHandler.currentProjectRepayMoney(projectId, accountId, interest,
				ProjectConstant.CHANGE_TYPE_BALANCE_CURRENT_PROJECT_POLL_INTEREST,
				currentProjectInfo.getName() + "--" +CurrentProjectConstant.INTEREST_CHANGE_REASON_POLL, opTerm);
		
		CurrentAccountInterestChangeHis currentAccountInterestChangeHis = new CurrentAccountInterestChangeHis();
		currentAccountInterestChangeHis.setAccountId(accountId);
		currentAccountInterestChangeHis.setProjectId(projectId);
		currentAccountInterestChangeHis.setChangeValue(-interest);
		currentAccountInterestChangeHis.setChangeType(CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_REDEEM);
		currentAccountInterestChangeHis.setChangeReason(CurrentProjectConstant.INTEREST_CHANGE_REASON_POLL);
		currentAccountInterestChangeHis.setOpTerm(opTerm);
		currentAccountInterestChangeHis.setOpDt(new Date());
		currentAccountInterestChangeHis.setThirdAccountRequestNo(requestNo);
		currentAccountInterestChangeHisDao.insert(currentAccountInterestChangeHis);
		
		result.put("isSuccess", true);
		result.put("message", "提取利息成功");
		return result;
	}

	/**
	 * 查询到accountId用户的持有列表
	 * @return
	 */
	public List<CurrentProjectHoldInfo> findListByAccountId(long accountId) {
		return dao.findListByAccountId(accountId);
	}

	/**
	 * 获取用户的某一项目信息
	 * @param accountId
	 * @param projectId
	 * @param status
	 * @return
	 */
	public CurrentProjectHoldInfo getByAccountIdAndProjectId(long accountId, Long projectId, String status) {
		return dao.getByAccountIdAndProjectId(accountId, projectId, status);
	}

	
}