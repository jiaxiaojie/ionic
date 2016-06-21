package com.thinkgem.jeesite.modules.api.web;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.base.BusinessConstant;
import com.thinkgem.jeesite.modules.api.po.PersonalReservate;
import com.thinkgem.jeesite.modules.api.po.PersonalTailorReq;
import com.thinkgem.jeesite.modules.api.to.PersonalTailorResp;
import com.thinkgem.jeesite.modules.feedback.service.CustomerLeaveMessageService;
import com.thinkgem.jeesite.modules.personal.service.PersonalTailorService;

/**
 * 私人订制Controller
 *<p/>
 * @createDate 2016-5-13
 */
@Controller("apiPrivateController")
@RequestMapping(value="${frontPath}/api/private", method = RequestMethod.POST)
public class PrivateController extends APIBaseController {

    @Autowired
    private PersonalTailorService personalTailorService;
    @Autowired
    private CustomerLeaveMessageService customerLeaveMessageService;
    /**
     * 私人订制-分页列表
     * @param response
     * @param personalTailor
     */
    @RequestMapping(value = "getPageList")
    public void getPageList(HttpServletResponse response, PersonalTailorReq personalTailor){
        Date startTime = new Date();
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            personalTailorService.queryPersonalList(personalTailor,map,currentRequest());
        } catch (Exception e) {
            logger.error("私人订制-分页列表",e);
            ApiUtil.mapRespData(map,null,"服务异常,请稍后...", false);
        }
        printLog("私人订制-分页列表", personalTailor, startTime, response, map);
    }

    /**
     * 私人订制-项目详情
     * @param response
     * @param personalTailor
     */
    @RequestMapping(value = "getPrivateDetails", method = RequestMethod.POST)
    public void getPrivateDetails(HttpServletResponse response, PersonalTailorReq personalTailor){
        Date startTime = new Date();
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            PersonalTailorResp personalTailorResp = personalTailorService.findByPersonalId(personalTailor);
           
            if(personalTailorResp==null){
                ApiUtil.mapRespData(map,null,"非法访问", false);
            }else{
            	personalTailorResp.setDescPic(ApiUtil.prefixSrc(personalTailorResp.getDescPic()));
                ApiUtil.mapRespData(map,personalTailorResp,"正常访问", true);
            }
        } catch (Exception e) {
            logger.error("私人订制-项目详情",e);
            ApiUtil.mapRespData(map,null,"服务异常,请稍后...", false);
        }
        printLog("私人订制-项目详情", personalTailor, startTime, response, map);
    }

    /**
     * 私人订制-立即预约
     * @param response
     * @param personalReservate
     */
    @RequestMapping(value = "reservation", method = RequestMethod.POST)
    public void reservation(HttpServletResponse response, PersonalReservate personalReservate){
        Date startTime = new Date();
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
           String result= customerLeaveMessageService.saveReservationInfor(personalReservate,currentRequest());
            if(BusinessConstant.SUCCESS.equals(result)){
                ApiUtil.mapRespData(map,null,"正常", true);
            }else{
                ApiUtil.mapRespData(map,null,result, false);
            }
        } catch (Exception e) {
            logger.error("私人订制-立即预约",e);
            ApiUtil.mapRespData(map,null,"服务异常,请稍后...", false);
        }
        printLog("私人订制-立即预约", personalReservate, startTime, response, map);
    }
}
