package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.entity.api.ProjectSearch;
import com.thinkgem.jeesite.modules.personal.service.PersonalTailorService;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ydt on 2016/5/17.
 */
public class ProjectInfoUtils {
	private static final String PROJECT_TYPE_CACHE = "projectTypeCache";
	private static final String PROJECT_TYPE_CACHE_KEY = "projectTypeCacheKey";
	private static ProjectBaseInfoService projectBaseInfoService = SpringContextHolder.getBean(ProjectBaseInfoService.class);
	private static ProjectTransferInfoService projectTransferInfoService = SpringContextHolder.getBean(ProjectTransferInfoService.class);
	private static PersonalTailorService personalTailorService = SpringContextHolder.getBean(PersonalTailorService.class);

	private static List<Map<String,Object>> projectTypeList = new ArrayList<>();
	static {
		//新花生
		Map<String,Object> newPeanut = new HashMap();
		ProjectSearch newPeanutSearch = new ProjectSearch();
		newPeanutSearch.setIsNewUser("0");
		newPeanutSearch.setDurationUnit(2);
		newPeanutSearch.setProjectDuration(1);
		newPeanut.put("projectName", "新花生");
		newPeanut.put("projectDesc", "新手专享");
		newPeanut.put("search", newPeanutSearch);
		//月花生
		Map<String,Object> monthPeanut = new HashMap();
		ProjectSearch monthPeanutSearch = new ProjectSearch();
		monthPeanutSearch.setIsNewUser("1");
		monthPeanutSearch.setDurationUnit(2);
		monthPeanutSearch.setProjectDuration(1);
		monthPeanut.put("projectName", "月花生");
		monthPeanut.put("projectDesc", "月月盈利");
		monthPeanut.put("search", monthPeanutSearch);
		//双月花生
		Map<String,Object> doubleMonthPeanut = new HashMap();
		ProjectSearch doubleMonthPeanutSearch = new ProjectSearch();
		doubleMonthPeanutSearch.setIsNewUser("1");
		doubleMonthPeanutSearch.setDurationUnit(2);
		doubleMonthPeanutSearch.setProjectDuration(2);
		doubleMonthPeanut.put("projectName", "双月花生");
		doubleMonthPeanut.put("projectDesc", "坐享收益");
		doubleMonthPeanut.put("search", doubleMonthPeanutSearch);
		//季花生
		Map<String,Object> seasonPeanut = new HashMap();
		ProjectSearch seasonPeanutSearch = new ProjectSearch();
		seasonPeanutSearch.setIsNewUser("1");
		seasonPeanutSearch.setDurationUnit(2);
		seasonPeanutSearch.setProjectDuration(3);
		seasonPeanut.put("projectName", "季花生");
		seasonPeanut.put("projectDesc", "灵活可控");
		seasonPeanut.put("search", seasonPeanutSearch);
		//双季花生
		Map<String,Object> doubleSeasonPeanut = new HashMap();
		ProjectSearch doubleSeasonPeanutSearch = new ProjectSearch();
		doubleSeasonPeanutSearch.setIsNewUser("1");
		doubleSeasonPeanutSearch.setDurationUnit(2);
		doubleSeasonPeanutSearch.setProjectDuration(6);
		doubleSeasonPeanut.put("projectName", "双季花生");
		doubleSeasonPeanut.put("projectDesc", "乐享回报");
		doubleSeasonPeanut.put("search", doubleSeasonPeanutSearch);
		//年花生
		Map<String,Object> yearPeanut = new HashMap();
		ProjectSearch yearPeanutSearch = new ProjectSearch();
		yearPeanutSearch.setIsNewUser("1");
		yearPeanutSearch.setDurationUnit(2);
		yearPeanutSearch.setProjectDuration(12);
		yearPeanut.put("projectName", "年花生");
		yearPeanut.put("projectDesc", "安心稳健");
		yearPeanut.put("search", yearPeanutSearch);

		projectTypeList.add(newPeanut);
		projectTypeList.add(monthPeanut);
		projectTypeList.add(doubleMonthPeanut);
		projectTypeList.add(seasonPeanut);
		projectTypeList.add(doubleSeasonPeanut);
		projectTypeList.add(yearPeanut);
	}

	public static Map<String,Object> getProjectTypeInfo() {
		if(CacheUtils.get(PROJECT_TYPE_CACHE, PROJECT_TYPE_CACHE_KEY) == null) {
			refreshProjectTypeInfo();
		}
		return (Map<String,Object>)CacheUtils.get(PROJECT_TYPE_CACHE, PROJECT_TYPE_CACHE_KEY);
	}

	public static void refreshProjectTypeInfo() {
		Map<String,Object> projectTypeInfo = new HashMap<>();
		int regularCount = 0;
		for(Map<String,Object> projectType : projectTypeList) {
			int count = projectBaseInfoService.getCanInvestmentProjectCount((ProjectSearch) projectType.get("search"));
			projectType.put("projectCount", count);
			regularCount += count;
		}
		int assignCount = projectTransferInfoService.getCountByStatus("0");
		regularCount += assignCount;
		projectTypeInfo.put("regularCount", regularCount);
		projectTypeInfo.put("assignCount", assignCount);
		projectTypeInfo.put("privateCount", personalTailorService.getCanInvestmentCount());
		projectTypeInfo.put("resultList", projectTypeList);
		CacheUtils.put(PROJECT_TYPE_CACHE, PROJECT_TYPE_CACHE_KEY, projectTypeInfo);
	}
}
