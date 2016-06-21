/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.*;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.EuropeanCupTopScorer;
import com.thinkgem.jeesite.modules.marketing.dao.EuropeanCupTopScorerDao;

/**
 * 欧洲杯射手榜Service
 * @author lzb
 * @version 2016-06-13
 */
@Service
@Transactional(readOnly = true)
public class EuropeanCupTopScorerService extends CrudService<EuropeanCupTopScorerDao, EuropeanCupTopScorer> {

	public EuropeanCupTopScorer get(String id) {
		return super.get(id);
	}
	
	public List<EuropeanCupTopScorer> findList(EuropeanCupTopScorer europeanCupTopScorer) {
		return super.findList(europeanCupTopScorer);
	}
	
	public Page<EuropeanCupTopScorer> findPage(Page<EuropeanCupTopScorer> page, EuropeanCupTopScorer europeanCupTopScorer) {
		return super.findPage(page, europeanCupTopScorer);
	}
	
	@Transactional(readOnly = false)
	public void save(EuropeanCupTopScorer europeanCupTopScorer) {
		super.save(europeanCupTopScorer);
	}
	
	@Transactional(readOnly = false)
	public void delete(EuropeanCupTopScorer europeanCupTopScorer) {
		super.delete(europeanCupTopScorer);
	}


	/**
	 * 初始化射手榜数据
	 * @param accountId
	 * @param activityId
     */
	public void initEuropeanCupTopScorerData(Long accountId, Long activityId){
		EuropeanCupTopScorer topScorer = dao.getByAccountIdAndActivityId(accountId, activityId);
		if(topScorer == null){
			EuropeanCupTopScorer cupTopScorer= new EuropeanCupTopScorer();
			cupTopScorer.setAccountId(accountId);
			cupTopScorer.setActivityId(activityId);
			cupTopScorer.setTotalGoals(0);
			cupTopScorer.setUsedScratchTimes(0);
			cupTopScorer.setOpDt(new Date());
		}
	}

	/**
	 * 更新用户进球总数
	 * @param accountId
	 * @param activityId
	 * @param totalGoals
     */
	public void updateTotalGoalsByAccountIdAndActivityId(Long accountId, Long activityId,int totalGoals){
		dao.updateTotalGoalsByAccountIdAndActivityId(accountId, activityId,totalGoals);
	}

	/**
	 * 更新用户刮奖次数
	 * @param accountId
	 * @param activityId
	 * @param usedScratchTimes
     */
	public void updateUsedScratchTimesByAccountIdAndActivityId(Long accountId, Long activityId,int usedScratchTimes){
		dao.updateUsedScratchTimesByAccountIdAndActivityId(accountId, activityId,usedScratchTimes);
	}

	/**
	 * 获取前10名射手数据
	 * @param acticityId
	 * @param startDate
	 * @param endDate
     * @return
     */
	public List<Map<String,Object>> getTop10Scorer(Long acticityId, Date startDate, Date endDate){
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		List<EuropeanCupTopScorer> list = dao.getTop10Scorer(acticityId, startDate, endDate);
		for(int i=0;i< list.size();i++){
			EuropeanCupTopScorer topScorer = list.get(i);
            Map<String,Object> mapInfo = new HashMap<String,Object>();
			mapInfo.put("mobile", StringUtils.vagueMobile(topScorer.getMobile()));
			mapInfo.put("investmentAmount",topScorer.getTotalGoals());
			mapInfo.put("prize", MarketConstant.ACTIVITY_1020_EUROPEAN_CUP_TOP10SCORER_REWARD_AMOUNT[i]+"元现金");
			mapList.add(mapInfo);
		}
		return mapList;
	}

	/**
	 * 获得射手榜数据，如果数据库没有则初始化
	 * @param accountId
	 * @param activityId
	 */
	@Transactional(readOnly = false)
	public EuropeanCupTopScorer findEuropeanCupTopScorerData(Long accountId, Long activityId){
		EuropeanCupTopScorer topScorer = dao.getByAccountIdAndActivityId(accountId, activityId);
		if(topScorer == null){
			EuropeanCupTopScorer cupTopScorer= new EuropeanCupTopScorer();
			cupTopScorer.setAccountId(accountId);
			cupTopScorer.setActivityId(activityId);
			cupTopScorer.setTotalGoals(0);
			cupTopScorer.setUsedScratchTimes(0);
			cupTopScorer.setOpDt(new Date());
			this.save(cupTopScorer);
			topScorer = dao.getByAccountIdAndActivityId(accountId, activityId);
		}
		return topScorer;
	}
}