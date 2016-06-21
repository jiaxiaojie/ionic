package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.to.ProjectBaseInfoResp;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.entity.api.ProjectSearch;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 债券转让Controller
 *
 * @author huangyuchen
 * @version 2016-5-23
 */
@Controller("myPeanut")
@RequestMapping(value="${frontPath}/api/credit", method = RequestMethod.POST)
public class CreditController extends APIBaseController {
    @Autowired
    private ProjectTransferInfoService projectTransferInfoService;
    @Autowired
    private ProjectBaseInfoService projectBaseInfoService;
    @Autowired
    private ProjectExecuteSnapshotService projectExecuteSnapshotService;
    @Autowired
    private CustomerBalanceService customerBalanceService;
    /**
     * 债权转让详情
     * @param response
     * @param client
     * @param transferProjectId
     * @return
     */
    @SuppressWarnings("details")
    @RequestMapping(value = "details", method = RequestMethod.POST)
    public String details(HttpServletResponse response, HttpServletRequest request, String client, String token, String transferProjectId) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api details start...");
        HashMap<String, Object> map = new HashMap<String, Object>();
        ProjectTransferInfo transferInfo = new ProjectTransferInfo();
        transferInfo.setTransferProjectId(NumberUtils.toLong(transferProjectId, 0L));
        transferInfo = projectTransferInfoService.get(transferInfo);
        String projectId = String.valueOf(transferInfo.getProjectId());
        ProjectBaseInfo pInfo = projectBaseInfoService.get(projectId);
        ProjectExecuteSnapshot pSnapshot = projectExecuteSnapshotService.getTransferSnapshot(NumberUtils.toLong(projectId, 0L), transferInfo.getTransferProjectId());
        pInfo.setPes(pSnapshot);
        if(pInfo != null){
            //项目详情数据
            if(token!=null) {
                CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
                Long accountId = clientToken.getCustomerId();
                CustomerBalance customerBalance = customerBalanceService.get(accountId + "");
                this.detailCreate(request, customerBalance, transferInfo, pInfo, pSnapshot, map);
            }else{
                this.detailCreate(request, null, transferInfo, pInfo, pSnapshot, map);
            }
        }else{
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
        }
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api detail end...");
        logger.info("api details total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }


    /**
     * 债权转让分页列表
     * @param response
     * @param client
     * @param status
     * @param duration
     * @param rate
     * @param repaymentMode
     * @param type
     * @param flag
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "pageList", method = RequestMethod.POST)
    public String pageList(HttpServletResponse response, String client, String status, String duration, String rate, String repaymentMode, ProjectSearch search,
                           String type, Integer flag,@RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false, defaultValue = "1") Integer pageNumber) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api pageList start...");
       int count = 0;
        Map<String,Object> dataMap = new HashMap<String,Object>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<ProjectTransferInfo> List = projectTransferInfoService.findCreditPageList(status,duration,rate,repaymentMode,type,pageNumber, pageSize);
        //项目分页列表数据
        List<ProjectBaseInfoResp> pRespList =   this.detailCreatePageList(List);
        if("1".equals(String.valueOf(flag))) {
            count = projectTransferInfoService.getCreditCount(status,duration,rate,repaymentMode,type);
        }
        dataMap.put("count",count);
        dataMap.put("resultList", pRespList);
        ApiUtil.mapRespData(map, dataMap, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api pageList end...");
        logger.info("api pageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }


    /**
     * 债权转让-统计信息
     * @param response
     * @param client
     * @return
     */
    @RequestMapping(value = "getStatisticInfo", method = RequestMethod.POST)
    public String getStatisticInfo(HttpServletResponse response, String client) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api getStatisticInfo start...");
        HashMap<String, Object> map = new HashMap<String, Object>();
        ProjectBaseInfoResp pResp = new ProjectBaseInfoResp();
        //权债项目数量
      int projectCount=projectTransferInfoService.getProjectCount(null);
        pResp.setProjectCount(projectCount);
        //转让中数量
      int transferCount=projectTransferInfoService.getTransferingProjectCount(null);
        pResp.setTransferCount(transferCount);
        //转让完成数量
      int transferDoneCount=projectTransferInfoService.getTransferEndProjectCount(null);
        pResp.setTransferDoneCount(transferDoneCount);
        ApiUtil.mapRespData(map, pResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api getStatisticInfo end...");
        logger.info("api getStatisticInfo total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }


}
