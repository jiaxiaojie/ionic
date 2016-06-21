package com.thinkgem.jeesite.modules.project.service.task;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.customer.service.CustomerClientTokenService;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * api token缓存维护
 *
 * @author lizibo
 * @since 2015/10/15
 */
@Component("autoClientTokenTask")
public class AutoClientTokenTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CustomerClientTokenService customerClientTokenService;
	
	
	/**
	 * 每5分钟执行一次
	 */
	public void job() {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】autoClientTokenTask start...");
		//失效时间
		Date theDate = DateUtils.addMinutes(new Date(), ApiConstant.API_MOBILE_CACHE_TOKEN_INVALID_TIME);
		List<CustomerClientToken> customerClientTokenList = CustomerClientUtils.getCatchList();
		for(CustomerClientToken clientToken : customerClientTokenList){
			String termType = clientToken.getTermType();
			if(ProjectConstant.OP_TERM_DICT_PC.equals(termType)){
				theDate = DateUtils.addMinutes(new Date(), ApiConstant.API_WEBSITE_CACHE_TOKEN_INVALID_TIME);
			}
			//失效时间大于最后更新时间清除缓存
			if(theDate.compareTo(clientToken.getLastDt()) > 0){
				if(CustomerClientUtils.checkToken(clientToken.getToken())){
					CustomerClientUtils.clearCache(clientToken);
					customerClientTokenService.update(clientToken);
				}
			}else{
				if(!CustomerClientUtils.checkToken(clientToken.getToken())){
					CustomerClientUtils.addCache(clientToken);
				}
				customerClientTokenService.update(clientToken);
			}
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】autoClientTokenTask end...");
		logger.info("autoClientTokenTask total time consuming：【" + (startTime.getTime() - endTime.getTime()) / 1000 + "s】");
	}
}
