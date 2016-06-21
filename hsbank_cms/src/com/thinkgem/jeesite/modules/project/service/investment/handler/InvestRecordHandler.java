package com.thinkgem.jeesite.modules.project.service.investment.handler;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.ProjectUtil;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.service.util.handler.RateTicketHandler;
import com.hsbank.util.type.NumberUtil;

/**
 * 投资记录处理
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
@Component("investmentRecordHandler")
public class InvestRecordHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	private RateTicketHandler rateTicketHandler;
	
	/**
	 * 数据入库：新增一条投资记录
	 * @param accountId					投资人账户Id
	 * @param ticketIds					优惠劵Ids
	 * @param amount					投资金额
	 * @param ticketAmount				优惠劵金额
	 * @param platformAmount			平台垫付金额
	 * @return
	 */
	public ProjectInvestmentRecord addRecord(ProjectBaseInfo projectInfo, String opTerm, String requestNo,
			Long accountId, String ticketIds, Double amount, Double ticketAmount,
			Double platformAmount, String rateTicketIds, Date beginInterestDate) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addRecord start...");
		ProjectInvestmentRecord investmentRecord = new ProjectInvestmentRecord();
		//计算实际投资额 = 投资总金额 - 用券金额 - 平台垫付金额
	    Double actualAmount = LoanUtil.formatAmount(amount - ticketAmount - platformAmount);
		investmentRecord.setProjectId(NumberUtil.toLong(projectInfo.getProjectId(), 0L));
		investmentRecord.setInvestmentUserId(accountId);
		investmentRecord.setTicketIds(ticketIds);
		investmentRecord.setRateTicketIds(rateTicketIds);
		investmentRecord.setAmount(amount);
		investmentRecord.setActualAmount(actualAmount);
		investmentRecord.setPlatformAmount(platformAmount);
		investmentRecord.setTicketAmount(ticketAmount);
		Double serviceCharge = ProjectUtil.getServiceCharge(amount, projectInfo.getServiceCharge(), projectInfo.getServiceChargeTypeCode(), projectInfo.getPes().getSumServiceCharge());
		investmentRecord.setToPlatformMoney(serviceCharge);
		Double toBorrowersUserMoney = actualAmount >= serviceCharge ? LoanUtil.formatAmount(actualAmount - serviceCharge) : 0;
		investmentRecord.setToBorrowersUserMoney(toBorrowersUserMoney);
		investmentRecord.setInvestmentType(ProjectConstant.PROJECT_INVESTMENT_TYPE_DIRECT);		//投资方式
		if(ProjectConstant.PROJECT_INVESTMENT_TYPE_DIRECT.equals(investmentRecord.getInvestmentType())){
			investmentRecord.setTransferProjectId(0L);
		}
		//加息券列表
		List<IncreaseInterestItem> interestItems = rateTicketHandler.getInterestItems(investmentRecord.getRateTicketIds());
		Double willProfit = ProjectUtil.calculateWillProfit(interestItems, projectInfo, amount, rateTicketIds, beginInterestDate);
		investmentRecord.setWillProfit(willProfit);
		investmentRecord.setWillReceivePrincipal(amount);
		investmentRecord.setWillReceiveInterest(willProfit);
		investmentRecord.setOpTerm(opTerm);	//操作终端
		investmentRecord.setOpDt(new Date());
		investmentRecord.setStatus(ProjectConstant.PROJECT_INVESTMENT_STATUS_FREEZE);	//投资状态(冻结中)
		investmentRecord.setThirdPartyOrder(requestNo);
		projectInvestmentRecordDao.insert(investmentRecord);
		investmentRecord.setId(investmentRecord.getId());
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addRecord end...");
		return investmentRecord;
	}
}
