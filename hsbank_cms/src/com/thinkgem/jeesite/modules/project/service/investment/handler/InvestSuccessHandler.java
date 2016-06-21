package com.thinkgem.jeesite.modules.project.service.investment.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.service.loan.LoanService;

/**
 * 投资成功处理
 * <p/>
 * @author zibo.li
 * CreateDate 2015-07-30
 */
@Component("investSuccessHandler")
public class InvestSuccessHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectInvestmentRecordDao investmentRecordDao;
	@Autowired
	private LoanService loanService;
	@Autowired
	private MarketFacadeService marketFacadeService;
	
	/**
	 * 处理器
	 * <1>.更新投资记录状态（正常）
	 * <2>.融资项目是否结束 ? 结束处理流程 : 返回
	 * @return
	 */
	public void handler(ProjectBaseInfo projectInfo, ProjectInvestmentRecord investmentRecord) {
		
		//更新投资记录状态（正常）
		logger.debug("---------" + DateUtils.formatDateTime(new Date())
				+ ":InvestSuccess updateStatus recordId :" + investmentRecord.getId() + " start.");
		investmentRecordDao.updateStatus(String.valueOf(investmentRecord.getId()), ProjectConstant.PROJECT_INVESTMENT_STATUS_NORMAL);
		logger.debug("---------" + DateUtils.formatDateTime(new Date())
				+ ":InvestSuccess updateStatus recordId :" + investmentRecord.getId() + " end.");
		//营销活动投资入口
		marketFacadeTender(projectInfo, investmentRecord);
		//融资项目是否结束 ? 结束处理流程 : 返回
		loanService.finishedHandler(projectInfo);
	}
	
	/**
	 * 营销活动投资入口
	 * @param projectInfo
	 * @param investmentRecord
	 */
	public void marketFacadeTender(ProjectBaseInfo projectInfo, ProjectInvestmentRecord investmentRecord){
		Map<String,Object> para = new HashMap<String,Object>();
		para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, investmentRecord.getInvestmentUserId());
		para.put(MarketConstant.AMOUNT_PARA, investmentRecord.getAmount());
		para.put(MarketConstant.PROJECT_ID_PARA, projectInfo.getProjectId());
		para.put(MarketConstant.RECORD_ID_PARA, investmentRecord.getId());
		para.put(MarketConstant.CHANNEL_PARA, investmentRecord.getOpTerm());
		para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER);
		marketFacadeService.investmentTender(para);
	}
}
