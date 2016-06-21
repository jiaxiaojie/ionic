package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.to.MyInvitationResp;
import com.thinkgem.jeesite.modules.api.to.PageResponse;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 我的邀请Controller
 * <p/>
 *
 * @createDate 2016-5-13
 */
@Controller("apiInvitationController")
@RequestMapping(value = "${frontPath}/api/myInvitation", method = RequestMethod.POST)
public class InvitationController extends APIBaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private ProjectInvestmentRecordService projectInvestmentRecordService;
    @Autowired
    private CustomerBaseService customerBaseService;
    @Autowired
    private CustomerBalanceService customerBalanceService;

    /**
     * 我的邀请-好友分页列表
     *
     * @param client client
     * @param token tokent
     * @param pageNumber 页码
     * @param pageSize 页容量
     * @return json
     * @author liuguoqing
     */
    @ResponseBody
    @RequestMapping(value = "friendsPageList")
    public Map<String, Object> friendsPageList(String client, String token, Integer pageNumber, Integer pageSize) {
        long begin = System.currentTimeMillis();
        logger.debug("=== 我的邀请-好友分页列表接口,输入参数：{}", client);
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(token)) {//验证token是否为空
            ApiUtil.tokenInvalidRespMap(map);
            return map;
        }
        PageResponse<MyInvitationResp> pageResp = new PageResponse<>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
        if (null != clientToken) {
            long count = customerAccountService.countRecommendorByAccountId(clientToken.getCustomerId());
            List<MyInvitationResp> resultList = new ArrayList<>();
            if (count > 0) {
                //根据用户账号获取邀请好友列表
                List<CustomerAccount> list = customerAccountService.getPageListByRecommendorAccountId(clientToken.getCustomerId(), pageNumber, pageSize);
                for (CustomerAccount ca : list) {
                    CustomerBase customerBase = customerBaseService.getByAccountId(ca.getAccountId());
                    //获取状态和状态名称
                    String status = ApiConstant.MY_INVITATION_STATUS_REGISTERED;
                    String statusName = "已注册";
                    int afterRepaymentCount = projectInvestmentRecordService.getRecordCountAfterRepaymentByAccountId(ca.getAccountId());
                    if (afterRepaymentCount > 0) {
                        status = ApiConstant.MY_INVITATION_STATUS_TRADED;
                        statusName = "已投资";
                    } else {
                        int investCount = projectInvestmentRecordService.getInvestCountByAccountId(ca.getAccountId());
                        if (investCount > 0) {
                            status = ApiConstant.MY_INVITATION_STATUS_INVESTED;
                            statusName = "已投资";
                        } else {
                            CustomerBalance customerBalance = customerBalanceService.get(ca.getAccountId() + "");
                            if (customerBalance.getRechargeCount() > 0) {
                                status = ApiConstant.MY_INVITATION_STATUS_RECHARGE;
                                statusName = "已充值";
                            } else if ("1".equals(ca.getHasOpenThirdAccount())) {
                                status = ApiConstant.MY_INVITATION_STATUS_HASOPENTHIRD;
                                statusName = "已开通资金托管账户";
                            }
                        }
                    }
                    MyInvitationResp data = new MyInvitationResp();
                    data.setName(StringUtils.toStr(customerBase.getCustomerName()));
                    data.setAccount(StringUtils.toStr(customerBase.getMobile()));
                    data.setStatus(NumberUtils.toLong(status, 0L));
                    data.setStatusName(statusName);
                    data.setRegisterChannel(ca.getRegisterChannel());
                    Date registerDt = ca.getRegisterDt() != null ? ca.getRegisterDt() : new Date();
                    data.setRegisterDt(DateUtils.formatDateTime(registerDt));
                    resultList.add(data);
                }
            }
            pageResp.setCount(count);
            pageResp.setResultList(resultList);
            ApiUtil.mapRespData(map, pageResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        } else {
            ApiUtil.tokenInvalidRespMap(map);
        }
        logger.debug("=== 我的邀请-好友分页列表接口,cost:{}, 输出参数：{}", (System.currentTimeMillis() - begin), pageResp);
        return map;
    }

}
