package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TokenUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.annotation.Authenticate;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.frame.spring.validate.login.Token;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.API;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APINode;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APIObjectNode;
import com.thinkgem.jeesite.modules.api.frame.generator.APIGenerator;
import com.thinkgem.jeesite.modules.api.frame.generator.convert.HSAPIConvertAction;
import com.thinkgem.jeesite.modules.api.frame.format.annotation.Formater;
import com.thinkgem.jeesite.modules.api.po.DailyProfitReq;
import com.thinkgem.jeesite.modules.api.to.MyCurProjectInfoResp;
import com.thinkgem.jeesite.modules.api.to.MyCurStatisticsResp;
import com.thinkgem.jeesite.modules.api.to.PageResponse;
import com.thinkgem.jeesite.modules.api.web.param.RegularParams;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountInterestChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountSummaryService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectHoldInfoService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentSplitRecordService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的投资Controller
 *<p/>
 * @createDate 2016-5-13
 */
@Controller("apiInvestmentController")
@RequestMapping(value="${frontPath}/api/myInvestment")
public class InvestmentController extends APIBaseController {
    @Autowired
    private CurrentProjectHoldInfoService currentProjectHoldInfoService;
    @Autowired
    private CurrentAccountInterestChangeHisService currentAccountInterestChangeHisService;
    @Autowired
    private CurrentAccountSummaryService currentAccountSummaryService;
    @Autowired
    private ProjectBaseInfoService projectBaseInfoService;
    @Autowired
    private ProjectRepaymentSplitRecordService projectRepaymentSplitRecordService;
    @Autowired
    private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;


    /**
     * 我的投资-活期理财(每日利息分页列表)
     * @param response
     * @param client
     * @param projectId
     * @param type
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Authenticate
    @ResponseBody
    @RequestMapping(value = "currentDailyPageList")
    public Map<String,Object> currentDailyPageList(HttpServletResponse response,String client,DailyProfitReq dailyProfitReq,
                                              @RequestParam(required=false,defaultValue="10")Integer pageSize, @RequestParam(required=false,defaultValue="1")Integer pageNumber) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api currentDailyPageList start...");
        HashMap<String, Object> map = new HashMap<String, Object>();
        //入参数据校验
        List<String> messages  = ApiUtil.validateBean(dailyProfitReq);
        if(messages != null){
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
        }else{
            //获得当前用户
            CustomerClientToken clientToken = TokenUtils.getCurrentCustomerClientToken();
            PageResponse pageResponse = currentAccountPrincipalChangeHisService.findMyChangeHisPageList(clientToken.getCustomerId(),dailyProfitReq.getProjectId(),dailyProfitReq.getType(), new Page(pageNumber,pageSize,true));
            ApiUtil.mapRespData(map, pageResponse, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        }
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api currentDailyPageList end...");
        logger.info("api currentDailyPageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return map;
    }


    /**
     * 我的投资-活期理财
     * @param response
     * @param client
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Authenticate
    @ResponseBody
    @RequestMapping(value = "currentPageList")
    public Map<String,Object> currentPageList(HttpServletResponse response,String client,
                                                @RequestParam(required=false,defaultValue="10")Integer pageSize, @RequestParam(required=false,defaultValue="1")Integer pageNumber) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api currentPageList start...");
        //获得当前用户
        CustomerClientToken clientToken = TokenUtils.getCurrentCustomerClientToken();
        PageResponse pageResponse = currentProjectHoldInfoService.findMyPageList(clientToken.getCustomerId(),new Page<CurrentProjectHoldInfo>(pageNumber, pageSize,true));
        HashMap<String, Object> map = new HashMap<String, Object>();
        ApiUtil.mapRespData(map, pageResponse, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api currentPageList end...");
        logger.info("api currentPageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return map;
    }


    /**
     * 我的投资-活期理财(活期项目信息)
     * @param client
     * @param projectId
     * @return
     */
    @Authenticate
    @ResponseBody
    @RequestMapping(value = "currentProjectInfo")
    public Map<String,Object> currentProjectInfo(String client,String projectId){
        HashMap<String, Object> map = new HashMap<String, Object>();
        if(StringUtils.isBlank(projectId)){
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "项目id不能为空", false);
            return map;
        }
        //获得当前用户
        CustomerClientToken clientToken = TokenUtils.getCurrentCustomerClientToken();
        Long accountId = clientToken.getCustomerId();
        //持有信息
        CurrentProjectHoldInfo holdInfo = currentProjectHoldInfoService.getMyCurrentProjectHoldInfo(accountId, projectId, "");
        if(holdInfo != null){
            String changeType = CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST;
            //昨日收益
            Double yesterdayProfit = currentAccountInterestChangeHisService.getYesterdayProfitByAccountIdAndProjectId(accountId, projectId, changeType);
            yesterdayProfit = NumberUtils.formatWithScale(yesterdayProfit,4);
            //累计收益
            Double sumProfit = currentAccountInterestChangeHisService.getSumProfitByAccountIdAndProjectId(accountId, projectId, changeType);
            sumProfit = NumberUtils.formatWithScale(sumProfit,4);
            //每万元日收益
            Double rate = holdInfo.getCurrentProjectInfo()!=null ? holdInfo.getCurrentProjectInfo().getRate() : 0;
            double dayProfit = NumberUtils.formatNotRoundWithScale(NumberUtils.div(
                    NumberUtils.mul(rate, CurrentProjectConstant.ONE_DAY_TEN_THOUSAND_PROFIT),
                    new Double(CurrentProjectConstant.DAYS_IN_YEAR)), 2);

            mycurrentProjectInfo(holdInfo, yesterdayProfit, sumProfit, dayProfit,map);
        }else{
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "数据不存在", false);
        }


        return map;
    }

    /**
     * 我的活期项目信息
     * @param holdInfo
     * @param yesterdayProfit
     * @param sumProfit
     * @param dayProfit
     * @param map
     */
   public void mycurrentProjectInfo(CurrentProjectHoldInfo holdInfo,Double yesterdayProfit,Double sumProfit, double dayProfit, HashMap<String, Object> map){
       MyCurProjectInfoResp myCurResp = new MyCurProjectInfoResp();
       myCurResp.setProjectId(holdInfo.getProjectId());
       String projectName = holdInfo.getCurrentProjectInfo() != null ? holdInfo.getCurrentProjectInfo().getName() : "";
       Double annualizedRate = holdInfo.getCurrentProjectInfo() != null ? holdInfo.getCurrentProjectInfo().getRate() : 0.0;
       myCurResp.setProjectName(projectName);
       myCurResp.setStatus(Integer.parseInt(holdInfo.getCurrentProjectInfo().getStatus()));
       String statusName = DictUtils.getDictLabel(holdInfo.getCurrentProjectInfo().getStatus(), "current_project_status", "");
       myCurResp.setStatusName(statusName);
       myCurResp.setAnnualizedRate(annualizedRate);
       myCurResp.setInterestOf10000Yuan(dayProfit);
       myCurResp.setYesterdayProfit(yesterdayProfit);
       myCurResp.setStartDate("T+0（工作日）");
       myCurResp.setAmount(holdInfo.getPrincipal());
       myCurResp.setReceivedProfit(sumProfit);
       myCurResp.setInterest(holdInfo.getInterest());
       ApiUtil.mapRespData(map, myCurResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
   }


    /**
     * 我的投资-活期理财(统计信息)
     * @param client
     * @param projectId
     * @return
     */
    @Authenticate
    @ResponseBody
    @RequestMapping(value = "currentStatistic")
    public Map<String,Object> currentStatistic(String client){
        //获得当前用户
        CustomerClientToken clientToken = TokenUtils.getCurrentCustomerClientToken();
        long accountId = clientToken.getCustomerId();
       //账户总览信息
        CurrentAccountSummary summary = currentAccountSummaryService.getByAccountId(accountId);
        Double yesterdayProfit = currentAccountInterestChangeHisService.getYesterdayProfitByAccountId(accountId, CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST);
        yesterdayProfit = NumberUtils.formatWithScale(yesterdayProfit,4);
        MyCurStatisticsResp statisticsResp = new MyCurStatisticsResp();
        statisticsResp.setCurrentPrincipal(summary.getCurrentPrincipal());
        statisticsResp.setYesterdayProfit(yesterdayProfit);
        statisticsResp.setTotalGetInterest(summary.getTotalGetInterest());
        HashMap<String, Object> map = new HashMap<String, Object>();
        ApiUtil.mapRespData(map, statisticsResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        return map;
    }


    /**
     * 我的投资-定期理财
     * @param regularParams
     * @author 万端瑞
     * @return
     */
    @Formater(path = "${frontPath}/api/myInvestment/regular")
    @ResponseBody
    @RequestMapping(value = "regular")
    public Map<String,Object> regular(Token token, RegularParams regularParams){

        //构造查询参数
        Long type = (regularParams.getType() != null && "0".equals(regularParams.getType())?null:Long.parseLong(regularParams.getType()));

        //执行查询
        Page<ProjectBaseInfo> projectBaseInfoPage = projectBaseInfoService.findProjectInvestmentListPage(
                new Page<ProjectBaseInfo>(regularParams.getPageNumber(), regularParams.getPageSize()), token.getAccountId(), type, regularParams.getStatus());


        //ProjectBaseInfo API数据封装策略/
        HSAPIConvertAction<ProjectBaseInfo> projectBaseInfoConvertAction = new HSAPIConvertAction<ProjectBaseInfo>(){

            @Override
            public Map<String, Object> convert(ProjectBaseInfo projectBaseInfo) {
                APIObjectNode dataNode = new APIObjectNode(projectBaseInfo);

                //查询投资剩余天数
                Long accountId = token.getAccountId();
                Long paiId = org.apache.commons.lang3.math.NumberUtils.toLong(projectBaseInfo.getPir().getId(), 0L);
                ProjectRepaymentSplitRecord pSplitRecord = projectRepaymentSplitRecordService.getRepaymentInfoByProjectAndccountId(projectBaseInfo.getProjectId(),accountId, paiId);
                Long remainingDays = (long) DateUtils.getDistanceOfTwoDate(DateUtils.dateFormate(new Date()), pSplitRecord.getLastRepaymentDt());
                dataNode.put("remainingDays",remainingDays<0?0:remainingDays);
                // 增加projectType、projectTypeName，2016-05-30 liuguoqing
                //dataNode.put("projectType", String.valueOf(projectBaseInfo.getProjectTypeCode()));
                //dataNode.put("projectTypeName", DictUtils.getDictLabel(String.valueOf(projectBaseInfo.getProjectTypeCode()), "project_type_dict", ""));

                return dataNode;
            }
        };

        //执行ProjectBaseInfo API数据封装
        APINode resultList = APIGenerator.toAPINodeWithCollection(projectBaseInfoPage.getList(),projectBaseInfoConvertAction);

        //组装api
        API api = APIGenerator.createAPIBuilder().putDataChildNode("count",projectBaseInfoPage.getCount()).putDataChildNode("resultList",resultList).build();

        return api;
    }
}
