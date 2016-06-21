package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.MarketingUtils;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivityHandler;

/**
 * @author ydt 维护cache中的有效活动列表
 */
@Component
public class FixEffectiveMarketingActivityList implements ApplicationListener<ContextRefreshedEvent> {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private MarketActivityHandler marketActivityHandler;

	/**
	 * 每5分执行一次
	 */
	public void job() {
		Date startDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTime) + "】fixEffectiveMarketingActivityList start...");
		List<MarketingActivityInfo> marketingActivityInfoList = marketActivityHandler.getEffectiveActivityList();
		if(marketingActivityInfoList != null) {
			for(MarketingActivityInfo marketingActivityInfo : marketingActivityInfoList) {
				if(MarketingUtils.get(marketingActivityInfo.getActicityId()) == null) {
					MarketingUtils.add(marketingActivityInfo);
				}
			}
		}
		MarketingUtils.check();
		String effectiveActivityListIds = "";
		List<MarketingActivityInfo> cacheList = MarketingUtils.getList();
		for(MarketingActivityInfo marketingActivityInfo : cacheList) {
			effectiveActivityListIds += marketingActivityInfo.getActicityId() + ";";
		}
		logger.info("effectiveActivityList sql size:" + marketingActivityInfoList.size() + ", cache size:" + cacheList.size() + ", ids:" + effectiveActivityListIds);
		Date endDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTiem) + "】fixEffectiveMarketingActivityList end...");
		logger.info("fixEffectiveMarketingActivityList total time consuming：【" + (endDateTiem.getTime() - startDateTime.getTime()) / 1000 + "s】");
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
			//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			job();
		}
	}
}
