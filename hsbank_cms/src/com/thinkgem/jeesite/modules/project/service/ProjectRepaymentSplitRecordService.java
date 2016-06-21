/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.to.RepaymentCalendarDetailResp;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentSplitRecordDao;

/**
 * 还款记录查分明细Service
 * @author yangtao
 * @version 2015-07-13
 */
@Service
@Transactional(readOnly = true)
public class ProjectRepaymentSplitRecordService extends CrudService<ProjectRepaymentSplitRecordDao, ProjectRepaymentSplitRecord> {
	@Autowired
	ProjectRepaymentSplitRecordDao projectRepaymentSplitRecordDao;
	public ProjectRepaymentSplitRecord get(String id) {
		return super.get(id);
	}
	
	public List<ProjectRepaymentSplitRecord> findList(ProjectRepaymentSplitRecord projectRepaymentSplitRecord) {
		return super.findList(projectRepaymentSplitRecord);
	}
	
	public Page<ProjectRepaymentSplitRecord> findPage(Page<ProjectRepaymentSplitRecord> page, ProjectRepaymentSplitRecord projectRepaymentSplitRecord) {
		return super.findPage(page, projectRepaymentSplitRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectRepaymentSplitRecord projectRepaymentSplitRecord) {
		super.save(projectRepaymentSplitRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectRepaymentSplitRecord projectRepaymentSplitRecord) {
		super.delete(projectRepaymentSplitRecord);
	}
	/**
	 * 获得正在进行的项目中，用户已获得投资收益总和
	 * @param accountId
	 * @return
	 */
	public Float getSumProfitInRunningProjectByAccountId(Long accountId){
		return projectRepaymentSplitRecordDao.getSumProfitInRunningProjectByAccountId(accountId);
	}
	
	/**
	 * 获得正在进行的项目中，用户待还资金总和
	 * @param accountId
	 * @return
	 */
	public Float getSumLoanInRunningProjectByAccountId(Long accountId){
		return projectRepaymentSplitRecordDao.getSumLoanInRunningProjectByAccountId(accountId);
	}
	/**
	 * 获得用户待还款项目概况
	 * @param accountId
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getLoanListInRunningProjectByAccountId(Long accountId){
		return projectRepaymentSplitRecordDao.getLoanListInRunningProjectByAccountId(accountId);
	}
	/**
	 * 获得用户将收到的资金和已收到的资金清单
	 * @param accountId
	 * @param status
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord>  getRepaymentListByAccountIdAndStatus(String accountId,String status,Date startDate,Date endDate){
		return projectRepaymentSplitRecordDao.getRepaymentListByAccountIdAndStatus(accountId, status, startDate, endDate);
	}

	/**
	 * 账户还款信息列表
	 * @param accountId
	 * @param status
	 * @param startDate
	 * @param endDate
     * @return
     */
	public List<ProjectRepaymentSplitRecord> getProjectRepaymentInfoList(long accountId,String status,Date startDate,Date endDate){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId",accountId);
		map.put("status",status);
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		return projectRepaymentSplitRecordDao.getProjectRepaymentInfoList(map);
	}

	/**
	 * 账户还款信息汇总
	 * @param accountId
	 * @param status
	 * @param startDate
	 * @param endDate
     * @return
     */
	public Map<String,Object> getSumProjectRepaymentInfo(long accountId,String status,Date startDate,Date endDate){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId",accountId);
		map.put("status",status);
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		return projectRepaymentSplitRecordDao.getSumProjectRepaymentInfo(map);
	}
	/**
	 * 根据投资记录编号获取还款计划
	 * @param recordId
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getRepaymentListByRecordId(String recordId){
		return projectRepaymentSplitRecordDao.getRepaymentListByRecordId(recordId,null);
	}
	
	/**
	 * 根据投资记录id及状态获取还款计划
	 * @param recordId
	 * @param status
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getRepaymentListByRecordIdAndStatus(String recordId,String status){
		return projectRepaymentSplitRecordDao.getRepaymentListByRecordId(recordId,status);
	}
	
	/**
	 * 根据查询字段得到page
	 * @param accountId
	 * @param status
	 * @param pageSearchBean
	 * @return
	 */
	public Page<ProjectRepaymentSplitRecord> searchPage(Long accountId,String status, PageSearchBean pageSearchBean) {
		Page<ProjectRepaymentSplitRecord> page = new Page<ProjectRepaymentSplitRecord>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("status", status);
		map.put("accountId", accountId);
		map.put("startDateTime", pageSearchBean.getStartDateTime());
		map.put("endDateTime", pageSearchBean.getEndDateTime());
		page.setList(dao.searchList(map));
		return page;
	}
	
	/**
	 * 获取指定项目、指定收款人的还款信息
	 * @param projectId
	 * @param accountId
	 * @return
	 */
	public ProjectRepaymentSplitRecord getRepaymentInfoByProjectAndccountId(String projectId, Long accountId, Long recordId){
		ProjectRepaymentSplitRecord rSplitRecord = new ProjectRepaymentSplitRecord();
		List<ProjectRepaymentSplitRecord> pList = dao.getRepaymentListByProjectAndccountId(projectId, accountId, recordId);
		Double sumReceivedProfit = 0.00; //合计已收收益总额
		Double sumWillProfit = 0.00; //合计待收收益总额
		int receivedPeriods = 0;	//已还款期数
		int totalPeriods = 0;	//总还款期数
		Date LastRepaymentDt = new Date();	//最后还款日期
		Date firstRepaymentDt = new Date();
		if(pList != null && pList.size() > 0){
			firstRepaymentDt = pList.get(pList.size()-1).getRepaymentDt();
			LastRepaymentDt =pList.get(0).getRepaymentDt();
			totalPeriods = pList.size();
		}
		for(ProjectRepaymentSplitRecord record : pList){
			if(ProjectConstant.PROJECT_REPAY_STATUS_BUDGET.equals(record.getStatus())){
				sumWillProfit += record.getInterest();
			}else if(ProjectConstant.PROJECT_REPAY_STATUS_ALREADY.equals(record.getStatus())){
				sumReceivedProfit += record.getInterest();
				receivedPeriods += 1;
			}
		}
		rSplitRecord.setSumReceivedProfit(LoanUtil.formatAmount(sumReceivedProfit));
		rSplitRecord.setSumWillProfit(LoanUtil.formatAmount(sumWillProfit));
		rSplitRecord.setReceivedPeriods(receivedPeriods);
		rSplitRecord.setTotalPeriods(totalPeriods);
		rSplitRecord.setLastRepaymentDt(LastRepaymentDt);
		rSplitRecord.setFirstRepaymentDt(firstRepaymentDt);
		return rSplitRecord;
	}
	
	/**
	 * 获取还款日历数据
	 * @param accountId
	 * @param year
	 * @param month
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord>  getRepaymentCalendarList(Long accountId,String year,String month){
		return projectRepaymentSplitRecordDao.getRepaymentCalendarList(accountId, year, month);
	}
	
	/**
	 * 获取账户某一天还款计划列表
	 * @param accountId
	 * @param year
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord>  getRepaymentCalendarDetailList(Long accountId,String year,String month){
		return projectRepaymentSplitRecordDao.getRepaymentCalendarDetailList(accountId, year, month);
	}
	
	/**
	 * 返回map日历数据
	 * @param accountId
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Object> getRepaymentCalendarDetailMap(Long accountId,String year,String month){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Double> mapSum = new HashMap<String,Double>();
		Map<Date,List<Object>> mapList = new HashMap<Date,List<Object>>();
		List<ProjectRepaymentSplitRecord> list = projectRepaymentSplitRecordDao.getRepaymentCalendarDetailList(accountId, year, month);
		Double sumRepaymentAmout = 0.00;
		Double repaymentAmout = 0.00;
		for(ProjectRepaymentSplitRecord record: list){
			RepaymentCalendarDetailResp dataDetail = new RepaymentCalendarDetailResp();
			Date repaymentDt = record.getRepaymentDt();
			if(ProjectConstant.PROJECT_REPAY_STATUS_ALREADY.equals(record.getStatus())){
				repaymentAmout += record.getMoney();
			}
			if(ProjectConstant.PROJECT_REPAY_STATUS_ALREADY.equals(record.getStatus())
					||ProjectConstant.PROJECT_REPAY_STATUS_BUDGET.equals(record.getStatus())){
				sumRepaymentAmout += record.getMoney();
			}
			if(repaymentDt != null){
				if(mapList.get(repaymentDt) == null ){
					List<Object> nList=new ArrayList<Object>();
					setDataDetail(dataDetail, record);
					nList.add(dataDetail);
					mapList.put(repaymentDt, nList);
				}else{
					List<Object> oList=mapList.get(repaymentDt);
					setDataDetail(dataDetail, record);
					oList.add(dataDetail);
					mapList.put(repaymentDt, oList);
				}
			}
			
		}
		mapSum.put("sumRepaymentAmout", NumberUtils.formatWithScale(sumRepaymentAmout,2));
		mapSum.put("repaymentAmout", NumberUtils.formatWithScale(repaymentAmout,2));
		map.put("mapSum", mapSum);
		map.put("mapList", mapList);
		return map;
	}
	
	/**
	 * 设置RepaymentCalendarDetailResp 属性
	 * @param dataDetail
	 * @param record
	 * @return
	 */
	public RepaymentCalendarDetailResp setDataDetail(RepaymentCalendarDetailResp dataDetail, ProjectRepaymentSplitRecord record){
		dataDetail.setProjectName(record.getProjectBaseInfo() != null ? record.getProjectBaseInfo().getProjectName() : "");
		dataDetail.setPlanDate(DateUtils.formatDate(record.getRepaymentDt()));
		dataDetail.setPlanMoney(NumberUtils.formatWithScale(record.getMoney(),2));
		dataDetail.setPrincipal(NumberUtils.formatWithScale(record.getPrincipal(),2));
		dataDetail.setInterest(NumberUtils.formatWithScale(record.getInterest(),2));
		dataDetail.setRemainingPrincipal(NumberUtils.formatWithScale(record.getRemainedPrincipal(),2));
		return dataDetail;
	}
	

	/**
	 * 获取用户待收款金额汇总信息（待收款总额 money、待收本金 principal、待收利息 interest）
	 * @param accountId
	 * @return
	 */
	public Map<String,Object> getUnReceivedMoneySummaryByAccountId(Long accountId) {
		return dao.getUnReceivedMoneySummaryByAccountId(accountId);
	}
	
	/**
	 * 获取用户待收款金额汇总信息（已收款总额 money、已收本金 principal、已收利息 interest）
	 * @param accountId
	 * @return
	 */
	public Map<String,Object> getReceivedMoneySummaryByAccountId(Long accountId) {
		return dao.getReceivedMoneySummaryByAccountId(accountId);
	}

	/**
	 * 获取项目还款计划（以天为单位，得到某天总的还款额）
	 * @param page
	 * @param platformUserNo 
	 * @param endDate 
	 * @param startDate 
	 * @param startDate
	 * @param endDate
	 * @param platformUserNo 
	 * @return
	 */
	public Page<Map<String, Object>> findProjectRepayPlanList(Page<Map<String, Object>> page, Date startDate, Date endDate, String platformUserNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("platformUserNo", platformUserNo);
		page.setList(dao.findProjectRepayPlanList(map));
		return page;
	}
	
	
	/**
	 * 获取项目还款计划（以天为单位，得到某天总的还款额）无分页
	 * @param page
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> findProjectRepayPlanListNoPage( Date startDate, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return dao.findProjectRepayPlanList(map);
	}

	/**
	 * 查询某一天的还款计划
	 * @param page
	 * @param date
	 * @return
	 */
	public Page<Map<String, Object>> findProjectRepayPlanListByDate(Page<Map<String, Object>> page, Date date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("date", date);
		page.setList(dao.findProjectRepayPlanListByDate(map));
		return page;
	}

	/**
	 * 根据项目id获取项目的本金和项目利息
	 * @param projectId
	 * @return
	 */
	public Map<String, Object> findSumPrincipalAndSumInterest(String projectId) {
		return projectRepaymentSplitRecordDao.findSumPrincipalAndSumInterest(projectId);
	}


	public Page<Map<String, Object>> selectSumPrincipalAndSumInterest(Page<Map<String, Object>> page,
			ProjectRepaymentSplitRecord projectRepaymentSplitRecord) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("projectId", projectRepaymentSplitRecord.getProjectId());
		page.setList(projectRepaymentSplitRecordDao.selectSumPrincipalAndSumInterest(map));
		return page;
	}
	/**
	 * 查询三天内的还款计划
	 * @param startDate
	 * @param endDate
	 * @param platformUserNo
	 * @return
	 */
	public Double amountStatistics(Date startDate, Date endDate,String platformUserNo) {
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("platformUserNo", platformUserNo);
		return projectRepaymentSplitRecordDao.amountStatistics(params);
	}
	
	
	
	
}