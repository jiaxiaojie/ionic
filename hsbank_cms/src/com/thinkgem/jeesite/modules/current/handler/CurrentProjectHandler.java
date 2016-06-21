package com.thinkgem.jeesite.modules.current.handler;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectDateNodeDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectInfoDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.message.service.MessageAlertSettingService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 活期产品项目处理
 * <p/>
 * @author lzb
 * @version 2015-12-10
 */
@Component
public class CurrentProjectHandler {

	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CurrentProjectExecuteSnapshotDao currentProjectExecuteSnapshotDao;
	@Autowired
	private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;
	@Autowired
	private CurrentProjectDateNodeDao currentProjectDateNodeDao;
	@Autowired
	private CurrentProjectInfoDao currentProjectInfoDao;
	@Autowired
	private MessageAlertSettingService messageAlertSettingService;
	
	/**
	 * 项目投资金额校验
	 * @param cInfo
	 * @param amount
	 */
	public void checkProjectAmount(CurrentProjectInfo cInfo,Double amount){
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":CurrentProjectHandler checkProjectAmount start...");
		//最大投资金额
		Double maxAmount = cInfo.getMaxAmount() != null ? cInfo.getMaxAmount() : ProjectConstant.PROJECT_MAX_AMOUNT_DEFAULT;
		//获取项目执行快照基本信息
		CurrentProjectExecuteSnapshot snapshot = currentProjectExecuteSnapshotDao.getByProjectId(NumberUtil.toLong(cInfo.getId(), 0L));
		if(amount.compareTo(0.00) <= 0){
			throw new ServiceException("投资金额必须大于0");
		}
		if(!NumberUtils.isIntMulOfOne(amount)){
			throw new ServiceException("投资金额必须为1的整数倍");
		}
		//项目可投金额 = 借款金额 - 已融资金额
		Double investAmount = LoanUtil.formatAmount(cInfo.getFinanceMoney() - snapshot.getHasFinancedMoney());
		if(amount.compareTo(investAmount) > 0){
			throw new ServiceException("项目可投金额【" + investAmount + "】小于本次投资金额【" + amount + "】");
		}
		//项目可投金额 <= 起投金额  ? 投资金额 = 项目可投金额 : "抛出异常"
		if(investAmount.compareTo(cInfo.getStartingAmount()) <= 0){
			if(amount.compareTo(investAmount) != 0){
				throw new ServiceException("项目可投金额【" + investAmount + "】小于等于起投金额【" + cInfo.getStartingAmount() + "】时，必须一次性投完");
			}
		}else{
			if(amount.compareTo(cInfo.getStartingAmount().doubleValue()) < 0){
				throw new ServiceException("投资金额【" + amount + "】小于起投金额【" + cInfo.getStartingAmount() + "】");
			}else if(maxAmount.compareTo(cInfo.getStartingAmount()) >=0  && amount.compareTo(maxAmount) > 0 ){
				throw new ServiceException("投资金额【" + amount + "】大于最大投资金额【" + cInfo.getMaxAmount() + "】");
			}
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":CurrentProjectHandler checkProjectAmount end...");
	}
	
	/**
	 * 项目投资状态校验
	 * @param projectId
	 */
	public void checkProjectStatus(String projectId){
		CurrentProjectInfo cInfo = currentProjectInfoDao.get(projectId);
		if(!CurrentProjectConstant.CURRENT_PROJECT_STATUS_TENDERING.equals(cInfo.getStatus())){
			throw new ServiceException("项目【" + cInfo.getName() + "】处于非投标中，您不能投资");
		}
	}
	
	/**
	 * 项目投资自动还款授权校验
	 * @param projectId
	 */
	public void checkProjectAutoRepay(String projectId,boolean isCheck){
		if(isCheck){
			CurrentProjectInfo cInfo = currentProjectInfoDao.get(projectId);
			if(!CurrentProjectConstant.YES.equals(cInfo.getIsAutoRepay())){
				throw new ServiceException("项目【" + cInfo.getName() + "】还未自动还款授权，您不能投资");
			}
		}
	}
	
	/**
	 * 投资：更新执行快照已融资金额、当前实际本金
	 * @param cInfo
	 * @param amount
	 */
	public void updateProjectSnapshotAmount(CurrentProjectInfo cInfo, Double amount){
		//获取项目执行快照基本信息
		CurrentProjectExecuteSnapshot snapshot = currentProjectExecuteSnapshotDao.getByProjectId(NumberUtil.toLong(cInfo.getId(), 0L));
		//项目可投金额 = 借款金额 - 已融资金额
		Double investAmount = LoanUtil.formatAmount(cInfo.getFinanceMoney() - snapshot.getHasFinancedMoney());
		if(amount.compareTo(investAmount) > 0){
			throw new ServiceException("项目可投金额【" + investAmount + "】小于本次投资金额【" + amount + "】");
		}else{
			currentProjectExecuteSnapshotDao.updateFinancedMoneyAndRealPrincipal(NumberUtil.toLong(cInfo.getId(), 0L), amount);
		}
	}
	
	/**
	 * 投资取消：回滚已融资金额、当前实际本金
	 * @param projectId
	 * @param amount
	 */
	public void cancelProjectSnapshotAmount(Long projectId, Double amount){
		currentProjectExecuteSnapshotDao.updateFinancedMoneyAndRealPrincipal(projectId, amount);
		//获取项目执行快照基本信息
		CurrentProjectExecuteSnapshot snapshot = currentProjectExecuteSnapshotDao.getByProjectId(projectId);
		Double hasFinancedMoney = snapshot.getHasFinancedMoney() !=null ? snapshot.getHasFinancedMoney() : 0.0;
		if(hasFinancedMoney.compareTo(0.0) < 0){
			throw new ServiceException("活期项目已融资金额小于0：已融资金额 =【" + hasFinancedMoney + "】");
		}
	}
	
	/**
	 * 投资：满标处理
	 * @param cInfo
	 */
	public void finishedCurrentProject(CurrentProjectInfo cInfo){
		if(isFinished(cInfo)){
			Long projectId = NumberUtil.toLong(cInfo.getId(), 0L);
			//更新项目状态(投标结束)
			currentProjectInfoDao.updateStatus(projectId, CurrentProjectConstant.CURRENT_PROJECT_STATUS_TENDER_OVER);
			messageAlertSettingService.sendSmsFromFengControl(cInfo.getName());
			//更新幕资结束时间
			currentProjectDateNodeDao.updateEndFundingDt(projectId, new Date());
		}
	}
	
	/**
	 * 是否满标判断
	 * @param cInfo
	 * @return
	 */
	public boolean isFinished(CurrentProjectInfo cInfo){
		//<1>.当前时间 > 投标截止时间 ? 满标 ：没满标
		if(System.currentTimeMillis() > cInfo.getEndInvestmentDt().getTime()){
			return true;
		}
		List<CurrentAccountPrincipalChangeHis> cList = currentAccountPrincipalChangeHisDao
				.getPrincipalChangeHisList(
						cInfo.getId(),
						CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT,
						CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_FREEZE);	
		//<2>.该项目的投资记录是否含有【冻结中】状态 ? 没有满标 : 下一步判断
		if(cList!=null && cList.size() > 0){
			return false;
		}
		CurrentProjectExecuteSnapshot snapshot = currentProjectExecuteSnapshotDao.getByProjectId(NumberUtil.toLong(cInfo.getId(), 0L));
		//<3>.该项目的已募集金额 >= 募集金额 ? 满标 : 没有满标
		if(snapshot.getHasFinancedMoney().compareTo(cInfo.getFinanceMoney()) < 0){
			return false;
		}
		return true;
	}

}
