package com.thinkgem.jeesite.modules.project.service.assignment.handler;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.ProjectUtil;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.service.util.handler.RateTicketHandler;

/**
 * 债权转让记录处理
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
@Component("assignRecordHandler")
public class AssignRecordHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	private RateTicketHandler rateTicketHandler;
	
	/**
	 * 数据入库：生成转让部分的投资记录
	 * @param projectTransferInfo
	 * @param requestNo							第三方流水号
	 * @param accountId							投资人账户Id
	 * @param ticketIds							优惠劵Ids
	 * @param amount							支付金额
	 * @param ticketAmount						优惠劵金额
	 * @param platformAmount					平台垫付金额
	 */
	public ProjectInvestmentRecord addRecord_assignment(ProjectTransferInfo projectTransferInfo, String opTerm,
			String requestNo, Long accountId, String ticketIds, Double amount, 
			Double ticketAmount, Double platformAmount, String rateTicketIds, Date beginInterestDate) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addRecord_assignment start...");
		ProjectInvestmentRecord investmentRecord = new ProjectInvestmentRecord();
		//上家(转让方)服务费
		Double upServiceCharge = ProjectUtil.getUpServiceCharge(amount);
		investmentRecord.setUpToPlatformMoney(upServiceCharge);
		//下家(受让方)服务费
		Double downServiceCharge = ProjectUtil.getDownServiceCharge(amount);
		investmentRecord.setDownToPlatformMoney(downServiceCharge);
		//计算： 实际投资额 = 转让金额 - 用券金额 - 平台垫付金额 + 下家(转让方)服务费
	    Double actualAmount = LoanUtil.formatAmount(amount - ticketAmount - platformAmount + downServiceCharge);
		investmentRecord.setProjectId(projectTransferInfo.getProjectId());
		investmentRecord.setTransferProjectId(projectTransferInfo.getTransferProjectId());
		investmentRecord.setInvestmentUserId(accountId);
		investmentRecord.setTicketIds(ticketIds);
		investmentRecord.setRateTicketIds(rateTicketIds);
		investmentRecord.setAmount(amount);
		investmentRecord.setActualAmount(actualAmount);
		investmentRecord.setPlatformAmount(platformAmount);
		investmentRecord.setTicketAmount(ticketAmount);
		Double serviceCharge = LoanUtil.formatAmount(upServiceCharge + downServiceCharge);
		//加息券列表
		List<IncreaseInterestItem> interestItems = rateTicketHandler.getInterestItems(investmentRecord.getRateTicketIds());
		Double willProfit = ProjectUtil.calculateWillProfit(interestItems, projectTransferInfo.getProjectBaseInfo(), amount, rateTicketIds, beginInterestDate);
		investmentRecord.setWillProfit(willProfit);
		investmentRecord.setWillReceivePrincipal(amount);
		investmentRecord.setWillReceiveInterest(willProfit);
		investmentRecord.setToPlatformMoney(serviceCharge);
		Double toBorrowersUserMoney = actualAmount >= serviceCharge ? LoanUtil.formatAmount(actualAmount - serviceCharge) : 0;
		investmentRecord.setToBorrowersUserMoney(toBorrowersUserMoney);
		investmentRecord.setInvestmentType(ProjectConstant.PROJECT_INVESTMENT_TYPE_ASSIGNMENT);		//投资方式
		investmentRecord.setOpTerm(opTerm);	//操作终端
		investmentRecord.setOpDt(new Date());
		investmentRecord.setStatus(ProjectConstant.PROJECT_INVESTMENT_STATUS_FREEZE);	//投资状态(冻结中)
		investmentRecord.setThirdPartyOrder(requestNo);
		projectInvestmentRecordDao.insert(investmentRecord);
		investmentRecord.setId(investmentRecord.getId());
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addRecord_assignment end...");
		return investmentRecord;
	}
	
	/**
	 * 数据入库：生成转让剩余部分的投资记录
	 * @param accountId							投资人账户Id
	 * @param amount							转让剩余金额
	 * @return
	 */
	public ProjectInvestmentRecord addRecord_remaining(ProjectTransferInfo projectTransferInfo, 
			String opTerm, String requestNo, Double amount,String rateTicketIds, Date beginInterestDate) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addRecord_remaining start...");
		ProjectInvestmentRecord investmentRecord = new ProjectInvestmentRecord();
		investmentRecord.setProjectId(projectTransferInfo.getProjectId());
		investmentRecord.setTransferProjectId(projectTransferInfo.getTransferProjectId());
		investmentRecord.setInvestmentUserId(projectTransferInfo.getPir().getInvestmentUserId());
		investmentRecord.setThirdPartyOrder(requestNo);
		investmentRecord.setAmount(amount);
		investmentRecord.setActualAmount(0.00);
		investmentRecord.setToPlatformMoney(0.00);
		investmentRecord.setToBorrowersUserMoney(0.00);
		//加息券列表
		List<IncreaseInterestItem> interestItems = rateTicketHandler.getInterestItems(investmentRecord.getRateTicketIds());
		Double willProfit = ProjectUtil.calculateWillProfit(interestItems, projectTransferInfo.getProjectBaseInfo(), amount, rateTicketIds, beginInterestDate);
		investmentRecord.setWillProfit(willProfit);
		investmentRecord.setWillReceivePrincipal(amount);
		investmentRecord.setWillReceiveInterest(willProfit);
		investmentRecord.setInvestmentType(ProjectConstant.PROJECT_INVESTMENT_TYPE_ASSIGNMENT);		//投资方式
		investmentRecord.setOpTerm(opTerm);	//操作终端
		investmentRecord.setOpDt(new Date());
		investmentRecord.setStatus(ProjectConstant.PROJECT_INVESTMENT_STATUS_FREEZE);	//投资状态(冻结中)
		projectInvestmentRecordDao.insert(investmentRecord);
		investmentRecord.setId(investmentRecord.getId());
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addRecord_remaining end...");
		return investmentRecord;
	}
	
	/**
	 * 数据入库：更新投资记录的状态
	 * @param investmentRecordId			投资记录Id			
	 * @param status									投资记录状态
	 */
	public void updateStatus(String investmentRecordId, String status) {
		logger.info("---------" + DateUtils.formatDate(new Date())
				+ ":update original investment  record 【" + investmentRecordId + "】start...");
		projectInvestmentRecordDao.updateStatus(investmentRecordId, status);
		logger.info("---------" + DateUtils.formatDate(new Date())
				+ ":update original investment  record 【" + investmentRecordId + "】end...");
	}
}
