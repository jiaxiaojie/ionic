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
import com.thinkgem.jeesite.modules.carousel.service.CarouselInfoService;
import com.thinkgem.jeesite.modules.entity.CarouselInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.hsbank.util.type.NumberUtil;

/**
 * 登录Controller
 * 
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping("${frontPath}/extend/event")
public class EventExtendController extends BaseController {
	@Autowired
	private CarouselInfoService carouselInfoService;

	/**
	 * 首页轮播列表
	 * @param response
	 * @param request
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "carousel", method = RequestMethod.POST)
	public String carousel(HttpServletResponse response, HttpServletRequest request, String client) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】extend carousel start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Object> dataList = new ArrayList<Object>();
		//解析client
		ClientProperty cProperty = ApiUtil.getClient(client);
		String theDate = DateUtils.getDateTime();
		String term = ApiUtil.getOperaChannel(cProperty.getType());
		String isNewWebsite = ProjectConstant.DICT_DEFAULT_VALUE;
		//接口web端调用时，则为新网站轮播
		if(ProjectConstant.OP_TERM_DICT_PC.equals(term)){
			isNewWebsite = "1";
		}
		List<CarouselInfo> list = carouselInfoService.getCarouselListByStatusAndTerm(ProjectConstant.CAROUSEL_INFO_STATUS_PASS, term,isNewWebsite, theDate);
		for(CarouselInfo cInfo: list){
            CarouselResp data = new CarouselResp();
            data.setImageUrl(ApiUtil.imageUrlConver(cInfo.getPictureBig()));
            data.setTitle(cInfo.getTitle());
            data.setType(NumberUtil.toInt(cInfo.getTypeCode(),0));
            data.setTarget(cInfo.getTarget());
            dataList.add(data);
		}
		map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_SUCCESS);
		map.put(ApiConstant.API_STATUS_TEXT, "ok");
		map.put(ApiConstant.API_RESP_DATA, dataList);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】extend carousel end...");
		logger.info("api carousel total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	
	
	

}
