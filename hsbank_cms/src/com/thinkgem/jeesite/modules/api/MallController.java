/**
 * 
 */
package com.thinkgem.jeesite.modules.api;


import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.MyBeanUtils;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.to.MallProductResp;
import com.thinkgem.jeesite.modules.entity.IntegralMallProduct;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductPriceService;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.hsbank.util.type.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author 万端瑞
 *
 */
@Controller
@RequestMapping("${frontPath}/api/mall")
public class MallController extends APIBaseController {
	@Autowired
	private IntegralMallProductService integralMallProductService;

	@Autowired
	private IntegralMallProductPriceService integralMallProductPriceService;
	
	
	
	/**
	 * 查询积分兑换商品分页列表
	 * @author 万端瑞
	 * @param response
	 * @param request
	 * @param client
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "productPageList", method = RequestMethod.POST)
	public String productPageList(HttpServletResponse response,HttpServletRequest request,
			String client,
			@RequestParam(required=false,defaultValue="10")Integer pageSize, 
			@RequestParam(required=false,defaultValue="1")Integer pageNumber){
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api productPageList start...");
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> mRespList = new ArrayList<Object>();
		String cPath = request.getContextPath();
		List<IntegralMallProduct> list = integralMallProductService.getMallProductPageList(pageNumber, pageSize);
		for(IntegralMallProduct product : list){
			mRespList.add(resultSuccess(product, map));
		}
		ApiUtil.mapRespData(map, mRespList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api productPageList end...");
		logger.info("api productPageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 成功返回属性
	 * @param product
	 * @param map
	 */
    public MallProductResp resultSuccess(IntegralMallProduct product, Map<String,Object> map){
    	MallProductResp mResp = new MallProductResp();
    	MyBeanUtils.copyBean2Bean(mResp, product);
    	mResp.setProductId(String.valueOf(product.getProductId()));
    	mResp.setTypeId(String.valueOf(product.getProductTypeId()));
    	mResp.setTypeName(DictUtils.getDictLabel(String.valueOf(product.getProductTypeId()), "marketing_product_type_dict", ""));
    	mResp.setLogoMin(ApiUtil.imageUrlConver( product.getProductLogoMin()));
    	mResp.setLogoNormal(ApiUtil.imageUrlConver(product.getProductLogoNormal()));
    	mResp.setIntroduction(ApiUtil.prefixSrc(product.getProductIntroduction()));
    	mResp.setUpDt(DateUtils.formatDateTime(product.getUpDt()));
    	mResp.setDowDt(DateUtils.formatDateTime(product.getDowDt()));
    	mResp.setStatus(NumberUtil.toLong(product.getStatus(), 0L));
    	mResp.setStatusName(DictUtils.getDictLabel(product.getStatus(), "integral_project_status_dict", ""));
    	return mResp;
    }

	
	/**
	 * 查询积分兑换商品详情
	 * @author 万端瑞
	 * @param response
	 * @param request
	 * @param client
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "productDetail", method = RequestMethod.POST)
	public String productDetail(HttpServletResponse response,HttpServletRequest request,
			String client,String productId){
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api mall productDetail start...");
		Map<String,Object> map = new HashMap<String,Object>();
		String cPath = request.getContextPath();
		IntegralMallProduct product = integralMallProductService.get(productId);
		//构造返回的数据结构
		if(product != null){
			ApiUtil.mapRespData(map, resultDetailSuccess(product, map), ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api mall productDetail end...");
		logger.info("api mall productDetail total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

    /**
     * 详情信息
     * @param product
     * @param map
     * @return
     */
    public MallProductResp resultDetailSuccess(IntegralMallProduct product, Map<String,Object> map){
    	MallProductResp mResp = new MallProductResp();
    	MyBeanUtils.copyBean2Bean(mResp, product);
    	String productId = String.valueOf(product.getProductId());
    	mResp.setProductId(productId);
    	mResp.setTypeId(String.valueOf(product.getProductTypeId()));
    	mResp.setTypeName(DictUtils.getDictLabel(String.valueOf(product.getProductTypeId()), "marketing_product_type_dict", ""));
    	mResp.setLogoMin(ApiUtil.imageUrlConver(product.getProductLogoMin()));
    	mResp.setLogoNormal(ApiUtil.imageUrlConver(product.getProductLogoNormal()));
    	mResp.setIntroduction(ApiUtil.prefixSrc(product.getProductIntroduction()));
    	mResp.setUpDt(DateUtils.formatDateTime(product.getUpDt()));
    	mResp.setDowDt(DateUtils.formatDateTime(product.getDowDt()));
    	mResp.setStatus(NumberUtil.toLong(product.getStatus(), 0L));
    	mResp.setStatusName(DictUtils.getDictLabel(product.getStatus(), "integral_project_status_dict", ""));
		//增加现价 showPrice
		mResp.setShowPrice(integralMallProductPriceService.getRightShowPrice(product.getProductId().longValue()+"", product.getPrice()));
    	return mResp;
    }


	
	





	

}
