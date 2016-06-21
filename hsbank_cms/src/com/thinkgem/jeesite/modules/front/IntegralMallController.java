package com.thinkgem.jeesite.modules.front;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.customer.service.CustomerIntegralSnapshotService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerAddress;
import com.thinkgem.jeesite.modules.entity.IntegralMallProduct;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductOrder;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductSps;
import com.thinkgem.jeesite.modules.integral.service.CustomerAddressService;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductOrderService;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductPriceService;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductService;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductSpsService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

/**
 * 花生乐园
 * @author ydt
 *
 */
@Controller
@RequestMapping("${frontPath}/integralMall")
public class IntegralMallController extends BaseController {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CustomerAddressService customerAddressService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private IntegralMallProductPriceService integralMallProductPriceService;
	@Autowired
	private CustomerIntegralSnapshotService customerIntegralSnapshotService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private IntegralMallProductService integralMallProductService;
	@Autowired
	private IntegralMallProductOrderService integralMallProductOrderService;
	@Autowired
	private IntegralMallProductSpsService integralMallProductSpsService;
	
	@ModelAttribute
	public List<IntegralMallProductOrder> buyOrderList(Model model) {
		List<IntegralMallProductOrder> orderList=integralMallProductOrderService.getOrderListByLast(5);
		for(IntegralMallProductOrder item:orderList){
			item.setCustomerAccountShow(StringUtils.vagueAccountName(item.getCustomerAccountShow()));
		}
		model.addAttribute("buyOrderList", orderList);
		return orderList;
	}
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<String> searchTypes = Arrays.asList(new String[]{"integralRang", "keywords"});
		List<String> integralRanges = Arrays.asList(new String[]{"integralRange0","integralRange1", "integralRange2", "integralRange3", "integralRange4", "integralRange5"});
		List<String> orderBys = Arrays.asList(new String[]{"up_dt desc", "up_dt asc", "price desc", "price asc"});
		String searchType = request.getParameter("searchType");
		String integralRange = request.getParameter("integralRange");
		String keywords = request.getParameter("keywords");
		String orderBy = request.getParameter("orderBy");
		String pageNo = request.getParameter("pageNo");
		if(!searchTypes.contains(searchType)) {
			searchType = searchTypes.get(0);
		}
		if(!integralRanges.contains(integralRange)) {
			integralRange = integralRanges.get(0);
		}
		if(!orderBys.contains(orderBy)) {
			orderBy = orderBys.get(3);
		}
		String theDay=DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
		Page<IntegralMallProduct> page = integralMallProductService.searchList(searchType, integralRange, keywords, orderBy,theDay, pageNo);
		for(IntegralMallProduct item : page.getList()){
			item.setShowPrice(integralMallProductPriceService.getRightShowPrice(item.getProductId().longValue()+"", item.getPrice()));
		}
		model.addAttribute("page", page);
		model.addAttribute("integralRange", integralRange);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keywords", keywords);
		model.addAttribute("orderBy", orderBy);
		return "modules/front/integralMall/integralMall";
	}
	
	/**
	 * 商品详情
	 * @return
	 */
	@RequestMapping("commodityDetails")
	public String commodityDetails(String productId, Model model) {
		IntegralMallProduct integralMallProduct = integralMallProductService.get(productId);
		integralMallProduct.setShowPrice(integralMallProductPriceService.getRightShowPrice(integralMallProduct.getProductId().longValue()+"", integralMallProduct.getPrice()));
		List<IntegralMallProductSps> spsList = integralMallProductSpsService.findListByProductId(productId);
		model.addAttribute("spsList", spsList);
		model.addAttribute("product", integralMallProduct);
		return "modules/front/integralMall/commodityDetails";
	}
	
	/**
	 * 订单确认
	 * @return
	 */
	@RequestMapping("confirmOrder")
	public String confirmOrder(String productId, Integer count, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		List<CustomerAddress> customerAddressList = customerAddressService.findListByAccountId(accountId);
		model.addAttribute("customerAddressList", customerAddressList);
		
		IntegralMallProduct integralMallProduct = integralMallProductService.get(productId);
		integralMallProduct.setShowPrice(integralMallProductPriceService.getRightShowPrice(integralMallProduct.getProductId().longValue()+"", integralMallProduct.getPrice()));
		model.addAttribute("createDate", new Date());
		model.addAttribute("product", integralMallProduct);
		model.addAttribute("count", count);
		return "modules/front/integralMall/confirmOrder";
	}
	
	/**
	 * 结算
	 * @return
	 */
	@RequestMapping("settlement")
	public String settlement(String addressId, String productId, int count, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		Map<String,Object> map = integralMallProductOrderService.buy(accountId, productId, addressId, count, ProjectConstant.OP_TERM_DICT_PC);
		if(map.get("orderNo") != null) {
			model.addAttribute("orderNo", map.get("orderNo"));
		}
		model.addAttribute("isSuccess", map.get("isSuccess"));
		model.addAttribute("message", map.get("message"));
		return "modules/front/integralMall/settlement";
	}
	
	/**
	 * 订单详情
	 * @return
	 */
	@RequestMapping("orderDetails")
	public String orderDetails(String orderNo, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		IntegralMallProductOrder integralMallProductOrder = integralMallProductOrderService.getByOrderNo(orderNo, accountId);
		IntegralMallProduct integralMallProduct = integralMallProductService.get(integralMallProductOrder.getProductId() + "");
		model.addAttribute("order", integralMallProductOrder);
		model.addAttribute("product", integralMallProduct);
		return "modules/front/integralMall/orderDetails";
	}
	
	/**
	 * 个人中心
	 * @return
	 */
	@RequestMapping("personalCenter")
	public String personalCenter(String recentMonth, String pageNo, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		IntegralMallProductOrder integralMallProductOrder = new IntegralMallProductOrder();
		integralMallProductOrder.setCustomerAccount(accountId);
		if("all".equals(recentMonth)) {
			integralMallProductOrder.setCreateDtStart(null);
			integralMallProductOrder.setCreateDtEnd(null);
		} else if("three".equals(recentMonth)) {
			integralMallProductOrder.setCreateDtStart(DateUtils.addMonths(new Date(), -3));
			integralMallProductOrder.setCreateDtEnd(new Date());
		} else {
			recentMonth = "one";
			integralMallProductOrder.setCreateDtStart(DateUtils.addMonths(new Date(), -1));
			integralMallProductOrder.setCreateDtEnd(new Date());
		}
		Page<IntegralMallProductOrder> page = integralMallProductOrderService.findCustomerList(integralMallProductOrder, pageNo);
		CustomerAccount customerAccount = customerAccountService.get(accountId + "");
		model.addAttribute("page", page);
		model.addAttribute("recentMonth", recentMonth);
		model.addAttribute("avatar_image", customerAccount.getAvatarImage());
		model.addAttribute("customerAccount", customerAccount);
		model.addAttribute("customerIntegralSnapshot", customerIntegralSnapshotService.getByAccountId(accountId));
		model.addAttribute("customerBase",customerBaseService.getByAccountId(accountId));
		List<CustomerAddress> customerAddressList = customerAddressService.findListByAccountId(accountId);
		model.addAttribute("customerAddressList", customerAddressList);
		return "modules/front/integralMall/personalCenter";
	}
	
	/**
	 * @param customerAddress
	 * @return
	 */
	@RequestMapping("saveAddress")
	@ResponseBody
	public String saveAddress(CustomerAddress customerAddress) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		Map<String,Object> result = new HashMap<String,Object>();
		customerAddress.setAccountId(accountId);
		if(StringUtils.isBlank(customerAddress.getId())) {
			customerAddressService.insert(customerAddress);
			result.put("isAdd", true);
		} else {
			customerAddressService.update(customerAddress);
			result.put("isAdd", false);
		}
		result.put("customerAddress", customerAddress);
		return JsonMapper.toJsonString(result);
	}
	
	/**
	 * 获取地址
	 * @param id
	 * @return
	 */
	@RequestMapping("getCustomerAddress")
	@ResponseBody
	public String getCustomerAddress(String id) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerAddress customerAddress = customerAddressService.get(id);
		if(customerAddress.getAccountId().longValue() != accountId.longValue()) {
			throw new ServiceException("只能查看本人的地址！");
		}
		return JsonMapper.toJsonString(customerAddress);
	}
	
	/**
	 * 删除地址（将status置为删除状态）
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteCustomerAddress")
	@ResponseBody
	public String deleteCustomerAddress(String id) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerAddress customerAddress = customerAddressService.get(id);
		customerAddress.setAccountId(accountId);
		customerAddress.setStatus(ProjectConstant.CUSTOMER_ADDRESS_STATUS_DELETED);
		customerAddressService.update(customerAddress);
		return id;
	}
	
	/**
	 * 设为默认地址
	 * @param id
	 * @return
	 */
	@RequestMapping("setDefaultCustomerAddress")
	@ResponseBody
	public String setDefaultCustomerAddress(String id) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerAddress customerAddress = customerAddressService.get(id);
		customerAddress.setAccountId(accountId);
		customerAddress.setIsDefault(ProjectConstant.CUSTOMER_ADDRESS_IS_DEFAULT_YES);
		String oldDefaultAddressId = customerAddressService.setDefaultAddress(customerAddress);
		return oldDefaultAddressId;
	}
}
