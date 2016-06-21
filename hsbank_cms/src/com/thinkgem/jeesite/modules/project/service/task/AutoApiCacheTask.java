package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.ApiCacheObject;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.sys.utils.ApiCacheUtils;

/**
 * api 访问易宝缓存数据维护
 *
 * @author lizibo
 * @since 2015/10/15
 */
@Component("autoApiCacheTask")
public class AutoApiCacheTask {
	Logger logger = Logger.getLogger(this.getClass());
	
	
	
	/**
	 * 每5分钟执行一次
	 */
	public void job() {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】autoApiCacheTask start...");
		//失效时间
		Date theDate = org.apache.commons.lang3.time.DateUtils.addMinutes(new Date(), ApiConstant.API_CACHE_YEEPAY_INVALID_TIME);
		List<ApiCacheObject> apiCacheObjectList = ApiCacheUtils.getCatchList();
		for(ApiCacheObject aObject : apiCacheObjectList){
			//失效时间大于最后更新时间清除缓存
			if(theDate.compareTo(aObject.getLastDt()) > 0){
				if(ApiCacheUtils.checkUUID(aObject.getUuid())){
					ApiCacheUtils.clearCache(aObject);
				}
			}
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】autoApiCacheTask end...");
		logger.info("autoApiCacheTask total time consuming：【" + (startTime.getTime() - endTime.getTime()) / 1000 + "s】");
	}
}
