/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.extend.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.client.ClientProperty;
import com.thinkgem.jeesite.modules.api.to.CarouselResp;
import com.thinkgem.jeesite.modules.cms.service.ActivityService;
import com.thinkgem.jeesite.modules.entity.Activity;

/**
 * 登录Controller
 * 
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping("${frontPath}/extend/more")
public class MoreExtendController extends BaseController {
	@Autowired
	private ActivityService activityService;
	/**
	 * 活动列表
	 * @param response
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "activityPageList", method = RequestMethod.POST)
	public String activityPageList(HttpServletResponse response,
			HttpServletRequest request, String client, Integer pageNumber,
			Integer pageSize) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime)
				+ "】api activityPageList start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Object> dataList = new ArrayList<Object>();
		// 解析client
		ClientProperty cProperty = ApiUtil.getClient(client);
		Date theDate = new Date();
		List<Activity> list = activityService.getActivityListPage(
				ApiUtil.getOperaChannel(cProperty.getType()), pageNumber,
				pageSize);
		for (Activity activity : list) {
			CarouselResp data = new CarouselResp();
			data.setImageUrl(ApiUtil.imageUrlConver(activity.getActivityCover()));
			data.setTitle(activity.getTitle());
			data.setType(1);	//活动
			data.setTarget(activity.getActivityJoin());
			String activity_period = DateUtils.formatDate(
					activity.getStartDt(), "yyyy-MM-dd")
					+ "至"
					+ DateUtils.formatDate(activity.getEndDt(), "yyyy-MM-dd");
			data.setActivity_period(activity_period);

			String status = "";
			String statusName = "";
			if (theDate.compareTo(activity.getStartDt()) >= 0
					&& theDate.compareTo(activity.getEndDt()) <= 0) {
				status = "0";
				statusName = "进行中";
			} else if (theDate.compareTo(activity.getEndDt()) > 0) {
				status = "1";
				statusName = "已结束";
			}
			data.setStatus(status);
			data.setStatusName(statusName);
			dataList.add(data);
		}
		map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_SUCCESS);
		map.put(ApiConstant.API_STATUS_TEXT, "ok");
		map.put(ApiConstant.API_RESP_DATA, dataList);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime)
				+ "】api activityPageList end...");
		logger.info("api activityPageList total time consuming：【"
				+ (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	
	
	
}
