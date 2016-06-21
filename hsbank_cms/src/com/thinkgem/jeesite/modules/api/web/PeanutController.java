package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.to.MyIntegralResp;
import com.thinkgem.jeesite.modules.api.to.MyOrderResp;
import com.thinkgem.jeesite.modules.api.to.PageResponse;
import com.thinkgem.jeesite.modules.customer.service.CustomerIntegralHisService;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.entity.CustomerIntegralHis;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductOrder;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductOrderService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.hsbank.util.type.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 我的花生Controller
 *
 * @author huangyuchen
 * @version 2016-5-23
 */
@Controller("apiMyPeanutController")
@RequestMapping(value="${frontPath}/api/myPeanut", method = RequestMethod.POST)
public class PeanutController extends APIBaseController {
    @Autowired
    private CustomerIntegralHisService customerIntegralHisService;
    @Autowired
    private IntegralMallProductOrderService integralMallProductOrderService;
    @Autowired
    private AreaService areaService;

    /**
     * 我的花生豆分页列表
     *
     * @param response
     * @param client
     * @param token
     * @param type
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "myPeanutPageList", method = RequestMethod.POST)
    public String myPeanutPageList(HttpServletResponse response, String client, String type, String token, Integer pageNumber, Integer pageSize) {
        Date startTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myPeanutPageList start...");
        HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
        if (clientToken != null) {
            List<CustomerIntegralHis> list = customerIntegralHisService.findPageList(clientToken.getCustomerId(), type, pageNumber, pageSize);
            //查询总数
            Integer count = customerIntegralHisService.getCount(clientToken.getCustomerId(), type);
            List<Object> dataList = new ArrayList<Object>();
            for (CustomerIntegralHis cIntegralHis : list) {
                MyIntegralResp data = new MyIntegralResp();
                data.setOpDt(DateUtils.formatDateTime(cIntegralHis.getOpDt()));
                data.setChangeReason(cIntegralHis.getChangeReason());
                data.setChangeVal(String.valueOf(cIntegralHis.getChangeVal()));
                String types = cIntegralHis.getChangeVal().compareTo(0) < 0 ? "2" : "1";
                data.setType(types);
                data.setTypeName(DictUtils.getDictLabel(cIntegralHis.getChangeType(), "customer_integral_change_type_dict", ""));
                dataList.add(data);
            }
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("count",count);
            dataMap.put("resultList",dataList);
            ApiUtil.mapRespData(map, dataMap, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        } else {
            ApiUtil.tokenInvalidRespMap(map);
        }
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api myPeanutPageList end...");
        logger.info("api myPeanutPageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        return renderString(response, map);
    }

    /**
     * 我的花生-兑换记录(订单)分页列表
     *
     * @param client client
     * @param token  token
     * @param pageNumber 页码
     * @param pageSize 页容量
     * @return json
     * @author liuguoqing
     */
    @ResponseBody
    @RequestMapping(value = "orderPageList")
    public Map<String, Object> orderPageList(String client, String token, @RequestParam(required = false, defaultValue = "0") Integer searchFlag, Integer pageNumber, Integer pageSize) {
        long begin = System.currentTimeMillis();
        logger.debug("=== 我的花生-兑换记录(订单)分页列表接口,输入参数：client:{}, token:{}", client, token);
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(token)) {//验证token是否为空
            ApiUtil.tokenInvalidRespMap(map);
            return map;
        }
        PageResponse<MyOrderResp> pageResp = new PageResponse<>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
        if (clientToken != null) {
            long count = integralMallProductOrderService.countOrderByAccountId(searchFlag,clientToken.getCustomerId());
            List<MyOrderResp> dataList = new ArrayList<>();
            if (count > 0) {
                List<IntegralMallProductOrder> list = integralMallProductOrderService.getOrderPageList(searchFlag,clientToken.getCustomerId(), pageNumber, pageSize);
                for (IntegralMallProductOrder order : list) {
                    MyOrderResp data = new MyOrderResp();
                    data.setOrderCode(order.getOrderNo());
                    data.setProductId(order.getProductId());
                    data.setProductCount(order.getProductCount());
                    data.setPrice(order.getPrice());
                    data.setProductPrice(order.getProductPrice());
                    data.setCreateDt(DateUtils.formatDateTime(order.getCreateDt()));
                    data.setStatus(NumberUtil.toLong(order.getOrderStatus(), 0L));
                    data.setStatusName(DictUtils.getDictLabel(order.getOrderStatus(), "integral_mall_order_status_dict", ""));
                    //订单商品信息
                    if(order.getProduct() != null) {
                        data.setProductName(order.getProduct().getProductName());
                        data.setLogoMin(ApiUtil.imageUrlConver(order.getProduct().getProductLogoMin()));
                    }
                    dataList.add(data);
                }
            }
            pageResp.setCount(count);
            pageResp.setResultList(dataList);
            ApiUtil.mapRespData(map, pageResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        } else {
            ApiUtil.tokenInvalidRespMap(map);
        }
        logger.debug("===我的花生-兑换记录(订单)分页列表接口,cost:{}, 输出参数：{}", (System.currentTimeMillis() - begin), pageResp);
        return map;
    }

    /**
     * 我的花生-兑换记录(订单)详情
     *
     * @param client client
     * @param token tokent
     * @param orderCode 订单号
     * @return json
     * @author liuguoqing
     */
    @ResponseBody
    @RequestMapping(value = "orderDetails")
    public Map<String, Object> orderDetails(String client, String token, String orderCode) {
        long begin = System.currentTimeMillis();
        logger.debug("=== 我的花生-兑换记录(订单)详情接口,输入参数：client:{}, token:{}, orderCode", client, token, orderCode);
        Map<String, Object> map = new HashMap<>();

        if(StringUtils.isBlank(orderCode)){//验证订单号
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "缺少订单编号！", false);
            return map;
        }
        if(StringUtils.isBlank(token)) {//验证token是否为空
            ApiUtil.tokenInvalidRespMap(map);
            return map;
        }

        CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
        MyOrderResp data = new MyOrderResp();
        if (null != clientToken) {
            IntegralMallProductOrder order = integralMallProductOrderService.getDetailsByOrderNo(clientToken.getCustomerId(), orderCode);
            //订单信息
            if(order != null) {
                data.setOrderCode(order.getOrderNo());
                data.setProductId(order.getProductId());
                data.setProductCount(order.getProductCount());
                data.setPrice(order.getPrice());
                data.setProductPrice(order.getProductPrice());
                data.setCreateDt(DateUtils.formatDateTime(order.getCreateDt()));
                data.setStatus(NumberUtil.toLong(order.getOrderStatus(), 0L));
                data.setStatusName(DictUtils.getDictLabel(order.getOrderStatus(), "integral_mall_order_status_dict", ""));
                data.setTypeId(order.getProductTypeId());
                data.setTypeName(DictUtils.getDictLabel(order.getProductTypeId(), "marketing_product_type_dict", ""));
                //订单商品信息
                if(order.getProduct() != null) {
                    data.setProductName(order.getProduct().getProductName());
                    data.setLogoMin(ApiUtil.imageUrlConver(order.getProduct().getProductLogoMin()));
                }
                //收货地址信息
                if (order.getAddress() != null) {
                    data.setShowName(order.getAddress().getShowName());// 收件人名称
                    data.setMobile(order.getAddress().getMobile());// 收件人手机号
                    data.setAddress(order.getAddress().getAddress());// 收件人地址
                    data.setPostCode(order.getAddress().getPostCode());// 收件人邮编
                    //区县
                    Area district = areaService.get(order.getAddress().getDistrictId());
                    if(district != null) {
                        data.setDistrictId(district.getId());
                        data.setDistrictName(district.getName());
                        //城市
                        Area city = areaService.get(district.getParentId());
                        if(city != null) {
                            data.setCityId(city.getId());
                            data.setCityName(city.getName());
                            //省份
                            Area province = areaService.get(city.getParentId());
                            if(province != null) {
                                data.setProvinceId(province.getId());
                                data.setProvinceName(province.getName());
                            }
                        }
                    }

                }
            }
            ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        } else {
            ApiUtil.tokenInvalidRespMap(map);
        }
        logger.debug("===我的花生-兑换记录(订单)详情接口,cost:{}, 输出参数：{}", (System.currentTimeMillis() - begin), data);
        return map;
    }

}
