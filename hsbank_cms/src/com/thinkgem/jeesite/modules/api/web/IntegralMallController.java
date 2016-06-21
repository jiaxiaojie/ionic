package com.thinkgem.jeesite.modules.api.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.frame.format.annotation.Formater;
import com.thinkgem.jeesite.modules.api.frame.generator.APIGenerator;
import com.thinkgem.jeesite.modules.api.frame.generator.convert.HSAPIConvertAction;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.API;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APINode;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APIObjectNode;
import com.thinkgem.jeesite.modules.api.to.IntegralProductOrderResp;
import com.thinkgem.jeesite.modules.api.to.ProvinceResp;
import com.thinkgem.jeesite.modules.api.web.param.AreaListParams;
import com.thinkgem.jeesite.modules.api.web.param.ProductPageListParams;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.entity.IntegralMallProduct;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductOrder;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductOrderService;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductPriceService;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 花生乐园Controller
 *
 * @author liuguoqing
 *         <p/>
 * @createDate 2016-5-24
 */
@Controller("apiIntegralMallController")
@RequestMapping(value = "${frontPath}/api/integralMall")
public class IntegralMallController extends APIBaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IntegralMallProductOrderService integralMallProductOrderService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private IntegralMallProductService integralMallProductService;
    @Autowired
    private IntegralMallProductPriceService integralMallProductPriceService;

    /**
     * 花生乐园-参与记录
     *
     * @param client client
     * @param limit 查询限制数量
     * @return json
     * @author liuguoqing
     */
    @ResponseBody
    @RequestMapping(value = "buyOrderList")
    public Map<String, Object> orderPageList(String client, @RequestParam(required = false, defaultValue = "10") Integer limit ) {
        long begin = System.currentTimeMillis();
        logger.debug("=== 花生乐园-参与记录接口,输入参数：client:{}", client);
        Map<String, Object> map = new HashMap<String, Object>();
        List<IntegralProductOrderResp> dataList = new ArrayList<IntegralProductOrderResp>();
        List<IntegralMallProductOrder> orderList = integralMallProductOrderService.getLastOrders(limit);
        for (IntegralMallProductOrder order : orderList) {
            IntegralProductOrderResp data = new IntegralProductOrderResp();
            data.setProductId(order.getProductId());
            data.setProductCount(order.getProductCount());
            data.setCustomerName(StringUtils.vagueAccountName(order.getCustomerAccountShow()));
            //订单商品信息
            if (order.getProduct() != null) {
                data.setProductName(order.getProduct().getProductName());
                data.setProductLogoMin(ApiUtil.imageUrlConver(order.getProduct().getProductLogoMin()));
            }
            dataList.add(data);
        }
        ApiUtil.mapRespData(map, dataList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        logger.debug("=== 花生乐园-参与记录接口,cost:{}, 输出参数：{}", (System.currentTimeMillis() - begin), dataList);
        return map;
    }

    /**
     * 花生乐园-区域列表
     * @param areaListParams
     * @author 万端瑞
     * @return 返回区区id的所有子区域
     */
    @Formater(path = "${frontPath}/api/integralMall/areaList")
    @ResponseBody
    @RequestMapping(value = "areaList")
    public API areaList(AreaListParams areaListParams) {
        List<Area> areas = areaService.findByParentId(areaListParams.getAreaId());
        API api = APIGenerator.toAPIWithCollection(areas);
        return api;
    }

    /**
     * 花生乐园-商品分页列表
     * @param productPageListParams
     * @author 万端瑞
     * @return
     */
    @Formater(path = "${frontPath}/api/integralMall/productPageList")
    @ResponseBody
    @RequestMapping(value = "productPageList")
    public API productPageList(ProductPageListParams productPageListParams) {
        //查询数据
        Long count = null;
        List<IntegralMallProduct> integralMallProducts = null;
        if(productPageListParams.getFlag() == 1){
            Page<IntegralMallProduct> page = integralMallProductService.findPage(new Page<IntegralMallProduct>(productPageListParams.getPageNumber(),productPageListParams.getPageSize(),true),productPageListParams.getType());
            count = page.getCount();
            integralMallProducts = page.getList();
        }else{
            integralMallProducts = integralMallProductService.findList(productPageListParams.getType(),(productPageListParams.getPageNumber()-1)*productPageListParams.getPageSize(),productPageListParams.getPageSize());
            count = 0L;
        }

        //创建API resultList节点构建策略
        HSAPIConvertAction convertction = new HSAPIConvertAction<IntegralMallProduct>(){
            @Override
            public Map<String, Object> convert(IntegralMallProduct dataObject) {
                dataObject.setShowPrice(integralMallProductPriceService.getRightShowPrice(dataObject.getProductId().longValue()+"", dataObject.getPrice()));
                APIObjectNode apiObjectNode = new APIObjectNode(dataObject);
                apiObjectNode.put("introduction",ApiUtil.prefixSrc(dataObject.getProductIntroduction()));
                return apiObjectNode;
            }

            @Override
            public int compare(APIObjectNode o1, APIObjectNode o2) {
                return o1.get("showPrice",Integer.class).compareTo(o2.get("showPrice",Integer.class));
            }
        };

        //执行节点构建
        APINode resultList = APIGenerator.toAPINodeWithCollection(integralMallProducts,convertction);

        //生成API
        API api = APIGenerator.createAPIBuilder().putDataChildNode("count",count).putDataChildNode("resultList",resultList).build();
        return api;
    }
    /**
     * 
     * <p>
     * Description:花生乐园-区域列表(app端)<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年6月20日
     * @param client
     * @param token
     * void
     */
    @RequestMapping("areaListByApp")
    public void areaListByApp(String client,String token,HttpServletResponse response){
    	HashMap<String, Object> map = new HashMap<String, Object>();	
    	Date start=new Date();
    	String opTerm = ApiUtil.getOperTerm(client);
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,opTerm);
    	if(clientToken!=null){
    		List<ProvinceResp> provinceResps=UserUtils.getAreaListByApp();
        	ApiUtil.mapRespData(map, provinceResps, "访问成功", true);
    	}else{
    		ApiUtil.tokenInvalidRespMap(map);
    	}
    	printLog("花生乐园-区域列表", client, start, response, map);
    }


}
