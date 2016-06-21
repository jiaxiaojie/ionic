package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.annotation.Authenticate;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.po.TransRecordReq;
import com.thinkgem.jeesite.modules.api.to.PageResponse;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceHisService;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 资金管理Controller
 *<p/>
 * @createDate 2016-5-13
 */
@Controller("apiCapitalManageController")
@RequestMapping(value="${frontPath}/api/myCapitalManage", method = RequestMethod.POST)
public class CapitalManageController extends APIBaseController {
    @Autowired
    private CustomerBalanceHisService customerBalanceHisService;

    /**
     * 资金管理-交易记录
     * @param response
     * @param recordReq
     * @return
     */
    @Authenticate
    @ResponseBody
    @RequestMapping(value = "transactionRecord")
    public Map<String,Object> transactionRecord(HttpServletResponse response, TransRecordReq recordReq,
                       @RequestParam(required=false,defaultValue="10")Integer pageSize, @RequestParam(required=false,defaultValue="1")Integer pageNumber) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api transactionRecord start...");
        String opTerm = ApiUtil.getOperTerm(recordReq.getClient());
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(recordReq.getToken(),opTerm);

        HashMap<String, Object> map = new HashMap<String, Object>();
        PageResponse pageResponse = customerBalanceHisService.findBalanceHisPage(new Page<CustomerBalanceHis>(pageNumber, pageSize,true),
                clientToken.getCustomerId(),recordReq);
        ApiUtil.mapRespData(map, pageResponse, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api transactionRecord end...");
        logger.info("api transactionRecord total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return map;
    }


}
