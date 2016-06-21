package com.thinkgem.jeesite.modules.project.service.util.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectShowTerm;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectTransferInfoDao;
import com.thinkgem.jeesite.modules.project.service.ProjectShowTermService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.hsbank.util.type.NumberUtil;

/**
 * 项目进度处理器
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
@Component("projectProcessHandler")
public class ProjectProcessHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectExecuteSnapshotDao snapshotDao;
	@Autowired
	private ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	ProjectTransferInfoDao projectTransferInfoDao;
	@Autowired
	private ProjectShowTermService projectShowTermService;
	
	/**
	 *处理器
	 * <1>.【本次项目投资金额】<=【项目可投金额】?【项目已融资金额】 -= 【本次项目投资金额】 : 抛出异常 
	 * @param projectId					项目流水号
	 * @param transferProjectId			转让流水号
	 * @param amount					投资金额
	 * @param ticketAmount				优惠券金额
	 * @param platformAmount			平台垫付金额
	 * @param toPlatformMoney			给平台金额
	 */
	public void handler(Long projectId, Long transferProjectId, Double amount, Double ticketAmount, Double platformAmount, Double toPlatformMoney) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":updateProjectProcess start...");
		//获取项目执行快照基本信息
		ProjectExecuteSnapshot snapshot = snapshotDao.getByProjectId(projectId, transferProjectId);
		//项目可投金额 = 借款金额 - 已融资金额
		Double investAmount = LoanUtil.formatAmount(snapshot.getFinanceMoney() - snapshot.getEndFinanceMoney());
		//判断，【本次项目投资金额】<=【项目可投金额】
		if(amount.compareTo(investAmount) <= 0){
			//更新项目已融资金额、已冻结服务费、已冻结抵用额、已冻结优惠卷额
			snapshotDao.updateAmount(projectId, transferProjectId, LoanUtil.formatAmount(amount), ticketAmount, platformAmount, toPlatformMoney);
		}else{
			throw new ServiceException("项目可投金额【" + investAmount + "】小于本次投资金额【" + amount + "】");
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":updateProjectProcess end...");
	}
	
	/**
	 * 投资金额校验
	 * @param projectInfo
	 * @param transferProjectId
	 * @param amount
	 */
	public void check(ProjectBaseInfo projectInfo, Long transferProjectId, Double amount){
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":ProjectProcessHandler check start...");
		//最大投资金额
		Long maxAmount = projectInfo.getMaxAmount() != null ? projectInfo.getMaxAmount() : ProjectConstant.PROJECT_MAX_AMOUNT_DEFAULT;
		//获取项目执行快照基本信息
		ProjectExecuteSnapshot snapshot = snapshotDao.getByProjectId(NumberUtil.toLong(projectInfo.getProjectId(), 0L), transferProjectId);
		if(amount.compareTo(0.00) <= 0){
			throw new ServiceException("投资金额必须大于0");
		}
		//项目可投金额 = 借款金额 - 已融资金额
		Double investAmount = LoanUtil.formatAmount(snapshot.getFinanceMoney() - snapshot.getEndFinanceMoney());
		if(amount.compareTo(investAmount) > 0){
			throw new ServiceException("项目可投金额【" + investAmount + "】小于本次投资金额【" + amount + "】");
		}
		//项目可投金额 <= 起投金额  ? 投资金额 = 项目可投金额 : "抛出异常"
		if(investAmount.compareTo(projectInfo.getStartingAmount().doubleValue()) <= 0){
			if(amount.compareTo(investAmount) != 0){
				throw new ServiceException("项目可投金额【" + investAmount + "】小于等于起投金额【" + projectInfo.getStartingAmount() + "】时，必须一次性投完");
			}
		}else{
			if(amount.compareTo(projectInfo.getStartingAmount().doubleValue()) < 0){
				throw new ServiceException("投资金额【" + amount + "】小于起投金额【" + projectInfo.getStartingAmount() + "】");
			}else if(maxAmount.compareTo(projectInfo.getStartingAmount()) >=0  && amount.compareTo(maxAmount.doubleValue()) > 0 ){
				throw new ServiceException("投资金额【" + amount + "】大于最大投资金额【" + projectInfo.getMaxAmount() + "】");
			}
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":ProjectProcessHandler check end...");
	}
	
	/**
	 * 投资项目状态校验
	 * @param projectId
	 * @param transferProjectId
	 * @param type
	 */
	public void checkProjectStatus(String projectId, Long transferProjectId, String type){
		if(ProjectConstant.PROJECT_INVESTMENT_TYPE_DIRECT.equals(type)){
			ProjectBaseInfo pBaseInfo = projectBaseInfoDao.get(projectId);
			if(!ProjectConstant.PROJECT_STATUS_INVESTMENT.equals(pBaseInfo.getProjectStatus())){
				throw new ServiceException("项目【" + pBaseInfo.getProjectId() + "】处于非投标中，您不能投资");
			}
		}else if(ProjectConstant.PROJECT_INVESTMENT_TYPE_ASSIGNMENT.equals(type)){
			ProjectTransferInfo pTransferInfo = projectTransferInfoDao.get(String.valueOf(transferProjectId));
			if(!ProjectConstant.PROJECT_TRANSFER_STATUS_RUNNING.equals(pTransferInfo.getStatus())){
				throw new ServiceException("转让项目【" + pTransferInfo.getTransferProjectId() + "】处于非转让中，您不能投资");
			}
		}
		
	}
	
	/**
	 * 项目可操作终端校验
	 * @param opTerm
	 * @param projectId
	 */
	public void checkProjectTerm(String opTerm, String projectId){
		//项目显示终端信息
		List<String> showTermList = new ArrayList<String>();
		for(ProjectShowTerm projectShowTerm : projectShowTermService.findListByProjectId(projectId)) {
			showTermList.add(projectShowTerm.getTermCode());
		}
		//若此项目的显示信息不包括opTerm终端，则抛出异常
		if(!showTermList.contains(opTerm)) {
			throw new ServiceException("此项目限制终端："+DictUtils.getDictLabel(opTerm, "op_term_dict", "") + "不能投资");
		}
	}
}
