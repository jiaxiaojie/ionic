/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.entity.api.ProjectSearchBean;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.PageBean;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.entity.front.ProjectTransferSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentSplitRecordDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectTransferInfoDao;
import com.hsbank.util.type.NumberUtil;

/**
 * 债权转让Service
 * 
 * @author yangtao
 * @version 2015-06-25
 */
@Service
@Transactional(readOnly = true)
public class ProjectTransferInfoService extends
		CrudService<ProjectTransferInfoDao, ProjectTransferInfo> {

	@Autowired
	ProjectTransferInfoDao projectTransferInfoDao;
	@Autowired
	ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	ProjectRepaymentSplitRecordDao projectRepaymentSplitRecordDao;
	@Autowired
	ProjectExecuteSnapshotDao projectExecuteSnapshotDao;
	
	public ProjectTransferInfo get(String id) {
		return super.get(id);
	}

	public List<ProjectTransferInfo> findList(
			ProjectTransferInfo projectTransferInfo) {
		return super.findList(projectTransferInfo);
	}

	public Page<ProjectTransferInfo> findPage(Page<ProjectTransferInfo> page,
			ProjectTransferInfo projectTransferInfo) {
		return super.findPage(page, projectTransferInfo);
	}

	@Transactional(readOnly = false)
	public void save(ProjectTransferInfo projectTransferInfo) {
		super.save(projectTransferInfo);
	}

	@Transactional(readOnly = false)
	public void delete(ProjectTransferInfo projectTransferInfo) {
		super.delete(projectTransferInfo);
	}

	@Transactional(readOnly = false)
	public boolean setRecommend(String flag, String transferId) {
		try {
			projectTransferInfoDao.setRecommend(flag, transferId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 获得转让中的项目列表
	 * 
	 * @param accountId
	 * @return
	 */
	public int getTransferingProjectCount(String accountId) {
		return projectTransferInfoDao.getTransferingProjectCount(accountId);
	}

	/**
	 * 获得项目总数
	 * @param accountId
	 * @return
     */
	public int getProjectCount(String accountId) {
		return projectTransferInfoDao.getProjectCount(accountId);
	}

	/**
	 * 获得已转让结束的项目数量
	 * 
	 * @param accountId
	 * @return
	 */
	public int getTransferEndProjectCount(String accountId) {
		return projectTransferInfoDao.getTransferEndProjectCount(accountId);
	}

	/**
	 * 获取债权转让项目列表，利率降序排列
	 * 
	 * @return
	 */
	public List<ProjectTransferInfo> getRecommendList() {
		return getRecommendList(4);
	}

	/**
	 * 获取债权转让项目列表，利率降序排列，数量限制为limit
	 * 
	 * @return
	 */
	public List<ProjectTransferInfo> getRecommendList(int limit) {
		return dao.getRecommendList(limit);
	}

	/**
	 * 根据transferProjectSearchBean中的搜索项，取得债权转让项目列表
	 * 
	 * @param projectTransferSearchBean
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<ProjectTransferInfo> searchPage(
			ProjectTransferSearchBean projectTransferSearchBean, String pageNo,
			int pageSize) {
		Page<ProjectTransferInfo> page = new Page<ProjectTransferInfo>(
				NumberUtil.toInt(pageNo, 1), pageSize);
		// 将page绑定到对象上，以便分页拦截器获取
		projectTransferSearchBean.setPage(page);
		page.setList(dao.searchList(projectTransferSearchBean));
		return page;
	}

	/**
	 * 债权分页列表
	 * @param pageNumber
     * @param pageSize
     * @return
     */
	public List<ProjectTransferInfo> getProjectListPage(Integer pageNumber, Integer pageSize){
		Map<String, Object>  map  = new  HashMap<String,Object>();
		PageBean  pageBean  = new PageBean(pageNumber, pageSize);
		map.put("startNumber",pageBean.getStartNumber());
		map.put("endNumber", pageBean.getEndNumber());
		return  dao.searchPageList(map);
	}

	/**
	 * 新版债权分页
	 * @param status
	 * @param duration
	 * @param rate
	 * @param repaymentMode
	 * @param type
	 * @param flag
	 * @param pageNumber
     * @param pageSize
     * @return
     */
	public List<ProjectTransferInfo>  findCreditPageList(String status,String duration,String rate,String repaymentMode,
														String type, Integer pageNumber, Integer pageSize) {
		Map<String, Object> map1 = new HashMap<String,Object>();
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		map1.put("status",status);
		map1.put("duration",duration);
		map1.put("rate",rate);
		map1.put("repaymentMode",repaymentMode);
		map1.put("type",type);
		map1.put("startNumber", pageBean.getStartNumber());
		map1.put("endNumber", pageBean.getEndNumber());
		return dao.findCreditPageList(map1);
	}



	/**
	 * 根据投资记录创建新的转让项目
	 * 
	 * @param recordId
	 * @param transferMoney
	 * @param transferDateLine
	 */
	@Transactional(readOnly = false)
	public void makeNewTransfer(String recordId, String transferMoney,
			String transferDateLine) {

		// 获得原始投资记录
		ProjectInvestmentRecord pir = projectInvestmentRecordDao.get(recordId);
		// 获得对应的投资项目信息
		ProjectBaseInfo pbi = projectBaseInfoDao.get(pir.getProjectId().longValue()+"");
		//获取下一还款日期
		List<ProjectRepaymentSplitRecord> splitList=projectRepaymentSplitRecordDao.getWillReapyListByInvestmentRecordId(recordId,new Date());
		Date nextRepayDate=new Date();
		if((splitList!=null)&&(splitList.size()>0)){
			nextRepayDate=splitList.get(0).getRepaymentDt();
		}
		// 构造对应的转让项目
		ProjectTransferInfo pti = new ProjectTransferInfo();
		
		pti.setParentTransferProjectId(pir.getTransferProjectId());
		pti.setProjectId(pir.getProjectId());
		pti.setIsRecommend("0");
		pti.setInvestmentRecordId(new Long(recordId));
		pti.setProjectEndDate(pbi.getLastRepaymentDate());
		pti.setNextRepaymentDate(nextRepayDate);
		pti.setTransferor(pir.getInvestmentUserId());
		pti.setTransferPrice(new Double(transferMoney));
		pti.setFairPrice(transferMoney);
		pti.setDiscountDate(DateUtils.parseDate(transferDateLine));
		pti.setServiceChargeType(ProjectConstant.PROJECT_TRANSFER_SERVICE_CHAGE_TYPE_UP_DOWN_HALF);
		pti.setCreateDate(new Date());
		DecimalFormat df = new DecimalFormat("######0.00");
		Double transactionCosts=new Double(transferMoney)*ProjectConstant.PROJECT_TRANSFER_SERVICE_RATE;
		pti.setTransactionCosts( new Double(df.format(transactionCosts)));
		pti.setRemainderCreditor(new Double(transferMoney));
		pti.setStatus("0");
		//更新投资状态为已转让
		projectTransferInfoDao.insert(pti);
		//获得项目剩余期限
		ProjectExecuteSnapshot pss=projectExecuteSnapshotDao.getTransferSnapshot(pti.getProjectId(),new Long("0"));
		ProjectExecuteSnapshot pes = new ProjectExecuteSnapshot();
		pes.setTransferProjectId(pti.getTransferProjectId());
		pes.setProjectId(pir.getProjectId());
		pes.setFinanceMoney(pti.getTransferPrice());
		pes.setEndFinanceMoney(0.0);
		pes.setEndRepayMoney(0.0);
		pes.setSumServiceCharge(new Double(0));
		pes.setRemainingTime(pss.getRemainingTime());
		pes.setStatus("0");
		projectExecuteSnapshotDao.insert(pes);
	}
	@Transactional(readOnly = false)
	public void updateStatus(String transferProjectId,String status){
		projectTransferInfoDao.updateStatus(transferProjectId, status);
	}
	/**
	 * 获得指定账号的转让中的项目列表
	 * @param accountId
	 * @param pageNo 
	 * @return
	 */
	public Page<ProjectTransferInfo> findTransferingProjectListByAccountId(String accountId, String pageNo){
		Page<ProjectTransferInfo> page = new Page<ProjectTransferInfo>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("page", page);
		page.setList(projectTransferInfoDao.findTransferingProjectListByAccountId(map));
		return page;
	}
	/**
	 * 获得指定账号的已转让的项目列表
	 * @param accountId
	 * @param pageNo 
	 * @return
	 */
	public Page<ProjectTransferInfo> findTransferEndProjectListByAccountId(String accountId, String pageNo){
		Page<ProjectTransferInfo> page = new Page<ProjectTransferInfo>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("page", page);
		page.setList(projectTransferInfoDao.findTransferEndProjectListByAccountId(map));
		return page;
	}
	/**
	 * 获得转让中的项目
	 * @return
	 */
	public List<ProjectTransferInfo> findTransferingProjectList(){
		return projectTransferInfoDao.findTransferingProjectList();
	}

	/**
	 * 根据状态获取转让项目数量
	 * @param status
	 * @return
	 */
	public Integer getCountByStatus(String status) {
		return dao.getCountByStatus(status);
	}

	/**
	 * 查询转让项目列表
	 * @param projectSearchBean
	 * @return
	 */
	public List<Map<String,Object>> findTransferProjectList(ProjectSearchBean projectSearchBean) {
		return dao.findTransferProjectList(projectSearchBean);
	}

	/**
	 * 得到转让项目数量
	 * @param projectSearchBean
	 * @return
	 */
	public Integer getCount(ProjectSearchBean projectSearchBean) {
		return dao.getCount(projectSearchBean);
	}

	public Integer getCreditCount(String status, String duration, String rate, String repaymentMode, String type) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("status",status);
		map.put("duration",duration);
		map.put("rate",rate);
		map.put("repaymentMode",repaymentMode);
		map.put("type",type);
		return dao.getCreditCount(map);
	}




	// /**
	// * 获得已转让结束的项目数量
	// * @param accountId
	// * @return
	// */
	// public List<ProjectTransferInfo> getTransferEndProjectCount(String
	// accountId){
	// return projectTransferInfoDao.getTransferEndProjectCount(accountId);
	// }

}