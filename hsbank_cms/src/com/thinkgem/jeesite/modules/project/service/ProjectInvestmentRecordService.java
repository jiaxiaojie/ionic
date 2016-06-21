/**
\ * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import com.hsbank.util.constant.DatetimeField;
import com.hsbank.util.type.DatetimeUtil;
import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentPlan;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConfig;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 投资记录Service
 * 
 * @author yangtao
 * @version 2015-06-24
 */
@Service
@Transactional(readOnly = true)
public class ProjectInvestmentRecordService extends
		CrudService<ProjectInvestmentRecordDao, ProjectInvestmentRecord> {
	@Autowired
	ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	ProjectRepaymentSplitRecordDao repaymentSplitRecordDao;
	@Autowired
	ProjectExecuteSnapshotDao projectExecuteSnapshotDao;
	@Autowired
	ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	ProjectTransferInfoDao projectTransferInfoDao;
	@Autowired
	ProjectRepaymentPlanDao projectRepaymentPlanDao;

	public ProjectInvestmentRecord get(String id) {
		return super.get(id);
	}

	public List<ProjectInvestmentRecord> findList(
			ProjectInvestmentRecord projectInvestmentRecord) {
		return super.findList(projectInvestmentRecord);
	}

	public Page<ProjectInvestmentRecord> findPage(
			Page<ProjectInvestmentRecord> page,
			ProjectInvestmentRecord projectInvestmentRecord) {
		return super.findPage(page, projectInvestmentRecord);
	}

	/**
	 * 最新投资分页列表
	 * @param page
	 * @return
     */
	public Page<ProjectInvestmentRecord> findLatestInvestPageList(Page<ProjectInvestmentRecord> page){
		PageSearchBean pageSearchBean = new PageSearchBean();
		pageSearchBean.setDefaultDateRangeWithMonths(-1);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page",page);
		map.put("beginDate",pageSearchBean.getStartDateTime());
		map.put("endDate",pageSearchBean.getEndDateTime());
		map.put("status",ProjectConstant.PROJECT_INVESTMENT_STATUS_NORMAL);
		page.setList(dao.findLatestInvestPageList(map));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(ProjectInvestmentRecord projectInvestmentRecord) {
		super.save(projectInvestmentRecord);
	}

	@Transactional(readOnly = false)
	public void delete(ProjectInvestmentRecord projectInvestmentRecord) {
		super.delete(projectInvestmentRecord);
	}

	/**
	 * 获得投资者投资的运行中的项目清单
	 * 
	 * @param accountId
	 * @return
	 */
	public List<ProjectInvestmentRecord> findInvestmentRunningProjectListByAccountId(
			String accountId) {
		return projectInvestmentRecordDao
				.findInvestmentRunningProjectListByAccountId(accountId);
	}

	/**
	 * 获取今日排行列表
	 * @param limit
	 * @return
     */
	public List<ProjectInvestmentRecord>  getTodayRankingList(int limit){
		return dao.getTodayRankingList(limit);
	}

	/**
	 * 获得投资者投资的结束的项目清单
	 * 
	 * @param accountId
	 * @return
	 */
	public List<ProjectInvestmentRecord> findInvestmentEndProjectListByAccountId(
			String accountId) {
		return projectInvestmentRecordDao
				.findInvestmentEndProjectListByAccountId(accountId);
	}

	/**
	 * 获得用户投资冻结状态项目数量
	 * 
	 * @param accountId
	 * @return
	 */
	public int findCongealProjectCountByAccountId(String accountId) {
		return projectInvestmentRecordDao
				.findCongealProjectCountByAccountId(accountId);
	}

	/**
	 * 获得结束项目中提前还款的项目数量
	 * 
	 * @param accountId
	 * @return
	 */
	public int getPreRepaymentProjectCount(String accountId) {
		return projectInvestmentRecordDao
				.getPreRepaymentProjectCount(accountId);
	}

	/**
	 * 获取投资记录列表
	 * 
	 * @param projectId
	 *            项目流水号
	 * @return
	 */
	public List<ProjectInvestmentRecord> findListByProjectId(String projectId) {
		return dao.findListByProjectId(projectId, "0");
	}
	/**
	 * 获取投资记录列表
	 * 
	 * @param projectId
	 *            项目流水号
	 * @return
	 */
	public List<ProjectInvestmentRecord> findListByProjectIdAndStatuses(String projectId) {
		return dao.findListByProjectIdAndStatuses(projectId);
	}
	
	/**
	 * 获取项目的投资人数
	 * @param projectId
	 * @return
	 */
	public int getCountByProjectIdAndStatuses(String projectId){
		return dao.getCountByProjectIdAndStatuses(projectId);
	}
	/**
	 * 根据投资交易第三方流水号查询信息
	 * 
	 * @param thirdPartyOrder
	 * @return
	 */
	public ProjectInvestmentRecord getByThirdPartyOrder(String thirdPartyOrder) {
		return dao.getByThirdPartyOrder(thirdPartyOrder);
	}

	/**
	 * 根据第三方流水号查询记录列表
	 * @param thirdPartyOrder
	 * @return
	 */
	public List<ProjectInvestmentRecord> findListByThirdPartyOrder(String thirdPartyOrder) {
		return dao.findListByThirdPartyOrder(thirdPartyOrder);
	}
	
	/**
	 * 根据projectId transferProjectId查询投资记录
	 * 
	 * @param projectId
	 * @param transferProjectId
	 * @return
	 */
	public List<ProjectInvestmentRecord> findListByProjectIdAndTransferProjectId(
			String projectId, Long transferProjectId, String status) {
		return dao.findListByProjectIdAndTransferProjectId(projectId,
				transferProjectId, status);
	}
	
	/**
	 * 获取债权投资记录个数
	 * @param projectId
	 * @param transferProjectId
	 * @return
	 */
	public int getCountByProjectIdAndTransferProjectId(String projectId, Long transferProjectId){
		return dao.getCountByProjectIdAndTransferProjectId(projectId, transferProjectId);
	}

	/**
	 * 获得指定会员的投资记录(持有中)
	 * 
	 * @param accountId
	 * @return
	 */
	public Page<ProjectInvestmentRecord> findListForCyz(String accountId, String pageNo) {
		Page<ProjectInvestmentRecord> page = new Page<ProjectInvestmentRecord>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("page", page);
		page.setList(dao.findListForCyz(map));
		return page;
	}

	/**
	 * 获得指定会员的投资记录(投标中)
	 * 
	 * @param accountId
	 * @param pageNo 
	 * @return
	 */
	public Page<ProjectInvestmentRecord> findListForTbz(String accountId, String pageNo) {
		Page<ProjectInvestmentRecord> page = new Page<ProjectInvestmentRecord>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("page", page);
		page.setList(dao.findListForTbz(map));
		return page;
	}

	/**
	 * 获得指定会员的投资记录(已结束)
	 * 
	 * @param accountId
	 * @param pageNo 
	 * @return
	 */
	public Page<ProjectInvestmentRecord> findListForYjs(String accountId, String pageNo) {
		Page<ProjectInvestmentRecord> page = new Page<ProjectInvestmentRecord>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("page", page);
		page.setList(dao.findListForYjs(map));
		return page;
	}

	/**
	 * 查询指定转让记录对应的投资记录中的上家给平台的服务费
	 * 
	 * @param transferProjectId
	 * @return
	 */
	public Double getUpServiceCharge(String transferProjectId) {
		return dao.getUpServiceCharge(transferProjectId);
	}

	/**
	 * 获取用户对某个项目的投资总额
	 * @param projectId
	 * @param transferProjectId
	 * @param accountId
	 * @return
	 */
	public Double getSumAmount(String projectId, String transferProjectId,  Long accountId){
		return dao.getSumAmount(projectId, transferProjectId, accountId);
	}
	/**
	 * 判断某项目是否可以转让
	 * 
	 * @param investmentId
	 * @return
	 */
	public Map<String, String> canTransfer(String investmentId) {
		Map<String, String> ret = new HashMap<String, String>();
		ProjectInvestmentRecord projectInvestmentRecord = dao.get(investmentId);
		Long projectId = projectInvestmentRecord.getProjectId();
		ProjectBaseInfo pbi = projectBaseInfoDao
				.get(projectId.longValue() + "");
		ProjectExecuteSnapshot pes = projectExecuteSnapshotDao.getByProjectId(
				projectId, 0L);
		// 原标的项目状态
		if (!ProjectConstant.PROJECT_EXECUTE_SNAPSHOT_STATUS_NORMAL.equals(pes
				.getStatus())) {
			ret.put("flag", "false");
			ret.put("mes", "投资项目状态不允许转让");
			return ret;
		}
		// 事先约定不能转让的项目
		Long transferCode = pbi.getTransferCode();
		if (transferCode == ProjectConstant.PROJECT_TRANSFER_CODE_WITHOUT_PERMISSION) {
			ret.put("flag", "false");
			ret.put("mes", "投资项目事先约定不允许转让");
			return ret;
		}
		// 距下一个还款日期N 天内的不允许转让
		List<ProjectRepaymentPlan> nextPlanList = projectRepaymentPlanDao
				.findNextPlan(projectId.longValue() + "",
						DateUtils.dateFormate(new Date()));

		Date tempDate = DateUtils.addDays(new Date(), ProjectConfig
				.getInstance().getDayCountToNextRepay());
		if ((nextPlanList != null) && (nextPlanList.size() > 0)) {
			ProjectRepaymentPlan theNextRecord = nextPlanList.get(0);
			if (tempDate.getTime() > theNextRecord.getPlanDate().getTime()) {
				ret.put("flag", "false");
				ret.put("mes", "距离下一还款日期小于"
						+ ProjectConfig.getInstance().getDayCountToNextRepay()
						+ "天");
				return ret;
			}
		}

		// 债权持有时间少于约定时间的
		if (DatetimeUtil.getDate(projectInvestmentRecord.getOpDt(),
				DatetimeField.DAY, transferCode.intValue()).getTime() > System
				.currentTimeMillis()) {
			ret.put("flag", "false");
			ret.put("mes", "债权持有时间少于" + transferCode.intValue() + "天");
			return ret;
		}
		// 包含有正在转让的时候不允许转让
		int count = projectTransferInfoDao
				.getTransferingCountByInvestmentId(investmentId);
		if (count > 0) {
			ret.put("flag", "false");
			ret.put("mes", "此债权已经在转让中");
			return ret;
		}
		ret.put("flag", "true");
		ret.put("mes", "允许转让");
		return ret;
	}

	/**
	 * 判断用户是否为新手
	 * 
	 * @param accountId
	 * @return
	 */
	public boolean isNewCustomer(String accountId) {
		int investCount = projectInvestmentRecordDao
				.getInvestCountIsNewCustomerByAccountId(accountId);
		if (investCount == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取指定用户的投资记录数量
	 * @param accountId
	 * @return
	 */
	public int getInvestCountByAccountId(Long accountId) {
		return dao.getInvestCountByAccountId(accountId);
	}

	/**
	 * 获取指定用户的（项目处于放款及其以后状态）的投资记录数量
	 * @param accountId
	 * @return
	 */
	public int getRecordCountAfterRepaymentByAccountId(Long accountId) {
		return dao.getRecordCountAfterRepaymentByAccountId(accountId);
	}
	
	/**
	 * 获取用户首条正常投资记录
	 * @param accountId
	 * @return
	 */
	public ProjectInvestmentRecord getCustomerFirstNormalRecord(Long accountId) {
		return dao.getCustomerFirstNormalRecord(accountId);
	}

	/**
	 * 获取双旦期间注册用户符合条件的投资列表（投资额>=1000，group by investment_user_id）
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<ProjectInvestmentRecord> findDoubleEggList(Date beginDate, Date endDate) {
		return dao.findDoubleEggList(beginDate, endDate);
	}

	/**
	 * 获得用户某一时间段内的年化投资额
	 * @param accountId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public double getCustomerAnnualizedAmountDuringTime(Long accountId, Date startDate, Date endDate) {
		Double amount = dao.getCustomerAnnualizedAmountDuringTime(accountId, startDate, endDate);
		return amount == null ? 0d : amount;
	}

	public String getCustomerStatusByAccountId(Long accountId) {
		// TODO Auto-generated method stub
		return projectInvestmentRecordDao.getCustomerStatusByAccountId(accountId);
	}

	

	public Map<String, Object> findCustomerInvestmentAmountDistributionNoPage(Date beginOpDt, Date endOpDt) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginOpDt", beginOpDt);
		map.put("endOpDt", endOpDt);
		return projectInvestmentRecordDao.findCustomerInvestmentAmountDistributionNoPage(map);
	}
	
	/**
	 *查询在各个投资范围内的的平局投资额度
	 * @param beginOpDt
	 * @param endOpDt
	 * @return
	 */
	public Map<String, Object> findCustomerInvestmentAmountDistributionAvgNoPage(Date beginOpDt, Date endOpDt) {
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("beginOpDt", beginOpDt);
		map1.put("endOpDt", endOpDt);
		return projectInvestmentRecordDao.findCustomerInvestmentAmountDistributionAvgNoPage(map1);
	}

	/**
	 * 日投资总额统计（所有用户的投资额及友好用户的投资额度）
	 * @param page
	 * @param beginOpDt
	 * @param endOpDt
	 * @return
	 */
	public Page<Map<String, Object>> findCustomerInvestmentStatistics(Page<Map<String, Object>> page, Date beginOpDt, Date endOpDt) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("beginOpDt", beginOpDt);
		map.put("endOpDt", endOpDt);
		page.setList(dao.findCustomerInvestmentStatistics(map));
		return page;
	}
	/**
	 * 查询用户的投资统计无分页
	 * @param beginOpDt
	 * @param endOpDt
	 * @return
	 */
	public List<Map<String, Object>> findCustomerInvestmentStatisticsNoPage(
			Date beginOpDt, Date endOpDt) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginOpDt", beginOpDt);
		map.put("endOpDt", endOpDt);
		 return dao.findCustomerInvestmentStatistics(map);
	}

	/**
	 *  按日期查询全部用户每日投资额度清单
	 * @param page
	 * @param date
	 * @return
	 */
	public Page<Map<String, Object>> findInvestmentRecordByDate(
			Page<Map<String, Object>> page, Date date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("date", date);
		page.setList(dao.findInvestmentRecordByDate(map));
		return page;
	}

	/**
	 *按日期查询小伙伴每日投资额度清单
	 * @param page
	 * @param date
	 * @return
	 */
	public Page<Map<String, Object>> findFriendsInvestmentRecordByDate(
			Page<Map<String, Object>> page, Date date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("date", date);
		page.setList(dao.findFriendsInvestmentRecordByDate(map));
		return page;
	}
	  /**
	   * 查询全部用户的投资总额
	   * @param beginOpDt
	   * @param endOpDt
	   * @return
	   */
	public Map<String, Object> findCustomerInvestmentAmount(Date beginOpDt,
			Date endOpDt) {
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("beginOpDt", beginOpDt);
		map2.put("endOpDt", endOpDt);
		return projectInvestmentRecordDao.findCustomerInvestmentAmount(map2);
	}

	/**
	 * 获取用户的投资冻结资金
	 * @param accountId
	 * @return
     */
	public Double getSumFrozenAmount(long accountId){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("accountId", accountId);
		params.put("status", ProjectConstant.PROJECT_INVESTMENT_STATUS_FREEZE);
		return projectInvestmentRecordDao.sumAmount(params);
	}

}