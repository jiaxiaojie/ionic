/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.api.PageBean;
import com.thinkgem.jeesite.modules.credit.service.CreditBaseInfoService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.entity.api.ProjectSearch;
import com.thinkgem.jeesite.modules.entity.front.ProjectSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.*;
import com.thinkgem.jeesite.modules.project.service.util.handler.RepaymentPlanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 借贷合同管理Service
 * 
 * @author yangtao
 * @version 2015-06-24
 */
@Service
@Transactional(readOnly = true)
public class ProjectBaseInfoService extends
		CrudService<ProjectBaseInfoDao, ProjectBaseInfo> {
	@Autowired
	private ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	private ProjectReviewRecordDao projectReviewRecordDao;
	
	@Autowired
	private ProjectRepaymentPlanDao projectRepaymentPlanDao;
	@Autowired
	private ProjectExecuteSnapshotDao projectExecuteSnapshotDao;
	@Autowired
	private ProjectDateNodeDao projectDateNodeDao;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	@Autowired
	private ProjectShowTermDao projectShowTermDao;
	@Autowired
	private CreditBaseInfoService creditBaseInfoService;

	public ProjectBaseInfo get(String id) {
		return super.get(id);
	}

	public List<ProjectBaseInfo> findList(ProjectBaseInfo projectBaseInfo) {
		return super.findList(projectBaseInfo);
	}

	public Page<ProjectBaseInfo> findPage(Page<ProjectBaseInfo> page,
			ProjectBaseInfo projectBaseInfo) {
		return super.findPage(page, projectBaseInfo);
	}

	public Page<ProjectBaseInfo> findSortedPage(Page<ProjectBaseInfo> page,
			ProjectBaseInfo projectBaseInfo) {
		projectBaseInfo.setPage(page);
		page.setList(dao.findSortedList(projectBaseInfo));
		return page;
	}

	public Page<ProjectBaseInfo> findCreatePage(Page<ProjectBaseInfo> page,
			ProjectBaseInfo projectBaseInfo) {
		projectBaseInfo.setPage(page);
		page.setList(projectBaseInfoDao.findCreateList(projectBaseInfo));
		return page;
	}

	public Page<ProjectBaseInfo> findReviewPage(Page<ProjectBaseInfo> page,
			ProjectBaseInfo projectBaseInfo) {
		projectBaseInfo.setProjectStatus(ProjectConstant.PROJECT_STATUS_SUBMIT);
		projectBaseInfo.setPage(page);
		page.setList(projectBaseInfoDao.findCreateList(projectBaseInfo));
		return page;
	}
	
	public Page<ProjectBaseInfo> findLoanPage(Page<ProjectBaseInfo> page,
			ProjectBaseInfo projectBaseInfo) {
		projectBaseInfo.setPage(page);
		page.setList(projectBaseInfoDao.findLoanList(projectBaseInfo));
		return page;
	}
	
	public Page<Map<String,Object>> findAlreadyLoanPage(Page<Map<String,Object>> page,
			ProjectBaseInfo projectBaseInfo) {
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("pbi", projectBaseInfo);
		
		
		page.setList(projectBaseInfoDao.findAlreadyLoanList(map));
		return page;
	}
	

	public List<ProjectBaseInfo> findIsNewUserProjectList(
			ProjectBaseInfo projectBaseInfo) {
		List<ProjectBaseInfo> list = projectBaseInfoDao.findIsNewUserList(projectBaseInfo);
		return list;
	}
	
	public Page<ProjectBaseInfo> findClearPage(Page<ProjectBaseInfo> page,
			ProjectBaseInfo projectBaseInfo) {
		projectBaseInfo.setProjectStatus(ProjectConstant.PROJECT_STATUS_REPAYMENTING);
		projectBaseInfo.setPage(page);
		page.setList(projectBaseInfoDao.findCreateList(projectBaseInfo));
		return page;
	}

	public Page<ProjectBaseInfo> findCustomerAsBorrowerPage(
			Page<ProjectBaseInfo> page, ProjectBaseInfo projectBaseInfo) {
		projectBaseInfo.setPage(page);
		page.setList(projectBaseInfoDao
				.findCustomerAsBorrowerProjectList(projectBaseInfo));
		return page;
	}

	public Page<ProjectBaseInfo> findCustomerAsInvestmentPage(
			Page<ProjectBaseInfo> page, ProjectBaseInfo projectBaseInfo) {
		projectBaseInfo.setPage(page);
		page.setList(projectBaseInfoDao
				.findCustomerAsInvestmentProjectList(projectBaseInfo));
		return page;
	}

	public Page<ProjectBaseInfo> findCustomerAsTransferPage(
			Page<ProjectBaseInfo> page, ProjectBaseInfo projectBaseInfo) {
		projectBaseInfo.setPage(page);
		page.setList(projectBaseInfoDao
				.findCustomerAsTransferProjectList(projectBaseInfo));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(ProjectBaseInfo projectBaseInfo) {
		if ((projectBaseInfo.getProjectId() == null)
				|| projectBaseInfo.getProjectId().equals("")) {
			projectBaseInfoDao.insert(projectBaseInfo);
		} else {
			projectBaseInfoDao.update(projectBaseInfo);
		}
		//先删除 后批量添加项目显示终端信息
		projectShowTermDao.deleteByProjectId(projectBaseInfo.getProjectId());
		List<ProjectShowTerm> projectShowTermList = new ArrayList<ProjectShowTerm>();
		
		List<String> showTermList = projectBaseInfo.getShowTermList();
		if(showTermList != null){
			for(String termCode : showTermList) {
				ProjectShowTerm projectShowTerm = new ProjectShowTerm();
				projectShowTerm.setTermCode(termCode);
				projectShowTerm.setProjectId(Long.parseLong(projectBaseInfo.getProjectId()));
				projectShowTermList.add(projectShowTerm);
			}
			projectShowTermDao.insertBatch(projectShowTermList);
		}
	}
	
	@Transactional(readOnly = false)
	public void copy(ProjectBaseInfo projectBaseInfo) {
		String oldProjectId = projectBaseInfo.getProjectId();
		//复制产品基本信息
		projectBaseInfo.setProjectName(projectBaseInfo.getProjectName() + "--副本");
		projectBaseInfo.setCreateDt(new Date());
		projectBaseInfo.setPublishDt(new Date());
		projectBaseInfo.setProjectStatus(ProjectConstant.PROJECT_STATUS_CREATE);
		projectBaseInfo.setIsLoan(ProjectConstant.DICT_DEFAULT_VALUE);
		projectBaseInfoDao.copy(projectBaseInfo);
		String projectId = projectBaseInfo.getProjectId();
		//先删除 后批量添加项目显示终端信息
		projectShowTermDao.deleteByProjectId(projectId);
		List<ProjectShowTerm> list = projectShowTermDao.findListByProjectId(oldProjectId);
		List<ProjectShowTerm> projectShowTermList = new ArrayList<ProjectShowTerm>();
		for(ProjectShowTerm showTerm : list) {
			ProjectShowTerm projectShowTerm = new ProjectShowTerm();
			projectShowTerm.setTermCode(showTerm.getTermCode());
			projectShowTerm.setProjectId(Long.parseLong(projectId));
			projectShowTermList.add(projectShowTerm);
		}
		if(projectShowTermList.size()>0){
			projectShowTermDao.insertBatch(projectShowTermList);
		}
	}

	@Transactional(readOnly = false)
	public void submit(String projectId) {
		projectBaseInfoDao.submit(projectId);
	}
	/**
	 * 获得推荐项目列表
	 * @return
	 */
	public List<ProjectBaseInfo> findRecommendProjectList(){
		return projectBaseInfoDao.findRecommendProjectList();
	}
	
	@Transactional(readOnly = false)
	public void review(ProjectBaseInfo projectBaseInfo) {
		projectBaseInfo.setEarlyRepaymentRate(ProjectConstant.PROJECT_EARLY_REPAY_DEFAULT_PAYMENT_RATIO);
		if(projectBaseInfo.getProjectTypeCode().longValue()!=2){
			projectBaseInfo.setServiceCharge(0D);
		}
		if((projectBaseInfo.getServiceChargeTypeCode()==null)||(projectBaseInfo.getServiceChargeTypeCode().equals(""))){
			if(projectBaseInfo.getServiceCharge().doubleValue()>0){
				projectBaseInfo.setServiceChargeTypeCode(ProjectConstant.PROJECT_SERVICE_CHARGE_TYPE_BY_RATE);
			}else{
				projectBaseInfo.setServiceChargeTypeCode(ProjectConstant.PROJECT_SERVICE_CHARGE_TYPE_SERVICE_ZERO);
			}
		}
		projectBaseInfoDao.update(projectBaseInfo);
		ProjectReviewRecord pr = new ProjectReviewRecord();
		pr.setProjectId(new Long(projectBaseInfo.getId()));
		pr.setReviewRemark(projectBaseInfo.getReviewRemark());
		pr.setReviewDt(new Date());
		pr.setReviewUserId(projectBaseInfo.getReviewUserId());
		pr.setReviewResult(new Long(projectBaseInfo.getProjectStatus()));
		projectReviewRecordDao.insert(pr);
		
		
		
		// 如果审批通过
		if (projectBaseInfo.getProjectStatus().equals("2")) {
			
			//<0>.更新债权募集中金额
			creditBaseInfoService.addRaisingMoney(projectBaseInfo.getCreditId(),projectBaseInfo.getFinanceMoney());
			
			//<1>.删除旧的还款计划，如果有的话
			projectRepaymentPlanDao.deleteByProjectId(projectBaseInfo.getProjectId());
			//<2>.生成新的还款计划
			repaymentPlanHandler.generateForProject(projectBaseInfo);
			//<3>.更新项目月还本息(等额本息时更新)
			if(ProjectConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL_PLUS_INTEREST.equals(projectBaseInfo.getRepaymentMode())){
				List<ProjectRepaymentPlan> repaymentPlanList = projectRepaymentPlanDao.findListByProjectId(projectBaseInfo.getProjectId());
				if(repaymentPlanList != null && repaymentPlanList.size() > 0){
					String planMoney = repaymentPlanList.get(0).getPlanMoney();
					projectBaseInfoDao.updateProjectMonthRepayMoney(projectBaseInfo.getProjectId(), Double.parseDouble(planMoney));
				}
			}
			Double repaymentMoney = projectRepaymentPlanDao.getSumPlanMoney(projectBaseInfo.getProjectId());
			//<4>.更新还款总额
			projectBaseInfoDao.updateProjectRepaymentMoney(projectBaseInfo.getProjectId(), repaymentMoney);
			//<5>.删除旧的执行快照，如果有的话
			projectExecuteSnapshotDao.deleteByProjectId(projectBaseInfo.getProjectId());
			//<6>.生成执行快照
			ProjectExecuteSnapshot pes = new ProjectExecuteSnapshot();
			pes.setTransferProjectId(new Long("0"));
			pes.setProjectId(new Long(projectBaseInfo.getProjectId()));
			pes.setFinanceMoney(projectBaseInfo.getFinanceMoney().doubleValue());
			pes.setEndFinanceMoney(0.0);
			pes.setEndRepayMoney(0.0);
			pes.setSumServiceCharge(new Double(0));
			pes.setRemainingTime(projectBaseInfo.getProjectDuration().intValue());
			pes.setStatus("0");
			pes.setSumPlatformAmount(new Double(0));
			pes.setSumTicketMoney(new Double(0));
			projectExecuteSnapshotDao.insert(pes);
		}
	}

	@Transactional(readOnly = false)
	public void delete(ProjectBaseInfo projectBaseInfo) {
		super.delete(projectBaseInfo);
	}

	public int checkDuplicate(ProjectBaseInfo projectBaseInfo) {
		return projectBaseInfoDao.checkDuplicate(projectBaseInfo);
	}
	
	/**
	 * 更新项目是否放款
	 * @param projectId
	 * @param isLoan
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateProjectIsLoan(Long projectId, String isLoan) {
		return projectBaseInfoDao.updateProjectIsLoan(projectId, isLoan);
	}

	/**
	 * 根据projectSearchBean中的搜索项，取得项目列表
	 * @param projectSearchBean
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<ProjectBaseInfo> searchPage(ProjectSearchBean projectSearchBean, String pageNo) {
		Page<ProjectBaseInfo> page = new Page<ProjectBaseInfo>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		//将page绑定到对象上，以便分页拦截器获取
		projectSearchBean.setPage(page);
		page.setList(dao.searchList(projectSearchBean));
		return page;
	}
	
	/**
	 * 项目分页列表api
	 * @param projectSearchBean
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<ProjectBaseInfo> getProjectListPage(Integer pageNumber, Integer pageSize) {
		Map<String, Object> map = new HashMap<String,Object>();
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		map.put("startNumber", pageBean.getStartNumber());
		map.put("endNumber", pageBean.getEndNumber());
		return dao.searchPageList(map);
	}
	
	/**
	 * 获得指定用户的借款尚未结清的信用贷款项目总额
	 * @param accountId
	 * @return
	 */
	public Double getNotEndProjectMyCreditProject(String accountId){
		return projectBaseInfoDao.getNotEndProjectMyCreditProject(accountId);
	}
	
	/**
	 * 查询我借款的项目
	 * @param accountId
	 * @param statusFlag
	 * @return
	 */
	public List<ProjectBaseInfo> findMyProjectList(String accountId,String statusFlag) {
		return projectBaseInfoDao.findMyProjectList( accountId,statusFlag);
	}
	
	/**
	 * 我的投资项目
	 * @param accountId
	 * @return
	 */
	public List<ProjectBaseInfo> findMyInvestmentList(String accountId, String flag) {
		return projectBaseInfoDao.findMyInvestmentList(accountId, flag);
	}
	
	/**
	 * 根据外部编号获取投资列表
	 * @param extendNo
	 * @param flag
	 * @return
	 */
	public List<ProjectBaseInfo> findMyInvestmentByExtendNoList(String extendNo, String flag) {
		return projectBaseInfoDao.findMyInvestmentByExtendNoList(extendNo, flag);
	}
	
	/**
	 * 根据投资记录编号查询项目信息
	 * @param recordId
	 * @return
	 */
	public ProjectBaseInfo getProjectByRecordId(String recordId){
		return projectBaseInfoDao.getProjectByRecordId(recordId);
	}

	/**
	 * 获取推荐项目列表
	 * @return
	 */
	public List<ProjectBaseInfo> getRecommendList() {
		return getRecommendList(4);
	}

	/**
	 * 获取推荐项目列表，数量限制为limit
	 * @param limit	列表数量限制
	 * @return
	 */
	public List<ProjectBaseInfo> getRecommendList(int limit) {
		return dao.getRecommendList(limit);
	}
	
	/**
	 * api获取推荐项目列表
	 * @return
	 */
	public List<ProjectBaseInfo> getApiRecommendList() {
		return getApiRecommendList(4);
	}
	
	/**
	 * api获取推荐项目列表，数量限制为limit
	 * @param limit
	 * @return
	 */
	public List<ProjectBaseInfo> getApiRecommendList(int limit) {
		return dao.getApiRecommendList(limit);
	}

	public ProjectDateNode getProjectDateNodeByProjectId(String projectId) {
		return projectDateNodeDao.get(projectId);
	}

	/**
	 * 获取前端融资项目数量
	 * @return
	 */
	public int getOnlineProjectCount() {
		return dao.getOnlineProjectCount();
	}

	/**
	 * 获取投标中项目数量
	 * @return
	 */
	public int getTenderingProjectCount() {
		return dao.getTenderingProjectCount();
	}

	/**
	 * 获取投标结束项目数量
	 * @return
	 */
	public int getTenderedProjectCount() {
		return dao.getTenderedProjectCount();
	}

	/**
	 * 获取还款中项目数量
	 * @return
	 */
	public int getRepaymentingProjectCount() {
		return dao.getRepaymentingProjectCount();
	}

	/**
	 * 获取还款结束项目数量
	 * @return
	 */
	public int getRepaymentedProjectCount() {
		return dao.getRepaymentedProjectCount();
	}

	/**
	 * 根据借款人的accountId查询到其募集中的借款
	 * 		（说明：查询出projectBaseInfo中的autoRepayCode代表的是：此项目是否可以做授权自动还款操作（"1"代表可以；其它：不可以））
	 * @param accountId
	 * @param pageNo
	 * @return
	 */
	public Page<ProjectBaseInfo> findFundingProjectByBorrowerAccountId(Long accountId, String pageNo) {
		Page<ProjectBaseInfo> page = new Page<ProjectBaseInfo>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		ProjectBaseInfo projectBaseInfo = new ProjectBaseInfo();
		projectBaseInfo.setPage(page);
		projectBaseInfo.setBorrowersUser(accountId);
		page.setList(dao.findFundingProjectByBorrowerAccountId(projectBaseInfo));
		return page;
	}

	/**
	 * 根据借款人的accountId查询到其还款中的借款
	 * @param accountId
	 * @param pageNo
	 * @return
	 */
	public Page<ProjectBaseInfo> findRepayingProjectByBorrowerAccountId(Long accountId, String pageNo) {
		Page<ProjectBaseInfo> page = new Page<ProjectBaseInfo>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		String[] projectStatuses = {"5"};
		return findProjectByBorrowerAccountIdAndProjectStatus(page, accountId, projectStatuses);
	}

	/**
	 * 根据借款人的accountId查询到其已还清的借款
	 * @param accountId
	 * @param pageNo
	 * @return
	 */
	public Page<ProjectBaseInfo> findEndedProjectByBorrowerAccountId(Long accountId, String pageNo) {
		Page<ProjectBaseInfo> page = new Page<ProjectBaseInfo>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		String[] projectStatuses = {"6","7"};
		return findProjectByBorrowerAccountIdAndProjectStatus(page, accountId, projectStatuses);
	}

	/**
	 * 根据借款人的accountId及项目状态查询项目
	 * @param page
	 * @param accountId
	 * @param projectStatuses
	 * @return
	 */
	private Page<ProjectBaseInfo> findProjectByBorrowerAccountIdAndProjectStatus(Page<ProjectBaseInfo> page, Long accountId, String[] projectStatuses) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("accountId", accountId);
		map.put("projectStatuses", projectStatuses);
		page.setList(dao.findProjectByBorrowerAccountIdAndProjectStatus(map));
		return page;
	}
	
	/**
	 * 更新projectBaseInfo表isAutoRepay状态（"0"为未授权；"1"为已授权）
	 * @param projectId
	 * @param isAutoRepay
	 */
	@Transactional(readOnly = false)
	public void updateIsAutoRepay(String projectId, String isAutoRepay) {
		dao.updateIsAutoRepay(projectId, isAutoRepay);
	}

	/**
	 * 更新projectBaseInfo表autoRepayCode（即为授权自动还款的requestNo）
	 * @param projectId
	 * @param requestNo
	 */
	@Transactional(readOnly = false)
	public void updateAutoRepayCode(String projectId, String requestNo) {
		dao.updateAutoRepayCode(projectId, requestNo);
	}
	/**
	 * 获得运行中的项目
	 * @return
	 */
	public List<ProjectBaseInfo> findRunningProject(){
		return dao.findRunningProject();
	}
	
	/**
	 * 获得逾期还款中的项目列表
	 * @return
	 */
	public List<ProjectBaseInfo> findOverDueProject(){
		return dao.findOverDueProject();
	}

	/**
	 * 更新项目sortInList字段
	 * @param projectId
	 * @param sort
	 */
	@Transactional(readOnly = false)
	public void updateSortInList(int projectId, int sortInList) {
		dao.updateSortInList(projectId, sortInList);
	}

	/**
	 * 更新项目sortInIndex字段
	 * @param projectId
	 * @param sort
	 */
	@Transactional(readOnly = false)
	public void updateSortInIndex(int projectId, int sortInIndex) {
		dao.updateSortInIndex(projectId, sortInIndex);
	}
	
	public Page<Map<String,Object>> findProjectOperationList(Page<Map<String,Object>> page, ProjectBaseInfo projectBaseInfo,Date beginDt, Date endDt) {
		// TODO Auto-generated method stub
		HashMap<String,Object> params = new HashMap<>();
		params.put("page", page);
		params.put("beginDt", beginDt);
		params.put("endDt", endDt);
		params.put("projectName", projectBaseInfo.getProjectName());
		page.setList(projectBaseInfoDao.findProjectOperationList(params));
		return page;
	}
	
	public List<Map<String,Object>> findProjectOperationListNoPage(ProjectBaseInfo projectBaseInfo) {
		// TODO Auto-generated method stub
		HashMap<String,Object> params = new HashMap<>();
		params.put("projectName", projectBaseInfo.getProjectName());
		return projectBaseInfoDao.findProjectOperationList(params);
	}


	public Map<String,Object> getAboutFiles(Integer projectId) {

		return projectBaseInfoDao.getAboutFiles(projectId);
	}
	/**
	 * 查询项目列表
	 * @param projectSearchBean
	 */
	public List<Map<String,Object>> findProjectList(com.thinkgem.jeesite.modules.entity.api.ProjectSearchBean projectSearchBean) {
		return dao.findProjectList(projectSearchBean);
	}

	/**
	 * 查询项目数量
	 * @param projectSearchBean
	 * @return
	 */
	public Integer getCount(com.thinkgem.jeesite.modules.entity.api.ProjectSearchBean projectSearchBean) {
		return dao.getCount(projectSearchBean);
	}

	/**
	 * 查询可投资项目数量
	 * @param projectSearch
	 * @return
	 */
	public Integer getCanInvestmentProjectCount(ProjectSearch projectSearch) {
		return dao.getCanInvestmentProjectCount(projectSearch);
	}


	public Page<ProjectBaseInfo> findProjectInvestmentListPage(Page<ProjectBaseInfo> page, Long accountId,Long projectTypeCode,String flag) {


		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("accountId", accountId);
		map.put("projectTypeCode", projectTypeCode);
		map.put("flag", flag);




		page.setList(projectBaseInfoDao.findInvestmentList(map));
		return page;
	}
}