/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.to.InvestmentRecordsResp;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;

import java.util.ArrayList;
import java.util.List;

/**
 * 投资排行 缓存工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class InvestRankCacheUtils {


	public static final String CACHE = "investRankCache";
	public static final String CACHE_KEY = "investRankCacheKey";
	private static ProjectInvestmentRecordService projectInvestmentRecordService = SpringContextHolder.getBean(ProjectInvestmentRecordService.class);
	

	
	/**
	 * 添加缓存
	 * @param investList
	 */
	public static void add(List<InvestmentRecordsResp> investList) {
		if(investList != null) {
			CacheUtils.put(CACHE, CACHE_KEY, investList);
		}
	}

	/**
	 * 清除缓存
	 */
	public static void clearCache(){
		CacheUtils.remove(CACHE,CACHE_KEY);
	}


	/**
	 * 刷新缓存
	 */
	public static void refreshInvestmentRankCache(){
		List<ProjectInvestmentRecord> list = projectInvestmentRecordService.getTodayRankingList(5);
		List<InvestmentRecordsResp> dataList = new ArrayList<InvestmentRecordsResp>();
		for(ProjectInvestmentRecord record: list){
			InvestmentRecordsResp recordsResp = new InvestmentRecordsResp();
			recordsResp.setAccountId(record.getInvestmentUserId());
			recordsResp.setAmount(record.getAmount());
			recordsResp.setMobile(StringUtils.vagueMobile(record.getCb().getMobile()));
			recordsResp.setDescription("总计投资");
			dataList.add(recordsResp);
		}
		CacheUtils.put(CACHE, CACHE_KEY, dataList);

	}

	/**
	 * 获取缓存List
	 * @return
	 */

	public static List<InvestmentRecordsResp> getInvestmentRankList() {
		List<InvestmentRecordsResp> list= (List<InvestmentRecordsResp>)CacheUtils.get(CACHE, CACHE_KEY);
		if(list == null || (list !=null && list.size() ==0)) {
			refreshInvestmentRankCache();
			list= (List<InvestmentRecordsResp>)CacheUtils.get(CACHE, CACHE_KEY);
		}
		return list;
	}

}
