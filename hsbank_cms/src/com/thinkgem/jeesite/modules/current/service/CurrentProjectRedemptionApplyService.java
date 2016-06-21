/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.yeepay.DirectReqUtils;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoReq;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountSummaryDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectHoldInfoDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectInfoDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectRedemptionApplyDao;
import com.thinkgem.jeesite.modules.current.dao.DateInfoDao;
import com.thinkgem.jeesite.modules.current.dao.DemandRedemptionInformationRecordDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectHoldInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectRedemptionApply;
import com.thinkgem.jeesite.modules.entity.DemandRedemptionInformationRecord;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.repayment.RepaymentService;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 活期产品赎回申请Service
 * @author ydt
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true)
public class CurrentProjectRedemptionApplyService extends CrudService<CurrentProjectRedemptionApplyDao, CurrentProjectRedemptionApply> {
	
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private CurrentProjectInfoDao currentProjectInfoDao;
	@Autowired
	private DateInfoDao dateInfoDao;
	@Autowired
	private CurrentProjectRedemptionApplyDao currentProjectRedemptionApplyDao;
	@Autowired
	private CurrentAccountSummaryDao currentAccountSummaryDao;
	@Autowired
	private CurrentProjectHoldInfoDao currentProjectHoldInfoDao;
	@Autowired
	private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;
	@Autowired
	private CurrentProjectExecuteSnapshotDao currentProjectExecuteSnapshotDao;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	@Autowired
	LogTimerTaskService logTimerTaskService;
	@Autowired
	private RepaymentService repaymentService;
	@Autowired
	private DemandRedemptionInformationRecordDao demandRedemptionInformationRecordDao;
	@Autowired
	private DirectReqUtils directReqUtils;
	
	public CurrentProjectRedemptionApply get(String id) {
		return super.get(id);
	}
	
	public List<CurrentProjectRedemptionApply> findList(CurrentProjectRedemptionApply currentProjectRedemptionApply) {
		return super.findList(currentProjectRedemptionApply);
	}
	
	public Page<CurrentProjectRedemptionApply> findPage(Page<CurrentProjectRedemptionApply> page, CurrentProjectRedemptionApply currentProjectRedemptionApply) {
		page.setOrderBy(" a.apply_dt desc ");
		return super.findPage(page, currentProjectRedemptionApply);
	}
	
	@Transactional(readOnly = false)
	public void save(CurrentProjectRedemptionApply currentProjectRedemptionApply) {
		super.save(currentProjectRedemptionApply);
	}
	
	@Transactional(readOnly = false)
	public void delete(CurrentProjectRedemptionApply currentProjectRedemptionApply) {
		super.delete(currentProjectRedemptionApply);
	}
	
	
	
	/**
	 * 查询账号情况 接口3.1
	 * 
	 * @param platformUserNo
	 * @return
	 */
	private AccountInfoResp queryAccountInfo(String platformUserNo) {
		Date startDate;
		Date endDate;
		// 查询用户余额状态
		startDate = new Date();
		AccountInfoReq req = new AccountInfoReq();
		req.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		req.setPlatformUserNo(platformUserNo);
		String accountInfoResp = directReqUtils.dirReq(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_REQ,
				req.toReq(),
				YeepayConstant.PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_SERVICE);
		endDate = new Date();
		AccountInfoResp accountInfoRespObj = JaxbMapper.fromXml(
				accountInfoResp, AccountInfoResp.class);
		repaymentService.saveThirdPartLog("",
				YeepayConstant.PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_REQ,
				req.toReq(), startDate, accountInfoResp,
				accountInfoRespObj.getCode(), endDate);
		return accountInfoRespObj;
	}
	
	
	/**
	 * 将处于审批通过的赎回申请金额从平台打款给用户
	 */
	@Transactional(readOnly = false)
	public void doPayRedeem(CurrentProjectRedemptionApply currentProjectRedemptionApply) {
		if(shouldPayRedeem(currentProjectRedemptionApply.getStatus(), currentProjectRedemptionApply.getApplyDt())) {
			CurrentProjectHoldInfo currentProjectHoldInfo = currentProjectHoldInfoDao.get(String.valueOf(currentProjectRedemptionApply.getHoldId()));
			//如果活期账户余额不足以支付用户的活期提现要求，则系统记录错误信息，并进行定期重试直至用户活期提现成功为止。
			 //1.根据项目流水号查询融资人账户编号
			 Long borrowerAccountId=currentProjectInfoDao.selectBorrowerAccountIdByProjectId(currentProjectHoldInfo.getProjectId());
			 //2.根据融资人账户编号查询易宝账户平台编号
			 String platformUserNo=customerAccountDao.selectPlatformUserNoByBorrowerAccountId(borrowerAccountId);
			 //3. 查询用户资金状态
             AccountInfoResp accountInfoRespObj =queryAccountInfo(platformUserNo);
			 //4.融资人账户可用余额
			 double availableAmount = new Double(accountInfoRespObj.getAvailableAmount()).doubleValue();
			  //5.申请赎回本金
			  Double redeemPrincipal = currentProjectRedemptionApply.getRedeemPrincipal();
			  //6.判断融资人账户可用余额是否满足用户申请赎回本金的额度
			  boolean amountTrueAndFalse=availableAmount-redeemPrincipal>0?true:false;
			  //7如果融资人账户余额满足支付投资用户的赎回金额则进行赎回相关操作
			  if(amountTrueAndFalse){
				  //将用户申请赎回的本金从平台打款给用户时的更新操作
					currentAccountSummaryDao.updateByPayRedeem(currentProjectHoldInfo.getAccountId(), currentProjectRedemptionApply.getRedeemPrincipal());
					currentProjectExecuteSnapshotDao.updateByPayRedeem(currentProjectHoldInfo.getProjectId(), currentProjectRedemptionApply.getRedeemPrincipal());
					//更新申请状态
					dao.updateByPayRedeem(currentProjectRedemptionApply.getId());
					currentProjectHoldInfoDao.updateByPayRedeem(currentProjectHoldInfo.getId(), currentProjectRedemptionApply.getRedeemPrincipal());
					//打钱
		        	String requestNo = yeepayCommonHandler.currentProjectRepayMoney(currentProjectHoldInfo.getProjectId(),
					currentProjectHoldInfo.getAccountId(), currentProjectRedemptionApply.getRedeemPrincipal(),
					ProjectConstant.CHANGE_TYPE_BALANCE_CURRENT_PROJECT_REDEEM_PRINCIPAL,
					currentProjectHoldInfo.getCurrentProjectInfo().getName() + "--" + CurrentProjectConstant.PRINCIPAL_CHANGE_REASON_REDEEM, ProjectConstant.OP_TERM_DICT_PC);
		        	//插入活期账户变更历史
					CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis = new CurrentAccountPrincipalChangeHis();
					currentAccountPrincipalChangeHis.setAccountId(currentProjectHoldInfo.getAccountId());
					currentAccountPrincipalChangeHis.setProjectId(currentProjectHoldInfo.getProjectId());
					currentAccountPrincipalChangeHis.setChangeType(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_REDEEM);
					currentAccountPrincipalChangeHis.setChangeValue(-currentProjectRedemptionApply.getRedeemPrincipal());
					currentAccountPrincipalChangeHis.setChangeReason(CurrentProjectConstant.PRINCIPAL_CHANGE_REASON_REDEEM);
					currentAccountPrincipalChangeHis.setStatus(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_NORMAL);
					currentAccountPrincipalChangeHis.setOpDt(new Date());
					currentAccountPrincipalChangeHis.setOpTerm(ProjectConstant.OP_TERM_DICT_PC);
					currentAccountPrincipalChangeHis.setThirdAccountRequestNo(requestNo);
					currentAccountPrincipalChangeHisDao.insert(currentAccountPrincipalChangeHis);
					//7.1系统插入成功信息
					initDemandRedemptionInformation(currentProjectRedemptionApply, availableAmount, redeemPrincipal, CurrentProjectConstant.CURRENT_APPLY_FINISH, CurrentProjectConstant.CURRENT_APPLY_SCESSES);
			  }else{
				    //7.2如果融资人账户余额不足以支付投资用户的赎回金额则系统记录错误信息
				    initDemandRedemptionInformation(currentProjectRedemptionApply, availableAmount, redeemPrincipal, CurrentProjectConstant.CURRENT_APPLY_AMOUNT_BZ, CurrentProjectConstant.CURRENT_APPLY_DEFOULT);
			  }
		}
	}
	
	/**
	 * 添加日志信息
	 * @param currentProjectRedemptionApply
	 * @param availableAmount
	 * @param redeemPrincipal
	 * @param infoReason
	 * @param status
	 */
	public void initDemandRedemptionInformation(
			CurrentProjectRedemptionApply currentProjectRedemptionApply,
			double availableAmount, Double redeemPrincipal, String infoReason,
			String status) {
		DemandRedemptionInformationRecord demandRedemptionInformationRecord = new DemandRedemptionInformationRecord();
		demandRedemptionInformationRecord
				.setRedemptionId(currentProjectRedemptionApply.getId());
		demandRedemptionInformationRecord
				.setReedmptionDate(currentProjectRedemptionApply.getApplyDt());
		demandRedemptionInformationRecord.setInfoReason(infoReason);
		demandRedemptionInformationRecord.setAccountAmount(availableAmount);
		demandRedemptionInformationRecord.setRedeemPrincipal(redeemPrincipal);
		demandRedemptionInformationRecord.setStatus(status);
		demandRedemptionInformationRecordDao
				.insert(demandRedemptionInformationRecord);
	}

	/**
	 * 判断是不是应该给赎回申请打款
	 * @param status
	 * @param applyDate
	 * @return
	 */
	public boolean shouldPayRedeem(String status, Date applyDate) {
//		if(CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_REVIEW_PASS.equals(status) && dateInfoDao.isWorkday(new Date())) {
//			if(dateInfoDao.isWorkday(applyDate)
//					&& DateUtils.formatDate(applyDate, "HH:mm:ss").compareTo(CurrentProjectConstant.REDEEM_SEPARATE_TIME) <= 0) {
//				return true;
//			} else if(DateUtils.formatDate(applyDate, "yyyy-MM-dd").compareTo(DateUtils.formatDate(new Date(), "yyyy-MM-dd")) < 0) {
//				return true;
//			}
//		}
		return CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_REVIEW_PASS.equals(status);
	}
	
	@Transactional(readOnly = false)
	public void review(CurrentProjectRedemptionApply currentProjectRedemptionApply) {
		currentProjectRedemptionApplyDao.update(currentProjectRedemptionApply);
		
	}

	public List<CurrentProjectRedemptionApply> findListByStatus(String status) {
		return dao.findListByStatus(status);
	}
	/**
	 * 获取申请赎回的人数（以天为单位，得到某天申请总人数）
	 * @param page
	 * @param
	 * @param
	 * @return
	 */
	public Page<Map<String, Object>> findRedemptionInfoList(Page<Map<String, Object>> page,CurrentProjectRedemptionApply currentProjectRedemptionApply,Date startDate,Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
/*		map.put("applyDt", currentProjectRedemptionApply.getApplyDt());*/
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		page.setList(dao.findRedemptionInfoList(map));
		return page;
	}
	/**
	 * 获取天赎回申请总人数（以天为单位，得到某天总的人数）无分页
	 * @param page
	 * @param startDate
	 * @param endDate
	 * @return
	 *//*
	public List<Map<String, Object>> findRedemptionInfoListNoPage( Date startDate, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return dao.findRedemptionInfoList(map);
	}

	*//**
	 * 查询某一天的还款计划
	 * @param page
	 * @param date
	 * @return
	 *//*
	public Page<Map<String, Object>> findRedemptionInfoListByDate(Page<Map<String, Object>> page, Date date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("date", date);
		page.setList(dao.findRedemptionInfoListByDate(map));
		return page;
	}*/
	@Transactional(readOnly = false)
	public void updatePrincipal(String id) {
		currentProjectRedemptionApplyDao.updatePrincipalById(id);
		
	}
	
	
	public static void main(String[] args) {
		 double availableAmount=500;
		 double redeemPrincipal=1000;
		 boolean amountTrueAndFalse=availableAmount-redeemPrincipal>0?true:false;
		 if(amountTrueAndFalse){
		 }
	}
	
	
}