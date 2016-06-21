package com.thinkgem.jeesite.modules.api.web;

import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TokenUtils;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.annotation.Authenticate;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.client.ClientProperty;
import com.thinkgem.jeesite.modules.api.to.MyAssetsResp;
import com.thinkgem.jeesite.modules.api.to.MyInfoResp;
import com.thinkgem.jeesite.modules.api.to.MyProfitResp;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountSummaryService;
import com.thinkgem.jeesite.modules.customer.service.*;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.service.MessageInstanceService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 我的账户Controller
 *
 * @author lzb
 * @version 2016-5-13
 */
@Controller("apiAccountController")
@RequestMapping(value="${frontPath}/api/myAccount", method = RequestMethod.POST)
public class AccountController extends APIBaseController {
    @Autowired
    private CustomerBalanceService customerBalanceService;
    @Autowired
    private CurrentAccountSummaryService currentAccountSummaryService;
    @Autowired
    private CustomerBalanceHisService customerBalanceHisService;
    @Autowired
    private CustomerIntegralSnapshotService customerIntegralSnapshotService;
    @Autowired
    private YeepayCommonHandler yeepayCommonHandler;
    @Autowired
    private CustomerBankCardService customerBankCardService;
    @Autowired
    private ProjectInvestmentRecordService projectInvestmentRecordService;
    @Autowired
    private LogThirdPartyService logThirdPartyService;
    @Autowired
    private CustomerInvestmentTicketService customerInvestmentTicketService;
    @Autowired
    private CustomerRateTicketService customerRateTicketService;
    @Autowired
    private MessageInstanceService messageInstanceService;
    @Autowired
    private CustomerAccountService customerAccountService;


    /**
     * 我的账号-账户资产
     * @param client
     * @return
     */
    @Authenticate
    @ResponseBody
    @RequestMapping(value = "myAssets")
    public Map<String,Object> myAssets(String client){
        //获得当前用户
        CustomerClientToken clientToken = TokenUtils.getCurrentCustomerClientToken();
        HashMap<String, Object> map = new HashMap<String, Object>();

        Long accountId = clientToken.getCustomerId();
        CustomerBalance customerBalance = customerBalanceService.get(accountId + "");
        CurrentAccountSummary summary = currentAccountSummaryService.getByAccountId(accountId);

        Double willPrincipal = customerBalance.getWillPrincipal() !=null ? customerBalance.getWillPrincipal() : 0.0;
        Double currentPrincipal = summary.getCurrentPrincipal() !=null ? summary.getCurrentPrincipal() : 0.0;
        MyAssetsResp myAssets = new MyAssetsResp();
        myAssets.setRegularInvestment(willPrincipal);
        myAssets.setCurrentInvestment(currentPrincipal);
        myAssets.setCongealVal(customerBalance.getCongealVal());
        myAssets.setAvailableBalance(availableBalance(customerBalance));
        //待收本金 = 定期待收本金 + 活期当前持有本金
        willPrincipal = LoanUtil.formatAmount(willPrincipal + currentPrincipal);
        myAssets.setNetAssets(LoanUtil.formatAmount(customerBalance.getGoldBalance() + willPrincipal));
        ApiUtil.mapRespData(map, myAssets, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);

        return map;
    }

    /**
     * 我的账号-账户收益
     * @param client
     * @return
     */
    @Authenticate
    @ResponseBody
    @RequestMapping(value = "myProfit")
    public Map<String,Object> myProfit(String client){
        //获得当前用户
        CustomerClientToken clientToken = TokenUtils.getCurrentCustomerClientToken();
        HashMap<String, Object> map = new HashMap<String, Object>();

        Long accountId = clientToken.getCustomerId();
        CustomerBalance customerBalance = customerBalanceService.get(accountId + "");
        CurrentAccountSummary summary = currentAccountSummaryService.getByAccountId(accountId);

        Double currentProfit = LoanUtil.formatAmount(summary.getTotalGetInterest());
        Double regularProfit = LoanUtil.formatAmount(customerBalance.getSumProfit());
        Double willProfit = LoanUtil.formatAmount(customerBalance.getWillProfit());
        Double activityReward = LoanUtil.formatAmount(customerBalanceHisService.getActivityReward(accountId));
        Double profitTotal = LoanUtil.formatAmount(regularProfit + currentProfit + activityReward + willProfit);
        MyProfitResp myProfit = new MyProfitResp();
        myProfit.setCurrentProfit(currentProfit);
        myProfit.setWillProfit(willProfit);
        myProfit.setRegularProfit(regularProfit);
        myProfit.setActivityReward(activityReward);
        myProfit.setProfitTotal(profitTotal);
        ApiUtil.mapRespData(map, myProfit, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);

        return map;
    }

    /**
     * 我的账户信息
     * @param client
     * @return
     */
    @Authenticate
    @ResponseBody
    @RequestMapping(value = "myInfo")
    public Map<String,Object> myInfo(HttpServletRequest request, String client){
        //获得当前用户
        CustomerClientToken clientToken = TokenUtils.getCurrentCustomerClientToken();
        HashMap<String, Object> map = new HashMap<String, Object>();

        Long accountId = clientToken.getCustomerId();
        CustomerBalance customerBalance = customerBalanceService.get(accountId + "");
        CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
        CustomerIntegralSnapshot cIntegralSnapshot = customerIntegralSnapshotService.getByAccountId(accountId);
        if(customerBalance != null ){
            //我的信息数据
            getMyInfoResp(request,accountId,client,customerBalance,customerAccount,cIntegralSnapshot,map);
        }else{
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
        }
        return map;
    }

    /**
     * 账户信息详情
     * @param accountId
     * @param client
     * @param customerBalance
     * @param customerAccount
     * @param cIntegralSnapshot
     * @param map
     * @return
     */
    public  MyInfoResp getMyInfoResp(HttpServletRequest request, Long accountId,String client, CustomerBalance  customerBalance, CustomerAccount customerAccount, CustomerIntegralSnapshot cIntegralSnapshot, HashMap<String, Object> map){
        MyInfoResp data = new MyInfoResp();
        data.setAccountId(String.valueOf(customerAccount.getAccountId()));
        data.setAvatar(ApiUtil.imageUrlConver(customerAccount.getAvatarImage()));
        data.setAccountName(StringUtil.dealString(customerAccount.getAccountName()));
        data.setNickname(StringUtil.dealString(customerAccount.getNickname()));

        data.setMobile(customerAccount.getCustomerBase().getMobile());
        data.setEmail(StringUtil.dealString(customerAccount.getCustomerBase().getEmail()));
        data.setAvailableBalance(availableBalance(customerBalance));
        data.setAvailableIntegral(cIntegralSnapshot.getIntegralBalance().doubleValue());
        data.setHasSigned(customerIntegralSnapshotService.canSign(accountId) == false ? "true" : "false");
        AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
        String customerName ="";
        String certNum = "";
        if(ProjectConstant.HASOPENED.equals(accountInfoResp.getCode())){
            customerName = customerAccount.getCustomerBase().getCustomerName();
            certNum = customerAccount.getCustomerBase().getCertNum();
        }
        certNum = StringUtils.isNotBlank(certNum) ? StringUtils.vagueCertNum(certNum) :  certNum;
        data.setCertNum(StringUtil.dealString(certNum));
        data.setCustomerName(StringUtil.dealString(customerName));
        data.setHasOpenThirdAccount(ProjectConstant.HASOPENED.equals(accountInfoResp.getCode()) ? ProjectConstant.HASOPENED : ProjectConstant.UNOPENED);
        data.setHasBindBankCard(customerBankCardService.hasBindBankCard(accountInfoResp) == true ? "1" : "0");
        data.setIsNewUser(projectInvestmentRecordService.isNewCustomer(accountId + "") == true ? "0" : "1");
        boolean hasRecharged = customerBalance.getRechargeCount() > 0 ? true : false;
        if(!hasRecharged){
            hasRecharged = logThirdPartyService.isHasRecharged(customerAccount.getPlatformUserNo());
        }
        data.setHasRecharged(hasRecharged ? "0" : "1");
        //获取用户可用现金券价值总额与总数
        Map<String,Object> cashTicketStatistics = customerInvestmentTicketService.getTicketStatistics(accountId,ProjectConstant.TICKET_DICT_NORMAL);
        cashTicketStatistics = cashTicketStatistics !=null ? cashTicketStatistics : new HashMap<String,Object>();
        data.setCashCouponAmount(NumberUtils.toDouble(cashTicketStatistics.get("sumDenomination")+""));
        data.setCashCouponCount(NumberUtils.toInt(cashTicketStatistics.get("count")+""));
        data.setTicketCount(customerBalance.getFreeWithdrawCount());
        Map<String,Object> rateTicketStatistics = customerRateTicketService.getRateTicketStatistics(accountId,ProjectConstant.TICKET_DICT_NORMAL);
        rateTicketStatistics = rateTicketStatistics !=null ? rateTicketStatistics : new HashMap<String,Object>();
        data.setInterestCouponCount(NumberUtils.toInt(rateTicketStatistics.get("count")+""));
        data.setUnreadMsgTotal(this.getUnreadMsgTotal(accountId,client));
        data.setLastLoginTime(DateUtils.formatDateTime(customerAccount.getLastLoginDt()));
        data.setConsecutiveDays(cIntegralSnapshot.getConsecutiveDays());
        ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        return data;
    }

    /**
     * 获取未读消息
     * @param accountId
     * @param client
     * @return
     */
    public  int getUnreadMsgTotal(Long accountId, String client){
        //获得请求渠道
        String opTerm = ApiUtil.getOperTerm(client);
        String messageChannel = ApiUtil.getPushChannel(opTerm);
        //设置消息查询时间区间
        PageSearchBean pageSearchBean = new PageSearchBean();
        pageSearchBean.setDefaultDateRangeWithMonths(-3);

        //查询未读信息总记录
        int unreadCount = messageInstanceService.getUnreadCount(accountId, null,messageChannel,MessageConstant.MESSAGE_STATUS_READ,pageSearchBean.getStartDateTime(), pageSearchBean.getEndDateTime());
        return unreadCount;
    }

    /**
     * 签到
     * @param response
     * @param client
     * @param token
     * @return
     */
    @Authenticate
    @RequestMapping(value = "sign", method = RequestMethod.POST)
    public String sign(HttpServletResponse response, String client, String token) {
        Date startTime = new Date();
        long currentTime = System.currentTimeMillis();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api sign start...");
        logger.info("【" + currentTime + "】sign income parameter: token=" + token + "");
        HashMap<String, Object> map = new HashMap<String, Object>();
        //获得当前用户
        CustomerClientToken clientToken = TokenUtils.getCurrentCustomerClientToken();
        CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
        if(customerIntegralSnapshotService.canSign(customerAccount.getAccountId())){
            ClientProperty cProperty = ApiUtil.getClient(client);
            Map signMap = customerIntegralSnapshotService.sign(customerAccount.getAccountId(), ApiUtil.getOperaChannel(cProperty.getType()));
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("changeVal",signMap.get("signIntegral"));
            dataMap.put("consecutiveDays",signMap.get("consecutiveSignDays"));
            ApiUtil.mapRespData(map, dataMap, "签到成功", true);
        }else{
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "今日已签到", false);
        }
        Date endTime = new Date();
        logger.info("【" + currentTime + "】sign out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api sign end...");
        logger.info("api sign total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }

    /**
     *我的账号-投资失败的冻结资金
     * @param response
     * @param client
     * @return
     */
    @Authenticate
    @RequestMapping(value = "frozenAmountByInvestFail", method = RequestMethod.POST)
    public String frozenAmountByInvestFail(HttpServletResponse response, String client) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api frozenAmountByInvestFail start...");
        HashMap<String, Object> map = new HashMap<String, Object>();
        //获得当前用户
        CustomerClientToken clientToken = TokenUtils.getCurrentCustomerClientToken();
        //投资收益
        Double frozenAmount = projectInvestmentRecordService.getSumFrozenAmount(clientToken.getCustomerId());
        BigDecimal frozenMoney = new BigDecimal(frozenAmount);
        frozenMoney = frozenMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
        ApiUtil.mapRespData(map, frozenMoney, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);

        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api frozenAmountByInvestFail end...");
        logger.info("api frozenAmountByInvestFail total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }


}
