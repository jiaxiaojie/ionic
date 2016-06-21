package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.to.CurrentResp;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountSummaryService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

/**
 * 我的投资Controller
 *<p/>
 * @createDate 2016-5-13
 */
@Controller("apiWebCurrentController")
@RequestMapping(value="${frontPath}/api/current", method = RequestMethod.POST)
public class CurrentController extends APIBaseController {
    @Autowired
    private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;
    @Autowired
    private CurrentProjectInfoService currentProjectInfoService;
    @Autowired
    private CurrentProjectExecuteSnapshotService currentProjectExecuteSnapshotService;
    @Autowired
    private CustomerBalanceService customerBalanceService;
    @Autowired
    private CurrentAccountSummaryService currentAccountSummaryService;

    /**
     * 活期详情
     * @param response
     * @param client
     * @param token
     * @author huangyuchen
     * @return
     *
     */
    @ResponseBody
    @RequestMapping(value = "currentDetails")
    public String currentDetails(HttpServletResponse response, String client, String token){
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api currentDetails start...");
        HashMap<String, Object> map = new HashMap<String, Object>();
        //查询最新的活期项目
        CurrentProjectInfo currentProjectInfo = currentProjectInfoService.getFirstTenderingCurrentProjectInfo();
        //查询活期项目执行快照
        CurrentProjectExecuteSnapshot snapshot=currentProjectExecuteSnapshotService.getByProjectId(currentProjectInfo.getId());
        if(snapshot == null){
            snapshot = new CurrentProjectExecuteSnapshot();
        }
        //如项目不为空则添加相关数据
        currentProjectInfo.setSnapshot(snapshot);
        if(currentProjectInfo!=null){
            //获取用户token
            CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
            //若token用户已登录
            if(clientToken != null) {
                //根据用户余额变更类型、用户状态、用户编号查询账户信息
                CurrentAccountPrincipalChangeHis queryEntity2 = new CurrentAccountPrincipalChangeHis();
                queryEntity2.setChangeType(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT);
                queryEntity2.setStatus(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_FREEZE);
                queryEntity2.setAccountId(clientToken.getCustomerId());
                //用户已投额度（用来计算用户还可投额度）
                Double changeValueSum = currentAccountPrincipalChangeHisService.getChangeValueSum(queryEntity2);
                //获取用户的活期投资的信息（主要获取用户所得利息，投资金额、赎回金额）
                CurrentAccountSummary currentAccountSummary = currentAccountSummaryService.getByAccountId(clientToken.getCustomerId());
                //获取会员账户余额信息（用来计算用户的可用额度）
                CustomerBalance customerBalance = customerBalanceService.get(clientToken.getCustomerId() + "");
                this.SetAdCurrentResp(currentProjectInfo, map,changeValueSum, customerBalance,currentAccountSummary);
            }else{
                //未登录（获取项目的基本信息，不获取用户的账户可用余额，项目还可投金额）
                this.SetAdCurrentResp(currentProjectInfo,map,null,null,null);
            }
        }else{
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
        }
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api currentDetails end...");
        logger.info("api currentDetails total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }

    /**
     *
     * 活期理财数据
     * @param currentProjectInfo
     * @param map
     * @author huangyuchen
     * @return
     */
    public CurrentResp SetAdCurrentResp(CurrentProjectInfo currentProjectInfo,HashMap<String, Object> map, Double changeValueSum,CustomerBalance customerBalance, CurrentAccountSummary currentAccountSummary){
        CurrentResp cr =new CurrentResp();
        //活期项目id
        cr.setProjectId(Integer.parseInt(currentProjectInfo.getId()));
        //项目名称
        cr.setProjectName(currentProjectInfo.getName());
        //项目提示
        cr.setProjectTips(currentProjectInfo.getTips());
        //还款方式
        cr.setRepaymentMode("按日计息");
        //融资金额
        cr.setFinanceMoney(currentProjectInfo.getFinanceMoney());
        //单位净值
        cr.setNetWorth(currentProjectInfo.getNetWorth());
        //可投金额
        Double amount =  NumberUtils.formatWithScale(currentProjectInfo.getFinanceMoney()-currentProjectInfo.getSnapshot().getHasFinancedMoney(), 2);
        cr.setAmount(amount);
        //已投百分比(%)
        Double rate = (currentProjectInfo.getSnapshot().getHasFinancedMoney()/currentProjectInfo.getFinanceMoney()) * 100;
        cr.setRate(ApiUtil.formatRate(rate));
        //状态(3-投标中，4--投标结束，5-还款中，6--还款结束，7-清算结束)
        String status=currentProjectInfo.getStatus();
        cr.setStatus(Integer.parseInt(status));
        //状态名称
        String statusName = DictUtils.getDictLabel(currentProjectInfo.getStatus(), "current_project_status", "");
        cr.setStatusName(statusName);
        //年化利率
        Double annualizedRate=currentProjectInfo.getRate();
        cr.setAnnualizedRate(annualizedRate);
        //正常年化利率
        Double annualizedRateNormal=currentProjectInfo.getRate();
        cr.setAnnualizedRateNormal(annualizedRateNormal);
        //活动加息年化利率
        Double annualizedRateAdd=0.0;
        cr.setAnnualizedRateAdd(annualizedRateAdd);
        //每万元日收益
        String interestOf10000Yuan = String.valueOf(NumberUtils.formatNotRoundWithScale((10000*currentProjectInfo.getRate()/CurrentProjectConstant.DAYS_IN_YEAR)+"", 2));
        cr.setInterestOf10000Yuan(interestOf10000Yuan);
        //活动说明（一元起投，灵活存取，按日计息）
        String activityRemark="一元起投，灵活存取，按日计息";
        cr.setActivityRemark(activityRemark);
        //起投金额
        Double  startingAmount=currentProjectInfo.getStartingAmount();
        cr.setStartingAmount(startingAmount);
        //最大投资金额
        Double maxAmount=currentProjectInfo.getMaxAmount();
        cr.setMaxAmount(maxAmount);
        //收益起始日
        String startDate = "T+0";
        cr.setStartDate(startDate);
        //项目简介
        String introduce=currentProjectInfo.getIntroduce();
        cr.setIntroduce(introduce);
        //投资范围
        String investmentScope=currentProjectInfo.getInvestmentScope();
        cr.setInvestmentScope(investmentScope);
        //期限
        String duration=currentProjectInfo.getDuration();
        cr.setDuration(duration);
        //购买规则
        String buyRule=currentProjectInfo.getBuyRule();
        cr.setBuyRule(buyRule);
        //赎回规则
        String redeemRule =currentProjectInfo.getRedeemRule();
        cr.setRedeemRule(redeemRule);
        //计息规则
        String interestCalculateRule=currentProjectInfo.getInterestCalculateRule();
        cr.setInterestCalculateRule(interestCalculateRule);
        //费用
        String fee="加入费用：0元；退出费用：0元";
        cr.setFee(fee);
        //累计投资总额
        Double hasFinancedMoney = currentProjectInfo.getSnapshot().getHasFinancedMoney();
        cr.setHasFinancedMoney(hasFinancedMoney);
        //获得总购买人数
        CurrentAccountPrincipalChangeHis queryEntity = new CurrentAccountPrincipalChangeHis();
        queryEntity.setChangeType(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT);
        queryEntity.setStatus(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_NORMAL);
        queryEntity.setProjectId(Long.parseLong(currentProjectInfo.getId()));
        //购买次数
        Integer buyCount = currentAccountPrincipalChangeHisService.getCount(queryEntity);
        cr.setBuyCount(buyCount);
        //累计已赚收益
        Double hasRepaidMoney = NumberUtils.formatNotRoundWithScale(currentProjectInfo.getSnapshot().getHasRepaidMoney(),2);
        cr.setHasRepaidMoney(hasRepaidMoney);
        //每人购买限额
        Double buyLimit=100000.00;
        cr.setBuyLimit(buyLimit);
        //适用终端编号，多个用逗号间隔(0：PC，1：Android，2：iOS，3：weixin)
        String terminalCodes="0,1,2,3";
        cr.setTerminalCodes(terminalCodes);
        //每日提取限额
        Double extractLimit=50000.00;
        cr.setExtractLimit(extractLimit);
        //项目发布时间(yyyy-MM-dd HH:mi)
        String publishDt = DateUtils.FormatDate(currentProjectInfo.getPublishDt(),"yyyy-MM-dd HH:mm:ss");
        cr.setPublishDt(publishDt);
        //投标截止时间(yyyy-MM-dd HH:mi)
        String endInvestmentDt = DateUtils.FormatDate(currentProjectInfo.getEndInvestmentDt(),"yyyy-MM-dd HH:mm:ss");
        cr.setEndInvestmentDt(endInvestmentDt);
        //可购买额度
        if(currentAccountSummary != null && currentAccountSummary.getCurrentPrincipal() != null){
            Double buyAmounts=CurrentProjectConstant.MAX_INVESTMENT_MONEY-currentAccountSummary.getCurrentPrincipal()-changeValueSum;
            String buyAmount=Double.toString(buyAmounts);
            cr.setBuyAmount(buyAmount);
        }else{
            String buyAmount="100000.00";
            cr.setBuyAmount(buyAmount);
        }

        //用户的账户可用余额
        if(customerBalance !=null){
        Double availableBalances=availableBalance(customerBalance);
            cr.setAvailableBalance(Double.toString(availableBalances));
        }
        //剩余天、时、分
        long distanceMillis = currentProjectInfo.getEndInvestmentDt().getTime() - new Date().getTime();
        long remainDay = distanceMillis / (1000 * 60 * 60 * 24);
        long remainHour = (distanceMillis - remainDay * (1000 * 60 * 60 * 24))/(1000 * 60 * 60);
        long remainMinute = (distanceMillis - remainDay * (1000 * 60 * 60 * 24) - remainHour * (1000 * 60 * 60))/(1000 * 60);
        String  surplusTime = Long.toString(remainDay)+" "+"天"+" "+Long.toString(remainHour)+" "+"时"+" "+Long.toString(remainMinute)+" "+"分";
        cr.setSurplusTime(surplusTime);
        ApiUtil.mapRespData(map,cr, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        return  cr;
    }
}
