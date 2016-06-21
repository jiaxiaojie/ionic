/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.to.InvestmentRankResp;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRank;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRankCalendar;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityInfoDao;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1008Handler;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRankCalendarDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRankDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;

/**
 * 投资排行Service
 * @author lizibo
 * @version 2015-11-23
 */
@Service
@Transactional(readOnly = true)
public class ProjectInvestmentRankService extends CrudService<ProjectInvestmentRankDao, ProjectInvestmentRank> {
	@Autowired
	private ProjectInvestmentRankCalendarDao projectInvestmentRankCalendarDao;
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	private MarketingActivityInfoDao marketingActivityInfoDao;

	public ProjectInvestmentRank get(String id) {
		return super.get(id);
	}
	
	public List<ProjectInvestmentRank> findList(ProjectInvestmentRank projectInvestmentRank) {
		return super.findList(projectInvestmentRank);
	}
	
	public Page<ProjectInvestmentRank> findPage(Page<ProjectInvestmentRank> page, ProjectInvestmentRank projectInvestmentRank) {
		return super.findPage(page, projectInvestmentRank);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectInvestmentRank projectInvestmentRank) {
		super.save(projectInvestmentRank);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectInvestmentRank projectInvestmentRank) {
		super.delete(projectInvestmentRank);
	}
	
	/**
	 * 月投资排行统计查询
	 * @param year
	 * @param month
	 * @param week
	 * @param pageSearchBean
	 * @return
	 */
	public List<ProjectInvestmentRank> findListWithMonthStat(ProjectInvestmentRank projectInvestmentRank) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", projectInvestmentRank.getType());
		map.put("beginDataDt", projectInvestmentRank.getBeginDataDt());
		map.put("endDataDt", projectInvestmentRank.getEndDataDt());
		return dao.findListWithStat(map);
	}
	
	/**
	 * api投资排行
	 * @param rankResp
	 * @return
	 */
	public List<ProjectInvestmentRank> findListWithApiStat(InvestmentRankResp rankResp){
		return dao.findListWithStat(mapApiData(rankResp));
	}
	
	
	/**
	 * 
	 * @param rankResp
	 * @return
	 */
	public ProjectInvestmentRank getInvestmentRankApi(InvestmentRankResp rankResp,CustomerClientToken clientToken){
		ProjectInvestmentRank investmentRank = new ProjectInvestmentRank();
		Map<String,Object> map = mapApiData(rankResp);
		investmentRank.setAccountId(clientToken.getCustomerId());
		investmentRank.setType(String.valueOf(map.get("type")));
		investmentRank.setBeginDataDt((Date)map.get("beginDataDt"));
		investmentRank.setEndDataDt((Date)map.get("endDataDt"));
		ProjectInvestmentRank rank = dao.getInvestmentRank(investmentRank);
		if(rank == null){
			rank = new ProjectInvestmentRank();
			map.put("accountId", clientToken.getCustomerId());
			rank.setAmount(String.valueOf(projectInvestmentRecordDao.getSumAmountWithStat(map)));
			rank.setRank("您还未进入前10名哦");
		}
		return rank;
	}
	
	/**
	 * 根据type获取排行榜信息
	 * @param type
	 * @param accountId
	 * @return
	 */
	public ProjectInvestmentRank getInvestmentRankApiByType(String type, Long accountId){
		ProjectInvestmentRank rank = dao.getInvestmentRankByType(type, accountId);
		if(rank == null){
			rank = new ProjectInvestmentRank();
			MarketingActivityInfo marketingActivityInfo = marketingActivityInfoDao.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1008Handler.class.getSimpleName()));
			Date startDate = DateUtils.dateFormateDayOfTheBeginTime(marketingActivityInfo.getBeginDate());
			Date endDate = DateUtils.dateFormateDayOfTheLastTime(marketingActivityInfo.getEndDate());
			Double amount = projectInvestmentRecordDao.getCustomerAnnualizedAmountDuringTime(accountId, startDate, endDate);
			amount = amount !=null ? amount : 0.0;
			rank.setAmount(String.valueOf(amount));
			rank.setRank("您还未进入前10名哦");
		}
		return rank;
	}
	
	/**
	 * 根据type获取列表信息
	 * @param type
	 * @return
	 */
	public List<ProjectInvestmentRank> findInvestmentListByType(String type) {
		return dao.findInvestmentListByType(type);
	}
	
	/**
	 * map数据
	 * @param rankResp
	 * @return
	 */
	public Map<String,Object> mapApiData(InvestmentRankResp rankResp){
		Date beginDataDt = null;
		Date endDataDt = null;
		String year = rankResp.getYear() != null ? rankResp.getYear() : DateUtils.getYear();
		String month = rankResp.getMonth() != null ? rankResp.getMonth() : DateUtils.getMonth();
		String week = rankResp.getWeek() != null ? rankResp.getWeek() : String.valueOf(DateUtils.getWeekOfYear(new Date()));
		String type="";
		if("month".equals(rankResp.getType())){
			Date theDate = DateUtils.stringToDateShort(year + "-" + month + "-" + "01");
			beginDataDt = DateUtils.dateFormateDayOfTheBeginTime(DateUtils.getMonthBegin(theDate));
			endDataDt = DateUtils.dateFormateDayOfTheLastTime(DateUtils.getMonthEnd(theDate));
			type = ProjectConstant.INVESTMENT_RANK_TYPE_MONTH;
		}else if("week".equals(rankResp.getType())){
			ProjectInvestmentRankCalendar iRankCalendar = projectInvestmentRankCalendarDao.getInvestmentRankCalendar(year, month, week);
			if(iRankCalendar == null){
				iRankCalendar = new ProjectInvestmentRankCalendar();
			}
			beginDataDt = iRankCalendar.getBeginDt();
			endDataDt = iRankCalendar.getEndDt();
			type = ProjectConstant.INVESTMENT_RANK_TYPE_WEEK;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", type);
		map.put("beginDataDt", beginDataDt);
		map.put("endDataDt", endDataDt);
		return map;
	}
	
	/**
	 * 获取某个用户的投资信息
	 * @param projectInvestmentRank
	 * @return
	 */
	public ProjectInvestmentRank getInvestmentRank(ProjectInvestmentRank projectInvestmentRank,List<ProjectInvestmentRank> list){
		ProjectInvestmentRank rank = dao.getInvestmentRank(projectInvestmentRank);
		Map<String,Object> map = new HashMap<String,Object>();
		if(rank == null){
			rank = new ProjectInvestmentRank();
			map.put("accountId", projectInvestmentRank.getAccountId());
			map.put("beginDataDt", projectInvestmentRank.getBeginDataDt());
			map.put("endDataDt", projectInvestmentRank.getEndDataDt());
			rank.setAmount(String.valueOf(projectInvestmentRecordDao.getSumAmountWithStat(map)));
			String rankInfo = list.size()>0 ? "您还未进入前10名哦" : "0";
			rank.setRank(rankInfo);
		}
		return rank;
	}
	
	/**
	 * 日投资统计
	 * @param projectInvestmentRank
	 */
	@Transactional(readOnly = false)
	public void investmentDayStat(ProjectInvestmentRank investmentRank) {
		//先删除统计数据
		dao.deleteByTheDateAndType(investmentRank);
		//插入统计数据
		dao.investmentStat(investmentRank);
	}
	
	/**
	 * 周投资统计
	 * @param projectInvestmentRank
	 */
	@Transactional(readOnly = false)
	public void investmentWeekStat(ProjectInvestmentRank investmentRank) {
		//先删除统计数据
		dao.deleteByTheDateAndType(investmentRank);
		//插入统计数据
		dao.investmentStat(investmentRank);
	}
	
	/**
	 * 月投资统计
	 * @param investmentRank
	 */
	@Transactional(readOnly = false)
	public void investmentMonthStat(ProjectInvestmentRank investmentRank) {
		//先删除统计数据
		dao.deleteByTheDateAndType(investmentRank);
		//插入统计数据
		dao.investmentStat(investmentRank);
	}
	
	/**
	 * 年投资统计
	 * @param investmentRank
	 */
	@Transactional(readOnly = false)
	public void investmentYearStat(ProjectInvestmentRank investmentRank) {
		//先删除统计数据
		dao.deleteByTheDateAndType(investmentRank);
		//插入统计数据
		dao.investmentStat(investmentRank);
	}

	/**
	 * 更新一月份投资年化排行榜数据
	 */
	@Transactional(readOnly = false)
	public void updateJanuaryInvestmentRankData() {
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoDao.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1008Handler.class.getSimpleName()));
		dao.deleteByType(ProjectConstant.INVESTMENT_RANK_TYPE_JANUARY);
		dao.insertJanuaryInvestmentRankData(DateUtils.dateFormateDayOfTheBeginTime(marketingActivityInfo.getBeginDate()),
				DateUtils.dateFormateDayOfTheLastTime(marketingActivityInfo.getEndDate()));
	}

	/**
	 * 根据类型查询排行榜列表
	 * @param type
	 * @return
	 */
	public List<ProjectInvestmentRank> findListByType(String type) {
		return dao.findListByType(type);
	}
	
}