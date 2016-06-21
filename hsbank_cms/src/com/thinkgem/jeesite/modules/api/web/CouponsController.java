package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.ticket.intelligent_ticket.CashTicketFacade;
import com.thinkgem.jeesite.common.ticket.util.TicketConstant;
import com.thinkgem.jeesite.common.ticket.util.bean.CashTicketBean;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.frame.spring.validate.login.Token;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.API;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APINode;
import com.thinkgem.jeesite.modules.api.frame.generator.APIGenerator;
import com.thinkgem.jeesite.modules.api.frame.format.annotation.Formater;
import com.thinkgem.jeesite.modules.api.web.param.CashCouponParams;
import com.thinkgem.jeesite.modules.api.web.param.CashCouponStatisticParams;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerFreeWithdrawCountHisService;
import com.thinkgem.jeesite.modules.customer.service.CustomerInvestmentTicketService;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.entity.CustomerInvestmentTicket;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 我的卡券Controller
 *<p/>
 * @createDate 2016-5-13
 */
@Controller("apiCouponsController")
@RequestMapping(value="${frontPath}/api/myCoupons")
public class CouponsController extends APIBaseController {
    @Autowired
    private CustomerInvestmentTicketService customerInvestmentTicketService;

    @Autowired
    private CustomerFreeWithdrawCountHisService customerFreeWithdrawCountHisService;
    @Autowired
    private CustomerBalanceService customerBalanceService;

    /**
     * 我的卡券-现金券
     * @param cashCouponParams
     * @author 万端瑞
     * @return
     */
    @Formater(path = "${frontPath}/api/myCoupons/cashCoupon")
    @ResponseBody
    @RequestMapping(value = "cashCoupon")
    public Map<String,Object> cashCoupon(Token token, CashCouponParams cashCouponParams){
        //查询数据
        CustomerInvestmentTicket CustomerInvestmentTicket = new CustomerInvestmentTicket();
        CustomerInvestmentTicket.setStatus(cashCouponParams.getStatus());
        CustomerInvestmentTicket.setAccountId(token.getAccountId());
        Page<CustomerInvestmentTicket> page = customerInvestmentTicketService.findPage(new Page<CustomerInvestmentTicket>(cashCouponParams.getPageNumber(),cashCouponParams.getPageSize(),true),CustomerInvestmentTicket);
        
        //数据节点构建
        APINode apiDataNode = APIGenerator.toAPINodeWithCollection(page.getList());

        //构建API
        API api = APIGenerator.createAPIBuilder().putDataChildNode("count",page.getCount())
                                          .putDataChildNode("resultList", apiDataNode).build();

        return api;
    }


    /**
     * 我的卡券-提现券
     * @author 万端瑞
     * @return
     */
    @Formater(path = "${frontPath}/api/myCoupons/withdrawCoupon")
    @ResponseBody
    @RequestMapping(value = "withdrawCoupon")
    public Map<String,Object> withdrawCoupon(Token token){
        //查询数据
        Map<String, Object> statistics = customerFreeWithdrawCountHisService.statistics(token.getAccountId());
        CustomerBalance customerBalance = customerBalanceService.get(StringUtils.toStr(token.getAccountId()));
        statistics.put("unusedCount",customerBalance.getFreeWithdrawCount());

        //构造API 并返回
        return APIGenerator.toAPI(statistics);
    }


    /**
     * 我的卡券-现金券统计
     * @author 万端瑞
     * @return
     */
    @Formater(path = "${frontPath}/api/myCoupons/cashCouponStatistic")
    @ResponseBody
    @RequestMapping(value = "cashCouponStatistic")
    public Map<String,Object> cashCouponStatistic(Token token,CashCouponStatisticParams cashCouponStatisticParams){
        //获取用户可用现金券价值总额与总数
        Map<String,Object> ticketStatistics = customerInvestmentTicketService.getTicketStatistics(token.getAccountId(), cashCouponStatisticParams.getStatus());
        //构造API 并返回
        return APIGenerator.toAPI(ticketStatistics);
    }


    /**
     * 智能选券
     * @param response
     * @param client
     * @param token
     * @param amount
     * @return
     */
    @RequestMapping(value = "intelligentChoose", method = RequestMethod.POST)
    public String intelligentChoose(HttpServletResponse response, String client, String token, Double amount) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myCoupons intelligentChoose start...");
        HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
        if(clientToken != null){
            Long accountId = clientToken.getCustomerId();
            List<CashTicketBean> list = customerInvestmentTicketService.findChooseListByAccountId(accountId, amount);
            Map<String, Object> resultList = CashTicketFacade.getCashTicketService().getBestTicketMap(list, amount);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<Long> couponsIds = (List<Long>)resultList.get("ticketIds");
            couponsIds = couponsIds != null ? couponsIds : new ArrayList<Long>();
            Double amountData = resultList.get("amount") != null ? (Double)resultList.get("amount") : 0.0;
            int count = couponsIds.size();
            dataMap.put("count",count);
            dataMap.put("amount",amountData);
            dataMap.put("couponsIds",couponsIds.toArray());
            int canUseCount = count > 0 ? customerInvestmentTicketService.getCountByAccountId(accountId, ProjectConstant.CUSTOMER_INVESTMENT_TICKET_STATUS_NORMAL) : 0;
            if(count > 0 && canUseCount <= TicketConstant.INTELLIGENT_MAX_LIMIT_TICKET_NUMBER){
                dataMap.put("resultList",customerInvestmentTicketService.getIntelligenceTicketList(accountId,couponsIds));
            }
            ApiUtil.mapRespData(map, dataMap, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        }else{
            ApiUtil.tokenInvalidRespMap(map);
        }
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api myCoupons intelligentChoose end...");
        logger.info("api intelligentChoose total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }
}
